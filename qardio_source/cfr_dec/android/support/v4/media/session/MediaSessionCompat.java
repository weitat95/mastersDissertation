/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 *  android.os.ResultReceiver
 */
package android.support.v4.media.session;

import android.os.Build;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.ResultReceiver;
import android.support.v4.media.MediaDescriptionCompat;
import android.support.v4.media.session.IMediaSession;
import android.support.v4.media.session.MediaSessionCompatApi21;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MediaSessionCompat {

    public static final class QueueItem
    implements Parcelable {
        public static final Parcelable.Creator<QueueItem> CREATOR = new Parcelable.Creator<QueueItem>(){

            public QueueItem createFromParcel(Parcel parcel) {
                return new QueueItem(parcel);
            }

            public QueueItem[] newArray(int n) {
                return new QueueItem[n];
            }
        };
        private final MediaDescriptionCompat mDescription;
        private final long mId;
        private Object mItem;

        QueueItem(Parcel parcel) {
            this.mDescription = (MediaDescriptionCompat)MediaDescriptionCompat.CREATOR.createFromParcel(parcel);
            this.mId = parcel.readLong();
        }

        private QueueItem(Object object, MediaDescriptionCompat mediaDescriptionCompat, long l) {
            if (mediaDescriptionCompat == null) {
                throw new IllegalArgumentException("Description cannot be null.");
            }
            if (l == -1L) {
                throw new IllegalArgumentException("Id cannot be QueueItem.UNKNOWN_ID");
            }
            this.mDescription = mediaDescriptionCompat;
            this.mId = l;
            this.mItem = object;
        }

        public static QueueItem fromQueueItem(Object object) {
            if (object == null || Build.VERSION.SDK_INT < 21) {
                return null;
            }
            return new QueueItem(object, MediaDescriptionCompat.fromMediaDescription(MediaSessionCompatApi21.QueueItem.getDescription(object)), MediaSessionCompatApi21.QueueItem.getQueueId(object));
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public static List<QueueItem> fromQueueItemList(List<?> list) {
            if (list == null) return null;
            if (Build.VERSION.SDK_INT < 21) {
                return null;
            }
            ArrayList<QueueItem> arrayList = new ArrayList<QueueItem>();
            Iterator<QueueItem> iterator = list.iterator();
            do {
                list = arrayList;
                if (!iterator.hasNext()) return list;
                arrayList.add(QueueItem.fromQueueItem(iterator.next()));
            } while (true);
        }

        public int describeContents() {
            return 0;
        }

        public String toString() {
            return "MediaSession.QueueItem {Description=" + this.mDescription + ", Id=" + this.mId + " }";
        }

        public void writeToParcel(Parcel parcel, int n) {
            this.mDescription.writeToParcel(parcel, n);
            parcel.writeLong(this.mId);
        }

    }

    static final class ResultReceiverWrapper
    implements Parcelable {
        public static final Parcelable.Creator<ResultReceiverWrapper> CREATOR = new Parcelable.Creator<ResultReceiverWrapper>(){

            public ResultReceiverWrapper createFromParcel(Parcel parcel) {
                return new ResultReceiverWrapper(parcel);
            }

            public ResultReceiverWrapper[] newArray(int n) {
                return new ResultReceiverWrapper[n];
            }
        };
        private ResultReceiver mResultReceiver;

        ResultReceiverWrapper(Parcel parcel) {
            this.mResultReceiver = (ResultReceiver)ResultReceiver.CREATOR.createFromParcel(parcel);
        }

        public int describeContents() {
            return 0;
        }

        public void writeToParcel(Parcel parcel, int n) {
            this.mResultReceiver.writeToParcel(parcel, n);
        }

    }

    public static final class Token
    implements Parcelable {
        public static final Parcelable.Creator<Token> CREATOR = new Parcelable.Creator<Token>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public Token createFromParcel(Parcel parcel) {
                if (Build.VERSION.SDK_INT >= 21) {
                    parcel = parcel.readParcelable(null);
                    do {
                        return new Token((Object)parcel);
                        break;
                    } while (true);
                }
                parcel = parcel.readStrongBinder();
                return new Token((Object)parcel);
            }

            public Token[] newArray(int n) {
                return new Token[n];
            }
        };
        private final IMediaSession mExtraBinder;
        private final Object mInner;

        Token(Object object) {
            this(object, null);
        }

        Token(Object object, IMediaSession iMediaSession) {
            this.mInner = object;
            this.mExtraBinder = iMediaSession;
        }

        public static Token fromToken(Object object) {
            return Token.fromToken(object, null);
        }

        public static Token fromToken(Object object, IMediaSession iMediaSession) {
            if (object != null && Build.VERSION.SDK_INT >= 21) {
                return new Token(MediaSessionCompatApi21.verifyToken(object), iMediaSession);
            }
            return null;
        }

        public int describeContents() {
            return 0;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean equals(Object object) {
            block6: {
                block7: {
                    block5: {
                        if (this == object) break block5;
                        if (!(object instanceof Token)) {
                            return false;
                        }
                        object = (Token)object;
                        if (this.mInner != null) break block6;
                        if (((Token)object).mInner != null) break block7;
                    }
                    return true;
                }
                return false;
            }
            if (((Token)object).mInner == null) {
                return false;
            }
            return this.mInner.equals(((Token)object).mInner);
        }

        public IMediaSession getExtraBinder() {
            return this.mExtraBinder;
        }

        public Object getToken() {
            return this.mInner;
        }

        public int hashCode() {
            if (this.mInner == null) {
                return 0;
            }
            return this.mInner.hashCode();
        }

        public void writeToParcel(Parcel parcel, int n) {
            if (Build.VERSION.SDK_INT >= 21) {
                parcel.writeParcelable((Parcelable)this.mInner, n);
                return;
            }
            parcel.writeStrongBinder((IBinder)this.mInner);
        }

    }

}

