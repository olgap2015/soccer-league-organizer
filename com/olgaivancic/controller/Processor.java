package com.olgaivancic.controller;

import com.olgaivancic.model.Player;
import com.olgaivancic.model.Players;
import com.olgaivancic.model.Team;
import com.olgaivancic.model.Teams;
import com.olgaivancic.view.Prompter;

import java.io.IOException;
import java.util.*;

public class Processor {

    private Players mPlayers;
    private Teams mTeams;
    private Map<String, List<Player>> mByHeightEvaluation;
    private Prompter mPrompter;

    public Processor(Players players, Teams teams, Prompter prompter) {
        mPlayers = players;
        mTeams = teams;
        mByHeightEvaluation = new TreeMap<>();
        mPrompter = prompter;
    }

    /**
     * Runs the mPrompter methods of the program.
     */
    public void run() {
        String choice = "";
        do {
            try {
                choice = mPrompter.promptAction();
                boolean tooManyTeams = mTeams.getTeams().size() == mPlayers.getPlayers().size();
                boolean noPlayersOnTheWaitingList = mPlayers.getPlayers().size() == 0;

                switch (choice.toLowerCase()) {
                    case "1":
                        if (tooManyTeams) {
                            System.out.println("\nYou can't create a new team because you have already created " +
                                    "the maximum amount of teams allowed!\n");
                            break;
                        } else if (noPlayersOnTheWaitingList) {
                            System.out.println("\nYou can't add a new team because there are no players " +
                                    "on the waiting list!\n");
                        } else {
                            Team team = mPrompter.promptForNewTeam();
                            mTeams.addTeam(team);
                            System.out.printf("%nNew team \"%s\" is created!  %n%n", team.getTeamName());
                        }
                        break;
                    case "2":
                        if (mTeams.getTeams().size() == 0) {
                            System.out.println("\nYou can't add a player because there are no teams created yet." +
                                    "\nPlease, start by creating a team first!");
                            break;
                        } else if (noPlayersOnTheWaitingList) {
                            System.out.println("\nYou can't add a player because there are no players on the " +
                                    "waiting list!\n");

                        }

                        // Choose the team
                        Team chosenTeam = mPrompter.promptForTeam();

                        // Make sure that the team didn't hit the max number of players yet.
                        if (chosenTeam.getTeamPlayers().size() == Team.MAX_NUMBER_OF_PLAYERS) {
                            System.out.printf("%nSorry, this team already has a maximum number " +
                                    "of players allowed - %d.%n%n", Team.MAX_NUMBER_OF_PLAYERS);
                            break;
                        }

                        // Output list of players.
                        mPrompter.outputPlayers();

                        // choose a player from the list of available players.
                        Player player = mPrompter.promptForPlayer();

                        // Add the chosen player to the chosen team
                        addPlayer(chosenTeam, player);
                        break;
                    case "3":
                        // choose the team
                        if (mTeams.getTeams().size() == 0) {
                            System.out.println("\nYou can't remove a player because there are no teams created yet." +
                                    "\nPlease, start by creating a team first and adding players!");
                            break;
                        }
                        chosenTeam = mPrompter.promptForTeam();

                        // Find and display players from that team
                        if (chosenTeam.getTeamPlayers().size() == 0) {
                            System.out.println("\nYou can't remove a player because there are no players in this team.\n" +
                                    "Please, start by adding players to this team first!");
                            break;
                        }

                        // Output alphabetically sorted list of players
                        chosenTeam.outputPlayers();

                        // Get the list of team players
                        ArrayList<Player> teamPlayers = chosenTeam.getTeamPlayers();

                        // Prompt for the player's number
                        player = mPrompter.promptForPlayer(teamPlayers);

                        // Remove player from the team
                        removePlayer(chosenTeam, player);
                        break;
                    case "4":
                        // Choose a team for the report.
                        chosenTeam = mPrompter.promptForTeam();

                        // Output height report for the chosen team if it has players.
                        runHeightReport(chosenTeam);
                        break;
                    case "5":
                        if (mTeams.getTeams().size() == 0) {
                            System.out.println("\nThere are no teams created yet!");
                        } else {
                            runLeagueBalanceReport();
                        }
                        break;
                    case "6":
                        if (mTeams.getTeams().size() == 0) {
                            System.out.println("There are no teams created yet!\n");
                            break;
                        }
                        chosenTeam = mPrompter.promptForTeam();
                        printRoster(chosenTeam);
                        break;
                    case "quit":
                        System.out.println("Thanks for using League Manager. Bye!");
                        break;
                    default:
                        System.out.printf("Unknown choice: \"%s\". Please, try again!  %n%n",
                                choice);
                }
            } catch (IOException ioe) {
                System.out.println("Unable to read your input. Please, try again!");
            }
        } while (!choice.equals("quit"));

    }

