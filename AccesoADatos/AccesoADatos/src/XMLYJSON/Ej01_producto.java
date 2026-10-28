package XMLYJSON;

public class Ej01_producto {
	private String codigo;
	private String descripcion;
	private String stock;
	
	public Ej01_producto(String cod, String descr, String stock) {
		this.codigo = cod;
		this.descripcion = descr;
		this.stock = stock;
	}
	
	public String toString() {
		return "Codigo:"+this.codigo+"\nDescripcion: "+this.descripcion+"\nStock: "+this.stock+"\n";
	}
	
	
}
