module com.adenychunker {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.base;
    requires java.desktop;
    requires IVCompressor;

    opens com.adenychunker to javafx.fxml;
    exports com.adenychunker;
}
