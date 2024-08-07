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

public class App extends Application {
    private static EntityManagerFactory emf = null;

    //objeto temporal donde volcar el usuaio para controlar la sesion
//    public static Usuario usuario = new Usuario();

    private static Scene scene;


    @Override
    public void start(Stage stage) throws IOException {
//        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/vista/usuario.fxml"));
//        scene = new Scene(fxmlLoader.load(), 1080, 720);
//        stage.setScene(scene);
//        stage.setResizable(false);
//        stage.show();
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource("/vista/usuario.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1080, 720);
        stage.setTitle("Inicio de Sesión");
        stage.setScene(scene);
        stage.show();

// ----------------------------------------------------------------
//// PRUEBAS
//        HashSet<String> hash = new HashSet<String>();
        emf = Persistence.createEntityManagerFactory("InfoLib");
        var repo = new Repositorio(emf);
        var repo2 = new Repositorio(emf);
        var pre = new PrestamoService(repo2);
        var us = new UsuarioService(repo);
        var lib = new LibroService(repo);
        var user = us.buscarPorDni("13339868");
//        us.guardarUsuario(new Usuario("13339868", "user2", "epa", "contraseña"));
        var libro = lib.buscarLibroPorTitulo("estimado");
        var copia = lib.buscarCopiasPorIsbn(libro.get(0));
//        System.out.println(copia);
//        pre.guardarPrestamo(copia.get(2), user);
//        var usersPrestamo = pre.buscarPrestamoPorUsuario(user);
//        System.out.println(usersPrestamo);
//        var repo2 = new Repositorio(emf);
//        var pre2 = new PrestamoService(repo2);
//        pre2.devolver(usersPrestamo.get(0));


//        var hash = new HashSet<String>();
//        hash.add("saik");
//        hash.add("opa");
//        hash.add("opai");
//        var l1 = new Libro("7314567190", "estimado", hash, "Estimado", "novela", "english");
//        var rack = new Rack("rack 2");
//        var c1 = new CopiaLibro(TipoLibro.TAPA_DURA, l1, EstadoLibro.DISPONIBLE, 10.0, rack, false );
//        var r = new RackService(repo);
//        r.guardarRack(rack);
//                lib.guardarLibro(l1);
//        lib.guardarCopia(c1, 3);

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
