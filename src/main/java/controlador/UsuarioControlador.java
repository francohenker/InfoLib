/**
 * Sample Skeleton for 'UsuariosABM.fxml' Controller Class
 */

package controlador;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import modelo.App;

public class UsuarioControlador {

    @FXML
    void inicio(ActionEvent event) throws IOException {
        App.setRoot("/vista/Inicio");
    }

}
