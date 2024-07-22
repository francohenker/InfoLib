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

    // persiste los libros en la base de datos, en caso de no encontrar libro con igual isbn
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

    // persiste las copias del libro en la base de datos
    public void guardarCopiar(CopiaLibro copia, int nCopias){
        if(nCopias < 1){
            throw new RuntimeException("Número de copias invalido");
        }
        EntityManager em = this.emf.createEntityManager();
        em.getTransaction().begin();

        for (int i = 0; i < nCopias; i++) {
            CopiaLibro nuevaCopia = new CopiaLibro(copia.getTipo(), copia.getLibro(), copia.getEstado(), copia.getPrecio(), copia.isCopiaReferencia());
            em.persist(nuevaCopia);
        }
        em.getTransaction().commit();
        em.close();
    }

    // busca libros por titulo
    public List<Libro> buscarLibroPorTitulo(String titulo) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("FROM Libro libro WHERE libro.titulo LIKE :titulo", Libro.class);
            query.setParameter("titulo", "%" + titulo.toUpperCase() + "%");
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    //busca un libro por isbn, lo usa guardarLibro para saber si existe algun libro con este isbn en la base de datos
    protected List<Libro> buscarLibroPorIsbn(String isbn) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("FROM Libro libro WHERE libro.ISBN = :isbn");
            query.setParameter("isbn", isbn);
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    //ARREGLAR
    public List<Libro> buscarLibroPorAutor(String autor) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("SELECT l FROM Libro l WHERE :autor MEMBER OF l.autores", Libro.class);
            query.setParameter("autor", autor);
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    // busca un libros por tematica
    public List<Libro> buscarLibroPorTematica(String tematica) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("FROM Libro libro WHERE libro.tematica LIKE :tematica", Libro.class);
            query.setParameter("tematica", "%" + tematica + "%");
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }

    // VER DONDE SE IMPLEMENTA
//    public List<CopiaLibro> buscarCopias(Libro libro){
//        EntityManager em = emf.createEntityManager();
//        try {
//            Query query = em.createQuery("SELECT c FROM CopiaLibro c WHERE c.libro = :libro AND c.estado = :estado", CopiaLibro.class);
//            query.setParameter("libro", libro);
//            query.setParameter("estado", EstadoLibro.DISPONIBLE);
//            return query.getResultList();
//        } finally {
//            if (em.isOpen()) {
//                em.close();
//            }
//        }
//    }
}
