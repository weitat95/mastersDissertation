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
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.Settings;
import com.getqardio.android.provider.DataHelper;

public class NotificationsWelcomeFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor> {
    private View rootView;
    private Settings settings;

    private void closeActivity() {
        this.getActivity().finish();
    }

    public static NotificationsWelcomeFragment newInstance() {
        return new NotificationsWelcomeFragment();
    }

    private void parseSettings(Cursor cursor) {
        if (cursor != null && cursor.moveToFirst()) {
            this.settings = DataHelper.SettingsHelper.parseSettings(cursor);
        }
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.setHasOptionsMenu(true);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
        }
        return DataHelper.SettingsHelper.getSettingsLoader((Context)this.getActivity(), CustomApplication.getApplication().getCurrentUserId(), null);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menuInflater.inflate(2131886089, menu2);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968713, viewGroup, false);
        return this.rootView;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (loader.getId() == 1) {
            this.parseSettings(cursor);
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return false;
            }
            case 2131821487: 
        }
        this.closeActivity();
        return true;
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        ((Button)this.rootView.findViewById(2131821059)).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                if (NotificationsWelcomeFragment.this.settings != null) {
                    NotificationsWelcomeFragment.access$000((NotificationsWelcomeFragment)NotificationsWelcomeFragment.this).allowNotifications = true;
                    DataHelper.SettingsHelper.saveSettings((Context)NotificationsWelcomeFragment.this.getActivity(), NotificationsWelcomeFragment.this.settings, false);
                }
                NotificationsWelcomeFragment.this.closeActivity();
            }
        });
    }

}

