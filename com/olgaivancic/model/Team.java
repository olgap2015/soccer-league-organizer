package com.olgaivancic.model;

import java.util.*;

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

    /**
     * This method creates a map of team players by height evaluations.
     *
     * @return Map of the team players by their height.
     */
    /*public Map<Integer, List<Player>> byHeightEvaluations() {
        Map<Integer, List<Player>> byHeightEvaluations = new TreeMap<>();
        for (Player player : mTeamPlayers) {
            List<Player> playerHeights = byHeightEvaluations.get(player.getHeightInInches());
            if (playerHeights == null) {
                playerHeights = new ArrayList<>();
                byHeightEvaluations.put(player.getHeightInInches(), playerHeights);
            }
            playerHeights.add(player);
        }
        return byHeightEvaluations;
    }*/
    public Map<String, List<Player>> byHeightEvaluations() {
        Map<String, List<Player>> byHeight = new TreeMap<>();
        for (Player player : mTeamPlayers) {
            List<Player> playerHeightEvaluations = byHeight.get(player.getHeightEvaluation());
            if (playerHeightEvaluations == null) {
                playerHeightEvaluations = new ArrayList<>();
                byHeight.put(player.getHeightEvaluation(), playerHeightEvaluations);
            }
            playerHeightEvaluations.add(player);
        }
        return byHeight;
    }


    public Set<String> getHeightEvaluations() {
        return byHeightEvaluations().keySet();
    }

    /**
     * This method looks through the list of players and finds the ones that match certain height
     * passed as a parameter.
     * @param heightEvaluation Height of a player in inches
     * @return List of players that match certain height
     */
    public List<Player> getPlayersForHeight(String heightEvaluation) {
        List<Player> players = byHeightEvaluations().get(heightEvaluation);
        Collections.sort(players, (p1, p2) -> p1.compareTo(p2));
        return players;
    }

    /**
     * This method calculates the percentage (1-100) of experienced players in the team.
     * If there are no players on the team, it returns -1.
     *
     * @return Percentage of experienced players in the team. If there are no players on the team, it returns -1.
     */
    public int calculatePercentOfExperPlayers() {
        int numberOfExperPlayers = 0;
        int percentOfExperPlayers = -1;

        if (mTeamPlayers.size() > 0) {
            for (Player player : mTeamPlayers) {
                if (player.hasPreviousExperience()) {
                    numberOfExperPlayers++;
                }
            }
            int teamSize = mTeamPlayers.size();
            percentOfExperPlayers = numberOfExperPlayers * 100 / teamSize;
        }
        return percentOfExperPlayers;
    }
}
