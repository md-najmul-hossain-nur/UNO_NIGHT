package com.example.uno_project;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.io.*;
import java.util.Objects;
public class Buy {
    @FXML
    private Rectangle Area;
    @FXML
    private Rectangle Area2;
    @FXML
    private Button Back;

    @FXML
    private VBox BuyCard1;

    @FXML
    private ImageView BuyCard1Image;

    @FXML
    private VBox BuyCard2;

    @FXML
    private ImageView BuyCard2Image;

    @FXML
    private VBox BuyCard3;

    @FXML
    private ImageView BuyCard3Image;

    @FXML
    private VBox BuyCard4;

    @FXML
    private ImageView BuyCard4Image;

    @FXML
    private VBox BuyCard5;

    @FXML
    private ImageView BuyCard5Image;

    @FXML
    private Label Buycard1coin;

    @FXML
    private Label Buycard2coin;

    @FXML
    private Label Buycard3coin;

    @FXML
    private Label Buycard4coin;

    @FXML
    private Label Buycard5coin;

    @FXML
    private Button CardBuy1;

    @FXML
    private Button CardBuy2;

    @FXML
    private Button CardBuy3;

    @FXML
    private Button CardBuy4;

    @FXML
    private Button CardBuy5;

    @FXML
    private HBox CardHbox;

    @FXML
    private Button ChooseCard1;

    @FXML
    private Button ChooseCard2;

    @FXML
    private Button ChooseCard3;

    @FXML
    private Button ChooseCard4;

    @FXML
    private Button ChooseCard5;

    @FXML
    private Button ChooseMusic1;

    @FXML
    private Button ChooseMusic2;

    @FXML
    private Button ChooseMusic3;

    @FXML
    private ImageView Music1;

    @FXML
    private ImageView Music2;

    @FXML
    private ImageView Music3;

    @FXML
    private Button MusicBuy1;

    @FXML
    private Button MusicBuy2;

    @FXML
    private Button MusicBuy3;

    @FXML
    private HBox MusicHbox;
    @FXML
    private Rectangle Area3;
    @FXML
    private Rectangle Area4;
    @FXML
    private Rectangle Area10;

    @FXML
    private Rectangle Area11;
    @FXML
    private Rectangle Area5;

    @FXML
    private Rectangle Area6;

    @FXML
    private Rectangle Area7;

    @FXML
    private Rectangle Area8;

    @FXML
    private Rectangle Area9;

    @FXML
    private VBox Musiccard1;

    @FXML
    private Label Musiccoin1;

    @FXML
    private Label Musiccoin2;

