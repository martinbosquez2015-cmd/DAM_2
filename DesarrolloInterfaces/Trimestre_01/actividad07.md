
# <center>IES FRANCISCO DE GOYA</center>
__NOMBRE:__ Martin Taboada  
__CURSO:__ DAM 2  
## <center> __Desarrollo de Interfaces:__</center>   <center> __Visión y Realidad Aumentada__</center>

### __1. Dato y significado__
<div align="justify">

&emsp;**Imagen / objeto reconocido:** una imagen es la información visual que recibe la cámara, mientras que un objeto reconocido es el resultado de interpretar esa información. Por ejemplo, cuando hacemos una fotografía de una bicicleta, el ordenador recibe una matriz de píxeles, pero, después de analizarla, puede determinar que dentro de la imagen hay una bicicleta.

&emsp;**Píxel / cara:** un píxel es un pequeño punto que contiene información sobre el color y la intensidad de una imagen, mientras que una cara es un elemento que el sistema puede reconocer a partir de la combinación de muchos píxeles. Por ejemplo, cuando una cámara enfoca a una persona, no identifica directamente sus ojos, nariz y boca, sino que analiza los píxeles para encontrar las características que corresponden a una cara.

&emsp;**Fotograma / movimiento:** un fotograma es una imagen individual dentro de un vídeo, mientras que el movimiento se obtiene al comparar diferentes fotogramas a lo largo del tiempo. Por ejemplo, en un vídeo de una persona caminando, cada fotograma muestra una posición concreta de su cuerpo, pero al analizar varios seguidos se puede reconocer que está caminando y determinar la dirección en la que se desplaza.
</div>

### __2. Distingue las tareas de visión artificial__
<div align="justify">

* **a) Contar las personas que entran en un aula.**

Se necesita detección y localización para encontrar a las personas dentro de la imagen. Además, sería necesario realizar un seguimiento para saber cuándo una persona cruza la entrada y evitar contarla varias veces. De esta manera, el sistema podría mantener un recuento de las personas que acceden al aula.

* **b) Reconocer al alumno que accede.**

Se necesita identificación, porque el sistema debe determinar quién es la persona concreta que está entrando. Para ello, tendría que comparar la información obtenida de la cámara con los datos de los alumnos registrados.

* **c) Seguir una mano para mover un cursor.**

Se necesita detección, localización y seguimiento. Primero, el sistema debe encontrar la mano y determinar su posición; después, debe actualizarla continuamente para que el cursor se desplace según los movimientos realizados por el usuario.

* **d) Saber si una foto contiene un coche.**

Se necesita clasificación, ya que el objetivo principal es determinar si la imagen contiene un coche o no. No sería necesario conocer su posición exacta, a menos que la aplicación también necesitara señalar dónde se encuentra dentro de la imagen.
</div>

### __3. Define tres gestos para controlar una aplicación__

| Gesto | Puntos necesarios | Condición aproximada | Acción |
|---|---|---|---|
| Levantar la mano derecha | Muñeca y hombro derechos | La muñeca derecha se encuentra por encima del hombro derecho. | Abrir el menú principal. |
| Juntar las dos manos | Muñecas y puntos de los dedos de ambas manos | La distancia entre las dos manos es inferior a un límite establecido. | Confirmar una selección. |
| Desplazar la mano hacia la derecha | Muñeca y articulaciones de los dedos | La posición de la mano se desplaza de izquierda a derecha durante varios fotogramas. | Pasar al siguiente elemento. |
<div align="justify">

&emsp;Estos gestos podrían utilizarse para controlar una aplicación sin necesidad de tocar la pantalla. Cada uno necesita detectar diferentes puntos del cuerpo y comprobar una condición para que el sistema pueda reconocerlo.

&emsp;En el caso de levantar la mano, basta con comparar la posición de la muñeca con la del hombro. Sin embargo, para detectar el desplazamiento hacia la derecha es necesario analizar varios fotogramas, porque una única imagen no permite determinar si la mano se está moviendo o permanece quieta.
</div>

### __4. ¿Postura o movimiento?__
<div align="justify">

**a) Levantar un brazo: postura y movimiento.**

&emsp;El sistema puede reconocer la postura cuando el brazo ya está levantado, observando la posición de la muñeca respecto al hombro. Sin embargo, si se quiere reconocer la acción de levantarlo, debe comparar varios fotogramas para comprobar que la muñeca pasa de una posición inferior a otra superior.

**b) Saludar con la mano: movimiento.**

&emsp;Para reconocer este gesto, el sistema debe analizar la posición de la mano durante un intervalo de tiempo. Tendría que detectar varios desplazamientos repetidos de izquierda a derecha y de derecha a izquierda, ya que una sola posición de la mano no permite distinguir un saludo de otros gestos.

**c) Permanecer agachado: postura.**

