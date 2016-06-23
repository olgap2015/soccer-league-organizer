package com.olgaivancic.model;

import java.util.ArrayList;

public class Players {

    private ArrayList<Player> mPlayers;

    public ArrayList<Player> getPlayers() {
        return mPlayers;
    }

    public Players() {
        mPlayers = new ArrayList<Player>();
        mPlayers.add(new Player("Joe", "Smith", 42, true));
        mPlayers.add(new Player("Jill", "Tanner", 36, true));
        mPlayers.add(new Player("Bill", "Bon", 43, true));
        mPlayers.add(new Player("Eva", "Gordon", 45, false));
        mPlayers.add(new Player("Matt", "Gill", 40, false));
        mPlayers.add(new Player("Kimmy", "Stein", 41, false));
        mPlayers.add(new Player("Sammy", "Adams", 45, false));
        mPlayers.add(new Player("Karl", "Saygan", 42, true));
        mPlayers.add(new Player("Suzane", "Greenberg", 44, true));
        mPlayers.add(new Player("Sal", "Dali", 41, false));
        mPlayers.add(new Player("Joe", "Kavalier", 39, false));
        mPlayers.add(new Player("Ben", "Finkelstein", 44, false));
        mPlayers.add(new Player("Diego", "Soto", 41, true));
        mPlayers.add(new Player("Chloe", "Alaska", 47, false));
        mPlayers.add(new Player("Arfalseld", "Willis", 43, false));
        mPlayers.add(new Player("Phillip", "Helm", 44, true));
        mPlayers.add(new Player("Les", "Clay", 42, true));
        mPlayers.add(new Player("Herschel", "Krustofski", 45, true));
        mPlayers.add(new Player("Andrew", "Chalklerz", 42, true));
        mPlayers.add(new Player("Pasan", "Membrane", 36, true));
        mPlayers.add(new Player("Kenny", "Lovins", 35, true));
        mPlayers.add(new Player("Alena", "Sketchings", 45, false));
        mPlayers.add(new Player("Carling", "Seacharpet", 40, false));
        mPlayers.add(new Player("Joseph", "Freely", 41, false));
        mPlayers.add(new Player("Gabe", "Listmaker", 45, false));
        mPlayers.add(new Player("Jeremy", "Smith", 42, true));
        mPlayers.add(new Player("Ben", "Droid", 44, true));
        mPlayers.add(new Player("James", "Dothnette", 41, false));
        mPlayers.add(new Player("Nick", "Grande", 39, false));
        mPlayers.add(new Player("Will", "Guyam", 44, false));
        mPlayers.add(new Player("Jason", "Seaver", 41, true));
        mPlayers.add(new Player("Johnny", "Thunder", 47, false));
        mPlayers.add(new Player("Ryan", "Creedson", 43, false));
    }

    /**
     * This method outputs on the console the list of the available players.
     */
    public void outputPlayers() {
        System.out.printf("Please, choose a player from the list of %d available mPlayers.%n", mPlayers.size());

        // output all the available mPlayers
        int i;
        for (i = 0; i < mPlayers.size(); i++) {
            //TODO: maybe format the output as a table later
            System.out.printf("Player #%d: %s%n", i + 1, mPlayers.get(i).toString());
        }
    }

    /**
     * This method removes specified player from the list of players.
     *
     * @param player
     */
    public void removePlayer(Player player) {
        for (int i = 0; i < mPlayers.size(); i++) {
            if (player.equals(mPlayers.get(i))) {
                mPlayers.remove(i);
            }
        }
    }
}