module principal {
    requires javafx.controls;
    requires transitive javafx.graphics;
    requires javafx.fxml;

    opens principal to javafx.fxml;
    opens controler to javafx.fxml;

    exports principal;
    exports controler;
    exports model;
}
