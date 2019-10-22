/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.mixpanel.android.mpmetrics;

import android.annotation.TargetApi;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.mixpanel.android.mpmetrics.InAppNotification;
import com.mixpanel.android.util.MPLog;
import java.util.concurrent.locks.ReentrantLock;

@TargetApi(value=16)
public class UpdateDisplayState
implements Parcelable {
    public static final Parcelable.Creator<UpdateDisplayState> CREATOR = new Parcelable.Creator<UpdateDisplayState>(){

        public UpdateDisplayState createFromParcel(Parcel parcel) {
            Bundle bundle = new Bundle(UpdateDisplayState.class.getClassLoader());
            bundle.readFromParcel(parcel);
            return new UpdateDisplayState(bundle);
        }

        public UpdateDisplayState[] newArray(int n) {
            return new UpdateDisplayState[n];
        }
    };
    private static int sNextIntentId;
    private static int sShowingIntentId;
    private static final ReentrantLock sUpdateDisplayLock;
    private static long sUpdateDisplayLockMillis;
    private static UpdateDisplayState sUpdateDisplayState;
    private final DisplayState mDisplayState;
    private final String mDistinctId;
    private final String mToken;

    static {
        sUpdateDisplayLock = new ReentrantLock();
        sUpdateDisplayLockMillis = -1L;
        sUpdateDisplayState = null;
        sNextIntentId = 0;
        sShowingIntentId = -1;
    }

    private UpdateDisplayState(Bundle bundle) {
        this.mDistinctId = bundle.getString("com.mixpanel.android.mpmetrics.UpdateDisplayState.DISTINCT_ID_BUNDLE_KEY");
        this.mToken = bundle.getString("com.mixpanel.android.mpmetrics.UpdateDisplayState.TOKEN_BUNDLE_KEY");
        this.mDisplayState = (DisplayState)bundle.getParcelable("com.mixpanel.android.mpmetrics.UpdateDisplayState.DISPLAYSTATE_BUNDLE_KEY");
    }

    UpdateDisplayState(DisplayState displayState, String string2, String string3) {
        this.mDistinctId = string2;
        this.mToken = string3;
        this.mDisplayState = displayState;
    }

    public static UpdateDisplayState claimDisplayState(int n) {
        UpdateDisplayState updateDisplayState;
        block6: {
            block5: {
                int n2;
                sUpdateDisplayLock.lock();
                if (sShowingIntentId <= 0 || (n2 = sShowingIntentId) == n) break block5;
                sUpdateDisplayLock.unlock();
                return null;
            }
            updateDisplayState = sUpdateDisplayState;
            if (updateDisplayState != null) break block6;
            sUpdateDisplayLock.unlock();
            return null;
        }
        try {
            sUpdateDisplayLockMillis = System.currentTimeMillis();
            sShowingIntentId = n;
            updateDisplayState = sUpdateDisplayState;
            return updateDisplayState;
        }
        finally {
            sUpdateDisplayLock.unlock();
        }
    }

    static ReentrantLock getLockObject() {
        return sUpdateDisplayLock;
    }

    static boolean hasCurrentProposal() {
        if (!sUpdateDisplayLock.isHeldByCurrentThread()) {
            throw new AssertionError();
        }
        long l = System.currentTimeMillis();
        long l2 = sUpdateDisplayLockMillis;
        if (sNextIntentId > 0 && l - l2 > 43200000L) {
            MPLog.i("MixpanelAPI.UpDisplSt", "UpdateDisplayState set long, long ago, without showing. Update state will be cleared.");
            sUpdateDisplayState = null;
        }
        return sUpdateDisplayState != null;
    }

    static int proposeDisplay(DisplayState displayState, String string2, String string3) {
        if (!sUpdateDisplayLock.isHeldByCurrentThread()) {
            throw new AssertionError();
        }
        if (!UpdateDisplayState.hasCurrentProposal()) {
            sUpdateDisplayLockMillis = System.currentTimeMillis();
            sUpdateDisplayState = new UpdateDisplayState(displayState, string2, string3);
            return ++sNextIntentId;
        }
        MPLog.v("MixpanelAPI.UpDisplSt", "Already showing (or cooking) a Mixpanel update, declining to show another.");
        return -1;
    }

    public static void releaseDisplayState(int n) {
        sUpdateDisplayLock.lock();
        try {
            if (n == sShowingIntentId) {
                sShowingIntentId = -1;
                sUpdateDisplayState = null;
            }
            return;
        }
        finally {
            sUpdateDisplayLock.unlock();
        }
    }

    public int describeContents() {
        return 0;
    }

    public DisplayState getDisplayState() {
        return this.mDisplayState;
    }

    public String getToken() {
        return this.mToken;
    }

    public void writeToParcel(Parcel parcel, int n) {
        Bundle bundle = new Bundle();
        bundle.putString("com.mixpanel.android.mpmetrics.UpdateDisplayState.DISTINCT_ID_BUNDLE_KEY", this.mDistinctId);
        bundle.putString("com.mixpanel.android.mpmetrics.UpdateDisplayState.TOKEN_BUNDLE_KEY", this.mToken);
        bundle.putParcelable("com.mixpanel.android.mpmetrics.UpdateDisplayState.DISPLAYSTATE_BUNDLE_KEY", (Parcelable)this.mDisplayState);
        parcel.writeBundle(bundle);
    }

    public static abstract class DisplayState
    implements Parcelable {
        public static final Parcelable.Creator<DisplayState> CREATOR = new Parcelable.Creator<DisplayState>(){

            public DisplayState createFromParcel(Parcel object) {
                Bundle bundle = new Bundle(DisplayState.class.getClassLoader());
                bundle.readFromParcel(object);
                object = bundle.getString("com.mixpanel.android.mpmetrics.UpdateDisplayState.DisplayState.STATE_TYPE_KEY");
                bundle = bundle.getBundle("com.mixpanel.android.mpmetrics.UpdateDisplayState.DisplayState.STATE_IMPL_KEY");
                if ("InAppNotificationState".equals(object)) {
                    return new InAppNotificationState(bundle);
                }
                throw new RuntimeException("Unrecognized display state type " + (String)object);
            }

            public DisplayState[] newArray(int n) {
                return new DisplayState[n];
            }
        };

        private DisplayState() {
        }

        public static final class InAppNotificationState
        extends DisplayState {
            public static final Parcelable.Creator<InAppNotificationState> CREATOR = new Parcelable.Creator<InAppNotificationState>(){

                public InAppNotificationState createFromParcel(Parcel parcel) {
                    Bundle bundle = new Bundle(InAppNotificationState.class.getClassLoader());
                    bundle.readFromParcel(parcel);
                    return new InAppNotificationState(bundle);
                }

                public InAppNotificationState[] newArray(int n) {
                    return new InAppNotificationState[n];
                }
            };
            private static String HIGHLIGHT_KEY;
            private static String INAPP_KEY;
            private final int mHighlightColor;
            private final InAppNotification mInAppNotification;

            static {
                INAPP_KEY = "com.com.mixpanel.android.mpmetrics.UpdateDisplayState.InAppNotificationState.INAPP_KEY";
                HIGHLIGHT_KEY = "com.com.mixpanel.android.mpmetrics.UpdateDisplayState.InAppNotificationState.HIGHLIGHT_KEY";
            }

            private InAppNotificationState(Bundle bundle) {
                this.mInAppNotification = (InAppNotification)bundle.getParcelable(INAPP_KEY);
                this.mHighlightColor = bundle.getInt(HIGHLIGHT_KEY);
            }

            public InAppNotificationState(InAppNotification inAppNotification, int n) {
                this.mInAppNotification = inAppNotification;
                this.mHighlightColor = n;
            }

            public int describeContents() {
                return 0;
            }

            public InAppNotification getInAppNotification() {
                return this.mInAppNotification;
            }

            public void writeToParcel(Parcel parcel, int n) {
                Bundle bundle = new Bundle();
                bundle.putParcelable(INAPP_KEY, (Parcelable)this.mInAppNotification);
                bundle.putInt(HIGHLIGHT_KEY, this.mHighlightColor);
                parcel.writeBundle(bundle);
            }

        }

    }

}

