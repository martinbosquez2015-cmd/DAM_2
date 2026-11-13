package com.hijo;

import java.io.IOException;
import java.util.Scanner;

public class EsPalin {

	public static void main(String[] args) throws IOException, InterruptedException{
		Scanner sc = new Scanner(System.in);
		//lo limpiamos quitandole los espacios y poniendolo todo en minúscula
		try {
		String pal = sc.nextLine().trim().toLowerCase();
		String palRev = "";
		
		
		
		for(int i =pal.length()-1; i>=0; i--) {
			palRev+= pal.charAt(i);
		}
		//tambien se puede evitar hacer el toLowerCase y ahcer la comparacion de esta forma
		//if(pal.equalsIgnoreCase(palRev)){
		if(pal.equals(palRev)) {
			System.out.println("Es palíndromo");
		}
		else {
			System.out.println("No es palíndromo");
		}
		System.exit(0);
		}catch (Exception e) {
			e.printStackTrace();
			System.exit(-1);
		}
		

	}

}
