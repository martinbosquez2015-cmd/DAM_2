# **SASDWEASD**  
**Xz<xxzczc**  
Dsadasd   
- Asd  
- Sdas  
- Sdsa  
Sadsad  
Sadad  
dsdasdwa  


# Apuntes de Java: Programación Multiproceso (PSP - UT1)

En Java, un **proceso** es un programa en ejecución que dispone de su propio espacio de memoria independiente asignado por el Sistema Operativo[cite: 65, 72]. A diferencia de los hilos (que comparten memoria), los procesos están aislados entre sí y se comunican mediante canales de Entrada/Salida (Streams), ficheros o sockets[cite: 13, 14, 72].

---

## 1. Conceptos Fundamentales y Ciclo de Vida de Procesos

### Clases principales (`java.lang`)
* **`ProcessBuilder`**: Es el "preparador" o configurador del proceso[cite: 18, 38]. Se utiliza **antes** de arrancar el proceso para definir qué programa se ejecuta, sus argumentos, su directorio de trabajo y hacia dónde se redirigen sus flujos de entrada/salida.
* **`Process`**: Es el objeto que representa al subproceso **ya en ejecución** (se obtiene al llamar a `pb.start()`)[cite: 14, 15, 38]. Permite interactuar con él mientras está vivo, esperar a que termine, leer lo que imprime, enviarle datos o destruirlo.

### Tipos de procesos según su interacción
1. **Independientes**: No se comunican ni comparten recursos con otros procesos[cite: 10].
2. **Cooperativos**: Colaboran para realizar una tarea común intercambiando información (por ejemplo, un proceso padre que lanza un hijo y recoge su resultado)[cite: 10, 19].
3. **Competitivos**: Luchan por acceder al mismo recurso compartido de forma exclusiva (por ejemplo, dos procesos intentando escribir en el mismo fichero a la vez)[cite: 10, 11].

---

## 2. Lanzamiento y Control Básico de Procesos

1. **Método 1: Lanzar un proceso hijo y comprobar su código de salida (`waitFor` y `System.exit`)**
   Por defecto, un proceso hijo lanzado desde Java **no tiene consola propia**[cite: 14, 18]. Además, el proceso padre no se detiene a esperarlo a menos que se lo indiquemos explícitamente con `waitFor()`[cite: 15, 16].
   <br>
   ```java
   public static void lanzarProcesoBasico() {
       try {
           // 1. Indicamos la ruta donde están compilados los archivos .class del proyecto hijo
           // OJO: El proyecto hijo DEBE estar compilado antes de poder lanzarlo desde el padre
           File directorio = new File("../ProyectoHijo/bin");

           // 2. Configuramos el comando. El primer elemento es el ejecutable ("java"), 
           // el segundo es paquete.Clase, y los siguientes son los argumentos (args[])
           ProcessBuilder pb = new ProcessBuilder("java", "mispaquetes.ValidadorEntero", "6");
           
           // 3. Establecemos el directorio de trabajo donde buscará la clase compilada
           pb.directory(directorio);

           // 4. Arrancamos el proceso. A partir de aquí, padre e hijo corren en paralelo
           Process p = pb.start();

           // 5. waitFor() bloquea al proceso padre hasta que el hijo ejecute System.exit() o acabe su main
           // Devuelve el código de salida (exit code). Por convención: 0 = OK, distinto de 0 = Error
           int exitVal = p.waitFor();
           System.out.println("El proceso hijo ha terminado con código: " + exitVal);

           // Ejemplo de interpretación de códigos personalizados (Práctica Multiproceso II - Ej. 1)
           switch (exitVal) {
               case -3:
                   System.out.println("Has escrito un entero positivo");
                   break;
               case 0:
                   System.out.println("El entero debe ser positivo");
                   break;
               default:
                   System.out.println("Error o dato no válido");
                   break;
           }
       } catch (Exception e) {
           System.err.println("Error al gestionar el proceso: " + e.getMessage());
       }
   }
   ```

