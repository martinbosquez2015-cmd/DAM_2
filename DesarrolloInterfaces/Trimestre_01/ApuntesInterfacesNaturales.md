# APUNTES DE ESTUDIO: INTERFACES NATURALES, VISIÓN, VOZ Y MACHINE LEARNING

---

## BLOQUE 1: INTRODUCCIÓN A LAS INTERFACES NATURALES

### Conceptos Fundamentales
* **Interfaz Tradicional vs. Interfaz Natural:** 
  * La interfaz tradicional utiliza elementos gráficos convencionales (menús, botones, cursores, ventanas) y dispositivos de control directo como el ratón y el teclado[cite: 70].
  * La interfaz natural (NUI) permite interactuar imitando las formas de comunicación habituales de las personas (hablar, tocar, mirar, hacer gestos o mover el cuerpo)[cite: 70].
* **Tipos de Interacción Natural:**
  * **Voz:** Palabras o comandos hablados (ej. pedir al asistente que ponga una alarma)[cite: 70].
  * **Gestual:** Movimientos y gestos de manos o dedos (ej. hacer un gesto para ejecutar una acción)[cite: 70].
  * **Táctil:** Contacto directo con pantallas (ej. pellizcar para ampliar una imagen)[cite: 70].
  * **Movimiento corporal:** Posición o desplazamiento de extremidades y cuerpo completo (ej. jugar moviendo los brazos)[cite: 70].
  * **Mirada:** Dirección y enfoque de los ojos (ej. seleccionar elementos mirando)[cite: 70].
  * **Reconocimiento visual:** Análisis de rostros u objetos mediante cámaras (ej. desbloqueo facial)[cite: 70].

### El Proceso en una Interfaz Natural
* El sistema siempre sigue un flujo básico de tres fases:
  1. **Entrada:** Captación de la señal física mediante un sensor (micrófono, cámara, acelerómetro)[cite: 71, 79].
  2. **Interpretación:** Limpieza, preprocesamiento y reconocimiento de patrones para dotar de significado al dato bruto[cite: 71, 80].
  3. **Respuesta:** Ejecución de la acción lógica asociada por parte del sistema[cite: 71, 80].

### Ventajas y Limitaciones
* **Ventajas:** Menor curva de aprendizaje, rapidez de interacción, utilidad con las manos ocupadas y nuevas vías de accesibilidad para personas con diversidad funcional[cite: 71].
* **Limitaciones:** Vulnerabilidad ante el ruido ambiental, fallos en la interpretación de gestos complejos, dependencia de sensores avanzados, problemas de privacidad y ambigüedad en las acciones[cite: 71].

---

## BLOQUE 2: VISIÓN ARTIFICIAL Y REALIDAD AUMENTADA

### De la Cámara a los Datos
* Para un ordenador, una imagen inicial es únicamente una **matriz de píxeles** (valores numéricos de color e intensidad). Reconocer elementos implica transformar esos valores en información con significado semántico[cite: 73].
* **Tareas Principales de la Visión Artificial:**
  * **Clasificación:** Asignar una categoría global a toda la imagen (ej. "contiene un perro")[cite: 73].
  * **Detección:** Encontrar y señalar la presencia de uno o varios objetos[cite: 73].
  * **Localización:** Delimitar la posición exacta de un elemento mediante coordenadas o rectángulos[cite: 73].
  * **Identificación:** Reconocer la identidad específica de un elemento (ej. reconocer un rostro registrado)[cite: 73].
  * **Seguimiento (*Tracking*):** Mantener la referencia y el rastro de un elemento en movimiento a lo largo de una secuencia de fotogramas[cite: 73].

### Puntos Clave (*Landmarks*), Posturas y Movimientos
* **Puntos clave (*Landmarks*):** Puntos anatómicos de referencia en la cara, manos o cuerpo (articulaciones, codos, muñecas) que permiten estimar posturas sin procesar el cuerpo completo[cite: 74].
* **Postura vs. Movimiento:**
  * *Postura:* Estado estático evaluable en un único instante o fotograma[cite: 75].
  * *Movimiento:* Secuencia temporal que requiere comparar posiciones en varios momentos para calcular trayectorias, velocidades o direcciones[cite: 75].
* **Reconocimiento por Reglas vs. Modelos:**
  * Las *reglas* lógicas (pseudocódigo con coordenadas `y`) funcionan bien con condiciones simples y fijas[cite: 75].
  * Los *modelos entrenados* se emplean ante variaciones complejas y múltiples muestras[cite: 75].

