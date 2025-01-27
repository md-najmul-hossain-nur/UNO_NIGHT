package com.example.uno_project;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class Gameover {

    @FXML
    private Button Exit;

    @FXML
    private MediaView Media;

    @FXML
    private Button playagain;

    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        playBackgroundMusic();

        try {

            String videoPath = Objects.requireNonNull(getClass().getResource("/Images/LOSE.mp4")).toExternalForm();
            Media media = new Media(videoPath);
            mediaPlayer = new MediaPlayer(media);

            Media.setMediaPlayer(mediaPlayer);
            mediaPlayer.setOnReady(() -> mediaPlayer.play());
            mediaPlayer.setOnError(() -> System.err.println("Error playing video: " + mediaPlayer.getError().getMessage()));


            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            });
        } catch (NullPointerException e) {
            System.err.println("Video file not found or path is incorrect.");
        }
        String buttonStyle = "-fx-background-color: linear-gradient(to bottom, #fb8500, #ff5400); " +
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 10; " +
                "-fx-border-color: rgba(255, 255, 255, 0.8); " +
                "-fx-border-width: 2; " +
                "-fx-font-size: 10x; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 15, 0.3, 0, 4);";
        String hoverStyle = "-fx-background-color: linear-gradient(to bottom, #bb3e03, #ff5400); " +
                "-fx-background-radius: 12; " +
                "-fx-border-radius: 12; " +
                "-fx-border-color: rgba(255, 255, 255, 0.9); " +
                "-fx-border-width: 2; " +
                "-fx-font-size: 12px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.6), 20, 0.4, 0, 5);";
        String computerButtonBaseStyle = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";
        // Hover style for the Computer Button
        String computerButtonHoverStyle = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        Exit.setStyle(computerButtonBaseStyle);
        Exit.setOnMouseEntered(e -> Exit.setStyle(computerButtonHoverStyle));
        Exit.setOnMouseExited(e -> Exit.setStyle(computerButtonBaseStyle));
        String localPlayerButtonBaseStyle = "-fx-background-color: linear-gradient(to bottom, #FFC107, #FFB300); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 193, 7, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Local Player Button
        String localPlayerButtonHoverStyle = "-fx-background-color: linear-gradient(to bottom, #FFD54F, #FFA000); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 193, 7, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Local Player Button
        playagain.setStyle(localPlayerButtonBaseStyle);
        playagain.setOnMouseEntered(e -> playagain.setStyle(localPlayerButtonHoverStyle));
        playagain.setOnMouseExited(e -> playagain.setStyle(localPlayerButtonBaseStyle));
        applyButtonStyle(playagain, buttonStyle, hoverStyle);
        applyButtonStyle(Exit,buttonStyle,hoverStyle);
    }
    private void applyButtonStyle(Button button, String normalStyle, String hoverStyle) {
        button.setStyle(normalStyle);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle)); // Change to hover style
        button.setOnMouseExited(e -> button.setStyle(normalStyle));

    }


    private void playBackgroundMusic() {
        try {
            String musicPath = Objects.requireNonNull(getClass().getResource("/Music/GameOverMusic.mp3")).toExternalForm();
            Media media = new Media(musicPath);
            MediaPlayer musicPlayer = new MediaPlayer(media);

            if (mediaPlayer != null) {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            }

            mediaPlayer = musicPlayer;
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing GameOverMusic.mp3: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @FXML
    void Exit(ActionEvent event) {
        stopMediaPlayer();
        navigateToScreen(event, "Home.fxml");
    }

    @FXML
    void playagain(ActionEvent event) {
        stopMediaPlayer();
        navigateToScreen(event, "GameMode.fxml");
    }

    private void navigateToScreen(ActionEvent event, String fxmlFile) {
        try {

            stopMediaPlayer();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            Stage stage = (Stage) ((Button) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.err.println("Error loading " + fxmlFile + ". Please check the file path.");
            e.printStackTrace();
        }
    }

    private void stopMediaPlayer() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.dispose();
        }
    }
}
