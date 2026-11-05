package com.dam2.goya;

import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Main {
	//Atributo estático para desabilitar el logger del hibernate para mensajes de error de hibernate
	private static final Logger LOG_HIBERNATE = Logger.getLogger(org.hibernate);
	
	
	public static void main(String[] args) {
		//Le decimos a hibernate que solo muestre los errores y no mensajes informativos
		LOG_HIBERNATE.setLevel(Level.SEVERE);
		
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("clasePU");
		EntityManager em = emf.createEntityManager();
		try {
			//em.getTransaction().begin();
			/*Producto p = new Producto("Teclado", 25.50);
			em.persist(p);
			p = new Producto("Teclado mecanico", 27.35);
			em.persist(p);
			p = new Producto("Raton gamer", 15.55);
			em.persist(p);
			p = new Producto("Fuente de alimentacion 500w", 45.50);
			em.persist(p);
			p = new Producto("Monitor 21\"", 133.60);
			em.persist(p);
			em.persist(new Producto("Caja minitorre", 31.60));*/
			//em.getTransaction().commit(); // el commit viene desactivado por defecto en hybernate
		
			/*Producto p = em.find(Producto.class, 1L);
			if (p != null)
			p.setPrecio(29.9);*/
			/*em.getTransaction().begin();
		
			Producto p = em.find(Producto.class, 1L);
			if (p != null)
			
			em.remove(p);
			em.getTransaction().commit();*/
		
			//String jpql = "FROM Producto p WHERE p.precio > 100";
			//String jpql = "FROM Producto p WHERE p.nombre LIKE 'Teclado%' ORDER BY p.precio DESC";
			//String jpql = "FROM Producto p WHERE p.precio > 100";
			//Recuerda que siempre se van a hacer referecias a los objetos y sus atributos y no a las tablas de la propia base de datos
			//String jpql = "FROM Producto p WHERE p.nombre LIKE 'Teclado%'";
			//List<Producto> lista = em.createQuery(jpql, Producto.class).getResultLists();
			/*for(Producto p : lista)
				System.out.println(p);*/
			//Esto es para acceder solo a certas caracteristicas del objeto y no el objeto entero
			/*
			String jpql = "SELECT p.nombre, p.precio FROM Producto p";
			List<Object[]> lista = em.createQuery(jpql, Object[].class).getResultList();
			for(Object[] p : lista)
				System.out.println((String)p[0]+" - "+(Double)p[1]);*/
			
			//Ahora vamos a parametrizar
				// HAy dos formas con el setParameter, la primera es mas entendible que la segunda
			String jpql = "FROM Producto p WHERE p.precio > :precioMax AND p.nombre LIKE ?2";
			List<Producto> lista = em.createQuery(jpql,Producto.class)
					.setParameter("precioMax", 100)
					.setParameter(2, "Mon%")
					.getResultList();
			
			System.out.println("Total de productos de la tabla: " + lista.size());
			//em.getTransaction().commit();
			//System.out.println("PRoducto guardado con id " + p.getId());
		}catch(Exception e) {
			System.out.println("Error "+ e.getMessage());
			//em.getTransaction().rollback;
		}
		
	}

}
