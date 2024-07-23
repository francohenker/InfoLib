package Repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import modelo.Libro;
import modelo.Prestamo;
import modelo.Usuario;
import modelo.CopiaLibro;

import java.time.LocalDateTime;
import java.util.List;

public class PrestamoRepositorio {
    EntityManagerFactory emf;

    public PrestamoRepositorio(EntityManagerFactory emf) {
        this.emf = emf;
    }
    /**
     VERIFICAR
     * no mas de 5 prestamos activos para un usuario
     * no realizar el prestamo si se supero la fecha de entrega de un libro y este no se entrego
     *
     **/
    public void guardarPrestamo(CopiaLibro copia, Usuario usuario) {
        EntityManager em = emf.createEntityManager();

//        if(buscarPrestamoPorUsuario(usuario).size() >= 5){
//            throw new RuntimeException("Solo se puede tener 5 prestamos activos");
//        }
//        if(tienePrestamoAtrasado(usuario)){
//            throw new RuntimeException("El usuario tiene prestamos atrasados");
//        }




        Prestamo prestamo = new Prestamo(copia, usuario);
        em.getTransaction().begin();
        em.persist(prestamo);
        em.getTransaction().commit();
        em.close();
    }
    //PROBAR
    protected List<Prestamo> buscarPrestamoPorUsuario(Usuario usuario) {
        EntityManager em = emf.createEntityManager();
        try {
            Query query = em.createQuery("FROM Prestamo p WHERE p.fechaDevolucion IS NULL", Prestamo.class);
//            query.setParameter("usuario", usuario);
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
    //PROBAR
    protected boolean tienePrestamoAtrasado(Usuario usuario){
        List<Prestamo> prestamos = buscarPrestamoPorUsuario(usuario);
        for(Prestamo prestamo : prestamos){
            if(prestamo.getFechaPrestamo().plusDays(10).isAfter(LocalDateTime.now())){ //VERIFICAR
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
