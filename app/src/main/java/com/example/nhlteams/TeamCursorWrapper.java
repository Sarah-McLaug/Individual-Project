package com.example.nhlteams;

import android.database.Cursor;
import android.database.CursorWrapper;

import com.example.nhlteams.TeamDbSchema.TeamTable;

import java.util.UUID;

public class TeamCursorWrapper extends CursorWrapper {
    public TeamCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public Team getTeam() {
        String uuidString = getString(getColumnIndex(TeamTable.Cols.UUID));
        String name = getString(getColumnIndex(TeamTable.Cols.TEAM));
        String captain = getString(getColumnIndex(TeamTable.Cols.CAPTAIN));
        String record = getString(getColumnIndex(TeamTable.Cols.RECORD));

        Team team = new Team(UUID.fromString(uuidString));
        team.setName(name);
        team.setCaptain(captain);
        team.setRecord(record);

        return team;
    }
}
