package com.controller;

import com.beans.Match;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
@Api(value = "MatchController", description = "REST Apis related to Match")
@RestController
@RequestMapping("/matches")
public class MatchServiceControler {

    private Map<Long, Match> matches = new HashMap<>();
    {
Match match1 = new Match(1L, "Real Madrid vs Barcelona", 1L, 2L);
        Match match2 = new Match(2L, "Bayern Munich vs Manchester United", 3L, 4L);
        Match match3 = new Match(3L, "Juventus vs Bayern Munich", 1L, 3L);
          Match match5 = new Match(5L, "Barcelona vs Manchester City", 4L, 2L);
        matches.put(match1.getId(), match1);
        matches.put(match2.getId(), match2);
        matches.put(match3.getId(), match3);
        matches.put(match5.getId(), match5);
    }
    @ApiOperation(value = "Get matches by id", response = Iterable.class, tags = "getmatchesbyid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @GetMapping("/{id}")
    public ResponseEntity<Match> getMatchById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(matches.get(id));
    }    @ApiOperation(value = "delete matches by id", response = Iterable.class, tags = "deletematchesbyid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteMatchById(@PathVariable("id") Long id) {
        matches.remove(id);
        return ResponseEntity.ok("Match deleted successfully");

    }
    @ApiOperation(value = "create match", response = Iterable.class, tags = "creatematch")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @PostMapping
    public ResponseEntity<String> createMatch(@RequestBody Match match) {
        matches.put(match.getId(), match);
        return ResponseEntity.ok("Match created successfully");
    }
    @ApiOperation(value = "update matches by id", response = Iterable.class, tags = "updateyid")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Suceess|OK"),
            @ApiResponse(code = 401, message = "not authorized!"),
            @ApiResponse(code = 403, message = "forbidden!!!"),
            @ApiResponse(code = 404, message = "not found!!!") })
    @HystrixCommand(fallbackMethod = "GeneralFallback", commandProperties = {@HystrixProperty(name = "execution.isolation.thread.timeoutInMillisecond", value = "1000")})
    @PutMapping
    public ResponseEntity<Match> updateMatch(@PathVariable Long id, @RequestBody Match updatedMatch) {
        Match match = matches.get(id);
        if (match != null) {
            match.setName(updatedMatch.getName());
            return ResponseEntity.ok(match);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    private String GeneralFallback(){
        System.out.println("Service down !!!!!!!!!!!!! FallBack General");
        return "Circuit Breaker enabled";

    }
}
