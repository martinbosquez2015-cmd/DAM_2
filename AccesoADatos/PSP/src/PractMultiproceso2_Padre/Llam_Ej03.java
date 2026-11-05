package PractMultiproceso2_Padre;

public class Llam_Ej03 {

	public static void main(String[] args) {
		System.out.print("Escribe un texto: ");
		//respuesta por teclado del usuario
        Scanner scConsola = new Scanner(System.in);
        String texto = scConsola.nextLine();
        scConsola.close();
        
        // Se guarda el proceso en una variable 
        File directorio = new File("../VerificadorPalindromo/bin");
        ProcessBuilder pb = new ProcessBuilder("java", "VerificadorPalindromo");
        pb.directory(directorio);

        try {
        	//Se inicia el proceso
            Process p = pb.start();

            // Enviar texto al hijo
            OutputStream os = p.getOutputStream();//Stream es un canal para que se comuniquen los procesos
            //en este caso el proceso padre envia la entrada por teclado del usuario dada al hijo
            os.write((texto + "\n").getBytes());
            os.flush();
            //Se espera a que el proceso finalice y nos de una señal/respuesta
            int exitVal = p.waitFor();
            System.out.println("Valor de Salida: " + exitVal);//El valor de la salida nos sirve para comprobar si todo salio bien,
            // o para comprobar en que condicion terminó el proceso llamado

            // Leer respuesta del hijo
            //En este caso la respuesta es el syso
            InputStream is = p.getInputStream();
            Scanner scHijo = new Scanner(is);
            while (scHijo.hasNextLine()) {
                System.out.println(scHijo.nextLine());
            }
            scHijo.close();
            is.close();

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
