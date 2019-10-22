/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.JsonReader
 *  android.util.JsonToken
 *  android.util.JsonWriter
 */
package com.segment.analytics;

import android.util.JsonReader;
import android.util.JsonToken;
import android.util.JsonWriter;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class Cartographer {
    static final Cartographer INSTANCE = new Builder().lenient(true).prettyPrint(false).build();
    private final boolean isLenient;
    private final boolean prettyPrint;

    Cartographer(boolean bl, boolean bl2) {
        this.isLenient = bl;
        this.prettyPrint = bl2;
    }

    private static void arrayToWriter(Object object, JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginArray();
        int n = Array.getLength(object);
        for (int i = 0; i < n; ++i) {
            Cartographer.writeValue(Array.get(object, i), jsonWriter);
        }
        jsonWriter.endArray();
    }

    private static void listToWriter(List<?> object, JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginArray();
        object = object.iterator();
        while (object.hasNext()) {
            Cartographer.writeValue(object.next(), jsonWriter);
        }
        jsonWriter.endArray();
    }

    private static void mapToWriter(Map<?, ?> object, JsonWriter jsonWriter) throws IOException {
        jsonWriter.beginObject();
        for (Map.Entry entry : object.entrySet()) {
            jsonWriter.name(String.valueOf(entry.getKey()));
            Cartographer.writeValue(entry.getValue(), jsonWriter);
        }
        jsonWriter.endObject();
    }

    private static Object readValue(JsonReader jsonReader) throws IOException {
        JsonToken jsonToken = jsonReader.peek();
        switch (1.$SwitchMap$android$util$JsonToken[jsonToken.ordinal()]) {
            default: {
                throw new IllegalStateException("Invalid token " + (Object)jsonToken);
            }
            case 1: {
                return Cartographer.readerToMap(jsonReader);
            }
            case 2: {
                return Cartographer.readerToList(jsonReader);
            }
            case 3: {
                return jsonReader.nextBoolean();
            }
            case 4: {
                jsonReader.nextNull();
                return null;
            }
            case 5: {
                return jsonReader.nextDouble();
            }
            case 6: 
        }
        return jsonReader.nextString();
    }

    private static List<Object> readerToList(JsonReader jsonReader) throws IOException {
        ArrayList<Object> arrayList = new ArrayList<Object>();
        jsonReader.beginArray();
        while (jsonReader.hasNext()) {
            arrayList.add(Cartographer.readValue(jsonReader));
        }
        jsonReader.endArray();
        return arrayList;
    }

    private static Map<String, Object> readerToMap(JsonReader jsonReader) throws IOException {
        LinkedHashMap<String, Object> linkedHashMap = new LinkedHashMap<String, Object>();
        jsonReader.beginObject();
        while (jsonReader.hasNext()) {
            linkedHashMap.put(jsonReader.nextName(), Cartographer.readValue(jsonReader));
        }
        jsonReader.endObject();
        return linkedHashMap;
    }

    private static void writeValue(Object object, JsonWriter jsonWriter) throws IOException {
        if (object == null) {
            jsonWriter.nullValue();
            return;
        }
        if (object instanceof Number) {
            jsonWriter.value((Number)object);
            return;
        }
        if (object instanceof Boolean) {
            jsonWriter.value(((Boolean)object).booleanValue());
            return;
        }
        if (object instanceof List) {
            Cartographer.listToWriter((List)object, jsonWriter);
            return;
        }
        if (object instanceof Map) {
            Cartographer.mapToWriter((Map)object, jsonWriter);
            return;
        }
        if (object.getClass().isArray()) {
            Cartographer.arrayToWriter(object, jsonWriter);
            return;
        }
        jsonWriter.value(String.valueOf(object));
    }

    public Map<String, Object> fromJson(Reader reader) throws IOException {
        if (reader == null) {
            throw new IllegalArgumentException("reader == null");
        }
        Object object = new JsonReader(reader);
        object.setLenient(this.isLenient);
        try {
            object = Cartographer.readerToMap(object);
            return object;
        }
        finally {
            reader.close();
        }
    }

    public Map<String, Object> fromJson(String string2) throws IOException {
        if (string2 == null) {
            throw new IllegalArgumentException("json == null");
        }
        if (string2.length() == 0) {
            throw new IllegalArgumentException("json empty");
        }
        return this.fromJson(new StringReader(string2));
    }

    public String toJson(Map<?, ?> map) {
        StringWriter stringWriter = new StringWriter();
        try {
            this.toJson(map, stringWriter);
            return stringWriter.toString();
        }
        catch (IOException iOException) {
            throw new AssertionError(iOException);
        }
    }

    public void toJson(Map<?, ?> map, Writer writer) throws IOException {
        if (map == null) {
            throw new IllegalArgumentException("map == null");
        }
        if (writer == null) {
            throw new IllegalArgumentException("writer == null");
        }
        writer = new JsonWriter(writer);
        writer.setLenient(this.isLenient);
        if (this.prettyPrint) {
            writer.setIndent("  ");
        }
        try {
            Cartographer.mapToWriter(map, (JsonWriter)writer);
            return;
        }
        finally {
            writer.close();
        }
    }

    public static class Builder {
        private boolean isLenient;
        private boolean prettyPrint;

        public Cartographer build() {
            return new Cartographer(this.isLenient, this.prettyPrint);
        }

        public Builder lenient(boolean bl) {
            this.isLenient = bl;
            return this;
        }

        public Builder prettyPrint(boolean bl) {
            this.prettyPrint = bl;
            return this;
        }
    }

}

