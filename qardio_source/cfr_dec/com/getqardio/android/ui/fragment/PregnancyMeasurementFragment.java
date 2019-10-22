/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.AlertDialog
 *  android.app.Fragment
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.AsyncTaskLoader
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.CursorLoader
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.content.Loader
 *  android.content.Loader$ForceLoadContentObserver
 *  android.content.res.Resources
 *  android.database.ContentObserver
 *  android.database.Cursor
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.FileObserver
 *  android.os.Parcelable
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 *  android.util.SparseArray
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.SeekBar
 *  android.widget.SeekBar$OnSeekBarChangeListener
 *  android.widget.TextView
 *  org.json.JSONException
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.AsyncTaskLoader;
import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.CursorLoader;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.Loader;
import android.content.res.Resources;
import android.database.ContentObserver;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.FileObserver;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import com.getqardio.android.datamodel.AverageWeightMeasurement;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.ui.WeightMeasurementCallback;
import com.getqardio.android.ui.activity.InviteUserActivity;
import com.getqardio.android.ui.activity.PregnancyGalleryActivity;
import com.getqardio.android.ui.adapter.GalleryAdapter;
import com.getqardio.android.ui.dialog.CustomAlertDialog;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment$$Lambda$3;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment$$Lambda$7;
import com.getqardio.android.ui.fragment.PregnancyMeasurementFragment$$Lambda$8;
import com.getqardio.android.ui.widget.HorizontalSpaceItemDecoration;
import com.getqardio.android.ui.widget.PregnancyChart;
import com.getqardio.android.ui.widget.PregnancyChartAdapter;
import com.getqardio.android.ui.widget.PregnancyChartAdapterImpl;
import com.getqardio.android.ui.widget.RecyclerViewItemClickListener;
import com.getqardio.android.ui.widget.ThumbTextSeekBar;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.PregnancyUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.QardioBaseDeviceAnalyticsTracker;
import com.getqardio.android.utils.cache.PregnancyGalleryCache;
import com.getqardio.android.utils.ui.ActivityUtils;
import io.reactivex.Completable;
import io.reactivex.CompletableTransformer;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import java.io.File;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import org.json.JSONException;
import timber.log.Timber;

