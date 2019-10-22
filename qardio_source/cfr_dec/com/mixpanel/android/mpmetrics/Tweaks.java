/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import com.mixpanel.android.mpmetrics.Tweak;
import com.mixpanel.android.util.MPLog;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweaks {
    private final List<OnTweakDeclaredListener> mTweakDeclaredListeners;
    private final ConcurrentMap<String, TweakValue> mTweakDefaultValues;
    private final ConcurrentMap<String, TweakValue> mTweakValues = new ConcurrentHashMap<String, TweakValue>();

    Tweaks() {
        this.mTweakDefaultValues = new ConcurrentHashMap<String, TweakValue>();
        this.mTweakDeclaredListeners = new ArrayList<OnTweakDeclaredListener>();
    }

    private TweakValue getValue(String object) {
        synchronized (this) {
            object = (TweakValue)this.mTweakValues.get(object);
            return object;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void addOnTweakDeclaredListener(OnTweakDeclaredListener onTweakDeclaredListener) {
        synchronized (this) {
            if (onTweakDeclaredListener == null) {
                throw new NullPointerException("listener cannot be null");
            }
            this.mTweakDeclaredListeners.add(onTweakDeclaredListener);
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void declareTweak(String string2, Object object, Number number, Number number2, int n) {
        if (this.mTweakValues.containsKey(string2)) {
            MPLog.w("MixpanelAPI.Tweaks", "Attempt to define a tweak \"" + string2 + "\" twice with the same name");
            return;
        } else {
            object = new TweakValue(n, object, number, number2, object, string2);
            this.mTweakValues.put(string2, (TweakValue)object);
            this.mTweakDefaultValues.put(string2, (TweakValue)object);
            int n2 = this.mTweakDeclaredListeners.size();
            for (n = 0; n < n2; ++n) {
                this.mTweakDeclaredListeners.get(n).onTweakDeclared();
            }
        }
    }

    public Map<String, TweakValue> getAllValues() {
        synchronized (this) {
            HashMap<String, TweakValue> hashMap = new HashMap<String, TweakValue>(this.mTweakValues);
            return hashMap;
        }
    }

    public Map<String, TweakValue> getDefaultValues() {
        synchronized (this) {
            HashMap<String, TweakValue> hashMap = new HashMap<String, TweakValue>(this.mTweakDefaultValues);
            return hashMap;
        }
    }

    Tweak<Integer> intTweak(final String string2, int n) {
        this.declareTweak(string2, n, null, null, 3);
        return new Tweak<Integer>(){

            @Override
            public Integer get() {
                return Tweaks.this.getValue(string2).getNumberValue().intValue();
            }
        };
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isNewValue(String string2, Object object) {
        boolean bl = false;
        synchronized (this) {
            block4: {
                if (this.mTweakValues.containsKey(string2)) break block4;
                MPLog.w("MixpanelAPI.Tweaks", "Attempt to reference a tweak \"" + string2 + "\" which has never been defined.");
                return bl;
            }
            boolean bl2 = ((TweakValue)this.mTweakValues.get(string2)).value.equals(object);
            if (bl2) return bl;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void set(String string2, Object object) {
        synchronized (this) {
            if (!this.mTweakValues.containsKey(string2)) {
                MPLog.w("MixpanelAPI.Tweaks", "Attempt to set a tweak \"" + string2 + "\" which has never been defined.");
            } else {
                TweakValue tweakValue;
                tweakValue = ((TweakValue)this.mTweakValues.get(string2)).updateValue(tweakValue);
                this.mTweakValues.put(string2, tweakValue);
            }
            return;
        }
    }

    Tweak<String> stringTweak(final String string2, String string3) {
        this.declareTweak(string2, string3, null, null, 4);
        return new Tweak<String>(){

            @Override
            public String get() {
                return Tweaks.this.getValue(string2).getStringValue();
            }
        };
    }

    public static interface OnTweakDeclaredListener {
        public void onTweakDeclared();
    }

    public static class TweakValue {
        private final Object defaultValue;
        private final Number maximum;
        private final Number minimum;
        private final String name;
        public final int type;
        private final Object value;

        private TweakValue(int n, Object object, Number object2, Number object3, Object object4, String object5) {
            this.type = n;
            this.name = object5;
            this.minimum = object2;
            this.maximum = object3;
            object3 = object;
            object5 = object4;
            if (this.minimum != null) {
                object3 = object;
                object5 = object4;
                if (this.maximum != null) {
                    object2 = object;
                    if (!this.isBetweenBounds(object)) {
                        object2 = Math.min(Math.max(((Number)object).longValue(), this.minimum.longValue()), this.maximum.longValue());
                        MPLog.w("MixpanelAPI.Tweaks", "Attempt to define a tweak \"" + this.name + "\" with default value " + object2 + " out of its bounds [" + this.minimum + ", " + this.maximum + "]Tweak \"" + this.name + "\" new default value: " + object2 + ".");
                    }
                    object3 = object2;
                    object5 = object4;
                    if (!this.isBetweenBounds(object4)) {
                        object5 = Math.min(Math.max(((Number)object4).longValue(), this.minimum.longValue()), this.maximum.longValue());
                        MPLog.w("MixpanelAPI.Tweaks", "Attempt to define a tweak \"" + this.name + "\" with value " + object2 + " out of its bounds [" + this.minimum + ", " + this.maximum + "]Tweak \"" + this.name + "\" new value: " + object5 + ".");
                        object3 = object2;
                    }
                }
            }
            this.defaultValue = object3;
            this.value = object5;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public static TweakValue fromJson(JSONObject object) {
            Object object2;
            String string2;
            Object object3;
            int n;
            Object object4;
            Object object5;
            block10: {
                try {
                    string2 = object.getString("name");
                    Object object6 = object.getString("type");
                    object5 = null;
                    object3 = null;
                    Number number = null;
                    object2 = null;
                    object4 = null;
                    if ("number".equals(object6)) {
                        object5 = object.getString("encoding");
                        if ("d".equals(object5)) {
                            int n2 = 2;
                            object6 = object.getDouble("value");
                            Double d = object.getDouble("default");
                            if (!object.isNull("minimum")) {
                                number = object.getDouble("minimum");
                            }
                            n = n2;
                            object5 = number;
                            object3 = d;
                            object4 = object6;
                            if (!object.isNull("maximum")) {
                                object2 = object.getDouble("maximum");
                                n = n2;
                                object5 = number;
                                object3 = d;
                                object4 = object6;
                            }
                        } else {
                            if (!"l".equals(object5)) return null;
                            object6 = object.getLong("value");
                            int n3 = 3;
                            Long l = object.getLong("default");
                            number = object3;
                            if (!object.isNull("minimum")) {
                                number = object.getLong("minimum");
                            }
                            n = n3;
                            object5 = number;
                            object3 = l;
                            object4 = object6;
                            if (!object.isNull("maximum")) {
                                object2 = object.getLong("maximum");
                                n = n3;
                                object5 = number;
                                object3 = l;
                                object4 = object6;
                            }
                        }
                        break block10;
                    }
                    if ("boolean".equals(object6)) {
                        n = 1;
                        boolean bl = object.getBoolean("value");
                        object = object.getBoolean("default");
                        object2 = bl;
                        return new TweakValue(n, object, (Number)object5, (Number)object4, object2, string2);
                    }
                    if (!"string".equals(object6)) return null;
                    n = 4;
                    object2 = object.getString("value");
                    object = object.getString("default");
                    return new TweakValue(n, object, (Number)object5, (Number)object4, object2, string2);
                }
                catch (JSONException jSONException) {
                    return null;
                }
            }
            object = object3;
            object3 = object4;
            object4 = object2;
            object2 = object3;
            return new TweakValue(n, object, (Number)object5, (Number)object4, object2, string2);
        }

        private boolean isBetweenBounds(Object object) {
            try {
                long l;
                long l2;
                object = (Number)object;
                if (Math.min(Math.max(((Number)object).longValue(), this.minimum.longValue()), this.maximum.longValue()) == this.minimum.longValue() || (l = Math.min(Math.max(((Number)object).longValue(), this.minimum.longValue()), this.maximum.longValue())) == (l2 = this.maximum.longValue())) {
                    return false;
                }
            }
            catch (ClassCastException classCastException) {
                // empty catch block
            }
            return true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Boolean getBooleanValue() {
            Boolean bl;
            Boolean bl2 = bl = Boolean.valueOf(false);
            if (this.defaultValue != null) {
                try {
                    bl2 = (Boolean)this.defaultValue;
                }
                catch (ClassCastException classCastException) {
                    bl2 = bl;
                }
            }
            bl = bl2;
            if (this.value == null) return bl;
            try {
                return (Boolean)this.value;
            }
            catch (ClassCastException classCastException) {
                return bl2;
            }
        }

        public Object getDefaultValue() {
            return this.defaultValue;
        }

        public Number getMaximum() {
            return this.maximum;
        }

        public Number getMinimum() {
            return this.minimum;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        public Number getNumberValue() {
            void var1_8;
            Integer n;
            void var2_4;
            Integer n2 = n = Integer.valueOf(0);
            if (this.defaultValue != null) {
                try {
                    Number number = (Number)this.defaultValue;
                }
                catch (ClassCastException classCastException) {
                    Integer n3 = n;
                }
            }
            void var2_2 = var1_8;
            if (this.value == null) return var2_4;
            try {
                Number number = (Number)this.value;
                return var2_4;
            }
            catch (ClassCastException classCastException) {
                return var1_8;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public String getStringValue() {
            String string2 = null;
            try {
                String string3;
                string2 = string3 = (String)this.defaultValue;
            }
            catch (ClassCastException classCastException) {}
            try {
                return (String)this.value;
            }
            catch (ClassCastException classCastException) {
                return string2;
            }
        }

        public Object getValue() {
            return this.value;
        }

        public TweakValue updateValue(Object object) {
            return new TweakValue(this.type, this.defaultValue, this.minimum, this.maximum, object, this.name);
        }
    }

}

