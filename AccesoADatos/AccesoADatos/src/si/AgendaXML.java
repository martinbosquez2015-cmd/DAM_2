package si;


import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.*;

//import org.w3c.dom.*;

public class AgendaXML {

	public static void main(String[] args) throws Exception{
		leerAgenda("EjerciciosDatos/XML/xml.xml");
		buscarEnAgenda("Elena", "EjerciciosDatos/XML/xml.xml");
		buscarEnAgenda("Pepe", "EjerciciosDatos/XML/xml.xml");
		eliminarContacto("Elena", "EjerciciosDatos/XML/xml.xml");
		eliminarContacto("Pepe", "EjerciciosDatos/XML/xml.xml");

	}
	public static void leerAgenda(String fichero) throws Exception{
		// Leemos el XML y lo almacenamos en el objeto doc
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);
		
		// creamos una lista iterable
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		
		// recorremos la lista de contactos
		for(int i=0; i<listaContactos.getLength(); i++) {
			// Cojo 7u7 el elemento i y lo guardo en el objeto contacto
			Node nodo = listaContactos.item(i);
			Element contacto = (Element) nodo;
			
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();
			
			System.out.println(nombre + " - " + telefono);
			
		}
		
	}

	public static void buscarEnAgenda(String nombreBuscar, String fichero) throws Exception{
		// Leemos el XML y lo almacenamos en el objeto doc
				DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
				DocumentBuilder builder = factory.newDocumentBuilder();
				Document doc = builder.parse(fichero);
				
				// Creamos una lista iterable
				NodeList listaContactos = doc.getElementsByTagName("contacto");
				//Recorremos la lista de contactos
				boolean contactoEncontrado = false;
				for(int i = 0; i <listaContactos.getLength() && contactoEncontrado == false; i++) {
					
					/*Node nodo = listaContactos.item(i);
					Element contacto = (Element)nodo;*/
					Element contacto = (Element)listaContactos.item(i);
					
					String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
					if(nombre.equals(nombreBuscar)) {
						
						String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();
						System.out.println();
						System.out.println("- Usuario encontrado en agenda con éxito");
						System.out.println("Nombre: "+nombre+" Telefono: "+telefono);
						contactoEncontrado = true;
				}

			}
			if(contactoEncontrado == false) {
				System.out.println();
				System.out.println("No tienes ese contacto en la agenda");
			}

}
	
	public static void buscarEnAgendaClase(String nombreBuscado, String fichero) throws Exception{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);
		
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		boolean encontrado = false;
		
		for(int i = 0; i <listaContactos.getLength() && encontrado == false; i++) {
			
			Element contacto = (Element)listaContactos.item(i);
			
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			if(nombre.equalsIgnoreCase(nombreBuscado)) {
				encontrado = true;
				String telefono = contacto.getElementsByTagName("telefono").item(0).getTextContent();
				System.out.println("El telefono de "+nombre + " es "+telefono);
			}
		}
		if(encontrado == false) {
			System.out.println("El contacto"+ nombreBuscado+ " no esta en tu agenda");
		}
		
		
		
	}
	
	
	public static void eliminarContacto(String nombreBuscado, String fichero) throws Exception{
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);
		
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		boolean encontrado = false;
		
		for(int i = 0; i <listaContactos.getLength() && encontrado == false; i++) {
			
			Element contacto = (Element)listaContactos.item(i);
			
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			if(nombre.equalsIgnoreCase(nombreBuscado)) {
				encontrado = true;
				Element raiz = doc.getDocumentElement();
				raiz.removeChild(contacto);
				System.out.println("El contacto "+nombre+" ha sido eliminado");
				
				
				// Guardar XML 
				TransformerFactory transformerFactory = TransformerFactory.newInstance();
				Transformer transformer = transformerFactory.newTransformer();
				transformer.setOutputProperty(OutputKeys.INDENT, "yes");
				transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "4");
				DOMSource source = new DOMSource(doc);
				StreamResult result = new StreamResult(fichero);
				transformer.transform(source, result);
	
			}
		}
		if(encontrado == false) {
			System.out.println("El contacto "+ nombreBuscado+ " no se puede eliminar porque no existe");
		}
		
	}
	public static void nuevoContacto(String nombreNuevo, String telefonoNuevo, String fichero)throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);
		
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		boolean encontrado = false;
		
		for(int i = 0; i <listaContactos.getLength() && encontrado == false; i++) {
			Node nodo= listaContactos.item(i);
			Element contacto = (Element)nodo;
			
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			if(nombre.equalsIgnoreCase(nombreNuevo)) {
				encontrado = true;
				
				
				
				
				
			}
		}
		if(encontrado == true) {
			System.out.println("Ya existe un contacto llamado "+ nombreNuevo);
		}
		else {
			Element nuevoContacto = doc.createElement("contacto");
			Element elementoNombre = doc.createElement("nombre");
			Element elementoTelefono = doc.createElement("telefono");
			elementoNombre.setTextContent(nombreNuevo);
			elementoTelefono.setTextContent(telefonoNuevo);
			nuevoContacto.appendChild(elementoNombre);
			nuevoContacto.appendChild(elementoTelefono);
			Element raiz = doc.getDocumentElement();
			raiz.appendChild(nuevoContacto);
			grabarXML(doc,fichero);
			System.out.println("contato creado exitosamente");
		}
	}
	public static void modificarTelefono(String nombreBuscado, String telefonoNuevo, String fichero)throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(fichero);
		
		NodeList listaContactos = doc.getElementsByTagName("contacto");
	boolean encontrado = false;
		
		for(int i = 0; i <listaContactos.getLength() && encontrado == false; i++) {
			Node nodo= listaContactos.item(i);
			Element contacto = (Element)nodo;
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			
			if(nombre.equalsIgnoreCase(nombreBuscado)) {
				encontrado = true;
				Element telefono = (Element)contacto.getElementsByTagName("telefono").item(0);
				telefono.setTextContent(nuevoTelefono);
				grabarXML();
				System.out.println("Teléfono modificado en el contacto "+ nombreBuscado);
				
				
				// como se soluciona la funcion de búsqueda para que suene más bonito
				
			}
		}
		if(encontrado == true) {
			System.out.println("Ya existe un contacto llamado "+ nombreNuevo);
		}
		else {
			Element nuevoContacto = doc.createElement("contacto");
			Element elementoNombre = doc.createElement("nombre");
			Element elementoTelefono = doc.createElement("telefono");
			elementoNombre.setTextContent(nombreNuevo);
			elementoTelefono.setTextContent(telefonoNuevo);
			nuevoContacto.appendChild(elementoNombre);
			nuevoContacto.appendChild(elementoTelefono);
			Element raiz = doc.getDocumentElement();
			raiz.appendChild(nuevoContacto);
			grabarXML(doc,fichero);
			System.out.println("contato creado exitosamente");
		}
	}
}


