package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class StairsUp implements ImageTile {

    private Position position;

    public StairsUp(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "StairsUp";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
