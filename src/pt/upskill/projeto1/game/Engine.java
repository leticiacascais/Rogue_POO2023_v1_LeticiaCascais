package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.rogue.utils.Position;

import java.awt.event.KeyEvent;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Engine {
    ImageMatrixGUI gui = ImageMatrixGUI.getInstance();
    Hero hero;
    Room currentRoom;
    String currentRoomPath = "room0.txt";
    List<Room> rooms = new ArrayList<>();

    public static int returnNumberOfFiles(File directory) {
        int count = 0;
        for (File file: directory.listFiles()) {
            if (file.isFile()) {
                count++;
            }
        }
        return count;
    }

    public void readAllRooms(){
        String filePath = "C:\\Users\\letic\\OneDrive\\Ambiente de Trabalho\\Rogue_POO2023_v1_Nome_Completo\\rooms";
        int numberOfFiles = returnNumberOfFiles(new File(filePath));

        for (int i = 0; i < numberOfFiles; i++) {
            filePath = "C:\\Users\\letic\\OneDrive\\Ambiente de Trabalho\\Rogue_POO2023_v1_Nome_Completo\\rooms\\room" + i + ".txt";
            Room addRoom = new Room(filePath, "room" + i +".txt");
            rooms.add(addRoom);
        }
    }

    public void init() {
        readAllRooms();

        for (int i = 0; i < rooms.size(); i++) {
            if(rooms.get(i).getRoomName().equalsIgnoreCase(currentRoomPath))
                currentRoom = rooms.get(i);
        }

        hero = new Hero(currentRoom.getHeroPosition());
        currentRoom.tiles.add(hero);

        gui.setEngine(this);
        gui.newImages(currentRoom.tiles);
        gui.newStatusImages(hero.createStatusHero());
        gui.go();

        gui.setStatus("O jogo começou!");

        while (true) {
            gui.update();
        }
    }

    private void moveHero(String message, int keyEvent) {
        System.out.println(message);
        hero.move(keyEvent, currentRoom);
        verifyPassDoor(hero.getPosition());

        for (ImageTile tile : currentRoom.tiles){
            if(tile instanceof Enemy){
                ((Enemy) tile).moveIntoEnemy(hero.getPosition(),currentRoom);
            }
        }
        refreshImages();
    }

    public void notify(int keyPressed) {
        switch (keyPressed) {
            case KeyEvent.VK_UP:
                //Move Up
                moveHero("User pressed up key!", keyPressed);
                verifyHeroDie();
                break;
            case KeyEvent.VK_DOWN:
                //Move Down
                moveHero("User pressed down key!", keyPressed);
                verifyHeroDie();
                break;
            case KeyEvent.VK_LEFT:
                //Move Left
                moveHero("User pressed left key!", keyPressed);
                verifyHeroDie();
                break;
            case KeyEvent.VK_RIGHT:
                //Move Right
                moveHero("User pressed right key!", keyPressed);
                verifyHeroDie();
                break;
            default:
                //Invalid
                break;
        }
        hero.keyPressedItems(keyPressed, currentRoom, gui);
    }

    public void verifyPassDoor(Position heroPosition){
        Door portaAntiga = null;
        for (Door door: currentRoom.doors) {
            if(door.isOpen()){
                if(door.getPosition().equals(heroPosition)){
                    portaAntiga = door;
                    String nextRoomPath = door.getPath();
                    for (int i = 0; i < rooms.size(); i++) {
                        if(rooms.get(i).getRoomName().equalsIgnoreCase(nextRoomPath)){
                            currentRoom = rooms.get(i);
                            for (Door newDoor: currentRoom.doors) {
                                if(portaAntiga.getNextNumberDoor().equalsIgnoreCase(newDoor.getNumberDoor())){
                                    currentRoom.tiles.add(hero.setPositionHero(newDoor.getPosition()));
                                }
                            }
                        }
                    }
                }
            }
            else {
                if(hero.heroHasKey(door.getNeedKey())){
                    if(door.getPosition().equals(heroPosition)){
                        door.openDoor();
                        portaAntiga = door;
                        String nextRoomPath = door.getPath();
                        for (int i = 0; i < rooms.size(); i++) {
                            if(rooms.get(i).getRoomName().equalsIgnoreCase(nextRoomPath)){
                                currentRoom = rooms.get(i);
                                for (Door newDoor: currentRoom.doors) {
                                    if(portaAntiga.getNextNumberDoor().equalsIgnoreCase(newDoor.getNumberDoor())){
                                        newDoor.openDoor();
                                        currentRoom.tiles.add(hero.setPositionHero(newDoor.getPosition()));
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    public void verifyHeroDie(){
        if(hero.getPoints() <= 0){
            hero.setPoints(0);
            Leaderboard leaderboard = new Leaderboard();
            String username = gui.showInputDialog("Username", "Escreva o seu username:");
            leaderboard.loadLeaderboard();
            leaderboard.addPlayer(username , hero.getPoints());
            leaderboard.printLeaderboard();
        }
    }

    private boolean hasKeyInInventory(){
        for(ImageTile item : hero.inventory){
            if(item.getName().equalsIgnoreCase("Key")){
                return true;
            }
        }
        return false;
    }

    public void refreshImages(){
        gui.clearImages();
        gui.clearStatus();
        gui.newImages(currentRoom.tiles);
        gui.newStatusImages(hero.statusTiles);
        gui.update();
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.init();
    }

}