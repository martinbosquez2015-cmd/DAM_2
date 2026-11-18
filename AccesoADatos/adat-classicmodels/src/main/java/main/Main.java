package main;

import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

import model.*;

public class Main {
	private static final Logger LOG_HIBERNATE = Logger.getLogger("org.hibernate");

	public static void main(String[] args) {
		LOG_HIBERNATE.setLevel(Level.SEVERE);
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("clasePU");
		EntityManager em = emf.createEntityManager();
		try {
			listadoNombresPaises(em);
			listadoNombresPaises2(em);
		} catch (Exception e) {
			System.out.println("Error: " + e.getMessage());
		}
		em.close();
		emf.close();
	}

	public static void listadoNombresPaises(EntityManager em) {
		//String jpql = "SELECT employees.lastName, employees.firstName, employees.email FROM Employees employees";
		String jpql = "SELECT CONCAT(employees.lastName,' ', employees.firstName,': ', employees.email) FROM Employees employees";
		List<String> listaEmpleados = em.createQuery(jpql, String.class).getResultList();
		System.out.println("--- Lista de empleados ---");
		for (String nombre : listaEmpleados) {
			System.out.println(nombre);
		}
		System.out.println("Total de empleados: " + listaEmpleados.size());
	}
	
	public static void listadoNombresPaises2(EntityManager em) {
		//String jpql = "SELECT employees.lastName, employees.firstName, employees.email FROM Employees employees";
		String jpql1 = "FROM Offices";
		String jpql2 = "SELECT concat(e.firstName,' ',e.lastName) from Employees e join Offices o on e.Offices=o.officeCode where o.officeCode = :code and o.country = :country";
		List<Offices> listaOficinas = em.createQuery(jpql1, Offices.class).getResultList();
		for(Offices of : listaOficinas) {
			int codigoOficina =  Integer.parseInt(of.getOfficeCode());
			String pais = of.getCountry();
			List<Object> listaPaises = em.createQuery(jpql2, Object.class)
					.setParameter("code", codigoOficina).setParameter("country", pais)
					.getResultList();
			for(Object e : listaPaises) {
				System.out.println((String) e);
			}
			
	
	}

	
}
}
