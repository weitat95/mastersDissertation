/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.activity_tracker.goals.view;

import android.view.View;
import android.widget.TextView;
import butterknife.Unbinder;
import butterknife.internal.DebouncingOnClickListener;
import butterknife.internal.Utils;
import com.getqardio.android.mvp.activity_tracker.goals.view.ActivityTrackerSetGoalForActivityTypeFragment;

public class ActivityTrackerSetGoalForActivityTypeFragment_ViewBinding
implements Unbinder {
    private ActivityTrackerSetGoalForActivityTypeFragment target;
    private View view2131820836;
    private View view2131820837;
    private View view2131820839;
    private View view2131820841;
    private View view2131820843;

    public ActivityTrackerSetGoalForActivityTypeFragment_ViewBinding(final ActivityTrackerSetGoalForActivityTypeFragment activityTrackerSetGoalForActivityTypeFragment, View view) {
        this.target = activityTrackerSetGoalForActivityTypeFragment;
        activityTrackerSetGoalForActivityTypeFragment.title = Utils.findRequiredViewAsType(view, 2131820833, "field 'title'", TextView.class);
        View view2 = Utils.findRequiredView(view, 2131820836, "field 'setGoal1' and method 'saveGoal1ForactivityType'");
        activityTrackerSetGoalForActivityTypeFragment.setGoal1 = Utils.castView(view2, 2131820836, "field 'setGoal1'", TextView.class);
        this.view2131820836 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                activityTrackerSetGoalForActivityTypeFragment.saveGoal1ForactivityType();
            }
        });
        view2 = Utils.findRequiredView(view, 2131820837, "field 'setGoal2' and method 'saveGoal2ForactivityType'");
        activityTrackerSetGoalForActivityTypeFragment.setGoal2 = Utils.castView(view2, 2131820837, "field 'setGoal2'", TextView.class);
        this.view2131820837 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                activityTrackerSetGoalForActivityTypeFragment.saveGoal2ForactivityType();
            }
        });
        view2 = Utils.findRequiredView(view, 2131820839, "field 'setGoal3' and method 'saveGoal3ForactivityType'");
        activityTrackerSetGoalForActivityTypeFragment.setGoal3 = Utils.castView(view2, 2131820839, "field 'setGoal3'", TextView.class);
        this.view2131820839 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                activityTrackerSetGoalForActivityTypeFragment.saveGoal3ForactivityType();
            }
        });
        view2 = Utils.findRequiredView(view, 2131820841, "field 'setGoal4' and method 'saveGoal4ForactivityType'");
        activityTrackerSetGoalForActivityTypeFragment.setGoal4 = Utils.castView(view2, 2131820841, "field 'setGoal4'", TextView.class);
        this.view2131820841 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                activityTrackerSetGoalForActivityTypeFragment.saveGoal4ForactivityType();
            }
        });
        view2 = Utils.findRequiredView(view, 2131820843, "field 'setGoal5' and method 'saveGoal5ForactivityType'");
        activityTrackerSetGoalForActivityTypeFragment.setGoal5 = Utils.castView(view2, 2131820843, "field 'setGoal5'", TextView.class);
        this.view2131820843 = view2;
        view2.setOnClickListener((View.OnClickListener)new DebouncingOnClickListener(){

            @Override
            public void doClick(View view) {
                activityTrackerSetGoalForActivityTypeFragment.saveGoal5ForactivityType();
            }
        });
        activityTrackerSetGoalForActivityTypeFragment.setGoal6 = Utils.findRequiredViewAsType(view, 2131820845, "field 'setGoal6'", TextView.class);
    }

}

