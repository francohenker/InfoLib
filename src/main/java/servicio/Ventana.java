package servicio;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class Ventana {


    public static void error(String header, String text) {
        Alert alertError = new Alert(Alert.AlertType.ERROR);
        alertError.setTitle("Error");
        alertError.setHeaderText(header);
        alertError.setContentText(text);
        alertError.showAndWait();
    }

    public static void exito(String header, String text) {
        Alert alertExisto = new Alert(Alert.AlertType.INFORMATION);
        alertExisto.setTitle("Exito");
        alertExisto.setHeaderText(header);
        alertExisto.setContentText(text);
        alertExisto.showAndWait();
    }

    public static Optional<ButtonType> confirmacion(String header, String text) {
        // Mostrar un Alert de confirmación
        Alert alertConfirmacion = new Alert(Alert.AlertType.CONFIRMATION);
        alertConfirmacion.setTitle("Confirmar");
        alertConfirmacion.setHeaderText(header);
        alertConfirmacion.setContentText(text);
        Optional<ButtonType> resultado = alertConfirmacion.showAndWait();
        return resultado;
    }
}
