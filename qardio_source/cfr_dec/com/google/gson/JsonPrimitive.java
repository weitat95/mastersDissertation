/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.internal.LazilyParsedNumber;
import java.math.BigInteger;

public final class JsonPrimitive
extends JsonElement {
    private static final Class<?>[] PRIMITIVE_TYPES = new Class[]{Integer.TYPE, Long.TYPE, Short.TYPE, Float.TYPE, Double.TYPE, Byte.TYPE, Boolean.TYPE, Character.TYPE, Integer.class, Long.class, Short.class, Float.class, Double.class, Byte.class, Boolean.class, Character.class};
    private Object value;

    public JsonPrimitive(Boolean bl) {
        this.setValue(bl);
    }

    public JsonPrimitive(Number number) {
        this.setValue(number);
    }

    public JsonPrimitive(String string2) {
        this.setValue(string2);
    }

    private static boolean isIntegral(JsonPrimitive object) {
        boolean bl;
        block2: {
            block3: {
                boolean bl2;
                bl = bl2 = false;
                if (!(((JsonPrimitive)object).value instanceof Number)) break block2;
                object = (Number)((JsonPrimitive)object).value;
                if (object instanceof BigInteger || object instanceof Long || object instanceof Integer || object instanceof Short) break block3;
                bl = bl2;
                if (!(object instanceof Byte)) break block2;
            }
            bl = true;
        }
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    private static boolean isPrimitiveOrString(Object class_) {
        if (!(class_ instanceof String)) {
            class_ = class_.getClass();
            Class<?>[] arrclass = PRIMITIVE_TYPES;
            int n = arrclass.length;
            int n2 = 0;
            do {
                if (n2 >= n) {
                    return false;
                }
                if (arrclass[n2].isAssignableFrom(class_)) break;
                ++n2;
            } while (true);
        }
        return true;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean equals(Object object) {
        double d;
        boolean bl = false;
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (JsonPrimitive)object;
        if (this.value == null) {
            if (((JsonPrimitive)object).value == null) return true;
            return false;
        }
        if (JsonPrimitive.isIntegral(this) && JsonPrimitive.isIntegral((JsonPrimitive)object)) {
            if (this.getAsNumber().longValue() == ((JsonPrimitive)object).getAsNumber().longValue()) return true;
            return false;
        }
        if (!(this.value instanceof Number)) return this.value.equals(((JsonPrimitive)object).value);
        if (!(((JsonPrimitive)object).value instanceof Number)) return this.value.equals(((JsonPrimitive)object).value);
        double d2 = this.getAsNumber().doubleValue();
        if (d2 == (d = ((JsonPrimitive)object).getAsNumber().doubleValue())) return true;
        boolean bl2 = bl;
        if (!Double.isNaN(d2)) return bl2;
        bl2 = bl;
        if (!Double.isNaN(d)) return bl2;
        return true;
    }

    @Override
    public boolean getAsBoolean() {
        if (this.isBoolean()) {
            return this.getAsBooleanWrapper();
        }
        return Boolean.parseBoolean(this.getAsString());
    }

    @Override
    Boolean getAsBooleanWrapper() {
        return (Boolean)this.value;
    }

    @Override
    public double getAsDouble() {
        if (this.isNumber()) {
            return this.getAsNumber().doubleValue();
        }
        return Double.parseDouble(this.getAsString());
    }

    @Override
    public int getAsInt() {
        if (this.isNumber()) {
            return this.getAsNumber().intValue();
        }
        return Integer.parseInt(this.getAsString());
    }

    @Override
    public long getAsLong() {
        if (this.isNumber()) {
            return this.getAsNumber().longValue();
        }
        return Long.parseLong(this.getAsString());
    }

    @Override
    public Number getAsNumber() {
        if (this.value instanceof String) {
            return new LazilyParsedNumber((String)this.value);
        }
        return (Number)this.value;
    }

    @Override
    public String getAsString() {
        if (this.isNumber()) {
            return this.getAsNumber().toString();
        }
        if (this.isBoolean()) {
            return this.getAsBooleanWrapper().toString();
        }
        return (String)this.value;
    }

    public int hashCode() {
        if (this.value == null) {
            return 31;
        }
        if (JsonPrimitive.isIntegral(this)) {
            long l = this.getAsNumber().longValue();
            return (int)(l >>> 32 ^ l);
        }
        if (this.value instanceof Number) {
            long l = Double.doubleToLongBits(this.getAsNumber().doubleValue());
            return (int)(l >>> 32 ^ l);
        }
        return this.value.hashCode();
    }

    public boolean isBoolean() {
        return this.value instanceof Boolean;
    }

    public boolean isNumber() {
        return this.value instanceof Number;
    }

    public boolean isString() {
        return this.value instanceof String;
    }

    /*
     * Enabled aggressive block sorting
     */
    void setValue(Object object) {
        if (object instanceof Character) {
            this.value = String.valueOf(((Character)object).charValue());
            return;
        }
        boolean bl = object instanceof Number || JsonPrimitive.isPrimitiveOrString(object);
        $Gson$Preconditions.checkArgument(bl);
        this.value = object;
    }
}

