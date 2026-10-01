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