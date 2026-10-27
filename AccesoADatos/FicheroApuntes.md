# Apuntes de JavaFicheros
    Existen diferentes métodos
## Lectura
1. __Método 1__:
    Este método usa dos objetos, uno que convierte la ruta en el fichero y otro que es como un dedo que lee línea por línea
    <br>
    ```
        public static void lectura1() {
		// Tratar las excepciones es obligatorio cuando se trabaja con ficheros
		try {
			// necesitamos dos objetos. FileReader simboliza al ficharo. Recibe como argumento el nombre 
			// o la ruta al mismo
			FileReader fichero = new FileReader("/mnt/temp/quijote.txt");
			// BufferedReader nos lo podemos imaginar como un cursor que apunta al lugar donde vamos leyendo
			// Como si señaláramos en un libro con el dedo la siguiente línea que nos toca leer
			// inicialmente apunta al principio del fichero
			BufferedReader lector = new BufferedReader(fichero);
			String linea;
			// Usamos un bucle para leer del fichero. Como al menos tendremos que leer una vez
			// la estructura do-while parece la mas correcta. Luego veremos una alternativa
			do{
				// readline lee una linea completa del fichero o null cuando hayamos llegado al final
				// el último caracter, el salto de línea si existe, que simbolizamos con \n se suprime cuando se lee
				linea = lector.readLine();
				if(linea!=null)
					System.out.println(linea);
			// cuando leamos null es nuestra condición de salida: hemos leído el fichero completo
			}while(linea!=null);
			// No olvidemos cerrar al final para no consumir recursos innecesariamente
			lector.close();
		// Existen excepciones concretas (FileNotFound, IOException) para la mayoría de problemas que 
		// puede haber con un fichero. Si usamos una excepción genérica, podemos ver que ocurre
		// exactamente con el método getMessage
		} catch (Exception e) {
			System.out.println("Error al leer: " + e.getMessage());
		}
	}
	```
2. __Método 2__: Método más compacto
    ```
	public static void lectura2() {
		// Este método es similar al anterior, pero mas compacto
		try {
			// Creamos en la misma sentencia los dos objetos que necesitamos
			BufferedReader lector = new BufferedReader(new FileReader("/mnt/temp/quijote.txt"));
			String linea;
			// Leemos las líneas del fichero en la condición el while y salimos si lo que leemos es null
			while ((linea = lector.readLine()) != null) {
				System.out.println(linea);
			}
			lector.close();
		} catch (Exception e) {
			System.out.println("Error al leer: " + e.getMessage());
		}
	}
    ```
3. __Método 3__: Aquí utilizamos el método de Scanner, pero lee el fichero línea por línea
    ``` 
	public static void lectura3() {
		try {
			// En este método vamos a usar la clase Scanner que ya conocemos de cuando leíamos del teclado
			// La clase File simboliza al fichero y scanner será nuestro cursor
			File fichero = new File("/mnt/temp/quijote.txt");
			Scanner scanner = new Scanner(fichero);
			// como condición de salida usamos hasNextLine que nos devuelve false cuando llegamos al final del fichero
			// y ya no hay mas líneas para leer y true mientras que esto no ocurra
			while (scanner.hasNextLine()) {
				// nextLine lee del fichero una línea completa, hasta que se encuentra un salto de línea
				// Al igual que en el caso anterior, también suprime los caracteres \n
				String linea = scanner.nextLine();
				System.out.println(linea);
			}
			scanner.close();
		} catch (Exception e) {
			System.out.println("Error al leer: " + e.getMessage());
		}
	}
    ```

4. __Método 4__: Este es el método estrella, será pasar el contenido del fichero por líneas a un ArrayList de Strings
    ```
	public static void lectura4() {
		// En este método leeremos el fichero de una vez y lo guardaremos en un ArrayList donde cada elemento
		// es una línea del fichero. Necesitamos el objeto Path que simboliza la ruta al fichero
		// Path no necesitar ir dentro de la excepción
		Path ruta = Path.of("/home/josemaria/quijote.txt");
		ArrayList<String> lineas = null;
		try {
			// El método readAllLines lee el fichero completamente y luego lo cierra y almacena el contenido en un List
			// Como nosotros no hemos usado el objeto List (y el ArrayList que si conocemos es compatible y mas potente)
			// Usamos un cast
			lineas = (ArrayList<String>)Files.readAllLines(ruta);
			// No obstante, si quieres usar List sería así:
			// List<String> lineas = Files.readAllLines(ruta);
		} catch (Exception e) {
			System.out.println("Error al leer: " + e.getMessage());
		}
		// Para procesar el contenido, trabajamos con el ArrayList
		// También ha suprimido por nosotros los saltos de línea finales de cada línea
		for (String linea : lineas) {
			System.out.println(linea);
		}
	}
    ```

