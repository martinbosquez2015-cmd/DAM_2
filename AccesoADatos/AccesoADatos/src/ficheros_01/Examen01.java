package ficheros_01;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;

public class Examen01 {
	final static String ruta = "coordenadas.dat";
	public static void main(String[] args) {
		// final int NUM_REGISTROS = 3; Esto es cuando tenemos un tamaño fijo en el archivo
		final int TAMANYO_REGISTRO = 20;
		
		try(DataInputStream fichero = new DataInputStream(new FileInputStream(ruta))){
			File ficheroFisico = new File(ruta);
			final int NUM_REGISTROS = (int)ficheroFisico.length()/TAMANYO_REGISTRO;// esto es para ficheros con mayor tamaño
			System.out.println("SATÉLITES Y COORDENADAS");
			for(int i=0; i< NUM_REGISTROS; i++) {
				int id = fichero.readInt();
				float latitud = fichero.readFloat();
				float longitud = fichero.readFloat();
				String estado = "";
				for(int j=0; j<4; j++)
					estado+=fichero.readChar();
				System.out.printf("Satélite ID: %d | Posición: (%.4f,%.4f) | Estado: %s\n", id, latitud, longitud);
			}
		}catch(Exception e) {
			System.out.println("");
		}

	}

}