2. **Método 2: Control de tiempo límite (`waitFor` con Timeout)**
   Si un proceso hijo se queda colgado en un bucle infinito, `waitFor()` sin parámetros dejaría al padre bloqueado para siempre. Para evitarlo en entornos reales, usamos su versión sobrecargada con límite de tiempo[cite: 17].
   <br>
   ```java
   public static void lanzarConTimeout() {
       try {
           ProcessBuilder pb = new ProcessBuilder("java", "mispaquetes.ProcesoLento");
           pb.directory(new File("../ProyectoHijo/bin"));
           Process p = pb.start();

           // Esperamos un máximo de 10 segundos a que el hijo termine
           // Devuelve true si terminó a tiempo, o false si se agotó el tiempo
           boolean terminadoATiempo = p.waitFor(10, TimeUnit.SECONDS);

           if (terminadoATiempo) {
               System.out.println("Proceso finalizado correctamente. Código: " + p.exitValue());
           } else {
               System.out.println("Timeout alcanzado. Forzando cierre del proceso...");
               // destroy() mata el proceso de forma limpia; destroyForcibly() lo mata a la fuerza
               p.destroy(); 
           }
       } catch (Exception e) {
           System.err.println("Error: " + e.getMessage());
       }
   }
   ```

---

## 3. Comunicación entre Procesos mediante Streams (Tuberías)

Como el proceso hijo no tiene ventana de consola, sus flujos estándar (`stdin`, `stdout`, `stderr`) están conectados directamente al proceso padre[cite: 14, 18].

> ** ¡OJO CON LA DIRECCIÓN DE LOS STREAMS! (Trampa típica de examen)**
> Los nombres de los métodos en la clase `Process` están nombrados **desde el punto de vista del proceso padre**:
> * `p.getOutputStream()`: Es un flujo de **salida** para el padre, por donde **envía** datos hacia la entrada estándar (`System.in`) del hijo[cite: 14, 15].
> * `p.getInputStream()`: Es un flujo de **entrada** para el padre, por donde **lee** lo que el hijo imprime en su salida normal (`System.out`).
> * `p.getErrorStream()`: Es un flujo de **entrada** para el padre, por donde **lee** los errores (`System.err`) del hijo[cite: 14, 15, 20].

1. **Método Estándar: Enviar datos al hijo y leer su respuesta**
   ```java
   public static void comunicacionBidireccional() {
       try {
           ProcessBuilder pb = new ProcessBuilder("java", "mispaquetes.SumaAcumulativa");
           pb.directory(new File("../ProyectoSuma/bin"));
           Process p = pb.start();

           // --- 1. ENVIAR DATOS AL HIJO (OutputStream del padre -> System.in del hijo) ---
           // Usamos try-with-resources o cerramos explícitamente al terminar de escribir
           OutputStream os = p.getOutputStream();
           
           // ¡IMPRESCINDIBLE! Añadir "\n" al final. Si el hijo lee con Scanner.nextLine() o BufferedReader.readLine(),
           // se quedará bloqueado esperando el salto de línea si no se lo enviamos
           os.write("3\n".getBytes());
           os.write("5\n".getBytes());
           os.write("*\n".getBytes());
           
           // flush() vacía el buffer intermedio y obliga a que los bytes viajen inmediatamente al hijo
           os.flush();
           os.close(); // Cerrar el stream indica al hijo que no habrá más datos (EOF)

           // --- 2. ESPERAR A QUE EL HIJO PROCESE ---
           int exitVal = p.waitFor();
           System.out.println("Valor de Salida: " + exitVal);

           // --- 3. LEER LA RESPUESTA DEL HIJO (System.out del hijo -> InputStream del padre) ---
           if (exitVal == 0) {
               // Envolvemos el InputStream en un Scanner para leer cómodamente línea a línea
               try (Scanner scHijo = new Scanner(p.getInputStream())) {
                   while (scHijo.hasNextLine()) {
                       System.out.println(scHijo.nextLine());
                   }
               }
           } else {
               // Si hubo error, leemos el canal de errores (getErrorStream) para saber qué pasó
               try (Scanner scError = new Scanner(p.getErrorStream())) {
                   while (scError.hasNextLine()) {
                       System.err.println("ERROR DEL HIJO: " + scError.nextLine());
                   }
               }
           }
       } catch (Exception e) {
           System.err.println("Error de comunicación: " + e.getMessage());
       }
   }
   ```

