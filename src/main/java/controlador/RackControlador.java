package controlador;

import Repositorio.Repositorio;
import db.Conexion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.Rack;
import servicio.Enrutador;
import servicio.RackService;
import servicio.Ventana;

import java.util.List;

public class RackControlador {
    private RackService rackService;
    private Enrutador en;

    @FXML
    private Button buttonPagePrestamos;
    @FXML
    private Button buttonPageUsuarios;
    @FXML
    private Button buttonPageLibros;
    @FXML
    private Button buttonRack;
    @FXML
    private Button agregarrack;
    @FXML
    private Button eliminarack;
    @FXML
    private Button modificarrack;
    @FXML
    private TextField descripcion;
    @FXML
    private TableView tvrack;
    @FXML
    private TableColumn <Rack, String> columnaId;
    @FXML
    private TableColumn <Rack, String> columnadescripcion;




    @FXML
    void initialize() {
        Repositorio repositorio = new Repositorio(Conexion.getEntityManagerFactory());
        this.rackService = new RackService(repositorio);

        //configura la accion de los botones laterales
        buttonRack.setOnAction(this::ventanaRack);
        buttonPagePrestamos.setOnAction(this::ventanaPrestamo);
        buttonPageLibros.setOnAction(this::ventanaLibros);
        buttonPageUsuarios.setOnAction(this::ventanaUsuario);

        //configura los botones del abm
        agregarrack.setOnAction(event -> agregar());
        eliminarack.setOnAction(event -> eliminar());
        modificarrack.setOnAction(event -> modificar());

        //configura las columnas de la tabla
        columnaId.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getId()));
        columnadescripcion.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDescripcion()));

        // Configurar el doble clic
        tvrack.setRowFactory( tv -> {
            TableRow<Rack> row = new TableRow<>();
            row.setOnMouseClicked(event ->
            {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Rack rack = row.getItem();
                    rellenarCampo(rack);
                }
            });
            return row;
        });
        //carga los datos de la tabla
        cargarTabla();
    }

    private void cargarTabla(){
        List<Rack> racks = rackService.obtenerTodos();

        ObservableList<Rack> listarack = FXCollections.observableArrayList(racks);

        tvrack.setItems(listarack);
    }

    private void rellenarCampo(Rack rack){
        descripcion.setText(rack.getDescripcion());
    }

    private void agregar(){
        Rack rack = new Rack();
        rack.updateDescripcion(descripcion.getText());
        rackService.guardarRack(rack);
        cargarTabla();
    }
    private void eliminar(){
        try{
            Rack rackSeleccionado = (Rack) tvrack.getSelectionModel().getSelectedItem();
            rackService.borrarRack(rackSeleccionado);
            cargarTabla();

        }catch (Exception e){
            Ventana.error("Error al eliminar rack", e.getMessage());
        }
    }
    private void modificar(){
        Rack rack = (Rack) tvrack.getSelectionModel().getSelectedItem();
        rack.updateDescripcion(descripcion.getText());
        rackService.modificarRack(rack);
        cargarTabla();
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










}
