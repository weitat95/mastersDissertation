/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 */
package android.support.transition;

import android.support.transition.Transition;
import android.view.View;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class TransitionValues {
    final ArrayList<Transition> mTargetedTransitions;
    public final Map<String, Object> values = new HashMap<String, Object>();
    public View view;

    public TransitionValues() {
        this.mTargetedTransitions = new ArrayList();
    }

    public boolean equals(Object object) {
        return object instanceof TransitionValues && this.view == ((TransitionValues)object).view && this.values.equals(((TransitionValues)object).values);
    }

    public int hashCode() {
        return this.view.hashCode() * 31 + this.values.hashCode();
    }

    public String toString() {
        String string2 = "TransitionValues@" + Integer.toHexString(this.hashCode()) + ":\n";
        string2 = string2 + "    view = " + (Object)this.view + "\n";
        string2 = string2 + "    values:";
        for (String string3 : this.values.keySet()) {
            string2 = string2 + "    " + string3 + ": " + this.values.get(string3) + "\n";
        }
        return string2;
    }
}

