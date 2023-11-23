package com.nhuszka.robots.store;

import com.nhuszka.robots.geometry.Coordinates;
import com.nhuszka.robots.robot.Robot;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class RobotRegistry {

    private static final List<Robot> ROBOTS = new ArrayList<>();

    public synchronized void registerRobot(Robot robot) {
        ROBOTS.add(robot);
    }

    public List<Coordinates> listRobotCoordinates() {
        return ROBOTS.stream()
                .map(Robot::getCurrentPosition)
                // TODO handle the geometry difference between server side geometry and JS side
                .map(coordinates -> new Coordinates(coordinates.x()+200, coordinates.y()+200))
                .collect(Collectors.toList());
    }
}
