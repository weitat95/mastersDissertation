/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.ListAdapter
 *  android.widget.ListView
 */
package com.getqardio.android.ui.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListAdapter;
import android.widget.ListView;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.adapter.LocationTagsAdapter;
import com.getqardio.android.utils.ui.ActivityUtils;

public class LocationTagsListActivity
extends BaseActivity {
    private LocationTagsAdapter adapter;

    public static Intent getStartIntent(Context context, int n) {
        context = new Intent(context, LocationTagsListActivity.class);
        context.putExtra("com.getqardio.android.extras.SELECTED_TAG", n);
        return context;
    }

    @Override
    public void onBackPressed() {
        this.finish();
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968740);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        int n = 0;
        bundle = this.getIntent();
        if (bundle != null) {
            n = bundle.getIntExtra("com.getqardio.android.extras.SELECTED_TAG", 0);
        }
        this.adapter = new LocationTagsAdapter((Context)this, n);
        bundle = (ListView)this.findViewById(2131821128);
        bundle.setAdapter((ListAdapter)this.adapter);
        bundle.setOnItemClickListener(new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> intent, View view, int n, long l) {
                LocationTagsListActivity.this.adapter.setSelectedTag(LocationTagsListActivity.this.adapter.getItem(n));
                LocationTagsListActivity.this.adapter.notifyDataSetChanged();
                intent = new Intent();
                intent.putExtra("com.getqardio.android.extras.SELECTED_TAG", LocationTagsListActivity.this.adapter.getSelectedTag());
                LocationTagsListActivity.this.setResult(-1, intent);
            }
        });
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