    @FXML
    private Label Musiccoin3;
    @FXML
    private Rectangle area;
    @FXML
    private HBox buycard1coin;
    private MediaPlayer musicPreviewPlayer;
    @FXML
    private Label coinLabel;
    @FXML
    public void initialize() {
        handleMusicHover(); // Setup hover for music previews

        // Styling for visual elements
        Area.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area.setStroke(javafx.scene.paint.Color.BLACK);
        Area.setStrokeWidth(2);
        updateCoinLabel();
        area.setFill(javafx.scene.paint.Color.TRANSPARENT);
        area.setStroke(Color.ORANGE);
        area.setStrokeWidth(2);
        Area4.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area4.setStroke(javafx.scene.paint.Color.WHITE);
        Area4.setStrokeWidth(2);
        Area5.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area5.setStroke(javafx.scene.paint.Color.WHITE);
        Area5.setStrokeWidth(2);
        Area6.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area6.setStroke(javafx.scene.paint.Color.WHITE);
        Area6.setStrokeWidth(2);
        Area7.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area7.setStroke(javafx.scene.paint.Color.WHITE);
        Area7.setStrokeWidth(2);
        Area8.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area8.setStroke(javafx.scene.paint.Color.WHITE);
        Area8.setStrokeWidth(2);
        Area9.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area9.setStroke(javafx.scene.paint.Color.WHITE);
        Area9.setStrokeWidth(2);
        Area10.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area10.setStroke(javafx.scene.paint.Color.WHITE);
        Area10.setStrokeWidth(2);
        Area11.setFill(javafx.scene.paint.Color.TRANSPARENT);
        Area11.setStroke(javafx.scene.paint.Color.WHITE);
        Area11.setStrokeWidth(2);
        // Hide Choose buttons initially
        ChooseCard1.setVisible(false);
        ChooseCard2.setVisible(false);
        ChooseCard3.setVisible(false);
        ChooseCard4.setVisible(false);
        ChooseCard5.setVisible(false);
        ChooseMusic1.setVisible(false);
        ChooseMusic2.setVisible(false);
        ChooseMusic3.setVisible(false);

        // Load claimed card and music paths
        String claimedCard = loadSelectedCardBack();
        String claimedMusic = loadSelectedMusic();


        // Check claimed cards
        if (claimedCard.equals("/Images/SHOP_CARD1.jpg")) {
            CardBuy1.setText("Claimed");
            CardBuy1.setStyle("-fx-font-weight: bold; -fx-text-fill: #007BFF;");
            CardBuy1.setDisable(true);
            ChooseCard1.setVisible(false);
        }
        if (claimedCard.equals("/Images/SHOP_CARD2.jpg")) {
            CardBuy2.setText("Claimed");
            CardBuy2.setStyle("-fx-font-weight: bold; -fx-text-fill: #007BFF;");
            CardBuy2.setDisable(true);
            ChooseCard2.setVisible(false);
        }
        if (claimedCard.equals("/Images/SHOP_CARD3.jpg")) {
            CardBuy3.setText("Claimed");
            CardBuy3.setStyle("-fx-font-weight: bold; -fx-text-fill: #007BFF;");
            CardBuy3.setDisable(true);
            ChooseCard3.setVisible(false);
        }
        if (claimedCard.equals("/Images/SHOP_CARD4.jpg")) {
            CardBuy4.setText("Claimed");
            CardBuy4.setStyle("-fx-font-weight: bold; -fx-text-fill: #007BFF;");
            CardBuy4.setDisable(true);
            ChooseCard4.setVisible(false);
        }
        if (claimedCard.equals("/Images/SHOP_CARD5.jpg")) {
            CardBuy5.setText("Claimed");
            CardBuy5.setStyle("-fx-font-weight: bold; -fx-text-fill: #007BFF;");
            CardBuy5.setDisable(true);
            ChooseCard5.setVisible(false);
        }
        // Check claimed music
        if (claimedMusic.equals("/Music/OddoutMusic.mp3")) {
            MusicBuy1.setText("Claimed");
            MusicBuy1.setStyle("-fx-font-weight: bold; -fx-text-fill: #FF5733;");
            MusicBuy1.setDisable(true);
            ChooseMusic1.setVisible(false);
        }
        if (claimedMusic.equals("/Music/ShopMusic.mp3")) {
            MusicBuy2.setText("Claimed");
            MusicBuy2.setStyle("-fx-font-weight: bold; -fx-text-fill: #FF5733;");
            MusicBuy2.setDisable(true);
            ChooseMusic2.setVisible(false);
        }
        if (claimedMusic.equals("/Music/Ratio.mp3")) {
            MusicBuy3.setText("Claimed");
            MusicBuy3.setStyle("-fx-font-weight: bold; -fx-text-fill: #FF5733;");
            MusicBuy3.setDisable(true);
            ChooseMusic3.setVisible(false);
        }
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
        CardBuy1.setStyle(computerButtonBaseStyle);
        CardBuy1.setOnMouseEntered(e -> CardBuy1.setStyle(computerButtonHoverStyle));
        CardBuy1.setOnMouseExited(e -> CardBuy1.setStyle(computerButtonBaseStyle));

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
        CardBuy2.setStyle(localPlayerButtonBaseStyle);
        CardBuy2.setOnMouseEntered(e -> CardBuy2.setStyle(localPlayerButtonHoverStyle));
        CardBuy2.setOnMouseExited(e -> CardBuy2.setStyle(localPlayerButtonBaseStyle));

        String ButtonBaseStyle = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String ButtonHoverStyle = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        CardBuy4.setStyle(computerButtonBaseStyle);
        CardBuy4.setOnMouseEntered(e -> CardBuy4.setStyle(ButtonHoverStyle));
        CardBuy4.setOnMouseExited(e -> CardBuy4.setStyle(ButtonBaseStyle));
        String BaseStyle = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String HoverStyle = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        CardBuy5.setStyle(computerButtonBaseStyle);
        CardBuy5.setOnMouseEntered(e -> CardBuy5.setStyle(HoverStyle));
        CardBuy5.setOnMouseExited(e -> CardBuy5.setStyle(BaseStyle));
        String Style = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String sStyle = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        MusicBuy1.setStyle(computerButtonBaseStyle);
        MusicBuy1.setOnMouseEntered(e -> MusicBuy1.setStyle(Style));
        MusicBuy1.setOnMouseExited(e -> MusicBuy1.setStyle(sStyle));
        String computerStyle = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String computersStyle = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        MusicBuy2.setStyle(computerButtonBaseStyle);
        MusicBuy2.setOnMouseEntered(e -> MusicBuy2.setStyle(computerStyle));
        MusicBuy2.setOnMouseExited(e -> MusicBuy2.setStyle(computersStyle));
        String computerButton = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String computerButtonHover = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        MusicBuy3.setStyle(computerButtonBaseStyle);
        MusicBuy3.setOnMouseEntered(e -> CardBuy1.setStyle(computerButton));
        MusicBuy3.setOnMouseExited(e -> CardBuy1.setStyle(computerButtonHover));


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
        CardBuy3.setStyle(buyButtonBaseStyle);

        // Add hover effects to Buy Button
        CardBuy3.setOnMouseEntered(e -> CardBuy3.setStyle(buyButtonHoverStyle));
        CardBuy3.setOnMouseExited(e -> CardBuy3.setStyle(buyButtonBaseStyle));

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
        Back.setStyle(settingButtonBaseStyle);

        // Add hover effects to Setting Button
        Back.setOnMouseEntered(e -> Back.setStyle(settingButtonHoverStyle));
        Back.setOnMouseExited(e -> Back.setStyle(settingButtonBaseStyle));
        String computerButtonBaseStyl = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String computerButtonHoverStl = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        ChooseCard1.setStyle(computerButtonBaseStyle);
        ChooseCard1.setOnMouseEntered(e -> ChooseCard1.setStyle(computerButtonHoverStl));
        ChooseCard1.setOnMouseExited(e -> ChooseCard1.setStyle(computerButtonBaseStyl));

        String BaseSstyle = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String computeronHoverStyle = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        ChooseCard2.setStyle(computerButtonBaseStyle);
        ChooseCard2.setOnMouseEntered(e -> ChooseCard2.setStyle(computeronHoverStyle));
        ChooseCard2.setOnMouseExited(e -> ChooseCard2.setStyle(BaseSstyle));
        String computer = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String HoverSyle = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        ChooseCard3.setStyle(computerButtonBaseStyle);
        ChooseCard3.setOnMouseEntered(e -> ChooseCard3.setStyle(HoverSyle));
        ChooseCard3.setOnMouseExited(e -> ChooseCard3.setStyle(computer));
        String computBaseStyle = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String componHoverStyle = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        ChooseCard4.setStyle(computerButtonBaseStyle);
        ChooseCard4.setOnMouseEntered(e -> ChooseCard4.setStyle(componHoverStyle));
        ChooseCard4.setOnMouseExited(e -> ChooseCard4.setStyle(computBaseStyle));
        String computerBute = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String computStyle = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        ChooseCard5.setStyle(computerButtonBaseStyle);
        ChooseCard5.setOnMouseEntered(e -> ChooseCard5.setStyle(computStyle));
        ChooseCard5.setOnMouseExited(e -> ChooseCard5.setStyle(computerBute));
        String comtyle = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String computeBurttonHoverStyle = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        ChooseMusic1.setStyle(computerButtonBaseStyle);
        ChooseMusic1.setOnMouseEntered(e -> ChooseMusic1.setStyle(computeBurttonHoverStyle));
        ChooseMusic1.setOnMouseExited(e -> ChooseMusic1.setStyle(comtyle));
        String coyle = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String computere = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        ChooseMusic2.setStyle(computerButtonBaseStyle);
        ChooseMusic2.setOnMouseEntered(e -> ChooseMusic2.setStyle(coyle));
        ChooseMusic2.setOnMouseExited(e -> ChooseMusic2.setStyle(computere));
        String coyl = "-fx-background-color: linear-gradient(to bottom, #4CAF50, #2E7D32); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.6), 15, 0.5, 0, 0);";

        // Hover style for the Computer Button
        String computr = "-fx-background-color: linear-gradient(to bottom, #66BB6A, #1B5E20); " +
                "-fx-background-radius: 25; " +
                "-fx-text-fill: white; " +
                "-fx-font-size: 18px; " +
                "-fx-font-weight: bold; " +
                "-fx-effect: dropshadow(gaussian, rgba(76, 175, 80, 0.9), 20, 0.8, 0, 0);";

        // Apply styles to the Computer Button
        ChooseMusic3.setStyle(computerButtonBaseStyle);
        ChooseMusic3.setOnMouseEntered(e -> ChooseMusic3.setStyle(coyl));
        ChooseMusic3.setOnMouseExited(e -> ChooseMusic3.setStyle(computr));


        applyButtonStyle(CardBuy1, buttonStyle, hoverStyle);
        applyButtonStyle(CardBuy2, buttonStyle, hoverStyle);
        applyButtonStyle(Back,buttonStyle,hoverStyle);
        applyButtonStyle(CardBuy3,buttonStyle,hoverStyle);
        applyButtonStyle(CardBuy4,buttonStyle,hoverStyle);
        applyButtonStyle(CardBuy5,buttonStyle,hoverStyle);
        applyButtonStyle(MusicBuy1,buttonStyle,hoverStyle);
        applyButtonStyle(MusicBuy2,buttonStyle,hoverStyle);
        applyButtonStyle(MusicBuy3,buttonStyle,hoverStyle);
        applyButtonStyle(ChooseMusic3,buttonStyle,hoverStyle);
        applyButtonStyle(ChooseMusic2,buttonStyle,hoverStyle);
        applyButtonStyle(ChooseMusic1,buttonStyle,hoverStyle);
        applyButtonStyle(ChooseCard1,buttonStyle,hoverStyle);
        applyButtonStyle(ChooseCard2,buttonStyle,hoverStyle);
        applyButtonStyle(ChooseCard3,buttonStyle,hoverStyle);
        applyButtonStyle(ChooseCard4,buttonStyle,hoverStyle);
        applyButtonStyle(ChooseCard5,buttonStyle,hoverStyle);

    }

