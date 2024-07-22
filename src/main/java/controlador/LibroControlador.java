/**
 * Sample Skeleton for 'LibroABM.fxml' Controller Class
 */

package controlador;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import modelo.App;

public class LibroControlador {

    @FXML
    void inicio(ActionEvent event) throws IOException {
        App.setRoot("Inicio");
    }

}
