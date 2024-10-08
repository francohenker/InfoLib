package controlador;

import Repositorio.Repositorio;
import db.Conexion;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
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

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class LibroControlador {
    private LibroService libroservice;
    @FXML
    private Button buttonRack;
    @FXML
    private Button buttonPagePrestamos;
    @FXML
    private Button buttonPageLibros;
    @FXML
    private Button buttonPageUsuarios;
    @FXML
    private Button salir;
    @FXML
    private Button agregarCopia;
    @FXML
    private Button modificarCopia;
    @FXML
    private Button agregarLibro;
    @FXML
    private Button eliminarLibro;
    @FXML
    private Button limpiarlibro;
    @FXML
    private Button limpiarcopia;
    @FXML
    private Button buscar;
    @FXML
    private Button prestar;
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
    private TextField busquedatitulo;
    @FXML
    private TextField busquedatematica;
    @FXML
    private TextField busquedaautor;
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
    private TableColumn <CopiaLibro, Rack> columnrack;
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
        Repositorio repositorio = new Repositorio(Conexion.getEntityManagerFactory());
        this.libroservice = new LibroService(repositorio);
        RackService rackService = new RackService(repositorio);

        //configura la accion de los botones laterales
        buttonRack.setOnAction(this::ventanaRack);
        buttonPagePrestamos.setOnAction(this::ventanaPrestamo);
        buttonPageLibros.setOnAction(this::ventanaLibros);
        buttonPageUsuarios.setOnAction(this::ventanaUsuario);

        //configura el boton de deslogeo
        salir.setOnAction(Enrutador::salir);

        //configura el boton de buscar
        buscar.setOnAction(event -> buscarLibro());

        //configura los botones del amb libro
        limpiarlibro.setOnAction(event -> limpiarCamposLibro());
        agregarLibro.setOnAction(event -> agregarLibro());
        eliminarLibro.setOnAction(event -> eliminarLibro());

        //configura los botones del amb copia
        limpiarcopia.setOnAction(event -> limpiarCamposCopia());
        agregarCopia.setOnAction(event -> agregarCopia());
        modificarCopia.setOnAction(event -> modificarCopia());
        prestar.setOnAction(event -> {
                CopiaLibro copiaSeleccionada = (CopiaLibro) tablecopia.getSelectionModel().getSelectedItem();
                abrirPantallaPrestamos(event, copiaSeleccionada);
        });


        //configura la tabla de libros
        isbncolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIsbn()));
        titulocolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTitulo()));
        autorescolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getAutores().toString()));
        editorialcolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getEditorial()));
        tematicacolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getTematica()));
        idiomacolumn.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getIdioma()));

        //configura la tabla de copia
        preciocopia.setCellValueFactory(new PropertyValueFactory<>("Precio"));
        referenciacopia.setCellValueFactory(cellData -> cellData.getValue().isCopiaReferencia() ? new SimpleStringProperty("SI") : new SimpleStringProperty("NO"));
        estadocopia.setCellValueFactory(new PropertyValueFactory<>("estado"));
        tipocopia.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        titulocopia.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getLibro().getTitulo()));
        columnrack.setCellValueFactory(new PropertyValueFactory<>("rack"));


        //configura los textfield de copia
        List<Rack> racks = rackService.obtenerTodos();
        choicerack.getItems().setAll(racks);
        referencia.getItems().setAll("SI", "NO");
        tipo.getItems().setAll(TipoLibro.values());
        estado.getItems().setAll(EstadoLibro.values());


        //controla la entrada de autores separados por comas
        autores.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Regex que permite solo letras (mayúsculas y minúsculas)
                if (!newValue.matches("[a-zA-Z, ]*")) {
                    // Si el nuevo valor contiene caracteres no permitidos, se borra
                    autores.setText(newValue.replaceAll("[^a-zA-Z, ]", ""));
                }
            }
        });

        //controla los campos de busqueda de libro para que se pueda

        // Configura el doble clic para cargar los campos textfield libro
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

        agregarListeners(busquedaautor, busquedatematica, busquedatitulo);
        agregarListeners(busquedatitulo, busquedaautor, busquedatematica);
        agregarListeners(busquedatematica, busquedatitulo, busquedaautor);

        // carga la tabla de libros
        cargarLibros();
    }




    private void ventanaPrestamo(ActionEvent event) {
        Enrutador.redirigir(event, "/vista/prestamo.fxml");
    }
    private void ventanaLibros(ActionEvent event) {
        Enrutador.redirigir(event, "/vista/libro.fxml");
    }
    private void ventanaUsuario(ActionEvent event) {
        Enrutador.redirigir(event, "/vista/usuario.fxml");
    }
    private void ventanaRack(ActionEvent event) {
        Enrutador.redirigir(event, "/vista/rack.fxml");
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
        tipo.setDisable(true);
        estado.setValue(copia.getEstado());
        precio.setText(String.valueOf(copia.getPrecio()));
        referencia.setValue(copia.isCopiaReferencia() ? "SI" : "NO");
        referencia.setDisable(true);
        choicerack.setValue(copia.getRack());
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

    private void cargarCopias(Libro libro){
        List<CopiaLibro> copias = libroservice.buscarCopiasPorIsbn(libro);

        ObservableList<CopiaLibro> listaCopias = FXCollections.observableArrayList(copias);

        tablecopia.setItems(listaCopias);

    }


    private void limpiarCamposLibro(){
        isbn.clear();
        titulo.clear();
        autores.clear();
        editorial.clear();
        tematica.clear();
        idioma.clear();
        isbn.setDisable(false);
        titulo.setDisable(false);
        autores.setDisable(false);
        editorial.setDisable(false);
        tematica.setDisable(false);
        idioma.setDisable(false);
        tablecopia.setItems(null);
        busquedatitulo.clear();
        busquedatematica.clear();
        busquedaautor.clear();
        limpiarCamposCopia();
        cargarLibros();
    }

    private void limpiarCamposCopia(){
        tipo.setValue(null);
        estado.setValue(null);
        precio.clear();
        referencia.setValue(null);
        choicerack.setValue(null);
        cantidadCopias.setDisable(false);
        referencia.setDisable(false);
        tipo.setDisable(false);
    }

    private void agregarLibro(){
        try{
            Set<String> autor = new HashSet<>(Arrays.asList(autores.getText().split(",")));
            libroservice.guardarLibro(new Libro(isbn.getText(), titulo.getText(), autor, editorial.getText(), tematica.getText(), idioma.getText()));
            cargarLibros();
        }catch (Exception e){
            Ventana.error("Error", "Error al agregar el libro:\n" + e.getMessage());
        }


    }

    private void eliminarLibro(){
        try{
            Libro libro = (Libro) tablelibro.getSelectionModel().getSelectedItem();
            libroservice.eliminarLibro(libro);
            cargarLibros();
        }catch (Exception e){
            Ventana.error("Error", "Error al eliminar el libro:\n" + e.getMessage());
        }
    }

    private void agregarCopia(){
        Libro libro = (Libro) tablelibro.getSelectionModel().getSelectedItem();
        try{
            libroservice.guardarCopia(new CopiaLibro((TipoLibro) tipo.getValue(), libro, (EstadoLibro) estado.getValue(), Double.parseDouble(precio.getText()),
                    (Rack) choicerack.getValue(), referencia.getValue().equals("SI")), Integer.parseInt(cantidadCopias.getText()));
            cargarCopias(libro);
            limpiarCamposCopia();

        }catch (Exception e){
            Ventana.error("Error", "Error al agregar la copia:\n" + e.getMessage());
        }
    }

    private void modificarCopia(){
        Libro libro = (Libro) tablelibro.getSelectionModel().getSelectedItem();
        try{
            CopiaLibro copia = (CopiaLibro) tablecopia.getSelectionModel().getSelectedItem();
            libroservice.modifcarCopia(copia, (EstadoLibro) estado.getValue(), (Rack) choicerack.getValue(), Double.parseDouble(precio.getText()));
            tablecopia.refresh();
            cargarCopias(libro);
            limpiarCamposCopia();
        }catch (Exception e){
            Ventana.error("Error", "Error al modificar la copia:\n" + e.getMessage());
        }
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

    private void abrirPantallaPrestamos(ActionEvent event, CopiaLibro copiaSeleccionada) {
        try {
            Enrutador.redirigir(event, "/vista/prestamo.fxml", copiaSeleccionada);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
