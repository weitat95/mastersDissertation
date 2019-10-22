/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.io.network.invite.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class InviteRequest {
    @Expose
    @SerializedName(value="recipientEmail")
    private String recipientEmail;
    @Expose
    @SerializedName(value="recipientLocale")
    private String recipientLocale;
    @Expose
    @SerializedName(value="recipientName")
    private String recipientName;
    @Expose
    @SerializedName(value="template")
    private String template;
    @Expose
    @SerializedName(value="type")
    private String type;

    public void setRecipientEmail(String string2) {
        this.recipientEmail = string2;
    }

    public void setRecipientLocale(String string2) {
        this.recipientLocale = string2;
    }

    public void setRecipientName(String string2) {
        this.recipientName = string2;
    }

    public void setTemplate(String string2) {
        this.template = string2;
    }

    public void setType(String string2) {
        this.type = string2;
    }
}

