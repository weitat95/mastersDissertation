/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.common.ui;

import com.getqardio.android.mvp.common.ui.MultiChoiceMode;
import dagger.internal.Factory;

public final class MultiChoiceMode_Factory
implements Factory<MultiChoiceMode> {
    private static final MultiChoiceMode_Factory INSTANCE = new MultiChoiceMode_Factory();

    public static Factory<MultiChoiceMode> create() {
        return INSTANCE;
    }

    @Override
    public MultiChoiceMode get() {
        return new MultiChoiceMode();
    }
}

