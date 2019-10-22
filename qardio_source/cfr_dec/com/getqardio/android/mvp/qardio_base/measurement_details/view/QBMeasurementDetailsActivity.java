/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 *  android.content.Intent
 *  android.os.Bundle
 *  android.text.TextUtils
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.qardio_base.measurement_details.view;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;
import com.getqardio.android.mvp.qardio_base.measurement_details.DaggerQBMeasurementDetailsComponent;
import com.getqardio.android.mvp.qardio_base.measurement_details.QBMeasurementDetailsComponent;
import com.getqardio.android.mvp.qardio_base.measurement_details.QBMeasurementDetailsContract;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenter;
import com.getqardio.android.mvp.qardio_base.measurement_details.presentation.QBMeasurementDetailsPresenterModule;
import com.getqardio.android.mvp.qardio_base.measurement_details.view.QBMeasurementDetailsActivity$$Lambda$1;
import com.getqardio.android.ui.activity.BaseActivity;
import com.getqardio.android.ui.activity.EditNoteActivity;
import com.getqardio.android.ui.widget.BmiResultChart;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.ui.ActivityUtils;
import java.util.Date;

public class QBMeasurementDetailsActivity
extends BaseActivity
implements QBMeasurementDetailsContract.View {
    @BindView
    BmiResultChart bmiChart;
    @BindView
    TextView boneView;
    @BindView
    TextView dateView;
    @BindView
    TextView fatView;
    @BindView
    TextView muscleView;
    @BindView
    TextView noteView;
    QBMeasurementDetailsPresenter presenter;
    @BindView
    TextView timeView;
    @BindView
    TextView waterView;
    @BindView
    TextView weightUnitView;
    @BindView
    TextView weightView;

    private long getMeasurementDate() {
        return this.getIntent().getExtras().getLong("com.getqardio.android.extras.WEIGHT_MEASUREMENT_DATE", -1L);
    }

    public static Intent getStartIntent(Context context, long l, long l2, int n) {
        context = new Intent(context, QBMeasurementDetailsActivity.class);
        context.putExtra("com.getqardio.android.extras.USER_ID", l);
        context.putExtra("com.getqardio.android.extras.WEIGHT_MEASUREMENT_DATE", l2);
        context.putExtra("com.getqardio.android.extras.WEIGHT_UNIT", n);
        return context;
    }

    public static Intent getStartIntent(Context context, long l, long l2, int n, boolean bl) {
        context = new Intent(context, QBMeasurementDetailsActivity.class);
        context.putExtra("com.getqardio.android.extras.USER_ID", l);
        context.putExtra("com.getqardio.android.extras.WEIGHT_MEASUREMENT_DATE", l2);
        context.putExtra("com.getqardio.android.extras.WEIGHT_UNIT", n);
        context.putExtra("com.getqardio.android.extras.EXTRA_IS_EDITABLE", bl);
        return context;
    }

    private long getUserId() {
        return this.getIntent().getExtras().getLong("com.getqardio.android.extras.USER_ID", -1L);
    }

    private boolean isEditable() {
        return this.getIntent().getExtras().getBoolean("com.getqardio.android.extras.EXTRA_IS_EDITABLE", true);
    }

    private void showEditNoteActivity() {
        this.startActivityForResult(EditNoteActivity.getStartIntent((Context)this, this.getUserId(), this.noteView.getText().toString(), "weight"), 0);
    }

    @Override
    public int getWeightUnit() {
        return this.getIntent().getExtras().getInt("com.getqardio.android.extras.WEIGHT_UNIT", -1);
    }

    /* synthetic */ void lambda$onCreate$0(View view) {
        this.showEditNoteActivity();
    }

    @Override
    public void onActivityResult(int n, int n2, Intent object) {
        super.onActivityResult(n, n2, (Intent)object);
        if (n == 0 && n2 == -1) {
            object = EditNoteActivity.extractNoteFromIntent(object);
            this.noteView.setText((CharSequence)object);
            this.presenter.onNoteChanged(this.getUserId(), this.getMeasurementDate(), (String)object);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(2131034130, 2131034133);
    }

    @Override
    protected void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        this.setContentView(2130968793);
        ButterKnife.bind(this);
        object = (MvpApplication)this.getApplication();
        DaggerQBMeasurementDetailsComponent.builder().repositoryComponent(((MvpApplication)((Object)object)).getApplicationComponent()).qBMeasurementDetailsPresenterModule(new QBMeasurementDetailsPresenterModule(this)).build().inject(this);
        ActivityUtils.getActionBar(this).setDisplayHomeAsUpEnabled(true);
        if (this.isEditable()) {
            this.noteView.setOnClickListener(QBMeasurementDetailsActivity$$Lambda$1.lambdaFactory$(this));
            return;
        }
        this.noteView.setEnabled(false);
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

    @Override
    public void onStart() {
        super.onStart();
        this.presenter.subscribe();
        this.presenter.fetchData(this.getUserId(), this.getMeasurementDate());
    }

    @Override
    public void onStop() {
        this.presenter.unsubscribe();
        super.onStop();
    }

    @Override
    public void setBMIChartValue(Float f) {
        this.bmiChart.setBodyMassIndex(f);
    }

    @Override
    public void setBoneValue(String string2) {
        this.boneView.setText((CharSequence)string2);
    }

    @Override
    public void setDateValue(Date date) {
        this.dateView.setText((CharSequence)DateUtils.formatDateInLocaleAndWithTodayAndYesterday((Context)this, date));
    }

    @Override
    public void setFatValue(String string2) {
        this.fatView.setText((CharSequence)string2);
    }

    @Override
    public void setMuscleValue(String string2) {
        this.muscleView.setText((CharSequence)string2);
    }

    @Override
    public void setNoteValue(String string2) {
        if (TextUtils.isEmpty((CharSequence)string2) && !this.isEditable()) {
            this.noteView.setVisibility(8);
            return;
        }
        this.noteView.setText((CharSequence)string2);
    }

    @Override
    public void setTimeValue(String string2) {
        this.timeView.setText((CharSequence)string2);
    }

    @Override
    public void setWaterValue(String string2) {
        this.waterView.setText((CharSequence)string2);
    }

    @Override
    public void setWeightUnitValue(int n) {
        this.weightUnitView.setText(n);
    }

    @Override
    public void setWeightValue(String string2) {
        this.weightView.setText((CharSequence)string2);
    }
}

