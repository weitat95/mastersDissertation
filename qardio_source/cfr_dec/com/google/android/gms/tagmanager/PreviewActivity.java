/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.AlertDialog$Builder
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.net.Uri
 *  android.os.Bundle
 */
package com.google.android.gms.tagmanager;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import com.google.android.gms.tagmanager.TagManager;
import com.google.android.gms.tagmanager.zzdj;
import com.google.android.gms.tagmanager.zzeh;

public class PreviewActivity
extends Activity {
    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onCreate(Bundle object) {
        try {
            Intent intent;
            super.onCreate((Bundle)object);
            zzdj.zzct("Preview activity");
            object = this.getIntent().getData();
            if (!TagManager.getInstance((Context)this).zzq((Uri)object)) {
                object = String.valueOf(object);
                object = new StringBuilder(String.valueOf(object).length() + 73).append("Cannot preview the app with the uri: ").append((String)object).append(". Launching current version instead.").toString();
                zzdj.zzcu((String)object);
                intent = new AlertDialog.Builder((Context)this).create();
                intent.setTitle((CharSequence)"Preview failure");
                intent.setMessage((CharSequence)object);
                intent.setButton(-1, (CharSequence)"Continue", (DialogInterface.OnClickListener)new zzeh(this));
                intent.show();
            }
            if ((intent = this.getPackageManager().getLaunchIntentForPackage(this.getPackageName())) != null) {
                object = String.valueOf(this.getPackageName());
                object = ((String)object).length() != 0 ? "Invoke the launch activity for package name: ".concat((String)object) : new String("Invoke the launch activity for package name: ");
                zzdj.zzct((String)object);
                this.startActivity(intent);
                return;
            }
            object = String.valueOf(this.getPackageName());
            object = ((String)object).length() != 0 ? "No launch activity found for package name: ".concat((String)object) : new String("No launch activity found for package name: ");
        }
        catch (Exception exception) {
            String string2 = String.valueOf(exception.getMessage());
            string2 = string2.length() != 0 ? "Calling preview threw an exception: ".concat(string2) : new String("Calling preview threw an exception: ");
            zzdj.zzct((String)object);
            return;
            zzdj.e(string2);
            return;
        }
    }
}

