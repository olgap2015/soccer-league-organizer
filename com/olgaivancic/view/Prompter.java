package com.olgaivancic.view;

import com.olgaivancic.model.Player;
import com.olgaivancic.model.Players;
import com.olgaivancic.model.Team;
import com.olgaivancic.model.Teams;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * This Class is responsible for implementing I/O methods for creating and managing the teams.
 */
public class Prompter {

    private BufferedReader mReader;
    private Players mPlayers;
    private Map<String, String> mMenu;
    private Teams mTeams;
    private Map<String, List<Player>> mByHeightEvaluation;


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
        mByHeightEvaluation = new TreeMap<>();
    }

    public Players getPlayers() {
        return mPlayers;
    }

    public Map<String, String> getMenu() {
        return mMenu;
    }

    public Teams getTeams() {
        return mTeams;
    }

    public String promptAction() throws IOException {
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

    /**
     * This method prompts user to choose a team from the list of created teams.
     *
     * @return The team chosen by the User.
     */
    public Team promptForTeam() {
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
                System.out.printf("Please, enter a number within the range of 1 to %d.%n",
                        mTeams.getTeams().size());
            }
        } while (teamNumber <= 0 || teamNumber > mTeams.getTeams().size());


        return mTeams.getTeams().get(teamNumber - 1);
    }

    public void outputPlayers() {
        System.out.printf("Please, choose a player from the list of %d available players.%n",
                mPlayers.getPlayers().size());

        // Sort the players alphabetically.
        mPlayers.sortPlayers();
        // output all players on the waiting list
        mPlayers.getPlayers().forEach(player -> System.out.printf("Player #%d: %s%n", mPlayers.getPlayers().indexOf(player) + 1, player));
    }

    /**
     * This method prompts for player from the list of available players
     *
     * @return player (type Player)
     */
    public Player promptForPlayer() {

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
     * This method prompts for player from the list of players passed as a parameter
     *
     * @param teamPlayers
     * @return teamPlayers (type ArrayList<Player>)
     */
    public Player promptForPlayer(ArrayList<Player> teamPlayers) {
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

    /**
     * This is a helper method that is designed to prompt User for input until a value is entered.
     *
     * @param promptingMessage
     * @return User's input as a String
     */
    public String promptForInput(String promptingMessage) {
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

    /*
     * This method prompting User for data necessary to create a new team - team name
     * and coach.
     *
     * @return Created team
     * @throws IOException
     */
    public Team promptForNewTeam() throws IOException {
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


}
