/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.bind.util.ISO8601Utils;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.Date;
import java.util.Locale;

public final class DateTypeAdapter
extends TypeAdapter<Date> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Date.class) {
                return new DateTypeAdapter();
            }
            return null;
        }
    };
    private final DateFormat enUsFormat = DateFormat.getDateTimeInstance(2, 2, Locale.US);
    private final DateFormat localFormat = DateFormat.getDateTimeInstance(2, 2);

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Date deserializeToDate(String object) {
        synchronized (this) {
            try {
                Date date = this.localFormat.parse((String)object);
                return date;
            }
            catch (ParseException parseException) {
                try {
                    Date date = this.enUsFormat.parse((String)object);
                    return date;
                }
                catch (ParseException parseException2) {
                    try {
                        Date date = ISO8601Utils.parse((String)object, new ParsePosition(0));
                        return date;
                    }
                    catch (ParseException parseException3) {
                        throw new JsonSyntaxException((String)object, parseException3);
                    }
                }
            }
        }
    }

    @Override
    public Date read(JsonReader jsonReader) throws IOException {
        if (jsonReader.peek() == JsonToken.NULL) {
            jsonReader.nextNull();
            return null;
        }
        return this.deserializeToDate(jsonReader.nextString());
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void write(JsonWriter jsonWriter, Date date) throws IOException {
        synchronized (this) {
            void var2_2;
            if (var2_2 == null) {
                jsonWriter.nullValue();
            } else {
                jsonWriter.value(this.enUsFormat.format((Date)var2_2));
            }
            return;
        }
    }

}

