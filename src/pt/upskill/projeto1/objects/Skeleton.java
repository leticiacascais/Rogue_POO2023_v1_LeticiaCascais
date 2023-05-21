package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.Random;

public class Skeleton extends Enemy {

    public Skeleton(Position position) {
        super(position, 20, 30, 15);
    }

    @Override
    public String getName() {
        return "Skeleton";
    }

}
