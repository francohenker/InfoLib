module modules {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;

    opens modelo to javafx.fxml;
    exports modelo;
}
