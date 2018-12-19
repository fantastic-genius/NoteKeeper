package com.example.android.notekeeper;

import android.provider.BaseColumns;

/**
 * Created by Abdulfattah Hamzah on 12/13/2018.
 */

public final class NoteKeeperDatabaseContract {
    private NoteKeeperDatabaseContract(){}

    public static final class CourseInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "course_info";
        public static final String COLUMN_COURSE_ID = "course_id";
        public static final String COLUMN_COURSE_TITLE = "course_title";

        // CREATE TABLE course_info(course_id, course_title)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                +_ID + " INTEGER PRIMARY KEY, "
                + COLUMN_COURSE_ID + " TEXT UNIQUE NOT NULL, "
                + COLUMN_COURSE_TITLE + " TEXT NOT NULL)";

    }


    public static final class NoteInfoEntry implements BaseColumns {
        public static final String TABLE_NAME = "note_info";
        public static final String COLUMN_NOTE_TITLE = "note_title";
        public static final String COLUMN_NOTE_TEXT = "note_text";
        public static final String COLUMN_COURSE_ID = "course_id";

        // CREATE TABLE note_Info(note_title, note_text, course_id)
        public static final String SQL_CREATE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " ("
                + _ID + " INTEGER PRIMARY KEY, "
                + COLUMN_NOTE_TITLE + " TEXT NOT NULL, "
                + COLUMN_NOTE_TEXT + " TEXT NOT NULL, "
                + COLUMN_COURSE_ID + " TEXT NOT NULL)";
    }
}
