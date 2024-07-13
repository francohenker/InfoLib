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
    private boolean copiaReferencia = false; // por defecto es una copia normal

    // protected para que no se puedan crear copias vacias
    protected CopiaLibro(){   }

    // Con este constructor se puede crear una copia normal o una copia de referencia
    public CopiaLibro(TipoLibro tipo, Libro libro, EstadoLibro estado, boolean copiaReferencia){
        this.tipo = tipo;
        this.libro = libro;
        this.estado = estado;
        this.copiaReferencia = copiaReferencia;
    }

    
    public void updateEstado(EstadoLibro estado){
        this.estado = estado;
    }

    // ver si se muestra esto en algun lado
    public boolean isCopiaReferencia(){
        return this.copiaReferencia;
    }

    @Override
    public String toString(){
        return this.libro.toString() + ". " + this.tipo + ". " + this.estado;
    }
    
}
