/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.ImageView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.support.v4.view.PagerAdapter;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import java.io.File;

public class PregnancyGalleryAdapter
extends PagerAdapter {
    private Context context;
    private File[] files;
    private ViewGroup.LayoutParams layoutParams;

    public PregnancyGalleryAdapter(Context context, File[] arrfile) {
        this.context = context;
        this.files = arrfile;
        this.layoutParams = new ViewGroup.LayoutParams(-1, -1);
    }

    public void delete(int n) {
        File[] arrfile = new File[this.files.length - 1];
        System.arraycopy(this.files, 0, arrfile, 0, n);
        System.arraycopy(this.files, n + 1, arrfile, n, this.files.length - n - 1);
        this.files = arrfile;
        this.notifyDataSetChanged();
    }

    @Override
    public void destroyItem(ViewGroup viewGroup, int n, Object object) {
        viewGroup.removeView((View)object);
    }

    @Override
    public int getCount() {
        return this.files.length;
    }

    @Override
    public int getItemPosition(Object object) {
        return -2;
    }

    public View instantiateItem(ViewGroup viewGroup, int n) {
        ImageView imageView = new ImageView(this.context);
        viewGroup.addView((View)imageView, this.layoutParams);
        Glide.with(this.context).load("file://" + this.files[n].getAbsolutePath()).into(imageView);
        return imageView;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view.equals(object);
    }
}

