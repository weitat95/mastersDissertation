/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.AbsListView
 *  android.widget.AbsListView$LayoutParams
 *  android.widget.ImageView
 *  android.widget.ImageView$ScaleType
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ImageView;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class GalleryAdapter
extends RecyclerView.Adapter<ViewHolder> {
    private Context context;
    private AbsListView.LayoutParams layoutParams;
    private List<File> photos;

    public GalleryAdapter(Context context) {
        this.context = context;
        this.photos = new ArrayList<File>();
        int n = context.getResources().getDimensionPixelSize(2131427541);
        this.layoutParams = new AbsListView.LayoutParams(n, n);
    }

    @Override
    public int getItemCount() {
        return this.photos.size() + 1;
    }

    @Override
    public long getItemId(int n) {
        if (n < this.photos.size()) {
            return n;
        }
        return -1L;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        if (n < this.photos.size()) {
            viewHolder.image.setBackground(null);
            Glide.with(this.context).load("file://" + this.photos.get(n).getAbsolutePath()).into(viewHolder.image);
            return;
        }
        viewHolder.image.setBackgroundResource(2130837719);
        Glide.with(this.context).load(2130837773).into(viewHolder.image);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        viewGroup = new ImageView(this.context);
        viewGroup.setScaleType(ImageView.ScaleType.CENTER);
        viewGroup.setLayoutParams((ViewGroup.LayoutParams)this.layoutParams);
        return new ViewHolder((View)viewGroup);
    }

    public void onItemDismiss(int n) {
        this.photos.remove(n);
        this.notifyItemRemoved(n);
    }

    public void setData(File[] arrfile) {
        this.photos.clear();
        this.photos.addAll(Arrays.asList(arrfile));
        this.notifyDataSetChanged();
    }

    static class ViewHolder
    extends RecyclerView.ViewHolder {
        public ImageView image;

        public ViewHolder(View view) {
            super(view);
            this.image = (ImageView)view;
        }
    }

}

