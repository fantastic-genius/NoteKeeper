package com.example.android.notekeeper;

import android.app.Service;
import android.app.job.JobParameters;
import android.app.job.JobService;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.IBinder;

public class NoteUploaderJobService extends JobService {

    public static final String EXTRA_DATA_URI = "com.example.android.notekeeper.DATA_URI";
    private NoteUploader mNoteUploader;

    public NoteUploaderJobService() {
    }

    @Override
    public boolean onStartJob(JobParameters params) {
        AsyncTask<JobParameters, Void, Void> task = new AsyncTask<JobParameters, Void, Void>() {
            @Override
            protected Void doInBackground(JobParameters... bachgroundParams) {
                JobParameters jobParams = bachgroundParams[0];
                String stringDataUri = jobParams.getExtras().getString(EXTRA_DATA_URI);
                Uri dataUri = Uri.parse(stringDataUri);
                mNoteUploader.doUpload(dataUri);

                if(! mNoteUploader.isCancelled())
                    jobFinished(jobParams, false);

                return null;
            }
        };

        mNoteUploader = new NoteUploader(this);
        task.execute(params);
        return false;
    }

    @Override
    public boolean onStopJob(JobParameters params) {
        mNoteUploader.cancel();
        return true;
    }


}
