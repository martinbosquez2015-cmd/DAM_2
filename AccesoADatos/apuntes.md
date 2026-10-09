# APUNTES DE ACCESO A DATOS
## PRIMER TRIMESTRE
### 







### __Aquí te doy un trazo de cómo es la clase del 1 de octubre__

```
public static void crearContacto(Contacto nuevo, String ruta){
    List<Contacto> contactos = cargarListaContactos(ruta);
    if(contactos! = null){
        if(buscarContacto(contactos,nuevo.getNombre()) == null){
            contactos
            System.out.println("Contacto grabado");
        }
        else
            System.out.println("Ya existe un contacto con ese nombre");
        }
    }
    ....
    }


public static Contacto buscarContacto(List<Contacto> contactos, Contacto buscado){
    Contacto contacto = null;
    /*
    for(Contacto c: contacto)
        if(c.getNombre().equalsIgnoreCase(buscando.getNombre())){
            contacto = c;
            break;
        }
    return contacto;
    */
    //En esta primara forma, el Chema acepta un break, pero hay otra que podríamos utilizar

    for(int i=0; 0<contactos.size() && contacto ==null ; i++)
        if(contactos.get(i).getNombre.equalsIgnoreCase(buscado.getNombre()))
            contacto = contactos.get(i);
    return contacto

    }


public static void borrarContacto(STring nombre, String ruta){
    List<Contacto> contactos = cargarListaContactos(ruta);
    if(contactos!=null){
        Contacto encontrado = buscarContacto(contactos, nombre);
        if( encontrado != null){
            contactos.remove(encontrado);
            guardarAgenda(contactos, ruta);
            System.out.println("Contacto eliminado");
        }
        else
            System.out.println("No existe un contacto con ese nombre")
    }
}


public static void modificarTelefono(String nombre, String telefono, String ruta){
    List<Contacto> contactos = cargarLista
}



```



### __Mano, te doy una pequeña guia de como instalar el docker y meterle el pipi de mysql para generar la conexion__
1. Primero seguimos la guia de instalación en el auyla virtual para ponerle el jar de mysl a eclipse
2. luego, desde la terminal ponemos los siguientes códigos:

```
docker run --name mysql -p 3306:3306 -e MYSQL_ROOT_PASSWORD=abc123 -d mysql

```

algunos codiguillos para abrir los dockers de mysql
```
docker ps
docker star mysql
docker stop mysql
docker exec -it mysql bash

```
luego con eso entramos al docker, desde ahí hay algunos codigos para crear usuarios dentro del mysql
```
create user perroflauta@localhost identified by "abc123";
grant all on *.* to perroflauta@localhost;
flush privileges;
```
pero nos daba un error con el java, que esta asi
```
package jdbcMcHunnigan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainJ {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost/";
		String usuario = "perroflauta";
		String password = "abc123";
		
		try {
			Connection conn = DriverManager.getConnection(url, usuario, password);
			System.out.println("Conexión exitosa weon");
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

}

```
así que creamos a un usuario con la ip que nos daba error en java
```
create user perroflauta@172.17.0.1 identified by "abc123";
grant all on *.* to perroflauta@172.17.0.1;
flush privileges;
```
