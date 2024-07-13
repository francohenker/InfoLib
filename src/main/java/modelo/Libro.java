package modelo;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "libro")
public class Libro {
    @Id
    private Long id;
    @Column(name = "isbn", nullable = false)
    private Integer ISBN;
    @Column(name = "titulo", length = 255, nullable = false)
    private String titulo;

    private Set<String> autores = new HashSet<>();
    @Column(name = "editorial", length = 50, nullable = false)
    private String editorial;
    @Column(name = "tematica", length = 50, nullable = false)
    private String tematica;
    @Column(name = "idioma", length = 50, nullable = false)
    private String idioma;
    @Column(name = "precio", nullable = false)
    private double precio;


    // constructor sin argumentos, protected para no crear un libro sin atributos
    protected Libro(){    }
    
    // constructor con argumentos, en caso de recibir un precio negativo se arroja una nueva excepcion indicando que no se puede 
    public Libro(Integer ISBN, String titulo, Set<String> autores, String editorial, String tematica, String idioma, double precio) throws IllegalArgumentException{ 
        try {
            updatePrecio(precio);
        }catch (Exception e){
            throw new IllegalArgumentException("Precio negativo");
        }
        if(ISBN != 13 && ISBN != 10){
            throw new RuntimeException("ISBN no válido");
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
        var a = this.ISBN + ". " + this.titulo + ". " + this.autores + ". " + this.editorial + ". " + this.tematica + ". " + this.idioma + " " + this.precio;
        var b = a.replace("[", "");
        return b.replace("]", ""); //provisorio
    }
}
