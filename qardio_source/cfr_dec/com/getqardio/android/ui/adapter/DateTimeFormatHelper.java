/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.getqardio.android.ui.adapter;

import android.text.TextUtils;
import com.getqardio.android.utils.DateUtils;
import java.text.SimpleDateFormat;
import java.util.TimeZone;

public class DateTimeFormatHelper {
    private Callback callback;
    private String currentDatePattern;
    private String currentTimePattern;
    private int currentTimezoneOffset;

    public DateTimeFormatHelper(Callback callback) {
        this.callback = callback;
        this.setDateTimeFormat();
    }

    private SimpleDateFormat getCurrentDateFormat() {
        return DateUtils.getCurrentDateFormat();
    }

    private SimpleDateFormat getCurrentTimeFormat() {
        return DateUtils.getCurrentTimeFormat();
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isDateTimeSettingsChanged() {
        boolean bl = false;
        int n = TimeZone.getDefault().getRawOffset();
        String string2 = this.getCurrentDateFormat().toLocalizedPattern();
        String string3 = this.getCurrentTimeFormat().toLocalizedPattern();
        boolean bl2 = !TextUtils.equals((CharSequence)this.currentDatePattern, (CharSequence)string2) || !TextUtils.equals((CharSequence)this.currentTimePattern, (CharSequence)string3);
        if (this.currentTimezoneOffset != n) return true;
        if (!bl2) return bl;
        return true;
    }

    private void setDateTimeFormat() {
        this.currentTimezoneOffset = TimeZone.getDefault().getRawOffset();
        this.currentDatePattern = this.getCurrentDateFormat().toLocalizedPattern();
        this.currentTimePattern = this.getCurrentTimeFormat().toLocalizedPattern();
        this.callback.setDateFormat(this.getCurrentDateFormat());
        this.callback.setTimeFormat(this.getCurrentTimeFormat());
        this.callback.updateData();
    }

    public void onUpdatePatterns() {
        if (this.isDateTimeSettingsChanged()) {
            this.setDateTimeFormat();
        }
    }

    public static interface Callback {
        public void setDateFormat(SimpleDateFormat var1);

        public void setTimeFormat(SimpleDateFormat var1);

        public void updateData();
    }

}

