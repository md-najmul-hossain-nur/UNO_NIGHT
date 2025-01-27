package com.example.uno_project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;

public class Home {
    @FXML
    private Button ABOUT_US;

    @FXML
    private Button Setting;
    @FXML
    private Button computerButton;
    @FXML
    private Button localPlayerButton;
    @FXML
    private Label profileNameLabel;
    @FXML
    private ImageView profileView;

    @FXML
    private Button BuyButton;
    @FXML
    private Rectangle Area;
    @FXML
    private Label coinLabel;
    private Socket clientSocket;
    private BufferedReader in;
    private PrintWriter out;
    @FXML
    public void initialize() {
        profileView.setImage(new Image(Objects.requireNonNull(getClass()
                .getResource(Objects.requireNonNullElse(SharedData.selectedProfileImagePath, "/Images/icon/Default_icon.jpg"))).toString()));
        profileNameLabel.setText(Objects.requireNonNullElse(SharedData.selectedProfileName, "Player 1"));
        updateCoinLabel();
        Area.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area.setStroke(Color.ORANGE);
        Area.setStrokeWidth(2);

        // Load the saved music path
        String selectedMusicPath = loadSelectedMusic();

        // Stop any previously playing music
        if (SharedData.mediaPlayer != null) {
            SharedData.mediaPlayer.stop();
        }
        try {
            if (!selectedMusicPath.isEmpty()) {
                // Play the selected music
                SharedData.mediaPlayer = new MediaPlayer(new javafx.scene.media.Media(Objects.requireNonNull(getClass().getResource(selectedMusicPath)).toString()));
            } else {
                // Play the default music if no selection is found
                SharedData.mediaPlayer = new MediaPlayer(new javafx.scene.media.Media(Objects.requireNonNull(getClass().getResource("/Music/HomeGame.mp3")).toString()));
            }
            if (SharedData.isMusicOn) {
                SharedData.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop the music
                SharedData.mediaPlayer.play();
            }
        } catch (Exception e) {
            System.err.println("Error loading or playing music: " + e.getMessage());
            e.printStackTrace();
        }
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
        BuyButton.setStyle(buyButtonBaseStyle);

        // Add hover effects to Buy Button
        BuyButton.setOnMouseEntered(e -> BuyButton.setStyle(buyButtonHoverStyle));
        BuyButton.setOnMouseExited(e -> BuyButton.setStyle(buyButtonBaseStyle));

        // Base style for the Setting Button
        String settingButtonBaseStyle = "-fx-background-color: linear-gradient(to bottom, #3F51B5, #283593); " +
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
        Setting.setStyle(settingButtonBaseStyle);

        // Add hover effects to Setting Button
        Setting.setOnMouseEntered(e -> Setting.setStyle(settingButtonHoverStyle));
        Setting.setOnMouseExited(e -> Setting.setStyle(settingButtonBaseStyle));
        String sStyle = "-fx-background-color: linear-gradient(to bottom, #3F51B5, #283593); " +
                "-fx-background-radius: 15; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.6), 15, 0.5, 0, 0);";

        // Shine effect style for Setting Button
        String sle = "-fx-background-color: linear-gradient(to bottom, #5C6BC0, #1A237E); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.9), 20, 0.8, 0, 0);";

        // Apply base style to Setting Button
        ABOUT_US.setStyle(settingButtonBaseStyle);

        // Add hover effects to Setting Button
        ABOUT_US.setOnMouseEntered(e -> ABOUT_US.setStyle(sle));
        ABOUT_US.setOnMouseExited(e -> ABOUT_US.setStyle(sStyle));

        applyButtonStyle(computerButton, buttonStyle, hoverStyle);
        applyButtonStyle(localPlayerButton, buttonStyle, hoverStyle);
        applyButtonStyle(BuyButton,buttonStyle,hoverStyle);
        applyButtonStyle(ABOUT_US,buttonStyle,hoverStyle);
        applyButtonStyle(Setting,buttonStyle,hoverStyle);

    }

    private void applyButtonStyle(Button button, String normalStyle, String hoverStyle) {
        button.setStyle(normalStyle);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle)); // Change to hover style
        button.setOnMouseExited(e -> button.setStyle(normalStyle));

    }
    private String loadSelectedMusic() {
        try (BufferedReader reader = new BufferedReader(new java.io.FileReader("selectedMusic.txt"))) {
            String savedMusicPath = reader.readLine();
            if (savedMusicPath != null && !savedMusicPath.isEmpty()) {
                System.out.println("Loaded selected music: " + savedMusicPath);
                return savedMusicPath; // Return the saved music path
            }
        } catch (IOException e) {
            System.out.println("No selected music found, default will be played.");
        }
        return "";
    }
    @FXML
    void gotoabouts(ActionEvent event) {
        SharedData.playButtonSound();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("AboutUS.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void goToSettings(ActionEvent event) {
        SharedData.playButtonSound();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Settings.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void updateCoinLabel() {
        int currentCoins = SharedData.getPlayerCoins();
        coinLabel.setText(String.valueOf(currentCoins)); // Show the coins in the label
    }
    @FXML
    void handleComputer(ActionEvent event) {
        SharedData.playButtonSound();
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Rule.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void handleLocalPlayer(ActionEvent event) {
        SharedData.playButtonSound(); // Play sound when the button is clicked
        try {
            Socket clientSocket = new Socket("localhost", 12346);
            System.out.println("Connected to the server!");
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("Lobby.fxml"));
            Parent root = loader.load();
            Lobby lobbyController = loader.getController();
            lobbyController.setSocket(clientSocket, in, out);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
            System.out.println("Moved to Lobby.");
        } catch (IOException e) {
            System.err.println("Failed to connect to the server.");
            e.printStackTrace();
        }
    }


    @FXML
    private void goToProfile() {
        SharedData.playButtonSound(); // Play sound when the button is clicked
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Profile.fxml")));
            Stage stage = (Stage) profileView.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    void Shop(ActionEvent event) {
        SharedData.playButtonSound();
        if (SharedData.mediaPlayer != null) {
            SharedData.mediaPlayer.stop();
        }
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Buy.fxml")));
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();
        } catch (IOException e) {
            System.out.println("Error loading buy.fxml. Please check the file path.");
            e.printStackTrace();
        }
    }
}
