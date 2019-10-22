/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.app.Activity
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$CompressFormat
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.graphics.Rect
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  android.util.Base64OutputStream
 *  android.util.DisplayMetrics
 *  android.util.JsonWriter
 *  android.util.LruCache
 *  android.view.Display
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.view.Window
 *  android.view.WindowManager
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  org.json.JSONObject
 */
package com.mixpanel.android.viewcrawler;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64OutputStream;
import android.util.DisplayMetrics;
import android.util.JsonWriter;
import android.util.LruCache;
import android.view.Display;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.mpmetrics.ResourceIds;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.viewcrawler.Caller;
import com.mixpanel.android.viewcrawler.PropertyDescription;
import com.mixpanel.android.viewcrawler.UIThreadSet;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.AccessibleObject;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import org.json.JSONObject;

@TargetApi(value=16)
class ViewSnapshot {
    private final ClassNameCache mClassnameCache;
    private final MPConfig mConfig;
    private final Handler mMainThreadHandler;
    private final List<PropertyDescription> mProperties;
    private final ResourceIds mResourceIds;
    private final RootViewFinder mRootViewFinder;

    public ViewSnapshot(Context context, List<PropertyDescription> list, ResourceIds resourceIds) {
        this.mConfig = MPConfig.getInstance(context);
        this.mProperties = list;
        this.mResourceIds = resourceIds;
        this.mMainThreadHandler = new Handler(Looper.getMainLooper());
        this.mRootViewFinder = new RootViewFinder();
        this.mClassnameCache = new ClassNameCache(255);
    }

