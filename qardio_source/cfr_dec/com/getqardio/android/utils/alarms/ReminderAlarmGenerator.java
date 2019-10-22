/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.AlarmManager
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.Intent
 */
package com.getqardio.android.utils.alarms;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.JobIntentService;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Reminder;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.alarms.AlarmEventReceiver;
import com.getqardio.android.utils.ui.Convert;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class ReminderAlarmGenerator
extends JobIntentService {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private NearestReminderMetadata calculateNearestReminderTime() {
        List<Reminder> list = DataHelper.ReminderHelper.getActiveRemindersForAlarm((Context)CustomApplication.getApplication());
        if (list == null) return null;
        if (list.isEmpty()) {
            return null;
        }
        Object object = Calendar.getInstance();
        ((Calendar)object).get(7);
        int n = ((Calendar)object).get(7);
        NearestReminderMetadata nearestReminderMetadata = new NearestReminderMetadata();
        int n2 = 0;
        while (n2 < 7) {
            for (Reminder reminder : list) {
                int n3 = 1 << ((n + 5) % 7 + n2) % 7;
                if ((reminder.days & n3) != n3) continue;
                long l = Convert.Reminder.timeAfterMidnightToLocalTime(reminder.remindTime) + TimeUnit.DAYS.toMillis(n2);
                if (nearestReminderMetadata.time != null && l == nearestReminderMetadata.time) {
                    nearestReminderMetadata.types.add(reminder.type);
                    continue;
                }
                if (l <= System.currentTimeMillis() || nearestReminderMetadata.time != null && l >= nearestReminderMetadata.time) continue;
                nearestReminderMetadata.types.clear();
                nearestReminderMetadata.types.add(reminder.type);
                nearestReminderMetadata.time = l;
            }
            object = nearestReminderMetadata;
            if (nearestReminderMetadata.time != null) return object;
            ++n2;
        }
        return null;
    }

    @Override
    protected void onHandleWork(Intent intent) {
        NearestReminderMetadata nearestReminderMetadata = this.calculateNearestReminderTime();
        intent = (AlarmManager)CustomApplication.getApplication().getSystemService("alarm");
        Timber.d("Notify - onHandleWork" + nearestReminderMetadata, new Object[0]);
        if (nearestReminderMetadata != null) {
            Timber.d("reminderAlarmTime: %d", nearestReminderMetadata.time);
            Intent intent2 = AlarmEventReceiver.createIntent((Context)this, nearestReminderMetadata.getArrayOfTypes());
            intent2 = PendingIntent.getBroadcast((Context)CustomApplication.getApplication(), (int)0, (Intent)intent2, (int)268435456);
            intent.cancel((PendingIntent)intent2);
            intent.set(0, nearestReminderMetadata.time.longValue(), (PendingIntent)intent2);
            return;
        }
        Timber.d("wreminderAlarmTime: null", new Object[0]);
        nearestReminderMetadata = AlarmEventReceiver.createIntent((Context)this, null);
        intent.cancel(PendingIntent.getBroadcast((Context)CustomApplication.getApplication(), (int)0, (Intent)nearestReminderMetadata, (int)268435456));
    }

    private static class NearestReminderMetadata {
        Long time = null;
        Set<String> types = new HashSet<String>();

        private NearestReminderMetadata() {
        }

        public String[] getArrayOfTypes() {
            String[] arrstring = new String[this.types.size()];
            int n = 0;
            for (String arrstring[n] : this.types) {
                ++n;
            }
            return arrstring;
        }

        public String toString() {
            return "NearestReminderMetadata{time=" + this.time + ", types=" + this.types + '}';
        }
    }

}

