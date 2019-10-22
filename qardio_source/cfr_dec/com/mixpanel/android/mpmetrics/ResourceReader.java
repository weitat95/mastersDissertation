/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.R
 *  android.R$drawable
 *  android.R$id
 *  android.content.Context
 *  android.util.SparseArray
 */
package com.mixpanel.android.mpmetrics;

import android.R;
import android.content.Context;
import android.util.SparseArray;
import com.mixpanel.android.mpmetrics.ResourceIds;
import com.mixpanel.android.util.MPLog;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public abstract class ResourceReader
implements ResourceIds {
    private final Context mContext;
    private final Map<String, Integer> mIdNameToId;
    private final SparseArray<String> mIdToIdName;

    protected ResourceReader(Context context) {
        this.mContext = context;
        this.mIdNameToId = new HashMap<String, Integer>();
        this.mIdToIdName = new SparseArray();
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void readClassIds(Class<?> class_, String string2, Map<String, Integer> map) {
        Field[] arrfield;
        int n;
        try {
            arrfield = class_.getFields();
            n = 0;
        }
        catch (IllegalAccessException illegalAccessException) {
            MPLog.e("MixpanelAPI.RsrcReader", "Can't read built-in id names from " + class_.getName(), illegalAccessException);
            return;
        }
        while (n < arrfield.length) {
            {
                Field field = arrfield[n];
                if (Modifier.isStatic(field.getModifiers()) && field.getType() == Integer.TYPE) {
                    String string3 = field.getName();
                    int n2 = field.getInt(null);
                    if (string2 != null) {
                        string3 = string2 + ":" + string3;
                    }
                    map.put(string3, n2);
                }
            }
            ++n;
        }
        return;
    }

    protected abstract String getLocalClassName(Context var1);

    protected abstract Class<?> getSystemClass();

    @Override
    public int idFromName(String string2) {
        return this.mIdNameToId.get(string2);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected void initialize() {
        this.mIdNameToId.clear();
        this.mIdToIdName.clear();
        ResourceReader.readClassIds(this.getSystemClass(), "android", this.mIdNameToId);
        Object object = this.getLocalClassName(this.mContext);
        try {
            ResourceReader.readClassIds(Class.forName((String)object), null, this.mIdNameToId);
        }
        catch (ClassNotFoundException classNotFoundException) {
            MPLog.w("MixpanelAPI.RsrcReader", "Can't load names for Android view ids from '" + (String)object + "', ids by name will not be available in the events editor.");
            MPLog.i("MixpanelAPI.RsrcReader", "You may be missing a Resources class for your package due to your proguard configuration, or you may be using an applicationId in your build that isn't the same as the package declared in your AndroidManifest.xml file.\nIf you're using proguard, you can fix this issue by adding the following to your proguard configuration:\n\n-keep class **.R$* {\n    <fields>;\n}\n\nIf you're not using proguard, or if your proguard configuration already contains the directive above, you can add the following to your AndroidManifest.xml file to explicitly point the Mixpanel library to the appropriate library for your resources class:\n\n<meta-data android:name=\"com.mixpanel.android.MPConfig.ResourcePackageName\" android:value=\"YOUR_PACKAGE_NAME\" />\n\nwhere YOUR_PACKAGE_NAME is the same string you use for the \"package\" attribute in your <manifest> tag.");
        }
        object = this.mIdNameToId.entrySet().iterator();
        while (object.hasNext()) {
            Map.Entry entry = (Map.Entry)object.next();
            this.mIdToIdName.put(((Integer)entry.getValue()).intValue(), entry.getKey());
        }
        return;
    }

    @Override
    public boolean knownIdName(String string2) {
        return this.mIdNameToId.containsKey(string2);
    }

    @Override
    public String nameForId(int n) {
        return (String)this.mIdToIdName.get(n);
    }

    public static class Drawables
    extends ResourceReader {
        private final String mResourcePackageName;

        protected Drawables(String string2, Context context) {
            super(context);
            this.mResourcePackageName = string2;
            this.initialize();
        }

        @Override
        protected String getLocalClassName(Context context) {
            return this.mResourcePackageName + ".R$drawable";
        }

        @Override
        protected Class<?> getSystemClass() {
            return R.drawable.class;
        }
    }

    public static class Ids
    extends ResourceReader {
        private final String mResourcePackageName;

        public Ids(String string2, Context context) {
            super(context);
            this.mResourcePackageName = string2;
            this.initialize();
        }

        @Override
        protected String getLocalClassName(Context context) {
            return this.mResourcePackageName + ".R$id";
        }

        @Override
        protected Class<?> getSystemClass() {
            return R.id.class;
        }
    }

}

