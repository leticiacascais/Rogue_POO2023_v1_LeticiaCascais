package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Meat extends Item {

    public Meat(Position position) {
        super(position);
    }

    public int getHealthPoints() {
        return 20;
    }
    @Override
    public String getName() {
        return "GoodMeat";
    }
}
