# CalculadoraFinanceira

## 1. Descripción del proyecto

CalculadoraFinanceira es una biblioteca desarrollada en Java que permite realizar operaciones matemáticas básicas y algunos cálculos financieros. Su objetivo es facilitar operaciones como sumas, restas, multiplicaciones, divisiones y cálculos de intereses, sin necesidad de implementar estas funciones desde cero en cada proyecto.

## 2. Instalación

Para utilizar esta biblioteca, primero se debe añadir como dependencia a nuestro proyecto.

**Maven:**

Se añade el siguiente código dentro de la sección `<dependencies>` del archivo `pom.xml`:

    <dependency>
        <groupId>com.ejemplo</groupId>
        <artifactId>calculadora-financeira</artifactId>
        <version>1.1.0</version>
    </dependency>

**Gradle:**

En el caso de utilizacion de Gradle, se añadirá la siguiente línea dentro de `dependencies` en el archivo `build.gradle`:

    implementation 'com.ejemplo:calculadora-financeira:1.1.0'

## 3. Ejemplo de uso básico

Una vez añadida la biblioteca al proyecto, se puede importar la clase `CalculadoraFinanceira` y utilizar sus métodos.

    import com.ejemplo.calculadorafinanceira.CalculadoraFinanceira;

    public class Main {
        public static void main(String[] args) {

            CalculadoraFinanceira calculadora = new CalculadoraFinanceira();

            double suma = calculadora.sumar(10, 5);
            double division = calculadora.dividir(20, 4);
            double interes = calculadora.calcularInteresSimple(1000, 5, 2);

            System.out.println("Suma: " + suma);
            System.out.println("División: " + division);
            System.out.println("Interés simple: " + interes);
        }
    }

En este ejemplo se crea un objeto de la clase y se utilizan tres de sus métodos para realizar diferentes operaciones.

## 4. Métodos disponibles

La biblioteca dispone de los siguientes métodos:
<div align="center">

| Método | Descripción | Parámetros |
|---|---|---|
| `sumar(a, b)` | Suma dos números. | Dos números. |
| `restar(a, b)` | Resta dos números. | Dos números. |
| `multiplicar(a, b)` | Multiplica dos números. | Dos números. |
| `dividir(a, b)` | Divide dos números. | Dos números. |
| `calcularInteresSimple(c, t, a)` | Calcula el interés simple. | Capital, tasa anual y años. |
| `calcularInteresCompuesto(c, t, a)` | Calcula el interés compuesto. | Capital, tasa anual y años. |
</div>

En los métodos de intereses, la tasa se indica como un porcentaje. Por ejemplo, para un 5 % se debe introducir el valor `5`.

En el caso de la división, el divisor no puede ser cero.

## 5. Licencia

Este proyecto utiliza la licencia MIT, que permite utilizar, modificar y distribuir el código, siempre que se mantenga la información de la licencia original.

Para más información, se puede consultar el archivo `LICENSE`.