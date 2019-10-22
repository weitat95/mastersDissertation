/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Bitmap
 *  android.graphics.drawable.BitmapDrawable
 *  android.graphics.drawable.ColorDrawable
 *  android.graphics.drawable.Drawable
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.viewcrawler;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import com.mixpanel.android.mpmetrics.ResourceIds;
import com.mixpanel.android.util.ImageStore;
import com.mixpanel.android.util.JSONUtils;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.MPPair;
import com.mixpanel.android.viewcrawler.Caller;
import com.mixpanel.android.viewcrawler.Pathfinder;
import com.mixpanel.android.viewcrawler.PropertyDescription;
import com.mixpanel.android.viewcrawler.ViewSnapshot;
import com.mixpanel.android.viewcrawler.ViewVisitor;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class EditProtocol {
    private static final List<Pathfinder.PathElement> NEVER_MATCH_PATH;
    private static final Class<?>[] NO_PARAMS;
    private final Context mContext;
    private final ImageStore mImageStore;
    private final ViewVisitor.OnLayoutErrorListener mLayoutErrorListener;
    private final ResourceIds mResourceIds;

    static {
        NO_PARAMS = new Class[0];
        NEVER_MATCH_PATH = Collections.emptyList();
    }

    public EditProtocol(Context context, ResourceIds resourceIds, ImageStore imageStore, ViewVisitor.OnLayoutErrorListener onLayoutErrorListener) {
        this.mContext = context;
        this.mResourceIds = resourceIds;
        this.mImageStore = imageStore;
        this.mLayoutErrorListener = onLayoutErrorListener;
    }

    private Object convertArgument(Object object, String string2, List<String> list) throws BadInstructionsException, CantGetEditAssetsException {
        block9: {
            try {
                if (!"java.lang.CharSequence".equals(string2)) break block9;
                return object;
            }
            catch (ClassCastException classCastException) {
                throw new BadInstructionsException("Couldn't interpret <" + object + "> as " + string2);
            }
        }
        if (!"boolean".equals(string2) && !"java.lang.Boolean".equals(string2)) {
            if ("int".equals(string2) || "java.lang.Integer".equals(string2)) {
                return ((Number)object).intValue();
            }
            if ("float".equals(string2) || "java.lang.Float".equals(string2)) {
                return Float.valueOf(((Number)object).floatValue());
            }
            if ("android.graphics.drawable.Drawable".equals(string2)) {
                return this.readBitmapDrawable((JSONObject)object, list);
            }
            if ("android.graphics.drawable.BitmapDrawable".equals(string2)) {
                return this.readBitmapDrawable((JSONObject)object, list);
            }
            if ("android.graphics.drawable.ColorDrawable".equals(string2)) {
                return new ColorDrawable(((Number)object).intValue());
            }
            throw new BadInstructionsException("Don't know how to interpret type " + string2 + " (arg was " + object + ")");
        }
        return object;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Drawable readBitmapDrawable(JSONObject jSONObject, List<String> list) throws BadInstructionsException, CantGetEditAssetsException {
        try {
            if (jSONObject.isNull("url")) {
                throw new BadInstructionsException("Can't construct a BitmapDrawable with a null url");
            }
        }
        catch (JSONException jSONException) {
            throw new BadInstructionsException("Couldn't read drawable description", jSONException);
        }
        {
            int n;
            boolean bl;
            int n2;
            int n3;
            int n4;
            String string2 = jSONObject.getString("url");
            boolean bl2 = jSONObject.isNull("dimensions");
            if (bl2) {
                n3 = 0;
                n2 = 0;
                n4 = 0;
                n = 0;
                bl = false;
            } else {
                jSONObject = jSONObject.getJSONObject("dimensions");
                n = jSONObject.getInt("left");
                n4 = jSONObject.getInt("right");
                n2 = jSONObject.getInt("top");
                n3 = jSONObject.getInt("bottom");
                bl = true;
            }
            try {
                jSONObject = this.mImageStore.getImage(string2);
                list.add(string2);
            }
            catch (ImageStore.CantGetImageException cantGetImageException) {
                throw new CantGetEditAssetsException(cantGetImageException.getMessage(), cantGetImageException.getCause());
            }
            jSONObject = new BitmapDrawable(Resources.getSystem(), (Bitmap)jSONObject);
            if (bl) {
                jSONObject.setBounds(n, n2, n4, n3);
                return jSONObject;
            }
            return jSONObject;
        }
    }

    /*
     * WARNING - void declaration
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private PropertyDescription readPropertyDescription(Class<?> object, JSONObject object2) throws BadInstructionsException {
        String string2 = object2.getString("name");
        Object object3 = null;
        try {
            void var2_7;
            if (object2.has("get")) {
                JSONObject jSONObject = object2.getJSONObject("get");
                object3 = jSONObject.getString("selector");
                Class<?> class_ = Class.forName(jSONObject.getJSONObject("result").getString("type"));
                object3 = new Caller((Class<?>)object, (String)object3, NO_PARAMS, class_);
            }
            if (object2.has("set")) {
                String string3 = object2.getJSONObject("set").getString("selector");
                do {
                    return new PropertyDescription(string2, (Class<?>)object, (Caller)object3, (String)var2_7);
                    break;
                } while (true);
            }
            Object var2_8 = null;
            return new PropertyDescription(string2, (Class<?>)object, (Caller)object3, (String)var2_7);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new BadInstructionsException("Can't create property reader", noSuchMethodException);
        }
        catch (JSONException jSONException) {
            throw new BadInstructionsException("Can't read property JSON", jSONException);
        }
        catch (ClassNotFoundException classNotFoundException) {
            throw new BadInstructionsException("Can't read property JSON, relevant arg/return class not found", classNotFoundException);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private Integer reconcileIds(int n, String string2, ResourceIds resourceIds) {
        int n2;
        if (string2 != null) {
            if (!resourceIds.knownIdName(string2)) {
                MPLog.w("MixpanelAPI.EProtocol", "Path element contains an id name not known to the system. No views will be matched.\nMake sure that you're not stripping your packages R class out with proguard.\nid name was \"" + string2 + "\"");
                return null;
            }
            n2 = resourceIds.idFromName(string2);
        } else {
            n2 = -1;
        }
        if (-1 != n2 && -1 != n && n2 != n) {
            MPLog.e("MixpanelAPI.EProtocol", "Path contains both a named and an explicit id, and they don't match. No views will be matched.");
            return null;
        }
        if (-1 != n2) {
            return n2;
        }
        return n;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Edit readEdit(JSONObject object) throws BadInstructionsException, CantGetEditAssetsException {
        JSONArray jSONArray;
        int n;
        int n2;
        List<Pathfinder.PathElement> list;
        ArrayList<ViewVisitor.LayoutRule> arrayList;
        ArrayList<String> arrayList2;
        block16: {
            Object[] arrobject;
            arrayList2 = new ArrayList<String>();
            list = this.readPath(object.getJSONArray("path"), this.mResourceIds);
            if (list.size() == 0) {
                throw new InapplicableInstructionsException("Edit will not be bound to any element in the UI.");
            }
            if (!object.getString("change_type").equals("property")) break block16;
            Object object2 = object.getJSONObject("property").getString("classname");
            if (object2 == null) {
                throw new BadInstructionsException("Can't bind an edit property without a target class");
            }
            try {
                arrobject = Class.forName((String)object2);
                object2 = this.readPropertyDescription((Class<?>)arrobject, object.getJSONObject("property"));
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new BadInstructionsException("Can't find class for visit path: " + (String)object2, classNotFoundException);
            }
            object = object.getJSONArray("args");
            arrobject = new Object[object.length()];
            for (int i = 0; i < object.length(); ++i) {
                JSONArray jSONArray2 = object.getJSONArray(i);
                arrobject[i] = this.convertArgument(jSONArray2.get(0), jSONArray2.getString(1), arrayList2);
            }
            object = ((PropertyDescription)object2).makeMutator(arrobject);
            if (object == null) {
                throw new BadInstructionsException("Can't update a read-only property " + ((PropertyDescription)object2).name + " (add a mutator to make this work)");
            }
            object = new ViewVisitor.PropertySetVisitor(list, (Caller)object, ((PropertyDescription)object2).accessor);
            return new Edit((ViewVisitor)object, arrayList2);
        }
        try {
            if (!object.getString("change_type").equals("layout")) throw new BadInstructionsException("Can't figure out the edit type");
            jSONArray = object.getJSONArray("args");
            arrayList = new ArrayList<ViewVisitor.LayoutRule>();
            n2 = jSONArray.length();
            n = 0;
        }
        catch (NoSuchMethodException noSuchMethodException) {
            throw new BadInstructionsException("Can't create property mutator", noSuchMethodException);
        }
        catch (JSONException jSONException) {
            throw new BadInstructionsException("Can't interpret instructions due to JSONException", jSONException);
        }
        do {
            if (n >= n2) {
                object = new ViewVisitor.LayoutUpdateVisitor(list, arrayList, object.getString("name"), this.mLayoutErrorListener);
                return new Edit((ViewVisitor)object, arrayList2);
            }
            JSONObject jSONObject = jSONArray.optJSONObject(n);
            String string2 = jSONObject.getString("view_id_name");
            String string3 = jSONObject.getString("anchor_id_name");
            Integer n3 = this.reconcileIds(-1, string2, this.mResourceIds);
            Integer n4 = string3.equals("0") ? Integer.valueOf(0) : (string3.equals("-1") ? Integer.valueOf(-1) : this.reconcileIds(-1, string3, this.mResourceIds));
            if (n3 == null || n4 == null) {
                MPLog.w("MixpanelAPI.EProtocol", "View (" + string2 + ") or anchor (" + string3 + ") not found.");
            } else {
                arrayList.add(new ViewVisitor.LayoutRule(n3, jSONObject.getInt("verb"), n4));
            }
            ++n;
        } while (true);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ViewVisitor readEventBinding(JSONObject object, ViewVisitor.OnEventListener onEventListener) throws BadInstructionsException {
        String string2;
        String string3;
        try {
            string2 = object.getString("event_name");
            string3 = object.getString("event_type");
            object = this.readPath(object.getJSONArray("path"), this.mResourceIds);
            if (object.size() == 0) {
                throw new InapplicableInstructionsException("event '" + string2 + "' will not be bound to any element in the UI.");
            }
        }
        catch (JSONException jSONException) {
            throw new BadInstructionsException("Can't interpret instructions due to JSONException", jSONException);
        }
        {
            if ("click".equals(string3)) {
                return new ViewVisitor.AddAccessibilityEventVisitor((List<Pathfinder.PathElement>)object, 1, string2, onEventListener);
            }
            if ("selected".equals(string3)) {
                return new ViewVisitor.AddAccessibilityEventVisitor((List<Pathfinder.PathElement>)object, 4, string2, onEventListener);
            }
            if ("text_changed".equals(string3)) {
                return new ViewVisitor.AddTextChangeListener((List<Pathfinder.PathElement>)object, string2, onEventListener);
            }
            if ("detected".equals(string3)) {
                return new ViewVisitor.ViewDetectorVisitor((List<Pathfinder.PathElement>)object, string2, onEventListener);
            }
            throw new BadInstructionsException("Mixpanel can't track event type \"" + string3 + "\"");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    List<Pathfinder.PathElement> readPath(JSONArray jSONArray, ResourceIds resourceIds) throws JSONException {
        ArrayList<Pathfinder.PathElement> arrayList = new ArrayList<Pathfinder.PathElement>();
        int n = 0;
        do {
            int n2;
            List<Pathfinder.PathElement> list = arrayList;
            if (n >= jSONArray.length()) return list;
            JSONObject jSONObject = jSONArray.getJSONObject(n);
            Object object = JSONUtils.optionalStringKey(jSONObject, "prefix");
            list = JSONUtils.optionalStringKey(jSONObject, "view_class");
            int n3 = jSONObject.optInt("index", -1);
            String string2 = JSONUtils.optionalStringKey(jSONObject, "contentDescription");
            int n4 = jSONObject.optInt("id", -1);
            String string3 = JSONUtils.optionalStringKey(jSONObject, "mp_id_name");
            String string4 = JSONUtils.optionalStringKey(jSONObject, "tag");
            if ("shortest".equals(object)) {
                n2 = 1;
            } else {
                if (object != null) {
                    MPLog.w("MixpanelAPI.EProtocol", "Unrecognized prefix type \"" + (String)object + "\". No views will be matched");
                    return NEVER_MATCH_PATH;
                }
                n2 = 0;
            }
            if ((object = this.reconcileIds(n4, string3, resourceIds)) == null) {
                return NEVER_MATCH_PATH;
            }
            arrayList.add(new Pathfinder.PathElement(n2, (String)((Object)list), n3, (Integer)object, string2, string4));
            ++n;
        } while (true);
    }

    public ViewSnapshot readSnapshotConfig(JSONObject object) throws BadInstructionsException {
        ArrayList<PropertyDescription> arrayList = new ArrayList<PropertyDescription>();
        object = object.getJSONObject("config").getJSONArray("classes");
        int n = 0;
        do {
            block9: {
                if (n >= object.length()) break block9;
                JSONObject jSONObject = object.getJSONObject(n);
                Class<?> class_ = Class.forName(jSONObject.getString("name"));
                jSONObject = jSONObject.getJSONArray("properties");
                int n2 = 0;
                do {
                    if (n2 < jSONObject.length()) {
                        arrayList.add(this.readPropertyDescription(class_, jSONObject.getJSONObject(n2)));
                        ++n2;
                        continue;
                    }
                    break;
                } while (true);
            }
            try {
                object = new ViewSnapshot(this.mContext, arrayList, this.mResourceIds);
                return object;
            }
            catch (JSONException jSONException) {
                throw new BadInstructionsException("Can't read snapshot configuration", jSONException);
            }
            catch (ClassNotFoundException classNotFoundException) {
                throw new BadInstructionsException("Can't resolve types for snapshot configuration", classNotFoundException);
            }
            ++n;
        } while (true);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public MPPair<String, Object> readTweak(JSONObject object) throws BadInstructionsException {
        String string2;
        String string3;
        try {
            string3 = object.getString("name");
            string2 = object.getString("type");
            if ("number".equals(string2)) {
                string2 = object.getString("encoding");
                if ("d".equals(string2)) {
                    object = object.getDouble("value");
                    return new MPPair<String, JSONObject>(string3, (JSONObject)object);
                }
                if (!"l".equals(string2)) throw new BadInstructionsException("number must have encoding of type \"l\" for long or \"d\" for double in: " + (Object)object);
                object = object.getLong("value");
                return new MPPair<String, JSONObject>(string3, (JSONObject)object);
            }
        }
        catch (JSONException jSONException) {
            throw new BadInstructionsException("Can't read tweak update", jSONException);
        }
        {
            if ("boolean".equals(string2)) {
                object = object.getBoolean("value");
                return new MPPair<String, JSONObject>(string3, (JSONObject)object);
            }
            if (!"string".equals(string2)) throw new BadInstructionsException("Unrecognized tweak type " + string2 + " in: " + (Object)object);
            object = object.getString("value");
            return new MPPair<String, JSONObject>(string3, (JSONObject)object);
        }
    }

    public static class BadInstructionsException
    extends Exception {
        public BadInstructionsException(String string2) {
            super(string2);
        }

        public BadInstructionsException(String string2, Throwable throwable) {
            super(string2, throwable);
        }
    }

    public static class CantGetEditAssetsException
    extends Exception {
        public CantGetEditAssetsException(String string2, Throwable throwable) {
            super(string2, throwable);
        }
    }

    public static class Edit {
        public final List<String> imageUrls;
        public final ViewVisitor visitor;

        private Edit(ViewVisitor viewVisitor, List<String> list) {
            this.visitor = viewVisitor;
            this.imageUrls = list;
        }
    }

    public static class InapplicableInstructionsException
    extends BadInstructionsException {
        public InapplicableInstructionsException(String string2) {
            super(string2);
        }
    }

}

