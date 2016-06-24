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
                        // Output list of players.
                        mPlayers.outputPlayers();
                        // choose a player and a team from the list of available players.
                        Player player = promptForPlayer(mPlayers);
                        Team chosenTeam = promptForTeam();
                        // Add the chosen player to the chosen team
                        mTeams.addPlayer(chosenTeam, player);
                        // Remove that player from the list of players.
//                        mPlayers.removePlayer(player);
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
                        ArrayList<Player> teamPlayers = mTeams.findTeamPlayers(chosenTeam);
                        for (int i = 0; i < teamPlayers.size(); i++) {
                            System.out.printf("Player #%d: %s%n", i + 1, teamPlayers.get(i).toString());
                        }
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
        mTeams.outputListOfTeams();

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
     * @return player (type Player)
     * @param players
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
        //TODO: make sure that created team is unique
        String teamName = "";
        do {
            System.out.print("\nPlease, enter a name for the team:  ");
            teamName = mReader.readLine();
        } while (teamName.length() == 0);

        String coach = "";
        do {
            System.out.print("Please, enter a coach for the team:  ");
            coach = mReader.readLine();
        } while (coach.length() == 0);
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
