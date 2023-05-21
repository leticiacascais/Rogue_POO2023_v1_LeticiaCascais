package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Sword extends Item {
    public int damage = 20;

    public Sword(Position position) {
        super(position, 15);
    }

    public int getDamage() {
        return damage;
    }

    @Override
    public String getName() {
        return "Sword";
    }

}
