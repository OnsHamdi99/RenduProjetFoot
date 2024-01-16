package com.beans;

public class Stats {
    private Long id;
    private Long wins;
    private Long losses;
    private Long ties;

    public Stats(Long id, Long wins, Long losses, Long ties) {
        super();
        this.id = id;
        this.wins = wins;
        this.losses = losses;
        this.ties = ties;
    }

    public Long getId() {
        return id;
    }
    public Long getWins() {
        return wins;
    }
    public Long getLosses() {
        return losses;
    }
    public Long getTies() {
        return ties;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String toString() {
        return "Stats [id=" + id + ", wins=" + wins + ", losses=" + losses + ", ties=" + ties + "]";
    }

}
