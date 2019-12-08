package com.example.nhlteams;

import java.util.Date;
import java.util.UUID;

public class Team {

    private UUID mId;
    private String mName;
    private String mCaptain;
    private String mRecord;
    private int mWins;
    private int mLosses;
    private int mTies;
    private boolean mSolved;

    public Team() {
        mId = UUID.randomUUID();
    }

    public Team(UUID id) {
        mId = id;
    }

    public UUID getId() {
        return mId;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getCaptain() {
        return mCaptain;
    }

    public void setCaptain(String captain) {
        mCaptain = captain;
    }

    public String getRecord() {
        return mRecord;
    }

    public void setRecord() {
        mRecord = mWins + "-" + mLosses + "-" + mTies;
    }

    public void setRecord(String record) {
        mRecord = record;
    }

    public int getWins() {
        return mWins;
    }

    public void setWins(int wins) {
        mWins = wins;
    }

    public int getLosses() {
        return mLosses;
    }

    public void setLosses(int losses) {
        mLosses = losses;
    }

    public int getTies() {
        return mTies;
    }

    public void setTies(int ties) {
        mTies = ties;
    }

    public boolean isSolved() {
        return mSolved;
    }

    public void setSolved(boolean solved) {
        mSolved = solved;
    }
}
