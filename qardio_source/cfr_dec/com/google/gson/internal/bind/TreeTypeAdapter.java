/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  com.google.gson.internal.bind.TreeTypeAdapter.com.google.gson.internal.bind.TreeTypeAdapter
 *  com.google.gson.internal.bind.TreeTypeAdapter.com.google.gson.internal.bind.TreeTypeAdapter$GsonContextImpl
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Preconditions;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.TreeTypeAdapter.com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.reflect.Type;

public final class TreeTypeAdapter<T>
extends TypeAdapter<T> {
    private final com.google.gson.internal.bind.TreeTypeAdapter$GsonContextImpl context = new GsonContextImpl();
    private TypeAdapter<T> delegate;
    private final JsonDeserializer<T> deserializer;
    private final Gson gson;
    private final JsonSerializer<T> serializer;
    private final TypeAdapterFactory skipPast;
    private final TypeToken<T> typeToken;

    public TreeTypeAdapter(JsonSerializer<T> jsonSerializer, JsonDeserializer<T> jsonDeserializer, Gson gson, TypeToken<T> typeToken, TypeAdapterFactory typeAdapterFactory) {
        this.serializer = jsonSerializer;
        this.deserializer = jsonDeserializer;
        this.gson = gson;
        this.typeToken = typeToken;
        this.skipPast = typeAdapterFactory;
    }

    private TypeAdapter<T> delegate() {
        TypeAdapter<T> typeAdapter = this.delegate;
        if (typeAdapter != null) {
            return typeAdapter;
        }
        this.delegate = typeAdapter = this.gson.getDelegateAdapter(this.skipPast, this.typeToken);
        return typeAdapter;
    }

    public static TypeAdapterFactory newFactory(TypeToken<?> typeToken, Object object) {
        return new SingleTypeFactory(object, typeToken, false, null);
    }

    @Override
    public T read(JsonReader object) throws IOException {
        if (this.deserializer == null) {
            return this.delegate().read((JsonReader)object);
        }
        if (((JsonElement)(object = Streams.parse((JsonReader)object))).isJsonNull()) {
            return null;
        }
        return this.deserializer.deserialize((JsonElement)object, this.typeToken.getType(), (JsonDeserializationContext)this.context);
    }

    @Override
    public void write(JsonWriter jsonWriter, T t) throws IOException {
        if (this.serializer == null) {
            this.delegate().write(jsonWriter, t);
            return;
        }
        if (t == null) {
            jsonWriter.nullValue();
            return;
        }
        Streams.write(this.serializer.serialize(t, this.typeToken.getType(), (JsonSerializationContext)this.context), jsonWriter);
    }

    private final class GsonContextImpl
    implements JsonDeserializationContext,
    JsonSerializationContext {
        private GsonContextImpl() {
        }
    }

    private static final class SingleTypeFactory
    implements TypeAdapterFactory {
        private final JsonDeserializer<?> deserializer;
        private final TypeToken<?> exactType;
        private final Class<?> hierarchyType;
        private final boolean matchRawType;
        private final JsonSerializer<?> serializer;

        /*
         * Enabled aggressive block sorting
         */
        SingleTypeFactory(Object jsonDeserializer, TypeToken<?> typeToken, boolean bl, Class<?> class_) {
            JsonSerializer jsonSerializer = jsonDeserializer instanceof JsonSerializer ? (JsonSerializer)((Object)jsonDeserializer) : null;
            this.serializer = jsonSerializer;
            jsonDeserializer = jsonDeserializer instanceof JsonDeserializer ? (JsonDeserializer)jsonDeserializer : null;
            this.deserializer = jsonDeserializer;
            boolean bl2 = this.serializer != null || this.deserializer != null;
            $Gson$Preconditions.checkArgument(bl2);
            this.exactType = typeToken;
            this.matchRawType = bl;
            this.hierarchyType = class_;
        }

        /*
         * Enabled aggressive block sorting
         */
        @Override
        public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
            boolean bl;
            if (this.exactType != null) {
                if (!this.exactType.equals(typeToken)) {
                    if (!this.matchRawType) return null;
                    if (this.exactType.getType() != typeToken.getRawType()) return null;
                }
                bl = true;
            } else {
                bl = this.hierarchyType.isAssignableFrom(typeToken.getRawType());
            }
            if (!bl) return null;
            return new TreeTypeAdapter(this.serializer, this.deserializer, gson, typeToken, this);
        }
    }

}

