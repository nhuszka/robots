package com.nhuszka.robots.robot;

import com.nhuszka.robots.geometry.Coordinates;
import com.nhuszka.robots.geometry.Direction;

interface Movement {

    static Coordinates move(int distance, Coordinates coordinates, Direction direction) {
        int x = coordinates.getX();
        int y = coordinates.getY();
        switch (direction) {
            case NORTH:
                y += distance;
                break;
            case SOUTH:
                y -= distance;
                break;
            case EAST:
                x += distance;
                break;
            case WEST:
                x -= distance;
                break;
        }
        return new Coordinates(x, y);
    }

}
