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
 *  android.database.Cursor
 *  android.graphics.Typeface
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.getqardio.android.datamodel.Tooltip;
import com.getqardio.android.provider.TooltipHelper;
import com.getqardio.android.utils.engagement.EngagementScreenItem;
import com.getqardio.android.utils.engagement.EngagementScreenItemsHelper;
import com.getqardio.android.utils.ui.ActivityUtils;
import com.getqardio.android.utils.ui.CustomFonts;
import io.reactivex.disposables.CompositeDisposable;
import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;

public class EngagementScreenFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor> {
    private Callback callback;
    private Runnable closeFragmentRunnable;
    private ImageView engagementPicture;
    private Hashtable<String, TextView> engagementTexts;
    private Handler handler;
    private boolean hasTooltip = false;
    private View rootView;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private Tooltip tooltip;

    public EngagementScreenFragment() {
        this.closeFragmentRunnable = new Runnable(){

            @Override
            public void run() {
                if (EngagementScreenFragment.this.callback != null) {
                    EngagementScreenFragment.this.callback.onEngagementScreenClosed(false, false);
                }
            }
        };
    }

    static /* synthetic */ Tooltip access$200(EngagementScreenFragment engagementScreenFragment) {
        return engagementScreenFragment.tooltip;
    }

    private void initTextViews() {
        this.engagementTexts = new Hashtable(3);
        this.engagementTexts.put("top", (TextView)this.rootView.findViewById(2131821037));
        this.engagementTexts.put("middle", (TextView)this.rootView.findViewById(2131821038));
        this.engagementTexts.put("bottom", (TextView)this.rootView.findViewById(2131821039));
        this.engagementTexts.get("top").setTypeface(CustomFonts.getInstance().getTypeface((Context)this.getActivity(), "fonts/BrandonGrotesqueLight.otf"));
        this.engagementTexts.get("middle").setTypeface(CustomFonts.getInstance().getTypeface((Context)this.getActivity(), "fonts/BrandonGrotesqueLight.otf"));
        this.engagementTexts.get("bottom").setTypeface(CustomFonts.getInstance().getTypeface((Context)this.getActivity(), "fonts/BrandonGrotesqueLight.otf"));
    }

    public static EngagementScreenFragment newInstance(Callback callback) {
        EngagementScreenFragment engagementScreenFragment = new EngagementScreenFragment();
        engagementScreenFragment.setCallback(callback);
        return engagementScreenFragment;
    }

    private void parseTooltip(Cursor cursor) {
        this.tooltip = TooltipHelper.parseTooltip(cursor);
        this.getActivity().getLoaderManager().destroyLoader(1);
        if (this.tooltip != null) {
            ((DrawableRequestBuilder)Glide.with(this.getActivity()).load(this.tooltip.imageUrl).listener((RequestListener)new RequestListener<String, GlideDrawable>(){

                @Override
                public boolean onException(Exception exception, String string2, Target<GlideDrawable> target, boolean bl) {
                    EngagementScreenFragment.this.setLocalTooltip("bottom");
                    return false;
                }

                @Override
                public boolean onResourceReady(GlideDrawable glideDrawable, String string2, Target<GlideDrawable> target, boolean bl, boolean bl2) {
                    EngagementScreenFragment.this.setText(EngagementScreenFragment.access$200((EngagementScreenFragment)EngagementScreenFragment.this).textPosition, EngagementScreenFragment.access$200((EngagementScreenFragment)EngagementScreenFragment.this).title);
                    return false;
                }
            })).into(this.engagementPicture);
            return;
        }
        this.setLocalTooltip("bottom");
    }

    private TextView resetText(String string2) {
        Iterator<TextView> iterator = this.engagementTexts.values().iterator();
        while (iterator.hasNext()) {
            iterator.next().setVisibility(8);
        }
        return this.engagementTexts.get(string2.toLowerCase());
    }

    private void setLocalTooltip(String string2) {
        EngagementScreenItem engagementScreenItem = EngagementScreenItemsHelper.getRandomItem();
        this.engagementPicture.setImageResource(engagementScreenItem.getImageResource());
        this.setText(string2, engagementScreenItem.getStringResource());
    }

    private void setText(String string2, int n) {
        if (this.resetText(string2) != null) {
            this.engagementTexts.get(string2).setText(n);
            this.engagementTexts.get(string2).setVisibility(0);
        }
    }

    private void setText(String string2, String string3) {
        if ((string2 = this.resetText(string2)) != null) {
            string2.setText((CharSequence)string3);
            string2.setVisibility(0);
        }
    }

    private void startCloseFragmentHandler() {
        this.handler.postDelayed(this.closeFragmentRunnable, 5000L);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).hide();
        this.handler = new Handler();
        this.initTextViews();
        this.engagementPicture = (ImageView)this.rootView.findViewById(2131821036);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        return TooltipHelper.getRandomTooltipCursorLoader((Context)this.getActivity());
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968699, viewGroup, false);
        return this.rootView;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (!this.hasTooltip) {
            this.hasTooltip = true;
            if (cursor.moveToFirst()) {
                this.parseTooltip(cursor);
            } else {
                this.setLocalTooltip("bottom");
            }
            this.startCloseFragmentHandler();
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onPause() {
        super.onPause();
        this.subscriptions.dispose();
    }

    public void setCallback(Callback callback) {
        this.callback = callback;
    }

    public void stopHandler(boolean bl) {
        this.stopHandler(bl, true);
    }

    public void stopHandler(boolean bl, boolean bl2) {
        if (this.handler != null) {
            this.handler.removeCallbacks(this.closeFragmentRunnable);
            if (this.callback != null) {
                this.callback.onEngagementScreenClosed(bl2, bl);
            }
        }
    }

    public static interface Callback {
        public void onEngagementScreenClosed(boolean var1, boolean var2);
    }

}

