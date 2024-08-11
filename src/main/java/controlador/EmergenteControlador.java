package controlador;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import modelo.Prestamo;

import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class EmergenteControlador {
    @FXML
    private TextField idprestamo;
    @FXML
    private TextField fechainicio;
    @FXML
    private TextField fechafin;
    @FXML
    private TextField multa;
    @FXML
    private TextField titulo;
    @FXML
    private TextField autor;
    @FXML
    private TextField editorial;
    @FXML
    private TextField tematica;
    @FXML
    private TextField idioma;
    @FXML
    private TextField tipo;
    @FXML
    private TextField precio;
    @FXML
    private TextField dni;
    @FXML
    private TextField apellido;
    @FXML
    private TextField nombre;
    @FXML
    private Button cerrar;

    @FXML
    void initialize() {
        //se configura el boton de cerrar la ventana
        cerrar.setOnAction(e  -> {
            Stage stage = (Stage) cerrar.getScene().getWindow();
            stage.close();
        });

        idprestamo.setDisable(true);
        fechainicio.setDisable(true);
        fechafin.setDisable(true);
        multa.setDisable(true);
        titulo.setDisable(true);
        autor.setDisable(true);
        editorial.setDisable(true);
        tematica.setDisable(true);
        idioma.setDisable(true);
        tipo.setDisable(true);
        precio.setDisable(true);
        dni.setDisable(true);
        apellido.setDisable(true);
        nombre.setDisable(true);

    }

    public void setDetallesPrestamo(Prestamo prestamo) {
        idprestamo.setText(String.valueOf(prestamo.getId()));

        fechainicio.setText(prestamo.getFechaPrestamo().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));

        fechafin.setText(prestamo.getFechaDevolucion() == null ? "AUN EN PRESTAMO" : prestamo.getFechaDevolucion().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm")));
        multa.setText(String.valueOf(prestamo.getMulta()));
        titulo.setText(prestamo.getCopiaLibro().getLibro().getTitulo());
        autor.setText(prestamo.getCopiaLibro().getLibro().getAutores().toString());
        editorial.setText(prestamo.getCopiaLibro().getLibro().getEditorial());
        tematica.setText(prestamo.getCopiaLibro().getLibro().getTematica());
        idioma.setText(prestamo.getCopiaLibro().getLibro().getIdioma());
        tipo.setText(prestamo.getCopiaLibro().getTipo().toString());
        precio.setText(String.valueOf(prestamo.getCopiaLibro().getPrecio()));
        dni.setText(prestamo.getUsuario().getDni());
        apellido.setText(prestamo.getUsuario().getApellido());
        nombre.setText(prestamo.getUsuario().getNombre());
    }
}
