/*
 * Decompiled with CFR 0.147.
 */
package com.firebase.jobdispatcher;

import com.firebase.jobdispatcher.ObservedUri;
import java.util.List;

public class JobTrigger {

    public static final class ContentUriTrigger
    extends JobTrigger {
        private final List<ObservedUri> uris;

        ContentUriTrigger(List<ObservedUri> list) {
            this.uris = list;
        }

        public List<ObservedUri> getUris() {
            return this.uris;
        }
    }

    public static final class ExecutionWindowTrigger
    extends JobTrigger {
        private final int windowEnd;
        private final int windowStart;

        ExecutionWindowTrigger(int n, int n2) {
            this.windowStart = n;
            this.windowEnd = n2;
        }

        public int getWindowEnd() {
            return this.windowEnd;
        }

        public int getWindowStart() {
            return this.windowStart;
        }
    }

    public static final class ImmediateTrigger
    extends JobTrigger {
        ImmediateTrigger() {
        }
    }

}

