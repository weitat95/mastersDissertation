/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.pm.PackageManager
 *  android.content.pm.PackageManager$NameNotFoundException
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Parcelable
 *  android.text.Html
 *  android.text.Spanned
 *  android.util.Log
 *  android.view.ActionProvider
 *  android.view.Menu
 *  android.view.MenuItem
 *  android.widget.ShareActionProvider
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Parcelable;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;
import android.view.ActionProvider;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ShareActionProvider;
import java.util.ArrayList;

public final class ShareCompat {
    public static final String EXTRA_CALLING_ACTIVITY = "android.support.v4.app.EXTRA_CALLING_ACTIVITY";
    public static final String EXTRA_CALLING_PACKAGE = "android.support.v4.app.EXTRA_CALLING_PACKAGE";
    private static final String HISTORY_FILENAME_PREFIX = ".sharecompat_";

    private ShareCompat() {
    }

    public static void configureMenuItem(Menu menu2, int n, IntentBuilder intentBuilder) {
        if ((menu2 = menu2.findItem(n)) == null) {
            throw new IllegalArgumentException("Could not find menu item with id " + n + " in the supplied menu");
        }
        ShareCompat.configureMenuItem((MenuItem)menu2, intentBuilder);
    }

    /*
     * Enabled aggressive block sorting
     */
    public static void configureMenuItem(MenuItem menuItem, IntentBuilder intentBuilder) {
        ActionProvider actionProvider = menuItem.getActionProvider();
        actionProvider = !(actionProvider instanceof ShareActionProvider) ? new ShareActionProvider((Context)intentBuilder.getActivity()) : (ShareActionProvider)actionProvider;
        actionProvider.setShareHistoryFileName(HISTORY_FILENAME_PREFIX + intentBuilder.getActivity().getClass().getName());
        actionProvider.setShareIntent(intentBuilder.getIntent());
        menuItem.setActionProvider(actionProvider);
        if (Build.VERSION.SDK_INT < 16 && !menuItem.hasSubMenu()) {
            menuItem.setIntent(intentBuilder.createChooserIntent());
        }
    }

    public static ComponentName getCallingActivity(Activity activity) {
        ComponentName componentName;
        ComponentName componentName2 = componentName = activity.getCallingActivity();
        if (componentName == null) {
            componentName2 = (ComponentName)activity.getIntent().getParcelableExtra(EXTRA_CALLING_ACTIVITY);
        }
        return componentName2;
    }

    public static String getCallingPackage(Activity activity) {
        String string2;
        String string3 = string2 = activity.getCallingPackage();
        if (string2 == null) {
            string3 = activity.getIntent().getStringExtra(EXTRA_CALLING_PACKAGE);
        }
        return string3;
    }

    public static class IntentBuilder {
        private Activity mActivity;
        private ArrayList<String> mBccAddresses;
        private ArrayList<String> mCcAddresses;
        private CharSequence mChooserTitle;
        private Intent mIntent;
        private ArrayList<Uri> mStreams;
        private ArrayList<String> mToAddresses;

