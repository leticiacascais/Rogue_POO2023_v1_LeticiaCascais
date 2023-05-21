package pt.upskill.projeto1.game;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.objects.*;
import pt.upskill.projeto1.rogue.utils.Direction;
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
    Leaderboard leaderboard = new Leaderboard();
    CheckPoint checkPoint;

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
        String filePath = "C:\\Users\\letic\\OneDrive\\Ambiente de Trabalho\\Git\\Rogue_POO2023_v1_LeticiaCascais\\rooms";
        int numberOfFiles = returnNumberOfFiles(new File(filePath));

        for (int i = 0; i < numberOfFiles; i++) {
            filePath = "C:\\Users\\letic\\OneDrive\\Ambiente de Trabalho\\Git\\Rogue_POO2023_v1_LeticiaCascais\\rooms\\room" + i + ".txt";
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
        checkPoint = new CheckPoint(currentRoom.getHeroPosition(), currentRoom);

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
                ((Enemy) tile).moveIntoHero(hero.getPosition(),currentRoom);
            }
        }
        refreshImages();
    }

    public void notify(int keyPressed) {
        switch (keyPressed) {
            case KeyEvent.VK_UP:
                //Move Up
                moveHero("User pressed up key!", keyPressed);
                verifyWin();
                verifyHeroDie();
                break;
            case KeyEvent.VK_DOWN:
                //Move Down
                moveHero("User pressed down key!", keyPressed);
                verifyWin();
                verifyHeroDie();
                break;
            case KeyEvent.VK_LEFT:
                //Move Left
                moveHero("User pressed left key!", keyPressed);
                verifyWin();
                verifyHeroDie();
                break;
            case KeyEvent.VK_RIGHT:
                //Move Right
                moveHero("User pressed right key!", keyPressed);
                verifyWin();
                verifyHeroDie();
                break;
            case KeyEvent.VK_SPACE:
                try {
                    String direction = gui.showInputDialog("Direção", "Escolhe entre UP, DOWN, RIGHT e LEFT");
                    shotFireBall(Direction.valueOf(direction));
                }
                catch (Exception exc) {
                    gui.setStatus("Direção da bola de fogo errada!");
                    System.out.println(exc);
                }

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
            String username = gui.showInputDialog("Username", "Escreva o seu username:");
            leaderboard.loadLeaderboard();
            leaderboard.addPlayer(username , hero.getPoints());
            leaderboard.printLeaderboard();
            gui.showMessage("Game Lose", "Perdeste o Jogo!");
            System.exit(0);
        }

        if(hero.getHealth() <= 0){
            currentRoom = checkPoint.getRoom();
            hero.setHealth(100);
            hero.setPosition(checkPoint.getPosition());
            checkPoint.getRoom().tiles.add(hero);
            gui.newImages(checkPoint.getRoom().tiles);
            gui.newStatusImages(hero.statusTiles);
        }
    }

    public void refreshImages(){
        gui.clearImages();
        gui.clearStatus();
        gui.newImages(currentRoom.tiles);
        gui.newStatusImages(hero.statusTiles);
        gui.update();
    }

    public void verifyWin(){
        for (ImageTile win: currentRoom.tiles) {
            if(win instanceof Win && win.getPosition().equals(hero.getPosition())){
                gui.showMessage("Venceste!", "Venceste");
                String username = gui.showInputDialog("Username", "Escreva o seu username:");
                leaderboard.loadLeaderboard();
                leaderboard.addPlayer(username , hero.getPoints());
                leaderboard.printLeaderboard();
                System.exit(0);
            }
        }
    }

    public void shotFireBall(Direction direction){
        Fire fire = new Fire(hero.getPosition());
        fire.setRoom(currentRoom);
        currentRoom.tiles.add(fire);
        refreshImages();
        FireBallThread fireBallThread = new FireBallThread(direction, fire);
        fireBallThread.run();
        fire.deleteRoom();
        currentRoom.tiles.remove(fire);
    }

    public static void main(String[] args) {
        Engine engine = new Engine();
        engine.init();
    }
}