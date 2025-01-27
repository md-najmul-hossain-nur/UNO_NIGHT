package com.example.uno_project;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.*;

public class Lobby {
    @FXML
    private Button ActiveMember;
    @FXML
    private Label Achievment1;

    @FXML
    private Label Achievment2;

    @FXML
    private Label Achievment3;

    @FXML
    private Label Achievment4;
    @FXML
    private Button Back;
    @FXML
    private VBox EVENTS;

    @FXML
    private Label COMMING;
    @FXML
    private Rectangle area;

    @FXML
    private HBox Event1;

    @FXML
    private HBox Event2;
    @FXML
    private Label Achivement;

    @FXML
    private Label GuildName;

    @FXML
    private TextArea chatArea;

    @FXML
    private TextField chatInput;

    @FXML
    private ImageView event1;

    @FXML
    private ImageView event2;

    @FXML
    private ImageView event3;

    @FXML
    private ImageView event4;

    @FXML
    private VBox ACHU;

    @FXML
    private Label nameLabel;

    @FXML
    private ImageView profileImageView;

    @FXML
    private Button send;

    private final Map<String, Map<String, String>> eventDetails = new HashMap<>();


    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;



    public void initialize() {


        Map<String, String> event1Details = new HashMap<>();
        event1Details.put("Date", "5th February, 2025");
        event1Details.put("Time", "3 PM");
        eventDetails.put("Event 1", event1Details);

        Map<String, String> event2Details = new HashMap<>();
        event2Details.put("Date", "6th February, 2025");
        event2Details.put("Time", "4 PM");
        eventDetails.put("Event 2", event2Details);

        Map<String, String> event3Details = new HashMap<>();
        event3Details.put("Date", "7th February, 2025");
        event3Details.put("Time", "5 PM");
        eventDetails.put("Event 3", event3Details);

        Map<String, String> event4Details = new HashMap<>();
        event4Details.put("Date", "8th February, 2025");
        event4Details.put("Time", "6 PM");
        eventDetails.put("Event 4", event4Details);

        // Assign click handlers to each event image
        event1.setOnMouseClicked(event -> showDialog("KID DAY OF UNO MANIA !!!!", eventDetails.get("Event 1")));
        event2.setOnMouseClicked(event -> showDialog("Friends Achievement Day Event!!!", eventDetails.get("Event 2")));
        event3.setOnMouseClicked(event -> showDialog("FARCRY SPECIAL UNO SKIN EVENT!!!!!", eventDetails.get("Event 3")));
        event4.setOnMouseClicked(event -> showDialog("POKER SPECIAL UNO SKIN EVENT!!!!", eventDetails.get("Event 4")));
        // Other initializations
        username = promptForNameAndImage();
        nameLabel.setText(username);
        area.setFill(javafx.scene.paint.Color.TRANSPARENT);
        area.setStroke(Color.ORANGE);
        area.setStrokeWidth(4);
        initializeTickerAnimations();
        chatArea.setStyle("-fx-background-color: #2E3440; " +
                "-fx-text-fill: #000000; " +
                "-fx-border-color: #88C0D0; " +
                "-fx-border-radius: 10; " +
                "-fx-background-radius: 10; " +
                "-fx-padding: 10px; " +
                "-fx-font-size: 14px; " +
                "-fx-font-family: 'Arial'; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(0, 0, 0, 0.5), 10, 0, 0, 0);");

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
        send.setStyle(localPlayerButtonBaseStyle);
        send.setOnMouseEntered(e -> send.setStyle(localPlayerButtonHoverStyle));
        send.setOnMouseExited(e -> send.setStyle(localPlayerButtonBaseStyle));
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
        ActiveMember.setStyle(buyButtonBaseStyle);
        // Add hover effects to Buy Button
        ActiveMember.setOnMouseEntered(e -> ActiveMember.setStyle(buyButtonHoverStyle));
        ActiveMember.setOnMouseExited(e -> ActiveMember.setStyle(buyButtonBaseStyle));
        applyButtonStyle(send, buttonStyle, hoverStyle);
        applyButtonStyle(Back, buttonStyle, hoverStyle);
        applyButtonStyle(ActiveMember,buttonStyle,hoverStyle);
        // Populate the event details map

        addWaveAnimationToCommingLabel(COMMING);
        addWaveAnimationWithWordTransition(Achivement);

        setupScrollAnimationForVBox();



    }


