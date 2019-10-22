/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONObject
 */
package com.getqardio.android.util;

import com.getqardio.android.util.BaseUserUtil;
import org.json.JSONObject;

public class ModeUtil {
    public static JSONObject createNormalModeCommand(int n, int n2) {
        return BaseUserUtil.createModeUpdateCommand(4, n, n2);
    }

    public static JSONObject createPregnancyModeCommand(int n, int n2) {
        return BaseUserUtil.createModeUpdateCommand(43, n, n2);
    }

    public static JSONObject createSmartFeedbackModeCommand(int n, int n2) {
        return BaseUserUtil.createModeUpdateCommand(35, n, n2);
    }

    public static JSONObject createWeightOnlyModeCommand(int n, int n2) {
        return BaseUserUtil.createModeUpdateCommand(38, n, n2);
    }
}

