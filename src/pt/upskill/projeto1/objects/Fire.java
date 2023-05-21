package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.FireTile;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Fire implements ImageTile, FireTile {

    private Position position;

    private Room room;

    public Fire(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Fire";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    @Override
    public boolean validateImpact() {
        if (this.room != null) {
            return !room.isWall(this.getPosition());
        }
        return false;
    }

    public void setRoom(Room room) {
        this.room = room;
    }

    public void deleteRoom() {
        this.room = null;
    }

    @Override
    public void setPosition(Position position) {
        this.position = position;
    }
}
