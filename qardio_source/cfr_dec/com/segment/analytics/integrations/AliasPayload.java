/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics.integrations;

import com.segment.analytics.integrations.BasePayload;

public class AliasPayload
extends BasePayload {
    public String previousId() {
        return this.getString("previousId");
    }

    @Override
    public String toString() {
        return "AliasPayload{userId=\"" + this.userId() + ",previousId=\"" + this.previousId() + "\"}";
    }
}

