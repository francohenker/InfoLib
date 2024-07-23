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
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;


    // no se puede crear un prestamo vacio desde afuera
    protected Prestamo() {}

    public Prestamo(CopiaLibro copiaLibro, Usuario usuario) {
        if(!usuario.isAlta()){
            throw new RuntimeException("El usuario no se encuentra activo");
        }
        this.usuario = usuario;
        this.copiaLibro = copiaLibro;
        this.copiaLibro.setEstado(EstadoLibro.PRESTADO);
    }

    public void devolverLibro(){
        this.fechaDevolucion = LocalDateTime.now();
        if(fechaDevolucion.isAfter(fechaPrestamo) ){
            Duration diferencia = Duration.between(fechaPrestamo, fechaDevolucion);
            this.multa = diferencia.toHours() * (copiaLibro.getPrecio() / 24);
        }
        copiaLibro.setEstado(EstadoLibro.DISPONIBLE);
    }

    public double getMulta(){
        return this.multa;
    }

    public LocalDateTime getFechaPrestamo(){
        return this.fechaPrestamo;
    }
}
