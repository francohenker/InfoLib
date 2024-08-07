/**
 * Sample Skeleton for 'Inicio.fxml' Controller Class
 */

package controlador;


import Repositorio.Repositorio;
import db.Conexion;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.App;
import servicio.UsuarioService;

public class loginControlador {
    private Repositorio repositorio;
    private UsuarioService usuarioService;


    @FXML
    private TextField campoUsuario;
    @FXML
    private TextField campoContraseña;
    @FXML
    private Button botonIngresar;

    @FXML
    public void initialize(){
        botonIngresar.setOnAction(event -> login());
    }

    @FXML
    public void login(){
        var repo = new Repositorio(Conexion.getEntityManagerFactory());
        var us = new UsuarioService(repo);
        String dniUsuario = campoUsuario.getText();
        String contrasenia = campoContraseña.getText();
        if(us.checkContraseña(contrasenia, dniUsuario)){
            System.out.println("login correcto");
        }

    }



}
