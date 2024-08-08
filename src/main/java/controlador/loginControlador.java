/**
 * Sample Skeleton for 'Inicio.fxml' Controller Class
 */

package controlador;


import Repositorio.Repositorio;
import db.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import servicio.Enrutador;
import servicio.UsuarioService;
import servicio.Ventana;

public class loginControlador {
    private Repositorio repositorio;
    private UsuarioService usuarioService;
    private Ventana ve = new Ventana();
    private Enrutador en = new Enrutador();
    @FXML
    private TextField campoUsuario;
    @FXML
    private TextField campoContraseña;
    @FXML
    private Button botonIngresar;

    @FXML
    public void initialize(){
        botonIngresar.setOnAction(event -> login(event));
    }

    @FXML
    public void login(ActionEvent event){
        var repo = new Repositorio(Conexion.getEntityManagerFactory());
        var us = new UsuarioService(repo);
        String dniUsuario = campoUsuario.getText();
        String contraseña = campoContraseña.getText();
        try{
            us.checkContraseña(contraseña, dniUsuario);
            Enrutador.redirigir(event, "/vista/usuario.fxml");
        }catch (Exception e){
            ve.error("Usuario y/o contraseña incorrectos");
        }

    }



}