    private void setupScrollAnimationForVBox() {
        SequentialTransition sequentialTransition = new SequentialTransition();

        // Iterate through all children in the VBox
        for (var node : ACHU.getChildren()) {
            if (node instanceof Label label) {
                // Create a scroll animation for each Label
                TranslateTransition transition = createScrollAnimation(label);
                sequentialTransition.getChildren().add(transition);
            }
        }

        // Make the animation loop indefinitely
        sequentialTransition.setCycleCount(SequentialTransition.INDEFINITE);

        // Start the animation
        sequentialTransition.play();
    }

    private TranslateTransition createScrollAnimation(Label label) {
        // Create a TranslateTransition for the label
        TranslateTransition transition = new TranslateTransition();
        transition.setNode(label);
        transition.setDuration(Duration.seconds(1)); // Duration for one cycle
        transition.setFromY(0); // Start at the original position
        transition.setToY(200); // Move 200 pixels down
        transition.setAutoReverse(true); // Reverse the movement
        transition.setCycleCount(2); // Down and up counts as one cycle

        return transition;
    }
    private void showDialog(String title, Map<String, String> details) {
        Alert dialog = new Alert(Alert.AlertType.INFORMATION);
        dialog.setTitle(title); // Set dialog title
        dialog.setHeaderText(null); // No header text

        // Display date and time separately
        String content = "Date: " + details.get("Date") + "\nTime: " + details.get("Time");
        dialog.setContentText(content); // Set dialog content

        // Get the dialog pane for styling
        DialogPane dialogPane = dialog.getDialogPane();

        // Add inline CSS styles
        dialogPane.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #3a3a3a, #2b2b2b); " + // Background gradient
                        "-fx-border-color: #88C0D0; " + // Border color
                        "-fx-border-width: 2px; " + // Border width
                        "-fx-border-radius: 10px; " + // Rounded corners for border
                        "-fx-background-radius: 10px; " + // Rounded corners for background
                        "-fx-padding: 10px;" // Padding inside the dialog
        );

        // Style the content text
        dialogPane.lookup(".content.label").setStyle(
                "-fx-text-fill: #D8DEE9; " + // Text color
                        "-fx-font-size: 14px; " + // Font size
                        "-fx-font-family: 'Arial'; " + // Font family
                        "-fx-font-weight: bold;" // Font weight
        );

