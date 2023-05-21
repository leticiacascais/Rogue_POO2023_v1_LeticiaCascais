package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public abstract class Enemy implements ImageTile {
    private boolean defeated = false;
    private Position position;
    public int damage;
    public int health;

    public int getPoints() {
        return points;
    }

    public void setPoints(int points) {
        this.points = points;
    }

    public int points;

    public Enemy(Position position, int damage, int health, int points) {
        this.position = position;
        this.damage=damage;
        this.health=health;
        this.points=points;
    }

    public void moveRandom(Room room) {

        Position currentPosition = getPosition();
        int currentX = currentPosition.getX();
        int currentY = currentPosition.getY();
        Position nextPosition = null;

        Random random = new Random();
        int randomDirection = random.nextInt(4);

        switch (randomDirection) {

            case 0:
                nextPosition = new Position(currentX, currentY - 1);
                break;
            case 1:
                nextPosition = new Position(currentX, currentY + 1);
                break;
            case 2:
                nextPosition = new Position(currentX - 1, currentY);
                break;
            case 3:
                nextPosition = new Position(currentX + 1, currentY);
                break;
            default:
                break;
        }

        attackHero(nextPosition, room);
    }

    public void moveIntoHero(Position heroPosition, Room room) {
        Position currentPosition = getPosition();
        double initialDistance = currentPosition.distanceTo(heroPosition);
        List<Position> possiblePositions = new ArrayList<>();

        possiblePositions.add(new Position(currentPosition.getX() + 1, currentPosition.getY()));
        possiblePositions.add(new Position(currentPosition.getX() - 1, currentPosition.getY()));
        possiblePositions.add(new Position(currentPosition.getX(), currentPosition.getY() + 1));
        possiblePositions.add(new Position(currentPosition.getX(), currentPosition.getY() - 1));

        double minDistance = Double.MAX_VALUE;
        Position nextPosition = null;

        for (Position position : possiblePositions) {
            double distance = position.distanceTo(heroPosition);
            if (!room.isWall(position) && !room.isEnemy(position) && !room.isDoor(position)) {
                if (distance < minDistance) {
                    minDistance = distance;
                    nextPosition = position;
                }
            }
        }

        if (initialDistance < 5 && nextPosition != null) {
            attackHero(nextPosition, room);
        } else {
            moveRandom(room);
        }
    }

    public void attackHero(Position nextPosition, Room room){
        if (nextPosition != null && !room.isWall(nextPosition) && !room.isDoor(nextPosition) && !room.isEnemy(nextPosition)) {
            if(room.isHero(nextPosition)){
                for (ImageTile tile : room.tiles) {
                    if (tile instanceof Hero && tile.getPosition().equals(nextPosition)) {
                        ((Hero) tile).reduceHealth(this.getDamage());
                        ImageMatrixGUI.getInstance().setStatus("O inimigo atacou-te e perdeste " + getDamage() + " de vida.");

                        if (((Hero) tile).getHealth() <= 0) {
                            ImageMatrixGUI.getInstance().setStatus("Morreste! Voltaste ao Checkpoint inicial!");
                            return;
                        }
                        return;
                    }
                }
            }
            this.setPosition(nextPosition);
        }
    }

    public int getDamage() {
        return damage;
    }

    public void reduceHealth(int damage) {

        this.health -= damage;
        if (this.health <= 0) {
            this.health = 0;
            defeated = true;
        }
    }
    public boolean isDefeated() {
        return defeated;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    @Override
    public Position getPosition() {
        return position;
    }
}



