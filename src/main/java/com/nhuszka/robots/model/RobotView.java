package com.nhuszka.robots.model;

import com.nhuszka.robots.geometry.Coordinates;
import lombok.Data;

@Data
public class RobotView {
    private final String name;
    private final Coordinates actualCoordinates;
}
