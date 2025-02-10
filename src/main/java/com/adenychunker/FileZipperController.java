package com.adenychunker;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

public class FileZipperController {

    @FXML
    private Button btnChoose;

    @FXML
    private Button compressBtn;

    @FXML
    private Button decompressBtn;

    @FXML
    private Button fileAdder;

    @FXML
    private Label fileUrlLabel;

    @FXML
    private ListView<String> listView;

    @FXML
    void fileAdder(ActionEvent event) {

    }

    @FXML
    void fileDecompressBtn(ActionEvent event) {

    }

    @FXML
    void fileOnCompress(ActionEvent event) {

    }

    @FXML
    void filechooseHandler(ActionEvent event) {

    }
    
    @FXML
    void backToHomePage(ActionEvent event) throws IOException {
        App.setRoot("Main");
    }


}
