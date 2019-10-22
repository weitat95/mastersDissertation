/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import android.widget.AdapterView;
import com.getqardio.android.ui.fragment.EditNoteFragment;
import java.lang.invoke.LambdaForm;

final class EditNoteFragment$$Lambda$2
implements AdapterView.OnItemClickListener {
    private final EditNoteFragment arg$1;

    private EditNoteFragment$$Lambda$2(EditNoteFragment editNoteFragment) {
        this.arg$1 = editNoteFragment;
    }

    public static AdapterView.OnItemClickListener lambdaFactory$(EditNoteFragment editNoteFragment) {
        return new EditNoteFragment$$Lambda$2(editNoteFragment);
    }

    @LambdaForm.Hidden
    public void onItemClick(AdapterView adapterView, View view, int n, long l) {
        this.arg$1.lambda$init$1(adapterView, view, n, l);
    }
}

