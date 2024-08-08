package modelo;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "rack")
public class Rack {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "descripcion", length = 255, nullable = true)
    private String descripcion;
    @OneToMany(mappedBy = "rack", cascade = CascadeType.ALL)
    private Set<CopiaLibro> copias  = new HashSet<>();



    public Rack(){    }

    public Rack(String descripcion){
        this.descripcion = descripcion;
    }

    public void updateDescripcion(String descripcion){
        this.descripcion = descripcion;
    }

    public String getDescripcion(){
        return this.descripcion;
    }

    public String getId(){
        return this.id.toString();
    }

}
