package com.controller;

import com.beans.Player;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Api(value = "PlayerController", description = "REST Apis related to Player")
@RestController
@Service
@RequestMapping("/players")
public class PlayerServiceController {

    @Autowired
    private RestTemplate restTemplate;
    private final String teamServiceUrl = "http://localhost:8080/teams/teamsName";
    private Map<Long, Player> players = new HashMap<>();

    @Autowired
    public PlayerServiceController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;

        initializeListOfPlayers();
    }

    private List<String> listOfTeams() {
        return restTemplate.exchange(teamServiceUrl, HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
        }).getBody();
    }


    private String randomTeam() {
        List<String> teams = listOfTeams();
        if (teams != null && !teams.isEmpty()) {
            int random = (int) (Math.random() * teams.size());
            return teams.get(random);
        }
        return null;
    }

    private void initializeListOfPlayers() {
        for (long i = 1; i <= 14; i++) {
            players.put(i, new Player(i, "Player" + i, randomTeam()));
        }
    }
    @ApiOperation(value = "Get all players ", response = Iterable.class, tags = "getplayers")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @GetMapping("/")
    public ResponseEntity<Map<Long, Player>> getAllPlayers() {
        return ResponseEntity.ok(players);
    }
    @ApiOperation(value = "Get player by id ", response = Iterable.class, tags = "getplayerbyid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @GetMapping("/{id}")
    public ResponseEntity<Player> getPlayerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(players.get(id));
    }
    @ApiOperation(value = "update player ", response = Iterable.class, tags = "updateplayer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @PutMapping
    public ResponseEntity<String> updatePlayer(@RequestBody Player player) {
        players.put(player.getId(), player);
        return ResponseEntity.ok("Player updated successfully");
    }
    @ApiOperation(value = "createplayer ", response = Iterable.class, tags = "createplayer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @PostMapping
    public ResponseEntity<String> createPlayer(@RequestBody Player player) {
        player.setTeamName(randomTeam());
        players.put(player.getId(), player);
        return ResponseEntity.ok("Player created successfully");
    }
    @ApiOperation(value = "delete player", response = Iterable.class, tags = "deleteplayer")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deletePlayerById(@PathVariable("id") Long id) {
        players.remove(id);
        return ResponseEntity.ok("Player deleted successfully");
    }
    private String GeneralFallback(){
        System.out.println("Service down !!!!!!!!!!!!! FallBack General");
        return "Circuit Breaker enabled";

    }
}
