package modelo;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

//add relationship

@Entity
@Table(name = "prestamo")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "copialibro_id", nullable = false)
    private CopiaLibro copiaLibro;
    @Column(name = "multa")
    private double multa = 0;
    @Column(name = "fechaprestamo", nullable = false)
    private LocalDateTime fechaPrestamo = LocalDateTime.now();
    private LocalDateTime fechaDevolucion;
    @ManyToOne
    @JoinColumn(name = "miembro_id", nullable = false)
    private Usuario miembro;


    // no se puede crear un prestamo vacio desde afuera
    protected Prestamo() {}

    public Prestamo(CopiaLibro copiaLibro, Usuario miembro) {
        this.miembro = miembro;
        this.copiaLibro = copiaLibro;
        this.copiaLibro.setEstado(EstadoLibro.PRESTADO);
    }

    public void devolverLibro(){
        copiaLibro.setEstado(EstadoLibro.DISPONIBLE);
        this.fechaDevolucion = LocalDateTime.now();
        if(fechaDevolucion.isAfter(fechaPrestamo) ){
            Duration diferencia = Duration.between(fechaPrestamo, fechaDevolucion);
            this.multa = diferencia.toHours() * (copiaLibro.getPrecio() / 24);
        }
    }

    public double getMulta(){
        return this.multa;
    }
}
