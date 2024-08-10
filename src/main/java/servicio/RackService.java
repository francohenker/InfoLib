package servicio;

import Repositorio.Repositorio;
import modelo.Rack;

import java.util.List;

public class RackService {
    private Repositorio repositorio;

    public RackService(Repositorio repositorio) {
        this.repositorio = repositorio;
    }

    public void guardarRack(Rack rack){
        this.repositorio.iniciarTransaccion();
        this.repositorio.insertar(rack);
        this.repositorio.confirmarTransaccion();
    }

    public void borrarRack(Rack rack){
        if(!rack.getCopias().isEmpty()){
            throw new RuntimeException("No se puede borrar un rack porque tiene copias asociadas");
        }
        this.repositorio.iniciarTransaccion();
        this.repositorio.eliminar(rack);
        this.repositorio.confirmarTransaccion();
    }
    public void modificarRack(Rack rack){
        this.repositorio.iniciarTransaccion();
        this.repositorio.modificar(rack);
        this.repositorio.confirmarTransaccion();
    }

    public List<Rack> obtenerTodos(){
        return this.repositorio.obtenerTodos(Rack.class);
    }

}
