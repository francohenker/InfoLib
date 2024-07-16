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
//        scene = new Scene(loadFXML("primary"), 640, 480);
//        stage.setScene(scene);
//        stage.show();
// ----------------------------------------------------------------
// PRUEBAS
        HashSet<String> hash = new HashSet<String>();
        hash.add("a");
        var user = new Bibliotecario("45.539.868", "asd", "Henkear");
        emf = Persistence.createEntityManagerFactory("InfoLib");

        var libro = new Libro("1234587891", "TITULOOOO", hash , "donpal SA", "saika", "eng");

//        var a = new Prestamo(l, user);
//        a.devolverLibro();
//        var e = new LibroRepositorio(emf);
//        var libro2 = e.buscarCopias(libro);

//        var l = new CopiaLibro(TipoLibro.TAPA_DURA, libro2.get(0), EstadoLibro.DISPONIBLE, 110, false);


//        System.out.println(libro2);
//        e.guardarLibro(libro);
//        EntityManager em = emf.createEntityManager();







//        e.guardarCopiar(l, 5);
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
