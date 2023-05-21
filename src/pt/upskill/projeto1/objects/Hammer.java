package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Hammer extends Item {

    public int Damage = 15;


    public Hammer(Position position) {
        super(position);
    }

    @Override
    public String getName() {
        return "Hammer";
    }

    public int getDamage() {
        return Damage;
    }
}