5. __Método 5__: Este método te guarda todo el contenido en un string, los saltos de línea se representan con el caracter "\n" que aquí solo es uno na mas(pero honestamente no tengo ni idea de como aplicarlo)
    ``` 
	public static void lectura5() {
		// El último método lee todo el contenido del fichero como un único String
		Path ruta = Path.of("/home/josemaria/quijote.txt");
		String contenido = null;
		try {
			// leemos todo el contenido de una vez en un String
			// cuidado que aquí los saltos de línea siguen existiendo. Si nuestro fichero tuviera dos lineas así
			// uno
			// dos
			// contenido sería así: "uno\ndos"
			// El \n simboliza el salto de línea y ocupa un solo caracter y no dos
			contenido = Files.readString(ruta);
		} catch (Exception e) {
			System.out.println("Error al leer: " + e.getMessage());
		}
		System.out.print(contenido);
	}
    ```

## Escritura
1. __Método 1__: Para esta función será necesario solo un objeto. Ojo con las dos formas de crear el objeto, cuando pasas solo el argumento de la ruta, exista o no el archivo lo crea de nuevo y sobreescribe lo que había antes; si cuando creas el FileWriter pones como segundo argumento el true, lo que va a hacer es ver si es que el archivo existe, en el caso de que no exista crea uno nuevo, pero si el archivo existe entonces lo que hace es que añade contenido sin borrar lo anterior.

    ``` 
	public static void escritura1() {
		try {
			// si el fichero no existe se crea. Si existe se borra su contenido antes de
			// escribir
			FileWriter escritor = new FileWriter("/mnt/temp/java.txt");
			escritor.write("Hola, mundo con FileWriter!\n");
			escritor.write("Escribiendo una segunda línea.");
			System.out.println("Archivo escrito correctamente.");
			escritor.close();
		} catch (Exception e) {
			System.err.println("Error al escribir el archivo: " + e.getMessage());
		}
		try {
			// el segundo parámetro, true, indica que se añade al final (append) si el
			// fichero ya existe
			// si el fichero no existe se crea
			FileWriter escritor = new FileWriter("/mnt/temp/java.txt", true);
			escritor.write("\nEsta línea se añade al final.");
            escritor.close()
		} catch (Exception e) {
			System.err.println("Error al añadir al archivo: " + e.getMessage());
		}
	}
    ```
2. __Método 1 mejorado__: Este método es igual que el primero, pero resulta más profesional a la hora de manipular los ficheros, esto porque lo que hace es que se cierra el archiovo correctamente luejo de ejecutarse la sentencia del try, en el caso del método 1, tenías que cerrar el objeto de tripo FileWriter manualmente.
    ```
	public static void escritura1bis() {
		// Idem al anterior pero usamos try-with-resources para que el fichero se cierre
		// automáticamente
		try (FileWriter escritor = new FileWriter("/mnt/temp/java.txt")) {
			escritor.write("Hola, mundo con FileWriter!\n");
			escritor.write("Escribiendo una segunda línea.");
			// Podríamos escribir caracteres sueltos o arrays de char.
			System.out.println("Archivo escrito correctamente.");
		} catch (Exception e) {
			System.err.println("Error al escribir el archivo: " + e.getMessage());
		}
		// Idem para añadir
		try (FileWriter escritor = new FileWriter("/mnt/temp/java.txt", true)) {
			escritor.write("\nEsta línea se añade al final.");
		} catch (Exception e) {
			System.err.println("Error al añadir al archivo: " + e.getMessage());
		}
	}
    ```


