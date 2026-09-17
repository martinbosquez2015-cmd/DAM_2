package ficheros_01;

import java.io.BufferedReader;
import java.io.File;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.IOException;



public class Ej_01 {

		// TODO Auto-generated method stub
		private static final String DIR_ANIM = "EjerciciosDatos"+File.separator + "Ficheros_01";
		private static final String ARCHIVO_PERS = DIR_ANIM + File.separator+"personajes.txt";
		private static final String ARCHIVO_ANIM  = DIR_ANIM + File.separator+ "animes.txt";
		

		public static void main(String[] args) throws IOException {
			try{
				File directorioActual = new File(".");
				System.out.println(directorioActual.getAbsolutePath());
				File dirAnim = new File(DIR_ANIM);
				boolean crearFichero = true;
				if(dirAnim.exists()==true) {
					System.out.println("El directorio " + DIR_ANIM+ " existe");
				}else {
					System.out.println("El directorio " + DIR_ANIM + " no existe");
					if(dirAnim.mkdirs() == false) {
						crearFichero = false;
						System.out.println("No he podido crear el directorio :(");
					}
				}
				if(crearFichero == true) {
					File archivoP = new File (ARCHIVO_PERS);
					File archivoA = new File (ARCHIVO_ANIM);
					if(archivoP.createNewFile() || archivoA.createNewFile())
						System.out.println("Archivo creado");
					else
						System.out.println("No puedo crearlo o ya existe");	
				}
				print
				
			}catch (Exception e){
				System.out.println("Error: "+e.getMessage());
			}
			
		
	}
		static ArrayList<String> leerPersonajes(int codigo) throws Exception {
			ArrayList<String> personajes = new ArrayList<>();
			try (BufferedReader br = new BufferedReader(new FileReader(ARCHIVO_PERS))){
				String linea;
				while ((linea = br.readLine()) !=null) {
					int posicion = linea.indexOf(" ");
					int num = Integer.parseInt(linea.substring(0,posicion));
					if(num == codigo) {
					String titulo = linea.substring(posicion +1);
					}
					
				}
			}
			return personajes;
		}

}
