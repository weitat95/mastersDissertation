/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.database.CursorWindow
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.samsung.android.sdk.internal.database;

import android.database.CursorWindow;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import com.samsung.android.sdk.internal.database.BulkCursorNative;
import com.samsung.android.sdk.internal.database.IBulkCursor;

public final class BulkCursorDescriptor
implements Parcelable {
    public static final Parcelable.Creator<BulkCursorDescriptor> CREATOR = new Parcelable.Creator<BulkCursorDescriptor>(){

        public final /* synthetic */ Object createFromParcel(Parcel parcel) {
            BulkCursorDescriptor bulkCursorDescriptor = new BulkCursorDescriptor();
            bulkCursorDescriptor.readFromParcel(parcel);
            return bulkCursorDescriptor;
        }
    };
    public String[] columnNames;
    public int count;
    public IBulkCursor cursor;
    public boolean wantsAllOnMoveCalls;
    public CursorWindow window;

    public final int describeContents() {
        return 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void readFromParcel(Parcel parcel) {
        this.cursor = BulkCursorNative.asInterface(parcel.readStrongBinder());
        this.columnNames = parcel.createStringArray();
        boolean bl = parcel.readInt() != 0;
        this.wantsAllOnMoveCalls = bl;
        this.count = parcel.readInt();
        if (parcel.readInt() != 0) {
            this.window = (CursorWindow)CursorWindow.CREATOR.createFromParcel(parcel);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public final void writeToParcel(Parcel parcel, int n) {
        parcel.writeStrongBinder(this.cursor.asBinder());
        parcel.writeStringArray(this.columnNames);
        int n2 = this.wantsAllOnMoveCalls ? 1 : 0;
        parcel.writeInt(n2);
        parcel.writeInt(this.count);
        if (this.window != null) {
            parcel.writeInt(1);
            this.window.writeToParcel(parcel, n);
            return;
        }
        parcel.writeInt(0);
    }

}

