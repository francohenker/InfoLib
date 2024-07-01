package modules;

public class CopiaLibro {
    // IMPLEMENTAR EL ID CON LA BD
    private Integer id;
    private TipoLibro tipo;
    private Libro libro;
    private EstadoLibro estado = EstadoLibro.DISPONIBLE;
    private boolean copiaReferencia = false; 

    public CopiaLibro(){

    }


    
    public void updateEstado(EstadoLibro estado){
        this.estado = estado;
    }

    public boolean isCopiaReferencia(){
        return this.copiaReferencia;
    }
    // ver si se muestra esto en algun lado
    @Override
    public String toString(){
        return this.libro.toString() + ". " + this.tipo + ". " + this.estado;
    }
    
}
