/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.content.Context
 *  android.util.AttributeSet
 *  javax.annotation.Nullable
 */
package com.facebook.drawee.view;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import com.facebook.drawee.generic.GenericDraweeHierarchy;
import com.facebook.drawee.generic.GenericDraweeHierarchyBuilder;
import com.facebook.drawee.generic.GenericDraweeHierarchyInflater;
import com.facebook.drawee.view.DraweeView;
import javax.annotation.Nullable;

public class GenericDraweeView
extends DraweeView<GenericDraweeHierarchy> {
    public GenericDraweeView(Context context) {
        super(context);
        this.inflateHierarchy(context, null);
    }

    public GenericDraweeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.inflateHierarchy(context, attributeSet);
    }

    public GenericDraweeView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.inflateHierarchy(context, attributeSet);
    }

    @TargetApi(value=21)
    public GenericDraweeView(Context context, AttributeSet attributeSet, int n, int n2) {
        super(context, attributeSet, n, n2);
        this.inflateHierarchy(context, attributeSet);
    }

    public GenericDraweeView(Context context, GenericDraweeHierarchy genericDraweeHierarchy) {
        super(context);
        this.setHierarchy(genericDraweeHierarchy);
    }

    protected void inflateHierarchy(Context object, @Nullable AttributeSet attributeSet) {
        object = GenericDraweeHierarchyInflater.inflateBuilder((Context)object, attributeSet);
        this.setAspectRatio(((GenericDraweeHierarchyBuilder)object).getDesiredAspectRatio());
        this.setHierarchy(((GenericDraweeHierarchyBuilder)object).build());
    }
}

