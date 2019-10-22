/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.Window
 */
package com.mixpanel.android.viewcrawler;

import android.app.Activity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import com.mixpanel.android.mpmetrics.MixpanelAPI;

public class GestureTracker {
    public GestureTracker(MixpanelAPI mixpanelAPI, Activity activity) {
        this.trackGestures(mixpanelAPI, activity);
    }

    private View.OnTouchListener getGestureTrackerTouchListener(final MixpanelAPI mixpanelAPI) {
        return new View.OnTouchListener(){
            private final int TIME_BETWEEN_FINGERS_THRESHOLD;
            private final int TIME_BETWEEN_TAPS_THRESHOLD;
            private final int TIME_FOR_LONG_TAP;
            private boolean mDidTapDownBothFingers = false;
            private long mFirstToSecondFingerDifference = -1L;
            private int mGestureSteps = 0;
            private long mSecondFingerTimeDown = -1L;
            private long mTimePassedBetweenTaps = -1L;
            {
                this.TIME_BETWEEN_FINGERS_THRESHOLD = 100;
                this.TIME_BETWEEN_TAPS_THRESHOLD = 1000;
                this.TIME_FOR_LONG_TAP = 2500;
            }

            private void resetGesture() {
                this.mFirstToSecondFingerDifference = -1L;
                this.mSecondFingerTimeDown = -1L;
                this.mGestureSteps = 0;
                this.mTimePassedBetweenTaps = -1L;
                this.mDidTapDownBothFingers = false;
            }

            /*
             * Enabled aggressive block sorting
             */
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getPointerCount() > 2) {
                    this.resetGesture();
                    return false;
                }
                switch (motionEvent.getActionMasked()) {
                    default: {
                        return false;
                    }
                    case 0: {
                        this.mFirstToSecondFingerDifference = System.currentTimeMillis();
                        return false;
                    }
                    case 5: {
                        if (System.currentTimeMillis() - this.mFirstToSecondFingerDifference >= 100L) {
                            this.resetGesture();
                            return false;
                        }
                        if (System.currentTimeMillis() - this.mTimePassedBetweenTaps > 1000L) {
                            this.resetGesture();
                        }
                        this.mSecondFingerTimeDown = System.currentTimeMillis();
                        this.mDidTapDownBothFingers = true;
                        return false;
                    }
                    case 6: {
                        if (this.mDidTapDownBothFingers) {
                            this.mFirstToSecondFingerDifference = System.currentTimeMillis();
                            return false;
                        }
                        this.resetGesture();
                        return false;
                    }
                    case 1: 
                }
                if (System.currentTimeMillis() - this.mFirstToSecondFingerDifference >= 100L) return false;
                {
                    if (System.currentTimeMillis() - this.mSecondFingerTimeDown >= 2500L) {
                        if (this.mGestureSteps == 3) {
                            mixpanelAPI.track("$ab_gesture1");
                            this.resetGesture();
                        }
                        this.mGestureSteps = 0;
                        return false;
                    }
                }
                this.mTimePassedBetweenTaps = System.currentTimeMillis();
                if (this.mGestureSteps < 4) {
                    ++this.mGestureSteps;
                    return false;
                }
                if (this.mGestureSteps == 4) {
                    mixpanelAPI.track("$ab_gesture2");
                    this.resetGesture();
                    return false;
                }
                this.resetGesture();
                return false;
            }
        };
    }

    private void trackGestures(MixpanelAPI mixpanelAPI, Activity activity) {
        activity.getWindow().getDecorView().setOnTouchListener(this.getGestureTrackerTouchListener(mixpanelAPI));
    }

}

