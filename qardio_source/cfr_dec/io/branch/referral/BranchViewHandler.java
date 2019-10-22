/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Dialog
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnDismissListener
 *  android.graphics.Bitmap
 *  android.graphics.Paint
 *  android.os.AsyncTask
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.animation.AccelerateInterpolator
 *  android.view.animation.AlphaAnimation
 *  android.view.animation.Animation
 *  android.view.animation.Interpolator
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Interpolator;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;
import io.branch.referral.Branch;
import io.branch.referral.Defines;
import io.branch.referral.PrefHelper;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.lang.ref.WeakReference;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.net.URLConnection;
import org.json.JSONObject;

public class BranchViewHandler {
    private static BranchViewHandler thisInstance_;
    private Dialog branchViewDialog_;
    private boolean isBranchViewAccepted_;
    private boolean isBranchViewDialogShowing_;
    private boolean loadingHtmlInBackGround_ = false;
    private BranchView openOrInstallPendingBranchView_ = null;
    private String parentActivityClassName_;
    private boolean webViewLoadError_;

    private BranchViewHandler() {
    }

    private void createAndShowBranchView(final BranchView branchView, Context context, final IBranchViewEvents iBranchViewEvents) {
        if (context != null && branchView != null) {
            context = new WebView(context);
            context.getSettings().setJavaScriptEnabled(true);
            if (Build.VERSION.SDK_INT >= 19) {
                context.setLayerType(2, null);
            }
            this.webViewLoadError_ = false;
            if (!TextUtils.isEmpty((CharSequence)branchView.webViewHtml_)) {
                context.loadDataWithBaseURL(null, branchView.webViewHtml_, "text/html", "utf-8", null);
                context.setWebViewClient(new WebViewClient((WebView)context){
                    final /* synthetic */ WebView val$webView;
                    {
                        this.val$webView = webView;
                    }

                    public void onPageFinished(WebView webView, String string2) {
                        super.onPageFinished(webView, string2);
                        BranchViewHandler.this.openBranchViewDialog(branchView, iBranchViewEvents, this.val$webView);
                    }

                    public void onPageStarted(WebView webView, String string2, Bitmap bitmap) {
                        super.onPageStarted(webView, string2, bitmap);
                    }

                    public void onReceivedError(WebView webView, int n, String string2, String string3) {
                        super.onReceivedError(webView, n, string2, string3);
                        BranchViewHandler.this.webViewLoadError_ = true;
                    }

                    /*
                     * Enabled aggressive block sorting
                     */
                    public boolean shouldOverrideUrlLoading(WebView webView, String string2) {
                        boolean bl = BranchViewHandler.this.handleUserActionRedirect(string2);
                        if (!bl) {
                            webView.loadUrl(string2);
                            return bl;
                        } else {
                            if (BranchViewHandler.this.branchViewDialog_ == null) return bl;
                            {
                                BranchViewHandler.this.branchViewDialog_.dismiss();
                                return bl;
                            }
                        }
                    }
                });
            }
        }
    }

    public static BranchViewHandler getInstance() {
        if (thisInstance_ == null) {
            thisInstance_ = new BranchViewHandler();
        }
        return thisInstance_;
    }

