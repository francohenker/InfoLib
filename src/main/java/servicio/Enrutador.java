package servicio;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import modelo.App;

import java.io.IOException;

public class Enrutador {

    public static void redirigir(ActionEvent event, String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(ruta));
            Parent root = loader.load();
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
