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
        MuseosEntity m = em.createQuery("from MuseosEntity", MuseosEntity.class).getSingleResult();
        //a partir de aquí trabajamos sobre el objeto instanciado que representa un registro de la base de datos

        //al hacer el commit los cambios se pasan a la base de datos
        transaction.commit();
    }

    public static void insertarObra(EntityManager em){
        EntityTransaction transaction = em.getTransaction();
        //comenzamos a crear el contexto de persistencia
        transaction.begin();
        ObrasEntity o = new ObrasEntity();
        o.setTitulo("OBRA AUTOR NUEVO");
        o.setFecha(Date.valueOf("1923-12-07"));
        o.setIdMuseo(1);
        o.setIdAutor(5);

        AutoresEntity a = new AutoresEntity();
        a.setNombre("NOMBRE NUEVO AUTOR");
        a.setFechaNacimiento(Date.valueOf("1899-12-08"));
        a.setFechaFallecimiento(Date.valueOf("1977-06-03"));
        a.setNacionalidad("Español");

        Query queryDep = em.createQuery("from ObrasEntity");
        ObrasEntity nuevaObra = (ObrasEntity) queryDep.getSingleResult();
        o.setAutoresByIdAutor(nuevaObra.getAutoresByIdAutor());
        em.persist(o);
        transaction.commit();
    }
}
