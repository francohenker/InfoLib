package Repositorio;

import jakarta.persistence.*;
import modelo.*;

import java.io.Serializable;
import java.util.List;

public class LibroRepositorio implements Serializable {
    EntityManagerFactory emf;

    public LibroRepositorio(EntityManagerFactory emf){
        this.emf = emf;
    }

    public void guardarLibro(Libro libro){
        EntityManager em = this.emf.createEntityManager();
        if(!buscarLibroPorIsbn(libro.getIsbn()).isEmpty()){
            throw new RuntimeException("El libro se encuentra en la biblioteca");
        }



        em.getTransaction().begin();
        em.persist(libro);
        em.getTransaction().commit();
        em.close();
    }
    public void guardarCopiar(CopiaLibro copia, int nCopias){
        if(nCopias < 0){
            throw new RuntimeException("Número de copias negativo");
        }
        EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();

        for (int i = 0; i < nCopias; i++){
            em.persist(copia);
        }
        em.getTransaction().commit();
        em.close();
    }

    public List<Libro> buscarLibroPorTitulo(String titulo) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("FROM Libro libro WHERE libro.titulo LIKE :titulo", Libro.class);
            query.setParameter("titulo", "%" + titulo + "%");
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    public List<Libro> buscarLibroPorIsbn(String isbn) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("FROM Libro libro WHERE libro.ISBN = :isbn", Libro.class);
            query.setParameter("isbn", isbn);
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    // PROBAR SI ANDA
    public List<CopiaLibro> buscarCopias(Libro libro){
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT COUNT(c) FROM CopiaLibro c WHERE c.libro = :libro AND c.estado = :estado", Long.class);
            query.setParameter("libro", libro);
            query.setParameter("estado", EstadoLibro.DISPONIBLE);
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }

    }
}
