package com.nhuszka.robots;

import com.nhuszka.robots.geometry.Box;
import com.nhuszka.robots.geometry.Coordinates;
import com.nhuszka.robots.geometry.Direction;
import com.nhuszka.robots.geometry.Line;
import com.nhuszka.robots.robot.Robot;
import com.nhuszka.robots.robot.RobotLinear;
import com.nhuszka.robots.robot.RobotRectangular;
import com.nhuszka.robots.store.RobotRegistry;
import com.nhuszka.robots.util.TimeUtil;

import java.util.logging.Logger;

public class RobotStarter {

    private static Logger log = Logger.getLogger(RobotStarter.class.getSimpleName());
    private final RobotRegistry robotRegistry;

    public RobotStarter(RobotRegistry robotRegistry) {
        this.robotRegistry = robotRegistry;
    }

    public void startRobots() {
        Box box1 = new Box(
                new Coordinates(20, 120),
                new Coordinates(120, 120),
                new Coordinates(120, 20),
                new Coordinates(20, 20)
        );
        Coordinates startCoordinates1 = new Coordinates(20, 20);
        // TODO handle case, when robot is started with a wrong position-direction combination. Like x=2, y=2, direction=EAST
        //      it would work for counter-clockwise, but the robot wants to move clockwise -> would generate exception
        Robot fastRobot1 = new RobotRectangular("fastRobot1", startCoordinates1, Direction.NORTH, box1, 100, 10);
        robotRegistry.registerRobot(fastRobot1);

        Box box2 = new Box(
                new Coordinates(-120, 20),
                new Coordinates(-20, -20),
                new Coordinates(-20, -120),
                new Coordinates(-120, -120)
        );
        Coordinates startCoordinates2 = new Coordinates(-120, -20);
        // TODO handle case, when robot is started with a wrong position-direction combination. Like x=-12, y=-12, direction=EAST
        //      it would work for counter-clockwise, but the robot wants to move clockwise -> would generate exception
        Robot fastRobot2 = new RobotRectangular("fastRobot2", startCoordinates2, Direction.EAST, box2, 100, 10);
        robotRegistry.registerRobot(fastRobot2);

        Line line = new Line(
                new Coordinates(-70, 0),
                new Coordinates(70, 0)
        );
        Coordinates startCoordinatesLinear = new Coordinates(-70, -70);
        // TODO handle case, when robot is started with a wrong position-direction combination. Like x=0, y=0, direction=EAST
        //      it would work for counter-clockwise, but the robot wants to move clockwise -> would generate exception
        Robot linearRobot = new RobotLinear("linearRobot", startCoordinatesLinear, Direction.EAST, line, 100, 10);
        robotRegistry.registerRobot(linearRobot);

//        Flux.interval(Duration.ofMillis(1000))
//                .map(timeToPollPosition -> fastRobot1.getCurrentPosition())
//                .subscribe(position -> System.out.printf("poll position [x: %d, y: %d]%n", position.getX(), position.getY()));

        fastRobot1.startMoving();
        fastRobot1.coordinatesFeed()
                .subscribe(
                        position -> log.info(String.format("observe coordinates rectangularRobot1 [x: %d, y: %d]", position.x(), position.y()))
                );

        TimeUtil.waitForMillis(500);

        linearRobot.startMoving();
        linearRobot.coordinatesFeed()
                .subscribe(
                        position -> log.info(String.format("observe coordinates linearRobot [x: %d, y: %d]", position.x(), position.y()))
                );

        TimeUtil.waitForMillis(500);

        fastRobot2.startMoving();
        fastRobot2.coordinatesFeed()
                .subscribe(
                        position -> log.info(String.format("observe coordinates rectangularRobot2 [x: %d, y: %d]", position.x(), position.y()))
                );

        TimeUtil.waitFor(10);
        log.info("Stopping robot.");
        fastRobot1.stopMoving();
        fastRobot2.stopMoving();
        linearRobot.stopMoving();
    }
}