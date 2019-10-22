/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.app.Fragment
 *  android.app.FragmentManager
 *  android.app.FragmentTransaction
 *  android.content.ActivityNotFoundException
 *  android.content.Context
 *  android.content.Intent
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.GradientDrawable
 *  android.net.Uri
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Handler
 *  android.util.DisplayMetrics
 *  android.util.TypedValue
 *  android.view.GestureDetector
 *  android.view.GestureDetector$OnGestureListener
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.View
 *  android.view.View$OnTouchListener
 *  android.view.ViewGroup
 *  android.view.animation.Animation
 *  android.view.animation.DecelerateInterpolator
 *  android.view.animation.Interpolator
 *  android.view.animation.ScaleAnimation
 *  android.view.animation.TranslateAnimation
 *  android.widget.ImageView
 *  android.widget.TextView
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;
import com.mixpanel.android.R;
import com.mixpanel.android.mpmetrics.InAppNotification;
import com.mixpanel.android.mpmetrics.MiniInAppNotification;
import com.mixpanel.android.mpmetrics.MixpanelAPI;
import com.mixpanel.android.mpmetrics.UpdateDisplayState;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.ViewUtils;
import org.json.JSONException;
import org.json.JSONObject;

@SuppressLint(value={"ClickableViewAccessibility"})
@TargetApi(value=16)
public class InAppFragment
extends Fragment {
    private boolean mCleanedUp;
    private GestureDetector mDetector;
    private Runnable mDisplayMini;
    private UpdateDisplayState.DisplayState.InAppNotificationState mDisplayState;
    private int mDisplayStateId;
    private Handler mHandler;
    private View mInAppView;
    private MixpanelAPI mMixpanel;
    private Activity mParent;
    private Runnable mRemover;

    private void cleanUp() {
        if (!this.mCleanedUp) {
            this.mHandler.removeCallbacks(this.mRemover);
            this.mHandler.removeCallbacks(this.mDisplayMini);
            UpdateDisplayState.releaseDisplayState(this.mDisplayStateId);
            this.mParent.getFragmentManager().beginTransaction().remove((Fragment)this).commit();
        }
        this.mCleanedUp = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void remove() {
        boolean bl = Build.VERSION.SDK_INT >= 17 ? this.mParent.isDestroyed() : false;
        if (!(this.mParent == null || this.mParent.isFinishing() || bl || this.mCleanedUp)) {
            this.mHandler.removeCallbacks(this.mRemover);
            this.mHandler.removeCallbacks(this.mDisplayMini);
            this.mParent.getFragmentManager().beginTransaction().setCustomAnimations(0, R.animator.com_mixpanel_android_slide_down).remove((Fragment)this).commit();
            UpdateDisplayState.releaseDisplayState(this.mDisplayStateId);
            this.mCleanedUp = true;
        }
    }

    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mParent = activity;
        if (this.mDisplayState == null) {
            this.cleanUp();
            return;
        }
        this.mHandler = new Handler();
        this.mRemover = new Runnable(){

            @Override
            public void run() {
                InAppFragment.this.remove();
            }
        };
        this.mDisplayMini = new Runnable(){

            @Override
            public void run() {
                InAppFragment.this.mInAppView.setVisibility(0);
                InAppFragment.this.mInAppView.setOnTouchListener(new View.OnTouchListener(){

                    public boolean onTouch(View view, MotionEvent motionEvent) {
                        return InAppFragment.this.mDetector.onTouchEvent(motionEvent);
                    }
                });
                ImageView imageView = (ImageView)InAppFragment.this.mInAppView.findViewById(R.id.com_mixpanel_android_notification_image);
                float f = TypedValue.applyDimension((int)1, (float)65.0f, (DisplayMetrics)InAppFragment.this.mParent.getResources().getDisplayMetrics());
                TranslateAnimation translateAnimation = new TranslateAnimation(0.0f, 0.0f, f, 0.0f);
                translateAnimation.setInterpolator((Interpolator)new DecelerateInterpolator());
                translateAnimation.setDuration(200L);
                InAppFragment.this.mInAppView.startAnimation((Animation)translateAnimation);
                translateAnimation = new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f, f / 2.0f, f / 2.0f);
                translateAnimation.setInterpolator((Interpolator)new SineBounceInterpolator());
                translateAnimation.setDuration(400L);
                translateAnimation.setStartOffset(200L);
                imageView.startAnimation((Animation)translateAnimation);
            }

        };
        this.mDetector = new GestureDetector((Context)activity, new GestureDetector.OnGestureListener(){

            public boolean onDown(MotionEvent motionEvent) {
                return true;
            }

            public boolean onFling(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                if (f2 > 0.0f) {
                    InAppFragment.this.remove();
                }
                return true;
            }

            public void onLongPress(MotionEvent motionEvent) {
            }

            public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float f, float f2) {
                return false;
            }

            public void onShowPress(MotionEvent motionEvent) {
            }

            /*
             * Loose catch block
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             * Lifted jumps to return sites
             */
            public boolean onSingleTapUp(MotionEvent motionEvent) {
                MiniInAppNotification miniInAppNotification;
                block8: {
                    miniInAppNotification = (MiniInAppNotification)InAppFragment.this.mDisplayState.getInAppNotification();
                    Object var2_5 = null;
                    Intent intent = null;
                    String string2 = miniInAppNotification.getCtaUrl();
                    motionEvent = intent;
                    if (string2 == null) break block8;
                    motionEvent = intent;
                    if (string2.length() <= 0) break block8;
                    try {
                        motionEvent = Uri.parse((String)string2);
                    }
                    catch (IllegalArgumentException illegalArgumentException) {
                        MPLog.i("MixpanelAPI.InAppFrag", "Can't parse notification URI, will not take any action", illegalArgumentException);
                        return true;
                    }
                    try {
                        intent = new Intent("android.intent.action.VIEW", (Uri)motionEvent);
                        InAppFragment.this.mParent.startActivity(intent);
                    }
                    catch (ActivityNotFoundException activityNotFoundException) {
                        MPLog.i("MixpanelAPI.InAppFrag", "User doesn't have an activity for notification URI " + (Object)motionEvent);
                    }
                    motionEvent = new JSONObject();
                    motionEvent.put("url", (Object)string2);
                    break block8;
                    {
                        catch (JSONException jSONException) {}
                    }
                    catch (JSONException jSONException) {
                        motionEvent = var2_5;
                        MPLog.e("MixpanelAPI.InAppFrag", "Can't put url into json properties");
                    }
                }
                InAppFragment.this.mMixpanel.getPeople().trackNotification("$campaign_open", miniInAppNotification, (JSONObject)motionEvent);
                InAppFragment.this.remove();
                return true;
            }
        });
    }

    public void onCreate(Bundle bundle) {
        super.onCreate(bundle);
        this.mCleanedUp = false;
    }

    /*
     * Enabled aggressive block sorting
     */
    public View onCreateView(LayoutInflater layoutInflater, ViewGroup object, Bundle bundle) {
        super.onCreateView(layoutInflater, (ViewGroup)object, bundle);
        if (this.mDisplayState == null) {
            this.cleanUp();
            return this.mInAppView;
        }
        this.mInAppView = layoutInflater.inflate(R.layout.com_mixpanel_android_activity_notification_mini, (ViewGroup)object, false);
        bundle = (TextView)this.mInAppView.findViewById(R.id.com_mixpanel_android_notification_title);
        layoutInflater = (ImageView)this.mInAppView.findViewById(R.id.com_mixpanel_android_notification_image);
        object = (MiniInAppNotification)this.mDisplayState.getInAppNotification();
        bundle.setText((CharSequence)((InAppNotification)object).getBody());
        bundle.setTextColor(((InAppNotification)object).getBodyColor());
        layoutInflater.setImageBitmap(((InAppNotification)object).getImage());
        this.mHandler.postDelayed(this.mRemover, 10000L);
        bundle = new GradientDrawable();
        bundle.setColor(((InAppNotification)object).getBackgroundColor());
        bundle.setCornerRadius(ViewUtils.dpToPx(7.0f, (Context)this.getActivity()));
        bundle.setStroke((int)ViewUtils.dpToPx(2.0f, (Context)this.getActivity()), ((MiniInAppNotification)object).getBorderColor());
        if (Build.VERSION.SDK_INT < 16) {
            this.mInAppView.setBackgroundDrawable((Drawable)bundle);
        } else {
            this.mInAppView.setBackground((Drawable)bundle);
        }
        bundle = new BitmapDrawable(this.getResources(), this.mDisplayState.getInAppNotification().getImage());
        bundle.setColorFilter(((MiniInAppNotification)object).getImageTintColor(), PorterDuff.Mode.SRC_ATOP);
        layoutInflater.setImageDrawable((Drawable)bundle);
        return this.mInAppView;
    }

    public void onPause() {
        super.onPause();
        this.cleanUp();
    }

    public void onResume() {
        super.onResume();
        this.mHandler.postDelayed(this.mDisplayMini, 500L);
    }

    public void onSaveInstanceState(Bundle bundle) {
        this.cleanUp();
        super.onSaveInstanceState(bundle);
    }

    public void onStart() {
        super.onStart();
        if (this.mCleanedUp) {
            this.mParent.getFragmentManager().beginTransaction().remove((Fragment)this).commit();
        }
    }

    public void setDisplayState(MixpanelAPI mixpanelAPI, int n, UpdateDisplayState.DisplayState.InAppNotificationState inAppNotificationState) {
        this.mMixpanel = mixpanelAPI;
        this.mDisplayStateId = n;
        this.mDisplayState = inAppNotificationState;
    }

    private class SineBounceInterpolator
    implements Interpolator {
        public float getInterpolation(float f) {
            return (float)(-(Math.pow(2.718281828459045, -8.0f * f) * Math.cos(12.0f * f))) + 1.0f;
        }
    }

}

