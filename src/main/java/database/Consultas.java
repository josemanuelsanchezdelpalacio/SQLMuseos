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

        //a partir de aqui trabajamos sobre el objeto instanciado que representa un registro de la base de datos
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

    public static void modificar(EntityManager em) {

        // Pedir al usuario que proporcione el nombre de la tabla
        String tableName = Leer.pedirCadena("Ingrese el nombre de la tabla que deseas modificar: ");

        // Comprobar que la tabla existe en la base de datos
        boolean tableExists = em.createNativeQuery("SHOW TABLES LIKE ?").setParameter(1, tableName).getResultList().size() > 0;
        if (tableExists) {
            // Mostrar las columnas de la tabla seleccionada
            List<String> columnNames = em.createNativeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?").setParameter(1, tableName).getResultList();
            System.out.println("Columnas de la tabla " + tableName + ":");

            for (String columnName : columnNames) {
                System.out.println(columnName);
            }

            // Pedir al usuario que proporcione el nombre de la columna
            String columnName = Leer.pedirCadena("Ingrese el nombre de la columna que deseas modificar: ");

            // Comprobar que la columna existe en la tabla seleccionada
            boolean columnExists = em.createNativeQuery("SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ? AND COLUMN_NAME = ?").setParameter(1, tableName).setParameter(2, columnName).getResultList().size() > 0;
            if (!columnExists) {
                System.out.println("La columna introducida no existe en la tabla " + tableName + ".");
                modificar(em);
            }

            // Pedir al usuario que proporcione el valor de la columna
            String columnValue = Leer.pedirCadena("Ingrese el valor de la columna: ");
            modificar(em);

            // Comprobar que el valor de la columna existe en la tabla seleccionada
            boolean columnValueExists = em.createNativeQuery("SELECT " + columnName + " FROM " + tableName + " WHERE " + columnName + " = ?").setParameter(1, columnValue).getResultList().size() > 0;
            if (!columnValueExists) {
                System.out.println("El valor de la columna introducido no existe en la tabla " + tableName + ".");
                modificar(em);
            }

            // Pedir al usuario que proporcione el nombre de la columna a modificar
            String newColumnName = columnName;

            // Si el nombre de la columna es ID_USUARIO, ID_ESTADOS, ID_ENTIDADES, ID_PARTICIPACIONES, ID_PROFESIONES, ID_PROYECTOS, ID_ROLES o ID_TAGS, no se puede modificar
            if (newColumnName.equals("ID_USUARIO") || newColumnName.equals("ID_ESTADOS") || newColumnName.equals("ID_ENTIDADES") || newColumnName.equals("ID_PARTICIPACIONES") || newColumnName.equals("ID_PROFESIONES") || newColumnName.equals("ID_PROYECTOS") || newColumnName.equals("ID_ROLES") || newColumnName.equals("ID_TAGS")) {
                System.out.println("No se puede modificar el id ya que es una clave primaria.");
                return;
            }

            // Pedir al usuario que proporcione el nuevo valor de la columna
            String newColumnValue = Leer.pedirCadena("Ingrese el nuevo valor de la columna: ");

            // Crear la sentencia SQL con el nombre de la tabla y los datos a insertar
            String sql = "UPDATE " + tableName + " SET " + newColumnName + " = '" + newColumnValue + "' WHERE " + columnName + " = '" + columnValue + "'";

            // Crear un objeto Query para ejecutar la sentencia SQL
            Query query = em.createNativeQuery(sql);

            // Ejecutar la sentencia SQL
            em.getTransaction().begin();
            int rowsUpdated = query.executeUpdate();
            em.getTransaction().commit();

            System.out.println(rowsUpdated + " fila(s) actualizada(s) en la tabla " + tableName);

        } else {
            System.out.println("La tabla introducida no existe en la base de datos.");
            modificar(em);
        }
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
