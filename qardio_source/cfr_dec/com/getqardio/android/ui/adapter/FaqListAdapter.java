/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.text.Html
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.CursorAdapter
 *  android.widget.TextView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.TextView;
import com.getqardio.android.datamodel.Faq;
import com.getqardio.android.provider.DataHelper;

public class FaqListAdapter
extends CursorAdapter {
    private int[] cellStates;

    public FaqListAdapter(Context context) {
        super(context, null, 0);
    }

    private Faq parseFaqForPostition(Cursor cursor, int n) {
        cursor.moveToPosition(n - 1);
        Faq faq = DataHelper.FaqHelper.parseFaq(cursor);
        cursor.moveToPosition(n);
        return faq;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void bindView(View object, Context object2, Cursor arrn) {
        boolean bl;
        ViewHolder viewHolder;
        Faq faq;
        block7: {
            int[] arrn2;
            int n = 1;
            viewHolder = (ViewHolder)object.getTag();
            int n2 = arrn2.getPosition();
            faq = DataHelper.FaqHelper.parseFaq((Cursor)arrn2);
            viewHolder.type = faq.deviceType;
            switch (this.cellStates[n2]) {
                default: {
                    bl = n2 == 0 ? true : !TextUtils.equals((CharSequence)this.parseFaqForPostition((Cursor)arrn2, (int)n2).deviceType, (CharSequence)faq.deviceType);
                }
                case 1: {
                    bl = true;
                    break block7;
                }
                case 2: {
                    bl = false;
                    break block7;
                }
            }
            arrn2 = this.cellStates;
            if (!bl) {
                n = 2;
            }
            arrn2[n2] = n;
        }
        if (bl) {
            viewHolder.separator.setText((CharSequence)faq.deviceType);
            viewHolder.separator.setVisibility(0);
        } else {
            viewHolder.separator.setVisibility(8);
        }
        viewHolder.question.setText((CharSequence)Html.fromHtml((String)faq.question));
    }

    public View newView(Context context, Cursor object, ViewGroup viewGroup) {
        context = LayoutInflater.from((Context)context).inflate(2130968707, viewGroup, false);
        object = new ViewHolder();
        object.question = (TextView)context.findViewById(2131821047);
        object.separator = (TextView)context.findViewById(2131821050);
        context.setTag(object);
        return context;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Cursor swapCursor(Cursor cursor) {
        int[] arrn = cursor == null ? null : new int[cursor.getCount()];
        this.cellStates = arrn;
        return super.swapCursor(cursor);
    }

    private static class ViewHolder {
        public TextView question;
        public TextView separator;
        public String type;

        private ViewHolder() {
        }
    }

}

