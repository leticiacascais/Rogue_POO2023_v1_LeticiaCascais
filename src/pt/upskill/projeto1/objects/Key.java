package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

public class Key extends Item {

    private String keyName;

    public Key(Position position, String keyName) {
        super(position);
        this.keyName = keyName;
    }

    public String getKeyName() {
        return keyName;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    @Override
    public String getName() {
        return "Key";
    }
}
