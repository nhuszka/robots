package com.nhuszka.robots.store;

import com.nhuszka.robots.DemoRobotCreator;
import com.nhuszka.robots.geometry.Box;
import com.nhuszka.robots.geometry.Coordinates;
import com.nhuszka.robots.geometry.Direction;
import com.nhuszka.robots.geometry.Line;
import com.nhuszka.robots.model.CreateRobotModel;
import com.nhuszka.robots.model.RobotType;
import com.nhuszka.robots.model.RobotView;
import com.nhuszka.robots.model.RobotSpeed;
import com.nhuszka.robots.robot.Robot;
import com.nhuszka.robots.robot.RobotLinear;
import com.nhuszka.robots.robot.RobotRectangular;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RobotService {

    private static final List<Robot> ROBOTS = new ArrayList<>();

    private final DemoRobotCreator demoRobotCreator;

    public RobotService(DemoRobotCreator demoRobotCreator) {
        this.demoRobotCreator = demoRobotCreator;
    }

    public void demoRobots() {
        if (ROBOTS.isEmpty()) {
            demoRobotCreator.startDemoRobots(this);
        } else {
            ROBOTS.forEach(Robot::startMoving);
        }
    }

    public synchronized void registerRobot(Robot robot) {
        ROBOTS.add(robot);
    }

    public void stopAllRobots() {
        for (Robot robot : ROBOTS) {
            robot.stopMoving();
        }
    }

    public void removeAllRobots() {
        for (Robot robot : ROBOTS) {
            robot.stopMoving();
        }
        ROBOTS.clear();
    }

    public List<Coordinates> listRobotCoordinates() {
        return ROBOTS.stream()
                .map(Robot::getCurrentPosition)
                .collect(Collectors.toList());
    }

    public List<RobotView> listRobots() {
        return ROBOTS.stream()
                .map(robot -> new RobotView(robot.getName(), robot.getCurrentPosition()))
                .collect(Collectors.toList());
    }

    public void addRobot(CreateRobotModel createRobotModel) {
        Robot robot = createRobot(createRobotModel);

        registerRobot(robot);
        robot.startMoving();
        robot.coordinatesFeed()
                .subscribe(
                        position -> log.info(String.format("observe coordinates %s [x: %d, y: %d]",
                                robot.getName(), position.x(), position.y()))
                );
    }

    private Robot createRobot(CreateRobotModel createRobotModel) {
        Coordinates startCoordinates = createRobotModel.getStartCoordinates();
        int dimensionOfPath = createRobotModel.getDimensionOfPath();
        int startX = startCoordinates.x();
        int startY = startCoordinates.y();
        Coordinates bottomLeft = new Coordinates(startX, startY);
        int moveDistance = RobotSpeed.FAST.equals(createRobotModel.getSpeed()) ? 20
                : RobotSpeed.NORMAL.equals(createRobotModel.getSpeed()) ? 10
                : 5;
        String name = createRobotModel.getName();

        Robot robot = null;
        if (RobotType.RECTANGULAR.equals(createRobotModel.getType())) {
            Box box = new Box(
                    new Coordinates(startX, startY + dimensionOfPath),
                    new Coordinates(startX + dimensionOfPath, startY + dimensionOfPath),
                    new Coordinates(startX + dimensionOfPath, startY),
                    bottomLeft
            );
            robot = new RobotRectangular(name, startCoordinates, Direction.NORTH, box, 100, moveDistance);

        }
        if (RobotType.LINEAR.equals(createRobotModel.getType())) {
            Line line = new Line(
                    new Coordinates(startX, startY),
                    new Coordinates(startX + dimensionOfPath, startY)
            );
            robot = new RobotLinear(name, startCoordinates, Direction.EAST, line, 100, moveDistance);
        }

        // TODO handle invalid input
        return robot;
    }
}
