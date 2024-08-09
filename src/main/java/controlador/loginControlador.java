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
    @FXML
    private TextField campoUsuario;
    @FXML
    private TextField campoContraseña;
    @FXML
    private Button botonIngresar;

    @FXML
    public void initialize(){
        this.repositorio = new Repositorio(Conexion.getEntityManagerFactory());
        this.usuarioService = new UsuarioService(repositorio);
        botonIngresar.setOnAction(event -> login(event));
    }

    @FXML
    public void login(ActionEvent event){
        String dniUsuario = campoUsuario.getText();
        String contraseña = campoContraseña.getText();
        try{
            usuarioService.checkContraseña(contraseña, dniUsuario);
            Enrutador.redirigir(event, "/vista/usuario.fxml");
        }catch (Exception e){
            Ventana.error("Error","Usuario y/o contraseña incorrectos");
        }

    }



}
