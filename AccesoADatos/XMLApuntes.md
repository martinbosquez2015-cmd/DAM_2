## JSON

### Agenda JSON

JSON es un formato que permite almacenar información estructurada mediante pares de clave y valor. En este ejemplo vamos a crear una agenda de contactos que se guarda en un fichero `agenda.json`.

Para trabajar con JSON en Java utilizamos la librería **Gson**, que permite convertir objetos Java en JSON y viceversa.

En este ejercicio vamos a tener tres clases:
- `AgendaJSON`: contiene los métodos para leer, buscar, crear, modificar y eliminar contactos.
- `Contacto`: representa cada contacto de la agenda.
- `Agenda`: representa el elemento raíz del JSON y contiene la lista de contactos.

### 1. Estructura del fichero JSON

El fichero `agenda.json` contiene una clave llamada `agenda`, cuyo valor es una lista de contactos. Cada contacto tiene un nombre, un teléfono y un DNI.

```json
{
    "agenda": [
        {
            "nombre": "Juan Perez",
            "telefono": "600111222",
            "dni": "28777666X"
        },
        {
            "nombre": "Maria Lopez",
            "telefono": "600333444",
            "dni": "77766644B"
        },
        {
            "nombre": "Pepe Pótamo",
            "telefono": "655311052",
            "dni": "44666555R"
        }
    ]
}
```

- Las claves (`nombre`, `telefono`, `dni`) identifican los datos de cada contacto.
- Los valores son los datos que almacenamos.
- Los corchetes `[]` representan una lista de elementos.
- Las llaves `{}` representan un objeto.
- La clave `agenda` contiene la lista completa de contactos.

### 2. Clase Contacto

Necesitamos una clase que represente cada contacto del JSON. Los nombres de los atributos deben coincidir con los nombres de las claves del JSON para que Gson pueda asignar los valores automáticamente.

```java
public class Contacto {
    private String nombre;
    private String telefono;
    private String dni;

    public Contacto(String n, String t, String d) {
        this.nombre = n;
        this.telefono = t;
        this.dni = d;
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String toString() {
        return "Nombre: " + this.nombre
            + "\nDNI: " + this.dni
            + "\nTeléfono: " + this.telefono + "\n";
    }
}
```

- Los atributos son privados para controlar el acceso a los datos.
- El constructor permite crear un contacto indicando su nombre, teléfono y DNI.
- `getNombre()` devuelve el nombre del contacto.
- `setTelefono()` modifica el teléfono.
- `toString()` devuelve una representación del contacto como texto para poder mostrarlo por pantalla.

### 3. Clase Agenda

Necesitamos otra clase que represente la raíz del JSON. Como el fichero tiene una clave llamada `agenda`, usamos la anotación `@SerializedName("agenda")` para indicar que esa clave corresponde al atributo `contactos`.

```java
import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Agenda {

    @SerializedName("agenda")
    private List<Contacto> contactos;

    public List<Contacto> getContactos() {
        return contactos;
    }

    public void setContactos(List<Contacto> contactos) {
        this.contactos = contactos;
    }
}
```

- `List<Contacto>` representa una lista de contactos.
- `@SerializedName("agenda")` relaciona el atributo `contactos` con la clave `agenda` del JSON.
- `getContactos()` devuelve la lista de contactos.
- `setContactos()` establece la lista de contactos.

Aunque en clase hayamos trabajado más con `ArrayList`, podemos usar `List` como tipo de referencia. Para este ejercicio no necesitamos hacer un cast para trabajar con la lista.

### 4. Imports necesarios

Para trabajar con Gson y leer o escribir ficheros necesitamos importar las siguientes clases:

```java
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.List;
```

La anotación `SerializedName` se importa en la clase `Agenda`:

```java
import com.google.gson.annotations.SerializedName;
```

**Importante:** también es necesario tener disponible la librería Gson en el proyecto para que estos imports funcionen.

### 5. Cargar la agenda

Este método lee el fichero JSON y convierte su contenido en objetos Java. Después devuelve la lista de contactos.

```java
public static List<Contacto> cargarAgenda(String rutaArchivo) {
    List<Contacto> contactos = null;

    try (Reader lector = new FileReader(rutaArchivo)) {
        Gson gson = new Gson();

        // Gson convierte el JSON en un objeto Agenda
        Agenda agenda = gson.fromJson(lector, Agenda.class);

        // Obtenemos la lista de contactos de la agenda
        contactos = agenda.getContactos();

    } catch (Exception e) {
        System.err.println("Error al leer el archivo: " + e.getMessage());
    }

    if (contactos == null)
        System.out.println("Error al leer los contactos de la agenda");

    return contactos;
}
```

