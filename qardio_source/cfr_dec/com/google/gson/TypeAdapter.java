/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.internal.bind.JsonTreeWriter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;

public abstract class TypeAdapter<T> {
    public final TypeAdapter<T> nullSafe() {
        return new TypeAdapter<T>(){

            @Override
            public T read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return TypeAdapter.this.read(jsonReader);
            }

            @Override
            public void write(JsonWriter jsonWriter, T t) throws IOException {
                if (t == null) {
                    jsonWriter.nullValue();
                    return;
                }
                TypeAdapter.this.write(jsonWriter, t);
            }
        };
    }

    public abstract T read(JsonReader var1) throws IOException;

    public final JsonElement toJsonTree(T object) {
        try {
            JsonTreeWriter jsonTreeWriter = new JsonTreeWriter();
            this.write(jsonTreeWriter, object);
            object = jsonTreeWriter.get();
            return object;
        }
        catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
    }

    public abstract void write(JsonWriter var1, T var2) throws IOException;

}

