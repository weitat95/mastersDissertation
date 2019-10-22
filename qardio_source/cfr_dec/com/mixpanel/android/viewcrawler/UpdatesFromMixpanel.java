/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONArray
 */
package com.mixpanel.android.viewcrawler;

import org.json.JSONArray;

public interface UpdatesFromMixpanel {
    public void applyPersistedUpdates();

    public void setEventBindings(JSONArray var1);

    public void setVariants(JSONArray var1);

    public void startUpdates();

    public void storeVariants(JSONArray var1);
}

