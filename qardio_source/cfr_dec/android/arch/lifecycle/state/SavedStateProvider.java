/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.arch.lifecycle.state;

import android.arch.lifecycle.state.Saveable;
import android.arch.lifecycle.state.StateMap;
import android.os.Bundle;
import java.util.Map;
import java.util.Set;

public class SavedStateProvider {
    private StateMap mStateMap = new StateMap();

    public void restoreState(Bundle bundle) {
        this.mStateMap.mSavedState = bundle;
    }

    public void saveState(Bundle bundle) {
        if (this.mStateMap.mSavedState != null) {
            bundle.putAll(this.mStateMap.mSavedState);
        }
        Map<String, Object> map = this.mStateMap.mMap;
        for (String string2 : map.keySet()) {
            ((Saveable)map.get(string2)).saveTo(bundle, string2);
        }
    }
}

