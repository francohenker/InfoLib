package modules;

import java.util.ArrayList;

public class Bibliotecario implements Miembro {
    private String dni;
    private String nombre;
    private String apellido;
    private EstadoMiembro estado = EstadoMiembro.ALTA;


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
