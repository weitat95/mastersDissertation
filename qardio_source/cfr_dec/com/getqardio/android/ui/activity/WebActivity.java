/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.http.SslError
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.view.MenuItem
 *  android.view.View
 *  android.webkit.SslErrorHandler
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.webkit.WebViewClient
 */
package com.getqardio.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.net.http.SslError;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.ActivityUtils;

public class WebActivity
extends BaseActivity {
    private int enterAnim;
    private View errorText;
    private int exitAnim;
    private boolean showBackButton;
    private String url;
    private WebView webView;

    public static Intent getStartIntent(Context context, String string2, String string3, boolean bl, int n, int n2) {
        context = new Intent(context, WebActivity.class);
        context.putExtra("com.getqardio.android.extra.URL", string3);
        context.putExtra("com.getqardio.android.extra.TITLE", string2);
        context.putExtra("com.getqardio.android.extra.SHOW_BACK", bl);
        context.putExtra("com.getqardio.android.extra.ENTER_ANIM", n);
        context.putExtra("com.getqardio.android.extra.EXIT_ANIM", n2);
        return context;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void initComponents() {
        this.webView = (WebView)this.findViewById(2131821399);
        this.webView.getSettings().setJavaScriptEnabled(true);
        WebSettings webSettings = this.webView.getSettings();
        int n = Utils.isNetworkAvaliable((Context)this) ? -1 : 1;
        webSettings.setCacheMode(n);
    }

    @Override
    public void onBackPressed() {
        this.finish();
        if (this.exitAnim > -1) {
            this.overridePendingTransition(this.enterAnim, this.exitAnim);
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968849);
        this.initComponents();
        bundle = this.getIntent();
        if (bundle != null) {
            this.url = bundle.getStringExtra("com.getqardio.android.extra.URL");
            this.showBackButton = bundle.getBooleanExtra("com.getqardio.android.extra.SHOW_BACK", false);
            ActionBar actionBar = ActivityUtils.getActionBar(this);
            actionBar.setTitle(bundle.getStringExtra("com.getqardio.android.extra.TITLE"));
            if (this.showBackButton) {
                actionBar.setDisplayHomeAsUpEnabled(true);
            }
            this.enterAnim = bundle.getIntExtra("com.getqardio.android.extra.ENTER_ANIM", -1);
            this.exitAnim = bundle.getIntExtra("com.getqardio.android.extra.EXIT_ANIM", -1);
        }
        this.errorText = this.findViewById(2131821010);
        this.webView.setWebViewClient(new WebViewClient(){

            public void onReceivedError(WebView webView, int n, String string2, String string3) {
                WebActivity.this.errorText.setVisibility(0);
            }

            public void onReceivedSslError(WebView webView, SslErrorHandler sslErrorHandler, SslError sslError) {
            }
        });
        if (this.webView != null && !TextUtils.isEmpty((CharSequence)this.url)) {
            this.webView.loadUrl(this.url);
        }
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 16908332: 
        }
        this.onBackPressed();
        return true;
    }

}

