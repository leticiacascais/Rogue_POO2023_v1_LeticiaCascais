package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.awt.*;

public class Win implements ImageTile {

    private Position position;

    public Win(Position position){
        this.position = position;
    }

    @Override
    public String getName() {
        return "Win";
    }

    @Override
    public Position getPosition() {
        return position;
    }
}