        private IntentBuilder(Activity activity) {
            this.mActivity = activity;
            this.mIntent = new Intent().setAction("android.intent.action.SEND");
            this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_PACKAGE, activity.getPackageName());
            this.mIntent.putExtra(ShareCompat.EXTRA_CALLING_ACTIVITY, (Parcelable)activity.getComponentName());
            this.mIntent.addFlags(524288);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void combineArrayExtra(String string2, ArrayList<String> arrayList) {
            String[] arrstring = this.mIntent.getStringArrayExtra(string2);
            int n = arrstring != null ? arrstring.length : 0;
            String[] arrstring2 = new String[arrayList.size() + n];
            arrayList.toArray(arrstring2);
            if (arrstring != null) {
                System.arraycopy(arrstring, 0, arrstring2, arrayList.size(), n);
            }
            this.mIntent.putExtra(string2, arrstring2);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void combineArrayExtra(String string2, String[] arrstring) {
            Intent intent = this.getIntent();
            String[] arrstring2 = intent.getStringArrayExtra(string2);
            int n = arrstring2 != null ? arrstring2.length : 0;
            String[] arrstring3 = new String[arrstring.length + n];
            if (arrstring2 != null) {
                System.arraycopy(arrstring2, 0, arrstring3, 0, n);
            }
            System.arraycopy(arrstring, 0, arrstring3, n, arrstring.length);
            intent.putExtra(string2, arrstring3);
        }

        public static IntentBuilder from(Activity activity) {
            return new IntentBuilder(activity);
        }

        public IntentBuilder addEmailBcc(String string2) {
            if (this.mBccAddresses == null) {
                this.mBccAddresses = new ArrayList();
            }
            this.mBccAddresses.add(string2);
            return this;
        }

        public IntentBuilder addEmailBcc(String[] arrstring) {
            this.combineArrayExtra("android.intent.extra.BCC", arrstring);
            return this;
        }

        public IntentBuilder addEmailCc(String string2) {
            if (this.mCcAddresses == null) {
                this.mCcAddresses = new ArrayList();
            }
            this.mCcAddresses.add(string2);
            return this;
        }

        public IntentBuilder addEmailCc(String[] arrstring) {
            this.combineArrayExtra("android.intent.extra.CC", arrstring);
            return this;
        }

        public IntentBuilder addEmailTo(String string2) {
            if (this.mToAddresses == null) {
                this.mToAddresses = new ArrayList();
            }
            this.mToAddresses.add(string2);
            return this;
        }

        public IntentBuilder addEmailTo(String[] arrstring) {
            this.combineArrayExtra("android.intent.extra.EMAIL", arrstring);
            return this;
        }

        public IntentBuilder addStream(Uri uri) {
            Uri uri2 = (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            if (this.mStreams == null && uri2 == null) {
                return this.setStream(uri);
            }
            if (this.mStreams == null) {
                this.mStreams = new ArrayList();
            }
            if (uri2 != null) {
                this.mIntent.removeExtra("android.intent.extra.STREAM");
                this.mStreams.add(uri2);
            }
            this.mStreams.add(uri);
            return this;
        }

        public Intent createChooserIntent() {
            return Intent.createChooser((Intent)this.getIntent(), (CharSequence)this.mChooserTitle);
        }

        Activity getActivity() {
            return this.mActivity;
        }

        /*
         * Enabled aggressive block sorting
         */
        public Intent getIntent() {
            boolean bl = true;
            if (this.mToAddresses != null) {
                this.combineArrayExtra("android.intent.extra.EMAIL", this.mToAddresses);
                this.mToAddresses = null;
            }
            if (this.mCcAddresses != null) {
                this.combineArrayExtra("android.intent.extra.CC", this.mCcAddresses);
                this.mCcAddresses = null;
            }
            if (this.mBccAddresses != null) {
                this.combineArrayExtra("android.intent.extra.BCC", this.mBccAddresses);
                this.mBccAddresses = null;
            }
            if (this.mStreams == null || this.mStreams.size() <= 1) {
                bl = false;
            }
            boolean bl2 = this.mIntent.getAction().equals("android.intent.action.SEND_MULTIPLE");
            if (!bl && bl2) {
                this.mIntent.setAction("android.intent.action.SEND");
                if (this.mStreams != null && !this.mStreams.isEmpty()) {
                    this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)this.mStreams.get(0));
                } else {
                    this.mIntent.removeExtra("android.intent.extra.STREAM");
                }
                this.mStreams = null;
            }
            if (!bl) return this.mIntent;
            if (bl2) return this.mIntent;
            this.mIntent.setAction("android.intent.action.SEND_MULTIPLE");
            if (this.mStreams != null && !this.mStreams.isEmpty()) {
                this.mIntent.putParcelableArrayListExtra("android.intent.extra.STREAM", this.mStreams);
                return this.mIntent;
            }
            this.mIntent.removeExtra("android.intent.extra.STREAM");
            return this.mIntent;
        }

