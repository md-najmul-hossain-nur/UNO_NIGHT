package com.example.uno_project;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.*;

import static com.example.uno_project.SharedData.mediaPlayer;

public class ComputerHardMode {

    @FXML
    private Button Back;
    @FXML
    private Label ComputerName1;

    @FXML
    private Label ComputerName2;

    @FXML
    private Label ComputerName3;

    @FXML
    private ImageView ComputerView1;

    @FXML
    private ImageView ComputerView2;

    @FXML
    private ImageView ComputerView3;
    @FXML
    private HBox ComputerCardBox1;

    @FXML
    private HBox ComputerCardBox2;

    @FXML
    private HBox ComputerCardBox3;

    @FXML
    private Button Draw;

    @FXML
    private HBox PlayerCardBox;

    @FXML
    private Button Uno_button;
    @FXML
    private ImageView drawPileImage;
    @FXML
    private Circle circleIndicator;

    @FXML
    private StackPane discardPileArea;

    @FXML
    private ImageView discardPileImage;

    @FXML
    private ImageView profileView;
    @FXML
    private Label profileNameLabel;
    private List<String> deck;
    private final List<String> playerHand = new ArrayList<>();
    private final List<String> computer1Hand = new ArrayList<>();
    private final List<String> computer2Hand = new ArrayList<>();
    private final List<String> computer3Hand = new ArrayList<>();
    private String topCard;
    private Queue<String> turnQueue;
    private boolean reverse = false;
    private boolean isUnoCalled = false;
    private boolean isPlayerTurn = false; // Tracks if it's the player's turn
    private final List<String> discardPile = new ArrayList<>();

