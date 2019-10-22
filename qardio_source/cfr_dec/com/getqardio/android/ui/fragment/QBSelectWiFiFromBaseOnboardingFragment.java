/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.content.Context
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.text.Editable
 *  android.text.TextWatcher
 *  android.view.KeyEvent
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnKeyListener
 *  android.view.ViewGroup
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.Button
 *  android.widget.EditText
 *  android.widget.ListAdapter
 *  android.widget.ListView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.os.IBinder;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import com.getqardio.android.baseble.QardioBaseManager;
import com.getqardio.android.handler.QBOnboardingManager;
import com.getqardio.android.ui.adapter.QBWiFiListAdapter;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.OnBoardingWifiProvider;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment$$Lambda$7;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.wifi.WifiAp;
import com.getqardio.android.utils.wifi.WifiSecurityState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import timber.log.Timber;

public class QBSelectWiFiFromBaseOnboardingFragment
extends AbstractQBOnboardingFragment
implements QBOnboardingManager.RefreshWifiFromBaseListener {
    private QBWiFiListAdapter adapter;
    private OnBoardingWifiProvider dataProvider;
    private Button doneButton;
    private EditText etPassword;
    private View footerView;
    private AdapterView.OnItemClickListener itemClickListener;
    private View.OnClickListener onFooterClickListener;
    private TextWatcher passwordTextWatcher = new TextWatcher(){

        public void afterTextChanged(Editable editable) {
            if (editable.length() > 5) {
                QBSelectWiFiFromBaseOnboardingFragment.this.doneButton.setEnabled(true);
                return;
            }
            QBSelectWiFiFromBaseOnboardingFragment.this.doneButton.setEnabled(false);
        }

        public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }

        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
        }
    };
    private QBOnboardingManager qbOnboardingManager;
    private SwipeRefreshLayout refreshLayout;
    private WifiAp selectedWifiAp;
    private SkipWifiDuringConfigurationListener skipWifiDuringConfigurationListner;
    private List<WifiAp> wifiApsList;

    public QBSelectWiFiFromBaseOnboardingFragment() {
        this.itemClickListener = QBSelectWiFiFromBaseOnboardingFragment$$Lambda$1.lambdaFactory$(this);
        this.onFooterClickListener = QBSelectWiFiFromBaseOnboardingFragment$$Lambda$2.lambdaFactory$();
    }

    private View createFooterView(ViewGroup viewGroup) {
        viewGroup = ((LayoutInflater)this.getActivity().getSystemService("layout_inflater")).inflate(2130968861, viewGroup, false);
        TextView textView = (TextView)viewGroup.findViewById(2131821454);
        textView.setText(2131362340);
        textView.setGravity(17);
        return viewGroup;
    }

    private void forceKeyboardToDismiss() {
        ((InputMethodManager)this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.etPassword.getWindowToken(), 0);
    }

    private void forceKeyboardToPopUp() {
        ((InputMethodManager)this.getActivity().getSystemService("input_method")).toggleSoftInput(2, 1);
    }

    static /* synthetic */ void lambda$new$6(View view) {
    }

    public static QBSelectWiFiFromBaseOnboardingFragment newInstance(String string2) {
        QBSelectWiFiFromBaseOnboardingFragment qBSelectWiFiFromBaseOnboardingFragment = new QBSelectWiFiFromBaseOnboardingFragment();
        Bundle bundle = new Bundle();
        bundle.putString("WifiAps", string2);
        qBSelectWiFiFromBaseOnboardingFragment.setArguments(bundle);
        return qBSelectWiFiFromBaseOnboardingFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showPreseledtedWifDialog(WifiAp wifiAp) {
        this.selectedWifiAp = wifiAp;
        this.forceKeyboardToPopUp();
        Object object = new AlertDialog.Builder((Context)this.getActivity());
        wifiAp = this.getActivity().getLayoutInflater().inflate(2130968732, null);
        ((AlertDialog.Builder)object).setView((View)wifiAp);
        object = ((AlertDialog.Builder)object).create();
        object.setCanceledOnTouchOutside(false);
        this.etPassword = (EditText)wifiAp.findViewById(2131821112);
        this.etPassword.addTextChangedListener(this.passwordTextWatcher);
        View view = wifiAp.findViewById(2131821111);
        this.doneButton = (Button)wifiAp.findViewById(2131821113);
        this.doneButton.setOnClickListener(QBSelectWiFiFromBaseOnboardingFragment$$Lambda$6.lambdaFactory$(this, (AlertDialog)object));
        ((TextView)wifiAp.findViewById(2131821114)).setOnClickListener(QBSelectWiFiFromBaseOnboardingFragment$$Lambda$7.lambdaFactory$(this, (AlertDialog)object));
        wifiAp = (TextView)wifiAp.findViewById(2131821110);
        Timber.d("selectedWifiAp.sec - %s", new Object[]{this.selectedWifiAp.sec});
        if (this.selectedWifiAp.sec == WifiSecurityState.SECURE) {
            wifiAp.setText((CharSequence)this.getString(2131362423, new Object[]{"\"" + this.selectedWifiAp.ssid + "\""}));
        } else {
            this.etPassword.setVisibility(8);
            view.setVisibility(8);
            this.doneButton.setEnabled(true);
            wifiAp.setText((CharSequence)this.getString(2131362421, new Object[]{"\"" + this.selectedWifiAp.ssid + "\""}));
        }
        object.show();
    }

    @Override
    protected int getLayoutResource() {
        return 2130968815;
    }

    /* synthetic */ void lambda$new$5(AdapterView adapterView, View view, int n, long l) {
        this.showPreseledtedWifDialog((WifiAp)adapterView.getItemAtPosition(n));
        this.forceKeyboardToPopUp();
    }

    /* synthetic */ boolean lambda$onViewCreated$0(View view, int n, KeyEvent keyEvent) {
        if (n == 4) {
            this.getActivity().onBackPressed();
            return true;
        }
        return false;
    }

    /* synthetic */ void lambda$onViewCreated$1() {
        this.qbOnboardingManager.setScanWifiFromBaseListener(this);
    }

    /* synthetic */ void lambda$onViewCreated$2(View view) {
        this.skipWifiDuringConfigurationListner.skipWifiAndFinish();
        this.getFragmentManager().popBackStack();
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$showPreseledtedWifDialog$3(AlertDialog alertDialog, View view) {
        this.forceKeyboardToDismiss();
        if (this.selectedWifiAp.sec == WifiSecurityState.SECURE) {
            this.dataProvider.setWifiAp(this.selectedWifiAp, this.etPassword.getText().toString());
        } else {
            this.dataProvider.setWifiAp(this.selectedWifiAp, null);
        }
        alertDialog.dismiss();
        this.getFragmentManager().popBackStack();
    }

    /* synthetic */ void lambda$showPreseledtedWifDialog$4(AlertDialog alertDialog, View view) {
        this.forceKeyboardToDismiss();
        alertDialog.dismiss();
    }

    public void onActivityCreated(Bundle object) {
        super.onActivityCreated((Bundle)object);
        object = ActivityUtils.getActionBar(this.getActivity());
        if (object != null) {
            ((ActionBar)object).setTitle(2131362355);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        String string2 = this.getArguments().getString("WifiAps");
        this.wifiApsList = string2 != null ? QardioBaseUtils.convertWifiResult(string2) : new ArrayList<WifiAp>();
        this.setHasOptionsMenu(true);
    }

    public void onPause() {
        super.onPause();
        this.qbOnboardingManager.unregisterForActions();
        this.qbOnboardingManager.unregisterForWifiActions();
    }

    @Override
    public void onResume() {
        super.onResume();
        QardioBaseManager qardioBaseManager = new QardioBaseManager((Context)this.getActivity());
        this.qbOnboardingManager = new QBOnboardingManager(this.getActivity(), qardioBaseManager);
        this.qbOnboardingManager.registerForWifiActions();
    }

    @Override
    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.dataProvider = (OnBoardingWifiProvider)this.getParentFragment();
        bundle = (ListView)view.findViewById(2131821288);
        this.adapter = new QBWiFiListAdapter((Context)this.getActivity(), 2131821288, this.wifiApsList);
        bundle.setAdapter((ListAdapter)this.adapter);
        bundle.setDescendantFocusability(262144);
        bundle.setOnItemClickListener(this.itemClickListener);
        if (this.footerView == null) {
            this.footerView = this.createFooterView((ViewGroup)bundle);
            this.footerView.setOnClickListener(this.onFooterClickListener);
            bundle.addFooterView(this.footerView);
        }
        bundle.setOnKeyListener(QBSelectWiFiFromBaseOnboardingFragment$$Lambda$3.lambdaFactory$(this));
        this.refreshLayout = (SwipeRefreshLayout)view.findViewById(2131821287);
        this.refreshLayout.setOnRefreshListener(QBSelectWiFiFromBaseOnboardingFragment$$Lambda$4.lambdaFactory$(this));
        view.findViewById(2131821109).setOnClickListener(QBSelectWiFiFromBaseOnboardingFragment$$Lambda$5.lambdaFactory$(this));
    }

    @Override
    public void sendNewListFromBase(List<WifiAp> list) {
        this.refreshLayout.setRefreshing(false);
        this.adapter.clear();
        this.adapter.notifyDataSetChanged();
        this.adapter.addAll(list);
        this.adapter.notifyDataSetChanged();
    }

    public static interface SkipWifiDuringConfigurationListener {
        public void skipWifiAndFinish();
    }

}

