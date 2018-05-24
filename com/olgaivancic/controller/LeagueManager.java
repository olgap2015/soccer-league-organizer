package com.olgaivancic.controller;

import com.olgaivancic.model.Players;
import com.olgaivancic.model.Teams;
import com.olgaivancic.view.Prompter;

public class LeagueManager {

    public static void main(String[] args) {
        Players players = new Players();
        Teams teams = new Teams(players);

        Prompter prompter = new Prompter(players, teams);
        Processor processor = new Processor(players, teams, prompter);
        processor.run();
    }

}
