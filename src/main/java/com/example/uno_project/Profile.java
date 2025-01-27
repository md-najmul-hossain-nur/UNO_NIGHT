package com.example.uno_project;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;

public class Profile {

    @FXML
    private Rectangle Area;

    @FXML
    private Button buyButton1;

    @FXML
    private Button closeButton;

    @FXML
    private Label coinLabel;

    @FXML
    private Button editButton;

    @FXML
    private Button localPlayerButton;

    @FXML
    private TextField nameInput;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView pic1Image;

    @FXML
    private ImageView pic2Image;

    @FXML
    private ImageView pic3Image;

    @FXML
    private ImageView pic4Image;

    @FXML
    private ImageView profilePreview;

    @FXML
    private Button saveButton;

    @FXML
    private Button settingButton;

    private String selectedImagePath;
    private boolean isEditMode = false;

    private static final String PROFILE_FILE = System.getProperty("user.home") + "/profile_data.txt";

    @FXML
    public void initialize() {
        loadProfileData();
        nameInput.setDisable(true);
        updateCoinLabel();
        Area.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area.setStroke(Color.ORANGE);
        Area.setStrokeWidth(2);
        if (SharedData.isMusicOn && SharedData.mediaPlayer != null) {
            SharedData.mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
            SharedData.mediaPlayer.play();
        }



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
        buyButton1.setStyle(buyButtonBaseStyle);

        // Add hover effects to Buy Button
        buyButton1.setOnMouseEntered(e -> buyButton1.setStyle(buyButtonHoverStyle));
        buyButton1.setOnMouseExited(e -> buyButton1.setStyle(buyButtonBaseStyle));

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
        settingButton.setStyle(settingButtonBaseStyle);

        // Add hover effects to Setting Button
        settingButton.setOnMouseEntered(e -> settingButton.setStyle(settingButtonHoverStyle));
        settingButton.setOnMouseExited(e -> settingButton.setStyle(settingButtonBaseStyle));
        String settingButto = "-fx-background-color: linear-gradient(to bottom, #3F51B5, #283593); " +
                "-fx-background-radius: 15; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.6), 15, 0.5, 0, 0);";

        // Shine effect style for Setting Button
        String sette = "-fx-background-color: linear-gradient(to bottom, #5C6BC0, #1A237E); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.9), 20, 0.8, 0, 0);";

        // Apply base style to Setting Button
        closeButton.setStyle(settingButtonBaseStyle);

        // Add hover effects to Setting Button
        closeButton.setOnMouseEntered(e -> closeButton.setStyle(sette));
        closeButton.setOnMouseExited(e -> closeButton.setStyle(settingButto));

        String setie = "-fx-background-color: linear-gradient(to bottom, #3F51B5, #283593); " +
                "-fx-background-radius: 15; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.6), 15, 0.5, 0, 0);";

        // Shine effect style for Setting Button
        String settyle = "-fx-background-color: linear-gradient(to bottom, #5C6BC0, #1A237E); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 10px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.9), 20, 0.8, 0, 0);";

        // Apply base style to Setting Button
        saveButton.setStyle(settingButtonBaseStyle);

        // Add hover effects to Setting Button
        saveButton.setOnMouseEntered(e -> saveButton.setStyle(settyle));
        saveButton.setOnMouseExited(e -> saveButton.setStyle(setie));

        applyButtonStyle(editButton, buttonStyle, hoverStyle);
        applyButtonStyle(localPlayerButton, buttonStyle, hoverStyle);
        applyButtonStyle(buyButton1,buttonStyle,hoverStyle);
        applyButtonStyle(saveButton,buttonStyle,hoverStyle);
        applyButtonStyle(editButton,buttonStyle,hoverStyle);
        applyButtonStyle(closeButton,buttonStyle,hoverStyle);
        applyButtonStyle(settingButton,buttonStyle,hoverStyle);



    }
    private void applyButtonStyle(Button button, String normalStyle, String hoverStyle) {
        button.setStyle(normalStyle);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle)); // Change to hover style
        button.setOnMouseExited(e -> button.setStyle(normalStyle));

    }

    @FXML
    private void handleSaveButton(ActionEvent event) {
        if (isEditMode) {
            // Update profile name
            String enteredName = nameInput.getText();
            if (!enteredName.isEmpty()) {
                nameLabel.setText(enteredName);
                SharedData.selectedProfileName = enteredName;
            }

            // Update profile picture
            if (selectedImagePath != null) {
                try {
                    profilePreview.setImage(new Image(Objects.requireNonNull(getClass().getResource(selectedImagePath)).toString()));
                    SharedData.selectedProfileImagePath = selectedImagePath;
                } catch (NullPointerException e) {
                    System.err.println("Image not found: " + selectedImagePath);
                }
            }

            // Check if profile_data.txt is empty or null
            File profileFile = new File("profile_data.txt");
            boolean isFileEmptyOrNull = isProfileDataFileEmpty(profileFile);

            // Add coins only if profile_data.txt is empty or null
            if (isFileEmptyOrNull) {
                SharedData.addCoins(1000); // Add 1000 coins as reward
                SharedData.setProfileSaveRewardGiven(true);
                showCongratulatoryDialog();
                updateCoinLabel(); // Update the coin label
                System.out.println("Profile saved for the first time. Reward of 1000 coins added!");
            } else {
                System.out.println("Reward already given. No coins added.");
            }

            // Save the updated profile data
            saveProfileData();

            // Disable edit mode
            isEditMode = false;
            nameInput.setDisable(true);
        }
    }
    private void showCongratulatoryDialog() {
        Platform.runLater(() -> {
            // Create the alert of type INFORMATION
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Congratulations!");
            alert.setHeaderText("You win 1000 coins for profile setup!");
            alert.setContentText("Your profile has been successfully saved. Enjoy your reward!");

            // Style the Alert's button
            ButtonType okButton = new ButtonType("OK");
            alert.getButtonTypes().setAll(okButton);

            // Apply custom style for the Alert's button
            Button okBtn = (Button) alert.getDialogPane().lookupButton(okButton);
            okBtn.setStyle("-fx-background-color: #4CAF50; -fx-text-fill: white; -fx-font-size: 14px; -fx-font-weight: bold; -fx-background-radius: 10;");

            // Apply custom style for the DialogPane of the Alert
            alert.getDialogPane().setStyle(
                    "-fx-background-color: #FFFFF0; " +           // Light yellow background for the dialog
                            "-fx-border-radius: 15px; " +                  // Rounded corners
                            "-fx-padding: 20px;"                            // Padding inside the dialog
            );

            // Apply custom header and content text style
            alert.getDialogPane().lookup(".header-panel").setStyle(
                    "-fx-font-size: 16px; " +                        // Header font size
                            "-fx-font-weight: bold; " +
                            "-fx-text-fill: #E64A19;"                        // Header text color (orange)
            );

            alert.getDialogPane().lookup(".content").setStyle(
                    "-fx-font-size: 14px; " +                        // Content font size
                            "-fx-text-fill: #2C6B2F;"                        // Content text color (green)
            );

            // Show and wait for the button click
            alert.showAndWait(); // Show the dialog and wait for the user's response
        });
    }



    private boolean isProfileDataFileEmpty(File profileFile) {
        if (!profileFile.exists()) {
            return true; // File doesn't exist, so it's considered empty
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(profileFile))) {
            String firstLine = reader.readLine();
            return (firstLine == null || firstLine.trim().isEmpty()); // Check if the first line is null or empty
        } catch (IOException e) {
            System.err.println("Error reading profile_data.txt: " + e.getMessage());
            return true; // If there's an error reading the file, treat it as empty
        }
    }




    @FXML
    private void handleEditButton(ActionEvent event) {
        isEditMode = true;
        nameInput.setDisable(false);
    }

    private void updateCoinLabel() {
        int currentCoins = SharedData.getPlayerCoins();
        coinLabel.setText("" + currentCoins);
    }

    @FXML
    private void handleCloseButton(ActionEvent event) {
        try {
            Parent homeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
            Stage stage = (Stage) closeButton.getScene().getWindow();
            stage.setScene(new Scene(homeRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void selectProfilePicture(MouseEvent event) {
        if (isEditMode) {
            ImageView clickedImage = (ImageView) event.getSource();

            switch (clickedImage.getId()) {
                case "pic1Image":
                    selectedImagePath = "/Images/icon/Boy_icon.jpeg";
                    break;
                case "pic2Image":
                    selectedImagePath = "/Images/icon/Girl_icon.jpeg";
                    break;
                case "pic3Image":
                    selectedImagePath = "/Images/icon/Man_icon.jpeg";
                    break;
                case "pic4Image":
                    selectedImagePath = "/Images/icon/Women_icon.jpeg";
                    break;
                default:
                    selectedImagePath = null;
                    break;
            }

            if (selectedImagePath != null) {
                try {
                    profilePreview.setImage(new Image(Objects.requireNonNull(getClass().getResource(selectedImagePath)).toString()));
                } catch (NullPointerException e) {
                    System.err.println("Image not found: " + selectedImagePath);
                }
            }
        }
    }

    private void saveProfileData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("profile_data.txt"))) {
            // Save the profile name
            writer.write(nameLabel.getText() + "\n");

            // Save the profile picture path
            if (selectedImagePath != null) {
                writer.write(selectedImagePath + "\n");
            } else {
                writer.write("\n");
            }

            // Save the reward state
            writer.write(String.valueOf(SharedData.isProfileSaveRewardGiven()) + "\n");
        } catch (IOException e) {
            System.err.println("Error saving profile data: " + e.getMessage());
        }
    }



    private void loadProfileData() {
        try (BufferedReader reader = new BufferedReader(new FileReader(PROFILE_FILE))) {
            String name = reader.readLine();
            if (name != null && !name.isEmpty()) {
                nameLabel.setText(name);
                SharedData.selectedProfileName = name;
            }

            String imagePath = reader.readLine();
            if (imagePath != null && !imagePath.isEmpty()) {
                selectedImagePath = imagePath;
                SharedData.selectedProfileImagePath = imagePath;
                try {
                    profilePreview.setImage(new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString()));
                } catch (NullPointerException e) {
                    System.err.println("Image not found: " + imagePath);
                }
            }

            // Load reward state
            String rewardState = reader.readLine();
            if (rewardState != null) {
                SharedData.setProfileSaveRewardGiven(Boolean.parseBoolean(rewardState));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
