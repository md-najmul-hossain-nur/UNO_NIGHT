package com.example.uno_project;

import java.io.*;
import java.net.*;

public class ClientHandler implements Runnable {
    private final Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    private String username;

    public ClientHandler(Socket socket) {
        this.socket = socket;
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("Error initializing streams: " + e.getMessage());
        }
    }

    @Override
    public void run() {
        try {
            String message;
            while ((message = in.readLine()) != null) {
                System.out.println("Received message from " + socket.getInetAddress() + ": " + message);

                // Register the username
                if (message.startsWith("USERNAME:")) {
                    username = message.substring(9).trim();
                    if (!username.isEmpty()) {
                        Server.clientProfiles.put(this, username); // Store username in server's clientProfiles map
                        System.out.println("Registered username: " + username);
                    }
                }
                // Handle guild name updates
                else if (message.startsWith("GUILD_NAME:")) {
                    String newGuildName = message.substring(11).trim();
                    if (!newGuildName.isEmpty()) {
                        Server.updateGuildName(newGuildName); // Update guild name
                        System.out.println("Guild name updated to: " + newGuildName);
                    } else {
                        sendMessage("ERROR: Guild name cannot be empty.");
                    }
                }
                // Handle animation commands
                else if (message.startsWith("ANIMATE:")) {
                    sendMessage(message); // Forward animation commands to the client
                }
                // Handle active members request
                else if (message.equals("GET_ACTIVE_MEMBERS")) {
                    // Generate a list of currently connected members
                    StringBuilder membersList = new StringBuilder();
                    synchronized (Server.clientProfiles) {
                        for (String name : Server.clientProfiles.values()) {
                            membersList.append(name).append(", ");
                        }
                    }
                    if (!membersList.isEmpty()) {
                        membersList.setLength(membersList.length() - 2); // Remove trailing comma and space
                    }
                    // Send the active members list to the requesting client
                    sendMessage("ACTIVE_MEMBERS:" + membersList);
                }
                // Broadcast chat messages
                if (message.startsWith("CHAT:")) {
                    String chatMessage = message.substring(5).trim(); // Remove "CHAT:" prefix
                    if (!chatMessage.isEmpty()) {
                        Server.broadcastChat(chatMessage, this); // Broadcast chat to all clients
                    }
                }
                // Handle other unknown messages
                else {
                    Server.broadcast(message, this);
                }
            }
        } catch (SocketException e) {
            System.err.println("Client disconnected unexpectedly: " + socket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Error handling client: " + e.getMessage());
        } finally {
            cleanup();
        }
    }


    public void sendMessage(String message) {
        try {
            if (out != null) {
                out.println(message);
                out.flush(); // Ensure immediate transmission
                System.out.println("Message sent to client (" + socket.getInetAddress() + "): " + message);
            } else {
                System.err.println("Cannot send message, output stream is null for: " + socket.getInetAddress());
            }
        } catch (Exception e) {
            System.err.println("Error sending message to client (" + socket.getInetAddress() + "): " + e.getMessage());
        }
    }

    public InetAddress getSocketAddress() {
        return socket.getInetAddress();
    }

    public void cleanup() {
        try {
            if (in != null) in.close();
            if (out != null) out.close();
            if (socket != null && !socket.isClosed()) socket.close();

            // Remove client from server lists
            Server.clients.remove(this);
            Server.clientProfiles.remove(this);
            System.out.println("Client disconnected and removed: " + socket.getInetAddress());
        } catch (IOException e) {
            System.err.println("Error during cleanup: " + e.getMessage());
        }
    }


}
