package modelo;

import jakarta.persistence.*;

import java.util.ArrayList;


@Entity
@Table(name = "usuario")
public class Usuario implements Miembro {
    @Id
    @Column(name = "dni", unique = true, nullable = false)
    private String dni;
    @Column(name = "nombre", length = 30, nullable = false)
    private String nombre;
    @Column(name = "apellido", length = 50, nullable = false)
    private String apellido;
    private EstadoMiembro estado = EstadoMiembro.ALTA;

    protected Usuario(){

    }
    public Usuario(String dni, String nombre, String apellido){
        this.dni = dni;
        this.nombre = nombre.toUpperCase();
        this.apellido = apellido.toUpperCase();
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
