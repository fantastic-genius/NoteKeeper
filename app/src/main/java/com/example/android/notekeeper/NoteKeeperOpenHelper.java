package com.example.android.notekeeper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static com.example.android.notekeeper.NoteKeeperDatabaseContract.*;

/**
 * Created by Abdulfattah Hamzah on 12/13/2018.
 */

public class NoteKeeperOpenHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "NoteKeeper.db";
    public static final int VERSION = 1;

    public NoteKeeperOpenHelper(Context context) {
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CourseInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(NoteInfoEntry.SQL_CREATE_TABLE);
        db.execSQL(CourseInfoEntry.SQL_CREATE_INDEX1);
        db.execSQL(NoteInfoEntry.SQL_CREATE_INDEX1);

        DatabaseDataWorker worker = new DatabaseDataWorker(db);
        worker.insertCourses();
        worker.insertSampleNotes();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        if(oldVersion < 2){
            db.execSQL(CourseInfoEntry.SQL_CREATE_INDEX1);
            db.execSQL(NoteInfoEntry.SQL_CREATE_INDEX1);
        }
    }
}
