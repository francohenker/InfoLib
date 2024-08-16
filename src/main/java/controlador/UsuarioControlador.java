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
import modelo.Bibliotecario;
import modelo.EstadoMiembro;
import modelo.Usuario;
import servicio.Enrutador;
import servicio.UsuarioService;
import servicio.Ventana;

import java.util.List;
import java.util.stream.Collectors;

public class UsuarioControlador {
    private Repositorio repositorio;
    private UsuarioService usuarioService;

    @FXML
    private Button salir;
    @FXML
    private Button buttonPagePrestamos;
    @FXML
    private Button buttonPageUsuarios;
    @FXML
    private Button buttonPageLibros;
    @FXML
    private Button buttonRack;
    @FXML
    private Button buttonAgregarUsuario;
    @FXML
    private Button buttonEliminarUsuario;
    @FXML
    private Button buttonModificarUsuario;
    @FXML
    private Button buttonlimpiar;
    @FXML
    private TextField textfieldDNIUsuario;
    @FXML
    private TextField textfieldApellidoUsuario;
    @FXML
    private TextField textfieldNombreUsuario;
    @FXML
    private TextField textfieldPasswordUsuario;
    @FXML
    private ChoiceBox choiceboxRolUsuario;
    @FXML
    private TableView tvusuario;
    @FXML
    private ChoiceBox estadousuario;
    @FXML
    private TableColumn<Usuario, String> dnicolumna;
    @FXML
    private TableColumn<Usuario, String> apellidocolumna;
    @FXML
    private TableColumn<Usuario, String> nombrecolumna;
    @FXML
    private TableColumn<Usuario, String> rolcolumna;

