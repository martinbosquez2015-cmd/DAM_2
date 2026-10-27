package Clases;

public class Escritor extends Thread{
	private String palabra;
	private int tiempo;
	
	Escritor(String palabra, int tiempo){
		super();
		this.palabra = palabra;
		this.tiempo = tiempo;
		
	}
	public void escritura() {
		try {
		for(int i = 0; i<19; i++) {
			System.out.println(this.palabra);
			Thread.sleep(tiempo*1000);
		}
		}catch(Exception e) {
			e.printStackTrace();
		}
		
	}
	@Override
	public void run() {
		this.escritura();
	}

}
