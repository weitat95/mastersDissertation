/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 */
package android.arch.lifecycle.state;

import android.os.Bundle;

abstract class Saveable {
    Saveable() {
    }

    abstract void saveTo(Bundle var1, String var2);
}

