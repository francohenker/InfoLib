/**
 * Sample Skeleton for 'UsuariosABM.fxml' Controller Class
 */

package controlador;


import Repositorio.Repositorio;
import db.Conexion;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import modelo.App;
import modelo.EstadoMiembro;
import modelo.Usuario;
import servicio.Enrutador;
import servicio.LibroService;
import servicio.UsuarioService;
import servicio.Ventana;


import java.util.List;

import static modelo.App.setRoot;

public class UsuarioControlador {
    private Repositorio repositorio;
    private UsuarioService usuarioService;
    private Ventana ve = new Ventana();
    private Enrutador en = new Enrutador();

    @FXML
    private Button buttonPagePrestamos;
    @FXML
    private Button buttonPageUsuarios;
    @FXML
    private Button buttonPageLibros;
    @FXML
    private TextField textfieldDNIUsuario;
    @FXML
    private TextField textfieldApellidoUsuario;
    @FXML
    private Button buttonRack;
    @FXML
    private TextField textfieldNombreUsuario;
    @FXML
    private Button buttonAgregarUsuario;
    @FXML
    private TextField textfieldPasswordUsuario;
    @FXML
    private Button buttonEliminarUsuario;
    @FXML
    private ChoiceBox choiceboxRolUsuario;
    @FXML
    private Button buttonModificarUsuario;
    @FXML
    private TableView  tvusuario;
    @FXML
    private TableColumn <Usuario, String> dnicolumna;
    @FXML
    private TableColumn <Usuario, String> apellidocolumna;
    @FXML
    private TableColumn <Usuario, String> nombrecolumna;
    @FXML
    private TableColumn <Usuario, String> rolcolumna;

    @FXML
    void initialize() {
        //configura la accion de los botones laterales
        buttonRack.setOnAction(event -> ventanaRack(event));
        buttonPagePrestamos.setOnAction(event -> ventanaPrestamo(event));
        buttonPageLibros.setOnAction(event -> ventanaLibros(event));
        buttonPageUsuarios.setOnAction(event -> ventanaUsuario(event));

//        buttonAgregarUsuario.setOnAction(event -> cargarUsuario());
        // configura los tipos de valores de la tabla
        dnicolumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        apellidocolumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        nombrecolumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));
        rolcolumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));

        // Configurar el doble clic para cargar los datos en los campos de texto
        // y el color rojo de los usuarios dados de BAJA del sistema
        tvusuario.setRowFactory(tv -> {
            TableRow<Usuario> row = new TableRow<>() {
                @Override
                protected void updateItem(Usuario usuario, boolean empty) {
                    super.updateItem(usuario, empty);

                    if (usuario == null || empty) {
                        setStyle("");
                    } else {
                        // Cambiar color de la fila si el estado es BAJA
                        if (usuario.getEstado().equals(EstadoMiembro.BAJA)) {
                            setStyle("-fx-background-color: #ff9999;"); // Rojo claro
                        } else {
                            setStyle(""); // Restablecer estilo si no es BAJA
                        }
                    }
                }
            };
            // Configurar el doble clic
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Usuario usuario = row.getItem();
                    rellenarCampos(usuario);
                }
            });

            return row;
        });

        //carga los datos de la base de datos en la tabla
        cargarTabla();
    }

    private void rellenarCampos(Usuario usuario){
        textfieldDNIUsuario.setText(usuario.getDni());
        textfieldNombreUsuario.setText(usuario.getNombre());
        textfieldApellidoUsuario.setText(usuario.getApellido());
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

    private void cargarTabla(){
        var us = new UsuarioService(new Repositorio(Conexion.getEntityManagerFactory()));
        List<Usuario> personas = us.obtenerTodos();

        ObservableList<Usuario> listaPersonas = FXCollections.observableArrayList(personas);

        tvusuario.setItems(listaPersonas);
    }

    public void cargarCampos(){

    }

//    public void cargarUsuario() {
//        //APLICAR VALIDACIONES
//        var repo = new Repositorio(Conexion.getEntityManagerFactory());
//        var us = new UsuarioService(repo);
//        if(us.buscarPorDni(textfieldDNIUsuario.getText()) != null) {
//            us.modificarUsuario(new Usuario(textfieldDNIUsuario.getText(), textfieldNombreUsuario.getText(),
//                    textfieldApellidoUsuario.getText(), textfieldPasswordUsuario.getText()), choiceboxRolUsuario.getConverter().toString(choiceboxRolUsuario.getValue()));
//        }else {
//            usuarioService.guardarUsuario();
//
//        }
//
//    }

    public void eliminarUsuario() {

    }

























}
