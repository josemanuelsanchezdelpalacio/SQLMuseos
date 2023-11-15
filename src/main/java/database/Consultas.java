package database;

import entities.AutoresEntity;
import entities.MuseosEntity;
import entities.ObrasEntity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.Query;

import java.sql.Date;
import java.util.List;

public class Consultas {

    public static void listarObras(EntityManager em) {
        List<ObrasEntity> obras = em.createQuery("from ObrasEntity",  ObrasEntity.class).getResultList();
        for (ObrasEntity o : obras){
            System.out.println(o.getTitulo() + ". Autor: " + o.getAutoresByIdAutor().getNombre() + ", Museo: " + o.getMuseosByIdMuseo().getNombre());
        }
    }

    public static void modificarMuseo(EntityManager em){
        EntityTransaction transaction = em.getTransaction();
        //comenzamos a crear el contexto de persistencia
        transaction.begin();
        ObrasEntity obra = em.find(ObrasEntity.class, obraId);
        //a partir de aquí trabajamos sobre el objeto instanciado que representa un registro de la base de datos
        int obraId, nuevoMuseoId;
        MuseosEntity nuevoMuseo = em.find(MuseosEntity.class, nuevoMuseoId);

        if (obra != null && nuevoMuseo != null) {
            obra.setMuseosByIdMuseo(nuevoMuseo);
            em.persist(obra);
        }

        //al hacer el commit los cambios se pasan a la base de datos
        transaction.commit();
    }

    public static void insertarObra(EntityManager em){
        EntityTransaction transaction = em.getTransaction();
        //comenzamos a crear el contexto de persistencia
        transaction.begin();
        
	    String titulo, nombreAutor, nacionalidad;
	    Date fecha, fechaNacimiento, fechaFallecimiento; 
	    int idMuseo;
	
        AutoresEntity autorNuevo = new AutoresEntity();
        autorNuevo.setNombre(nombreAutor);
        autorNuevo.setFechaNacimiento(fechaNacimiento);
        autorNuevo.setFechaFallecimiento(fechaFallecimiento);
        autorNuevo.setNacionalidad(nacionalidad);
        em.persist(autorNuevo);

        ObrasEntity nuevaObra = new ObrasEntity();
        nuevaObra.setTitulo(titulo);
        nuevaObra.setFecha(fecha);
        nuevaObra.setIdMuseo(idMuseo);
        nuevaObra.setIdAutor(autorNuevo.getId());
        nuevaObra.setAutoresByIdAutor(autorNuevo);

        em.persist(nuevaObra);
        transaction.commit();
    }
}
