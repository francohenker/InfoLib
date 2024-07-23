package Repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import modelo.Usuario;
import java.util.List;

public class UsuarioRepositorio {
    EntityManagerFactory emf;

    public UsuarioRepositorio(EntityManagerFactory emf){
        this.emf = emf;
    }
    // guarda un usuario en la base de datos
    public void guardarUsuario(Usuario usuario){
        EntityManager em = this.emf.createEntityManager();
        if(!buscarPorDni(usuario.getDni()).isEmpty()){
            throw new RuntimeException("Usuario existente en la base de datos");
        }
        em.getTransaction().begin();
        em.persist(usuario);
        em.getTransaction().commit();
        em.close();
    }

    //busca un usuario por dni en la base de datos
    public List<Usuario> buscarPorDni(String dni) {
        if(!Usuario.isValid(dni)){
            throw new RuntimeException("Dni invalido");
        }
        EntityManager em = this.emf.createEntityManager();
        try {
            em.getTransaction().begin();
            Query query = em.createQuery("FROM Usuario WHERE dni = :dniBuscado", Usuario.class);
            query.setParameter("dniBuscado", dni);
            return query.getResultList();
        } finally {
            if (em.isOpen()) {
                em.close();
            }
        }
    }
}

