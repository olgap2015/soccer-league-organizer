package com.olgaivancic.view;

import com.olgaivancic.model.Player;
import com.olgaivancic.model.Team;
import com.olgaivancic.model.TeamList;

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
    private Player[] mPlayers;
    private Map<String, String> mMenu;
    private TeamList mTeamList;


    public Prompter(Player[] players, TeamList teamList) {
        mReader = new BufferedReader(new InputStreamReader(System.in));
        mPlayers = players;
        mMenu = new HashMap<>();
        mMenu.put("cnt", "create a new team");
        mMenu.put("quit", "quit the program");
        mTeamList = teamList;
    }

    public void run() {
        String choice = "";
        do {
            try {
                choice = promptAction();
                switch (choice) {
                    case "cnt":
                        if (mTeamList.getTeams().size() == mTeamList.getTotalAvailablePlayers()) {
                            System.out.println("You can't create a new team because you have already created " +
                                    "the maximum amount of teams allowed!\n");
                        } else {
                            Team team = promptForNewTeam();
                            mTeamList.addTeam(team);
                            System.out.printf("New team \"%s\" is created!  %n%n", team.getTeamName());
                        }
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

    private Team promptForNewTeam() throws IOException {
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
            System.out.printf("There are currently %d registered players.%n", mPlayers.length);
            System.out.println("What would you like to do? Your options are:");

            for (Map.Entry<String, String> option : mMenu.entrySet()) {
                System.out.printf("%s - %s%n", option.getKey(), option.getValue());
            }

            choice = mReader.readLine();
        } while (choice.length() == 0);

        return choice;
    }
}