        public IntentBuilder setChooserTitle(int n) {
            return this.setChooserTitle(this.mActivity.getText(n));
        }

        public IntentBuilder setChooserTitle(CharSequence charSequence) {
            this.mChooserTitle = charSequence;
            return this;
        }

        public IntentBuilder setEmailBcc(String[] arrstring) {
            this.mIntent.putExtra("android.intent.extra.BCC", arrstring);
            return this;
        }

        public IntentBuilder setEmailCc(String[] arrstring) {
            this.mIntent.putExtra("android.intent.extra.CC", arrstring);
            return this;
        }

        public IntentBuilder setEmailTo(String[] arrstring) {
            if (this.mToAddresses != null) {
                this.mToAddresses = null;
            }
            this.mIntent.putExtra("android.intent.extra.EMAIL", arrstring);
            return this;
        }

        public IntentBuilder setHtmlText(String string2) {
            this.mIntent.putExtra("android.intent.extra.HTML_TEXT", string2);
            if (!this.mIntent.hasExtra("android.intent.extra.TEXT")) {
                this.setText((CharSequence)Html.fromHtml((String)string2));
            }
            return this;
        }

        public IntentBuilder setStream(Uri uri) {
            if (!this.mIntent.getAction().equals("android.intent.action.SEND")) {
                this.mIntent.setAction("android.intent.action.SEND");
            }
            this.mStreams = null;
            this.mIntent.putExtra("android.intent.extra.STREAM", (Parcelable)uri);
            return this;
        }

        public IntentBuilder setSubject(String string2) {
            this.mIntent.putExtra("android.intent.extra.SUBJECT", string2);
            return this;
        }

        public IntentBuilder setText(CharSequence charSequence) {
            this.mIntent.putExtra("android.intent.extra.TEXT", charSequence);
            return this;
        }

        public IntentBuilder setType(String string2) {
            this.mIntent.setType(string2);
            return this;
        }

        public void startChooser() {
            this.mActivity.startActivity(this.createChooserIntent());
        }
    }

    public static class IntentReader {
        private static final String TAG = "IntentReader";
        private Activity mActivity;
        private ComponentName mCallingActivity;
        private String mCallingPackage;
        private Intent mIntent;
        private ArrayList<Uri> mStreams;

        private IntentReader(Activity activity) {
            this.mActivity = activity;
            this.mIntent = activity.getIntent();
            this.mCallingPackage = ShareCompat.getCallingPackage(activity);
            this.mCallingActivity = ShareCompat.getCallingActivity(activity);
        }

        public static IntentReader from(Activity activity) {
            return new IntentReader(activity);
        }

        /*
         * Enabled aggressive block sorting
         */
        private static void withinStyle(StringBuilder stringBuilder, CharSequence charSequence, int n, int n2) {
            while (n < n2) {
                char c = charSequence.charAt(n);
                if (c == '<') {
                    stringBuilder.append("&lt;");
                } else if (c == '>') {
                    stringBuilder.append("&gt;");
                } else if (c == '&') {
                    stringBuilder.append("&amp;");
                } else if (c > '~' || c < ' ') {
                    stringBuilder.append("&#" + c + ";");
                } else if (c == ' ') {
                    while (n + 1 < n2 && charSequence.charAt(n + 1) == ' ') {
                        stringBuilder.append("&nbsp;");
                        ++n;
                    }
                    stringBuilder.append(' ');
                } else {
                    stringBuilder.append(c);
                }
                ++n;
            }
            return;
        }

        public ComponentName getCallingActivity() {
            return this.mCallingActivity;
        }

