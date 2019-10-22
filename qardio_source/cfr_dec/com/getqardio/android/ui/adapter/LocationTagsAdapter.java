/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Typeface
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.BaseAdapter
 *  android.widget.CheckBox
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

public class LocationTagsAdapter
extends BaseAdapter {
    private static final int[] ITEMS = new int[]{1, 2, 3, 4, 5};
    private LayoutInflater inflater;
    private int selectedTag;

    public LocationTagsAdapter(Context context, int n) {
        this.inflater = LayoutInflater.from((Context)context);
        this.selectedTag = n;
    }

    public int getCount() {
        return ITEMS.length;
    }

    public Integer getItem(int n) {
        return ITEMS[n];
    }

    public long getItemId(int n) {
        return ITEMS[n];
    }

    public int getSelectedTag() {
        return this.selectedTag;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public View getView(int n, View object, ViewGroup viewGroup) {
        Object object2 = object;
        if (object == null) {
            void var3_7;
            object2 = this.inflater.inflate(2130968741, (ViewGroup)var3_7, false);
            ViewHolder viewHolder = new ViewHolder();
            viewHolder.tagIcon = (ImageView)object2.findViewById(2131820933);
            viewHolder.tagName = (TextView)object2.findViewById(2131821129);
            viewHolder.tagName.setTypeface(Typeface.create((String)"sans-serif-light", (int)0));
            viewHolder.tagChecked = (CheckBox)object2.findViewById(2131821130);
            object2.setTag((Object)viewHolder);
        }
        ViewHolder viewHolder = (ViewHolder)object2.getTag();
        switch (this.getItem(n)) {
            case 1: {
                viewHolder.tagIcon.setImageResource(2130837851);
                viewHolder.tagName.setText(2131361952);
                break;
            }
            case 2: {
                viewHolder.tagIcon.setImageResource(2130837853);
                viewHolder.tagName.setText(2131362091);
                break;
            }
            case 3: {
                viewHolder.tagIcon.setImageResource(2130837857);
                viewHolder.tagName.setText(2131362082);
                break;
            }
            case 4: {
                viewHolder.tagIcon.setImageResource(2130837849);
                viewHolder.tagName.setText(2131361940);
                break;
            }
            case 5: {
                viewHolder.tagIcon.setImageResource(2130837847);
                viewHolder.tagName.setText(2131361902);
            }
        }
        CheckBox checkBox = viewHolder.tagChecked;
        boolean bl = this.getItem(n) == this.selectedTag;
        checkBox.setChecked(bl);
        return object2;
    }

    public void setSelectedTag(int n) {
        this.selectedTag = n;
    }

    private static class ViewHolder {
        public CheckBox tagChecked;
        public ImageView tagIcon;
        public TextView tagName;

        private ViewHolder() {
        }
    }

}

