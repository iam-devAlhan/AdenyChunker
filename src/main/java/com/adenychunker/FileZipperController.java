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
import javax.swing.JOptionPane;

public class FileZipperController {

    

    @FXML
    private Button compressBtn;

    @FXML
    private Button decompressBtn;

    @FXML
    private Button fileAdder;

    @FXML
    private Label fileUrlLabel;
    
    @FXML
    private Button deleteSelected;

    @FXML
    private ListView<String> listView;
    
    private File file;
    DirectoryChooser dc = new DirectoryChooser();
    FileChooser fc = new FileChooser();
    Zipper zipper = new Zipper();

    @FXML
    void fileAdder(ActionEvent event) {
        fc.setTitle("Choose file to add: ");
        file = fc.showOpenDialog(new Stage());
        fileUrlLabel.setText(file.getAbsolutePath());
        if (file != null) {
            ObservableList<String> items = listView.getItems();
            items.add(file.getAbsolutePath());
        }
    }

    @FXML
    void fileDecompressBtn(ActionEvent event) throws IOException {
        try {
            fc.setTitle("Choose zip file");
            File zipFile = fc.showOpenDialog(new Stage());
            zipper = new Zipper(zipFile.getAbsolutePath());
            dc.setTitle("Choose path to extract zip contents");
            File pathToExtract = dc.showDialog(new Stage());
            zipper.extract(pathToExtract.getAbsolutePath());
            JOptionPane.showMessageDialog(null, "Successfully extracted to: " + pathToExtract.getAbsolutePath(), "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error while unzipping the package", "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }

    @FXML
    void fileOnCompress(ActionEvent event) throws IOException {
        try {
            dc.setTitle("Choose directory to save compressed zip archive");
            File pathToSave = dc.showDialog(new Stage());
            String archive = JOptionPane.showInputDialog("Enter zip file name to archive");
            String finalPath = pathToSave + File.separator + archive;
            String files[];
            ObservableList<String> items = listView.getItems();
            files = items.toArray(String[]::new);
            zipper.compress(finalPath, files);
            JOptionPane.showMessageDialog(null, "Successfully archived to: " + finalPath, "SUCCESS", JOptionPane.INFORMATION_MESSAGE);
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Error! Cannot archive items" + e.getMessage() , "ERROR", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    @FXML
    void fileDeleteHandler(ActionEvent event) {
        String selectedFile = listView.getSelectionModel().getSelectedItem();
        listView.getItems().remove(selectedFile);
    }
    
    @FXML
    void backToHomePage(ActionEvent event) throws IOException {
        App.setRoot("Main");
    }


}
