module com.adenychunker {
    requires javafx.controls;
    requires javafx.fxml;

    opens com.adenychunker to javafx.fxml;
    exports com.adenychunker;
}
