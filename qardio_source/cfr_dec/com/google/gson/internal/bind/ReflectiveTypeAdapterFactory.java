/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal.bind;

import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.JsonAdapter;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.TypeAdapterRuntimeTypeWrapper;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class ReflectiveTypeAdapterFactory
implements TypeAdapterFactory {
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final FieldNamingStrategy fieldNamingPolicy;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;

    public ReflectiveTypeAdapterFactory(ConstructorConstructor constructorConstructor, FieldNamingStrategy fieldNamingStrategy, Excluder excluder, JsonAdapterAnnotationTypeAdapterFactory jsonAdapterAnnotationTypeAdapterFactory) {
        this.constructorConstructor = constructorConstructor;
        this.fieldNamingPolicy = fieldNamingStrategy;
        this.excluder = excluder;
        this.jsonAdapterFactory = jsonAdapterAnnotationTypeAdapterFactory;
    }

    /*
     * Enabled aggressive block sorting
     */
    private BoundField createBoundField(Gson gson, final Field field, String string2, TypeToken<?> typeToken, boolean bl, boolean bl2) {
        boolean bl3 = Primitives.isPrimitive(typeToken.getRawType());
        Object object = field.getAnnotation(JsonAdapter.class);
        TypeAdapter<?> typeAdapter = null;
        if (object != null) {
            typeAdapter = this.jsonAdapterFactory.getTypeAdapter(this.constructorConstructor, gson, typeToken, (JsonAdapter)object);
        }
        final boolean bl4 = typeAdapter != null;
        object = typeAdapter;
        if (typeAdapter == null) {
            object = gson.getAdapter(typeToken);
        }
        return new BoundField(string2, bl, bl2, (TypeAdapter)object, gson, typeToken, bl3){
            final /* synthetic */ Gson val$context;
            final /* synthetic */ TypeToken val$fieldType;
            final /* synthetic */ boolean val$isPrimitive;
            final /* synthetic */ TypeAdapter val$typeAdapter;
            {
                this.val$typeAdapter = typeAdapter;
                this.val$context = gson;
                this.val$fieldType = typeToken;
                this.val$isPrimitive = bl42;
                super(string2, bl, bl2);
            }

            @Override
            void read(JsonReader jsonReader, Object object) throws IOException, IllegalAccessException {
                if ((jsonReader = this.val$typeAdapter.read(jsonReader)) != null || !this.val$isPrimitive) {
                    field.set(object, jsonReader);
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            void write(JsonWriter jsonWriter, Object typeAdapter) throws IOException, IllegalAccessException {
                Object object = field.get(typeAdapter);
                typeAdapter = bl4 ? this.val$typeAdapter : new TypeAdapterRuntimeTypeWrapper(this.val$context, this.val$typeAdapter, this.val$fieldType.getType());
                typeAdapter.write(jsonWriter, object);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public boolean writeField(Object object) throws IOException, IllegalAccessException {
                return this.serialized && field.get(object) != object;
            }
        };
    }

    static boolean excludeField(Field field, boolean bl, Excluder excluder) {
        return !excluder.excludeClass(field.getType(), bl) && !excluder.excludeField(field, bl);
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private Map<String, BoundField> getBoundFields(Gson gson, TypeToken<?> typeToken, Class<?> class_) {
        LinkedHashMap<String, BoundField> linkedHashMap = new LinkedHashMap<String, BoundField>();
        if (class_.isInterface()) {
            return linkedHashMap;
        }
        Type type = typeToken.getType();
        while (class_ != Object.class) {
            for (Field field : class_.getDeclaredFields()) {
                boolean bl = this.excludeField(field, true);
                boolean bl2 = this.excludeField(field, false);
                if (!bl && !bl2) continue;
                field.setAccessible(true);
                Type type2 = $Gson$Types.resolve(typeToken.getType(), class_, field.getGenericType());
                List<String> list = this.getFieldNames(field);
                Object object = null;
                for (int i = 0; i < list.size(); ++i) {
                    Object object2 = list.get(i);
                    if (i != 0) {
                        bl = false;
                    }
                    BoundField boundField = linkedHashMap.put((String)object2, this.createBoundField(gson, field, (String)object2, TypeToken.get(type2), bl, bl2));
                    object2 = object;
                    if (object == null) {
                        object2 = boundField;
                    }
                    object = object2;
                }
                if (object == null) continue;
                throw new IllegalArgumentException(type + " declares multiple JSON fields named " + ((BoundField)object).name);
            }
            typeToken = TypeToken.get($Gson$Types.resolve(typeToken.getType(), class_, class_.getGenericSuperclass()));
            class_ = typeToken.getRawType();
        }
        return linkedHashMap;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private List<String> getFieldNames(Field object) {
        Object object2 = ((Field)object).getAnnotation(SerializedName.class);
        if (object2 == null) {
            return Collections.singletonList(this.fieldNamingPolicy.translateName((Field)object));
        }
        object = object2.value();
        String[] arrstring = object2.alternate();
        if (arrstring.length == 0) {
            return Collections.singletonList(object);
        }
        object2 = new ArrayList(arrstring.length + 1);
        object2.add(object);
        int n = arrstring.length;
        int n2 = 0;
        do {
            object = object2;
            if (n2 >= n) return object;
            object2.add(arrstring[n2]);
            ++n2;
        } while (true);
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Class<T> class_ = typeToken.getRawType();
        if (!Object.class.isAssignableFrom(class_)) {
            return null;
        }
        return new Adapter<T>(this.constructorConstructor.get(typeToken), this.getBoundFields(gson, typeToken, class_));
    }

    public boolean excludeField(Field field, boolean bl) {
        return ReflectiveTypeAdapterFactory.excludeField(field, bl, this.excluder);
    }

    public static final class Adapter<T>
    extends TypeAdapter<T> {
        private final Map<String, BoundField> boundFields;
        private final ObjectConstructor<T> constructor;

        Adapter(ObjectConstructor<T> objectConstructor, Map<String, BoundField> map) {
            this.constructor = objectConstructor;
            this.boundFields = map;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public T read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            T t = this.constructor.construct();
            try {
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    Object object = jsonReader.nextName();
                    if ((object = this.boundFields.get(object)) == null || !((BoundField)object).deserialized) {
                        jsonReader.skipValue();
                        continue;
                    }
                    ((BoundField)object).read(jsonReader, t);
                }
            }
            catch (IllegalStateException illegalStateException) {
                throw new JsonSyntaxException(illegalStateException);
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError(illegalAccessException);
            }
            jsonReader.endObject();
            return t;
        }

        @Override
        public void write(JsonWriter jsonWriter, T t) throws IOException {
            if (t == null) {
                jsonWriter.nullValue();
                return;
            }
            jsonWriter.beginObject();
            try {
                for (BoundField boundField : this.boundFields.values()) {
                    if (!boundField.writeField(t)) continue;
                    jsonWriter.name(boundField.name);
                    boundField.write(jsonWriter, t);
                }
            }
            catch (IllegalAccessException illegalAccessException) {
                throw new AssertionError(illegalAccessException);
            }
            jsonWriter.endObject();
        }
    }

    static abstract class BoundField {
        final boolean deserialized;
        final String name;
        final boolean serialized;

        protected BoundField(String string2, boolean bl, boolean bl2) {
            this.name = string2;
            this.serialized = bl;
            this.deserialized = bl2;
        }

        abstract void read(JsonReader var1, Object var2) throws IOException, IllegalAccessException;

        abstract void write(JsonWriter var1, Object var2) throws IOException, IllegalAccessException;

        abstract boolean writeField(Object var1) throws IOException, IllegalAccessException;
    }

}

