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
        this.repositorio.iniciarTransaccion();
        if(!buscarPorDni(usuario.getDni()).isEmpty()){
            throw new RuntimeException("Usuario existente en la base de datos");
        }
        this.repositorio.insertar(usuario);
        this.repositorio.confirmarTransaccion();
    }

    //busca un usuario por dni en la base de datos
    protected List<Usuario> buscarPorDni(String dni) {
        if(!Usuario.isValid(dni)){
            throw new RuntimeException("Dni invalido");
        }
        TypedQuery<Usuario> query =  repositorio.getEntityManager().createQuery("FROM Usuario WHERE dni = :dniBuscado", Usuario.class);
        query.setParameter("dniBuscado", dni);
        return query.getResultList();
        }
    }


