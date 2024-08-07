/**
 * Sample Skeleton for 'UsuariosABM.fxml' Controller Class
 */

package controlador;


import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import modelo.App;

import static modelo.App.setRoot;

public class UsuarioControlador {

    @FXML
    private Button buttonprestamos;
    @FXML
    private Button buttonusuarios;
    @FXML
    private Button buttonlibros;
    @FXML
    private TextField textfieldDNIUsuario;
    @FXML
    private TextField textfieldApellidoUsuario;
    @FXML
    private TextField nombreUsuario;
    @FXML
    private TextField contrasenaUsuario;
    @FXML
    private Button agregarUsuario;
    @FXML
    private TextField textfieldPasswordUsuario;
    @FXML
    private Button buttonEliminarUsuario;
    @FXML
    private ChoiceBox choiceboxRolUsuario;
    @FXML
    private Button buttonModificarUsuario;
    @FXML
    private Button buttonEliminarContrasena;


    @FXML
    void initialize() {
//        buttonprestamos.setOnAction(event -> App.setRoot("prestamo"));
//        buttonlibros.setOnAction(event -> App.setRoot("libro"));
//        buttonusuarios.setOnAction(event -> App.setRoot("usuario"));
    }




}
