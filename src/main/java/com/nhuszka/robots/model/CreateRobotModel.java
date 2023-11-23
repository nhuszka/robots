package com.nhuszka.robots.model;

import com.nhuszka.robots.geometry.Coordinates;
import lombok.Data;

@Data
public class CreateRobotModel {

    private final RobotType type;
    private final String name;
    private final Coordinates startCoordinates;
    private final int dimensionOfPath;
    private final RobotSpeed speed;
}
