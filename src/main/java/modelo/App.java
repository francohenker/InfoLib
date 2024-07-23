package modelo;

import Repositorio.UsuarioRepositorio;
import db.Conexion;
import Repositorio.LibroRepositorio;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.Query;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;
import java.util.HashSet;

public class App extends Application {
    private static EntityManagerFactory emf = null;



    private static Scene scene;


    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/vista/Inicio.fxml"));
        scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setScene(scene);
        stage.show();

// ----------------------------------------------------------------
//// PRUEBAS
//        HashSet<String> hash = new HashSet<String>();


//--------------------------------------------------------------
    }

    public static void setRoot(String fxml) throws IOException {
        scene.setRoot(loadFXML(fxml));
    }

    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }

    @Override
    public void stop() throws Exception {
        super.stop();
        if (Conexion.getEntityManagerFactory() != null && Conexion.getEntityManagerFactory().isOpen()) {
            Conexion.getEntityManagerFactory().close(); // Cierra el EMF
        }
    }

    public static void main(String[] args) throws Exception {
        Conexion.crearEntityManagerFactory();
        launch();

    }

}
