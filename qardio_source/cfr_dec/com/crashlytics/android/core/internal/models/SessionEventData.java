/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android.core.internal.models;

import com.crashlytics.android.core.internal.models.BinaryImageData;
import com.crashlytics.android.core.internal.models.CustomAttributeData;
import com.crashlytics.android.core.internal.models.DeviceData;
import com.crashlytics.android.core.internal.models.SignalData;
import com.crashlytics.android.core.internal.models.ThreadData;

public class SessionEventData {
    public final BinaryImageData[] binaryImages;
    public final CustomAttributeData[] customAttributes;
    public final DeviceData deviceData;
    public final SignalData signal;
    public final ThreadData[] threads;
    public final long timestamp;
}

