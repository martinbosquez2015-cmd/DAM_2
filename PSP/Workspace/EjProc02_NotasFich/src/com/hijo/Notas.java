package com.hijo;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;

public class Notas {
	// hacer esto con el retorno no es muy recomendable, las variables estáticas
	// suelen ser inamovibles
	// pero en este caso se hace porque separamos el programa en dos funciones, como
	// es un programa pequeño
	// para optimizarlo, lo mejor será no separarlo en funciones y crear varios
	// trys, nos e, se lo preguntaré a la ia
	// Al final si lo meti dentro de la funcion porque me dio un horrible dolor de cabeza ptm

	public static void main(String asignatura, String fichero) throws Exception {
		int retorno = 0;
		ArrayList<String> notas = null;
		//actualizamos la ruta del fichero para que nos lleve a donde tenemos guardados los ficheros
		Path ruta = Path.of("../../Ficheros/"+fichero);
		try {
			notas= (ArrayList)Files.readAllLines(ruta);
		}catch(Exception e) {
			retorno = -2;
		}
		int contador = 0;
		int suma = 0;
		for(String nota: notas) {
			System.out.println(nota);
			try{
				int notaI = Integer.parseInt(nota);
			if(notaI>=5)
				contador++;
			else if(notaI<0)
				retorno = -3;
			suma+= notaI;
			}catch(NumberFormatException e) {
				retorno = -1;
			}
		}
		int media = suma/notas.size();
		System.out.printf("=================\nValor de Salida: %d\nEn la asignatura de %s\nHan aprobado %d alumnos\nLa media es %d\n=================\n",retorno,asignatura,contador,media);
		System.exit(retorno);
	}


}
