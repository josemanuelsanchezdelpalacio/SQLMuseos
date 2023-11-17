package database;

import entities.AutoresEntity;
import entities.MuseosEntity;
import entities.ObrasEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;
import libs.Leer;

import java.sql.Date;
import java.util.List;

public class Consultas {

    public static void listarObras(EntityManager em) {
        List<ObrasEntity> obras = em.createQuery("from ObrasEntity",  ObrasEntity.class).getResultList();
        for (ObrasEntity o : obras){
            System.out.println(o.getTitulo() + ". Autor: " + o.getAutoresByIdAutor().getNombre() + ", Museo: " + o.getMuseosByIdMuseo().getNombre());
        }
    }

    public static void modificarMuseo(EntityManager em) {
    	EntityTransaction transaction = em.getTransaction();
    	// comenzamos a crear el contexto de persistencia
    	transaction.begin();

    	// a partir de aquí trabajamos sobre el objeto instanciado que representa un registro de la base de datos
    	int idMuseo = Leer.pedirEntero("Introduce un ID para el museo a modificar: ");

    	// Obtener el museo por su ID
    	MuseosEntity museo = em.find(MuseosEntity.class, idMuseo);

    	if (museo != null) {
        	// Modificar cada atributo del museo
        	museo.setNombre(Leer.pedirCadena("Introduce el nuevo nombre del museo: "));
        	museo.setDireccion(Leer.pedirCadena("Introduce la nueva dirección del museo: "));
        	museo.setCiudad(Leer.pedirCadena("Introduce la nueva ciudad del museo: "));
        	museo.setPais(Leer.pedirCadena("Introduce el nuevo país del museo: "));

        	// Utilizar una consulta con parámetros para actualizar cada atributo del museo
        	Query query = em.createQuery("UPDATE MuseosEntity m SET m.nombre = :nombre, m.direccion = :direccion, m.ciudad = :ciudad, m.pais = :pais WHERE m.id = :idMuseo");
        	query.setParameter("nombre", museo.getNombre());
        	query.setParameter("direccion", museo.getDireccion());
        	query.setParameter("ciudad", museo.getCiudad());
        	query.setParameter("pais", museo.getPais());
        	query.setParameter("idMuseo", idMuseo);

        	int updatedEntities = query.executeUpdate();

        	if (updatedEntities > 0) {
            		System.out.println("El museo ha sido modificado exitosamente.");
        	} else {
            		System.out.println("No se encontró el museo con el ID proporcionado.");
        	}
    	} else {
        	System.out.println("No se encontró el museo con el ID proporcionado.");
    	}

    	// al hacer el commit, los cambios se pasan a la base de datos
    	transaction.commit();
    }

    public static void insertarObra(EntityManager em){
        EntityTransaction transaction = em.getTransaction();
        //comenzamos a crear el contexto de persistencia
        transaction.begin();
	
        AutoresEntity autorNuevo = new AutoresEntity();
        autorNuevo.setNombre("nombreAutor");
        autorNuevo.setFechaNacimiento(Date.valueOf("1973-01-01"));
        autorNuevo.setFechaFallecimiento(Date.valueOf("2023-01-01"));
        autorNuevo.setNacionalidad("español");
        em.persist(autorNuevo);

        ObrasEntity nuevaObra = new ObrasEntity();
        nuevaObra.setTitulo("titulo obra");
        nuevaObra.setFecha(Date.valueOf("2017-01-01"));
        nuevaObra.setIdMuseo(1);
        nuevaObra.setIdAutor(autorNuevo.getId());
        nuevaObra.setAutoresByIdAutor(autorNuevo);

        em.persist(nuevaObra);
        transaction.commit();
    }
}
