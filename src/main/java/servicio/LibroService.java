package servicio;

import Repositorio.Repositorio;
import jakarta.persistence.TypedQuery;
import modelo.CopiaLibro;
import modelo.Libro;
import modelo.Rack;

import java.util.List;

public class LibroService {
    private Repositorio repositorio;

    public LibroService(Repositorio repositorio) {
        this.repositorio = repositorio;
    }
    // persiste los libros en la base de datos en caso de no encontrar libro con igual isbn
    public void guardarLibro(Libro libro) {

        if (!buscarLibroPorIsbn(libro.getIsbn()).isEmpty()) {
            throw new RuntimeException("El libro se encuentra en la biblioteca");
        }
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(libro);
        this.repositorio.confirmarTransaccion();
    }

    // persiste las copias del libro en la base de datos
    public void guardarCopia(CopiaLibro copia, int nCopias) {
        if (nCopias < 1) {
            throw new RuntimeException("Número de copias invalido");
        }

        this.repositorio.iniciarTransaccion();

        for (int i = 0; i < nCopias; i++) {
            CopiaLibro nuevaCopia = new CopiaLibro(copia.getTipo(), copia.getLibro(), copia.getEstado(), copia.getPrecio(), copia.getRack(), copia.isCopiaReferencia());
            this.repositorio.insertar(nuevaCopia);
        }
        this.repositorio.confirmarTransaccion();
    }

    // busca libros que coincidan con el titulo
    public List<Libro> buscarLibroPorTitulo(String titulo) {
        TypedQuery<Libro> query = repositorio.getEntityManager().createQuery("FROM Libro libro WHERE libro.titulo LIKE :titulo", Libro.class);
        query.setParameter("titulo", "%" + titulo.toUpperCase() + "%");
        return query.getResultList();
    }

    //busca un libro por isbn, lo usa el metodo guardarLibro para saber si existe algun libro con este isbn en la base de datos
    protected List<Libro> buscarLibroPorIsbn(String isbn) {
        this.repositorio.iniciarTransaccion();
        TypedQuery<Libro> query = repositorio.getEntityManager().createQuery("FROM Libro libro WHERE libro.ISBN = :isbn", Libro.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();
    }

    //busca libros que coincidan con el autor
    public List<Libro> buscarLibroPorAutor(String autor) {
        this.repositorio.iniciarTransaccion();
        TypedQuery<Libro> query = repositorio.getEntityManager().createQuery("SELECT l FROM Libro l WHERE :autor MEMBER OF l.autores", Libro.class);
        query.setParameter("autor", autor);
        return query.getResultList();

    }

    // busca libros que coincidan con la tematica
    public List<Libro> buscarLibroPorTematica(String tematica) {
        this.repositorio.iniciarTransaccion();
        TypedQuery<Libro> query = repositorio.getEntityManager().createQuery("FROM Libro libro WHERE libro.tematica LIKE :tematica", Libro.class);
        query.setParameter("tematica", "%" + tematica.toUpperCase() + "%");
        return query.getResultList();
    }

    // busca copias de un libro por isbn
    //AGREGAR VALIDACION PARA MOSTRAR SOLAMENTE LAS COPIAS QUE NO ESTEN PRESTADAS
    public List<CopiaLibro> buscarCopiasPorIsbn(Libro libro){
//        this.repositorio.iniciarTransaccion();
        TypedQuery<CopiaLibro> query = repositorio.getEntityManager().createQuery("SELECT c FROM CopiaLibro c WHERE c.libro.ISBN = :isbn", CopiaLibro.class);
        query.setParameter("isbn", libro.getIsbn());
        return query.getResultList();

    }


}
