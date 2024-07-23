package modelo;

import jakarta.persistence.*;

@Entity
@Table(name = "copialibro")
public class CopiaLibro {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "tipolibro", nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoLibro tipo;
    @ManyToOne
    @JoinColumn(name = "libro_id", nullable = false)
    private Libro libro;
    @Column(name = "estado", nullable = false)
    @Enumerated(EnumType.STRING)
    private EstadoLibro estado = EstadoLibro.DISPONIBLE;
    @Column(name = "precio", nullable = false)
    private double precio;
    private boolean copiaReferencia = false; // por defecto es una copia normal

    @ManyToOne
    @JoinColumn(name = "rack_id")
    private Rack rack;


    // protected para que no se puedan crear copias vacias
    protected CopiaLibro(){   }

    // Con este constructor se puede crear una copia normal o una copia de referencia
    public CopiaLibro(TipoLibro tipo, Libro libro, EstadoLibro estado, double precio, Rack rack, boolean copiaReferencia){
        try {
            updatePrecio(precio);
        }catch(Exception e){
            throw new IllegalArgumentException("Precio negativo");
        }
        this.tipo = tipo;
        this.libro = libro;
        this.estado = estado;
        this.rack = rack;
        this.copiaReferencia = copiaReferencia;
    }

    public double getPrecio(){
        return this.precio;
    }
    public void updatePrecio(double precio){
        if(precio < 0){
            throw new RuntimeException("Precio negativo");
        }
        this.precio = precio;
    }
    public EstadoLibro getEstado(){
        return this.estado;
    }
    public void setEstado(EstadoLibro estado){
        this.estado = estado;
    }

    public TipoLibro getTipo(){return this.tipo;}

    public Libro getLibro(){
        return this.libro;
    }

    public Rack getRack(){
        return this.rack;
    }
    public void setRack(Rack rack){
        this.rack = rack;
    }


    // ver si se muestra esto en algun lado
    public boolean isCopiaReferencia(){
        return this.copiaReferencia;
    }

    @Override
    public String toString(){
        return this.libro.toString() + ". " + this.tipo + ". " + this.estado + ". " + this.precio;
    }
    
}
