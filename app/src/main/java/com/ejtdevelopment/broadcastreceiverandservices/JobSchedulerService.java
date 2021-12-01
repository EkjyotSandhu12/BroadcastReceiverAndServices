package com.ejtdevelopment.broadcastreceiverandservices;

import android.app.job.JobParameters;
import android.app.job.JobService;
import android.os.Handler;
import android.os.Looper;
import android.os.SystemClock;
import android.util.Log;

public class JobSchedulerService  extends JobService { // jobservices are use to work in back ground i.e. update server data or downloading
    // by default they will use main thread soo make a different thread

    public static final String TAG = "JobScheduler"; // type psfs to get auto complete
    public static Boolean jobCancelled = false;
    JobParameters params;

    Handler handler = new Handler(Looper.getMainLooper());
    // To get the main Thread's handler u need to inititate the handler in main thread activity or get its looper

    @Override
    public boolean onStartJob(JobParameters jobParameters) { // job parameters contains info about yourjob
        Log.d(TAG, "onStartJob: job started"); // type logd to get auto complete
        doBackgroundWork(jobParameters);

        return true; //keep it true to tell system to keep the device awake to keep the system running and complete the job
    }

    public void doBackgroundWork(JobParameters params){ // different ways to implement threads / note: you can only run thread object once
        this.params = params;


        // simple runnable thread
       new Thread(new Runnable() { // this is anonymous class // They enable you to declare and instantiate a class at the same time.
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {

                    Log.d(TAG, "count: " + i);
                    if(jobCancelled){
                        jobCancelled = false;
                        return;
                    }

                    SystemClock.sleep(1000); // same as sleep

                }

                jobFinished(params, false); //wantsReschedule even after finished successfully
                Log.d(TAG, "Job Finished");
            }
        }).start();

        // posting new Thread work on main thread
        handler.post(new Thread() { // you can make changes to UI using this technique
            @Override
            public void run() {
                for (int i = 0; i < 10; i++) {

                    Log.d(TAG, "on Main Thread using handler -> count: " + i);
                    if(jobCancelled){
                        jobCancelled = false;
                        return;
                    }

                    SystemClock.sleep(1000); // same as sleep

                }

                jobFinished(params, false); //wantsReschedule even after finished successfully
                Log.d(TAG, "Job Finished");
            }
        });


    /* // does same work as handler
       button.post(new Runnable() {
       @Override
       public void run() {
            buttonStartThread.setText("50%");
            }
            });

    runOnUiThread(new Runnable() {
        @Override
        public void run() {
            button.setText("50%");
        }
    });*/

        ThreadExample threadExample = new ThreadExample();
        threadExample.start();
        //handler.post(threadExample);

        RunnableExample runnableExample = new RunnableExample();
        new Thread(runnableExample).start();
        //handler.post(runnableExample);

        //note: we can directly execute any custom made threads on main thread by .run() method
    }

    @Override
    public boolean onStopJob(JobParameters jobParameters) { //job has been cancelled before jobFinished() has been called
                                 //scheduler.cancel(); has been called through Activity, that doesnt stop the jobService soo we have to make jobCancelled = true
        /*
        This method is called if the system has determined that you must stop execution of your job even before
        you've had a chance to call jobFinished(). Once this method is called, you no longer need
        to call jobFinished().
        */

        Log.d(TAG, "Job has been cancelled");
        jobCancelled = true; // to explicitly cancle the job and cancle the corresponding job service (we are canceling the job)
        return true; //do u want to reschedule your job?
    }


    class ThreadExample extends Thread{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {

                Log.d(TAG, "class Thread count: " + i);
                if(jobCancelled){
                    jobCancelled = false;
                    return;
                }

                SystemClock.sleep(1000); // same as sleep

            }

            jobFinished(params, false); //wantsReschedule even after finished successfully
            Log.d(TAG, "Job Finished");
        }
    }

    class RunnableExample implements Runnable{

        @Override
        public void run() {
            for (int i = 0; i < 10; i++) {

                Log.d(TAG, "Runnable Thread count: " + i);
                if(jobCancelled){
                    jobCancelled = false;
                    return;
                }

                SystemClock.sleep(1000); // same as sleep

            }

            jobFinished(params, false); //wantsReschedule even after finished successfully
            Log.d(TAG, "Job Finished");
        }
    }
}