        // Style the buttons
        Node button = dialogPane.lookupButton(ButtonType.OK); // Locate the OK button
        button.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #4CAF50, #388E3C); " + // Green gradient
                        "-fx-text-fill: white; " + // Text color
                        "-fx-font-size: 12px; " + // Font size
                        "-fx-font-weight: bold; " + // Font weight
                        "-fx-background-radius: 5px; " + // Rounded corners
                        "-fx-padding: 5px 15px;" // Padding inside the button
        );

        dialog.showAndWait(); // Display the dialog
    }


    private void addWaveAnimationToCommingLabel(Label label) {
        String originalText = label.getText(); // Get the original text
        char[] letters = originalText.toCharArray(); // Convert the text into characters
        int textLength = letters.length;

        Timeline timeline = new Timeline();

        for (int i = 0; i < textLength; i++) {
            final int currentIndex = i; // Capture the index of the current letter

            KeyFrame keyFrame = new KeyFrame(
                    Duration.seconds(i * 0.2), // Delay for each letter (adjust speed by changing 0.2)
                    event -> {
                        // Highlight the current letter and reset the others
                        StringBuilder styledText = new StringBuilder();
                        for (int j = 0; j < textLength; j++) {
                            if (j == currentIndex) {
                                // Set the current letter's color to orange
                                styledText.append(String.format(" ", letters[j]));
                            } else {
                                // Default color for other letters
                                styledText.append(letters[j]);
                            }
                        }
                        label.setText(styledText.toString());
                        label.setStyle("-fx-font-size: 24px; -fx-font-weight: bold;");
                    }
            );

            timeline.getKeyFrames().add(keyFrame);
        }

        // Reset the label to the original text after the last letter
        KeyFrame resetFrame = new KeyFrame(
                Duration.seconds(textLength * 0.2),
                event -> {
                    label.setText(originalText); // Restore the original text
                    label.setStyle("-fx-text-fill: black; -fx-font-size: 24px; -fx-font-weight: normal;");
                }
        );

        timeline.getKeyFrames().add(resetFrame);

        timeline.setCycleCount(Timeline.INDEFINITE); // Loop the animation indefinitely
        timeline.play(); // Start the animation
    }




    private void addWaveAnimationWithWordTransition(Label label) {
        String originalText = label.getText(); // Get the label's text
        String[] words = originalText.split(" "); // Split the text into words

        Timeline timeline = new Timeline();

        for (int i = 0; i < words.length; i++) {
            final int index = i; // Capture the index of the current word

            KeyFrame keyFrame = new KeyFrame(
                    Duration.seconds(i * 1), // Delay between word changes
                    event -> {
                        // Display only the current word with a color change
                        label.setText(words[index]); // Update the label to show the current word
                        label.setStyle("-fx-text-fill: orange; -fx-font-size: 24px; -fx-font-weight: bold;");
                    }
            );

            timeline.getKeyFrames().add(keyFrame);
        }

        // Reset to original text after the last word
        KeyFrame resetFrame = new KeyFrame(
                Duration.seconds(words.length * 1), // Reset after all words are shown
                event -> {
                    label.setText(originalText); // Restore the original text
                    label.setStyle("-fx-text-fill: black; -fx-font-size: 24px; -fx-font-weight: normal;");
                }
        );

        timeline.getKeyFrames().add(resetFrame);
        timeline.setCycleCount(Timeline.INDEFINITE); // Loop the animation indefinitely
        timeline.play(); // Start the animation
    }



    private void applyButtonStyle(Button button, String normalStyle, String hoverStyle) {
        button.setStyle(normalStyle);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle)); // Change to hover style
        button.setOnMouseExited(e -> button.setStyle(normalStyle));

    }


    private void initializeTickerAnimations() {
        Platform.runLater(() -> {
            applyClipToVBox();
            startHBoxScrollAnimation();
        });
    }

    private void applyClipToVBox() {
        Rectangle clip = new Rectangle(EVENTS.getWidth(), EVENTS.getHeight());
        EVENTS.setClip(clip);

        EVENTS.widthProperty().addListener((observable, oldValue, newValue) -> clip.setWidth(newValue.doubleValue()));
        EVENTS.heightProperty().addListener((observable, oldValue, newValue) -> clip.setHeight(newValue.doubleValue()));
    }

    private void startHBoxScrollAnimation() {
        List<HBox> hBoxes = Arrays.asList(Event1, Event2);

        for (int i = 0; i < hBoxes.size(); i++) {
            HBox hBox = hBoxes.get(i);

            double vboxWidth = EVENTS.getWidth();
            double hBoxWidth = hBox.getWidth();

            // Direction ঠিক করা (Event1: Left to Right, Event2: Right to Left)
            boolean isLeftToRight = (i % 2 == 0); // Even index = Left to Right, Odd index = Right to Left

            Timeline timeline;
            if (isLeftToRight) {
                // Left to Right
                timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(hBox.translateXProperty(), -hBoxWidth)),
                        new KeyFrame(Duration.seconds(10), new KeyValue(hBox.translateXProperty(), vboxWidth))
                );
            } else {
                // Right to Left
                timeline = new Timeline(
                        new KeyFrame(Duration.ZERO, new KeyValue(hBox.translateXProperty(), vboxWidth)),
                        new KeyFrame(Duration.seconds(10), new KeyValue(hBox.translateXProperty(), -hBoxWidth))
                );
            }

            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.setAutoReverse(false);
            hBox.setOnMouseEntered(event -> timeline.pause());
            hBox.setOnMouseExited(event -> timeline.play());

            timeline.play();
        }
    }



    public void setSocket(Socket socket, BufferedReader in, PrintWriter out) {
        this.socket = socket;
        this.in = in;
        this.out = out;
        out.println("USERNAME:" + username);

        // Start a thread to listen for server messages
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    handleServerMessage(message); // Call the handler
                }
            } catch (IOException e) {
                Platform.runLater(() -> showErrorMessage("Connection lost with the server."));
                e.printStackTrace();
            }
        }).start();

    }

    // Method to handle messages from the server
    private void handleServerMessage(String message) {
        if (message.startsWith("Event:")) {
            // Handle event-related messages if needed
            System.out.println("Event message received: " + message);
        } else if (message.startsWith("CHAT:")) {
            String chatMessage = message.substring(5); // Remove "CHAT:" prefix
            Platform.runLater(() -> chatArea.appendText(chatMessage + "\n")); // Display in chat area
        } else if (message.startsWith("UPDATED_GUILD_NAME:")) {
            // Handle guild name updates
            String updatedGuildName = message.substring(19); // Remove "UPDATED_GUILD_NAME:" prefix
            Platform.runLater(() -> GuildName.setText(updatedGuildName)); // Update GuildName label
        }  else if (message.startsWith("ACTIVE_MEMBERS:")) {
            // Handle active members list
            String activeMembers = message.substring(15); // Remove "ACTIVE_MEMBERS:" prefix
            Platform.runLater(() -> showActiveMembersDialog(activeMembers));
        } else {
            // Handle unknown message types
            System.out.println("Unknown message type: " + message);
        }
    }



    private void showErrorMessage(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    void sendMessage() {
        String message = chatInput.getText().trim();
        if (!message.isEmpty()) {
            out.println("CHAT:" + username + ": " + message); // Send chat message to server
            chatArea.appendText("You: " + message + "\n"); // Show in own chat area
            chatInput.clear();
        }
    }


    private String promptForNameAndImage() {
        // Create TextInputDialog for entering player name
        TextInputDialog nameDialog = new TextInputDialog("Player");
        nameDialog.setTitle("Enter Your Details");
        nameDialog.setHeaderText("Welcome to the Game!");
        nameDialog.setContentText("Please enter your name:");

        // Apply CSS styling to TextInputDialog
        nameDialog.getDialogPane().setStyle(
                "-fx-background-color: linear-gradient(to bottom, #fb8500, #ff5400); " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-color: #ffffff; " +
                        "-fx-border-width: 2; " +
                        "-fx-font-family: Arial; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px;"
        );

        // Style buttons in TextInputDialog
        Node nameDialogButton = nameDialog.getDialogPane().lookupButton(nameDialog.getDialogPane().getButtonTypes().getFirst());
        nameDialogButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #3F51B5, #283593); " +
                        "-fx-background-radius: 10; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 5px 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.6), 10, 0.5, 0, 0);"
        );

        Optional<String> nameResult = nameDialog.showAndWait();
        String playerName = nameResult.orElse("Guest");

        // Call the method to get the selected image path
        String selectedImagePath = getFinalImagePath();

        // Update profile image
        Platform.runLater(() -> {
            try {
                profileImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource(selectedImagePath)).toExternalForm()));
            } catch (NullPointerException | IllegalArgumentException e) {
                profileImageView.setImage(new Image(Objects.requireNonNull(getClass().getResource("/Images/icon/Default_icon.jpeg")).toExternalForm()));
            }
        });

        return playerName;
    }


    @FXML
    void handleGuildNameClick() {
        TextInputDialog dialog = new TextInputDialog(GuildName.getText());
        dialog.setTitle("Change Guild Name");
        dialog.setHeaderText("Enter a new Guild Name:");
        dialog.setContentText("Guild Name:");

        // Apply simple CSS styles directly
        DialogPane dialogPane = dialog.getDialogPane();
        dialogPane.setStyle("-fx-background-color: lightgray; " +
                "-fx-border-color: darkblue; " +
                "-fx-border-width: 2px; " +
                "-fx-border-radius: 10px; " +
                "-fx-background-radius: 10px;");

        dialogPane.lookup(".header-panel").setStyle("-fx-font-size: 16px; " +
                "-fx-text-fill: darkblue; " +
                "-fx-font-weight: bold;");
        dialogPane.lookup(".text-field").setStyle("-fx-background-color: white; " +
                "-fx-border-color: gray; " +
                "-fx-border-radius: 5px;");
        dialogPane.lookup(".button-bar").setStyle("-fx-background-color: lightblue;");

        Optional<String> result = dialog.showAndWait();
        result.ifPresent(newGuildName -> {
            if (!newGuildName.trim().isEmpty()) {
                GuildName.setText(newGuildName);
                out.println("GUILD_NAME:" + newGuildName); // Send to server
            }
        });
    }


    private String getFinalImagePath() {
        // Create ChoiceDialog for selecting profile image
        List<String> imageOptions = Arrays.asList("Boy", "Avatar", "Host", "Man");
        ChoiceDialog<String> imageDialog = new ChoiceDialog<>("Avatar", imageOptions);
        imageDialog.setTitle("Select Profile Picture");
        imageDialog.setHeaderText("Choose your profile picture:");
        imageDialog.setContentText("Available options:");

        // Apply CSS styling to ChoiceDialog
        imageDialog.getDialogPane().setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffcc00, #ff8800); " +
                        "-fx-border-radius: 10; " +
                        "-fx-border-color: #ffffff; " +
                        "-fx-border-width: 2; " +
                        "-fx-font-family: Arial; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-text-fill: white; " +
                        "-fx-padding: 10px;"
        );

        // Style buttons in ChoiceDialog
        Node choiceDialogButton = imageDialog.getDialogPane().lookupButton(imageDialog.getDialogPane().getButtonTypes().getFirst());
        choiceDialogButton.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #4CAF50, #388E3C); " +
                        "-fx-background-radius: 10; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 12px; " +
                        "-fx-font-weight: bold; " +
                        "-fx-padding: 5px 15px; " +
                        "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 10, 0.5, 0, 0);"
        );

        Optional<String> imageResult = imageDialog.showAndWait();
        String selectedImage = imageResult.orElse("Default");

        // Return image path based on the selection
        return switch (selectedImage) {
            case "Boy" -> "/Images/icon/Boy_icon.jpeg";
            case "Avatar" -> "/Images/icon/playeravatar.jpeg";
            case "Host" -> "/Images/icon/host_avatar.jpg";
            case "Man" -> "/Images/icon/Man_icon.jpeg";
            default -> "/Images/icon/Default_icon.jpeg";
        };
    }

    @FXML
    void handleActiveMemberClick() {
        out.println("GET_ACTIVE_MEMBERS"); // Request active members from the server


        new Thread(() -> {
            try {
                String response = in.readLine(); // Wait for the server response
                if (response.startsWith("ACTIVE_MEMBERS:")) {
                    String activeMembers = response.substring(15); // Remove "ACTIVE_MEMBERS:" prefix
                    Platform.runLater(() -> {
                        showActiveMembersDialog(activeMembers); // Show the usernames
                    });
                }
            } catch (IOException e) {
                Platform.runLater(() -> {

                    showErrorMessage("Failed to fetch active members.");
                });
                e.printStackTrace();
            }
        }).start();
    }

    private void showActiveMembersDialog(String membersList) {
        // Create the Alert dialog
        Alert membersDialog = new Alert(Alert.AlertType.INFORMATION);
        membersDialog.setTitle("Active Members");
        membersDialog.setHeaderText("Currently Active Members:");
        membersDialog.setContentText(membersList.replace(",", "\n")); // Format names line by line

        // Get the DialogPane of the Alert
        DialogPane dialogPane = membersDialog.getDialogPane();

        // Apply CSS styles dynamically
        dialogPane.setStyle(
                "-fx-background-color: linear-gradient(to bottom, #ffffff, #d4e4ff); " + // Gradient background
                        "-fx-border-color: #5a91d6; " +
                        "-fx-border-width: 2px; " +
                        "-fx-border-radius: 12px; " +
                        "-fx-background-radius: 12px; " +
                        "-fx-padding: 10px;"
        );

        // Style Header Text
        Label headerLabel = (Label) dialogPane.lookup(".header-panel .label");
        if (headerLabel != null) {
            headerLabel.setStyle(
                    "-fx-font-size: 18px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-text-fill: #2d2d2d;" // Darker gray text for the header
            );
        }

        // Style Content Text
        Label contentLabel = (Label) dialogPane.lookup(".content.text");
        if (contentLabel != null) {
            contentLabel.setStyle(
                    "-fx-font-size: 14px; " +
                            "-fx-font-family: 'Verdana'; " +
                            "-fx-text-fill: #3e4b59;" // Muted blue-gray text
            );
        }

        // Add an Icon (Optional)
        dialogPane.setGraphic(null); // Remove the default icon
        Label iconLabel = new Label("👥"); // Custom icon as a label
        iconLabel.setStyle(
                "-fx-font-size: 24px; " +
                        "-fx-text-fill: #5a91d6;" // Blue color for the icon
        );
        dialogPane.setGraphic(iconLabel); // Set the custom icon

        // Show the dialog
        membersDialog.showAndWait();
    }





    @FXML
    public void BackHandle(ActionEvent event) {
        // Create a custom dialog
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.setTitle("Exit Game");
        dialog.setHeaderText("Are you sure you want to exit?");

        // Create custom buttons for dialog
        ButtonType okButton = new ButtonType("Yes", ButtonBar.ButtonData.OK_DONE);
        ButtonType cancelButton = new ButtonType("No", ButtonBar.ButtonData.CANCEL_CLOSE);

        // Add buttons to the dialog
        dialog.getDialogPane().getButtonTypes().setAll(okButton, cancelButton);

        // Set custom content for the dialog (Optional)
        Label contentLabel = new Label("Do you want to save your progress before exiting?");
        dialog.getDialogPane().setContent(contentLabel);

        // Inline CSS Styling (Without External CSS File)
        dialog.getDialogPane().setStyle("-fx-background-color: #f5f5f5; -fx-border-color: #cccccc; -fx-border-width: 2; -fx-padding: 20;");

        // Button styling
        dialog.getDialogPane().lookupButton(okButton).setStyle("-fx-font-size: 14px; -fx-base: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-background-radius: 5px;");
        dialog.getDialogPane().lookupButton(cancelButton).setStyle("-fx-font-size: 14px; -fx-base: #f44336; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-background-radius: 5px;");

        // Set the hover effects for buttons
        dialog.getDialogPane().lookupButton(okButton).setOnMouseEntered(e -> {
            dialog.getDialogPane().lookupButton(okButton).setStyle("-fx-font-size: 14px; -fx-base: #45a049; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-background-radius: 5px;");
        });
        dialog.getDialogPane().lookupButton(okButton).setOnMouseExited(e -> {
            dialog.getDialogPane().lookupButton(okButton).setStyle("-fx-font-size: 14px; -fx-base: #4CAF50; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-background-radius: 5px;");
        });

        dialog.getDialogPane().lookupButton(cancelButton).setOnMouseEntered(e -> {
            dialog.getDialogPane().lookupButton(cancelButton).setStyle("-fx-font-size: 14px; -fx-base: #f44336; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-background-radius: 5px;");
        });
        dialog.getDialogPane().lookupButton(cancelButton).setOnMouseExited(e -> {
            dialog.getDialogPane().lookupButton(cancelButton).setStyle("-fx-font-size: 14px; -fx-base: #f44336; -fx-text-fill: white; -fx-padding: 10px 20px; -fx-font-weight: bold; -fx-background-radius: 5px;");
        });

        // Show dialog and wait for user response
        Optional<ButtonType> result = dialog.showAndWait();

        // Handle the user's response
        if (result.isPresent() && result.get() == okButton) {
            try {
                // Load the GameMode page
                Parent gameModeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));

                // Get the current stage (window)
                Stage stage = (Stage) Back.getScene().getWindow();

                // Set the scene to GameMode
                stage.setScene(new Scene(gameModeRoot));
                stage.show();

                System.out.println("Navigated to GameMode page.");
            } catch (IOException e) {
                e.printStackTrace();
                System.err.println("Error navigating to GameMode: " + e.getMessage());
            }
        } else {
            // If the user clicks 'No', stay on the current screen
            System.out.println("Stayed on the current screen.");
        }
    }

    private void closeConnection() {
        try {
            if (socket != null && !socket.isClosed()) {
                socket.close();
            }
            if (in != null) {
                in.close();
            }
            if (out != null) {
                out.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
