/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 */
package com.getqardio.android.ui.fragment;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;

public class MarketingLoginFragment9
extends Fragment {
    public static MarketingLoginFragment9 newInstance() {
        return new MarketingLoginFragment9();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968751, viewGroup, false);
        Glide.with(this).load(2130837879).into((ImageView)layoutInflater.findViewById(2131821135));
        Glide.with(this).load(2130837880).into((ImageView)layoutInflater.findViewById(2131821138));
        Glide.with(this).load(2130837881).into((ImageView)layoutInflater.findViewById(2131821137));
        Glide.with(this).load(2130837882).into((ImageView)layoutInflater.findViewById(2131821136));
        return layoutInflater;
    }
}

