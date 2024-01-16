package com.beans;

public class Player {
    private Long id;
    private String name;

    private String teamName;


    public Player(Long id, String name, String teamName) {
        super();
        this.id = id;
        this.name = name;
        this.teamName = teamName;

    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }


    public String getTeamName() {
        return teamName;
    }

    @Override
    public String toString() {
        return "Player [id=" + id + ", name=" + name + ", position=" +  ", teamId=" + teamName + "]";
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }
}
