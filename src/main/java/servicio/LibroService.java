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
        if(libro.getIsbn().trim().isEmpty()){
            throw new RuntimeException("ISBN no válido");
        }
        if (!buscarLibroPorIsbn(libro.getIsbn()).isEmpty()) {
            throw new RuntimeException("El libro se encuentra en la biblioteca");
        }
        if(libro.getAutores().isEmpty()){
            throw new RuntimeException("Debe indicar al menos un autor");
        }
        if(libro.getEditorial().isEmpty()){
            throw new RuntimeException("Debe indicar una editorial");
        }
        if(libro.getTematica().isEmpty()){
            throw new RuntimeException("Debe indicar una tematica");
        }
        if(libro.getIdioma().isEmpty()){
            throw new RuntimeException("Debe indicar un idioma");
        }
        if(contieneNumeros(libro.getIdioma())){
            throw new RuntimeException("El idioma no puede contener números");
        }
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(libro);
        this.repositorio.confirmarTransaccion();

    }

    //eliminar un libro simpre y cuando no posea copias
    public void eliminarLibro(Libro libro) {
        if(!buscarCopiasPorIsbn(libro).isEmpty()){
            throw new RuntimeException("No se puede eliminar un libro que tenga copias");
        }
        this.repositorio.iniciarTransaccion();
        this.repositorio.eliminar(libro);
        this.repositorio.confirmarTransaccion();
    }

    // persiste las copias del libro en la base de datos
    public void guardarCopia(CopiaLibro copia, int nCopias) {
        if (nCopias < 1) {
            throw new RuntimeException("Número de copias invalido");
        }
        if(copia.getLibro() == null){
            throw new RuntimeException("Debe indicar un libro");
        }
        if(copia.getTipo() == null){
            throw new RuntimeException("Debe indicar un tipo");
        }
        if(copia.getEstado() == EstadoLibro.PERDIDO || copia.getEstado() == EstadoLibro.PRESTADO){
            throw new RuntimeException("No se puede crear copias prestadas o perdidas");
        }
        if(copia.getPrecio() < 0){
            throw new RuntimeException("El precio debe ser positivo");
        }
        if(copia.getRack() == null){
            throw new RuntimeException("Debe indicar un rack");
        }

        this.repositorio.iniciarTransaccion();

        for (int i = 0; i < nCopias; i++) {
            try{
                CopiaLibro nuevaCopia = new CopiaLibro(copia.getTipo(), copia.getLibro(), copia.getEstado(), copia.getPrecio(), copia.getRack(), copia.isCopiaReferencia());
                this.repositorio.insertar(nuevaCopia);

            }catch (Exception e){
                this.repositorio.descartarTransaccion();
                throw new RuntimeException("Error al crear copia\n" + e.getMessage());
            }
        }
        this.repositorio.confirmarTransaccion();
    }


    // modifica una copialibro
    public void modifcarCopia(CopiaLibro copia, EstadoLibro estado, Rack rack, double precio){
        if(estado == EstadoLibro.PRESTADO){
            throw new RuntimeException("No se puede modificar el estado directamente a prestado");
        }

        this.repositorio.iniciarTransaccion();
        copia.setEstado(estado);
        copia.setRack(rack);
        copia.updatePrecio(precio);
        this.repositorio.modificar(copia);
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
        TypedQuery<CopiaLibro> query = repositorio.getEntityManager().createQuery("SELECT c FROM CopiaLibro c WHERE c.libro.ISBN = :isbn", CopiaLibro.class);
        query.setParameter("isbn", libro.getIsbn());
        return query.getResultList();

    }

    private boolean contieneNumeros(String string){
        return string.chars().anyMatch(Character::isDigit);
    }

    public List<Libro> obtenerTodos(){
        return this.repositorio.obtenerTodos(Libro.class);
    }
}
