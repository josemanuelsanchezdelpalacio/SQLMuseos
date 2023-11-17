import database.Consultas;
import database.EmfSingleton;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import libs.Leer;

import static libs.Leer.pedirCadena;

public class Main {

    static EntityManagerFactory emf = EmfSingleton.getInstance().getEmf();
    public static EntityManager em = emf.createEntityManager();

    public static void main(String[] args) {
        boolean salir = false;
        int opcion;
        do {
            System.out.println("0. Salir");
            System.out.println("1. Ej1: Listar obra");
            System.out.println("2. Ej1b: Modificar museo");
            System.out.println("3. Ej2: Insertar nueva obra");

            opcion = Leer.pedirEntero("Introduce una opción: ");

            switch (opcion) {
                case 0 -> {salir = true;}
                case 1 -> {Consultas.listarObras(em);}
                case 2 -> {Consultas.modificarMuseo(em);}
                case 3 -> {Consultas.insertarObra(em);}
                default -> {System.out.println("La opcion seleccionada no existe");}
            }
            desconectar();
        } while (!salir);
    }

    private static void desconectar() {
        em.close();
        emf.close();
    }
}