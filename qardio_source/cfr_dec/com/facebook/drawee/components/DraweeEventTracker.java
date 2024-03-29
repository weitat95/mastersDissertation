/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.drawee.components;

import java.util.Queue;
import java.util.concurrent.ArrayBlockingQueue;

public class DraweeEventTracker {
    private static boolean sEnabled;
    private static final DraweeEventTracker sInstance;
    private final Queue<Event> mEventQueue = new ArrayBlockingQueue<Event>(20);

    static {
        sInstance = new DraweeEventTracker();
        sEnabled = true;
    }

    private DraweeEventTracker() {
    }

    public static DraweeEventTracker newInstance() {
        if (sEnabled) {
            return new DraweeEventTracker();
        }
        return sInstance;
    }

    public void recordEvent(Event event) {
        if (!sEnabled) {
            return;
        }
        if (this.mEventQueue.size() + 1 > 20) {
            this.mEventQueue.poll();
        }
        this.mEventQueue.add(event);
    }

    public String toString() {
        return this.mEventQueue.toString();
    }

    public static enum Event {
        ON_SET_HIERARCHY,
        ON_CLEAR_HIERARCHY,
        ON_SET_CONTROLLER,
        ON_CLEAR_OLD_CONTROLLER,
        ON_CLEAR_CONTROLLER,
        ON_INIT_CONTROLLER,
        ON_ATTACH_CONTROLLER,
        ON_DETACH_CONTROLLER,
        ON_RELEASE_CONTROLLER,
        ON_DATASOURCE_SUBMIT,
        ON_DATASOURCE_RESULT,
        ON_DATASOURCE_RESULT_INT,
        ON_DATASOURCE_FAILURE,
        ON_DATASOURCE_FAILURE_INT,
        ON_HOLDER_ATTACH,
        ON_HOLDER_DETACH,
        ON_HOLDER_TRIM,
        ON_HOLDER_UNTRIM,
        ON_DRAWABLE_SHOW,
        ON_DRAWABLE_HIDE,
        ON_ACTIVITY_START,
        ON_ACTIVITY_STOP,
        ON_RUN_CLEAR_CONTROLLER,
        ON_SCHEDULE_CLEAR_CONTROLLER,
        ON_SAME_CONTROLLER_SKIPPED,
        ON_SUBMIT_CACHE_HIT;

    }

}

