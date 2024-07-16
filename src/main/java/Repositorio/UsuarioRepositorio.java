package Repositorio;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Query;
import modelo.Libro;
import modelo.Usuario;
import java.util.ArrayList;
import java.util.List;

public class UsuarioRepositorio {
    EntityManagerFactory emf;

    public UsuarioRepositorio(EntityManagerFactory emf){
        this.emf = emf;
    }

    public void guardar(Usuario usuario){
        EntityManager em = emf.createEntityManager();
        em.persist(usuario);
    }

    public List<Libro> buscarTitulo(String titulo){
        EntityManager em = db.Conexion.crearEntityManager();
        try{
            Query query = em.createQuery("select * from libro where like " + titulo);
        }catch(Exception e){}


        return new ArrayList<>();

    }

    public ArrayList<Libro> buscarAutor(String autor){

        return new ArrayList<>();
    }

    public ArrayList<Libro> buscarTematica(String tematica){
        return new ArrayList<>();

    }

}
