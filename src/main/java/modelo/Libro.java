package modelo;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "isbn", nullable = false)
    private String ISBN;
    @Column(name = "titulo", length = 255, nullable = false)
    private String titulo;

    private Set<String> autores = new HashSet<>();
    @Column(name = "editorial", length = 50, nullable = false)
    private String editorial;
    @Column(name = "tematica", length = 50, nullable = false)
    private String tematica;
    @Column(name = "idioma", length = 50, nullable = false)
    private String idioma;



    // constructor sin argumentos, protected para no crear un libro sin atributos
    protected Libro(){    }
    
    // constructor con argumentos, en caso de recibir un precio negativo se arroja una nueva excepcion indicando que no se puede 
    public Libro(String ISBN, String titulo, Set<String> autores, String editorial, String tematica, String idioma) throws IllegalArgumentException{
        if(ISBN.length() != 13 && ISBN.length() != 10){
            throw new RuntimeException("ISBN no válido");
        }
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autores = autores;
        this.editorial = editorial;
        this.tematica = tematica;
        this.idioma = idioma;
        
    }

    // IMPLEMENTAR DESPUES
    // public void addCopia(Libro libro){

    // }

    // public void removeCopia(Libro libro){

    // }

    @Override
    public String toString(){
        var a = this.ISBN + ". " + this.titulo + ". " + this.autores + ". " + this.editorial + ". " + this.tematica + ". " + this.idioma;
        var b = a.replace("[", "");
        return b.replace("]", ""); //provisorio
    }
}
