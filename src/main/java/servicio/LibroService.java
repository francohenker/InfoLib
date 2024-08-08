package servicio;

import Repositorio.Repositorio;
import jakarta.persistence.TypedQuery;
import modelo.CopiaLibro;
import modelo.EstadoLibro;
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
            try{
                CopiaLibro nuevaCopia = new CopiaLibro(copia.getTipo(), copia.getLibro(), copia.getEstado(), copia.getPrecio(), copia.getRack(), copia.isCopiaReferencia());
                this.repositorio.insertar(nuevaCopia);

            }catch (Exception e){
                this.repositorio.descartarTransaccion();
            }
        }
        this.repositorio.confirmarTransaccion();

    }


    //
    public void modifcarEstado(CopiaLibro copia, EstadoLibro estado){
        if(estado == EstadoLibro.PRESTADO){
            throw new RuntimeException("No se puede modificar el estado directamente a prestado");
        }
        copia.setEstado(estado);
        this.repositorio.modificar(copia);
    }

    // busca libros que coincidan con el titulo
    public List<Libro> buscarLibroPorTitulo(String titulo) {
        TypedQuery<Libro> query = repositorio.getEntityManager().createQuery("FROM Libro libro WHERE libro.titulo LIKE :titulo", Libro.class);
        query.setParameter("titulo", "%" + titulo.toUpperCase() + "%");
        return query.getResultList();
    }

    //busca un libro por isbn, lo usa el metodo guardarLibro para saber si existe algun libro con este isbn en la base de datos
    protected List<Libro> buscarLibroPorIsbn(String isbn) {
        TypedQuery<Libro> query = repositorio.getEntityManager().createQuery("FROM Libro libro WHERE libro.ISBN = :isbn", Libro.class);
        query.setParameter("isbn", isbn);
        return query.getResultList();

    }

    //busca libros que coincidan con el autor
    public List<Libro> buscarLibroPorAutor(String autor) {
        TypedQuery<Libro> query = repositorio.getEntityManager().createQuery("SELECT l FROM Libro l WHERE :autor MEMBER OF l.autores", Libro.class);
        query.setParameter("autor", autor);
        return query.getResultList();

    }

    // busca libros que coincidan con la tematica
    public List<Libro> buscarLibroPorTematica(String tematica) {
        TypedQuery<Libro> query = repositorio.getEntityManager().createQuery("FROM Libro libro WHERE libro.tematica LIKE :tematica", Libro.class);
        query.setParameter("tematica", "%" + tematica.toUpperCase() + "%");
        return query.getResultList();
    }

    // busca copias de un libro por isbn
    public List<CopiaLibro> buscarCopiasPorIsbn(Libro libro){
        TypedQuery<CopiaLibro> query = repositorio.getEntityManager().createQuery("SELECT c FROM CopiaLibro c WHERE c.libro.ISBN = :isbn AND c.estado = 'DISPONIBLE'", CopiaLibro.class);
        query.setParameter("isbn", libro.getIsbn());
        return query.getResultList();

    }

    public List<Libro> obtenerTodos(){
        return this.repositorio.obtenerTodos(Libro.class);
    }
}
