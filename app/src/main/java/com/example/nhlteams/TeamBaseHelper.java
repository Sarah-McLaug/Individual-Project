package com.example.nhlteams;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.nhlteams.TeamDbSchema.TeamTable;

public class TeamBaseHelper extends SQLiteOpenHelper {
    private static final int VERSION = 1;
    private static final String DATABASE_NAME = "crimeBase.db";

    public TeamBaseHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table " + TeamTable.NAME + "(" +
                " _id integer primary key autoincrement, " +
                TeamTable.Cols.UUID + ", " +
                TeamTable.Cols.TEAM + ", " +
                TeamTable.Cols.CAPTAIN + ", " +
                TeamTable.Cols.RECORD +
                ")"
        );
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
