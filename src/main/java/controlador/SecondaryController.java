package controlador;

import java.io.IOException;
import javafx.fxml.FXML;
import modelo.App;

public class SecondaryController {

   @FXML
   private void switchToPrimary() throws IOException {
       App.setRoot("primary");
   }
}