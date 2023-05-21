package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Door implements ImageTile {

    private boolean isOpen=true;
    private Position position;
    private String numberDoor;
    private String path;
    private String nextNumberDoor;

    public String getNeedKey() {
        return NeedKey;
    }

    private String NeedKey;

    public Door(String numberDoor, String path, String nextNumberDoor) {
        this.numberDoor = numberDoor;
        this.path = path;
        this.nextNumberDoor = nextNumberDoor;
    }

    public String getNumberDoor() {
        return numberDoor;
    }

    public String getPath() {
        return path;
    }

    public boolean isOpen() {
        return isOpen;
    }

    @Override
    public String getName() {
        if (this.isOpen()) {
            return "DoorOpen";
        } else {
            return "DoorClosed";
        }
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
    public String getNextNumberDoor() {
        return nextNumberDoor;
    }
    public void NeedKey (String NeedKey){
        this.isOpen = false;
        this.NeedKey = NeedKey;
    }
    public void openDoor(){
        this.isOpen = true;
    }

    public boolean canBeOpened(String key) {
        return this.isOpen || key.equals(this.NeedKey);
    }
}
