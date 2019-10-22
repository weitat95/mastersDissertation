/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package com.getqardio.android.ui.fragment;

import android.view.View;
import com.getqardio.android.ui.fragment.ImageSlideshowFragment;
import java.lang.invoke.LambdaForm;

final class ImageSlideshowFragment$$Lambda$1
implements View.OnClickListener {
    private final ImageSlideshowFragment arg$1;

    private ImageSlideshowFragment$$Lambda$1(ImageSlideshowFragment imageSlideshowFragment) {
        this.arg$1 = imageSlideshowFragment;
    }

    public static View.OnClickListener lambdaFactory$(ImageSlideshowFragment imageSlideshowFragment) {
        return new ImageSlideshowFragment$$Lambda$1(imageSlideshowFragment);
    }

    @LambdaForm.Hidden
    public void onClick(View view) {
        this.arg$1.lambda$init$0(view);
    }
}