- `FileReader` permite leer el fichero.
- `Reader` representa el flujo de lectura.
- `Gson` es el objeto que convierte JSON en objetos Java.
- `fromJson(lector, Agenda.class)` lee el JSON y construye un objeto `Agenda`.
- `getContactos()` obtiene la lista almacenada dentro de ese objeto.
- `try-with-resources` cierra automáticamente el lector al terminar.

Este método devuelve `null` si no ha conseguido obtener la lista de contactos, por lo que conviene comprobar el resultado antes de utilizarlo.

### 6. Guardar la agenda

Este método recibe una lista de contactos, la introduce en un objeto `Agenda` y guarda el resultado en el fichero JSON.

```java
private static void guardarAgenda(String rutaArchivo, List<Contacto> contactos) {
    Agenda agenda = new Agenda();
    agenda.setContactos(contactos);

    // GsonBuilder permite configurar cómo se genera el JSON
    // PrettyPrinting hace que el JSON sea más legible
    Gson gson = new GsonBuilder().setPrettyPrinting().create();

    try (Writer escritor = new FileWriter(rutaArchivo)) {
        // Convertimos el objeto Agenda en JSON y lo escribimos en el fichero
        gson.toJson(agenda, escritor);

    } catch (Exception e) {
        System.err.println("Error al guardar el archivo: " + e.getMessage());
    }
}
```

- `FileWriter` permite escribir en el fichero.
- `Writer` representa el flujo de escritura.
- `GsonBuilder` permite configurar el formato de salida.
- `setPrettyPrinting()` organiza el JSON con sangrías y saltos de línea para que sea más fácil de leer.
- `toJson(agenda, escritor)` convierte el objeto Java en JSON y lo escribe en el fichero.

**Ojo:** al utilizar `new FileWriter(rutaArchivo)` sin activar el modo de añadir, el contenido anterior del fichero se sustituye por el nuevo JSON. Por eso primero recuperamos la lista completa, hacemos los cambios y después guardamos la lista actualizada.

### 7. Buscar un contacto

Este método recorre la lista y busca un contacto por su nombre. Si lo encuentra, devuelve el objeto `Contacto`; si no, devuelve `null`.

```java
public static Contacto buscarContacto(List<Contacto> contactos, String nombreBuscado) {
    Contacto contacto = null;

    for (Contacto c : contactos) {
        if (c.getNombre().equalsIgnoreCase(nombreBuscado))
            contacto = c;
    }

    // Si el contacto no existe, devuelve null
    return contacto;
}
```

- `for (Contacto c : contactos)` recorre todos los contactos de la lista.
- `getNombre()` obtiene el nombre de cada contacto.
- `equalsIgnoreCase()` compara los nombres sin distinguir entre mayúsculas y minúsculas.
- Si no encuentra el contacto, la variable `contacto` conserva el valor `null`.

En este código no se detiene el bucle cuando encuentra una coincidencia. Si existen varios contactos con el mismo nombre, devolverá el último que encuentre.

### 8. Leer y mostrar la agenda

Este método utiliza `cargarAgenda()` para recuperar la lista y después muestra el número total de contactos y sus datos.

```java
public static void leerAgenda(String rutaArchivo) {
    List<Contacto> contactos = cargarAgenda(rutaArchivo);

    if (contactos != null) {
        System.out.println("AGENDA");

        // size() devuelve el número de contactos de la lista
        System.out.println("Total de contactos: " + contactos.size());

        for (Contacto c : contactos)
            System.out.println(c);
    }
}
```

- `size()` devuelve el número de elementos de una lista.
- `System.out.println(c)` utiliza automáticamente el método `toString()` de `Contacto`.
- Comprobamos que la lista no sea `null` antes de recorrerla.

### 9. Crear un contacto

Para crear un contacto primero cargamos la agenda y comprobamos si ya existe otro contacto con el mismo nombre. Si no existe, añadimos el contacto a la lista y guardamos los cambios.

```java
public static void crearContacto(String rutaArchivo, Contacto contacto) {
    List<Contacto> contactos = cargarAgenda(rutaArchivo);

    if (contactos != null) {
        if (buscarContacto(contactos, contacto.getNombre()) == null) {
            // Añadimos el nuevo contacto a la lista
            contactos.add(contacto);

            // Guardamos la lista actualizada
            guardarAgenda(rutaArchivo, contactos);

            System.out.println("Contacto creado");
        }
        else
            System.out.println("Ya existe un contacto con ese nombre");
    }
}
```

- `add(contacto)` añade el contacto al final de la lista.
- Antes de añadirlo, usamos `buscarContacto()` para evitar nombres duplicados.
- Después de modificar la lista, llamamos a `guardarAgenda()` para conservar los cambios.

