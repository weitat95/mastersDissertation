/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.content.res.TypedArray
 *  android.net.Uri
 *  android.util.AttributeSet
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.res.TypedArray;
import android.net.Uri;
import android.util.AttributeSet;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.drawee.R;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.interfaces.DraweeController;
import com.facebook.drawee.interfaces.SimpleDraweeControllerBuilder;
import com.facebook.drawee.view.GenericDraweeView;
import javax.annotation.Nullable;

public class SimpleDraweeView
extends GenericDraweeView {
    private static Supplier<? extends SimpleDraweeControllerBuilder> sDraweeControllerBuilderSupplier;
    private SimpleDraweeControllerBuilder mSimpleDraweeControllerBuilder;

    public SimpleDraweeView(Context context) {
        super(context);
        this.init(context, null);
    }

    public SimpleDraweeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.init(context, attributeSet);
    }

    public SimpleDraweeView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.init(context, attributeSet);
    }

    @TargetApi(value=21)
    public SimpleDraweeView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.init(context, attributeSet);
    }

    public SimpleDraweeView(Context context, GenericDraweeHierarchy genericDraweeHierarchy) {
        super(context, genericDraweeHierarchy);
        this.init(context, null);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void init(Context context, @Nullable AttributeSet attributeSet) {
        if (this.isInEditMode()) {
            return;
        }
        Preconditions.checkNotNull(sDraweeControllerBuilderSupplier, "SimpleDraweeView was not initialized!");
        this.mSimpleDraweeControllerBuilder = sDraweeControllerBuilderSupplier.get();
        if (attributeSet == null) return;
        context = context.obtainStyledAttributes(attributeSet, R.styleable.SimpleDraweeView);
        try {
            if (!context.hasValue(R.styleable.SimpleDraweeView_actualImageUri)) return;
            this.setImageURI(Uri.parse((String)context.getString(R.styleable.SimpleDraweeView_actualImageUri)), null);
            return;
        }
        finally {
            context.recycle();
        }
    }

    public static void initialize(Supplier<? extends SimpleDraweeControllerBuilder> supplier) {
        sDraweeControllerBuilderSupplier = supplier;
    }

    public static void shutDown() {
        sDraweeControllerBuilderSupplier = null;
    }

    protected SimpleDraweeControllerBuilder getControllerBuilder() {
        return this.mSimpleDraweeControllerBuilder;
    }

    @Override
    public void setImageURI(Uri uri) {
        this.setImageURI(uri, null);
    }

    public void setImageURI(Uri uri, @Nullable Object object) {
        this.setController(this.mSimpleDraweeControllerBuilder.setCallerContext(object).setUri(uri).setOldController(this.getController()).build());
    }

    public void setImageURI(@Nullable String string2) {
        this.setImageURI(string2, null);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setImageURI(@Nullable String string2, @Nullable Object object) {
        string2 = string2 != null ? Uri.parse((String)string2) : null;
        this.setImageURI((Uri)string2, object);
    }
}

