package modelo;

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

/**
 * JavaFX App
 */
public class App extends Application {
    private static EntityManagerFactory emf = null;



    private static Scene scene;


    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/vista/LibroABM.fxml"));
//        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
//        stage.setScene(scene);
//        stage.show();

// ----------------------------------------------------------------
// PRUEBAS
        HashSet<String> hash = new HashSet<String>();
        hash.add("a");
        var user = new Bibliotecario("45.539.868", "asd", "Henkear");
        emf = Persistence.createEntityManagerFactory("InfoLib");

        var libro = new Libro("2134587895", "tuki", hash , "donpal SA", "Entretenimientoski", "español");
        var l = new CopiaLibro(TipoLibro.TAPA_DURA, libro, EstadoLibro.DISPONIBLE, 110, false);

        var e = new LibroRepositorio(emf);
//        e.guardarLibro(libro);
//        e.guardarCopiar(l, 3);
        var libro2 = e.buscarLibroPorTematica("En");
//        e.guardarCopiar(l, 1);


//        var a = new Prestamo(l, user);
//        a.devolverLibro();



        System.out.println(libro2);
//        EntityManager em = emf.createEntityManager();







//        em.getTransaction().begin();
//        em.persist(l);
//        em.getTransaction().commit();
//        em.close();

//--------------------------------------------------------------
    }

    static void setRoot(String fxml) throws IOException {
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
