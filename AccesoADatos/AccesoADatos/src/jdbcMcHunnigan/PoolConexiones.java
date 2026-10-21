package jdbcMcHunnigan;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.commons.dbcp2.BasicDataSource;

public class PoolConexiones {
	private static BasicDataSource dataSource;
	static {
		dataSource = new BasicDataSource();
		String url = "jdbc:mysql://localhost/pokemondb";
		/*Normalmente, ya se sabe que el puerto de conexion 
		 * de mysl siempre es 3306, por lo que no hace falta ponerlo
		 * ya es una opción predeterminada por defecto, 
		 * en el caso de que la ruta no fuera la tipica 3306
		 * ahí si tenemos que especificarla
		 * */
		//String url = "jdbc:mysql://localhost/3306";
		String usuario = "perroflauta";
		String password = "abc123";
		dataSource.setUrl(url);
		dataSource.setUsername(usuario);
		dataSource.setPassword(password);
		
		dataSource.setInitialSize(5);
		dataSource.setMaxTotal(100);
		dataSource.setPoolPreparedStatements(true);
		dataSource.setMaxOpenPreparedStatements(50);
	}
	public static Connection getConnection() throws SQLException{
		return dataSource.getConnection();
	}
}
