/*
 * Decompiled with CFR 0.147.
 */
package com.mixpanel.android.viewcrawler;

import com.mixpanel.android.viewcrawler.Caller;

class PropertyDescription {
    public final Caller accessor;
    private final String mMutatorName;
    public final String name;
    public final Class<?> targetClass;

    public PropertyDescription(String string2, Class<?> class_, Caller caller, String string3) {
        this.name = string2;
        this.targetClass = class_;
        this.accessor = caller;
        this.mMutatorName = string3;
    }

    public Caller makeMutator(Object[] arrobject) throws NoSuchMethodException {
        if (this.mMutatorName == null) {
            return null;
        }
        return new Caller(this.targetClass, this.mMutatorName, arrobject, Void.TYPE);
    }

    public String toString() {
        return "[PropertyDescription " + this.name + "," + this.targetClass + ", " + this.accessor + "/" + this.mMutatorName + "]";
    }
}

