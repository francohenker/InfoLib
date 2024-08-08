package modelo;

import jakarta.persistence.*;
import org.hibernate.Hibernate;

import java.util.HashSet;
import java.util.Set;



@Entity
@Table(name = "libro")
public class Libro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "isbn", nullable = false, length = 13)
    private String ISBN;
    @Column(name = "titulo", length = 255, nullable = false)
    private String titulo;
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "libro_autores", joinColumns = @JoinColumn(name = "libro_id"))
    @Column(name = "autor")
    private Set<String> autores = new HashSet<>();
    @Column(name = "editorial", length = 50, nullable = false)
    private String editorial;
    @Column(name = "tematica", length = 50, nullable = false)
    private String tematica;
    @Column(name = "idioma", length = 50, nullable = false)
    private String idioma;
    @OneToMany(mappedBy = "libro")
    private Set<CopiaLibro> copias = new HashSet<>();

    // constructor sin argumentos, protected para no crear un libro sin atributos
    protected Libro(){    }
    
    // constructor con argumentos, en caso de recibir un precio negativo se arroja una nueva excepcion indicando que no se puede 
    public Libro(String ISBN, String titulo, Set<String> autores, String editorial, String tematica, String idioma) throws IllegalArgumentException{
        if(ISBN.length() != 13 && ISBN.length() != 10){
            throw new IllegalArgumentException("ISBN no válido");
        }
        this.ISBN = ISBN;
        this.titulo = titulo.toUpperCase();
        this.autores = autores;
        this.editorial = editorial.toUpperCase();
        this.tematica = tematica.toUpperCase();
        this.idioma = idioma.toUpperCase();
        
    }

    // IMPLEMENTAR DESPUES
    // public void addCopia(Libro libro){

    // }

    // public void removeCopia(Libro libro){

    // }
    public String getIsbn(){
        return this.ISBN;
    }
    public String getTitulo(){
        return this.titulo;
    }
    public Set<String> getAutores(){
        return this.autores;
    }
    public String getEditorial(){
        return this.editorial;
    }
    public String getTematica(){
        return this.tematica;
    }
    public String getIdioma(){
        return this.idioma;
    }

    @Override
    public String toString(){
//        Hibernate.initialize(this.autores);
        var a = this.ISBN + ". " + this.titulo + ". " + this.autores + ". " + this.editorial + ". " + this.tematica + ". " + this.idioma;
        var b = a.replace("[", "");
        return b.replace("]", ""); //provisorio
    }
}
