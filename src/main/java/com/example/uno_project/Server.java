package com.example.uno_project;

import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

public class Server {
    public static final CopyOnWriteArrayList<ClientHandler> clients = new CopyOnWriteArrayList<>();
    public static final ConcurrentHashMap<ClientHandler, String> clientProfiles = new ConcurrentHashMap<>();


    private static final String GUILD_NAME_FILE = "guild_name.txt"; // File to store guild name
    private static String guildName = "LIGHT SEEKER"; // Default guild name

    public static void main(String[] args) {
        // Load guild name from file
        loadGuildNameFromFile();

        try (ServerSocket serverSocket = new ServerSocket(12346)) {
            System.out.println("Server started on port 12346...");
            System.out.println("Current Guild Name: " + guildName);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("New client connected: " + clientSocket.getInetAddress());

                // Initialize the client handler and send the guild name
                ClientHandler clientHandler = new ClientHandler(clientSocket);
                clients.add(clientHandler);
                clientHandler.sendMessage("UPDATED_GUILD_NAME:" + guildName); // Send guild name to new client

                // Start the client handler in a new thread
                new Thread(clientHandler).start();
            }
        } catch (Exception e) {
            System.err.println("Server error: " + e.getMessage());
        }
    }

    public static void broadcast(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (sender == null || client != sender) {
                client.sendMessage(message);
            }
        }
    }

    public static synchronized void updateGuildName(String newGuildName) {
        if (newGuildName == null || newGuildName.trim().isEmpty()) {
            throw new IllegalArgumentException("Guild name cannot be empty.");
        }
        guildName = newGuildName;
        saveGuildNameToFile(); // Save to file
        broadcast("UPDATED_GUILD_NAME:" + newGuildName, null); // Broadcast to all clients
    }

    private static void loadGuildNameFromFile() {
        try (BufferedReader reader = new BufferedReader(new FileReader(GUILD_NAME_FILE))) {
            String name = reader.readLine();
            if (name != null && !name.trim().isEmpty()) {
                guildName = name.trim();
            }
        } catch (IOException e) {
            System.out.println("No previous guild name found. Using default.");
        }
    }
    public static void broadcastChat(String message, ClientHandler sender) {
        for (ClientHandler client : clients) {
            if (sender == null || client != sender) {
                client.sendMessage("CHAT:" + message);
            }
        }
        logChatMessage(message); // Log the chat message
    }



    public static void logChatMessage(String message) {
        try (FileWriter fw = new FileWriter("chat_log.txt", true);
             BufferedWriter bw = new BufferedWriter(fw);
             PrintWriter out = new PrintWriter(bw)) {
            out.println(message);
        } catch (IOException e) {
            System.err.println("Failed to log chat message: " + e.getMessage());
        }
    }



    private static void saveGuildNameToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(GUILD_NAME_FILE))) {
            writer.println(guildName);
        } catch (IOException e) {
            System.err.println("Failed to save guild name to file: " + e.getMessage());
        }
    }
}
