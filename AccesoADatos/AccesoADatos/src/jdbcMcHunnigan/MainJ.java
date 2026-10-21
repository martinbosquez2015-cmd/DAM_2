package jdbcMcHunnigan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MainJ {

	public static void main(String[] args) {
		/*String url = "jdbc:mysql://localhost/dam2";
		/*Normalmente, ya se sabe que el puerto de conexion 
		 * de mysl siempre es 3306, por lo que no hace falta ponerlo
		 * ya es una opción predeterminada por defecto, 
		 * en el caso de que la ruta no fuera la tipica 3306
		 * ahí si tenemos que especificarla
		 * 
		//String url = "jdbc:mysql://localhost/3306";
		String usuario = "perroflauta";
		String password = "abc123";
		*/
		
		try /*(Connection conn = DriverManager.getConnection(url, usuario, password))*/{
			Connection conn = PoolConexiones.getConnection();
			System.out.println("Conexión exitosa weon");
			conn.setAutoCommit(false);
			Statement sql = conn.createStatement();
			//sql.executeUpdate("DELETE FROM alumnos WHERE email='ana@educa.madrid.org'");
			//ResultSet resultado = sql.executeQuery("SELECT * FROM alumnos");
			ResultSet resultado = sql.executeQuery("SHOW TABLES");
			while(resultado.next()) {
				System.out.println(resultado.getString(1));
			}
			//conn.commit();
			
		}catch(SQLException e) {
			e.printStackTrace();
		}

	}

}
