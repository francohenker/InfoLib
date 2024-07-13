package modelo;
import jakarta.persistence.*;

import java.time.Duration;
import java.time.LocalDateTime;

@Entity
@Table(name = "prestamo")
public class Prestamo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "copialibro_id", nullable = false)
    private CopiaLibro copiaLibro;
    private double multa = 0;

    private LocalDateTime fechaPrestamo = LocalDateTime.now();
    private LocalDateTime fechaDevolucion;

//    private Miembro solicitante;
    // no se puede crear un prestamo vacio desde afuera
    protected Prestamo() {}

    public Prestamo(CopiaLibro copiaLibro){
        this.copiaLibro = copiaLibro;
        this.copiaLibro.setEstado(EstadoLibro.PRESTADO);
    }

    public void devolverLibro(){
        copiaLibro.setEstado(EstadoLibro.DISPONIBLE);
        this.fechaDevolucion = LocalDateTime.now();
        if(fechaDevolucion.isAfter(fechaPrestamo) ){
            Duration diferencia = Duration.between(fechaPrestamo, fechaDevolucion);
            this.multa = diferencia.toMinutes() * (copiaLibro.getPrecio() / 24);
        }
    }
}
