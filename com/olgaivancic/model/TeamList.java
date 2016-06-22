package com.olgaivancic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents the list of the teams. There can't be more teams than the players available on Players[] list.
 */
public class TeamList {
    private List<Team> mTeams;
    private final int mTotalAvailablePlayers;

    public TeamList(int totalAvailablePlayers) {
        mTeams = new ArrayList<Team>();
        mTotalAvailablePlayers = totalAvailablePlayers;
    }

    public int getTotalAvailablePlayers() {
        return mTotalAvailablePlayers;
    }

    public List<Team> getTeams() {
        return mTeams;
    }

    public void setTeams(List<Team> teams) {
        mTeams = teams;
    }

    public void addTeam(Team team) {
        mTeams.add(team);
    }
}
