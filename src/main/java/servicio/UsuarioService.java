package servicio;

import Repositorio.Repositorio;
import jakarta.persistence.TypedQuery;
import modelo.Usuario;

import java.util.List;

public class UsuarioService {
    private Repositorio repositorio;

    public UsuarioService(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    // guarda un usuario en la base de datos
    public void guardarUsuario(Usuario usuario){
        if(buscarPorDni(usuario.getDni()) == null){
            throw new RuntimeException("Usuario existente en la base de datos");
        }
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(usuario);
        this.repositorio.confirmarTransaccion();
    }

    //busca un usuario por dni en la base de datos
    public Usuario buscarPorDni(String dni) {
        if(!Usuario.isValid(dni)){
            throw new RuntimeException("Dni invalido");
        }
        this.repositorio.iniciarTransaccion();
        TypedQuery<Usuario> query =  repositorio.getEntityManager().createQuery("FROM Usuario WHERE dni = :dniBuscado", Usuario.class);
        query.setParameter("dniBuscado", dni);
        //en caso de no encontrar usuario con ese dni, retorna null
        // de lo contrario retorna el primer elemento (deberia ser el unico)
        this.repositorio.confirmarTransaccion();
        return query.getResultList().isEmpty() ? null : query.getResultList().get(0);
        }
    }


