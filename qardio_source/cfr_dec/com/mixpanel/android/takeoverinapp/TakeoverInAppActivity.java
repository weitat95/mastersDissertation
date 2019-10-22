/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.Point
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.text.method.TransformationMethod
 *  android.view.Display
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.WindowManager
 *  android.view.animation.Animation
 *  android.view.animation.AnimationUtils
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.ScaleAnimation
 *  android.view.animation.TranslateAnimation
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.LinearLayout$LayoutParams
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.TextView
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.takeoverinapp;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.method.TransformationMethod;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.mixpanel.android.R;
import com.mixpanel.android.mpmetrics.InAppButton;
import com.mixpanel.android.mpmetrics.InAppNotification;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.TakeoverInAppNotification;
import com.mixpanel.android.mpmetrics.UpdateDisplayState;
import com.mixpanel.android.takeoverinapp.FadingImageView;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.ViewUtils;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint(value={"ClickableViewAccessibility"})
@TargetApi(value=16)
public class TakeoverInAppActivity
extends Activity {
    private int mIntentId = -1;
    private MixpanelAPI mMixpanel;
    private UpdateDisplayState mUpdateDisplayState;

    static /* synthetic */ MixpanelAPI access$100(TakeoverInAppActivity takeoverInAppActivity) {
        return takeoverInAppActivity.mMixpanel;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onCreateInAppNotification() {
        this.setContentView(R.layout.com_mixpanel_android_activity_notification_full);
        ImageView imageView = (ImageView)this.findViewById(R.id.com_mixpanel_android_notification_gradient);
        FadingImageView fadingImageView = (FadingImageView)this.findViewById(R.id.com_mixpanel_android_notification_image);
        TextView textView = (TextView)this.findViewById(R.id.com_mixpanel_android_notification_title);
        TextView textView2 = (TextView)this.findViewById(R.id.com_mixpanel_android_notification_subtext);
        ArrayList<Button> arrayList = new ArrayList<Button>();
        Button button = (Button)this.findViewById(R.id.com_mixpanel_android_notification_button);
        arrayList.add(button);
        arrayList.add((Button)this.findViewById(R.id.com_mixpanel_android_notification_second_button));
        LinearLayout linearLayout = (LinearLayout)this.findViewById(R.id.com_mixpanel_android_button_exit_wrapper);
        ImageView imageView2 = (ImageView)this.findViewById(R.id.com_mixpanel_android_image_close);
        TakeoverInAppNotification takeoverInAppNotification = (TakeoverInAppNotification)((UpdateDisplayState.DisplayState.InAppNotificationState)this.mUpdateDisplayState.getDisplayState()).getInAppNotification();
        Display display = this.getWindowManager().getDefaultDisplay();
        Point point = new Point();
        display.getSize(point);
        if (this.getResources().getConfiguration().orientation == 1) {
            display = (RelativeLayout.LayoutParams)linearLayout.getLayoutParams();
            display.setMargins(0, 0, 0, (int)((float)point.y * 0.06f));
            linearLayout.setLayoutParams((ViewGroup.LayoutParams)display);
        }
        fadingImageView.showShadow(takeoverInAppNotification.setShouldShowShadow());
        imageView.setBackgroundColor(takeoverInAppNotification.getBackgroundColor());
        if (takeoverInAppNotification.hasTitle()) {
            textView.setVisibility(0);
            textView.setText((CharSequence)takeoverInAppNotification.getTitle());
            textView.setTextColor(takeoverInAppNotification.getTitleColor());
        } else {
            textView.setVisibility(8);
        }
        if (takeoverInAppNotification.hasBody()) {
            textView2.setVisibility(0);
            textView2.setText((CharSequence)takeoverInAppNotification.getBody());
            textView2.setTextColor(takeoverInAppNotification.getBodyColor());
        } else {
            textView2.setVisibility(8);
        }
        fadingImageView.setImageBitmap(takeoverInAppNotification.getImage());
        for (int i = 0; i < arrayList.size(); ++i) {
            InAppButton inAppButton = takeoverInAppNotification.getButton(i);
            this.setUpInAppButton((Button)arrayList.get(i), inAppButton, takeoverInAppNotification, i);
        }
        if (takeoverInAppNotification.getNumButtons() == 1) {
            LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams)button.getLayoutParams();
            layoutParams.weight = 0.0f;
            layoutParams.width = -2;
            button.setLayoutParams((ViewGroup.LayoutParams)layoutParams);
        }
        imageView2.setColorFilter(takeoverInAppNotification.getCloseColor());
        linearLayout.setOnClickListener(new View.OnClickListener(){

            public void onClick(View view) {
                TakeoverInAppActivity.this.finish();
                UpdateDisplayState.releaseDisplayState(TakeoverInAppActivity.this.mIntentId);
            }
        });
        this.setUpNotificationAnimations(fadingImageView, textView, textView2, arrayList, linearLayout);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setUpInAppButton(Button button, final InAppButton inAppButton, final InAppNotification inAppNotification, final int n) {
        final int n2 = 864454278;
        if (inAppButton == null) {
            button.setVisibility(8);
            return;
        }
        button.setVisibility(0);
        button.setText((CharSequence)inAppButton.getText());
        button.setTextColor(inAppButton.getTextColor());
        button.setTransformationMethod(null);
        final GradientDrawable gradientDrawable = new GradientDrawable();
        if (inAppButton.getBackgroundColor() != 0) {
            n2 = ViewUtils.mixColors(inAppButton.getBackgroundColor(), 864454278);
        }
        button.setOnTouchListener(new View.OnTouchListener(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == 0) {
                    int n = n2;
                    gradientDrawable.setColor(n);
                    do {
                        return false;
                        break;
                    } while (true);
                }
                gradientDrawable.setColor(inAppButton.getBackgroundColor());
                return false;
            }
        });
        gradientDrawable.setColor(inAppButton.getBackgroundColor());
        gradientDrawable.setStroke((int)ViewUtils.dpToPx(2.0f, (Context)this), inAppButton.getBorderColor());
        gradientDrawable.setCornerRadius((float)((int)ViewUtils.dpToPx(5.0f, (Context)this)));
        if (Build.VERSION.SDK_INT < 16) {
            button.setBackgroundDrawable((Drawable)gradientDrawable);
        } else {
            button.setBackground((Drawable)gradientDrawable);
        }
        button.setOnClickListener(new View.OnClickListener(){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            public void onClick(View var1_1) {
                block11: {
                    var2_5 = null;
                    var3_8 = null;
                    var4_9 = inAppButton.getCtaUrl();
                    var1_1 = var3_8;
                    if (var4_9 == null) break block11;
                    var1_1 = var3_8;
                    if (var4_9.length() <= 0) break block11;
                    try {
                        var1_1 = Uri.parse((String)var4_9);
                    }
                    catch (IllegalArgumentException var1_2) {
                        MPLog.i("MixpanelAPI.TakeoverInAppActivity", "Can't parse notification URI, will not take any action", var1_2);
                        return;
                    }
                    try {
                        var1_1 = new Intent("android.intent.action.VIEW", (Uri)var1_1);
                        TakeoverInAppActivity.this.startActivity((Intent)var1_1);
                    }
                    catch (ActivityNotFoundException var1_3) {
                        MPLog.i("MixpanelAPI.TakeoverInAppActivity", "User doesn't have an activity for notification URI");
                    }
                    var1_1 = new JSONObject();
                    var1_1.put("url", (Object)var4_9);
                    break block11;
                    {
                        catch (JSONException var2_7) {}
                    }
                    catch (JSONException var1_4) {
                        var1_1 = var2_5;
                        MPLog.e("MixpanelAPI.TakeoverInAppActivity", "Can't put url into json properties");
                    }
                }
                var3_8 = "primary";
                if (((TakeoverInAppNotification)inAppNotification).getNumButtons() == 2) {
                    var3_8 = n == 0 ? "secondary" : "primary";
                }
                var2_5 = var1_1;
                if (var1_1 != null) ** GOTO lbl38
                try {
                    var2_5 = new JSONObject();
lbl38:
                    // 2 sources
                    var1_1 = var2_5;
                    var2_5.put("button", (Object)var3_8);
                    var1_1 = var2_5;
                }
                catch (JSONException var2_6) {
                    MPLog.e("MixpanelAPI.TakeoverInAppActivity", "Can't put button type into json properties");
                }
                TakeoverInAppActivity.access$100(TakeoverInAppActivity.this).getPeople().trackNotification("$campaign_open", inAppNotification, (JSONObject)var1_1);
                TakeoverInAppActivity.this.finish();
                UpdateDisplayState.releaseDisplayState(TakeoverInAppActivity.access$000(TakeoverInAppActivity.this));
            }
        });
    }

    private void setUpNotificationAnimations(ImageView imageView, TextView object, TextView textView, ArrayList<Button> arrayList, LinearLayout linearLayout) {
        ScaleAnimation scaleAnimation = new ScaleAnimation(0.95f, 1.0f, 0.95f, 1.0f, 1, 0.5f, 1, 1.0f);
        scaleAnimation.setDuration(200L);
        imageView.startAnimation((Animation)scaleAnimation);
        imageView = new TranslateAnimation(1, 0.0f, 1, 0.0f, 1, 0.5f, 1, 0.0f);
        imageView.setInterpolator((Interpolator)new DecelerateInterpolator());
        imageView.setDuration(200L);
        object.startAnimation((Animation)imageView);
        textView.startAnimation((Animation)imageView);
        object = arrayList.iterator();
        while (object.hasNext()) {
            ((Button)object.next()).startAnimation((Animation)imageView);
        }
        linearLayout.startAnimation(AnimationUtils.loadAnimation((Context)this, (int)R.anim.com_mixpanel_android_fade_in));
    }

    public void onBackPressed() {
        UpdateDisplayState.releaseDisplayState(this.mIntentId);
        super.onBackPressed();
    }

    protected void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mIntentId = this.getIntent().getIntExtra("com.mixpanel.android.takeoverinapp.TakeoverInAppActivity.INTENT_ID_KEY", Integer.MAX_VALUE);
        this.mUpdateDisplayState = UpdateDisplayState.claimDisplayState(this.mIntentId);
        if (this.mUpdateDisplayState == null) {
            MPLog.e("MixpanelAPI.TakeoverInAppActivity", "TakeoverInAppActivity intent received, but nothing was found to show.");
            this.finish();
            return;
        }
        this.mMixpanel = MixpanelAPI.getInstance((Context)this, this.mUpdateDisplayState.getToken());
        this.onCreateInAppNotification();
    }

}

