package com.padre;

import java.io.File;
import java.util.Scanner;

public class EjAnteriorP {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Escribe un número entero positivo: ");
		String numero = sc.nextLine();
		File directorio = new File("../EjProc02_IntNumFich/bin");
		ProcessBuilder pb = new ProcessBuilder("java","com.hijo.EjAnterior");
		pb.directory(directorio);
	    //se ejecuta el proceso
		Process p = pb.start();

		int salida = p.waitFor();
		switch (salida) {
		// Caso de que no sea un entero
		case 256 - 2:
			System.out.println("No has escrito un entero.");
			break;
		case 256 - 1:
			System.out.println("El argumento está vacio weon.");
			break;
		case 256 - 3:
			System.out.println("Has escrito un número entero positivo!");
			break;
		case 0:
			System.out.println("El entero debe de ser positivo.");
			break;
		default:
			System.out.println("Has escrito el 0");

		}

		sc.close();
	}

}