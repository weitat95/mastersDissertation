/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 *  android.view.View
 */
package android.support.v4.app;

import android.content.Context;
import android.os.Build;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentHostCallback;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManagerImpl;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LogWriter;
import android.support.v4.view.ViewCompat;
import android.util.Log;
import android.view.View;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Modifier;
import java.util.ArrayList;

final class BackStackRecord
extends FragmentTransaction
implements FragmentManager.BackStackEntry,
FragmentManagerImpl.OpGenerator {
    static final int OP_ADD = 1;
    static final int OP_ATTACH = 7;
    static final int OP_DETACH = 6;
    static final int OP_HIDE = 4;
    static final int OP_NULL = 0;
    static final int OP_REMOVE = 3;
    static final int OP_REPLACE = 2;
    static final int OP_SET_PRIMARY_NAV = 8;
    static final int OP_SHOW = 5;
    static final int OP_UNSET_PRIMARY_NAV = 9;
    static final boolean SUPPORTS_TRANSITIONS;
    static final String TAG = "FragmentManager";
    boolean mAddToBackStack;
    boolean mAllowAddToBackStack = true;
    int mBreadCrumbShortTitleRes;
    CharSequence mBreadCrumbShortTitleText;
    int mBreadCrumbTitleRes;
    CharSequence mBreadCrumbTitleText;
    ArrayList<Runnable> mCommitRunnables;
    boolean mCommitted;
    int mEnterAnim;
    int mExitAnim;
    int mIndex = -1;
    final FragmentManagerImpl mManager;
    String mName;
    ArrayList<Op> mOps = new ArrayList();
    int mPopEnterAnim;
    int mPopExitAnim;
    boolean mReorderingAllowed = false;
    ArrayList<String> mSharedElementSourceNames;
    ArrayList<String> mSharedElementTargetNames;
    int mTransition;
    int mTransitionStyle;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = Build.VERSION.SDK_INT >= 21;
        SUPPORTS_TRANSITIONS = bl;
    }

    public BackStackRecord(FragmentManagerImpl fragmentManagerImpl) {
        this.mManager = fragmentManagerImpl;
    }

    private void doAddOp(int n, Fragment fragment, String string2, int n2) {
        Class<?> class_ = fragment.getClass();
        int n3 = class_.getModifiers();
        if (class_.isAnonymousClass() || !Modifier.isPublic(n3) || class_.isMemberClass() && !Modifier.isStatic(n3)) {
            throw new IllegalStateException("Fragment " + class_.getCanonicalName() + " must be a public static class to be  properly recreated from" + " instance state.");
        }
        fragment.mFragmentManager = this.mManager;
        if (string2 != null) {
            if (fragment.mTag != null && !string2.equals(fragment.mTag)) {
                throw new IllegalStateException("Can't change tag of fragment " + fragment + ": was " + fragment.mTag + " now " + string2);
            }
            fragment.mTag = string2;
        }
        if (n != 0) {
            if (n == -1) {
                throw new IllegalArgumentException("Can't add fragment " + fragment + " with tag " + string2 + " to container view with no id");
            }
            if (fragment.mFragmentId != 0 && fragment.mFragmentId != n) {
                throw new IllegalStateException("Can't change container ID of fragment " + fragment + ": was " + fragment.mFragmentId + " now " + n);
            }
            fragment.mFragmentId = n;
            fragment.mContainerId = n;
        }
        this.addOp(new Op(n2, fragment));
    }

    private static boolean isFragmentPostponed(Op object) {
        object = ((Op)object).fragment;
        return object != null && ((Fragment)object).mAdded && ((Fragment)object).mView != null && !((Fragment)object).mDetached && !((Fragment)object).mHidden && ((Fragment)object).isPostponed();
    }

    @Override
    public FragmentTransaction add(int n, Fragment fragment) {
        this.doAddOp(n, fragment, null, 1);
        return this;
    }

    @Override
    public FragmentTransaction add(int n, Fragment fragment, String string2) {
        this.doAddOp(n, fragment, string2, 1);
        return this;
    }

    @Override
    public FragmentTransaction add(Fragment fragment, String string2) {
        this.doAddOp(0, fragment, string2, 1);
        return this;
    }

    void addOp(Op op) {
        this.mOps.add(op);
        op.enterAnim = this.mEnterAnim;
        op.exitAnim = this.mExitAnim;
        op.popEnterAnim = this.mPopEnterAnim;
        op.popExitAnim = this.mPopExitAnim;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public FragmentTransaction addSharedElement(View object, String string2) {
        if (SUPPORTS_TRANSITIONS) {
            void var2_3;
            String string3 = ViewCompat.getTransitionName(object);
            if (string3 == null) {
                throw new IllegalArgumentException("Unique transitionNames are required for all sharedElements");
            }
            if (this.mSharedElementSourceNames == null) {
                this.mSharedElementSourceNames = new ArrayList();
                this.mSharedElementTargetNames = new ArrayList();
            } else {
                if (this.mSharedElementTargetNames.contains(var2_3)) {
                    throw new IllegalArgumentException("A shared element with the target name '" + (String)var2_3 + "' has already been added to the transaction.");
                }
                if (this.mSharedElementSourceNames.contains(string3)) {
                    throw new IllegalArgumentException("A shared element with the source name '" + string3 + " has already been added to the transaction.");
                }
            }
            this.mSharedElementSourceNames.add(string3);
            this.mSharedElementTargetNames.add((String)var2_3);
        }
        return this;
    }

    @Override
    public FragmentTransaction addToBackStack(String string2) {
        if (!this.mAllowAddToBackStack) {
            throw new IllegalStateException("This FragmentTransaction is not allowed to be added to the back stack.");
        }
        this.mAddToBackStack = true;
        this.mName = string2;
        return this;
    }

    @Override
    public FragmentTransaction attach(Fragment fragment) {
        this.addOp(new Op(7, fragment));
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    void bumpBackStackNesting(int n) {
        if (this.mAddToBackStack) {
            if (FragmentManagerImpl.DEBUG) {
                Log.v((String)TAG, (String)("Bump nesting in " + this + " by " + n));
            }
            int n2 = this.mOps.size();
            for (int i = 0; i < n2; ++i) {
                Op op = this.mOps.get(i);
                if (op.fragment == null) continue;
                Fragment fragment = op.fragment;
                fragment.mBackStackNesting += n;
                if (!FragmentManagerImpl.DEBUG) continue;
                Log.v((String)TAG, (String)("Bump nesting of " + op.fragment + " to " + op.fragment.mBackStackNesting));
            }
        }
    }

    @Override
    public int commit() {
        return this.commitInternal(false);
    }

    @Override
    public int commitAllowingStateLoss() {
        return this.commitInternal(true);
    }

    /*
     * Enabled aggressive block sorting
     */
    int commitInternal(boolean bl) {
        if (this.mCommitted) {
            throw new IllegalStateException("commit already called");
        }
        if (FragmentManagerImpl.DEBUG) {
            Log.v((String)TAG, (String)("Commit: " + this));
            PrintWriter printWriter = new PrintWriter(new LogWriter(TAG));
            this.dump("  ", null, printWriter, null);
            printWriter.close();
        }
        this.mCommitted = true;
        this.mIndex = this.mAddToBackStack ? this.mManager.allocBackStackIndex(this) : -1;
        this.mManager.enqueueAction(this, bl);
        return this.mIndex;
    }

    @Override
    public void commitNow() {
        this.disallowAddToBackStack();
        this.mManager.execSingleAction(this, false);
    }

    @Override
    public void commitNowAllowingStateLoss() {
        this.disallowAddToBackStack();
        this.mManager.execSingleAction(this, true);
    }

    @Override
    public FragmentTransaction detach(Fragment fragment) {
        this.addOp(new Op(6, fragment));
        return this;
    }

    @Override
    public FragmentTransaction disallowAddToBackStack() {
        if (this.mAddToBackStack) {
            throw new IllegalStateException("This transaction is already being added to the back stack");
        }
        this.mAllowAddToBackStack = false;
        return this;
    }

    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        this.dump(string2, printWriter, true);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void dump(String string2, PrintWriter printWriter, boolean bl) {
        if (bl) {
            printWriter.print(string2);
            printWriter.print("mName=");
            printWriter.print(this.mName);
            printWriter.print(" mIndex=");
            printWriter.print(this.mIndex);
            printWriter.print(" mCommitted=");
            printWriter.println(this.mCommitted);
            if (this.mTransition != 0) {
                printWriter.print(string2);
                printWriter.print("mTransition=#");
                printWriter.print(Integer.toHexString(this.mTransition));
                printWriter.print(" mTransitionStyle=#");
                printWriter.println(Integer.toHexString(this.mTransitionStyle));
            }
            if (this.mEnterAnim != 0 || this.mExitAnim != 0) {
                printWriter.print(string2);
                printWriter.print("mEnterAnim=#");
                printWriter.print(Integer.toHexString(this.mEnterAnim));
                printWriter.print(" mExitAnim=#");
                printWriter.println(Integer.toHexString(this.mExitAnim));
            }
            if (this.mPopEnterAnim != 0 || this.mPopExitAnim != 0) {
                printWriter.print(string2);
                printWriter.print("mPopEnterAnim=#");
                printWriter.print(Integer.toHexString(this.mPopEnterAnim));
                printWriter.print(" mPopExitAnim=#");
                printWriter.println(Integer.toHexString(this.mPopExitAnim));
            }
            if (this.mBreadCrumbTitleRes != 0 || this.mBreadCrumbTitleText != null) {
                printWriter.print(string2);
                printWriter.print("mBreadCrumbTitleRes=#");
                printWriter.print(Integer.toHexString(this.mBreadCrumbTitleRes));
                printWriter.print(" mBreadCrumbTitleText=");
                printWriter.println(this.mBreadCrumbTitleText);
            }
            if (this.mBreadCrumbShortTitleRes != 0 || this.mBreadCrumbShortTitleText != null) {
                printWriter.print(string2);
                printWriter.print("mBreadCrumbShortTitleRes=#");
                printWriter.print(Integer.toHexString(this.mBreadCrumbShortTitleRes));
                printWriter.print(" mBreadCrumbShortTitleText=");
                printWriter.println(this.mBreadCrumbShortTitleText);
            }
        }
        if (!this.mOps.isEmpty()) {
            printWriter.print(string2);
            printWriter.println("Operations:");
            String cfr_ignored_0 = string2 + "    ";
            int n = this.mOps.size();
            for (int i = 0; i < n; ++i) {
                String string3;
                Op op = this.mOps.get(i);
                switch (op.cmd) {
                    default: {
                        string3 = "cmd=" + op.cmd;
                        break;
                    }
                    case 0: {
                        string3 = "NULL";
                        break;
                    }
                    case 1: {
                        string3 = "ADD";
                        break;
                    }
                    case 2: {
                        string3 = "REPLACE";
                        break;
                    }
                    case 3: {
                        string3 = "REMOVE";
                        break;
                    }
                    case 4: {
                        string3 = "HIDE";
                        break;
                    }
                    case 5: {
                        string3 = "SHOW";
                        break;
                    }
                    case 6: {
                        string3 = "DETACH";
                        break;
                    }
                    case 7: {
                        string3 = "ATTACH";
                        break;
                    }
                    case 8: {
                        string3 = "SET_PRIMARY_NAV";
                        break;
                    }
                    case 9: {
                        string3 = "UNSET_PRIMARY_NAV";
                    }
                }
                printWriter.print(string2);
                printWriter.print("  Op #");
                printWriter.print(i);
                printWriter.print(": ");
                printWriter.print(string3);
                printWriter.print(" ");
                printWriter.println(op.fragment);
                if (!bl) continue;
                if (op.enterAnim != 0 || op.exitAnim != 0) {
                    printWriter.print(string2);
                    printWriter.print("enterAnim=#");
                    printWriter.print(Integer.toHexString(op.enterAnim));
                    printWriter.print(" exitAnim=#");
                    printWriter.println(Integer.toHexString(op.exitAnim));
                }
                if (op.popEnterAnim == 0 && op.popExitAnim == 0) continue;
                printWriter.print(string2);
                printWriter.print("popEnterAnim=#");
                printWriter.print(Integer.toHexString(op.popEnterAnim));
                printWriter.print(" popExitAnim=#");
                printWriter.println(Integer.toHexString(op.popExitAnim));
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void executeOps() {
        int n = this.mOps.size();
        for (int i = 0; i < n; ++i) {
            Op op = this.mOps.get(i);
            Fragment fragment = op.fragment;
            if (fragment != null) {
                fragment.setNextTransition(this.mTransition, this.mTransitionStyle);
            }
            switch (op.cmd) {
                default: {
                    throw new IllegalArgumentException("Unknown cmd: " + op.cmd);
                }
                case 1: {
                    fragment.setNextAnim(op.enterAnim);
                    this.mManager.addFragment(fragment, false);
                    break;
                }
                case 3: {
                    fragment.setNextAnim(op.exitAnim);
                    this.mManager.removeFragment(fragment);
                    break;
                }
                case 4: {
                    fragment.setNextAnim(op.exitAnim);
                    this.mManager.hideFragment(fragment);
                    break;
                }
                case 5: {
                    fragment.setNextAnim(op.enterAnim);
                    this.mManager.showFragment(fragment);
                    break;
                }
                case 6: {
                    fragment.setNextAnim(op.exitAnim);
                    this.mManager.detachFragment(fragment);
                    break;
                }
                case 7: {
                    fragment.setNextAnim(op.enterAnim);
                    this.mManager.attachFragment(fragment);
                    break;
                }
                case 8: {
                    this.mManager.setPrimaryNavigationFragment(fragment);
                    break;
                }
                case 9: {
                    this.mManager.setPrimaryNavigationFragment(null);
                }
            }
            if (this.mReorderingAllowed || op.cmd == 1 || fragment == null) continue;
            this.mManager.moveFragmentToExpectedState(fragment);
        }
        if (!this.mReorderingAllowed) {
            this.mManager.moveToState(this.mManager.mCurState, true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    void executePopOps(boolean bl) {
        for (int i = this.mOps.size() - 1; i >= 0; --i) {
            Op op = this.mOps.get(i);
            Fragment fragment = op.fragment;
            if (fragment != null) {
                fragment.setNextTransition(FragmentManagerImpl.reverseTransit(this.mTransition), this.mTransitionStyle);
            }
            switch (op.cmd) {
                default: {
                    throw new IllegalArgumentException("Unknown cmd: " + op.cmd);
                }
                case 1: {
                    fragment.setNextAnim(op.popExitAnim);
                    this.mManager.removeFragment(fragment);
                    break;
                }
                case 3: {
                    fragment.setNextAnim(op.popEnterAnim);
                    this.mManager.addFragment(fragment, false);
                    break;
                }
                case 4: {
                    fragment.setNextAnim(op.popEnterAnim);
                    this.mManager.showFragment(fragment);
                    break;
                }
                case 5: {
                    fragment.setNextAnim(op.popExitAnim);
                    this.mManager.hideFragment(fragment);
                    break;
                }
                case 6: {
                    fragment.setNextAnim(op.popEnterAnim);
                    this.mManager.attachFragment(fragment);
                    break;
                }
                case 7: {
                    fragment.setNextAnim(op.popExitAnim);
                    this.mManager.detachFragment(fragment);
                    break;
                }
                case 8: {
                    this.mManager.setPrimaryNavigationFragment(null);
                    break;
                }
                case 9: {
                    this.mManager.setPrimaryNavigationFragment(fragment);
                }
            }
            if (this.mReorderingAllowed || op.cmd == 3 || fragment == null) continue;
            this.mManager.moveFragmentToExpectedState(fragment);
        }
        if (!this.mReorderingAllowed && bl) {
            this.mManager.moveToState(this.mManager.mCurState, true);
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    Fragment expandOps(ArrayList<Fragment> var1_1, Fragment var2_2) {
        var4_3 = 0;
        var9_4 = var2_2;
        while (var4_3 < this.mOps.size()) {
            var10_10 = this.mOps.get(var4_3);
            var3_5 = var4_3;
            var2_2 = var9_4;
            switch (var10_10.cmd) {
                default: {
                    var2_2 = var9_4;
                    var3_5 = var4_3;
                    break;
                }
                case 1: 
                case 7: {
                    var1_1.add(var10_10.fragment);
                    var3_5 = var4_3;
                    var2_2 = var9_4;
                    break;
                }
                case 3: 
                case 6: {
                    var1_1.remove(var10_10.fragment);
                    var3_5 = var4_3;
                    var2_2 = var9_4;
                    if (var10_10.fragment == var9_4) {
                        this.mOps.add(var4_3, new Op(9, var10_10.fragment));
                        var3_5 = var4_3 + 1;
                        var2_2 = null;
                        break;
                    }
                    ** GOTO lbl75
                }
                case 2: {
                    var11_11 = var10_10.fragment;
                    var8_9 = var11_11.mContainerId;
                    var5_6 = 0;
                    var6_7 = var1_1.size() - 1;
                    var2_2 = var9_4;
                    var3_5 = var4_3;
                    for (var4_3 = var6_7; var4_3 >= 0; --var4_3) {
                        var12_12 = var1_1.get(var4_3);
                        var6_7 = var5_6;
                        var7_8 = var3_5;
                        var9_4 = var2_2;
                        if (var12_12.mContainerId == var8_9) {
                            if (var12_12 == var11_11) {
                                var6_7 = 1;
                                var9_4 = var2_2;
                                var7_8 = var3_5;
                            } else {
                                var6_7 = var3_5;
                                var9_4 = var2_2;
                                if (var12_12 == var2_2) {
                                    this.mOps.add(var3_5, new Op(9, var12_12));
                                    var6_7 = var3_5 + 1;
                                    var9_4 = null;
                                }
                                var2_2 = new Op(3, var12_12);
                                var2_2.enterAnim = var10_10.enterAnim;
                                var2_2.popEnterAnim = var10_10.popEnterAnim;
                                var2_2.exitAnim = var10_10.exitAnim;
                                var2_2.popExitAnim = var10_10.popExitAnim;
                                this.mOps.add(var6_7, (Op)var2_2);
                                var1_1.remove(var12_12);
                                var7_8 = var6_7 + 1;
                                var6_7 = var5_6;
                            }
                        }
                        var5_6 = var6_7;
                        var3_5 = var7_8;
                        var2_2 = var9_4;
                    }
                    if (var5_6 != 0) {
                        this.mOps.remove(var3_5);
                        --var3_5;
                        break;
                    }
                    var10_10.cmd = 1;
                    var1_1.add(var11_11);
                }
lbl75:
                // 3 sources
                case 4: 
                case 5: {
                    break;
                }
                case 8: {
                    this.mOps.add(var4_3, new Op(9, (Fragment)var9_4));
                    var3_5 = var4_3 + 1;
                    var2_2 = var10_10.fragment;
                }
            }
            var4_3 = var3_5 + 1;
            var9_4 = var2_2;
        }
        return var9_4;
    }

    @Override
    public boolean generateOps(ArrayList<BackStackRecord> arrayList, ArrayList<Boolean> arrayList2) {
        if (FragmentManagerImpl.DEBUG) {
            Log.v((String)TAG, (String)("Run: " + this));
        }
        arrayList.add(this);
        arrayList2.add(false);
        if (this.mAddToBackStack) {
            this.mManager.addBackStackState(this);
        }
        return true;
    }

    @Override
    public CharSequence getBreadCrumbShortTitle() {
        if (this.mBreadCrumbShortTitleRes != 0) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbShortTitleRes);
        }
        return this.mBreadCrumbShortTitleText;
    }

    @Override
    public int getBreadCrumbShortTitleRes() {
        return this.mBreadCrumbShortTitleRes;
    }

    @Override
    public CharSequence getBreadCrumbTitle() {
        if (this.mBreadCrumbTitleRes != 0) {
            return this.mManager.mHost.getContext().getText(this.mBreadCrumbTitleRes);
        }
        return this.mBreadCrumbTitleText;
    }

    @Override
    public int getBreadCrumbTitleRes() {
        return this.mBreadCrumbTitleRes;
    }

    @Override
    public int getId() {
        return this.mIndex;
    }

    @Override
    public String getName() {
        return this.mName;
    }

    public int getTransition() {
        return this.mTransition;
    }

    public int getTransitionStyle() {
        return this.mTransitionStyle;
    }

    @Override
    public FragmentTransaction hide(Fragment fragment) {
        this.addOp(new Op(4, fragment));
        return this;
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean interactsWith(int n) {
        boolean bl = false;
        int n2 = this.mOps.size();
        int n3 = 0;
        do {
            boolean bl2 = bl;
            if (n3 >= n2) return bl2;
            Op op = this.mOps.get(n3);
            int n4 = op.fragment != null ? op.fragment.mContainerId : 0;
            if (n4 != 0 && n4 == n) {
                return true;
            }
            ++n3;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    boolean interactsWith(ArrayList<BackStackRecord> var1_1, int var2_2, int var3_3) {
        if (var3_3 == var2_2) {
            return false;
        }
        var10_4 = this.mOps.size();
        var6_5 = -1;
        var5_6 = 0;
        block0: do {
            if (var5_6 >= var10_4) return false;
            var12_12 = this.mOps.get(var5_6);
            var4_7 = var12_12.fragment != null ? var12_12.fragment.mContainerId : 0;
            var8_9 = var6_5;
            if (var4_7 == 0) ** GOTO lbl-1000
            var8_9 = var6_5;
            if (var4_7 == var6_5) ** GOTO lbl-1000
            var6_5 = var4_7;
            var7_8 = var2_2;
            do {
                var8_9 = var6_5;
                if (var7_8 < var3_3) {
                    var12_12 = var1_1.get(var7_8);
                    var11_11 = var12_12.mOps.size();
                } else lbl-1000:
                // 3 sources
                {
                    ++var5_6;
                    var6_5 = var8_9;
                    continue block0;
                }
                for (var8_9 = 0; var8_9 < var11_11; ++var8_9) {
                    var13_13 = var12_12.mOps.get(var8_9);
                    var9_10 = var13_13.fragment != null ? var13_13.fragment.mContainerId : 0;
                    if (var9_10 != var4_7) continue;
                    return true;
                }
                ++var7_8;
            } while (true);
            break;
        } while (true);
    }

    @Override
    public boolean isAddToBackStackAllowed() {
        return this.mAllowAddToBackStack;
    }

    @Override
    public boolean isEmpty() {
        return this.mOps.isEmpty();
    }

    boolean isPostponed() {
        for (int i = 0; i < this.mOps.size(); ++i) {
            if (!BackStackRecord.isFragmentPostponed(this.mOps.get(i))) continue;
            return true;
        }
        return false;
    }

    @Override
    public FragmentTransaction remove(Fragment fragment) {
        this.addOp(new Op(3, fragment));
        return this;
    }

    @Override
    public FragmentTransaction replace(int n, Fragment fragment) {
        return this.replace(n, fragment, null);
    }

    @Override
    public FragmentTransaction replace(int n, Fragment fragment, String string2) {
        if (n == 0) {
            throw new IllegalArgumentException("Must use non-zero containerViewId");
        }
        this.doAddOp(n, fragment, string2, 2);
        return this;
    }

    @Override
    public FragmentTransaction runOnCommit(Runnable runnable) {
        if (runnable == null) {
            throw new IllegalArgumentException("runnable cannot be null");
        }
        this.disallowAddToBackStack();
        if (this.mCommitRunnables == null) {
            this.mCommitRunnables = new ArrayList();
        }
        this.mCommitRunnables.add(runnable);
        return this;
    }

    public void runOnCommitRunnables() {
        if (this.mCommitRunnables != null) {
            int n = this.mCommitRunnables.size();
            for (int i = 0; i < n; ++i) {
                this.mCommitRunnables.get(i).run();
            }
            this.mCommitRunnables = null;
        }
    }

    @Override
    public FragmentTransaction setAllowOptimization(boolean bl) {
        return this.setReorderingAllowed(bl);
    }

    @Override
    public FragmentTransaction setBreadCrumbShortTitle(int n) {
        this.mBreadCrumbShortTitleRes = n;
        this.mBreadCrumbShortTitleText = null;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbShortTitle(CharSequence charSequence) {
        this.mBreadCrumbShortTitleRes = 0;
        this.mBreadCrumbShortTitleText = charSequence;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbTitle(int n) {
        this.mBreadCrumbTitleRes = n;
        this.mBreadCrumbTitleText = null;
        return this;
    }

    @Override
    public FragmentTransaction setBreadCrumbTitle(CharSequence charSequence) {
        this.mBreadCrumbTitleRes = 0;
        this.mBreadCrumbTitleText = charSequence;
        return this;
    }

    @Override
    public FragmentTransaction setCustomAnimations(int n, int n2) {
        return this.setCustomAnimations(n, n2, 0, 0);
    }

    @Override
    public FragmentTransaction setCustomAnimations(int n, int n2, int n3, int n4) {
        this.mEnterAnim = n;
        this.mExitAnim = n2;
        this.mPopEnterAnim = n3;
        this.mPopExitAnim = n4;
        return this;
    }

    void setOnStartPostponedListener(Fragment.OnStartEnterTransitionListener onStartEnterTransitionListener) {
        for (int i = 0; i < this.mOps.size(); ++i) {
            Op op = this.mOps.get(i);
            if (!BackStackRecord.isFragmentPostponed(op)) continue;
            op.fragment.setOnStartEnterTransitionListener(onStartEnterTransitionListener);
        }
    }

    @Override
    public FragmentTransaction setPrimaryNavigationFragment(Fragment fragment) {
        this.addOp(new Op(8, fragment));
        return this;
    }

    @Override
    public FragmentTransaction setReorderingAllowed(boolean bl) {
        this.mReorderingAllowed = bl;
        return this;
    }

    @Override
    public FragmentTransaction setTransition(int n) {
        this.mTransition = n;
        return this;
    }

    @Override
    public FragmentTransaction setTransitionStyle(int n) {
        this.mTransitionStyle = n;
        return this;
    }

    @Override
    public FragmentTransaction show(Fragment fragment) {
        this.addOp(new Op(5, fragment));
        return this;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder(128);
        stringBuilder.append("BackStackEntry{");
        stringBuilder.append(Integer.toHexString(System.identityHashCode(this)));
        if (this.mIndex >= 0) {
            stringBuilder.append(" #");
            stringBuilder.append(this.mIndex);
        }
        if (this.mName != null) {
            stringBuilder.append(" ");
            stringBuilder.append(this.mName);
        }
        stringBuilder.append("}");
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    Fragment trackAddedFragmentsInPop(ArrayList<Fragment> arrayList, Fragment fragment) {
        int n = 0;
        Fragment fragment2 = fragment;
        while (n < this.mOps.size()) {
            Op op = this.mOps.get(n);
            fragment = fragment2;
            switch (op.cmd) {
                default: {
                    fragment = fragment2;
                    break;
                }
                case 1: 
                case 7: {
                    arrayList.remove(op.fragment);
                    fragment = fragment2;
                    break;
                }
                case 3: 
                case 6: {
                    arrayList.add(op.fragment);
                    fragment = fragment2;
                    break;
                }
                case 9: {
                    fragment = op.fragment;
                }
                case 2: 
                case 4: 
                case 5: {
                    break;
                }
                case 8: {
                    fragment = null;
                }
            }
            ++n;
            fragment2 = fragment;
        }
        return fragment2;
    }

    static final class Op {
        int cmd;
        int enterAnim;
        int exitAnim;
        Fragment fragment;
        int popEnterAnim;
        int popExitAnim;

        Op() {
        }

        Op(int n, Fragment fragment) {
            this.cmd = n;
            this.fragment = fragment;
        }
    }

}

