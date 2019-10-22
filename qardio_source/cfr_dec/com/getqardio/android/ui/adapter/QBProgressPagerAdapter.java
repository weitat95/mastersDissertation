/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Fragment
 *  android.app.FragmentManager
 */
package com.getqardio.android.ui.adapter;

import android.app.Fragment;
import android.app.FragmentManager;
import android.support.v13.app.FragmentStatePagerAdapter;
import com.getqardio.android.handler.QBOnboardingManager;
import com.getqardio.android.ui.fragment.QBProgressMessageFragment;

public class QBProgressPagerAdapter
extends FragmentStatePagerAdapter {
    public QBProgressPagerAdapter(FragmentManager fragmentManager) {
        super(fragmentManager);
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Override
    public Fragment getItem(int n) {
        switch (n) {
            default: {
                return null;
            }
            case 0: {
                return QBProgressMessageFragment.newInstance(2131362207);
            }
            case 1: {
                return QBProgressMessageFragment.newInstance(2131362358);
            }
            case 2: {
                return QBProgressMessageFragment.newInstance(2131362208);
            }
            case 3: {
                return QBProgressMessageFragment.newInstance(2131362396);
            }
            case 4: {
                return QBProgressMessageFragment.newInstance(2131362357);
            }
            case 5: 
        }
        return QBProgressMessageFragment.newInstance(2131362359);
    }

    public int getPagePosition(QBOnboardingManager.BaseCommHandler.Page page) {
        int n = 0;
        switch (1.$SwitchMap$com$getqardio$android$handler$QBOnboardingManager$BaseCommHandler$Page[page.ordinal()]) {
            default: {
                throw new IllegalArgumentException(String.format("unsupported page - %s", new Object[]{page}));
            }
            case 2: {
                n = 1;
            }
            case 1: {
                return n;
            }
            case 3: {
                return 2;
            }
            case 4: {
                return 3;
            }
            case 5: {
                return 4;
            }
            case 6: 
        }
        return 5;
    }

}

