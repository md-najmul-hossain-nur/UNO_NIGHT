package com.example.uno_project;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.DialogPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.stage.Stage;


import java.io.IOException;
import java.util.Objects;

public class Winpage {



    @FXML
    private Button Back;

    @FXML
    private MediaView Meida;

    @FXML
    private Button PlayAgain;

    private MediaPlayer mediaPlayer;

    @FXML
    public void initialize() {
        playBackgroundMusic();

        try {

            String videoPath = Objects.requireNonNull(getClass().getResource("/Images/CONGRATULATION.mp4")).toExternalForm();


            stopMediaPlayer();

            Media media = new Media(videoPath);
            mediaPlayer = new MediaPlayer(media);

            Meida.setMediaPlayer(mediaPlayer);
            mediaPlayer.play();

            mediaPlayer.setOnEndOfMedia(() -> {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            });
        } catch (NullPointerException e) {
            System.err.println("Video file not found: /Images/CONGRATULATION.mp4");
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
            Back.setStyle(computerButtonBaseStyle);
            Back.setOnMouseEntered(e -> Back.setStyle(computerButtonHoverStyle));
            Back.setOnMouseExited(e -> Back.setStyle(computerButtonBaseStyle));
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
        PlayAgain.setStyle(localPlayerButtonBaseStyle);
        PlayAgain.setOnMouseEntered(e -> PlayAgain.setStyle(localPlayerButtonHoverStyle));
        PlayAgain.setOnMouseExited(e -> PlayAgain.setStyle(localPlayerButtonBaseStyle));
        applyButtonStyle(Back, buttonStyle, hoverStyle);
        applyButtonStyle(PlayAgain,buttonStyle,hoverStyle);
        }
    private void applyButtonStyle(Button button, String normalStyle, String hoverStyle) {
        button.setStyle(normalStyle);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle)); // Change to hover style
        button.setOnMouseExited(e -> button.setStyle(normalStyle));

    }


    private void playBackgroundMusic() {
        try {
            String musicPath = Objects.requireNonNull(getClass().getResource("/Music/WinSound.mp3")).toExternalForm();

            stopMediaPlayer();

            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            mediaPlayer.setVolume(0.5);
            mediaPlayer.play();
        } catch (NullPointerException e) {
            System.err.println("Music file not found: /Music/WinSound.mp3");
        } catch (Exception e) {
            System.err.println("Error playing WinSound.mp3: " + e.getMessage());
            e.printStackTrace();
        }
    }


    @FXML
    void Backtohome(ActionEvent event) {
        if (mediaPlayer != null) { // Null check before calling stop
            stopMediaPlayer();
        }
        navigateToScreen(event, "Home.fxml");
        showCongratulationsMessage();
    }

    @FXML
    void playagain(ActionEvent event) {
        if (mediaPlayer != null) { // Null check before calling stop
            stopMediaPlayer();
        }
        navigateToScreen(event, "GameMode.fxml");
        showCongratulationsMessage();
    }



    private void stopMediaPlayer() {
        if (mediaPlayer != null) {
            try {
                mediaPlayer.stop();
                mediaPlayer.dispose();
            } catch (Exception e) {
                System.err.println("Error stopping MediaPlayer: " + e.getMessage());
            }
        }
    }


    private void navigateToScreen(ActionEvent event, String fxmlFile) {
        try {
            stopMediaPlayer();
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlFile)));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showCongratulationsMessage() {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Congratulations!");
        alert.setHeaderText("You've Achieved a Milestone!");
        alert.setContentText("Congratulations! You won 1000 coins!");

        try {
            // সঠিক CSS পাথ
            DialogPane dialogPane = alert.getDialogPane();
            dialogPane.getStylesheets().add(
                    Objects.requireNonNull(getClass().getResource("/CSS/alertStyle.css")).toExternalForm()
            );
            dialogPane.getStyleClass().add("dialog-pane");
        } catch (NullPointerException e) {
            System.err.println("CSS file not found: /CSS/alertStyle.css");
        }

        alert.showAndWait();
    }
}
