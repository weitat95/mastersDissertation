/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.IBinder$DeathRecipient
 *  android.os.Message
 *  android.os.RemoteException
 *  android.os.ResultReceiver
 *  android.util.Log
 *  android.view.KeyEvent
 */
package android.support.v4.media.session;

import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.os.ResultReceiver;
import android.support.v4.app.BundleCompat;
import android.support.v4.media.MediaMetadataCompat;
import android.support.v4.media.session.IMediaControllerCallback;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaControllerCompatApi21;
import android.support.v4.media.session.MediaSessionCompat;
import android.support.v4.media.session.ParcelableVolumeInfo;
import android.support.v4.media.session.PlaybackStateCompat;
import android.util.Log;
import android.view.KeyEvent;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

public final class MediaControllerCompat {
    private final MediaControllerImpl mImpl;
    private final HashSet<Callback> mRegisteredCallbacks = new HashSet();
    private final MediaSessionCompat.Token mToken;

    public MediaControllerCompat(Context context, MediaSessionCompat.Token token) throws RemoteException {
        if (token == null) {
            throw new IllegalArgumentException("sessionToken must not be null");
        }
        this.mToken = token;
        if (Build.VERSION.SDK_INT >= 24) {
            this.mImpl = new MediaControllerImplApi24(context, token);
            return;
        }
        if (Build.VERSION.SDK_INT >= 23) {
            this.mImpl = new MediaControllerImplApi23(context, token);
            return;
        }
        if (Build.VERSION.SDK_INT >= 21) {
            this.mImpl = new MediaControllerImplApi21(context, token);
            return;
        }
        this.mImpl = new MediaControllerImplBase(this.mToken);
    }

