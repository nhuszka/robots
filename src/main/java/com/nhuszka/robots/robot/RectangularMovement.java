package com.nhuszka.robots.robot;

import com.nhuszka.robots.geometry.Box;
import com.nhuszka.robots.geometry.Coordinates;
import com.nhuszka.robots.geometry.Direction;

import static com.nhuszka.robots.geometry.Direction.*;

class RectangularMovement {

    static boolean isWithinBox(Coordinates coordinate, Box box) {
        return coordinate.x() >= box.bottomLeft().x()
                && coordinate.x() <= box.bottomRight().x()
                &&  coordinate.y() >= box.bottomLeft().y()
                &&  coordinate.y() <= box.topLeft().y();
    }

    static Direction getDirection(int distanceToMove, Coordinates coordinates, Direction direction, Box box) {
        boolean shouldTurn = shouldTurn(distanceToMove, coordinates, direction, box);
        if (shouldTurn) {
            return switch (direction) {
                case NORTH -> EAST;
                case EAST -> SOUTH;
                case SOUTH -> Direction.WEST;
                case WEST -> NORTH;
            };
        }
        return direction;
    }

    private static boolean shouldTurn(int distanceToMove, Coordinates coordinates, Direction direction, Box box) {
        int x = coordinates.x();
        int y = coordinates.y();

        boolean reachedEastBorder = direction == EAST
                && (x + distanceToMove > box.topRight().x());
        boolean reachedWestBorder = direction == Direction.WEST
                && (x - distanceToMove < box.bottomLeft().x());
        boolean reachedNorthBorder = direction == NORTH
                && (y + distanceToMove > box.topLeft().y());
        boolean reachedSouthBorder = direction == SOUTH
                && (y - distanceToMove < box.bottomRight().y());

        return reachedEastBorder || reachedWestBorder || reachedNorthBorder || reachedSouthBorder;
    }
}
