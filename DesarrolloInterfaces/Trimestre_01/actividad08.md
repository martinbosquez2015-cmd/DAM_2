# <center>IES FRANCISCO DE GOYA</center>
__NOMBRE:__ Martin Taboada  
__CURSO:__ DAM 2  
## <center> __Desarrollo de Interfaces:__</center>   <center> __Interfaces naturales: Voz y Machine Learning__</center>



### __1. Del gesto humano al dato digital__

| Situación | Sensor | Dato recibido por el sistema |
|---|---|---|
| Una persona dice «abre la persiana» | Micrófono | Señal de audio digital con las variaciones de presión del aire producidas por la voz. |
| El usuario levanta la mano delante de la cámara | Cámara | Imagen o secuencia de vídeo en la que aparecen los píxeles correspondientes a la mano y al resto del entorno. |
| El usuario gira el teléfono para jugar | Giroscopio y acelerómetro | Datos sobre la rotación, la orientación y la aceleración del teléfono. |
| El usuario mira durante dos segundos a un botón | Sistema de seguimiento ocular | Datos sobre la dirección aproximada de la mirada y el tiempo durante el que se mantiene sobre el botón. |
<div align="justify">

&emsp;Cada sensor obtiene un tipo de información diferente. Por ejemplo, el micrófono no recibe directamente la orden de abrir la persiana, sino una señal de audio que después debe procesarse para reconocer las palabras y determinar qué quiere hacer el usuario.
</div>

### __2. Ordena el proceso de reconocimiento de voz__
<div align="justify">

**Orden correcto de los pasos:**

1. El micrófono capta el sonido.
2. Se procesa la señal de audio.
3. El sistema obtiene el texto «enciende la luz».
4. Se interpreta la intención de encender y el dispositivo luz.
5. La aplicación enciende la luz.

&emsp;Si el reconocimiento transformara mal una palabra, la aplicación podría interpretar una orden diferente de la que realmente ha dado el usuario. Por ejemplo, si reconoce «apaga la luz» cuando la persona ha dicho «enciende la luz», podría ejecutar una acción contraria a la deseada. Por eso, el sistema debe intentar reconocer correctamente el audio y, cuando no tenga suficiente confianza en el resultado, pedir al usuario que repita la orden.
</div>

### __3. ¿Reglas o aprendizaje automático?__

| Caso | Reglas / ML | Justificación |
|---|---|---|
| Cambiar el fondo cuando el usuario pulsa un botón | Reglas | Es una acción sencilla que puede programarse mediante una condición. Si el usuario pulsa el botón, la aplicación cambia el fondo. No es necesario entrenar un modelo. |
| Reconocer si una persona está sonriendo en una fotografía | Aprendizaje automático | Es necesario analizar características de la cara y reconocer un patrón visual. Existen muchas diferencias entre personas, por lo que resulta más razonable utilizar un modelo entrenado con diferentes fotografías. |
| Subir un grado la temperatura al pulsar «+» | Reglas | La acción está claramente definida. Cada vez que se pulsa el botón, el programa aumenta un grado la temperatura. |
| Distinguir entre cientos de palabras pronunciadas por personas diferentes | Aprendizaje automático | Las personas tienen voces, acentos y pronunciaciones diferentes. Programar una regla para cada posible variación sería muy complicado, por lo que un modelo entrenado con numerosos ejemplos resulta más adecuado. |

<div align="justify">

&emsp;Considerando los cuatro casos, la programación tradicional resulta suficiente cuando las condiciones están claras y la acción se puede definir mediante reglas sencillas. Sin embargo, cuando hay muchas variaciones que no se pueden describir fácilmente, como ocurre con las voces o las expresiones faciales, el aprendizaje automático permite reconocer patrones a partir de ejemplos.
</div>

### __4. Un entrenamiento problemático__

<div align="justify">

&emsp;Si quisiera crear un sistema que reconociera si una persona lleva mascarilla y solo dispusiera de 30 fotografías de mis compañeros tomadas en el aula, tendría varios problemas con el conjunto de datos.

