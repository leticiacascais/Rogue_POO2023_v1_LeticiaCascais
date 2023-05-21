package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class CheckPoint implements ImageTile {
    private Position position;
    private Room room;

    public void setPosition(Position position) {
        this.position = position;
    }

    public Room getRoom() {
        return room;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public CheckPoint(Position position, Room room){
        this.position = position;
        this.room = room;
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public Position getPosition() {
        return position;
    }



}
