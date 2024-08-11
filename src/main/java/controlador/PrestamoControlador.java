package controlador;

import Repositorio.Repositorio;
import db.Conexion;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Modality;
import javafx.stage.Stage;
import modelo.*;
import servicio.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PrestamoControlador {
    private CopiaLibro copiaLibro;
    private PrestamoService prestamoservice;
    private UsuarioService usuarioService;
    private LibroService libroService;

    @FXML
    private Button buttonrack;
    @FXML
    private Button buttonPagePrestamos;
    @FXML
    private Button buttonPageLibros;
    @FXML
    private Button buttonPageUsuarios;
    @FXML
    private Button verdetalle;
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
        this.usuarioService = new UsuarioService(repositorio);
        this.libroService = new LibroService(repositorio);


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
            LocalDateTime date = celldata.getValue().getFechaDevolucion();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm");
            return new SimpleStringProperty(date != null ? date.format(formatter) : "");
        });

        //configura los botones de prestamo, detalle y busqueda
        prestamo.setOnAction(event -> prestar());
        devolucion.setOnAction(event -> devolver());
        buscardni.setOnAction(event -> buscarDni());
        buscarid.setOnAction(event -> buscarId());
        limpiar.setOnAction(event -> limpiarCampos());
        verdetalle.setOnAction(event -> verDetalles());
        // configura los valores de choicebox tipo de copia
        tipo.getItems().setAll(TipoLibro.values());

        cargarTabla();
    }

    @FXML
    private void verDetalles(Prestamo prestamoo) {
        try {
            // Cargar el FXML de la ventana emergente
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/vista/emergente.fxml"));
            Parent root = loader.load();

            // Obtener el controlador de la ventana emergente
            EmergenteControlador emergenteControlador = loader.getController();

            // Pasar los datos al controlador de la ventana emergente
            emergenteControlador.setDetallesPrestamo(prestamoo);  // Configura los detalles del préstamo aquí

            // Crear una nueva ventana (Stage) para la ventana emergente
            Stage stage = new Stage();
            stage.setTitle("Detalles del Préstamo");
            stage.initModality(Modality.APPLICATION_MODAL);  // Bloquea la ventana anterior hasta que esta se cierre
            stage.setScene(new Scene(root));
            stage.showAndWait();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void verDetalles(){
        Prestamo prestamo = (Prestamo) tvprestamo.getSelectionModel().getSelectedItem();
        if(prestamo == null){
            Ventana.error("Error", "Debe seleccionar un prestamo");
            return;
        }
        verDetalles(prestamo);
    }

    public void setCopia(CopiaLibro copiaLibro) {
        this.copiaLibro = copiaLibro;
        cargarCamposCopia(copiaLibro);
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

    private void cargarTabla(List<Prestamo> prestamos){
        ObservableList<Prestamo> listaprestamo = FXCollections.observableArrayList(prestamos);

        tvprestamo.setItems(listaprestamo);
    }

    private void cargarCamposCopia(CopiaLibro copia){
        if(copia != null){
            tipo.setValue(copiaLibro.getTipo());
            precio.setText(String.valueOf(copiaLibro.getPrecio()));
            titulo.setText(copiaLibro.getLibro().getTitulo());
            tipo.setDisable(true);
            precio.setDisable(true);
            titulo.setDisable(true);
        }

    }

    private void limpiarCampos(){
        dni.setText("");
        titulo.setText("");
        busquedadni.setText("");
        busquedaid.setText("");
        precio.setText("");
    }

    private void prestar(){
        try {
            prestamoservice.guardarPrestamo(copiaLibro, usuarioService.buscarPorDni(dni.getText()));
        }catch (Exception e){
            Ventana.error("Error al prestar libro", e.getMessage());
        }

        limpiarCampos();
        cargarTabla();
    }

    private void devolver(){
        try{
            Prestamo prestamo = (Prestamo) tvprestamo.getSelectionModel().getSelectedItem();
            prestamoservice.devolverPrestamo(prestamo);

        }catch (Exception e){
            Ventana.error("Error al devolver libro", e.getMessage());
        }
        tvprestamo.refresh();
        limpiarCampos();
        cargarTabla();
    }
    private void buscarDni(){
        try{
            if(busquedadni.getText().trim().isEmpty()){
                cargarTabla();
                return;
            }
            List<Prestamo> listaprestamo = prestamoservice.getLibrosPorUsuario(usuarioService.buscarPorDni(busquedadni.getText()));
            tvprestamo.refresh();
            cargarTabla(listaprestamo);
        }catch (Exception e){
            Ventana.error("Error al buscar prestamo", e.getMessage());
        }
    }
    private void buscarId(){
        try{
            if(busquedaid.getText().trim().isEmpty()){
                cargarTabla();
                return;
            }
            List<Prestamo> listaprestamo = prestamoservice.getUsuariosPorLibro(libroService.buscarLibroPorIsbn(busquedaid.getText()));

            tvprestamo.refresh();
            cargarTabla(listaprestamo);
        }catch (Exception e){
            Ventana.error("Error al buscar prestamo", e.getMessage());
        }
    }












}
