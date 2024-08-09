package controlador;

import Repositorio.Repositorio;
import db.Conexion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.*;
import servicio.Enrutador;
import servicio.LibroService;
import servicio.RackService;
import servicio.Ventana;

import java.util.List;

public class libroControlador {
    private Repositorio repositorio;
    private LibroService libroservice;
    private RackService rackService;
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
    private TableColumn <CopiaLibro, Double> preciocopia = new TableColumn<>("precio");
    @FXML
    private TableColumn <CopiaLibro, String> referenciacopia;
    @FXML
    private TableColumn <CopiaLibro, EstadoLibro> estadocopia = new TableColumn<>("estado");
    @FXML
    private TableColumn <CopiaLibro, TipoLibro> tipocopia = new TableColumn<>("tipo");
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
    private ChoiceBox choicerack;


    @FXML
    void initialize() {
        this.repositorio = new Repositorio(Conexion.getEntityManagerFactory());
        this.libroservice = new LibroService(repositorio);
        this.rackService = new RackService(repositorio);
        //configura la accion de los botones laterales
        buttonRack.setOnAction(event -> ventanaRack(event));
        buttonPagePrestamos.setOnAction(event -> ventanaPrestamo(event));
        buttonPageLibros.setOnAction(event -> ventanaLibros(event));
        buttonPageUsuarios.setOnAction(event -> ventanaUsuario(event));

        //configura la tabla de libros
        isbncolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
        titulocolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        autorescolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutores().toString()));
        editorialcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditorial()));
        tematicacolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTematica()));
        idiomacolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdioma()));

        //configura la tabla de copia
        preciocopia.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        referenciacopia.setCellValueFactory(cellData -> {
            return cellData.getValue().isCopiaReferencia() ? new SimpleStringProperty("SI") : new SimpleStringProperty("NO");
        });
        estadocopia.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tipocopia.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        titulocopia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibro().getTitulo()));
//        cantidadcopia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().)); //realizar la consulta para saber la cantidad de copias


        //configura los textfield de copia
        List<Rack> racks = rackService.obtenerTodos();
        choicerack.getItems().setAll(racks);
        referencia.getItems().setAll("SI", "NO");
        tipo.getItems().setAll(TipoLibro.values());
        estado.getItems().setAll(EstadoLibro.values());





        // Configura el doble clic para cargar los campos textfield
        tablelibro.setRowFactory( tv -> {
            TableRow<Libro> row = new TableRow<>();
            row.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Libro libro = row.getItem();
                    rellenarCampos(libro);
                    cargarCopias(libro);
                }
            });
            return row;
        });

        //configura el doble click para cargar copias
        tablecopia.setRowFactory( tv -> {
            TableRow<CopiaLibro> row = new TableRow<>();
            row.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    CopiaLibro copia = row.getItem();
                    rellenarCampos(copia);
                }
            });
            return row;
        });

        // carga la tabla de libros
        cargarLibros();
    }
    public void ventanaPrestamo(ActionEvent event) {
        Enrutador.redirigir(event, "/vista/prestamo.fxml");
    }
    public void ventanaLibros(ActionEvent event) {
        Enrutador.redirigir(event, "/vista/libro.fxml");
    }
    public void ventanaUsuario(ActionEvent event) {
        Enrutador.redirigir(event, "/vista/usuario.fxml");
    }
    public void ventanaRack(ActionEvent event) {
        Enrutador.redirigir(event, "/vista/rack.fxml");
    }

    private void rellenarCampos(Libro libro){
        isbn.setText(libro.getIsbn());
        isbn.setDisable(true);
        titulo.setText(libro.getTitulo());
        titulo.setDisable(true);
        autores.setText(libro.getAutores().toString());
        autores.setDisable(true);
        editorial.setText(libro.getEditorial());
        editorial.setDisable(true);
        tematica.setText(libro.getTematica());
        tematica.setDisable(true);
        idioma.setText(libro.getIdioma());
        idioma.setDisable(true);
    }

    private void rellenarCampos(CopiaLibro copia){
        tipo.setValue(copia.getTipo());
        estado.setValue(copia.getEstado());
        precio.setText(String.valueOf(copia.getPrecio()));
        referencia.setValue(copia.isCopiaReferencia() ? "SI" : "NO");
        cantidadCopias.setDisable(true);
        choicerack.setValue(copia.getRack());
    }

    private void cargarLibros(){

        List<Libro> libros = libroservice.obtenerTodos();

        ObservableList<Libro> listaLibros = FXCollections.observableArrayList(libros);

        tablelibro.setItems(listaLibros);
    }

    private void cargarCopias(Libro libro){
        List<CopiaLibro> copias = libroservice.buscarCopiasPorIsbn(libro);

        ObservableList<CopiaLibro> listaCopias = FXCollections.observableArrayList(copias);

        tablecopia.setItems(listaCopias);
//        cantidadcopia.setText("1");
    }

    private void limpiarCampo(){
        isbn.clear();
        titulo.clear();
        autores.clear();
        editorial.clear();
        tematica.clear();
        idioma.clear();
        precio.clear();
        cantidadCopias.clear();

    }


}
