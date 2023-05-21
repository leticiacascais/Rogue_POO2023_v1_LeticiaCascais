package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;


public class Hero implements ImageTile {

    private Position position;
    private int Damage = 15, Health = 100, points = 50;
    public List<ImageTile> statusTiles = new ArrayList<>();
    public List<ImageTile> inventory = new ArrayList<>();

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public Hero(Position heroPosition) {
        this.position = heroPosition;
        createStatusHero();
    }
    public int getDamage() {
        return Damage;
    }

    @Override
    public String getName() {
        return "Hero";
    }

    @Override
    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Hero setPositionHero(Position position) {
        this.position = position;
        return this;
    }

    public void move(int direction, Room room) {

        Position currentPosition = getPosition();
        int currentX = currentPosition.getX();
        int currentY = currentPosition.getY();
        Position nextPosition = null;

        switch (direction) {
            case KeyEvent.VK_UP:
                nextPosition = new Position(currentX, currentY - 1);
                break;
            case KeyEvent.VK_DOWN:
                nextPosition = new Position(currentX, currentY + 1);
                break;
            case KeyEvent.VK_LEFT:
                nextPosition = new Position(currentX - 1, currentY);
                break;
            case KeyEvent.VK_RIGHT:
                nextPosition = new Position(currentX + 1, currentY);
                break;
            default:
                break;
        }

        if (nextPosition != null && !room.isWall(nextPosition)) {

            if(room.isEnemy(nextPosition)){
                for (ImageTile tile : room.tiles) {
                    if (tile instanceof Enemy && tile.getPosition().equals(nextPosition)) {
                        ((Enemy) tile).reduceHealth(this.getDamage());
                        ImageMatrixGUI.getInstance().setStatus("Atacaste o inimigo e tiraste-lhe " + getDamage() + " de vida.");

                        if (((Enemy) tile).isDefeated()) {
                            ImageMatrixGUI.getInstance().setStatus("Inimigo derrotado!");
                            room.removeEnemy((Enemy) tile);
                            room.tiles.remove(tile);
                        }
                        return;
                    }
                }

            }
            this.setPoints(this.getPoints()-1);
            this.setPosition(nextPosition);
        }


        if (inventory.size() == 3) {
            ImageMatrixGUI.getInstance().setStatus("Inventory full!");
            return;
        }

        pickUpItem(room);
    }

    public void keyPressedItems(int event, Room room, ImageMatrixGUI gui) {
        switch (event) {
            case KeyEvent.VK_1:
                dropItem(0, room, gui);
                break;
            case KeyEvent.VK_2:
                dropItem(1,room, gui);
                break;
            case KeyEvent.VK_3:
                dropItem(2,room, gui);
                break;
            default:
                break;
        }
    }

    public void dropItem(int index, Room room, ImageMatrixGUI gui) {
        if (index >= 0 && index < inventory.size()) {
            Item removedItem = (Item) inventory.get(index);
            inventory.remove(index);
            statusTiles.remove(removedItem);


            for (int j = 0; j < inventory.size(); j++) {
                Item item = (Item) inventory.get(j);
                item.setPosition(new Position(7 + j , item.getPosition().getY()));
            }


            if (removedItem instanceof Sword){
                this.setDamage(this.getDamage() - ((Sword) removedItem).getDamage());

            }
            else if (removedItem instanceof Hammer){
                this.setDamage(this.getDamage() - ((Hammer) removedItem).getDamage());

            }

            gui.setStatus("Item removido: " + removedItem.getName());

            removedItem.setPosition(new Position(this.getPosition().getX(), this.getPosition().getY()));
            room.tiles.add(removedItem);

            gui.newImages(room.tiles);
            gui.newStatusImages(statusTiles);
            gui.update();

        }
    }

    public void setDamage(int damage) {
        Damage = damage;
    }

    public void reduceHealth(int damage) {
        this.setHealth(this.getHealth()-damage);
    }
    public void setHealth(int Health) {
        this.Health = Health;
        this.updateStatusTiles();
    }

    public int getHealth() {
        return Health;
    }

    public List<ImageTile> createStatusHero() {

        // Add the elements of the status bar
        statusTiles.add(new Black(new Position(0, 0)));
        statusTiles.add(new Black(new Position(1, 0)));
        statusTiles.add(new Black(new Position(2, 0)));
        statusTiles.add(new Fire(new Position(0, 0)));
        statusTiles.add(new Fire(new Position(1, 0)));
        statusTiles.add(new Fire(new Position(2, 0)));
        statusTiles.add(new Green(new Position(3, 0)));
        statusTiles.add(new Green(new Position(4, 0)));
        statusTiles.add(new Green(new Position(5, 0)));
        statusTiles.add(new Green(new Position(6, 0)));
        statusTiles.add(new Black(new Position(7, 0)));
        statusTiles.add(new Black(new Position(8, 0)));
        statusTiles.add(new Black(new Position(9, 0)));

        return statusTiles;
    }

