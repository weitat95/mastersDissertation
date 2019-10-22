/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 */
package com.bumptech.glide.manager;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.manager.ActivityFragmentLifecycle;
import com.bumptech.glide.manager.RequestManagerRetriever;
import com.bumptech.glide.manager.RequestManagerTreeNode;
import java.util.HashSet;

@TargetApi(value=11)
public class RequestManagerFragment
extends Fragment {
    private final HashSet<RequestManagerFragment> childRequestManagerFragments;
    private final ActivityFragmentLifecycle lifecycle;
    private RequestManager requestManager;
    private final RequestManagerTreeNode requestManagerTreeNode = new FragmentRequestManagerTreeNode();
    private RequestManagerFragment rootRequestManagerFragment;

    public RequestManagerFragment() {
        this(new ActivityFragmentLifecycle());
    }

    @SuppressLint(value={"ValidFragment"})
    RequestManagerFragment(ActivityFragmentLifecycle activityFragmentLifecycle) {
        this.childRequestManagerFragments = new HashSet();
        this.lifecycle = activityFragmentLifecycle;
    }

    private void addChildRequestManagerFragment(RequestManagerFragment requestManagerFragment) {
        this.childRequestManagerFragments.add(requestManagerFragment);
    }

    private void removeChildRequestManagerFragment(RequestManagerFragment requestManagerFragment) {
        this.childRequestManagerFragments.remove((Object)requestManagerFragment);
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

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.rootRequestManagerFragment = RequestManagerRetriever.get().getRequestManagerFragment(this.getActivity().getFragmentManager());
        if (this.rootRequestManagerFragment != this) {
            this.rootRequestManagerFragment.addChildRequestManagerFragment(this);
        }
    }

    public void onDestroy() {
        super.onDestroy();
        this.lifecycle.onDestroy();
    }

    public void onDetach() {
        super.onDetach();
        if (this.rootRequestManagerFragment != null) {
            this.rootRequestManagerFragment.removeChildRequestManagerFragment(this);
            this.rootRequestManagerFragment = null;
        }
    }

    public void onLowMemory() {
        if (this.requestManager != null) {
            this.requestManager.onLowMemory();
        }
    }

    public void onStart() {
        super.onStart();
        this.lifecycle.onStart();
    }

    public void onStop() {
        super.onStop();
        this.lifecycle.onStop();
    }

    public void onTrimMemory(int n) {
        if (this.requestManager != null) {
            this.requestManager.onTrimMemory(n);
        }
    }

    public void setRequestManager(RequestManager requestManager) {
        this.requestManager = requestManager;
    }

    private class FragmentRequestManagerTreeNode
    implements RequestManagerTreeNode {
        private FragmentRequestManagerTreeNode() {
        }
    }

}

