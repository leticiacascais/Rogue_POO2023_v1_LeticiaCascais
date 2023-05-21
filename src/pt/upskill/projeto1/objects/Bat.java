package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Bat extends Enemy {

    public Bat(Position position, int damage, int health) {
        super(position, 12, 15);
    }

    @Override
    public String getName() {
        return "Bat";
    }

}
