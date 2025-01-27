package com.example.uno_project;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
import javafx.util.Duration;


public class GameMode {
    @FXML
    private Label Game_Mode;
    @FXML
    private Button Back;
    @FXML
    private Button Demo;
    @FXML
    private Button Easy;
    @FXML
    private Button Hard;
    @FXML
    private Button No_mercy;

    @FXML
    public void initialize() {
        // Glass-like glossy button style
        String buttonStyle = "-fx-background-color: linear-gradient(to bottom, #fb8500, ae2012); " +
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 15; " +
                "-fx-border-color: rgba(255, 255, 255, 0.8); " +
                "-fx-border-width: 2; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.4), 15, 0.3, 0, 4);";

        String hoverStyle = "-fx-background-color: linear-gradient(to bottom, #bb3e03, #bb3e03); " +
                "-fx-background-radius: 15; " +
                "-fx-border-radius: 15; " +
                "-fx-border-color: rgba(255, 255, 255, 0.9); " +
                "-fx-border-width: 2; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: bold; " +
                "-fx-text-fill: white; " +
                "-fx-padding: 10px 20px; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.6), 20, 0.4, 0, 5);";

        Game_Mode.setText("GAME MODE");

        // Start wave color animation
        startWordWaveAnimation(Game_Mode);

        // Apply styles to buttons
        Back.setStyle(buttonStyle);
        Demo.setStyle(buttonStyle);
        Easy.setStyle(buttonStyle);
        Hard.setStyle(buttonStyle);
        No_mercy.setStyle(buttonStyle);

        // Add hover effects
        addHoverEffect(Back, buttonStyle, hoverStyle);
        addHoverEffect(Demo, buttonStyle, hoverStyle);
        addHoverEffect(Easy, buttonStyle, hoverStyle);
        addHoverEffect(Hard, buttonStyle, hoverStyle);
        addHoverEffect(No_mercy, buttonStyle, hoverStyle);
    }

    private void addHoverEffect(Button button, String defaultStyle, String hoverStyle) {
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(defaultStyle));
    }
    private void startWordWaveAnimation(Label label) {
        String text = label.getText(); // Get the label's text
        String[] words = text.split(" "); // Split text into words
        Timeline timeline = new Timeline();

        // Iterate over words to animate one word at a time
        for (int i = 0; i < words.length; i++) {
            final int index = i; // Store the current word index
            KeyFrame keyFrame = new KeyFrame(
                    Duration.seconds(i * 0.5), // Delay for each word
                    e -> {
                        // Rebuild the label's text with the active word highlighted
                        StringBuilder animatedText = new StringBuilder();
                        for (int j = 0; j < words.length; j++) {
                            if (j == index) {
                                // Highlight the active word with red-orange gradient
                                animatedText.append("  ").append(words[j]).append(" ");
                                label.setStyle("-fx-font-size: 44px; -fx-font-weight: bold; -fx-text-fill: linear-gradient(from 0% 0% to 100% 0%, red, orange);");
                            } else {
                                // Normal style for non-active words
                                animatedText.append(words[j]).append(" ");
                            }
                        }
                        label.setText(animatedText.toString());
                    }
            );
            timeline.getKeyFrames().add(keyFrame);
        }

        // Loop the animation indefinitely
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }
    @FXML
    void BackHandle(ActionEvent event) {
        try {
            Parent homeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
            Stage stage = (Stage) Back.getScene().getWindow();
            stage.setScene(new Scene(homeRoot));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading Home.fxml. Please check the file path.");
            e.printStackTrace();
        }
    }

    @FXML
    void DemoHandle(ActionEvent event) {
        SharedData.playButtonSound();
        try {
            Parent homeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("demo.fxml")));
            Stage stage = (Stage) Back.getScene().getWindow();
            stage.setScene(new Scene(homeRoot));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading demo.fxml. Please check the file path.");
            e.printStackTrace();
        }
    }

    @FXML
    void gotoComputerPlayer(ActionEvent event) {
        SharedData.playButtonSound();
        try {
            Parent easyModeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ComputerEasyMode.fxml")));
            Stage stage = (Stage) Easy.getScene().getWindow();
            stage.setScene(new Scene(easyModeRoot));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading ComputerEasyMode.fxml. Please check the file path.");
            e.printStackTrace();
        }
    }

    @FXML
    void gotoHardMode(ActionEvent event) {
        SharedData.playButtonSound();

        try {
            Thread.sleep(100); // Delay for 100ms
            Parent homeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("ComputerHardMode.fxml")));
            Stage stage = (Stage) Hard.getScene().getWindow();
            stage.setScene(new Scene(homeRoot));
            stage.show();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error loading ComputerHardMode.fxml. Please check the file path.");
            e.printStackTrace();
        }
    }

    @FXML
    void gotoNo_mercy(ActionEvent event) {
        SharedData.playButtonSound();
        try {
            Thread.sleep(100); // Delay for 100ms
            Parent homeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("NO_MERCY.fxml")));
            Stage stage = (Stage) No_mercy.getScene().getWindow();
            stage.setScene(new Scene(homeRoot));
            stage.show();
        } catch (IOException | InterruptedException e) {
            System.out.println("Error loading NO_MERCY.fxml. Please check the file path.");
            e.printStackTrace();
        }
    }
}
