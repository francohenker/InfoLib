package modelo;

import jakarta.persistence.Entity;

import java.util.HashSet;
import java.util.Set;



//@Entity
public class Libro {
    // IMPLEMENTAR EL ID CON LA BD
    private Integer id;
    private Integer ISBN;
    private String titulo;
    private Set<String> autores = new HashSet<>();
    private String editorial;
    private String tematica;
    private String idioma;
    private double precio;


    // constructor sin argumentos
    public Libro(){
    }
    
    // constructor con argumentos, en caso de recibir un precio negativo se arroja una nueva excepcion indicando que no se puede 
    public Libro(Integer ISBN, String titulo, Set<String> autores, String editorial, String tematica, String idioma, double precio) throws IllegalArgumentException{ 
        try {
            updatePrecio(precio);
        }catch (Exception e){
            throw new IllegalArgumentException("Precio negativo");
        }
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autores = autores;
        this.editorial = editorial;
        this.tematica = tematica;
        this.idioma = idioma;
        
    }

    public void updatePrecio(double precio){
        if(precio < 0){
            throw new RuntimeException("Precio negativo");
        }
        this.precio = precio;
    }

    // IMPLEMENTAR DESPUES 
    // public void addCopia(Libro libro){

    // }

    // public void removeCopia(Libro libro){

    // }

    @Override
    public String toString(){
        var a = this.titulo + ". " + this.ISBN + ". " + this.autores + ". " + this.editorial + ". " + this.tematica + ". " + this.idioma + " " + this.precio;
        var b = a.replace("[", "");
        return b.replace("]", ""); //provisorio
    }
}
