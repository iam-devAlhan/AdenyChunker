package com.adenychunker;

import java.io.IOException;
import java.io.File;
import javax.swing.JOptionPane;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import javafx.stage.Stage;

import io.github.techgnious.IVCompressor;
import io.github.techgnious.dto.IVAudioAttributes;
import io.github.techgnious.dto.IVSize;
import io.github.techgnious.dto.IVVideoAttributes;
import io.github.techgnious.dto.VideoFormats;
import io.github.techgnious.exception.VideoException;
import java.nio.file.Files;
import java.nio.file.Path;

public class VideoComController {
    
    @FXML
    private Label videoAttrLabel;

    @FXML
    private Button compressBtn;

    @FXML
    private ChoiceBox<String> resChoiceBox;
    
    @FXML
    private ChoiceBox<String> fpsChoiceBox;

    @FXML
    private Label fpsLabel;

    @FXML
    private Button uploadBtn;
    
    private File videoFile;
    private IVCompressor compressor = new IVCompressor();
    private IVVideoAttributes videoAttr = new IVVideoAttributes();
    private IVAudioAttributes audioAttr = new IVAudioAttributes();
    private IVSize videoSize = new IVSize();
    private FileChooser fc = new FileChooser();
    private DirectoryChooser dc = new DirectoryChooser();
    
    @FXML
    public void initialize() {
        // Add items to choice boxes
        resChoiceBox.getItems().addAll("1920x1080", "1280x720", "854x480", "640x360");
        fpsChoiceBox.getItems().addAll("24", "30", "60");

        // Set default values
        resChoiceBox.setValue("1280x720");
        fpsChoiceBox.setValue("30");
    }

    @FXML
    void fileChooserHandler(ActionEvent event) {
        try {
            fc.getExtensionFilters().addAll(new ExtensionFilter("Video Files", "*.mp4", "*.mkv"));
            fc.setTitle("Choose appropriate video file");
            videoFile = fc.showOpenDialog(new Stage());

            if (videoFile == null) {
                JOptionPane.showMessageDialog(null, "No video selected");
                return;
            } else {
                videoAttrLabel.setText("Your Video File: " + videoFile.getAbsolutePath());
                JOptionPane.showMessageDialog(null, "Check video details before compressing from their properties!");
               
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Unexpected error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    

    @FXML
    void compressVideoOutput() throws IOException, VideoException {
        if (videoFile == null) {
            JOptionPane.showMessageDialog(null, "Please select a video file first!");
            return;
        }

        String selectedResolution = resChoiceBox.getValue();
        String selectedFPS = fpsChoiceBox.getValue();

    
        switch (selectedResolution) {
            case "1280x720":
                videoSize.setHeight(720);
                videoSize.setWidth(1280);
                break;
            case "854x480":
                videoSize.setHeight(480);
                videoSize.setWidth(854);
                break;
            case "640x340":
                videoSize.setHeight(340);
                videoSize.setWidth(640);
                break;
            default:
                break;
        }
        videoAttr.setFrameRate(Integer.valueOf(selectedFPS));
        videoAttr.setSize(videoSize);
        byte[] video = getVideoBytes(videoFile.getAbsolutePath());
        compressor.encodeVideoWithAttributes(video, VideoFormats.MP4, audioAttr, videoAttr);
        
        // Compression logic here
        JOptionPane.showMessageDialog(null, "Compression started with settings:\n" +
                "Resolution: " + selectedResolution + "\n" +
                "FPS: " + selectedFPS);
         JOptionPane.showMessageDialog(null, "Completed!");
    }

    @FXML
    void backToHomePage(ActionEvent event) throws IOException {
        App.setRoot("Main");
    }
    
    public static byte[] getVideoBytes(String videoPath) throws IOException {
        File videoFile = new File(videoPath);
        
        if (!videoFile.exists()) {
            throw new IOException("Video file not found!");
        }
        
        // Use Files.readAllBytes to directly read the file into a byte array
        Path videoPathObj = videoFile.toPath();
        return Files.readAllBytes(videoPathObj);
    }
}
