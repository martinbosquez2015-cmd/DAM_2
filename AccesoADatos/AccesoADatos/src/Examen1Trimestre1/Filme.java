package Examen1Trimestre1;

public class Filme {
	private String titulo;
	private String director;
	private int fase;
	private int anio;

	public Filme(String n, String d, int f, int a) {
		this.titulo = n;
		this.director = d;
		this.fase = f;
		this.anio = a;

	}
	
	public String getTitulo() {
		return this.titulo;
	}
	public String getdirector() {
		return this.director;
	}
	public int getFase() {
		return this.fase;
	}
	public int getAnio() {
		return this.anio;
	}
	
	
}
