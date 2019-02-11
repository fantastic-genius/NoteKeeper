package com.example.android.notekeeper;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;

import static com.example.android.notekeeper.NoteKeeperProviderContract.*;

/**
 * Created by Abdulfattah Hamzah on 2/10/2019.
 */

public class NoteUploader {

    private String TAG = getClass().getSimpleName();

    private final Context mContext;
    private boolean mCancelled;

    public NoteUploader(Context context){
        mContext = context;
    }

    public boolean isCancelled(){
        return mCancelled;
    }

    public void cancel(){
        mCancelled = true;
    }

    public void doUpload(Uri dataUri){
        String[] columns = {
                Notes.COLUMN_COURSE_ID,
                Notes.COLUMN_NOTE_TITLE,
                Notes.COLUMN_NOTE_TEXT
        };

        Cursor cursor = mContext.getContentResolver().query(dataUri, columns, null, null, null);
        int courseIdPos = cursor.getColumnIndex(Notes.COLUMN_COURSE_ID);
        int noteTitlePos = cursor.getColumnIndex(Notes.COLUMN_NOTE_TITLE);
        int noteTextPos = cursor.getColumnIndex(Notes.COLUMN_NOTE_TEXT);

        Log.i(TAG, ">>>***  UPLOAD START - " + dataUri + "   ***<<<");
        mCancelled = false;
        while (!mCancelled && cursor.moveToNext()){
            String courseId = cursor.getString(courseIdPos);
            String noteTitle = cursor.getString(noteTitlePos);
            String noteText = cursor.getString(noteTextPos);

            if(!noteTitle.equals("")){
                Log.i(TAG, ">>>Uploading Note<<< " + courseId + "|" + noteTitle + "|" + noteText);
                simulateLongRunningWork();
            }
        }

        if(mCancelled)
            Log.i(TAG, ">>>***   UPLOAD !!CANCELLED!! - " + dataUri + "   ***<<<");
        else
            Log.i(TAG, ">>>***   UPLOAD COMPLETE  - " + dataUri + "  ***<<<");
        cursor.close();
    }


    private static void simulateLongRunningWork() {
        try {
            Thread.sleep(1000);
        } catch(Exception ex) {}
    }

}
