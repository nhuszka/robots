package com.nhuszka.robots.robot;

import com.nhuszka.robots.geometry.Coordinates;
import com.nhuszka.robots.geometry.Direction;
import com.nhuszka.robots.geometry.Line;

import static com.nhuszka.robots.geometry.Direction.*;

class LinearMovement {

    static boolean isOnLine(Coordinates coordinate, Line line) {
        return coordinate.x() >= line.a().x()
                && coordinate.x() <= line.b().x();
    }

    static Direction getDirection(int distanceToMove, Coordinates coordinates, Direction direction, Line line) {
        boolean shouldTurn = shouldTurn(distanceToMove, coordinates, direction, line);
        if (shouldTurn) {
            return switch (direction) {
                case NORTH -> SOUTH;
                case EAST -> WEST;
                case SOUTH -> NORTH;
                case WEST -> EAST;
            };
        }
        return direction;
    }

    private static boolean shouldTurn(int distanceToMove, Coordinates coordinates, Direction direction, Line line) {
        int x = coordinates.x();
        int y = coordinates.y();

        boolean reachedEastPoint = direction == EAST
                && (x + distanceToMove > line.b().x());
        boolean reachedWestPoint = direction == Direction.WEST
                && (x - distanceToMove < line.a().x());
        boolean reachedNorthPoint = direction == NORTH
                && (y + distanceToMove > line.a().y());
        boolean reachedSouthPoint = direction == SOUTH
                && (y - distanceToMove < line.b().y());

        return reachedEastPoint || reachedWestPoint || reachedNorthPoint || reachedSouthPoint;
    }
}
