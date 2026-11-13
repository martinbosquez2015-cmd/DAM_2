package com.padle;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Scanner;

public class EsPalinLlam {

	public static void main(String[] args) throws IOException, InterruptedException{
		System.out.println("EsPalindromoPadreInador version 1.0");
		
		
		Scanner teclado = new Scanner(System.in);
		System.out.println("Por favor, introduce la palabra a demostrar: ");
		// SE pone un salto de linea para que la wea funcione bien
		String enviaPalabra = teclado.nextLine()+"\n";
		/* Llamamos al otro programa pasando una ruta realtiva */	
		File directorio = new File("../EjProc01_EsPalin/bin");
		//El proceso a ejecutar es Ejemplo2	
		ProcessBuilder pb = new ProcessBuilder("java","com.hijo.EsPalin");
		pb.directory(directorio);
	    //se ejecuta el proceso
		Process p = pb.start();
		
		//crear canal para enviarle los argumentos al Hijo Del Proceso
		OutputStream os = p.getOutputStream();
		
		/* ALTERNATIVA 1 - Trabajamos con bytes */
		//os.write(respuesta.getBytes());
		//os.flush(); // vacía el buffer de salida. Usar uno entre flush y close
		//os.close();
		
		/* ALTERNATIVA 2 - Trabajamos con bytes en buffer*/
		/*BufferedOutputStream bs = new BufferedOutputStream(os);
		bs.write(respuesta.getBytes());
		bs.flush(); // vacía el buffer de salida. Usar uno entre flush y close
		//bs.close();
		*/
		/* ALTERNATIVA 3 - Trabajamos con cadenas  en buffer*/	
		OutputStreamWriter ow = new OutputStreamWriter(os);
		BufferedWriter bw = new BufferedWriter (ow);
		bw.write(enviaPalabra);
		bw.flush();
		//Lo cerramos por si las flys
		bw.close();
		
		//Comprobación de error -0 bien -1 mal
		int exitVal = -99;
		try {
			exitVal = p.waitFor();
			System.out.println("Valor de salida: " + exitVal);
			
		}catch(InterruptedException e) {
			e.printStackTrace();
		}
		if(exitVal == 0) {
			//Se obtiene la salida devuelta por el proceso
			try {
				// se abre canar para recibir lo que el proceso hijo le envia
				// en este caso el padre recibe los prinln
				InputStream is = p.getInputStream();
				Scanner sc2 = new Scanner(is);
				String salida = sc2.nextLine();
				System.out.println(salida);
				if(sc2.hasNext() ==true) {
					System.out.println(salida);
				}
			}catch(Exception e) {
				e.printStackTrace();
			}
		}
		
		
		
		
		
		
				

	}

}
