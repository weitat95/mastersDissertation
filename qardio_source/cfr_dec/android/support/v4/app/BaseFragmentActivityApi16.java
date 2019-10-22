/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Bundle
 */
package android.support.v4.app;

import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.support.v4.app.BaseFragmentActivityApi14;

abstract class BaseFragmentActivityApi16
extends BaseFragmentActivityApi14 {
    boolean mStartedActivityFromFragment;

    BaseFragmentActivityApi16() {
    }

    public void startActivityForResult(Intent intent, int n, Bundle bundle) {
        if (!this.mStartedActivityFromFragment && n != -1) {
            BaseFragmentActivityApi16.checkForValidRequestCode(n);
        }
        super.startActivityForResult(intent, n, bundle);
    }

    public void startIntentSenderForResult(IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        if (!this.mStartedIntentSenderFromFragment && n != -1) {
            BaseFragmentActivityApi16.checkForValidRequestCode(n);
        }
        super.startIntentSenderForResult(intentSender, n, intent, n2, n3, n4, bundle);
    }
}

