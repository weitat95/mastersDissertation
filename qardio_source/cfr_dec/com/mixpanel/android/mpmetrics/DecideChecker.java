/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.Point
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.view.Display
 *  android.view.WindowManager
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.view.Display;
import android.view.WindowManager;
import com.mixpanel.android.mpmetrics.BadDecideObjectException;
import com.mixpanel.android.mpmetrics.DecideMessages;
import com.mixpanel.android.mpmetrics.InAppNotification;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.mpmetrics.MiniInAppNotification;
import com.mixpanel.android.mpmetrics.SystemInformation;
import com.mixpanel.android.mpmetrics.TakeoverInAppNotification;
import com.mixpanel.android.util.ImageStore;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.OfflineMode;
import com.mixpanel.android.util.RemoteService;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.net.ssl.SSLSocketFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class DecideChecker {
    private static final JSONArray EMPTY_JSON_ARRAY = new JSONArray();
    private final Map<String, DecideMessages> mChecks;
    private final MPConfig mConfig;
    private final Context mContext;
    private final ImageStore mImageStore;
    private final SystemInformation mSystemInformation;

    public DecideChecker(Context context, MPConfig mPConfig) {
        this.mContext = context;
        this.mConfig = mPConfig;
        this.mChecks = new HashMap<String, DecideMessages>();
        this.mImageStore = this.createImageStore(context);
        this.mSystemInformation = SystemInformation.getInstance(context);
    }

    private static byte[] checkDecide(RemoteService arrby, Context context, String string2) throws RemoteService.ServiceUnavailableException {
        MPConfig mPConfig = MPConfig.getInstance(context);
        if (!arrby.isOnline(context, mPConfig.getOfflineMode())) {
            return null;
        }
        try {
            arrby = arrby.performRequest(string2, null, mPConfig.getSSLSocketFactory());
            return arrby;
        }
        catch (MalformedURLException malformedURLException) {
            MPLog.e("MixpanelAPI.DChecker", "Cannot interpret " + string2 + " as a URL.", malformedURLException);
            return null;
        }
        catch (FileNotFoundException fileNotFoundException) {
            MPLog.v("MixpanelAPI.DChecker", "Cannot get " + string2 + ", file not found.", fileNotFoundException);
            return null;
        }
        catch (IOException iOException) {
            MPLog.v("MixpanelAPI.DChecker", "Cannot get " + string2 + ".", iOException);
            return null;
        }
        catch (OutOfMemoryError outOfMemoryError) {
            MPLog.e("MixpanelAPI.DChecker", "Out of memory when getting to " + string2 + ".", outOfMemoryError);
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String getDecideResponseFromServer(String object, String charSequence, RemoteService remoteService) throws RemoteService.ServiceUnavailableException {
        String string2;
        try {
            string2 = URLEncoder.encode((String)object, "utf-8");
            object = charSequence != null ? URLEncoder.encode((String)charSequence, "utf-8") : null;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("Mixpanel library requires utf-8 string encoding to be available", unsupportedEncodingException);
        }
        charSequence = new StringBuilder().append("?version=1&lib=android&token=").append(string2);
        if (object != null) {
            ((StringBuilder)charSequence).append("&distinct_id=").append((String)object);
        }
        ((StringBuilder)charSequence).append("&properties=");
        object = new JSONObject();
        try {
            object.putOpt("$android_lib_version", (Object)"5.2.2");
            object.putOpt("$android_app_version", (Object)this.mSystemInformation.getAppVersionName());
            object.putOpt("$android_version", (Object)Build.VERSION.RELEASE);
            object.putOpt("$android_app_release", (Object)this.mSystemInformation.getAppVersionCode());
            object.putOpt("$android_device_model", (Object)Build.MODEL);
            ((StringBuilder)charSequence).append(URLEncoder.encode(object.toString(), "utf-8"));
        }
        catch (Exception exception) {
            MPLog.e("MixpanelAPI.DChecker", "Exception constructing properties JSON", exception.getCause());
        }
        object = ((StringBuilder)charSequence).toString();
        object = this.mConfig.getDecideEndpoint() + (String)object;
        MPLog.v("MixpanelAPI.DChecker", "Querying decide server, url: " + (String)object);
        object = DecideChecker.checkDecide(remoteService, this.mContext, (String)object);
        if (object == null) {
            return null;
        }
        try {
            return new String((byte[])object, "UTF-8");
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException("UTF not supported on this platform?", unsupportedEncodingException);
        }
    }

    @SuppressLint(value={"NewApi"})
    private static int getDisplayWidth(Display display) {
        if (Build.VERSION.SDK_INT < 13) {
            return display.getWidth();
        }
        Point point = new Point();
        display.getSize(point);
        return point.x;
    }

    private Bitmap getNotificationImage(InAppNotification object, Context arrstring) throws RemoteService.ServiceUnavailableException {
        int n = 0;
        String[] arrstring2 = new Bitmap[]{((InAppNotification)object).getImage2xUrl(), ((InAppNotification)object).getImageUrl()};
        int n2 = DecideChecker.getDisplayWidth(((WindowManager)arrstring.getSystemService("window")).getDefaultDisplay());
        arrstring = arrstring2;
        if (((InAppNotification)object).getType() == InAppNotification.Type.TAKEOVER) {
            arrstring = arrstring2;
            if (n2 >= 720) {
                arrstring = new String[]{((InAppNotification)object).getImage4xUrl(), ((InAppNotification)object).getImage2xUrl(), ((InAppNotification)object).getImageUrl()};
            }
        }
        n2 = arrstring.length;
        while (n < n2) {
            object = arrstring[n];
            try {
                arrstring2 = this.mImageStore.getImage((String)object);
                return arrstring2;
            }
            catch (ImageStore.CantGetImageException cantGetImageException) {
                MPLog.v("MixpanelAPI.DChecker", "Can't load image " + (String)object + " for a notification", cantGetImageException);
                ++n;
            }
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    static Result parseDecideResponse(String string2) throws UnintelligibleMessageException {
        JSONObject jSONObject;
        Result result;
        block23: {
            Object var3_8;
            result = new Result();
            try {
                jSONObject = new JSONObject(string2);
                var3_8 = null;
                string2 = var3_8;
            }
            catch (JSONException jSONException) {
                throw new UnintelligibleMessageException("Mixpanel endpoint returned unparsable result:\n" + string2, jSONException);
            }
            if (!jSONObject.has("notifications")) break block23;
            try {
                string2 = jSONObject.getJSONArray("notifications");
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.DChecker", "Mixpanel endpoint returned non-array JSON for notifications: " + (Object)jSONObject);
                string2 = var3_8;
            }
        }
        if (string2 != null) {
            int n = Math.min(string2.length(), 2);
            for (int i = 0; i < n; ++i) {
                try {
                    JSONObject jSONObject2 = string2.getJSONObject(i);
                    String string3 = jSONObject2.getString("type");
                    if (string3.equalsIgnoreCase("takeover")) {
                        TakeoverInAppNotification takeoverInAppNotification = new TakeoverInAppNotification(jSONObject2);
                        result.notifications.add(takeoverInAppNotification);
                        continue;
                    }
                    if (!string3.equalsIgnoreCase("mini")) continue;
                    MiniInAppNotification miniInAppNotification = new MiniInAppNotification(jSONObject2);
                    result.notifications.add(miniInAppNotification);
                    continue;
                }
                catch (JSONException jSONException) {
                    MPLog.e("MixpanelAPI.DChecker", "Received a strange response from notifications service: " + string2.toString(), jSONException);
                    continue;
                }
                catch (BadDecideObjectException badDecideObjectException) {
                    MPLog.e("MixpanelAPI.DChecker", "Received a strange response from notifications service: " + string2.toString(), badDecideObjectException);
                    continue;
                }
                catch (OutOfMemoryError outOfMemoryError) {
                    MPLog.e("MixpanelAPI.DChecker", "Not enough memory to show load notification from package: " + string2.toString(), outOfMemoryError);
                }
            }
        }
        if (jSONObject.has("event_bindings")) {
            try {
                result.eventBindings = jSONObject.getJSONArray("event_bindings");
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.DChecker", "Mixpanel endpoint returned non-array JSON for event bindings: " + (Object)jSONObject);
            }
        }
        if (jSONObject.has("variants")) {
            try {
                result.variants = jSONObject.getJSONArray("variants");
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.DChecker", "Mixpanel endpoint returned non-array JSON for variants: " + (Object)jSONObject);
            }
        }
        if (jSONObject.has("automatic_events")) {
            try {
                result.automaticEvents = jSONObject.getBoolean("automatic_events");
            }
            catch (JSONException jSONException) {
                MPLog.e("MixpanelAPI.DChecker", "Mixpanel endpoint returned a non boolean value for automatic events: " + (Object)jSONObject);
            }
        }
        if (!jSONObject.has("integrations")) return result;
        try {
            result.integrations = jSONObject.getJSONArray("integrations");
            return result;
        }
        catch (JSONException jSONException) {
            MPLog.e("MixpanelAPI.DChecker", "Mixpanel endpoint returned a non-array JSON for integrations: " + (Object)jSONObject);
            return result;
        }
    }

    private Result runDecideCheck(String object, String object2, RemoteService object3) throws RemoteService.ServiceUnavailableException, UnintelligibleMessageException {
        object2 = this.getDecideResponseFromServer((String)object, (String)object2, (RemoteService)object3);
        MPLog.v("MixpanelAPI.DChecker", "Mixpanel decide server response was:\n" + (String)object2);
        object = null;
        if (object2 != null) {
            object2 = DecideChecker.parseDecideResponse((String)object2);
            object3 = ((Result)object2).notifications.iterator();
            do {
                object = object2;
                if (!object3.hasNext()) break;
                object = (InAppNotification)object3.next();
                Bitmap bitmap = this.getNotificationImage((InAppNotification)object, this.mContext);
                if (bitmap == null) {
                    MPLog.i("MixpanelAPI.DChecker", "Could not retrieve image for notification " + ((InAppNotification)object).getId() + ", will not show the notification.");
                    object3.remove();
                    continue;
                }
                ((InAppNotification)object).setImage(bitmap);
            } while (true);
        }
        return object;
    }

    public void addDecideCheck(DecideMessages decideMessages) {
        this.mChecks.put(decideMessages.getToken(), decideMessages);
    }

    protected ImageStore createImageStore(Context context) {
        return new ImageStore(context, "DecideChecker");
    }

    public DecideMessages getDecideMessages(String string2) {
        return this.mChecks.get(string2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public void runDecideCheck(String object, RemoteService object2) throws RemoteService.ServiceUnavailableException {
        if ((object = this.mChecks.get(object)) == null) return;
        String string2 = ((DecideMessages)object).getDistinctId();
        try {
            object2 = this.runDecideCheck(((DecideMessages)object).getToken(), string2, (RemoteService)object2);
            if (object2 == null) return;
        }
        catch (UnintelligibleMessageException unintelligibleMessageException) {
            MPLog.e("MixpanelAPI.DChecker", unintelligibleMessageException.getMessage(), unintelligibleMessageException);
            return;
        }
        ((DecideMessages)object).reportResults(((Result)object2).notifications, ((Result)object2).eventBindings, ((Result)object2).variants, ((Result)object2).automaticEvents, ((Result)object2).integrations);
    }

    static class Result {
        public boolean automaticEvents = false;
        public JSONArray eventBindings;
        public JSONArray integrations;
        public final List<InAppNotification> notifications = new ArrayList<InAppNotification>();
        public JSONArray variants;

        public Result() {
            this.eventBindings = EMPTY_JSON_ARRAY;
            this.variants = EMPTY_JSON_ARRAY;
        }
    }

    static class UnintelligibleMessageException
    extends Exception {
        public UnintelligibleMessageException(String string2, JSONException jSONException) {
            super(string2, jSONException);
        }
    }

}

