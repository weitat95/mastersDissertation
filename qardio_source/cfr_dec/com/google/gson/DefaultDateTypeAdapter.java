/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.bind.util.ISO8601Utils;
import java.lang.reflect.Type;
import java.sql.Date;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Locale;

final class DefaultDateTypeAdapter
implements JsonDeserializer<java.util.Date>,
JsonSerializer<java.util.Date> {
    private final DateFormat enUsFormat;
    private final DateFormat localFormat;

    DefaultDateTypeAdapter() {
        this(DateFormat.getDateTimeInstance(2, 2, Locale.US), DateFormat.getDateTimeInstance(2, 2));
    }

    public DefaultDateTypeAdapter(int n, int n2) {
        this(DateFormat.getDateTimeInstance(n, n2, Locale.US), DateFormat.getDateTimeInstance(n, n2));
    }

    DefaultDateTypeAdapter(String string2) {
        this(new SimpleDateFormat(string2, Locale.US), new SimpleDateFormat(string2));
    }

    DefaultDateTypeAdapter(DateFormat dateFormat, DateFormat dateFormat2) {
        this.enUsFormat = dateFormat;
        this.localFormat = dateFormat2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private java.util.Date deserializeToDate(JsonElement jsonElement) {
        DateFormat dateFormat = this.localFormat;
        synchronized (dateFormat) {
            try {
                return this.localFormat.parse(jsonElement.getAsString());
            }
            catch (ParseException parseException) {
                try {
                    return this.enUsFormat.parse(jsonElement.getAsString());
                }
                catch (ParseException parseException2) {
                    try {
                        return ISO8601Utils.parse(jsonElement.getAsString(), new ParsePosition(0));
                    }
                    catch (ParseException parseException3) {
                        throw new JsonSyntaxException(jsonElement.getAsString(), parseException3);
                    }
                }
            }
        }
    }

    @Override
    public java.util.Date deserialize(JsonElement object, Type type, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        if (!(object instanceof JsonPrimitive)) {
            throw new JsonParseException("The date should be a string value");
        }
        object = this.deserializeToDate((JsonElement)object);
        if (type == java.util.Date.class) {
            return object;
        }
        if (type == Timestamp.class) {
            return new Timestamp(((java.util.Date)object).getTime());
        }
        if (type == Date.class) {
            return new Date(((java.util.Date)object).getTime());
        }
        throw new IllegalArgumentException(this.getClass() + " cannot deserialize to " + type);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public JsonElement serialize(java.util.Date object, Type object2, JsonSerializationContext jsonSerializationContext) {
        object2 = this.localFormat;
        synchronized (object2) {
            return new JsonPrimitive(this.enUsFormat.format((java.util.Date)object));
        }
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(DefaultDateTypeAdapter.class.getSimpleName());
        stringBuilder.append('(').append(this.localFormat.getClass().getSimpleName()).append(')');
        return stringBuilder.toString();
    }
}