    public void updateStatusTiles() {
        int health = this.getHealth();

        if (health >= 88) {
            statusTiles.add(new Green(new Position(3, 0)));
            statusTiles.add(new Green(new Position(4, 0)));
            statusTiles.add(new Green(new Position(5, 0)));
            statusTiles.add(new Green(new Position(6, 0)));

        } else if (health >= 75 && health<87.5) {
            statusTiles.add(new Green(new Position(3, 0)));
            statusTiles.add(new Green(new Position(4, 0)));
            statusTiles.add(new Green(new Position(5, 0)));
            statusTiles.add(new RedGreen(new Position(6, 0)));

        } else if (health >= 62.5 && health<75) {
            statusTiles.add(new Green(new Position(3, 0)));
            statusTiles.add(new Green(new Position(4, 0)));
            statusTiles.add(new Green(new Position(5, 0)));
            statusTiles.add(new Red(new Position(6, 0)));

        } else if (health >= 50 && health<62.5) {
            statusTiles.add(new Green(new Position(3, 0)));
            statusTiles.add(new Green(new Position(4, 0)));
            statusTiles.add(new RedGreen(new Position(5, 0)));
            statusTiles.add(new Red(new Position(6, 0)));

        } else if (health >= 37.5 && health<50) {
            statusTiles.add(new Green(new Position(3, 0)));
            statusTiles.add(new Green(new Position(4, 0)));
            statusTiles.add(new Red(new Position(5, 0)));
            statusTiles.add(new Red(new Position(6, 0)));

        } else if (health >= 25 && health<37.5) {
            statusTiles.add(new Green(new Position(3, 0)));
            statusTiles.add(new RedGreen(new Position(4, 0)));
            statusTiles.add(new Red(new Position(5, 0)));
            statusTiles.add(new Red(new Position(6, 0)));

        } else if (health >= 12.5 && health<25) {
            statusTiles.add(new Green(new Position(3, 0)));
            statusTiles.add(new Red(new Position(4, 0)));
            statusTiles.add(new Red(new Position(5, 0)));
            statusTiles.add(new Red(new Position(6, 0)));

        } else if (health > 0 && health<12.5) {
            statusTiles.add(new RedGreen(new Position(3, 0)));
            statusTiles.add(new Red(new Position(4, 0)));
            statusTiles.add(new Red(new Position(5, 0)));
            statusTiles.add(new Red(new Position(6, 0)));
        }
        else{
            statusTiles.add(new Red(new Position(3, 0)));
            statusTiles.add(new Red(new Position(4, 0)));
            statusTiles.add(new Red(new Position(5, 0)));
            statusTiles.add(new Red(new Position(6, 0)));

        }
        ImageMatrixGUI.getInstance().newStatusImages(statusTiles);
    }
    private Position findEmptyPosition() {
        for (int i = 7; i < 10; i++) {
            boolean positionOccupied = false;
            for (ImageTile item : inventory) {
                if (item.getPosition().getX() == i && item.getPosition().getY() == 0) {
                    positionOccupied = true;
                    break;
                }
            }
            if (!positionOccupied) {
                return new Position(i, 0);
            }
        }
        return null;
    }
    public void pickUpItem(Room room) {
        List<ImageTile> itemsToRemove = new ArrayList<>();
        try {
            for (ImageTile item : room.tiles) {
                if (item.getName().equalsIgnoreCase("GoodMeat") && item.getPosition().equals(this.position)) {
                    Meat meat = (Meat) item;
                    Health += meat.getHealthPoints();
                    ImageMatrixGUI.getInstance().setStatus("You ate meat and gained " + meat.getHealthPoints() + " health points!");
                    itemsToRemove.add(item);
                    //gui.newImages(room.tiles);
                    System.out.println("ola");
                } else if (item.getName().equalsIgnoreCase("Sword") && item.getPosition().equals(this.position)) {
                    Sword sword = (Sword) item;
                    Damage += sword.getDamage();
                    if (inventory.size() < 3) {
                        Position emptyPosition = findEmptyPosition();
                        if (emptyPosition != null) {
                            inventory.add(sword);
                            sword.setPosition(emptyPosition);
                            ImageMatrixGUI.getInstance().setStatus("You caught sword and gained " + sword.getDamage() + " damage points!");
                            System.out.println("a");
                            itemsToRemove.add(item);
                            System.out.println("b");
                            statusTiles.add(item);
                        }
                        else{
                            ImageMatrixGUI.getInstance().setStatus("Inventory is full. Cannot add sword.");
                        }
                    }
                }
                else if (item.getName().equalsIgnoreCase("Hammer") && item.getPosition().equals(this.position)) {
                    Hammer hammer = (Hammer) item;
                    Damage += hammer.getDamage();
                    if (inventory.size() < 3) {
                        Position emptyPosition = findEmptyPosition();
                        if (emptyPosition != null) {
                            inventory.add(hammer);
                            hammer.setPosition(emptyPosition);
                            ImageMatrixGUI.getInstance().setStatus("You caught " + hammer.getName() + " and gained " + hammer.getDamage() + " damage points!");
                            itemsToRemove.add(item);
                            statusTiles.add(item);
                        }
                        else{
                            ImageMatrixGUI.getInstance().setStatus("Inventory is full. Cannot add Hammer.");
                        }
                    }
                }
                else if (item.getName().equalsIgnoreCase("Key") && item.getPosition().equals(this.position)) {
                    Key key = (Key) item;
                    if (inventory.size() < 3) {
                        Position emptyPosition = findEmptyPosition();
                        if (emptyPosition != null) {
                            inventory.add(item);
                            key.setPosition(emptyPosition);
                            ImageMatrixGUI.getInstance().setStatus("You caught " + ((ImageTile) key).getName());
                            itemsToRemove.add(item);
                            statusTiles.add(item);
                        }
                        else{
                            ImageMatrixGUI.getInstance().setStatus("Inventory is full. Cannot add Hammer.");
                        }
                    }
                }
            }

            statusTiles.remove(itemsToRemove);
            room.tiles.removeAll(itemsToRemove);

            ImageMatrixGUI.getInstance().newImages(room.tiles);
            updateStatusTiles();
            ImageMatrixGUI.getInstance().newStatusImages(statusTiles);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public boolean heroHasKey(String keyName){

        for (ImageTile key: inventory) {
            if(key instanceof Key){
                if(((Key) key).getKeyName().equalsIgnoreCase(keyName))
                    return true;
            }
        }

        ImageMatrixGUI.getInstance().setStatus("Precisas da chave para abrir a porta!");
        return false;
    }
}
