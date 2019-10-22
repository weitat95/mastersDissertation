/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.Context
 *  android.content.Loader
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Paint
 *  android.os.Bundle
 *  android.text.Html
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.webkit.WebSettings
 *  android.webkit.WebView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Paint;
import android.os.Bundle;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;
import com.getqardio.android.datamodel.Faq;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.Utils;

public class FaqDetailsFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor> {
    private WebView faqAnswer;
    private TextView faqQuestion;
    private boolean isNetworkEnabled;

    private void clearContent() {
        this.faqQuestion.setText((CharSequence)"");
        this.faqAnswer.loadUrl("about:blank");
    }

    private long getFaqId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.FAQ_ID")) {
            return bundle.getLong("com.getqardio.android.argument.FAQ_ID");
        }
        return -1L;
    }

    private void init(View view) {
        this.faqQuestion = (TextView)view.findViewById(2131821047);
        this.faqAnswer = (WebView)view.findViewById(2131821048);
        this.faqAnswer.setLayerType(1, null);
        this.faqAnswer.setBackgroundColor(this.getResources().getColor(2131689552));
        this.getResources().getDimension(2131427673);
        view = this.faqAnswer.getSettings();
        view.setCacheMode(2);
        view.setAppCacheEnabled(false);
        view.setBlockNetworkImage(false);
        view.setLoadsImagesAutomatically(true);
        view.setGeolocationEnabled(false);
        view.setNeedInitialFocus(false);
        view.setSaveFormData(false);
    }

    public static FaqDetailsFragment newInstance(long l) {
        Bundle bundle = new Bundle(1);
        bundle.putLong("com.getqardio.android.argument.FAQ_ID", l);
        FaqDetailsFragment faqDetailsFragment = new FaqDetailsFragment();
        faqDetailsFragment.setArguments(bundle);
        return faqDetailsFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setContent(Cursor object) {
        if (object.moveToFirst()) {
            object = DataHelper.FaqHelper.parseFaq((Cursor)object);
            this.faqQuestion.setText((CharSequence)Html.fromHtml((String)((Faq)object).question));
            object = !this.isNetworkEnabled ? ((Faq)object).answer.replaceAll("<a .*<img.*</a>", "") : ((Faq)object).answer;
            object = String.format("<html><head><meta name='viewport' content='target-densityDpi=device-dpi'/></head><body><p style=\" font-family:sans-serif-light;color:#4c4d4e;align=\\\"justify\\\">%s</p></body></html>", ((String)object).replace("\\n", "<br>"));
            this.faqAnswer.loadData((String)object, "text/html; charset=UTF-8", "utf-8");
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        this.isNetworkEnabled = Utils.isNetworkAvaliable((Context)this.getActivity());
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
        }
        return DataHelper.FaqHelper.getFaqLoader((Context)this.getActivity(), this.getFaqId(), DataHelper.FaqHelper.FAQ_DETAILS_PROJECTION);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968704, viewGroup, false);
        this.init((View)layoutInflater);
        return layoutInflater;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: 
        }
        this.setContent(cursor);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
        switch (loader.getId()) {
            default: {
                return;
            }
            case 1: 
        }
        this.clearContent();
    }
}

