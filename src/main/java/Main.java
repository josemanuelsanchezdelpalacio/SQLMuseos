import database.Consultas;
import database.EmfSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;

import static libs.Leer.pedirCadena;

public class Main {

    static EntityManagerFactory emf = EmfSingleton.getInstance().getEmf();
    public static EntityManager em = emf.createEntityManager();
    public static void main(String[] args) {
        Consultas.listarObras(em);
        Consultas.insertarObra(em);
        desconectar();
    }

    private static void desconectar() {
        em.close();
        emf.close();
    }
}