package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Room implements ImageTile{

    public List<ImageTile> tiles = new ArrayList<>();
    public List<Door> doors = new ArrayList<>();
    public Position heroPosition = new Position(0 , 0);
    public List<Key> keys = new ArrayList<>();
    public String roomName;

    public Room(String filePath, String roomName){
        ReadRoom(filePath);
        this.roomName = roomName;
    }

    public String getRoomName() {
        return roomName;
    }

    public void ReadRoom (String filePath){
        addFloor();
        try {
            Scanner fileScanner = new Scanner(new File(filePath));
            int i = 0;
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine();
                if(!(line.charAt(0)=='#')){
                    for (int j = 0; j < line.length(); j++) {
                        AssignLetters(line.charAt(j), i, j);
                    }
                    i++;
                }
                else if ((line.charAt(0) == '#')){
                    String[] info = line.split(" ");
                    if(line.charAt(4) == 'D'){
                        Door door = new Door(info[1], info[3], info[4]);
                        if (info.length >= 6){
                            door.NeedKey(info[5]);
                        }
                        doors.add(door);
                    }else if (line.charAt(2) == 'K'){
                        Key key = new Key(new Position(0,0), info[2]);
                        keys.add(key);
                    }
                }
            }
            fileScanner.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void addFloor () {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                tiles.add(new Floor(new Position(i, j)));
            }
        }
    }

    public void AssignLetters (char letter, int j, int i){
        if (Character.isDigit(letter)){
            setDoorPosition(letter, j, i);
        }
        else {
            switch (letter) {
                case 'W':
                    tiles.add(new Wall(new Position(i, j)));
                    break;
                case 'S':
                    tiles.add(new Skeleton(new Position(i, j), 20, 30));
                    break;
                case 'A':
                    Sword sword = new Sword(new Position(i, j));
                    tiles.add(sword);
                    break;
                case 'H':
                    heroPosition = new Position(i ,j);
                    break;
                case 'M':
                    Meat meat = new Meat(new Position(i, j));
                    tiles.add(meat);
                    break;
                case 'Z':
                    Hammer hammer = new Hammer(new Position(i, j));
                    tiles.add(hammer);
                    break;
                case 'B':
                    tiles.add(new Bat(new Position(i, j), 12, 15));
                    break;
                case 'G':
                    tiles.add(new BadGuy(new Position(i, j), 25, 40));
                    break;
                case 'D':
                    tiles.add(new StairsDown(new Position(i, j)));
                    break;
                case 'U':
                    tiles.add(new StairsUp(new Position(i, j)));
                case 'T':
                    tiles.add(new Thief(new Position(i, j), 30, 35));
                case 'K':
                    if (!keys.isEmpty()) {
                        Key key = keys.get(0);
                        key.setPosition(new Position(i, j));
                        tiles.add(key);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    public void setDoorPosition(char letter, int j, int i){
        for (Door door: doors) {
            if(door.getNumberDoor().equalsIgnoreCase(String.valueOf(letter))){
                door.setPosition(new Position(i, j));
                tiles.add(door);
            }
        }
    }

    public boolean isWall(Position position) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Wall && tile.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isDoor(Position position) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Door && tile.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHero(Position position) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Hero && tile.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isItems(Position position) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Item && tile.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isEnemy(Position position) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Enemy && tile.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public boolean isHammer(Position position) {
        for (ImageTile tile : tiles) {
            if (tile instanceof Hammer && tile.getPosition().equals(position)) {
                return true;
            }
        }
        return false;
    }

    public void removeEnemy(Enemy enemy) {
        tiles.remove(enemy);
    }

    public Position getHeroPosition(){
        return heroPosition;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Position getPosition() {
        return null;
    }

}
