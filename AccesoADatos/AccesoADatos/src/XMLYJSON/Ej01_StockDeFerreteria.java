package XMLYJSON;

import java.io.FileWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

public class Ej01_StockDeFerreteria {

	public static void main(String[] args) throws Exception{
		// El ejercicio pide que lo pasemos a JSON, pero tambien lo pasaremos a XML
		String ruta = "/home/alumno/DAM2/AccesoADatos/AccesoADatos/EjerciciosDatos/Ficheros_01/ferreteria.txt";
		List<String> listaProd = lectura(ruta);
		List<Ej01_producto> productos = transLecturaAObjeto(listaProd);
		//Fragmento para saber que todo está bien
		for(Ej01_producto e: productos) {
			System.out.println(e);
		}
		guardarDato(ruta, productos);
		
		

	}
	
	public static List<String> lectura(String ruta) throws Exception{
		return Files.readAllLines(Path.of(ruta));
	}
	public static List<Ej01_producto> transLecturaAObjeto(List<String> listaProd){
		int contador = 0;
		List<Ej01_producto> productos = new ArrayList<>();
		for (int i = 0; i<listaProd.size(); i+=3) {
			String cod = listaProd.get(i);
			String descr = listaProd.get(i+1);
			String stock =  listaProd.get(i+2);
			Ej01_producto prod1 = new Ej01_producto(cod, descr, stock);
			productos.add(prod1);
		}
		return productos;
		
	}
	
	public static void guardarDato(String ruta, List<Ej01_producto> productos) {
		Ej01_ferreteria ferreteria = new Ej01_ferreteria();
		ferreteria.setProductos(productos);
		Gson gson = new GsonBuilder().setPrettyPrinting().create();
		try(Writer escritor = new FileWriter(ruta)){
			gson.toJson(ferreteria, escritor);
			
		}catch(Exception e) {
			System.err.println("Error al guardar el archivo: "+ e.getMessage());
		}
	}

}
