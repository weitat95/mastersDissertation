/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.ImageView
 */
package com.getqardio.android.shopify.view.collections;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProviders;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.bumptech.glide.BitmapTypeRequest;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.shopify.domain.model.Collection;
import com.getqardio.android.shopify.view.BasePaginatedListViewModel;
import com.getqardio.android.shopify.view.ScreenRouter;
import com.getqardio.android.shopify.view.cart.CartClickActionEvent;
import com.getqardio.android.shopify.view.collections.CollectionListActivity$$Lambda$1;
import com.getqardio.android.shopify.view.collections.CollectionListView;
import com.getqardio.android.shopify.view.collections.RealCollectionListViewModel;
import com.getqardio.android.ui.activity.MainActivity;

public final class CollectionListActivity
extends AppCompatActivity {
    @BindView
    CollectionListView collectionListView;
    @BindView
    Toolbar toolbarView;

    private void initViewModels() {
        RealCollectionListViewModel realCollectionListViewModel = ViewModelProviders.of(this).get(RealCollectionListViewModel.class);
        this.collectionListView.bindViewModel(realCollectionListViewModel);
    }

    /* synthetic */ void lambda$onCreateOptionsMenu$0(View view) {
        ScreenRouter.route((Context)this, new CartClickActionEvent());
    }

    @Override
    public void onBackPressed() {
        Long l = CustomApplication.getApplication().getCurrentUserId();
        if (l != null && l > 0L) {
            this.startActivity(MainActivity.getStartIntent((Context)this, true));
            this.finish();
            return;
        }
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968605);
        ButterKnife.bind(this);
        Glide.with(this).load(2130837603).asBitmap().into((ImageView)this.findViewById(2131820766));
        this.setSupportActionBar(this.toolbarView);
        this.getSupportActionBar().setTitle(2131362486);
        this.getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        this.getSupportActionBar().setDisplayShowHomeEnabled(true);
        this.initViewModels();
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131886087, menu2);
        menu2.findItem(2131821475).getActionView().setOnClickListener(CollectionListActivity$$Lambda$1.lambdaFactory$(this));
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        this.onBackPressed();
        return true;
    }
}

