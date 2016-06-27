package com.olgaivancic.model;

import java.io.Serializable;

public class Player implements Comparable<Player>, Serializable {
    private static final long serialVersionUID = 1L;

    private String mFirstName;
    private String mLastName;
    private int mHeightInInches;
    private boolean mPreviousExperience;

    public Player(String firstName, String lastName, int heightInInches, boolean previousExperience) {
        this.mFirstName = firstName;
        this.mLastName = lastName;
        this.mHeightInInches = heightInInches;
        this.mPreviousExperience = previousExperience;
    }

    public String getFirstName() {
        return mFirstName;
    }

    public String getLastName() {
        return mLastName;
    }

    public int getHeightInInches() {
        return mHeightInInches;
    }

    public boolean isPreviousExperience() {
        return mPreviousExperience;
    }

    // TODO: complete compareTo method for Player
    @Override
    public int compareTo(Player other) {
        // Compare by last name then first name
        if (this.equals(other)) {
            return 0;
        }
        if (mLastName.equals(other.getLastName())) {
            return mFirstName.compareTo(other.getFirstName());
        }
        return mLastName.compareTo(other.getLastName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;

        Player player = (Player) o;

        if (mHeightInInches != player.mHeightInInches) return false;
        if (mPreviousExperience != player.mPreviousExperience) return false;
        if (!mFirstName.equals(player.mFirstName)) return false;
        return mLastName.equals(player.mLastName);

    }

    @Override
    public int hashCode() {
        int result = mFirstName.hashCode();
        result = 31 * result + mLastName.hashCode();
        result = 31 * result + mHeightInInches;
        result = 31 * result + (mPreviousExperience ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        String hasExperience = "";
        if (mPreviousExperience) {
            hasExperience = "yes";
        } else {
            hasExperience = "no";
        }
        return mLastName + ", " + mFirstName + " (height - " + mHeightInInches + " in, previous experience - " +
                hasExperience + ")";
    }
}
