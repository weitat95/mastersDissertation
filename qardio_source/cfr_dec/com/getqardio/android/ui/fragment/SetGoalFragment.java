/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.LoaderManager
 *  android.app.LoaderManager$LoaderCallbacks
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.Loader
 *  android.database.Cursor
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.FrameLayout
 *  android.widget.HorizontalScrollView
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.RelativeLayout
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.app.LoaderManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Loader;
import android.database.Cursor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.getqardio.android.datamodel.Goal;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.ui.dialog.CustomAlertDialog;
import com.getqardio.android.ui.dialog.GoalSavedDialog;
import com.getqardio.android.ui.widget.BmiLabelsView;
import com.getqardio.android.ui.widget.HorizontalNumberPicker;
import com.getqardio.android.ui.widget.OnGoalValueChangedListener;
import com.getqardio.android.ui.widget.SetGoalView;
import com.getqardio.android.ui.widget.WeeksPicker;
import com.getqardio.android.ui.widget.WeightHelper;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.Convert;
import java.util.Date;

public class SetGoalFragment
extends Fragment
implements LoaderManager.LoaderCallbacks<Cursor>,
SetGoalView.OnTutorialDiscoveredListener {
    private BmiLabelsView bmiLabelsView;
    private Goal currentGoal;
    private TextView currentWeightLabel;
    private float goalPerWeek;
    private GoalSavedDialog goalSavedDialog;
    private int goalWeeks;
    private float goalWeight;
    private boolean hasGoalData;
    private boolean hasProfileData;
    private TextView lossWeightUnitView;
    private TextView lossWeightView;
    private TextView perWeekUnitView;
    private TextView perWeekView;
    private WeeksPicker picker;
    private View rootView;
    private MenuItem saveButton;
    private HorizontalScrollView scrollView;
    private SetGoalView.Helper setGoalHelper = new SetGoalView.Helper(){

        @Override
        public String valueToWeightString(float f) {
            return Convert.floatToString(Float.valueOf(Utils.round(Float.valueOf(MetricUtils.convertWeight(0, SetGoalFragment.this.getWeightUnit(), f)), 1)), 1);
        }
    };
    private TextView setGoalLossGainLabel;
    private LinearLayout setGoalTutorialContainer;
    private RelativeLayout setGoalValuesContainer;
    private SetGoalView setGoalView;
    private TextView targetWeightUnitView;
    private TextView targetWeightView;
    private float userHeight;
    private float userWeight;
    private TextView weeksLabel;
    private FrameLayout weeksPickerContainer;
    private float weightDifference;
    private WeightHelper weightHelper = new WeightHelper(){

        @Override
        public float calculateBmi(int n, int n2) {
            return QardioBaseUtils.bmi(n, n2);
        }

        @Override
        public float calculateMaxWeight(float f) {
            return Utils.round(Float.valueOf(Goal.getMaxGoalWeight(f)), 1);
        }

        @Override
        public float calculateMinWeight(float f) {
            return Utils.round(Float.valueOf(Goal.getMinGoalWeight(f)), 1);
        }
    };

    private void applyGoal(Cursor cursor) {
        if (cursor.moveToFirst()) {
            this.currentGoal = DataHelper.CurrentGoalHelper.parseGoal(cursor);
        }
        this.hasGoalData = true;
    }

    private void applyProfile(Cursor cursor) {
        if (cursor.moveToFirst()) {
            this.userWeight = MetricUtils.convertWeight(this.getWeightUnit(), 0, DataHelper.ProfileHelper.parseWeight(cursor));
            this.userHeight = MetricUtils.convertHeight(this.getHeightUnit(), 0, DataHelper.ProfileHelper.parseHeight(cursor));
            this.setGoalView.setCurrentWeight(this.userWeight);
            this.bmiLabelsView.setHeight((int)this.userHeight);
            this.bmiLabelsView.setCurrentWeight(this.userWeight);
        }
        this.hasProfileData = true;
    }

    private float calculateGoalPerWeek() {
        float f;
        block3: {
            block2: {
                f = 0.0f;
                if (this.goalWeeks == 0) break block2;
                f = (this.goalWeight - this.userWeight) / (float)this.goalWeeks;
                if (!(f > 0.0f)) break block3;
                f = Math.min(f, 2.0f);
            }
            return f;
        }
        return Math.max(f, -2.0f);
    }

    private float calculateWeightDifference() {
        return Utils.round(Float.valueOf(this.goalWeight - this.userWeight), 1);
    }

    private int getHeightUnit() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.HEIGHT_UNIT")) {
            return bundle.getInt("com.getqardio.android.argument.HEIGHT_UNIT");
        }
        return 0;
    }

    private long getUserId() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.USER_ID")) {
            return bundle.getLong("com.getqardio.android.argument.USER_ID");
        }
        return -1L;
    }

    private int getWeightUnit() {
        Bundle bundle = this.getArguments();
        if (bundle != null && bundle.containsKey("com.getqardio.android.argument.WEIGHT_UNIT")) {
            return bundle.getInt("com.getqardio.android.argument.WEIGHT_UNIT");
        }
        return 0;
    }

    private void hideSetGoalValues() {
        this.setGoalTutorialContainer.setVisibility(0);
        this.setGoalValuesContainer.setVisibility(8);
        this.currentWeightLabel.setVisibility(0);
        this.weeksLabel.setVisibility(8);
        this.weeksPickerContainer.setVisibility(8);
        this.bmiLabelsView.setVisibility(8);
    }

    private void init() {
        this.weeksPickerContainer = (FrameLayout)this.rootView.findViewById(2131821315);
        this.weeksLabel = (TextView)this.rootView.findViewById(2131821317);
        this.currentWeightLabel = (TextView)this.rootView.findViewById(2131821318);
        ((ImageView)this.rootView.findViewById(2131821304)).setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                SetGoalFragment.this.onDeleteGoal();
            }
        });
        this.setGoalValuesContainer = (RelativeLayout)this.rootView.findViewById(2131821303);
        this.setGoalTutorialContainer = (LinearLayout)this.rootView.findViewById(2131821302);
        this.setGoalLossGainLabel = (TextView)this.rootView.findViewById(2131821308);
        this.setGoalView = (SetGoalView)this.rootView.findViewById(2131821314);
        this.setGoalView.setOnGoalValueChangedListener(new OnGoalValueChangedListener(){

            @Override
            public void onValueChanged(float f) {
                SetGoalFragment.this.onTargetWeightChanged();
                SetGoalFragment.this.bmiLabelsView.scrollTo(SetGoalFragment.this.scrollView, f);
            }
        });
        this.setGoalView.setWeightUnit(this.getString(MetricUtils.getWeightUnitNameRes(this.getWeightUnit())));
        this.setGoalView.setHelper(this.setGoalHelper);
        this.setGoalView.setWeightHelper(this.weightHelper);
        this.setGoalView.setOnTutorialDiscoveredListener(this);
        this.lossWeightView = (TextView)this.rootView.findViewById(2131821305);
        this.lossWeightUnitView = (TextView)this.rootView.findViewById(2131821306);
        this.targetWeightView = (TextView)this.rootView.findViewById(2131821309);
        this.targetWeightUnitView = (TextView)this.rootView.findViewById(2131821310);
        this.perWeekView = (TextView)this.rootView.findViewById(2131821311);
        this.perWeekUnitView = (TextView)this.rootView.findViewById(2131821312);
        this.picker = (WeeksPicker)this.rootView.findViewById(2131821316);
        this.picker.setMinMaxValues(0, 53);
        this.picker.setOnItemSelectedListener(new HorizontalNumberPicker.OnItemSelected(){

            @Override
            public void onItemSelected(int n) {
                SetGoalFragment.this.onWeeksCountChanged(n);
            }
        });
        this.bmiLabelsView = (BmiLabelsView)this.rootView.findViewById(2131821313);
        this.bmiLabelsView.setWeightHelper(this.weightHelper);
        this.scrollView = (HorizontalScrollView)this.rootView.findViewById(2131820596);
    }

    private boolean isGoalChanged() {
        return this.currentGoal != null && (this.currentGoal.target.floatValue() != this.goalWeight || this.currentGoal.targetPerWeek.floatValue() != this.goalPerWeek);
    }

    public static SetGoalFragment newInstance(long l, int n, int n2) {
        SetGoalFragment setGoalFragment = new SetGoalFragment();
        Bundle bundle = new Bundle(3);
        bundle.putLong("com.getqardio.android.argument.USER_ID", l);
        bundle.putInt("com.getqardio.android.argument.WEIGHT_UNIT", n);
        bundle.putInt("com.getqardio.android.argument.HEIGHT_UNIT", n2);
        setGoalFragment.setArguments(bundle);
        return setGoalFragment;
    }

    private void onDeleteGoal() {
        Object object = this.getActivity();
        object = CustomAlertDialog.newInstance((Context)object, object.getString(2131361900), object.getString(2131361899));
        ((CustomAlertDialog)object).setOnNegativeClickListener(new DialogInterface.OnClickListener((CustomAlertDialog)object){
            final /* synthetic */ CustomAlertDialog val$customAlertDialog;
            {
                this.val$customAlertDialog = customAlertDialog;
            }

            public void onClick(DialogInterface dialogInterface, int n) {
                this.val$customAlertDialog.dismiss();
            }
        });
        ((CustomAlertDialog)object).setOnClickListener(new DialogInterface.OnClickListener(){

            public void onClick(DialogInterface dialogInterface, int n) {
                SetGoalFragment.this.currentGoal = null;
                SetGoalFragment.this.resetViewsData();
                DataHelper.CurrentGoalHelper.deleteGoal((Context)SetGoalFragment.this.getActivity(), SetGoalFragment.this.getUserId());
                SetGoalFragment.this.hideSetGoalValues();
                SetGoalFragment.this.setGoalView.setTutorial(true);
            }
        });
        object.show();
    }

    private void onTargetWeightChanged() {
        this.goalWeight = this.setGoalView.getGoalWeight();
        this.goalWeeks = this.picker.getSelectedItem();
        this.goalPerWeek = this.calculateGoalPerWeek();
        this.weightDifference = this.calculateWeightDifference();
        this.updateGoalData();
    }

    private void onWeeksCountChanged(int n) {
        this.goalWeeks = n;
        this.goalPerWeek = this.calculateGoalPerWeek();
        this.updateGoalData();
    }

    private void resetViewsData() {
        if (this.saveButton != null) {
            this.saveButton.setEnabled(false);
        }
        this.setGoalView.setGoalWeight(this.userWeight);
        this.picker.resetWeeksSelection();
        this.goalWeeks = 1;
        this.goalPerWeek = 0.0f;
        this.goalWeight = this.userWeight;
        this.weightDifference = 0.0f;
        this.bmiLabelsView.scrollTo(this.scrollView, this.userWeight);
        this.updateGoalData();
    }

    private void saveGoal() {
        Goal goal = new Goal();
        goal.userId = this.getUserId();
        goal.startDate = new Date().getTime();
        goal.type = "weight";
        goal.targetPerWeek = Float.valueOf(this.goalPerWeek);
        goal.target = Float.valueOf(this.goalWeight);
        DataHelper.CurrentGoalHelper.saveGoal((Context)this.getActivity(), goal, true);
        this.goalSavedDialog = GoalSavedDialog.newInstance((Context)this.getActivity());
        this.goalSavedDialog.show();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void showGoalData() {
        boolean bl = true;
        boolean bl2 = this.currentGoal != null;
        SetGoalView setGoalView = this.setGoalView;
        if (bl2) {
            bl = false;
        }
        setGoalView.setTutorial(bl);
        if (!bl2) {
            this.hideSetGoalValues();
            return;
        }
        this.showSetGoalValues();
        this.goalWeight = this.currentGoal.target.floatValue();
        this.goalPerWeek = this.currentGoal.targetPerWeek.floatValue();
        this.weightDifference = this.calculateWeightDifference();
        if (this.currentGoal.targetPerWeek.floatValue() != 0.0f) {
            this.goalWeeks = (int)Math.abs(Utils.round(Float.valueOf((this.currentGoal.target.floatValue() - this.userWeight) / this.currentGoal.targetPerWeek.floatValue()), 0));
        }
        this.bmiLabelsView.scrollTo(this.scrollView, this.goalWeight);
        this.setGoalView.setGoalWeight(this.goalWeight);
        this.updateGoalData();
    }

    private void showSetGoalValues() {
        this.setGoalTutorialContainer.setVisibility(8);
        this.setGoalValuesContainer.setVisibility(0);
        this.currentWeightLabel.setVisibility(8);
        this.weeksLabel.setVisibility(0);
        this.weeksPickerContainer.setVisibility(0);
        this.bmiLabelsView.setVisibility(0);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateGoalData() {
        if (this.saveButton != null) {
            this.saveButton.setEnabled(this.isGoalChanged());
        }
        TextView textView = this.setGoalLossGainLabel;
        int n = this.weightDifference < 0.0f ? 2131362271 : 2131362242;
        textView.setText(n);
        n = this.getWeightUnit();
        int n2 = MetricUtils.getWeightUnitNameRes(n);
        this.lossWeightView.setText((CharSequence)Convert.floatToString(Float.valueOf(MetricUtils.convertWeight(0, n, this.weightDifference)), 1));
        this.lossWeightUnitView.setText(n2);
        this.perWeekView.setText((CharSequence)Convert.floatToString(Float.valueOf(MetricUtils.convertWeight(0, n, this.goalPerWeek)), 1));
        this.perWeekUnitView.setText(n2);
        this.targetWeightView.setText((CharSequence)Convert.floatToString(Float.valueOf(MetricUtils.convertWeight(0, n, this.goalWeight)), 1));
        this.targetWeightUnitView.setText(n2);
        this.picker.setMinSelectableValue(Goal.getMinWeeksCount(this.userWeight, this.goalWeight));
        this.picker.setSelectedItem(this.goalWeeks);
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
        this.setHasOptionsMenu(true);
        this.getLoaderManager().initLoader(1, null, (LoaderManager.LoaderCallbacks)this);
        this.getLoaderManager().initLoader(2, null, (LoaderManager.LoaderCallbacks)this);
    }

    public Loader<Cursor> onCreateLoader(int n, Bundle bundle) {
        switch (n) {
            default: {
                return null;
            }
            case 1: {
                return DataHelper.ProfileHelper.getProfileLoader((Context)this.getActivity(), this.getUserId(), DataHelper.ProfileHelper.SET_GOAL_PROJECTION);
            }
            case 2: 
        }
        return DataHelper.CurrentGoalHelper.getGoalLoader((Context)this.getActivity(), this.getUserId(), null);
    }

    public void onCreateOptionsMenu(Menu menu2, MenuInflater menuInflater) {
        menu2.clear();
        menuInflater.inflate(2131886094, menu2);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.rootView = layoutInflater.inflate(2130968824, viewGroup, false);
        return this.rootView;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        switch (loader.getId()) {
            case 1: {
                this.applyProfile(cursor);
            }
            default: {
                break;
            }
            case 2: {
                this.applyGoal(cursor);
            }
        }
        if (this.hasProfileData && this.hasGoalData) {
            this.showGoalData();
        }
    }

    public void onLoaderReset(Loader<Cursor> loader) {
    }

    public void onPause() {
        super.onPause();
        if (this.goalSavedDialog != null && this.goalSavedDialog.isShowing()) {
            this.goalSavedDialog.dismiss();
        }
    }

    public void onPrepareOptionsMenu(Menu menu2) {
        super.onPrepareOptionsMenu(menu2);
        this.saveButton = menu2.findItem(2131821493);
        if (this.saveButton != null) {
            this.saveButton.setEnabled(false);
            this.saveButton.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener(){

                public boolean onMenuItemClick(MenuItem menuItem) {
                    SetGoalFragment.this.saveGoal();
                    return false;
                }
            });
        }
    }

    @Override
    public void onTutorialDiscovered() {
        this.resetViewsData();
        this.showSetGoalValues();
        this.currentGoal = new Goal();
        this.currentGoal.targetPerWeek = Float.valueOf(this.goalPerWeek);
        this.currentGoal.target = Float.valueOf(this.goalWeight);
    }

    public void onViewCreated(View view, Bundle bundle) {
        super.onViewCreated(view, bundle);
        this.init();
    }

}

