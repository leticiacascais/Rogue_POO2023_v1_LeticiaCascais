package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Fire_old implements ImageTile {

    private Position position;

    public Fire_old(Position position) {
        this.position = position;
    }

    @Override
    public String getName() {
        return "Fire_old";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
