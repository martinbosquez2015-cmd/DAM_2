package PractMultiproceso2_Padre;

public class Llam_Ej02 {

	public static void main(String[] args) {
Scanner scConsola = new Scanner(System.in);
        
        File directorio = new File("../SumaAcumulativa/bin");
        ProcessBuilder pb = new ProcessBuilder("java", "SumaAcumulativa");
        pb.directory(directorio);

        try {
            Process p = pb.start();
            
            // Canal para enviar datos al proceso hijo[cite: 20, 24]
            OutputStream os = p.getOutputStream();

            String entrada;
            do {
                System.out.print("Escribe un número (o * para terminar): ");
                entrada = scConsola.nextLine();
                // Es obligatorio incluir el salto de línea para que el Scanner del hijo lo procese
                os.write((entrada + "\n").getBytes());
                os.flush(); // Vacía el búfer de salida[cite: 24]
            } while (!entrada.equals("*"));

            scConsola.close();

            int exitVal = p.waitFor();
            if (exitVal == 0) {
                // Recogemos la salida devuelta por el proceso hijo[cite: 25]
                InputStream is = p.getInputStream();
                Scanner scHijo = new Scanner(is);
                while (scHijo.hasNextLine()) {
                    System.out.println(scHijo.nextLine());
                }
                scHijo.close();
                is.close();
            } else {
                System.out.println("El proceso hijo terminó con error debido a una cadena no válida.");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
	}

}
