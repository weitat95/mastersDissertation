/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.Context
 *  android.content.Intent
 *  android.content.Loader
 *  android.database.Cursor
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.Intent;
import android.content.Loader;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.activity.EditNoteActivity;
import com.getqardio.android.ui.adapter.DateTimeFormatHelper;
import com.getqardio.android.ui.fragment.BPMeasurementsHistoryDetailsFragment$$Lambda$1;
import com.getqardio.android.ui.widget.BPResultChart;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.shared.utils.BpLevelConstants;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BPMeasurementsHistoryDetailsFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
DateTimeFormatHelper.Callback {
    private TextView bloodPressure;
    private ImageView bpLevel;
    private BPResultChart bpResultChart;
    private DateTimeFormatHelper dateTimeFormatHelper;
    private LinearLayout irregularHeartBeatField;
    private boolean isEditable;
    private boolean isNoteChangedByUser = false;
    private BPMeasurement measurement;
    private TextView measurementDate;
    private TextView measurementTime;
    private TextView note;
    private TextView pulse;
    private SimpleDateFormat timeFormat;

    private long getMeasurementDate() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.MEASUREMENT_DATE")) {
            return bundle.getLong("com.getqardio.android.argument.MEASUREMENT_DATE");
        }
        return -1L;
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void init(View view) {
        this.isEditable = this.getArguments().getBoolean("com.getqardio.android.argument.IS_EDITABLE", false);
        this.pulse = (TextView)view.findViewById(2131820930);
        this.bloodPressure = (TextView)view.findViewById(2131820929);
        this.measurementDate = (TextView)view.findViewById(2131820920);
        this.measurementTime = (TextView)view.findViewById(2131820921);
        this.bpLevel = (ImageView)view.findViewById(2131820919);
        this.irregularHeartBeatField = (LinearLayout)view.findViewById(2131820807);
        this.note = (TextView)view.findViewById(2131820885);
        if (this.isEditable) {
            this.note.setOnClickListener(BPMeasurementsHistoryDetailsFragment$$Lambda$1.lambdaFactory$(this));
        } else {
            this.note.setEnabled(false);
        }
        this.dateTimeFormatHelper = new DateTimeFormatHelper(this);
        this.bpResultChart = (BPResultChart)view.findViewById(2131820808);
    }

    public static BPMeasurementsHistoryDetailsFragment newInstance(long l, long l2, boolean bl) {
        Bundle bundle = new Bundle(3);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putLong("com.getqardio.android.argument.MEASUREMENT_DATE", l2);
        bundle.putBoolean("com.getqardio.android.argument.IS_EDITABLE", bl);
        BPMeasurementsHistoryDetailsFragment bPMeasurementsHistoryDetailsFragment = new BPMeasurementsHistoryDetailsFragment();
        bPMeasurementsHistoryDetailsFragment.setArguments(bundle);
        return bPMeasurementsHistoryDetailsFragment;
    }

    private void onNoteSelected(String string2) {
        this.note.setText((CharSequence)string2);
        this.isNoteChangedByUser = true;
    }

    private void saveNote() {
        Activity activity;
        String string2 = this.note.getText().toString();
        if (!string2.equals(this.note.getTag()) && (activity = this.getActivity()) != null) {
            MeasurementHelper.BloodPressure.changeMeasurementNote((Context)activity, this.getUserId(), this.getMeasurementDate(), string2, true);
        }
    }

    private void setDate(BPMeasurement bPMeasurement) {
        if (bPMeasurement != null && bPMeasurement.measureDate != null) {
            Date date = bPMeasurement.measureDate;
            if (this.measurementDate != null) {
                this.measurementDate.setText((CharSequence)DateUtils.formatDateInLocaleAndWithTodayAndYesterday((Context)this.getActivity(), bPMeasurement.measureDate));
            }
            if (this.measurementTime != null && this.timeFormat != null) {
                this.measurementTime.setText((CharSequence)this.timeFormat.format(date));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setMeasurementData(Cursor object) {
        block9: {
            block8: {
                object.moveToFirst();
                if (object.isAfterLast()) break block8;
                this.measurement = MeasurementHelper.BloodPressure.parseMeasurement((Cursor)object);
                this.setDate(this.measurement);
                if (this.measurement.dia != null && this.measurement.sys != null) {
                    this.bpResultChart.setBP(this.measurement.dia, this.measurement.sys);
                    object = String.format("%s / %s", Utils.formatInteger(this.measurement.sys), Utils.formatInteger(this.measurement.dia));
                    this.bloodPressure.setText((CharSequence)object);
                    this.bpLevel.setColorFilter(BpLevelConstants.getColorForLevel((Context)this.getActivity(), this.measurement.dia, this.measurement.sys).intValue(), PorterDuff.Mode.SRC_ATOP);
                }
                if (Boolean.TRUE.equals(this.measurement.irregularHeartBeat)) {
                    this.pulse.setCompoundDrawablesWithIntrinsicBounds(2130837796, 0, 0, 0);
                    this.irregularHeartBeatField.setVisibility(0);
                } else {
                    this.pulse.setCompoundDrawablesWithIntrinsicBounds(2130837778, 0, 0, 0);
                }
                if ((object = this.measurement.pulse) != null) {
                    object = (Integer)object != 0 ? Utils.formatInteger((Integer)object) : "--";
                    this.pulse.setText((CharSequence)object);
                }
                if (!TextUtils.isEmpty((CharSequence)this.measurement.note) || this.isEditable) break block9;
                this.note.setVisibility(8);
            }
            return;
        }
        object = this.measurement.note != null ? this.measurement.note : "";
        if (!this.isNoteChangedByUser) {
            this.note.setText((CharSequence)object);
        }
        this.note.setTag(object);
    }

    private void showEditNoteDialog() {
        Activity activity;
        if (this.isEditable && (activity = this.getActivity()) != null) {
            this.startActivityForResult(EditNoteActivity.getStartIntent((Context)activity, this.getUserId(), this.note.getText().toString(), "bp"), 0);
        }
    }

    /* synthetic */ void lambda$init$0(View view) {
        this.showEditNoteDialog();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "BP measurement details");
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 0 && n2 == -1) {
            this.onNoteSelected(EditNoteActivity.extractNoteFromIntent(intent));
        }
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: 
        }
        return MeasurementHelper.BloodPressure.getMeasurementsLoaderByDate((Context)this.getActivity(), this.getUserId(), this.getMeasurementDate(), null);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968643, viewGroup, false);
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
        this.setMeasurementData(cursor);
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onPause() {
        super.onPause();
        if (this.getActivity().isFinishing()) {
            this.saveNote();
        }
    }

    public void onResume() {
        super.onResume();
        this.dateTimeFormatHelper.onUpdatePatterns();
    }

    @Override
    public void setDateFormat(SimpleDateFormat simpleDateFormat) {
    }

    @Override
    public void setTimeFormat(SimpleDateFormat simpleDateFormat) {
        this.timeFormat = simpleDateFormat;
    }

    @Override
    public void updateData() {
        this.setDate(this.measurement);
    }
}

