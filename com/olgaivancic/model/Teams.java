package com.olgaivancic.model;

import java.util.ArrayList;
import java.util.Collections;
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
    public void outputTeams() {
        System.out.printf("There are %d team(s) available:%n%n", mTeams.size());

        // Sort teams alphabetically.
        Collections.sort(mTeams, (team1, team2) -> team1.compareTo(team2));

        // Output the sorted list of teams.
        mTeams.forEach(team -> System.out.printf("Team #%d: %s%n", mTeams.indexOf(team) + 1, team));
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

    /**
     * This method locates the team (passed as a parameter) in the list of teams.
     *
     * @param chosenTeam
     * @return The list of players of the specified team.
     */
    public ArrayList<Player> findTeamPlayers(Team chosenTeam) {
        ArrayList<Player> players = new ArrayList<>();
        // Find the team from the list of teams
        for (int i = 0; i < mTeams.size(); i++) {
            if (chosenTeam.equals(mTeams.get(i))) {
                players = mTeams.get(i).getTeamPlayers();
            }
        }

        return players;
    }

    /**
     * This method removes player from the chosen team. Both player and team are passed to the method as parameters.
     *
     * @param team
     * @param player
     */
    public void removePlayer(Team team, Player player) {
        for (int i = 0; i < mTeams.size(); i++) {
            if (team.equals(mTeams.get(i))) {
                mTeams.get(i).getTeamPlayers().remove(player);
            }
        }
    }

    /**
     * This method checks to make sure the name of a new team hasn't been used before.
     *
     * @param teamName
     * @return True if the name of the team is original; false if the name of the team is already in use.
     */
    public boolean checkIfTeamNameIsNotARepeat(String teamName) {
        for (int i = 0; i < mTeams.size(); i++) {
            if (mTeams.get(i).getTeamName().equalsIgnoreCase(teamName)) {
                return false;
            }
        }
        return true;
    }

    /**
     * This method checks to make sure the name of the coach hasn't been used before.
     *
     * @param coach
     * @return True if the name of the coach is original; false if the name of the coach is already in use.
     */
    public boolean checkIfCoachIsNotARepeat(String coach) {
        for (int i = 0; i < mTeams.size(); i++) {
            if (mTeams.get(i).getCoach().equalsIgnoreCase(coach)) {
                return false;
            }
        }
        return true;
    }
}
