/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.view.MenuItem
 */
package com.getqardio.android.ui.activity;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.fragment.EditNoteFragment;
import com.getqardio.android.utils.ui.ActivityUtils;

public class EditNoteActivity
extends BaseActivity
implements EditNoteFragment.OnNoteEditedListener {
    private EditNoteFragment editNoteFragment;

    public static String extractNoteFromIntent(Intent intent) {
        if (intent.hasExtra("com.getqardio.android.extras.NOTE")) {
            return intent.getStringExtra("com.getqardio.android.extras.NOTE");
        }
        return "";
    }

    private Intent generateResultIntent(String string2) {
        Intent intent = new Intent();
        intent.putExtra("com.getqardio.android.extras.NOTE", string2);
        return intent;
    }

    public static Intent getStartIntent(Context context, long l, String string2, String string3) {
        context = new Intent(context, EditNoteActivity.class);
        context.putExtra("com.getqardio.android.extras.USER_ID", l);
        context.putExtra("com.getqardio.android.extras.NOTE", string2);
        context.putExtra("com.getqardio.android.extras.MEASUREMENT_TYPE", string3);
        return context;
    }

    @Override
    public void onBackPressed() {
        if (this.editNoteFragment.textOfNoteEntered()) {
            super.onBackPressed();
        }
    }

    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968695);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        if (bundle == null) {
            bundle = this.getIntent();
            if (bundle != null && bundle.hasExtra("com.getqardio.android.extras.USER_ID") && bundle.hasExtra("com.getqardio.android.extras.NOTE")) {
                this.editNoteFragment = EditNoteFragment.newInstance(bundle.getLongExtra("com.getqardio.android.extras.USER_ID", -1L), bundle.getStringExtra("com.getqardio.android.extras.NOTE"), bundle.getStringExtra("com.getqardio.android.extras.MEASUREMENT_TYPE"));
                bundle = this.getFragmentManager().beginTransaction();
                bundle.add(2131820778, (Fragment)this.editNoteFragment);
            }
            return;
            finally {
                bundle.commit();
            }
        }
        this.editNoteFragment = (EditNoteFragment)this.getFragmentManager().findFragmentById(2131820778);
    }

    @Override
    public void onNoteEdited(String string2) {
        this.setResult(-1, this.generateResultIntent(string2));
        this.finish();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                do {
                    return super.onOptionsItemSelected(menuItem);
                    break;
                } while (true);
            }
            case 16908332: 
        }
        this.onBackPressed();
        return super.onOptionsItemSelected(menuItem);
    }
}