---

## 4. Redirección de Entrada/Salida a Ficheros

En lugar de leer y escribir manualmente con `Scanner` y `OutputStream`, `ProcessBuilder` permite conectar los flujos del hijo directamente a archivos del disco duro **antes** de hacer `start()`[cite: 39, 40, 41].

> **Importante:** Si rediriges un flujo (por ejemplo, `pb.redirectOutput(archivo)`), el stream correspondiente en `Process` (`p.getInputStream()`) se "vacía" y devolverá nulo o un stream vacío, porque los datos ya van directos al archivo[cite: 18, 37, 39].

1. **Método Estándar: Redirección completa (Entrada, Salida y Errores)**
   ```java
   public static void redirigirAFicheros() {
       try {
           ProcessBuilder pb = new ProcessBuilder("java", "mispaquetes.SumaFicheros");
           pb.directory(new File("../ProyectoSuma/bin"));

           // Definimos los archivos físicos. Si no se indica ruta, se buscan/crean en la raíz del proyecto padre
           File fEntrada = new File("datos.txt");
           File fSalida = new File("suma.txt");
           File fError = new File("error.txt");

           // Configuramos las redirecciones ANTES de invocar pb.start()
           pb.redirectInput(fEntrada);  // El hijo leerá datos.txt como si fuera su teclado (System.in)
           pb.redirectOutput(fSalida);  // Todo System.out.println() del hijo irá directo a suma.txt
           pb.redirectError(fError);    // Todo System.err.println() o excepción irá directo a error.txt

           Process p = pb.start();
           int exitVal = p.waitFor();

           if (exitVal == 0) {
               System.out.println("Proceso terminado. Resultados guardados en suma.txt");
           } else {
               System.out.println("Hubo un error. Revisa error.txt");
           }
       } catch (Exception e) {
           System.err.println("Error al redirigir: " + e.getMessage());
       }
   }
   ```

---

## 5. Sincronización de Procesos Competitivos (Bloqueo de Ficheros)

Cuando dos procesos independientes intentan leer y escribir el mismo fichero simultáneamente, se producen **condiciones de carrera** e intercalación corrupta de datos[cite: 47, 48]. Como Java no tiene palabras clave (`synchronized`) que funcionen entre *procesos distintos* (solo sirven para hilos dentro del mismo proceso), debemos usar mecanismos del Sistema Operativo[cite: 51].

1. **Método 1 (Rudimentario / No recomendado): Fichero testigo con `File` (Espera Activa)**
   Consiste en crear un fichero temporal (`lock.tmp`) como semáforo[cite: 52].
   * *Fallo grave si se usa `while(lock.exists())`*: Dos procesos pueden comprobar `exists()` en el mismo microsegundo, ver que es `false` y entrar ambos a la vez[cite: 53].
   * *Versión mejorada*: Usar `createNewFile()`, que es una operación atómica del SO (devuelve `true` si lo crea, `false` si ya existía)[cite: 54]. Aun así, requiere **espera activa** (consumir CPU durmiendo y despertando en un bucle)[cite: 55].
   <br>
   ```java
   public static void semaforoFicheroRudimentario() {
       File lock = new File("datos.lock");
       try {
           // createNewFile() es atómico: solo un proceso logrará que devuelva true
           while (!lock.createNewFile()) {
               // Espera activa (polling): dormimos 100ms antes de volver a preguntar
               Thread.sleep(100);
           }
           
           // --- SECCIÓN CRÍTICA (Uso exclusivo del recurso compartido) ---
           System.out.println("Recurso adquirido. Escribiendo datos...");
           
       } catch (Exception e) {
           System.err.println("Error: " + e.getMessage());
       } finally {
           // ¡CRÍTICO! Siempre liberar el bloqueo en el bloque finally para evitar deadlocks si hay error
           if (lock.exists()) {
               lock.delete();
           }
       }
   }
   ```

