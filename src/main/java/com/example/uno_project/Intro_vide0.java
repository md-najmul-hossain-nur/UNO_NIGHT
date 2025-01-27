package com.example.uno_project;

import javafx.fxml.FXML;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.util.Duration;
import javafx.animation.PauseTransition;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;

public class Intro_vide0 {

    @FXML
    private MediaView mediaView;

    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        // Specify the path to your video file
        String videoPath = "src/main/resources/Images/UNO_INTRO.mp4"; // Update this with your actual path

        // Load the media file
        File videoFile = new File(videoPath);
        if (videoFile.exists()) {
            Media media = new Media(videoFile.toURI().toString());
            mediaPlayer = new MediaPlayer(media);
            mediaView.setMediaPlayer(mediaPlayer);

            // Auto-play the video
            mediaPlayer.play();

            // Set up a transition to switch scenes after 4 seconds
            PauseTransition pause = new PauseTransition(Duration.seconds(4));
            pause.setOnFinished(event -> {
                try {
                    changeScene(); // Call the scene-changing method
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        } else {
            System.out.println("Video file not found: " + videoPath);
        }
    }

    private void changeScene() throws IOException {
        // Load the new FXML file
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Uno.fxml")); // Update with your FXML file name
        Parent root = loader.load();

        // Get the current stage
        Stage stage = (Stage) mediaView.getScene().getWindow();

        // Set the new scene
        Scene newScene = new Scene(root);
        stage.setScene(newScene);

        // Optionally, set a title for the new scene
        stage.setTitle("Next Page");
    }
}
