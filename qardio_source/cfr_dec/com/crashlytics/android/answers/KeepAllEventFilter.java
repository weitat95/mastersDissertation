/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.EventFilter;
import com.crashlytics.android.answers.SessionEvent;

class KeepAllEventFilter
implements EventFilter {
    KeepAllEventFilter() {
    }

    @Override
    public boolean skipEvent(SessionEvent sessionEvent) {
        return false;
    }
}

