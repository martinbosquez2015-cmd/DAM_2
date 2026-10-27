package Clases;

public class Main {

	public static void main(String[] args) {
		Contador c = new Contador();
		
		Hilo h1 = new Hilo(c);
		Hilo h2 = new Hilo(c);
		Hilo h3 = new Hilo(c);
		Hilo h4 = new Hilo(c);
		Hilo h5 = new Hilo(c);
		
		h1.start();
		h2.start();
		h3.start();
		h4.start();
		h5.start();
		
		while(h1.isAlive() || h2.isAlive() || h3.isAlive() || h4.isAlive() || h5.isAlive()) {
			try {
				Thread.sleep(100);
			}catch(InterruptedException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println(c.getContador());
		System.out.println(c.getContador2());
		

	}
}
