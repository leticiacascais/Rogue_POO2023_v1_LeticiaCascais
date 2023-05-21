package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Trap implements ImageTile {

    private Position position;

    @Override
    public String getName() {
        return "Trap";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
