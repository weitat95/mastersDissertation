/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentSender
 *  android.content.IntentSender$SendIntentException
 *  android.os.Bundle
 *  android.os.Handler
 *  android.view.LayoutInflater
 *  android.view.View
 */
package android.support.v4.app;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.IntentSender;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentContainer;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.app.LoaderManager;
import android.support.v4.app.LoaderManagerImpl;
import android.support.v4.util.SimpleArrayMap;
import android.view.LayoutInflater;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;

public abstract class FragmentHostCallback<E>
extends FragmentContainer {
    private final Activity mActivity;
    private SimpleArrayMap<String, LoaderManager> mAllLoaderManagers;
    private boolean mCheckedForLoaderManager;
    final Context mContext;
    final FragmentManagerImpl mFragmentManager;
    private final Handler mHandler;
    private LoaderManagerImpl mLoaderManager;
    private boolean mLoadersStarted;
    private boolean mRetainLoaders;
    final int mWindowAnimations;

    FragmentHostCallback(Activity activity, Context context, Handler handler, int n) {
        this.mFragmentManager = new FragmentManagerImpl();
        this.mActivity = activity;
        this.mContext = context;
        this.mHandler = handler;
        this.mWindowAnimations = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public FragmentHostCallback(Context context, Handler handler, int n) {
        Activity activity = context instanceof Activity ? (Activity)context : null;
        this(activity, context, handler, n);
    }

    FragmentHostCallback(FragmentActivity fragmentActivity) {
        this(fragmentActivity, (Context)fragmentActivity, fragmentActivity.mHandler, 0);
    }

    void doLoaderDestroy() {
        if (this.mLoaderManager == null) {
            return;
        }
        this.mLoaderManager.doDestroy();
    }

    void doLoaderRetain() {
        if (this.mLoaderManager == null) {
            return;
        }
        this.mLoaderManager.doRetain();
    }

    /*
     * Enabled aggressive block sorting
     */
    void doLoaderStart() {
        if (this.mLoadersStarted) {
            return;
        }
        this.mLoadersStarted = true;
        if (this.mLoaderManager != null) {
            this.mLoaderManager.doStart();
        } else if (!this.mCheckedForLoaderManager) {
            this.mLoaderManager = this.getLoaderManager("(root)", this.mLoadersStarted, false);
            if (this.mLoaderManager != null && !this.mLoaderManager.mStarted) {
                this.mLoaderManager.doStart();
            }
        }
        this.mCheckedForLoaderManager = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    void doLoaderStop(boolean bl) {
        this.mRetainLoaders = bl;
        if (this.mLoaderManager == null || !this.mLoadersStarted) {
            return;
        }
        this.mLoadersStarted = false;
        if (bl) {
            this.mLoaderManager.doRetain();
            return;
        }
        this.mLoaderManager.doStop();
    }

    void dumpLoaders(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        printWriter.print(string2);
        printWriter.print("mLoadersStarted=");
        printWriter.println(this.mLoadersStarted);
        if (this.mLoaderManager != null) {
            printWriter.print(string2);
            printWriter.print("Loader Manager ");
            printWriter.print(Integer.toHexString(System.identityHashCode(this.mLoaderManager)));
            printWriter.println(":");
            this.mLoaderManager.dump(string2 + "  ", fileDescriptor, printWriter, arrstring);
        }
    }

    Activity getActivity() {
        return this.mActivity;
    }

    Context getContext() {
        return this.mContext;
    }

    FragmentManagerImpl getFragmentManagerImpl() {
        return this.mFragmentManager;
    }

    Handler getHandler() {
        return this.mHandler;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    LoaderManagerImpl getLoaderManager(String object, boolean bl, boolean bl2) {
        LoaderManagerImpl loaderManagerImpl;
        if (this.mAllLoaderManagers == null) {
            this.mAllLoaderManagers = new SimpleArrayMap();
        }
        if ((loaderManagerImpl = (LoaderManagerImpl)this.mAllLoaderManagers.get(object)) == null && bl2) {
            loaderManagerImpl = new LoaderManagerImpl((String)object, this, bl);
            this.mAllLoaderManagers.put((String)object, loaderManagerImpl);
            return loaderManagerImpl;
        }
        object = loaderManagerImpl;
        if (!bl) return object;
        object = loaderManagerImpl;
        if (loaderManagerImpl == null) return object;
        object = loaderManagerImpl;
        if (loaderManagerImpl.mStarted) return object;
        loaderManagerImpl.doStart();
        return loaderManagerImpl;
    }

    LoaderManagerImpl getLoaderManagerImpl() {
        if (this.mLoaderManager != null) {
            return this.mLoaderManager;
        }
        this.mCheckedForLoaderManager = true;
        this.mLoaderManager = this.getLoaderManager("(root)", this.mLoadersStarted, true);
        return this.mLoaderManager;
    }

    boolean getRetainLoaders() {
        return this.mRetainLoaders;
    }

    void inactivateFragment(String string2) {
        LoaderManagerImpl loaderManagerImpl;
        if (this.mAllLoaderManagers != null && (loaderManagerImpl = (LoaderManagerImpl)this.mAllLoaderManagers.get(string2)) != null && !loaderManagerImpl.mRetaining) {
            loaderManagerImpl.doDestroy();
            this.mAllLoaderManagers.remove(string2);
        }
    }

    void onAttachFragment(Fragment fragment) {
    }

    public void onDump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
    }

    @Override
    public View onFindViewById(int n) {
        return null;
    }

    public abstract E onGetHost();

    public LayoutInflater onGetLayoutInflater() {
        return (LayoutInflater)this.mContext.getSystemService("layout_inflater");
    }

    public int onGetWindowAnimations() {
        return this.mWindowAnimations;
    }

    @Override
    public boolean onHasView() {
        return true;
    }

    public boolean onHasWindowAnimations() {
        return true;
    }

    public void onRequestPermissionsFromFragment(Fragment fragment, String[] arrstring, int n) {
    }

    public boolean onShouldSaveFragmentState(Fragment fragment) {
        return true;
    }

    public boolean onShouldShowRequestPermissionRationale(String string2) {
        return false;
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n) {
        this.onStartActivityFromFragment(fragment, intent, n, null);
    }

    public void onStartActivityFromFragment(Fragment fragment, Intent intent, int n, Bundle bundle) {
        if (n != -1) {
            throw new IllegalStateException("Starting activity with a requestCode requires a FragmentActivity host");
        }
        this.mContext.startActivity(intent);
    }

    public void onStartIntentSenderFromFragment(Fragment fragment, IntentSender intentSender, int n, Intent intent, int n2, int n3, int n4, Bundle bundle) throws IntentSender.SendIntentException {
        if (n != -1) {
            throw new IllegalStateException("Starting intent sender with a requestCode requires a FragmentActivity host");
        }
        ActivityCompat.startIntentSenderForResult(this.mActivity, intentSender, n, intent, n2, n3, n4, bundle);
    }

    public void onSupportInvalidateOptionsMenu() {
    }

    void reportLoaderStart() {
        if (this.mAllLoaderManagers != null) {
            int n;
            int n2 = this.mAllLoaderManagers.size();
            LoaderManagerImpl[] arrloaderManagerImpl = new LoaderManagerImpl[n2];
            for (n = n2 - 1; n >= 0; --n) {
                arrloaderManagerImpl[n] = (LoaderManagerImpl)this.mAllLoaderManagers.valueAt(n);
            }
            for (n = 0; n < n2; ++n) {
                LoaderManagerImpl loaderManagerImpl = arrloaderManagerImpl[n];
                loaderManagerImpl.finishRetain();
                loaderManagerImpl.doReportStart();
            }
        }
    }

    void restoreLoaderNonConfig(SimpleArrayMap<String, LoaderManager> simpleArrayMap) {
        if (simpleArrayMap != null) {
            int n = simpleArrayMap.size();
            for (int i = 0; i < n; ++i) {
                ((LoaderManagerImpl)simpleArrayMap.valueAt(i)).updateHostController(this);
            }
        }
        this.mAllLoaderManagers = simpleArrayMap;
    }

    /*
     * Enabled aggressive block sorting
     */
    SimpleArrayMap<String, LoaderManager> retainLoaderNonConfig() {
        int n = 0;
        int n2 = 0;
        if (this.mAllLoaderManagers != null) {
            int n3;
            int n4 = this.mAllLoaderManagers.size();
            LoaderManagerImpl[] arrloaderManagerImpl = new LoaderManagerImpl[n4];
            for (n3 = n4 - 1; n3 >= 0; --n3) {
                arrloaderManagerImpl[n3] = (LoaderManagerImpl)this.mAllLoaderManagers.valueAt(n3);
            }
            boolean bl = this.getRetainLoaders();
            n = 0;
            n3 = n2;
            n2 = n;
            do {
                n = n3;
                if (n2 >= n4) break;
                LoaderManagerImpl loaderManagerImpl = arrloaderManagerImpl[n2];
                if (!loaderManagerImpl.mRetaining && bl) {
                    if (!loaderManagerImpl.mStarted) {
                        loaderManagerImpl.doStart();
                    }
                    loaderManagerImpl.doRetain();
                }
                if (loaderManagerImpl.mRetaining) {
                    n3 = 1;
                } else {
                    loaderManagerImpl.doDestroy();
                    this.mAllLoaderManagers.remove(loaderManagerImpl.mWho);
                }
                ++n2;
            } while (true);
        }
        if (n != 0) {
            return this.mAllLoaderManagers;
        }
        return null;
    }
}

