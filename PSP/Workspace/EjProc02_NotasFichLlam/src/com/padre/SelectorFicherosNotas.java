package com.padre;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

public class SelectorFicherosNotas {

	public static void main(String[] args) throws IOException, InterruptedException {
		// Este scanner servirá tanto comoe entrada de tecladoa como para recibir los
		// syso del programa que iniciaremos
		Scanner sc = new Scanner(System.in);
		boolean ciclo = true;
		String entradaFinalizadora = "*";
		while (ciclo) {
			System.out.println("LectorDeNotasPSPFicheroInador versión 1.0");
			System.out.println("Escribe el nombre de la asignatura:");
			String asignatura = sc.nextLine();
			System.out.println("Escribe ahora el nombre del fichero:");
			String fichero = sc.nextLine();
			if (asignatura.equals(entradaFinalizadora) || fichero.equals(entradaFinalizadora)) {
				ciclo = false;
			} else {
				//En el caso de que no se haya tecleado "*" entonces se procede a iniciar el proceso y enviarle los argumentos que queremos
				File directorio = new File("../EjProc02_NotasFich/bin");
				ProcessBuilder pb = new ProcessBuilder("java", "com.hijo.Notas", asignatura, fichero);
				pb.directory(directorio);
				Process p = pb.start();
				// esperamos el codigo de salida
				int salida = p.waitFor();
				// verificación de salida
				switch (salida) {
				// Caso de que no sea un entero
				case 256 - 2:
					System.out.println("Mi llave, alguna de las notas dentro del fichero no es un numero.");
					break;
				case 256 - 1:
					System.out.println("Mano, hubo un problema al leer el fichero.");
					break;
				case 256 - 3:
					System.out.println(
							"Alguna de las notas dentro del fichero es negativa, mano que te pasa?, como vas a poner notas negativas a tus estudiantes?!.");
					break;
				case 0:
					System.out.println("Todo bien mano.");
					break;
				default:
					System.out.println("QUÈ?!!!\nESTE ERROR NO DEBERÍA PASAR");
				}
				InputStream is = p.getInputStream();
				sc = new Scanner(is);
				String impresion = sc.nextLine();
				System.out.println(impresion);
			}
		}
		sc.close();
		System.out.printf("Programa terminado, se ha intdroducido la entrada %s", entradaFinalizadora);

	}

}
