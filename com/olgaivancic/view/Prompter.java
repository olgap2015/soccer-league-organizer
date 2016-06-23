package com.olgaivancic.view;

import com.olgaivancic.model.Player;
import com.olgaivancic.model.Players;
import com.olgaivancic.model.Team;
import com.olgaivancic.model.Teams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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
                            System.out.println("You can't create a new team because you have already created " +
                                    "the maximum amount of teams allowed!\n");
                            break;
                        } else {
                            Team team = promptForNewTeam();
                            mTeams.addTeam(team);
                            System.out.printf("New team \"%s\" is created!  %n%n", team.getTeamName());
                        }
                        break;
                    case "ap":
                        // TODO: manage the case when there are no teams created yet
                        // choose a player and a team from the list of available players.
                        Player player = promptForPlayer();
                        Team chosenTeam = promptForTeam();
                        // Add the chosen player to the chosen team
                        mTeams.addPlayer(chosenTeam, player);
                        // Remove that player from the list of players.
                        mPlayers.removePlayer(player);
                        System.out.printf("Player %s %s was added to the team \"%s\".%n",
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
     */
    private Player promptForPlayer() {
        // Output list of players and another one for the list of teams.
        mPlayers.outputPlayers();

        int playersNumber = 0;
        do {
            String playersNumberAsString = promptForInput("\nChoose the player by entering his number: ");
            try {
                playersNumber = Integer.parseInt(playersNumberAsString);
            } catch (NumberFormatException nfe) {
                System.out.println("Please, enter a whole number!");
            }
            // check if a number is within the proper range
            if (playersNumber <= 0 || playersNumber > mPlayers.getPlayers().size()) {
                System.out.printf("Please, enter a number within the range of 1 to %d.%n", mPlayers.getPlayers().size());
            }
        } while (playersNumber <= 0 || playersNumber > mPlayers.getPlayers().size());


        return mPlayers.getPlayers().get(playersNumber - 1);
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
            System.out.print("Please, enter a name of the team:  ");
            teamName = mReader.readLine();
        } while (teamName.length() == 0);

        String coach = "";
        do {
            System.out.print("Please, enter the coach for the team:  ");
            coach = mReader.readLine();
        } while (coach.length() == 0);
        return new Team(teamName, coach);
    }

    private String promptAction() throws IOException {
        String choice = "";
        do {
            System.out.printf("There are currently %d registered players.%n", mPlayers.getPlayers().size());
            System.out.println("What would you like to do? Your options are:");

            for (Map.Entry<String, String> option : mMenu.entrySet()) {
                System.out.printf("%s - %s%n", option.getKey(), option.getValue());
            }

            choice = mReader.readLine();
        } while (choice.length() == 0);

        return choice;
    }
}
