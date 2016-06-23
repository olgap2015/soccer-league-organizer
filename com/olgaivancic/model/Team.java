package com.olgaivancic.model;

import java.util.ArrayList;

/**
 * This Class models the team of players. Players are unique.
 */
public class Team {

    private String mTeamName;
    private String mCoach;
    private ArrayList<Player> mTeamPlayers;

    public Team(String teamName, String coach) {
        mTeamName = teamName;
        mCoach = coach;
        mTeamPlayers = new ArrayList<Player>();
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

    public ArrayList<Player> getTeamPlayers() {
        return mTeamPlayers;
    }

    public void setTeamPlayers(ArrayList<Player> teamPlayers) {
        mTeamPlayers = teamPlayers;
    }

    /**
     * This method adds a player to the team.
     */
    /*public void addPlayer(Player player) {
        mTeamPlayers.add(player);
    }*/
    @Override
    public String toString() {
        return mTeamName + " (coach - " + mCoach + ")";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;

        Team team = (Team) o;

        if (mTeamName != team.mTeamName) return false;
        return mCoach.equals(team.mCoach);
    }
}
