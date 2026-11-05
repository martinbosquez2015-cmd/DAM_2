package Examen1Trimestre1;

import java.util.List;

import com.google.gson.annotations.SerializedName;

public class Ucm {
	
	@SerializedName("ucm")
	private List<Filme> filmes;
	
	public List<Filme> getFilmes(){
		return filmes;
	}
	

}
