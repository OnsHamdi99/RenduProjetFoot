package com.beans;

public class Match {
    //match has a name, an id, a team id, a second team id
    private Long id;
    private String name;
    private Long firstTeamId;
    private Long secondTeamId;

    public Match(Long id, String name, Long firstTeamId, Long secondTeamId) {
        super();
        this.id = id;
        this.name = name;
        this.firstTeamId = firstTeamId;
        this.secondTeamId = secondTeamId;
    }

    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }
    public Long getFirstTeamId() {
        return firstTeamId;
    }
    public Long getSecondTeamId() {
        return secondTeamId;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setFirstTeamId(Long firstTeamId) {
        this.firstTeamId = firstTeamId;
    }
    public void setSecondTeamId(Long secondTeamId) {
        this.secondTeamId = secondTeamId;
    }
    public String toString() {
        return "Match [id=" + id + ", name=" + name + ", firstTeamId=" + firstTeamId + ", secondTeamId=" + secondTeamId + "]";
    }
}