### Realidades XR (Aumentada, Virtual y Mixta)
* **Realidad Aumentada (RA):** Superpone elementos digitales sobre la visión del mundo real que capta el dispositivo[cite: 77].
* **Realidad Virtual (RV):** Sustituye por completo el entorno físico real por una simulación digital generada por ordenador[cite: 77].
* **Realidad Mixta (RM):** Integra elementos digitales interactivos que se relacionan de forma compleja con el entorno físico real (pueden quedar ocultos tras objetos reales)[cite: 77].
* **Funcionamiento de la RA:** Requiere captura de cámara, análisis del entorno (mediante marcadores o detección de superficies y planos), anclaje de coordenadas y renderizado en tiempo real[cite: 77].

---

## BLOQUE 3: VOZ Y MACHINE LEARNING

### Sensores y Conversión de Señales
* Los sensores transforman magnitudes físicas del entorno en datos digitales legibles por el procesador[cite: 79]:
  * *Micrófono:* Señal de audio[cite: 79].
  * *Cámara:* Imágenes o vídeo[cite: 79].
  * *Acelerómetro / Giroscopio:* Aceleración, rotación e inclinación[cite: 79].

### Programación Tradicional frente a Machine Learning (ML)
* **Programación Tradicional:** El programador diseña explícitamente las reglas de decisión. Es útil en entornos cerrados y condiciones estáticas[cite: 80, 81].
* **Machine Learning:** El sistema infiere patrones a partir de conjuntos de datos de entrenamiento. Es imprescindible ante variaciones masivas (distintas voces, acentos, condiciones lumínicas)[cite: 80, 81].
* **Fases del Entrenamiento:** Recopilación de muestras y etiquetado $\rightarrow$ Entrenamiento y ajuste de parámetros $\rightarrow$ Validación y pruebas $\rightarrow$ **Inferencia** (predicción en entornos reales con datos nuevos)[cite: 81, 82].

### Fiabilidad y Errores en Modelos
* **Umbral de confianza:** Porcentaje mínimo de probabilidad exigido por la aplicación para aceptar una predicción como válida[cite: 82].
* **Tipos de Errores:**
  * *Falso Positivo:* El sistema detecta o ejecuta una orden cuando el usuario no la ha emitido[cite: 82].
  * *Falso Negativo:* El usuario emite la orden claramente y el sistema es incapaz de detectarla[cite: 82].

### Procesamiento de la Voz
* El flujo del reconocimiento de voz comprende tres etapas diferenciadas[cite: 82, 83]:
  1. *Captura de audio:* Conversión acústica a señal digital[cite: 82].
  2. *Reconocimiento Automático del Habla (ASR):* Transcripción de la señal de voz a texto plano[cite: 82, 83].
  3. *Comprensión e Interpretación:* Extracción de la **intención** del usuario y de las **entidades** o datos relevantes (parámetros de la acción)[cite: 83].

---
---

# EXAMEN TIPO TEST (10 PREGUNTAS)

**1. ¿Qué diferencia principal existe entre una interfaz gráfica tradicional y una interfaz natural (NUI)?**  
a) Las interfaces naturales solo funcionan mediante pantallas táctiles de alta resolución.  
b) Las interfaces naturales imitan las formas de comunicación y acción cotidianas de las personas (voz, gestos, mirada), frente al uso exclusivo de ratón, teclado y ventanas de las tradicionales.  
c) Las interfaces tradicionales no requieren ningún tipo de procesamiento por parte del ordenador.  
d) No existe ninguna diferencia técnica en la experiencia de usuario.  

**2. ¿Cómo interpreta inicialmente un ordenador una fotografía capturada por una cámara digital?**  
a) Como un conjunto abstracto de objetos con significado humano inmediato.  
b) Como una matriz de píxeles formada por pequeños puntos con valores numéricos de color e intensidad.  
c) Como un archivo de texto estructurado en etiquetas XML.  
d) Como un vector acústico de presión atmosférica.  

**3. Si una aplicación de visión artificial analiza una secuencia de vídeo para determinar exactamente las coordenadas y delimitar con un rectángulo la posición de un coche en movimiento, ¿qué tarea principal está realizando?**  
a) Clasificación.  
b) Localización y detección.  
c) Identificación biométrica.  
d) Síntesis de voz.  

