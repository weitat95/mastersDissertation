/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.firebase.jobdispatcher;

import android.net.Uri;
import java.util.List;

public class TriggerReason {
    private final List<Uri> triggeredContentUris;

    TriggerReason(List<Uri> list) {
        this.triggeredContentUris = list;
    }
}

