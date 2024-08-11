package controlador;

import Repositorio.Repositorio;
import db.Conexion;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import modelo.App;
import modelo.Bibliotecario;
import servicio.Enrutador;
import servicio.UsuarioService;
import servicio.Ventana;

public class LoginControlador {
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
        botonIngresar.setOnAction(this::login);
    }

    @FXML
    public void login(ActionEvent event){
        String dniUsuario = campoUsuario.getText();
        String contraseña = campoContraseña.getText();
        try{
            usuarioService.checkContraseña(contraseña, dniUsuario);
            App.usuario = usuarioService.buscarPorDni(dniUsuario);

            if(App.usuario instanceof Bibliotecario){
                Enrutador.redirigir(event, "/vista/libro.fxml");
            }else{
                Enrutador.redirigir(event, "/vista/librousuario.fxml");
            }
        }catch (Exception e){
            Ventana.error("Error","Usuario y/o contraseña incorrectos");
            e.printStackTrace();
        }
    }
}