3. __Método 2__: Este método es eficiente para archivos grandes, el objeto a usar sera de tipo BufferedWriter
aquí, igual que el método 1 mejorado, utilizamos la forma try-with resources para no olvidar de cerrar el objeto
    ```

	public static void escritura2() {
		// BufferedWriter es mas eficiente. sobre todo para grandes datos
		// Usa un buffer intermedio. Por lo mismo es mas crítico no olvidar cerrar el fichero
		// Podemos usar la estrategia de try-with-resource para no olvidarlo
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter("/mnt/temp/java.txt"))) {
			escritor.write("Primera línea con Buffer.");
			// Escribe el salto de línea. Es mas portable porque si en el sistema en el que
			// se ejecuta se usa otro caracter diferente al \n lo tiene en cuenta
			escritor.newLine();
			escritor.write("Segunda línea. Es más eficiente.");
			escritor.newLine();
			// escritor.close();
			System.out.println("Archivo escrito eficientemente.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		// y también podemos añadir
		try (BufferedWriter escritor = new BufferedWriter(new FileWriter("/mnt/temp/java.txt", true))) {
			escritor.write("Última línea.");
			System.out.println("Añadido.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
    ```

4. __Método 4__: Con este método podemos escribir con formato, el tipo de objeto será PrintWriter
    ```
	public static void escritura3() {
		// PrintWriter nos permite grabar "con formato" de la misma forma que haríamos con printf
		// Podemos especificar el juego de caracteres que vamos a usar (opcional)
		try (PrintWriter escritor = new PrintWriter("/mnt/temp/java.txt", StandardCharsets.UTF_8)) {
			escritor.println("Línea 1: Hola con PrintWriter.");
			escritor.print("Línea 2: Esto no tiene salto de línea. ");
			escritor.println("Pero esto sí lo añade.");
			// Formateo de datos
			String nombre = "Ana";
			int edad = 30;
			double altura = 1.7589;
			escritor.printf("Usuario: %s, Edad: %d, Altura: %.2f m", nombre, edad, altura);
			escritor.println(); // Salto de línea adicional

			System.out.println("Archivo escrito con formato correctamente.");

			// Podemos comprobar si hubo errores que no generen excepción así:
			if (escritor.checkError()) {
				System.err.println("Ocurrió un error durante la escritura.");
			}

		} catch (Exception e) {
			System.err.println("Error al abrir/crear el archivo: " + e.getMessage());
		}
		// Para añadir con PrintWriter usamos también PrintWriter
		try (PrintWriter escritor = new PrintWriter(
                new FileWriter("/mnt/temp/java.txt", StandardCharsets.UTF_8, true))) {
            
            escritor.println("Esta línea se añade al final.");
            escritor.printf("Número de línea: %d%n", 42);
            
            System.out.println("Texto añadido correctamente con PrintWriter");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
	}
    ```

5. __Método 5__: COn este puede meter listas en un fichero
    ```
	public static void escritura4() {
		// Ahora vamos a ver un método para grabar listas
		Path rutaArchivo = Paths.get("/mnt/temp/java.txt");
		ArrayList<String> lineas = new ArrayList<>(List.of("Primera Linea", "Segunda línea", "Tercera línea"));
		try {
			// Abre el fichero, escribe la lista (una línea por elemento) y luego cierra el fichero
			Files.write(rutaArchivo, lineas, StandardCharsets.UTF_8);
			System.out.println("Lista escrita en archivo.");
		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
		
		// y para añadir
		ArrayList<String> nuevasLineas = new ArrayList<>(List.of("Cuarta línea", "Quinta línea"));
		try {
            Files.write(
            	rutaArchivo, 
                nuevasLineas, 
                StandardCharsets.UTF_8,
                // StandardOpenOption.CREATE crea el fichero si no existe. Si no lo ponemos generaría una excepción si el fichero no existe, pero si existe añade
                StandardOpenOption.CREATE,   
                StandardOpenOption.APPEND
            );
            
            System.out.println("Líneas añadidas correctamente");
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
	}
    ```

5. __Método 6__: Este método es similar al anterior, pero en este caso metemos strigs en lugar de listas
    ```
	public static void escritura5() {
		// Similar al método anterior, pero para escribir Strings
		Path rutaArchivo = Paths.get("/mnt/temp/java.txt");
		String contenido = "Este es el contenido a escribir.\nSegunda línea.";
		try {
			// Abre el fichero, graba el string y lo cierra luego
			Files.writeString(rutaArchivo, contenido, StandardCharsets.UTF_8);
			System.out.println("Archivo escrito con Files.writeString");

			// Añadir más contenido (append). Funciona también igual que en el caso de las listas
			String masContenido = "\nAñadiendo más texto.";
			Files.writeString(rutaArchivo, masContenido, StandardCharsets.UTF_8, StandardOpenOption.CREATE, StandardOpenOption.APPEND);
			System.out.println("Texto añadido.");

		} catch (Exception e) {
			System.err.println("Error: " + e.getMessage());
		}
	}
    ```


