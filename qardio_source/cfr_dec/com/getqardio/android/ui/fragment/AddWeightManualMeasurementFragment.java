/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.DatePickerDialog
 *  android.app.DatePickerDialog$OnDateSetListener
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.app.TimePickerDialog
 *  android.app.TimePickerDialog$OnTimeSetListener
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Intent
 *  android.content.Loader
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.text.Editable
 *  android.text.InputFilter
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.text.format.DateFormat
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.ViewGroup
 *  android.widget.DatePicker
 *  android.widget.EditText
 *  android.widget.TextView
 *  android.widget.TimePicker
 *  android.widget.Toast
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.LoaderManager;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.Loader;
import android.content.res.Resources;
import android.database.Cursor;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.format.DateFormat;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.device_related_services.fit.GoogleFitDataHelper;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.shealth.ShealthDataHelper;
import com.getqardio.android.ui.activity.EditNoteActivity;
import com.getqardio.android.ui.dialog.DatePickerDialogFragment;
import com.getqardio.android.ui.dialog.TimePickerDialogFragment;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment$$Lambda$4;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment$$Lambda$5;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment$$Lambda$6;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment$$Lambda$7;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment$$Lambda$8;
import com.getqardio.android.ui.fragment.AddWeightManualMeasurementFragment$$Lambda$9;
import com.getqardio.android.ui.widget.BmiResultChart;
import com.getqardio.android.ui.widget.SimpleTextWatcher;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.Validator;
import com.getqardio.android.utils.analytics.AnalyticsScreenTracker;
import com.getqardio.android.utils.ui.DecimalInputFilter;
import com.getqardio.android.utils.ui.UIUtils;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class AddWeightManualMeasurementFragment
extends Fragment
implements DatePickerDialog.OnDateSetListener,
TimePickerDialog.OnTimeSetListener {
    View.OnFocusChangeListener anyFieldFocusListener;
    private BmiResultChart bmiChart;
    private EditText bonePercentage;
    private LoaderManager.LoaderCallbacks<Cursor> cursorLoaderCallback;
    private java.text.DateFormat dateFormat;
    private EditText fatPercentage;
    private boolean is24Hour;
    private EditText musclePercentage;
    private EditText note;
    private NumberFormat numberFormat = new DecimalFormat("###.##");
    private Profile profile;
    private MenuItem saveMenuItem;
    private Calendar selectedDate;
    private String selectedDateText;
    private TextView selectedDateView;
    private String selectedTimeText;
    private TextView selectedTimeView;
    private int shownWeightUnit = 0;
    private java.text.DateFormat timeFormat;
    private String[] units;
    private EditText waterPercentage;
    private EditText weightUnit;
    private EditText weightValue;

    public AddWeightManualMeasurementFragment() {
        this.cursorLoaderCallback = new LoaderManager.LoaderCallbacks<Cursor>(){

            public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
                bundle = null;
                if (n == 0) {
                    bundle = DataHelper.ProfileHelper.getProfileLoader((Context)AddWeightManualMeasurementFragment.this.getActivity(), AddWeightManualMeasurementFragment.this.getUserId(), null);
                }
                return bundle;
            }

            public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
                if (loader.getId() == 0 && cursor.moveToFirst()) {
                    AddWeightManualMeasurementFragment.this.profile = DataHelper.ProfileHelper.parseProfile(cursor);
                    int n = MetricUtils.getWeightUnitPositionInStringsArray(0);
                    if (AddWeightManualMeasurementFragment.access$100((AddWeightManualMeasurementFragment)AddWeightManualMeasurementFragment.this).weightUnit != null) {
                        n = MetricUtils.getWeightUnitPositionInStringsArray(AddWeightManualMeasurementFragment.access$100((AddWeightManualMeasurementFragment)AddWeightManualMeasurementFragment.this).weightUnit);
                    }
                    AddWeightManualMeasurementFragment.this.onNewWeightUnitSelected(n);
                    AddWeightManualMeasurementFragment.this.weightUnit.setText((CharSequence)AddWeightManualMeasurementFragment.this.units[n]);
                }
            }

            public void onLoaderReset(Loader<Cursor> loader) {
            }
        };
        this.anyFieldFocusListener = AddWeightManualMeasurementFragment$$Lambda$1.lambdaFactory$(this);
    }

    static /* synthetic */ Profile access$100(AddWeightManualMeasurementFragment addWeightManualMeasurementFragment) {
        return addWeightManualMeasurementFragment.profile;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addMeasurement() {
        Activity activity;
        block10: {
            block9: {
                if (!this.isDataValid()) break block9;
                activity = this.getActivity();
                if (this.profile != null) break block10;
            }
            return;
        }
        WeightMeasurement weightMeasurement = new WeightMeasurement();
        weightMeasurement.weight = this.getShownWeightInKg();
        weightMeasurement.fat = this.getPercentageValue((TextView)this.fatPercentage);
        weightMeasurement.muscle = this.getPercentageValue((TextView)this.musclePercentage);
        weightMeasurement.water = this.getPercentageValue((TextView)this.waterPercentage);
        weightMeasurement.bone = this.getPercentageValue((TextView)this.bonePercentage);
        weightMeasurement.deviceId = "0";
        weightMeasurement.measurementId = String.valueOf(this.selectedDate.getTime().getTime());
        weightMeasurement.measureDate = this.selectedDate.getTime();
        weightMeasurement.timezone = String.format("UTC%s", DateUtils.getTimeZoneOffset(weightMeasurement.measureDate));
        if (!TextUtils.isEmpty((CharSequence)this.note.getText())) {
            weightMeasurement.note = this.note.getText().toString();
        }
        weightMeasurement.measurementSource = 4;
        if (this.profile.height != null && this.profile.heightUnit != null) {
            weightMeasurement.height = Math.round(MetricUtils.convertHeight(this.profile.heightUnit, 0, this.profile.height.floatValue()));
        }
        long l = this.getUserId();
        WeightMeasurement weightMeasurement2 = MeasurementHelper.Weight.getLastMeasurement((Context)this.getActivity(), l, null);
        if (weightMeasurement2 == null || weightMeasurement2.measureDate == null || weightMeasurement2.measureDate.before(weightMeasurement.measureDate)) {
            if (this.profile.weight == null) {
                this.saveProfileWeight(weightMeasurement.weight, this.shownWeightUnit);
            } else if (Math.abs(MetricUtils.convertWeight(this.profile.weightUnit, 0, this.profile.weight.floatValue()) - weightMeasurement.weight.floatValue()) >= 0.2f) {
                this.saveProfileWeight(weightMeasurement.weight, this.profile.weightUnit);
            }
        }
        GoogleFitDataHelper.Weight.requestSaveToGoogleFit((Context)activity, l, weightMeasurement);
        MeasurementHelper.Weight.addMeasurement((Context)activity, l, weightMeasurement, true);
        ShealthDataHelper.WeightMeasurements.requestSaveWeightMeasurement((Context)activity, l, weightMeasurement);
        activity.onBackPressed();
    }

    private void checkDate() {
        if (this.selectedDate.getTime().after(new Date())) {
            this.selectedDate.setTime(new Date());
            Toast.makeText((Context)this.getActivity(), (int)2131362210, (int)1).show();
        }
    }

    private Integer getPercentageValue(TextView textView) {
        if (TextUtils.isEmpty((CharSequence)textView.getText())) {
            return null;
        }
        return Integer.valueOf(textView.getText().toString());
    }

    private Float getShownWeight() {
        String string2 = this.weightValue.getText().toString();
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            return Utils.parseNumber(string2);
        }
        return null;
    }

    private Float getShownWeightInKg() {
        Float f;
        Float f2 = f = this.getShownWeight();
        if (this.shownWeightUnit != 0) {
            f2 = Float.valueOf(MetricUtils.convertWeight(this.shownWeightUnit, 0, f.floatValue()));
        }
        return f2;
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private void handleFieldsError(View view) {
        switch (view.getId()) {
            default: {
                return;
            }
            case 2131820887: {
                this.showErrorFieldState(this.isPercentageValid((TextView)this.fatPercentage, 1, 50), UIUtils.findTopTextInputLayout((View)this.fatPercentage), 1.0f, 50.0f);
                return;
            }
            case 2131820888: {
                this.showErrorFieldState(this.isPercentageValid((TextView)this.musclePercentage, 5, 50), UIUtils.findTopTextInputLayout((View)this.musclePercentage), 5.0f, 50.0f);
                return;
            }
            case 2131820889: {
                this.showErrorFieldState(this.isPercentageValid((TextView)this.waterPercentage, 40, 70), UIUtils.findTopTextInputLayout((View)this.waterPercentage), 40.0f, 70.0f);
                return;
            }
            case 2131820890: {
                this.showErrorFieldState(this.isPercentageValid((TextView)this.bonePercentage, 5, 20), UIUtils.findTopTextInputLayout((View)this.bonePercentage), 5.0f, 20.0f);
                return;
            }
            case 2131820869: 
        }
        this.onWeightChanged();
    }

    private void init(View view) {
        this.weightValue = (EditText)view.findViewById(2131820869);
        this.weightUnit = (EditText)view.findViewById(2131820870);
        this.fatPercentage = (EditText)view.findViewById(2131820887);
        this.musclePercentage = (EditText)view.findViewById(2131820888);
        this.waterPercentage = (EditText)view.findViewById(2131820889);
        this.bonePercentage = (EditText)view.findViewById(2131820890);
        this.selectedDateView = (TextView)view.findViewById(2131820883);
        this.selectedTimeView = (TextView)view.findViewById(2131820884);
        this.note = (EditText)view.findViewById(2131820885);
        this.bmiChart = (BmiResultChart)view.findViewById(2131820875);
        this.selectedDateText = this.dateFormat.format(this.selectedDate.getTime());
        this.selectedTimeText = this.timeFormat.format(this.selectedDate.getTime());
        this.selectedDateView.setText((CharSequence)this.selectedDateText);
        this.selectedDateView.setOnClickListener(AddWeightManualMeasurementFragment$$Lambda$4.lambdaFactory$(this));
        this.selectedTimeView.setText((CharSequence)this.selectedTimeText);
        this.selectedTimeView.setOnClickListener(AddWeightManualMeasurementFragment$$Lambda$5.lambdaFactory$(this));
        this.weightValue.setFilters(new InputFilter[]{new DecimalInputFilter()});
        this.units = this.getResources().getStringArray(2131755016);
        this.weightUnit.setOnClickListener(AddWeightManualMeasurementFragment$$Lambda$6.lambdaFactory$(this));
        this.weightValue.addTextChangedListener((TextWatcher)new SimpleTextWatcher(){

            @Override
            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                AddWeightManualMeasurementFragment.this.onWeightChanged();
            }
        });
        this.note.setOnFocusChangeListener(AddWeightManualMeasurementFragment$$Lambda$7.lambdaFactory$(this));
        this.note.setOnClickListener(AddWeightManualMeasurementFragment$$Lambda$8.lambdaFactory$(this));
        this.weightValue.addTextChangedListener((TextWatcher)new AnyFieldTextWatcher((View)this.weightValue));
        this.fatPercentage.addTextChangedListener((TextWatcher)new AnyFieldTextWatcher((View)this.fatPercentage));
        this.musclePercentage.addTextChangedListener((TextWatcher)new AnyFieldTextWatcher((View)this.musclePercentage));
        this.waterPercentage.addTextChangedListener((TextWatcher)new AnyFieldTextWatcher((View)this.waterPercentage));
        this.bonePercentage.addTextChangedListener((TextWatcher)new AnyFieldTextWatcher((View)this.bonePercentage));
        this.note.addTextChangedListener((TextWatcher)new AnyFieldTextWatcher((View)this.weightValue));
        this.selectedTimeView.addTextChangedListener((TextWatcher)new AnyFieldTextWatcher((View)this.selectedTimeView));
        this.selectedDateView.addTextChangedListener((TextWatcher)new AnyFieldTextWatcher((View)this.selectedDateView));
        this.weightValue.setOnFocusChangeListener(this.anyFieldFocusListener);
        this.fatPercentage.setOnFocusChangeListener(this.anyFieldFocusListener);
        this.musclePercentage.setOnFocusChangeListener(this.anyFieldFocusListener);
        this.waterPercentage.setOnFocusChangeListener(this.anyFieldFocusListener);
        this.bonePercentage.setOnFocusChangeListener(this.anyFieldFocusListener);
        this.weightUnit.setHint((CharSequence)this.getResources().getString(2131362394));
    }

    private boolean isAnyFieldChanged() {
        return !TextUtils.isEmpty((CharSequence)this.weightValue.getText()) || !TextUtils.isEmpty((CharSequence)this.fatPercentage.getText()) || !TextUtils.isEmpty((CharSequence)this.musclePercentage.getText()) || !TextUtils.isEmpty((CharSequence)this.waterPercentage.getText()) || !TextUtils.isEmpty((CharSequence)this.bonePercentage.getText()) || !TextUtils.isEmpty((CharSequence)this.note.getText()) || !this.selectedDateView.getText().toString().equals(this.selectedDateText) || !this.selectedTimeView.getText().toString().equals(this.selectedTimeText);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean isDataValid() {
        boolean bl = true;
        boolean bl2 = this.isWeightValid() == FieldState.OK;
        boolean bl3 = this.isPercentageValid((TextView)this.fatPercentage, 1, 50) == FieldState.OK;
        boolean bl4 = this.isPercentageValid((TextView)this.musclePercentage, 5, 50) == FieldState.OK;
        boolean bl5 = this.isPercentageValid((TextView)this.waterPercentage, 40, 70) == FieldState.OK;
        if (this.isPercentageValid((TextView)this.bonePercentage, 5, 20) == FieldState.OK) {
            return true & bl2 & bl3 & bl4 & bl5 & bl;
        }
        bl = false;
        return true & bl2 & bl3 & bl4 & bl5 & bl;
    }

    private FieldState isPercentageValid(TextView textView, int n, int n2) {
        if (TextUtils.isEmpty((CharSequence)textView.getText())) {
            return FieldState.OK;
        }
        int n3 = Integer.parseInt(textView.getText().toString());
        if (n3 < n) {
            return FieldState.LESS;
        }
        if (n3 > n2) {
            return FieldState.MORE;
        }
        return FieldState.OK;
    }

    private FieldState isWeightValid() {
        Float f = this.getShownWeight();
        if (f == null) {
            return FieldState.EMPTY;
        }
        if (!Validator.isWeightMaxValid(this.shownWeightUnit, f.floatValue())) {
            return FieldState.MORE;
        }
        if (!Validator.isWeightMinValid(this.shownWeightUnit, f.floatValue())) {
            return FieldState.LESS;
        }
        return FieldState.OK;
    }

    public static AddWeightManualMeasurementFragment newInstance(long l) {
        Bundle bundle = new Bundle(1);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        AddWeightManualMeasurementFragment addWeightManualMeasurementFragment = new AddWeightManualMeasurementFragment();
        addWeightManualMeasurementFragment.setArguments(bundle);
        return addWeightManualMeasurementFragment;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onNewWeightUnitSelected(int n) {
        int n2 = this.shownWeightUnit;
        switch (n) {
            case 0: {
                this.shownWeightUnit = 1;
                break;
            }
            case 1: {
                this.shownWeightUnit = 2;
                break;
            }
            case 2: {
                this.shownWeightUnit = 0;
                break;
            }
        }
        this.recalculateWeight(n2, this.shownWeightUnit);
    }

    private void onNoteSelected(String string2) {
        this.note.setText((CharSequence)string2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onWeightChanged() {
        FieldState fieldState = this.isWeightValid();
        this.showErrorFieldState(fieldState, UIUtils.findTopTextInputLayout((View)this.weightValue), Validator.getMinWeightForUnit(this.shownWeightUnit), Validator.getMaxWeightForUnit(this.shownWeightUnit));
        if (fieldState != FieldState.OK) {
            this.bmiChart.clearBodyMassIndex();
            return;
        } else {
            if (this.profile == null || this.profile.height == null || this.profile.heightUnit == null) return;
            {
                int n = Math.round(MetricUtils.convertHeight(this.profile.heightUnit, 0, this.profile.height.floatValue()));
                this.bmiChart.setBodyMassIndex(Float.valueOf(QardioBaseUtils.bmi(this.getShownWeightInKg().floatValue(), n)));
                return;
            }
        }
    }

    private void recalculateWeight(int n, int n2) {
        Object object = this.getShownWeight();
        if (n != n2 && object != null) {
            float f = MetricUtils.convertWeight(n, n2, ((Float)object).floatValue());
            object = this.numberFormat.format(f);
            this.weightValue.setText((CharSequence)object);
        }
    }

    private void saveProfileWeight(Float f, int n) {
        float f2 = MetricUtils.convertWeight(0, n, f.floatValue());
        DataHelper.ProfileHelper.setProfileWeight((Context)this.getActivity(), this.getUserId(), f2, n, this.profile.syncStatus);
    }

    private void selectDate() {
        DatePickerDialogFragment datePickerDialogFragment = DatePickerDialogFragment.newInstance(this.selectedDate.get(1), this.selectedDate.get(2), this.selectedDate.get(5));
        datePickerDialogFragment.setDateSetListener(this);
        datePickerDialogFragment.show(this.getFragmentManager(), null);
    }

    private void selectTime() {
        TimePickerDialogFragment timePickerDialogFragment = TimePickerDialogFragment.newInstance(this.selectedDate.get(11), this.selectedDate.get(12), this.is24Hour);
        timePickerDialogFragment.setTimeSetListener(this);
        timePickerDialogFragment.show(this.getFragmentManager(), null);
    }

    private void setSaveMenuItemEnabled(boolean bl) {
        UIUtils.enableMenuItemAndChangeColor(this.saveMenuItem, bl);
    }

    private void showEditNoteScreen() {
        Activity activity = this.getActivity();
        if (activity != null) {
            this.startActivityForResult(EditNoteActivity.getStartIntent((Context)activity, this.getUserId(), this.note.getText().toString(), "weight"), 0);
        }
    }

    private void showErrorFieldState(FieldState fieldState, TextInputLayout textInputLayout, float f, float f2) {
        switch (3.$SwitchMap$com$getqardio$android$ui$fragment$AddWeightManualMeasurementFragment$FieldState[fieldState.ordinal()]) {
            default: {
                return;
            }
            case 1: {
                textInputLayout.setError(this.getString(2131362287, new Object[]{this.numberFormat.format(f2)}));
                return;
            }
            case 2: {
                textInputLayout.setError(this.getString(2131362270, new Object[]{this.numberFormat.format(f)}));
                return;
            }
            case 3: {
                textInputLayout.setError(this.getString(2131362227));
                return;
            }
            case 4: 
        }
        textInputLayout.setError(null);
    }

    private void trackScreen() {
        AnalyticsScreenTracker.sendScreen((Context)this.getActivity(), "Weight manual measurement");
    }

    /* synthetic */ void lambda$init$1(View view) {
        this.selectDate();
    }

    /* synthetic */ void lambda$init$2(View view) {
        this.selectTime();
    }

    /* synthetic */ void lambda$init$4(View view) {
        new AlertDialog.Builder((Context)this.getActivity()).setItems(this.units, AddWeightManualMeasurementFragment$$Lambda$9.lambdaFactory$(this)).create().show();
    }

    /* synthetic */ void lambda$init$5(View view, boolean bl) {
        if (bl) {
            this.showEditNoteScreen();
        }
    }

    /* synthetic */ void lambda$init$6(View view) {
        this.showEditNoteScreen();
    }

    /* synthetic */ void lambda$new$0(View view, boolean bl) {
        if (!bl) {
            this.handleFieldsError(view);
        }
    }

    /* synthetic */ void lambda$null$3(DialogInterface dialogInterface, int n) {
        this.weightUnit.setText((CharSequence)this.units[n]);
        this.onNewWeightUnitSelected(n);
    }

    public void onActivityResult(int n, int n2, Intent intent) {
        super.onActivityResult(n, n2, intent);
        if (n == 0 && n2 == -1) {
            this.onNoteSelected(EditNoteActivity.extractNoteFromIntent(intent));
        }
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void onCreate(Bundle object) {
        void var1_3;
        super.onCreate(object);
        this.setHasOptionsMenu(true);
        this.selectedDate = Calendar.getInstance();
        this.is24Hour = DateFormat.is24HourFormat((Context)this.getActivity());
        if (this.is24Hour) {
            String string2 = "HH:mm";
        } else {
            String string3 = "hh:mm";
        }
        this.timeFormat = new SimpleDateFormat((String)var1_3);
        this.dateFormat = new SimpleDateFormat(DateFormat.getBestDateTimePattern((Locale)Locale.getDefault(), (String)"dd.MM.yyyy"));
        this.getLoaderManager().initLoader(0, null, this.cursorLoaderCallback);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menu2.clear();
        menuInflater.inflate(2131886094, menu2);
        this.saveMenuItem = menu2.findItem(2131821493);
        this.setSaveMenuItemEnabled(false);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968636, viewGroup, false);
        this.init((View)layoutInflater);
        this.trackScreen();
        return layoutInflater;
    }

    public void onDateSet(DatePicker datePicker, int n, int n2, int n3) {
        this.selectedDate.set(1, n);
        this.selectedDate.set(2, n2);
        this.selectedDate.set(5, n3);
        this.checkDate();
        this.selectedDateView.setText((CharSequence)this.dateFormat.format(this.selectedDate.getTime()));
    }

    public boolean onOptionsItemSelected(MenuItem menuItem) {
        if (menuItem.getItemId() == 2131821493) {
            this.addMeasurement();
            return true;
        }
        return super.onOptionsItemSelected(menuItem);
    }

    public void onTimeSet(TimePicker timePicker, int n, int n2) {
        this.selectedDate.set(11, n);
        this.selectedDate.set(12, n2);
        this.checkDate();
        this.selectedTimeView.setText((CharSequence)this.timeFormat.format(this.selectedDate.getTime()));
    }

    private class AnyFieldTextWatcher
    extends SimpleTextWatcher {
        private View view;

        private AnyFieldTextWatcher(View view) {
            this.view = view;
        }

        @Override
        public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            AddWeightManualMeasurementFragment.this.setSaveMenuItemEnabled(AddWeightManualMeasurementFragment.this.isAnyFieldChanged());
            AddWeightManualMeasurementFragment.this.handleFieldsError(this.view);
        }
    }

    private static enum FieldState {
        OK,
        MORE,
        LESS,
        EMPTY;

    }

}