2. **Método 2 (Método Estrella / Profesional): Bloqueo nativo con `FileChannel` y `FileLock` (Java NIO)**
   Es la forma recomendada en los apuntes[cite: 55, 56]. Utiliza el sistema operativo para dormir al proceso de forma real (sin gastar CPU en bucles `while`) hasta que el otro proceso suelte el cerrojo[cite: 55, 56].
   > **Regla de oro:** Nunca hagas el `FileLock` sobre el mismo fichero en el que escribes con `PrintWriter` o `BufferedWriter` porque los buffers chocan con el bloqueo[cite: 56]. Usa siempre un fichero auxiliar exclusivo para el cerrojo (ej. `datos.lock`)[cite: 56, 60].
   <br>
   ```java
   public static void bloqueoProfesionalNIO() {
       File lockFile = new File("datos.lock");
       File dataFile = new File("datos.txt");

       try {
           if (!lockFile.exists()) lockFile.createNewFile();

           // Usamos try-with-resources: FileLock es AutoCloseable, por lo que al salir del try
           // el bloqueo se libera automáticamente incluso si ocurre una excepción
           try (RandomAccessFile raf = new RandomAccessFile(lockFile, "rw");
                FileChannel channel = raf.getChannel();
                // channel.lock() detiene el proceso aquí hasta que el cerrojo esté libre (Bloqueo real)
                FileLock lock = channel.lock();
                PrintWriter writer = new PrintWriter(new FileWriter(dataFile, true))) {

               System.out.println("Bloqueo exclusivo adquirido.");
               writer.println("Escribiendo línea de forma segura sin condiciones de carrera.");
               writer.flush();

           } // <-- Aquí se cierra writer, lock, channel y raf automáticamente
           System.out.println("Bloqueo liberado.");

       } catch (Exception e) {
           System.err.println("Error en bloqueo NIO: " + e.getMessage());
       }
   }
   ```

---

## Consejos de Nivel Profesional (Clean Code y Buenas Prácticas)

1. **Un único punto de salida en procesos hijos (`System.exit`)**:
   Evita llenar tu código de múltiples `System.exit(-1)`, `System.exit(-2)` repartidos por diferentes `if` y `catch`. Guarda el código en una variable (ej. `int exitCode = 0;`), cierra tus recursos (`Scanner`, streams) de forma ordenada y haz un único `System.exit(exitCode);` al final del `main`. Esto evita fugas de memoria y hace el código mucho más legible y mantenible.
2. **Codificación de caracteres explícita (`UTF-8`)**:
   Al pasar textos con tildes (`á, é, í`) o eñes (`ñ`) entre procesos mediante `os.write(texto.getBytes())`, en Windows puede fallar si no especificas el charset (porque Windows usa `Cp1252` o `ISO-8859-1` por defecto mientras que Linux usa `UTF-8`)[cite: 156]. Usa siempre `texto.getBytes(StandardCharsets.UTF_8)` al escribir y `new Scanner(is, StandardCharsets.UTF_8)` al leer.
3. **Evita el bloqueo mutuo de buffers (*Deadlock* de Streams)**:
   Si un proceso hijo imprime muchísimas líneas por `System.out` o `System.err`, el buffer interno del Sistema Operativo (que suele ser pequeño, de unos 4KB a 64KB) se llenará. Cuando se llena, el hijo se queda congelado esperando a que alguien vacíe el buffer, y si el padre está esperando en `p.waitFor()` **antes** de leer el `InputStream`, ambos procesos se quedarán bloqueados para siempre (*Deadlock*). **Solución:** Lee siempre el `InputStream` del hijo *antes* de llamar a `p.waitFor()`, o léele en un hilo separado.
4. **Rutas portables con `File.separator` o `Path.of()`**:
   Evita escribir rutas a mano con barras invertidas de Windows (`..\\carpeta\\bin`) o barras de Linux (`../carpeta/bin`) si el programa va a ejecutarse en distintos sistemas. Usa `Path.of("..", "ProyectoHijo", "bin").toFile()` para que Java construya la ruta correctamente según el sistema operativo anfitrión.
5. **Cerrar siempre el `OutputStream` hacia el hijo**:
   Cuando el padre termina de enviar datos al hijo mediante `p.getOutputStream()`, llama siempre a `os.flush()` y seguidamente a `os.close()`. Si dejas el stream abierto, un hijo que esté leyendo con un bucle `while(sc.hasNextLine())` nunca detectará el fin de fichero (*EOF*) y se quedará esperando eternamente.