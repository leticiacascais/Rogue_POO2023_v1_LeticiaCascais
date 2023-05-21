package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class StairsDown implements ImageTile {

    private Position position;

    public StairsDown(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "StairsDown";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
