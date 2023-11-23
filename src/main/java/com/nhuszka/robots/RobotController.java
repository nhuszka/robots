package com.nhuszka.robots;

import com.nhuszka.robots.model.CreateRobotModel;
import com.nhuszka.robots.model.RobotView;
import com.nhuszka.robots.store.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RobotController {

    @Autowired
    private RobotService robotService;

    @GetMapping("/demoRobots")
    public String demoRobots() {
        robotService.demoRobots();
        return "Demo robots started.";
    }

    @PostMapping("/addRobot")
    public String addRobot(@RequestBody CreateRobotModel createRobotModel) {
        robotService.addRobot(createRobotModel);
        return "Robots added.";
    }

    @GetMapping("/stopRobots")
    public String stopRobots() {
        robotService.stopAllRobots();
        return "Robots stopped.";
    }

    @GetMapping("/removeRobots")
    public String removeRobots() {
        robotService.removeAllRobots();
        return "Robots removed.";
    }

    @GetMapping("/robots")
    public List<RobotView> listRobots() {
        return robotService.listRobots();
    }
}
