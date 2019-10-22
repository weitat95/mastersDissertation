/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Handler
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewTreeObserver
 *  android.view.ViewTreeObserver$OnScrollChangedListener
 *  android.widget.AbsListView
 *  android.widget.TextView
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.indexing;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.AbsListView;
import android.widget.TextView;
import io.branch.indexing.ContentDiscoveryManifest;
import io.branch.referral.PrefHelper;
import java.lang.ref.WeakReference;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ContentDiscoverer {
    private static ContentDiscoverer thisInstance_;
    private ContentDiscoveryManifest cdManifest_;
    private JSONObject contentEvent_;
    private ArrayList<String> discoveredViewList_ = new ArrayList();
    private int discoveryRepeatCnt_;
    private Handler handler_;
    private final HashHelper hashHelper_;
    private WeakReference<Activity> lastActivityReference_;
    private int maxDiscoveryRepeatCnt = 15;
    private Runnable readContentRunnable = new Runnable(){

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            try {
                ContentDiscoverer.access$008(ContentDiscoverer.this);
                if (!ContentDiscoverer.this.cdManifest_.isCDEnabled() || ContentDiscoverer.this.lastActivityReference_ == null || ContentDiscoverer.this.lastActivityReference_.get() == null) return;
                {
                    Activity activity = (Activity)ContentDiscoverer.this.lastActivityReference_.get();
                    ContentDiscoverer.this.contentEvent_ = new JSONObject();
                    ContentDiscoverer.this.contentEvent_.put("ts", System.currentTimeMillis());
                    if (!TextUtils.isEmpty((CharSequence)ContentDiscoverer.this.referredUrl_)) {
                        ContentDiscoverer.this.contentEvent_.put("rl", (Object)ContentDiscoverer.this.referredUrl_);
                    }
                    String string2 = "/" + activity.getClass().getSimpleName();
                    ContentDiscoverer.this.contentEvent_.put("v", (Object)string2);
                    ViewGroup viewGroup = (ViewGroup)activity.findViewById(16908290);
                    if (viewGroup == null) return;
                    {
                        ContentDiscoveryManifest.CDPathProperties cDPathProperties = ContentDiscoverer.this.cdManifest_.getCDPathProperties(activity);
                        boolean bl = cDPathProperties != null && cDPathProperties.isClearTextRequested();
                        JSONObject jSONObject = null;
                        if (cDPathProperties != null) {
                            boolean bl2 = cDPathProperties.isClearTextRequested();
                            jSONObject = ContentDiscoverer.this.contentEvent_;
                            bl = !bl2;
                            jSONObject.put("h", bl);
                            jSONObject = cDPathProperties.getFilteredElements();
                            bl = bl2;
                        }
                        if (jSONObject != null && jSONObject.length() > 0) {
                            viewGroup = new JSONArray();
                            ContentDiscoverer.this.contentEvent_.put("ck", (Object)viewGroup);
                            cDPathProperties = new JSONArray();
                            ContentDiscoverer.this.contentEvent_.put("cd", (Object)cDPathProperties);
                            ContentDiscoverer.this.discoverContentData((JSONArray)jSONObject, (JSONArray)cDPathProperties, (JSONArray)viewGroup, activity, bl);
                        } else if (!ContentDiscoverer.this.discoveredViewList_.contains(string2)) {
                            cDPathProperties = new JSONArray();
                            ContentDiscoverer.this.contentEvent_.put("ck", (Object)cDPathProperties);
                            ContentDiscoverer.this.discoverContentKeys(viewGroup, (JSONArray)cDPathProperties, activity.getResources());
                        }
                        ContentDiscoverer.this.discoveredViewList_.add(string2);
                        PrefHelper.getInstance((Context)activity).saveBranchAnalyticsData(ContentDiscoverer.this.contentEvent_);
                        int n = ContentDiscoverer.this.cdManifest_.getCDPathProperties(activity).getDiscoveryRepeatInterval();
                        ContentDiscoverer.this.maxDiscoveryRepeatCnt = ContentDiscoverer.this.cdManifest_.getCDPathProperties(activity).getMaxDiscoveryRepeatNumber();
                        if (ContentDiscoverer.this.discoveryRepeatCnt_ >= ContentDiscoverer.this.maxDiscoveryRepeatCnt || n < 500 || jSONObject == null || jSONObject.length() <= 0) return;
                        {
                            ContentDiscoverer.this.handler_.postDelayed(ContentDiscoverer.this.readContentRunnable, (long)n);
                            return;
                        }
                    }
                }
            }
            catch (Exception exception) {
                // empty catch block
            }
        }
    };
    private Runnable readListRunnable;
    private String referredUrl_;
    private ViewTreeObserver.OnScrollChangedListener scrollChangedListener = new ViewTreeObserver.OnScrollChangedListener(){

        public void onScrollChanged() {
            ContentDiscoverer.this.handler_.removeCallbacks(ContentDiscoverer.this.readListRunnable);
            if (ContentDiscoverer.this.maxDiscoveryRepeatCnt > ContentDiscoverer.this.discoveryRepeatCnt_) {
                ContentDiscoverer.this.handler_.postDelayed(ContentDiscoverer.this.readListRunnable, 1500L);
            }
        }
    };
    private final Map<String, WeakReference<ViewTreeObserver>> viewTreeObserverMap;

    private ContentDiscoverer() {
        this.readListRunnable = new Runnable(){

            @Override
            public void run() {
                ContentDiscoverer.this.readContentRunnable.run();
            }
        };
        this.handler_ = new Handler();
        this.hashHelper_ = new HashHelper();
        this.viewTreeObserverMap = new HashMap<String, WeakReference<ViewTreeObserver>>();
    }

    static /* synthetic */ int access$008(ContentDiscoverer contentDiscoverer) {
        int n = contentDiscoverer.discoveryRepeatCnt_;
        contentDiscoverer.discoveryRepeatCnt_ = n + 1;
        return n;
    }

    private void discoverContent(Activity activity) {
        this.discoveryRepeatCnt_ = 0;
        if (this.discoveredViewList_.size() < this.cdManifest_.getMaxViewHistorySize()) {
            this.handler_.removeCallbacks(this.readContentRunnable);
            this.lastActivityReference_ = new WeakReference<Activity>(activity);
            this.handler_.postDelayed(this.readContentRunnable, 1000L);
        }
    }

    private void discoverContentData(JSONArray jSONArray, JSONArray jSONArray2, JSONArray jSONArray3, Activity activity, boolean bl) {
        int n = 0;
        do {
            block6: {
                block5: {
                    try {
                        if (n >= jSONArray.length()) break block5;
                        String string2 = jSONArray.getString(n);
                        if (string2.startsWith("$")) {
                            this.discoverListViewContentData(string2, activity, bl, jSONArray2, jSONArray3);
                        } else {
                            this.updateElementData(string2, activity.findViewById(activity.getResources().getIdentifier(jSONArray.getString(n), "id", activity.getPackageName())), bl, jSONArray2, jSONArray3);
                        }
                        break block6;
                    }
                    catch (JSONException jSONException) {
                        // empty catch block
                    }
                }
                return;
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void discoverContentKeys(ViewGroup viewGroup, JSONArray jSONArray, Resources resources) {
        int n = 0;
        while (n < viewGroup.getChildCount()) {
            View view = viewGroup.getChildAt(n);
            if (view.getVisibility() == 0) {
                if (view instanceof AbsListView || view.getClass().getSimpleName().equals("RecyclerView")) {
                    this.discoverListViewContentKeys((ViewGroup)view, resources, jSONArray);
                } else if (view instanceof ViewGroup) {
                    this.discoverContentKeys((ViewGroup)view, jSONArray, resources);
                } else if (view instanceof TextView) {
                    jSONArray.put((Object)this.getViewName(view, resources));
                }
            }
            ++n;
        }
        return;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void discoverListViewContentData(String string2, Activity activity, boolean bl, JSONArray object, JSONArray object2) {
        int[] arrn;
        int n;
        JSONObject jSONObject;
        JSONObject jSONObject2 = new JSONObject();
        object2.put((Object)string2);
        object.put((Object)jSONObject2);
        object2 = string2.replace("$", "");
        try {
            jSONObject = new JSONObject((String)object2);
            if (jSONObject.length() <= 0) return;
            arrn = (int[])jSONObject.keys().next();
            n = activity.getResources().getIdentifier((String)arrn, "id", activity.getPackageName());
            string2 = activity.getCurrentFocus() != null ? activity.getCurrentFocus().findViewById(n) : null;
            object = string2;
            if (string2 == null) {
                object = activity.findViewById(n);
            }
            if (object == null) return;
            if (!(object instanceof ViewGroup)) return;
            string2 = (ViewGroup)object;
            object = jSONObject.getJSONArray((String)arrn);
            arrn = new int[object.length()];
            for (n = 0; n < object.length(); ++n) {
                arrn[n] = activity.getResources().getIdentifier(object.getString(n), "id", activity.getPackageName());
            }
            n = string2 instanceof AbsListView ? ((AbsListView)string2).getFirstVisiblePosition() : 0;
        }
        catch (JSONException jSONException) {
            jSONException.printStackTrace();
            return;
        }
        int n2 = 0;
        do {
            block7: {
                block8: {
                    block6: {
                        if (n2 >= string2.getChildCount()) break block6;
                        if (string2.getChildAt(n2) == null) break block7;
                        activity = new JSONObject();
                        jSONObject2.put("" + (n2 + n), (Object)activity);
                        break block8;
                    }
                    if (!jSONObject.has("bnc_esw")) return;
                    if (!jSONObject.getBoolean("bnc_esw")) return;
                    n = 1;
                    if (n == 0) return;
                    if (this.viewTreeObserverMap.containsKey(object2)) return;
                    string2.getViewTreeObserver().addOnScrollChangedListener(this.scrollChangedListener);
                    this.viewTreeObserverMap.put((String)object2, new WeakReference<ViewTreeObserver>(string2.getViewTreeObserver()));
                    return;
                }
                for (int i = 0; i < arrn.length; ++i) {
                    View view;
                    if (string2.getChildAt(n2) == null || !((view = string2.getChildAt(n2).findViewById(arrn[i])) instanceof TextView)) continue;
                    activity.put(object.getString(i), (Object)this.getTextViewValue(view, bl));
                }
            }
            ++n2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void discoverListViewContentKeys(ViewGroup viewGroup, Resources resources, JSONArray jSONArray) {
        int n = 1;
        JSONObject jSONObject = new JSONObject();
        if (viewGroup != null && viewGroup.getChildCount() > -1) {
            View view;
            if (viewGroup.getChildCount() <= 1) {
                n = 0;
            }
            if ((view = viewGroup.getChildAt(n)) != null) {
                JSONArray jSONArray2 = new JSONArray();
                try {
                    jSONObject.put(this.getViewName((View)viewGroup, resources), (Object)jSONArray2);
                }
                catch (JSONException jSONException) {
                    jSONException.printStackTrace();
                }
                if (view instanceof ViewGroup) {
                    this.discoverContentKeys((ViewGroup)view, jSONArray2, resources);
                } else if (view instanceof TextView) {
                    jSONArray2.put((Object)this.getViewName(view, resources));
                }
                if (jSONObject.length() > 0) {
                    jSONArray.put((Object)("$" + (Object)jSONObject));
                }
            }
        }
    }

    public static ContentDiscoverer getInstance() {
        if (thisInstance_ == null) {
            thisInstance_ = new ContentDiscoverer();
        }
        return thisInstance_;
    }

    private String getTextViewValue(View object, boolean bl) {
        block3: {
            block2: {
                Object var3_3 = null;
                TextView textView = (TextView)object;
                object = var3_3;
                if (textView.getText() == null) break block2;
                object = textView.getText().toString().substring(0, Math.min(textView.getText().toString().length(), this.cdManifest_.getMaxTextLen()));
                if (!bl) break block3;
            }
            return object;
        }
        return this.hashHelper_.hashContent((String)object);
    }

    private String getViewName(View object, Resources resources) {
        int n = object.getId();
        try {
            object = resources.getResourceEntryName(object.getId());
            return object;
        }
        catch (Exception exception) {
            return String.valueOf(n);
        }
    }

    private void updateElementData(String string2, View view, boolean bl, JSONArray jSONArray, JSONArray jSONArray2) {
        if (view instanceof TextView) {
            jSONArray.put((Object)this.getTextViewValue(view, bl));
            jSONArray2.put((Object)string2);
        }
    }

    private void updateLastViewTimeStampIfNeeded() {
        try {
            if (this.contentEvent_ != null) {
                this.contentEvent_.put("tc", System.currentTimeMillis());
            }
            return;
        }
        catch (JSONException jSONException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void discoverContent(Activity activity, String object) {
        this.cdManifest_ = ContentDiscoveryManifest.getInstance((Context)activity);
        this.referredUrl_ = object;
        object = this.cdManifest_.getCDPathProperties(activity);
        if (object != null) {
            if (((ContentDiscoveryManifest.CDPathProperties)object).isSkipContentDiscovery()) return;
            {
                this.discoverContent(activity);
                return;
            }
        } else {
            if (TextUtils.isEmpty((CharSequence)this.referredUrl_)) return;
            {
                this.discoverContent(activity);
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public JSONObject getContentDiscoverDataForCloseRequest(Context context) {
        JSONObject jSONObject = null;
        JSONObject jSONObject2 = PrefHelper.getInstance(context).getBranchAnalyticsData();
        JSONObject jSONObject3 = jSONObject;
        if (jSONObject2.length() > 0) {
            jSONObject3 = jSONObject;
            if (jSONObject2.toString().length() < this.cdManifest_.getMaxPacketSize()) {
                jSONObject = new JSONObject();
                try {
                    jSONObject.put("mv", (Object)ContentDiscoveryManifest.getInstance(context).getManifestVersion()).put("e", (Object)jSONObject2);
                    jSONObject3 = jSONObject;
                    if (context != null) {
                        jSONObject.put("p", (Object)context.getPackageName());
                        jSONObject.put("p", (Object)context.getPackageName());
                        jSONObject3 = jSONObject;
                    }
                }
                catch (JSONException jSONException) {
                    jSONException.printStackTrace();
                    jSONObject3 = jSONObject;
                }
            }
        }
        PrefHelper.getInstance(context).clearBranchAnalyticsData();
        return jSONObject3;
    }

    public void onActivityStopped(Activity object) {
        if (this.lastActivityReference_ != null && this.lastActivityReference_.get() != null && ((Activity)this.lastActivityReference_.get()).getClass().getName().equals(object.getClass().getName())) {
            this.handler_.removeCallbacks(this.readContentRunnable);
            this.lastActivityReference_ = null;
        }
        this.updateLastViewTimeStampIfNeeded();
        object = this.viewTreeObserverMap.values().iterator();
        while (object.hasNext()) {
            ViewTreeObserver viewTreeObserver = (ViewTreeObserver)((WeakReference)object.next()).get();
            if (viewTreeObserver == null) continue;
            viewTreeObserver.removeOnScrollChangedListener(this.scrollChangedListener);
        }
        this.viewTreeObserverMap.clear();
    }

    public void onSessionStarted(Activity activity, String string2) {
        this.discoveredViewList_ = new ArrayList();
        this.discoverContent(activity, string2);
    }

    private class HashHelper {
        MessageDigest messageDigest_;

        HashHelper() {
            try {
                this.messageDigest_ = MessageDigest.getInstance("MD5");
                return;
            }
            catch (NoSuchAlgorithmException noSuchAlgorithmException) {
                return;
            }
        }

        String hashContent(String string2) {
            String string3 = "";
            if (this.messageDigest_ != null) {
                this.messageDigest_.reset();
                this.messageDigest_.update(string2.getBytes());
                string3 = new String(this.messageDigest_.digest());
            }
            return string3;
        }
    }

}

