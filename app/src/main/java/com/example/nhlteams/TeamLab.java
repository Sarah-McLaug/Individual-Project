package com.example.nhlteams;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.nhlteams.TeamDbSchema.TeamTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class TeamLab {
    private static TeamLab sTeamLab;

    private Context mContext;
    private SQLiteDatabase mDatabase;

    public static TeamLab get(Context context) {
        if (sTeamLab == null) {
            sTeamLab = new TeamLab(context);
        }
        return sTeamLab;
    }

    private TeamLab(Context context) {
        mContext = context.getApplicationContext();
        mDatabase = new TeamBaseHelper(mContext)
                .getWritableDatabase();
    }

    public void addTeam(Team t) {
        ContentValues values = getContentValues(t);

        mDatabase.insert(TeamTable.NAME, null, values);
    }

    public int deleteTeam(Team team) {
        String uuidString = team.getId().toString();
        return mDatabase.delete(
                TeamTable.NAME,
                TeamTable.Cols.UUID + " = ?",
                new String[] { uuidString }
        );
    }

    public List<Team> getTeams() {
        List<Team> teams = new ArrayList<>();

        TeamCursorWrapper cursor = queryTeams(null, null);

        try {
            cursor.moveToFirst();
            while(!cursor.isAfterLast()) {
                teams.add(cursor.getTeam());
                cursor.moveToNext();
            }
        } finally {
            cursor.close();
        }

        return teams;
    }

    public void updateTeam(Team team) {
        String uuidString = team.getId().toString();
        ContentValues values = getContentValues(team);

        mDatabase.update(TeamTable.NAME, values,
                TeamTable.Cols.UUID + " = ?",
                new String[] { uuidString });
    }

    private TeamCursorWrapper queryTeams(String whereClause, String[] whereArgs) {
        Cursor cursor = mDatabase.query(
                TeamTable.NAME,
                null, // columns - null selects all columns
                whereClause,
                whereArgs,
                null, // groupBy
                null, // having
                null  // orderBy
        );

        return new TeamCursorWrapper(cursor);
    }

    public Team getTeam(UUID id) {
        TeamCursorWrapper cursor = queryTeams(
                TeamTable.Cols.UUID + " = ? ",
                new String[] { id.toString() }
        );

        try {
            if(cursor.getCount() == 0) {
                return null;
            }

            cursor.moveToFirst();
            return cursor.getTeam();
        } finally {
            cursor.close();
        }
    }

    private static ContentValues getContentValues(Team team) {
        ContentValues values = new ContentValues();
        values.put(TeamTable.Cols.UUID, team.getId().toString());
        values.put(TeamTable.Cols.TEAM, team.getName());
        values.put(TeamTable.Cols.CAPTAIN, team.getCaptain());
        values.put(TeamTable.Cols.RECORD, team.getRecord());

        return values;
    }
}
