package PrimerTri;

import java.util.Scanner;
import java.io.IOException;
public class Ejercicio_01_Navegador {
// Abrir un navegador alv
	public static void main(String[] args) throws IOException{
		/*Primero Creamos la clase Scanner para qu el usauario pueda
		 * introducir por teclado la web a la que 
		 * quiere dirigirse.
		 *
		 * */
		 Scanner teclado = new Scanner(System.in);
		 
		 System.out.println("Por favor, introduce el nombre de la web a la que quieras entrar");
		 String link = teclado.nextLine();
		 
		 // OJO que aquí viene lo importante
		 ProcessBuilder pb = new ProcessBuilder("firefox", "https://"+link+".com");
		 // En los apuntes este ejercicio se pedía con "google-chrome"
		 pb.start();
		 System.out.println("Abriendo "+ link+ " en Google Chrome...");
		 teclado.close();

	}

}
