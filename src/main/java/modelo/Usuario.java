package modelo;

import jakarta.persistence.*;

import java.util.Base64;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Entity
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "usuario")

public class Usuario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "dni", unique = true, nullable = false, length = 20)
    private String dni;
    @Column(name = "nombre", length = 30, nullable = false)
    private String nombre;
    @Column(name = "apellido", length = 50, nullable = false)
    private String apellido;
    @Enumerated(EnumType.STRING)
    @Column(name = "estado", length = 4, nullable = false)
    private EstadoMiembro estado = EstadoMiembro.ALTA;
    @Column(name = "contraseña", length = 100, nullable = false)
    private String contraseña;

    protected Usuario(){    }

    public Usuario(String dni, String nombre, String apellido, String contraseña){
        if(!isValid(dni)){
            throw new RuntimeException("Dni invalido");
        }
        if(nombre == null || nombre.isEmpty()){
            throw new RuntimeException("El nombre no puede ser vacio");
        }
        if(apellido == null || apellido.isEmpty()){
            throw new RuntimeException("El apellido no puede ser vacio");
        }
        if(contraseña == null || contraseña.isEmpty()){
            throw new RuntimeException("La contraseña no puede ser vacia");
        }
        if(contraseña.length() < 8){
            throw new RuntimeException("La contraseña debe tener al menos 8 caracteres");
        }
        if(contraseña.length() > 20){
            throw new RuntimeException("La contraseña debe tener menos de 20 caracteres");
        }
        this.dni = dni;
        this.nombre = nombre.toUpperCase();
        this.apellido = apellido.toUpperCase();
        this.contraseña = Base64.getEncoder().encodeToString(contraseña.getBytes());
    }

    // metodo estatico para comprobar si un dni es valido
    public static boolean isValid(String dni){
        String dniRegex = "^\\d{8}$";
        Pattern pattern = Pattern.compile(dniRegex);
        Matcher matcher = pattern.matcher(dni);
        return matcher.matches();
    }

    public String getDni(){
        return this.dni;
    }

    public String getNombre(){
        return this.nombre;
    }

    public String getApellido(){
        return this.apellido;
    }

    public EstadoMiembro getEstado(){
        return this.estado;
    }

    public void setEstado(EstadoMiembro estado){
        this.estado = estado;
    }

    public void setContraseña(String contraseña){
        this.contraseña = Base64.getEncoder().encodeToString(contraseña.getBytes());
    }

    public boolean isAlta(){
        return (this.estado == EstadoMiembro.ALTA);
    }

    public boolean checkContraseña(String contraseña){
        return this.contraseña.equals(Base64.getEncoder().encodeToString(contraseña.getBytes()));
    }

    @Override
    public String toString() {
        return  "id=" + id +
                ", dni='" + dni + '\'' +
                ", nombre='" + nombre + '\'' +
                ", apellido='" + apellido + '\'' +
                ", estado=" + estado;
    }


}
