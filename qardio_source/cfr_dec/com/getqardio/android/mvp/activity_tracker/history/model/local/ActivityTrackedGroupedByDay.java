/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.activity_tracker.history.model.local;

import io.realm.ActivityTrackedGroupedByDayRealmProxyInterface;
import io.realm.RealmObject;
import io.realm.internal.RealmObjectProxy;

public class ActivityTrackedGroupedByDay
extends RealmObject
implements ActivityTrackedGroupedByDayRealmProxyInterface {
    public long endTimestamp;
    public int goalActivity;
    public int goalCycle;
    public int goalRun;
    public int goalSteps;
    public int goalWalk;
    public int goalWeight;
    private int notifyActivityGoal;
    private int notifyStepsGoal;
    private int notifyTotalGoal;
    public long startTimestamp;
    public long totalSecondsCycling;
    public long totalSecondsOfActivity;
    public long totalSecondsRunning;
    public long totalSecondsWalking;
    public long totalSteps;
    private long userId;

    public ActivityTrackedGroupedByDay() {
        if (this instanceof RealmObjectProxy) {
            ((RealmObjectProxy)((Object)this)).realm$injectObjectContext();
        }
        this.realmSet$notifyTotalGoal(-1);
        this.realmSet$notifyActivityGoal(-1);
        this.realmSet$notifyStepsGoal(-1);
    }

    @Override
    public long realmGet$endTimestamp() {
        return this.endTimestamp;
    }

    @Override
    public int realmGet$goalActivity() {
        return this.goalActivity;
    }

    @Override
    public int realmGet$goalCycle() {
        return this.goalCycle;
    }

    @Override
    public int realmGet$goalRun() {
        return this.goalRun;
    }

    @Override
    public int realmGet$goalSteps() {
        return this.goalSteps;
    }

    @Override
    public int realmGet$goalWalk() {
        return this.goalWalk;
    }

    @Override
    public int realmGet$goalWeight() {
        return this.goalWeight;
    }

    @Override
    public int realmGet$notifyActivityGoal() {
        return this.notifyActivityGoal;
    }

    @Override
    public int realmGet$notifyStepsGoal() {
        return this.notifyStepsGoal;
    }

    @Override
    public int realmGet$notifyTotalGoal() {
        return this.notifyTotalGoal;
    }

    @Override
    public long realmGet$startTimestamp() {
        return this.startTimestamp;
    }

    @Override
    public long realmGet$totalSecondsCycling() {
        return this.totalSecondsCycling;
    }

    @Override
    public long realmGet$totalSecondsOfActivity() {
        return this.totalSecondsOfActivity;
    }

    @Override
    public long realmGet$totalSecondsRunning() {
        return this.totalSecondsRunning;
    }

    @Override
    public long realmGet$totalSecondsWalking() {
        return this.totalSecondsWalking;
    }

    @Override
    public long realmGet$totalSteps() {
        return this.totalSteps;
    }

    @Override
    public long realmGet$userId() {
        return this.userId;
    }

    @Override
    public void realmSet$endTimestamp(long l) {
        this.endTimestamp = l;
    }

    @Override
    public void realmSet$goalActivity(int n) {
        this.goalActivity = n;
    }

    @Override
    public void realmSet$goalCycle(int n) {
        this.goalCycle = n;
    }

    @Override
    public void realmSet$goalRun(int n) {
        this.goalRun = n;
    }

    @Override
    public void realmSet$goalSteps(int n) {
        this.goalSteps = n;
    }

    @Override
    public void realmSet$goalWalk(int n) {
        this.goalWalk = n;
    }

    @Override
    public void realmSet$goalWeight(int n) {
        this.goalWeight = n;
    }

    @Override
    public void realmSet$notifyActivityGoal(int n) {
        this.notifyActivityGoal = n;
    }

    @Override
    public void realmSet$notifyStepsGoal(int n) {
        this.notifyStepsGoal = n;
    }

    @Override
    public void realmSet$notifyTotalGoal(int n) {
        this.notifyTotalGoal = n;
    }

    @Override
    public void realmSet$startTimestamp(long l) {
        this.startTimestamp = l;
    }

    @Override
    public void realmSet$totalSecondsCycling(long l) {
        this.totalSecondsCycling = l;
    }

    @Override
    public void realmSet$totalSecondsOfActivity(long l) {
        this.totalSecondsOfActivity = l;
    }

    @Override
    public void realmSet$totalSecondsRunning(long l) {
        this.totalSecondsRunning = l;
    }

    @Override
    public void realmSet$totalSecondsWalking(long l) {
        this.totalSecondsWalking = l;
    }

    @Override
    public void realmSet$totalSteps(long l) {
        this.totalSteps = l;
    }

    @Override
    public void realmSet$userId(long l) {
        this.userId = l;
    }
}

