package com.ejtdevelopment.broadcastreceiverandservices;

import androidx.appcompat.app.AppCompatActivity;

import android.app.job.JobInfo;
import android.app.job.JobScheduler;
import android.content.ComponentName;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

public class JobSchedulerActivity extends AppCompatActivity {

    private static final String TAG = "JobScheduler";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_job_scheduler);
    }
    // With Android 5.0 (API level 21) and later, you can start a service with JobScheduler
    public void scheduleJob(View view) {
        ComponentName componentName = new ComponentName(this, JobSchedulerService.class); // component name is used to identify a component
        JobInfo info = new JobInfo.Builder(123, componentName) // job id to identify the job service and component of service
                .setRequiresCharging(false) // does it require charging of phone?
                .setRequiredNetworkType(JobInfo.NETWORK_TYPE_UNMETERED) // type of internet connection required
                .setPersisted(true) // keep our job alive even if we reboot the device
                // it requires RECEIVE_BOOT_COMPLETED permission to be written in manifested file
                .setPeriodic(15 * 60 * 1000) // after only this much amount of time u can start this job again  | minimum is 15min |
                .build();
               //JOB Info is information passed into the JobScheduler to start job service according to the info provided

        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);

        int resultCode = scheduler.schedule(info); // return integer if it succeeded or not

        if (resultCode == JobScheduler.RESULT_SUCCESS) {
            Log.d(TAG, "Job scheduled");
        } else {
            Log.d(TAG, "Job scheduling failed");
        }
    }

    public void cancelJob(View view) {
        JobScheduler scheduler = (JobScheduler) getSystemService(JOB_SCHEDULER_SERVICE);
        scheduler.cancel(123);
        Log.d(TAG, "Job cancel pressed");
    }


}
