/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.crashlytics.android.core;

import android.content.Context;
import io.fabric.sdk.android.services.common.CommonUtils;
import io.fabric.sdk.android.services.settings.PromptSettingsData;

class DialogStringResolver {
    private final Context context;
    private final PromptSettingsData promptData;

    public DialogStringResolver(Context context, PromptSettingsData promptSettingsData) {
        this.context = context;
        this.promptData = promptSettingsData;
    }

    private boolean isNullOrEmpty(String string2) {
        return string2 == null || string2.length() == 0;
    }

    private String resourceOrFallbackValue(String string2, String string3) {
        return this.stringOrFallback(CommonUtils.getStringsFileValue(this.context, string2), string3);
    }

    private String stringOrFallback(String string2, String string3) {
        if (this.isNullOrEmpty(string2)) {
            return string3;
        }
        return string2;
    }

    public String getAlwaysSendButtonTitle() {
        return this.resourceOrFallbackValue("com.crashlytics.CrashSubmissionAlwaysSendTitle", this.promptData.alwaysSendButtonTitle);
    }

    public String getCancelButtonTitle() {
        return this.resourceOrFallbackValue("com.crashlytics.CrashSubmissionCancelTitle", this.promptData.cancelButtonTitle);
    }

    public String getMessage() {
        return this.resourceOrFallbackValue("com.crashlytics.CrashSubmissionPromptMessage", this.promptData.message);
    }

    public String getSendButtonTitle() {
        return this.resourceOrFallbackValue("com.crashlytics.CrashSubmissionSendTitle", this.promptData.sendButtonTitle);
    }

    public String getTitle() {
        return this.resourceOrFallbackValue("com.crashlytics.CrashSubmissionPromptTitle", this.promptData.title);
    }
}

