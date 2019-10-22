/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.text.TextUtils
 */
package android.support.v4.media.session;

import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import java.util.ArrayList;
import java.util.List;

public interface IMediaControllerCallback
extends IInterface {
    public void onCaptioningEnabledChanged(boolean var1) throws RemoteException;

    public void onEvent(String var1, Bundle var2) throws RemoteException;

    public void onExtrasChanged(Bundle var1) throws RemoteException;

    public void onMetadataChanged(MediaMetadataCompat var1) throws RemoteException;

    public void onPlaybackStateChanged(PlaybackStateCompat var1) throws RemoteException;

    public void onQueueChanged(List<MediaSessionCompat.QueueItem> var1) throws RemoteException;

    public void onQueueTitleChanged(CharSequence var1) throws RemoteException;

    public void onRepeatModeChanged(int var1) throws RemoteException;

    public void onSessionDestroyed() throws RemoteException;

    public void onShuffleModeChanged(int var1) throws RemoteException;

    public void onShuffleModeChangedDeprecated(boolean var1) throws RemoteException;

    public void onVolumeInfoChanged(ParcelableVolumeInfo var1) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IMediaControllerCallback {
        public Stub() {
            this.attachInterface((IInterface)this, "android.support.v4.media.session.IMediaControllerCallback");
        }

        public static IMediaControllerCallback asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("android.support.v4.media.session.IMediaControllerCallback");
            if (iInterface != null && iInterface instanceof IMediaControllerCallback) {
                return (IMediaControllerCallback)iInterface;
            }
            return new Proxy(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n, Parcel object, Parcel object2, int n2) throws RemoteException {
            boolean bl = false;
            boolean bl2 = false;
            switch (n) {
                default: {
                    void var3_21;
                    void var4_23;
                    return super.onTransact(n, object, (Parcel)var3_21, (int)var4_23);
                }
                case 1598968902: {
                    void var3_21;
                    var3_21.writeString("android.support.v4.media.session.IMediaControllerCallback");
                    return true;
                }
                case 1: {
                    void var2_4;
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    String string2 = object.readString();
                    if (object.readInt() != 0) {
                        Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel(object);
                    } else {
                        Object var2_5 = null;
                    }
                    this.onEvent(string2, (Bundle)var2_4);
                    return true;
                }
                case 2: {
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    this.onSessionDestroyed();
                    return true;
                }
                case 3: {
                    void var2_7;
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    if (object.readInt() != 0) {
                        PlaybackStateCompat playbackStateCompat = (PlaybackStateCompat)PlaybackStateCompat.CREATOR.createFromParcel(object);
                    } else {
                        Object var2_8 = null;
                    }
                    this.onPlaybackStateChanged((PlaybackStateCompat)var2_7);
                    return true;
                }
                case 4: {
                    void var2_10;
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    if (object.readInt() != 0) {
                        MediaMetadataCompat mediaMetadataCompat = (MediaMetadataCompat)MediaMetadataCompat.CREATOR.createFromParcel(object);
                    } else {
                        Object var2_11 = null;
                    }
                    this.onMetadataChanged((MediaMetadataCompat)var2_10);
                    return true;
                }
                case 5: {
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    this.onQueueChanged((List)object.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR));
                    return true;
                }
                case 6: {
                    void var2_13;
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    if (object.readInt() != 0) {
                        CharSequence charSequence = (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(object);
                    } else {
                        Object var2_14 = null;
                    }
                    this.onQueueTitleChanged((CharSequence)var2_13);
                    return true;
                }
                case 7: {
                    void var2_16;
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    if (object.readInt() != 0) {
                        Bundle bundle = (Bundle)Bundle.CREATOR.createFromParcel(object);
                    } else {
                        Object var2_17 = null;
                    }
                    this.onExtrasChanged((Bundle)var2_16);
                    return true;
                }
                case 8: {
                    void var2_19;
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    if (object.readInt() != 0) {
                        ParcelableVolumeInfo parcelableVolumeInfo = (ParcelableVolumeInfo)ParcelableVolumeInfo.CREATOR.createFromParcel(object);
                    } else {
                        Object var2_20 = null;
                    }
                    this.onVolumeInfoChanged((ParcelableVolumeInfo)var2_19);
                    return true;
                }
                case 9: {
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    this.onRepeatModeChanged(object.readInt());
                    return true;
                }
                case 10: {
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.onShuffleModeChangedDeprecated(bl2);
                    return true;
                }
                case 11: {
                    object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
                    bl2 = bl;
                    if (object.readInt() != 0) {
                        bl2 = true;
                    }
                    this.onCaptioningEnabledChanged(bl2);
                    return true;
                }
                case 12: 
            }
            object.enforceInterface("android.support.v4.media.session.IMediaControllerCallback");
            this.onShuffleModeChanged(object.readInt());
            return true;
        }

        private static class Proxy
        implements IMediaControllerCallback {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onCaptioningEnabledChanged(boolean bl) throws RemoteException {
                int n = 1;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    this.mRemote.transact(11, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onEvent(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(1, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(7, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    if (mediaMetadataCompat != null) {
                        parcel.writeInt(1);
                        mediaMetadataCompat.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(4, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    if (playbackStateCompat != null) {
                        parcel.writeInt(1);
                        playbackStateCompat.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(3, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    parcel.writeTypedList(list);
                    this.mRemote.transact(5, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    if (charSequence != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel((CharSequence)charSequence, (Parcel)parcel, (int)0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(6, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onRepeatModeChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    parcel.writeInt(n);
                    this.mRemote.transact(9, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onSessionDestroyed() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    this.mRemote.transact(2, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            @Override
            public void onShuffleModeChanged(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    parcel.writeInt(n);
                    this.mRemote.transact(12, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onShuffleModeChangedDeprecated(boolean bl) throws RemoteException {
                int n = 1;
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    if (!bl) {
                        n = 0;
                    }
                    parcel.writeInt(n);
                    this.mRemote.transact(10, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaControllerCallback");
                    if (parcelableVolumeInfo != null) {
                        parcel.writeInt(1);
                        parcelableVolumeInfo.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(8, parcel, null, 1);
                    return;
                }
                finally {
                    parcel.recycle();
                }
            }
        }

    }

}

