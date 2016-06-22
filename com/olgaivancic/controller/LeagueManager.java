package com.olgaivancic.controller;

import com.olgaivancic.model.Player;
import com.olgaivancic.model.Players;
import com.olgaivancic.model.TeamList;
import com.olgaivancic.view.Prompter;

public class LeagueManager {

    public static void main(String[] args) {
        Player[] players = Players.load();
        TeamList teamList = new TeamList(players.length);

        Prompter prompter = new Prompter(players, teamList);
        prompter.run();
    }

}
