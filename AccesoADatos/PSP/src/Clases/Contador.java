package Clases;

public class Contador {
	int contador =0;
	int contador2;
	
	
	public /*synchronized*/ void sumar() {
		this.contador2++;
		synchronized(this) {
		this.contador++;
		}
	}
	
	public int getContador() {
		return this.contador;
	}
	
	public int getContador2() {
		return this.contador;
	}
	
}
