/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.util.TypedValue
 *  android.view.View
 */
package butterknife.internal;

import android.content.Context;
import android.content.res.Resources;
import android.util.TypedValue;
import android.view.View;

public final class Utils {
    private static final TypedValue VALUE = new TypedValue();

    public static <T> T castView(View object, int n, String string2, Class<T> class_) {
        try {
            class_ = class_.cast(object);
        }
        catch (ClassCastException classCastException) {
            object = Utils.getResourceEntryName(object, n);
            throw new IllegalStateException("View '" + (String)object + "' with ID " + n + " for " + string2 + " was of the wrong type. See cause for more info.", classCastException);
        }
        return (T)class_;
    }

    public static <T> T findOptionalViewAsType(View view, int n, String string2, Class<T> class_) {
        return Utils.castView(view.findViewById(n), n, string2, class_);
    }

    public static View findRequiredView(View object, int n, String string2) {
        View view = object.findViewById(n);
        if (view != null) {
            return view;
        }
        object = Utils.getResourceEntryName(object, n);
        throw new IllegalStateException("Required view '" + (String)object + "' with ID " + n + " for " + string2 + " was not found. If this view is optional add '@Nullable' (fields) or '@Optional' (methods) annotation.");
    }

    public static <T> T findRequiredViewAsType(View view, int n, String string2, Class<T> class_) {
        return Utils.castView(Utils.findRequiredView(view, n, string2), n, string2, class_);
    }

    private static String getResourceEntryName(View view, int n) {
        if (view.isInEditMode()) {
            return "<unavailable while editing>";
        }
        return view.getContext().getResources().getResourceEntryName(n);
    }
}