### 10. Modificar el teléfono de un contacto

Para modificar un teléfono buscamos el contacto en la lista. Si existe, usamos `setTelefono()` para cambiar el número y guardamos la agenda.

```java
public static void modificarTelefono(String rutaArchivo, String nombreBuscado, String nuevoTelefono) {
    List<Contacto> contactos = cargarAgenda(rutaArchivo);

    if (contactos != null) {
        Contacto encontrado = buscarContacto(contactos, nombreBuscado);

        if (encontrado != null) {
            // Modificamos el teléfono del contacto encontrado
            encontrado.setTelefono(nuevoTelefono);

            // Guardamos los cambios en el JSON
            guardarAgenda(rutaArchivo, contactos);

            System.out.println("Teléfono actualizado");
        }
        else
            System.out.println("No existe ese contacto");
    }
}
```

- `buscarContacto()` devuelve el objeto que queremos modificar.
- `setTelefono()` cambia el valor del atributo `telefono`.
- Como el objeto pertenece a la lista, al modificarlo también estamos modificando el contenido de esa lista.
- Finalmente guardamos la lista completa en el fichero.

### 11. Borrar un contacto

Para eliminar un contacto lo buscamos y, si existe, lo quitamos de la lista mediante `remove()`. Después guardamos la agenda actualizada.

```java
public static void borrarContacto(String rutaArchivo, String nombreBuscado) {
    List<Contacto> contactos = cargarAgenda(rutaArchivo);

    if (contactos != null) {
        Contacto encontrado = buscarContacto(contactos, nombreBuscado);

        if (encontrado != null) {
            // Eliminamos el objeto de la lista
            contactos.remove(encontrado);

            // Guardamos los cambios en el fichero
            guardarAgenda(rutaArchivo, contactos);

            System.out.println("Contacto eliminado");
        }
        else
            System.out.println("No existe ese contacto");
    }
}
```

- `remove(encontrado)` elimina el objeto indicado de la lista.
- Si el contacto no existe, mostramos un mensaje y no modificamos la agenda.
- Es importante guardar el fichero después de eliminar el contacto para que el cambio sea permanente.

### 12. Método main

En el método principal podemos probar todas las operaciones de la agenda.

```java
public static void main(String[] args) {
    String rutaArchivo = "agenda.json";

    // Mostramos todos los contactos
    leerAgenda(rutaArchivo);

    // Creamos un objeto Contacto
    Contacto contacto = new Contacto("Maripili", "999888777", "11223344G");

    // Probamos las operaciones
    crearContacto(rutaArchivo, contacto);
    borrarContacto(rutaArchivo, "Maripili");
    modificarTelefono(rutaArchivo, "Leonor", "888777666");
}
```

Primero leemos la agenda, después creamos un contacto, lo añadimos, lo eliminamos y finalmente intentamos modificar el teléfono de Leonor.

Si Leonor no está en el fichero, el programa mostrará el mensaje `No existe ese contacto`.

### Resumen de los métodos

| Método | Función |
|---|---|
| `cargarAgenda()` | Lee el JSON y devuelve la lista de contactos. |
| `guardarAgenda()` | Convierte la agenda en JSON y la guarda en el fichero. |
| `buscarContacto()` | Busca un contacto por su nombre. |
| `leerAgenda()` | Muestra todos los contactos y el número total. |
| `crearContacto()` | Añade un contacto si no existe otro con ese nombre. |
| `modificarTelefono()` | Modifica el teléfono de un contacto existente. |
| `borrarContacto()` | Elimina un contacto existente. |

### Cosas importantes que recordar

- **JSON a Java:** usamos `gson.fromJson()` para convertir el fichero en objetos Java.
- **Java a JSON:** usamos `gson.toJson()` para guardar los objetos en el fichero.
- **Lista de contactos:** utilizamos `List<Contacto>` para trabajar con todos los contactos.
- **Objeto raíz:** la clase `Agenda` representa la estructura principal del JSON.
- **Anotación `@SerializedName`:** permite relacionar el nombre de una clave JSON con un atributo Java que tiene otro nombre.
- **Añadir:** usamos `add()`.
- **Eliminar:** usamos `remove()`.
- **Modificar:** usamos el método setter correspondiente, en este caso `setTelefono()`.
- **Persistencia:** después de cambiar la lista, llamamos a `guardarAgenda()` para que los cambios se mantengan en el fichero.

**Importante:** en el código original, `modificarTelefono()` llama a `buscarContacto()` sin comprobar antes si `contactos` es `null`. Si la lectura del fichero falla, podría producirse un `NullPointerException`. Es preferible comprobar que la lista se ha cargado correctamente antes de buscar, crear, modificar o borrar contactos.