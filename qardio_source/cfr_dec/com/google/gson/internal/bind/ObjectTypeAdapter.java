/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.util.ArrayList;

public final class ObjectTypeAdapter
extends TypeAdapter<Object> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            if (typeToken.getRawType() == Object.class) {
                return new ObjectTypeAdapter(gson);
            }
            return null;
        }
    };
    private final Gson gson;

    ObjectTypeAdapter(Gson gson) {
        this.gson = gson;
    }

    @Override
    public Object read(JsonReader jsonReader) throws IOException {
        Object object = jsonReader.peek();
        switch (2.$SwitchMap$com$google$gson$stream$JsonToken[((Enum)object).ordinal()]) {
            default: {
                throw new IllegalStateException();
            }
            case 1: {
                object = new ArrayList();
                jsonReader.beginArray();
                while (jsonReader.hasNext()) {
                    object.add(this.read(jsonReader));
                }
                jsonReader.endArray();
                return object;
            }
            case 2: {
                object = new LinkedTreeMap();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    object.put(jsonReader.nextName(), this.read(jsonReader));
                }
                jsonReader.endObject();
                return object;
            }
            case 3: {
                return jsonReader.nextString();
            }
            case 4: {
                return jsonReader.nextDouble();
            }
            case 5: {
                return jsonReader.nextBoolean();
            }
            case 6: 
        }
        jsonReader.nextNull();
        return null;
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        if (object == null) {
            jsonWriter.nullValue();
            return;
        }
        TypeAdapter<?> typeAdapter = this.gson.getAdapter(object.getClass());
        if (typeAdapter instanceof ObjectTypeAdapter) {
            jsonWriter.beginObject();
            jsonWriter.endObject();
            return;
        }
        typeAdapter.write(jsonWriter, object);
    }

}

