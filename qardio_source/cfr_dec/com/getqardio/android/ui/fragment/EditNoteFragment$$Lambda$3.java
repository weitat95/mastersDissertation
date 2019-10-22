/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.KeyEvent
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package com.getqardio.android.ui.fragment;

import android.view.KeyEvent;
import android.widget.TextView;
import com.getqardio.android.ui.fragment.EditNoteFragment;
import java.lang.invoke.LambdaForm;

final class EditNoteFragment$$Lambda$3
implements TextView.OnEditorActionListener {
    private final EditNoteFragment arg$1;

    private EditNoteFragment$$Lambda$3(EditNoteFragment editNoteFragment) {
        this.arg$1 = editNoteFragment;
    }

    public static TextView.OnEditorActionListener lambdaFactory$(EditNoteFragment editNoteFragment) {
        return new EditNoteFragment$$Lambda$3(editNoteFragment);
    }

    @LambdaForm.Hidden
    public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
        return this.arg$1.lambda$init$2(textView, n, keyEvent);
    }
}

