/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Notification
 *  android.app.Notification$Action
 *  android.app.Notification$Builder
 *  android.app.Notification$DecoratedCustomViewStyle
 *  android.app.Notification$Style
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.os.SystemClock
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  android.text.style.TextAppearanceSpan
 *  android.widget.RemoteViews
 */
package android.support.v4.app;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.os.SystemClock;
import android.support.compat.R;
import android.support.v4.app.NotificationBuilderWithActions;
import android.support.v4.app.NotificationBuilderWithBuilderAccessor;
import android.support.v4.app.NotificationCompatApi20;
import android.support.v4.app.NotificationCompatApi21;
import android.support.v4.app.NotificationCompatApi24;
import android.support.v4.app.NotificationCompatApi26;
import android.support.v4.app.NotificationCompatBase;
import android.support.v4.app.NotificationCompatJellybean;
import android.support.v4.app.NotificationCompatKitKat;
import android.support.v4.app.RemoteInput;
import android.support.v4.app.RemoteInputCompatBase;
import android.support.v4.text.BidiFormatter;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.TextAppearanceSpan;
import android.widget.RemoteViews;
import java.lang.annotation.Annotation;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class NotificationCompat {
    public static final int BADGE_ICON_LARGE = 2;
    public static final int BADGE_ICON_NONE = 0;
    public static final int BADGE_ICON_SMALL = 1;
    public static final String CATEGORY_ALARM = "alarm";
    public static final String CATEGORY_CALL = "call";
    public static final String CATEGORY_EMAIL = "email";
    public static final String CATEGORY_ERROR = "err";
    public static final String CATEGORY_EVENT = "event";
    public static final String CATEGORY_MESSAGE = "msg";
    public static final String CATEGORY_PROGRESS = "progress";
    public static final String CATEGORY_PROMO = "promo";
    public static final String CATEGORY_RECOMMENDATION = "recommendation";
    public static final String CATEGORY_REMINDER = "reminder";
    public static final String CATEGORY_SERVICE = "service";
    public static final String CATEGORY_SOCIAL = "social";
    public static final String CATEGORY_STATUS = "status";
    public static final String CATEGORY_SYSTEM = "sys";
    public static final String CATEGORY_TRANSPORT = "transport";
    public static final int COLOR_DEFAULT = 0;
    public static final int DEFAULT_ALL = -1;
    public static final int DEFAULT_LIGHTS = 4;
    public static final int DEFAULT_SOUND = 1;
    public static final int DEFAULT_VIBRATE = 2;
    public static final String EXTRA_AUDIO_CONTENTS_URI = "android.audioContents";
    public static final String EXTRA_BACKGROUND_IMAGE_URI = "android.backgroundImageUri";
    public static final String EXTRA_BIG_TEXT = "android.bigText";
    public static final String EXTRA_COMPACT_ACTIONS = "android.compactActions";
    public static final String EXTRA_CONVERSATION_TITLE = "android.conversationTitle";
    public static final String EXTRA_INFO_TEXT = "android.infoText";
    public static final String EXTRA_LARGE_ICON = "android.largeIcon";
    public static final String EXTRA_LARGE_ICON_BIG = "android.largeIcon.big";
    public static final String EXTRA_MEDIA_SESSION = "android.mediaSession";
    public static final String EXTRA_MESSAGES = "android.messages";
    public static final String EXTRA_PEOPLE = "android.people";
    public static final String EXTRA_PICTURE = "android.picture";
    public static final String EXTRA_PROGRESS = "android.progress";
    public static final String EXTRA_PROGRESS_INDETERMINATE = "android.progressIndeterminate";
    public static final String EXTRA_PROGRESS_MAX = "android.progressMax";
    public static final String EXTRA_REMOTE_INPUT_HISTORY = "android.remoteInputHistory";
    public static final String EXTRA_SELF_DISPLAY_NAME = "android.selfDisplayName";
    public static final String EXTRA_SHOW_CHRONOMETER = "android.showChronometer";
    public static final String EXTRA_SHOW_WHEN = "android.showWhen";
    public static final String EXTRA_SMALL_ICON = "android.icon";
    public static final String EXTRA_SUB_TEXT = "android.subText";
    public static final String EXTRA_SUMMARY_TEXT = "android.summaryText";
    public static final String EXTRA_TEMPLATE = "android.template";
    public static final String EXTRA_TEXT = "android.text";
    public static final String EXTRA_TEXT_LINES = "android.textLines";
    public static final String EXTRA_TITLE = "android.title";
    public static final String EXTRA_TITLE_BIG = "android.title.big";
    public static final int FLAG_AUTO_CANCEL = 16;
    public static final int FLAG_FOREGROUND_SERVICE = 64;
    public static final int FLAG_GROUP_SUMMARY = 512;
    @Deprecated
    public static final int FLAG_HIGH_PRIORITY = 128;
    public static final int FLAG_INSISTENT = 4;
    public static final int FLAG_LOCAL_ONLY = 256;
    public static final int FLAG_NO_CLEAR = 32;
    public static final int FLAG_ONGOING_EVENT = 2;
    public static final int FLAG_ONLY_ALERT_ONCE = 8;
    public static final int FLAG_SHOW_LIGHTS = 1;
    public static final int GROUP_ALERT_ALL = 0;
    public static final int GROUP_ALERT_CHILDREN = 2;
    public static final int GROUP_ALERT_SUMMARY = 1;
    static final NotificationCompatImpl IMPL = Build.VERSION.SDK_INT >= 26 ? new NotificationCompatApi26Impl() : (Build.VERSION.SDK_INT >= 24 ? new NotificationCompatApi24Impl() : (Build.VERSION.SDK_INT >= 21 ? new NotificationCompatApi21Impl() : (Build.VERSION.SDK_INT >= 20 ? new NotificationCompatApi20Impl() : (Build.VERSION.SDK_INT >= 19 ? new NotificationCompatApi19Impl() : (Build.VERSION.SDK_INT >= 16 ? new NotificationCompatApi16Impl() : new NotificationCompatBaseImpl())))));
    public static final int PRIORITY_DEFAULT = 0;
    public static final int PRIORITY_HIGH = 1;
    public static final int PRIORITY_LOW = -1;
    public static final int PRIORITY_MAX = 2;
    public static final int PRIORITY_MIN = -2;
    public static final int STREAM_DEFAULT = -1;
    public static final int VISIBILITY_PRIVATE = 0;
    public static final int VISIBILITY_PUBLIC = 1;
    public static final int VISIBILITY_SECRET = -1;

    static void addActionsToBuilder(NotificationBuilderWithActions notificationBuilderWithActions, ArrayList<Action> object) {
        object = ((ArrayList)object).iterator();
        while (object.hasNext()) {
            notificationBuilderWithActions.addAction((Action)object.next());
        }
    }

    public static Action getAction(Notification notification, int n) {
        return IMPL.getAction(notification, n);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static int getActionCount(Notification notification) {
        int n = 0;
        if (Build.VERSION.SDK_INT >= 19) {
            if (notification.actions == null) return n;
            return notification.actions.length;
        }
        if (Build.VERSION.SDK_INT < 16) return n;
        return NotificationCompatJellybean.getActionCount(notification);
    }

    public static int getBadgeIconType(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getBadgeIconType();
        }
        return 0;
    }

    public static String getCategory(Notification notification) {
        if (Build.VERSION.SDK_INT >= 21) {
            return notification.category;
        }
        return null;
    }

    public static String getChannelId(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getChannelId();
        }
        return null;
    }

    public static Bundle getExtras(Notification notification) {
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras;
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification);
        }
        return null;
    }

    public static String getGroup(Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return notification.getGroup();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getString("android.support.groupKey");
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getString("android.support.groupKey");
        }
        return null;
    }

    public static int getGroupAlertBehavior(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getGroupAlertBehavior();
        }
        return 0;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean getLocalOnly(Notification notification) {
        boolean bl = false;
        if (Build.VERSION.SDK_INT >= 20) {
            if ((notification.flags & 0x100) == 0) return bl;
            return true;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean("android.support.localOnly");
        }
        if (Build.VERSION.SDK_INT < 16) return bl;
        return NotificationCompatJellybean.getExtras(notification).getBoolean("android.support.localOnly");
    }

    static Notification[] getNotificationArrayFromBundle(Bundle bundle, String string2) {
        Parcelable[] arrparcelable = bundle.getParcelableArray(string2);
        if (arrparcelable instanceof Notification[] || arrparcelable == null) {
            return (Notification[])arrparcelable;
        }
        Notification[] arrnotification = new Notification[arrparcelable.length];
        for (int i = 0; i < arrparcelable.length; ++i) {
            arrnotification[i] = (Notification)arrparcelable[i];
        }
        bundle.putParcelableArray(string2, (Parcelable[])arrnotification);
        return arrnotification;
    }

    public static String getShortcutId(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getShortcutId();
        }
        return null;
    }

    public static String getSortKey(Notification notification) {
        if (Build.VERSION.SDK_INT >= 20) {
            return notification.getSortKey();
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getString("android.support.sortKey");
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return NotificationCompatJellybean.getExtras(notification).getString("android.support.sortKey");
        }
        return null;
    }

    public static long getTimeoutAfter(Notification notification) {
        if (Build.VERSION.SDK_INT >= 26) {
            return notification.getTimeoutAfter();
        }
        return 0L;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean isGroupSummary(Notification notification) {
        boolean bl = false;
        if (Build.VERSION.SDK_INT >= 20) {
            if ((notification.flags & 0x200) == 0) return bl;
            return true;
        }
        if (Build.VERSION.SDK_INT >= 19) {
            return notification.extras.getBoolean("android.support.isGroupSummary");
        }
        if (Build.VERSION.SDK_INT < 16) return bl;
        return NotificationCompatJellybean.getExtras(notification).getBoolean("android.support.isGroupSummary");
    }

    public static class Action
    extends NotificationCompatBase.Action {
        public static final NotificationCompatBase.Action.Factory FACTORY = new NotificationCompatBase.Action.Factory(){

            @Override
            public NotificationCompatBase.Action build(int n, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInputCompatBase.RemoteInput[] arrremoteInput, RemoteInputCompatBase.RemoteInput[] arrremoteInput2, boolean bl) {
                return new Action(n, charSequence, pendingIntent, bundle, (RemoteInput[])arrremoteInput, (RemoteInput[])arrremoteInput2, bl);
            }

            public Action[] newArray(int n) {
                return new Action[n];
            }
        };
        public PendingIntent actionIntent;
        public int icon;
        private boolean mAllowGeneratedReplies;
        private final RemoteInput[] mDataOnlyRemoteInputs;
        final Bundle mExtras;
        private final RemoteInput[] mRemoteInputs;
        public CharSequence title;

        public Action(int n, CharSequence charSequence, PendingIntent pendingIntent) {
            this(n, charSequence, pendingIntent, new Bundle(), null, null, true);
        }

        /*
         * Enabled aggressive block sorting
         */
        Action(int n, CharSequence charSequence, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] arrremoteInput, RemoteInput[] arrremoteInput2, boolean bl) {
            this.icon = n;
            this.title = android.support.v4.app.NotificationCompat$Builder.limitCharSequenceLength(charSequence);
            this.actionIntent = pendingIntent;
            if (bundle == null) {
                bundle = new Bundle();
            }
            this.mExtras = bundle;
            this.mRemoteInputs = arrremoteInput;
            this.mDataOnlyRemoteInputs = arrremoteInput2;
            this.mAllowGeneratedReplies = bl;
        }

        @Override
        public PendingIntent getActionIntent() {
            return this.actionIntent;
        }

        @Override
        public boolean getAllowGeneratedReplies() {
            return this.mAllowGeneratedReplies;
        }

        public RemoteInput[] getDataOnlyRemoteInputs() {
            return this.mDataOnlyRemoteInputs;
        }

        @Override
        public Bundle getExtras() {
            return this.mExtras;
        }

        @Override
        public int getIcon() {
            return this.icon;
        }

        public RemoteInput[] getRemoteInputs() {
            return this.mRemoteInputs;
        }

        @Override
        public CharSequence getTitle() {
            return this.title;
        }

        public static final class Builder {
            private boolean mAllowGeneratedReplies = true;
            private final Bundle mExtras;
            private final int mIcon;
            private final PendingIntent mIntent;
            private ArrayList<RemoteInput> mRemoteInputs;
            private final CharSequence mTitle;

            public Builder(int n, CharSequence charSequence, PendingIntent pendingIntent) {
                this(n, charSequence, pendingIntent, new Bundle(), null, true);
            }

            /*
             * Enabled aggressive block sorting
             */
            private Builder(int n, CharSequence arrayList, PendingIntent pendingIntent, Bundle bundle, RemoteInput[] arrremoteInput, boolean bl) {
                this.mIcon = n;
                this.mTitle = android.support.v4.app.NotificationCompat$Builder.limitCharSequenceLength(arrayList);
                this.mIntent = pendingIntent;
                this.mExtras = bundle;
                arrayList = arrremoteInput == null ? null : new ArrayList<RemoteInput>(Arrays.asList(arrremoteInput));
                this.mRemoteInputs = arrayList;
                this.mAllowGeneratedReplies = bl;
            }

            public Builder(Action action) {
                this(action.icon, action.title, action.actionIntent, new Bundle(action.mExtras), action.getRemoteInputs(), action.getAllowGeneratedReplies());
            }

            public Builder addExtras(Bundle bundle) {
                if (bundle != null) {
                    this.mExtras.putAll(bundle);
                }
                return this;
            }

            public Builder addRemoteInput(RemoteInput remoteInput) {
                if (this.mRemoteInputs == null) {
                    this.mRemoteInputs = new ArrayList();
                }
                this.mRemoteInputs.add(remoteInput);
                return this;
            }

            /*
             * Enabled aggressive block sorting
             */
            public Action build() {
                Object object = new ArrayList();
                RemoteInput[] arrremoteInput = new ArrayList();
                if (this.mRemoteInputs != null) {
                    for (RemoteInput remoteInput : this.mRemoteInputs) {
                        if (remoteInput.isDataOnly()) {
                            object.add(remoteInput);
                            continue;
                        }
                        arrremoteInput.add(remoteInput);
                    }
                }
                object = object.isEmpty() ? null : object.toArray(new RemoteInput[object.size()]);
                if (arrremoteInput.isEmpty()) {
                    arrremoteInput = null;
                    return new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, arrremoteInput, (RemoteInput[])object, this.mAllowGeneratedReplies);
                }
                arrremoteInput = arrremoteInput.toArray(new RemoteInput[arrremoteInput.size()]);
                return new Action(this.mIcon, this.mTitle, this.mIntent, this.mExtras, arrremoteInput, (RemoteInput[])object, this.mAllowGeneratedReplies);
            }

            public Builder extend(Extender extender) {
                extender.extend(this);
                return this;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public Builder setAllowGeneratedReplies(boolean bl) {
                this.mAllowGeneratedReplies = bl;
                return this;
            }
        }

        public static interface Extender {
            public Builder extend(Builder var1);
        }

        public static final class WearableExtender
        implements Extender {
            private static final int DEFAULT_FLAGS = 1;
            private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
            private static final int FLAG_AVAILABLE_OFFLINE = 1;
            private static final int FLAG_HINT_DISPLAY_INLINE = 4;
            private static final int FLAG_HINT_LAUNCHES_ACTIVITY = 2;
            private static final String KEY_CANCEL_LABEL = "cancelLabel";
            private static final String KEY_CONFIRM_LABEL = "confirmLabel";
            private static final String KEY_FLAGS = "flags";
            private static final String KEY_IN_PROGRESS_LABEL = "inProgressLabel";
            private CharSequence mCancelLabel;
            private CharSequence mConfirmLabel;
            private int mFlags = 1;
            private CharSequence mInProgressLabel;

            public WearableExtender() {
            }

            public WearableExtender(Action action) {
                action = action.getExtras().getBundle(EXTRA_WEARABLE_EXTENSIONS);
                if (action != null) {
                    this.mFlags = action.getInt(KEY_FLAGS, 1);
                    this.mInProgressLabel = action.getCharSequence(KEY_IN_PROGRESS_LABEL);
                    this.mConfirmLabel = action.getCharSequence(KEY_CONFIRM_LABEL);
                    this.mCancelLabel = action.getCharSequence(KEY_CANCEL_LABEL);
                }
            }

            private void setFlag(int n, boolean bl) {
                if (bl) {
                    this.mFlags |= n;
                    return;
                }
                this.mFlags &= ~n;
            }

            public WearableExtender clone() {
                WearableExtender wearableExtender = new WearableExtender();
                wearableExtender.mFlags = this.mFlags;
                wearableExtender.mInProgressLabel = this.mInProgressLabel;
                wearableExtender.mConfirmLabel = this.mConfirmLabel;
                wearableExtender.mCancelLabel = this.mCancelLabel;
                return wearableExtender;
            }

            @Override
            public Builder extend(Builder builder) {
                Bundle bundle = new Bundle();
                if (this.mFlags != 1) {
                    bundle.putInt(KEY_FLAGS, this.mFlags);
                }
                if (this.mInProgressLabel != null) {
                    bundle.putCharSequence(KEY_IN_PROGRESS_LABEL, this.mInProgressLabel);
                }
                if (this.mConfirmLabel != null) {
                    bundle.putCharSequence(KEY_CONFIRM_LABEL, this.mConfirmLabel);
                }
                if (this.mCancelLabel != null) {
                    bundle.putCharSequence(KEY_CANCEL_LABEL, this.mCancelLabel);
                }
                builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
                return builder;
            }

            public CharSequence getCancelLabel() {
                return this.mCancelLabel;
            }

            public CharSequence getConfirmLabel() {
                return this.mConfirmLabel;
            }

            public boolean getHintDisplayActionInline() {
                return (this.mFlags & 4) != 0;
            }

            public boolean getHintLaunchesActivity() {
                return (this.mFlags & 2) != 0;
            }

            public CharSequence getInProgressLabel() {
                return this.mInProgressLabel;
            }

            public boolean isAvailableOffline() {
                return (this.mFlags & 1) != 0;
            }

            public WearableExtender setAvailableOffline(boolean bl) {
                this.setFlag(1, bl);
                return this;
            }

            public WearableExtender setCancelLabel(CharSequence charSequence) {
                this.mCancelLabel = charSequence;
                return this;
            }

            public WearableExtender setConfirmLabel(CharSequence charSequence) {
                this.mConfirmLabel = charSequence;
                return this;
            }

            public WearableExtender setHintDisplayActionInline(boolean bl) {
                this.setFlag(4, bl);
                return this;
            }

            public WearableExtender setHintLaunchesActivity(boolean bl) {
                this.setFlag(2, bl);
                return this;
            }

            public WearableExtender setInProgressLabel(CharSequence charSequence) {
                this.mInProgressLabel = charSequence;
                return this;
            }
        }

    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface BadgeIconType {
    }

    public static class BigPictureStyle
    extends Style {
        private Bitmap mBigLargeIcon;
        private boolean mBigLargeIconSet;
        private Bitmap mPicture;

        public BigPictureStyle() {
        }

        public BigPictureStyle(Builder builder) {
            this.setBuilder(builder);
        }

        @Override
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 16) {
                NotificationCompatJellybean.addBigPictureStyle(notificationBuilderWithBuilderAccessor, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mPicture, this.mBigLargeIcon, this.mBigLargeIconSet);
            }
        }

        public BigPictureStyle bigLargeIcon(Bitmap bitmap) {
            this.mBigLargeIcon = bitmap;
            this.mBigLargeIconSet = true;
            return this;
        }

        public BigPictureStyle bigPicture(Bitmap bitmap) {
            this.mPicture = bitmap;
            return this;
        }

        public BigPictureStyle setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public BigPictureStyle setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence);
            this.mSummaryTextSet = true;
            return this;
        }
    }

    public static class BigTextStyle
    extends Style {
        private CharSequence mBigText;

        public BigTextStyle() {
        }

        public BigTextStyle(Builder builder) {
            this.setBuilder(builder);
        }

        @Override
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 16) {
                NotificationCompatJellybean.addBigTextStyle(notificationBuilderWithBuilderAccessor, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mBigText);
            }
        }

        public BigTextStyle bigText(CharSequence charSequence) {
            this.mBigText = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public BigTextStyle setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public BigTextStyle setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence);
            this.mSummaryTextSet = true;
            return this;
        }
    }

    public static class Builder {
        private static final int MAX_CHARSEQUENCE_LENGTH = 5120;
        public ArrayList<Action> mActions = new ArrayList();
        int mBadgeIcon = 0;
        RemoteViews mBigContentView;
        String mCategory;
        String mChannelId;
        int mColor = 0;
        boolean mColorized;
        boolean mColorizedSet;
        public CharSequence mContentInfo;
        PendingIntent mContentIntent;
        public CharSequence mContentText;
        public CharSequence mContentTitle;
        RemoteViews mContentView;
        public Context mContext;
        Bundle mExtras;
        PendingIntent mFullScreenIntent;
        private int mGroupAlertBehavior = 0;
        String mGroupKey;
        boolean mGroupSummary;
        RemoteViews mHeadsUpContentView;
        public Bitmap mLargeIcon;
        boolean mLocalOnly = false;
        public Notification mNotification = new Notification();
        public int mNumber;
        public ArrayList<String> mPeople;
        int mPriority;
        int mProgress;
        boolean mProgressIndeterminate;
        int mProgressMax;
        Notification mPublicVersion;
        public CharSequence[] mRemoteInputHistory;
        String mShortcutId;
        boolean mShowWhen = true;
        String mSortKey;
        public Style mStyle;
        public CharSequence mSubText;
        RemoteViews mTickerView;
        long mTimeout;
        public boolean mUseChronometer;
        int mVisibility = 0;

        @Deprecated
        public Builder(Context context) {
            this(context, null);
        }

        public Builder(Context context, String string2) {
            this.mContext = context;
            this.mChannelId = string2;
            this.mNotification.when = System.currentTimeMillis();
            this.mNotification.audioStreamType = -1;
            this.mPriority = 0;
            this.mPeople = new ArrayList();
        }

        /*
         * Enabled aggressive block sorting
         */
        protected static CharSequence limitCharSequenceLength(CharSequence charSequence) {
            if (charSequence == null || charSequence.length() <= 5120) {
                return charSequence;
            }
            return charSequence.subSequence(0, 5120);
        }

        private void setFlag(int n, boolean bl) {
            if (bl) {
                Notification notification = this.mNotification;
                notification.flags |= n;
                return;
            }
            Notification notification = this.mNotification;
            notification.flags &= ~n;
        }

        public Builder addAction(int n, CharSequence charSequence, PendingIntent pendingIntent) {
            this.mActions.add(new Action(n, charSequence, pendingIntent));
            return this;
        }

        public Builder addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public Builder addExtras(Bundle bundle) {
            block3: {
                block2: {
                    if (bundle == null) break block2;
                    if (this.mExtras != null) break block3;
                    this.mExtras = new Bundle(bundle);
                }
                return this;
            }
            this.mExtras.putAll(bundle);
            return this;
        }

        public Builder addPerson(String string2) {
            this.mPeople.add(string2);
            return this;
        }

        public Notification build() {
            return IMPL.build(this, this.getExtender());
        }

        public Builder extend(Extender extender) {
            extender.extend(this);
            return this;
        }

        public RemoteViews getBigContentView() {
            return this.mBigContentView;
        }

        public int getColor() {
            return this.mColor;
        }

        public RemoteViews getContentView() {
            return this.mContentView;
        }

        protected BuilderExtender getExtender() {
            return new BuilderExtender();
        }

        public Bundle getExtras() {
            if (this.mExtras == null) {
                this.mExtras = new Bundle();
            }
            return this.mExtras;
        }

        public RemoteViews getHeadsUpContentView() {
            return this.mHeadsUpContentView;
        }

        @Deprecated
        public Notification getNotification() {
            return this.build();
        }

        public int getPriority() {
            return this.mPriority;
        }

        public long getWhenIfShowing() {
            if (this.mShowWhen) {
                return this.mNotification.when;
            }
            return 0L;
        }

        public Builder setAutoCancel(boolean bl) {
            this.setFlag(16, bl);
            return this;
        }

        public Builder setBadgeIconType(int n) {
            this.mBadgeIcon = n;
            return this;
        }

        public Builder setCategory(String string2) {
            this.mCategory = string2;
            return this;
        }

        public Builder setChannelId(String string2) {
            this.mChannelId = string2;
            return this;
        }

        public Builder setColor(int n) {
            this.mColor = n;
            return this;
        }

        public Builder setColorized(boolean bl) {
            this.mColorized = bl;
            this.mColorizedSet = true;
            return this;
        }

        public Builder setContent(RemoteViews remoteViews) {
            this.mNotification.contentView = remoteViews;
            return this;
        }

        public Builder setContentInfo(CharSequence charSequence) {
            this.mContentInfo = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setContentIntent(PendingIntent pendingIntent) {
            this.mContentIntent = pendingIntent;
            return this;
        }

        public Builder setContentText(CharSequence charSequence) {
            this.mContentText = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setContentTitle(CharSequence charSequence) {
            this.mContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setCustomBigContentView(RemoteViews remoteViews) {
            this.mBigContentView = remoteViews;
            return this;
        }

        public Builder setCustomContentView(RemoteViews remoteViews) {
            this.mContentView = remoteViews;
            return this;
        }

        public Builder setCustomHeadsUpContentView(RemoteViews remoteViews) {
            this.mHeadsUpContentView = remoteViews;
            return this;
        }

        public Builder setDefaults(int n) {
            this.mNotification.defaults = n;
            if ((n & 4) != 0) {
                Notification notification = this.mNotification;
                notification.flags |= 1;
            }
            return this;
        }

        public Builder setDeleteIntent(PendingIntent pendingIntent) {
            this.mNotification.deleteIntent = pendingIntent;
            return this;
        }

        public Builder setExtras(Bundle bundle) {
            this.mExtras = bundle;
            return this;
        }

        public Builder setFullScreenIntent(PendingIntent pendingIntent, boolean bl) {
            this.mFullScreenIntent = pendingIntent;
            this.setFlag(128, bl);
            return this;
        }

        public Builder setGroup(String string2) {
            this.mGroupKey = string2;
            return this;
        }

        public Builder setGroupAlertBehavior(int n) {
            this.mGroupAlertBehavior = n;
            return this;
        }

        public Builder setGroupSummary(boolean bl) {
            this.mGroupSummary = bl;
            return this;
        }

        public Builder setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Builder setLights(int n, int n2, int n3) {
            int n4 = 1;
            this.mNotification.ledARGB = n;
            this.mNotification.ledOnMS = n2;
            this.mNotification.ledOffMS = n3;
            n = this.mNotification.ledOnMS != 0 && this.mNotification.ledOffMS != 0 ? 1 : 0;
            Notification notification = this.mNotification;
            n2 = this.mNotification.flags;
            n = n != 0 ? n4 : 0;
            notification.flags = n | n2 & 0xFFFFFFFE;
            return this;
        }

        public Builder setLocalOnly(boolean bl) {
            this.mLocalOnly = bl;
            return this;
        }

        public Builder setNumber(int n) {
            this.mNumber = n;
            return this;
        }

        public Builder setOngoing(boolean bl) {
            this.setFlag(2, bl);
            return this;
        }

        public Builder setOnlyAlertOnce(boolean bl) {
            this.setFlag(8, bl);
            return this;
        }

        public Builder setPriority(int n) {
            this.mPriority = n;
            return this;
        }

        public Builder setProgress(int n, int n2, boolean bl) {
            this.mProgressMax = n;
            this.mProgress = n2;
            this.mProgressIndeterminate = bl;
            return this;
        }

        public Builder setPublicVersion(Notification notification) {
            this.mPublicVersion = notification;
            return this;
        }

        public Builder setRemoteInputHistory(CharSequence[] arrcharSequence) {
            this.mRemoteInputHistory = arrcharSequence;
            return this;
        }

        public Builder setShortcutId(String string2) {
            this.mShortcutId = string2;
            return this;
        }

        public Builder setShowWhen(boolean bl) {
            this.mShowWhen = bl;
            return this;
        }

        public Builder setSmallIcon(int n) {
            this.mNotification.icon = n;
            return this;
        }

        public Builder setSmallIcon(int n, int n2) {
            this.mNotification.icon = n;
            this.mNotification.iconLevel = n2;
            return this;
        }

        public Builder setSortKey(String string2) {
            this.mSortKey = string2;
            return this;
        }

        public Builder setSound(Uri uri) {
            this.mNotification.sound = uri;
            this.mNotification.audioStreamType = -1;
            return this;
        }

        public Builder setSound(Uri uri, int n) {
            this.mNotification.sound = uri;
            this.mNotification.audioStreamType = n;
            return this;
        }

        public Builder setStyle(Style style2) {
            if (this.mStyle != style2) {
                this.mStyle = style2;
                if (this.mStyle != null) {
                    this.mStyle.setBuilder(this);
                }
            }
            return this;
        }

        public Builder setSubText(CharSequence charSequence) {
            this.mSubText = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setTicker(CharSequence charSequence) {
            this.mNotification.tickerText = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public Builder setTicker(CharSequence charSequence, RemoteViews remoteViews) {
            this.mNotification.tickerText = Builder.limitCharSequenceLength(charSequence);
            this.mTickerView = remoteViews;
            return this;
        }

        public Builder setTimeoutAfter(long l) {
            this.mTimeout = l;
            return this;
        }

        public Builder setUsesChronometer(boolean bl) {
            this.mUseChronometer = bl;
            return this;
        }

        public Builder setVibrate(long[] arrl) {
            this.mNotification.vibrate = arrl;
            return this;
        }

        public Builder setVisibility(int n) {
            this.mVisibility = n;
            return this;
        }

        public Builder setWhen(long l) {
            this.mNotification.when = l;
            return this;
        }
    }

    protected static class BuilderExtender {
        protected BuilderExtender() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Notification build(Builder builder, NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            RemoteViews remoteViews = builder.mStyle != null ? builder.mStyle.makeContentView(notificationBuilderWithBuilderAccessor) : null;
            Notification notification = notificationBuilderWithBuilderAccessor.build();
            if (remoteViews != null) {
                notification.contentView = remoteViews;
            } else if (builder.mContentView != null) {
                notification.contentView = builder.mContentView;
            }
            if (Build.VERSION.SDK_INT >= 16 && builder.mStyle != null && (remoteViews = builder.mStyle.makeBigContentView(notificationBuilderWithBuilderAccessor)) != null) {
                notification.bigContentView = remoteViews;
            }
            if (Build.VERSION.SDK_INT >= 21 && builder.mStyle != null && (builder = builder.mStyle.makeHeadsUpContentView(notificationBuilderWithBuilderAccessor)) != null) {
                notification.headsUpContentView = builder;
            }
            return notification;
        }
    }

    public static final class CarExtender
    implements Extender {
        private static final String EXTRA_CAR_EXTENDER = "android.car.EXTENSIONS";
        private static final String EXTRA_COLOR = "app_color";
        private static final String EXTRA_CONVERSATION = "car_conversation";
        private static final String EXTRA_LARGE_ICON = "large_icon";
        private static final String TAG = "CarExtender";
        private int mColor = 0;
        private Bitmap mLargeIcon;
        private UnreadConversation mUnreadConversation;

        public CarExtender() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CarExtender(Notification notification) {
            if (Build.VERSION.SDK_INT < 21) return;
            if (NotificationCompat.getExtras(notification) == null) {
                return;
            }
            notification = NotificationCompat.getExtras(notification).getBundle(EXTRA_CAR_EXTENDER);
            if (notification == null) {
                return;
            }
            this.mLargeIcon = (Bitmap)notification.getParcelable(EXTRA_LARGE_ICON);
            this.mColor = notification.getInt(EXTRA_COLOR, 0);
            notification = notification.getBundle(EXTRA_CONVERSATION);
            this.mUnreadConversation = (UnreadConversation)IMPL.getUnreadConversationFromBundle((Bundle)notification, UnreadConversation.FACTORY, RemoteInput.FACTORY);
        }

        @Override
        public Builder extend(Builder builder) {
            if (Build.VERSION.SDK_INT < 21) {
                return builder;
            }
            Bundle bundle = new Bundle();
            if (this.mLargeIcon != null) {
                bundle.putParcelable(EXTRA_LARGE_ICON, (Parcelable)this.mLargeIcon);
            }
            if (this.mColor != 0) {
                bundle.putInt(EXTRA_COLOR, this.mColor);
            }
            if (this.mUnreadConversation != null) {
                bundle.putBundle(EXTRA_CONVERSATION, IMPL.getBundleForUnreadConversation(this.mUnreadConversation));
            }
            builder.getExtras().putBundle(EXTRA_CAR_EXTENDER, bundle);
            return builder;
        }

        public int getColor() {
            return this.mColor;
        }

        public Bitmap getLargeIcon() {
            return this.mLargeIcon;
        }

        public UnreadConversation getUnreadConversation() {
            return this.mUnreadConversation;
        }

        public CarExtender setColor(int n) {
            this.mColor = n;
            return this;
        }

        public CarExtender setLargeIcon(Bitmap bitmap) {
            this.mLargeIcon = bitmap;
            return this;
        }

        public CarExtender setUnreadConversation(UnreadConversation unreadConversation) {
            this.mUnreadConversation = unreadConversation;
            return this;
        }

        public static class UnreadConversation
        extends NotificationCompatBase.UnreadConversation {
            static final NotificationCompatBase.UnreadConversation.Factory FACTORY = new NotificationCompatBase.UnreadConversation.Factory(){

                @Override
                public UnreadConversation build(String[] arrstring, RemoteInputCompatBase.RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] arrstring2, long l) {
                    return new UnreadConversation(arrstring, (RemoteInput)remoteInput, pendingIntent, pendingIntent2, arrstring2, l);
                }
            };
            private final long mLatestTimestamp;
            private final String[] mMessages;
            private final String[] mParticipants;
            private final PendingIntent mReadPendingIntent;
            private final RemoteInput mRemoteInput;
            private final PendingIntent mReplyPendingIntent;

            UnreadConversation(String[] arrstring, RemoteInput remoteInput, PendingIntent pendingIntent, PendingIntent pendingIntent2, String[] arrstring2, long l) {
                this.mMessages = arrstring;
                this.mRemoteInput = remoteInput;
                this.mReadPendingIntent = pendingIntent2;
                this.mReplyPendingIntent = pendingIntent;
                this.mParticipants = arrstring2;
                this.mLatestTimestamp = l;
            }

            @Override
            public long getLatestTimestamp() {
                return this.mLatestTimestamp;
            }

            @Override
            public String[] getMessages() {
                return this.mMessages;
            }

            @Override
            public String getParticipant() {
                if (this.mParticipants.length > 0) {
                    return this.mParticipants[0];
                }
                return null;
            }

            @Override
            public String[] getParticipants() {
                return this.mParticipants;
            }

            @Override
            public PendingIntent getReadPendingIntent() {
                return this.mReadPendingIntent;
            }

            @Override
            public RemoteInput getRemoteInput() {
                return this.mRemoteInput;
            }

            @Override
            public PendingIntent getReplyPendingIntent() {
                return this.mReplyPendingIntent;
            }

            public static class Builder {
                private long mLatestTimestamp;
                private final List<String> mMessages = new ArrayList<String>();
                private final String mParticipant;
                private PendingIntent mReadPendingIntent;
                private RemoteInput mRemoteInput;
                private PendingIntent mReplyPendingIntent;

                public Builder(String string2) {
                    this.mParticipant = string2;
                }

                public Builder addMessage(String string2) {
                    this.mMessages.add(string2);
                    return this;
                }

                public UnreadConversation build() {
                    String[] arrstring = this.mMessages.toArray(new String[this.mMessages.size()]);
                    String string2 = this.mParticipant;
                    RemoteInput remoteInput = this.mRemoteInput;
                    PendingIntent pendingIntent = this.mReplyPendingIntent;
                    PendingIntent pendingIntent2 = this.mReadPendingIntent;
                    long l = this.mLatestTimestamp;
                    return new UnreadConversation(arrstring, remoteInput, pendingIntent, pendingIntent2, new String[]{string2}, l);
                }

                public Builder setLatestTimestamp(long l) {
                    this.mLatestTimestamp = l;
                    return this;
                }

                public Builder setReadPendingIntent(PendingIntent pendingIntent) {
                    this.mReadPendingIntent = pendingIntent;
                    return this;
                }

                public Builder setReplyAction(PendingIntent pendingIntent, RemoteInput remoteInput) {
                    this.mRemoteInput = remoteInput;
                    this.mReplyPendingIntent = pendingIntent;
                    return this;
                }
            }

        }

    }

    public static class DecoratedCustomViewStyle
    extends Style {
        private static final int MAX_ACTION_BUTTONS = 3;

        /*
         * Enabled aggressive block sorting
         */
        private RemoteViews createRemoteViews(RemoteViews remoteViews, boolean bl) {
            int n;
            int n2 = 0;
            RemoteViews remoteViews2 = this.applyStandardTemplate(true, R.layout.notification_template_custom_big, false);
            remoteViews2.removeAllViews(R.id.actions);
            int n3 = n = 0;
            if (bl) {
                n3 = n;
                if (this.mBuilder.mActions != null) {
                    int n4 = Math.min(this.mBuilder.mActions.size(), 3);
                    n3 = n;
                    if (n4 > 0) {
                        int n5 = 1;
                        n = 0;
                        do {
                            n3 = n5;
                            if (n >= n4) break;
                            RemoteViews remoteViews3 = this.generateActionButton(this.mBuilder.mActions.get(n));
                            remoteViews2.addView(R.id.actions, remoteViews3);
                            ++n;
                        } while (true);
                    }
                }
            }
            n3 = n3 != 0 ? n2 : 8;
            remoteViews2.setViewVisibility(R.id.actions, n3);
            remoteViews2.setViewVisibility(R.id.action_divider, n3);
            this.buildIntoRemoteViews(remoteViews2, remoteViews);
            return remoteViews2;
        }

        /*
         * Enabled aggressive block sorting
         */
        private RemoteViews generateActionButton(Action action) {
            boolean bl = action.actionIntent == null;
            String string2 = this.mBuilder.mContext.getPackageName();
            int n = bl ? R.layout.notification_action_tombstone : R.layout.notification_action;
            string2 = new RemoteViews(string2, n);
            string2.setImageViewBitmap(R.id.action_image, this.createColoredBitmap(action.getIcon(), this.mBuilder.mContext.getResources().getColor(R.color.notification_action_color_filter)));
            string2.setTextViewText(R.id.action_text, action.title);
            if (!bl) {
                string2.setOnClickPendingIntent(R.id.action_container, action.actionIntent);
            }
            if (Build.VERSION.SDK_INT >= 15) {
                string2.setContentDescription(R.id.action_container, action.title);
            }
            return string2;
        }

        @Override
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24) {
                notificationBuilderWithBuilderAccessor.getBuilder().setStyle((Notification.Style)new Notification.DecoratedCustomViewStyle());
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            block5: {
                block4: {
                    if (Build.VERSION.SDK_INT >= 24) break block4;
                    notificationBuilderWithBuilderAccessor = this.mBuilder.getBigContentView();
                    if (notificationBuilderWithBuilderAccessor == null) {
                        notificationBuilderWithBuilderAccessor = this.mBuilder.getContentView();
                    }
                    if (notificationBuilderWithBuilderAccessor != null) break block5;
                }
                return null;
            }
            return this.createRemoteViews((RemoteViews)notificationBuilderWithBuilderAccessor, true);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 24 || this.mBuilder.getContentView() == null) {
                return null;
            }
            return this.createRemoteViews(this.mBuilder.getContentView(), false);
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            block3: {
                block2: {
                    if (Build.VERSION.SDK_INT >= 24) break block2;
                    RemoteViews remoteViews = this.mBuilder.getHeadsUpContentView();
                    notificationBuilderWithBuilderAccessor = remoteViews != null ? remoteViews : this.mBuilder.getContentView();
                    if (remoteViews != null) break block3;
                }
                return null;
            }
            return this.createRemoteViews((RemoteViews)notificationBuilderWithBuilderAccessor, true);
        }
    }

    public static interface Extender {
        public Builder extend(Builder var1);
    }

    public static class InboxStyle
    extends Style {
        private ArrayList<CharSequence> mTexts = new ArrayList();

        public InboxStyle() {
        }

        public InboxStyle(Builder builder) {
            this.setBuilder(builder);
        }

        public InboxStyle addLine(CharSequence charSequence) {
            this.mTexts.add(Builder.limitCharSequenceLength(charSequence));
            return this;
        }

        @Override
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            if (Build.VERSION.SDK_INT >= 16) {
                NotificationCompatJellybean.addInboxStyle(notificationBuilderWithBuilderAccessor, this.mBigContentTitle, this.mSummaryTextSet, this.mSummaryText, this.mTexts);
            }
        }

        public InboxStyle setBigContentTitle(CharSequence charSequence) {
            this.mBigContentTitle = Builder.limitCharSequenceLength(charSequence);
            return this;
        }

        public InboxStyle setSummaryText(CharSequence charSequence) {
            this.mSummaryText = Builder.limitCharSequenceLength(charSequence);
            this.mSummaryTextSet = true;
            return this;
        }
    }

    public static class MessagingStyle
    extends Style {
        public static final int MAXIMUM_RETAINED_MESSAGES = 25;
        CharSequence mConversationTitle;
        List<Message> mMessages = new ArrayList<Message>();
        CharSequence mUserDisplayName;

        MessagingStyle() {
        }

        public MessagingStyle(CharSequence charSequence) {
            this.mUserDisplayName = charSequence;
        }

        public static MessagingStyle extractMessagingStyleFromNotification(Notification notification) {
            if ((notification = NotificationCompat.getExtras(notification)) != null && !notification.containsKey(NotificationCompat.EXTRA_SELF_DISPLAY_NAME)) {
                return null;
            }
            try {
                MessagingStyle messagingStyle = new MessagingStyle();
                messagingStyle.restoreFromCompatExtras((Bundle)notification);
                return messagingStyle;
            }
            catch (ClassCastException classCastException) {
                return null;
            }
        }

        private Message findLatestIncomingMessage() {
            for (int i = this.mMessages.size() - 1; i >= 0; --i) {
                Message message = this.mMessages.get(i);
                if (TextUtils.isEmpty((CharSequence)message.getSender())) continue;
                return message;
            }
            if (!this.mMessages.isEmpty()) {
                return this.mMessages.get(this.mMessages.size() - 1);
            }
            return null;
        }

        private boolean hasMessagesWithoutSender() {
            for (int i = this.mMessages.size() - 1; i >= 0; --i) {
                if (this.mMessages.get(i).getSender() != null) continue;
                return true;
            }
            return false;
        }

        private TextAppearanceSpan makeFontColorSpan(int n) {
            return new TextAppearanceSpan(null, 0, 0, ColorStateList.valueOf((int)n), null);
        }

        /*
         * Enabled aggressive block sorting
         */
        private CharSequence makeMessageLine(Message object) {
            CharSequence charSequence;
            BidiFormatter bidiFormatter = BidiFormatter.getInstance();
            SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder();
            boolean bl = Build.VERSION.SDK_INT >= 21;
            int n = bl ? -16777216 : -1;
            CharSequence charSequence2 = ((Message)object).getSender();
            int n2 = n;
            if (TextUtils.isEmpty((CharSequence)((Message)object).getSender())) {
                charSequence = this.mUserDisplayName == null ? "" : this.mUserDisplayName;
                n2 = n;
                charSequence2 = charSequence;
                if (bl) {
                    n2 = n;
                    charSequence2 = charSequence;
                    if (this.mBuilder.getColor() != 0) {
                        n2 = this.mBuilder.getColor();
                        charSequence2 = charSequence;
                    }
                }
            }
            charSequence = bidiFormatter.unicodeWrap(charSequence2);
            spannableStringBuilder.append(charSequence);
            spannableStringBuilder.setSpan((Object)this.makeFontColorSpan(n2), spannableStringBuilder.length() - charSequence.length(), spannableStringBuilder.length(), 33);
            object = ((Message)object).getText() == null ? "" : ((Message)object).getText();
            spannableStringBuilder.append((CharSequence)"  ").append(bidiFormatter.unicodeWrap((CharSequence)object));
            return spannableStringBuilder;
        }

        @Override
        public void addCompatExtras(Bundle bundle) {
            super.addCompatExtras(bundle);
            if (this.mUserDisplayName != null) {
                bundle.putCharSequence(NotificationCompat.EXTRA_SELF_DISPLAY_NAME, this.mUserDisplayName);
            }
            if (this.mConversationTitle != null) {
                bundle.putCharSequence(NotificationCompat.EXTRA_CONVERSATION_TITLE, this.mConversationTitle);
            }
            if (!this.mMessages.isEmpty()) {
                bundle.putParcelableArray(NotificationCompat.EXTRA_MESSAGES, (Parcelable[])Message.getBundleArrayForMessages(this.mMessages));
            }
        }

        public MessagingStyle addMessage(Message message) {
            this.mMessages.add(message);
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        public MessagingStyle addMessage(CharSequence charSequence, long l, CharSequence charSequence2) {
            this.mMessages.add(new Message(charSequence, l, charSequence2));
            if (this.mMessages.size() > 25) {
                this.mMessages.remove(0);
            }
            return this;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            Object object;
            Notification.Builder builder;
            boolean bl;
            if (Build.VERSION.SDK_INT >= 24) {
                ArrayList<CharSequence> arrayList = new ArrayList<CharSequence>();
                ArrayList<Long> arrayList2 = new ArrayList<Long>();
                ArrayList<CharSequence> arrayList3 = new ArrayList<CharSequence>();
                ArrayList<String> arrayList4 = new ArrayList<String>();
                ArrayList<Uri> arrayList5 = new ArrayList<Uri>();
                for (Message message : this.mMessages) {
                    arrayList.add(message.getText());
                    arrayList2.add(message.getTimestamp());
                    arrayList3.add(message.getSender());
                    arrayList4.add(message.getDataMimeType());
                    arrayList5.add(message.getDataUri());
                }
                NotificationCompatApi24.addMessagingStyle(notificationBuilderWithBuilderAccessor, this.mUserDisplayName, this.mConversationTitle, arrayList, arrayList2, arrayList3, arrayList4, arrayList5);
                return;
            } else {
                object = this.findLatestIncomingMessage();
                if (this.mConversationTitle != null) {
                    notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(this.mConversationTitle);
                } else if (object != null) {
                    notificationBuilderWithBuilderAccessor.getBuilder().setContentTitle(((Message)object).getSender());
                }
                if (object != null) {
                    builder = notificationBuilderWithBuilderAccessor.getBuilder();
                    object = this.mConversationTitle != null ? this.makeMessageLine((Message)object) : ((Message)object).getText();
                    builder.setContentText((CharSequence)object);
                }
                if (Build.VERSION.SDK_INT < 16) return;
                {
                    builder = new SpannableStringBuilder();
                    bl = this.mConversationTitle != null || this.hasMessagesWithoutSender();
                }
            }
            int n = this.mMessages.size() - 1;
            do {
                if (n < 0) {
                    NotificationCompatJellybean.addBigTextStyle(notificationBuilderWithBuilderAccessor, null, false, null, (CharSequence)builder);
                    return;
                }
                object = this.mMessages.get(n);
                object = bl ? this.makeMessageLine((Message)object) : ((Message)object).getText();
                if (n != this.mMessages.size() - 1) {
                    builder.insert(0, (CharSequence)"\n");
                }
                builder.insert(0, (CharSequence)object);
                --n;
            } while (true);
        }

        public CharSequence getConversationTitle() {
            return this.mConversationTitle;
        }

        public List<Message> getMessages() {
            return this.mMessages;
        }

        public CharSequence getUserDisplayName() {
            return this.mUserDisplayName;
        }

        @Override
        protected void restoreFromCompatExtras(Bundle arrparcelable) {
            this.mMessages.clear();
            this.mUserDisplayName = arrparcelable.getString(NotificationCompat.EXTRA_SELF_DISPLAY_NAME);
            this.mConversationTitle = arrparcelable.getString(NotificationCompat.EXTRA_CONVERSATION_TITLE);
            arrparcelable = arrparcelable.getParcelableArray(NotificationCompat.EXTRA_MESSAGES);
            if (arrparcelable != null) {
                this.mMessages = Message.getMessagesFromBundleArray(arrparcelable);
            }
        }

        public MessagingStyle setConversationTitle(CharSequence charSequence) {
            this.mConversationTitle = charSequence;
            return this;
        }

        public static final class Message {
            static final String KEY_DATA_MIME_TYPE = "type";
            static final String KEY_DATA_URI = "uri";
            static final String KEY_EXTRAS_BUNDLE = "extras";
            static final String KEY_SENDER = "sender";
            static final String KEY_TEXT = "text";
            static final String KEY_TIMESTAMP = "time";
            private String mDataMimeType;
            private Uri mDataUri;
            private Bundle mExtras = new Bundle();
            private final CharSequence mSender;
            private final CharSequence mText;
            private final long mTimestamp;

            public Message(CharSequence charSequence, long l, CharSequence charSequence2) {
                this.mText = charSequence;
                this.mTimestamp = l;
                this.mSender = charSequence2;
            }

            static Bundle[] getBundleArrayForMessages(List<Message> list) {
                Bundle[] arrbundle = new Bundle[list.size()];
                int n = list.size();
                for (int i = 0; i < n; ++i) {
                    arrbundle[i] = list.get(i).toBundle();
                }
                return arrbundle;
            }

            static Message getMessageFromBundle(Bundle bundle) {
                Message message;
                block6: {
                    block5: {
                        Message message2;
                        try {
                            if (!bundle.containsKey(KEY_TEXT) || !bundle.containsKey(KEY_TIMESTAMP)) break block5;
                            message2 = new Message(bundle.getCharSequence(KEY_TEXT), bundle.getLong(KEY_TIMESTAMP), bundle.getCharSequence(KEY_SENDER));
                            if (bundle.containsKey(KEY_DATA_MIME_TYPE) && bundle.containsKey(KEY_DATA_URI)) {
                                message2.setData(bundle.getString(KEY_DATA_MIME_TYPE), (Uri)bundle.getParcelable(KEY_DATA_URI));
                            }
                            message = message2;
                        }
                        catch (ClassCastException classCastException) {
                            return null;
                        }
                        if (bundle.containsKey(KEY_EXTRAS_BUNDLE)) {
                            message2.getExtras().putAll(bundle.getBundle(KEY_EXTRAS_BUNDLE));
                            return message2;
                        }
                        break block6;
                    }
                    message = null;
                }
                return message;
            }

            static List<Message> getMessagesFromBundleArray(Parcelable[] arrparcelable) {
                ArrayList<Message> arrayList = new ArrayList<Message>(arrparcelable.length);
                for (int i = 0; i < arrparcelable.length; ++i) {
                    Message message;
                    if (!(arrparcelable[i] instanceof Bundle) || (message = Message.getMessageFromBundle((Bundle)arrparcelable[i])) == null) continue;
                    arrayList.add(message);
                }
                return arrayList;
            }

            private Bundle toBundle() {
                Bundle bundle = new Bundle();
                if (this.mText != null) {
                    bundle.putCharSequence(KEY_TEXT, this.mText);
                }
                bundle.putLong(KEY_TIMESTAMP, this.mTimestamp);
                if (this.mSender != null) {
                    bundle.putCharSequence(KEY_SENDER, this.mSender);
                }
                if (this.mDataMimeType != null) {
                    bundle.putString(KEY_DATA_MIME_TYPE, this.mDataMimeType);
                }
                if (this.mDataUri != null) {
                    bundle.putParcelable(KEY_DATA_URI, (Parcelable)this.mDataUri);
                }
                if (this.mExtras != null) {
                    bundle.putBundle(KEY_EXTRAS_BUNDLE, this.mExtras);
                }
                return bundle;
            }

            public String getDataMimeType() {
                return this.mDataMimeType;
            }

            public Uri getDataUri() {
                return this.mDataUri;
            }

            public Bundle getExtras() {
                return this.mExtras;
            }

            public CharSequence getSender() {
                return this.mSender;
            }

            public CharSequence getText() {
                return this.mText;
            }

            public long getTimestamp() {
                return this.mTimestamp;
            }

            public Message setData(String string2, Uri uri) {
                this.mDataMimeType = string2;
                this.mDataUri = uri;
                return this;
            }
        }

    }

    static class NotificationCompatApi16Impl
    extends NotificationCompatBaseImpl {
        NotificationCompatApi16Impl() {
        }

        @Override
        public Notification build(Builder builder, BuilderExtender builderExtender) {
            NotificationCompatJellybean.Builder builder2 = new NotificationCompatJellybean.Builder(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon, builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate, builder.mUseChronometer, builder.mPriority, builder.mSubText, builder.mLocalOnly, builder.mExtras, builder.mGroupKey, builder.mGroupSummary, builder.mSortKey, builder.mContentView, builder.mBigContentView);
            NotificationCompat.addActionsToBuilder(builder2, builder.mActions);
            if (builder.mStyle != null) {
                builder.mStyle.apply(builder2);
            }
            builderExtender = builderExtender.build(builder, builder2);
            if (builder.mStyle != null && (builder2 = NotificationCompat.getExtras((Notification)builderExtender)) != null) {
                builder.mStyle.addCompatExtras((Bundle)builder2);
            }
            return builderExtender;
        }

        @Override
        public Action getAction(Notification notification, int n) {
            return (Action)NotificationCompatJellybean.getAction(notification, n, Action.FACTORY, RemoteInput.FACTORY);
        }

        @Override
        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            return (Action[])NotificationCompatJellybean.getActionsFromParcelableArrayList(arrayList, Action.FACTORY, RemoteInput.FACTORY);
        }

        @Override
        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] arraction) {
            return NotificationCompatJellybean.getParcelableArrayListForActions(arraction);
        }
    }

    static class NotificationCompatApi19Impl
    extends NotificationCompatApi16Impl {
        NotificationCompatApi19Impl() {
        }

        @Override
        public Notification build(Builder builder, BuilderExtender builderExtender) {
            NotificationCompatKitKat.Builder builder2 = new NotificationCompatKitKat.Builder(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon, builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate, builder.mShowWhen, builder.mUseChronometer, builder.mPriority, builder.mSubText, builder.mLocalOnly, builder.mPeople, builder.mExtras, builder.mGroupKey, builder.mGroupSummary, builder.mSortKey, builder.mContentView, builder.mBigContentView);
            NotificationCompat.addActionsToBuilder(builder2, builder.mActions);
            if (builder.mStyle != null) {
                builder.mStyle.apply(builder2);
            }
            return builderExtender.build(builder, builder2);
        }

        @Override
        public Action getAction(Notification notification, int n) {
            return (Action)NotificationCompatKitKat.getAction(notification, n, Action.FACTORY, RemoteInput.FACTORY);
        }
    }

    static class NotificationCompatApi20Impl
    extends NotificationCompatApi19Impl {
        NotificationCompatApi20Impl() {
        }

        @Override
        public Notification build(Builder builder, BuilderExtender builderExtender) {
            NotificationCompatApi20.Builder builder2 = new NotificationCompatApi20.Builder(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon, builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate, builder.mShowWhen, builder.mUseChronometer, builder.mPriority, builder.mSubText, builder.mLocalOnly, builder.mPeople, builder.mExtras, builder.mGroupKey, builder.mGroupSummary, builder.mSortKey, builder.mContentView, builder.mBigContentView, builder.mGroupAlertBehavior);
            NotificationCompat.addActionsToBuilder(builder2, builder.mActions);
            if (builder.mStyle != null) {
                builder.mStyle.apply(builder2);
            }
            builderExtender = builderExtender.build(builder, builder2);
            if (builder.mStyle != null) {
                builder.mStyle.addCompatExtras(NotificationCompat.getExtras((Notification)builderExtender));
            }
            return builderExtender;
        }

        @Override
        public Action getAction(Notification notification, int n) {
            return (Action)NotificationCompatApi20.getAction(notification, n, Action.FACTORY, RemoteInput.FACTORY);
        }

        @Override
        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            return (Action[])NotificationCompatApi20.getActionsFromParcelableArrayList(arrayList, Action.FACTORY, RemoteInput.FACTORY);
        }

        @Override
        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] arraction) {
            return NotificationCompatApi20.getParcelableArrayListForActions(arraction);
        }
    }

    static class NotificationCompatApi21Impl
    extends NotificationCompatApi20Impl {
        NotificationCompatApi21Impl() {
        }

        @Override
        public Notification build(Builder builder, BuilderExtender builderExtender) {
            NotificationCompatApi21.Builder builder2 = new NotificationCompatApi21.Builder(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon, builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate, builder.mShowWhen, builder.mUseChronometer, builder.mPriority, builder.mSubText, builder.mLocalOnly, builder.mCategory, builder.mPeople, builder.mExtras, builder.mColor, builder.mVisibility, builder.mPublicVersion, builder.mGroupKey, builder.mGroupSummary, builder.mSortKey, builder.mContentView, builder.mBigContentView, builder.mHeadsUpContentView, builder.mGroupAlertBehavior);
            NotificationCompat.addActionsToBuilder(builder2, builder.mActions);
            if (builder.mStyle != null) {
                builder.mStyle.apply(builder2);
            }
            builderExtender = builderExtender.build(builder, builder2);
            if (builder.mStyle != null) {
                builder.mStyle.addCompatExtras(NotificationCompat.getExtras((Notification)builderExtender));
            }
            return builderExtender;
        }

        @Override
        public Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation unreadConversation) {
            return NotificationCompatApi21.getBundleForUnreadConversation(unreadConversation);
        }

        @Override
        public NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle bundle, NotificationCompatBase.UnreadConversation.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
            return NotificationCompatApi21.getUnreadConversationFromBundle(bundle, factory, factory2);
        }
    }

    static class NotificationCompatApi24Impl
    extends NotificationCompatApi21Impl {
        NotificationCompatApi24Impl() {
        }

        @Override
        public Notification build(Builder builder, BuilderExtender builderExtender) {
            NotificationCompatApi24.Builder builder2 = new NotificationCompatApi24.Builder(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon, builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate, builder.mShowWhen, builder.mUseChronometer, builder.mPriority, builder.mSubText, builder.mLocalOnly, builder.mCategory, builder.mPeople, builder.mExtras, builder.mColor, builder.mVisibility, builder.mPublicVersion, builder.mGroupKey, builder.mGroupSummary, builder.mSortKey, builder.mRemoteInputHistory, builder.mContentView, builder.mBigContentView, builder.mHeadsUpContentView, builder.mGroupAlertBehavior);
            NotificationCompat.addActionsToBuilder(builder2, builder.mActions);
            if (builder.mStyle != null) {
                builder.mStyle.apply(builder2);
            }
            builderExtender = builderExtender.build(builder, builder2);
            if (builder.mStyle != null) {
                builder.mStyle.addCompatExtras(NotificationCompat.getExtras((Notification)builderExtender));
            }
            return builderExtender;
        }

        @Override
        public Action getAction(Notification notification, int n) {
            return (Action)NotificationCompatApi24.getAction(notification, n, Action.FACTORY, RemoteInput.FACTORY);
        }

        @Override
        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            return (Action[])NotificationCompatApi24.getActionsFromParcelableArrayList(arrayList, Action.FACTORY, RemoteInput.FACTORY);
        }

        @Override
        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] arraction) {
            return NotificationCompatApi24.getParcelableArrayListForActions(arraction);
        }
    }

    static class NotificationCompatApi26Impl
    extends NotificationCompatApi24Impl {
        NotificationCompatApi26Impl() {
        }

        @Override
        public Notification build(Builder builder, BuilderExtender builderExtender) {
            NotificationCompatApi26.Builder builder2 = new NotificationCompatApi26.Builder(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon, builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate, builder.mShowWhen, builder.mUseChronometer, builder.mPriority, builder.mSubText, builder.mLocalOnly, builder.mCategory, builder.mPeople, builder.mExtras, builder.mColor, builder.mVisibility, builder.mPublicVersion, builder.mGroupKey, builder.mGroupSummary, builder.mSortKey, builder.mRemoteInputHistory, builder.mContentView, builder.mBigContentView, builder.mHeadsUpContentView, builder.mChannelId, builder.mBadgeIcon, builder.mShortcutId, builder.mTimeout, builder.mColorized, builder.mColorizedSet, builder.mGroupAlertBehavior);
            NotificationCompat.addActionsToBuilder(builder2, builder.mActions);
            if (builder.mStyle != null) {
                builder.mStyle.apply(builder2);
            }
            builderExtender = builderExtender.build(builder, builder2);
            if (builder.mStyle != null) {
                builder.mStyle.addCompatExtras(NotificationCompat.getExtras((Notification)builderExtender));
            }
            return builderExtender;
        }
    }

    static class NotificationCompatBaseImpl
    implements NotificationCompatImpl {
        NotificationCompatBaseImpl() {
        }

        @Override
        public Notification build(Builder builder, BuilderExtender builderExtender) {
            return builderExtender.build(builder, new BuilderBase(builder.mContext, builder.mNotification, builder.mContentTitle, builder.mContentText, builder.mContentInfo, builder.mTickerView, builder.mNumber, builder.mContentIntent, builder.mFullScreenIntent, builder.mLargeIcon, builder.mProgressMax, builder.mProgress, builder.mProgressIndeterminate));
        }

        @Override
        public Action getAction(Notification notification, int n) {
            return null;
        }

        @Override
        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> arrayList) {
            return null;
        }

        @Override
        public Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation unreadConversation) {
            return null;
        }

        @Override
        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] arraction) {
            return null;
        }

        @Override
        public NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle bundle, NotificationCompatBase.UnreadConversation.Factory factory, RemoteInputCompatBase.RemoteInput.Factory factory2) {
            return null;
        }

        public static class BuilderBase
        implements NotificationBuilderWithBuilderAccessor {
            private Notification.Builder mBuilder;

            /*
             * Enabled aggressive block sorting
             */
            BuilderBase(Context context, Notification notification, CharSequence charSequence, CharSequence charSequence2, CharSequence charSequence3, RemoteViews remoteViews, int n, PendingIntent pendingIntent, PendingIntent pendingIntent2, Bitmap bitmap, int n2, int n3, boolean bl) {
                context = new Notification.Builder(context).setWhen(notification.when).setSmallIcon(notification.icon, notification.iconLevel).setContent(notification.contentView).setTicker(notification.tickerText, remoteViews).setSound(notification.sound, notification.audioStreamType).setVibrate(notification.vibrate).setLights(notification.ledARGB, notification.ledOnMS, notification.ledOffMS);
                boolean bl2 = (notification.flags & 2) != 0;
                context = context.setOngoing(bl2);
                bl2 = (notification.flags & 8) != 0;
                context = context.setOnlyAlertOnce(bl2);
                bl2 = (notification.flags & 0x10) != 0;
                context = context.setAutoCancel(bl2).setDefaults(notification.defaults).setContentTitle(charSequence).setContentText(charSequence2).setContentInfo(charSequence3).setContentIntent(pendingIntent).setDeleteIntent(notification.deleteIntent);
                bl2 = (notification.flags & 0x80) != 0;
                this.mBuilder = context.setFullScreenIntent(pendingIntent2, bl2).setLargeIcon(bitmap).setNumber(n).setProgress(n2, n3, bl);
            }

            @Override
            public Notification build() {
                return this.mBuilder.getNotification();
            }

            @Override
            public Notification.Builder getBuilder() {
                return this.mBuilder;
            }
        }

    }

    static interface NotificationCompatImpl {
        public Notification build(Builder var1, BuilderExtender var2);

        public Action getAction(Notification var1, int var2);

        public Action[] getActionsFromParcelableArrayList(ArrayList<Parcelable> var1);

        public Bundle getBundleForUnreadConversation(NotificationCompatBase.UnreadConversation var1);

        public ArrayList<Parcelable> getParcelableArrayListForActions(Action[] var1);

        public NotificationCompatBase.UnreadConversation getUnreadConversationFromBundle(Bundle var1, NotificationCompatBase.UnreadConversation.Factory var2, RemoteInputCompatBase.RemoteInput.Factory var3);
    }

    @Retention(value=RetentionPolicy.SOURCE)
    public static @interface NotificationVisibility {
    }

    public static abstract class Style {
        CharSequence mBigContentTitle;
        protected Builder mBuilder;
        CharSequence mSummaryText;
        boolean mSummaryTextSet = false;

        private int calculateTopPadding() {
            Resources resources = this.mBuilder.mContext.getResources();
            int n = resources.getDimensionPixelSize(R.dimen.notification_top_pad);
            int n2 = resources.getDimensionPixelSize(R.dimen.notification_top_pad_large_text);
            float f = (Style.constrain(resources.getConfiguration().fontScale, 1.0f, 1.3f) - 1.0f) / 0.29999995f;
            return Math.round((1.0f - f) * (float)n + (float)n2 * f);
        }

        private static float constrain(float f, float f2, float f3) {
            if (f < f2) {
                return f2;
            }
            if (f > f3) {
                return f3;
            }
            return f;
        }

        /*
         * Enabled aggressive block sorting
         */
        private Bitmap createColoredBitmap(int n, int n2, int n3) {
            Drawable drawable2 = this.mBuilder.mContext.getResources().getDrawable(n);
            n = n3 == 0 ? drawable2.getIntrinsicWidth() : n3;
            if (n3 == 0) {
                n3 = drawable2.getIntrinsicHeight();
            }
            Bitmap bitmap = Bitmap.createBitmap((int)n, (int)n3, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            drawable2.setBounds(0, 0, n, n3);
            if (n2 != 0) {
                drawable2.mutate().setColorFilter((ColorFilter)new PorterDuffColorFilter(n2, PorterDuff.Mode.SRC_IN));
            }
            drawable2.draw(new Canvas(bitmap));
            return bitmap;
        }

        private Bitmap createIconWithBackground(int n, int n2, int n3, int n4) {
            int n5 = R.drawable.notification_icon_background;
            int n6 = n4;
            if (n4 == 0) {
                n6 = 0;
            }
            Bitmap bitmap = this.createColoredBitmap(n5, n6, n2);
            Canvas canvas = new Canvas(bitmap);
            Drawable drawable2 = this.mBuilder.mContext.getResources().getDrawable(n).mutate();
            drawable2.setFilterBitmap(true);
            n = (n2 - n3) / 2;
            drawable2.setBounds(n, n, n3 + n, n3 + n);
            drawable2.setColorFilter((ColorFilter)new PorterDuffColorFilter(-1, PorterDuff.Mode.SRC_ATOP));
            drawable2.draw(canvas);
            return bitmap;
        }

        private void hideNormalContent(RemoteViews remoteViews) {
            remoteViews.setViewVisibility(R.id.title, 8);
            remoteViews.setViewVisibility(R.id.text2, 8);
            remoteViews.setViewVisibility(R.id.text, 8);
        }

        public void addCompatExtras(Bundle bundle) {
        }

        public void apply(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
        }

        /*
         * Enabled aggressive block sorting
         */
        public RemoteViews applyStandardTemplate(boolean bl, int n, boolean bl2) {
            NumberFormat numberFormat;
            int n2;
            Resources resources = this.mBuilder.mContext.getResources();
            RemoteViews remoteViews = new RemoteViews(this.mBuilder.mContext.getPackageName(), n);
            boolean bl3 = false;
            int n3 = 0;
            n = this.mBuilder.getPriority() < -1 ? 1 : 0;
            if (Build.VERSION.SDK_INT >= 16 && Build.VERSION.SDK_INT < 21) {
                if (n != 0) {
                    remoteViews.setInt(R.id.notification_background, "setBackgroundResource", R.drawable.notification_bg_low);
                    remoteViews.setInt(R.id.icon, "setBackgroundResource", R.drawable.notification_template_icon_low_bg);
                } else {
                    remoteViews.setInt(R.id.notification_background, "setBackgroundResource", R.drawable.notification_bg);
                    remoteViews.setInt(R.id.icon, "setBackgroundResource", R.drawable.notification_template_icon_bg);
                }
            }
            if (this.mBuilder.mLargeIcon != null) {
                if (Build.VERSION.SDK_INT >= 16) {
                    remoteViews.setViewVisibility(R.id.icon, 0);
                    remoteViews.setImageViewBitmap(R.id.icon, this.mBuilder.mLargeIcon);
                } else {
                    remoteViews.setViewVisibility(R.id.icon, 8);
                }
                if (bl && this.mBuilder.mNotification.icon != 0) {
                    n = resources.getDimensionPixelSize(R.dimen.notification_right_icon_size);
                    n2 = resources.getDimensionPixelSize(R.dimen.notification_small_icon_background_padding);
                    if (Build.VERSION.SDK_INT >= 21) {
                        numberFormat = this.createIconWithBackground(this.mBuilder.mNotification.icon, n, n - n2 * 2, this.mBuilder.getColor());
                        remoteViews.setImageViewBitmap(R.id.right_icon, (Bitmap)numberFormat);
                    } else {
                        remoteViews.setImageViewBitmap(R.id.right_icon, this.createColoredBitmap(this.mBuilder.mNotification.icon, -1));
                    }
                    remoteViews.setViewVisibility(R.id.right_icon, 0);
                }
            } else if (bl && this.mBuilder.mNotification.icon != 0) {
                remoteViews.setViewVisibility(R.id.icon, 0);
                if (Build.VERSION.SDK_INT >= 21) {
                    n = resources.getDimensionPixelSize(R.dimen.notification_large_icon_width);
                    n2 = resources.getDimensionPixelSize(R.dimen.notification_big_circle_margin);
                    int n4 = resources.getDimensionPixelSize(R.dimen.notification_small_icon_size_as_large);
                    numberFormat = this.createIconWithBackground(this.mBuilder.mNotification.icon, n - n2, n4, this.mBuilder.getColor());
                    remoteViews.setImageViewBitmap(R.id.icon, (Bitmap)numberFormat);
                } else {
                    remoteViews.setImageViewBitmap(R.id.icon, this.createColoredBitmap(this.mBuilder.mNotification.icon, -1));
                }
            }
            if (this.mBuilder.mContentTitle != null) {
                remoteViews.setTextViewText(R.id.title, this.mBuilder.mContentTitle);
            }
            if (this.mBuilder.mContentText != null) {
                remoteViews.setTextViewText(R.id.text, this.mBuilder.mContentText);
                bl3 = true;
            }
            n = Build.VERSION.SDK_INT < 21 && this.mBuilder.mLargeIcon != null ? 1 : 0;
            if (this.mBuilder.mContentInfo != null) {
                remoteViews.setTextViewText(R.id.info, this.mBuilder.mContentInfo);
                remoteViews.setViewVisibility(R.id.info, 0);
                bl3 = true;
                n = 1;
            } else if (this.mBuilder.mNumber > 0) {
                n = resources.getInteger(R.integer.status_bar_notification_info_maxnum);
                if (this.mBuilder.mNumber > n) {
                    remoteViews.setTextViewText(R.id.info, (CharSequence)resources.getString(R.string.status_bar_notification_info_overflow));
                } else {
                    numberFormat = NumberFormat.getIntegerInstance();
                    remoteViews.setTextViewText(R.id.info, (CharSequence)numberFormat.format(this.mBuilder.mNumber));
                }
                remoteViews.setViewVisibility(R.id.info, 0);
                bl3 = true;
                n = 1;
            } else {
                remoteViews.setViewVisibility(R.id.info, 8);
            }
            n2 = n3;
            if (this.mBuilder.mSubText != null) {
                n2 = n3;
                if (Build.VERSION.SDK_INT >= 16) {
                    remoteViews.setTextViewText(R.id.text, this.mBuilder.mSubText);
                    if (this.mBuilder.mContentText != null) {
                        remoteViews.setTextViewText(R.id.text2, this.mBuilder.mContentText);
                        remoteViews.setViewVisibility(R.id.text2, 0);
                        n2 = 1;
                    } else {
                        remoteViews.setViewVisibility(R.id.text2, 8);
                        n2 = n3;
                    }
                }
            }
            if (n2 != 0 && Build.VERSION.SDK_INT >= 16) {
                if (bl2) {
                    float f = resources.getDimensionPixelSize(R.dimen.notification_subtext_size);
                    remoteViews.setTextViewTextSize(R.id.text, 0, f);
                }
                remoteViews.setViewPadding(R.id.line1, 0, 0, 0, 0);
            }
            if (this.mBuilder.getWhenIfShowing() != 0L) {
                if (this.mBuilder.mUseChronometer && Build.VERSION.SDK_INT >= 16) {
                    remoteViews.setViewVisibility(R.id.chronometer, 0);
                    remoteViews.setLong(R.id.chronometer, "setBase", this.mBuilder.getWhenIfShowing() + (SystemClock.elapsedRealtime() - System.currentTimeMillis()));
                    remoteViews.setBoolean(R.id.chronometer, "setStarted", true);
                } else {
                    remoteViews.setViewVisibility(R.id.time, 0);
                    remoteViews.setLong(R.id.time, "setTime", this.mBuilder.getWhenIfShowing());
                }
                n = 1;
            }
            n2 = R.id.right_side;
            n = n != 0 ? 0 : 8;
            remoteViews.setViewVisibility(n2, n);
            n2 = R.id.line3;
            n = bl3 ? 0 : 8;
            remoteViews.setViewVisibility(n2, n);
            return remoteViews;
        }

        public Notification build() {
            Notification notification = null;
            if (this.mBuilder != null) {
                notification = this.mBuilder.build();
            }
            return notification;
        }

        public void buildIntoRemoteViews(RemoteViews remoteViews, RemoteViews remoteViews2) {
            this.hideNormalContent(remoteViews);
            remoteViews.removeAllViews(R.id.notification_main_column);
            remoteViews.addView(R.id.notification_main_column, remoteViews2.clone());
            remoteViews.setViewVisibility(R.id.notification_main_column, 0);
            if (Build.VERSION.SDK_INT >= 21) {
                remoteViews.setViewPadding(R.id.notification_main_column_container, 0, this.calculateTopPadding(), 0, 0);
            }
        }

        public Bitmap createColoredBitmap(int n, int n2) {
            return this.createColoredBitmap(n, n2, 0);
        }

        public RemoteViews makeBigContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        public RemoteViews makeContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        public RemoteViews makeHeadsUpContentView(NotificationBuilderWithBuilderAccessor notificationBuilderWithBuilderAccessor) {
            return null;
        }

        protected void restoreFromCompatExtras(Bundle bundle) {
        }

        public void setBuilder(Builder builder) {
            if (this.mBuilder != builder) {
                this.mBuilder = builder;
                if (this.mBuilder != null) {
                    this.mBuilder.setStyle(this);
                }
            }
        }
    }

    public static final class WearableExtender
    implements Extender {
        private static final int DEFAULT_CONTENT_ICON_GRAVITY = 8388613;
        private static final int DEFAULT_FLAGS = 1;
        private static final int DEFAULT_GRAVITY = 80;
        private static final String EXTRA_WEARABLE_EXTENSIONS = "android.wearable.EXTENSIONS";
        private static final int FLAG_BIG_PICTURE_AMBIENT = 32;
        private static final int FLAG_CONTENT_INTENT_AVAILABLE_OFFLINE = 1;
        private static final int FLAG_HINT_AVOID_BACKGROUND_CLIPPING = 16;
        private static final int FLAG_HINT_CONTENT_INTENT_LAUNCHES_ACTIVITY = 64;
        private static final int FLAG_HINT_HIDE_ICON = 2;
        private static final int FLAG_HINT_SHOW_BACKGROUND_ONLY = 4;
        private static final int FLAG_START_SCROLL_BOTTOM = 8;
        private static final String KEY_ACTIONS = "actions";
        private static final String KEY_BACKGROUND = "background";
        private static final String KEY_BRIDGE_TAG = "bridgeTag";
        private static final String KEY_CONTENT_ACTION_INDEX = "contentActionIndex";
        private static final String KEY_CONTENT_ICON = "contentIcon";
        private static final String KEY_CONTENT_ICON_GRAVITY = "contentIconGravity";
        private static final String KEY_CUSTOM_CONTENT_HEIGHT = "customContentHeight";
        private static final String KEY_CUSTOM_SIZE_PRESET = "customSizePreset";
        private static final String KEY_DISMISSAL_ID = "dismissalId";
        private static final String KEY_DISPLAY_INTENT = "displayIntent";
        private static final String KEY_FLAGS = "flags";
        private static final String KEY_GRAVITY = "gravity";
        private static final String KEY_HINT_SCREEN_TIMEOUT = "hintScreenTimeout";
        private static final String KEY_PAGES = "pages";
        public static final int SCREEN_TIMEOUT_LONG = -1;
        public static final int SCREEN_TIMEOUT_SHORT = 0;
        public static final int SIZE_DEFAULT = 0;
        public static final int SIZE_FULL_SCREEN = 5;
        public static final int SIZE_LARGE = 4;
        public static final int SIZE_MEDIUM = 3;
        public static final int SIZE_SMALL = 2;
        public static final int SIZE_XSMALL = 1;
        public static final int UNSET_ACTION_INDEX = -1;
        private ArrayList<Action> mActions = new ArrayList();
        private Bitmap mBackground;
        private String mBridgeTag;
        private int mContentActionIndex = -1;
        private int mContentIcon;
        private int mContentIconGravity = 8388613;
        private int mCustomContentHeight;
        private int mCustomSizePreset = 0;
        private String mDismissalId;
        private PendingIntent mDisplayIntent;
        private int mFlags = 1;
        private int mGravity = 80;
        private int mHintScreenTimeout;
        private ArrayList<Notification> mPages = new ArrayList();

        public WearableExtender() {
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public WearableExtender(Notification notification) {
            notification = NotificationCompat.getExtras(notification);
            if (notification == null) return;
            notification = notification.getBundle(EXTRA_WEARABLE_EXTENSIONS);
            if (notification == null) return;
            Action[] arraction = IMPL.getActionsFromParcelableArrayList(notification.getParcelableArrayList(KEY_ACTIONS));
            if (arraction != null) {
                Collections.addAll(this.mActions, arraction);
            }
            this.mFlags = notification.getInt(KEY_FLAGS, 1);
            this.mDisplayIntent = (PendingIntent)notification.getParcelable(KEY_DISPLAY_INTENT);
            arraction = NotificationCompat.getNotificationArrayFromBundle((Bundle)notification, KEY_PAGES);
            if (arraction != null) {
                Collections.addAll(this.mPages, arraction);
            }
            this.mBackground = (Bitmap)notification.getParcelable(KEY_BACKGROUND);
            this.mContentIcon = notification.getInt(KEY_CONTENT_ICON);
            this.mContentIconGravity = notification.getInt(KEY_CONTENT_ICON_GRAVITY, 8388613);
            this.mContentActionIndex = notification.getInt(KEY_CONTENT_ACTION_INDEX, -1);
            this.mCustomSizePreset = notification.getInt(KEY_CUSTOM_SIZE_PRESET, 0);
            this.mCustomContentHeight = notification.getInt(KEY_CUSTOM_CONTENT_HEIGHT);
            this.mGravity = notification.getInt(KEY_GRAVITY, 80);
            this.mHintScreenTimeout = notification.getInt(KEY_HINT_SCREEN_TIMEOUT);
            this.mDismissalId = notification.getString(KEY_DISMISSAL_ID);
            this.mBridgeTag = notification.getString(KEY_BRIDGE_TAG);
        }

        private void setFlag(int n, boolean bl) {
            if (bl) {
                this.mFlags |= n;
                return;
            }
            this.mFlags &= ~n;
        }

        public WearableExtender addAction(Action action) {
            this.mActions.add(action);
            return this;
        }

        public WearableExtender addActions(List<Action> list) {
            this.mActions.addAll(list);
            return this;
        }

        public WearableExtender addPage(Notification notification) {
            this.mPages.add(notification);
            return this;
        }

        public WearableExtender addPages(List<Notification> list) {
            this.mPages.addAll(list);
            return this;
        }

        public WearableExtender clearActions() {
            this.mActions.clear();
            return this;
        }

        public WearableExtender clearPages() {
            this.mPages.clear();
            return this;
        }

        public WearableExtender clone() {
            WearableExtender wearableExtender = new WearableExtender();
            wearableExtender.mActions = new ArrayList<Action>(this.mActions);
            wearableExtender.mFlags = this.mFlags;
            wearableExtender.mDisplayIntent = this.mDisplayIntent;
            wearableExtender.mPages = new ArrayList<Notification>(this.mPages);
            wearableExtender.mBackground = this.mBackground;
            wearableExtender.mContentIcon = this.mContentIcon;
            wearableExtender.mContentIconGravity = this.mContentIconGravity;
            wearableExtender.mContentActionIndex = this.mContentActionIndex;
            wearableExtender.mCustomSizePreset = this.mCustomSizePreset;
            wearableExtender.mCustomContentHeight = this.mCustomContentHeight;
            wearableExtender.mGravity = this.mGravity;
            wearableExtender.mHintScreenTimeout = this.mHintScreenTimeout;
            wearableExtender.mDismissalId = this.mDismissalId;
            wearableExtender.mBridgeTag = this.mBridgeTag;
            return wearableExtender;
        }

        @Override
        public Builder extend(Builder builder) {
            Bundle bundle = new Bundle();
            if (!this.mActions.isEmpty()) {
                bundle.putParcelableArrayList(KEY_ACTIONS, IMPL.getParcelableArrayListForActions(this.mActions.toArray(new Action[this.mActions.size()])));
            }
            if (this.mFlags != 1) {
                bundle.putInt(KEY_FLAGS, this.mFlags);
            }
            if (this.mDisplayIntent != null) {
                bundle.putParcelable(KEY_DISPLAY_INTENT, (Parcelable)this.mDisplayIntent);
            }
            if (!this.mPages.isEmpty()) {
                bundle.putParcelableArray(KEY_PAGES, (Parcelable[])this.mPages.toArray((T[])new Notification[this.mPages.size()]));
            }
            if (this.mBackground != null) {
                bundle.putParcelable(KEY_BACKGROUND, (Parcelable)this.mBackground);
            }
            if (this.mContentIcon != 0) {
                bundle.putInt(KEY_CONTENT_ICON, this.mContentIcon);
            }
            if (this.mContentIconGravity != 8388613) {
                bundle.putInt(KEY_CONTENT_ICON_GRAVITY, this.mContentIconGravity);
            }
            if (this.mContentActionIndex != -1) {
                bundle.putInt(KEY_CONTENT_ACTION_INDEX, this.mContentActionIndex);
            }
            if (this.mCustomSizePreset != 0) {
                bundle.putInt(KEY_CUSTOM_SIZE_PRESET, this.mCustomSizePreset);
            }
            if (this.mCustomContentHeight != 0) {
                bundle.putInt(KEY_CUSTOM_CONTENT_HEIGHT, this.mCustomContentHeight);
            }
            if (this.mGravity != 80) {
                bundle.putInt(KEY_GRAVITY, this.mGravity);
            }
            if (this.mHintScreenTimeout != 0) {
                bundle.putInt(KEY_HINT_SCREEN_TIMEOUT, this.mHintScreenTimeout);
            }
            if (this.mDismissalId != null) {
                bundle.putString(KEY_DISMISSAL_ID, this.mDismissalId);
            }
            if (this.mBridgeTag != null) {
                bundle.putString(KEY_BRIDGE_TAG, this.mBridgeTag);
            }
            builder.getExtras().putBundle(EXTRA_WEARABLE_EXTENSIONS, bundle);
            return builder;
        }

        public List<Action> getActions() {
            return this.mActions;
        }

        public Bitmap getBackground() {
            return this.mBackground;
        }

        public String getBridgeTag() {
            return this.mBridgeTag;
        }

        public int getContentAction() {
            return this.mContentActionIndex;
        }

        public int getContentIcon() {
            return this.mContentIcon;
        }

        public int getContentIconGravity() {
            return this.mContentIconGravity;
        }

        public boolean getContentIntentAvailableOffline() {
            return (this.mFlags & 1) != 0;
        }

        public int getCustomContentHeight() {
            return this.mCustomContentHeight;
        }

        public int getCustomSizePreset() {
            return this.mCustomSizePreset;
        }

        public String getDismissalId() {
            return this.mDismissalId;
        }

        public PendingIntent getDisplayIntent() {
            return this.mDisplayIntent;
        }

        public int getGravity() {
            return this.mGravity;
        }

        public boolean getHintAmbientBigPicture() {
            return (this.mFlags & 0x20) != 0;
        }

        public boolean getHintAvoidBackgroundClipping() {
            return (this.mFlags & 0x10) != 0;
        }

        public boolean getHintContentIntentLaunchesActivity() {
            return (this.mFlags & 0x40) != 0;
        }

        public boolean getHintHideIcon() {
            return (this.mFlags & 2) != 0;
        }

        public int getHintScreenTimeout() {
            return this.mHintScreenTimeout;
        }

        public boolean getHintShowBackgroundOnly() {
            return (this.mFlags & 4) != 0;
        }

        public List<Notification> getPages() {
            return this.mPages;
        }

        public boolean getStartScrollBottom() {
            return (this.mFlags & 8) != 0;
        }

        public WearableExtender setBackground(Bitmap bitmap) {
            this.mBackground = bitmap;
            return this;
        }

        public WearableExtender setBridgeTag(String string2) {
            this.mBridgeTag = string2;
            return this;
        }

        public WearableExtender setContentAction(int n) {
            this.mContentActionIndex = n;
            return this;
        }

        public WearableExtender setContentIcon(int n) {
            this.mContentIcon = n;
            return this;
        }

        public WearableExtender setContentIconGravity(int n) {
            this.mContentIconGravity = n;
            return this;
        }

        public WearableExtender setContentIntentAvailableOffline(boolean bl) {
            this.setFlag(1, bl);
            return this;
        }

        public WearableExtender setCustomContentHeight(int n) {
            this.mCustomContentHeight = n;
            return this;
        }

        public WearableExtender setCustomSizePreset(int n) {
            this.mCustomSizePreset = n;
            return this;
        }

        public WearableExtender setDismissalId(String string2) {
            this.mDismissalId = string2;
            return this;
        }

        public WearableExtender setDisplayIntent(PendingIntent pendingIntent) {
            this.mDisplayIntent = pendingIntent;
            return this;
        }

        public WearableExtender setGravity(int n) {
            this.mGravity = n;
            return this;
        }

        public WearableExtender setHintAmbientBigPicture(boolean bl) {
            this.setFlag(32, bl);
            return this;
        }

        public WearableExtender setHintAvoidBackgroundClipping(boolean bl) {
            this.setFlag(16, bl);
            return this;
        }

        public WearableExtender setHintContentIntentLaunchesActivity(boolean bl) {
            this.setFlag(64, bl);
            return this;
        }

        public WearableExtender setHintHideIcon(boolean bl) {
            this.setFlag(2, bl);
            return this;
        }

        public WearableExtender setHintScreenTimeout(int n) {
            this.mHintScreenTimeout = n;
            return this;
        }

        public WearableExtender setHintShowBackgroundOnly(boolean bl) {
            this.setFlag(4, bl);
            return this;
        }

        public WearableExtender setStartScrollBottom(boolean bl) {
            this.setFlag(8, bl);
            return this;
        }
    }

}

