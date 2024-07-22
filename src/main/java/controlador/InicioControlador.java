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
    void indexLibros(ActionEvent event) throws IOException {
        App.setRoot("LibroABM");
    }

    @FXML
    void indexPrestamos(ActionEvent event) throws IOException {
        App.setRoot("PrestamoABM");
    }

    @FXML
    void indexUsuarios(ActionEvent event) throws IOException {
        App.setRoot("UsuariosABM");
    }

}
