/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics.integrations;

import com.segment.analytics.integrations.BasePayload;

public class GroupPayload
extends BasePayload {
    public String groupId() {
        return this.getString("groupId");
    }

    @Override
    public String toString() {
        return "GroupPayload{groupId=\"" + this.groupId() + "\"}";
    }
}

