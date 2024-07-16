package Repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import modelo.*;

public class LibroRepositorio {
    EntityManagerFactory emf;

    public LibroRepositorio(EntityManagerFactory emf){
        this.emf = emf;
    }

    public void guardarLibro(Libro libro){
        EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();
        em.persist(libro);
        em.getTransaction().commit();
        em.close();
    }
    public void guardarCopiar(CopiaLibro copia, int nCopias){
        EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();
        for (int i = 0; i < nCopias; i++){
            em.persist(copia);
        }
        em.getTransaction().commit();
        em.close();
    }


}
