package servicio;
import Repositorio.Repositorio;
import jakarta.persistence.TypedQuery;
import modelo.*;

import java.time.LocalDateTime;
import java.util.List;

public class PrestamoService {
    private Repositorio repositorio;

    public PrestamoService(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    /**
     * VERIFICAR
     * no mas de 5 prestamos activos para un usuario
     * no realizar el prestamo si se supero la fecha de entrega de un libro y este no se entrego
     **/

    public void guardarPrestamo(CopiaLibro copia, Usuario usuario) {
        try{
            validarPrestamo(copia, usuario);
            Prestamo prestamo = new Prestamo(copia, usuario);
            this.repositorio.iniciarTransaccion();
            this.repositorio.insertar(prestamo);
            this.repositorio.modificar(copia);
            this.repositorio.confirmarTransaccion();
        }catch (Exception e){
            this.repositorio.descartarTransaccion();
            throw new RuntimeException(e.getMessage());
        }


    }

    private boolean validarPrestamo(CopiaLibro copia, Usuario usuario){
        if(usuario == null){
            throw new RuntimeException("No se encontro el usuario");
        }
        if(copia.isCopiaReferencia()){
            throw new RuntimeException("No se puede realizar un prestamo de una copia de referencia");
        }
        if(usuario.getEstado() == EstadoMiembro.BAJA){
            throw new RuntimeException("El usuario se encuentra dado de baja");
        }
        if(buscarPrestamoPorUsuario(usuario).size() >= 5){
            throw new RuntimeException("Solo se puede tener 5 prestamos activos");
        }
        if(tienePrestamoAtrasado(usuario)){
            throw new RuntimeException("El usuario tiene prestamos atrasados");
        }
        if(copia == null){
            throw new RuntimeException("No se encontro el libro");
        }
        if(copia.getEstado() != EstadoLibro.DISPONIBLE){
            throw new RuntimeException("El libro no se encuentra disponible");
        }
        return true;
    }

    //CONTROLAR COMO MOSTRAR LA MULTA EN CASO DE HABER UNA
    // setea la fecha de devolucion del prestamo
    public void devolverPrestamo(Prestamo prestamo){
        if(prestamo.getCopiaLibro().getEstado() == EstadoLibro.PERDIDO){
            throw new RuntimeException("El libro no se encuentra perdido");
        }
        if(prestamo.getCopiaLibro().getEstado() == EstadoLibro.DISPONIBLE){
            throw new RuntimeException("El libro no se encuentra prestado");
        }

        this.repositorio.iniciarTransaccion();
        prestamo.devolverLibro();
        this.repositorio.modificar(prestamo);
        this.repositorio.modificar(prestamo.getCopiaLibro());
        if(prestamo.getMulta() > 0){
            Ventana.confirmacion("Multas", String.format("El usuario tiene una multa de $%.2f", prestamo.getMulta()));
        }
        this.repositorio.confirmarTransaccion();


    }

    //busca la cantidad de prestamos que tiene actuales un usuario
    public List<Prestamo> buscarPrestamoPorUsuario(Usuario usuario) {

        TypedQuery<Prestamo> query = repositorio.getEntityManager().createQuery("FROM Prestamo p WHERE p.usuario = :usuario AND p.fechaDevolucion IS NULL", Prestamo.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();


    }

    // busca si el usuario tiene prestamos atrasados
    protected boolean tienePrestamoAtrasado(Usuario usuario) {
        List<Prestamo> prestamos = buscarPrestamoPorUsuario(usuario);
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getFechaPrestamo().plusDays(10).isBefore(LocalDateTime.now())) {
                return true;
            }
        }
        return false;
    }





    public List<Prestamo> obtenerTodos(){
        return this.repositorio.obtenerTodos(Prestamo.class);

    }

//        //IMPLEMENTAR
//    public List<Usuario> getUsuariosPorLibro(Libro libro){
//
//    }
//    //IMPLEMENTAR
//    public List<Libro> getLibrosPorUsuario(Usuario usuario){
//
//    }
}