module modules {
    requires javafx.controls;
    requires javafx.fxml;
    requires jakarta.persistence;
    requires org.hibernate.orm.core;


    opens modelo to javafx.fxml, org.hibernate.orm.core;
    exports modelo;
}
