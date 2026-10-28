package EjerciciosJdbcMcHunnigan;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Ej01_EmpleadosClassicModels {

	public static void main(String[] args) {
		String jdbcURL = "jdbc:mysql://localhost:3306/classicmodels";
		String usuario = "perroflauta";
		String password = "abc123";

		try (Connection conn = DriverManager.getConnection(jdbcURL, usuario, password)) {
			System.out.println("Conexión exitosa weon");
			listarEmpleados(conn, "Paris");
			listarEmpleados(conn, "Madrid");
			listarEmpleados(conn, "Rio de Janeiro");

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void listarEmpleados(Connection conn, String ciuda) throws SQLException {
		String query1 = "SELECT CONCAT(lastName,', ', firstName,' ', '(',email,')') as employees_in_city from employees join offices using (officeCode) where city = ?";
		String queryPrev = "SELECT * FROM offices WHERE city = ?";
		PreparedStatement consulta1 = conn.prepareStatement(queryPrev, ResultSet.TYPE_SCROLL_INSENSITIVE,
				ResultSet.CONCUR_READ_ONLY);
		consulta1.setString(1, ciuda);
		ResultSet resultado = consulta1.executeQuery();
		resultado.last();
		if(resultado.getRow()==0) {
			System.out.printf("No existe oficina en %s\n",ciuda);
		}else {
			consulta1 = conn.prepareStatement(query1, ResultSet.TYPE_SCROLL_INSENSITIVE,
					ResultSet.CONCUR_READ_ONLY);
			consulta1.setString(1, ciuda);
			ResultSet resultado2 = consulta1.executeQuery();
			resultado2.last();
			int numEmpl = resultado2.getRow();
			if (numEmpl == 0) {
				System.out.printf("La ciudad %s no tiene empleados asignados...\n",ciuda);
				
			}else {
				
				resultado2.beforeFirst();
				System.out.printf("Hay %d empleados en la oficina de %s. Sus datos son:\n",numEmpl, ciuda);
				while(resultado2.next()) {
					System.out.println(" - "+resultado2.getString(1));
					
				}
			}
		}
		
	}

}
