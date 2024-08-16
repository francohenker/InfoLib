package servicio;

import controlador.PrestamoControlador;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import modelo.App;
import modelo.CopiaLibro;


public class Enrutador {

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

    //usado solo para redirigir a la pantalla de prestamo y cargar los datos para realizar el mismo
    public static void redirigir(ActionEvent event, String ruta, CopiaLibro copia) {
        if(!ruta.equals("/vista/prestamo.fxml")){
            throw new RuntimeException("No se puede cambiar la vista de la pantalla");
        }
        try {

            FXMLLoader loader = new FXMLLoader(App.class.getResource(ruta));
            AnchorPane root = loader.load();

            PrestamoControlador prestamoControlador = loader.getController();
            prestamoControlador.setCopia(copia);

            Stage stage = (Stage) ((Button)event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (Exception e) {
            Ventana.error("No se pudo cargar la ventana", "Ocurrio un error al intentar cargar la ventana: " + ruta);
        }
    }

    public static void salir(ActionEvent event){
        App.usuario = null;
        redirigir(event, "/vista/login.fxml");
    }
}
