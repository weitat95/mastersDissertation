/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.Resources
 *  android.content.res.TypedArray
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.InflateException
 *  android.view.View
 *  android.view.View$OnClickListener
 */
package android.support.v7.app;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.os.Build;
import android.support.v4.util.ArrayMap;
import android.support.v4.view.ViewCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.ContextThemeWrapper;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatCheckBox;
import android.support.v7.widget.AppCompatCheckedTextView;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatImageButton;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatMultiAutoCompleteTextView;
import android.support.v7.widget.AppCompatRadioButton;
import android.support.v7.widget.AppCompatRatingBar;
import android.support.v7.widget.AppCompatSeekBar;
import android.support.v7.widget.AppCompatSpinner;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.TintContextWrapper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.View;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;

class AppCompatViewInflater {
    private static final String[] sClassPrefixList;
    private static final Map<String, Constructor<? extends View>> sConstructorMap;
    private static final Class<?>[] sConstructorSignature;
    private static final int[] sOnClickAttrs;
    private final Object[] mConstructorArgs = new Object[2];

    static {
        sConstructorSignature = new Class[]{Context.class, AttributeSet.class};
        sOnClickAttrs = new int[]{16843375};
        sClassPrefixList = new String[]{"android.widget.", "android.view.", "android.webkit."};
        sConstructorMap = new ArrayMap<String, Constructor<? extends View>>();
    }

    AppCompatViewInflater() {
    }

