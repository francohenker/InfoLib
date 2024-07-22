module modules {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;

    //  opens com.example.View to javafx.fxml;
    // opens com.example.Controller to javafx.fxml;
 
    // exports com.example.View to javafx.fxml;
    // exports com.example.Controller to javafx.fxml;

    opens modelo to javafx.fxml, org.hibernate.orm.core;
    opens controlador to javafx.fxml, org.hibernate.orm.core;
    exports controlador;
    exports modelo;
}
