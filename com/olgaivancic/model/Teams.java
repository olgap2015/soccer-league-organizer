package com.olgaivancic.model;

import java.util.ArrayList;
import java.util.List;

/**
 * This Class represents the list of the teams. There can't be more teams than the players available on Players[] list.
 */
public class Teams {
    private List<Team> mTeams;
    private Players mPlayers;
    private int mTotalAvailablePlayers;

    public Teams(Players players) {
        mTeams = new ArrayList<Team>();
        mPlayers = players;
        mTotalAvailablePlayers = players.getPlayers().size();
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

    /**
     * This method adds a single team passed as a parameter to the list of teams
     *
     * @param team
     */
    public void addTeam(Team team) {
        mTeams.add(team);
    }

    /**
     * This method outputs the list of teams on the console.
     */
    public void outputListOfTeams() {
        System.out.printf("There are %d team(s) available:%n%n", mTeams.size());
        int i = 0;
        for (i = 0; i < mTeams.size(); i++) {
            System.out.printf("Team #%d: %s%n", i + 1, mTeams.get(i).toString());
        }
    }

    /**
     * This method adds player to a team. Both player and team are passed to the method as parameters.
     *
     * @param team
     * @param player
     */
    public void addPlayer(Team team, Player player) {
        for (int i = 0; i < mTeams.size(); i++) {
            if (team.equals(mTeams.get(i))) {
                mTeams.get(i).getTeamPlayers().add(player);
            }
        }
    }
}