    /**
     * Helper method that removes chosen player from the chosen team and adds that player back
     * to the waiting list. Both the team and the player are passed as parameters.
     *
     * @param chosenTeam
     * @param player
     */
    private void removePlayer(Team chosenTeam, Player player) {
        // Remove player from the team
        mTeams.removePlayer(chosenTeam, player);

        // Add player to the waiting list
        mPlayers.getPlayers().add(player);

        System.out.printf("%nPlayer %s %s was removed from the team \"%s\".%n",
                player.getFirstName(),
                player.getLastName(),
                chosenTeam.getTeamName());

        outputSortedTeamPlayers(chosenTeam);
    }

    /**
     * Helper method that adds chosen player to the chosen team and removes the player from the
     * waiting list. Both the team and the player are passed as parameters.
     *
     * @param chosenTeam
     * @param player
     */
    private void addPlayer(Team chosenTeam, Player player) {
        // Add player to the chosen team
        mTeams.addPlayer(chosenTeam, player);
        // Remove that player from the waiting list.
        mPlayers.getPlayers().remove(player);
        // Output the current list of players
        System.out.printf("%nPlayer %s %s was added to the team \"%s\".%n",
                player.getFirstName(),
                player.getLastName(),
                chosenTeam.getTeamName());

        outputSortedTeamPlayers(chosenTeam);
    }

    /**
     * This method outputs a roster on the console containing current list of players and their stats.
     * It also displays the percentage of experienced players in the team.
     *
     * @param chosenTeam
     */
    private void printRoster(Team chosenTeam) {
        System.out.printf("%nROSTER for team \"%s\".%n", chosenTeam.getTeamName());
        if (chosenTeam.getTeamPlayers().size() == 0) {
            System.out.println("\nThere are no players in this team!\n");
            return;
        }
        System.out.printf("%nCoach - %s.%n", chosenTeam.getCoach());
        System.out.printf("%d%% of players have previous experience.%n%n",
                chosenTeam.calculatePercentOfExperPlayers());

        outputSortedTeamPlayers(chosenTeam);
        System.out.println();
    }

    /**
     * This method sorts in alphabetical order and outputs on the console the list of
     * players from the particular teams.
     *
     * @param chosenTeam
     */
    private void outputSortedTeamPlayers(Team chosenTeam) {
        System.out.printf("Current list of players for \"%s\":%n",
                chosenTeam.getTeamName());

        Collections.sort(chosenTeam.getTeamPlayers(), (p1, p2) -> p1.compareTo(p2));

        for (Player player : chosenTeam.getTeamPlayers()) {
            System.out.printf("%d. %s%n",
                    chosenTeam.getTeamPlayers().indexOf(player) + 1,
                    player.toString());
        }
        System.out.println();
    }

