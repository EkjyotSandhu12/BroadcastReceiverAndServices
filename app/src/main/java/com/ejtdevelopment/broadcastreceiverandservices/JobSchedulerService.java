package com.ejtdevelopment.broadcastreceiverandservices;

import android.app.job.JobInfo;
import android.app.job.JobParameters;
import android.app.job.JobScheduler;
import android.app.job.JobService;
import android.app.job.JobWorkItem;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class JobSchedulerService  extends JobService { // jobservices are use to work in back ground i.e. update server data or downloading
    // by default they will use main thread soo make a different thread

    public static final String TAG = "JobScheduler"; // type psfs to get auto complete
    public static Boolean jobCancelled = false;

    @Override
    public boolean onStartJob(JobParameters jobParameters) { // job parameters contains info about yourjob
        Log.d(TAG, "onStartJob: job started"); // type logd to get auto complete
        doBackgroundWork(jobParameters);

        return true; //keep it true to tell system to keep the device awake to keep the system running and complete the job
    }

    public void doBackgroundWork(JobParameters params){

        new Thread(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {

                    Log.d(TAG, "count: " + i);
                    if(jobCancelled){
                        jobCancelled = false;
                        return;
                    }

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }

                jobFinished(params, false); //wantsReschedule even after finished successfullu
            }
        }).start();
        Log.d(TAG, "Job Finished");
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) { //job has been cancelled before jobFinished() has been called
                                 //scheduler.cancel(); has been called through Activity, that doesnt stop the jobService

        /*
        This method is called if the system has determined that you must stop execution of your job even before
        you've had a chance to call jobFinished(). Once this method is called, you no longer need
        to call jobFinished().
        */

        Log.d(TAG, "Job has been cancelled");
        jobCancelled = true; // to explicitly cancle the job and cancle the corresponding job service (we are canceling the job)
        return true; //do u want to reschedule your job?
    }
}
