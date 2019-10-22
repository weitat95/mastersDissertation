/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.EventFilter;
import com.crashlytics.android.answers.SessionEvent;
import com.crashlytics.android.answers.SessionEventMetadata;
import java.util.HashSet;
import java.util.Set;

class SamplingEventFilter
implements EventFilter {
    static final Set<SessionEvent.Type> EVENTS_TYPE_TO_SAMPLE = new HashSet<SessionEvent.Type>(){
        {
            this.add(SessionEvent.Type.START);
            this.add(SessionEvent.Type.RESUME);
            this.add(SessionEvent.Type.PAUSE);
            this.add(SessionEvent.Type.STOP);
        }
    };
    final int samplingRate;

    public SamplingEventFilter(int n) {
        this.samplingRate = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public boolean skipEvent(SessionEvent sessionEvent) {
        boolean bl = EVENTS_TYPE_TO_SAMPLE.contains((Object)sessionEvent.type) && sessionEvent.sessionEventMetadata.betaDeviceToken == null;
        boolean bl2 = Math.abs(sessionEvent.sessionEventMetadata.installationId.hashCode() % this.samplingRate) != 0;
        return bl && bl2;
    }

}

