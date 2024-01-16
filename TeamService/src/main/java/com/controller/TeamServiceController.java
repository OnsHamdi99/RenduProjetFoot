package com.controller;

import com.beans.Team;


import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.netflix.ribbon.proxy.annotation.Hystrix;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "TeamController", description = "REST Apis related to Team")
@RestController
@RequestMapping("/teams")
@Service
@EnableHystrix
public class TeamServiceController {
    public Map<Long, Team> teams = new HashMap<>();

    //initializing teams
    {
        Team team1 = new Team(1L, "Real Madrid");
        Team team2 = new Team(2L, "Barcelona");
        Team team3 = new Team(3L, "Bayern Munich");
        Team team4 = new Team(4L, "Manchester United");
        Team team5 = new Team(5L, "Juventus");
        teams.put(team1.getId(), team1);
        teams.put(team2.getId(), team2);
        teams.put(team3.getId(), team3);
        teams.put(team4.getId(), team4);
        teams.put(team5.getId(), team5);
    }
    @ApiOperation(value = "Get all Teams ", response = Iterable.class, tags = "getteams")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @GetMapping("/")
    public ResponseEntity<Map<Long, Team>> getAllTeams() {
        return ResponseEntity.ok(teams);
    }
    @ApiOperation(value = "Get Teams names", response = Iterable.class, tags = "getteamsname")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @GetMapping("/teamsName")
        public ResponseEntity<List<String>> getAllTeamsName() {
            List<String> teamsName = new ArrayList<>();
            for (Team team : teams.values()) {
                teamsName.add(team.getName());
            }
            return ResponseEntity.ok(teamsName);
        }
    @ApiOperation(value = "Get Team by id", response = Iterable.class, tags = "getteam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @GetMapping("/{id}")
    public ResponseEntity<Team> getTeamById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(teams.get(id));
    }
    @ApiOperation(value = "Delete Team by ", response = Iterable.class, tags = "deleteteam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteTeamById(@PathVariable("id") Long id) {
        teams.remove(id);
        return ResponseEntity.ok("Team deleted successfully");

    }
    @ApiOperation(value = "Create Team ", response = Iterable.class, tags = "createteam")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @PostMapping
    public ResponseEntity<String> createTeam(@RequestBody Team team) {
        teams.put(team.getId(), team);
        return ResponseEntity.ok("Team created successfully");
    }
    @ApiOperation(value = "Update Team ", response = Iterable.class, tags = "updateteams")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @PutMapping("/{id}")
    public ResponseEntity<Team> updateTeam(@PathVariable Long id, @RequestBody Team updatedTeam) {
        Team team = teams.get(id);
        if (team != null) {
            team.setName(updatedTeam.getName());
            return ResponseEntity.ok(team);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private String GeneralFallback(){
        System.out.println("Service down !!!!!!!!!!!!! FallBack General");
        return "Circuit Breaker enabled";

    }


}