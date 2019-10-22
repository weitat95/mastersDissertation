/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Dialog
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.ContentResolver
 *  android.content.ContentValues
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.Loader
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.widget.TextView
 */
package com.getqardio.android.ui.activity;

import android.app.Dialog;
import android.app.LoaderManager;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.adapter.PregnancyGalleryAdapter;
import com.getqardio.android.ui.dialog.CustomAlertDialog;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.PregnancyUtils;
import java.io.File;
import java.io.Serializable;
import java.util.Date;

public class PregnancyGalleryActivity
extends BaseActivity
implements LoaderManager.LoaderCallbacks<Cursor>,
ViewPager.OnPageChangeListener {
    private PregnancyGalleryAdapter adapter;
    private Float[] gain;
    private TextView gainValue;
    private File[] photos;
    private long pregnancyStartDate;
    private float pregnancyStartWeight;
    private String shownImagePath;
    private long userId;
    private ViewPager viewPager;
    private TextView weeksCount;
    private int weightUnit;

    private void calculateWeightGain() {
        if (this.userId != -1L && this.photos.length > 0) {
            this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        }
    }

    public static Intent createStartIntent(Context context, long l, File[] arrfile, int n, long l2, float f, int n2) {
        context = new Intent(context, PregnancyGalleryActivity.class);
        context.putExtra("com.getqardio.android.extras.USER_ID", l);
        context.putExtra("com.getqardio.android.extras.PHOTOS", (Serializable)arrfile);
        context.putExtra("com.getqardio.android.extras.SELECTED_PHOTO_NUMBER", n);
        context.putExtra("com.getqardio.android.extras.PREGNANCY_START_DATE", l2);
        context.putExtra("com.getqardio.android.extras.PREGNANCY_START_WEIGHT", f);
        context.putExtra("com.getqardio.android.extras.PREGNANCY_WEIGHT_UNIT", n2);
        return context;
    }

    private void delete() {
        int n;
        block3: {
            block2: {
                n = this.viewPager.getCurrentItem();
                if (!this.photos[n].delete()) break block2;
                this.removePhotoFromAndroidGallery(this.photos[n]);
                if (this.photos.length != 1) break block3;
                this.finish();
            }
            return;
        }
        this.adapter.delete(n);
        Comparable<File>[] arrcomparable = new File[this.photos.length - 1];
        System.arraycopy(this.photos, 0, arrcomparable, 0, n);
        System.arraycopy(this.photos, n + 1, arrcomparable, n, this.photos.length - n - 1);
        this.photos = arrcomparable;
        arrcomparable = new Float[this.gain.length - 1];
        System.arraycopy(this.gain, 0, arrcomparable, 0, n);
        System.arraycopy(this.gain, n + 1, arrcomparable, n, this.gain.length - n - 1);
        this.gain = arrcomparable;
        this.onPageSelected(this.viewPager.getCurrentItem());
    }

    private Uri getImageContentUri(String string2) {
        Cursor cursor = this.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id"}, "_data=? ", new String[]{string2}, null);
        if (cursor != null && cursor.moveToFirst()) {
            int n = cursor.getInt(cursor.getColumnIndex("_id"));
            return Uri.withAppendedPath((Uri)MediaStore.Images.Media.EXTERNAL_CONTENT_URI, (String)("" + n));
        }
        cursor = new ContentValues();
        cursor.put("_data", string2);
        return this.getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, (ContentValues)cursor);
    }

    private void init(File[] arrfile, int n) {
        this.viewPager = (ViewPager)((Object)this.findViewById(2131821085));
        this.weeksCount = (TextView)this.findViewById(2131821086);
        this.gainValue = (TextView)this.findViewById(2131821087);
        this.adapter = new PregnancyGalleryAdapter((Context)this, arrfile);
        this.viewPager.setAdapter(this.adapter);
        this.viewPager.setCurrentItem(n);
        this.shownImagePath = arrfile[n].getAbsolutePath();
        this.updateUI(n);
    }

    private void removePhotoFromAndroidGallery(File file) {
        Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
        intent.setData(Uri.fromFile((File)file));
        this.sendBroadcast(intent);
    }

    private void share() {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.SEND");
        intent.setType("image/*");
        intent.putExtra("android.intent.extra.STREAM", (Parcelable)this.getImageContentUri(this.shownImagePath));
        this.startActivity(Intent.createChooser((Intent)intent, (CharSequence)this.getResources().getText(2131362362)));
    }

    private void updateGainValue(int n) {
        if (this.gain[n] != null) {
            float f = MetricUtils.convertWeight(0, this.weightUnit, this.gain[n].floatValue());
            this.gainValue.setText((CharSequence)String.format("%+.1f %2$s", Float.valueOf(f), this.getString(MetricUtils.getWeightUnitNameRes(this.weightUnit))));
            this.gainValue.setVisibility(0);
            return;
        }
        this.gainValue.setVisibility(8);
    }

    private void updateUI(int n) {
        this.updateWeekNumber(n);
        this.updateGainValue(n);
    }

    private void updateWeekNumber(int n) {
        if ((n = PregnancyUtils.calculateWeekOfPregnancy(new Date(this.pregnancyStartDate), new Date(this.photos[n].lastModified()))) > 0) {
            this.weeksCount.setText((CharSequence)this.getString(2131362405, new Object[]{n}));
            this.weeksCount.setVisibility(0);
            return;
        }
        this.weeksCount.setVisibility(8);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.setContentView(2130968721);
        int n = 0;
        bundle = this.getIntent();
        if (bundle != null) {
            this.userId = bundle.getLongExtra("com.getqardio.android.extras.USER_ID", -1L);
            this.pregnancyStartDate = bundle.getLongExtra("com.getqardio.android.extras.PREGNANCY_START_DATE", System.currentTimeMillis());
            this.pregnancyStartWeight = bundle.getFloatExtra("com.getqardio.android.extras.PREGNANCY_START_WEIGHT", 0.0f);
            Object[] arrobject = (Object[])bundle.getSerializableExtra("com.getqardio.android.extras.PHOTOS");
            this.photos = new File[arrobject.length];
            System.arraycopy(arrobject, 0, this.photos, 0, this.photos.length);
            n = bundle.getIntExtra("com.getqardio.android.extras.SELECTED_PHOTO_NUMBER", 0);
            this.weightUnit = bundle.getIntExtra("com.getqardio.android.extras.PREGNANCY_WEIGHT_UNIT", 0);
        } else {
            this.userId = -1L;
            this.pregnancyStartDate = System.currentTimeMillis();
            this.pregnancyStartWeight = 0.0f;
            this.photos = new File[0];
            this.weightUnit = 0;
        }
        this.gain = new Float[this.photos.length];
        this.init(this.photos, n);
        this.calculateWeightGain();
    }

    protected Dialog onCreateDialog(int n) {
        if (n == 1) {
            CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance((Context)this, this.getString(2131361898), this.getString(2131361901));
            customAlertDialog.setCancelable(false);
            customAlertDialog.setOnNegativeClickListener(null);
            customAlertDialog.setOnClickListener(new DialogInterface.OnClickListener(){

                public void onClick(DialogInterface dialogInterface, int n) {
                    PregnancyGalleryActivity.this.delete();
                }
            });
            return customAlertDialog;
        }
        return super.onCreateDialog(n);
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        if (n == 1) {
            return MeasurementHelper.Weight.getMeasurementsLoaderForPeriod((Context)this, this.userId, this.pregnancyStartDate, this.photos[this.photos.length - 1].lastModified(), MeasurementHelper.Weight.PREGNANCY_GALLERY_PROJECTION, "measure_date ASC");
        }
        return null;
    }

    public boolean onCreateOptionsMenu(Menu menu2) {
        this.getMenuInflater().inflate(2131886090, menu2);
        return true;
    }

    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        int n = cursor.getColumnIndex("measure_date");
        int n2 = cursor.getColumnIndex("weight");
        for (int i = 0; i < this.photos.length; ++i) {
            long l = this.photos[i].lastModified();
            cursor.moveToFirst();
            while (!cursor.isAfterLast() && cursor.getLong(n) <= l) {
                cursor.moveToNext();
            }
            cursor.moveToPrevious();
            if (cursor.isBeforeFirst()) continue;
            this.gain[i] = Float.valueOf(cursor.getFloat(n2) - this.pregnancyStartWeight);
        }
        this.updateGainValue(this.viewPager.getCurrentItem());
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            default: {
                return super.onOptionsItemSelected(menuItem);
            }
            case 2131821488: {
                this.share();
                return true;
            }
            case 2131821472: 
        }
        this.showDialog(1);
        return true;
    }

    @Override
    public void onPageScrollStateChanged(int n) {
    }

    @Override
    public void onPageScrolled(int n, float f, int n2) {
    }

    @Override
    public void onPageSelected(int n) {
        this.updateUI(n);
        this.shownImagePath = this.photos[n].getAbsolutePath();
    }

    @Override
    protected void onPause() {
        super.onPause();
        this.viewPager.removeOnPageChangeListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        this.viewPager.addOnPageChangeListener(this);
    }

}

