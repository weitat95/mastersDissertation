/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Type;
import java.util.ArrayList;

public final class ArrayTypeAdapter<E>
extends TypeAdapter<Object> {
    public static final TypeAdapterFactory FACTORY = new TypeAdapterFactory(){

        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> object) {
            if (!((object = ((TypeToken)object).getType()) instanceof GenericArrayType || object instanceof Class && ((Class)object).isArray())) {
                return null;
            }
            object = $Gson$Types.getArrayComponentType((Type)object);
            return new ArrayTypeAdapter(gson, gson.getAdapter(TypeToken.get((Type)object)), $Gson$Types.getRawType((Type)object));
        }
    };
    private final Class<E> componentType;
    private final TypeAdapter<E> componentTypeAdapter;

    public ArrayTypeAdapter(Gson gson, TypeAdapter<E> typeAdapter, Class<E> class_) {
        this.componentTypeAdapter = new TypeAdapterRuntimeTypeWrapper<E>(gson, typeAdapter, class_);
        this.componentType = class_;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Object read(JsonReader object) throws IOException {
        if (((JsonReader)object).peek() == JsonToken.NULL) {
            ((JsonReader)object).nextNull();
            return null;
        }
        ArrayList<E> arrayList = new ArrayList<E>();
        ((JsonReader)object).beginArray();
        while (((JsonReader)object).hasNext()) {
            arrayList.add(this.componentTypeAdapter.read((JsonReader)object));
        }
        ((JsonReader)object).endArray();
        Object object2 = Array.newInstance(this.componentType, arrayList.size());
        int n = 0;
        do {
            object = object2;
            if (n >= arrayList.size()) return object;
            Array.set(object2, n, arrayList.get(n));
            ++n;
        } while (true);
    }

    @Override
    public void write(JsonWriter jsonWriter, Object object) throws IOException {
        if (object == null) {
            jsonWriter.nullValue();
            return;
        }
        jsonWriter.beginArray();
        int n = Array.getLength(object);
        for (int i = 0; i < n; ++i) {
            Object object2 = Array.get(object, i);
            this.componentTypeAdapter.write(jsonWriter, object2);
        }
        jsonWriter.endArray();
    }

}

