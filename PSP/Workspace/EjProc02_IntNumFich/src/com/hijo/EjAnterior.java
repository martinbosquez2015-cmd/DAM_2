package com.hijo;

import java.util.Scanner;

public class EjAnterior {
	public static void main(String[] args) {
		//El ejercicio cambia porque ya no recibimos argumentos por separado, sino que recibimos un fichero
		//añadimos un scanner para que este lea las lineas y verigique
		Scanner sc = new Scanner(System.in);
		int retorno = -1;// por defecto se le asume como si fuera un error o estuviera vacío
		
		/*
		if (args.length == 0 || args[0].equals("")) {
			retorno = -1;
		} else {
			try {
				int señal = Integer.parseInt(args[0]);
				if (señal > 0) {
					retorno = -3;
				} else if (señal == 0) {
					retorno = 1;
				} else {
					retorno = 0;
				}
			} catch (NumberFormatException e) {
				retorno = -2;
			}
		}*/
		//Primero se observa si hay lineas en el fichero
		if (sc.hasNextLine()) {
			// Se sanea la cadena
			String input = sc.nextLine().trim();
			if (!input.isEmpty()) {
				try {
				int numero = Integer.parseInt(input);
				if(numero>0) {
					
				}
			}catch (NumberFormatException e) {
				retorno = -2; // Entero no válido
			}
			}
		}
		System.exit(retorno);
	}
}
