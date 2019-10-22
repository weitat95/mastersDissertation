/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.getqardio.android.googlefit;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class MyAdapter
extends RecyclerView.Adapter<ViewHolder> {
    private String[] mDataset;

    public MyAdapter(String[] arrstring) {
        this.mDataset = arrstring;
    }

    @Override
    public int getItemCount() {
        return this.mDataset.length;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int n) {
        viewHolder.mTextView.setText((CharSequence)this.mDataset[n]);
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new ViewHolder((TextView)LayoutInflater.from((Context)viewGroup.getContext()).inflate(2130968734, viewGroup, false));
    }

    public static class ViewHolder
    extends RecyclerView.ViewHolder {
        public TextView mTextView;

        public ViewHolder(TextView textView) {
            super((View)textView);
            this.mTextView = textView;
        }
    }

}

