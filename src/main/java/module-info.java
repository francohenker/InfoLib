module modules {
    requires javafx.controls;
    requires javafx.fxml;

    opens modules to javafx.fxml;
    exports modules;
}
