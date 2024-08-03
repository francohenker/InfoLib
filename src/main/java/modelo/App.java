package modelo;

import Repositorio.Repositorio;
import db.Conexion;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import servicio.LibroService;
import servicio.PrestamoService;
import servicio.UsuarioService;

import java.io.IOException;
import java.util.HashSet;

public class App extends Application {
    private static EntityManagerFactory emf = null;



    private static Scene scene;


    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/vista/prestamoAlta.fxml"));
//        scene = new Scene(fxmlLoader.load(), 1080, 720);
//        stage.setScene(scene);
//        stage.show();

// ----------------------------------------------------------------
//// PRUEBAS
//        HashSet<String> hash = new HashSet<String>();
        emf = Persistence.createEntityManagerFactory("InfoLib");
        var repo = new Repositorio(emf);
        var pre = new PrestamoService(repo);
        var us = new UsuarioService(repo);
        var lib = new LibroService(repo);
        var user = us.buscarPorDni("44.539.868");
        var libro = lib.buscarLibroPorTitulo("");
        var copia = lib.buscarCopiasPorIsbn(libro.get(0));
        pre.guardarPrestamo(copia.get(0), user);




        var hash = new HashSet<String>();
        hash.add("saik");
        hash.add("opa");
        hash.add("opai");
//        var libro = new Libro("4314567190", "DON saika", hash, "Estimado", "novela", "english");
//        var rack = new Rack("rack 1");
//        var copia = new CopiaLibro(TipoLibro.AUDIOLIBRO, libro, EstadoLibro.DISPONIBLE, 10.0, rack, false );


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