    // Method to apply style and hover effect to a button
    private void applyButtonStyle(Button button, String normalStyle, String hoverStyle) {
        button.setStyle(normalStyle);

        // Add hover effect
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle)); // Change to hover style
        button.setOnMouseExited(e -> button.setStyle(normalStyle));



    }

    @FXML
    void handleBuyItem(String itemName, Label coinLabel, Button buyButton, Button chooseButton) {
        int price = SharedData.getCardPrice(itemName); // Get the price from buy.txt

        if (price == -1) {
            System.out.println(itemName + " price not found in buy.txt!");
            return;
        }
        if (SharedData.getPlayerCoins() >= price) {
            SharedData.addCoins(-price); // Deduct the price from player's coins
            updateCoinLabel(); // Update the coin label
            buyButton.setVisible(false); // Hide the buy button
            chooseButton.setVisible(true); // Show the choose button
            System.out.println(itemName + " purchased successfully.");

            // Play success sound and show dialog
            showDialog("Purchase Successful", "You have successfully purchased " + "!", "/Music/CelebrationSound.mp3");
        } else {
            System.out.println("Not enough coins to purchase " + itemName + ".");

            // Play failure sound and show dialog (optional)
            showDialog("Purchase Failed", "You do not have enough coins to purchase. " + " ", "/Music/ErrorSound.mp3");
        }
    }

    private String loadSelectedMusic() {
        try (BufferedReader reader = new BufferedReader(new FileReader("selectedMusic.txt"))) {
            String savedMusicPath = reader.readLine();
            if (savedMusicPath != null && !savedMusicPath.isEmpty()) {
                System.out.println("Loaded selected music: " + savedMusicPath);
                return savedMusicPath; // Return the saved music path
            }
        } catch (IOException e) {
            System.out.println("No selected music found or an error occurred while reading the file.");
        }
        return ""; // Return an empty string if no music is saved
    }

    private void showDialog(String title, String message, String soundPath) {
        MediaPlayer dialogMediaPlayer = null;

        try {
            // Play dialog-specific sound without affecting background music
            String soundFilePath = Objects.requireNonNull(getClass().getResource(soundPath)).toString();
            Media sound = new Media(soundFilePath);
            dialogMediaPlayer = new MediaPlayer(sound);
            dialogMediaPlayer.setVolume(0.7); // Adjust volume if needed
            dialogMediaPlayer.play(); // Play dialog sound
        } catch (Exception e) {
            System.err.println("Error playing dialog sound: " + e.getMessage());
        }

        // Show the dialog box
        javafx.scene.control.Alert alert = new javafx.scene.control.Alert(javafx.scene.control.Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);

        // Add style to the content text
        Label contentLabel = new Label(message);
        contentLabel.setStyle("-fx-font-size: 18px; -fx-text-fill: #333333; -fx-font-weight: bold;");

        // Add style to the dialog pane
        alert.getDialogPane().setContent(contentLabel);
        alert.getDialogPane().setStyle("-fx-background-color: #f0f0f0; -fx-border-color: #ff8500; -fx-border-width: 2px;");

        // Show and wait for the dialog to close
        alert.showAndWait();

        // Stop dialog-specific sound after dialog box closes
        if (dialogMediaPlayer != null) {
            dialogMediaPlayer.stop();
        }

        // Home music will continue to play without any interruption
    }




    private void saveSelectedMusic(String musicPath) {
        try (BufferedWriter writer = new BufferedWriter(new java.io.FileWriter("selectedMusic.txt"))) {
            writer.write(musicPath); // Write the music path to the file
            System.out.println("Music path saved: " + musicPath);
        } catch (IOException e) {
            System.out.println("An error occurred while saving the music path.");
            e.printStackTrace();
        }
    }

    private void updateCoinLabel() {
        coinLabel.setText(String.valueOf(SharedData.getPlayerCoins()));
    }
    static {
        try {

            String musicPath = Objects.requireNonNull(SharedData.class.getResource("/Music/HomeGame.mp3")).toExternalForm();
            Media media = new Media(musicPath);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        } catch (Exception e) {
            System.out.println("Error initializing MediaPlayer: " + e.getMessage());
        }
    }


    @FXML
    private void handleMusicHover() {
        setupMusicHover(Music1, "/Music/OddoutMusic.mp3");
        setupMusicHover(Music2, "/Music/ShopMusic.mp3");
        setupMusicHover(Music3, "/Music/Ratio.mp3");
    }


    private void setupMusicHover(ImageView musicImageView, String musicPath) {
        // Mouse Entered: Play preview and apply highlight effect
        musicImageView.setOnMouseEntered(event -> {
            playMusicPreview(musicPath);
            System.out.println("Hovering over music - Playing preview: " + musicPath);

            // Apply highlight effect (e.g., scale up and add a border)
            musicImageView.setScaleX(1.2); // Scale up by 20%
            musicImageView.setScaleY(1.2);
            musicImageView.setStyle("-fx-effect: dropshadow(gaussian, red, 15, 0.7, 0, 0);");
        });

        // Mouse Exited: Stop preview and remove highlight effect
        musicImageView.setOnMouseExited(event -> {
            stopMusicPreview();
            System.out.println("Mouse left music - Stopping preview.");

            // Remove highlight effect (reset to normal size and style)
            musicImageView.setScaleX(1.0); // Reset to original size
            musicImageView.setScaleY(1.0);
            musicImageView.setStyle(""); // Remove the drop shadow
        });
    }


    private void playMusicPreview(String musicPath) {
        try {
            if (musicPreviewPlayer != null) {
                musicPreviewPlayer.stop(); // Stop any currently playing preview
            }
            String path = Objects.requireNonNull(getClass().getResource(musicPath)).toExternalForm(); // Use the provided musicPath
            Media music = new Media(path);
            musicPreviewPlayer = new MediaPlayer(music);
            musicPreviewPlayer.play(); // Play the new preview
        } catch (Exception e) {
            System.err.println("Error playing music preview: " + e.getMessage());
        }
    }
    private void stopMusicPreview() {
        if (musicPreviewPlayer != null) {
            musicPreviewPlayer.stop(); // Stop the current preview
            musicPreviewPlayer = null; // Reset the player
        }
    }


    @FXML
    void handleBuyCard1(ActionEvent event) {
        handleBuyItem("Card1", Buycard1coin, CardBuy1, ChooseCard1);
    }

    @FXML
    void handleBuyCard2(ActionEvent event) {
        handleBuyItem("Card2", Buycard2coin, CardBuy2, ChooseCard2);
    }

    @FXML
    void handleBuyCard3(ActionEvent event) {
        handleBuyItem("Card3", Buycard3coin, CardBuy3, ChooseCard3);
    }

    @FXML
    void handleBuyCard4(ActionEvent event) {
        handleBuyItem("Card4", Buycard4coin, CardBuy4, ChooseCard4);
    }

    @FXML
    void handleBuyCard5(ActionEvent event) {
        handleBuyItem("Card5", Buycard5coin, CardBuy5, ChooseCard5);
    }

    @FXML
    void handleBuyMusic1(ActionEvent event) {
        handleBuyItem("Music1", Musiccoin1, MusicBuy1, ChooseMusic1);
        saveSelectedMusic("/Music/OddoutMusic.mp3"); // Save the selected music path
    }

    @FXML
    void handleBuyMusic2(ActionEvent event) {
        handleBuyItem("Music2", Musiccoin2, MusicBuy2, ChooseMusic2);
        saveSelectedMusic("/Music/ShopMusic.mp3"); // Save the selected music path
    }

    @FXML
    void handleBuyMusic3(ActionEvent event) {
        handleBuyItem("Music3", Musiccoin3, MusicBuy3, ChooseMusic3);
        saveSelectedMusic("/Music/Ratio.mp3"); // Save the selected music path
    }

    // Save selected card
    private void saveSelectedCard(String cardPath) {
        try (FileWriter writer = new FileWriter("selectedPlayerCard.txt")) {
            writer.write(cardPath);
            System.out.println("Selected player card saved: " + cardPath);
        } catch (IOException e) {
            System.err.println("Error saving selected player card: " + e.getMessage());
        }
    }

    @FXML
    void handleChooseCard1(ActionEvent event) {
        saveSelectedCard("/Images/SHOP_CARD1.jpg");
        System.out.println("SHOP_CARD1 chosen as back cover.");
    }

    @FXML
    void handleChooseCard2(ActionEvent event) {
        saveSelectedCard("/Images/SHOP_CARD2.jpg");
        System.out.println("SHOP_CARD2 chosen as back cover.");
    }

    @FXML
    void handleChooseCard3(ActionEvent event) {
        saveSelectedCard("/Images/SHOP_CARD3.jpg");
        System.out.println("SHOP_CARD3 chosen as back cover.");
    }

    @FXML
    void handleChooseCard4(ActionEvent event) {
        saveSelectedCard("/Images/SHOP_CARD4.jpg");
        System.out.println("SHOP_CARD4 chosen as back cover.");
    }

    @FXML
    void handleChooseCard5(ActionEvent event) {
        saveSelectedCard("/Images/SHOP_CARD5.jpg");
        System.out.println("SHOP_CARD5 chosen as back cover.");
    }


    // Load selected card
    private String loadSelectedCardBack() {
        try (BufferedReader reader = new BufferedReader(new FileReader("selectedPlayerCard.txt"))) {
            String savedPath = reader.readLine();
            if (savedPath != null && !savedPath.isEmpty()) {
                System.out.println("Loaded claimed card: " + savedPath);
                return savedPath;
            }
        } catch (IOException e) {
            System.out.println("No claimed card found.");
        }
        return ""; // Return an empty string if no card is claimed
    }



    @FXML
    void Backhandle(ActionEvent event) {
        try {
            Parent homeRoot = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("Home.fxml")));
            Stage stage = (Stage) Back.getScene().getWindow();
            stage.setScene(new Scene(homeRoot));
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
