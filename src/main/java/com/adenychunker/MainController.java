package com.adenychunker;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainController {

    @FXML
    private Button exitBtn;

    @FXML
    void exitButtonAction(ActionEvent event) {
        System.exit(0);
    }   

}