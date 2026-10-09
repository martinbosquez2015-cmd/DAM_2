package si;

import java.util.List;

public class Contacto2 {
	private String nombre;
	//private String telefono; 
	private List<String> telefono;
	private String dni;
	
	public Contacto2(String n, String d, List<String> t ) {
		this.nombre=n;
		this.dni=d;
		this.telefono=t;
	}
	public String getNombre() {
		return nombre;
	}
	
	public void setTelefono(List<String> telefono) {
		this.telefono= telefono;
	}
	@Override
	public String toString() {
		return "Nombre: "+this.nombre+"\nDNI: "+this.dni+"\nTelefono: "+"\n";
	}
	
}

