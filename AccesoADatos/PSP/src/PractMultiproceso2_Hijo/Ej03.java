package PractMultiproceso2_Hijo;

public class Ej03 {

	public static void main(String[] args) {
		//Scanner para leer lo que el proceso padre le manda
		Scanner sc = new Scanner(System.in);
		if (sc.hasNextLine()/*si el mensaje enviado poir el proceso padre no es nulo entonces*/) {
			// limpiar el texto para comprarlo con su uinvertido
			String texto = sc.nextLine().trim();
			String limpio = texto.replaceAll("\\s+", "").toLowerCase();
			String invertido = new StringBuilder(limpio).reverse().toString();

			if (limpio.equals(invertido)) {
				System.out.println("Es palindromo");
			} else {
				System.out.println("NO es palíndromo");
			}
		}
		sc.close();
		//Una respuesta del proceso para saber si todo salio bien
		System.exit(0);
	}
}
