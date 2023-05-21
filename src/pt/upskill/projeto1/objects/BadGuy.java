package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class BadGuy extends Enemy {

    public BadGuy(Position position) {
        super(position, 25, 40, 20);
    }


    @Override
    public String getName() {
        return "BadGuy";
    }

}
