package Repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import modelo.Bibliotecario;
import modelo.Libro;
import modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio {
    EntityManagerFactory emf;

    public UsuarioRepositorio(EntityManagerFactory emf){
        this.emf = emf;
    }

    public void guardarUsuario(Usuario usuario){
        EntityManager em = emf.createEntityManager();
        if(!buscarPorDni(usuario.getDni()).isEmpty()){
            throw new RuntimeException("Usuario existente en la base de datos");
        }
        em.persist(usuario);
    }

    //CORREGIR
    public List<Usuario> buscarPorDni(String dni) {
        EntityManager em = this.emf.createEntityManager();

        try {
            em.getTransaction().begin();

            // Buscar en Usuario
            Query query = em.createQuery("FROM Usuario u WHERE u.dni = :dniBuscado", Libro.class);
            query.setParameter("dniBuscado", dni);
//            return query.getResultList().get(0);

            if (!query.getResultList().isEmpty()) {
                return query.getResultList();
            }

            // Buscar en Bibliotecario
            Query query2 = em.createQuery("FROM Bibliotecario b WHERE b.dni = :dniBuscado", Libro.class);
            query.setParameter("dniBuscado", dni);

            return query2.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}
