/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.getqardio.android.io.network.response;

import com.getqardio.android.datamodel.Tooltip;
import java.util.ArrayList;
import java.util.List;
import org.json.JSONObject;

public class TooltipsResponse {
    public List<Tooltip> tooltips;

    public TooltipsResponse(int n) {
        this.tooltips = new ArrayList<Tooltip>(n);
    }

    public static Tooltip parse(JSONObject jSONObject) {
        Tooltip tooltip = new Tooltip();
        tooltip.tooltipId = jSONObject.optInt("id", 0);
        tooltip.title = jSONObject.optString("title", "");
        tooltip.text = jSONObject.optString("text", "");
        tooltip.imageUrl = jSONObject.optString("imageUrl", "");
        tooltip.textPosition = jSONObject.optString("textPosition", "");
        return tooltip;
    }
}

