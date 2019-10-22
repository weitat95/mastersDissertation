/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ShortcutInfo
 *  android.content.pm.ShortcutInfo$Builder
 *  android.content.pm.ShortcutManager
 *  android.graphics.drawable.Icon
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.getqardio.android.shortcuts;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ShortcutInfo;
import android.content.pm.ShortcutManager;
import android.graphics.drawable.Icon;
import android.net.Uri;
import android.os.Build;
import java.util.ArrayList;
import java.util.List;

public class QardioSCHelper {
    private static QardioSCHelper instance;
    private Context context;

    private QardioSCHelper(Context context) {
        this.context = context.getApplicationContext();
    }

    @TargetApi(value=25)
    private ShortcutInfo createFriendsAndFamilyShortCut() {
        return new ShortcutInfo.Builder(this.context, "ID_FRIENDS_AND_FAMILY").setShortLabel((CharSequence)this.context.getString(2131362544)).setIcon(Icon.createWithResource((Context)this.context, (int)2130837787)).setIntent(new Intent("android.intent.action.VIEW", Uri.parse((String)"shortcut://dl.getqardio.com/app/fandf"))).setRank(2).build();
    }

    @TargetApi(value=25)
    private ShortcutInfo createQABPHistoryShortCut() {
        return new ShortcutInfo.Builder(this.context, "ID_BP_HISTORY").setShortLabel((CharSequence)this.context.getString(2131362542)).setIcon(Icon.createWithResource((Context)this.context, (int)2130837828)).setIntent(new Intent("android.intent.action.VIEW", Uri.parse((String)"shortcut://dl.getqardio.com/app/qarm/history"))).setRank(4).build();
    }

    @TargetApi(value=25)
    private ShortcutInfo createQABPMeasurementShortCut() {
        return new ShortcutInfo.Builder(this.context, "ID_QA_START").setShortLabel((CharSequence)this.context.getString(2131362543)).setIcon(Icon.createWithResource((Context)this.context, (int)2130837788)).setIntent(new Intent("android.intent.action.VIEW", Uri.parse((String)"shortcut://dl.getqardio.com/app/qarm/start"))).setRank(1).build();
    }

    @TargetApi(value=25)
    private ShortcutInfo createQBWeightHistoryShortCut() {
        return new ShortcutInfo.Builder(this.context, "ID_WEIGHT_HISTORY").setShortLabel((CharSequence)this.context.getString(2131362545)).setIcon(Icon.createWithResource((Context)this.context, (int)2130837828)).setIntent(new Intent("android.intent.action.VIEW", Uri.parse((String)"shortcut://dl.getqardio.com/app/qbase/history"))).setRank(3).build();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static QardioSCHelper getInstance(Context context) {
        if (instance == null) {
            synchronized (QardioSCHelper.class) {
                if (instance == null) {
                    instance = new QardioSCHelper(context);
                }
            }
        }
        return instance;
    }

    public void build() {
        ShortcutManager shortcutManager;
        if (Build.VERSION.SDK_INT < 25 || (shortcutManager = (ShortcutManager)this.context.getSystemService(ShortcutManager.class)) == null) {
            return;
        }
        ArrayList<ShortcutInfo> arrayList = new ArrayList<ShortcutInfo>(2);
        arrayList.add(this.createQABPHistoryShortCut());
        arrayList.add(this.createQBWeightHistoryShortCut());
        arrayList.add(this.createFriendsAndFamilyShortCut());
        arrayList.add(this.createQABPMeasurementShortCut());
        shortcutManager.setDynamicShortcuts(arrayList);
    }
}