## Escritura binaria
    Aquí solo hay dos métodos, uno para lectura y otro para escritura: InputStream y Output. Con estas formas podemos meter objetos tambien, pero es necesario que haya un metodo para cada tipo de objeto; y en la calse de objeto, se tiene que serializar con el constructor. 
    Ej:
``` 
package Ficheros;

import java.io.Serializable;

//Para poder grabar los objetos en un fichero y darles persistencia tienen que ser Serializables
public class Pokemon implements Serializable{ 
```

    otra cosa importante, cuando leas ficheros va a ser necesario que, si asignas variables, tomes en cuenta el orden en que escribiste las cosas, porque se reservan numeros de bits por cada tipo de dato. Por último, este método, si ve que no hay ningun fichero lo crea automáticamente.
- Método para escribir cosas normales:
```
public static void escribirFichero(String fichero) {
		// Fíjate que usamos "output" para escribir e "input" para leer. Puede parecer contradictorio
		// pero piensa que el punto de vista es el de la "persona" que lee o graba
		// cuando escribes es output porque los datos salen de ti hacia el fichero
		// cuando lees es input porque los datos vienen a ti desde el fichero
		 try (DataOutputStream binario = new DataOutputStream(new FileOutputStream(fichero))) {
	            // Lo mas importante a la hora de escribir en un fichero binario es que importa el tipo de datos
			 	// que se escribe. En los de texto no, porque siempre escribimos texto. Aquí usamos métodos distintos
			 	// por cada tipo de datos
			 	// Por lo demás, la estructura es muy similar
	            binario.writeInt(42);             
	            binario.writeDouble(3.14159);     
	            binario.writeBoolean(true);       
	            binario.writeUTF("Hola Mundo");   
	            binario.writeChar('A');           
	            
	            System.out.println("Datos escritos correctamente en " + fichero);         
	        } catch (Exception e) {
	            System.err.println("Error: " + e.getMessage());
	        }
	}
	
	public static void leerFichero(String fichero) {
		try (DataInputStream binario = new DataInputStream(new FileInputStream(fichero))) {
            
            // Tenemos que leer en el mismo orden y los mismos tipos que se escribieron
			// Prueba a intercambia el orden de las dos líneas siguientes y verás que lo que se lee
			// en ese caso es totalmente distinto a lo que escribiste
            int entero = binario.readInt();
            double decimal = binario.readDouble();
            boolean bool= binario.readBoolean();
            String texto = binario.readUTF();
            char caracter = binario.readChar();
            
            System.out.println("Datos leídos del fichero:");
            System.out.println("Entero: " + entero);
            System.out.println("Double: " + decimal);
            System.out.println("Booleano: " + bool);
            System.out.println("String: " + texto);
            System.out.println("Char: " + caracter);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
	}
	
```
- Métodos para escribir sobre objetos y así:
```
	public static void main(String[] args) {
		// Los ficheros binarios guardan información no legible ni modificable directamente
		// ventajas: menos espacio, mas velocidad, permite guardar datos complejos y persistencia de objetos de forma mas cómoda
		String fichero = "/home/josemaria/binario.bin";
		
		// Persistencia de objetos
		Pokemon pokemon = new Pokemon(6, "Charizard", "Fuego", "Volador");
		guardarPokemon(pokemon, fichero);
		Pokemon pokemonRecuperado = recuperarPokemon(fichero);
		if(pokemonRecuperado!=null)
			pokemonRecuperado.mostrar();
		
		// Guardar y recuperar listas de objetos
		Pokemon p1 = new Pokemon(1, "Bulbasaur", "Planta");
		Pokemon p2 = new Pokemon(6, "Charizard", "Fuego", "Volador");
		Pokemon p3 = new Pokemon(2, "Ivysaur", "Planta");
		Pokemon p4 = new Pokemon(25, "Pikachu", "Eléctrico");
		Pokemon p5 = new Pokemon(11, "Metapod", "Bicho");
		Pokemon p6 = new Pokemon(7, "Squirtle", "Agua");
		ArrayList<Pokemon> listaPokemons = new ArrayList<>(List.of(p1, p2, p3, p4, p5, p6));
		guardarListaPokemons(listaPokemons, fichero);
		ArrayList<Pokemon> listaRecuperada = recuperarListaPokemons(fichero);
		for (Pokemon poke:listaRecuperada) {
			poke.mostrar();
		}
		
		// Si quisieramos añadir un pokemon a un fichero existente lo recuperamos, añadimos el fichero a la lista y volvemos a grabar
		Pokemon p7 = new Pokemon(131,"Lapras","Agua","Hielo");
		listaRecuperada = recuperarListaPokemons(fichero);
		listaRecuperada.add(p7);
		guardarListaPokemons(listaRecuperada, fichero);
		listaRecuperada = recuperarListaPokemons(fichero);
		for (Pokemon poke:listaRecuperada) {
			poke.mostrar();
		}
	}
    public static void guardarPokemon(Pokemon pokemon, String fichero) {
		// guardar un objeto es sumamente fácil. Usamos siempre el mismo método y no hace falta usar un "cast"
        try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
            binario.writeObject(pokemon);
            System.out.println("Pokemon guardado correctamente en " + fichero);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
	}
	
	public static Pokemon recuperarPokemon(String fichero) {
		Pokemon pokemon = null;
        try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {
        	// Al leer, el cast al tipo de objeto es obligatorio
            pokemon = (Pokemon) binario.readObject();
            System.out.println("Pokemon recuperado correctamente");
            
        }catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return pokemon;
    }
	
	public static void guardarListaPokemons(ArrayList<Pokemon> lista, String fichero) {
		// Un método muy práctico para guardar y recuperar objetos sin preocuparnos de cuantos son es guardarlos en una lista
		// La lista se trata como un único objeto independientemente de su tamaño
        try (ObjectOutputStream binario = new ObjectOutputStream(new FileOutputStream(fichero))) {
            binario.writeObject(lista);
            System.out.println("Lista de Pokemons guardada correctamente en " + fichero);
            
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
	}
	
	public static ArrayList<Pokemon> recuperarListaPokemons(String fichero) {
		ArrayList <Pokemon> lista = null;
        try (ObjectInputStream binario = new ObjectInputStream(new FileInputStream(fichero))) {        
        	// Al leer, la lista es también un único objeto de forma independiente a su tamaño
        	// No olvides el cast, que también hace falta
            lista = (ArrayList<Pokemon>) binario.readObject();
            System.out.println("Lista de Pokemons recuperada correctamente");
            
        }catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
        return lista;
    }
}
```
## Escritura binaria con bases de datos RandomAccessFile