&emsp;Se puede reconocer mediante la posición de los puntos clave del cuerpo, como la cadera, las rodillas y los hombros. Para comprobar que la persona permanece agachada durante cierto tiempo, también sería necesario observar varios fotogramas y verificar que la postura se mantiene.

**d) Dar dos palmadas: movimiento.**

&emsp;El sistema debe detectar las dos manos y analizar cómo se aproximan hasta entrar en contacto, para después separarse. Además, debe comprobar que esta secuencia ocurre dos veces, por lo que necesita información de varios fotogramas y no únicamente una imagen.

**e) Inclinar la cabeza a la derecha: postura y movimiento.**

&emsp;La inclinación puede reconocerse observando la posición de puntos clave de la cara, como los ojos, la nariz y las orejas. Si se quiere detectar que la persona está inclinando la cabeza, el sistema debe comparar su posición inicial con la posterior. En cambio, si solo interesa saber si mantiene la cabeza inclinada, puede utilizar la postura actual.

</div>

### __5. Escribe reglas simples para reconocer gestos__
<div align="justify">

* **a) Manos juntas**

```text
SI la distancia entre la mano izquierda y la mano derecha
es inferior al límite establecido
ENTONCES manos_juntas = verdadero
```

&emsp;La regla permite comprobar si las dos manos están suficientemente cerca. Si la distancia supera el límite, el sistema considerará que las manos están separadas.

* **b) Brazo izquierdo levantado**

```text
SI muñeca_izquierda.y < hombro_izquierdo.y
ENTONCES brazo_izquierdo_levantado = verdadero
```

&emsp;Esta regla utiliza la posición vertical de la muñeca y del hombro. Como el origen de coordenadas suele estar situado en la parte superior de la imagen, un valor de `y` menor indica una posición más alta.

* **c) Persona agachada**

```text
SI la distancia vertical entre los hombros y la cadera
es inferior al límite establecido
Y las rodillas están flexionadas
ENTONCES persona_agachada = verdadero
```

&emsp;En este caso, el sistema utiliza varios puntos clave para estimar la postura de la persona. Es necesario tener en cuenta que los límites deben ajustarse para que la regla no confunda una persona agachada con alguien que simplemente está sentado o inclinado.
</div>

### __6. Plan de pruebas para reconocer una mano levantada__
<div align="justify">

&emsp;Para comprobar el funcionamiento del sistema, realizaría seis pruebas modificando las condiciones en las que se encuentra el usuario. De esta manera, se puede observar si el reconocimiento continúa funcionando cuando cambia el entorno o cuando la cámara tiene dificultades para detectar la mano.

| Prueba | Qué cambias | Resultado esperado / qué observar |
|---|---|---|
| 1. Iluminación | Realizar la prueba con buena iluminación y después con poca luz. | Con buena iluminación, la mano debería detectarse correctamente. Con poca luz, se comprobaría si el sistema sigue reconociéndola o pierde precisión. |
| 2. Distancia | Levantar la mano cerca de la cámara y después alejarse progresivamente. | La mano debería detectarse a una distancia adecuada. Se observaría a partir de qué distancia empieza a fallar el reconocimiento. |
| 3. Dos personas | Colocar a dos personas delante de la cámara y hacer que solo una levante la mano. | El sistema debería reconocer el gesto de la persona que levanta la mano sin confundirlo con el de la otra. |
| 4. Parte del cuerpo oculta | Tapar parcialmente la mano o el brazo con un objeto. | Se comprobaría si el sistema sigue detectando los puntos clave necesarios o si deja de reconocer el gesto. |
| 5. Movimiento rápido | Levantar y bajar la mano rápidamente varias veces. | Se observaría si el sistema detecta el gesto correctamente o si pierde la posición de la mano por el desenfoque o la velocidad del movimiento. |
| 6. Fondo complejo | Realizar el gesto delante de un fondo con muchos objetos o con otras personas moviéndose. | La mano debería reconocerse sin confundirla con otros elementos de la imagen. Se comprobaría si el fondo afecta a la detección. |

&emsp;Considerando los resultados de estas pruebas, se podría determinar en qué situaciones funciona mejor el sistema y cuáles son sus limitaciones. Si la cámara no consigue detectar la mano con suficiente confianza, la aplicación debería informar al usuario y permitirle repetir el gesto. También sería conveniente ofrecer una alternativa de control, como un botón en pantalla, para que la aplicación pueda seguir utilizándose cuando la detección falle.
</div>

### __7. Propón una interfaz multimodal__

<div align="justify">

**Contexto: aula.**

&emsp;Propondría una aplicación para controlar las presentaciones de clase mediante la voz y los gestos de las manos. De esta manera, el profesor podría cambiar de diapositiva, volver a una anterior o activar determinadas opciones sin tener que acercarse al ordenador.

