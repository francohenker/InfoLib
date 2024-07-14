package modelo;

import InfoLib.db.Conexion;
import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
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
//        scene = new Scene(loadFXML("primary"), 640, 480);
//        stage.setScene(scene);
//        stage.show();
//        HashSet<String> hash = new HashSet<String>();
//        hash.add("a");
//        var libro = new Libro("1234567891", "tit00", hash , "a", "cine", "español");
//        var l = new CopiaLibro(TipoLibro.LIBRO_ELECTRONICO, libro, EstadoLibro.DISPONIBLE, 10, false);
//        var a = new Prestamo(l);
//        a.devolverLibro();
//
//        emf = Persistence.createEntityManagerFactory("InfoLib");
//        EntityManager em = emf.createEntityManager();
//        em.getTransaction().begin();
//        em.persist(libro);
//        em.persist(l);
//        em.persist(a);
//        em.getTransaction().commit();


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
