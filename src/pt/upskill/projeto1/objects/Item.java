package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public abstract class Item implements ImageTile {

    private Position position;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    private int points;
    public Item(Position position, int points) {
        this.position = position;
        this.points=points;
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

