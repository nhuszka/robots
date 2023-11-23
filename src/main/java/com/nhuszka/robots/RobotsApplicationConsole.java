package com.nhuszka.robots;

import com.nhuszka.robots.store.RobotRegistry;
import com.nhuszka.robots.util.TimeUtil;

import java.util.logging.Logger;

public class RobotsApplicationConsole {

    private static Logger log = Logger.getLogger(RobotsApplicationConsole.class.getSimpleName());

    public static void main(String[] args) {
        RobotRegistry robotRegistry = new RobotRegistry();
        new RobotStarter(robotRegistry).startRobots();
        log.info("Waiting for program to finish.");
        TimeUtil.waitFor(5);
        log.info("Last known position of robots: " + robotRegistry.listRobotCoordinates());
    }
}
