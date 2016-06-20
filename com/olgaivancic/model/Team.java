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

    //TODO: possibly need to change the constructor adding name and coach parameters.
    public Team() {
        mTeamPlayers = new TreeSet<Player>();
    }
}
