package com.example.uno_project;

import javafx.animation.FadeTransition;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.ActionEvent;
import javafx.util.Duration;

import java.io.IOException;
import java.util.Objects;

public class Settings {

    @FXML
    private Label coinLabel;
    @FXML
    private Rectangle Area;
    @FXML
    private Button buyButton;

    @FXML
    private Button closeButton;

    @FXML
    private Button computerButton;

    @FXML
    private Button localPlayerButton;

    @FXML
    private ToggleButton musicToggle;

    @FXML
    private Label profileNameLabel;

    @FXML
    private ImageView profileView;
    @FXML
    private Button ABOUT_US;
    @FXML
    private Slider volumeSlider;


    @FXML
    private void handleCloseButton(ActionEvent event) {
        SharedData.playButtonSound();
        try {
            // Ensure the MediaPlayer resumes from the current position
            if (SharedData.mediaPlayer != null) {
                SharedData.resumePlayback(); // Resume playback without restarting
            }

            // Navigate to home screen
            Parent homeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("home.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(homeRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void setupVolumeSlider() {
        // Load saved volume
        double initialVolume = SharedData.getGlobalVolume();
        volumeSlider.setValue(initialVolume * 100); // Convert to percentage

        // Add listener to update global volume
        volumeSlider.valueProperty().addListener((observable, oldValue, newValue) -> {
            double volume = newValue.doubleValue() / 100; // Convert 0-100 to 0.0-1.0
            SharedData.updateGlobalVolume(volume); // Update global volume
            System.out.println("Global Volume set to: " + (int) (volume * 100) + "%");
        });
    }


    @FXML
    public void initialize() {
        profileView.setFitWidth(80); // Set the width of the ImageView
        profileView.setFitHeight(70); // Set the height of the ImageView
        profileView.setPreserveRatio(true); // Preserve the aspect ratio of the image
        profileView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0, 0); " +
                "-fx-border-color: linear-gradient(to bottom, #2E7D32, #E64A19); " +
                "-fx-border-width: 3; " +
                "-fx-border-radius: 50%; " +
                "-fx-background-radius: 50%;");

        // Adding shine and hover effect to the profileView (Image)
        profileView.setOnMouseEntered(event -> {
            profileView.setStyle("-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.8), 10, 0.5, 0, 0); " +
                    "-fx-border-color: linear-gradient(to bottom, #2E7D32, #FF8A65); " +
                    "-fx-border-width: 3; " +
                    "-fx-border-radius: 50%; " +
                    "-fx-background-radius: 50%;");
        });

        profileView.setOnMouseExited(event -> {
            profileView.setStyle("-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0.5, 0, 0); " +
                    "-fx-border-color: linear-gradient(to bottom, #2E7D32, #E64A19); " +
                    "-fx-border-width: 3; " +
                    "-fx-border-radius: 50%; " +
                    "-fx-background-radius: 50%;");
        });
        profileNameLabel.setStyle("-fx-font-size: 20px; " +
                "-fx-text-fill: white; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0.5, 0, 0);");

        // Add hover effect to the profileNameLabel
        profileNameLabel.setOnMouseEntered(event -> {
            profileNameLabel.setStyle("-fx-font-size: 22px; " +
                    "-fx-text-fill: linear-gradient(to bottom, #FF5722, #E64A19); " +
                    "-fx-font-family: 'Arial'; " +
                    "-fx-font-weight: bold; " +
                    "-fx-effect: dropshadow(gaussian, rgba(255, 255, 255, 0.8), 10, 0.5, 0, 0);");
        });

        profileNameLabel.setOnMouseExited(event -> {
            profileNameLabel.setStyle("-fx-font-size: 20px; " +
                    "-fx-text-fill: white; " +
                    "-fx-font-family: 'Arial'; " +
                    "-fx-font-weight: bold; " +
                    "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 5, 0.5, 0, 0);");
        });


        // Styles for Computer Button

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
        computerButton.setStyle(computerButtonBaseStyle);
        computerButton.setOnMouseEntered(e -> computerButton.setStyle(computerButtonHoverStyle));
        computerButton.setOnMouseExited(e -> computerButton.setStyle(computerButtonBaseStyle));

        // Base style for the Local Player Button
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
        localPlayerButton.setStyle(localPlayerButtonBaseStyle);
        localPlayerButton.setOnMouseEntered(e -> localPlayerButton.setStyle(localPlayerButtonHoverStyle));
        localPlayerButton.setOnMouseExited(e -> localPlayerButton.setStyle(localPlayerButtonBaseStyle));
        // Base style for the Local Player Button
        String locale = "-fx-background-color: linear-gradient(to bottom, #FFC107, #FFB300); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 193, 7, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Local Player Button
        String loca = "-fx-background-color: linear-gradient(to bottom, #FFD54F, #FFA000); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 193, 7, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Local Player Button
        ABOUT_US.setStyle(localPlayerButtonBaseStyle);
        ABOUT_US.setOnMouseEntered(e -> ABOUT_US.setStyle(loca));
        ABOUT_US.setOnMouseExited(e -> ABOUT_US.setStyle(locale));

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
        // Base style for the Buy Button
        String buyButtonBaseStyle = "-fx-background-color: linear-gradient(to bottom, #FF5722, #E64A19); " +
                "-fx-background-radius: 15; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 153, 0, 0.6), 15, 0.5, 0, 0);";

        // Shine effect style for Buy Button
        String buyButtonHoverStyle = "-fx-background-color: linear-gradient(to bottom, #FF7043, #D84315); " +
                "-fx-background-radius: 15; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 153, 0, 0.9), 20, 0.8, 0, 0);";

        // Apply base style to Buy Button
        buyButton.setStyle(buyButtonBaseStyle);

        // Add hover effects to Buy Button
        buyButton.setOnMouseEntered(e -> buyButton.setStyle(buyButtonHoverStyle));
        buyButton.setOnMouseExited(e -> buyButton.setStyle(buyButtonBaseStyle));
        // Styles for Close Button
        String closeButtonBaseStyle = "-fx-background-color: linear-gradient(to bottom, #bf0603, #d83a3a); " +
                "-fx-background-radius: 15; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(189, 6, 3, 0.6), 15, 0.5, 0, 0);";

        String closeButtonHoverStyle = "-fx-background-color: linear-gradient(to bottom, #f14136, #bf0603); " +
                "-fx-background-radius: 15; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(189, 6, 3, 0.9), 20, 0.8, 0, 0);";

        // Apply the styles to the Close Button

        // Apply base style to Buy Button
        closeButton.setStyle(buyButtonBaseStyle);

        // Add hover effects to Buy Button
        closeButton.setOnMouseEntered(e -> closeButton.setStyle(closeButtonHoverStyle));
        closeButton.setOnMouseExited(e -> closeButton.setStyle(closeButtonBaseStyle));

        // Base style for the Setting Button
        String toggleButtonBaseStyle  = "-fx-background-color: linear-gradient(to bottom, #3F51B5, #283593); " +
                "-fx-background-radius: 15; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.6), 15, 0.5, 0, 0);";

        // Shine effect style for Setting Button
        String settingButtonHoverStyle = "-fx-background-color: linear-gradient(to bottom, #5C6BC0, #1A237E); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.9), 20, 0.8, 0, 0);";

        // Apply base style to Setting Button
        musicToggle.setStyle(toggleButtonBaseStyle);

        // Add hover effects to Setting Button
        musicToggle.setOnMouseEntered(e -> musicToggle.setStyle(settingButtonHoverStyle));
        musicToggle.setOnMouseExited(e -> musicToggle.setStyle(toggleButtonBaseStyle));

        applyButtonStyle(computerButton, buttonStyle, hoverStyle);
        applyButtonStyle(localPlayerButton, buttonStyle, hoverStyle);
        applyButtonStyle(buyButton,buttonStyle,hoverStyle);
        applyButtonStyle(musicToggle,buttonStyle,hoverStyle);
        applyButtonStyle(closeButton,buttonStyle,hoverStyle);

        applyButtonStyle(ABOUT_US,buttonStyle,hoverStyle);

        // Set up the volume slider
        setupVolumeSlider();

        // Set Profile Image and Name
        profileView.setImage(new Image(Objects.requireNonNull(getClass().getResource(
                Objects.requireNonNullElse(SharedData.selectedProfileImagePath, "/Images/icon/Default_icon.jpg")
        )).toString()));
        profileNameLabel.setText(Objects.requireNonNullElse(SharedData.selectedProfileName, "Guest"));

        // Update Coin Label
        updateCoinLabel();

        // Style Rectangle Area
        Area.setFill(Color.TRANSPARENT);
        Area.setStroke(Color.ORANGE);
        Area.setStrokeWidth(2);

        // Initialize Toggle Button State
        musicToggle.setSelected(SharedData.isMusicOn);
    }

