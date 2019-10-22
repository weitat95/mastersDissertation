/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.support.v7.widget.AppCompatImageView;
import android.util.AttributeSet;
import android.view.View;
import com.getqardio.android.utils.permission.PermissionUtil;

public class PickContactView
extends AppCompatImageView
implements View.OnClickListener {
    private Callback callback;

    public PickContactView(Context context) {
        this(context, null);
    }

    public PickContactView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, 0);
    }

    public PickContactView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init();
    }

    private void init() {
        this.setImageResource(2130837916);
        this.setOnClickListener((View.OnClickListener)this);
    }

    private void processClickEvent() {
        if (PermissionUtil.Contact.hasReadContactsPermission(this.getContext())) {
            this.callback.requestPickContact();
            return;
        }
        this.callback.requestContactPermissions();
    }

    public void onClick(View view) {
        if (this.callback != null) {
            this.processClickEvent();
        }
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public static interface Callback {
        public void requestContactPermissions();

        public void requestPickContact();
    }

}