    /*
     * WARNING - void declaration
     */
    private void addProperties(JsonWriter jsonWriter, View view) throws IOException {
        Class<?> class_ = view.getClass();
        for (PropertyDescription propertyDescription : this.mProperties) {
            Object object;
            if (!propertyDescription.targetClass.isAssignableFrom(class_) || propertyDescription.accessor == null || (object = propertyDescription.accessor.applyMethod(view)) == null) continue;
            if (object instanceof Number) {
                jsonWriter.name(propertyDescription.name).value((Number)object);
                continue;
            }
            if (object instanceof Boolean) {
                jsonWriter.name(propertyDescription.name).value(((Boolean)object).booleanValue());
                continue;
            }
            if (object instanceof ColorStateList) {
                jsonWriter.name(propertyDescription.name).value((Number)((ColorStateList)object).getDefaultColor());
                continue;
            }
            if (object instanceof Drawable) {
                void var3_8;
                object = (Drawable)object;
                Rect rect = object.getBounds();
                jsonWriter.name(propertyDescription.name);
                jsonWriter.beginObject();
                jsonWriter.name("classes");
                jsonWriter.beginArray();
                Class<?> class_2 = object.getClass();
                while (var3_8 != Object.class) {
                    jsonWriter.value(var3_8.getCanonicalName());
                    Class class_3 = var3_8.getSuperclass();
                }
                jsonWriter.endArray();
                jsonWriter.name("dimensions");
                jsonWriter.beginObject();
                jsonWriter.name("left").value((long)rect.left);
                jsonWriter.name("right").value((long)rect.right);
                jsonWriter.name("top").value((long)rect.top);
                jsonWriter.name("bottom").value((long)rect.bottom);
                jsonWriter.endObject();
                if (object instanceof ColorDrawable) {
                    ColorDrawable colorDrawable = (ColorDrawable)object;
                    jsonWriter.name("color").value((long)colorDrawable.getColor());
                }
                jsonWriter.endObject();
                continue;
            }
            jsonWriter.name(propertyDescription.name).value(object.toString());
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void snapshotView(JsonWriter jsonWriter, View view) throws IOException {
        if (view.getVisibility() != 4 || !this.mConfig.getIgnoreInvisibleViewsEditor()) {
            int n;
            View view2;
            int n2 = view.getId();
            Object object = -1 == n2 ? null : this.mResourceIds.nameForId(n2);
            jsonWriter.beginObject();
            jsonWriter.name("hashCode").value((long)view.hashCode());
            jsonWriter.name("id").value((long)n2);
            jsonWriter.name("mp_id_name").value((String)object);
            object = view.getContentDescription();
            if (object == null) {
                jsonWriter.name("contentDescription").nullValue();
            } else {
                jsonWriter.name("contentDescription").value(object.toString());
            }
            if ((object = view.getTag()) == null) {
                jsonWriter.name("tag").nullValue();
            } else if (object instanceof CharSequence) {
                jsonWriter.name("tag").value(object.toString());
            }
            jsonWriter.name("top").value((long)view.getTop());
            jsonWriter.name("left").value((long)view.getLeft());
            jsonWriter.name("width").value((long)view.getWidth());
            jsonWriter.name("height").value((long)view.getHeight());
            jsonWriter.name("scrollX").value((long)view.getScrollX());
            jsonWriter.name("scrollY").value((long)view.getScrollY());
            jsonWriter.name("visibility").value((long)view.getVisibility());
            float f = 0.0f;
            float f2 = 0.0f;
            if (Build.VERSION.SDK_INT >= 11) {
                f = view.getTranslationX();
                f2 = view.getTranslationY();
            }
            jsonWriter.name("translationX").value((double)f);
            jsonWriter.name("translationY").value((double)f2);
            jsonWriter.name("classes");
            jsonWriter.beginArray();
            object = view.getClass();
            do {
                jsonWriter.value((String)this.mClassnameCache.get(object));
                view2 = object.getSuperclass();
                if (view2 == Object.class) break;
                object = view2;
            } while (view2 != null);
            jsonWriter.endArray();
            this.addProperties(jsonWriter, view);
            object = view.getLayoutParams();
            if (object instanceof RelativeLayout.LayoutParams) {
                object = ((RelativeLayout.LayoutParams)object).getRules();
                jsonWriter.name("layoutRules");
                jsonWriter.beginArray();
                n = ((int[])object).length;
                for (n2 = 0; n2 < n; ++n2) {
                    jsonWriter.value((long)object[n2]);
                }
                jsonWriter.endArray();
            }
            jsonWriter.name("subviews");
            jsonWriter.beginArray();
            if (view instanceof ViewGroup) {
                object = (ViewGroup)view;
                n = object.getChildCount();
                for (n2 = 0; n2 < n; ++n2) {
                    view2 = object.getChildAt(n2);
                    if (view2 == null) continue;
                    jsonWriter.value((long)view2.hashCode());
                }
            }
            jsonWriter.endArray();
            jsonWriter.endObject();
            if (view instanceof ViewGroup) {
                view = (ViewGroup)view;
                n = view.getChildCount();
                for (n2 = 0; n2 < n; ++n2) {
                    object = view.getChildAt(n2);
                    if (object == null) continue;
                    this.snapshotView(jsonWriter, (View)object);
                }
            }
        }
    }

    void snapshotViewHierarchy(JsonWriter jsonWriter, View view) throws IOException {
        jsonWriter.beginArray();
        this.snapshotView(jsonWriter, view);
        jsonWriter.endArray();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void snapshots(UIThreadSet<Activity> object, OutputStream outputStream) throws IOException {
        this.mRootViewFinder.findInActivities((UIThreadSet<Activity>)object);
        Object object2 = new FutureTask<List<RootViewInfo>>(this.mRootViewFinder);
        this.mMainThreadHandler.post(object2);
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream);
        object = Collections.emptyList();
        outputStreamWriter.write("[");
        try {
            object = object2 = ((FutureTask)object2).get(1L, TimeUnit.SECONDS);
        }
        catch (InterruptedException interruptedException) {
            MPLog.d("MixpanelAPI.Snapshot", "Screenshot interrupted, no screenshot will be sent.", interruptedException);
        }
        catch (TimeoutException timeoutException) {
            MPLog.i("MixpanelAPI.Snapshot", "Screenshot took more than 1 second to be scheduled and executed. No screenshot will be sent.", timeoutException);
        }
        catch (ExecutionException executionException) {
            MPLog.e("MixpanelAPI.Snapshot", "Exception thrown during screenshot attempt", executionException);
        }
        int n = object.size();
        int n2 = 0;
        do {
            if (n2 >= n) {
                outputStreamWriter.write("]");
                outputStreamWriter.flush();
                return;
            }
            if (n2 > 0) {
                outputStreamWriter.write(",");
            }
            object2 = (RootViewInfo)object.get(n2);
            outputStreamWriter.write("{");
            outputStreamWriter.write("\"activity\":");
            outputStreamWriter.write(JSONObject.quote((String)((RootViewInfo)object2).activityName));
            outputStreamWriter.write(",");
            outputStreamWriter.write("\"scale\":");
            outputStreamWriter.write(String.format("%s", Float.valueOf(((RootViewInfo)object2).scale)));
            outputStreamWriter.write(",");
            outputStreamWriter.write("\"serialized_objects\":");
            JsonWriter jsonWriter = new JsonWriter((Writer)outputStreamWriter);
            jsonWriter.beginObject();
            jsonWriter.name("rootObject").value((long)((RootViewInfo)object2).rootView.hashCode());
            jsonWriter.name("objects");
            this.snapshotViewHierarchy(jsonWriter, ((RootViewInfo)object2).rootView);
            jsonWriter.endObject();
            jsonWriter.flush();
            outputStreamWriter.write(",");
            outputStreamWriter.write("\"screenshot\":");
            outputStreamWriter.flush();
            ((RootViewInfo)object2).screenshot.writeBitmapJSON(Bitmap.CompressFormat.PNG, 100, outputStream);
            outputStreamWriter.write("}");
            ++n2;
        } while (true);
    }

    private static class CachedBitmap {
        private Bitmap mCached = null;
        private final Paint mPaint = new Paint(2);

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void recreate(int n, int n2, int n3, Bitmap bitmap) {
            synchronized (this) {
                int n4;
                if (this.mCached == null || this.mCached.getWidth() != n || (n4 = this.mCached.getHeight()) != n2) {
                    try {
                        this.mCached = Bitmap.createBitmap((int)n, (int)n2, (Bitmap.Config)Bitmap.Config.RGB_565);
                    }
                    catch (OutOfMemoryError outOfMemoryError) {
                        this.mCached = null;
                    }
                    if (this.mCached != null) {
                        this.mCached.setDensity(n3);
                    }
                }
                if (this.mCached != null) {
                    new Canvas(this.mCached).drawBitmap(bitmap, 0.0f, 0.0f, this.mPaint);
                }
                return;
            }
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void writeBitmapJSON(Bitmap.CompressFormat compressFormat, int n, OutputStream outputStream) throws IOException {
            synchronized (this) {
                void var3_3;
                if (this.mCached == null || this.mCached.getWidth() == 0 || this.mCached.getHeight() == 0) {
                    var3_3.write("null".getBytes());
                } else {
                    var3_3.write(34);
                    compressFormat = new Base64OutputStream((OutputStream)var3_3, 2);
                    this.mCached.compress(Bitmap.CompressFormat.PNG, 100, (OutputStream)compressFormat);
                    compressFormat.flush();
                    var3_3.write(34);
                }
                return;
            }
        }
    }

    private static class ClassNameCache
    extends LruCache<Class<?>, String> {
        public ClassNameCache(int n) {
            super(n);
        }

        protected String create(Class<?> class_) {
            return class_.getCanonicalName();
        }
    }

    private static class RootViewFinder
    implements Callable<List<RootViewInfo>> {
        private final CachedBitmap mCachedBitmap;
        private final int mClientDensity;
        private final DisplayMetrics mDisplayMetrics = new DisplayMetrics();
        private UIThreadSet<Activity> mLiveActivities;
        private final List<RootViewInfo> mRootViews = new ArrayList<RootViewInfo>();

        public RootViewFinder() {
            this.mClientDensity = 160;
            this.mCachedBitmap = new CachedBitmap();
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void takeScreenshot(RootViewInfo rootViewInfo) {
            Object object;
            float f;
            View view = rootViewInfo.rootView;
            Object object2 = null;
            try {
                object = View.class.getDeclaredMethod("createSnapshot", Bitmap.Config.class, Integer.TYPE, Boolean.TYPE);
                ((AccessibleObject)object).setAccessible(true);
                object = (Bitmap)((Method)object).invoke((Object)view, new Object[]{Bitmap.Config.RGB_565, -1, false});
                object2 = object;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                MPLog.v("MixpanelAPI.Snapshot", "Can't call createSnapshot, will use drawCache", noSuchMethodException);
            }
            catch (IllegalArgumentException illegalArgumentException) {
                MPLog.d("MixpanelAPI.Snapshot", "Can't call createSnapshot with arguments", illegalArgumentException);
            }
            catch (InvocationTargetException invocationTargetException) {
                MPLog.e("MixpanelAPI.Snapshot", "Exception when calling createSnapshot", invocationTargetException);
            }
            catch (IllegalAccessException illegalAccessException) {
                MPLog.e("MixpanelAPI.Snapshot", "Can't access createSnapshot, using drawCache", illegalAccessException);
            }
            catch (ClassCastException classCastException) {
                MPLog.e("MixpanelAPI.Snapshot", "createSnapshot didn't return a bitmap?", classCastException);
            }
            Object object3 = null;
            object = null;
            Bitmap bitmap = object2;
            if (object2 == null) {
                object = object3;
                try {
                    object = object3 = Boolean.valueOf(view.isDrawingCacheEnabled());
                    view.setDrawingCacheEnabled(true);
                    object = object3;
                    view.buildDrawingCache(true);
                    object = object3;
                    bitmap = view.getDrawingCache();
                    object = object3;
                }
                catch (RuntimeException runtimeException) {
                    MPLog.v("MixpanelAPI.Snapshot", "Can't take a bitmap snapshot of view " + (Object)view + ", skipping for now.", runtimeException);
                    bitmap = object2;
                }
            }
            float f2 = f = 1.0f;
            if (bitmap != null) {
                int n = bitmap.getDensity();
                if (n != 0) {
                    f = 160.0f / (float)n;
                }
                n = bitmap.getWidth();
                int n2 = bitmap.getHeight();
                int n3 = (int)((double)((float)bitmap.getWidth() * f) + 0.5);
                int n4 = (int)((double)((float)bitmap.getHeight() * f) + 0.5);
                f2 = f;
                if (n > 0) {
                    f2 = f;
                    if (n2 > 0) {
                        f2 = f;
                        if (n3 > 0) {
                            f2 = f;
                            if (n4 > 0) {
                                this.mCachedBitmap.recreate(n3, n4, 160, bitmap);
                                f2 = f;
                            }
                        }
                    }
                }
            }
            if (object != null && !((Boolean)object).booleanValue()) {
                view.setDrawingCacheEnabled(false);
            }
            rootViewInfo.scale = f2;
            rootViewInfo.screenshot = this.mCachedBitmap;
        }

        @Override
        public List<RootViewInfo> call() throws Exception {
            this.mRootViews.clear();
            for (Object object : this.mLiveActivities.getAll()) {
                String string2 = object.getClass().getCanonicalName();
                View view = object.getWindow().getDecorView().getRootView();
                object.getWindowManager().getDefaultDisplay().getMetrics(this.mDisplayMetrics);
                object = new RootViewInfo(string2, view);
                this.mRootViews.add((RootViewInfo)object);
            }
            int n = this.mRootViews.size();
            for (int i = 0; i < n; ++i) {
                this.takeScreenshot(this.mRootViews.get(i));
            }
            return this.mRootViews;
        }

        public void findInActivities(UIThreadSet<Activity> uIThreadSet) {
            this.mLiveActivities = uIThreadSet;
        }
    }

    private static class RootViewInfo {
        public final String activityName;
        public final View rootView;
        public float scale;
        public CachedBitmap screenshot;

        public RootViewInfo(String string2, View view) {
            this.activityName = string2;
            this.rootView = view;
            this.screenshot = null;
            this.scale = 1.0f;
        }
    }

}

