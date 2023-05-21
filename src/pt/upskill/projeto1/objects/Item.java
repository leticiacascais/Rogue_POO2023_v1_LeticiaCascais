package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Item implements ImageTile {

    private Position position;

    public Item(Position position) {
        this.position = position;
    }

    public ImageTile setPosition(Position position) {
        this.position = position;
        return this;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}

