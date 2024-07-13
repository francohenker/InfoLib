package modelo;

import java.util.ArrayList;
import jakarta.persistence.*;

public class Bibliotecario implements Miembro {
    @Id
    @Column(name = "dni", unique = true, nullable = false)
    private String dni;
    @Column(name = "nombre", length = 30, nullable = false)
    private String nombre;
    @Column(name = "apellido", length = 50, nullable = false)
    private String apellido;
    private EstadoMiembro estado = EstadoMiembro.ALTA;


    protected Bibliotecario(){    }

    public Bibliotecario(String dni, String nombre, String apellido){
        this.dni = dni;
        this.nombre = nombre.toUpperCase();
        this.apellido = apellido.toUpperCase();
    }
    // IMPLEMENTAR DESPUES
    public ArrayList<Libro> buscarTitulo(String titulo){
        return new ArrayList<>();

    }

    public ArrayList<Libro> buscarAutor(String autor){
        return new ArrayList<>();
    }

    public ArrayList<Libro> buscarTematica(String tematica){
        return new ArrayList<>();

    }


    public void createPrestamo(Miembro miembro, Libro libro){

    }

    public void addLibro(Libro libro){

    }

    public void removeLibro(Libro libro){

    }
}
