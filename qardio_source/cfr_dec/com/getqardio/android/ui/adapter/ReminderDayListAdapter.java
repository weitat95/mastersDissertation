/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ArrayAdapter
 *  android.widget.CheckBox
 *  android.widget.TextView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import com.getqardio.android.utils.ui.Convert;
import java.util.List;

public class ReminderDayListAdapter
extends ArrayAdapter<Integer> {
    private ReminderDayListAdapterCallback callback;
    private Context context;

    public ReminderDayListAdapter(Context context, List<Integer> list, ReminderDayListAdapterCallback reminderDayListAdapterCallback) {
        super(context, 2130968804, list);
        this.callback = reminderDayListAdapterCallback;
        this.context = context;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public View getView(int n, View view, ViewGroup object) {
        void var3_5;
        n = (Integer)this.getItem(n);
        if (view == null) {
            ViewHolder viewHolder = new ViewHolder();
            view = LayoutInflater.from((Context)this.getContext()).inflate(2130968804, object, false);
            viewHolder.title = (TextView)view.findViewById(2131820567);
            viewHolder.checkBox = (CheckBox)view.findViewById(2131820730);
            view.setTag((Object)viewHolder);
            ViewHolder viewHolder2 = viewHolder;
        } else {
            ViewHolder viewHolder = (ViewHolder)view.getTag();
        }
        var3_5.checkBox.setTag((Object)n);
        switch (n) {
            default: {
                return view;
            }
            case 127: {
                var3_5.title.setText(this.context.getText(2131361920));
                var3_5.checkBox.setChecked(this.callback.isDaySelected(127));
                return view;
            }
            case 1: {
                var3_5.title.setText((CharSequence)Convert.getFullDayName(0));
                var3_5.checkBox.setChecked(this.callback.isDaySelected(1));
                return view;
            }
            case 2: {
                var3_5.title.setText((CharSequence)Convert.getFullDayName(1));
                var3_5.checkBox.setChecked(this.callback.isDaySelected(2));
                return view;
            }
            case 4: {
                var3_5.title.setText((CharSequence)Convert.getFullDayName(2));
                var3_5.checkBox.setChecked(this.callback.isDaySelected(4));
                return view;
            }
            case 8: {
                var3_5.title.setText((CharSequence)Convert.getFullDayName(3));
                var3_5.checkBox.setChecked(this.callback.isDaySelected(8));
                return view;
            }
            case 16: {
                var3_5.title.setText((CharSequence)Convert.getFullDayName(4));
                var3_5.checkBox.setChecked(this.callback.isDaySelected(16));
                return view;
            }
            case 32: {
                var3_5.title.setText((CharSequence)Convert.getFullDayName(5));
                var3_5.checkBox.setChecked(this.callback.isDaySelected(32));
                return view;
            }
            case 64: 
        }
        var3_5.title.setText((CharSequence)Convert.getFullDayName(6));
        var3_5.checkBox.setChecked(this.callback.isDaySelected(64));
        return view;
    }

    public static interface ReminderDayListAdapterCallback {
        public boolean isDaySelected(int var1);
    }

    public static class ViewHolder {
        public CheckBox checkBox;
        public TextView title;
    }

}

