/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.goals.presentation;

import com.getqardio.android.mvp.activity_tracker.goals.GoalActivityContract;
import com.getqardio.android.mvp.activity_tracker.goals.model.GoalForActivityTypeRepository;
import com.getqardio.android.mvp.activity_tracker.history.model.local.ActivityTrackedGroupedByDay;
import io.reactivex.Single;
import io.reactivex.disposables.CompositeDisposable;

public class SetGoalForActivityTypePresenter {
    private final GoalForActivityTypeRepository repository;
    private CompositeDisposable subscriptions = new CompositeDisposable();
    private GoalActivityContract.View view;

    SetGoalForActivityTypePresenter(GoalForActivityTypeRepository goalForActivityTypeRepository, GoalActivityContract.View view) {
        this.repository = goalForActivityTypeRepository;
        this.view = view;
    }

    public void setGoalForActivityType(long l, int n, int n2) {
        this.repository.setGoalForActivityType(l, n, n2);
    }
}

