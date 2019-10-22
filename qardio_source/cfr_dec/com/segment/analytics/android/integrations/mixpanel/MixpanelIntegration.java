/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.content.Context
 *  android.os.Bundle
 *  org.json.JSONObject
 */
package com.segment.analytics.android.integrations.mixpanel;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.os.Bundle;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.segment.analytics.Analytics;
import com.segment.analytics.Properties;
import com.segment.analytics.Traits;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.AliasPayload;
import com.segment.analytics.integrations.BasePayload;
import com.segment.analytics.integrations.IdentifyPayload;
import com.segment.analytics.integrations.Integration;
import com.segment.analytics.integrations.Logger;
import com.segment.analytics.integrations.ScreenPayload;
import com.segment.analytics.integrations.TrackPayload;
import com.segment.analytics.internal.Utils;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONObject;

public class MixpanelIntegration
extends Integration<MixpanelAPI> {
    public static final Integration.Factory FACTORY = new Integration.Factory(){

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public Integration<?> create(ValueMap object, Analytics object2) {
            boolean bl = ((ValueMap)object).getBoolean("consolidatedPageCalls", true);
            boolean bl2 = ((ValueMap)object).getBoolean("trackAllPages", false);
            boolean bl3 = ((ValueMap)object).getBoolean("trackCategorizedPages", false);
            boolean bl4 = ((ValueMap)object).getBoolean("trackNamedPages", false);
            boolean bl5 = ((ValueMap)object).getBoolean("people", false);
            String string2 = ((ValueMap)object).getString("token");
            Set set = MixpanelIntegration.getStringSet((ValueMap)object, "increments");
            boolean bl6 = ((ValueMap)object).getBoolean("setAllTraitsByDefault", true);
            Set set2 = MixpanelIntegration.getStringSet((ValueMap)object, "peopleProperties");
            Set set3 = MixpanelIntegration.getStringSet((ValueMap)object, "superProperties");
            Logger logger = ((Analytics)object2).logger("Mixpanel");
            object2 = MixpanelAPI.getInstance((Context)((Analytics)object2).getApplication(), string2);
            logger.verbose("MixpanelAPI.getInstance(context, %s);", string2);
            if (bl5) {
                object = ((MixpanelAPI)object2).getPeople();
                do {
                    return new MixpanelIntegration((MixpanelAPI)object2, (MixpanelAPI.People)object, bl5, bl, bl2, bl3, bl4, string2, logger, set, bl6, set2, set3);
                    break;
                } while (true);
            }
            object = null;
            return new MixpanelIntegration((MixpanelAPI)object2, (MixpanelAPI.People)object, bl5, bl, bl2, bl3, bl4, string2, logger, set, bl6, set2, set3);
        }

        @Override
        public String key() {
            return "Mixpanel";
        }
    };
    private static final Map<String, String> MAPPER;
    private final boolean consolidatedPageCalls;
    final Set<String> increments;
    final boolean isPeopleEnabled;
    private final Logger logger;
    private final MixpanelAPI mixpanel;
    final MixpanelAPI.People mixpanelPeople;
    private final Set<String> peopleProperties;
    final boolean setAllTraitsByDefault;
    private final Set<String> superProperties;
    final String token;
    final boolean trackAllPages;
    final boolean trackCategorizedPages;
    final boolean trackNamedPages;

    static {
        LinkedHashMap<String, String> linkedHashMap = new LinkedHashMap<String, String>();
        linkedHashMap.put("email", "$email");
        linkedHashMap.put("phone", "$phone");
        linkedHashMap.put("firstName", "$first_name");
        linkedHashMap.put("lastName", "$last_name");
        linkedHashMap.put("name", "$name");
        linkedHashMap.put("username", "$username");
        linkedHashMap.put("createdAt", "$created");
        MAPPER = Collections.unmodifiableMap(linkedHashMap);
    }

    public MixpanelIntegration(MixpanelAPI mixpanelAPI, MixpanelAPI.People people, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, String string2, Logger logger, Set<String> set, boolean bl6, Set<String> set2, Set<String> set3) {
        this.mixpanel = mixpanelAPI;
        this.mixpanelPeople = people;
        this.isPeopleEnabled = bl;
        this.consolidatedPageCalls = bl2;
        this.trackAllPages = bl3;
        this.trackCategorizedPages = bl4;
        this.trackNamedPages = bl5;
        this.token = string2;
        this.logger = logger;
        this.increments = set;
        this.setAllTraitsByDefault = bl6;
        this.peopleProperties = set2;
        this.superProperties = set3;
    }

    static <T> Map<String, T> filter(Map<String, T> map, Iterable<String> object) {
        LinkedHashMap<String, T> linkedHashMap = new LinkedHashMap<String, T>();
        object = object.iterator();
        while (object.hasNext()) {
            String string2 = (String)object.next();
            if (!map.containsKey(string2)) continue;
            linkedHashMap.put(string2, map.get(string2));
        }
        return linkedHashMap;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Set<String> getStringSet(ValueMap set, String hashSet) {
        int n;
        List list = (List)((ValueMap)((Object)set)).get(hashSet);
        if (list == null) return Collections.emptySet();
        try {
            if (list.size() == 0) {
                return Collections.emptySet();
            }
            hashSet = new HashSet(list.size());
            n = 0;
        }
        catch (ClassCastException classCastException) {
            return Collections.emptySet();
        }
        do {
            set = hashSet;
            if (n >= list.size()) return set;
            hashSet.add((String)list.get(n));
            ++n;
            continue;
            break;
        } while (true);
    }

    private void registerSuperProperties(Map<String, Object> jSONObject) {
        if (Utils.isNullOrEmpty(jSONObject)) {
            return;
        }
        jSONObject = new ValueMap(Utils.transform(jSONObject, MAPPER)).toJsonObject();
        this.mixpanel.registerSuperProperties(jSONObject);
        this.logger.verbose("mixpanel.registerSuperProperties(%s)", new Object[]{jSONObject});
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setPeopleProperties(Map<String, Object> jSONObject) {
        if (Utils.isNullOrEmpty((Map)jSONObject) || !this.isPeopleEnabled) {
            return;
        }
        jSONObject = new ValueMap(Utils.transform(jSONObject, MAPPER)).toJsonObject();
        this.mixpanelPeople.set(jSONObject);
        this.logger.verbose("mixpanel.getPeople().set(%s)", new Object[]{jSONObject});
    }

    @Override
    public void alias(AliasPayload object) {
        String string2;
        super.alias((AliasPayload)object);
        String string3 = string2 = ((AliasPayload)object).previousId();
        if (string2.equals(((BasePayload)object).anonymousId())) {
            string3 = this.mixpanel.getDistinctId();
        }
        if ((object = ((BasePayload)object).userId()) != null) {
            this.mixpanel.alias((String)object, string3);
            this.logger.verbose("mixpanel.alias(%s, %s)", object, string3);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void event(String string2, Properties properties) {
        double d;
        JSONObject jSONObject = properties.toJsonObject();
        this.mixpanel.track(string2, jSONObject);
        this.logger.verbose("mixpanel.track(%s, %s)", new Object[]{string2, jSONObject});
        if (!this.isPeopleEnabled || (d = properties.revenue()) == 0.0) {
            return;
        }
        this.mixpanelPeople.trackCharge(d, jSONObject);
        this.logger.verbose("mixpanelPeople.trackCharge(%s, %s)", new Object[]{d, jSONObject});
    }

    @Override
    public void flush() {
        super.flush();
        this.mixpanel.flush();
        this.logger.verbose("mixpanel.flush()", new Object[0]);
    }

    @Override
    public void identify(IdentifyPayload valueMap) {
        super.identify((IdentifyPayload)valueMap);
        String string2 = valueMap.userId();
        if (string2 != null) {
            this.mixpanel.identify(string2);
            this.logger.verbose("mixpanel.identify(%s)", string2);
            if (this.isPeopleEnabled) {
                this.mixpanelPeople.identify(string2);
                this.logger.verbose("mixpanel.getPeople().identify(%s)", string2);
            }
        }
        valueMap = valueMap.traits();
        if (this.setAllTraitsByDefault) {
            this.registerSuperProperties(valueMap);
            this.setPeopleProperties(valueMap);
            return;
        }
        this.registerSuperProperties(MixpanelIntegration.filter(valueMap, this.superProperties));
        this.setPeopleProperties(MixpanelIntegration.filter(valueMap, this.peopleProperties));
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle bundle) {
        super.onActivityCreated(activity, bundle);
        MixpanelAPI.getInstance((Context)activity, this.token);
    }

    @Override
    public void reset() {
        super.reset();
        this.mixpanel.reset();
        this.logger.verbose("mixpanel.reset()", new Object[0]);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void screen(ScreenPayload screenPayload) {
        if (this.consolidatedPageCalls) {
            Properties properties = new Properties();
            properties.putAll(screenPayload.properties());
            properties.put("name", (Object)screenPayload.name());
            this.event("Loaded a Screen", properties);
            return;
        } else {
            if (this.trackAllPages) {
                this.event(String.format("Viewed %s Screen", screenPayload.event()), screenPayload.properties());
                return;
            }
            if (this.trackCategorizedPages && !Utils.isNullOrEmpty(screenPayload.category())) {
                this.event(String.format("Viewed %s Screen", screenPayload.category()), screenPayload.properties());
                return;
            }
            if (!this.trackNamedPages || Utils.isNullOrEmpty(screenPayload.name())) return;
            {
                this.event(String.format("Viewed %s Screen", screenPayload.name()), screenPayload.properties());
                return;
            }
        }
    }

    @Override
    public void track(TrackPayload trackPayload) {
        String string2 = trackPayload.event();
        this.event(string2, trackPayload.properties());
        if (this.increments.contains(string2) && this.isPeopleEnabled) {
            this.mixpanelPeople.increment(string2, 1.0);
            this.mixpanelPeople.set("Last " + string2, new Date());
        }
    }

}