```
package tercerTrimestre;

import java.io.RandomAccessFile;
import java.util.HashMap;

public class Ficheros4 {

	// Usar variables globales está mal visto y hay que limitarlo lo mas posible
	// Usar constantes globales, sin embargo, resulta muy útil.
	// En este caso definimos aquí los tamaños de los datos que vamos a usar de forma
	// que podamos cambiarlo en un único punto si lo necesitamos
	static final int TAMANYO_NOMBRE = 20; // caracteres. un caracter ocupa 2 bytes
	static final int TAMANYO_EDAD = 4; // bytes. un entero ocupa 4 bytes
	static final int TAMANYO_REGISTRO = TAMANYO_NOMBRE *2 + TAMANYO_EDAD;
	
	public static void main(String[] args) {
		// El acceso aleatorio a un fichero nos permite dirigirnos directamente a un dato concreto sin necesidad
		// de pasar por los demas. Lo mas importante para ello es que los "registros" de datos tengan un
		// tamaño fijo.

		// Vamos a crear una pequeña agenda en un fichero. Los registros se componen de nombre y edad
		// Cargamos los datos en un diccionario inicialmente
		String fichero = "registros.dat";
		HashMap<String, Integer> agenda = new HashMap<>();
		agenda.put("Isabel", 35);
		agenda.put("Marcos", 51);
		agenda.put("José María", 57);
		agenda.put("Luis", 23);
		
		try {
			// creamos el fichero
			crearRegistro(fichero, agenda);
			// leemos el registro número 2
			leerRegistro(fichero,2);
			// modificamos el contenido del registro número 2
			modificarRegistro(fichero, 2, "José Miguel", 56);
			// intentamos modificar un registro que no existe
			modificarRegistro(fichero, 200, "Luis Miguel", 31);
			leerRegistro(fichero,2);
			// intentamos leer un registro que no existe
			leerRegistro(fichero,500);
			// leemos todos los registros
			leerTodosLosRegistros(fichero);
			// Añadimos un registro nuevo
			//anyadeRegistro(fichero,"Armando", 35);
			leerRegistro(fichero,5);
			// Borramos el registro 3
			borrarRegistro(fichero,3);
			// tratamos de borrar, leer o modificar un registro marcado como borrado
			borrarRegistro(fichero,3);
			leerRegistro(fichero,3);
			modificarRegistro(fichero, 3, "José Miguel", 56);
			leerTodosLosRegistros(fichero);
			
		}catch(Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}
	
	// otra forma de tratar las excepciones: se capturan todas desde el programa principal
	// en los métodos añadimos la clausula throws Exception (o el nombre de la excepción concreta)
	// cuando se produzca esa excepción el método deja de ejecutarse y vuelve al programa principal
	// y se ejecuta allí el bloque catch
	public static void crearRegistro (String fichero, HashMap<String, Integer> agenda) throws Exception{
		// el modo rw permite leer y escribir y crea el fichero si no existe. No existe modo w como en los ficheros de texto
		// los modos rws y rwd son similares pero escriben directamente a disco y no a cache.
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {

			for (String nombre : agenda.keySet()) {
                escribirNombre(raf, nombre);
                raf.writeInt(agenda.get(nombre));  // Los enteros se graban con 4 bytes
            }      
            System.out.println("Archivo creado con " + agenda.size() + " registros");
            // length nos devuelve el tamaño en bytes del archivo. Nos va a ayudar a ver que todo vaya bien
            System.out.println("Tamaño total del archivo: " + raf.length() + " bytes");
        }
	}
	
	// Método para escribir un nombre con tamaño fijo
    private static void escribirNombre(RandomAccessFile raf, String nombre) throws Exception {
    	// Crear un array de caracteres del tamaño fijo
        char[] chars = new char[TAMANYO_NOMBRE];     
        // Llenar con espacios o con el nombre
        for (int i = 0; i < TAMANYO_NOMBRE; i++) {
            if (i < nombre.length()) {
                chars[i] = nombre.charAt(i);
            } else {
                chars[i] = ' ';  // Rellenar con espacios
            }
        }
        // Escribir cada caracter como char (2 bytes cada uno)
        for (char c : chars) {
            raf.writeChar(c);
        }
    }
    
    private static void leerRegistro(String fichero, int registro) throws Exception {
    	// el modo r es de solo lectura. provoca excepción si el fichero no existe
    	try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
            // Calcular la posicion donde empezamos a leer
    		// El registro 1 es el primero (y empieza en la posición 0)
            long offset = (registro-1) * TAMANYO_REGISTRO;
            if(offset >=raf.length()) {
            	System.out.println("No existe el registro " + registro);
            	System.out.println("El registro mas alto es el " + raf.length()/TAMANYO_REGISTRO);
            }
            else {
            	// seek nos permite posicionarnos en un punto del fichero
            	// offset es un número de bytes a partir del principio del fichero
            	raf.seek(offset);
            	String nombre = leerNombre(raf);
            	if(nombre.charAt(0)=='*')
            		System.out.println("El registro " + registro + " está marcado para ser eliminado");
            	else {
            		int edad = raf.readInt();
            		System.out.printf("Registro %d: '%s', %d años%n", registro, nombre, edad);
            	}
            }
        }
    }
    
    private static String leerNombre(RandomAccessFile raf) throws Exception {
        String nombre ="";
        // Leer cada caracter
        for (int i = 0; i < TAMANYO_NOMBRE; i++) {
            char c = raf.readChar();
            nombre = nombre + c;
        }
        return nombre.trim();  // trim() para eliminar los espacios en blanco
    }
    
    private static void modificarRegistro(String fichero, int registro, String nombreNuevo, int edadNueva) throws Exception{
    	try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            long offset = (registro-1) * TAMANYO_REGISTRO;
            if(offset>=raf.length()) {
            	System.out.println("No existe el registro " + registro);
            	System.out.println("El registro mas alto es el " + raf.length()/TAMANYO_REGISTRO);
            }
            else {
            	raf.seek(offset);
            	String nombre = leerNombre(raf);
            	if(nombre.charAt(0)!='*') {
                	raf.seek(offset);
                	escribirNombre(raf, nombreNuevo);
                	raf.writeInt(edadNueva);
                	System.out.println("Registro " + registro + " modificado");
            	}
            	else
            		System.out.println("El registro " + registro + " no puede ser modificado porque está marcado para ser eliminado");
            }
        }
    }
    
    private static void leerTodosLosRegistros(String fichero) throws Exception {
    	try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
    		// calculamos el número de registros a partir del tamaño del fichero
    		int numRegistros = (int)raf.length()/TAMANYO_REGISTRO;
    		for(int i=0; i< numRegistros; i++) {
    			String nombre = leerNombre(raf);
   				int edad = raf.readInt();
   				if(nombre.charAt(0)!='*')
   					System.out.printf("Registro %d: '%s', %d años%n", i+1, nombre, edad);
    		}
        }
    }
    
    private static void anyadeRegistro(String fichero, String nombre, int edad) throws Exception{
    	try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            raf.seek(raf.length());
            escribirNombre(raf, nombre);
            raf.writeInt(edad);
            System.out.println("Registro añadido");
        }
    }    
    
    private static void borrarRegistro(String fichero, int registro) throws Exception {
    	try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
            long offset = (registro-1) * TAMANYO_REGISTRO;
            if(offset >=raf.length()) {
            	System.out.println("No existe el registro " + registro);
            	System.out.println("El registro mas alto es el " + raf.length()/TAMANYO_REGISTRO);
            }
            else {
            	raf.seek(offset);
            	String nombre = leerNombre(raf);
            	// usaremos un * para reemplazar el primer caracter como marca de borrado
           		if (nombre.charAt(0) == '*')
           			System.out.println("El registro " + registro + " ya había sido borrado");
           		else {
           			nombre = '*' + nombre.substring(1);
           			raf.seek(offset);
           			escribirNombre(raf,nombre);
           			System.out.println("Registro " + registro + " eliminado con éxito");
           		}
           		
            }
        }
    }
}
```

