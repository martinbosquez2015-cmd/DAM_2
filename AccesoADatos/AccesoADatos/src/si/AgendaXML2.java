package si;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;


import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.Element;

public class AgendaXML2 {

	public static void main(String[] args) {
		
		String fichero = "EjerciciosDatos/XML/agenda.xml";
		try {
		Document doc = leerXML(fichero);
		NodeList listaContactos = doc.getElementsByTagName("contacto");
		System.out.println("Contactos: "+ listaContactos.getLength());
		for(int i=0; i<listaContactos.getLength(); i++) {
			Element contacto = (Element) listaContactos.item(i);
			String nombre = contacto.getElementsByTagName("nombre").item(0).getTextContent();
			System.out.println(nombre);
			NodeList telefonos = contacto.getElementsByTagName("telefono");
			for(int j=0; j<telefonos.getLength(); j++) {
				Element telefono = (Element) telefonos.item(j);
				String tipo = telefono.getAttribute("tipo");
				String tlf = telefono.getTextContent();
				System.out.println(" - "+ tlf+"("+tipo+")");
			}
		}
				}catch (Exception e) {
					System.out.println("Error");
				}

	}

	public static Document leerXML(String fichero) throws Exception{
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(fichero);
	}

}
