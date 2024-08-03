package servicio;
import Repositorio.Repositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import modelo.CopiaLibro;
import modelo.Prestamo;
import modelo.Usuario;

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

//        if(buscarPrestamoPorUsuario(usuario).size() >= 5){
//            throw new RuntimeException("Solo se puede tener 5 prestamos activos");
//        }
//        if(tienePrestamoAtrasado(usuario)){
//            throw new RuntimeException("El usuario tiene prestamos atrasados");
//        }


        Prestamo prestamo = new Prestamo(copia, usuario);
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(prestamo);
        this.repositorio.confirmarTransaccion();
    }

    //PROBAR
    //busca la cantidad de prestamos que tiene actuales un usuario
    protected List<Prestamo> buscarPrestamoPorUsuario(Usuario usuario) {
        this.repositorio.iniciarTransaccion();
        TypedQuery<Prestamo> query = repositorio.getEntityManager().createQuery("FROM Prestamo p WHERE p.fechaDevolucion IS NULL", Prestamo.class);
        query.setParameter("usuario", usuario);
        return query.getResultList();

    }

    //PROBAR
    // busca si el usuario tiene prestamos atrasados
    protected boolean tienePrestamoAtrasado(Usuario usuario) {
        List<Prestamo> prestamos = buscarPrestamoPorUsuario(usuario);
        for (Prestamo prestamo : prestamos) {
            if (prestamo.getFechaPrestamo().plusDays(10).isAfter(LocalDateTime.now())) { //VERIFICAR
                return true;
            }
        }
        return false;
    }

    //    //IMPLEMENTAR
//    public List<Usuario> getUsuariosPorLibro(Libro libro){
//
//    }
//    //IMPLEMENTAR
//    public List<Libro> getLibrosPorUsuario(Usuario usuario){
//
//    }
}