### Consejillos y demás cosas para la creación de ejercicios
- Para saber si un fichero existe(en el caso de que solo hagamos lectura) puedes usar esta función:
```
	public static boolean existeElFichero(String fichero) {
		File f = new File(fichero);
		return (f.exists());
	}
```


### Ejemplillos 
```
package ejemplos;

import java.io.RandomAccessFile;

public class Porra {

	static final int NUM_BOLETOS = 100;
	static final int TAMANYO_NOMBRE = 30;
	static final int TAMANYO_REGISTRO = TAMANYO_NOMBRE * 2;

	// EL método utilizado es muy similar al que hemos visto en clase
	// de hecho, puedes observar que muchos de los métodos son corta-pegas con
	// ligeras modificaciones

	public static void main(String[] args) {
		String fichero = "porra.dat";
		try {
			crearFichero(fichero);
			apuestaPorNumero(5, "Pepe Morón", fichero);
			apuestaPorNumero(4, "Rocío López", fichero);
			apuestaPorNumero(10, "Javier Ruíz", fichero);
			apuestaPorNumero(1, "Antonio Barbas", fichero);
			apuestaPorNumero(9, "Andrea", fichero);
			apuestaPorNumero(4, "Paquita", fichero);
			apuestaPorNumero(3, "Jordan", fichero);
			apuestaPorNumero(6, "Andrés", fichero);
			apuestaPorNumero(8, "Sara", fichero);
			apuestaPorNumero(2, "Martín", fichero);
			apuestaPorNumero(7, "Lucía", fichero);
			listarParticipantes(fichero);
			// Dos estrategias a la hora de hacer el sorteo
			// La 1, si no están apostados todos los números no se hace
			hacerSorteo1(fichero);
			// La 2, si sale un número por el que no ha apostado nadie se repite el sorteo
			hacerSorteo2(fichero);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
	}

	public static void crearFichero(String fichero) throws Exception {
		// Creamos el fichero con tantas entradas vacías como indique NUM_BOLETOS
		// Usamos * para indicar que ese número aún no tiene ninguna apuesta
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
			for (int i = 0; i < NUM_BOLETOS; i++) {
				escribirNombre(raf, "*");
			}
			System.out.println("Archivo creado con " + NUM_BOLETOS + " posibles boletos");
		}
	}

	private static void escribirNombre(RandomAccessFile raf, String nombre) throws Exception {
		// Crea un array de caractere del tamaño fijo
		// Es exactamente el mismo método del ejercicio de clase
		char[] chars = new char[TAMANYO_NOMBRE];
		// Llenar con espacios o con el nombre
		for (int i = 0; i < TAMANYO_NOMBRE; i++)
			if (i < nombre.length())
				chars[i] = nombre.charAt(i);
			else
				chars[i] = ' '; // Rellenar con espacios
		// Escribir cada caracter como char (2 bytes cada uno)
		for (char c : chars)
			raf.writeChar(c);
	}

	public static void apuestaPorNumero(int numero, String nombre, String fichero) throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "rw")) {
			if (numero > NUM_BOLETOS)
				System.out.println("El mayor número por el que es posible apostar es el " + NUM_BOLETOS);
			else {
				// Al igual que hacíamos en clase, buscamos el registro indicado y leemos
				long offset = (numero - 1) * TAMANYO_REGISTRO;
				raf.seek(offset);
				String nombreEnFichero = leerNombre(raf);
				// Ahora, si hay un asterisco es que nadie ha apostado: retrocedemos y escribimos el nombre
				if (nombreEnFichero.charAt(0) == '*') {
					raf.seek(offset);
					escribirNombre(raf, nombre);
					System.out.println("Apuesta de " + nombre + " al número " + numero + " registrada correctamente");
				} else
					System.out.println(nombreEnFichero + " ya ha apostado por el número " + numero
							+ ". Elige otro número " + nombre);
			}
		}
	}

	private static String leerNombre(RandomAccessFile raf) throws Exception {
		String nombre = "";
		// Exactamente el mismo método del ejercicio de clase
		for (int i = 0; i < TAMANYO_NOMBRE; i++) {
			char c = raf.readChar();
			nombre = nombre + c;
		}
		return nombre.trim();
	}

	public static void listarParticipantes(String fichero) throws Exception {
		System.out.println("LISTA DE PARTICIPANTES EN LA PORRA:");
		int contador = 0;
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
			// Recorremos el fichero completamente pero solo listamos las entradas donde no hay asterisco
			for (int i = 0; i < NUM_BOLETOS; i++) {
				String nombre = leerNombre(raf);
				if (nombre.charAt(0) != '*') {
					System.out.printf("Número: %d: - Participante: %s\n", i + 1, nombre);
					contador++;
				}
			}
		}
		// Usamos un contador para distinguir los casos especiales: que nadie haya apostado o que el sorteo esté completo
		// En caso contrario indicamos cuantas apuestas faltan. Podríamos ampliarlo diciendo los números por los que no ha apostado 
		// nadie aún ¿te animas a hacerlo?
		if (contador == 0)
			System.out.println("Aún no ha participado nadie en la porra! Anímate!");
		else if (contador == NUM_BOLETOS)
			System.out.println("La porra está completa. Es hora de realizar el sorteo!");
		else
			System.out.printf("Faltan %d números por los que apostar para poder realizar el sorteo\n",
					NUM_BOLETOS - contador);
	}

	public static void hacerSorteo1(String fichero) throws Exception {
		int contador = 0;
		// Primer método. Tenemos que ver antes que estén todos los números.
		// Lo hacemos con un contador como en el método anterior
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
			for (int i = 0; i < NUM_BOLETOS; i++) {
				String nombre = leerNombre(raf);
				if (nombre.charAt(0) != '*')
					contador++;
			}
			// Si no están todas las apuestas no hacemos el sorteo
			if (contador != NUM_BOLETOS)
				System.out.printf(
						"El sorteo no puede realizarse aún. Hay %d números por los que nadie ha apostado aún\n",
						NUM_BOLETOS - contador);
			else {
				// En caso contrario, elegimos un número al azar y vemos quien ha ganado
				int numGanador = (int) (Math.random() * NUM_BOLETOS) + 1;
				long offset = (numGanador - 1) * TAMANYO_REGISTRO;
				raf.seek(offset);
				String ganador = leerNombre(raf);
				System.out.printf("El ganador es el número %d y la persona afortunada es %s!\n", numGanador, ganador);
			}
		}
	}

	public static void hacerSorteo2(String fichero) throws Exception {
		try (RandomAccessFile raf = new RandomAccessFile(fichero, "r")) {
			String ganador = null;
			int numGanador = 0;
			// En este caso, vamos a elegir números aleatorios hasta que encontremos un ganador
			// La condición de salida es que el registro elegido al azar tenga un apostador válido (no sea *)
			do {
				numGanador = (int) (Math.random() * NUM_BOLETOS) + 1;
				long offset = (numGanador - 1) * TAMANYO_REGISTRO;
				raf.seek(offset);
				ganador = leerNombre(raf);
			}while(ganador.equals("*"));
			System.out.printf("El ganador es el número %d y la persona afortunada es %s!\n", numGanador, ganador);
		}
	}
}
```