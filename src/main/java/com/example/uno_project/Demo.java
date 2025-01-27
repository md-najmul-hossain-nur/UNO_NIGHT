package com.example.uno_project;

import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.*;

public class Demo {
    @FXML
    private ImageView discardPileImage;
    @FXML
    private StackPane discardPileArea;

    @FXML
    private Button Back;
    @FXML
    private ImageView ComputerView;
    @FXML
    private Button Drawcard;
    @FXML
    private Circle circleIndicator;
    @FXML
    private Button Uno_player;

    @FXML
    private Button Uno_computer;
    @FXML
    private ImageView drawPileImage;
    @FXML
    private HBox computerCardsBox;
    @FXML
    private HBox playerCardsBox;
    private Queue<Card> drawPile = new LinkedList<>();
    private final Queue<Card> discardPile = new LinkedList<>();
    private final List<Card> playerHand = new ArrayList<>();
    private final List<Card> computerHand = new ArrayList<>();
    private boolean isUnoCalled = false;
    private boolean isPlayerTurn = true;
    private String selectedWildColor = "";
    private MediaPlayer unoSoundPlayer;

    @FXML
    public void initialize() {
        try {
            initializeDeck();
            shuffleDrawPile();
            preloadUnoSound();

            distributeCards(() -> {
                setInitialDiscardPileCard();
                updateUI();
                startPlayerTurn();
            });
        } catch (Exception e) {
            e.printStackTrace();
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
        Drawcard.setStyle(localPlayerButtonBaseStyle);
        Drawcard.setOnMouseEntered(e -> Drawcard.setStyle(localPlayerButtonHoverStyle));
        Drawcard.setOnMouseExited(e -> Drawcard.setStyle(localPlayerButtonBaseStyle));
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
        Uno_player.setStyle(buyButtonBaseStyle);

        // Add hover effects to Buy Button
        Uno_player.setOnMouseEntered(e -> Uno_player.setStyle(buyButtonHoverStyle));
        Uno_player.setOnMouseExited(e -> Uno_player.setStyle(buyButtonBaseStyle));

        applyButtonStyle(Drawcard, buttonStyle, hoverStyle);
        applyButtonStyle(Back, buttonStyle, hoverStyle);
        applyButtonStyle(Uno_player,buttonStyle,hoverStyle);
    }
    private void applyButtonStyle(Button button, String normalStyle, String hoverStyle) {
        button.setStyle(normalStyle);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle)); // Change to hover style
        button.setOnMouseExited(e -> button.setStyle(normalStyle));

    }

    private void initializeDeck() {
        String[] colors = {"BLUE", "GREEN", "RED", "YELLOW"};
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Skip", "Draw2", "Draw4", "Wild"};

        for (String color : colors) {
            for (String value : values) {
                if (value.equals("Wild") || value.equals("Draw4")) {
                    drawPile.add(new Card("", value));
                    drawPile.add(new Card("", value));
                } else {
                    drawPile.add(new Card(color, value));
                    if (!value.equals("0")) {
                        drawPile.add(new Card(color, value));
                    }
                }
            }
        }
    }

    private void shuffleDrawPile() {
        List<Card> tempDeck = new ArrayList<>(drawPile);
        Collections.shuffle(tempDeck);
        drawPile = new LinkedList<>(tempDeck);
    }

    private void preloadUnoSound() {
        try {
            String musicPath = Objects.requireNonNull(getClass().getResource("/Music/UnoSound.mp3")).toString();
            Media media = new Media(musicPath);
            unoSoundPlayer = new MediaPlayer(media);
        } catch (Exception e) {
            System.err.println("Error loading UNO sound: " + e.getMessage());
        }
    }

    private void playUnoMusic() {
        try {
            if (unoSoundPlayer != null) {
                unoSoundPlayer.stop(); // Reset if already playing
                unoSoundPlayer.play();
            }
        } catch (Exception e) {
            System.err.println("Error playing UNO sound: " + e.getMessage());
        }
    }

    private void distributeCards(Runnable onComplete) {
        try {
            playerHand.clear();
            playerHand.add(new Card("WILD", "Wild"));
            playerHand.add(new Card("YELLOW", "Draw4"));
            playerHand.add(new Card("YELLOW", "9"));

            computerHand.clear();
            computerHand.add(new Card("YELLOW", "2"));
            computerHand.add(new Card("YELLOW", "4"));
            computerHand.add(new Card("BLUE", "9"));

            drawPile.clear();
            initializeDeck();

            drawPile.removeIf(card -> playerHand.contains(card) || computerHand.contains(card));

            shuffleDrawPile();
            onComplete.run();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setInitialDiscardPileCard() {
        try {
            while (!drawPile.isEmpty()) {
                Card card = drawPile.poll();
                // Ensure the card is not a power card (Skip, Draw2, Draw4, Wild)
                if (card != null && !card.value().equalsIgnoreCase("Skip") &&
                        !card.value().equalsIgnoreCase("Draw2") &&
                        !card.value().equalsIgnoreCase("Draw4") &&
                        !card.value().equalsIgnoreCase("Wild")) {

                    discardPile.add(card); // Add the card to the discard pile
                    updateDiscardPile(); // Update the UI to reflect the discard pile
                    updateCircleIndicator(card.color()); // Update the circle to show the card color
                    break;
                } else {
                    // If it's a power card, move it to the back of the draw pile
                    drawPile.add(card);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateCircleIndicator(String color) {
        try {
            if (color == null || color.isEmpty()) {
                circleIndicator.setVisible(false); // Hide the circle if the color is invalid
                return;
            }

            circleIndicator.setVisible(true); // Make the circle visible
            circleIndicator.setStrokeWidth(10); // Set stroke width for better visibility
            circleIndicator.setFill(javafx.scene.paint.Color.TRANSPARENT); // Keep the center transparent

            // Set the stroke color based on the card color
            switch (color.toUpperCase()) {
                case "RED" -> circleIndicator.setStroke(javafx.scene.paint.Color.RED);
                case "BLUE" -> circleIndicator.setStroke(javafx.scene.paint.Color.BLUE);
                case "GREEN" -> circleIndicator.setStroke(javafx.scene.paint.Color.GREEN);
                case "YELLOW" -> circleIndicator.setStroke(javafx.scene.paint.Color.YELLOW);
                default -> circleIndicator.setStroke(javafx.scene.paint.Color.GRAY); // Default to gray
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private void updateUI() {
        try {
            updateDrawPile();
            updateDiscardPile();
            updatePlayerCards();
            updateComputerCards();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDrawPile() {
        try {
            drawPileImage.setImage(!drawPile.isEmpty() ?
                    new Image(Objects.requireNonNull(getClass().getResource("/Images/UNO_back_Cover.jpeg")).toString()) : null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateDiscardPile() {
        try {
            if (!discardPile.isEmpty()) {
                Card topCard = ((LinkedList<Card>) discardPile).getLast();
                discardPileImage.setImage(new Image(Objects.requireNonNull(getClass()
                        .getResource(getCardImagePath(topCard))).toString()));
            } else {
                discardPileImage.setImage(null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private String getCardImagePath(Card card) {
        try {
            if (card == null) return "";

            if (card.value().equalsIgnoreCase("wild")) {
                return "/Images/POWER CARD UNO/uno_card_wildchange.png";
            } else if (card.value().equalsIgnoreCase("draw4")) {
                return "/Images/POWER CARD UNO/uno_card_wilddraw4.png";
            } else {
                return "/Images/" + card.color().toUpperCase() + " UNO/uno_card_"
                        + card.color().toLowerCase() + card.value().toLowerCase() + ".png";
            }
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    private void updatePlayerCards() {
        try {
            playerCardsBox.getChildren().clear();
            for (Card card : playerHand) {
                if (card != null) {
                    ImageView cardView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(getCardImagePath(card))).toString()));
                    cardView.setFitWidth(50);
                    cardView.setFitHeight(80);
                    cardView.setOnMouseClicked(event -> handlePlayerCardClick(card));
                    playerCardsBox.getChildren().add(cardView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateComputerCards() {
        try {
            computerCardsBox.getChildren().clear();
            for (Card card : computerHand) {
                if (card != null) {
                    ImageView cardView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(getCardImagePath(card))).toString()));
                    cardView.setFitWidth(50);
                    cardView.setFitHeight(80);
                    computerCardsBox.getChildren().add(cardView);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePlayerCardClick(Card card) {
        try {
            if (card != null && isValidCard(card)) {
                playCard(card, playerHand);
                if (card.value().equals("Wild")) {
                    selectWildColor();
                } else if (card.value().equals("Draw4")) {
                    addCardsToHBox(computerHand, Arrays.asList(
                            new Card("YELLOW", "5"),
                            new Card("YELLOW", "7"),
                            new Card("RED", "9"),
                            new Card("GREEN", "9")));
                    System.out.println("Opponent draws 4 cards: 2 Yellow, 1 Red, 1 Green!");
                }

                if (playerHand.isEmpty()) {
                    System.out.println("Player wins!");
                    navigateToWinPage(); // Pass the rewardCoins value (example: 100).
                } else {
                    handleComputerTurn();
                }
            } else {
                System.out.println("Invalid card!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addCardsToHBox(List<Card> hand, List<Card> cardsToAdd) {
        try {
            for (Card card : cardsToAdd) {
                if (card != null) {
                    hand.add(card);
                    ImageView cardView = new ImageView(new Image(Objects.requireNonNull(getClass().getResource(getCardImagePath(card))).toString()));
                    cardView.setFitWidth(50);
                    cardView.setFitHeight(80);
                    if (hand == computerHand) {
                        computerCardsBox.getChildren().add(cardView);
                    } else {
                        playerCardsBox.getChildren().add(cardView);
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void selectWildColor() {
        try {
            List<String> choices = Arrays.asList("RED", "BLUE", "GREEN", "YELLOW");
            ChoiceDialog<String> dialog = new ChoiceDialog<>("RED", choices);
            dialog.setTitle("Wild Card Color Selection");
            dialog.setHeaderText("Choose a color for the Wild card");
            dialog.setContentText("Select color:");

            Optional<String> result = dialog.showAndWait();
            result.ifPresent(color -> {
                selectedWildColor = color;
                System.out.println("Player chose the color: " + color);
                updateCircleIndicator(color);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void handleComputerTurn() {
        try {
            isPlayerTurn = false;
            updateUI();

            PauseTransition pause = new PauseTransition(Duration.seconds(2));
            pause.setOnFinished(event -> {
                try {
                    Card cardToPlay = computerHand.stream().filter(this::isValidCard).findFirst().orElse(null);
                    if (cardToPlay != null) {
                        playCard(cardToPlay, computerHand);
                        updateCircleIndicator(cardToPlay.color());
                        if (computerHand.isEmpty()) {
                            System.out.println("Computer wins!");

                        } else {
                            isPlayerTurn = true;
                            updateUI();
                        }
                    } else {
                        System.out.println("Computer draws a card.");
                        addCardsToHBox(computerHand, List.of(drawPile.poll()));
                        updateUI();
                        isPlayerTurn = true;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
            pause.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean isValidCard(Card card) {
        try {
            if (discardPile.isEmpty()) return true;
            Card topCard = ((LinkedList<Card>) discardPile).getLast();
            return card != null && (card.color().equals(selectedWildColor) || card.color().equals(topCard.color()) || card.value().equals(topCard.value()) || card.value().equals("Wild") || card.value().equals("Draw4"));
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private void playCard(Card card, List<Card> hand) {
        try {
            if (card != null) {
                hand.remove(card);
                discardPile.add(card);
                updateDiscardPile();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startPlayerTurn() {
        try {
            isPlayerTurn = true;
            System.out.println("Player's turn started.");
            updateUI();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void navigateToWinPage() {
        try {
            SharedData.addCoins(1000);
            Parent winRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Winpage.fxml")));
            Stage stage = (Stage) Back.getScene().getWindow();
            stage.setScene(new Scene(winRoot));
            stage.show();
            System.out.println("Navigated to Win Page. Coins added: " + 100);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
                Parent gameModeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GameMode.fxml")));

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


    @FXML
    public void handleDrawCard(ActionEvent event) {
        try {
            if (isPlayerTurn) {
                System.out.println("Player draws a card.");
                addCardsToHBox(playerHand, List.of(drawPile.poll()));
                updatePlayerCards();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleUnoCallPlayer(ActionEvent event) {
        try {
            if (isPlayerTurn && playerHand.size() == 1) {
                System.out.println("UNO called by Player!");
                isUnoCalled = true;
                playUnoMusic(); // Play UNO sound
            } else {
                System.out.println("UNO call invalid!");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public record Card(String color, String value) {}
}