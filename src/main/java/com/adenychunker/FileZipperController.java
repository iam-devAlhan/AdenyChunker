package com.adenychunker;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import com.adenychunker.classes.utils.Zipper;
import java.io.File;
import javafx.collections.ObservableList;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

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
    
    private File file;
    DirectoryChooser dc = new DirectoryChooser();
    FileChooser fc = new FileChooser();

    @FXML
    void fileAdder(ActionEvent event) {
        if (file != null) {
            ObservableList<String> items = listView.getItems();
            items.add(file.getAbsolutePath());
        }
    }

    @FXML
    void fileDecompressBtn(ActionEvent event) {

    }

    @FXML
    void fileOnCompress(ActionEvent event) {

    }

    @FXML
    void filechooseHandler(ActionEvent event) throws IOException {
        fc.setTitle("Choose file to add: ");
        file = fc.showOpenDialog(new Stage());
        fileUrlLabel.setText(file.getAbsolutePath());
        
    }
    
    @FXML
    void backToHomePage(ActionEvent event) throws IOException {
        App.setRoot("Main");
    }


}
