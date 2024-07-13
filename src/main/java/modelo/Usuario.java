package modelo;

import jakarta.persistence.*;

import java.util.ArrayList;


@Entity
@Table(name = "usuario", schema = "public")
public class Usuario implements Miembro {
    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dni", unique = true, nullable = false)
    private String dni;
    private String nombre;
    private String apellido;
    private EstadoMiembro estado = EstadoMiembro.ALTA;

    public Usuario(){

    }
    public Usuario(String dni, String nombre, String apellido){
        this.dni = dni;
        this.nombre = nombre;
        this.apellido = apellido;
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


    //IMPLEMENTAR DESPUES
    public ArrayList<Libro> buscarTitulo(String titulo){
        return new ArrayList<>();

    }

    public ArrayList<Libro> buscarAutor(String autor){
        return new ArrayList<>();
    }

    public ArrayList<Libro> buscarTematica(String tematica){
        return new ArrayList<>();

    }


    




}
