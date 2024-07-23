package modelo;

import Repositorio.PrestamoRepositorio;
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
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/vista/Inicio.fxml"));
//        scene = new Scene(fxmlLoader.load(), 1080, 720);
//        stage.setScene(scene);
//        stage.show();

// ----------------------------------------------------------------
//// PRUEBAS
//        HashSet<String> hash = new HashSet<String>();
        emf = Persistence.createEntityManagerFactory("InfoLib");
        var u = new UsuarioRepositorio(emf);
        var user = u.buscarPorDni("44.539.868");

//        u.guardarUsuario(new Usuario("44.539.868", "admin", "admin"));
        var pre = new PrestamoRepositorio(emf);
        var lr = new LibroRepositorio(emf);
        var hash = new HashSet<String>();
        hash.add("epcio");
        hash.add("epa");
        hash.add("tuki");
        var libro = new Libro("6234567890", "epa", hash, "los pachis", "espacial", "español");
        var rack = new Rack("rack 1");
        var copia = new CopiaLibro(TipoLibro.AUDIOLIBRO, libro, EstadoLibro.DISPONIBLE, 10.0, rack, false );
//        lr.guardarLibro(libro);
//        lr.guardarRack(rack);
//        lr.guardarCopia(copia, 1);
//        var p = new Prestamo(copia, user.get(0));
        var c = lr.buscarLibroPorTitulo("");

        pre.guardarPrestamo(copia, user.get(0));
//        var pre = new PrestamoRepositorio(emf);
//        pre.buscarPrestamoPorUsuario();
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
