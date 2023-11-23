package com.nhuszka.robots;

import com.nhuszka.robots.geometry.Coordinates;
import com.nhuszka.robots.store.RobotRegistry;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RobotController {

    @Autowired
    private RobotRegistry robotRegistry;

    @GetMapping("/startRobots")
    public String startRobots() {
        new RobotStarter(robotRegistry).startRobots();
        return "Robots started.";
    }

    @GetMapping("/coordinates")
    public List<Coordinates> listCoordinates() {
        return robotRegistry.listRobotCoordinates();
    }
}
