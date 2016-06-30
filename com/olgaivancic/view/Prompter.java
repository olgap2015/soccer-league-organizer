package com.olgaivancic.view;

import com.olgaivancic.model.Player;
import com.olgaivancic.model.Players;
import com.olgaivancic.model.Team;
import com.olgaivancic.model.Teams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * This Class is responsible for implementing I/O methods for creating and managing the teams.
 */
public class Prompter {

    private BufferedReader mReader;
    private Players mPlayers;
    private Map<String, String> mMenu;
    private Teams mTeams;


    public Prompter(Players players, Teams teams) {
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mPlayers = players;
        mMenu = new TreeMap<>();
        mMenu.put("1", "create a NEW TEAM");
        mMenu.put("2", "ADD a PLAYER to the team");
        mMenu.put("3", "REMOVE PLAYER from the team back to the waiting list");
        mMenu.put("4", "run a HEIGHT REPORT for a particular team");
        mMenu.put("5", "run a LEAGUE BALANCE REPORT");
        mMenu.put("6", "print (on the screen) a ROSTER for a particular team");
        mMenu.put("quit", "QUIT the program");
        mTeams = teams;
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice.toLowerCase()) {
                    case "1":
                        if (mTeams.getTeams().size() == mTeams.getTotalAvailablePlayers()) {
                            System.out.println("\nYou can't create a new team because you have already created " +
                                    "the maximum amount of teams allowed!\n");
                            break;
                        } else {
                            Team team = promptForNewTeam();
                            mTeams.addTeam(team);
                            System.out.printf("%nNew team \"%s\" is created!  %n%n", team.getTeamName());
                        }
                        break;
                    case "2":
                        if (mTeams.getTeams().size() == 0) {
                            System.out.println("\nYou can't add a player because there are no teams created yet." +
                                    "\nPlease, start by creating a team first!");
                            break;
                        }

                        // Choose the team
                        Team chosenTeam = promptForTeam();

                        // Make sure that the team didn't hit the max number of players yet.
                        if (chosenTeam.getTeamPlayers().size() == Team.MAX_NUMBER_OF_PLAYERS) {
                            System.out.printf("%nSorry, this team already has a maximum number " +
                                    "of players allowed - %d.%n%n", Team.MAX_NUMBER_OF_PLAYERS);
                            break;
                        }

                        // Output list of players.
                        mPlayers.outputPlayers();

                        // choose a player from the list of available players.
                        Player player = promptForPlayer(mPlayers);

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
                        chosenTeam = promptForTeam();

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
                        player = promptForPlayer(teamPlayers);

                        // Remove player from the team
                        removePlayer(chosenTeam, player);
                        break;
                    case "4":
                        // Choose a team for the report.
                        chosenTeam = promptForTeam();

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
                        chosenTeam = promptForTeam();
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
        // Create an updated map of teams mapped to percentage of experienced players in each team.
        Map<Team, Integer> teamsByExperience = mTeams.byExperience();

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

        for (String heightEvaluation : chosenTeam.getHeights()) {
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

    /**
     * This method prompts for player from the list of players passed as a parameter
     *
     * @param teamPlayers
     * @return teamPlayers (type ArrayList<Player>)
     */
    private Player promptForPlayer(ArrayList<Player> teamPlayers) {
        int playersNumber = 0;
        do {
            String playersNumberAsString = promptForInput("\nChoose the player by entering his number: ");
            try {
                playersNumber = Integer.parseInt(playersNumberAsString);
            } catch (NumberFormatException nfe) {
                System.out.println("Please, enter a whole number!");
            }
            // check if a number is within the proper range
            if (playersNumber <= 0 || playersNumber > teamPlayers.size()) {
                System.out.printf("Please, enter a number within the range of 1 to %d.%n", teamPlayers.size());
            }
        } while (playersNumber <= 0 || playersNumber > teamPlayers.size());


        return teamPlayers.get(playersNumber - 1);
    }

    private Team promptForTeam() {
        // Output the list of available teams.
        mTeams.outputTeams();

        // prompt for team number.
        int teamNumber = 0;
        do {
            String teamsNumberAsString = promptForInput("\nChoose the team by entering its number: ");
            try {
                teamNumber = Integer.parseInt(teamsNumberAsString);
            } catch (NumberFormatException nfe) {
                System.out.println("Please, enter a whole number!");
            }
            // check if a number is within the proper range
            if (teamNumber <= 0 || teamNumber > mTeams.getTeams().size()) {
                System.out.printf("Please, enter a number within the range of 1 to %d.%n", mTeams.getTeams().size());
            }
        } while (teamNumber <= 0 || teamNumber > mTeams.getTeams().size());


        return mTeams.getTeams().get(teamNumber - 1);
    }

    /**
     * This method prompts for player from the list of available players
     *
     * @param players
     * @return player (type Player)
     */
    private Player promptForPlayer(Players players) {

        int playersNumber = 0;
        do {
            String playersNumberAsString = promptForInput("\nChoose the player by entering his number: ");
            try {
                playersNumber = Integer.parseInt(playersNumberAsString);
            } catch (NumberFormatException nfe) {
                System.out.println("Please, enter a whole number!");
            }
            // check if a number is within the proper range
            if (playersNumber <= 0 || playersNumber > players.getPlayers().size()) {
                System.out.printf("Please, enter a number within the range of 1 to %d.%n", players.getPlayers().size());
            }
        } while (playersNumber <= 0 || playersNumber > players.getPlayers().size());


        return players.getPlayers().get(playersNumber - 1);
    }

    /**
     * This is a helper method that is designed to prompt User for input until a value is entered.
     *
     * @param promptingMessage
     * @return User's input as a String
     */
    private String promptForInput(String promptingMessage) {
        String choice = "";
        do {
            System.out.print(promptingMessage);
            try {
                choice = mReader.readLine();
            } catch (IOException ioe) {
                System.out.println("Unable to read your input. Please, try again!");
            }
        } while (choice.length() == 0);
        return choice;
    }

    private Team promptForNewTeam() throws IOException {
        String teamName = "";
        boolean teamNameIsNotARepeat = true;
        do {
            System.out.print("\nPlease, enter a name for the team:  ");
            teamName = mReader.readLine();

            // Make sure that entered team name is unique
            teamNameIsNotARepeat = mTeams.checkIfTeamNameIsNotARepeat(teamName);
            if (!teamNameIsNotARepeat) {
                System.out.println("This name is already in use for an existing team.");
            }
        } while (teamName.length() == 0 || !teamNameIsNotARepeat);


        String coach = "";
        boolean coachIsNotARepeat = true;
        do {
            System.out.print("Please, enter a coach for the team:  ");
            coach = mReader.readLine();

            // Make sure that entered coach is unique
            coachIsNotARepeat = mTeams.checkIfCoachIsNotARepeat(coach);
            if (!coachIsNotARepeat) {
                System.out.println("\nThis coach is already chosen by another team.");
            }
        } while (coach.length() == 0 || !coachIsNotARepeat);


        return new Team(teamName, coach);
    }

    private String promptAction() throws IOException {
        String choice = "";
        do {
            System.out.printf("There are currently %d players on the waiting list and %d team(s).%n%n",
                    mPlayers.getPlayers().size(),
                    mTeams.getTeams().size());
            System.out.println("\tWhat would you like to do? Your options are:");

            for (Map.Entry<String, String> option : mMenu.entrySet()) {
                if (option.getKey().equals("quit")) {
                    System.out.printf("\t%s - %s%n", option.getKey(), option.getValue());
                } else {
                    System.out.printf("\t%s. - %s%n", option.getKey(), option.getValue());
                }
            }

            choice = mReader.readLine();
        } while (choice.length() == 0);

        return choice;
    }
}