&emsp;**1. Poca cantidad de fotografías.**

&emsp;&emsp;Treinta imágenes pueden ser insuficientes para representar todas las situaciones que se pueden encontrar en la realidad. El modelo podría aprender a reconocer los ejemplos disponibles, pero fallar cuando analizara fotografías nuevas.

&emsp;&emsp;Para mejorarlo, recopilaría más imágenes de personas diferentes, tanto con mascarilla como sin ella, intentando representar distintas situaciones.

&emsp;**2. Poca variedad de personas.**

&emsp;&emsp;Si todas las fotografías pertenecen a los mismos compañeros, el modelo podría aprender características concretas de esas personas en lugar de aprender a reconocer correctamente una mascarilla.

&emsp;&emsp;Para solucionarlo, utilizaría fotografías de personas con diferentes características faciales, edades y apariencias, de manera que el modelo pudiera reconocer el elemento que realmente interesa.

&emsp;**3. Misma iluminación y distancia.**

&emsp;&emsp;Al haberse tomado todas las fotografías con la misma iluminación y desde la misma distancia, el sistema podría tener dificultades cuando las condiciones fueran diferentes. Por ejemplo, podría fallar si una persona aparece más lejos de la cámara o si la imagen tiene poca luz.

&emsp;&emsp;Para mejorar este aspecto, realizaría fotografías con diferentes niveles de iluminación, distancias y ángulos.

&emsp;**4. Falta de variedad en las situaciones.**

&emsp;&emsp;El conjunto de datos podría no incluir mascarillas de diferentes tipos, personas con la mascarilla mal colocada o imágenes en las que parte de la cara estuviera oculta por otro objeto.

&emsp;&emsp;Por ello, añadiría ejemplos variados que permitieran al sistema reconocer las mascarillas en distintas posiciones y situaciones.

&emsp;**5. Posible falta de datos de prueba independientes.**

&emsp;&emsp;Si se utilizan todas las fotografías para entrenar el modelo, no se podría comprobar correctamente si funciona con imágenes que no ha utilizado para aprender.

&emsp;&emsp;&emspPara evitarlo, dividiría los datos en conjuntos de entrenamiento, validación y prueba. El primero serviría para que el modelo aprendiera los patrones, el segundo para comprobar su comportamiento durante el desarrollo y el tercero para realizar una evaluación final con imágenes que no hubiera utilizado durante el entrenamiento.

&emsp;&emsp;De esta manera, el sistema tendría más posibilidades de funcionar correctamente en situaciones reales. La calidad y la variedad de los datos son fundamentales, ya que no basta con tener fotografías si todas representan prácticamente las mismas condiciones.

</div>

### __5. Analiza los errores__

<div align="justify">

&emsp;**a) Desbloqueo facial de un móvil.**

&emsp;&emsp;El falso positivo sería el error más grave, porque significaría que el sistema permite desbloquear el móvil a una persona que no es su propietario. Esto podría facilitar el acceso a información personal sin autorización.

&emsp;&emsp;Un falso negativo también sería molesto, ya que impediría al propietario desbloquear su propio dispositivo, pero podría intentarlo de nuevo mediante otro reconocimiento o un método alternativo de acceso.

&emsp;**b) Sistema de dictado.**

&emsp;&emsp;En este caso, ambos errores pueden resultar problemáticos, aunque considero que un falso positivo puede ser especialmente molesto si el sistema introduce palabras que el usuario no ha dicho. Esto podría cambiar el significado de una frase y obligar a corregir el texto.

&emsp;&emsp;El falso negativo también afecta al funcionamiento, porque algunas palabras pronunciadas no aparecerían en el documento. La gravedad dependería del contexto y de la importancia de la información que se está dictando.

&emsp;**c) Detector de humo controlado por sensores.**

&emsp;&emsp;El falso negativo sería el más grave, porque significaría que el sistema no detecta humo cuando realmente existe. Esto podría impedir que se activara la alarma a tiempo y poner en peligro a las personas.

