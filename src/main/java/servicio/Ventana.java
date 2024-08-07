package servicio;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Ventana {


    public void error(String s) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error");
        alertError.setHeaderText((String) null);
        alertError.setContentText(s);
        alertError.showAndWait();
    }

    public void exito(String s) {
        Alert alertExisto = new Alert(Alert.AlertType.INFORMATION);
        alertExisto.setTitle("Exito");
        alertExisto.setHeaderText((String) null);
        alertExisto.setContentText(s);
        alertExisto.showAndWait();
    }

    public Optional<ButtonType> confirmacion(String p, String r) {
        // Mostrar un Alert de confirmación
        Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmacion.setTitle("Confirmar");
        alertConfirmacion.setHeaderText(p);
        alertConfirmacion.setContentText(r);
        Optional<ButtonType> resultado = alertConfirmacion.showAndWait();
        return resultado;
    }
}
