package modelo;

import java.util.ArrayList;

public interface Miembro {
    public ArrayList<Libro> buscarTitulo(String titulo);
    public ArrayList<Libro> buscarAutor(String autor);
    public ArrayList<Libro> buscarTematica(String tematica);
}
