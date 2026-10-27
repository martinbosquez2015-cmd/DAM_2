package Clases;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class TicTacMain {

	public static void main(String[] args) throws InterruptedException{
		Escritor tic = new Escritor("TIC", 1);
		Escritor tac = new Escritor("TAC", 1);
		Escritor toc = new Escritor("TOC", 1);
		Escritor tuc = new Escritor("TUC", 1);

		ExecutorService miEjecutor = Executors.newFixedThreadPool(2);
		/*
		 * Para que se ejecuten de manera ordenada tic start(); tac start(); toc
		 * start();
		 */
		miEjecutor.submit(tic);
		miEjecutor.submit(tac);
		miEjecutor.submit(toc);
		miEjecutor.submit(tuc);
		// para que se apague la wea
		miEjecutor.shutdown();
		// while(miEjecutor.isTerminated()) {
//			Thread.sleep(1000);
		// }
		
		//sigue el programa segun la condicion que acabe primero
		miEjecutor.awaitTermination(1, TimeUnit.HOURS);
		System.out.println("Hemos acabado");
	}

}
