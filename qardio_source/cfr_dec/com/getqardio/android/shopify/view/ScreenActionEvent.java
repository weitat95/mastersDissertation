/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 */
package com.getqardio.android.shopify.view;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.getqardio.android.shopify.util.Util;

public abstract class ScreenActionEvent
implements Parcelable {
    protected final String action;
    protected final Bundle payload;

    protected ScreenActionEvent(Parcel parcel) {
        this.action = parcel.readString();
        this.payload = parcel.readBundle(Bundle.class.getClassLoader());
    }

    protected ScreenActionEvent(String string2) {
        this(string2, new Bundle());
    }

    protected ScreenActionEvent(String string2, Bundle bundle) {
        Util.checkNotNull(string2, "action == null");
        Util.checkNotNull(bundle, "payload == null");
        this.action = string2;
        this.payload = bundle;
    }

    public String action() {
        return this.action;
    }

    public int describeContents() {
        return 0;
    }

    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (!(object instanceof ScreenActionEvent)) {
            return false;
        }
        object = (ScreenActionEvent)object;
        return this.action.equals(((ScreenActionEvent)object).action);
    }

    public int hashCode() {
        return this.action.hashCode();
    }

    public Bundle payload() {
        return this.payload;
    }

    public String toString() {
        return "ScreenActionEvent{action='" + this.action + '\'' + ", payload=" + (Object)this.payload + '}';
    }

    public void writeToParcel(Parcel parcel, int n) {
        parcel.writeString(this.action);
        parcel.writeBundle(this.payload);
    }
}

