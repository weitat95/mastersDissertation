/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.os.Bundle
 *  android.os.CountDownTimer
 *  android.util.DisplayMetrics
 *  android.view.Display
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.WindowManager
 *  android.widget.ImageView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import com.bumptech.glide.DrawableRequestBuilder;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.target.Target;
import com.getqardio.android.ui.fragment.ImageSlideshowFragment$$Lambda$1;
import com.getqardio.android.utils.RandomImageGenerator;
import java.util.concurrent.TimeUnit;

public class ImageSlideshowFragment
extends Fragment {
    private static final long INTERVAL;
    private static final long PERIOD;
    private ImageView image;
    RandomImageGenerator random;
    private CountDownTimer timer = new CountDownTimer(PERIOD, INTERVAL){

        public void onFinish() {
        }

        public void onTick(long l) {
            String string2 = ImageSlideshowFragment.this.random.getRandomImagePath();
            Glide.with(ImageSlideshowFragment.this.getActivity()).load(string2).centerCrop().into(ImageSlideshowFragment.this.image);
        }
    };

    static {
        PERIOD = TimeUnit.HOURS.toMillis(1L);
        INTERVAL = TimeUnit.SECONDS.toMillis(3L);
    }

    private void init(View view) {
        this.image = (ImageView)view.findViewById(2131820710);
        view.findViewById(2131821098).setOnClickListener(ImageSlideshowFragment$$Lambda$1.lambdaFactory$(this));
    }

    public static ImageSlideshowFragment newInstance(boolean bl, boolean bl2) {
        Bundle bundle = new Bundle(2);
        bundle.putBoolean("com.getqardio.android.argument.USE_FLICKR", bl);
        bundle.putBoolean("com.getqardio.android.argument.USE_PHOTO_FROM_DEVICE", bl2);
        ImageSlideshowFragment imageSlideshowFragment = new ImageSlideshowFragment();
        imageSlideshowFragment.setArguments(bundle);
        return imageSlideshowFragment;
    }

    private void onClose() {
        Activity activity = this.getActivity();
        if (activity != null) {
            activity.finish();
        }
    }

    private boolean useFlickr() {
        Bundle bundle = this.getArguments();
        return bundle != null && bundle.containsKey("com.getqardio.android.argument.USE_FLICKR") && bundle.getBoolean("com.getqardio.android.argument.USE_FLICKR");
    }

    private boolean usePhotoFromDevice() {
        Bundle bundle = this.getArguments();
        return bundle != null && bundle.containsKey("com.getqardio.android.argument.USE_PHOTO_FROM_DEVICE") && bundle.getBoolean("com.getqardio.android.argument.USE_PHOTO_FROM_DEVICE");
    }

    /* synthetic */ void lambda$init$0(View view) {
        this.onClose();
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        bundle = new DisplayMetrics();
        this.getActivity().getWindowManager().getDefaultDisplay().getMetrics((DisplayMetrics)bundle);
        this.random = new RandomImageGenerator(this.getActivity(), this.useFlickr(), this.usePhotoFromDevice());
    }

    public View onCreateView(LayoutInflater layoutInflater, ViewGroup viewGroup, Bundle bundle) {
        layoutInflater = layoutInflater.inflate(2130968728, viewGroup, false);
        this.init((View)layoutInflater);
        return layoutInflater;
    }

    public void onPause() {
        super.onPause();
        this.timer.cancel();
    }

    public void onResume() {
        super.onResume();
        this.timer.start();
    }

}

