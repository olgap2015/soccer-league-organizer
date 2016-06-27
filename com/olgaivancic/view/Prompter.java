package com.olgaivancic.view;

import com.olgaivancic.model.Player;
import com.olgaivancic.model.Players;
import com.olgaivancic.model.Team;
import com.olgaivancic.model.Teams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

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
        mMenu = new HashMap<>();
        mMenu.put("cnt", "create a new team");
        mMenu.put("ap", "add a player to the team");
        mMenu.put("rp", "remove player from the team back to the waiting list");
        mMenu.put("quit", "quit the program");
        mTeams = teams;
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice.toLowerCase()) {
                    case "cnt":
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
                    case "ap":
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
                        mTeams.addPlayer(chosenTeam, player);
                        // Remove that player from the waiting list.
                        mPlayers.getPlayers().remove(player);
                        System.out.printf("%nPlayer %s %s was added to the team \"%s\".%n",
                                player.getFirstName(),
                                player.getLastName(),
                                chosenTeam.getTeamName());
                        break;
                    case "rp":
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
                        mTeams.removePlayer(chosenTeam, player);

                        // Add player to the waiting list
                        mPlayers.getPlayers().add(player);

                        System.out.printf("%nPlayer %s %s was removed from the team \"%s\".%n",
                                player.getFirstName(),
                                player.getLastName(),
                                chosenTeam.getTeamName());
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
                System.out.printf("Please, enter a number within the range of 1 to %d.%n", mPlayers.getPlayers().size());
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
                System.out.printf("\t%s - %s%n", option.getKey(), option.getValue());
            }

            choice = mReader.readLine();
        } while (choice.length() == 0);

        return choice;
    }
}
