module principal {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;

    opens principal to javafx.fxml;
    opens controler to javafx.fxml;

    exports principal;
    exports controler;
}