    @FXML
    public void initialize() {
        profile(); // Initialize player profiles
        initializeDeck(); // Set up the deck
        distributeCardsWithAnimation(() -> {
            setInitialTopCard();// Use animation for distributing cards
            updateUI(); // Update UI after card distribution
            startGame(); // Start the game once cards are distributed
        });
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
        Draw.setStyle(localPlayerButtonBaseStyle);
        Draw.setOnMouseEntered(e -> Draw.setStyle(localPlayerButtonHoverStyle));
        Draw.setOnMouseExited(e -> Draw.setStyle(localPlayerButtonBaseStyle));
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
        Uno_button.setStyle(buyButtonBaseStyle);

        // Add hover effects to Buy Button
        Uno_button.setOnMouseEntered(e -> Uno_button.setStyle(buyButtonHoverStyle));
        Uno_button.setOnMouseExited(e -> Uno_button.setStyle(buyButtonBaseStyle));

        applyButtonStyle(Draw, buttonStyle, hoverStyle);
        applyButtonStyle(Back, buttonStyle, hoverStyle);
        applyButtonStyle(Uno_button,buttonStyle,hoverStyle);
    }
    private void applyButtonStyle(Button button, String normalStyle, String hoverStyle) {
        button.setStyle(normalStyle);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle)); // Change to hover style
        button.setOnMouseExited(e -> button.setStyle(normalStyle));

    }
    private void initializeDeck() {
        deck = new ArrayList<>();
        String[] colors = {"Red", "Green", "Blue", "Yellow"};
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Skip", "Reverse", "Draw2"};

        for (String color : colors) {
            for (String value : values) {
                deck.add(color + " " + value);
                if (!value.equals("0")) {
                    deck.add(color + " " + value); // Add duplicates for non-zero cards
                }
            }
        }
        Collections.shuffle(deck);
    }

    private void distributeCardsWithAnimation(Runnable onComplete) {
        int totalCardsToDistribute = 28; // 7 cards for 4 players
        int[] cardCount = {0};

        circleIndicator.setVisible(false); // Hide the circle during animation

        Timeline timeline = new Timeline();
        timeline.setCycleCount(totalCardsToDistribute);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), event -> {
            ImageView cardImage = new ImageView(new Image(
                    Objects.requireNonNull(getClass().getResource("/Images/UNO_back_Cover.jpeg")).toString()
            ));
            cardImage.setFitWidth(50);
            cardImage.setFitHeight(80);

            // Distribute cards to players and computers
            if (cardCount[0] % 4 == 0) { // Player
                animateCardToPosition(cardImage, PlayerCardBox.localToScene(0, 0).getX(),
                        PlayerCardBox.localToScene(0, 0).getY(), () -> {
                            playerHand.add(deck.removeFirst());
                            updatePlayerCards();
                        });
            } else if (cardCount[0] % 4 == 1) { // Computer 1
                animateCardToPosition(cardImage, -135, 255, () -> {
                    computer1Hand.add(deck.removeFirst());
                    updateComputerCards(ComputerCardBox1, computer1Hand);
                });
            } else if (cardCount[0] % 4 == 2) { // Computer 2
                animateCardToPosition(cardImage, ComputerCardBox2.localToScene(0, 0).getX(),
                        ComputerCardBox2.localToScene(0, 0).getY(), () -> {
                            computer2Hand.add(deck.removeFirst());
                            updateComputerCards(ComputerCardBox2, computer2Hand);
                        });
            } else { // Computer 3
                animateCardToPosition(cardImage, 725, 255, () -> {
                    computer3Hand.add(deck.removeFirst());
                    updateComputerCards(ComputerCardBox3, computer3Hand);
                });
            }

            cardCount[0]++;
            if (cardCount[0] == totalCardsToDistribute) {
                PauseTransition pause = getPauseTransition(onComplete);
                pause.play();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private PauseTransition getPauseTransition(Runnable onComplete) {
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(e -> {
            // Make the circle indicator visible with only the stroke after animation
            circleIndicator.setFill(Color.TRANSPARENT); // Transparent center
            circleIndicator.setStrokeWidth(10); // Stroke width
            circleIndicator.setVisible(true); // Make stroke visible

            // Draw the first card for the discard pile
            if (!deck.isEmpty()) {
                topCard = deck.removeFirst(); // Set the first card as the top card
                discardPile.add(topCard); // Add it to the discard pile
                updateDiscardPile(); // Update the discard pile visuals
            }

            onComplete.run();
        });
        return pause;
    }


    private void animateCardToPosition(ImageView cardImage, double targetX, double targetY, Runnable onFinished) {
        discardPileArea.getChildren().add(cardImage);

        // Get starting coordinates
        double startX = drawPileImage.localToScene(0, 0).getX();
        double startY = drawPileImage.localToScene(0, 0).getY();
        cardImage.setLayoutX(startX);
        cardImage.setLayoutY(startY);

        // Translate Transition
        TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), cardImage);
        translate.setFromX(0);
        translate.setFromY(0);
        translate.setToX(targetX - startX);
        translate.setToY(targetY - startY);
        translate.setInterpolator(Interpolator.EASE_BOTH);

        // Fade Transition
        FadeTransition fade = new FadeTransition(Duration.seconds(0.5), cardImage);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);

        // Rotate Transition
        RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), cardImage);
        rotate.setByAngle(360);

        // Combine Transitions
        ParallelTransition animation = new ParallelTransition(translate, fade, rotate);
        animation.setOnFinished(event -> {
            discardPileArea.getChildren().remove(cardImage);

            // Update discard pile immediately
            updateDiscardPile();

            // Call the onFinished Runnable
            onFinished.run();
        });

        animation.play();
    }
    private void animateCardToDiscardPile(ImageView cardImage, String card, Runnable onFinished) {
        double startX = cardImage.localToScene(0, 0).getX();
        double startY = cardImage.localToScene(0, 0).getY();
        cardImage.setLayoutX(startX);
        cardImage.setLayoutY(startY);

        double targetX = discardPileImage.localToScene(0, 0).getX();
        double targetY = discardPileImage.localToScene(0, 0).getY();

        TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), cardImage);
        translate.setFromX(0);
        translate.setFromY(0);
        translate.setToX(targetX - startX);
        translate.setToY(targetY - startY);
        translate.setInterpolator(Interpolator.EASE_BOTH);

        RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), cardImage);
        rotate.setByAngle(360);

        ParallelTransition animation = new ParallelTransition(translate, rotate);
        animation.setOnFinished(event -> {
            // Update discard pile state
            topCard = card;
            discardPile.add(card);
            updateDiscardPile();

            onFinished.run();
        });

        animation.play();
    }
    private void startGame() {
        turnQueue = new LinkedList<>(Arrays.asList("Player", "Computer1", "Computer2", "Computer3"));
        refillQueue(); // Ensure the queue is populated with active players
        nextTurn();
    }
    private void updateDrawPile() {
        if (!deck.isEmpty())
            drawPileImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/UNO_back_Cover.jpeg"))));
        else {
            drawPileImage.setImage(null); // Hide draw pile if empty
        }
    }
    private void setInitialTopCard() {
        while (!deck.isEmpty()) {
            String potentialTopCard = deck.remove(0); // Draw the first card from the deck

            // Check if it's not a power card
            if (!isPowerCard(potentialTopCard)) {
                topCard = potentialTopCard; // Set this as the top card
                discardPile.add(topCard); // Add it to the discard pile
                updateDiscardPile(); // Update the UI for the discard pile
                break;
            } else {
                // If it's a power card, add it back to the deck at the end
                deck.add(potentialTopCard);
            }
        }
    }
    private boolean isPowerCard(String card) {
        String[] cardParts = card.split(" ");
        String value = cardParts.length > 1 ? cardParts[1] : "";
        return value.equals("Skip") || value.equals("Reverse") || value.equals("Draw2") || value.equals("Draw4") || value.equals("Wild");
    }
    private void handleSpecialCard(String card) {
        String currentTurn;

        if (reverse) {
            currentTurn = ((Deque<String>) turnQueue).getLast();
        } else {
            currentTurn = turnQueue.peek();
        }

        String[] cardParts = card.split(" ");
        String value = cardParts.length > 1 ? cardParts[1] : "";

        switch (value) {
            case "Skip" -> {
                System.out.println("Skip card played. Next player's turn is skipped.");
                if (!turnQueue.isEmpty()) {
                    if (reverse) {
                        ((Deque<String>) turnQueue).pollLast(); // Skip the last player
                    } else {
                        turnQueue.poll(); // Skip the first player
                    }
                }
            }
            case "Reverse" -> {
                System.out.println(currentTurn + " played Reverse. Turn order reversed.");
                reverse = !reverse; // Toggle the reverse flag
                System.out.println("Reverse flag is now: " + reverse);
            }
            case "Draw2" -> {
                System.out.println("Draw2 card played. Next player draws 2 cards.");
                if (!turnQueue.isEmpty()) {
                    // Get the next player based on the reverse flag
                    String nextPlayer = reverse ? ((Deque<String>) turnQueue).pollLast() : turnQueue.poll();

                    // Draw 2 cards for the next player
                    drawCards(nextPlayer, 2);
                    System.out.println(nextPlayer + " drew 2 cards and skips their turn.");

                    // Skip re-adding the player to the queue
                }
            }

        }
    }
    private void nextTurn() {
        if (checkForWinner()) {
            return; // Stop the game if a winner is found
        }
        if (turnQueue.isEmpty()) {
            refillQueue(); // Refill the queue if it's empty
            if (turnQueue.isEmpty()) {
                System.out.println("No players left. Ending the game.");
                return; // Stop if no players can continue
            }
        }
        Deque<String> dequeQueue = (Deque<String>) turnQueue;
        String currentTurn;

        // Determine the next player based on the reverse flag
        if (reverse) {
            currentTurn = dequeQueue.pollLast(); // Get the last player in reverse order
        } else {
            currentTurn = dequeQueue.poll(); // Get the first player in normal order
        }
        System.out.println(currentTurn + " is taking its turn.");
        updateProfileHighlighting(currentTurn);

        if (currentTurn.equals("Player")) {
            handlePlayerTurn();
        } else {
            System.out.println(currentTurn + " is thinking...");
            new Thread(() -> {
                try {
                    Thread.sleep(1000); // Simulate AI thinking time
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }
                Platform.runLater(() -> handleComputerTurn(currentTurn));
            }).start();
        }
        // Re-add the current player to the queue after handling the turn
        if (!checkForWinner()) {
            if (reverse) {
                dequeQueue.addFirst(currentTurn); // Add to the front if reverse
            } else {
                dequeQueue.add(currentTurn); // Add to the end if normal order
            }
        }
        System.out.println("Turn Queue after turn: " + dequeQueue);
        System.out.println("Reverse flag: " + reverse);
    }
    private void handlePlayerTurn() {
        isPlayerTurn = true; // Flag to indicate it's the player's turn
        PlayerCardBox.setDisable(false); // Enable player actions
        Draw.setDisable(false); // Allow drawing a card
        if (topCard != null) {
            String topColor = topCard.split(" ")[0]; // Extract the color of the top card
            if (hasMatchingCard(topColor)) {
                System.out.println("Player has no matching cards. Consider drawing a card.");
            }
        }

        System.out.println("Player's turn. Choose a card to play or draw a card.");
    }
    private void handleComputerTurn(String computer) {
        PlayerCardBox.setDisable(true);
        Draw.setDisable(true);
        System.out.println(computer + " is taking its turn."); // Log the computer's turn
        List<String> computerHand = getComputerHand(computer);
        if (computerHand != null) {
            playComputerMove(computerHand, computer); // Delegate move logic to playComputerMove
        } else {
            System.err.println("Invalid computer name: " + computer);
        }
    }
    // Helper method to get the hand of a specific computer
    private List<String> getComputerHand(String computer) {
        return switch (computer) {
            case "Computer1" -> computer1Hand;
            case "Computer2" -> computer2Hand;
            case "Computer3" -> computer3Hand;
            default -> new ArrayList<>();
        };
    }

    private boolean checkForWinner() {
        if (playerHand.isEmpty()) {
            navigateToWinPage("Player", 2000); // Player wins with 2000 coins as reward
            return true;
        } else if (computer1Hand.isEmpty()) {
            navigateToLosePage("Computer1"); // Computer 1 wins
            return true;
        } else if (computer2Hand.isEmpty()) {
            navigateToLosePage("Computer2"); // Computer 2 wins
            return true;
        } else if (computer3Hand.isEmpty()) {
            navigateToLosePage("Computer3"); // Computer 3 wins
            return true;
        }
        return false;
    }
    private void navigateToWinPage(String winner, int rewardCoins) {
        System.out.println(winner + " wins the game!");
        SharedData.addCoins(rewardCoins); // Add reward coins to player's balance
        isPlayerTurn = false; // Stop player interaction
        turnQueue.clear(); // Stop further turns

        try {
            Parent winRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Win.fxml")));
            Stage stage = (Stage) Back.getScene().getWindow();
            stage.setScene(new Scene(winRoot));
            stage.show();

            System.out.println("Navigated to Win Page. Coins added: " + rewardCoins);
        } catch (IOException e) {
            System.err.println("Error loading Win.fxml");
            e.printStackTrace();
        }
    }
    private void navigateToLosePage(String winner) {
        System.out.println(winner + " wins the game!");
        isPlayerTurn = false; // Stop player interaction
        turnQueue.clear(); // Stop further turns

        try {
            Parent loseRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GameOver.fxml")));
            Stage stage = (Stage) Back.getScene().getWindow();
            stage.setScene(new Scene(loseRoot));
            stage.show();

            System.out.println("Navigated to Game Over Page. Winner: " + winner);
        } catch (IOException e) {
            System.err.println("Error loading GameOver.fxml");
            e.printStackTrace();
        }
    }

    private boolean hasMatchingCard(String color) {
        List<String> allHands = new ArrayList<>(playerHand); // Start with player hand
        allHands.addAll(computer1Hand);
        allHands.addAll(computer2Hand);
        allHands.addAll(computer3Hand);
        for (String card : allHands) {
            String cardColor = card.split(" ")[0];
            if (cardColor.equalsIgnoreCase(color)) {
                return false;
            }
        }
        return true;
    }
    private boolean isValidCard(String card) {
        if (topCard == null) return true; // No card on discard pile, all cards are valid
        String[] topCardParts = topCard.split(" ");
        String topColor = topCardParts[0];
        String topValue = topCardParts.length > 1 ? topCardParts[1] : ""; // Handle special cases

        String[] cardParts = card.split(" ");
        String cardColor = cardParts[0];
        String cardValue = cardParts.length > 1 ? cardParts[1] : ""; // Handle special cases

        // Match by color or value
        return cardColor.equals(topColor) || cardValue.equals(topValue);
    }
    private void refillQueue() {
        if (!playerHand.isEmpty()) {
            turnQueue.add("Player");
        }
        if (!computer1Hand.isEmpty()) {
            turnQueue.add("Computer1");
        }
        if (!computer2Hand.isEmpty()) {
            turnQueue.add("Computer2");
        }
        if (!computer3Hand.isEmpty()) {
            turnQueue.add("Computer3");
        }

        if (turnQueue.isEmpty()) {
            System.out.println("No players can continue. Game over!");
        } else {
            System.out.println("Turn queue repopulated: " + turnQueue);
        }
    }

    @FXML
    private void handleUnoButton() {
        if (playerHand.size() == 1 && !isUnoCalled) {
            System.out.println("UNO! Player has one card left.");
            isUnoCalled = true; // Mark UNO call
        } else {
            System.out.println("Invalid UNO call. Either not the right time or already called.");
        }
    }
    private ImageView createCard(String card) {
        String imagePath = getString(card);
        try {
            Image cardImage = new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath)));
            ImageView cardView = new ImageView(cardImage);
            cardView.setFitWidth(60);
            cardView.setFitHeight(90);
            cardView.setOnMouseClicked(this::handleCardClick);
            cardView.setUserData(card);
            return cardView;
        } catch (NullPointerException e) {
            System.err.println("Error: Image not found for card " + card + ". Path: " + imagePath);
            return new ImageView(new Image(Objects.requireNonNull(getClass().getResourceAsStream("/Images/default_card.png"))));
        }
    }
    private static String getString(String card) {
        String[] cardParts = card.split(" ");
        String color = cardParts[0].toLowerCase();
        String value = cardParts.length > 1 ? cardParts[1].toLowerCase() : "";
        String folder;
        String imagePath;
        // Handle regular cards
        folder = color.toUpperCase() + " UNO"; // Folder name based on card color
        imagePath = "/Images/" + folder + "/uno_card_" + color + value + ".png";
        return imagePath;
    }
    private void drawCards(String player, int numberOfCards) {
        for (int i = 0; i < numberOfCards; i++) {
            if (deck.isEmpty()) {
                if (!discardPile.isEmpty() && topCard != null) {
                    // Reshuffle discard pile into draw pile
                    List<String> reshuffledCards = new ArrayList<>(discardPile);
                    reshuffledCards.remove(topCard); // Exclude the top card
                    discardPile.clear();
                    discardPile.add(topCard); // Keep the top card
                    Collections.shuffle(reshuffledCards);
                    deck.addAll(reshuffledCards);
                    System.out.println("Discard pile reshuffled into the draw pile.");
                    updateDrawPile();
                } else {
                    System.out.println("No cards left to draw.");
                    break; // Stop drawing if no cards are available
                }
            }
            if (!deck.isEmpty()) {
                String drawnCard = deck.removeFirst();
                switch (player) {
                    case "Player" -> playerHand.add(drawnCard);
                    case "Computer1" -> computer1Hand.add(drawnCard);
                    case "Computer2" -> computer2Hand.add(drawnCard);
                    case "Computer3" -> computer3Hand.add(drawnCard);
                }
            }
        }
        updateUI(); // Update the UI after drawing cards
    }
    private void updateDiscardPile() {
        if (topCard != null) {
            try {
                String imagePath = getString(topCard); // Get the image path for the top card
                discardPileImage.setImage(new Image(Objects.requireNonNull(getClass().getResourceAsStream(imagePath))));
                System.out.println("Top card updated to: " + topCard);
                // Update the circle indicator for the top card's color
                String cardColor = topCard.split(" ")[0]; // Extract the color
                updateCircleIndicator(cardColor); // Update the stroke color of the circle indicator
            } catch (NullPointerException e) {
                System.err.println("Error: Image not found for card " + topCard);
                discardPileImage.setImage(null); // Set to null if the image isn't found
            }
        } else {
            discardPileImage.setImage(null); // Hide discard pile if no card is present
            circleIndicator.setStroke(Color.TRANSPARENT); // Hide the circle indicator
            circleIndicator.setStrokeWidth(0);
        }
    }
    private void updateCircleIndicator(String color) {
        circleIndicator.setFill(javafx.scene.paint.Color.TRANSPARENT); // Keep the center transparent
        circleIndicator.setVisible(true); // Ensure the stroke is visible
        circleIndicator.setStrokeWidth(10); // Set stroke width
        // Set stroke color based on the color argument
        switch (color.toUpperCase()) {
            case "RED" -> circleIndicator.setStroke(javafx.scene.paint.Color.RED);
            case "BLUE" -> circleIndicator.setStroke(javafx.scene.paint.Color.BLUE);
            case "GREEN" -> circleIndicator.setStroke(javafx.scene.paint.Color.GREEN);
            case "YELLOW" -> circleIndicator.setStroke(javafx.scene.paint.Color.YELLOW);
            default -> circleIndicator.setStroke(javafx.scene.paint.Color.BLACK); // Default stroke
        }

        System.out.println("Circle updated with color: " + color);
    }
    private void updateUI() {
        updatePlayerCards();
        updateComputerCards(ComputerCardBox1, computer1Hand);
        updateComputerCards(ComputerCardBox2, computer2Hand);
        updateComputerCards(ComputerCardBox3, computer3Hand);
        updateDrawPile();
        updateDiscardPile();
        profile();
    }
    private void updatePlayerCards() {
        PlayerCardBox.getChildren().clear();
        int cardCount = playerHand.size();
        double cardWidth = 70;
        double cardHeight = cardWidth *1.5;
        double overlapOffset = -25;
        if (cardCount > 7) {
            overlapOffset = -30 + (cardCount - 7) * -5; // Increase overlap as card count grows
        }

        PlayerCardBox.setSpacing(overlapOffset); // Set spacing to overlap cards
        PlayerCardBox.setAlignment(Pos.CENTER); // Center-align the cards
        for (String card : playerHand) {
            ImageView cardView = createCard(card); // Create ImageView for the card
            // Add hover effects
            cardView.setOnMouseEntered(event -> {
                if (isValidCard(card)) {
                    cardView.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0, 0, 0);");
                } else {
                    cardView.setStyle("-fx-opacity: 0.5;"); // Dim unplayable cards
                }
            });
            cardView.setOnMouseExited(event -> cardView.setStyle("")); // Reset style on exit
            cardView.setFitWidth(cardWidth); // Fixed card width
            cardView.setFitHeight(cardHeight); // Fixed card height
            PlayerCardBox.getChildren().add(cardView); // Add card to HBox
        }
    }
    private void updateComputerCards(HBox computerBox, List<String> computerHand) {
        computerBox.getChildren().clear(); // Clear previous cards
        int cardCount = computerHand.size();
        double cardWidth = 70; // Fixed width for cards
        double cardHeight = cardWidth * 1.5; // Maintain aspect ratio
        double overlapOffset = -20; // Base overlap margin
        // Adjust margin dynamically for better visual spacing with more cards
        if (cardCount > 7) {
            overlapOffset = -30 + (cardCount - 7) * -5; // Increase overlap as card count grows
        }
        computerBox.setSpacing(overlapOffset); // Set spacing to overlap cards
        computerBox.setAlignment(Pos.CENTER); // Center-align the cards
        for (int i = 0; i < cardCount; i++) {
            ImageView cardBack = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/Images/UNO_back_Cover.jpeg")).toString()));
            cardBack.setFitWidth(cardWidth);
            cardBack.setFitHeight(cardHeight);
            computerBox.getChildren().add(cardBack);
        }
    }
    @FXML
    private void handleCardClick(MouseEvent event) {
        if (!isPlayerTurn) return;
        ImageView clickedCard = (ImageView) event.getSource();
        String card = (String) clickedCard.getUserData();
        if (isValidCard(card)) {
            playerHand.remove(card);
            topCard = card; // Set the clicked card as the top card
            animateCardToDiscardPile(clickedCard, card, () -> {
                handleSpecialCard(card);
                updateUI();
                PlayerCardBox.setDisable(true);
                Draw.setDisable(true);
                isPlayerTurn = false;
                nextTurn();
            });
        } else {
            System.out.println("Invalid card played.");
        }
    }
    @FXML
    private void handleDrawCard() {
        if (!isPlayerTurn) return;
        if (deck.isEmpty()) {
            if (!discardPile.isEmpty() && topCard != null) {
                // Reshuffle discard pile into the draw pile, excluding the top card
                List<String> reshuffledCards = new ArrayList<>(discardPile);
                reshuffledCards.remove(topCard); // Keep the top card separate
                discardPile.clear();
                discardPile.add(topCard); // Add the top card back to the discard pile
                Collections.shuffle(reshuffledCards);
                deck.addAll(reshuffledCards);
                System.out.println("Discard pile reshuffled into the draw pile.");
                updateDrawPile();
            } else {
                System.out.println("Draw pile and discard pile are empty. No cards to draw.");
                return; // End turn if no cards can be drawn
            }
        }
        if (!deck.isEmpty()) {
            String drawnCard = deck.removeFirst();
            playerHand.add(drawnCard);
            System.out.println("Player drew: " + drawnCard);
            updatePlayerCards();
            if (isValidCard(drawnCard)) {
                System.out.println("Drawn card is playable. You can play it.");
                // Optionally allow immediate play
            } else {
                System.out.println("Drawn card is unplayable. Player's turn ends.");
                PlayerCardBox.setDisable(true);
                Draw.setDisable(true);
                isPlayerTurn = false;
                nextTurn();
            }
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
    private void playComputerMove(List<String> computerHand, String computerName) {
        Platform.runLater(() -> {
            if (checkForWinner()) return;
            List<String> validCards = computerHand.stream().filter(this::isValidCard).toList();
            if (!validCards.isEmpty()) {
                String selectedCard = validCards.getFirst();
                computerHand.remove(selectedCard);
                topCard = selectedCard;
                // Directly update the game state
                discardPile.add(selectedCard);
                updateDiscardPile();
                updateCircleIndicator(selectedCard.split(" ")[0]);
                System.out.println(computerName + " played: " + selectedCard);
                // Handle any special effects of the card
                handleSpecialCard(selectedCard);
                // Update the UI and move to the next turn
                updateUI();
                nextTurn();
            } else {
                // No valid cards, the computer draws a card
                drawCards(computerName, 1);
                System.out.println(computerName + " drew a card.");
                nextTurn();
            }
        });
    }
    @FXML
    public void profile() {
        profileView.setImage(new Image(Objects.requireNonNull(getClass().getResource(
                Objects.requireNonNullElse(SharedData.selectedProfileImagePath, "/Images/icon/Default_icon.jpg"))).toString()));
        profileNameLabel.setText(Objects.requireNonNullElse(SharedData.selectedProfileName, "Player 1"));
    }
    private void updateProfileHighlighting(String currentTurn) {
        // Reset all profiles to default
        profileView.setStyle("-fx-effect: none; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
        profileNameLabel.setStyle("-fx-font-weight: normal; -fx-text-fill: white;");
        ComputerView1.setStyle("-fx-effect: none; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
        ComputerName1.setStyle("-fx-font-weight: normal; -fx-text-fill: white;");
        ComputerView2.setStyle("-fx-effect: none; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
        ComputerName2.setStyle("-fx-font-weight: normal; -fx-text-fill: white;");

        ComputerView3.setStyle("-fx-effect: none; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
        ComputerName3.setStyle("-fx-font-weight: normal; -fx-text-fill: white;");

        // Highlight the current turn profile
        switch (currentTurn) {
            case "Player" -> {
                // Highlight the player's profile
                profileView.setStyle("-fx-effect: dropshadow(gaussian, yellow, 20, 0.7, 0, 0);"
                        + "-fx-scale-x: 1.1; -fx-scale-y: 1.1;");
                profileNameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: yellow;");
            }
            case "Computer1" -> {
                ComputerView1.setStyle("-fx-effect: dropshadow(gaussian, red, 20, 0.7, 0, 0);"
                        + "-fx-scale-x: 1.1; -fx-scale-y: 1.1;");
                ComputerName1.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
            }
            case "Computer2" -> {
                ComputerView2.setStyle("-fx-effect: dropshadow(gaussian, red, 20, 0.7, 0, 0);"
                        + "-fx-scale-x: 1.1; -fx-scale-y: 1.1;");
                ComputerName2.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
            }
            case "Computer3" -> {
                ComputerView3.setStyle("-fx-effect: dropshadow(gaussian, red, 20, 0.7, 0, 0);"
                        + "-fx-scale-x: 1.1; -fx-scale-y: 1.1;");
                ComputerName3.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");
            }
        }
    }






}