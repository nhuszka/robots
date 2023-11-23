package com.nhuszka.robots;

import com.nhuszka.robots.geometry.Box;
import com.nhuszka.robots.geometry.Coordinates;
import com.nhuszka.robots.geometry.Direction;
import com.nhuszka.robots.geometry.Line;
import com.nhuszka.robots.robot.Robot;
import com.nhuszka.robots.robot.RobotLinear;
import com.nhuszka.robots.robot.RobotRectangular;
import com.nhuszka.robots.store.RobotService;
import com.nhuszka.robots.util.TimeUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.logging.Logger;

@AllArgsConstructor
@Component
public class DemoRobotCreator {

    private static Logger log = Logger.getLogger(DemoRobotCreator.class.getSimpleName());

    public void startDemoRobots(RobotService robotService) {
        Box box1 = new Box(
                new Coordinates(20, 120),
                new Coordinates(120, 120),
                new Coordinates(120, 20),
                new Coordinates(20, 20)
        );
        Coordinates startCoordinatesFast = new Coordinates(20, 20);
        // TODO handle case, when robot is started with a wrong position-direction combination. Like x=2, y=2, direction=EAST
        //      it would work for counter-clockwise, but the robot wants to move clockwise -> would generate exception
        Robot fastRectangularRobot = new RobotRectangular("fastRectangularRobot",
                startCoordinatesFast, Direction.NORTH, box1, 100, 10);

        Box box2 = new Box(
                new Coordinates(140, 230),
                new Coordinates(240, 230),
                new Coordinates(240, 130),
                new Coordinates(140, 130)
        );
        Coordinates startCoordinatesSlow = new Coordinates(140, 130);
        // TODO handle case, when robot is started with a wrong position-direction combination. Like x=-12, y=-12, direction=EAST
        //      it would work for counter-clockwise, but the robot wants to move clockwise -> would generate exception
        Robot slowRectangularRobot = new RobotRectangular("slowRectangularRobot",
                startCoordinatesSlow, Direction.NORTH, box2, 250, 10);

        Line line = new Line(
                new Coordinates(130, 120),
                new Coordinates(440, 120)
        );
        Coordinates startCoordinatesLinear = new Coordinates(130, 120);
        // TODO handle case, when robot is started with a wrong position-direction combination. Like x=0, y=0, direction=EAST
        //      it would work for counter-clockwise, but the robot wants to move clockwise -> would generate exception
        Robot linearRobot = new RobotLinear("linearRobot",
                startCoordinatesLinear, Direction.EAST, line, 50, 10);

//        Flux.interval(Duration.ofMillis(1000))
//                .map(timeToPollPosition -> fastRobot1.getCurrentPosition())
//                .subscribe(position -> System.out.printf("poll position [x: %d, y: %d]%n", position.getX(), position.getY()));

        robotService.registerRobot(fastRectangularRobot);
        fastRectangularRobot.startMoving();
        fastRectangularRobot.coordinatesFeed()
                .subscribe(
                        position -> log.info(String.format("observe coordinates fastRectangularRobot1 [x: %d, y: %d]", position.x(), position.y()))
                );

        TimeUtil.waitForMillis(1534);

        robotService.registerRobot(slowRectangularRobot);
        slowRectangularRobot.startMoving();
        slowRectangularRobot.coordinatesFeed()
                .subscribe(
                        position -> log.info(String.format("observe coordinates slowRectangularRobot2 [x: %d, y: %d]", position.x(), position.y()))
                );

        TimeUtil.waitForMillis(1770);

        robotService.registerRobot(linearRobot);
        linearRobot.startMoving();
        linearRobot.coordinatesFeed()
                .subscribe(
                        position -> log.info(String.format("observe coordinates linearRobot [x: %d, y: %d]", position.x(), position.y()))
                );
    }
}