/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.answers;

import com.crashlytics.android.answers.SessionEvent;

interface EventFilter {
    public boolean skipEvent(SessionEvent var1);
}