&emsp;La primera forma de entrada sería la voz. El profesor podría decir «siguiente» para avanzar una diapositiva o «anterior» para volver a la anterior. La segunda forma de entrada serían los gestos, por ejemplo, desplazar la mano hacia la derecha para avanzar y hacia la izquierda para retroceder. Para reconocerlos, la cámara tendría que detectar la mano y seguir su posición durante varios fotogramas.

&emsp;La aplicación debería permitir utilizar cualquiera de las dos modalidades por separado. Si el micrófono falla o existe demasiado ruido en el aula, el profesor podría continuar utilizando los gestos. En cambio, si la cámara no consigue detectar correctamente las manos debido a la iluminación o a la distancia, podría seguir controlando la presentación mediante la voz.

&emsp;Si las dos entradas generan órdenes diferentes al mismo tiempo, el sistema debería evitar ejecutar ambas sin control. Por ejemplo, podría priorizar la orden de voz cuando se detecte una orden clara y mostrar en pantalla la acción que se va a realizar. También debería existir la posibilidad de desactivar el reconocimiento por cámara cuando no sea necesario, para reducir la captación de imágenes del entorno y proteger la privacidad de los alumnos.
</div>

### __8. Diseña una aplicación de realidad aumentada__

<div align="justify">

* **Ámbito y objetivo**

&emsp;El ámbito elegido es el comercio, concretamente una aplicación de realidad aumentada para visualizar muebles dentro de una habitación antes de comprarlos.

&emsp;El objetivo sería que el usuario pudiera comprobar cómo quedaría un sofá, una mesa o una estantería en su casa sin tener que adquirir el producto previamente. Esto permitiría hacerse una idea más clara del tamaño y de la apariencia del mueble en comparación con observar únicamente una fotografía en una tienda online.

* **Entrada de cámara**

&emsp;La aplicación utilizaría la cámara del móvil para captar imágenes de la habitación. A partir de ellas, el sistema analizaría el entorno y el movimiento del dispositivo para estimar la posición de las superficies y la orientación de la cámara.

* **Qué debe detectar**

&emsp;La aplicación tendría que detectar superficies como el suelo y, si fuera necesario, las paredes. También debería reconocer características visuales del entorno que permitan calcular la posición del móvil y mantener una referencia estable mientras el usuario se desplaza.

&emsp;Además, tendría que estimar las dimensiones y la orientación de la superficie donde se quiere colocar el mueble, para evitar que aparezca en una posición poco realista.

* **Elemento digital añadido**

&emsp;El elemento digital sería un modelo tridimensional del mueble seleccionado. Por ejemplo, si el usuario elige un sofá en el catálogo, la aplicación mostraría una representación virtual con una apariencia y unas dimensiones aproximadas a las del producto real.

* **Cómo se posiciona**

&emsp;El sistema analizaría las imágenes de la cámara para detectar el suelo y estimar la posición del dispositivo. Después, colocaría el sofá virtual sobre la superficie detectada y lo mantendría anclado a esa posición.

&emsp;Cuando el usuario moviera el móvil, la aplicación actualizaría continuamente la posición y la perspectiva del modelo para que pareciera formar parte de la habitación. Si el usuario se desplazara hacia un lado, debería poder observar el sofá desde otro ángulo sin que este se moviera de manera incoherente respecto al entorno.

* **Interacción del usuario**

&emsp;El usuario podría seleccionar un mueble del catálogo, colocarlo sobre el suelo y cambiar su posición mediante controles en pantalla. También podría girarlo para comprobar cómo queda desde diferentes ángulos y sustituirlo por otro modelo para comparar las opciones disponibles.

&emsp;La aplicación debería mostrar una indicación cuando no consiga detectar correctamente el suelo, para que el usuario pueda mover el móvil y volver a intentarlo. Así se evitaría colocar el objeto digital en una posición incorrecta.

* **Posible problema técnico o de privacidad**

&emsp;Uno de los problemas técnicos sería la dificultad para detectar las superficies cuando hay poca iluminación, el suelo tiene pocos detalles visuales o existen muchos objetos que dificultan el análisis de la habitación. Esto podría provocar que el mueble virtual se desplace, cambie de tamaño o deje de mantenerse anclado correctamente.

&emsp;Otro problema sería la diferencia entre las dimensiones del modelo digital y las del producto real, ya que una representación aproximada podría dar una idea equivocada del espacio que ocuparía el mueble. Por ello, sería necesario utilizar modelos con medidas fiables y permitir que el usuario compruebe las dimensiones del producto.

&emsp;Con respecto a la privacidad, la cámara podría captar imágenes del interior de la vivienda y de objetos personales. Para evitar riesgos innecesarios, la aplicación debería informar sobre el uso de las imágenes, solicitar los permisos correspondientes y evitar almacenar o enviar las grabaciones si no resulta necesario para su funcionamiento.

</div>
