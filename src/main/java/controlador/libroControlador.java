package controlador;

import Repositorio.Repositorio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import servicio.Enrutador;
import servicio.LibroService;
import servicio.Ventana;

public class libroControlador {
    private Repositorio repositorio;
    private LibroService libroservice;
    private Ventana ve = new Ventana();
    private Enrutador en = new Enrutador();

    @FXML
    private Button buttonRack;
    @FXML
    private Button buttonPagePrestamos;
    @FXML
    private Button buttonPageLibros;
    @FXML
    private Button buttonPageUsuarios;

//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private
//    @FXML
//    private

    @FXML
    void initialize() {
        //configura la accion de los botones laterales
        buttonRack.setOnAction(event -> ventanaRack(event));
        buttonPagePrestamos.setOnAction(event -> ventanaPrestamo(event));
        buttonPageLibros.setOnAction(event -> ventanaLibros(event));
        buttonPageUsuarios.setOnAction(event -> ventanaUsuario(event));
    }
    public void ventanaPrestamo(ActionEvent event) {
        en.redirigir(event, "/vista/prestamo.fxml");
    }
    public void ventanaLibros(ActionEvent event) {
        en.redirigir(event, "/vista/libro.fxml");
    }
    public void ventanaUsuario(ActionEvent event) {
        en.redirigir(event, "/vista/usuario.fxml");
    }
    public void ventanaRack(ActionEvent event) {
        en.redirigir(event, "/vista/rack.fxml");
    }
}
