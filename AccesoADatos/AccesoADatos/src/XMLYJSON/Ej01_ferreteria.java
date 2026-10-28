package XMLYJSON;

import java.util.List;
import com.google.gson.annotations.SerializedName;

public class Ej01_ferreteria {
	@SerializedName ("ferreteria")
	private List<Ej01_producto> productos;
	public List<Ej01_producto> getProductos(){
		return productos;
	}
	public void setProductos(List<Ej01_producto> product) {
		this.productos = product;
	}
	
}
