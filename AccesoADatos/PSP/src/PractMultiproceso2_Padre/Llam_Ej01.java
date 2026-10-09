package PractMultiproceso2_Padre;

import java.util.Scanner;

public class Llam_Ej01 {

	public static void main(String[] args) throws Exception {
		Scanner sc = new Scanner(System.in);
		System.out.println("Escribe un número entero positivo: ");
		String numero = sc.nextLine();

		ProcessBuilder pb = new ProcessBuilder("java", "-cp", "bin", "PractMultiproceso2_Hijo.Ej01", numero);
		Process proceso = pb.start();

		int salida = proceso.waitFor();
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
