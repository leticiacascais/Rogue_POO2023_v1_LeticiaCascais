package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class DoorClosed implements ImageTile {

    private Position position;

    public DoorClosed(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "DoorClosed";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
