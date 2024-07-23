/**
 * Sample Skeleton for 'Inicio.fxml' Controller Class
 */

package controlador;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import modelo.App;

public class InicioControlador {

    @FXML
    private void indexLibros(ActionEvent event) throws IOException {
        App.setRoot("/vista/LibroABM");
    }

    @FXML
    void indexPrestamos(ActionEvent event) throws IOException {
        App.setRoot("/vista/PrestamoABM");
    }

    @FXML
    void indexUsuarios(ActionEvent event) throws IOException {
        App.setRoot("/vista/UsuarioABM");
    }

}
