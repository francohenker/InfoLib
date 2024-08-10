package controlador;

import Repositorio.Repositorio;
import db.Conexion;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Prestamo;
import modelo.TipoLibro;
import modelo.Usuario;
import servicio.Enrutador;
import servicio.PrestamoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrestamoControlador {
    private PrestamoService prestamoservice;
    @FXML
    private Button buttonrack;
    @FXML
    private Button buttonPagePrestamos;
    @FXML
    private Button buttonPageLibros;
    @FXML
    private Button buttonPageUsuarios;
    @FXML
    private Button buscardni;
    @FXML
    private Button buscarid;
    @FXML
    private Button limpiar;
    @FXML
    private Button prestamo;
    @FXML
    private Button devolucion;
    @FXML
    private TextField dni;
    @FXML
    private TextField titulo;
    @FXML
    private TextField busquedadni;
    @FXML
    private TextField busquedaid;
    @FXML
    private TextField precio;
    @FXML
    private ChoiceBox tipo;
    @FXML
    private TableView tvprestamo;
    @FXML
    private TableColumn <Prestamo, String> columnaid;
    @FXML
    private TableColumn <Prestamo, String> columnadni;
    @FXML
    private TableColumn <Prestamo, String> columnatitulo;
    @FXML
    private TableColumn <Prestamo, TipoLibro> columnatipo;
    @FXML
    private TableColumn <Prestamo, Double> columnaprecio;
    @FXML
    private TableColumn <Prestamo, Double> columnamulta;
    @FXML
    private TableColumn <Prestamo, String> columnainicio;
    @FXML
    private TableColumn <Prestamo, String> columnadevolucion;

    @FXML
    void initialize() {
        Repositorio repositorio = new Repositorio(Conexion.getEntityManagerFactory());
        this.prestamoservice = new PrestamoService(repositorio);

        //configura la accion de los botones laterales
        buttonrack.setOnAction(this::ventanaRack);
        buttonPagePrestamos.setOnAction(this::ventanaPrestamo);
        buttonPageLibros.setOnAction(this::ventanaLibros);
        buttonPageUsuarios.setOnAction(this::ventanaUsuario);


        //configura la tabla prestamos
        columnaid.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId().toString()));
        columnadni.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getUsuario().getDni()));

        columnatitulo.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getCopiaLibro().getLibro().getTitulo()));
        columnatipo.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCopiaLibro().getTipo()));
        columnaprecio.setCellValueFactory(cellData -> new SimpleObjectProperty<>(cellData.getValue().getCopiaLibro().getPrecio()));
        columnamulta.setCellValueFactory(new PropertyValueFactory<>("multa"));
        columnainicio.setCellValueFactory(cellData -> {
            LocalDateTime date = cellData.getValue().getFechaPrestamo();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return new SimpleStringProperty(date != null ? date.format(formatter) : "");
        } );
        columnadevolucion.setCellValueFactory(celldata -> {
            LocalDateTime date = celldata.getValue().getFechaPrestamo();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return new SimpleStringProperty(date != null ? date.format(formatter) : "");
        });

        tipo.getItems().setAll(TipoLibro.values());


        cargarTabla();
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


    private void cargarTabla(){
        List<Prestamo> prestamo = prestamoservice.obtenerTodos();

        ObservableList<Prestamo> listaprestamo = FXCollections.observableArrayList(prestamo);

        tvprestamo.setItems(listaprestamo);
    }
















}
