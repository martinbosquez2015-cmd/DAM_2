package Examen1Trimestre1;

import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class Ej01_Main {
	public static void main(String[] args) throws Exception {
		String ficheroXML = "pokedex.xml";
		String ficheroTXT = "pokedex.txt";
		Document doc = leerPokedeXML(ficheroXML);
		grabarPokedexDeOakEnFichero(doc, ficheroTXT);
	}

	public static Document leerPokedeXML(String fichero) throws Exception {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		return builder.parse(fichero);
	}

	public static void grabarPokedexDeOakEnFichero(Document doc, String ficheroTXT) throws Exception {
		try (PrintWriter escritor = new PrintWriter(ficheroTXT, StandardCharsets.UTF_8)) {
			escritor.println("=== POKEDEX DEL PROFESOR OAK ===");
			NodeList listaPokemon = doc.getElementsByTagName("pokemon");
			int contadorPokemon = 0;
			for (int i = 0; i < listaPokemon.getLength(); i++) {
				contadorPokemon++;
				Element pokemon = (Element) listaPokemon.item(i);
				int id = Integer.parseInt(pokemon.getAttribute("id"));
				int nivel = Integer.parseInt(pokemon.getAttribute("nivel"));
				String nombre = pokemon.getElementsByTagName("nombre").item(0).getTextContent();

				NodeList tipos = pokemon.getElementsByTagName("tipo");
				String tipStr = "";
				for (int j = 0; j < tipos.getLength(); j++) {
					Element tipo = (Element) tipos.item(j);
					String tip = tipo.getTextContent();
					tipStr += tip;
					if (j < tipos.getLength() - 1)
						tipStr += "/";
				}

				escritor.printf("#%03d %s | %S | Nivel %d\n", id, nombre, tipStr, nivel);

			}
			escritor.printf("=== Total: %d Pokemon ===", contadorPokemon);
			System.out.printf("Pokemon procesados: %d", contadorPokemon);

		}
	}
}
