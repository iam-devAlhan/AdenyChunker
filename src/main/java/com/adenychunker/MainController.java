package com.adenychunker;

import java.awt.event.MouseEvent;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class MainController {
    
    @FXML
    private Pane paneBox01;
    
    @FXML
    private Pane paneBox02;
    
    @FXML
    private Pane paneBox03;
    
    @FXML
    private Button btnComText;

    @FXML
    private Button btnComVideo;

    @FXML
    private Button btnZipFile;
   
     @FXML
    void textCompressionHandler(ActionEvent event) throws IOException {
        App.setRoot("FileEncoder");
    }

    @FXML
    void videoCompressionHandler(ActionEvent event) throws IOException {
//        App.setRoot("VideoCom");
    }

    @FXML
    void zipFileHandler(ActionEvent event) throws IOException{
        App.setRoot("FileZipper");
    }

}