    private void checkOnClickListener(View view, AttributeSet attributeSet) {
        Object object = view.getContext();
        if (!(object instanceof ContextWrapper) || Build.VERSION.SDK_INT >= 15 && !ViewCompat.hasOnClickListeners(view)) {
            return;
        }
        attributeSet = object.obtainStyledAttributes(attributeSet, sOnClickAttrs);
        object = attributeSet.getString(0);
        if (object != null) {
            view.setOnClickListener((View.OnClickListener)new DeclaredOnClickListener(view, (String)object));
        }
        attributeSet.recycle();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private View createView(Context var1_1, String var2_3, String var3_4) throws ClassNotFoundException, InflateException {
        var5_10 = AppCompatViewInflater.sConstructorMap.get(var2_8);
        var4_11 = var5_10;
        if (var5_10 != null) ** GOTO lbl13
        try {
            var4_11 = var1_1 /* !! */ .getClassLoader();
            if (var3_9 != null) {
                var1_2 = (String)var3_9 + (String)var2_8;
            } else {
                var1_6 = var2_8;
            }
            var4_11 = var4_11.loadClass((String)var1_3).asSubclass(View.class).getConstructor(AppCompatViewInflater.sConstructorSignature);
            AppCompatViewInflater.sConstructorMap.put((String)var2_8, var4_11);
lbl13:
            // 2 sources
            var4_11.setAccessible(true);
            return (View)var4_11.newInstance(this.mConstructorArgs);
        }
        catch (Exception var1_7) {
            return null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private View createViewFromTag(Context context, String string2, AttributeSet attributeSet) {
        int n;
        String string3 = string2;
        if (string2.equals("view")) {
            string3 = attributeSet.getAttributeValue(null, "class");
        }
        try {
            this.mConstructorArgs[0] = context;
            this.mConstructorArgs[1] = attributeSet;
            if (-1 != string3.indexOf(46)) return this.createView(context, string3, null);
            n = 0;
        }
        catch (Exception exception) {
            return null;
        }
        catch (Throwable throwable) {
            throw throwable;
        }
        do {
            if (n >= sClassPrefixList.length) {
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
                return null;
            }
            string2 = this.createView(context, string3, sClassPrefixList[n]);
            if (string2 != null) {
                this.mConstructorArgs[0] = null;
                this.mConstructorArgs[1] = null;
                return string2;
            }
            ++n;
        } while (true);
    }

    private static Context themifyContext(Context context, AttributeSet object, boolean bl, boolean bl2) {
        block7: {
            int n;
            block8: {
                object = context.obtainStyledAttributes(object, R.styleable.View, 0, 0);
                int n2 = 0;
                if (bl) {
                    n2 = object.getResourceId(R.styleable.View_android_theme, 0);
                }
                n = n2;
                if (bl2) {
                    n = n2;
                    if (n2 == 0) {
                        n = n2 = object.getResourceId(R.styleable.View_theme, 0);
                        if (n2 != 0) {
                            Log.i((String)"AppCompatViewInflater", (String)"app:theme is now deprecated. Please move to using android:theme instead.");
                            n = n2;
                        }
                    }
                }
                object.recycle();
                object = context;
                if (n == 0) break block7;
                if (!(context instanceof ContextThemeWrapper)) break block8;
                object = context;
                if (((ContextThemeWrapper)context).getThemeResId() == n) break block7;
            }
            object = new ContextThemeWrapper(context, n);
        }
        return object;
    }

    /*
     * Enabled aggressive block sorting
     */
    public final View createView(View object, String string2, Context context, AttributeSet attributeSet, boolean bl, boolean bl2, boolean bl3, boolean bl4) {
        Context context2;
        block40: {
            block39: {
                context2 = context;
                if (bl) {
                    context2 = context;
                    if (object != null) {
                        context2 = object.getContext();
                    }
                }
                if (bl2) break block39;
                object = context2;
                if (!bl3) break block40;
            }
            object = AppCompatViewInflater.themifyContext(context2, attributeSet, bl2, bl3);
        }
        context2 = object;
        if (bl4) {
            context2 = TintContextWrapper.wrap((Context)object);
        }
        object = null;
        int n = -1;
        switch (string2.hashCode()) {
            case -938935918: {
                if (!string2.equals("TextView")) break;
                n = 0;
                break;
            }
            case 1125864064: {
                if (!string2.equals("ImageView")) break;
                n = 1;
                break;
            }
            case 2001146706: {
                if (!string2.equals("Button")) break;
                n = 2;
                break;
            }
            case 1666676343: {
                if (!string2.equals("EditText")) break;
                n = 3;
                break;
            }
            case -339785223: {
                if (!string2.equals("Spinner")) break;
                n = 4;
                break;
            }
            case -937446323: {
                if (!string2.equals("ImageButton")) break;
                n = 5;
                break;
            }
            case 1601505219: {
                if (!string2.equals("CheckBox")) break;
                n = 6;
                break;
            }
            case 776382189: {
                if (!string2.equals("RadioButton")) break;
                n = 7;
                break;
            }
            case -1455429095: {
                if (!string2.equals("CheckedTextView")) break;
                n = 8;
                break;
            }
            case 1413872058: {
                if (!string2.equals("AutoCompleteTextView")) break;
                n = 9;
                break;
            }
            case -1346021293: {
                if (!string2.equals("MultiAutoCompleteTextView")) break;
                n = 10;
                break;
            }
            case -1946472170: {
                if (!string2.equals("RatingBar")) break;
                n = 11;
                break;
            }
            case -658531749: {
                if (!string2.equals("SeekBar")) break;
                n = 12;
                break;
            }
        }
        switch (n) {
            case 0: {
                object = new AppCompatTextView(context2, attributeSet);
                break;
            }
            case 1: {
                object = new AppCompatImageView(context2, attributeSet);
                break;
            }
            case 2: {
                object = new AppCompatButton(context2, attributeSet);
                break;
            }
            case 3: {
                object = new AppCompatEditText(context2, attributeSet);
                break;
            }
            case 4: {
                object = new AppCompatSpinner(context2, attributeSet);
                break;
            }
            case 5: {
                object = new AppCompatImageButton(context2, attributeSet);
                break;
            }
            case 6: {
                object = new AppCompatCheckBox(context2, attributeSet);
                break;
            }
            case 7: {
                object = new AppCompatRadioButton(context2, attributeSet);
                break;
            }
            case 8: {
                object = new AppCompatCheckedTextView(context2, attributeSet);
                break;
            }
            case 9: {
                object = new AppCompatAutoCompleteTextView(context2, attributeSet);
                break;
            }
            case 10: {
                object = new AppCompatMultiAutoCompleteTextView(context2, attributeSet);
                break;
            }
            case 11: {
                object = new AppCompatRatingBar(context2, attributeSet);
                break;
            }
            case 12: {
                object = new AppCompatSeekBar(context2, attributeSet);
                break;
            }
        }
        Object object2 = object;
        if (object == null) {
            object2 = object;
            if (context != context2) {
                object2 = this.createViewFromTag(context2, string2, attributeSet);
            }
        }
        if (object2 != null) {
            this.checkOnClickListener((View)object2, attributeSet);
        }
        return object2;
    }

    private static class DeclaredOnClickListener
    implements View.OnClickListener {
        private final View mHostView;
        private final String mMethodName;
        private Context mResolvedContext;
        private Method mResolvedMethod;

        public DeclaredOnClickListener(View view, String string2) {
            this.mHostView = view;
            this.mMethodName = string2;
        }

        /*
         * WARNING - void declaration
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private void resolveMethod(Context object, String object2) {
            void var1_6;
            void var1_2;
            while (var1_2 != null) {
                block7: {
                    if (var1_2.isRestricted()) break block7;
                    Method method = var1_2.getClass().getMethod(this.mMethodName, View.class);
                    if (method == null) break block7;
                    try {
                        this.mResolvedMethod = method;
                        this.mResolvedContext = var1_2;
                        return;
                    }
                    catch (NoSuchMethodException noSuchMethodException) {
                        // empty catch block
                    }
                }
                if (var1_2 instanceof ContextWrapper) {
                    Context context = ((ContextWrapper)var1_2).getBaseContext();
                    continue;
                }
                Object var1_4 = null;
            }
            int n = this.mHostView.getId();
            if (n == -1) {
                String string2 = "";
                do {
                    throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick " + "attribute defined on view " + this.mHostView.getClass() + (String)var1_6);
                    break;
                } while (true);
            }
            String string3 = " with id '" + this.mHostView.getContext().getResources().getResourceEntryName(n) + "'";
            throw new IllegalStateException("Could not find method " + this.mMethodName + "(View) in a parent or ancestor Context for android:onClick " + "attribute defined on view " + this.mHostView.getClass() + (String)var1_6);
        }

        public void onClick(View view) {
            if (this.mResolvedMethod == null) {
                this.resolveMethod(this.mHostView.getContext(), this.mMethodName);
            }
            try {
                this.mResolvedMethod.invoke((Object)this.mResolvedContext, new Object[]{view});
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new IllegalStateException("Could not execute non-public method for android:onClick", illegalAccessException);
            }
            catch (InvocationTargetException invocationTargetException) {
                throw new IllegalStateException("Could not execute method for android:onClick", invocationTargetException);
            }
        }
    }

}

