package com.beans;

import java.util.List;

public class Team {
    private Long id;
    private String name;


    public Team(Long id, String name) {
        super();
        this.id = id;
        this.name = name;
    }
    public String getName() {
        return name;
    }
    public Long getId() {
        return id;
    }


    @Override
    public String toString() {
        return "Team [id=" + id + ", name=" + name  +"]";
    }


    public void setName(String name) {
        this.name = name;
    }
}
