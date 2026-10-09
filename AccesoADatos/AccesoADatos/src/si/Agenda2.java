package si;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Agenda2 {
	
	@SerializedName("agenda")
	
	private List<Contacto2> contacto;
	
	public List<Contacto2> getLista(){
		return contacto;
	}
}

