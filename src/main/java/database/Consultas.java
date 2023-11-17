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

    public static void modificarMuseo(EntityManager em){
        EntityTransaction transaction = em.getTransaction();
        //comenzamos a crear el contexto de persistencia
        transaction.begin();

        //a partir de aquí trabajamos sobre el objeto instanciado que representa un registro de la base de datos
        int idObra = Leer.pedirEntero("Introduce un ID para la obra a modificar: ");
        int idMuseo = Leer.pedirEntero("Introduce un ID para el museo a modificar: ");
        ObrasEntity obra = em.find(ObrasEntity.class, idObra);
        MuseosEntity nuevoMuseo = em.find(MuseosEntity.class, idMuseo);

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
	
        AutoresEntity autorNuevo = new AutoresEntity();
        autorNuevo.setNombre("nombreAutor");
        autorNuevo.setFechaNacimiento(Date.valueOf("01-01-1973"));
        autorNuevo.setFechaFallecimiento(Date.valueOf("01-01-2023"));
        autorNuevo.setNacionalidad("español");
        em.persist(autorNuevo);

        ObrasEntity nuevaObra = new ObrasEntity();
        nuevaObra.setTitulo("titulo obra");
        nuevaObra.setFecha(Date.valueOf("01-01-2017"));
        nuevaObra.setIdMuseo(1);
        nuevaObra.setIdAutor(autorNuevo.getId());
        nuevaObra.setAutoresByIdAutor(autorNuevo);

        em.persist(nuevaObra);
        transaction.commit();
    }
}
