/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.MenuItem;
import com.getqardio.android.ui.fragment.EditNoteFragment;
import java.lang.invoke.LambdaForm;

final class EditNoteFragment$$Lambda$1
implements MenuItem.OnMenuItemClickListener {
    private final EditNoteFragment arg$1;

    private EditNoteFragment$$Lambda$1(EditNoteFragment editNoteFragment) {
        this.arg$1 = editNoteFragment;
    }

    public static MenuItem.OnMenuItemClickListener lambdaFactory$(EditNoteFragment editNoteFragment) {
        return new EditNoteFragment$$Lambda$1(editNoteFragment);
    }

    @LambdaForm.Hidden
    public boolean onMenuItemClick(MenuItem menuItem) {
        return this.arg$1.lambda$onCreateOptionsMenu$0(menuItem);
    }
}