    /**
     * This method outputs on the console report that contains break down
     * of each team by experience. If a team doesn't have players, then
     * "no players on the team" is displayed for that team.
     * Precondition: there must be at least one team created.
     */
    private void runLeagueBalanceReport() {
        // Create an updated map of teams by the percentage of experienced players in each team.
        Map<Team, Integer> teamsByExperience = mTeams.byExperience();
        System.out.printf("%47s%n%n", "EXPERIENCE REPORT");
        System.out.printf("%37s%20s%n", "Experienced", "Inexperienced");
        System.out.printf("%37s%18s%n%n", "players, %", "players, %");

        // Loop through the teams to get the keys and values to use in the report
        for (Map.Entry<Team, Integer> entry : teamsByExperience.entrySet()) {
            // If there are no players in the team display a message instead of stats.
            if (entry.getValue() == -1) {
                System.out.printf("%-30s%-24s%n",
                        "Team \"" + entry.getKey().getTeamName() + "\"",
                        "No players in this team!");
            } else {
                System.out.printf("%-30s%-18d%-3d%n",
                        "Team \"" + entry.getKey().getTeamName() + "\"",
                        entry.getValue(),
                        100 - entry.getValue());
            }
        }
        System.out.println();

        // Output the header of the table
        System.out.printf("%47s%n%n", "HEIGHT REPORT");
        System.out.printf("%35s%13s%18s%n", "Below Average", "Average", "Above Average");
        System.out.printf("%32s%16s%15s%n", "players", "players", "players");
        System.out.printf("%33s%17s%14s%n%n", "(< 39 in)", "(39 – 42 in)", "(> 42 in)");

        // loop through each team to get the data out of the map into the table
        for (Team team : mTeams.getTeams()) {
            mByHeightEvaluation = team.byHeightEvaluations();

            // If there are no players in the team display a message instead of stats.
            if (team.getTeamPlayers().size() == 0) {
                System.out.printf("%-30s%33s%n",
                        "Team \"" + team.getTeamName() + "\"",
                        "No   players   in   this   team");
            } else {
                int shortPlayerCount = countTeamPlayersByHeight("Below Average");
                int averagePlayerCount = countTeamPlayersByHeight("Average");
                int tallPlayerCount = countTeamPlayersByHeight("Above Average");
                System.out.printf("%-30s%-16d%-15d%-5d%n",
                        "Team \"" + team.getTeamName() + "\"",
                        shortPlayerCount,
                        averagePlayerCount,
                        tallPlayerCount);
            }
        }
        System.out.println();
    }

    private int countTeamPlayersByHeight(String heightCategory) {
        int playersCount = 0;
        if (mByHeightEvaluation.containsKey(heightCategory)) {
            playersCount = mByHeightEvaluation.get(heightCategory).size();
        }
        return playersCount;
    }

    /**
     * This method outputs a report about players of a particular team grouped by players' height.
     *
     * @param chosenTeam Team of players
     */
    private void runHeightReport(Team chosenTeam) {
        System.out.println("\nHEIGHT REPORT for team \"" + chosenTeam.getTeamName() + "\"\n");

        if (chosenTeam.getTeamPlayers().size() == 0) {
            System.out.println("This team currently doesn't have any players.\n");
        }

        for (String heightEvaluation : chosenTeam.getHeightEvaluations()) {
            List<Player> playersForHeight = chosenTeam.getPlayersForHeight(heightEvaluation);
            Collections.sort(playersForHeight, (p1, p2) -> p1.compareTo(p2));
            String string1;
            if (heightEvaluation.equals("Below Average")) {
                string1 = "< 39";
            } else if (heightEvaluation.equals("Average")) {
                string1 = "39 - 42";
            } else {
                string1 = "> 42";
            }
            int playersCount = playersForHeight.size();
            System.out.printf("%s (%s in) - %d players:%n",
                    heightEvaluation,
                    string1,
                    playersCount);
            for (Player player : playersForHeight) {
                System.out.printf("\t- %s, %s (%d in)%n",
                        player.getLastName(),
                        player.getFirstName(),
                        player.getHeightInInches());
            }
            System.out.println();
        }
    }


}
