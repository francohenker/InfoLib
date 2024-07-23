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

    //BUSCA UN USUARIO POR DNI EN LA BASE DE DATOS
    public List<Usuario> buscarPorDni(String dni) {
        EntityManager em = this.emf.createEntityManager();
        var dni2 = dni;
        try {
            em.getTransaction().begin();

            // Buscar en Usuario
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