**4. ¿Cuál es la diferencia fundamental entre detectar una "postura" y un "movimiento" corporal?**  
a) La postura requiere analizar obligatoriamente una secuencia temporal larga, mientras que el movimiento se evalúa en un único fotograma estático.  
b) La postura se evalúa observando un único instante o fotograma, mientras que el movimiento exige analizar una secuencia temporal para calcular trayectorias y velocidades.  
c) No hay diferencia; ambos términos son sinónimos en visión artificial.  
d) La postura solo se puede calcular mediante sensores de infrarrojos y el movimiento mediante micrófonos.  

**5. ¿Qué tecnología de realidad extendida superpone información o elementos digitales directamente sobre la visión del mundo físico real que capta la cámara?**  
a) Realidad Virtual (RV).  
b) Realidad Aumentada (RA).  
c) Entorno virtual inmersivo cerrado.  
d) Realidad sintética offline.  

**6. ¿Qué componente físico o sensor del dispositivo se encarga principalmente de capturar la aceleración lineal y los cambios de inclinación para la navegación o juegos?**  
a) El micrófono ambiental.  
b) El acelerómetro.  
c) El sensor de seguimiento ocular.  
d) La cámara de infrarrojos lejanos.  

**7. ¿En qué situación está justificado utilizar el Aprendizaje Automático (*Machine Learning*) en lugar de la programación tradicional basada en reglas fijas?**  
a) Cuando las condiciones del entorno son totalmente rígidas y predecibles.  
b) Cuando el número de variaciones posibles (como acentos de voz, iluminaciones o posturas corporales) es enorme y difícil de describir mediante reglas estáticas.  
c) Cuando se requiere ejecutar operaciones aritméticas simples sin consumir memoria RAM.  
d) Únicamente cuando no se dispone de un lenguaje de programación estructurado.  

**8. ¿Cómo se denomina al error en un sistema inteligente donde el modelo interpreta o detecta una orden (por ejemplo, apagar la luz) a pesar de que el usuario no ha emitido ningún comando?**  
a) Falso negativo.  
b) Falso positivo.  
c) Error de latencia de red.  
d) Desbordamiento de pila por inferencia.  

**9. En el procesamiento del reconocimiento automático del habla (voz), ¿qué término define la acción concreta que el usuario desea realizar (por ejemplo, `cambiar_temperatura`)?**  
a) El marcador de superficie.  
b) La entidad o parámetro secundario.  
c) La intención (*intent*).  
d) La frecuencia de muestreo acústico.  

**10. ¿Qué ventaja principal aportan las interfaces multimodales en el diseño de aplicaciones interactivas?**  
a) Obligan al usuario a utilizar exclusivamente un único canal de entrada para evitar confusiones.  
b) Combinan varios canales de entrada (como voz, gestos o tacto), aportando mayor flexibilidad y permitiendo respaldar la interacción si uno de los canales falla.  
c) Reducen drásticamente la potencia de cálculo necesaria en el procesador central.  
d) Eliminan por completo la necesidad de utilizar sensores de captura visual.  

---
### Soluciones del Test:
1. **b** | 2. **b** | 3. **b** | 4. **b** | 5. **b** | 6. **b** | 7. **b** | 8. **b** | 9. **c** | 10. **b**

---
---

# EXAMEN DE DESARROLLO (PREGUNTAS Y SUPUESTOS PRÁCTICOS)

### Pregunta 1. Teoría y Procesos de Visión Artificial (3,5 puntos)
Explica detalladamente qué recibe un ordenador cuando una cámara capta una escena en movimiento y describe las cinco tareas fundamentales de la visión artificial (clasificación, detección, localización, identificación y seguimiento). Pon un ejemplo práctico de ámbito domótico o industrial para cada una de ellas.

> **Respuesta orientativa / Criterios de corrección:**
> * *Explicación inicial:* El estudiante debe indicar que el ordenador no percibe objetos con significado semántico directo, sino una matriz numérica de píxeles cambiantes en sucesivos fotogramas de vídeo[cite: 73].
> * *Tareas y Ejemplos:*
>   1. **Clasificación:** Asignar una categoría global a la imagen. *(Ejemplo: Una cámara de seguridad indica si en la imagen hay una persona o un animal).*
>   2. **Detección:** Señalar la presencia de uno o varios objetos de interés sin precisar contornos exactos. *(Ejemplo: Indicar que hay presencia de intrusos en una zona prohibida).*
>   3. **Localización:** Determinar la posición espacial mediante coordenadas o delimitaciones rectangulares. *(Ejemplo: Enmarcar un bulto sospechoso en una cinta transportadora).*
>   4. **Identificación:** Reconocer la identidad concreta del objeto o sujeto comparándolo con bases de datos. *(Ejemplo: Reconocer que el rostro pertenece al administrador autorizado).*
>   5. **Seguimiento (*Tracking*):** Mantener la referencia temporal de un elemento dinámico a través de una secuencia continua de fotogramas. *(Ejemplo: Seguir la trayectoria de un brazo robótico o una pieza defectuosa en la línea de montaje).*

