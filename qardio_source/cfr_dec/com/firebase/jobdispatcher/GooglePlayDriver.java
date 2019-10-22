/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.os.Parcelable
 */
package com.firebase.jobdispatcher;

import android.app.PendingIntent;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import com.firebase.jobdispatcher.DefaultJobValidator;
import com.firebase.jobdispatcher.Driver;
import com.firebase.jobdispatcher.GooglePlayJobWriter;
import com.firebase.jobdispatcher.GooglePlayReceiver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobValidator;

public final class GooglePlayDriver
implements Driver {
    private final boolean available;
    private final Context context;
    private final PendingIntent token;
    private final JobValidator validator;
    private final GooglePlayJobWriter writer;

    public GooglePlayDriver(Context context) {
        this.available = true;
        this.context = context;
        this.token = PendingIntent.getBroadcast((Context)context, (int)0, (Intent)new Intent(), (int)0);
        this.writer = new GooglePlayJobWriter();
        this.validator = new DefaultJobValidator(context);
    }

    private Intent createScheduleRequest(JobParameters jobParameters) {
        Intent intent = this.createSchedulerIntent("SCHEDULE_TASK");
        intent.putExtras(this.writer.writeToBundle(jobParameters, intent.getExtras()));
        return intent;
    }

    private Intent createSchedulerIntent(String string2) {
        Intent intent = new Intent("com.google.android.gms.gcm.ACTION_SCHEDULE");
        intent.setPackage("com.google.android.gms");
        intent.putExtra("scheduler_action", string2);
        intent.putExtra("app", (Parcelable)this.token);
        intent.putExtra("source", 8);
        intent.putExtra("source_version", 1);
        return intent;
    }

    @Override
    public int cancel(String string2) {
        this.context.sendBroadcast(this.createCancelRequest(string2));
        return 0;
    }

    protected Intent createCancelRequest(String string2) {
        Intent intent = this.createSchedulerIntent("CANCEL_TASK");
        intent.putExtra("tag", string2);
        intent.putExtra("component", (Parcelable)new ComponentName(this.context, this.getReceiverClass()));
        return intent;
    }

    protected Class<GooglePlayReceiver> getReceiverClass() {
        return GooglePlayReceiver.class;
    }

    @Override
    public JobValidator getValidator() {
        return this.validator;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public int schedule(Job job) {
        GooglePlayReceiver.onSchedule(job);
        this.context.sendBroadcast(this.createScheduleRequest(job));
        return 0;
    }
}

