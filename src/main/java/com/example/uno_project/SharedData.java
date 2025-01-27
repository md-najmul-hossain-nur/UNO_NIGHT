package com.example.uno_project;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

import java.io.*;
import java.util.Objects;

public class SharedData {
    // Game state variables
    public static boolean isMusicOn = true;
    public static String selectedProfileName = "GUEST";
    public static String selectedProfileImagePath = "/Images/icon/Default_icon.jpg";
    private static final String PLAYER_COINS_FILE = "player_coins.txt";
    private static final String BUY_FILE = "buy.txt";
    private static final String REWARD_STATE_FILE = "reward_state.txt";

    public static MediaPlayer mediaPlayer;
    private static final String VOLUME_STATE_FILE = "volume_state.txt";

    // Load the saved volume from file
    public static double loadVolume() {
        File file = new File(VOLUME_STATE_FILE);
        if (!file.exists()) {
            saveVolume(50); // Default volume: 50%
            return 0.5; // Default volume in MediaPlayer scale (0.5 for 50%)
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String volumeValue = reader.readLine();
            return (volumeValue != null && !volumeValue.trim().isEmpty()) ? Double.parseDouble(volumeValue) / 100 : 0.5;
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error loading volume from file: " + e.getMessage());
            return 0.5; // Default volume
        }
    }
    // Update the global volume and save it
    public static void updateGlobalVolume(double volume) {
        if (mediaPlayer != null) {
            mediaPlayer.setVolume(volume); // Set volume in MediaPlayer
        }
        saveVolume(volume); // Save volume to file
    }

    // Load the global volume when the application starts
    public static double getGlobalVolume() {
        return loadVolume(); // Load the saved volume
    }

    // Save the current volume to file
    public static void saveVolume(double volume) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(VOLUME_STATE_FILE))) {
            writer.write(String.valueOf((int) (volume * 100))); // Save as percentage
        } catch (IOException e) {
            System.err.println("Error saving volume to file: " + e.getMessage());
        }
    }


    // Coins and reward state variables
    private static int playerCoins = 0;
    private static boolean profileSaveRewardGiven = false;

    static {
        try {
            System.out.println("Initializing SharedData...");

            loadCoinsFromFile();
            loadRewardStateFromFile();
            initializeMediaPlayer();
            ensureBuyDataInitialized();

            System.out.println("SharedData initialized successfully.");
        } catch (Exception e) {
            System.err.println("Error during initialization: " + e.getMessage());
        }
    }

    // Retrieves the price of a card or music
    public static int getCardPrice(String cardName) {
        ensureBuyDataInitialized();

        try (BufferedReader reader = new BufferedReader(new FileReader(BUY_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data[0].equalsIgnoreCase(cardName)) {
                    return Integer.parseInt(data[1]);
                }
            }
        } catch (IOException | NumberFormatException e) {
            System.err.println("Error reading buy file: " + e.getMessage());
        }
        return -1; // Return -1 if item not found
    }

    private static void ensureBuyDataInitialized() {
        File file = new File(BUY_FILE);
        if (!file.exists()) {
            saveDefaultBuyData();
        }
    }

    // Saves default card and music prices to the buy.txt file
    private static void saveDefaultBuyData() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(BUY_FILE))) {
            writer.write("Card1,1000\n");
            writer.write("Card2,5000\n");
            writer.write("Card3,7000\n");
            writer.write("Card4,10000\n");
            writer.write("Card5,20000\n");
            writer.write("Music1,25000\n");
            writer.write("Music2,3000\n");
            writer.write("Music3,10000\n");
            System.out.println("Default buy data saved to buy.txt");
        } catch (IOException e) {
            System.err.println("Error saving default buy data: " + e.getMessage());
        }
    }

    // Media player initialization
    private static void initializeMediaPlayer() {
        try {
            String musicPath = Objects.requireNonNull(SharedData.class.getResource("/Music/HomeGame.mp3")).toExternalForm();
            Media media = new Media(musicPath);
            mediaPlayer = new MediaPlayer(media);
            mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);

            // Set initial volume based on saved value
            double savedVolume = getGlobalVolume(); // Load saved volume
            mediaPlayer.setVolume(savedVolume);
        } catch (Exception e) {
            System.err.println("Error initializing MediaPlayer: " + e.getMessage());
        }
    }


    // Reward state methods
    public static boolean isProfileSaveRewardGiven() {
        return profileSaveRewardGiven;
    }

    public static void setProfileSaveRewardGiven(boolean rewarded) {
        profileSaveRewardGiven = rewarded;
        saveRewardStateToFile();
    }

    // Player coins methods
    public static int getPlayerCoins() {
        return playerCoins;
    }

    public static void addCoins(int coins) {
        playerCoins += coins;
        saveCoinsToFile();
    }
    public static void resumePlayback() {
        if (mediaPlayer != null) {
            mediaPlayer.play();
        }
    }

    public void playBackgroundMusic() {
        if (SharedData.mediaPlayer != null) {
            SharedData.mediaPlayer.setVolume(SharedData.getGlobalVolume()); // Ensure correct volume
            SharedData.mediaPlayer.play();
        }
    }

    private static void loadCoinsFromFile() {
        File file = new File(PLAYER_COINS_FILE);
        if (!file.exists()) {
            playerCoins = 0;
            saveCoinsToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String coinValue = reader.readLine();
            playerCoins = (coinValue != null && !coinValue.trim().isEmpty()) ? Integer.parseInt(coinValue) : 0;
        } catch (IOException | NumberFormatException e) {
            playerCoins = 0;
            System.err.println("Error loading coins from file: " + e.getMessage());
        }
    }

    private static void saveCoinsToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(PLAYER_COINS_FILE))) {
            writer.write(String.valueOf(playerCoins));
        } catch (IOException e) {
            System.err.println("Error saving coins to file: " + e.getMessage());
        }
    }

    private static void loadRewardStateFromFile() {
        File file = new File(REWARD_STATE_FILE);
        if (!file.exists()) {
            saveRewardStateToFile();
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String rewardValue = reader.readLine();
            profileSaveRewardGiven = Boolean.parseBoolean(rewardValue);
        } catch (IOException e) {
            profileSaveRewardGiven = false;
            System.err.println("Error loading reward state: " + e.getMessage());
        }
    }

    private static void saveRewardStateToFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(REWARD_STATE_FILE))) {
            writer.write(String.valueOf(profileSaveRewardGiven));
        } catch (IOException e) {
            System.err.println("Error saving reward state to file: " + e.getMessage());
        }
    }

    // Play button sound
    public static void playButtonSound() {
        try {
            String buttonSoundPath = Objects.requireNonNull(SharedData.class.getResource("/Music/alu.mp3")).toExternalForm();
            Media buttonSound = new Media(buttonSoundPath);
            MediaPlayer buttonSoundPlayer = new MediaPlayer(buttonSound);

            buttonSoundPlayer.setOnReady(buttonSoundPlayer::play);
            buttonSoundPlayer.setOnEndOfMedia(buttonSoundPlayer::dispose);
        } catch (Exception e) {
            System.err.println("Error playing button sound: " + e.getMessage());
        }
    }
}