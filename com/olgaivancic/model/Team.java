package com.olgaivancic.model;

import java.util.ArrayList;
import java.util.Collections;

/**
 * This Class models the team of players. Players are unique.
 */
public class Team implements Comparable<Team> {

    public final static int MAX_NUMBER_OF_PLAYERS = 11;
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

    @Override
    public int compareTo(Team team) {
        // Compare teams by team name
        if (this.equals(team)) {
            return 0;
        }
        return mTeamName.compareTo(team.getTeamName());
    }

    /**
     * This method sorts the list of team players alphabetically (by last name, then by first name)
     * and outputs the list on the console.
     */
    public void outputPlayers() {
        // Sort the list of players alphabetically
        Collections.sort(mTeamPlayers, (p1, p2) -> p1.compareTo(p2));

        // Output the sorted list on the console
        mTeamPlayers.forEach(player -> System.out.printf("Player #%d: %s%n",
                mTeamPlayers.indexOf(player) + 1, player));
    }
}
