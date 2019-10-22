/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.BroadcastReceiver
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnDismissListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.wifi.ScanResult
 *  android.net.wifi.WifiInfo
 *  android.net.wifi.WifiManager
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.IBinder
 *  android.os.Message
 *  android.text.Editable
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.View
 *  android.view.View$OnClickListener
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
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
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
import com.getqardio.android.ui.adapter.QBWiFiListAdapter;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$$Lambda$7;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$$Lambda$8;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$$Lambda$9;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$3$$Lambda$1;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$3$$Lambda$2;
import com.getqardio.android.ui.fragment.QBSelectWiFiOnboardingFragment$3$$Lambda$3;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.wifi.QBWifiConfig;
import com.getqardio.android.utils.wifi.WifiAp;
import com.getqardio.android.utils.wifi.WifiSecurityState;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class QBSelectWiFiOnboardingFragment
extends Fragment {
    private QBWiFiListAdapter adapter;
    private Handler bleCmdHandler = new Handler();
    private Callback callback;
    private QBWifiConfig config;
    private AlertDialog dialog;
    private Button doneButton;
    private EditText etPassword;
    private View footerView;
    private boolean isWifiConfigVerificationInProgress;
    private AdapterView.OnItemClickListener itemClickListener;
    private ListView listView;
    private Menu menu;
    private View.OnClickListener onFooterClickListener;
    private TextWatcher passwordTextWatcher;
    private boolean preselectedWifiDialogShown;
    private QardioBaseManager qardioBaseManager;
    private BroadcastReceiver qbWifiStateReceiver;
    private SwipeRefreshLayout refreshLayout;
    private View rootView;
    private List<WifiAp> scanResults;
    private Handler timeoutHandler = new Handler(){

        public void handleMessage(Message message) {
            if (1 == message.what && QBSelectWiFiOnboardingFragment.this.getActivity() != null && QBSelectWiFiOnboardingFragment.this.isWifiConfigVerificationInProgress) {
                QBSelectWiFiOnboardingFragment.this.isWifiConfigVerificationInProgress = false;
                LocalBroadcastManager.getInstance((Context)QBSelectWiFiOnboardingFragment.this.getActivity()).unregisterReceiver(QBSelectWiFiOnboardingFragment.this.qbWifiStateReceiver);
                QBSelectWiFiOnboardingFragment.this.wifiManager.startScan();
            }
        }
    };
    private TextView txtTitle;
    private View wifiConnectedLayout;
    private int wifiCounter = 0;
    private WifiManager wifiManager;
    private BroadcastReceiver wifiStateReceiver = new BroadcastReceiver(){

        public void onReceive(Context object, Intent intent) {
            object = QBSelectWiFiOnboardingFragment.this.wifiManager.getScanResults();
            QBSelectWiFiOnboardingFragment.this.handleWifiResults((List)object);
        }
    };

    public QBSelectWiFiOnboardingFragment() {
        this.qbWifiStateReceiver = new BroadcastReceiver(){

            /* synthetic */ void lambda$onReceive$0() {
                QBSelectWiFiOnboardingFragment.this.wifiManager.startScan();
            }

            /* synthetic */ void lambda$onReceive$1() {
                QBSelectWiFiOnboardingFragment.this.qardioBaseManager.readWifiState();
            }

            /* synthetic */ void lambda$onReceive$2() {
                if (QBSelectWiFiOnboardingFragment.this.callback != null) {
                    QBSelectWiFiOnboardingFragment.this.callback.onSkippedWifiConfiguration();
                    QBSelectWiFiOnboardingFragment.this.callback.onDonePressed();
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onReceive(Context object, Intent intent) {
                if (!QBSelectWiFiOnboardingFragment.this.isWifiConfigVerificationInProgress) return;
                {
                    object = intent.getAction();
                    int n = -1;
                    switch (((String)object).hashCode()) {
                        case 1258970562: {
                            if (((String)object).equals("com.qardio.base.QB_WIFI_CONFIG")) {
                                n = 0;
                            }
                        }
                        default: {
                            break;
                        }
                        case 1856640497: {
                            if (!((String)object).equals("com.qardio.base.QB_WIFI_STATE")) break;
                            n = 1;
                        }
                    }
                    switch (n) {
                        default: {
                            return;
                        }
                        case 0: {
                            object = intent.getStringExtra("com.qardio.base.DATA");
                            QBSelectWiFiOnboardingFragment.this.config = QardioBaseUtils.parseWifiConfig((String)object);
                            if (TextUtils.isEmpty((CharSequence)QBSelectWiFiOnboardingFragment.this.config.getSsid())) {
                                QBSelectWiFiOnboardingFragment.this.txtTitle.setText((CharSequence)QBSelectWiFiOnboardingFragment.this.getString(2131362351));
                                QBSelectWiFiOnboardingFragment.this.isWifiConfigVerificationInProgress = false;
                                QBSelectWiFiOnboardingFragment.this.timeoutHandler.removeMessages(1);
                                QBSelectWiFiOnboardingFragment.this.wifiManager.startScan();
                                return;
                            }
                            if (!TextUtils.isEmpty((CharSequence)QBSelectWiFiOnboardingFragment.this.config.getSsid())) {
                                QBSelectWiFiOnboardingFragment.this.txtTitle.setText((CharSequence)String.format(QBSelectWiFiOnboardingFragment.this.getString(2131362335), QBSelectWiFiOnboardingFragment.this.config.getSsid()));
                            }
                            QBSelectWiFiOnboardingFragment.this.qardioBaseManager.readWifiState();
                            return;
                        }
                        case 1: {
                            n = intent.getIntExtra("com.qardio.base.DATA", 0);
                            if (n == 0) {
                                QBSelectWiFiOnboardingFragment.access$808(QBSelectWiFiOnboardingFragment.this);
                                if (QBSelectWiFiOnboardingFragment.this.wifiCounter >= 2) {
                                    QBSelectWiFiOnboardingFragment.this.refreshLayout.setOnRefreshListener(QBSelectWiFiOnboardingFragment$3$$Lambda$1.lambdaFactory$(this));
                                    QBSelectWiFiOnboardingFragment.this.timeoutHandler.removeMessages(1);
                                    QBSelectWiFiOnboardingFragment.this.isWifiConfigVerificationInProgress = false;
                                    QBSelectWiFiOnboardingFragment.this.wifiManager.startScan();
                                    LocalBroadcastManager.getInstance((Context)QBSelectWiFiOnboardingFragment.this.getActivity()).unregisterReceiver(QBSelectWiFiOnboardingFragment.this.qbWifiStateReceiver);
                                    return;
                                }
                                QBSelectWiFiOnboardingFragment.this.bleCmdHandler.postDelayed(QBSelectWiFiOnboardingFragment$3$$Lambda$2.lambdaFactory$(this), TimeUnit.SECONDS.toMillis(QBSelectWiFiOnboardingFragment.this.wifiCounter * 5));
                                return;
                            }
                            if (n != 6) return;
                            QBSelectWiFiOnboardingFragment.this.refreshLayout.setRefreshing(false);
                            if (!TextUtils.isEmpty((CharSequence)QBSelectWiFiOnboardingFragment.this.config.getSsid())) {
                                QBSelectWiFiOnboardingFragment.this.txtTitle.setText((CharSequence)String.format(QBSelectWiFiOnboardingFragment.this.getString(2131362336), QBSelectWiFiOnboardingFragment.this.config.getSsid()));
                            }
                            QBSelectWiFiOnboardingFragment.this.wifiConnectedLayout.setVisibility(0);
                            QBSelectWiFiOnboardingFragment.this.timeoutHandler.removeMessages(1);
                            QBSelectWiFiOnboardingFragment.this.isWifiConfigVerificationInProgress = false;
                            QBSelectWiFiOnboardingFragment.this.bleCmdHandler.postDelayed(QBSelectWiFiOnboardingFragment$3$$Lambda$3.lambdaFactory$(this), 5000L);
                            return;
                        }
                    }
                }
            }
        };
        this.onFooterClickListener = QBSelectWiFiOnboardingFragment$$Lambda$1.lambdaFactory$();
        this.itemClickListener = QBSelectWiFiOnboardingFragment$$Lambda$2.lambdaFactory$(this);
        this.passwordTextWatcher = new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                if (editable.length() > 5) {
                    QBSelectWiFiOnboardingFragment.this.doneButton.setEnabled(true);
                    return;
                }
                QBSelectWiFiOnboardingFragment.this.doneButton.setEnabled(false);
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        };
    }

    static /* synthetic */ int access$808(QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment) {
        int n = qBSelectWiFiOnboardingFragment.wifiCounter;
        qBSelectWiFiOnboardingFragment.wifiCounter = n + 1;
        return n;
    }

    private void addToScanResult(String string2, WifiSecurityState wifiSecurityState, int n) {
        boolean bl = false;
        Iterator<WifiAp> iterator = this.scanResults.iterator();
        while (iterator.hasNext()) {
            if (!string2.equals(iterator.next().ssid)) continue;
            bl = true;
        }
        if (!bl) {
            this.scanResults.add(new WifiAp(string2, wifiSecurityState, n));
        }
    }

    private View createFooterView(ViewGroup viewGroup) {
        viewGroup = ((LayoutInflater)this.getActivity().getSystemService("layout_inflater")).inflate(2130968861, viewGroup, false);
        TextView textView = (TextView)viewGroup.findViewById(2131821454);
        textView.setText(2131362340);
        textView.setGravity(17);
        return viewGroup;
    }

    private void forceKeyboardToDismiss() {
        if (this.getActivity() != null) {
            ((InputMethodManager)this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.etPassword.getWindowToken(), 0);
        }
    }

    private void forceKeyboardToPopUp() {
        if (this.getActivity() != null) {
            ((InputMethodManager)this.getActivity().getSystemService("input_method")).toggleSoftInput(2, 1);
        }
    }

    private String getSSIDFromPhoneWifiAP() {
        return ((WifiManager)this.getActivity().getApplicationContext().getSystemService("wifi")).getConnectionInfo().getSSID();
    }

    private void handleWifiResults(List<ScanResult> object) {
        String string2 = this.getSSIDFromPhoneWifiAP();
        this.refreshLayout.setRefreshing(false);
        if (this.scanResults == null) {
            this.scanResults = new ArrayList<WifiAp>();
        }
        object = object.iterator();
        while (object.hasNext()) {
            ScanResult scanResult = (ScanResult)object.next();
            if (scanResult.frequency >= 3000) continue;
            String string3 = scanResult.SSID;
            WifiSecurityState wifiSecurityState = WifiAp.getSecurityString(scanResult);
            int n = scanResult.level;
            if (!TextUtils.isEmpty((CharSequence)string3) && string2.equals(string3) && !this.preselectedWifiDialogShown) {
                this.showPreseledtedWifDialog(new WifiAp(string3, wifiSecurityState, n));
                this.preselectedWifiDialogShown = true;
            }
            this.addToScanResult(string3, wifiSecurityState, n);
        }
        this.adapter.clear();
        this.adapter.notifyDataSetChanged();
        this.adapter.addAll(this.scanResults);
        this.adapter.notifyDataSetChanged();
        if (this.footerView == null) {
            this.footerView = this.createFooterView((ViewGroup)this.listView);
            this.footerView.setOnClickListener(this.onFooterClickListener);
            this.listView.addFooterView(this.footerView);
        }
        if (this.getArguments() != null && this.getArguments().containsKey("EXTRA_FROM_SETTINGS")) {
            this.rootView.findViewById(2131821283).setVisibility(8);
            return;
        }
        this.rootView.findViewById(2131821283).setVisibility(0);
    }

    private boolean isWifiCheckNeeded() {
        Bundle bundle = this.getArguments();
        return bundle != null && bundle.containsKey("EXTRA_WIFI_CHECK_NEEDED") && bundle.getBoolean("EXTRA_WIFI_CHECK_NEEDED");
    }

    static /* synthetic */ void lambda$new$0(View view) {
    }

    public static QBSelectWiFiOnboardingFragment newInstance(Bundle bundle) {
        QBSelectWiFiOnboardingFragment qBSelectWiFiOnboardingFragment = new QBSelectWiFiOnboardingFragment();
        qBSelectWiFiOnboardingFragment.setArguments(bundle);
        return qBSelectWiFiOnboardingFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showPreseledtedWifDialog(WifiAp wifiAp) {
        this.forceKeyboardToPopUp();
        View view = this.getActivity().getLayoutInflater().inflate(2130968732, null);
        AlertDialog.Builder builder = new AlertDialog.Builder((Context)this.getActivity(), 2131493366);
        builder.setCancelable(true);
        builder.setOnDismissListener(QBSelectWiFiOnboardingFragment$$Lambda$6.lambdaFactory$(this));
        builder.setView(view);
        this.dialog = builder.create();
        this.dialog.setCanceledOnTouchOutside(true);
        this.etPassword = (EditText)view.findViewById(2131821112);
        this.etPassword.addTextChangedListener(this.passwordTextWatcher);
        builder = view.findViewById(2131821111);
        this.doneButton = (Button)view.findViewById(2131821113);
        this.doneButton.setOnClickListener(QBSelectWiFiOnboardingFragment$$Lambda$7.lambdaFactory$(this, wifiAp));
        ((TextView)view.findViewById(2131821114)).setOnClickListener(QBSelectWiFiOnboardingFragment$$Lambda$8.lambdaFactory$(this));
        view = (TextView)view.findViewById(2131821110);
        if (wifiAp.sec == WifiSecurityState.SECURE) {
            view.setText((CharSequence)this.getString(2131362423, new Object[]{"\"" + wifiAp.ssid + "\""}));
        } else {
            this.etPassword.setVisibility(8);
            builder.setVisibility(8);
            this.doneButton.setEnabled(true);
            view.setText((CharSequence)this.getString(2131362421, new Object[]{"\"" + wifiAp.ssid + "\""}));
        }
        this.dialog.show();
    }

    /* synthetic */ void lambda$new$8(AdapterView adapterView, View view, int n, long l) {
        this.showPreseledtedWifDialog((WifiAp)adapterView.getItemAtPosition(n));
        this.forceKeyboardToPopUp();
    }

    /* synthetic */ void lambda$null$4() {
        if (this.getActivity() != null && this.getActivity().getCurrentFocus() != null) {
            ((InputMethodManager)this.getActivity().getSystemService("input_method")).hideSoftInputFromWindow(this.getActivity().getCurrentFocus().getWindowToken(), 0);
        }
    }

    /* synthetic */ void lambda$onCreateView$1(View view) {
        this.callback.onSkippedWifiConfiguration();
        this.callback.onDonePressed();
    }

    /* synthetic */ void lambda$onStart$2() {
        this.refreshLayout.setRefreshing(true);
    }

    /* synthetic */ void lambda$onViewCreated$3() {
        this.wifiManager.startScan();
    }

    /* synthetic */ void lambda$showPreseledtedWifDialog$5(DialogInterface dialogInterface) {
        new Handler().postDelayed(QBSelectWiFiOnboardingFragment$$Lambda$9.lambdaFactory$(this), 50L);
    }

    /*
     * Enabled aggressive block sorting
     */
    /* synthetic */ void lambda$showPreseledtedWifDialog$6(WifiAp wifiAp, View view) {
        this.refreshLayout.setEnabled(false);
        this.forceKeyboardToDismiss();
        if (wifiAp.sec == WifiSecurityState.SECURE) {
            this.callback.onConfigureWifiAp(wifiAp, this.etPassword.getText().toString());
        } else {
            this.callback.onConfigureWifiAp(wifiAp, null);
        }
        this.callback.onDonePressed();
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    /* synthetic */ void lambda$showPreseledtedWifDialog$7(View view) {
        this.forceKeyboardToDismiss();
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.callback = (Callback)this.getParentFragment();
        this.qardioBaseManager = new QardioBaseManager((Context)this.getActivity());
    }

    /*
     * Enabled aggressive block sorting
     */
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968815, viewGroup, false);
        layoutInflater = this.getArguments();
        if (layoutInflater != null && layoutInflater.containsKey("EXTRA_FROM_SETTINGS")) {
            this.rootView.findViewById(2131821283).setVisibility(8);
        } else {
            this.rootView.findViewById(2131821283).setVisibility(4);
            this.rootView.findViewById(2131821109).setOnClickListener(QBSelectWiFiOnboardingFragment$$Lambda$3.lambdaFactory$(this));
        }
        if ((layoutInflater = (TextView)this.getActivity().findViewById(2131820997)) != null) {
            layoutInflater.setVisibility(4);
        }
        this.txtTitle = (TextView)this.rootView.findViewById(2131820567);
        return this.rootView;
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.isWifiCheckNeeded()) {
            LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.qbWifiStateReceiver);
        }
        if (this.dialog != null && this.dialog.isShowing()) {
            this.dialog.dismiss();
        }
        this.callback = null;
        this.timeoutHandler.removeMessages(1);
        this.timeoutHandler = null;
        this.isWifiConfigVerificationInProgress = false;
    }

    public void onPause() {
        super.onPause();
        this.getActivity().unregisterReceiver(this.wifiStateReceiver);
        this.refreshLayout.setRefreshing(false);
    }

    public void onResume() {
        super.onResume();
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.net.wifi.SCAN_RESULTS");
        this.getActivity().registerReceiver(this.wifiStateReceiver, intentFilter);
        if (!this.isWifiCheckNeeded()) {
            this.wifiManager.startScan();
        }
    }

    public void onStart() {
        super.onStart();
        this.wifiManager = (WifiManager)this.getActivity().getApplicationContext().getSystemService("wifi");
        this.refreshLayout.post(QBSelectWiFiOnboardingFragment$$Lambda$4.lambdaFactory$(this));
    }

    public void onStop() {
        if (this.menu != null) {
            this.menu.clear();
        }
        super.onStop();
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.wifiConnectedLayout = view.findViewById(2131821284);
        this.listView = (ListView)this.rootView.findViewById(2131821288);
        this.adapter = new QBWiFiListAdapter((Context)this.getActivity(), 2131821288, new ArrayList<WifiAp>());
        this.listView.setAdapter((ListAdapter)this.adapter);
        this.listView.setOnItemClickListener(this.itemClickListener);
        this.refreshLayout = (SwipeRefreshLayout)this.rootView.findViewById(2131821287);
        this.refreshLayout.setOnRefreshListener(QBSelectWiFiOnboardingFragment$$Lambda$5.lambdaFactory$(this));
        if (this.isWifiCheckNeeded()) {
            this.txtTitle.setText((CharSequence)this.getString(2131362335));
            view = new IntentFilter();
            view.addAction("com.qardio.base.QB_WIFI_STATE");
            view.addAction("com.qardio.base.QB_WIFI_CONFIG");
            this.isWifiConfigVerificationInProgress = true;
            LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.qbWifiStateReceiver, (IntentFilter)view);
            this.qardioBaseManager.readWifiConfig();
            this.timeoutHandler.sendMessageDelayed(Message.obtain((Handler)this.timeoutHandler, (int)1), 12000L);
            this.refreshLayout.setOnRefreshListener(null);
        }
    }

    public static interface Callback {
        public void onConfigureWifiAp(WifiAp var1, String var2);

        public void onDonePressed();

        public void onSkippedWifiConfiguration();
    }

}

