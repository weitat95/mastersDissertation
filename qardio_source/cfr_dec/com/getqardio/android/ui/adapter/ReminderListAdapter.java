/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.CursorAdapter
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.datamodel.Reminder;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;
import com.getqardio.android.utils.ui.Convert;

public class ReminderListAdapter
extends CursorAdapter {
    private View.OnClickListener deleteListener;
    private OnReminderDeleteListener onReminderDeleteListener;

    public ReminderListAdapter(Context context, OnReminderDeleteListener onReminderDeleteListener) {
        super(context, null, 0);
        this.onReminderDeleteListener = onReminderDeleteListener;
        this.deleteListener = new View.OnClickListener(){

            public void onClick(View view) {
                if (view.getTag() != null && ReminderListAdapter.this.onReminderDeleteListener != null) {
                    ReminderListAdapter.this.onReminderDeleteListener.onDeleteReminder((Reminder)view.getTag());
                }
            }
        };
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void bindView(View view, Context object, Cursor object2) {
        void var3_4;
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        Reminder reminder = DataHelper.ReminderHelper.parseReminder((Cursor)var3_4);
        if (reminder.remindTime != null) {
            String string2 = Convert.Reminder.localTimeToTimeString(Convert.Reminder.timeAfterMidnightToLocalTime(reminder.remindTime));
            viewHolder.time.setText((CharSequence)string2);
        } else {
            viewHolder.time.setText(null);
        }
        if (reminder.days != null) {
            String string3 = Convert.Reminder.daysToString(object, reminder.days);
            viewHolder.days.setText((CharSequence)String.valueOf(string3));
        } else {
            viewHolder.days.setText(null);
        }
        viewHolder.deleteButton.setTag((Object)reminder);
        BackPanelListViewHelper.hideBackPanel(view);
    }

    public View getView(int n, View view, ViewGroup viewGroup) {
        return super.getView(n, view, viewGroup);
    }

    public View newView(Context context, Cursor object, ViewGroup viewGroup) {
        context = BackPanelListViewHelper.wrapListViewItem(context, 2130968809, 2130968679, viewGroup);
        object = new ViewHolder();
        object.time = (TextView)context.findViewById(2131821278);
        object.days = (TextView)context.findViewById(2131821279);
        object.deleteButton = (ImageView)context.findViewById(2131821017);
        object.deleteButton.setOnClickListener(this.deleteListener);
        context.setTag(object);
        return context;
    }

    public static interface OnReminderDeleteListener {
        public void onDeleteReminder(Reminder var1);
    }

    public static class ViewHolder {
        public TextView days;
        public ImageView deleteButton;
        public TextView time;
    }

}

