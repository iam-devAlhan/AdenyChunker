package com.adenychunker;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;

public class FileEncoderController {

    @FXML
    private Button backBtn;

    @FXML
    private Button decoderBtn;

    @FXML
    private Label decodingProcess;

    @FXML
    private ProgressBar decodingProgress;

    @FXML
    private Button encoderBtn;

    @FXML
    private Label encodingProcess;

    @FXML
    private ProgressBar encodingProgress;

    @FXML
    private Button filechooserBtn;

    @FXML
    void backToHomePage(ActionEvent event) throws IOException{
        App.setRoot("Main");
    }

    @FXML
    void decodeDataFromFile(ActionEvent event) {

    }

    @FXML
    void encodeDataFromFile(ActionEvent event) {

    }

    @FXML
    void onFileUpload(ActionEvent event) {

    }

}
