/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.text.TextUtils
 */
package android.support.v4.media.session;

import android.os.Build;
import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.media.session.PlaybackStateCompatApi21;
import android.support.v4.media.session.PlaybackStateCompatApi22;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public final class PlaybackStateCompat
implements Parcelable {
    public static final Parcelable.Creator<PlaybackStateCompat> CREATOR = new Parcelable.Creator<PlaybackStateCompat>(){

        public PlaybackStateCompat createFromParcel(Parcel parcel) {
            return new PlaybackStateCompat(parcel);
        }

        public PlaybackStateCompat[] newArray(int n) {
            return new PlaybackStateCompat[n];
        }
    };
    final long mActions;
    final long mActiveItemId;
    final long mBufferedPosition;
    List<CustomAction> mCustomActions;
    final int mErrorCode;
    final CharSequence mErrorMessage;
    final Bundle mExtras;
    final long mPosition;
    final float mSpeed;
    final int mState;
    private Object mStateObj;
    final long mUpdateTime;

    PlaybackStateCompat(int n, long l, long l2, float f, long l3, int n2, CharSequence charSequence, long l4, List<CustomAction> list, long l5, Bundle bundle) {
        this.mState = n;
        this.mPosition = l;
        this.mBufferedPosition = l2;
        this.mSpeed = f;
        this.mActions = l3;
        this.mErrorCode = n2;
        this.mErrorMessage = charSequence;
        this.mUpdateTime = l4;
        this.mCustomActions = new ArrayList<CustomAction>(list);
        this.mActiveItemId = l5;
        this.mExtras = bundle;
    }

    PlaybackStateCompat(Parcel parcel) {
        this.mState = parcel.readInt();
        this.mPosition = parcel.readLong();
        this.mSpeed = parcel.readFloat();
        this.mUpdateTime = parcel.readLong();
        this.mBufferedPosition = parcel.readLong();
        this.mActions = parcel.readLong();
        this.mErrorMessage = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
        this.mCustomActions = parcel.createTypedArrayList(CustomAction.CREATOR);
        this.mActiveItemId = parcel.readLong();
        this.mExtras = parcel.readBundle();
        this.mErrorCode = parcel.readInt();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static PlaybackStateCompat fromPlaybackState(Object object) {
        if (object != null && Build.VERSION.SDK_INT >= 21) {
            Bundle bundle;
            void var1_3;
            Object object2 = PlaybackStateCompatApi21.getCustomActions(object);
            Object var1_2 = null;
            if (object2 != null) {
                bundle = new ArrayList(object2.size());
                object2 = object2.iterator();
                do {
                    Bundle bundle2 = bundle;
                    if (!object2.hasNext()) break;
                    bundle.add(CustomAction.fromCustomAction(object2.next()));
                } while (true);
            }
            bundle = Build.VERSION.SDK_INT >= 22 ? PlaybackStateCompatApi22.getExtras(object) : null;
            PlaybackStateCompat playbackStateCompat = new PlaybackStateCompat(PlaybackStateCompatApi21.getState(object), PlaybackStateCompatApi21.getPosition(object), PlaybackStateCompatApi21.getBufferedPosition(object), PlaybackStateCompatApi21.getPlaybackSpeed(object), PlaybackStateCompatApi21.getActions(object), 0, PlaybackStateCompatApi21.getErrorMessage(object), PlaybackStateCompatApi21.getLastPositionUpdateTime(object), (List<CustomAction>)var1_3, PlaybackStateCompatApi21.getActiveQueueItemId(object), bundle);
            playbackStateCompat.mStateObj = object;
            return playbackStateCompat;
        }
        return null;
    }

    public int describeContents() {
        return 0;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("PlaybackState {");
        stringBuilder.append("state=").append(this.mState);
        stringBuilder.append(", position=").append(this.mPosition);
        stringBuilder.append(", buffered position=").append(this.mBufferedPosition);
        stringBuilder.append(", speed=").append(this.mSpeed);
        stringBuilder.append(", updated=").append(this.mUpdateTime);
        stringBuilder.append(", actions=").append(this.mActions);
        stringBuilder.append(", error code=").append(this.mErrorCode);
        stringBuilder.append(", error message=").append(this.mErrorMessage);
        stringBuilder.append(", custom actions=").append(this.mCustomActions);
        stringBuilder.append(", active item id=").append(this.mActiveItemId);
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeInt(this.mState);
        parcel.writeLong(this.mPosition);
        parcel.writeFloat(this.mSpeed);
        parcel.writeLong(this.mUpdateTime);
        parcel.writeLong(this.mBufferedPosition);
        parcel.writeLong(this.mActions);
        TextUtils.writeToParcel((CharSequence)this.mErrorMessage, (Parcel)parcel, (int)n);
        parcel.writeTypedList(this.mCustomActions);
        parcel.writeLong(this.mActiveItemId);
        parcel.writeBundle(this.mExtras);
        parcel.writeInt(this.mErrorCode);
    }

    public static final class CustomAction
    implements Parcelable {
        public static final Parcelable.Creator<CustomAction> CREATOR = new Parcelable.Creator<CustomAction>(){

            public CustomAction createFromParcel(Parcel parcel) {
                return new CustomAction(parcel);
            }

            public CustomAction[] newArray(int n) {
                return new CustomAction[n];
            }
        };
        private final String mAction;
        private Object mCustomActionObj;
        private final Bundle mExtras;
        private final int mIcon;
        private final CharSequence mName;

        CustomAction(Parcel parcel) {
            this.mAction = parcel.readString();
            this.mName = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel);
            this.mIcon = parcel.readInt();
            this.mExtras = parcel.readBundle();
        }

        CustomAction(String string2, CharSequence charSequence, int n, Bundle bundle) {
            this.mAction = string2;
            this.mName = charSequence;
            this.mIcon = n;
            this.mExtras = bundle;
        }

        public static CustomAction fromCustomAction(Object object) {
            if (object == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            CustomAction customAction = new CustomAction(PlaybackStateCompatApi21.CustomAction.getAction(object), PlaybackStateCompatApi21.CustomAction.getName(object), PlaybackStateCompatApi21.CustomAction.getIcon(object), PlaybackStateCompatApi21.CustomAction.getExtras(object));
            customAction.mCustomActionObj = object;
            return customAction;
        }

        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "Action:mName='" + this.mName + ", mIcon=" + this.mIcon + ", mExtras=" + (Object)this.mExtras;
        }

        public void writeToParcel(Parcel parcel, int n) {
            parcel.writeString(this.mAction);
            TextUtils.writeToParcel((CharSequence)this.mName, (Parcel)parcel, (int)n);
            parcel.writeInt(this.mIcon);
            parcel.writeBundle(this.mExtras);
        }

    }

}

