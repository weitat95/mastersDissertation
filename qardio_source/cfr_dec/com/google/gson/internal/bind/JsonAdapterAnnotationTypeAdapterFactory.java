/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import java.lang.annotation.Annotation;

public final class JsonAdapterAnnotationTypeAdapterFactory
implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;

    public JsonAdapterAnnotationTypeAdapterFactory(ConstructorConstructor constructorConstructor) {
        this.constructorConstructor = constructorConstructor;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        JsonAdapter jsonAdapter = typeToken.getRawType().getAnnotation(JsonAdapter.class);
        if (jsonAdapter == null) {
            return null;
        }
        return this.getTypeAdapter(this.constructorConstructor, gson, typeToken, jsonAdapter);
    }

    /*
     * Enabled aggressive block sorting
     */
    TypeAdapter<?> getTypeAdapter(ConstructorConstructor object, Gson typeAdapter, TypeToken<?> typeToken, JsonAdapter jsonDeserializer) {
        if ((jsonDeserializer = ((ConstructorConstructor)object).get(TypeToken.get(jsonDeserializer.value())).construct()) instanceof TypeAdapter) {
            object = (TypeAdapter)((Object)jsonDeserializer);
        } else if (jsonDeserializer instanceof TypeAdapterFactory) {
            object = ((TypeAdapterFactory)((Object)jsonDeserializer)).create((Gson)((Object)typeAdapter), typeToken);
        } else {
            if (!(jsonDeserializer instanceof JsonSerializer) && !(jsonDeserializer instanceof JsonDeserializer)) {
                throw new IllegalArgumentException("@JsonAdapter value must be TypeAdapter, TypeAdapterFactory, JsonSerializer or JsonDeserializer reference.");
            }
            object = jsonDeserializer instanceof JsonSerializer ? (JsonSerializer)((Object)jsonDeserializer) : null;
            jsonDeserializer = jsonDeserializer instanceof JsonDeserializer ? (JsonDeserializer)jsonDeserializer : null;
            object = new TreeTypeAdapter((JsonSerializer<?>)object, jsonDeserializer, (Gson)((Object)typeAdapter), typeToken, null);
        }
        typeAdapter = object;
        if (object == null) return typeAdapter;
        return ((TypeAdapter)object).nullSafe();
    }
}

