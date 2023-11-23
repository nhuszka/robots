package com.nhuszka.robots;

import com.nhuszka.robots.store.RobotService;
import com.nhuszka.robots.util.TimeUtil;

import java.util.logging.Logger;

public class RobotsApplicationConsole {

    private static Logger log = Logger.getLogger(RobotsApplicationConsole.class.getSimpleName());

    public static void main(String[] args) {
        RobotService robotService = new RobotService(new DemoRobotCreator());
        robotService.demoRobots();

        TimeUtil.waitFor(10);
        log.info("Stopping robots.");
        robotService.stopAllRobots();

        log.info("Waiting for program to finish.");
        TimeUtil.waitFor(5);
        log.info("Last known position of robots: " + robotService.listRobotCoordinates());
    }
}
