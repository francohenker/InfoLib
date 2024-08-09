/**
 * Sample Skeleton for 'UsuariosABM.fxml' Controller Class
 */

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
import modelo.App;
import modelo.Bibliotecario;
import modelo.EstadoMiembro;
import modelo.Usuario;
import servicio.Enrutador;
import servicio.LibroService;
import servicio.UsuarioService;
import servicio.Ventana;


import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static modelo.App.setRoot;

public class UsuarioControlador {
    private Repositorio repositorio;
    private UsuarioService usuarioService;
    private Ventana ve = new Ventana();

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
        buttonRack.setOnAction(event -> ventanaRack(event));
        buttonPagePrestamos.setOnAction(event -> ventanaPrestamo(event));
        buttonPageLibros.setOnAction(event -> ventanaLibros(event));
        buttonPageUsuarios.setOnAction(event -> ventanaUsuario(event));

        //configura los botones del abm
        buttonAgregarUsuario.setOnAction(event -> agregarUsuario());
        buttonEliminarUsuario.setOnAction(event -> eliminarUsuario());
        buttonModificarUsuario.setOnAction(event -> modificarUsuario());

        buttonlimpiar.setOnAction(event -> limpiarCampos());

        // configura los tipos de valores de la tabla
        dnicolumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getDni()));
        apellidocolumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getApellido()));
        nombrecolumna.setCellValueFactory(cellData -> new SimpleStringProperty(cellData.getValue().getNombre()));

        textfieldApellidoUsuario.textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                // Regex que permite solo letras (mayúsculas y minúsculas)
                if (!newValue.matches("[a-zA-Z]*")) {
                    // Si el nuevo valor contiene caracteres no permitidos, se borra
                    apellidocolumna.setText(newValue.replaceAll("[^a-zA-Z]", ""));
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
        if(estadousuario.getValue().equals(EstadoMiembro.BAJA)){
            Ventana.error("Error", "No se puede registrar un usuario dado de BAJA");
            return;
        }
        if (textfieldDNIUsuario.getText().trim().isEmpty()) {
            Ventana.error("Error", "El dni no puede estar vacío");
            return;
        }
        if (textfieldNombreUsuario.getText().trim().isEmpty()) {
            Ventana.error("Error", "El nombre no puede estar vacío");
            return;
        }
        if (textfieldApellidoUsuario.getText().trim().isEmpty()) {
            Ventana.error("Error", "El apellido no puede estar vacío");
            return;
        }
        if (textfieldPasswordUsuario.getText().trim().isEmpty()) {
            Ventana.error("Error", "La contrasenya no puede estar vacía");
            return;
        }
        if (textfieldPasswordUsuario.getText().trim().length() < 8 || textfieldPasswordUsuario.getText().trim().length() > 20) {
            Ventana.error("Error", "La contrasenya debe tener entre 8 y 20 caracteres");
            return;
        }
        if(choiceboxRolUsuario.getValue() == null){
            Ventana.error("Error", "Debe ingresar el rol del miembro");
            return;
        }
        if (!verificarNombre(textfieldNombreUsuario.getText().trim())) {
            Ventana.error("Error", "El nombre no puede contener símbolos ni números.");
            return;
        }
        if (!verificarNombre(textfieldApellidoUsuario.getText().trim())) {
            Ventana.error("Error", "El apellido no puede contener símbolos ni números.");
            return;
        }
        try {
            if (usuarioService.buscarPorDni(textfieldDNIUsuario.getText()) != null) {
                Ventana.error("El usuario ya existe", "Existe un usuario con este dni en el sistema");
                return;
            } else {
                if (choiceboxRolUsuario.getValue().equals("BIBLIOTECARIO")) {
                    usuarioService.guardarUsuario(new Bibliotecario(textfieldDNIUsuario.getText(), textfieldNombreUsuario.getText(), textfieldApellidoUsuario.getText(), textfieldPasswordUsuario.getText()));
                } else {
                    usuarioService.guardarUsuario(new Usuario(textfieldDNIUsuario.getText(), textfieldNombreUsuario.getText(), textfieldApellidoUsuario.getText(), textfieldPasswordUsuario.getText()));
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
        if (usuarioService.buscarPorDni(textfieldDNIUsuario.getText()) == null) {
            Ventana.error("El usuario no existe", "No existe un usuario con este dni en el sistema para modificar");
        } else {
            usuarioService.modificarUsuario(user, (EstadoMiembro) estadousuario.getValue(), textfieldPasswordUsuario.getText());

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
        if (usuarioService.buscarPorDni(user.getDni()) == null) {
            Ventana.error("El usuario no existe", "No existe un usuario con este dni en el sistema para dar de baja");
        } else {
            usuarioService.modificarUsuario(user, EstadoMiembro.BAJA);
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
        //ver el choiceboxrolusuario como limpiar
    }

    private boolean verificarNombre(String nombre) {
        if (!nombre.isEmpty()) {
            return nombre.matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+");
        }
        return false;
    }

}