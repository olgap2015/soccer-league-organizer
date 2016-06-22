package com.olgaivancic.model;

import java.util.Set;
import java.util.TreeSet;

/**
 * This Class models the team of players. Players are unique.
 */
public class Team {

    private String mTeamName;
    private String mCoach;
    private Set<Player> mTeamPlayers;

    public Team(String teamName, String coach) {
        mTeamName = teamName;
        mCoach = coach;
        mTeamPlayers = new TreeSet<Player>();
    }

    public String getTeamName() {
        return mTeamName;
    }

    public void setTeamName(String teamName) {
        mTeamName = teamName;
    }

    public String getCoach() {
        return mCoach;
    }

    public void setCoach(String coach) {
        mCoach = coach;
    }

    public Set<Player> getTeamPlayers() {
        return mTeamPlayers;
    }

    public void setTeamPlayers(Set<Player> teamPlayers) {
        mTeamPlayers = teamPlayers;
    }


}
