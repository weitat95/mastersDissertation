/*
 * Decompiled with CFR 0.147.
 */
package io.fabric.sdk.android.services.settings;

public class PromptSettingsData {
    public final String alwaysSendButtonTitle;
    public final String cancelButtonTitle;
    public final String message;
    public final String sendButtonTitle;
    public final boolean showAlwaysSendButton;
    public final boolean showCancelButton;
    public final String title;

    public PromptSettingsData(String string2, String string3, String string4, boolean bl, String string5, boolean bl2, String string6) {
        this.title = string2;
        this.message = string3;
        this.sendButtonTitle = string4;
        this.showCancelButton = bl;
        this.cancelButtonTitle = string5;
        this.showAlwaysSendButton = bl2;
        this.alwaysSendButtonTitle = string6;
    }
}

