/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.Context
 *  android.content.CursorLoader
 *  android.content.Intent
 *  android.content.Loader
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Parcelable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.CompoundButton
 *  android.widget.CompoundButton$OnCheckedChangeListener
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.content.Context;
import android.content.CursorLoader;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.TextView;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.target.Target;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.datamodel.PregnancyData;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.fragment.QBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBPregnancySetupFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.QBPregnancySetupFragment$$Lambda$2;
import com.getqardio.android.ui.fragment.QBPregnancySetupFragment$$Lambda$3;
import com.getqardio.android.ui.widget.CustomSwitch;
import com.getqardio.android.ui.widget.DatePickerFragment;
import com.getqardio.android.ui.widget.WeightPickerFragment;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.HelperUtils;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.PregnancyUtils;
import com.getqardio.android.utils.permission.PermissionUtil;
import com.getqardio.android.utils.ui.ActivityUtils;
import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class QBPregnancySetupFragment
extends Fragment {
    private SimpleDateFormat dateFormat = DateUtils.getProfileDateFormat("LLLL dd, yyyy");
    private TextView dueDateValueView;
    private CustomSwitch hideWeightSwitch;
    private LoaderManager.LoaderCallbacks<Cursor> loaderCallback = new LoaderManager.LoaderCallbacks<Cursor>(){

        public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
            if (n == 1) {
                return new CursorLoader((Context)QBPregnancySetupFragment.this.getActivity(), DataHelper.PregnancyDataHelper.buildUriForUser(QBPregnancySetupFragment.this.userId), null, "end_date is null  OR  ( " + System.currentTimeMillis() + " - " + "end_date" + " ) <= " + TimeUnit.MILLISECONDS.convert(7L, TimeUnit.DAYS), null, null);
            }
            if (n == 2) {
                bundle = QBPregnancySetupFragment.this.getActivity();
                Uri uri = MeasurementHelper.Weight.buildDailyMeasurementsUri(QBPregnancySetupFragment.this.userId);
                String string2 = " abs( measure_date - " + QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).startDate.getTime() + " ) ASC limit 1";
                return new CursorLoader((Context)bundle, uri, new String[]{"avg(weight) as weight"}, null, null, string2);
            }
            if (n == 3) {
                return DataHelper.ProfileHelper.getProfileLoader((Context)QBPregnancySetupFragment.this.getActivity(), QBPregnancySetupFragment.this.userId, new String[]{"weight", "weight_unit"});
            }
            return null;
        }

        /*
         * Enabled aggressive block sorting
         */
        public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
            int n = loader.getId();
            if (n == 1) {
                if (cursor.moveToFirst()) {
                    QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).id = HelperUtils.getLong(cursor, "_id", null);
                    QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).cloudId = HelperUtils.getLong(cursor, "cloud_id", null);
                    QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).startDate = HelperUtils.getDate(cursor, "start_date", null);
                    QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).isShowWeight = HelperUtils.getBoolean(cursor, "is_show_weight", (Boolean)false);
                    QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).dueDate = HelperUtils.getDate(cursor, "due_date", null);
                    QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).prePregnancyWeight = HelperUtils.getFloat(cursor, "start_weight", null).floatValue();
                    QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).endDate = null;
                }
                QBPregnancySetupFragment.this.updatePregnancyData();
                return;
            } else {
                if (n == 2) {
                    QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).prePregnancyWeight = cursor.moveToFirst() ? cursor.getFloat(0) : QBPregnancySetupFragment.this.profileWeight;
                    QBPregnancySetupFragment.this.updatePregnancyData();
                    return;
                }
                if (n != 3) return;
                {
                    if (cursor.moveToFirst()) {
                        QBPregnancySetupFragment.this.weightUnit = cursor.getInt(1);
                        QBPregnancySetupFragment.this.profileWeight = MetricUtils.convertWeight(QBPregnancySetupFragment.this.weightUnit, 0, cursor.getFloat(0));
                        QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).prePregnancyWeight = QBPregnancySetupFragment.this.profileWeight;
                    }
                    QBPregnancySetupFragment.this.getLoaderManager().initLoader(1, null, QBPregnancySetupFragment.this.loaderCallback);
                    return;
                }
            }
        }

        public void onLoaderReset(Loader<Cursor> loader) {
        }
    };
    private TextView prePregnancyWeightValueView;
    private PregnancyData pregnancyData;
    private float profileWeight;
    private Button saveBtn;
    private TextView startDateValueView;
    private long userId;
    private int weightUnit = 0;

    private void loadWeightForStartDate() {
        this.getLoaderManager().restartLoader(2, null, this.loaderCallback);
    }

    public static QBPregnancySetupFragment newInstance(int n, Fragment fragment) {
        QBPregnancySetupFragment qBPregnancySetupFragment = new QBPregnancySetupFragment();
        if (!(fragment instanceof QBOnboardingFragment) || Build.VERSION.SDK_INT <= 25) {
            qBPregnancySetupFragment.setTargetFragment(fragment, n);
        }
        return qBPregnancySetupFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onSaved() {
        OnPregnancyDataSaveListener onPregnancyDataSaveListener;
        boolean bl = true;
        boolean bl2 = true;
        if (!(this.getParentFragment() instanceof QBOnboardingFragment) || Build.VERSION.SDK_INT <= 25) {
            Fragment fragment = this.getTargetFragment();
            if (fragment == null) return;
            {
                Intent intent = new Intent();
                if (this.pregnancyData.isShowWeight.booleanValue()) {
                    bl2 = false;
                }
                intent.putExtra("com.getqardio.android.extra.HIDE_WEIGHT", bl2);
                fragment.onActivityResult(this.getTargetRequestCode(), -1, intent);
                return;
            }
        } else {
            if (!(this.getParentFragment() instanceof OnPregnancyDataSaveListener)) return;
            {
                onPregnancyDataSaveListener = (OnPregnancyDataSaveListener)this.getParentFragment();
                bl2 = this.pregnancyData.isShowWeight == false ? bl : false;
            }
        }
        onPregnancyDataSaveListener.onPregnancyDataSaved(bl2);
    }

    private void savePregnancyModeData() {
        Observable.just(new Object()).flatMap(QBPregnancySetupFragment$$Lambda$1.lambdaFactory$(this)).compose(RxUtil.applySchedulers()).subscribe(QBPregnancySetupFragment$$Lambda$2.lambdaFactory$(this), QBPregnancySetupFragment$$Lambda$3.lambdaFactory$());
    }

    private void setDueDate(Date date) {
        this.pregnancyData.dueDate = date;
        this.updatePregnancyData();
    }

    private void setStartDate(Date date) {
        this.pregnancyData.startDate = date;
        if (!DataHelper.PregnancyDataHelper.isPregnancyModeActive((Context)this.getActivity(), this.userId)) {
            this.pregnancyData.dueDate = PregnancyUtils.calculateDueDate(date);
        }
        this.loadWeightForStartDate();
        this.updatePregnancyData();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updatePregnancyData() {
        TextView textView = this.startDateValueView;
        Object object = this.pregnancyData.startDate != null ? this.dateFormat.format(this.pregnancyData.startDate) : null;
        textView.setText(object);
        textView = this.dueDateValueView;
        object = this.pregnancyData.dueDate != null ? this.dateFormat.format(this.pregnancyData.dueDate) : null;
        textView.setText((CharSequence)object);
        if (this.pregnancyData.prePregnancyWeight > 0.0f) {
            float f = MetricUtils.convertWeight(0, this.weightUnit, this.pregnancyData.prePregnancyWeight);
            this.prePregnancyWeightValueView.setText((CharSequence)this.getString(2131362528, new Object[]{Float.valueOf(f), this.getString(MetricUtils.getWeightUnitNameRes(this.weightUnit))}));
        } else {
            this.prePregnancyWeightValueView.setText(null);
        }
        object = this.hideWeightSwitch;
        boolean bl = this.pregnancyData.isShowWeight == false;
        ((CustomSwitch)((Object)object)).setChecked(bl, false);
        if (this.pregnancyData.prePregnancyWeight > 0.0f && this.pregnancyData.startDate != null && this.pregnancyData.dueDate != null) {
            this.saveBtn.setEnabled(true);
            return;
        }
        this.saveBtn.setEnabled(false);
    }

    /* synthetic */ ObservableSource lambda$savePregnancyModeData$0(Object object) throws Exception {
        DataHelper.PregnancyDataHelper.savePregnancyData((Context)this.getActivity(), this.userId, this.pregnancyData, true);
        return Observable.just(object);
    }

    /* synthetic */ void lambda$savePregnancyModeData$1(Object object) throws Exception {
        Timber.d("saved", new Object[0]);
        this.onSaved();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.setHasOptionsMenu(false);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onActivityResult(int n, int n2, Intent intent) {
        if (n2 == -1) {
            if (n == 1) {
                long l = intent.getLongExtra("EXTRA_RESULT_DATE_IN_MILLIS", 0L);
                if (l > 0L) {
                    this.setStartDate(new Date(l));
                }
            } else if (n == 2) {
                long l = intent.getLongExtra("EXTRA_RESULT_DATE_IN_MILLIS", 0L);
                if (l > 0L) {
                    this.setDueDate(new Date(l));
                }
            } else if (n == 3 && intent != null && intent.hasExtra("EXTRA_RESULT_WEIGHT") && intent.hasExtra("EXTRA_RESULT_WEIGHT_UNIT")) {
                float f = intent.getFloatExtra("EXTRA_RESULT_WEIGHT", 0.0f);
                this.weightUnit = intent.getIntExtra("EXTRA_RESULT_WEIGHT_UNIT", 0);
                this.pregnancyData.prePregnancyWeight = MetricUtils.convertWeight(this.weightUnit, 0, f);
                this.updatePregnancyData();
            }
        }
        super.onActivityResult(n, n2, intent);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.pregnancyData = bundle == null ? new PregnancyData() : (PregnancyData)bundle.getParcelable("KEY_PREGNANCY_DATA");
        this.userId = CustomApplication.getApplication().getCurrentUserId();
        if (bundle == null) {
            DataHelper.PregnancyDataHelper.requestPregnancyModeGetHistory((Context)this.getActivity(), this.userId);
        }
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        return layoutInflater.inflate(2130968776, viewGroup, false);
    }

    public void onResume() {
        super.onResume();
        ActivityUtils.getActionBar(this.getActivity()).setTitle(2131362317);
        Activity activity = this.getActivity();
        if (activity != null && !PermissionUtil.ExternalStorage.hasExternalStoragePermission(activity)) {
            PermissionUtil.ExternalStorage.checkExternalStoragePermission(activity);
        }
    }

    public void onSaveInstanceState(Bundle bundle) {
        bundle.putParcelable("KEY_PREGNANCY_DATA", (Parcelable)this.pregnancyData);
        super.onSaveInstanceState(bundle);
    }

    public void onViewCreated(final View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        Glide.with(this).load(2130837939).into(new SimpleTarget<GlideDrawable>(){

            @Override
            public void onResourceReady(GlideDrawable glideDrawable, GlideAnimation<? super GlideDrawable> glideAnimation) {
                view.setBackground((Drawable)glideDrawable);
            }
        });
        this.hideWeightSwitch = (CustomSwitch)view.findViewById(2131821203);
        this.hideWeightSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onCheckedChanged(CompoundButton object, boolean bl) {
                boolean bl2;
                PregnancyData pregnancyData = QBPregnancySetupFragment.this.pregnancyData;
                bl2 = !bl2;
                pregnancyData.isShowWeight = bl2;
            }
        });
        view.findViewById(2131821197).setOnClickListener(new View.OnClickListener(){

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             */
            public void onClick(View object) {
                void var1_3;
                Date date = PregnancyUtils.calculateDueDate(new Date());
                if (QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).dueDate != null) {
                    Date date2 = QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).dueDate;
                } else {
                    Date date3 = date;
                }
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance((Date)var1_3, new Date(), date);
                datePickerFragment.setTargetFragment((Fragment)QBPregnancySetupFragment.this, 2);
                datePickerFragment.show(QBPregnancySetupFragment.this.getFragmentManager(), null);
            }
        });
        this.dueDateValueView = (TextView)view.findViewById(2131821199);
        view.findViewById(2131821194).setOnClickListener(new View.OnClickListener(){

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             */
            public void onClick(View object) {
                void var1_3;
                if (QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).startDate != null) {
                    Date date = QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).startDate;
                } else {
                    Date date = new Date();
                }
                DatePickerFragment datePickerFragment = DatePickerFragment.newInstance((Date)var1_3, PregnancyUtils.calculateStartDate(new Date()), new Date());
                datePickerFragment.setTargetFragment((Fragment)QBPregnancySetupFragment.this, 1);
                datePickerFragment.show(QBPregnancySetupFragment.this.getFragmentManager(), null);
            }
        });
        this.startDateValueView = (TextView)view.findViewById(2131821196);
        view.findViewById(2131821200).setOnClickListener(new View.OnClickListener(){

            public void onClick(View object) {
                object = WeightPickerFragment.newInstance(MetricUtils.convertWeight(0, QBPregnancySetupFragment.this.weightUnit, QBPregnancySetupFragment.access$100((QBPregnancySetupFragment)QBPregnancySetupFragment.this).prePregnancyWeight), QBPregnancySetupFragment.this.weightUnit, false);
                object.setTargetFragment((Fragment)QBPregnancySetupFragment.this, 3);
                object.show(QBPregnancySetupFragment.this.getFragmentManager(), null);
            }
        });
        this.prePregnancyWeightValueView = (TextView)view.findViewById(2131821202);
        this.saveBtn = (Button)view.findViewById(2131821205);
        this.saveBtn.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                QBPregnancySetupFragment.this.savePregnancyModeData();
            }
        });
        this.getLoaderManager().initLoader(3, null, this.loaderCallback);
        if (bundle != null) {
            this.updatePregnancyData();
        }
    }

    public static interface OnPregnancyDataSaveListener {
        public void onPregnancyDataSaved(boolean var1);
    }

}

