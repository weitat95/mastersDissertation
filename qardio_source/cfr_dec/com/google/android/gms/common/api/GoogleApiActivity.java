/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.PendingIntent
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.util.Log
 */
package com.google.android.gms.common.api;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Parcelable;
import android.util.Log;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.internal.zzbm;

public class GoogleApiActivity
extends Activity
implements DialogInterface.OnCancelListener {
    private int zzfmm = 0;

    public static PendingIntent zza(Context context, PendingIntent pendingIntent, int n) {
        return PendingIntent.getActivity((Context)context, (int)0, (Intent)GoogleApiActivity.zza(context, pendingIntent, n, true), (int)134217728);
    }

    public static Intent zza(Context context, PendingIntent pendingIntent, int n, boolean bl) {
        context = new Intent(context, GoogleApiActivity.class);
        context.putExtra("pending_intent", (Parcelable)pendingIntent);
        context.putExtra("failing_client_id", n);
        context.putExtra("notify_manager", bl);
        return context;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onActivityResult(int n, int n2, Intent object) {
        super.onActivityResult(n, n2, (Intent)object);
        if (n == 1) {
            boolean bl = this.getIntent().getBooleanExtra("notify_manager", true);
            this.zzfmm = 0;
            this.setResult(n2, (Intent)object);
            if (bl) {
                object = zzbm.zzcj((Context)this);
                switch (n2) {
                    case 0: {
                        ((zzbm)object).zza(new ConnectionResult(13, null), this.getIntent().getIntExtra("failing_client_id", -1));
                    }
                    default: {
                        break;
                    }
                    case -1: {
                        ((zzbm)object).zzagz();
                        break;
                    }
                }
            }
        } else if (n == 2) {
            this.zzfmm = 0;
            this.setResult(n2, (Intent)object);
        }
        this.finish();
    }

    public void onCancel(DialogInterface dialogInterface) {
        this.zzfmm = 0;
        this.setResult(0);
        this.finish();
    }

    protected void onCreate(Bundle bundle) {
        Object object;
        block9: {
            block8: {
                super.onCreate(bundle);
                if (bundle != null) {
                    this.zzfmm = bundle.getInt("resolution");
                }
                if (this.zzfmm == 1) break block8;
                object = this.getIntent().getExtras();
                if (object != null) break block9;
                Log.e((String)"GoogleApiActivity", (String)"Activity started without extras");
                this.finish();
            }
            return;
        }
        bundle = (PendingIntent)object.get("pending_intent");
        object = (Integer)object.get("error_code");
        if (bundle == null && object == null) {
            Log.e((String)"GoogleApiActivity", (String)"Activity started without resolution");
            this.finish();
            return;
        }
        if (bundle != null) {
            try {
                this.startIntentSenderForResult(bundle.getIntentSender(), 1, null, 0, 0, 0);
                this.zzfmm = 1;
                return;
            }
            catch (IntentSender.SendIntentException sendIntentException) {
                Log.e((String)"GoogleApiActivity", (String)"Failed to launch pendingIntent", (Throwable)sendIntentException);
                this.finish();
                return;
            }
        }
        GoogleApiAvailability.getInstance().showErrorDialogFragment(this, (Integer)object, 2, this);
        this.zzfmm = 1;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("resolution", this.zzfmm);
        super.onSaveInstanceState(bundle);
    }
}

