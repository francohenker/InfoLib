package InfoLib.db;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;

public class Conexion {
    private static EntityManagerFactory emf = null;

    public static void crearEntityManagerFactory() {
        if (emf == null) {
            emf = Persistence.createEntityManagerFactory("InfoLib");
        }
    }

    public static void cerrarEntityManagerFactory() {
        if (emf != null) {
            emf.close();
        }
    }

    public static EntityManager crearEntityManager() {
        return emf.createEntityManager();
    }

    public static EntityManagerFactory getEntityManagerFactory() {
        return emf;
    }





    }
