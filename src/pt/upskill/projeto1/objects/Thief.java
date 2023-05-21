package pt.upskill.projeto1.objects;

import pt.upskill.projeto1.gui.ImageMatrixGUI;
import pt.upskill.projeto1.gui.ImageTile;
import pt.upskill.projeto1.rogue.utils.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Thief extends Enemy {

    public Thief(Position position) {
        super(position, 30, 35, 25);
    }

    @Override
    public void moveRandom(Room room) {

        Position currentPosition = getPosition();
        int currentX = currentPosition.getX();
        int currentY = currentPosition.getY();
        Position nextPosition = null;

        Random random = new Random();
        int randomDirection = random.nextInt(4);

        switch (randomDirection) {
            case 0:
                nextPosition = new Position(currentX + 1, currentY - 1);
                break;
            case 1:
                nextPosition = new Position(currentX + 1, currentY + 1);
                break;
            case 2:
                nextPosition = new Position(currentX - 1, currentY -1);
                break;
            case 3:
                nextPosition = new Position(currentX - 1, currentY + 1);
                break;
            default:
                break;
        }

        attackHero(nextPosition, room);
    }

    @Override
    public void moveIntoHero(Position heroPosition, Room room) {
        Position currentPosition = getPosition();
        double initialDistance = currentPosition.distanceTo(heroPosition);
        List<Position> possiblePositions = new ArrayList<>();

        possiblePositions.add(new Position(currentPosition.getX() + 1, currentPosition.getY()-1));
        possiblePositions.add(new Position(currentPosition.getX() + 1, currentPosition.getY()+1));
        possiblePositions.add(new Position(currentPosition.getX()-1, currentPosition.getY() + 1));
        possiblePositions.add(new Position(currentPosition.getX()-1, currentPosition.getY() - 1));

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

        if (initialDistance < 3 && nextPosition != null) {
            attackHero(nextPosition, room);
        } else {
            moveRandom(room);
        }
    }

    @Override
    public String getName() {
        return "Thief";
    }


}
