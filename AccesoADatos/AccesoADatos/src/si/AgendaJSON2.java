package si;
import java.util.List;

import com.google.gson.Gson;

import java.io.Reader;
import java.io.FileReader;



public class AgendaJSON2 {

	public static void main(String[] args) {
		String fichero = "agenda.json";
		try(Reader lector = FileReader(fichero)){
			Gson gson = new Gson();
			Agenda agenda = gson.fromJson(lector, Agenda.class);
			List<Contacto2> contactos= agenda.getContactos();
			System.out.println("Contactos: "+contactos.size());
			for(Contacto2 c: contactos)
				System.out.println(c);
		}catch (Exception e) {
			System.out.println("Error");
		}

	}

}
