# <center>IES FRANCISCO DE GOYA</center>
__NOMBRE:__ Martin Taboada  
__CURSO:__ DAM 2  
## <center> __Ampliación de Entornos de Desarrollo:__</center>   <center> __Optimización__</center>

### __Ejercicio 11: Switch vs Pattern Matching__
<div align="justify">

&emsp;**Análisis y optimización:** El código original utiliza valores enteros mágicos dentro de una estructura `switch` para determinar el rol del usuario. Esto obliga a mantener la lógica de traducción dispersa y poco tipada. Al encapsular estos valores en un `enum` con un método de búsqueda, centralizamos la representación de los roles, ganamos seguridad de tipos y hacemos que el código sea mucho más expresivo y fácil de ampliar.

&emsp;**Código original:**
```java
public String getTipo(Usuario usuario) {
    return switch (usuario.getTipo()) {
        case 1 -> "Admin";
        case 2 -> "Gestor";
        case 3 -> "Usuario";
        case 4 -> "Invitado";
        default -> "Desconocido";
    };
}
```

&emsp;**Código optimizado:**
```java
public enum TipoUsuario {
    ADMIN(1, "Admin"),
    GESTOR(2, "Gestor"),
    USUARIO(3, "Usuario"),
    INVITADO(4, "Invitado"),
    DESCONOCIDO(-1, "Desconocido");

    private final int codigo;
    private final String descripcion;

    TipoUsuario(int codigo, String descripcion) {
        this.codigo = codigo;
        this.descripcion = descripcion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public static String obtenerDescripcionPorCodigo(int codigo) {
        for (TipoUsuario tipo : values()) {
            if (tipo.codigo == codigo) {
                return tipo.getDescripcion();
            }
        }
        return DESCONOCIDO.getDescripcion();
    }
}

public String getTipo(Usuario usuario) {
    return TipoUsuario.obtenerDescripcionPorCodigo(usuario.getTipo());
}
```
</div>

### __Ejercicio 12: Bucles vs Streams__
<div align="justify">

&emsp;**Análisis y optimización:** En la versión original se recurre a un bucle `for` tradicional con índice y a la creación manual de una lista auxiliar para filtrar los elementos. Mediante el uso de la API `Stream` de Java, podemos transformar este enfoque imperativo en uno declarativo, logrando un código más limpio, conciso y fácil de leer sin necesidad de gestionar índices ni listas intermedias.

&emsp;**Código original:**
```java
public List<Integer> getPares(List<Integer> numeros) {
    List<Integer> resultado = new ArrayList<>();
    for (int i = 0; i < numeros.size(); i++) {
        if (numeros.get(i) % 2 == 0) {
            resultado.add(numeros.get(i));
        }
    }
    return resultado;
}
```

&emsp;**Código optimizado:**
```java
public List<Integer> getPares(List<Integer> numeros) {
    return numeros.stream()
            .filter(this::esPar)
            .toList();
}

private boolean esPar(Integer numero) {
    return numero % 2 == 0;
}
```
</div>

### __Ejercicio 13: Map vs Switch__
<div align="justify">

&emsp;**Análisis y optimización:** El bloque `switch` original funciona como una tabla de búsqueda estática que relaciona cada país con su tarifa de envío. Reemplazar esta estructura por un `Map` inmutable (`Map.of`) junto con el método `getOrDefault` desacopla los datos de la lógica de control, mejora la escalabilidad y permite consultar el precio en tiempo constante de una forma mucho más limpia.

&emsp;**Código original:**
```java
public int getPrecioEnvio(String pais) {
    return switch (pais.toLowerCase()) {
        case "españa" -> 5;
        case "francia" -> 8;
        case "alemania" -> 10;
        case "italia" -> 7;
        case "portugal" -> 4;
        case "reino unido" -> 12;
        case "belgica" -> 9;
        case "paises bajos" -> 11;
        default -> 15;
    };
}
```

&emsp;**Código optimizado:**
```java
private static final int TARIFA_POR_DEFECTO = 15;
private static final Map<String, Integer> TARIFAS_ENVIO = Map.of(
    "españa", 5,
    "francia", 8,
    "alemania", 10,
    "italia", 7,
    "portugal", 4,
    "reino unido", 12,
    "belgica", 9,
    "paises bajos", 11
);

public int getPrecioEnvio(String pais) {
    if (pais == null) {
        return TARIFA_POR_DEFECTO;
    }
    return TARIFAS_ENVIO.getOrDefault(pais.toLowerCase(), TARIFA_POR_DEFECTO);
}
```
</div>

### __Ejercicio 14: Null Checking__
<div align="justify">

&emsp;**Análisis y optimización:** Las comprobaciones secuenciales con `if` para evitar excepciones `NullPointerException` añaden ruido visual y complejidad ciclomática innecesaria. Al emplear `Optional`, podemos encadenar las transformaciones y definir los valores alternativos de manera fluida y funcional, dejando clara la intención de cada caso nulo.

&emsp;**Código original:**
```java
public String getNombre(Usuario usuario) {
    if (usuario == null) {
        return "Anónimo";
    }

    if (usuario.getNombre() == null) {
        return "Sin nombre";
    }

    return usuario.getNombre();
}
```

&emsp;**Código optimizado:**
```java
public String getNombre(Usuario usuario) {
    return Optional.ofNullable(usuario)
            .map(Usuario::getNombre)
            .orElse(usuario == null ? "Anónimo" : "Sin nombre");
}
```
</div>

### __Ejercicio 15: Construcción de cadenas__
<div align="justify">

&emsp;**Análisis y optimización:** La concatenación directa mediante el operador `+` con múltiples variables intercaladas dificulta la lectura de la plantilla final del texto y genera objetos `String` intermedios en memoria. Utilizar `String.format` (o el método `.formatted()` en versiones modernas de Java) permite visualizar la estructura completa de la frase de un vistazo y separar el formato de los datos insertados.

&emsp;**Código original:**
```java
String mensaje = "El usuario " + usuario.getNombre() + " con ID " + usuario.getId()
        + " ha realizado " + pedido.getCantidad() + " compras por un total de " + pedido.getTotal() + " euros.";
```

&emsp;**Código optimizado:**
```java
String mensaje = String.format(
    "El usuario %s con ID %s ha realizado %d compras por un total de %s euros.",
    usuario.getNombre(),
    usuario.getId(),
    pedido.getCantidad(),
    pedido.getTotal()
);
```
</div>