&emsp;&emsp;El falso positivo también podría causar problemas si la alarma se activa sin que exista humo, pero no detectar una situación real de peligro tendría consecuencias potencialmente mucho más graves.

&emsp;**d) Videojuego controlado por voz.**

&emsp;&emsp;El falso positivo sería especialmente molesto si el juego ejecuta una orden que el usuario no ha dado. Por ejemplo, el personaje podría saltar o atacar cuando el jugador no lo pretende, lo que afectaría a la partida.

&emsp;&emsp;El falso negativo también perjudicaría la experiencia, porque el juego no respondería a una orden correcta. Sin embargo, normalmente sería posible repetirla sin que las consecuencias fueran demasiado graves.
</div>

### __6. Intención y datos relevantes__

| Frase | Intención | Datos relevantes |
|---|---|---|
| «Enciende la luz del dormitorio» | Encender una luz. | Dispositivo: luz. Ubicación: dormitorio. |
| «Pon la temperatura a 21 grados» | Cambiar la temperatura. | Temperatura objetivo: 21 grados. |
| «Cierra la persiana» | Cerrar una persiana. | Dispositivo: persiana. Acción: cerrar. |
| «Activa el modo noche» | Activar un modo de funcionamiento. | Modo: noche. Acción: activar. |

<div align="justify">

&emsp;La intención indica qué quiere conseguir el usuario, mientras que los datos relevantes permiten concretar la acción. Por ejemplo, en la frase «Pon la temperatura a 21 grados», el sistema debe reconocer que se quiere cambiar la temperatura y obtener el valor de 21 grados para ejecutar correctamente la orden.

&emsp;Si el sistema solo reconociera la intención, sabría que debe modificar la temperatura, pero no conocería el valor que tiene que establecer. Por eso, es necesario identificar tanto la intención como los datos que acompañan a la frase.
</div>

### __7. Diseña un sistema robusto para una habitación inteligente__

<div align="justify">

&emsp;Para que una habitación inteligente pueda reconocer las órdenes de voz aunque el usuario utilice expresiones diferentes, propondría aceptar varias frases para una misma intención.

| Comando | Expresión alternativa 1 | Expresión alternativa 2 |
|---|---|---|
| Encender luz | «Enciende las luces» | «Quiero luz» |
| Apagar luz | «Apaga las luces» | «Desconecta la luz» |
| Subir temperatura | «Aumenta la temperatura» | «Pon la habitación más caliente» |
| Cerrar persiana | «Baja la persiana» | «Quiero que se cierre la persiana» |

&emsp;De esta manera, el sistema podría reconocer distintas formas de expresar una misma orden sin depender de una frase exacta. Para conseguirlo, tendría que analizar el texto reconocido y determinar la intención del usuario, teniendo en cuenta también los datos necesarios para ejecutar la acción.

&emsp;Si la aplicación no entendiera una orden, debería informar al usuario de que no ha podido reconocerla y pedirle que la repita. Por ejemplo, podría mostrar un mensaje como «No he entendido la orden, inténtalo de nuevo». También sería conveniente indicar algunos comandos disponibles para ayudar al usuario a expresarse de una forma que el sistema pueda reconocer.

&emsp;Además, la aplicación no debería ejecutar una acción cuando no tenga suficiente confianza en la interpretación. Si la orden es ambigua, podría pedir una aclaración antes de actuar. Por ejemplo, si no consigue determinar qué dispositivo debe apagar, debería preguntar al usuario en lugar de modificar el estado de un dispositivo incorrecto.

&emsp;Por último, tendría en cuenta problemas como el ruido ambiental, la distancia al micrófono y las diferencias de pronunciación. La aplicación podría pedir que el usuario se acercara al micrófono cuando la señal fuera demasiado débil y permitir repetir los comandos cuando el reconocimiento fallara. Así, el sistema sería más flexible y evitaría ejecutar acciones equivocadas por una mala interpretación de la voz.

</div>