package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageMatrixGUI;

import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

public class Leaderboard {
    public List<Player> players = new ArrayList<>();
    public String leaderboardFile = "C:\\Users\\letic\\OneDrive\\Ambiente de Trabalho\\Git\\Rogue_POO2023_v1_LeticiaCascais\\.idea\\leaderboard\\leaderboard.txt";

    public void loadLeaderboard() {
        List<Player> existingPlayers = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(leaderboardFile))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] data = line.split(",");
                if (data.length == 2) {
                    String username = data[0].trim();
                    int points = Integer.parseInt(data[1].trim());
                    Player player = new Player(username, points);
                    existingPlayers.add(player);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading leaderboard: " + e.getMessage());
        }
        players.addAll(existingPlayers);
    }

    public void addPlayer(String username, int points) {
        Player player = new Player(username, points);
        players.add(player);
        updateLeaderboardFile();
    }

    private void updateLeaderboardFile() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(this.leaderboardFile))) {
            Collections.sort(players, Collections.reverseOrder());
            for (Player player : players) {
                writer.write(player.getUsername() + "," + player.getPoints());
                writer.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error updating leaderboard file: " + e.getMessage());
        }
    }

    public void printLeaderboard() {
        String printLeaderboard = " ";
        for (int i = 0; i < players.size(); i++) {
            Player player = players.get(i);
            printLeaderboard += player.getUsername() + " " + player.getPoints() + " points" + "\r\n";
        }
        ImageMatrixGUI.getInstance().showMessage("Leaderboard", printLeaderboard);
    }
}

class Player implements Comparable<Player> {
    private String username;
    private int points;

    public Player(String username, int points) {
        this.username = username;
        this.points = points;
    }

    public String getUsername() {
        return username;
    }

    public int getPoints() {
        return points;
    }

    @Override
    public int compareTo(Player other) {
        return Integer.compare(points, other.getPoints());
    }
}
