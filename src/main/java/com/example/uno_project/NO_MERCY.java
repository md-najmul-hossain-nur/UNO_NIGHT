package com.example.uno_project;
import javafx.animation.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class NO_MERCY {
    @FXML
    private ImageView discardPileImage;
    @FXML
    private StackPane discardPileArea;

    @FXML
    private Button Back;
    @FXML
    private Label ComputerName;

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
    @FXML
    private ImageView profileView;
    @FXML
    private Label profileNameLabel;
    private Queue<Card> drawPile = new LinkedList<>();
    private final Queue<Card> discardPile = new LinkedList<>();
    private final List<Card> playerHand = new ArrayList<>();
    private final List<Card> computerHand = new ArrayList<>();
    private boolean isUnoCalled = false;
    private boolean isPlayerTurn = true;

    @FXML
    public void initialize() {
        loadSelectedCard();
        profile();
        System.out.println("Initialization executed!");
        initializeDeck();
        shuffleDrawPile();

        distributeCardsWithAnimation(() -> {
            setInitialDiscardPileCard();
            updateUI();
            System.out.println("Card distribution completed!");
            startPlayerTurn();
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
        String[] values = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "Skip", "Draw2", "Draw4", "Wild","Swap","Zero"};


        for (String color : colors) {
            for (String value : values) {
                drawPile.add(new Card(color, value));
                if (!value.equals("0")) {
                    drawPile.add(new Card(color, value));
                }
            }
        }
    }

    private void shuffleDrawPile() {
        List<Card> tempDeck = new ArrayList<>(drawPile);
        Collections.shuffle(tempDeck);
        drawPile = new LinkedList<>(tempDeck);
    }

    private void distributeCardsWithAnimation(Runnable onComplete) {
        int totalCardsToDistribute = 14; // 7 for player + 7 for computer
        int[] cardCount = {0};

        // Load the selected player card back
        String playerCardBack = loadSelectedCardBack();

        Timeline timeline = new Timeline();
        timeline.setCycleCount(totalCardsToDistribute);

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), _ -> {
            ImageView cardImage;

            // Use player's selected card back for their cards and default for computer
            if (cardCount[0] % 2 == 0) {
                cardImage = new ImageView(new Image(Objects.requireNonNull(getClass()
                        .getResource("/Images/UNO_back_Cover.jpeg")).toString())); // Default card for computer
            } else {
                cardImage = new ImageView(new Image(Objects.requireNonNull(getClass()
                        .getResource(playerCardBack)).toString())); // Player's selected card back
            }

            cardImage.setFitWidth(50);
            cardImage.setFitHeight(80);

            if (cardCount[0] % 2 == 0) {
                animateCardToHand(cardImage, computerCardsBox, () -> {
                    computerHand.add(drawPile.poll());
                    updateComputerCards();
                });
            } else {
                animateCardToHand(cardImage, playerCardsBox, () -> {
                    playerHand.add(drawPile.poll());
                    updatePlayerCards();
                });
            }

            cardCount[0]++;

            if (cardCount[0] == totalCardsToDistribute) {
                PauseTransition pause = new PauseTransition(Duration.seconds(1));
                pause.setOnFinished(_ -> {
                    setInitialDiscardPileCard();
                    onComplete.run();
                });
                pause.play();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.play();
    }

    private void animateCardToHand(ImageView cardImage, HBox targetBox, Runnable onFinished) {
        discardPileArea.getChildren().add(cardImage);
        double startX = drawPileImage.localToScene(0, 0).getX();
        double startY = drawPileImage.localToScene(0, 0).getY();
        cardImage.setLayoutX(startX);
        cardImage.setLayoutY(startY);

        double targetX = targetBox.localToScene(0, 0).getX() + 10 * targetBox.getChildren().size();
        double targetY = targetBox.localToScene(0, 0).getY();

        // Translate Transition
        TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), cardImage);
        translate.setFromX(0);
        translate.setFromY(0);
        translate.setToX(targetX - startX);
        translate.setToY(targetY - startY);
        translate.setInterpolator(Interpolator.EASE_BOTH);

        // Fade Transition
        ParallelTransition animation = getParallelTransition(cardImage, onFinished, translate);

        animation.play();
    }
    private ParallelTransition getParallelTransition(ImageView cardImage, Runnable onFinished, TranslateTransition translate) {
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
            onFinished.run();
        });
        return animation;
    }
    private String loadSelectedCardBack() {
        String defaultCardBack = "/Images/UNO_back_Cover.jpeg"; // Default card back
        try (BufferedReader reader = new BufferedReader(new FileReader("selectedPlayerCard.txt"))) {
            String savedPath = reader.readLine();
            if (savedPath != null && !savedPath.isEmpty()) {
                System.out.println("Loaded selected card back: " + savedPath);
                return savedPath; // Return the saved card back
            }
        } catch (IOException e) {
            System.out.println("No custom card back found. Using default.");
        }
        return defaultCardBack; // Use default if no custom card is saved
    }
    private void setInitialDiscardPileCard() {
        while (!drawPile.isEmpty()) {
            Card card = drawPile.poll(); // Draw the top card

            // Check if the card is not a power card
            if (!isPowerCard(card)) {
                // If it's not a special card, set it as the first discard
                discardPile.add(card);
                updateDiscardPile();
                break;
            } else {
                // Put the power card back at the bottom of the draw pile
                drawPile.add(card);
                System.out.println("Power card skipped for initial discard pile: " + card);
            }
        }
    }
    private boolean isPowerCard(Card card) {
        return card.value().equalsIgnoreCase("Draw4") ||
                card.value().equalsIgnoreCase("Wild") || card.value().equalsIgnoreCase("Zero") || card.value().equalsIgnoreCase("Swap");
    }
    private void updateUI() {
        updateDrawPile();
        updateDiscardPile();
        updatePlayerCards();
        updateComputerCards();
        updateDrawCardButton();
        profile();
    }
    private void updateDrawPile() {
        drawPileImage.setImage(!drawPile.isEmpty() ?
                new Image(Objects.requireNonNull(getClass().getResource("/Images/UNO_back_Cover.jpeg")).toString()) : null);
    }
    private void updateDiscardPile() {
        if (!discardPile.isEmpty()) {
            Card topCard = ((LinkedList<Card>) discardPile).getLast();

            try {
                // Attempt to load the image
                String imagePath = getCardImagePath(topCard);
                discardPileImage.setImage(new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString()));
            } catch (NullPointerException e) {
                System.err.println("Error: Image not found for card: " + topCard);
                discardPileImage.setImage(null); // Set to null if image is missing
            }

            // Set circle indicator color based on the card's color
            circleIndicator.setStrokeWidth(10);
            circleIndicator.setFill(javafx.scene.paint.Color.TRANSPARENT);
            circleIndicator.setVisible(true);

            switch (topCard.color().toUpperCase()) {
                case "RED" -> circleIndicator.setStroke(javafx.scene.paint.Color.RED);
                case "BLUE" -> circleIndicator.setStroke(javafx.scene.paint.Color.BLUE);
                case "GREEN" -> circleIndicator.setStroke(javafx.scene.paint.Color.GREEN);
                case "YELLOW" -> circleIndicator.setStroke(javafx.scene.paint.Color.YELLOW);
                default -> circleIndicator.setStroke(javafx.scene.paint.Color.BLACK);
            }
        } else {
            discardPileImage.setImage(null); // No cards in the pile
            circleIndicator.setVisible(false); // Hide indicator
        }
    }

    private String getCardImagePath(Card card) {
        if (card.value().equalsIgnoreCase("wild")) {
            return "/Images/POWER CARD UNO/uno_card_wildchange.png"; // Wild card
        } else if (card.value().equalsIgnoreCase("draw4")) {
            return "/Images/POWER CARD UNO/uno_card_wilddraw4.png"; // Draw4 card
        } else if (card.value().equalsIgnoreCase("swap")) {
            return "/Images/Special Card/Swaphand.jpg"; // Swap Hand card
        } else if (card.value().equalsIgnoreCase("zero")) {
            return "/Images/Special Card/Zero.jpg"; // Zero card
        } else {
            // Other cards (color-specific)
            return "/Images/" + card.color().toUpperCase() + " UNO/uno_card_"
                    + card.color().toLowerCase() + card.value().toLowerCase() + ".png";
        }
    }

    private void updatePlayerCards() {
        playerCardsBox.getChildren().clear(); // Clear existing cards

        int cardCount = playerHand.size();
        double cardWidth = 70; // Fixed card width
        double cardHeight = cardWidth * 1.5; // Maintain aspect ratio
        double overlapOffset = -20; // Base overlap margin

        // Dynamically adjust overlap based on the number of cards
        if (cardCount > 7) {
            overlapOffset = -30 + (cardCount - 7) * -5; // Increase overlap for more cards
        }
        playerCardsBox.setSpacing(overlapOffset); // Set spacing to overlap cards
        playerCardsBox.setAlignment(Pos.CENTER); // Center-align the cards
        for (Card card : playerHand) {
            ImageView cardView = new ImageView();
            cardView.setFitWidth(cardWidth); // Fixed card width
            cardView.setFitHeight(cardHeight); // Fixed card height

            // Set card image
            String imagePath = getCardImagePath(card);
            try {
                cardView.setImage(new Image(Objects.requireNonNull(getClass().getResource(imagePath)).toString()));
            } catch (NullPointerException e) {
                System.err.println("Error: Player card image not found: " + imagePath);
            }

            // Add hover effects
            cardView.setOnMouseEntered(event -> {
                if (isValidCard(card)) {
                    cardView.setStyle("-fx-effect: dropshadow(gaussian, yellow, 10, 0, 0, 0);"); // Highlight valid cards
                } else {
                    cardView.setStyle("-fx-opacity: 0.5;"); // Dim invalid cards
                }
            });
            cardView.setOnMouseExited(event -> cardView.setStyle("")); // Reset style on exit

            // Add click event to handle card selection
            cardView.setOnMouseClicked(this::handleCardClick);

            // Add the card to the player's box
            playerCardsBox.getChildren().add(cardView);
        }
    }
    private void updateComputerCards() {
        computerCardsBox.getChildren().clear();

        int cardCount = computerHand.size();
        double cardWidth = 70;
        double cardHeight = cardWidth * 1.5;
        double overlapOffset = -20;
        if (cardCount > 7) {
            overlapOffset = -30 + (cardCount - 7) * -5; // Increase overlap as card count grows
        }
        computerCardsBox.setSpacing(overlapOffset); // Set spacing to overlap cards
        computerCardsBox.setAlignment(Pos.CENTER); // Center-align the cards
        for (int i = 0; i < cardCount; i++) {
            ImageView cardBack = new ImageView(new Image(Objects.requireNonNull(getClass().getResource("/Images/UNO_back_Cover.jpeg")).toString()));

            cardBack.setFitWidth(cardWidth); // Fixed card width
            cardBack.setFitHeight(cardHeight); // Fixed card height
            // Add hover effect (for debugging or visual enhancement)
            cardBack.setOnMouseEntered(event -> {
                cardBack.setStyle("-fx-effect: dropshadow(gaussian, blue, 10, 0, 0, 0);"); // Highlight effect
            });
            cardBack.setOnMouseExited(event -> cardBack.setStyle("")); // Reset style on exit
            computerCardsBox.getChildren().add(cardBack); // Add card back to HBox
        }
    }


    private void updateDrawCardButton() {
        Drawcard.setVisible(playerHand.stream().noneMatch(this::isValidCard));
    }

    @FXML
    private void handleCardClick(MouseEvent event) {
        if (!isPlayerTurn) {
            System.out.println("It's not your turn!");
            return;
        }
        ImageView clickedCard = (ImageView) event.getSource();
        int index = playerCardsBox.getChildren().indexOf(clickedCard);
        if (index >= 0) {
            Card card = playerHand.get(index);
            handlePlayerThrowCard(card);
        }
    }
    private void startPlayerTurn() {
        isPlayerTurn = true;
        hasPlayedPowerCard = false; // Reset for the player's new turn
        updateProfileHighlighting();
        updateUI();
    }

    private boolean hasPlayedPowerCard = false; // Track if a power card has been played

    private void handlePlayerThrowCard(Card card) {
        if (!isPlayerTurn) {
            System.out.println("It's not your turn!");
            return;
        }

        // Check if the player is trying to play multiple power cards in one turn
        if (isPowerCard(card) && hasPlayedPowerCard) {
            System.out.println("You cannot play more than one power card in a single turn!");
            return;
        }

        if (isValidCard(card)) {
            // Find the ImageView corresponding to the card
            int cardIndex = playerHand.indexOf(card);
            ImageView cardView = (ImageView) playerCardsBox.getChildren().get(cardIndex);

            // Play animation to move the card to the discard pile
            TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), cardView);
            translate.setToX(discardPileArea.localToScene(0, 0).getX() - cardView.localToScene(0, 0).getX());
            translate.setToY(discardPileArea.localToScene(0, 0).getY() - cardView.localToScene(0, 0).getY());
            translate.setInterpolator(Interpolator.EASE_BOTH);

            // Remove the card after animation
            translate.setOnFinished(event -> {
                playerHand.remove(card);
                discardPile.add(card);
                updateDiscardPile();
                playerCardsBox.getChildren().remove(cardView);

                if (isPowerCard(card)) {
                    hasPlayedPowerCard = true; // Mark that a power card has been played
                }

                // Check for win condition
                if (playerHand.isEmpty()) {
                    endRound();
                } else {
                    boolean specialCardHandled = handleSpecialCard(card, playerHand);
                    updatePlayerCards();

                    if (!specialCardHandled) {
                        handleComputerTurn();
                    }
                }
            });

            translate.play();
        } else {
            System.out.println("Invalid card played: " + card);
        }
    }


    private void handleComputerTurn() {
        isPlayerTurn = false;
        updateProfileHighlighting();
        System.out.println("Computer's turn started.");
        hasPlayedPowerCard = false; // Reset for the computer's turn

        PauseTransition pause = new PauseTransition(Duration.seconds(2));
        pause.setOnFinished(event -> {
            List<Card> validCards = computerHand.stream().filter(this::isValidCard).toList();

            if (validCards.isEmpty()) {
                drawCards(computerHand, 1);
                System.out.println("Computer drew a card.");
                updateComputerCards();
                startPlayerTurn();
            } else {
                Card cardToPlay = null;

                // Ensure only one power card is played
                for (Card card : validCards) {
                    if (isPowerCard(card) && hasPlayedPowerCard) {
                        continue; // Skip additional power cards
                    }

                    cardToPlay = card;

                    if (isPowerCard(card)) {
                        hasPlayedPowerCard = true; // Mark that a power card has been played
                    }

                    break;
                }

                if (cardToPlay != null) {
                    playComputerCard(cardToPlay);
                } else {
                    System.out.println("Computer has no valid cards to play.");
                    startPlayerTurn();
                }
            }
        });
        pause.play();
    }


    private void playComputerCard(Card card) {
        System.out.println("Computer played: " + card);
        ImageView thrownCard = (ImageView) computerCardsBox.getChildren().get(computerHand.indexOf(card));
        TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), thrownCard);
        translate.setToX(discardPileArea.localToScene(0, 0).getX() - thrownCard.localToScene(0, 0).getX());
        translate.setToY(discardPileArea.localToScene(0, 0).getY() - thrownCard.localToScene(0, 0).getY());
        translate.setInterpolator(Interpolator.EASE_BOTH);
        translate.setOnFinished(event -> {

            computerHand.remove(card);
            discardPile.add(card);
            updateDiscardPile();
            updateComputerCards();

            if (computerHand.isEmpty()) {
                System.out.println("Computer wins the game!");
                navigateToLosePage();
            } else {

                boolean specialCardHandled = handleSpecialCard(card, computerHand);
                if (!specialCardHandled) {
                    startPlayerTurn();
                }
            }
        });

        translate.play();
    }

    private String playerCardBack = "/Images/UNO_back_Cover.jpeg";
    private void loadSelectedCard() {
        try (BufferedReader reader = new BufferedReader(new FileReader("selectedPlayerCard.txt"))) {
            String savedPath = reader.readLine();
            if (savedPath != null && !savedPath.isEmpty()) {
                playerCardBack = savedPath;
                System.out.println("Loaded player card back: " + playerCardBack);
            }
        } catch (IOException e) {
            System.out.println("No custom card back found. Using default.");
        }
    }

    private boolean handleSpecialCard(Card card, List<Card> hand) {
        switch (card.value()) {
            case "Skip" -> {
                System.out.println("Skip card played. Opponent's turn is skipped.");
                isPlayerTurn = !isPlayerTurn;
                if (!isPlayerTurn) {
                    handleComputerTurn();
                } else {
                    startPlayerTurn();
                }
                return true;
            }
            case "Draw2" -> {
                System.out.println("Draw2 card played. Opponent must draw 2 cards.");
                drawCards(hand == playerHand ? computerHand : playerHand, 2);
                if (!isPlayerTurn) {
                    handleComputerTurn();
                } else {
                    startPlayerTurn();
                }
                return true;
            }case "Swap" -> {
                handleReverseCard(); // Call the Swap Hand logic
                if (hand == playerHand) {
                    showColorChangeDialog();
                } else {
                    autoSelectColor();
                }
                if (!isPlayerTurn) {
                    handleComputerTurn();
                } else {
                    startPlayerTurn();
                }
                return true;
            }
            case "Draw4" -> {
                System.out.println("Draw4 card played. Opponent must draw 4 cards.");
                drawCards(hand == playerHand ? computerHand : playerHand, 4);
                if (hand == playerHand) {
                    showColorChangeDialog();
                } else {
                    autoSelectColor();
                }
                if (!isPlayerTurn) {
                    handleComputerTurn();
                } else {
                    startPlayerTurn();
                }
                return true;
            }case "Zero" -> {

                shuffleAllHands();
                if (hand == playerHand) {
                    showColorChangeDialog();
                } else {
                    autoSelectColor();
                }
                if (!isPlayerTurn) {
                    handleComputerTurn();
                } else {
                    startPlayerTurn();
                }
                return true;}
            case "Wild" -> {
                System.out.println("Wild card played. Color can be changed.");
                if (hand == playerHand) {
                    showColorChangeDialog();
                } else {
                    autoSelectColor();
                }
                if (!isPlayerTurn) {
                    handleComputerTurn();
                } else {
                    startPlayerTurn();
                }
                return true;
            }
            default -> {
                System.out.println("Normal card played. No special action.");
                return false;
            }
        }
    }
    private void shuffleAllHands() {
        System.out.println("Collecting all cards from players...");

        // Collect all cards from both hands
        List<Card> allCards = new ArrayList<>(playerHand);
        allCards.addAll(computerHand);

        // Clear the current hands
        playerHand.clear();
        computerHand.clear();

        // Animate card collection and shuffle
        animateCardCollection(() -> {
            // Shuffle the collected cards
            Collections.shuffle(allCards);

            // Redistribute the cards, ensuring each hand gets 7 cards
            int totalCards = allCards.size();
            int cardsToDistribute = Math.min(7, totalCards / 2); // Distribute up to 7 cards each if possible

            List<Card> playerNewHand = new ArrayList<>();
            List<Card> computerNewHand = new ArrayList<>();

            for (int i = 0; i < cardsToDistribute * 2; i++) {
                if (i % 2 == 0) {
                    playerNewHand.add(allCards.get(i));
                } else {
                    computerNewHand.add(allCards.get(i));
                }
            }

            // Redistribute remaining cards back to the draw pile
            List<Card> remainingCards = allCards.subList(cardsToDistribute * 2, totalCards);
            drawPile.addAll(remainingCards);

            // Animate redistribution of new hands
            animateCardRedistribution(playerNewHand, computerNewHand, () -> {
                // Assign the new hands
                playerHand.addAll(playerNewHand);
                computerHand.addAll(computerNewHand);

                // Update the UI after redistribution
                updatePlayerCards();
                updateComputerCards();
                updateDrawPile();

                System.out.println("Hands shuffled and redistributed.");
                System.out.println("Player's new hand: " + playerHand);
                System.out.println("Computer's new hand: " + computerHand);
            });
        });
    }


    private void animateCardCollection(Runnable onAnimationComplete) {
        List<ImageView> allCardViews = new ArrayList<>();

        // Collect card views from player and computer hands
        for (Node node : playerCardsBox.getChildren()) {
            if (node instanceof ImageView) {
                allCardViews.add((ImageView) node);
            }
        }
        for (Node node : computerCardsBox.getChildren()) {
            if (node instanceof ImageView) {
                allCardViews.add((ImageView) node);
            }
        }

        // Create animations for all cards
        Timeline timeline = new Timeline();
        for (ImageView cardView : allCardViews) {
            // Add a ColorAdjust effect to make the card appear black
            ColorAdjust colorAdjust = new ColorAdjust();
            colorAdjust.setBrightness(-1.0); // Fully black

            // Apply the color adjustment effect before animation
            cardView.setEffect(colorAdjust);

            // Translate Transition (Move to discard pile)
            TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), cardView);
            translate.setToX(discardPileArea.localToScene(0, 0).getX() - cardView.localToScene(0, 0).getX());
            translate.setToY(discardPileArea.localToScene(0, 0).getY() - cardView.localToScene(0, 0).getY());
            translate.setInterpolator(Interpolator.EASE_BOTH);

            // Rotate Transition (Rotate card during movement)
            RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), cardView);
            rotate.setByAngle(360); // Full rotation

            // Fade Transition (Fade out as it moves)
            FadeTransition fade = new FadeTransition(Duration.seconds(0.5), cardView);
            fade.setFromValue(1.0); // Fully visible
            fade.setToValue(0.0);   // Fully invisible

            // Combine all animations
            ParallelTransition parallelTransition = new ParallelTransition(translate, rotate, fade);
            timeline.getKeyFrames().add(new KeyFrame(Duration.seconds(0.1), e -> parallelTransition.play()));
        }

        // Execute completion task after animation
        timeline.setOnFinished(e -> {
            playerCardsBox.getChildren().clear();
            computerCardsBox.getChildren().clear();
            onAnimationComplete.run();
        });

        timeline.play();
    }


    private void animateCardRedistribution(List<Card> playerNewHand, List<Card> computerNewHand, Runnable onAnimationComplete) {
        List<Card> allCards = new ArrayList<>();
        allCards.addAll(playerNewHand);  // Add player cards
        allCards.addAll(computerNewHand);  // Add computer cards

        Timeline timeline = new Timeline();
        int totalCardsToDistribute = allCards.size();
        int[] cardIndex = {0};  // Track the current card being animated

        // Default UNO back cover image for redistribution
        String cardBackPath = "/Images/UNO_back_Cover.jpeg";

        KeyFrame keyFrame = new KeyFrame(Duration.seconds(0.5), event -> {
            if (cardIndex[0] < totalCardsToDistribute) {
                // Create a new card image using the default back cover
                ImageView cardImage = new ImageView(new Image(Objects.requireNonNull(getClass()
                        .getResource(cardBackPath)).toString()));
                cardImage.setFitWidth(50);
                cardImage.setFitHeight(80);

                // Temporarily add the card to the discard pile
                discardPileArea.getChildren().add(cardImage);

                // Determine the target box (player or computer) based on index
                HBox targetBox = (cardIndex[0] < playerNewHand.size()) ? playerCardsBox : computerCardsBox;

                // Calculate positions
                double startX = discardPileArea.localToScene(0, 0).getX();
                double startY = discardPileArea.localToScene(0, 0).getY();
                double targetX = targetBox.localToScene(0, 0).getX() + 10 * targetBox.getChildren().size();
                double targetY = targetBox.localToScene(0, 0).getY();

                // Create animations
                TranslateTransition translate = new TranslateTransition(Duration.seconds(0.5), cardImage);
                translate.setFromX(0);
                translate.setFromY(0);
                translate.setToX(targetX - startX);
                translate.setToY(targetY - startY);
                translate.setInterpolator(Interpolator.EASE_BOTH);

                RotateTransition rotate = new RotateTransition(Duration.seconds(0.5), cardImage);
                rotate.setByAngle(360);  // Rotate card during movement

                ParallelTransition parallelTransition = new ParallelTransition(translate, rotate);
                parallelTransition.setOnFinished(e -> {
                    // Add the card to the correct hand after animation
                    targetBox.getChildren().add(cardImage);
                    discardPileArea.getChildren().remove(cardImage);  // Remove from discard pile
                });

                // Play the animation
                parallelTransition.play();
                cardIndex[0]++;
            }

            // Call the completion callback after all cards are animated
            if (cardIndex[0] == totalCardsToDistribute) {
                onAnimationComplete.run();
            }
        });

        timeline.getKeyFrames().add(keyFrame);
        timeline.setCycleCount(totalCardsToDistribute);
        timeline.play();
    }

    private void handleReverseCard() {
        System.out.println("Reverse card played! Exchanging hands...");

        // Perform hand exchange with animation
        animateHandExchange(() -> {
            // Swap hands logically after the animation
            List<Card> temp = new ArrayList<>(playerHand);
            playerHand.clear();
            playerHand.addAll(computerHand);

            computerHand.clear();
            computerHand.addAll(temp);

            // Update UI
            updatePlayerCards();
            updateComputerCards();

            System.out.println("Player hand after exchange: " + playerHand);
            System.out.println("Computer hand after exchange: " + computerHand);
        });
    }


    private void animateHandExchange(Runnable onAnimationComplete) {
        System.out.println("Animating hand exchange...");

        // Create rotation animation for player and computer hand exchange
        RotateTransition playerRotation = new RotateTransition(Duration.seconds(0.5), playerCardsBox);
        playerRotation.setByAngle(360); // Full rotation
        playerRotation.setInterpolator(Interpolator.EASE_BOTH);

        RotateTransition computerRotation = new RotateTransition(Duration.seconds(0.5), computerCardsBox);
        computerRotation.setByAngle(360); // Full rotation
        computerRotation.setInterpolator(Interpolator.EASE_BOTH);

        // Play animations simultaneously
        ParallelTransition parallelTransition = new ParallelTransition(playerRotation, computerRotation);
        parallelTransition.setOnFinished(event -> {
            // Call the logic to swap hands after animation
            onAnimationComplete.run();
        });

        parallelTransition.play();
    }


    private void showColorChangeDialog() {
        Platform.runLater(() -> {
            List<String> colors = Arrays.asList("Red", "Blue", "Green", "Yellow");

            // Create a ChoiceDialog
            ChoiceDialog<String> dialog = new ChoiceDialog<>(colors.get(0), colors);
            dialog.setTitle("Choose a Color");
            dialog.setHeaderText("Select a new color for the Wild card");
            dialog.setContentText("Choose:");

            // Style the DialogPane
            dialog.getDialogPane().setStyle(
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

            // Style the buttons in the dialog
            dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(0)).setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #3F51B5, #283593); " +
                            "-fx-background-radius: 10; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 12px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 5px 15px; " +
                            "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.6), 10, 0.5, 0, 0);"
            );

            // Add hover effect to the buttons
            Node button = dialog.getDialogPane().lookupButton(dialog.getDialogPane().getButtonTypes().get(0));
            button.setOnMouseEntered(e -> button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #5C6BC0, #1A237E); " +
                            "-fx-background-radius: 10; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 12px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 5px 15px; " +
                            "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.8), 15, 0.6, 0, 0);"
            ));
            button.setOnMouseExited(e -> button.setStyle(
                    "-fx-background-color: linear-gradient(to bottom, #3F51B5, #283593); " +
                            "-fx-background-radius: 10; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 12px; " +
                            "-fx-font-weight: bold; " +
                            "-fx-padding: 5px 15px; " +
                            "-fx-effect: dropshadow(gaussian, rgba(63, 81, 181, 0.6), 10, 0.5, 0, 0);"
            ));

            // Show dialog and capture result
            Optional<String> result = dialog.showAndWait();
            result.ifPresent(color -> {
                System.out.println("Color changed to: " + color);
                updateDiscardPileColor(color);
            });
        });
    }


    private void updateDiscardPileColor(String color) {
        Card topCard = ((LinkedList<Card>) discardPile).getLast();
        discardPile.add(new Card(color.toUpperCase(), topCard.value()));
        circleIndicator.setVisible(true);
        switch (color.toUpperCase()) {
            case "RED" -> circleIndicator.setStroke(javafx.scene.paint.Color.RED);
            case "BLUE" -> circleIndicator.setStroke(javafx.scene.paint.Color.BLUE);
            case "GREEN" -> circleIndicator.setStroke(javafx.scene.paint.Color.GREEN);
            case "YELLOW" -> circleIndicator.setStroke(javafx.scene.paint.Color.YELLOW);
            default -> circleIndicator.setStroke(javafx.scene.paint.Color.BLACK);
        }

        updateDiscardPile();
    }


    private boolean isValidCard(Card card) {
        if (discardPile.isEmpty()) return true; // If discard pile is empty, any card is valid
        Card topCard = ((LinkedList<Card>) discardPile).getLast();

        // Allow Swap card to be played regardless of color or value
        if (card.value().equalsIgnoreCase("Swap") || card.value().equalsIgnoreCase("Zero")) {
            return true;
        }


        return card.color().equals(topCard.color()) // Match color
                || card.value().equals(topCard.value()) // Match value
                || card.value().equalsIgnoreCase("Wild") // Wild cards
                || card.value().equalsIgnoreCase("Draw4"); // Draw4 cards
    }



    private void playCard(Card card, List<Card> hand) {
        // Remove the played card from the player's hand
        hand.remove(card);

        // Add the card to the discard pile
        discardPile.add(card);
        updateDiscardPile();

        // Handle special card actions
        boolean specialHandled = handleSpecialCard(card, hand);

        // If no special card effect, proceed with the next turn
        if (!specialHandled) {
            if (isPlayerTurn) {
                handleComputerTurn();
            } else {
                startPlayerTurn();
            }
        }
    }

    private void autoSelectColor() {
        List<String> colors = Arrays.asList("Red", "Blue", "Green", "Yellow");
        String selectedColor = colors.get(new Random().nextInt(colors.size()));
        System.out.println("Computer selected color: " + selectedColor);
        updateDiscardPileColor(selectedColor);
    }


    private void drawCards(List<Card> hand, int count) {
        for (int i = 0; i < count; i++) {
            if (!drawPile.isEmpty()) {
                hand.add(drawPile.poll());
            }
        }
        updateUI();
    }

    private void endRound() {
        System.out.println("Round over!");

        if (playerHand.isEmpty()) {
            System.out.println("Player wins!");
            navigateToWinPage();
        } else if (computerHand.isEmpty()) {
            System.out.println("Computer wins!");
            navigateToLosePage();
        } else {
            System.out.println("No winner yet!");
        }
    }

    private void navigateToWinPage() {
        try {
            // Add reward coins to SharedData
            SharedData.addCoins(1000);

            // Load the Win Page (Win.fxml)
            Parent winRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Winpage.fxml")));
            Stage stage = (Stage) Back.getScene().getWindow();
            stage.setScene(new Scene(winRoot));
            stage.show();

            System.out.println("Navigated to Win Page. Coins added: " + 1000);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void navigateToLosePage() {
        try {
            Parent loseRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("GameOver.fxml")));
            Stage stage = (Stage) Back.getScene().getWindow();
            stage.setScene(new Scene(loseRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    public void handleDrawCard(ActionEvent event) {
        if (!isPlayerTurn) {
            System.out.println("It's not your turn!");
            return;
        }
        if (!drawPile.isEmpty()) {
            Card drawnCard = drawPile.poll();

            ImageView cardImage = new ImageView(new Image(Objects.requireNonNull(getClass()
                    .getResource("/Images/UNO_back_Cover.jpeg")).toString()));
            cardImage.setFitWidth(50);
            cardImage.setFitHeight(80);

            animateCardToHand(cardImage, playerCardsBox, () -> {
                playerHand.add(drawnCard);
                System.out.println("Player drew a card: " + drawnCard);
                updateUI();

                if (!isValidCard(drawnCard)) {
                    System.out.println("Drawn card is not playable. Passing turn to the computer.");
                    isPlayerTurn = false;
                    handleComputerTurn();
                }
            });
        } else {
            System.out.println("Draw pile is empty! Cannot draw a card.");
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
    public void handleUnoCallPlayer(ActionEvent event) {
        if (!isPlayerTurn) {
            System.out.println("It's not your turn to call UNO!");
            return;
        }

        if (!isUnoCalled) {
            System.out.println("Player called UNO!");
            isUnoCalled = true;
            playUnoMusic();
        } else {
            System.out.println("UNO already called by the player!");
        }
    }

    private void playUnoMusic() {
        try {
            // Replace "uno_success.mp3" with the actual path of your UNO sound file
            String musicPath = Objects.requireNonNull(getClass().getResource("/Music/UnoSound.mp3")).toString();
            Media media = new Media(musicPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.play();
        } catch (Exception e) {
            System.err.println("Error playing UNO success sound: " + e.getMessage());
            e.printStackTrace();
        }
    }
    public record Card(String color, String value) {}
    @FXML
    public void profile() {
        profileView.setImage(new Image(Objects.requireNonNull(getClass().getResource(
                Objects.requireNonNullElse(SharedData.selectedProfileImagePath, "/Images/icon/Default_icon.jpg"))).toString()));
        profileNameLabel.setText(Objects.requireNonNullElse(SharedData.selectedProfileName, "Player 1"));
    }
    private void updateProfileHighlighting() {
        if (isPlayerTurn) {
            // Highlight the player's profile
            profileView.setStyle("-fx-effect: dropshadow(gaussian, yellow, 20, 0.7, 0, 0);"
                    + "-fx-scale-x: 1.1; -fx-scale-y: 1.1;");
            profileNameLabel.setStyle("-fx-font-weight: bold; -fx-text-fill: yellow;");

            // Reset the computer's profile to normal
            ComputerView.setStyle("-fx-effect: none; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
            ComputerName.setStyle("-fx-font-weight: normal; -fx-text-fill: white;");
        } else {
            // Highlight the computer's profile
            ComputerView.setStyle("-fx-effect: dropshadow(gaussian, red, 20, 0.7, 0, 0);"
                    + "-fx-scale-x: 1.1; -fx-scale-y: 1.1;");
            ComputerName.setStyle("-fx-font-weight: bold; -fx-text-fill: red;");

            // Reset the player's profile to normal
            profileView.setStyle("-fx-effect: none; -fx-scale-x: 1.0; -fx-scale-y: 1.0;");
            profileNameLabel.setStyle("-fx-font-weight: normal; -fx-text-fill: white;");
        }
    }

}