    @FXML
    void initialize() {
        this.repositorio = new Repositorio(Conexion.getEntityManagerFactory());
        this.usuarioService = new UsuarioService(repositorio);

        //configura la accion de los botones laterales
        buttonRack.setOnAction(this::ventanaRack);
        buttonPagePrestamos.setOnAction(this::ventanaPrestamo);
        buttonPageLibros.setOnAction(this::ventanaLibros);
        buttonPageUsuarios.setOnAction(this::ventanaUsuario);

        //configura el boton de deslogeo
        salir.setOnAction(Enrutador::salir);


        //configura los botones del abm
        buttonAgregarUsuario.setOnAction(event -> agregarUsuario());
        buttonEliminarUsuario.setOnAction(event -> eliminarUsuario());
        buttonModificarUsuario.setOnAction(event -> modificarUsuario());

        buttonlimpiar.setOnAction(event -> limpiarCampos());

        // configura los tipos de valores de la tabla
        dnicolumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        apellidocolumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        nombrecolumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        //controla la entrada unica de letras en apellido
        textfieldApellidoUsuario.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Regex que permite solo letras (mayúsculas y minúsculas)
                if (!newValue.matches("[a-zA-Z]*")) {
                    // Si el nuevo valor contiene caracteres no permitidos, se borra
                    textfieldApellidoUsuario.setText(newValue.replaceAll("[^a-zA-Z]", ""));
                }
            }
        });

        //controla la entrada unica de letras en nombre
        textfieldNombreUsuario.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Regex que permite solo letras (mayúsculas y minúsculas)
                if (!newValue.matches("[a-zA-Z]*")) {
                    // Si el nuevo valor contiene caracteres no permitidos, se borra
                    textfieldNombreUsuario.setText(newValue.replaceAll("[^a-zA-Z]", ""));
                }
            }
        });
        //controla la entrada unica de numeros en el dni
        textfieldDNIUsuario.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Regex que permite solo letras (mayúsculas y minúsculas)
                if (!newValue.matches("[0-9]*")) {
                    // Si el nuevo valor contiene caracteres no permitidos, se borra
                    textfieldDNIUsuario.setText(newValue.replaceAll("[^0-9]", ""));
                }
            }
        });

        rolcolumna.setCellValueFactory(cellData -> {
            Usuario usuario = cellData.getValue();
            if (usuario instanceof Bibliotecario) {
                return new SimpleStringProperty("BIBLIOTECARIO");
            } else {
                return new SimpleStringProperty("USUARIO");
            }
        });

        estadousuario.setItems(FXCollections.observableArrayList(EstadoMiembro.values()));
        estadousuario.setValue(EstadoMiembro.ALTA);
        choiceboxRolUsuario.setItems(FXCollections.observableArrayList("BIBLIOTECARIO", "USUARIO"));


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
                    estadousuario.setDisable(false);
                    estadousuario.setValue(usuario.getEstado());
                    choiceboxRolUsuario.setDisable(true);
                }
            });
            return row;
        });
        //carga los datos de la base de datos en la tabla
        cargarTabla();
    }

    private void rellenarCampos(Usuario usuario) {
        textfieldDNIUsuario.setText(usuario.getDni());
        textfieldDNIUsuario.setDisable(true);
        textfieldNombreUsuario.setText(usuario.getNombre());
        textfieldNombreUsuario.setDisable(true);
        textfieldApellidoUsuario.setText(usuario.getApellido());
        textfieldApellidoUsuario.setDisable(true);
//        textfieldPasswordUsuario.setText("**********");
        estadousuario.setValue(usuario.getEstado());
        choiceboxRolUsuario.setValue(usuario instanceof Bibliotecario ? "BIBLIOTECARIO" : "USUARIO");
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

    private void cargarTabla() {
        var us = new UsuarioService(new Repositorio(Conexion.getEntityManagerFactory()));
        List<Usuario> usuarios = us.obtenerTodos();
        List<Bibliotecario> bibliotecarios = usuarios.stream()
                .filter(usuario -> usuario instanceof Bibliotecario)
                .map(usuario -> (Bibliotecario) usuario)
                .collect(Collectors.toList());

        ObservableList<Usuario> listaPersonas = FXCollections.observableArrayList(usuarios);

        tvusuario.setItems(listaPersonas);
    }


    private void agregarUsuario() {
        try {
            if (usuarioService.buscarPorDni(textfieldDNIUsuario.getText()) != null) {
                Ventana.error("El usuario ya existe", "Existe un usuario con este dni en el sistema");
                return;
            } else {
                if (choiceboxRolUsuario.getValue().equals("BIBLIOTECARIO")) {
                    usuarioService.guardarUsuario(new Bibliotecario(textfieldDNIUsuario.getText(), textfieldNombreUsuario.getText(), textfieldApellidoUsuario.getText(), textfieldPasswordUsuario.getText()), textfieldPasswordUsuario.getText());
                } else {
                    usuarioService.guardarUsuario(new Usuario(textfieldDNIUsuario.getText(), textfieldNombreUsuario.getText(), textfieldApellidoUsuario.getText(), textfieldPasswordUsuario.getText()), textfieldPasswordUsuario.getText());
                }
            }
            cargarTabla();
            limpiarCampos();
        } catch (Exception e) {
            Ventana.error("Error al agregar usuario", e.getMessage());
        }
    }

    private void modificarUsuario() {
        Usuario user = (Usuario) tvusuario.getSelectionModel().getSelectedItem();
        if (user == null) {
            Ventana.error("No hay usuario seleccionado", "No hay usuario seleccionado para modificar");
            return;
        }
        try{
            if (usuarioService.buscarPorDni(textfieldDNIUsuario.getText()) == null) {
                Ventana.error("El usuario no existe", "No existe un usuario con este dni en el sistema para modificar");
            } else {
                if(textfieldPasswordUsuario.getText().trim().isEmpty()){
                    usuarioService.modificarUsuario(user, (EstadoMiembro) estadousuario.getValue());
                }else{
                    usuarioService.modificarUsuario(user, (EstadoMiembro) estadousuario.getValue(), textfieldPasswordUsuario.getText());
                }
            }
        }catch (Exception e){
            Ventana.error("Error al modificar usuario", e.getMessage());
        }
        cargarTabla();
        limpiarCampos();
    }

    private void eliminarUsuario() {
        Usuario user = (Usuario) tvusuario.getSelectionModel().getSelectedItem();
        if (user == null) {
            Ventana.error("No hay usuario seleccionado", "No hay usuario seleccionado para dar de baja");
            return;
        }
        try{
            if (usuarioService.buscarPorDni(user.getDni()) == null) {
                Ventana.error("El usuario no existe", "No existe un usuario con este dni en el sistema para dar de baja");
            } else {
                usuarioService.modificarUsuario(user, EstadoMiembro.BAJA);
            }
        }catch (Exception e){
            Ventana.error("Error al dar de baja usuario", e.getMessage());
        }
        cargarTabla();
        limpiarCampos();
    }

    private void limpiarCampos() {
        textfieldApellidoUsuario.setDisable(false);
        textfieldNombreUsuario.setDisable(false);
        textfieldDNIUsuario.setDisable(false);
        textfieldApellidoUsuario.clear();
        textfieldNombreUsuario.clear();
        textfieldDNIUsuario.clear();
        textfieldPasswordUsuario.clear();
        choiceboxRolUsuario.setValue(null);
        choiceboxRolUsuario.setDisable(false);
        estadousuario.setValue(null);
    }



}