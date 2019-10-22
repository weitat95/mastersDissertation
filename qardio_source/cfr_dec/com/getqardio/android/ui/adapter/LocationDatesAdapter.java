/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.BaseAdapter
 *  android.widget.TextView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import com.getqardio.android.datamodel.LocationDatesItem;
import com.getqardio.android.utils.Utils;
import java.util.Collections;
import java.util.List;

public class LocationDatesAdapter
extends BaseAdapter {
    private Context context;
    private int datePeriodWidth;
    private LocationDateFormatter formatter;
    private List<LocationDatesItem> items;
    private int selectedPosition;
    private int textColorSelected;
    private int textColorUnselected;

    public LocationDatesAdapter(Context context, LocationDateFormatter locationDateFormatter, int n) {
        this.context = context;
        this.formatter = locationDateFormatter;
        this.items = Collections.EMPTY_LIST;
        this.selectedPosition = -1;
        this.datePeriodWidth = Utils.getScreenWidth(context) / n;
        this.textColorSelected = context.getResources().getColor(2131689548);
        this.textColorUnselected = context.getResources().getColor(2131689554);
    }

    public int getCount() {
        return this.items.size();
    }

    public LocationDatesItem getItem(int n) {
        return this.items.get(n);
    }

    public long getItemId(int n) {
        return this.getItem((int)n).startDate;
    }

    public LocationDatesItem getSelectedItem() {
        if (this.selectedPosition != -1) {
            return this.getItem(this.selectedPosition);
        }
        return null;
    }

    public int getSelectedItemX() {
        if (this.selectedPosition != -1) {
            return this.selectedPosition * this.datePeriodWidth;
        }
        return 0;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public View getView(int n, View object, ViewGroup viewGroup) {
        Object object2 = object;
        if (object == null) {
            void var3_5;
            object2 = LayoutInflater.from((Context)this.context).inflate(2130968737, (ViewGroup)var3_5, false);
            object2.getLayoutParams().width = this.datePeriodWidth;
        }
        LocationDatesItem locationDatesItem = this.getItem(n);
        ((TextView)object2).setText((CharSequence)this.formatter.convertDateToString(locationDatesItem));
        if (!locationDatesItem.clustersExist) {
            ((TextView)object2).setTextColor(this.textColorUnselected);
            object2.setAlpha(0.4f);
            return object2;
        }
        TextView textView = (TextView)object2;
        n = n == this.selectedPosition ? this.textColorSelected : this.textColorUnselected;
        textView.setTextColor(n);
        object2.setAlpha(1.0f);
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean setDateSelected(LocationDatesItem locationDatesItem) {
        if (locationDatesItem != null) {
            for (int i = this.items.size() - 1; i >= 0; --i) {
                if (!locationDatesItem.equals(this.items.get(i))) continue;
                this.setSelectedPosition(i);
                return true;
            }
        }
        return false;
    }

    public void setFormatter(LocationDateFormatter locationDateFormatter) {
        this.formatter = locationDateFormatter;
        this.notifyDataSetChanged();
    }

    public void setItemsList(List<LocationDatesItem> list) {
        this.items = list;
        this.selectedPosition = -1;
        this.notifyDataSetChanged();
    }

    public void setLastDateSelected() {
        int n = -1;
        int n2 = this.items.size() - 1;
        do {
            block4: {
                int n3;
                block3: {
                    n3 = n;
                    if (n2 < 0) break block3;
                    if (!this.items.get((int)n2).clustersExist) break block4;
                    n3 = n2;
                }
                this.setSelectedPosition(n3);
                return;
            }
            --n2;
        } while (true);
    }

    public void setSelectedPosition(int n) {
        this.selectedPosition = n;
        this.notifyDataSetChanged();
    }

    public static interface LocationDateFormatter {
        public String convertDateToString(LocationDatesItem var1);
    }

}

