/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Application
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.os.Bundle
 *  android.preference.PreferenceManager
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.TextView
 *  android.widget.Toast
 */
package com.getqardio.android.mvp.activity_tracker.goals.view;

import android.app.Activity;
import android.app.Application;
import android.app.Fragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.mvp.MvpApplication;
import com.getqardio.android.mvp.activity_tracker.goals.DaggerGoalActivityComponent;
import com.getqardio.android.mvp.activity_tracker.goals.GoalActivityComponent;
import com.getqardio.android.mvp.activity_tracker.goals.GoalActivityContract;
import com.getqardio.android.mvp.activity_tracker.goals.presentation.SetGoalForActivityTypePresenter;
import com.getqardio.android.mvp.activity_tracker.goals.presentation.SetGoalForActivityTypePresenterModule;
import com.getqardio.android.mvp.common.injection.RepositoryComponent;

public class ActivityTrackerSetGoalForActivityTypeFragment
extends Fragment
implements GoalActivityContract.View {
    private int activityTypeGoal;
    private String argumentSetGoalInSharedPreferences;
    SetGoalForActivityTypePresenter presenter;
    @BindView
    TextView setGoal1;
    @BindView
    TextView setGoal2;
    @BindView
    TextView setGoal3;
    @BindView
    TextView setGoal4;
    @BindView
    TextView setGoal5;
    @BindView
    TextView setGoal6;
    @BindView
    TextView title;

    private void generateViewForCycleGoal() {
        this.title.setText(2131362162);
        this.setGoal1.setText((CharSequence)"15");
        this.setGoal2.setText((CharSequence)"30");
        this.setGoal3.setText((CharSequence)"60");
        this.setGoal4.setText((CharSequence)"90");
        this.setGoal5.setText((CharSequence)"120");
    }

    private void generateViewForMinutesOfActivity() {
        this.title.setText(2131362163);
        this.setGoal1.setText((CharSequence)"15");
        this.setGoal2.setText((CharSequence)"30");
        this.setGoal3.setText((CharSequence)"40");
        this.setGoal4.setText((CharSequence)"90");
        this.setGoal5.setText((CharSequence)"120");
        this.argumentSetGoalInSharedPreferences = "com.getqardio.android.extra.GOAL_VALUE_FOR_ACTIVITY_TRACKER";
    }

    private void generateViewForRunGoal() {
        this.title.setText(2131362164);
        this.setGoal1.setText((CharSequence)"15");
        this.setGoal2.setText((CharSequence)"30");
        this.setGoal3.setText((CharSequence)"60");
        this.setGoal4.setText((CharSequence)"90");
        this.setGoal5.setText((CharSequence)"120");
    }

    private void generateViewForStepsGoal() {
        this.title.setText(2131362165);
        this.setGoal1.setText((CharSequence)"1000");
        this.setGoal2.setText((CharSequence)"2000");
        this.setGoal3.setText((CharSequence)"5000");
        this.setGoal4.setText((CharSequence)"10000");
        this.setGoal5.setText((CharSequence)"15000");
        this.argumentSetGoalInSharedPreferences = "com.getqardio.android.extra.GOAL_VALUE_FOR_STEPS_TRACKER";
    }

    private void generateViewForWalkGoal() {
        this.title.setText(2131362166);
        this.setGoal1.setText((CharSequence)"15");
        this.setGoal2.setText((CharSequence)"30");
        this.setGoal3.setText((CharSequence)"60");
        this.setGoal4.setText((CharSequence)"90");
        this.setGoal5.setText((CharSequence)"120");
    }

    private void generateViewForWeightGoal() {
        this.title.setText(2131362167);
    }

    private long getUserId() {
        return CustomApplication.getApplication().getCurrentUserId();
    }

    public static ActivityTrackerSetGoalForActivityTypeFragment newInstance(int n) {
        ActivityTrackerSetGoalForActivityTypeFragment activityTrackerSetGoalForActivityTypeFragment = new ActivityTrackerSetGoalForActivityTypeFragment();
        Bundle bundle = new Bundle();
        bundle.putInt("setGoal", n);
        activityTrackerSetGoalForActivityTypeFragment.setArguments(bundle);
        return activityTrackerSetGoalForActivityTypeFragment;
    }

    private void storeGoalInSharedPreferences(int n) {
        SharedPreferences.Editor editor = PreferenceManager.getDefaultSharedPreferences((Context)this.getActivity()).edit();
        editor.putInt(this.argumentSetGoalInSharedPreferences, n);
        editor.apply();
    }

    public void onCreate(Bundle object) {
        super.onCreate((Bundle)object);
        object = (MvpApplication)this.getActivity().getApplication();
        DaggerGoalActivityComponent.builder().repositoryComponent(((MvpApplication)((Object)object)).getApplicationComponent()).setGoalForActivityTypePresenterModule(new SetGoalForActivityTypePresenterModule(this)).build().inject(this);
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        this.activityTypeGoal = this.getArguments().getInt("setGoal");
        layoutInflater = (ViewGroup)layoutInflater.inflate(2130968626, viewGroup, false);
        ButterKnife.bind(this, (View)layoutInflater);
        switch (this.activityTypeGoal) {
            default: {
                return layoutInflater;
            }
            case 0: {
                this.generateViewForWalkGoal();
                return layoutInflater;
            }
            case 1: {
                this.generateViewForRunGoal();
                return layoutInflater;
            }
            case 2: {
                this.generateViewForCycleGoal();
                return layoutInflater;
            }
            case 4: {
                this.generateViewForWeightGoal();
                return layoutInflater;
            }
            case 3: {
                this.generateViewForStepsGoal();
                return layoutInflater;
            }
            case 5: 
        }
        this.generateViewForMinutesOfActivity();
        return layoutInflater;
    }

    @OnClick
    public void saveGoal1ForactivityType() {
        this.storeGoalInSharedPreferences(Integer.parseInt(this.setGoal1.getText().toString()));
        this.presenter.setGoalForActivityType(this.getUserId(), this.activityTypeGoal, Integer.parseInt(this.setGoal1.getText().toString()));
        Toast.makeText((Context)this.getActivity(), (CharSequence)("set goal: " + this.setGoal1.getText() + " for goal type: " + this.activityTypeGoal), (int)1).show();
        this.getActivity().finish();
    }

    @OnClick
    public void saveGoal2ForactivityType() {
        this.storeGoalInSharedPreferences(Integer.parseInt(this.setGoal2.getText().toString()));
        this.presenter.setGoalForActivityType(this.getUserId(), this.activityTypeGoal, Integer.parseInt(this.setGoal2.getText().toString()));
        Toast.makeText((Context)this.getActivity(), (CharSequence)("set goal: " + this.setGoal2.getText() + " for goal type: " + this.activityTypeGoal), (int)1).show();
        this.getActivity().finish();
    }

    @OnClick
    public void saveGoal3ForactivityType() {
        this.storeGoalInSharedPreferences(Integer.parseInt(this.setGoal3.getText().toString()));
        this.presenter.setGoalForActivityType(this.getUserId(), this.activityTypeGoal, Integer.parseInt(this.setGoal3.getText().toString()));
        Toast.makeText((Context)this.getActivity(), (CharSequence)("set goal: " + this.setGoal3.getText() + " for goal type: " + this.activityTypeGoal), (int)1).show();
        this.getActivity().finish();
    }

    @OnClick
    public void saveGoal4ForactivityType() {
        this.storeGoalInSharedPreferences(Integer.parseInt(this.setGoal4.getText().toString()));
        this.presenter.setGoalForActivityType(this.getUserId(), this.activityTypeGoal, Integer.parseInt(this.setGoal4.getText().toString()));
        Toast.makeText((Context)this.getActivity(), (CharSequence)("set goal: " + this.setGoal4.getText() + " for goal type: " + this.activityTypeGoal), (int)1).show();
        this.getActivity().finish();
    }

    @OnClick
    public void saveGoal5ForactivityType() {
        this.storeGoalInSharedPreferences(Integer.parseInt(this.setGoal5.getText().toString()));
        this.presenter.setGoalForActivityType(this.getUserId(), this.activityTypeGoal, Integer.valueOf(this.setGoal5.getText().toString()));
        Toast.makeText((Context)this.getActivity(), (CharSequence)("set goal: " + this.setGoal5.getText() + " for goal type: " + this.activityTypeGoal), (int)1).show();
        this.getActivity().finish();
    }
}

