/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ArrayAdapter
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.utils.Constants;
import com.getqardio.android.utils.wifi.WifiAp;
import com.getqardio.android.utils.wifi.WifiSecurityState;
import java.util.List;

public class QBWiFiListAdapter
extends ArrayAdapter<WifiAp> {
    private Context context;
    private List<WifiAp> wifiAps;

    public QBWiFiListAdapter(Context context, int n, List<WifiAp> list) {
        super(context, n, list);
        this.context = context;
        this.wifiAps = list;
    }

    private boolean isSecure(int n) {
        return this.getItem((int)n).sec == WifiSecurityState.SECURE;
    }

    public WifiAp getItem(int n) {
        return this.wifiAps.get(n);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public View getView(int n, View view, ViewGroup object) {
        void var3_5;
        void var3_7;
        Drawable drawable2 = this.context.getResources().getDrawable(2130837838);
        if (view == null) {
            view = ((LayoutInflater)this.context.getSystemService("layout_inflater")).inflate(2130968861, object, false);
            WifiApViewHolder wifiApViewHolder = new WifiApViewHolder();
            wifiApViewHolder.wifiApName = (TextView)view.findViewById(2131821454);
            wifiApViewHolder.wifiSignalLevel = (ImageView)view.findViewById(2131821455);
            wifiApViewHolder.wifiSecurity = (ImageView)view.findViewById(2131821456);
            view.setTag((Object)wifiApViewHolder);
        } else {
            WifiApViewHolder wifiApViewHolder = (WifiApViewHolder)view.getTag();
        }
        var3_5.wifiApName.setText((CharSequence)this.getItem((int)n).ssid);
        var3_5.wifiSignalLevel.setImageResource(Constants.WifiLevel.calculateSignalLevel(this.getItem((int)n).rssi));
        ImageView imageView = var3_5.wifiSecurity;
        if (this.isSecure(n)) {
            Drawable drawable3 = drawable2;
        } else {
            Object var3_9 = null;
        }
        imageView.setImageDrawable((Drawable)var3_7);
        return view;
    }

    private static class WifiApViewHolder {
        TextView wifiApName;
        ImageView wifiSecurity;
        ImageView wifiSignalLevel;

        private WifiApViewHolder() {
        }
    }

}

