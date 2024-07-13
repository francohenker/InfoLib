package modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "rack")
public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descripcion", length = 255, nullable = true)
    private String descripcion;
    @OneToMany
    private List<CopiaLibro> libros = new ArrayList<>();


    public Rack(){    }

    public Rack(String descripcion){
        this.descripcion = descripcion;
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

    // no se sabe si lo implementamos
    // public boolean isLibro(Libro libro) { }
}