public class PregnancyMeasurementFragment
extends Fragment
implements RecyclerViewItemClickListener.OnItemClickListener {
    private static IntentFilter MEASUREMENTS_INTENT_FILTER = new IntentFilter();
    private final String[] PROJECTION = new String[]{"_data"};
    private LoaderManager.LoaderCallbacks<Cursor> cursorLoaderCallback;
    private CompositeDisposable disposables = new CompositeDisposable();
    private GalleryAdapter galleryAdapter;
    private PregnancyGalleryCache galleryCache;
    private RecyclerView galleryView;
    private boolean isPregnancyDataLoaded = false;
    private WeightMeasurementCallback measurementCallback;
    private Date measurementDate;
    private long measurementPregnancyID = -1L;
    private boolean measurementReceived = false;
    private BroadcastReceiver measurementReceiver = new BroadcastReceiver(){

        /*
         * Enabled aggressive block sorting
         */
        public void onReceive(Context object, Intent intent) {
            object = intent.getAction();
            int n = -1;
            switch (((String)object).hashCode()) {
                case 1424235813: {
                    if (!((String)object).equals("com.qardio.base.CONNECTED")) break;
                    n = 0;
                    break;
                }
                case 421757567: {
                    if (!((String)object).equals("com.qardio.base.DISCONNECTED")) break;
                    n = 1;
                    break;
                }
                case -629657166: {
                    if (!((String)object).equals("com.qardio.base.QB_MEASUREMENT")) break;
                    n = 2;
                    break;
                }
            }
            switch (n) {
                case 0: {
                    PregnancyMeasurementFragment.this.measurementReceived = false;
                    return;
                }
                case 1: {
                    if (PregnancyMeasurementFragment.this.measurementReceived || PregnancyMeasurementFragment.this.measurementCallback == null) return;
                    {
                        PregnancyMeasurementFragment.this.measurementCallback.onMeasurementFailed();
                        return;
                    }
                }
                default: {
                    return;
                }
                case 2: {
                    PregnancyMeasurementFragment.this.measurementReceived = true;
                    object = intent.getStringExtra("com.qardio.base.DATA");
                    if (PregnancyMeasurementFragment.this.profile == null) return;
                    PregnancyMeasurementFragment.this.saveMeasurement((String)object);
                    if (PregnancyMeasurementFragment.this.measurementCallback == null) return;
                    PregnancyMeasurementFragment.this.measurementCallback.onMeasurementReceived();
                    return;
                }
            }
        }
    };
    private AlertDialog mlocationPermNeeededDialog;
    private int mode;
    private File photoPath;
    private File[] photosCache;
    private PhotosLoaderCallback photosLoaderCallback;
    private PregnancyChartAdapterImpl pregnancyChartAdapter;
    private Date pregnancyDueDate;
    private TextView pregnancyDueDateView;
    private TextView pregnancyDurationView;
    private Date pregnancyEndDate;
    private Date pregnancyStartDate;
    private TextView pregnancyTotalWeightGainView;
    private ThumbTextSeekBar pregnancyWeightGainSeekbar;
    private Profile profile;
    private float startWeight;
    private long userID;
    private LoaderManager.LoaderCallbacks<AverageWeightMeasurement[]> weightLoaderCallback = new LoaderManager.LoaderCallbacks<AverageWeightMeasurement[]>(){

        public Loader<AverageWeightMeasurement[]> onCreateLoader(int n, Bundle bundle) {
            if (n == 1) {
                return new DailyMeasurementsLoader((Context)PregnancyMeasurementFragment.this.getActivity(), PregnancyMeasurementFragment.this.userID, PregnancyMeasurementFragment.this.pregnancyStartDate, PregnancyMeasurementFragment.this.getActualEndDate());
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onLoadFinished(Loader<AverageWeightMeasurement[]> sparseArray, AverageWeightMeasurement[] arraverageWeightMeasurement) {
            if (sparseArray.getId() == 1) {
                PregnancyMeasurementFragment.this.weightMeasurements = new SparseArray();
                PregnancyMeasurementFragment.this.weightMeasurements.put(0, (Object)Float.valueOf(PregnancyMeasurementFragment.this.startWeight));
                sparseArray = new SparseArray();
                sparseArray.put(-1, (Object)Float.valueOf(MetricUtils.convertWeight(0, PregnancyMeasurementFragment.this.weightUnit, PregnancyMeasurementFragment.this.startWeight)));
                if (arraverageWeightMeasurement != null && arraverageWeightMeasurement.length > 0) {
                    for (AverageWeightMeasurement averageWeightMeasurement : arraverageWeightMeasurement) {
                        int n = PregnancyUtils.calculateMonthOfPregnancy(PregnancyMeasurementFragment.this.pregnancyStartDate, averageWeightMeasurement.measureDate);
                        int n2 = PregnancyUtils.calculateDayOfPregnancy(PregnancyMeasurementFragment.this.pregnancyStartDate, averageWeightMeasurement.measureDate);
                        PregnancyMeasurementFragment.this.weightMeasurements.put(n2, (Object)averageWeightMeasurement.weight);
                        sparseArray.put(n, (Object)Float.valueOf(MetricUtils.convertWeight(0, PregnancyMeasurementFragment.this.weightUnit, averageWeightMeasurement.weight.floatValue())));
                    }
                }
                PregnancyMeasurementFragment.this.isPregnancyDataLoaded = true;
                PregnancyMeasurementFragment.this.pregnancyChartAdapter.setWeight((SparseArray<Float>)sparseArray);
                if (PregnancyMeasurementFragment.this.mode == 2) {
                    PregnancyMeasurementFragment.this.showCurrentPregnancyWeight();
                } else {
                    PregnancyMeasurementFragment.this.showMeasurementDateInfo();
                }
                PregnancyMeasurementFragment.this.pregnancyWeightGainSeekbar.setEnabled(true);
            }
        }

        public void onLoaderReset(Loader<AverageWeightMeasurement[]> loader) {
            if (loader.getId() == 1) {
                PregnancyMeasurementFragment.this.pregnancyChartAdapter.setWeight(null);
            }
        }
    };
    private SparseArray<Float> weightMeasurements;
    private int weightUnit;

    static {
        MEASUREMENTS_INTENT_FILTER.addAction("com.qardio.base.QB_MEASUREMENT");
        MEASUREMENTS_INTENT_FILTER.addAction("com.qardio.base.STATE");
        MEASUREMENTS_INTENT_FILTER.addAction("com.qardio.base.CONNECTED");
        MEASUREMENTS_INTENT_FILTER.addAction("com.qardio.base.DISCONNECTED");
    }

    public PregnancyMeasurementFragment() {
        this.cursorLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>(){

            public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
                if (n == 2) {
                    if (PregnancyMeasurementFragment.this.mode == 1) {
                        return new CursorLoader((Context)PregnancyMeasurementFragment.this.getActivity(), DataHelper.PregnancyDataHelper.buildUriForUser(PregnancyMeasurementFragment.this.userID), null, "_id = ? ", new String[]{String.valueOf(PregnancyMeasurementFragment.this.measurementPregnancyID)}, null);
                    }
                    return new CursorLoader((Context)PregnancyMeasurementFragment.this.getActivity(), DataHelper.PregnancyDataHelper.buildUriForUser(PregnancyMeasurementFragment.this.userID), null, "end_date is null ", null, null);
                }
                if (n == 4) {
                    return DataHelper.ProfileHelper.getProfileLoader((Context)PregnancyMeasurementFragment.this.getActivity(), PregnancyMeasurementFragment.this.userID, null);
                }
                return null;
            }

            /*
             * Enabled aggressive block sorting
             */
            public void onLoadFinished(Loader<Cursor> object, Cursor cursor) {
                int n = object.getId();
                if (n == 2) {
                    if (!cursor.moveToFirst()) return;
                    {
                        PregnancyMeasurementFragment.this.pregnancyStartDate = new Date(cursor.getLong(cursor.getColumnIndex("start_date")));
                        PregnancyMeasurementFragment.this.pregnancyStartDate = DateUtils.getStartOfTheDay(PregnancyMeasurementFragment.this.pregnancyStartDate);
                        PregnancyMeasurementFragment.this.pregnancyDueDate = new Date(cursor.getLong(cursor.getColumnIndex("due_date")));
                        PregnancyMeasurementFragment.this.startWeight = cursor.getFloat(cursor.getColumnIndex("start_weight"));
                        n = cursor.getColumnIndex("end_date");
                        PregnancyMeasurementFragment pregnancyMeasurementFragment = PregnancyMeasurementFragment.this;
                        object = cursor.isNull(n) ? null : new Date(cursor.getLong(n));
                        pregnancyMeasurementFragment.pregnancyEndDate = (Date)object;
                        object = DateUtils.getCurrentDateFormat();
                        PregnancyMeasurementFragment.this.pregnancyDueDateView.setText((CharSequence)PregnancyMeasurementFragment.this.getString(2131362314, new Object[]{((DateFormat)object).format(PregnancyMeasurementFragment.this.pregnancyDueDate)}));
                        PregnancyMeasurementFragment.this.pregnancyWeightGainSeekbar.setMax(PregnancyMeasurementFragment.this.getPregnancyDurationInDays());
                        PregnancyMeasurementFragment.this.updateMeasurementsData();
                        return;
                    }
                } else {
                    if (n != 4 || !cursor.moveToFirst()) return;
                    {
                        PregnancyMeasurementFragment.this.profile = DataHelper.ProfileHelper.parseProfile(cursor);
                        return;
                    }
                }
            }

            public void onLoaderReset(Loader<Cursor> loader) {
            }
        };
    }

    private void addPhotosToAndroidGallery(File[] arrfile) {
        Activity activity = this.getActivity();
        if (activity != null) {
            for (File file : arrfile) {
                Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
                intent.setData(Uri.fromFile((File)file));
                activity.sendBroadcast(intent);
            }
        }
    }

    private void checkExternalStoragePermission(Context context) {
        if (ContextCompat.checkSelfPermission(context, "android.permission.WRITE_EXTERNAL_STORAGE") != 0) {
            this.galleryView.setVisibility(8);
            if (Build.VERSION.SDK_INT >= 23) {
                this.requestPermissions(new String[]{"android.permission.WRITE_EXTERNAL_STORAGE"}, 0);
            }
            return;
        }
        this.galleryView.setVisibility(0);
    }

    private Date getActualEndDate() {
        if (this.pregnancyEndDate != null) {
            return this.pregnancyEndDate;
        }
        Date date = new Date();
        if (this.pregnancyDueDate.after(date)) {
            date = this.pregnancyDueDate;
        }
        return DateUtils.getEndOfTheDay(date);
    }

    private int getPregnancyDurationInDays() {
        return Math.max(280, (int)TimeUnit.DAYS.convert(this.getActualEndDate().getTime() - this.pregnancyStartDate.getTime(), TimeUnit.MILLISECONDS));
    }

    static /* synthetic */ void lambda$processPhoto$5() throws Exception {
        Timber.d("Measurement photo saved", new Object[0]);
    }

    static /* synthetic */ void lambda$saveMeasurement$3() throws Exception {
        Timber.d("Measurement saved", new Object[0]);
    }

    public static PregnancyMeasurementFragment newInstance(long l, int n) {
        PregnancyMeasurementFragment pregnancyMeasurementFragment = new PregnancyMeasurementFragment();
        Bundle bundle = new Bundle(2);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putInt("com.getqardio.android.argument.WEIGHT_UNIT", n);
        pregnancyMeasurementFragment.setArguments(bundle);
        return pregnancyMeasurementFragment;
    }

    public static PregnancyMeasurementFragment newInstance(long l, int n, long l2, Date date) {
        PregnancyMeasurementFragment pregnancyMeasurementFragment = new PregnancyMeasurementFragment();
        Bundle bundle = new Bundle(3);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putInt("com.getqardio.android.argument.WEIGHT_UNIT", n);
        bundle.putLong("com.getqardio.android.argument.MEASUREMENT_PREGNANCY_ID", l2);
        if (date != null) {
            bundle.putLong("com.getqardio.android.argument.MEASUREMENT_DATE", date.getTime());
        }
        pregnancyMeasurementFragment.setArguments(bundle);
        return pregnancyMeasurementFragment;
    }

    private void processPhoto(File file, Uri uri) {
        Timber.d("processPhoto", new Object[0]);
        this.disposables.add(Completable.fromRunnable(PregnancyMeasurementFragment$$Lambda$6.lambdaFactory$(this, uri, file)).compose(RxUtil.applyCompletableSchedulers()).subscribe(PregnancyMeasurementFragment$$Lambda$7.lambdaFactory$(), PregnancyMeasurementFragment$$Lambda$8.lambdaFactory$()));
    }

    private void removePhotoFromAndroidGallery(File file) {
        Activity activity = this.getActivity();
        if (activity != null) {
            Intent intent = new Intent("android.intent.action.MEDIA_SCANNER_SCAN_FILE");
            intent.setData(Uri.fromFile((File)file));
            activity.sendBroadcast(intent);
        }
    }

    private void removePhotoFromGallery(int n) {
        if (this.photosCache[n].delete()) {
            this.galleryAdapter.onItemDismiss(n);
            this.removePhotoFromAndroidGallery(this.photosCache[n]);
            File[] arrfile = new File[this.photosCache.length - 1];
            System.arraycopy(this.photosCache, 0, arrfile, 0, n);
            System.arraycopy(this.photosCache, n + 1, arrfile, n, this.photosCache.length - n - 1);
            this.photosCache = arrfile;
        }
    }

    private void saveMeasurement(String string2) {
        Timber.d("saveMeasurement = " + string2, new Object[0]);
        this.disposables.add(Completable.fromRunnable(PregnancyMeasurementFragment$$Lambda$3.lambdaFactory$(this, string2)).compose(RxUtil.applyCompletableSchedulers()).subscribe(PregnancyMeasurementFragment$$Lambda$4.lambdaFactory$(), PregnancyMeasurementFragment$$Lambda$5.lambdaFactory$()));
    }

    private void showCurrentPregnancyWeight() {
        if (!this.isPregnancyDataLoaded) {
            return;
        }
        int n = (int)Math.ceil((float)PregnancyUtils.calculateDayOfPregnancy(this.pregnancyStartDate, new Date()) * 1.0f / (float)this.getPregnancyDurationInDays() * (float)this.pregnancyWeightGainSeekbar.getMax());
        if (this.pregnancyWeightGainSeekbar.getProgress() != n) {
            this.pregnancyWeightGainSeekbar.setProgress(n);
            return;
        }
        this.updateUI();
    }

    private void showDeleteConfirmationDialog(int n) {
        Object object = this.getActivity();
        if (object != null) {
            object = CustomAlertDialog.newInstance((Context)object, this.getString(2131361898), this.getString(2131361901));
            object.setCancelable(false);
            ((CustomAlertDialog)object).setOnNegativeClickListener(PregnancyMeasurementFragment$$Lambda$1.lambdaFactory$(this, n));
            ((CustomAlertDialog)object).setOnClickListener(PregnancyMeasurementFragment$$Lambda$2.lambdaFactory$(this, n));
            object.show();
        }
    }

    private void showMeasurementDateInfo() {
        if (!this.isPregnancyDataLoaded) {
            return;
        }
        int n = (int)Math.ceil((float)PregnancyUtils.calculateDayOfPregnancy(this.pregnancyStartDate, this.measurementDate) * 1.0f / (float)this.getPregnancyDurationInDays() * (float)this.pregnancyWeightGainSeekbar.getMax());
        if (this.pregnancyWeightGainSeekbar.getProgress() != n) {
            this.pregnancyWeightGainSeekbar.setProgress(n);
            return;
        }
        this.updateUI();
    }

    private void trackPhotoAddedEvent() {
        QardioBaseDeviceAnalyticsTracker.trackAddedPregnancyPhoto((Context)this.getActivity());
    }

    private void updateMeasurementsData() {
        this.getLoaderManager().restartLoader(1, null, this.weightLoaderCallback);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateUI() {
        float f;
        int n;
        if (!this.isPregnancyDataLoaded) {
            return;
        }
        int n2 = this.pregnancyWeightGainSeekbar.getProgress();
        if (n2 > (n = (int)Math.ceil((float)PregnancyUtils.calculateDayOfPregnancy(this.pregnancyStartDate, new Date()) * 1.0f / (float)this.getPregnancyDurationInDays() * (float)this.pregnancyWeightGainSeekbar.getMax()))) {
            this.showCurrentPregnancyWeight();
            return;
        }
        int n3 = (int)Math.ceil((float)this.getPregnancyDurationInDays() * ((float)n2 * 1.0f / (float)this.pregnancyWeightGainSeekbar.getMax()));
        Date date = new Date(this.pregnancyStartDate.getTime() + TimeUnit.MILLISECONDS.convert(n3, TimeUnit.DAYS));
        SimpleDateFormat simpleDateFormat = DateUtils.getCurrentDateFormat();
        if (n == n2) {
            date = new Date();
            this.pregnancyWeightGainSeekbar.setThumbTopText(this.getString(2131362316));
        } else {
            this.pregnancyWeightGainSeekbar.setThumbTopText(simpleDateFormat.format(date));
        }
        float f2 = f = ((Float)this.weightMeasurements.get(n3, (Object)Float.valueOf(0.0f))).floatValue();
        if (f <= 0.0f) {
            f2 = f;
            while (f2 <= 0.0f && n3 >= 0) {
                f2 = ((Float)this.weightMeasurements.get(n3, (Object)Float.valueOf(0.0f))).floatValue();
                --n3;
            }
        }
        f = MetricUtils.convertWeight(0, this.weightUnit, f2);
        this.pregnancyWeightGainSeekbar.setThumbBottomText(String.format("%1.1f %2s", Float.valueOf(f), this.getString(MetricUtils.getWeightUnitNameRes(this.weightUnit))));
        f2 = MetricUtils.convertWeight(0, this.weightUnit, f2 - this.startWeight);
        if (f2 != 0.0f) {
            this.pregnancyTotalWeightGainView.setText((CharSequence)String.format("%.1f %2$s", Float.valueOf(f2), this.getString(MetricUtils.getWeightUnitNameRes(this.weightUnit))));
        } else {
            this.pregnancyTotalWeightGainView.setText((CharSequence)Utils.formatInteger(0));
        }
        this.pregnancyDurationView.setText((CharSequence)Utils.formatInteger(PregnancyUtils.calculateWeekOfPregnancy(this.pregnancyStartDate, date)));
    }

    /*
     * Exception decompiling
     */
    /* synthetic */ void lambda$processPhoto$4(Uri var1_1, File var2_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // java.lang.IllegalStateException: Backjump on non jumping statement [] lbl19 : TryStatement: try { 2[TRYBLOCK]

        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner$1.call(Cleaner.java:44)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner$1.call(Cleaner.java:22)
        // org.benf.cfr.reader.util.graph.GraphVisitorDFS.process(GraphVisitorDFS.java:68)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.Cleaner.removeUnreachableCode(Cleaner.java:54)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.RemoveDeterministicJumps.apply(RemoveDeterministicJumps.java:38)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:501)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:917)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /* synthetic */ void lambda$saveMeasurement$2(String object) {
        try {
            object = QardioBaseUtils.weightMeasurementFromBase((String)object, this.profile);
            if (((WeightMeasurement)object).qbUserId != null && this.profile.refId != null && this.profile.refId.equals(((WeightMeasurement)object).qbUserId)) {
                ((WeightMeasurement)object).deviceId = DataHelper.DeviceIdHelper.getDeviceId((Context)this.getActivity(), this.userID);
                MeasurementHelper.Weight.addMeasurement((Context)this.getActivity(), this.userID, (WeightMeasurement)object, true);
                ShealthDataHelper.WeightMeasurements.requestSaveWeightMeasurement((Context)this.getActivity(), this.userID, (WeightMeasurement)object);
            }
            return;
        }
        catch (JSONException jSONException) {
            Timber.e(jSONException, "Returned measurement data not valid json: %s", jSONException.getMessage());
            return;
        }
    }

    /* synthetic */ void lambda$showDeleteConfirmationDialog$0(int n, DialogInterface dialogInterface, int n2) {
        this.galleryAdapter.notifyItemChanged(n);
    }

    /* synthetic */ void lambda$showDeleteConfirmationDialog$1(int n, DialogInterface dialogInterface, int n2) {
        this.removePhotoFromGallery(n);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362315);
        this.checkExternalStoragePermission((Context)this.getActivity());
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onActivityResult(int n, int n2, Intent object) {
        if (n == 1 && n2 == -1) {
            this.trackPhotoAddedEvent();
            File file = this.photoPath;
            object = object != null ? object.getData() : null;
            this.processPhoto(file, (Uri)object);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onCreate(Bundle bundle) {
        boolean bl = true;
        super.onCreate(bundle);
        if (bundle != null && bundle.containsKey("com.getqardio.android.key.PHOTO_PATH")) {
            this.photoPath = (File)bundle.getSerializable("com.getqardio.android.key.PHOTO_PATH");
        }
        if (this.getArguments() != null) {
            this.userID = this.getArguments().getLong("com.getqardio.android.argument.USER_ID");
            this.weightUnit = this.getArguments().getInt("com.getqardio.android.argument.WEIGHT_UNIT");
            this.measurementPregnancyID = this.getArguments().getLong("com.getqardio.android.argument.MEASUREMENT_PREGNANCY_ID", -1L);
            int n = this.measurementPregnancyID == -1L ? 2 : 1;
            this.mode = n;
            if (this.mode == 1) {
                this.measurementDate = new Date(this.getArguments().getLong("com.getqardio.android.argument.MEASUREMENT_DATE", System.currentTimeMillis()));
            }
        }
        this.photosLoaderCallback = new PhotosLoaderCallback();
        this.galleryCache = new PregnancyGalleryCache(Utils.getPregnancyGalleryCacheDir());
        if (bundle == null) {
            DataHelper.PregnancyDataHelper.requestPregnancyModeGetHistory((Context)this.getActivity(), this.userID);
        }
        if (this.mode == 2) {
            this.measurementCallback = (WeightMeasurementCallback)this.getParentFragment();
        }
        if (this.mode != 2) {
            bl = false;
        }
        this.setHasOptionsMenu(bl);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menuInflater.inflate(2131886091, menu2);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130968775, viewGroup, false);
    }

    public void onDestroyView() {
        super.onDestroyView();
        if (this.mlocationPermNeeededDialog != null && this.mlocationPermNeeededDialog.isShowing()) {
            this.mlocationPermNeeededDialog.dismiss();
            this.mlocationPermNeeededDialog = null;
        }
        this.disposables.clear();
    }

    @Override
    public void onItemClick(View view, int n) {
        if (this.galleryAdapter.getItemId(n) == -1L) {
            this.galleryCache.ensureCacheDirExists();
            Intent intent = new Intent("android.intent.action.PICK", MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            view = new Intent("android.media.action.IMAGE_CAPTURE");
            this.photoPath = this.galleryCache.createNewPhotoFile();
            view.putExtra("output", (Parcelable)Uri.fromFile((File)this.photoPath));
            intent = Intent.createChooser((Intent)intent, (CharSequence)this.getString(2131362349));
            intent.putExtra("android.intent.extra.INITIAL_INTENTS", (Parcelable[])new Intent[]{view});
            this.startActivityForResult(intent, 1);
            return;
        }
        this.startActivity(PregnancyGalleryActivity.createStartIntent((Context)this.getActivity(), this.userID, this.photosCache, n, this.pregnancyStartDate.getTime(), this.startWeight, this.weightUnit));
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
            case 2131821489: 
        }
        this.startActivity(new Intent((Context)this.getActivity(), InviteUserActivity.class));
        return super.onOptionsItemSelected(menuItem);
    }

    public void onPause() {
        LocalBroadcastManager.getInstance((Context)this.getActivity()).unregisterReceiver(this.measurementReceiver);
        super.onPause();
    }

    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        switch (n) {
            default: {
                return;
            }
            case 0: 
        }
        if (arrn.length > 0 && arrn[0] == 0) {
            this.galleryView.setVisibility(0);
            this.getLoaderManager().restartLoader(3, null, (LoaderManager.LoaderCallbacks)this.photosLoaderCallback);
            return;
        }
        this.galleryView.setVisibility(8);
    }

    public void onResume() {
        super.onResume();
        LocalBroadcastManager.getInstance((Context)this.getActivity()).registerReceiver(this.measurementReceiver, MEASUREMENTS_INTENT_FILTER);
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putSerializable("com.getqardio.android.key.PHOTO_PATH", (Serializable)this.photoPath);
    }

    public void onViewCreated(View view, Bundle object) {
        super.onViewCreated(view, (Bundle)object);
        this.pregnancyWeightGainSeekbar = (ThumbTextSeekBar)view.findViewById(2131821187);
        this.pregnancyWeightGainSeekbar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            public void onProgressChanged(SeekBar seekBar, int n, boolean bl) {
                PregnancyMeasurementFragment.this.updateUI();
            }

            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
        this.pregnancyWeightGainSeekbar.setEnabled(false);
        object = (PregnancyChart)view.findViewById(2131821189);
        this.pregnancyChartAdapter = new PregnancyChartAdapterImpl((Context)this.getActivity());
        ((PregnancyChart)((Object)object)).setAdapter(this.pregnancyChartAdapter);
        this.pregnancyDurationView = (TextView)view.findViewById(2131821192);
        this.pregnancyDueDateView = (TextView)view.findViewById(2131821191);
        this.pregnancyTotalWeightGainView = (TextView)view.findViewById(2131821193);
        this.galleryView = (RecyclerView)view.findViewById(2131821188);
        this.galleryView.setHasFixedSize(true);
        this.galleryView.setLayoutManager(new LinearLayoutManager((Context)this.getActivity(), 0, false));
        int n = this.getResources().getDimensionPixelSize(2131427540);
        this.galleryView.addItemDecoration(new HorizontalSpaceItemDecoration(n));
        this.galleryAdapter = new GalleryAdapter((Context)this.getActivity());
        this.galleryView.setAdapter(this.galleryAdapter);
        this.galleryView.addOnItemTouchListener(new RecyclerViewItemClickListener((Context)this.getActivity(), this));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, 1){

            @Override
            public int getMovementFlags(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                if (viewHolder.getAdapterPosition() == recyclerView.getAdapter().getItemCount() - 1) {
                    return ItemTouchHelper.Callback.makeMovementFlags(0, 0);
                }
                return super.getMovementFlags(recyclerView, viewHolder);
            }

            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder viewHolder2) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int n) {
                PregnancyMeasurementFragment.this.showDeleteConfirmationDialog(viewHolder.getAdapterPosition());
            }
        }).attachToRecyclerView(this.galleryView);
        this.getLoaderManager().initLoader(2, null, this.cursorLoaderCallback);
        this.getLoaderManager().initLoader(4, null, this.cursorLoaderCallback);
        this.getLoaderManager().initLoader(3, null, (LoaderManager.LoaderCallbacks)this.photosLoaderCallback);
    }

    private static class DailyMeasurementsLoader
    extends AsyncTaskLoader<AverageWeightMeasurement[]> {
        private ContentObserver contentObserver;
        private Date endDate;
        private Date startDate;
        private long userID;

        public DailyMeasurementsLoader(Context context, long l, Date date, Date date2) {
            super(context);
            this.userID = l;
            this.startDate = date;
            this.endDate = date2;
            this.contentObserver = new Loader.ForceLoadContentObserver((Loader)this);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public AverageWeightMeasurement[] loadInBackground() {
            int n;
            AverageWeightMeasurement[] arraverageWeightMeasurement;
            AverageWeightMeasurement[] arraverageWeightMeasurement2;
            AverageWeightMeasurement[] arraverageWeightMeasurement3;
            block6: {
                arraverageWeightMeasurement = arraverageWeightMeasurement2 = null;
                try {
                    this.getContext().getContentResolver().registerContentObserver(MeasurementHelper.Weight.buildMeasurementsUri(this.userID), true, this.contentObserver);
                    arraverageWeightMeasurement = arraverageWeightMeasurement2;
                    arraverageWeightMeasurement2 = MeasurementHelper.Weight.getDailyMeasurementsForPeriod(this.getContext(), this.userID, this.startDate, this.endDate, new String[]{"measure_date", "avg(weight) as weight"}, "measure_date ASC ");
                    if (arraverageWeightMeasurement2 != null) {
                        arraverageWeightMeasurement = arraverageWeightMeasurement2;
                        if (arraverageWeightMeasurement2.moveToFirst()) {
                            arraverageWeightMeasurement = arraverageWeightMeasurement2;
                            arraverageWeightMeasurement3 = new AverageWeightMeasurement[arraverageWeightMeasurement2.getCount()];
                            n = 0;
                            break block6;
                        }
                    }
                    Object var5_5 = null;
                    arraverageWeightMeasurement = var5_5;
                    if (arraverageWeightMeasurement2 == null) return arraverageWeightMeasurement;
                    arraverageWeightMeasurement = var5_5;
                }
                catch (Throwable throwable) {
                    if (arraverageWeightMeasurement == null) throw throwable;
                    if (arraverageWeightMeasurement.isClosed()) throw throwable;
                    arraverageWeightMeasurement.close();
                    throw throwable;
                }
                if (arraverageWeightMeasurement2.isClosed()) return arraverageWeightMeasurement;
                arraverageWeightMeasurement2.close();
                return null;
            }
            do {
                arraverageWeightMeasurement = arraverageWeightMeasurement2;
                arraverageWeightMeasurement3[n] = MeasurementHelper.Weight.parseAverageWeightMeasurement((Cursor)arraverageWeightMeasurement2);
                arraverageWeightMeasurement = arraverageWeightMeasurement2;
                boolean bl = arraverageWeightMeasurement2.moveToNext();
                if (!bl) {
                    arraverageWeightMeasurement = arraverageWeightMeasurement3;
                    if (arraverageWeightMeasurement2 == null) return arraverageWeightMeasurement;
                    arraverageWeightMeasurement = arraverageWeightMeasurement3;
                    if (arraverageWeightMeasurement2.isClosed()) return arraverageWeightMeasurement;
                    arraverageWeightMeasurement2.close();
                    return arraverageWeightMeasurement3;
                }
                ++n;
            } while (true);
        }

        protected void onReset() {
            super.onReset();
            this.onStopLoading();
            if (this.contentObserver != null) {
                this.getContext().getContentResolver().unregisterContentObserver(this.contentObserver);
            }
        }

        protected void onStartLoading() {
            this.forceLoad();
        }

        protected void onStopLoading() {
            this.cancelLoad();
        }
    }

    private static class PhotoLoader
    extends AsyncTaskLoader<File[]> {
        private File[] data;
        private PregnancyGalleryCache galleryCache;
        private FileObserver mFileObserver;

        public PhotoLoader(Context context, PregnancyGalleryCache pregnancyGalleryCache) {
            super(context);
            this.galleryCache = pregnancyGalleryCache;
        }

        public void deliverResult(File[] arrfile) {
            if (Arrays.equals(this.data, arrfile)) {
                return;
            }
            this.data = arrfile;
            super.deliverResult((Object)arrfile);
        }

        public File[] loadInBackground() {
            return this.galleryCache.getPhotoFiles();
        }

        protected void onReset() {
            if (this.mFileObserver != null) {
                this.mFileObserver.stopWatching();
                this.mFileObserver = null;
            }
        }

        protected void onStartLoading() {
            if (this.data != null) {
                this.deliverResult(this.data);
            }
            if (this.mFileObserver == null) {
                this.mFileObserver = new FileObserver(this.galleryCache.getCacheDir().getPath(), 3720){

                    public void onEvent(int n, String string2) {
                        PhotoLoader.this.onContentChanged();
                    }
                };
                this.mFileObserver.startWatching();
            }
            if (this.takeContentChanged() || this.data == null) {
                this.forceLoad();
            }
        }

    }

    private class PhotosLoaderCallback
    implements LoaderManager.LoaderCallbacks<File[]> {
        private PhotosLoaderCallback() {
        }

        public Loader<File[]> onCreateLoader(int n, Bundle bundle) {
            if (n == 3) {
                return new PhotoLoader((Context)PregnancyMeasurementFragment.this.getActivity(), PregnancyMeasurementFragment.this.galleryCache);
            }
            return null;
        }

        public void onLoadFinished(Loader<File[]> loader, File[] arrfile) {
            if (loader.getId() == 3) {
                PregnancyMeasurementFragment.this.photosCache = arrfile;
                PregnancyMeasurementFragment.this.galleryAdapter.setData(PregnancyMeasurementFragment.this.photosCache);
                PregnancyMeasurementFragment.this.galleryView.scrollToPosition(PregnancyMeasurementFragment.this.galleryAdapter.getItemCount() - 1);
                PregnancyMeasurementFragment.this.addPhotosToAndroidGallery(arrfile);
            }
        }

        public void onLoaderReset(Loader<File[]> loader) {
        }
    }

}

