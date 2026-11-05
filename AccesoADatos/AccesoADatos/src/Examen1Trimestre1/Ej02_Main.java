package Examen1Trimestre1;

import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;

public class Ej02_Main {

	public static void main(String[] args) {
		String url = "jdbc:mysql://localhost:3306/";
		String usuario = "root";
		String password = "abc123";
		String fichero = "marvel.json";
		try (Connection conn = DriverManager.getConnection(url, usuario, password)) {
			System.out.println("Estroy dentro");
			crearBaseUCM(conn);
			grabarEnBaseDesdeJson(fichero, conn);
			leerBase(conn);
		} catch (SQLException e) {
			System.out.println(e.getMessage());
		}

	}

	public static void crearBaseUCM(Connection conn) throws SQLException {
		String queryPrev = "DROP DATABASE IF EXISTS UCM";
		String query1 = "CREATE DATABASE IF NOT EXISTS UCM";
		String query2 = "USE UCM";
		String query3 = "CREATE TABLE IF NOT EXISTS pelicula(" + "titulo VARCHAR(150) PRIMARY KEY,"
				+ "anio INT NOT NULL," + "fase INT NOT NULL," + "director VARCHAR(100) NOT NULL" + ")";
		Statement consulta = conn.createStatement();
		consulta.executeUpdate(queryPrev);
		consulta.executeUpdate(query1);
		consulta.executeUpdate(query2);
		consulta.executeUpdate(query3);
	}

	public static void grabarEnBaseDesdeJson(String fichero, Connection conn) throws SQLException {
		List<Filme> filmes = null;
		try (Reader lector = new FileReader(fichero)) {
			Gson gson = new Gson();
			Ucm ucm = gson.fromJson(lector, Ucm.class);
			filmes =  ucm.getFilmes();
			conn.setAutoCommit(false);
			
			
			for(Filme f: filmes) {
				
				String titulo = f.getTitulo();
				String director = f.getdirector();
				String fase = String.valueOf(f.getFase());
				String anio = String.valueOf(f.getAnio());
				System.out.println(titulo + director+fase+anio);
				String query = "INSERT INTO `UCM`.`pelicula`"
						+ "("
						+ "titulo,"
						+ "anio,"
						+ "fase,"
						+ "director"
						+ ")"
						+ "VALUES"
						+ "("
						+ "`"+titulo+"`,"
						+ "`"+director+"`,"
						+fase+","
						+ anio
						+ ")";
				Statement consulta = conn.createStatement();
				consulta.executeUpdate(query);
				
				conn.commit();
			}
			

		} catch (Exception e) {
			System.err.println("Error al leer el archivo: " + e.getMessage());
		}

		if (filmes == null)
			System.out.println("Error al leer los filmes");
	}

	public static void leerBase(Connection conn) throws SQLException {
		String query = "SELECT * FROM pelicula";
		PreparedStatement consulta = conn.prepareStatement(query, ResultSet.TYPE_SCROLL_INSENSITIVE, ResultSet.CONCUR_READ_ONLY);
		ResultSet resultado = consulta.executeQuery();
		System.out.println("Contenido de la tabla:");
		while (resultado.next()) {
			System.out.printf("  %d (Fase %d) - %s [%s]\n", resultado.getInt("anio"),resultado.getInt("fase"),resultado.getString("nombre"),resultado.getString("director"));
		}
	}

}