---

### Pregunta 2. Caso Práctico: Diseño de un Sistema Multimodal y Análisis de Errores (3,5 puntos)
Imagina que estás diseñando la interfaz de control por voz y gestos para un **coche inteligente**. 
1. Propón una **interfaz multimodal** combinando al menos dos modalidades de entrada natural vistas en el temario e indica cómo se complementan (qué ocurre si una de ellas falla).
2. Analiza un escenario donde se produzca un **falso positivo** y otro donde se produzca un **falso negativo**, explicando por qué son críticos en el contexto de la automoción.
3. Diseña un sistema de control gestual basado en **puntos clave (*landmarks*)** para silenciar la radio mediante un gesto con la mano, indicando de forma razonada las condiciones y los puntos necesarios.

> **Respuesta orientativa / Criterios de corrección:**
> 1. *Interfaz Multimodal (Coche):* Combinación de **Voz** y **Seguimiento Ocular / Gestual**. Si el conductor experimenta fatiga acústica o hay un ruido extremo en la carretera que invalida el reconocimiento por voz, el sistema permite confirmar órdenes mediante un gesto rápido en el volante o la mirada en un menú flotante sobre el parabrisas.
> 2. *Análisis de Errores Críticos:*
>   * *Falso Positivo:* El sistema interpreta por error una conversación intrascendente del pasajero como un comando de voz para apagar los faros o desbloquear puertas. Es crítico porque compromete la seguridad vial activa.
>   * *Falso Negativo:* El conductor grita claramente *"¡Frenar / Emergencia!"* o ejecuta un gesto de parada y el sistema no lo reconoce por interferencias del aire acondicionado. Es crítico porque impide una respuesta rápida ante imprevistos.
> 3. *Diseño Gestual (Landmarks):* 
>   * *Puntos necesarios:* Articulaciones de los dedos y muñeca de la mano derecha[cite: 74].
>   * *Condición aproximada:* Si la posición espacial de la palma abierta se sitúa verticalmente frente al salpicadero a una distancia predefinida durante más de un segundo, o se cierra el puño de forma seca.
  
---

### Pregunta 3. Caso Práctico: Desarrollo de Aplicación de Realidad Aumentada (RA) (3,0 puntos)
Diseña la estructura técnica y funcional de una aplicación de **Realidad Aumentada (RA)** aplicada al sector educativo (por ejemplo, para clases de anatomía o montaje de circuitos electrónicos en el aula). Rellena los apartados técnicos fundamentales exigidos para un proyecto de desarrollo:
* **Ámbito y objetivo:**
* **Entrada de cámara:**
* **Qué debe detectar:**
* **Elemento digital añadido:**
* **Cómo se posiciona / anclaje:**
* **Interacción del usuario:**
* **Posible problema técnico o de privacidad:**

> **Respuesta orientativa / Criterios de corrección:**
> * *Ámbito y objetivo:* Educación secundaria; facilitar la visualización tridimensional del sistema circulatorio humano superpuesto sobre un maniquí o lámina de estudio para mejorar la comprensión espacial.
> * *Entrada de cámara:* Vídeo en tiempo real capturado por la cámara trasera de una tablet o smartphone del alumno[cite: 77, 78].
> * *Qué debe detectar:* Planos horizontales de la mesa o un marcador visual impreso (código patrón) situado junto a la lámina anatómica de referencia[cite: 77, 78].
> * *Elemento digital añadido:* Modelo 3D interactivo y animado del corazón y vasos sanguíneos principales[cite: 77, 78].
> * *Cómo se posiciona / anclaje:* Anclaje espacial basado en la detección de superficies planas o sobre el marcador visual, asegurando que el modelo virtual conserve la perspectiva y el tamaño relativo al mover el dispositivo[cite: 77, 78].
> * *Interacción del usuario:* Táctil sobre la pantalla (pulsar sobre las aurículas o ventrículos para desplegar información textual y aislar componentes)[cite: 78].
> * *Posible problema técnico o de privacidad:* Oclusiones parciales si el alumno pasa la mano por delante de la cámara bloqueando la referencia, o problemas de iluminación insuficiente en el aula que degraden el anclaje de la RA[cite: 75, 78].