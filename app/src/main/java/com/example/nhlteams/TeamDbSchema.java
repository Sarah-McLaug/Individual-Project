package com.example.nhlteams;

public class TeamDbSchema {
    public static final class TeamTable {
        public static final String NAME = "teams";

        public static final class Cols {
            public static final String UUID = "uuid";
            public static final String TEAM = "team";
            public static final String CAPTAIN = "captain";
            public static final String RECORD = "record";
        }
    }
}