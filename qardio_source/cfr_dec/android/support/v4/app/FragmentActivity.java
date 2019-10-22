/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.content.res.Configuration
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.os.Message
 *  android.os.Parcelable
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.LayoutInflater
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.View
 *  android.view.Window
 *  android.view.WindowManager
 *  android.view.WindowManager$LayoutParams
 */
package android.support.v4.app;

import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.content.res.Configuration;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.BaseFragmentActivityApi16;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentController;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerNonConfig;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.SharedElementCallback;
import android.support.v4.util.SimpleArrayMap;
import android.support.v4.util.SparseArrayCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public class FragmentActivity
extends BaseFragmentActivityApi16
implements ActivityCompat.OnRequestPermissionsResultCallback,
ActivityCompat.RequestPermissionsRequestCodeValidator {
    static final String ALLOCATED_REQUEST_INDICIES_TAG = "android:support:request_indicies";
    static final String FRAGMENTS_TAG = "android:support:fragments";
    static final int MAX_NUM_PENDING_FRAGMENT_ACTIVITY_RESULTS = 65534;
    static final int MSG_REALLY_STOPPED = 1;
    static final int MSG_RESUME_PENDING = 2;
    static final String NEXT_CANDIDATE_REQUEST_INDEX_TAG = "android:support:next_request_index";
    static final String REQUEST_FRAGMENT_WHO_TAG = "android:support:request_fragment_who";
    private static final String TAG = "FragmentActivity";
    boolean mCreated;
    final FragmentController mFragments;
    final Handler mHandler = new Handler(){

        /*
         * Enabled aggressive block sorting
         */
        public void handleMessage(Message message) {
            switch (message.what) {
                default: {
                    super.handleMessage(message);
                    return;
                }
                case 1: {
                    if (!FragmentActivity.this.mStopped) return;
                    {
                        FragmentActivity.this.doReallyStop(false);
                        return;
                    }
                }
                case 2: 
            }
            FragmentActivity.this.onResumeFragments();
            FragmentActivity.this.mFragments.execPendingActions();
        }
    };
    int mNextCandidateRequestIndex;
    SparseArrayCompat<String> mPendingFragmentActivityResults;
    boolean mReallyStopped = true;
    boolean mRequestedPermissionsFromFragment;
    boolean mResumed;
    boolean mRetaining;
    boolean mStopped = true;

    public FragmentActivity() {
        this.mFragments = FragmentController.createController(new HostCallbacks());
    }

    private int allocateRequestIndex(Fragment fragment) {
        if (this.mPendingFragmentActivityResults.size() >= 65534) {
            throw new IllegalStateException("Too many pending Fragment activity results.");
        }
        while (this.mPendingFragmentActivityResults.indexOfKey(this.mNextCandidateRequestIndex) >= 0) {
            this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % 65534;
        }
        int n = this.mNextCandidateRequestIndex;
        this.mPendingFragmentActivityResults.put(n, fragment.mWho);
        this.mNextCandidateRequestIndex = (this.mNextCandidateRequestIndex + 1) % 65534;
        return n;
    }

    @Override
    final View dispatchFragmentsOnCreateView(View view, String string2, Context context, AttributeSet attributeSet) {
        return this.mFragments.onCreateView(view, string2, context, attributeSet);
    }

    /*
     * Enabled aggressive block sorting
     */
    void doReallyStop(boolean bl) {
        if (!this.mReallyStopped) {
            this.mReallyStopped = true;
            this.mRetaining = bl;
            this.mHandler.removeMessages(1);
            this.onReallyStop();
            return;
        } else {
            if (!bl) return;
            {
                this.mFragments.doLoaderStart();
                this.mFragments.doLoaderStop(true);
                return;
            }
        }
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string2, fileDescriptor, printWriter, arrstring);
        printWriter.print(string2);
        printWriter.print("Local FragmentActivity ");
        printWriter.print(Integer.toHexString(System.identityHashCode(this)));
        printWriter.println(" State:");
        String string3 = string2 + "  ";
        printWriter.print(string3);
        printWriter.print("mCreated=");
        printWriter.print(this.mCreated);
        printWriter.print("mResumed=");
        printWriter.print(this.mResumed);
        printWriter.print(" mStopped=");
        printWriter.print(this.mStopped);
        printWriter.print(" mReallyStopped=");
        printWriter.println(this.mReallyStopped);
        this.mFragments.dumpLoaders(string3, fileDescriptor, printWriter, arrstring);
        this.mFragments.getSupportFragmentManager().dump(string2, fileDescriptor, printWriter, arrstring);
    }

    public Object getLastCustomNonConfigurationInstance() {
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances)this.getLastNonConfigurationInstance();
        if (nonConfigurationInstances != null) {
            return nonConfigurationInstances.custom;
        }
        return null;
    }

    public FragmentManager getSupportFragmentManager() {
        return this.mFragments.getSupportFragmentManager();
    }

    public LoaderManager getSupportLoaderManager() {
        return this.mFragments.getSupportLoaderManager();
    }

    protected void onActivityResult(int n, int n2, Intent intent) {
        this.mFragments.noteStateNotSaved();
        int n3 = n >> 16;
        if (n3 != 0) {
            String string2 = this.mPendingFragmentActivityResults.get(--n3);
            this.mPendingFragmentActivityResults.remove(n3);
            if (string2 == null) {
                Log.w((String)TAG, (String)"Activity result delivered for unknown Fragment.");
                return;
            }
            Fragment fragment = this.mFragments.findFragmentByWho(string2);
            if (fragment == null) {
                Log.w((String)TAG, (String)("Activity result no fragment exists for who: " + string2));
                return;
            }
            fragment.onActivityResult(0xFFFF & n, n2, intent);
            return;
        }
        super.onActivityResult(n, n2, intent);
    }

    public void onAttachFragment(Fragment fragment) {
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onBackPressed() {
        FragmentManager fragmentManager = this.mFragments.getSupportFragmentManager();
        boolean bl = fragmentManager.isStateSaved();
        if (bl && Build.VERSION.SDK_INT <= 25 || !bl && fragmentManager.popBackStackImmediate()) {
            return;
        }
        super.onBackPressed();
    }

    public void onConfigurationChanged(Configuration configuration) {
        super.onConfigurationChanged(configuration);
        this.mFragments.dispatchConfigurationChanged(configuration);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected void onCreate(Bundle arrstring) {
        int[] arrn = null;
        this.mFragments.attachHost(null);
        super.onCreate((Bundle)arrstring);
        NonConfigurationInstances nonConfigurationInstances = (NonConfigurationInstances)this.getLastNonConfigurationInstance();
        if (nonConfigurationInstances != null) {
            this.mFragments.restoreLoaderNonConfig(nonConfigurationInstances.loaders);
        }
        if (arrstring != null) {
            Parcelable parcelable = arrstring.getParcelable(FRAGMENTS_TAG);
            FragmentController fragmentController = this.mFragments;
            if (nonConfigurationInstances != null) {
                arrn = nonConfigurationInstances.fragments;
            }
            fragmentController.restoreAllState(parcelable, (FragmentManagerNonConfig)arrn);
            if (arrstring.containsKey(NEXT_CANDIDATE_REQUEST_INDEX_TAG)) {
                this.mNextCandidateRequestIndex = arrstring.getInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG);
                arrn = arrstring.getIntArray(ALLOCATED_REQUEST_INDICIES_TAG);
                arrstring = arrstring.getStringArray(REQUEST_FRAGMENT_WHO_TAG);
                if (arrn == null || arrstring == null || arrn.length != arrstring.length) {
                    Log.w((String)TAG, (String)"Invalid requestCode mapping in savedInstanceState.");
                } else {
                    this.mPendingFragmentActivityResults = new SparseArrayCompat(arrn.length);
                    for (int i = 0; i < arrn.length; ++i) {
                        this.mPendingFragmentActivityResults.put(arrn[i], arrstring[i]);
                    }
                }
            }
        }
        if (this.mPendingFragmentActivityResults == null) {
            this.mPendingFragmentActivityResults = new SparseArrayCompat();
            this.mNextCandidateRequestIndex = 0;
        }
        this.mFragments.dispatchCreate();
    }

    public boolean onCreatePanelMenu(int n, Menu menu2) {
        if (n == 0) {
            return super.onCreatePanelMenu(n, menu2) | this.mFragments.dispatchCreateOptionsMenu(menu2, this.getMenuInflater());
        }
        return super.onCreatePanelMenu(n, menu2);
    }

    protected void onDestroy() {
        super.onDestroy();
        this.doReallyStop(false);
        this.mFragments.dispatchDestroy();
        this.mFragments.doLoaderDestroy();
    }

    public void onLowMemory() {
        super.onLowMemory();
        this.mFragments.dispatchLowMemory();
    }

    public boolean onMenuItemSelected(int n, MenuItem menuItem) {
        if (super.onMenuItemSelected(n, menuItem)) {
            return true;
        }
        switch (n) {
            default: {
                return false;
            }
            case 0: {
                return this.mFragments.dispatchOptionsItemSelected(menuItem);
            }
            case 6: 
        }
        return this.mFragments.dispatchContextItemSelected(menuItem);
    }

    public void onMultiWindowModeChanged(boolean bl) {
        this.mFragments.dispatchMultiWindowModeChanged(bl);
    }

    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        this.mFragments.noteStateNotSaved();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void onPanelClosed(int n, Menu menu2) {
        switch (n) {
            default: {
                break;
            }
            case 0: {
                this.mFragments.dispatchOptionsMenuClosed(menu2);
            }
        }
        super.onPanelClosed(n, menu2);
    }

    protected void onPause() {
        super.onPause();
        this.mResumed = false;
        if (this.mHandler.hasMessages(2)) {
            this.mHandler.removeMessages(2);
            this.onResumeFragments();
        }
        this.mFragments.dispatchPause();
    }

    public void onPictureInPictureModeChanged(boolean bl) {
        this.mFragments.dispatchPictureInPictureModeChanged(bl);
    }

    protected void onPostResume() {
        super.onPostResume();
        this.mHandler.removeMessages(2);
        this.onResumeFragments();
        this.mFragments.execPendingActions();
    }

    protected boolean onPrepareOptionsPanel(View view, Menu menu2) {
        return super.onPreparePanel(0, view, menu2);
    }

    public boolean onPreparePanel(int n, View view, Menu menu2) {
        if (n == 0 && menu2 != null) {
            return this.onPrepareOptionsPanel(view, menu2) | this.mFragments.dispatchPrepareOptionsMenu(menu2);
        }
        return super.onPreparePanel(n, view, menu2);
    }

    void onReallyStop() {
        this.mFragments.doLoaderStop(this.mRetaining);
        this.mFragments.dispatchReallyStop();
    }

    @Override
    public void onRequestPermissionsResult(int n, String[] arrstring, int[] arrn) {
        String string2;
        block5: {
            block4: {
                int n2 = n >> 16 & 0xFFFF;
                if (n2 == 0) break block4;
                string2 = this.mPendingFragmentActivityResults.get(--n2);
                this.mPendingFragmentActivityResults.remove(n2);
                if (string2 != null) break block5;
                Log.w((String)TAG, (String)"Activity result delivered for unknown Fragment.");
            }
            return;
        }
        Fragment fragment = this.mFragments.findFragmentByWho(string2);
        if (fragment == null) {
            Log.w((String)TAG, (String)("Activity result no fragment exists for who: " + string2));
            return;
        }
        fragment.onRequestPermissionsResult(n & 0xFFFF, arrstring, arrn);
    }

    protected void onResume() {
        super.onResume();
        this.mHandler.sendEmptyMessage(2);
        this.mResumed = true;
        this.mFragments.execPendingActions();
    }

    protected void onResumeFragments() {
        this.mFragments.dispatchResume();
    }

    public Object onRetainCustomNonConfigurationInstance() {
        return null;
    }

    public final Object onRetainNonConfigurationInstance() {
        if (this.mStopped) {
            this.doReallyStop(true);
        }
        Object object = this.onRetainCustomNonConfigurationInstance();
        FragmentManagerNonConfig fragmentManagerNonConfig = this.mFragments.retainNestedNonConfig();
        SimpleArrayMap<String, LoaderManager> simpleArrayMap = this.mFragments.retainLoaderNonConfig();
        if (fragmentManagerNonConfig == null && simpleArrayMap == null && object == null) {
            return null;
        }
        NonConfigurationInstances nonConfigurationInstances = new NonConfigurationInstances();
        nonConfigurationInstances.custom = object;
        nonConfigurationInstances.fragments = fragmentManagerNonConfig;
        nonConfigurationInstances.loaders = simpleArrayMap;
        return nonConfigurationInstances;
    }

    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        int[] arrn = this.mFragments.saveAllState();
        if (arrn != null) {
            bundle.putParcelable(FRAGMENTS_TAG, (Parcelable)arrn);
        }
        if (this.mPendingFragmentActivityResults.size() > 0) {
            bundle.putInt(NEXT_CANDIDATE_REQUEST_INDEX_TAG, this.mNextCandidateRequestIndex);
            arrn = new int[this.mPendingFragmentActivityResults.size()];
            String[] arrstring = new String[this.mPendingFragmentActivityResults.size()];
            for (int i = 0; i < this.mPendingFragmentActivityResults.size(); ++i) {
                arrn[i] = this.mPendingFragmentActivityResults.keyAt(i);
                arrstring[i] = this.mPendingFragmentActivityResults.valueAt(i);
            }
            bundle.putIntArray(ALLOCATED_REQUEST_INDICIES_TAG, arrn);
            bundle.putStringArray(REQUEST_FRAGMENT_WHO_TAG, arrstring);
        }
    }

    protected void onStart() {
        super.onStart();
        this.mStopped = false;
        this.mReallyStopped = false;
        this.mHandler.removeMessages(1);
        if (!this.mCreated) {
            this.mCreated = true;
            this.mFragments.dispatchActivityCreated();
        }
        this.mFragments.noteStateNotSaved();
        this.mFragments.execPendingActions();
        this.mFragments.doLoaderStart();
        this.mFragments.dispatchStart();
        this.mFragments.reportLoaderStart();
    }

    public void onStateNotSaved() {
        this.mFragments.noteStateNotSaved();
    }

    protected void onStop() {
        super.onStop();
        this.mStopped = true;
        this.mHandler.sendEmptyMessage(1);
        this.mFragments.dispatchStop();
    }

    void requestPermissionsFromFragment(Fragment fragment, String[] arrstring, int n) {
        if (n == -1) {
            ActivityCompat.requestPermissions(this, arrstring, n);
            return;
        }
        FragmentActivity.checkForValidRequestCode(n);
        try {
            this.mRequestedPermissionsFromFragment = true;
            ActivityCompat.requestPermissions(this, arrstring, (this.allocateRequestIndex(fragment) + 1 << 16) + (0xFFFF & n));
            return;
        }
        finally {
            this.mRequestedPermissionsFromFragment = false;
        }
    }

    public void setEnterSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ActivityCompat.setEnterSharedElementCallback(this, sharedElementCallback);
    }

    public void setExitSharedElementCallback(SharedElementCallback sharedElementCallback) {
        ActivityCompat.setExitSharedElementCallback(this, sharedElementCallback);
    }

    public void startActivityForResult(Intent intent, int n) {
        if (!this.mStartedActivityFromFragment && n != -1) {
            FragmentActivity.checkForValidRequestCode(n);
        }
        super.startActivityForResult(intent, n);
    }

    public void startActivityFromFragment(Fragment fragment, Intent intent, int n) {
        this.startActivityFromFragment(fragment, intent, n, null);
    }

    public void startActivityFromFragment(Fragment fragment, Intent intent, int n, Bundle bundle) {
        this.mStartedActivityFromFragment = true;
        if (n == -1) {
            ActivityCompat.startActivityForResult(this, intent, -1, bundle);
            return;
        }
        try {
            FragmentActivity.checkForValidRequestCode(n);
            ActivityCompat.startActivityForResult(this, intent, (this.allocateRequestIndex(fragment) + 1 << 16) + (0xFFFF & n), bundle);
            this.mStartedActivityFromFragment = false;
            return;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            this.mStartedActivityFromFragment = false;
        }
    }

    public void startIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        this.mStartedIntentSenderFromFragment = true;
        if (n == -1) {
            ActivityCompat.startIntentSenderForResult(this, intentSender, n, intent, n2, n3, n4, bundle);
            return;
        }
        try {
            FragmentActivity.checkForValidRequestCode(n);
            ActivityCompat.startIntentSenderForResult(this, intentSender, (this.allocateRequestIndex(fragment) + 1 << 16) + (0xFFFF & n), intent, n2, n3, n4, bundle);
            this.mStartedIntentSenderFromFragment = false;
            return;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        finally {
            this.mStartedIntentSenderFromFragment = false;
        }
    }

    public void supportFinishAfterTransition() {
        ActivityCompat.finishAfterTransition(this);
    }

    @Deprecated
    public void supportInvalidateOptionsMenu() {
        this.invalidateOptionsMenu();
    }

    public void supportPostponeEnterTransition() {
        ActivityCompat.postponeEnterTransition(this);
    }

    public void supportStartPostponedEnterTransition() {
        ActivityCompat.startPostponedEnterTransition(this);
    }

    @Override
    public final void validateRequestPermissionsRequestCode(int n) {
        if (!this.mRequestedPermissionsFromFragment && n != -1) {
            FragmentActivity.checkForValidRequestCode(n);
        }
    }

    class HostCallbacks
    extends FragmentHostCallback<FragmentActivity> {
        public HostCallbacks() {
            super(FragmentActivity.this);
        }

        @Override
        public void onAttachFragment(Fragment fragment) {
            FragmentActivity.this.onAttachFragment(fragment);
        }

        @Override
        public void onDump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
            FragmentActivity.this.dump(string2, fileDescriptor, printWriter, arrstring);
        }

        @Override
        public View onFindViewById(int n) {
            return FragmentActivity.this.findViewById(n);
        }

        @Override
        public FragmentActivity onGetHost() {
            return FragmentActivity.this;
        }

        @Override
        public LayoutInflater onGetLayoutInflater() {
            return FragmentActivity.this.getLayoutInflater().cloneInContext((Context)FragmentActivity.this);
        }

        @Override
        public int onGetWindowAnimations() {
            Window window = FragmentActivity.this.getWindow();
            if (window == null) {
                return 0;
            }
            return window.getAttributes().windowAnimations;
        }

        @Override
        public boolean onHasView() {
            Window window = FragmentActivity.this.getWindow();
            return window != null && window.peekDecorView() != null;
        }

        @Override
        public boolean onHasWindowAnimations() {
            return FragmentActivity.this.getWindow() != null;
        }

        @Override
        public void onRequestPermissionsFromFragment(Fragment fragment, String[] arrstring, int n) {
            FragmentActivity.this.requestPermissionsFromFragment(fragment, arrstring, n);
        }

        @Override
        public boolean onShouldSaveFragmentState(Fragment fragment) {
            return !FragmentActivity.this.isFinishing();
        }

        @Override
        public boolean onShouldShowRequestPermissionRationale(String string2) {
            return ActivityCompat.shouldShowRequestPermissionRationale(FragmentActivity.this, string2);
        }

        @Override
        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n) {
            FragmentActivity.this.startActivityFromFragment(fragment, intent, n);
        }

        @Override
        public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n, Bundle bundle) {
            FragmentActivity.this.startActivityFromFragment(fragment, intent, n, bundle);
        }

        @Override
        public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
            FragmentActivity.this.startIntentSenderFromFragment(fragment, intentSender, n, intent, n2, n3, n4, bundle);
        }

        @Override
        public void onSupportInvalidateOptionsMenu() {
            FragmentActivity.this.supportInvalidateOptionsMenu();
        }
    }

    static final class NonConfigurationInstances {
        Object custom;
        FragmentManagerNonConfig fragments;
        SimpleArrayMap<String, LoaderManager> loaders;

        NonConfigurationInstances() {
        }
    }

}

