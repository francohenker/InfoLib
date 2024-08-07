package servicio;

import Repositorio.Repositorio;
import jakarta.persistence.TypedQuery;
import modelo.Bibliotecario;
import modelo.EstadoMiembro;
import modelo.Usuario;

import java.util.Base64;
import java.util.List;

public class UsuarioService {
    private Repositorio repositorio;

    public UsuarioService(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    // guarda un usuario en la base de datos
    public void guardarUsuario(Usuario usuario){
        if(buscarPorDni(usuario.getDni()) != null){
            throw new RuntimeException("Usuario existente en la base de datos");
        }
        if(usuario.getEstado() == EstadoMiembro.BAJA){
            throw new RuntimeException("No se puede registrar un usuario dado de baja");
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


    //modifica el estado de un usuario
    public void modificarUsuario(Usuario usuario, EstadoMiembro estado){
        if(usuario.getEstado() == estado){
            return;
        }
        usuario.setEstado(estado);
        this.repositorio.iniciarTransaccion();
        this.repositorio.modificar(usuario);
        this.repositorio.confirmarTransaccion();
    }

    //modifica el estado de un usuario y su contraseña
    public void modificarUsuario(Usuario usuario, EstadoMiembro estado, String contraseña){
        boolean actualizar = false;

        if(usuario.getEstado() != estado){
            usuario.setEstado(estado);
            actualizar = true;
        }
        if(!usuario.checkContraseña(contraseña)){
            usuario.setContraseña(contraseña);
            actualizar = true;
        }
        if(actualizar){
            this.repositorio.iniciarTransaccion();
            this.repositorio.modificar(usuario);
            this.repositorio.confirmarTransaccion();
        }
    }

    public boolean esBibliotecario(Long usuarioId) {

        try {
            String hql = "SELECT COUNT(b) FROM Bibliotecario b WHERE b.id = :usuarioId";
            Long count = this.repositorio.getEntityManager().createQuery(hql, Long.class)
                    .setParameter("usuarioId", usuarioId)
                    .getSingleResult();
            return count > 0;
        } finally {
            this.repositorio.cerrar();
        }
    }

    public boolean checkContraseña(String contraseña, String dni){
        if(contraseña == null || contraseña.isEmpty()){
            throw new RuntimeException("La contraseña no puede ser vacia");
        }
        if(!Usuario.isValid(dni)){
            throw new RuntimeException("Dni invalido");
        }
        var usuario = buscarPorDni(dni);
        if(usuario == null){
            throw new RuntimeException("Usuario no encontrado");
        }

        return usuario.checkContraseña(contraseña);
    }

    public List<Usuario> obtenerTodos(){
        return this.repositorio.obtenerTodos(Usuario.class);
    }
}


