/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.net.Uri
 *  android.os.Binder
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.IInterface
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.RemoteException
 *  android.text.TextUtils
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.app.PendingIntent;
import android.net.Uri;
import android.os.Binder;
import android.os.Bundle;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.RemoteException;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.RatingCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import java.util.ArrayList;
import java.util.List;

public interface IMediaSession
extends IInterface {
    public void addQueueItem(MediaDescriptionCompat var1) throws RemoteException;

    public void addQueueItemAt(MediaDescriptionCompat var1, int var2) throws RemoteException;

    public void adjustVolume(int var1, int var2, String var3) throws RemoteException;

    public void fastForward() throws RemoteException;

    public Bundle getExtras() throws RemoteException;

    public long getFlags() throws RemoteException;

    public PendingIntent getLaunchPendingIntent() throws RemoteException;

    public MediaMetadataCompat getMetadata() throws RemoteException;

    public String getPackageName() throws RemoteException;

    public PlaybackStateCompat getPlaybackState() throws RemoteException;

    public List<MediaSessionCompat.QueueItem> getQueue() throws RemoteException;

    public CharSequence getQueueTitle() throws RemoteException;

    public int getRatingType() throws RemoteException;

    public int getRepeatMode() throws RemoteException;

    public int getShuffleMode() throws RemoteException;

    public String getTag() throws RemoteException;

    public ParcelableVolumeInfo getVolumeAttributes() throws RemoteException;

    public boolean isCaptioningEnabled() throws RemoteException;

    public boolean isShuffleModeEnabledDeprecated() throws RemoteException;

    public boolean isTransportControlEnabled() throws RemoteException;

    public void next() throws RemoteException;

    public void pause() throws RemoteException;

    public void play() throws RemoteException;

    public void playFromMediaId(String var1, Bundle var2) throws RemoteException;

    public void playFromSearch(String var1, Bundle var2) throws RemoteException;

    public void playFromUri(Uri var1, Bundle var2) throws RemoteException;

    public void prepare() throws RemoteException;

    public void prepareFromMediaId(String var1, Bundle var2) throws RemoteException;

    public void prepareFromSearch(String var1, Bundle var2) throws RemoteException;

    public void prepareFromUri(Uri var1, Bundle var2) throws RemoteException;

    public void previous() throws RemoteException;

    public void rate(RatingCompat var1) throws RemoteException;

    public void rateWithExtras(RatingCompat var1, Bundle var2) throws RemoteException;

    public void registerCallbackListener(IMediaControllerCallback var1) throws RemoteException;

    public void removeQueueItem(MediaDescriptionCompat var1) throws RemoteException;

    public void removeQueueItemAt(int var1) throws RemoteException;

    public void rewind() throws RemoteException;

    public void seekTo(long var1) throws RemoteException;

    public void sendCommand(String var1, Bundle var2, MediaSessionCompat.ResultReceiverWrapper var3) throws RemoteException;

    public void sendCustomAction(String var1, Bundle var2) throws RemoteException;

    public boolean sendMediaButton(KeyEvent var1) throws RemoteException;

    public void setCaptioningEnabled(boolean var1) throws RemoteException;

    public void setRepeatMode(int var1) throws RemoteException;

    public void setShuffleMode(int var1) throws RemoteException;

    public void setShuffleModeEnabledDeprecated(boolean var1) throws RemoteException;

    public void setVolumeTo(int var1, int var2, String var3) throws RemoteException;

    public void skipToQueueItem(long var1) throws RemoteException;

    public void stop() throws RemoteException;

    public void unregisterCallbackListener(IMediaControllerCallback var1) throws RemoteException;

    public static abstract class Stub
    extends Binder
    implements IMediaSession {
        public static IMediaSession asInterface(IBinder iBinder) {
            if (iBinder == null) {
                return null;
            }
            IInterface iInterface = iBinder.queryLocalInterface("android.support.v4.media.session.IMediaSession");
            if (iInterface != null && iInterface instanceof IMediaSession) {
                return (IMediaSession)iInterface;
            }
            return new Proxy(iBinder);
        }

        public IBinder asBinder() {
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTransact(int n, Parcel object, Parcel parcel, int n2) throws RemoteException {
            int n3 = 0;
            int n4 = 0;
            int n5 = 0;
            int n6 = 0;
            switch (n) {
                default: {
                    return super.onTransact(n, (Parcel)object, parcel, n2);
                }
                case 1598968902: {
                    parcel.writeString("android.support.v4.media.session.IMediaSession");
                    return true;
                }
                case 1: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    String string2 = object.readString();
                    Bundle bundle = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (MediaSessionCompat.ResultReceiverWrapper)MediaSessionCompat.ResultReceiverWrapper.CREATOR.createFromParcel((Parcel)object) : null;
                    this.sendCommand(string2, bundle, (MediaSessionCompat.ResultReceiverWrapper)object);
                    parcel.writeNoException();
                    return true;
                }
                case 2: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = object.readInt() != 0 ? (KeyEvent)KeyEvent.CREATOR.createFromParcel((Parcel)object) : null;
                    boolean bl = this.sendMediaButton((KeyEvent)object);
                    parcel.writeNoException();
                    n = n6;
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    return true;
                }
                case 3: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.registerCallbackListener(IMediaControllerCallback.Stub.asInterface(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 4: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.unregisterCallbackListener(IMediaControllerCallback.Stub.asInterface(object.readStrongBinder()));
                    parcel.writeNoException();
                    return true;
                }
                case 5: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    boolean bl = this.isTransportControlEnabled();
                    parcel.writeNoException();
                    n = n3;
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    return true;
                }
                case 6: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = this.getPackageName();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 7: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = this.getTag();
                    parcel.writeNoException();
                    parcel.writeString((String)object);
                    return true;
                }
                case 8: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = this.getLaunchPendingIntent();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 9: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    long l = this.getFlags();
                    parcel.writeNoException();
                    parcel.writeLong(l);
                    return true;
                }
                case 10: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = this.getVolumeAttributes();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((ParcelableVolumeInfo)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 11: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.adjustVolume(object.readInt(), object.readInt(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 12: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.setVolumeTo(object.readInt(), object.readInt(), object.readString());
                    parcel.writeNoException();
                    return true;
                }
                case 27: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = this.getMetadata();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((MediaMetadataCompat)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 28: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = this.getPlaybackState();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        ((PlaybackStateCompat)object).writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 29: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = this.getQueue();
                    parcel.writeNoException();
                    parcel.writeTypedList((List)object);
                    return true;
                }
                case 30: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = this.getQueueTitle();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        TextUtils.writeToParcel((CharSequence)object, (Parcel)parcel, (int)1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 31: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = this.getExtras();
                    parcel.writeNoException();
                    if (object != null) {
                        parcel.writeInt(1);
                        object.writeToParcel(parcel, 1);
                        return true;
                    }
                    parcel.writeInt(0);
                    return true;
                }
                case 32: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    n = this.getRatingType();
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                case 45: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    boolean bl = this.isCaptioningEnabled();
                    parcel.writeNoException();
                    n = n4;
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    return true;
                }
                case 37: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    n = this.getRepeatMode();
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                case 38: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    boolean bl = this.isShuffleModeEnabledDeprecated();
                    parcel.writeNoException();
                    n = n5;
                    if (bl) {
                        n = 1;
                    }
                    parcel.writeInt(n);
                    return true;
                }
                case 47: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    n = this.getShuffleMode();
                    parcel.writeNoException();
                    parcel.writeInt(n);
                    return true;
                }
                case 41: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = object.readInt() != 0 ? (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel((Parcel)object) : null;
                    this.addQueueItem((MediaDescriptionCompat)object);
                    parcel.writeNoException();
                    return true;
                }
                case 42: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    MediaDescriptionCompat mediaDescriptionCompat = object.readInt() != 0 ? (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel((Parcel)object) : null;
                    this.addQueueItemAt(mediaDescriptionCompat, object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 43: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = object.readInt() != 0 ? (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel((Parcel)object) : null;
                    this.removeQueueItem((MediaDescriptionCompat)object);
                    parcel.writeNoException();
                    return true;
                }
                case 44: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.removeQueueItemAt(object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 33: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.prepare();
                    parcel.writeNoException();
                    return true;
                }
                case 34: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    String string3 = object.readString();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.prepareFromMediaId(string3, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 35: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    String string4 = object.readString();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.prepareFromSearch(string4, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 36: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    Uri uri = object.readInt() != 0 ? (Uri)Uri.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.prepareFromUri(uri, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 13: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.play();
                    parcel.writeNoException();
                    return true;
                }
                case 14: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    String string5 = object.readString();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.playFromMediaId(string5, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 15: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    String string6 = object.readString();
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.playFromSearch(string6, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 16: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    Uri uri = object.readInt() != 0 ? (Uri)Uri.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.playFromUri(uri, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 17: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.skipToQueueItem(object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 18: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.pause();
                    parcel.writeNoException();
                    return true;
                }
                case 19: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.stop();
                    parcel.writeNoException();
                    return true;
                }
                case 20: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.next();
                    parcel.writeNoException();
                    return true;
                }
                case 21: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.previous();
                    parcel.writeNoException();
                    return true;
                }
                case 22: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.fastForward();
                    parcel.writeNoException();
                    return true;
                }
                case 23: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.rewind();
                    parcel.writeNoException();
                    return true;
                }
                case 24: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.seekTo(object.readLong());
                    parcel.writeNoException();
                    return true;
                }
                case 25: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    object = object.readInt() != 0 ? (RatingCompat)RatingCompat.CREATOR.createFromParcel((Parcel)object) : null;
                    this.rate((RatingCompat)object);
                    parcel.writeNoException();
                    return true;
                }
                case 51: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    RatingCompat ratingCompat = object.readInt() != 0 ? (RatingCompat)RatingCompat.CREATOR.createFromParcel((Parcel)object) : null;
                    object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
                    this.rateWithExtras(ratingCompat, (Bundle)object);
                    parcel.writeNoException();
                    return true;
                }
                case 46: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    boolean bl = object.readInt() != 0;
                    this.setCaptioningEnabled(bl);
                    parcel.writeNoException();
                    return true;
                }
                case 39: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.setRepeatMode(object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 40: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    boolean bl = object.readInt() != 0;
                    this.setShuffleModeEnabledDeprecated(bl);
                    parcel.writeNoException();
                    return true;
                }
                case 48: {
                    object.enforceInterface("android.support.v4.media.session.IMediaSession");
                    this.setShuffleMode(object.readInt());
                    parcel.writeNoException();
                    return true;
                }
                case 26: 
            }
            object.enforceInterface("android.support.v4.media.session.IMediaSession");
            String string7 = object.readString();
            object = object.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel((Parcel)object) : null;
            this.sendCustomAction(string7, (Bundle)object);
            parcel.writeNoException();
            return true;
        }

        private static class Proxy
        implements IMediaSession {
            private IBinder mRemote;

            Proxy(IBinder iBinder) {
                this.mRemote = iBinder;
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addQueueItem(MediaDescriptionCompat mediaDescriptionCompat) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (mediaDescriptionCompat != null) {
                        parcel.writeInt(1);
                        mediaDescriptionCompat.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(41, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void addQueueItemAt(MediaDescriptionCompat mediaDescriptionCompat, int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (mediaDescriptionCompat != null) {
                        parcel.writeInt(1);
                        mediaDescriptionCompat.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    parcel.writeInt(n);
                    this.mRemote.transact(42, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void adjustVolume(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.mRemote.transact(11, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            @Override
            public void fastForward() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(22, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Bundle getExtras() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(31, parcel, parcel2, 0);
                    parcel2.readException();
                    Bundle bundle = parcel2.readInt() != 0 ? (Bundle)Bundle.CREATOR.createFromParcel(parcel2) : null;
                    return bundle;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public long getFlags() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(9, parcel, parcel2, 0);
                    parcel2.readException();
                    long l = parcel2.readLong();
                    return l;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public PendingIntent getLaunchPendingIntent() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(8, parcel, parcel2, 0);
                    parcel2.readException();
                    PendingIntent pendingIntent = parcel2.readInt() != 0 ? (PendingIntent)PendingIntent.CREATOR.createFromParcel(parcel2) : null;
                    return pendingIntent;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public MediaMetadataCompat getMetadata() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(27, parcel, parcel2, 0);
                    parcel2.readException();
                    MediaMetadataCompat mediaMetadataCompat = parcel2.readInt() != 0 ? (MediaMetadataCompat)MediaMetadataCompat.CREATOR.createFromParcel(parcel2) : null;
                    return mediaMetadataCompat;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getPackageName() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(6, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public PlaybackStateCompat getPlaybackState() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(28, parcel, parcel2, 0);
                    parcel2.readException();
                    PlaybackStateCompat playbackStateCompat = parcel2.readInt() != 0 ? (PlaybackStateCompat)PlaybackStateCompat.CREATOR.createFromParcel(parcel2) : null;
                    return playbackStateCompat;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public List<MediaSessionCompat.QueueItem> getQueue() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(29, parcel, parcel2, 0);
                    parcel2.readException();
                    ArrayList arrayList = parcel2.createTypedArrayList(MediaSessionCompat.QueueItem.CREATOR);
                    return arrayList;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public CharSequence getQueueTitle() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(30, parcel, parcel2, 0);
                    parcel2.readException();
                    CharSequence charSequence = parcel2.readInt() != 0 ? (CharSequence)TextUtils.CHAR_SEQUENCE_CREATOR.createFromParcel(parcel2) : null;
                    return charSequence;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getRatingType() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(32, parcel, parcel2, 0);
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getRepeatMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(37, parcel, parcel2, 0);
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public int getShuffleMode() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(47, parcel, parcel2, 0);
                    parcel2.readException();
                    int n = parcel2.readInt();
                    return n;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public String getTag() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(7, parcel, parcel2, 0);
                    parcel2.readException();
                    String string2 = parcel2.readString();
                    return string2;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public ParcelableVolumeInfo getVolumeAttributes() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(10, parcel, parcel2, 0);
                    parcel2.readException();
                    ParcelableVolumeInfo parcelableVolumeInfo = parcel2.readInt() != 0 ? (ParcelableVolumeInfo)ParcelableVolumeInfo.CREATOR.createFromParcel(parcel2) : null;
                    return parcelableVolumeInfo;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public boolean isCaptioningEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block2: {
                    bl = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                        this.mRemote.transact(45, parcel, parcel2, 0);
                        parcel2.readException();
                        int n = parcel2.readInt();
                        if (n == 0) break block2;
                        bl = true;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isShuffleModeEnabledDeprecated() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block2: {
                    bl = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                        this.mRemote.transact(38, parcel, parcel2, 0);
                        parcel2.readException();
                        int n = parcel2.readInt();
                        if (n == 0) break block2;
                        bl = true;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public boolean isTransportControlEnabled() throws RemoteException {
                Parcel parcel;
                Parcel parcel2;
                boolean bl;
                block2: {
                    bl = false;
                    parcel = Parcel.obtain();
                    parcel2 = Parcel.obtain();
                    try {
                        parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                        this.mRemote.transact(5, parcel, parcel2, 0);
                        parcel2.readException();
                        int n = parcel2.readInt();
                        if (n == 0) break block2;
                        bl = true;
                    }
                    catch (Throwable throwable) {
                        parcel2.recycle();
                        parcel.recycle();
                        throw throwable;
                    }
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void next() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(20, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void pause() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(18, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void play() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(13, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void playFromMediaId(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(14, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void playFromSearch(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(15, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void playFromUri(Uri uri, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(16, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void prepare() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(33, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void prepareFromMediaId(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(34, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void prepareFromSearch(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(35, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void prepareFromUri(Uri uri, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (uri != null) {
                        parcel.writeInt(1);
                        uri.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(36, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void previous() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(21, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void rate(RatingCompat ratingCompat) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (ratingCompat != null) {
                        parcel.writeInt(1);
                        ratingCompat.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(25, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void rateWithExtras(RatingCompat ratingCompat, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (ratingCompat != null) {
                        parcel.writeInt(1);
                        ratingCompat.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(51, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void registerCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    iMediaControllerCallback = iMediaControllerCallback != null ? iMediaControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iMediaControllerCallback);
                    this.mRemote.transact(3, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void removeQueueItem(MediaDescriptionCompat mediaDescriptionCompat) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (mediaDescriptionCompat != null) {
                        parcel.writeInt(1);
                        mediaDescriptionCompat.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(43, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void removeQueueItemAt(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeInt(n);
                    this.mRemote.transact(44, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void rewind() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(23, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void seekTo(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeLong(l);
                    this.mRemote.transact(24, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void sendCommand(String string2, Bundle bundle, MediaSessionCompat.ResultReceiverWrapper resultReceiverWrapper) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    if (resultReceiverWrapper != null) {
                        parcel.writeInt(1);
                        resultReceiverWrapper.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(1, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void sendCustomAction(String string2, Bundle bundle) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeString(string2);
                    if (bundle != null) {
                        parcel.writeInt(1);
                        bundle.writeToParcel(parcel, 0);
                    } else {
                        parcel.writeInt(0);
                    }
                    this.mRemote.transact(26, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public boolean sendMediaButton(KeyEvent keyEvent) throws RemoteException {
                Parcel parcel;
                boolean bl;
                Parcel parcel2;
                block6: {
                    block5: {
                        bl = true;
                        parcel = Parcel.obtain();
                        parcel2 = Parcel.obtain();
                        try {
                            parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                            if (keyEvent != null) {
                                parcel.writeInt(1);
                                keyEvent.writeToParcel(parcel, 0);
                            } else {
                                parcel.writeInt(0);
                            }
                            this.mRemote.transact(2, parcel, parcel2, 0);
                            parcel2.readException();
                            int n = parcel2.readInt();
                            if (n == 0) break block5;
                            break block6;
                        }
                        catch (Throwable throwable) {
                            parcel2.recycle();
                            parcel.recycle();
                            throw throwable;
                        }
                    }
                    bl = false;
                }
                parcel2.recycle();
                parcel.recycle();
                return bl;
            }

            @Override
            public void setCaptioningEnabled(boolean bl) throws RemoteException {
                int n;
                Parcel parcel;
                Parcel parcel2;
                block4: {
                    n = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (!bl) break block4;
                    n = 1;
                }
                try {
                    parcel2.writeInt(n);
                    this.mRemote.transact(46, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setRepeatMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeInt(n);
                    this.mRemote.transact(39, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setShuffleMode(int n) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeInt(n);
                    this.mRemote.transact(48, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void setShuffleModeEnabledDeprecated(boolean bl) throws RemoteException {
                int n;
                Parcel parcel;
                Parcel parcel2;
                block4: {
                    n = 0;
                    parcel2 = Parcel.obtain();
                    parcel = Parcel.obtain();
                    parcel2.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    if (!bl) break block4;
                    n = 1;
                }
                try {
                    parcel2.writeInt(n);
                    this.mRemote.transact(40, parcel2, parcel, 0);
                    parcel.readException();
                    return;
                }
                finally {
                    parcel.recycle();
                    parcel2.recycle();
                }
            }

            @Override
            public void setVolumeTo(int n, int n2, String string2) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeInt(n);
                    parcel.writeInt(n2);
                    parcel.writeString(string2);
                    this.mRemote.transact(12, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void skipToQueueItem(long l) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    parcel.writeLong(l);
                    this.mRemote.transact(17, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            @Override
            public void stop() throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    this.mRemote.transact(19, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void unregisterCallbackListener(IMediaControllerCallback iMediaControllerCallback) throws RemoteException {
                Parcel parcel = Parcel.obtain();
                Parcel parcel2 = Parcel.obtain();
                try {
                    parcel.writeInterfaceToken("android.support.v4.media.session.IMediaSession");
                    iMediaControllerCallback = iMediaControllerCallback != null ? iMediaControllerCallback.asBinder() : null;
                    parcel.writeStrongBinder((IBinder)iMediaControllerCallback);
                    this.mRemote.transact(4, parcel, parcel2, 0);
                    parcel2.readException();
                    return;
                }
                finally {
                    parcel2.recycle();
                    parcel.recycle();
                }
            }
        }

    }

}

