package com.controller;

import com.beans.Stats;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@Service
@RequestMapping("/stats")
public class StatsServiceController {
    @Autowired
    private RestTemplate restTemplate;
    private final String teamServiceUrl = "http://localhost:8080/teams/teamsName";
    private final String playerServiceUrl = "http://localhost:8081/players/";

    private Map<Long, Stats> players = new HashMap<>();

    private Map<Long, Stats> teams = new HashMap<>();

    @Autowired
    public StatsServiceController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
        initializeListOfPlayers();
        initializeListOfTeams();
    }
    private List listOfPlayers() {
        return restTemplate.exchange(playerServiceUrl,HttpMethod.GET, null, new ParameterizedTypeReference<List<String>>() {
        }).getBody();
    }

    private void initializeListOfPlayers(){

        for (int i = 1; i <= 14; i++) {
            players.put(listOfPlayers().get(i), new Stats(, i,i, i))
        }
    }
}
