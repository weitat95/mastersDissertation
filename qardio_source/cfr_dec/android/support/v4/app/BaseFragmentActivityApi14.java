/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.util.AttributeSet
 *  android.view.View
 */
package android.support.v4.app;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.support.v4.app.SupportActivity;
import android.util.AttributeSet;
import android.view.View;

abstract class BaseFragmentActivityApi14
extends SupportActivity {
    boolean mStartedIntentSenderFromFragment;

    BaseFragmentActivityApi14() {
    }

    static void checkForValidRequestCode(int n) {
        if ((0xFFFF0000 & n) != 0) {
            throw new IllegalArgumentException("Can only use lower 16 bits for requestCode");
        }
    }

    abstract View dispatchFragmentsOnCreateView(View var1, String var2, Context var3, AttributeSet var4);

    public View onCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
        View view2;
        View view3 = view2 = this.dispatchFragmentsOnCreateView(view, string2, context, attributeSet);
        if (view2 == null) {
            view3 = super.onCreateView(view, string2, context, attributeSet);
        }
        return view3;
    }

    public View onCreateView(String string2, Context context, AttributeSet attributeSet) {
        View view;
        View view2 = view = this.dispatchFragmentsOnCreateView(null, string2, context, attributeSet);
        if (view == null) {
            view2 = super.onCreateView(string2, context, attributeSet);
        }
        return view2;
    }

    public void startIntentSenderForResult(IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4) throws IntentSender.SendIntentException {
        if (!this.mStartedIntentSenderFromFragment && n != -1) {
            BaseFragmentActivityApi14.checkForValidRequestCode(n);
        }
        super.startIntentSenderForResult(intentSender, n, intent, n2, n3, n4);
    }
}

