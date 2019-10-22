/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Handler
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.viewcrawler;

import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.viewcrawler.ViewVisitor;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import org.json.JSONException;
import org.json.JSONObject;

class DynamicEventTracker
implements ViewVisitor.OnEventListener {
    private static String LOGTAG = "MixpanelAPI.DynamicEventTracker";
    private final Map<Signature, UnsentEvent> mDebouncedEvents;
    private final Handler mHandler;
    private final MixpanelAPI mMixpanel;
    private final Runnable mTask;

    public DynamicEventTracker(MixpanelAPI mixpanelAPI, Handler handler) {
        this.mMixpanel = mixpanelAPI;
        this.mDebouncedEvents = new HashMap<Signature, UnsentEvent>();
        this.mTask = new SendDebouncedTask();
        this.mHandler = handler;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static String textPropertyFromView(View object) {
        String string2;
        String string3 = null;
        if (object instanceof TextView) {
            object = ((TextView)object).getText();
            string2 = string3;
            if (object == null) return string2;
            return object.toString();
        }
        string2 = string3;
        if (!(object instanceof ViewGroup)) return string2;
        StringBuilder stringBuilder = new StringBuilder();
        object = (ViewGroup)object;
        int n = object.getChildCount();
        boolean bl = false;
        for (int i = 0; i < n && stringBuilder.length() < 128; ++i) {
            string2 = DynamicEventTracker.textPropertyFromView(object.getChildAt(i));
            boolean bl2 = bl;
            if (string2 != null) {
                bl2 = bl;
                if (string2.length() > 0) {
                    if (bl) {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(string2);
                    bl2 = true;
                }
            }
            bl = bl2;
        }
        if (stringBuilder.length() > 128) {
            return stringBuilder.substring(0, 128);
        }
        string2 = string3;
        if (!bl) return string2;
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void OnEvent(View object, String object2, boolean bl) {
        long l = System.currentTimeMillis();
        Object object3 = new JSONObject();
        try {
            object3.put("$text", (Object)DynamicEventTracker.textPropertyFromView(object));
            object3.put("$from_binding", true);
            object3.put("time", l / 1000L);
        }
        catch (JSONException jSONException) {
            MPLog.e(LOGTAG, "Can't format properties from view due to JSON issue", jSONException);
        }
        if (bl) {
            object = new Signature((View)object, (String)object2);
            object3 = new UnsentEvent((String)object2, (JSONObject)object3, l);
            object2 = this.mDebouncedEvents;
            synchronized (object2) {
                bl = this.mDebouncedEvents.isEmpty();
                this.mDebouncedEvents.put((Signature)object, (UnsentEvent)object3);
                if (bl) {
                    this.mHandler.postDelayed(this.mTask, 1000L);
                }
                return;
            }
        }
        this.mMixpanel.track((String)object2, (JSONObject)object3);
    }

    private final class SendDebouncedTask
    implements Runnable {
        private SendDebouncedTask() {
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public void run() {
            long l = System.currentTimeMillis();
            Map map = DynamicEventTracker.this.mDebouncedEvents;
            synchronized (map) {
                Iterator iterator = DynamicEventTracker.this.mDebouncedEvents.entrySet().iterator();
                while (iterator.hasNext()) {
                    UnsentEvent unsentEvent = (UnsentEvent)iterator.next().getValue();
                    if (l - unsentEvent.timeSentMillis <= 1000L) continue;
                    DynamicEventTracker.this.mMixpanel.track(unsentEvent.eventName, unsentEvent.properties);
                    iterator.remove();
                }
                if (!DynamicEventTracker.this.mDebouncedEvents.isEmpty()) {
                    DynamicEventTracker.this.mHandler.postDelayed((Runnable)this, 500L);
                }
                return;
            }
        }
    }

    private static class Signature {
        private final int mHashCode;

        public Signature(View view, String string2) {
            this.mHashCode = view.hashCode() ^ string2.hashCode();
        }

        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = bl = false;
            if (object instanceof Signature) {
                bl2 = bl;
                if (this.mHashCode == object.hashCode()) {
                    bl2 = true;
                }
            }
            return bl2;
        }

        public int hashCode() {
            return this.mHashCode;
        }
    }

    private static class UnsentEvent {
        public final String eventName;
        public final JSONObject properties;
        public final long timeSentMillis;

        public UnsentEvent(String string2, JSONObject jSONObject, long l) {
            this.eventName = string2;
            this.properties = jSONObject;
            this.timeSentMillis = l;
        }
    }

}

