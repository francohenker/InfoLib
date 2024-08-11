package servicio;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.App;



public class Enrutador {
//    private Stage stage;

    public static void redirigir(ActionEvent event, String ruta) {
        try {
            FXMLLoader loader = new FXMLLoader(App.class.getResource(ruta));
            AnchorPane root = loader.load();
            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (Exception e) {
            Ventana.error("No se pudo cargar la ventana", "Ocurrio un error al intentar cargar la ventana: " + ruta);
        }
    }

//    public static void redirigir(String ruta){
//
//    try {
//        // Cargar la nueva pantalla desde un archivo FXML
//        FXMLLoader loader = new FXMLLoader(App.class.getResource(ruta));
//        AnchorPane root = loader.load();
//
//        // Si tienes acceso al Stage actual
//        if (stage == null) {
//            stage = (Stage) algunaReferenciaDeEscenaActual.getScene().getWindow(); // Obtener Stage desde un nodo actual
//        }
//
//        // Establecer el nuevo contenido de la escena
//        stage.getScene().setRoot(root);
//
//    } catch (Exception e) {
//        e.printStackTrace();
//    }
//    }
}
