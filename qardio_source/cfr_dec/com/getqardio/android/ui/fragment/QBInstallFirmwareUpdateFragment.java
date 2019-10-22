/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.res.Resources
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.res.Resources;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.getqardio.android.utils.ui.ActivityUtils;

public class QBInstallFirmwareUpdateFragment
extends Fragment
implements View.OnClickListener {
    private Button install;
    private TextView installUpdateDescription;
    private TextView installUpdateTitle;
    private QBInstallFirmwareUpdateListener qbInstallFirmwareUpdateListener;
    private TextView stepOnHint2;
    private ImageView userOnIcon;

    private String getUpdateDescription() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.UPDATE_DESCRIPTION")) {
            return bundle.getString("com.getqardio.android.argument.UPDATE_DESCRIPTION");
        }
        return "";
    }

    private String getUpdateVersion() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.UPDATE_VERSION")) {
            return bundle.getString("com.getqardio.android.argument.UPDATE_VERSION");
        }
        return "";
    }

    private void init(View view) {
        this.installUpdateTitle = (TextView)view.findViewById(2131821226);
        this.installUpdateDescription = (TextView)view.findViewById(2131821234);
        this.stepOnHint2 = (TextView)view.findViewById(2131821236);
        this.userOnIcon = (ImageView)view.findViewById(2131821235);
        this.install = (Button)view.findViewById(2131821237);
        this.installUpdateTitle.setText((CharSequence)this.getString(2131362438, new Object[]{this.getUpdateVersion()}));
        this.installUpdateDescription.setText((CharSequence)this.getUpdateDescription());
        if (!this.isUpToDate()) {
            this.install.setOnClickListener((View.OnClickListener)this);
            return;
        }
        this.stepOnHint2.setVisibility(4);
        this.install.setText((CharSequence)this.getString(2131361927));
    }

    private boolean isUpToDate() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.ISUPTODATE")) {
            return bundle.getBoolean("com.getqardio.android.argument.ISUPTODATE");
        }
        return false;
    }

    public static QBInstallFirmwareUpdateFragment newInstance(String string2, String string3) {
        return QBInstallFirmwareUpdateFragment.newInstance(string2, string3, false);
    }

    public static QBInstallFirmwareUpdateFragment newInstance(String object, String string2, boolean bl) {
        Bundle bundle = new Bundle(3);
        bundle.putString("com.getqardio.android.argument.UPDATE_VERSION", object);
        bundle.putString("com.getqardio.android.argument.UPDATE_DESCRIPTION", string2);
        bundle.putBoolean("com.getqardio.android.argument.ISUPTODATE", bl);
        object = new QBInstallFirmwareUpdateFragment();
        object.setArguments(bundle);
        return object;
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131361928);
    }

    public void onClick(View view) {
        if (this.qbInstallFirmwareUpdateListener != null) {
            this.qbInstallFirmwareUpdateListener.onInstallSelected();
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968791, viewGroup, false);
        viewGroup = (ImageView)layoutInflater.findViewById(2131821233);
        Glide.with(this).load(2130837939).into((ImageView)viewGroup);
        this.init((View)layoutInflater);
        this.userAbsent();
        return layoutInflater;
    }

    public void userAbsent() {
        this.userOnIcon.setVisibility(4);
        this.stepOnHint2.setTextColor(this.getResources().getColor(2131689548));
        this.stepOnHint2.setTypeface(Typeface.create((String)"sans-serif-light", (int)0));
        this.install.setEnabled(false);
    }

    public static interface QBInstallFirmwareUpdateListener {
        public void onInstallSelected();
    }

}

