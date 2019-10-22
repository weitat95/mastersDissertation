/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.content.res.Resources$Theme
 *  android.content.res.XmlResourceParser
 *  android.graphics.ColorFilter
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.graphics.PorterDuffColorFilter
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.graphics.drawable.LayerDrawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.util.TypedValue
 *  android.util.Xml
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v7.widget;

import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.content.res.XmlResourceParser;
import android.graphics.ColorFilter;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.graphics.drawable.AnimatedVectorDrawableCompat;
import android.support.graphics.drawable.VectorDrawableCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.graphics.ColorUtils;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.util.ArrayMap;
import android.support.v4.util.LongSparseArray;
import android.support.v4.util.LruCache;
import android.support.v4.util.SparseArrayCompat;
import android.support.v7.appcompat.R;
import android.support.v7.content.res.AppCompatResources;
import android.support.v7.widget.DrawableUtils;
import android.support.v7.widget.ThemeUtils;
import android.support.v7.widget.TintInfo;
import android.support.v7.widget.VectorEnabledTintResources;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.util.Xml;
import java.lang.ref.WeakReference;
import java.util.WeakHashMap;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public final class AppCompatDrawableManager {
    private static final int[] COLORFILTER_COLOR_BACKGROUND_MULTIPLY;
    private static final int[] COLORFILTER_COLOR_CONTROL_ACTIVATED;
    private static final int[] COLORFILTER_TINT_COLOR_CONTROL_NORMAL;
    private static final ColorFilterLruCache COLOR_FILTER_CACHE;
    private static final PorterDuff.Mode DEFAULT_MODE;
    private static AppCompatDrawableManager INSTANCE;
    private static final int[] TINT_CHECKABLE_BUTTON_LIST;
    private static final int[] TINT_COLOR_CONTROL_NORMAL;
    private static final int[] TINT_COLOR_CONTROL_STATE_LIST;
    private ArrayMap<String, InflateDelegate> mDelegates;
    private final Object mDrawableCacheLock = new Object();
    private final WeakHashMap<Context, LongSparseArray<WeakReference<Drawable.ConstantState>>> mDrawableCaches = new WeakHashMap(0);
    private boolean mHasCheckedVectorDrawableSetup;
    private SparseArrayCompat<String> mKnownDrawableIdTags;
    private WeakHashMap<Context, SparseArrayCompat<ColorStateList>> mTintLists;
    private TypedValue mTypedValue;

    static {
        DEFAULT_MODE = PorterDuff.Mode.SRC_IN;
        COLOR_FILTER_CACHE = new ColorFilterLruCache(6);
        COLORFILTER_TINT_COLOR_CONTROL_NORMAL = new int[]{R.drawable.abc_textfield_search_default_mtrl_alpha, R.drawable.abc_textfield_default_mtrl_alpha, R.drawable.abc_ab_share_pack_mtrl_alpha};
        TINT_COLOR_CONTROL_NORMAL = new int[]{R.drawable.abc_ic_commit_search_api_mtrl_alpha, R.drawable.abc_seekbar_tick_mark_material, R.drawable.abc_ic_menu_share_mtrl_alpha, R.drawable.abc_ic_menu_copy_mtrl_am_alpha, R.drawable.abc_ic_menu_cut_mtrl_alpha, R.drawable.abc_ic_menu_selectall_mtrl_alpha, R.drawable.abc_ic_menu_paste_mtrl_am_alpha};
        COLORFILTER_COLOR_CONTROL_ACTIVATED = new int[]{R.drawable.abc_textfield_activated_mtrl_alpha, R.drawable.abc_textfield_search_activated_mtrl_alpha, R.drawable.abc_cab_background_top_mtrl_alpha, R.drawable.abc_text_cursor_material, R.drawable.abc_text_select_handle_left_mtrl_dark, R.drawable.abc_text_select_handle_middle_mtrl_dark, R.drawable.abc_text_select_handle_right_mtrl_dark, R.drawable.abc_text_select_handle_left_mtrl_light, R.drawable.abc_text_select_handle_middle_mtrl_light, R.drawable.abc_text_select_handle_right_mtrl_light};
        COLORFILTER_COLOR_BACKGROUND_MULTIPLY = new int[]{R.drawable.abc_popup_background_mtrl_mult, R.drawable.abc_cab_background_internal_bg, R.drawable.abc_menu_hardkey_panel_mtrl_mult};
        TINT_COLOR_CONTROL_STATE_LIST = new int[]{R.drawable.abc_tab_indicator_material, R.drawable.abc_textfield_search_material};
        TINT_CHECKABLE_BUTTON_LIST = new int[]{R.drawable.abc_btn_check_material, R.drawable.abc_btn_radio_material};
    }

    private void addDelegate(String string2, InflateDelegate inflateDelegate) {
        if (this.mDelegates == null) {
            this.mDelegates = new ArrayMap();
        }
        this.mDelegates.put(string2, inflateDelegate);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private boolean addDrawableToCache(Context context, long l, Drawable object) {
        Drawable.ConstantState constantState = object.getConstantState();
        if (constantState == null) {
            return false;
        }
        Object object2 = this.mDrawableCacheLock;
        synchronized (object2) {
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.mDrawableCaches.get((Object)context);
            object = longSparseArray;
            if (longSparseArray == null) {
                object = new LongSparseArray();
                this.mDrawableCaches.put(context, (LongSparseArray<WeakReference<Drawable.ConstantState>>)object);
            }
            object.put(l, new WeakReference<Drawable.ConstantState>(constantState));
            return true;
        }
    }

    private void addTintListToCache(Context context, int n, ColorStateList colorStateList) {
        SparseArrayCompat<ColorStateList> sparseArrayCompat;
        if (this.mTintLists == null) {
            this.mTintLists = new WeakHashMap();
        }
        SparseArrayCompat<Object> sparseArrayCompat2 = sparseArrayCompat = this.mTintLists.get((Object)context);
        if (sparseArrayCompat == null) {
            sparseArrayCompat2 = new SparseArrayCompat();
            this.mTintLists.put(context, sparseArrayCompat2);
        }
        sparseArrayCompat2.append(n, (Object)colorStateList);
    }

    private static boolean arrayContains(int[] arrn, int n) {
        boolean bl = false;
        int n2 = arrn.length;
        int n3 = 0;
        do {
            block4: {
                boolean bl2;
                block3: {
                    bl2 = bl;
                    if (n3 >= n2) break block3;
                    if (arrn[n3] != n) break block4;
                    bl2 = true;
                }
                return bl2;
            }
            ++n3;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void checkVectorDrawableSetup(Context context) {
        block3: {
            block2: {
                if (this.mHasCheckedVectorDrawableSetup) break block2;
                this.mHasCheckedVectorDrawableSetup = true;
                if ((context = this.getDrawable(context, R.drawable.abc_vector_test)) == null || !AppCompatDrawableManager.isVectorDrawable((Drawable)context)) break block3;
            }
            return;
        }
        this.mHasCheckedVectorDrawableSetup = false;
        throw new IllegalStateException("This app has been built with an incorrect configuration. Please configure your build for VectorDrawableCompat.");
    }

    private ColorStateList createBorderlessButtonColorStateList(Context context) {
        return this.createButtonColorStateList(context, 0);
    }

    private ColorStateList createButtonColorStateList(Context context, int n) {
        int[][] arrarrn = new int[4][];
        int[] arrn = new int[4];
        int n2 = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlHighlight);
        int n3 = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorButtonNormal);
        arrarrn[0] = ThemeUtils.DISABLED_STATE_SET;
        arrn[0] = n3;
        n3 = 0 + 1;
        arrarrn[n3] = ThemeUtils.PRESSED_STATE_SET;
        arrn[n3] = ColorUtils.compositeColors(n2, n);
        arrarrn[++n3] = ThemeUtils.FOCUSED_STATE_SET;
        arrn[n3] = ColorUtils.compositeColors(n2, n);
        n2 = n3 + 1;
        arrarrn[n2] = ThemeUtils.EMPTY_STATE_SET;
        arrn[n2] = n;
        return new ColorStateList((int[][])arrarrn, arrn);
    }

    private static long createCacheKey(TypedValue typedValue) {
        return (long)typedValue.assetCookie << 32 | (long)typedValue.data;
    }

    private ColorStateList createColoredButtonColorStateList(Context context) {
        return this.createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorAccent));
    }

    private ColorStateList createDefaultButtonColorStateList(Context context) {
        return this.createButtonColorStateList(context, ThemeUtils.getThemeAttrColor(context, R.attr.colorButtonNormal));
    }

    private Drawable createDrawableIfNeeded(Context context, int n) {
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue typedValue = this.mTypedValue;
        context.getResources().getValue(n, typedValue, true);
        long l = AppCompatDrawableManager.createCacheKey(typedValue);
        Drawable drawable2 = this.getCachedDrawable(context, l);
        if (drawable2 != null) {
            return drawable2;
        }
        if (n == R.drawable.abc_cab_background_top_material) {
            drawable2 = new LayerDrawable(new Drawable[]{this.getDrawable(context, R.drawable.abc_cab_background_internal_bg), this.getDrawable(context, R.drawable.abc_cab_background_top_mtrl_alpha)});
        }
        if (drawable2 != null) {
            drawable2.setChangingConfigurations(typedValue.changingConfigurations);
            this.addDrawableToCache(context, l, drawable2);
        }
        return drawable2;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ColorStateList createSwitchThumbColorStateList(Context context) {
        int[][] arrarrn = new int[3][];
        int[] arrn = new int[3];
        ColorStateList colorStateList = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorSwitchThumbNormal);
        if (colorStateList != null && colorStateList.isStateful()) {
            arrarrn[0] = ThemeUtils.DISABLED_STATE_SET;
            arrn[0] = colorStateList.getColorForState(arrarrn[0], 0);
            int n = 0 + 1;
            arrarrn[n] = ThemeUtils.CHECKED_STATE_SET;
            arrn[n] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
            arrarrn[++n] = ThemeUtils.EMPTY_STATE_SET;
            arrn[n] = colorStateList.getDefaultColor();
            do {
                return new ColorStateList((int[][])arrarrn, arrn);
                break;
            } while (true);
        }
        arrarrn[0] = ThemeUtils.DISABLED_STATE_SET;
        arrn[0] = ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
        int n = 0 + 1;
        arrarrn[n] = ThemeUtils.CHECKED_STATE_SET;
        arrn[n] = ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated);
        arrarrn[++n] = ThemeUtils.EMPTY_STATE_SET;
        arrn[n] = ThemeUtils.getThemeAttrColor(context, R.attr.colorSwitchThumbNormal);
        return new ColorStateList((int[][])arrarrn, arrn);
    }

    private static PorterDuffColorFilter createTintFilter(ColorStateList colorStateList, PorterDuff.Mode mode, int[] arrn) {
        if (colorStateList == null || mode == null) {
            return null;
        }
        return AppCompatDrawableManager.getPorterDuffColorFilter(colorStateList.getColorForState(arrn, 0), mode);
    }

    public static AppCompatDrawableManager get() {
        if (INSTANCE == null) {
            INSTANCE = new AppCompatDrawableManager();
            AppCompatDrawableManager.installDefaultInflateDelegates(INSTANCE);
        }
        return INSTANCE;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Drawable getCachedDrawable(Context context, long l) {
        Object object = this.mDrawableCacheLock;
        synchronized (object) {
            LongSparseArray<WeakReference<Drawable.ConstantState>> longSparseArray = this.mDrawableCaches.get((Object)context);
            if (longSparseArray == null) {
                return null;
            }
            Drawable.ConstantState constantState = longSparseArray.get(l);
            if (constantState == null) return null;
            if ((constantState = (Drawable.ConstantState)constantState.get()) != null) {
                return constantState.newDrawable(context.getResources());
            }
            longSparseArray.delete(l);
            return null;
        }
    }

    public static PorterDuffColorFilter getPorterDuffColorFilter(int n, PorterDuff.Mode mode) {
        PorterDuffColorFilter porterDuffColorFilter;
        PorterDuffColorFilter porterDuffColorFilter2 = porterDuffColorFilter = COLOR_FILTER_CACHE.get(n, mode);
        if (porterDuffColorFilter == null) {
            porterDuffColorFilter2 = new PorterDuffColorFilter(n, mode);
            COLOR_FILTER_CACHE.put(n, mode, porterDuffColorFilter2);
        }
        return porterDuffColorFilter2;
    }

    private ColorStateList getTintListFromCache(Context object, int n) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = colorStateList = null;
        if (this.mTintLists != null) {
            object = this.mTintLists.get(object);
            colorStateList2 = colorStateList;
            if (object != null) {
                colorStateList2 = (ColorStateList)((SparseArrayCompat)object).get(n);
            }
        }
        return colorStateList2;
    }

    static PorterDuff.Mode getTintMode(int n) {
        PorterDuff.Mode mode = null;
        if (n == R.drawable.abc_switch_thumb_material) {
            mode = PorterDuff.Mode.MULTIPLY;
        }
        return mode;
    }

    private static void installDefaultInflateDelegates(AppCompatDrawableManager appCompatDrawableManager) {
        if (Build.VERSION.SDK_INT < 24) {
            appCompatDrawableManager.addDelegate("vector", new VdcInflateDelegate());
            if (Build.VERSION.SDK_INT >= 11) {
                appCompatDrawableManager.addDelegate("animated-vector", new AvdcInflateDelegate());
            }
        }
    }

    private static boolean isVectorDrawable(Drawable drawable2) {
        return drawable2 instanceof VectorDrawableCompat || "android.graphics.drawable.VectorDrawable".equals(drawable2.getClass().getName());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Drawable loadDrawableFromDelegates(Context context, int n) {
        String string2;
        if (this.mDelegates == null) return null;
        if (this.mDelegates.isEmpty()) return null;
        if (this.mKnownDrawableIdTags != null) {
            string2 = this.mKnownDrawableIdTags.get(n);
            if ("appcompat_skip_skip".equals(string2)) return null;
            if (string2 != null && this.mDelegates.get(string2) == null) {
                return null;
            }
        } else {
            this.mKnownDrawableIdTags = new SparseArrayCompat();
        }
        if (this.mTypedValue == null) {
            this.mTypedValue = new TypedValue();
        }
        TypedValue typedValue = this.mTypedValue;
        Resources resources = context.getResources();
        resources.getValue(n, typedValue, true);
        long l = AppCompatDrawableManager.createCacheKey(typedValue);
        String string3 = string2 = this.getCachedDrawable(context, l);
        if (string2 != null) return string3;
        String string4 = string2;
        if (typedValue.string != null) {
            string4 = string2;
            if (typedValue.string.toString().endsWith(".xml")) {
                string4 = string2;
                try {
                    int n2;
                    resources = resources.getXml(n);
                    string4 = string2;
                    AttributeSet attributeSet = Xml.asAttributeSet((XmlPullParser)resources);
                    do {
                        string4 = string2;
                    } while ((n2 = resources.next()) != 2 && n2 != 1);
                    if (n2 != 2) {
                        string4 = string2;
                        throw new XmlPullParserException("No start tag found");
                    }
                    string4 = string2;
                    string3 = resources.getName();
                    string4 = string2;
                    this.mKnownDrawableIdTags.append(n, string3);
                    string4 = string2;
                    InflateDelegate inflateDelegate = (InflateDelegate)this.mDelegates.get(string3);
                    string3 = string2;
                    if (inflateDelegate != null) {
                        string4 = string2;
                        string3 = inflateDelegate.createFromXmlInner(context, (XmlPullParser)resources, attributeSet, context.getTheme());
                    }
                    string4 = string3;
                    if (string3 != null) {
                        string4 = string3;
                        string3.setChangingConfigurations(typedValue.changingConfigurations);
                        string4 = string3;
                        boolean bl = this.addDrawableToCache(context, l, (Drawable)string3);
                        string4 = string3;
                        if (bl) {
                            string4 = string3;
                        }
                    }
                }
                catch (Exception exception) {
                    Log.e((String)"AppCompatDrawableManager", (String)"Exception while inflating drawable", (Throwable)exception);
                }
            }
        }
        string3 = string4;
        if (string4 != null) return string3;
        this.mKnownDrawableIdTags.append(n, "appcompat_skip_skip");
        return string4;
    }

    private static void setPorterDuffColorFilter(Drawable drawable2, int n, PorterDuff.Mode mode) {
        Drawable drawable3 = drawable2;
        if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
            drawable3 = drawable2.mutate();
        }
        drawable2 = mode;
        if (mode == null) {
            drawable2 = DEFAULT_MODE;
        }
        drawable3.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(n, (PorterDuff.Mode)drawable2));
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Drawable tintDrawable(Context context, int n, boolean bl, Drawable drawable2) {
        ColorStateList colorStateList = this.getTintList(context, n);
        if (colorStateList != null) {
            context = drawable2;
            if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
                context = drawable2.mutate();
            }
            context = DrawableCompat.wrap((Drawable)context);
            DrawableCompat.setTintList((Drawable)context, colorStateList);
            drawable2 = AppCompatDrawableManager.getTintMode(n);
            colorStateList = context;
            if (drawable2 == null) return colorStateList;
            DrawableCompat.setTintMode((Drawable)context, (PorterDuff.Mode)drawable2);
            return context;
        }
        if (n == R.drawable.abc_seekbar_track_material) {
            colorStateList = (LayerDrawable)drawable2;
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908288), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
            return drawable2;
        }
        if (n == R.drawable.abc_ratingbar_material || n == R.drawable.abc_ratingbar_indicator_material || n == R.drawable.abc_ratingbar_small_material) {
            colorStateList = (LayerDrawable)drawable2;
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908288), ThemeUtils.getDisabledThemeAttrColor(context, R.attr.colorControlNormal), DEFAULT_MODE);
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908303), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
            AppCompatDrawableManager.setPorterDuffColorFilter(colorStateList.findDrawableByLayerId(16908301), ThemeUtils.getThemeAttrColor(context, R.attr.colorControlActivated), DEFAULT_MODE);
            return drawable2;
        }
        colorStateList = drawable2;
        if (AppCompatDrawableManager.tintDrawableUsingColorFilter(context, n, drawable2)) return colorStateList;
        colorStateList = drawable2;
        if (!bl) return colorStateList;
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    static void tintDrawable(Drawable drawable2, TintInfo tintInfo, int[] arrn) {
        if (DrawableUtils.canSafelyMutateDrawable(drawable2) && drawable2.mutate() != drawable2) {
            Log.d((String)"AppCompatDrawableManager", (String)"Mutated drawable is not the same instance as the input.");
            return;
        } else {
            if (tintInfo.mHasTintList || tintInfo.mHasTintMode) {
                ColorStateList colorStateList = tintInfo.mHasTintList ? tintInfo.mTintList : null;
                tintInfo = tintInfo.mHasTintMode ? tintInfo.mTintMode : DEFAULT_MODE;
                drawable2.setColorFilter((ColorFilter)AppCompatDrawableManager.createTintFilter(colorStateList, (PorterDuff.Mode)tintInfo, arrn));
            } else {
                drawable2.clearColorFilter();
            }
            if (Build.VERSION.SDK_INT > 23) return;
            {
                drawable2.invalidateSelf();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean tintDrawableUsingColorFilter(Context context, int n, Drawable drawable2) {
        PorterDuff.Mode mode;
        int n2;
        PorterDuff.Mode mode2 = DEFAULT_MODE;
        boolean bl = false;
        int n3 = 0;
        int n4 = -1;
        if (AppCompatDrawableManager.arrayContains(COLORFILTER_TINT_COLOR_CONTROL_NORMAL, n)) {
            n3 = R.attr.colorControlNormal;
            bl = true;
            mode = mode2;
            n2 = n4;
        } else if (AppCompatDrawableManager.arrayContains(COLORFILTER_COLOR_CONTROL_ACTIVATED, n)) {
            n3 = R.attr.colorControlActivated;
            bl = true;
            n2 = n4;
            mode = mode2;
        } else if (AppCompatDrawableManager.arrayContains(COLORFILTER_COLOR_BACKGROUND_MULTIPLY, n)) {
            n3 = 16842801;
            bl = true;
            mode = PorterDuff.Mode.MULTIPLY;
            n2 = n4;
        } else if (n == R.drawable.abc_list_divider_mtrl_alpha) {
            n3 = 16842800;
            bl = true;
            n2 = Math.round(40.8f);
            mode = mode2;
        } else {
            n2 = n4;
            mode = mode2;
            if (n == R.drawable.abc_dialog_material_background) {
                n3 = 16842801;
                bl = true;
                n2 = n4;
                mode = mode2;
            }
        }
        if (!bl) {
            return false;
        }
        mode2 = drawable2;
        if (DrawableUtils.canSafelyMutateDrawable(drawable2)) {
            mode2 = drawable2.mutate();
        }
        mode2.setColorFilter((ColorFilter)AppCompatDrawableManager.getPorterDuffColorFilter(ThemeUtils.getThemeAttrColor(context, n3), mode));
        if (n2 != -1) {
            mode2.setAlpha(n2);
        }
        return true;
    }

    public Drawable getDrawable(Context context, int n) {
        return this.getDrawable(context, n, false);
    }

    Drawable getDrawable(Context context, int n, boolean bl) {
        Drawable drawable2;
        this.checkVectorDrawableSetup(context);
        Drawable drawable3 = drawable2 = this.loadDrawableFromDelegates(context, n);
        if (drawable2 == null) {
            drawable3 = this.createDrawableIfNeeded(context, n);
        }
        drawable2 = drawable3;
        if (drawable3 == null) {
            drawable2 = ContextCompat.getDrawable(context, n);
        }
        drawable3 = drawable2;
        if (drawable2 != null) {
            drawable3 = this.tintDrawable(context, n, bl, drawable2);
        }
        if (drawable3 != null) {
            DrawableUtils.fixDrawable(drawable3);
        }
        return drawable3;
    }

    /*
     * Enabled aggressive block sorting
     */
    ColorStateList getTintList(Context context, int n) {
        ColorStateList colorStateList;
        ColorStateList colorStateList2 = colorStateList = this.getTintListFromCache(context, n);
        if (colorStateList != null) return colorStateList2;
        if (n == R.drawable.abc_edit_text_material) {
            colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_edittext);
        } else if (n == R.drawable.abc_switch_track_mtrl_alpha) {
            colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_switch_track);
        } else if (n == R.drawable.abc_switch_thumb_material) {
            colorStateList = this.createSwitchThumbColorStateList(context);
        } else if (n == R.drawable.abc_btn_default_mtrl_shape) {
            colorStateList = this.createDefaultButtonColorStateList(context);
        } else if (n == R.drawable.abc_btn_borderless_material) {
            colorStateList = this.createBorderlessButtonColorStateList(context);
        } else if (n == R.drawable.abc_btn_colored_material) {
            colorStateList = this.createColoredButtonColorStateList(context);
        } else if (n == R.drawable.abc_spinner_mtrl_am_alpha || n == R.drawable.abc_spinner_textfield_background_material) {
            colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_spinner);
        } else if (AppCompatDrawableManager.arrayContains(TINT_COLOR_CONTROL_NORMAL, n)) {
            colorStateList = ThemeUtils.getThemeAttrColorStateList(context, R.attr.colorControlNormal);
        } else if (AppCompatDrawableManager.arrayContains(TINT_COLOR_CONTROL_STATE_LIST, n)) {
            colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_default);
        } else if (AppCompatDrawableManager.arrayContains(TINT_CHECKABLE_BUTTON_LIST, n)) {
            colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_btn_checkable);
        } else if (n == R.drawable.abc_seekbar_thumb_material) {
            colorStateList = AppCompatResources.getColorStateList(context, R.color.abc_tint_seek_thumb);
        }
        colorStateList2 = colorStateList;
        if (colorStateList == null) return colorStateList2;
        this.addTintListToCache(context, n, colorStateList);
        return colorStateList;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void onConfigurationChanged(Context object) {
        Object object2 = this.mDrawableCacheLock;
        synchronized (object2) {
            object = this.mDrawableCaches.get(object);
            if (object != null) {
                ((LongSparseArray)object).clear();
            }
            return;
        }
    }

    Drawable onDrawableLoadedFromResources(Context context, VectorEnabledTintResources vectorEnabledTintResources, int n) {
        Drawable drawable2;
        Drawable drawable3 = drawable2 = this.loadDrawableFromDelegates(context, n);
        if (drawable2 == null) {
            drawable3 = vectorEnabledTintResources.superGetDrawable(n);
        }
        if (drawable3 != null) {
            return this.tintDrawable(context, n, false, drawable3);
        }
        return null;
    }

    private static class AvdcInflateDelegate
    implements InflateDelegate {
        AvdcInflateDelegate() {
        }

        @Override
        public Drawable createFromXmlInner(Context object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                object = AnimatedVectorDrawableCompat.createFromXmlInner(object, object.getResources(), xmlPullParser, attributeSet, theme);
                return object;
            }
            catch (Exception exception) {
                Log.e((String)"AvdcInflateDelegate", (String)"Exception while inflating <animated-vector>", (Throwable)exception);
                return null;
            }
        }
    }

    private static class ColorFilterLruCache
    extends LruCache<Integer, PorterDuffColorFilter> {
        public ColorFilterLruCache(int n) {
            super(n);
        }

        private static int generateCacheKey(int n, PorterDuff.Mode mode) {
            return (n + 31) * 31 + mode.hashCode();
        }

        PorterDuffColorFilter get(int n, PorterDuff.Mode mode) {
            return (PorterDuffColorFilter)this.get(ColorFilterLruCache.generateCacheKey(n, mode));
        }

        PorterDuffColorFilter put(int n, PorterDuff.Mode mode, PorterDuffColorFilter porterDuffColorFilter) {
            return this.put(ColorFilterLruCache.generateCacheKey(n, mode), porterDuffColorFilter);
        }
    }

    private static interface InflateDelegate {
        public Drawable createFromXmlInner(Context var1, XmlPullParser var2, AttributeSet var3, Resources.Theme var4);
    }

    private static class VdcInflateDelegate
    implements InflateDelegate {
        VdcInflateDelegate() {
        }

        @Override
        public Drawable createFromXmlInner(Context object, XmlPullParser xmlPullParser, AttributeSet attributeSet, Resources.Theme theme) {
            try {
                object = VectorDrawableCompat.createFromXmlInner(object.getResources(), xmlPullParser, attributeSet, theme);
                return object;
            }
            catch (Exception exception) {
                Log.e((String)"VdcInflateDelegate", (String)"Exception while inflating <vector>", (Throwable)exception);
                return null;
            }
        }
    }

}

