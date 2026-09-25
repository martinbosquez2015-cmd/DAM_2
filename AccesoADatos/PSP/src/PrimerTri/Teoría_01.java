package PrimerTri;

import java.util.concurrent.TimeUnit;

public class Teoría_01 {

	public static void main(String[] args) {
		ProccessBuilder pb = new ProccessBuilder("gedit", "notas.txt");
		pb.directory(directorio);
		
		Proccess p = pb.start();
		
		//esperamos como máximo 5 segundos
		boolean terminado = p.waitFor(5, TimeUnit.SECONDS);
		
		// Si no ha terminado, lo destruimos
		if(!terminado) {
			System.out.println("Han pasado 5 segunddos, destruimos el proceso");
		}

	}

}
