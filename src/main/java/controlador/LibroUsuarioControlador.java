package controlador;

import Repositorio.Repositorio;
import db.Conexion;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.*;
import servicio.Enrutador;
import servicio.LibroService;
import servicio.RackService;
import servicio.Ventana;

import java.util.List;

public class LibroUsuarioControlador {
    private LibroService libroservice;

    @FXML
    private Button salir;
    @FXML
    private Button buscar;
    @FXML
    private TextField busquedatitulo;
    @FXML
    private TextField busquedatematica;
    @FXML
    private TextField busquedaautor;
    @FXML
    private TableView tablelibro;
    @FXML
    private TableColumn<Libro, String> isbncolumn;
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
    void initialize(){
        if(App.usuario instanceof Bibliotecario){
//            Enrutador.redirigir("vista/libro.fxml");
        }
        Repositorio repositorio = new Repositorio(Conexion.getEntityManagerFactory());
        this.libroservice = new LibroService(repositorio);
        RackService rackService = new RackService(repositorio);

        //configura el boton de buscar
        buscar.setOnAction(event -> buscarLibro());

        //configura el boton de deslogeo
        salir.setOnAction(Enrutador::salir);


        //configura la tabla de libros
        isbncolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
        titulocolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        autorescolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutores().toString()));
        editorialcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditorial()));
        tematicacolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTematica()));
        idiomacolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdioma()));


        agregarListeners(busquedaautor, busquedatematica, busquedatitulo);
        agregarListeners(busquedatematica, busquedatitulo, busquedaautor);
        agregarListeners(busquedatitulo, busquedaautor, busquedatematica);

        // carga la tabla de libros
        cargarLibros();

    }
    //listener para asegurarse que solo se escriba en un textfield de busqueda
    private void agregarListeners(TextField principal, TextField... otros) {
        principal.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                if (!newValue.isEmpty()) {
                    for (TextField textField : otros) {
                        textField.clear();
                    }
                }
            }
        });
    }
    //carga todos los libros de la base de datos
    private void cargarLibros(){

        List<Libro> libros = libroservice.obtenerTodos();

        ObservableList<Libro> listaLibros = FXCollections.observableArrayList(libros);

        tablelibro.setItems(listaLibros);
    }
    //carga los libros que coincidan con la busqueda realizada por autor, titulo o tematica
    private void cargarLibros(List<Libro> libros){

        ObservableList<Libro> listaLibros = FXCollections.observableArrayList(libros);

        tablelibro.setItems(listaLibros);
    }

    private void buscarLibro(){
        if(busquedaautor.getText().trim().isEmpty() && busquedatitulo.getText().trim().isEmpty() && busquedatematica.getText().trim().isEmpty()){
            cargarLibros();
            return;
        }
        try{
            if(!busquedatitulo.getText().trim().isEmpty()){
                List<Libro> libros = libroservice.buscarLibroPorTitulo(busquedatitulo.getText());
                cargarLibros(libros);
                return;
            }
            if(!busquedaautor.getText().trim().isEmpty()){
                libroservice.buscarLibroPorAutor(busquedaautor.getText());
                List<Libro> libros = libroservice.buscarLibroPorAutor(busquedaautor.getText());
                cargarLibros(libros);
                return;
            }
            if(!busquedatematica.getText().trim().isEmpty()){
                libroservice.buscarLibroPorTematica(busquedatematica.getText());
                List<Libro> libros = libroservice.buscarLibroPorTematica(busquedatematica.getText());
                cargarLibros(libros);
                return;
            }

        }catch (Exception e) {
            Ventana.error("Error", "Error al buscar el libro:\n" + e.getMessage());
        }
    }

}
