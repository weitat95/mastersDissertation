/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.res.Resources;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryFragment;
import com.getqardio.android.ui.fragment.ReminderListFragment;
import com.getqardio.android.ui.fragment.StartBPFragment;

public class BloodPressureFragment
extends Fragment
implements BPMeasurementsHistoryFragment.Callback {
    private View historyButton;
    private ImageView historyImage;
    private TextView historyText;
    private boolean isAfterAttach;
    private View remindersButton;
    private ReminderListFragment remindersFragment;
    private ImageView remindersImage;
    private TextView remindersText;
    private View rootView;
    private View startButton;
    private ImageView startImage;
    private TextView startText;

    private void init() {
        this.startButton = this.rootView.findViewById(2131820621);
        this.startImage = (ImageView)this.rootView.findViewById(2131820895);
        this.startText = (TextView)this.rootView.findViewById(2131820896);
        this.historyButton = this.rootView.findViewById(2131820897);
        this.historyImage = (ImageView)this.rootView.findViewById(2131820898);
        this.historyText = (TextView)this.rootView.findViewById(2131820899);
        this.remindersButton = this.rootView.findViewById(2131820900);
        this.remindersImage = (ImageView)this.rootView.findViewById(2131820901);
        this.remindersText = (TextView)this.rootView.findViewById(2131820902);
        this.startButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (!view.isSelected()) {
                    BloodPressureFragment.this.onTabSelected(1);
                }
            }
        });
        this.historyButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (!view.isSelected()) {
                    BloodPressureFragment.this.onTabSelected(2);
                }
            }
        });
        this.remindersButton.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (!view.isSelected()) {
                    BloodPressureFragment.this.onTabSelected(3);
                }
            }
        });
    }

    private boolean isFromNotification() {
        Bundle bundle = this.getArguments();
        return bundle != null && bundle.containsKey("com.getqardio.android.argument.FROM_NOTIFICATION") && bundle.getBoolean("com.getqardio.android.argument.FROM_NOTIFICATION");
    }

    public static BloodPressureFragment newInstance(boolean bl, int n) {
        BloodPressureFragment bloodPressureFragment = new BloodPressureFragment();
        Bundle bundle = new Bundle(1);
        bundle.putBoolean("com.getqardio.android.argument.FROM_NOTIFICATION", bl);
        bundle.putInt("com.getqardio.android.argument.FROM_SHORTCUT", n);
        bloodPressureFragment.setArguments(bundle);
        return bloodPressureFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onTabSelected(int n) {
        switch (n) {
            case 1: {
                this.setStartTabSelected(true);
                this.setHistoryTabSelected(false);
                this.setRemindersTabSelected(false);
                this.showStartFragment();
                CustomApplication.getApplication().setBpTab(n);
                break;
            }
            case 2: {
                this.setHistoryTabSelected(true);
                this.setStartTabSelected(false);
                this.setRemindersTabSelected(false);
                this.showMeasurementsFragment();
                CustomApplication.getApplication().setBpTab(n);
                break;
            }
            case 3: {
                this.setRemindersTabSelected(true);
                this.setStartTabSelected(false);
                this.setHistoryTabSelected(false);
                this.showRemindersFragment();
                CustomApplication.getApplication().setBpTab(n);
                break;
            }
        }
        this.isAfterAttach = false;
    }

    private void setHistoryTabSelected(boolean bl) {
        this.historyButton.setSelected(bl);
        if (bl) {
            this.historyImage.setImageResource(2130838014);
            this.historyText.setTextColor(this.getResources().getColor(2131689548));
            return;
        }
        this.historyImage.setImageResource(2130837961);
        this.historyText.setTextColor(this.getResources().getColor(2131689655));
    }

    private void setRemindersTabSelected(boolean bl) {
        this.remindersButton.setSelected(bl);
        if (bl) {
            this.remindersImage.setImageResource(2130838018);
            this.remindersText.setTextColor(this.getResources().getColor(2131689548));
            return;
        }
        this.remindersImage.setImageResource(2130837964);
        this.remindersText.setTextColor(this.getResources().getColor(2131689655));
    }

    private void setStartTabSelected(boolean bl) {
        this.startButton.setSelected(bl);
        if (bl) {
            this.startImage.setImageResource(2130838016);
            this.startText.setTextColor(this.getResources().getColor(2131689548));
            return;
        }
        this.startImage.setImageResource(2130837962);
        this.startText.setTextColor(this.getResources().getColor(2131689655));
    }

    private void showMeasurementsFragment() {
        BPMeasurementsHistoryFragment bPMeasurementsHistoryFragment = BPMeasurementsHistoryFragment.newInstance(CustomApplication.getApplication().getCurrentUserId(), false);
        this.getChildFragmentManager().beginTransaction().replace(2131820892, (Fragment)bPMeasurementsHistoryFragment).commitAllowingStateLoss();
    }

    private void showRemindersFragment() {
        FragmentManager fragmentManager;
        if (this.remindersFragment == null) {
            this.remindersFragment = ReminderListFragment.getInstance("bp");
        }
        if ((fragmentManager = this.getChildFragmentManager()).findFragmentById(this.remindersFragment.getId()) == null || this.isAfterAttach) {
            fragmentManager.beginTransaction().replace(2131820892, (Fragment)this.remindersFragment).commitAllowingStateLoss();
        }
    }

    private void showStartFragment() {
        this.getChildFragmentManager().beginTransaction().replace(2131820892, (Fragment)StartBPFragment.newInstance(this.isFromNotification())).commitAllowingStateLoss();
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menu2.clear();
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968640, viewGroup, false);
        return this.rootView;
    }

    @Override
    public void onTakeMeasurementsClick() {
        this.onTabSelected(1);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.init();
        this.isAfterAttach = true;
        view = this.getArguments();
        CustomApplication.getApplication().getBpTab();
        int n = view != null && view.containsKey("PRE_SELECTED_TAB") ? view.getInt("PRE_SELECTED_TAB") : (view != null && view.containsKey("com.getqardio.android.argument.FROM_SHORTCUT") ? view.getInt("com.getqardio.android.argument.FROM_SHORTCUT") : 1);
        this.onTabSelected(n);
        this.setHasOptionsMenu(true);
    }

}