        public Drawable getCallingActivityIcon() {
            if (this.mCallingActivity == null) {
                return null;
            }
            PackageManager packageManager = this.mActivity.getPackageManager();
            try {
                packageManager = packageManager.getActivityIcon(this.mCallingActivity);
                return packageManager;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.e((String)TAG, (String)"Could not retrieve icon for calling activity", (Throwable)nameNotFoundException);
                return null;
            }
        }

        public Drawable getCallingApplicationIcon() {
            if (this.mCallingPackage == null) {
                return null;
            }
            PackageManager packageManager = this.mActivity.getPackageManager();
            try {
                packageManager = packageManager.getApplicationIcon(this.mCallingPackage);
                return packageManager;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.e((String)TAG, (String)"Could not retrieve icon for calling application", (Throwable)nameNotFoundException);
                return null;
            }
        }

        public CharSequence getCallingApplicationLabel() {
            if (this.mCallingPackage == null) {
                return null;
            }
            Object object = this.mActivity.getPackageManager();
            try {
                object = object.getApplicationLabel(object.getApplicationInfo(this.mCallingPackage, 0));
                return object;
            }
            catch (PackageManager.NameNotFoundException nameNotFoundException) {
                Log.e((String)TAG, (String)"Could not retrieve label for calling application", (Throwable)nameNotFoundException);
                return null;
            }
        }

        public String getCallingPackage() {
            return this.mCallingPackage;
        }

        public String[] getEmailBcc() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.BCC");
        }

        public String[] getEmailCc() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.CC");
        }

        public String[] getEmailTo() {
            return this.mIntent.getStringArrayExtra("android.intent.extra.EMAIL");
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public String getHtmlText() {
            String string2 = this.mIntent.getStringExtra("android.intent.extra.HTML_TEXT");
            CharSequence charSequence = string2;
            if (string2 != null) return charSequence;
            CharSequence charSequence2 = this.getText();
            if (charSequence2 instanceof Spanned) {
                return Html.toHtml((Spanned)((Spanned)charSequence2));
            }
            charSequence = string2;
            if (charSequence2 == null) return charSequence;
            if (Build.VERSION.SDK_INT >= 16) {
                return Html.escapeHtml((CharSequence)charSequence2);
            }
            charSequence = new StringBuilder();
            IntentReader.withinStyle((StringBuilder)charSequence, charSequence2, 0, charSequence2.length());
            return ((StringBuilder)charSequence).toString();
        }

        public Uri getStream() {
            return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
        }

        public Uri getStream(int n) {
            if (this.mStreams == null && this.isMultipleShare()) {
                this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            if (this.mStreams != null) {
                return this.mStreams.get(n);
            }
            if (n == 0) {
                return (Uri)this.mIntent.getParcelableExtra("android.intent.extra.STREAM");
            }
            throw new IndexOutOfBoundsException("Stream items available: " + this.getStreamCount() + " index requested: " + n);
        }

        public int getStreamCount() {
            if (this.mStreams == null && this.isMultipleShare()) {
                this.mStreams = this.mIntent.getParcelableArrayListExtra("android.intent.extra.STREAM");
            }
            if (this.mStreams != null) {
                return this.mStreams.size();
            }
            if (this.mIntent.hasExtra("android.intent.extra.STREAM")) {
                return 1;
            }
            return 0;
        }

        public String getSubject() {
            return this.mIntent.getStringExtra("android.intent.extra.SUBJECT");
        }

        public CharSequence getText() {
            return this.mIntent.getCharSequenceExtra("android.intent.extra.TEXT");
        }

        public String getType() {
            return this.mIntent.getType();
        }

        public boolean isMultipleShare() {
            return "android.intent.action.SEND_MULTIPLE".equals(this.mIntent.getAction());
        }

        public boolean isShareIntent() {
            String string2 = this.mIntent.getAction();
            return "android.intent.action.SEND".equals(string2) || "android.intent.action.SEND_MULTIPLE".equals(string2);
        }

        public boolean isSingleShare() {
            return "android.intent.action.SEND".equals(this.mIntent.getAction());
        }
    }

}

