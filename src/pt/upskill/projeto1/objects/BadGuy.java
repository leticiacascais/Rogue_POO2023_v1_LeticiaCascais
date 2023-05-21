package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class BadGuy extends Enemy {

    private Position position;

    public BadGuy(Position position, int damage, int health) {
        super(position, 25, 40);
    }


    @Override
    public String getName() {
        return "BadGuy";
    }

}
