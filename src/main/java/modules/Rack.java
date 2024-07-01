package modules;

import java.util.ArrayList;

public class Rack {
    // IMPLEMENTAR EL ID CON LA BD
    private Integer id;
    private String descripcion;
    private ArrayList<CopiaLibro> libros;

    public Rack(){

    }

    // IMPLEMENTAR DESPUES EN CASO DE SER NECESARIO
        
    public void addLibro(CopiaLibro libro){
        this.libros.add(libro);
    }
    
    public void removeLibro(CopiaLibro libro){
        this.libros.remove(libro);
    }

    // public ArrayList<CopiaLibro> getLibros(){
    //     // return libros;
    // }

    public void updateDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    // public boolean isLibro(Libro libro){
    // no se sabe si lo implementamos
    // }
}
