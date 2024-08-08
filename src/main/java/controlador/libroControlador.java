package controlador;

import Repositorio.Repositorio;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.CopiaLibro;
import modelo.Libro;
import servicio.Enrutador;
import servicio.LibroService;
import servicio.Ventana;

public class libroControlador {
    private Repositorio repositorio;
    private LibroService libroservice;
    private Ventana ve = new Ventana();
    private Enrutador en = new Enrutador();

    @FXML
    private Button buttonRack;
    @FXML
    private Button buttonPagePrestamos;
    @FXML
    private Button buttonPageLibros;
    @FXML
    private Button buttonPageUsuarios;

    @FXML
    private Button agregarCopia;
    @FXML
    private Button eliminarCopia;
    @FXML
    private Button modificarCopia;
    @FXML
    private Button agregarLibro;
    @FXML
    private Button eliminarLibro;
    @FXML
    private Button modificarLibro;
    @FXML
    private TextField precio;
    @FXML
    private TextField cantidadCopias;
    @FXML
    private TextField isbn;
    @FXML
    private TextField titulo;
    @FXML
    private TextField autores;
    @FXML
    private TextField editorial;
    @FXML
    private TextField tematica;
    @FXML
    private TextField idioma;
    @FXML
    private TableView tablecopia;
    @FXML
    private TableView tablelibro;
    @FXML
    private TableColumn <Libro, String> isbncolumn;
    @FXML
    private TableColumn <Libro, String> titulocolumn;
    @FXML
    private TableColumn <Libro, String> autorescolumn;
    @FXML
    private TableColumn <Libro, String> editorialcolumn;
    @FXML
    private TableColumn <Libro, String> tematicacolumn;
    @FXML
    private TableColumn <Libro, String> idiomacolumn;
    @FXML
    private TableColumn <CopiaLibro, String> preciocopia;
    @FXML
    private TableColumn <CopiaLibro, String> referenciacopia;
    @FXML
    private TableColumn <CopiaLibro, String> estadocopia;
    @FXML
    private TableColumn <CopiaLibro, String> tipocopia;
    @FXML
    private TableColumn <CopiaLibro, String> titulocopia;
    @FXML
    private TableColumn <CopiaLibro, String> cantidadcopia;
    @FXML
    private ChoiceBox tipo;
    @FXML
    private ChoiceBox estado;
    @FXML
    private ChoiceBox referencia;


    @FXML
    void initialize() {
        //configura la accion de los botones laterales
        buttonRack.setOnAction(event -> ventanaRack(event));
        buttonPagePrestamos.setOnAction(event -> ventanaPrestamo(event));
        buttonPageLibros.setOnAction(event -> ventanaLibros(event));
        buttonPageUsuarios.setOnAction(event -> ventanaUsuario(event));
    }
    public void ventanaPrestamo(ActionEvent event) {
        en.redirigir(event, "/vista/prestamo.fxml");
    }
    public void ventanaLibros(ActionEvent event) {
        en.redirigir(event, "/vista/libro.fxml");
    }
    public void ventanaUsuario(ActionEvent event) {
        en.redirigir(event, "/vista/usuario.fxml");
    }
    public void ventanaRack(ActionEvent event) {
        en.redirigir(event, "/vista/rack.fxml");
    }
}
