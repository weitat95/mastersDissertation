/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.ContentProviderOperation
 *  android.content.ContentProviderOperation$Builder
 *  android.content.ContentProviderResult
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.CursorLoader
 *  android.content.Intent
 *  android.content.OperationApplicationException
 *  android.database.Cursor
 *  android.net.Uri
 *  android.net.Uri$Builder
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.RemoteException
 */
package com.getqardio.android.provider;

import android.content.ComponentName;
import android.content.ContentProviderOperation;
import android.content.ContentProviderResult;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.RemoteException;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.getqardio.android.datamodel.Tooltip;
import com.getqardio.android.io.network.request.TooltipsRequestHandler;
import com.getqardio.android.provider.AppProvideContract;
import com.getqardio.android.provider.TooltipHelper$$Lambda$1;
import com.getqardio.android.utils.HelperUtils;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public abstract class TooltipHelper {
    public static Uri buildTooltipsUri() {
        return AppProvideContract.BASE_CONTENT_URI.buildUpon().appendPath("tooltips").build();
    }

    public static void createGetTooltipIntent(Context context, long l) {
        context.startService(TooltipsRequestHandler.createTooltipsIntent(context, l));
    }

    public static CursorLoader getRandomTooltipCursorLoader(Context context) {
        return new CursorLoader(context, TooltipHelper.buildTooltipsUri(), null, null, null, "RANDOM() LIMIT 1");
    }

    static /* synthetic */ void lambda$saveTooltips$0(Context context, Tooltip tooltip) {
        Glide.with(context).load(tooltip.imageUrl).preload();
    }

    public static Tooltip parseTooltip(Cursor cursor) {
        Tooltip tooltip = new Tooltip();
        tooltip._id = HelperUtils.getLong(cursor, "_id", null);
        tooltip.tooltipId = HelperUtils.getInt(cursor, "tooltip_id", null);
        tooltip.title = HelperUtils.getString(cursor, "text", null);
        tooltip.text = HelperUtils.getString(cursor, "title", null);
        tooltip.imageUrl = HelperUtils.getString(cursor, "image_url", null);
        tooltip.textPosition = HelperUtils.getString(cursor, "text_position", null);
        return tooltip;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static void saveTooltips(Context var0, List<Tooltip> var1_4) {
        var2_5 = TooltipHelper.buildTooltipsUri();
        var3_6 = new ArrayList<ContentProviderOperation>();
        var4_7 = new Handler(Looper.getMainLooper());
        var1_4 = var1_4.iterator();
        while (var1_4.hasNext()) {
            var5_8 = (Tooltip)var1_4.next();
            var6_9 = new ContentValues(5);
            HelperUtils.put(var6_9, "tooltip_id", var5_8.tooltipId);
            HelperUtils.put(var6_9, "text", var5_8.text);
            HelperUtils.put(var6_9, "title", var5_8.title);
            HelperUtils.put(var6_9, "image_url", var5_8.imageUrl);
            HelperUtils.put(var6_9, "text_position", var5_8.textPosition);
            var3_6.add(ContentProviderOperation.newInsert((Uri)var2_5).withValues(var6_9).build());
            var4_7.post(TooltipHelper$$Lambda$1.lambdaFactory$(var0, var5_8));
        }
        try {
            var0.getContentResolver().applyBatch("com.getqardio.android", var3_6);
            return;
        }
        catch (RemoteException var0_1) {}
        ** GOTO lbl-1000
        catch (OperationApplicationException var0_3) {}
lbl-1000:
        // 2 sources
        {
            Timber.e((Throwable)var0_2);
            return;
        }
    }
}

