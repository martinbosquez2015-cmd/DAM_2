# <center>IES FRANCISCO DE GOYA</center>
__NOMBRE:__ Martin Taboada  
__CURSO:__ DAM 2  
## <center> __Ampliación de entornos de desarrolo:__</center>   <center> __Refactorización__</center>

### __Ejercicio 7: Código Duplicado__
<div align="justify">

&emsp;**Identificación del Code Smell:** El código original presenta una clara duplicación de lógica estructural entre los métodos `guardarEstudiante` y `guardarProfesor`. Ambas validan si el objeto recibido es nulo, llaman a una validación específica, guardan en el repositorio y registran un mensaje de éxito en el log cambiando únicamente el texto.

&emsp;**Código original:**
```java
public void guardarEstudiante(Estudiante estudiante) {
    if (estudiante == null) {
        throw new IllegalArgumentException();
    }
    validarEstudiante(estudiante);
    repositorio.save(estudiante);
    logger.info("Estudiante guardado");
}

public void guardarProfesor(Profesor profesor) {
    if (profesor == null) {
        throw new IllegalArgumentException();
    }
    validarProfesor(profesor);
    repositorio.save(profesor);
    logger.info("Profesor guardado");
}
```

&emsp;**Código refactorizado:**
&emsp;Para eliminar la duplicación, podemos unificar el comportamiento en un método genérico o auxiliar que reciba la función de validación y el mensaje correspondiente, asegurando el principio DRY (*Don't Repeat Yourself*).

```java
public <T> void guardarEntidad(T entidad, Runnable validador, String mensajeExito) {
    if (entidad == null) {
        throw new IllegalArgumentException();
    }
    validador.run();
    repositorio.save(entidad);
    logger.info(mensajeExito);
}

public void guardarEstudiante(Estudiante estudiante) {
    guardarEntidad(estudiante, () -> validarEstudiante(estudiante), "Estudiante guardado");
}

public void guardarProfesor(Profesor profesor) {
    guardarEntidad(profesor, () -> validarProfesor(profesor), "Profesor guardado");
}
```
</div>

### __Ejercicio 8: Método Largo__
<div align="justify">

&emsp;**Identificación del Code Smell:** El método `procesarPedido` realiza demasiadas responsabilidades en un solo lugar (validaciones de cliente y dirección, cálculo de subtotales, aplicación de descuentos, cálculo de IVA, persistencia y envío de correos). Esto dificulta su lectura, mantenimiento y prueba.

&emsp;**Código original:**
```java
public void procesarPedido(Pedido pedido) {
    // Validar cliente
    if (pedido.getCliente() == null) {
        logger.error("Cliente nulo");
        return;
    }

    // Validar dirección
    if (pedido.getDireccion() == null || pedido.getDireccion().isBlank()) {
        logger.error("Dirección vacía");
        return;
    }

    // Calcular totales
    BigDecimal subtotal = BigDecimal.ZERO;
    for (Item item : pedido.getItems()) {
        subtotal = subtotal.add(item.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
    }

    // Aplicar descuento
    BigDecimal descuento = BigDecimal.ZERO;
    if (pedido.getCliente().tieneDescuento()) {
        descuento = subtotal.multiply(new BigDecimal("0.10"));
    }

    // Calcular IVA
    BigDecimal iva = subtotal.subtract(descuento).multiply(new BigDecimal("0.21"));
    BigDecimal total = subtotal.subtract(descuento).add(iva);

    // Guardar pedido
    repositorio.guardar(pedido);

    // Enviar email
    emailService.enviar(pedido.getCliente().getEmail(), "Pedido procesado");
}
```

&emsp;**Código refactorizado:**
&emsp;Se divide el proceso en métodos más pequeños y específicos aplicando el principio de responsabilidad única (*Single Responsibility Principle*).

```java
public void procesarPedido(Pedido pedido) {
    if (!validarPedido(pedido)) {
        return;
    }
    
    BigDecimal subtotal = calcularSubtotal(pedido);
    BigDecimal descuento = calcularDescuento(pedido.getCliente(), subtotal);
    BigDecimal total = calcularTotalConImpuestos(subtotal, descuento);
    
    repositorio.guardar(pedido);
    notificarCliente(pedido);
}

private boolean validarPedido(Pedido pedido) {
    if (pedido.getCliente() == null) {
        logger.error("Cliente nulo");
        return false;
    }
    if (pedido.getDireccion() == null || pedido.getDireccion().isBlank()) {
        logger.error("Dirección vacía");
        return false;
    }
    return true;
}

private BigDecimal calcularSubtotal(Pedido pedido) {
    BigDecimal subtotal = BigDecimal.ZERO;
    for (Item item : pedido.getItems()) {
        subtotal = subtotal.add(item.getPrecio().multiply(BigDecimal.valueOf(item.getCantidad())));
    }
    return subtotal;
}

private BigDecimal calcularDescuento(Cliente cliente, BigDecimal subtotal) {
    if (cliente.tieneDescuento()) {
        return subtotal.multiply(new BigDecimal("0.10"));
    }
    return BigDecimal.ZERO;
}

private BigDecimal calcularTotalConImpuestos(BigDecimal subtotal, BigDecimal descuento) {
    BigDecimal iva = subtotal.subtract(descuento).multiply(new BigDecimal("0.21"));
    return subtotal.subtract(descuento).add(iva);
}

private void notificarCliente(Pedido pedido) {
    emailService.enviar(pedido.getCliente().getEmail(), "Pedido procesado");
}
```
</div>

### __Ejercicio 9: Magic Numbers__
<div align="justify">

&emsp;**Identificación del Code Smell:** El código utiliza números mágicos (`0.21`, `18`, `30`, `7`, `1`) y literales de cadena esparcidos sin contexto explicativo, lo que reduce la legibilidad y complica futuras modificaciones.

&emsp;**Código original:**
```java
public class Calculadora {
    public BigDecimal calcularImpuesto(BigDecimal monto) {
        return monto.multiply(new BigDecimal("0.21"));
    }

    public boolean esMayorDeEdad(int edad) {
        return edad >= 18;
    }

    public int obtenerDiasPlazo(String tipo) {
        return switch (tipo) {
            case "normal" -> 30;
            case "urgente" -> 7;
            case "express" -> 1;
            default -> 30;
        };
    }
}
```

&emsp;**Código refactorizado:**
&emsp;Se reemplazan los números y cadenas mágicas por constantes descriptivas (`static final`) y se introduce un `enum` para los tipos de plazos.

```java
public class Calculadora {
    private static final BigDecimal TASA_IVA = new BigDecimal("0.21");
    private static final int EDAD_MINIMA_MAYORIA = 18;

    public enum TipoPlazo {
        NORMAL(30),
        URGENTE(7),
        EXPRESS(1);

        private final int dias;

        TipoPlazo(int dias) {
            this.dias = dias;
        }

        public int getDias() {
            return dias;
        }
    }

    public BigDecimal calcularImpuesto(BigDecimal monto) {
        return monto.multiply(TASA_IVA);
    }

    public boolean esMayorDeEdad(int edad) {
        return edad >= EDAD_MINIMA_MAYORIA;
    }

    public int obtenerDiasPlazo(String tipoStr) {
        try {
            TipoPlazo tipo = TipoPlazo.valueOf(tipoStr.toUpperCase());
            return tipo.getDias();
        } catch (IllegalArgumentException | NullPointerException e) {
            return TipoPlazo.NORMAL.getDias(); // Valor por defecto
        }
    }
}
```
</div>

### __Ejercicio 10: Nombres Pobres__
<div align="justify">

&emsp;**Identificación del Code Smell:** La clase se llama `C`, el método se llama `p`, y las variables son `a`, `b`, `x`, `y`. Estos nombres carecen por completo de significado semántico, dificultando la comprensión de la lógica matemática subyacente (que corresponde a una diferencia de cuadrados: $(a+b) \times (a-b) = a^2 - b^2$).

&emsp;**Código original:**
```java
public class C {
    public int p(int a, int b) {
        int x = a + b;
        int y = a - b;
        return x * y;
    }
}
```

&emsp;**Código refactorizado:**
&emsp;Se renombran la clase, el método y las variables con nombres descriptivos y orientados al dominio o propósito de la operación.

```java
public class CalculadoraMatematica {
    public int calcularDiferenciaDeCuadrados(int numeroUno, int numeroDos) {
        int suma = numeroUno + numeroDos;
        int resta = numeroUno - numeroDos;
        return suma * resta;
    }
}
```
</div>