    private boolean handleUserActionRedirect(String object) {
        block4: {
            block5: {
                object = new URI((String)object);
                if (!((URI)object).getScheme().equalsIgnoreCase("branch-cta")) break block4;
                if (!((URI)object).getHost().equalsIgnoreCase("accept")) break block5;
                this.isBranchViewAccepted_ = true;
                return true;
            }
            try {
                if (((URI)object).getHost().equalsIgnoreCase("cancel")) {
                    this.isBranchViewAccepted_ = false;
                    return true;
                }
            }
            catch (URISyntaxException uRISyntaxException) {
                // empty catch block
            }
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private void openBranchViewDialog(final BranchView branchView, final IBranchViewEvents iBranchViewEvents, WebView webView) {
        if (!this.webViewLoadError_ && Branch.getInstance() != null && Branch.getInstance().currentActivityReference_ != null) {
            Activity activity = (Activity)Branch.getInstance().currentActivityReference_.get();
            if (activity == null) return;
            branchView.updateUsageCount(activity.getApplicationContext(), branchView.branchViewID_);
            this.parentActivityClassName_ = activity.getClass().getName();
            RelativeLayout relativeLayout = new RelativeLayout((Context)activity);
            relativeLayout.setVisibility(8);
            relativeLayout.addView((View)webView, (ViewGroup.LayoutParams)new RelativeLayout.LayoutParams(-1, -1));
            relativeLayout.setBackgroundColor(0);
            if (this.branchViewDialog_ != null && this.branchViewDialog_.isShowing()) {
                if (iBranchViewEvents == null) return;
                iBranchViewEvents.onBranchViewError(-200, "Unable to create a Branch view. A Branch view is already showing", branchView.branchViewAction_);
                return;
            }
            this.branchViewDialog_ = new Dialog((Context)activity, 16973834);
            this.branchViewDialog_.setContentView((View)relativeLayout);
            relativeLayout.setVisibility(0);
            webView.setVisibility(0);
            this.branchViewDialog_.show();
            this.showViewWithAlphaAnimation((View)relativeLayout);
            this.showViewWithAlphaAnimation((View)webView);
            this.isBranchViewDialogShowing_ = true;
            if (iBranchViewEvents != null) {
                iBranchViewEvents.onBranchViewVisible(branchView.branchViewAction_, branchView.branchViewID_);
            }
            this.branchViewDialog_.setOnDismissListener(new DialogInterface.OnDismissListener(){

                public void onDismiss(DialogInterface dialogInterface) {
                    block3: {
                        block2: {
                            BranchViewHandler.this.isBranchViewDialogShowing_ = false;
                            BranchViewHandler.this.branchViewDialog_ = null;
                            if (iBranchViewEvents == null) break block2;
                            if (!BranchViewHandler.this.isBranchViewAccepted_) break block3;
                            iBranchViewEvents.onBranchViewAccepted(branchView.branchViewAction_, branchView.branchViewID_);
                        }
                        return;
                    }
                    iBranchViewEvents.onBranchViewCancelled(branchView.branchViewAction_, branchView.branchViewID_);
                }
            });
            return;
        }
        this.isBranchViewDialogShowing_ = false;
        if (iBranchViewEvents == null) return;
        iBranchViewEvents.onBranchViewError(-202, "Unable to create a Branch view due to a temporary network error", branchView.branchViewAction_);
    }

    private boolean showBranchView(BranchView branchView, Context context, IBranchViewEvents iBranchViewEvents) {
        if (this.isBranchViewDialogShowing_ || this.loadingHtmlInBackGround_) {
            if (iBranchViewEvents != null) {
                iBranchViewEvents.onBranchViewError(-200, "Unable to create a Branch view. A Branch view is already showing", branchView.branchViewAction_);
            }
            return false;
        }
        this.isBranchViewDialogShowing_ = false;
        this.isBranchViewAccepted_ = false;
        if (context != null && branchView != null) {
            if (branchView.isAvailable(context)) {
                if (!TextUtils.isEmpty((CharSequence)branchView.webViewHtml_)) {
                    this.createAndShowBranchView(branchView, context, iBranchViewEvents);
                    return true;
                }
                this.loadingHtmlInBackGround_ = true;
                new loadBranchViewTask(branchView, context, iBranchViewEvents).execute((Object[])new Void[0]);
                return true;
            }
            if (iBranchViewEvents != null) {
                iBranchViewEvents.onBranchViewError(-203, "Unable to create this Branch view. Reached maximum usage limit ", branchView.branchViewAction_);
            }
        }
        return false;
    }

    private void showViewWithAlphaAnimation(View view) {
        AlphaAnimation alphaAnimation = new AlphaAnimation(0.1f, 1.0f);
        alphaAnimation.setDuration(500L);
        alphaAnimation.setStartOffset(10L);
        alphaAnimation.setInterpolator((Interpolator)new AccelerateInterpolator());
        alphaAnimation.setFillAfter(true);
        view.setVisibility(0);
        view.startAnimation((Animation)alphaAnimation);
    }

    public boolean isInstallOrOpenBranchViewPending(Context context) {
        return this.openOrInstallPendingBranchView_ != null && this.openOrInstallPendingBranchView_.isAvailable(context);
    }

    public boolean markInstallOrOpenBranchViewPending(JSONObject jSONObject, String string2) {
        boolean bl = false;
        BranchView branchView = new BranchView(jSONObject, string2);
        boolean bl2 = bl;
        if (branchView != null) {
            bl2 = bl;
            if (Branch.getInstance().currentActivityReference_ != null) {
                Activity activity = (Activity)Branch.getInstance().currentActivityReference_.get();
                bl2 = bl;
                if (activity != null) {
                    bl2 = bl;
                    if (branchView.isAvailable((Context)activity)) {
                        this.openOrInstallPendingBranchView_ = new BranchView(jSONObject, string2);
                        bl2 = true;
                    }
                }
            }
        }
        return bl2;
    }

    public void onCurrentActivityDestroyed(Activity activity) {
        if (this.parentActivityClassName_ != null && this.parentActivityClassName_.equalsIgnoreCase(activity.getClass().getName())) {
            this.isBranchViewDialogShowing_ = false;
        }
    }

    public boolean showBranchView(JSONObject jSONObject, String string2, Context context, IBranchViewEvents iBranchViewEvents) {
        return this.showBranchView(new BranchView(jSONObject, string2), context, iBranchViewEvents);
    }

    public boolean showPendingBranchView(Context context) {
        boolean bl = this.showBranchView(this.openOrInstallPendingBranchView_, context, null);
        if (bl) {
            this.openOrInstallPendingBranchView_ = null;
        }
        return bl;
    }

    private class BranchView {
        private String branchViewAction_ = "";
        private String branchViewID_ = "";
        private int num_of_use_ = 1;
        private String webViewHtml_ = "";
        private String webViewUrl_ = "";

        private BranchView(JSONObject jSONObject, String string2) {
            try {
                this.branchViewAction_ = string2;
                if (jSONObject.has(Defines.Jsonkey.BranchViewID.getKey())) {
                    this.branchViewID_ = jSONObject.getString(Defines.Jsonkey.BranchViewID.getKey());
                }
                if (jSONObject.has(Defines.Jsonkey.BranchViewNumOfUse.getKey())) {
                    this.num_of_use_ = jSONObject.getInt(Defines.Jsonkey.BranchViewNumOfUse.getKey());
                }
                if (jSONObject.has(Defines.Jsonkey.BranchViewUrl.getKey())) {
                    this.webViewUrl_ = jSONObject.getString(Defines.Jsonkey.BranchViewUrl.getKey());
                }
                if (jSONObject.has(Defines.Jsonkey.BranchViewHtml.getKey())) {
                    this.webViewHtml_ = jSONObject.getString(Defines.Jsonkey.BranchViewHtml.getKey());
                }
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        private boolean isAvailable(Context context) {
            int n = PrefHelper.getInstance(context).getBranchViewUsageCount(this.branchViewID_);
            return this.num_of_use_ > n || this.num_of_use_ == -1;
        }

        public void updateUsageCount(Context context, String string2) {
            PrefHelper.getInstance(context).updateBranchViewUsageCount(string2);
        }
    }

    public static interface IBranchViewEvents {
        public void onBranchViewAccepted(String var1, String var2);

        public void onBranchViewCancelled(String var1, String var2);

        public void onBranchViewError(int var1, String var2, String var3);

        public void onBranchViewVisible(String var1, String var2);
    }

    private class loadBranchViewTask
    extends AsyncTask<Void, Void, Boolean> {
        private final BranchView branchView;
        private final IBranchViewEvents callback;
        private final Context context;

        public loadBranchViewTask(BranchView branchView, Context context, IBranchViewEvents iBranchViewEvents) {
            this.branchView = branchView;
            this.context = context;
            this.callback = iBranchViewEvents;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        protected Boolean doInBackground(Void ... object) {
            int n;
            boolean bl = false;
            int n2 = n = -1;
            try {
                Object object2 = (HttpURLConnection)new URL(this.branchView.webViewUrl_).openConnection();
                n2 = n;
                ((HttpURLConnection)object2).setRequestMethod("GET");
                n2 = n;
                ((URLConnection)object2).connect();
                n2 = n;
                n2 = n = ((HttpURLConnection)object2).getResponseCode();
                if (n == 200) {
                    n2 = n;
                    object = new ByteArrayOutputStream();
                    n2 = n;
                    object2 = ((URLConnection)object2).getInputStream();
                    n2 = n;
                    byte[] arrby = new byte[1024];
                    do {
                        n2 = n;
                        int n3 = ((InputStream)object2).read(arrby);
                        if (n3 == -1) break;
                        n2 = n;
                        ((ByteArrayOutputStream)object).write(arrby, 0, n3);
                    } while (true);
                    n2 = n;
                    this.branchView.webViewHtml_ = ((ByteArrayOutputStream)object).toString("UTF-8");
                    n2 = n;
                    ((ByteArrayOutputStream)object).close();
                    n2 = n;
                    ((InputStream)object2).close();
                    n2 = n;
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
            if (n2 == 200) {
                bl = true;
            }
            return bl;
        }

        /*
         * Enabled aggressive block sorting
         */
        protected void onPostExecute(Boolean bl) {
            super.onPostExecute((Object)bl);
            if (bl.booleanValue()) {
                BranchViewHandler.this.createAndShowBranchView(this.branchView, this.context, this.callback);
            } else if (this.callback != null) {
                this.callback.onBranchViewError(-202, "Unable to create a Branch view due to a temporary network error", this.branchView.branchViewAction_);
            }
            BranchViewHandler.this.loadingHtmlInBackGround_ = false;
        }
    }

}

