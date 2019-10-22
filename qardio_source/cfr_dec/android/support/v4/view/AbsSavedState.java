/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 */
package android.support.v4.view;

import android.os.Parcel;
import android.os.Parcelable;

public abstract class AbsSavedState
implements Parcelable {
    public static final Parcelable.Creator<AbsSavedState> CREATOR;
    public static final AbsSavedState EMPTY_STATE;
    private final Parcelable mSuperState;

    static {
        EMPTY_STATE = new AbsSavedState(){};
        CREATOR = new Parcelable.ClassLoaderCreator<AbsSavedState>(){

            public AbsSavedState createFromParcel(Parcel parcel) {
                return this.createFromParcel(parcel, null);
            }

            public AbsSavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                if (parcel.readParcelable(classLoader) != null) {
                    throw new IllegalStateException("superState must be null");
                }
                return EMPTY_STATE;
            }

            public AbsSavedState[] newArray(int n) {
                return new AbsSavedState[n];
            }
        };
    }

    private AbsSavedState() {
        this.mSuperState = null;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    protected AbsSavedState(Parcel object, ClassLoader classLoader) {
        void var1_3;
        void var2_5;
        Parcelable parcelable = object.readParcelable((ClassLoader)var2_5);
        if (parcelable == null) {
            AbsSavedState absSavedState = EMPTY_STATE;
        }
        this.mSuperState = var1_3;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected AbsSavedState(Parcelable parcelable) {
        if (parcelable == null) {
            throw new IllegalArgumentException("superState must not be null");
        }
        if (parcelable == EMPTY_STATE) {
            parcelable = null;
        }
        this.mSuperState = parcelable;
    }

    public int describeContents() {
        return 0;
    }

    public final Parcelable getSuperState() {
        return this.mSuperState;
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeParcelable(this.mSuperState, n);
    }

}