    public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
        if (keyEvent == null) {
            throw new IllegalArgumentException("KeyEvent may not be null");
        }
        return this.mImpl.dispatchMediaButtonEvent(keyEvent);
    }

    public static abstract class Callback
    implements IBinder.DeathRecipient {
        private final Object mCallbackObj;
        MessageHandler mHandler;
        boolean mHasExtraCallback;

        public Callback() {
            if (Build.VERSION.SDK_INT >= 21) {
                this.mCallbackObj = MediaControllerCompatApi21.createCallback(new StubApi21(this));
                return;
            }
            this.mCallbackObj = new StubCompat(this);
        }

        public void onAudioInfoChanged(PlaybackInfo playbackInfo) {
        }

        public void onCaptioningEnabledChanged(boolean bl) {
        }

        public void onExtrasChanged(Bundle bundle) {
        }

        public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) {
        }

        public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) {
        }

        public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) {
        }

        public void onQueueTitleChanged(CharSequence charSequence) {
        }

        public void onRepeatModeChanged(int n) {
        }

        public void onSessionDestroyed() {
        }

        public void onSessionEvent(String string2, Bundle bundle) {
        }

        public void onShuffleModeChanged(int n) {
        }

        @Deprecated
        public void onShuffleModeChanged(boolean bl) {
        }

        void postToHandler(int n, Object object, Bundle bundle) {
            if (this.mHandler != null) {
                object = this.mHandler.obtainMessage(n, object);
                object.setData(bundle);
                object.sendToTarget();
            }
        }

        private class MessageHandler
        extends Handler {
            boolean mRegistered;
            final /* synthetic */ Callback this$0;

            public void handleMessage(Message message) {
                if (!this.mRegistered) {
                    return;
                }
                switch (message.what) {
                    default: {
                        return;
                    }
                    case 1: {
                        this.this$0.onSessionEvent((String)message.obj, message.getData());
                        return;
                    }
                    case 2: {
                        this.this$0.onPlaybackStateChanged((PlaybackStateCompat)message.obj);
                        return;
                    }
                    case 3: {
                        this.this$0.onMetadataChanged((MediaMetadataCompat)message.obj);
                        return;
                    }
                    case 5: {
                        this.this$0.onQueueChanged((List)message.obj);
                        return;
                    }
                    case 6: {
                        this.this$0.onQueueTitleChanged((CharSequence)message.obj);
                        return;
                    }
                    case 11: {
                        this.this$0.onCaptioningEnabledChanged((Boolean)message.obj);
                        return;
                    }
                    case 9: {
                        this.this$0.onRepeatModeChanged((Integer)message.obj);
                        return;
                    }
                    case 10: {
                        this.this$0.onShuffleModeChanged((Boolean)message.obj);
                        return;
                    }
                    case 12: {
                        this.this$0.onShuffleModeChanged((Integer)message.obj);
                        return;
                    }
                    case 7: {
                        this.this$0.onExtrasChanged((Bundle)message.obj);
                        return;
                    }
                    case 4: {
                        this.this$0.onAudioInfoChanged((PlaybackInfo)message.obj);
                        return;
                    }
                    case 8: 
                }
                this.this$0.onSessionDestroyed();
            }
        }

        private static class StubApi21
        implements MediaControllerCompatApi21.Callback {
            private final WeakReference<Callback> mCallback;

            StubApi21(Callback callback) {
                this.mCallback = new WeakReference<Callback>(callback);
            }

            @Override
            public void onAudioInfoChanged(int n, int n2, int n3, int n4, int n5) {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.onAudioInfoChanged(new PlaybackInfo(n, n2, n3, n4, n5));
                }
            }

            @Override
            public void onExtrasChanged(Bundle bundle) {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.onExtrasChanged(bundle);
                }
            }

            @Override
            public void onMetadataChanged(Object object) {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.onMetadataChanged(MediaMetadataCompat.fromMediaMetadata(object));
                }
            }

            @Override
            public void onPlaybackStateChanged(Object object) {
                Callback callback = (Callback)this.mCallback.get();
                if (callback == null || callback.mHasExtraCallback) {
                    return;
                }
                callback.onPlaybackStateChanged(PlaybackStateCompat.fromPlaybackState(object));
            }

            @Override
            public void onQueueChanged(List<?> list) {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.onQueueChanged(MediaSessionCompat.QueueItem.fromQueueItemList(list));
                }
            }

            @Override
            public void onQueueTitleChanged(CharSequence charSequence) {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.onQueueTitleChanged(charSequence);
                }
            }

            @Override
            public void onSessionDestroyed() {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.onSessionDestroyed();
                }
            }

            @Override
            public void onSessionEvent(String string2, Bundle bundle) {
                Callback callback = (Callback)this.mCallback.get();
                if (callback == null || callback.mHasExtraCallback && Build.VERSION.SDK_INT < 23) {
                    return;
                }
                callback.onSessionEvent(string2, bundle);
            }
        }

        private static class StubCompat
        extends IMediaControllerCallback.Stub {
            private final WeakReference<Callback> mCallback;

            StubCompat(Callback callback) {
                this.mCallback = new WeakReference<Callback>(callback);
            }

            @Override
            public void onCaptioningEnabledChanged(boolean bl) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(11, bl, null);
                }
            }

            @Override
            public void onEvent(String string2, Bundle bundle) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(1, string2, bundle);
                }
            }

            @Override
            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(7, (Object)bundle, null);
                }
            }

            @Override
            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(3, mediaMetadataCompat, null);
                }
            }

            @Override
            public void onPlaybackStateChanged(PlaybackStateCompat playbackStateCompat) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(2, playbackStateCompat, null);
                }
            }

            @Override
            public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(5, list, null);
                }
            }

            @Override
            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(6, charSequence, null);
                }
            }

            @Override
            public void onRepeatModeChanged(int n) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(9, n, null);
                }
            }

            @Override
            public void onSessionDestroyed() throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(8, null, null);
                }
            }

            @Override
            public void onShuffleModeChanged(int n) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(12, n, null);
                }
            }

            @Override
            public void onShuffleModeChangedDeprecated(boolean bl) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    callback.postToHandler(10, bl, null);
                }
            }

            @Override
            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                Callback callback = (Callback)this.mCallback.get();
                if (callback != null) {
                    PlaybackInfo playbackInfo = null;
                    if (parcelableVolumeInfo != null) {
                        playbackInfo = new PlaybackInfo(parcelableVolumeInfo.volumeType, parcelableVolumeInfo.audioStream, parcelableVolumeInfo.controlType, parcelableVolumeInfo.maxVolume, parcelableVolumeInfo.currentVolume);
                    }
                    callback.postToHandler(4, playbackInfo, null);
                }
            }
        }

    }

    static interface MediaControllerImpl {
        public boolean dispatchMediaButtonEvent(KeyEvent var1);
    }

    static class MediaControllerImplApi21
    implements MediaControllerImpl {
        private HashMap<Callback, ExtraCallback> mCallbackMap;
        protected final Object mControllerObj;
        private IMediaSession mExtraBinder;
        private final List<Callback> mPendingCallbacks = new ArrayList<Callback>();

        public MediaControllerImplApi21(Context context, MediaSessionCompat.Token token) throws RemoteException {
            this.mCallbackMap = new HashMap();
            this.mControllerObj = MediaControllerCompatApi21.fromToken(context, token.getToken());
            if (this.mControllerObj == null) {
                throw new RemoteException();
            }
            this.mExtraBinder = token.getExtraBinder();
            if (this.mExtraBinder == null) {
                this.requestExtraBinder();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void processPendingCallbacks() {
            if (this.mExtraBinder == null) {
                return;
            }
            List<Callback> list = this.mPendingCallbacks;
            synchronized (list) {
                for (Callback callback : this.mPendingCallbacks) {
                    ExtraCallback extraCallback = new ExtraCallback(callback);
                    this.mCallbackMap.put(callback, extraCallback);
                    callback.mHasExtraCallback = true;
                    try {
                        this.mExtraBinder.registerCallbackListener(extraCallback);
                    }
                    catch (RemoteException remoteException) {
                        Log.e((String)"MediaControllerCompat", (String)"Dead object in registerCallback.", (Throwable)remoteException);
                        break;
                    }
                }
                this.mPendingCallbacks.clear();
                return;
            }
        }

        private void requestExtraBinder() {
            this.sendCommand("android.support.v4.media.session.command.GET_EXTRA_BINDER", null, new ExtraBinderRequestResultReceiver(this, new Handler()));
        }

        @Override
        public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
            return MediaControllerCompatApi21.dispatchMediaButtonEvent(this.mControllerObj, keyEvent);
        }

        public void sendCommand(String string2, Bundle bundle, ResultReceiver resultReceiver) {
            MediaControllerCompatApi21.sendCommand(this.mControllerObj, string2, bundle, resultReceiver);
        }

        private static class ExtraBinderRequestResultReceiver
        extends ResultReceiver {
            private WeakReference<MediaControllerImplApi21> mMediaControllerImpl;

            public ExtraBinderRequestResultReceiver(MediaControllerImplApi21 mediaControllerImplApi21, Handler handler) {
                super(handler);
                this.mMediaControllerImpl = new WeakReference<MediaControllerImplApi21>(mediaControllerImplApi21);
            }

            protected void onReceiveResult(int n, Bundle bundle) {
                MediaControllerImplApi21 mediaControllerImplApi21 = (MediaControllerImplApi21)this.mMediaControllerImpl.get();
                if (mediaControllerImplApi21 == null || bundle == null) {
                    return;
                }
                mediaControllerImplApi21.mExtraBinder = IMediaSession.Stub.asInterface(BundleCompat.getBinder(bundle, "android.support.v4.media.session.EXTRA_BINDER"));
                mediaControllerImplApi21.processPendingCallbacks();
            }
        }

        private static class ExtraCallback
        extends Callback.StubCompat {
            ExtraCallback(Callback callback) {
                super(callback);
            }

            @Override
            public void onExtrasChanged(Bundle bundle) throws RemoteException {
                throw new AssertionError();
            }

            @Override
            public void onMetadataChanged(MediaMetadataCompat mediaMetadataCompat) throws RemoteException {
                throw new AssertionError();
            }

            @Override
            public void onQueueChanged(List<MediaSessionCompat.QueueItem> list) throws RemoteException {
                throw new AssertionError();
            }

            @Override
            public void onQueueTitleChanged(CharSequence charSequence) throws RemoteException {
                throw new AssertionError();
            }

            @Override
            public void onSessionDestroyed() throws RemoteException {
                throw new AssertionError();
            }

            @Override
            public void onVolumeInfoChanged(ParcelableVolumeInfo parcelableVolumeInfo) throws RemoteException {
                throw new AssertionError();
            }
        }

    }

    static class MediaControllerImplApi23
    extends MediaControllerImplApi21 {
        public MediaControllerImplApi23(Context context, MediaSessionCompat.Token token) throws RemoteException {
            super(context, token);
        }
    }

    static class MediaControllerImplApi24
    extends MediaControllerImplApi23 {
        public MediaControllerImplApi24(Context context, MediaSessionCompat.Token token) throws RemoteException {
            super(context, token);
        }
    }

    static class MediaControllerImplBase
    implements MediaControllerImpl {
        private IMediaSession mBinder;

        public MediaControllerImplBase(MediaSessionCompat.Token token) {
            this.mBinder = IMediaSession.Stub.asInterface((IBinder)token.getToken());
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean dispatchMediaButtonEvent(KeyEvent keyEvent) {
            if (keyEvent == null) {
                throw new IllegalArgumentException("event may not be null.");
            }
            try {
                this.mBinder.sendMediaButton(keyEvent);
                do {
                    return false;
                    break;
                } while (true);
            }
            catch (RemoteException remoteException) {
                Log.e((String)"MediaControllerCompat", (String)"Dead object in dispatchMediaButtonEvent.", (Throwable)remoteException);
                return false;
            }
        }
    }

    public static final class PlaybackInfo {
        private final int mAudioStream;
        private final int mCurrentVolume;
        private final int mMaxVolume;
        private final int mPlaybackType;
        private final int mVolumeControl;

        PlaybackInfo(int n, int n2, int n3, int n4, int n5) {
            this.mPlaybackType = n;
            this.mAudioStream = n2;
            this.mVolumeControl = n3;
            this.mMaxVolume = n4;
            this.mCurrentVolume = n5;
        }
    }

}

