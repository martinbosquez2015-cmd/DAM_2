package jdbcMcHunnigan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MainJ {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost/dam2";
		/*Normalmente, ya se sabe que el puerto de conexion 
		 * de mysl siempre es 3306, por lo que no hace falta ponerlo
		 * ya es una opción predeterminada por defecto, 
		 * en el caso de que la ruta no fuera la tipica 3306
		 * ahí si tenemos que especificarla
		 * */
		//String url = "jdbc:mysql://localhost/3306";
		String usuario = "perroflauta";
		String password = "abc123";
		
		try {
			Connection conn = DriverManager.getConnection(url, usuario, password);
			System.out.println("Conexión exitosa weon");
			Statement sql = conn.createStatement();
			ResultSet resultado = sql.executeQuery("SELECT * FROM alumnos");
			while(resultado.next()) {
				
			}
			conn.close();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

}
