package PractMultiproceso2_Hijo;

import java.util.Scanner;

public class Ej02 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		double suma = 0;
		String linea = "";
		while(sc.hasNextLine() && linea.equals("*") == false) {
			linea = sc.nextLine().trim();
			try {
				double numero = Double.parseDouble(linea);
				suma += numero;
			}
		}
		
		

	}

}
