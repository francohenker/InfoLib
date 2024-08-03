package servicio;

import Repositorio.Repositorio;
import modelo.Rack;

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
        this.repositorio.iniciarTransaccion();
        this.repositorio.eliminar(rack);
        this.repositorio.confirmarTransaccion();
    }

}
