package com.adenychunker;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;


public class VideoComController {

    @FXML
    private ChoiceBox<String> bitrateChoiceBox;

    @FXML
    private Button compressBtn;



    @FXML
    private ChoiceBox<String> resChoiceBox;

    @FXML
    private Button uploadBtn;

    @FXML
    void compressFinalVideoOutput(ActionEvent event) {

    }

    @FXML
    void fileChooserHandler(ActionEvent event) {

    }

    @FXML
    void backToHomePage(ActionEvent event) throws IOException{
        App.setRoot("Main");
    }


}