    private void applyButtonStyle(ButtonBase button, String baseStyle, String hoverStyle) {
        if (button != null) {
            button.setStyle(baseStyle);

            // Add hover effect
            button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
            button.setOnMouseExited(e -> button.setStyle(baseStyle));
        } else {
            System.err.println("ButtonBase is null! Check your FXML file.");
        }
    }

    private void updateCoinLabel() {
        int currentCoins = SharedData.getPlayerCoins();
        coinLabel.setText(String.valueOf(currentCoins));
    }

    private void updateMusicToggleButtonAppearance() {
        // Change the button text
        musicToggle.setText(SharedData.isMusicOn ? "ON" : "OFF");

        // Apply a smooth transition for visual feedback
        String onStyle = "-fx-background-color: linear-gradient(to right, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 20; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.8), 10, 0.6, 0, 0);";

        String offStyle = "-fx-background-color: linear-gradient(to right, #FF5252, #D32F2F); " +
                "-fx-background-radius: 20; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 14px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(255, 82, 82, 0.8), 10, 0.6, 0, 0);";

        // Animate the style change
        musicToggle.setStyle(SharedData.isMusicOn ? onStyle : offStyle);

        // Optionally add a short fade animation for smoother effect
        FadeTransition fadeTransition = new FadeTransition(Duration.millis(300), musicToggle);
        fadeTransition.setFromValue(0.7);
        fadeTransition.setToValue(1.0);
        fadeTransition.play();
    }





    @FXML
    private void toggleMusic(ActionEvent event) {
        // Toggle the music state
        SharedData.isMusicOn = !SharedData.isMusicOn;

        // Smoothly update the toggle button
        updateMusicToggleButtonAppearance();

        // Control music playback
        if (SharedData.isMusicOn) {
            if (SharedData.mediaPlayer != null) {
                SharedData.mediaPlayer.play(); // Play music
            }
        } else {
            if (SharedData.mediaPlayer != null) {
                SharedData.mediaPlayer.pause(); // Pause music (not stop)
            }
        }
    }

}