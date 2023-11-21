package com.nhuszka.robots.geometry;

public record Box(
        Coordinates topLeft,
        Coordinates topRight,
        Coordinates bottomRight,
        Coordinates bottomLeft
) implements Shape {
}
