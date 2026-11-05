package PractMultiproceso2_Hijo;

import java.util.Scanner;

public class Ej02 {

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		double suma = 0.0;
		boolean procesando = true;// con esto evitamos el break

		while (procesando && sc.hasNextLine()) {
			String linea = sc.nextLine().trim();

			if (linea.equals("*")) {
				procesando = false;
			} else {
				try {
					double numero = Double.parseDouble(linea);
					suma += numero;
				} catch (NumberFormatException e) {
					// al encontrar una cadena que no es válida, sale con error -1, segun la prćtica
					sc.close();
					System.exit(-1);
				}

			}
		}
		sc.close();
		System.out.println("Suma: "+ suma);;
		System.exit(0);

	}

}
