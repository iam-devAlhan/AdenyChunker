package com.adenychunker;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import java.io.File;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import com.adenychunker.classes.algorithms.Huffman;
import javafx.scene.control.ChoiceBox;
import javax.swing.JOptionPane;


public class FileEncoderController {

    @FXML
    private Button backBtn;

    @FXML
    private Button decoderBtn;

    @FXML
    private Button encoderBtn;
    
     @FXML
    private ChoiceBox<String> choiceBoxforAlgo;
    
    @FXML
    private Label filePathLabel;
    @FXML
    private Label filePathLabel1;

    @FXML
    private Button filechooserBtn;
    private String filePath;
//    Usage of directory and file chooser
    DirectoryChooser dirChooser = new DirectoryChooser();
    FileChooser fileChooser = new FileChooser();
//    Huffman Object used for encoding and decoding data
    Huffman huffman = new Huffman();
    private File selectedfile;
    
    
    @FXML
    void backToHomePage(ActionEvent event) throws IOException {
        App.setRoot("Main");
    }

    //    Decode your file by uploading .ctxt, .ccsv, .cjson as huffman encoded files
    @FXML
    void decodeDataFromFile(ActionEvent event) throws IOException {
        selectedfile = getFile(false);
        dirChooser.setTitle("Choose directory to save your decoded file");
        if (selectedfile != null) {
            filePathLabel.setText("Your selected file: " + selectedfile.getAbsolutePath());
        }
        String fileExtension = selectedfile.getPath();
        File outputDir = dirChooser.showDialog(new Stage());
        String outputDirPath = outputDir.getAbsolutePath();
        String inputFilePath = selectedfile.getAbsolutePath() + File.separator;
        String fileName = selectedfile.getName().replaceFirst("\\.[^.]+$","");
        if (selectedfile != null) {
            filePathLabel1.setText(selectedfile.getAbsolutePath());
        }
      
        if (fileExtension.endsWith(".ctxt")) {
            huffman.decodeToFile(inputFilePath, outputDirPath + File.separator + fileName + "-decoded" + ".txt");
        } else if (fileExtension.endsWith(".ccsv")) {
            huffman.decodeToFile(inputFilePath, outputDirPath + File.separator + fileName + "-decoded" +  ".csv");
        } else if (fileExtension.endsWith(".cjson")) {
            huffman.decodeToFile(inputFilePath, outputDirPath + File.separator + fileName + "-decoded" + ".json");
        } else {
            JOptionPane.showMessageDialog(null, "Error opening the file", "Error while decoding", JOptionPane.ERROR_MESSAGE);
        }
        
    }

//    Encode your file by uploading .txt, .csv, .json
    @FXML
    void encodeDataFromFile(ActionEvent event) throws IOException {
        selectedfile = getFile(true);
        dirChooser.setTitle("Choose directory to save your encoded file");
        File outputDir = dirChooser.showDialog(new Stage());
        String outputDirPath = outputDir.getAbsolutePath() + File.separator;
        String inputFilePath = selectedfile.getAbsolutePath();
        String fileName = selectedfile.getName().replaceFirst("\\.[^.]+$","");
        if (selectedfile != null) {
            filePathLabel.setText(selectedfile.getAbsolutePath());
        }
        String fileExtension = selectedfile.getPath();
        if (fileExtension.endsWith(".txt")) {
            huffman.encodeFromFile(inputFilePath, outputDirPath + File.separator + fileName + "-encoded" + ".ctxt");
        } else if (fileExtension.endsWith(".csv")) {
            huffman.encodeFromFile(inputFilePath, outputDirPath +  File.separator + fileName + "-encoded" + ".ccsv");
        } else if (fileExtension.endsWith(".json")) {
            huffman.encodeFromFile(inputFilePath, outputDirPath + File.separator + fileName + "-encoded" + ".cjson");
        } else {
            JOptionPane.showMessageDialog(null, "Error opening the file", "Error while encoding", JOptionPane.ERROR_MESSAGE);
        }
    }


    
    private File getFile(boolean isEncoding) {
        if (isEncoding) {
            FileChooser.ExtensionFilter textFileFilter = new FileChooser.ExtensionFilter("Text files, *.txt", "*.txt");
            FileChooser.ExtensionFilter csvFileFilter = new FileChooser.ExtensionFilter("CSV Files, *.csv", "*.csv");
            FileChooser.ExtensionFilter jsonFileFilter = new FileChooser.ExtensionFilter("JSON File, *.json", "*.json");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().addAll(textFileFilter, csvFileFilter, jsonFileFilter);
        }
        else {
            FileChooser.ExtensionFilter ctextFilter = new FileChooser.ExtensionFilter("CText files, *.ctxt", "*.ctxt");
            FileChooser.ExtensionFilter ccsvFilter = new FileChooser.ExtensionFilter("CCSV files, *.ccsv", "*.ccsv");
            FileChooser.ExtensionFilter cjsonFilter = new FileChooser.ExtensionFilter("CJson files, *.cjson", "*.cjson");
            fileChooser.getExtensionFilters().clear();
            fileChooser.getExtensionFilters().addAll(ctextFilter, ccsvFilter, cjsonFilter);
            fileChooser.setTitle("Select .ctxt, .ccsv or .cjson for decoding");
        }
        File file = fileChooser.showOpenDialog(new Stage());
        return file;
    }

}
