/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.app.Activity
 */
package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.manager.ActivityFragmentLifecycle;
import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import java.util.HashSet;

public class SupportRequestManagerFragment
extends Fragment {
    private final HashSet<SupportRequestManagerFragment> childRequestManagerFragments;
    private final ActivityFragmentLifecycle lifecycle;
    private RequestManager requestManager;
    private final RequestManagerTreeNode requestManagerTreeNode = new SupportFragmentRequestManagerTreeNode();
    private SupportRequestManagerFragment rootRequestManagerFragment;

    public SupportRequestManagerFragment() {
        this(new ActivityFragmentLifecycle());
    }

    @SuppressLint(value={"ValidFragment"})
    public SupportRequestManagerFragment(ActivityFragmentLifecycle activityFragmentLifecycle) {
        this.childRequestManagerFragments = new HashSet();
        this.lifecycle = activityFragmentLifecycle;
    }

    private void addChildRequestManagerFragment(SupportRequestManagerFragment supportRequestManagerFragment) {
        this.childRequestManagerFragments.add(supportRequestManagerFragment);
    }

    private void removeChildRequestManagerFragment(SupportRequestManagerFragment supportRequestManagerFragment) {
        this.childRequestManagerFragments.remove(supportRequestManagerFragment);
    }

    ActivityFragmentLifecycle getLifecycle() {
        return this.lifecycle;
    }

    public RequestManager getRequestManager() {
        return this.requestManager;
    }

    public RequestManagerTreeNode getRequestManagerTreeNode() {
        return this.requestManagerTreeNode;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.rootRequestManagerFragment = RequestManagerRetriever.get().getSupportRequestManagerFragment(this.getActivity().getSupportFragmentManager());
        if (this.rootRequestManagerFragment != this) {
            this.rootRequestManagerFragment.addChildRequestManagerFragment(this);
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        this.lifecycle.onDestroy();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        if (this.rootRequestManagerFragment != null) {
            this.rootRequestManagerFragment.removeChildRequestManagerFragment(this);
            this.rootRequestManagerFragment = null;
        }
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        if (this.requestManager != null) {
            this.requestManager.onLowMemory();
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        this.lifecycle.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
        this.lifecycle.onStop();
    }

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    private class SupportFragmentRequestManagerTreeNode
    implements RequestManagerTreeNode {
        private SupportFragmentRequestManagerTreeNode() {
        }
    }

}

