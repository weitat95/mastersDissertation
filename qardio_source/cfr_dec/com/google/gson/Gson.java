/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.InstanceCreator;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonSyntaxException;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.Primitives;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.ArrayTypeAdapter;
import com.google.gson.internal.bind.CollectionTypeAdapterFactory;
import com.google.gson.internal.bind.DateTypeAdapter;
import com.google.gson.internal.bind.JsonAdapterAnnotationTypeAdapterFactory;
import com.google.gson.internal.bind.JsonTreeReader;
import com.google.gson.internal.bind.MapTypeAdapterFactory;
import com.google.gson.internal.bind.ObjectTypeAdapter;
import com.google.gson.internal.bind.ReflectiveTypeAdapterFactory;
import com.google.gson.internal.bind.SqlDateTypeAdapter;
import com.google.gson.internal.bind.TimeTypeAdapter;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;
import java.io.Closeable;
import java.io.EOFException;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.concurrent.atomic.AtomicLongArray;

public final class Gson {
    private static final TypeToken<?> NULL_KEY_SURROGATE = new TypeToken<Object>(){};
    private final ThreadLocal<Map<TypeToken<?>, FutureTypeAdapter<?>>> calls = new ThreadLocal();
    private final ConstructorConstructor constructorConstructor;
    private final Excluder excluder;
    private final List<TypeAdapterFactory> factories;
    private final FieldNamingStrategy fieldNamingStrategy;
    private final boolean generateNonExecutableJson;
    private final boolean htmlSafe;
    private final JsonAdapterAnnotationTypeAdapterFactory jsonAdapterFactory;
    private final boolean lenient;
    private final boolean prettyPrinting;
    private final boolean serializeNulls;
    private final Map<TypeToken<?>, TypeAdapter<?>> typeTokenCache = new ConcurrentHashMap();

    public Gson() {
        this(Excluder.DEFAULT, FieldNamingPolicy.IDENTITY, Collections.emptyMap(), false, false, false, true, false, false, false, LongSerializationPolicy.DEFAULT, Collections.emptyList());
    }

    Gson(Excluder excluder, FieldNamingStrategy fieldNamingStrategy, Map<Type, InstanceCreator<?>> object, boolean bl, boolean bl2, boolean bl3, boolean bl4, boolean bl5, boolean bl6, boolean bl7, LongSerializationPolicy object2, List<TypeAdapterFactory> list) {
        this.constructorConstructor = new ConstructorConstructor((Map<Type, InstanceCreator<?>>)object);
        this.excluder = excluder;
        this.fieldNamingStrategy = fieldNamingStrategy;
        this.serializeNulls = bl;
        this.generateNonExecutableJson = bl3;
        this.htmlSafe = bl4;
        this.prettyPrinting = bl5;
        this.lenient = bl6;
        object = new ArrayList();
        object.add(TypeAdapters.JSON_ELEMENT_FACTORY);
        object.add(ObjectTypeAdapter.FACTORY);
        object.add(excluder);
        object.addAll(list);
        object.add(TypeAdapters.STRING_FACTORY);
        object.add(TypeAdapters.INTEGER_FACTORY);
        object.add(TypeAdapters.BOOLEAN_FACTORY);
        object.add(TypeAdapters.BYTE_FACTORY);
        object.add(TypeAdapters.SHORT_FACTORY);
        object2 = Gson.longAdapter(object2);
        object.add(TypeAdapters.newFactory(Long.TYPE, Long.class, object2));
        object.add(TypeAdapters.newFactory(Double.TYPE, Double.class, this.doubleAdapter(bl7)));
        object.add(TypeAdapters.newFactory(Float.TYPE, Float.class, this.floatAdapter(bl7)));
        object.add(TypeAdapters.NUMBER_FACTORY);
        object.add(TypeAdapters.ATOMIC_INTEGER_FACTORY);
        object.add(TypeAdapters.ATOMIC_BOOLEAN_FACTORY);
        object.add(TypeAdapters.newFactory(AtomicLong.class, Gson.atomicLongAdapter((TypeAdapter<Number>)object2)));
        object.add(TypeAdapters.newFactory(AtomicLongArray.class, Gson.atomicLongArrayAdapter((TypeAdapter<Number>)object2)));
        object.add(TypeAdapters.ATOMIC_INTEGER_ARRAY_FACTORY);
        object.add(TypeAdapters.CHARACTER_FACTORY);
        object.add(TypeAdapters.STRING_BUILDER_FACTORY);
        object.add(TypeAdapters.STRING_BUFFER_FACTORY);
        object.add(TypeAdapters.newFactory(BigDecimal.class, TypeAdapters.BIG_DECIMAL));
        object.add(TypeAdapters.newFactory(BigInteger.class, TypeAdapters.BIG_INTEGER));
        object.add(TypeAdapters.URL_FACTORY);
        object.add(TypeAdapters.URI_FACTORY);
        object.add(TypeAdapters.UUID_FACTORY);
        object.add(TypeAdapters.CURRENCY_FACTORY);
        object.add(TypeAdapters.LOCALE_FACTORY);
        object.add(TypeAdapters.INET_ADDRESS_FACTORY);
        object.add(TypeAdapters.BIT_SET_FACTORY);
        object.add(DateTypeAdapter.FACTORY);
        object.add(TypeAdapters.CALENDAR_FACTORY);
        object.add(TimeTypeAdapter.FACTORY);
        object.add(SqlDateTypeAdapter.FACTORY);
        object.add(TypeAdapters.TIMESTAMP_FACTORY);
        object.add(ArrayTypeAdapter.FACTORY);
        object.add(TypeAdapters.CLASS_FACTORY);
        object.add(new CollectionTypeAdapterFactory(this.constructorConstructor));
        object.add(new MapTypeAdapterFactory(this.constructorConstructor, bl2));
        this.jsonAdapterFactory = new JsonAdapterAnnotationTypeAdapterFactory(this.constructorConstructor);
        object.add(this.jsonAdapterFactory);
        object.add(TypeAdapters.ENUM_FACTORY);
        object.add(new ReflectiveTypeAdapterFactory(this.constructorConstructor, fieldNamingStrategy, excluder, this.jsonAdapterFactory));
        this.factories = Collections.unmodifiableList(object);
    }

    private static void assertFullConsumption(Object object, JsonReader jsonReader) {
        if (object != null) {
            try {
                if (jsonReader.peek() != JsonToken.END_DOCUMENT) {
                    throw new JsonIOException("JSON document was not fully consumed.");
                }
            }
            catch (MalformedJsonException malformedJsonException) {
                throw new JsonSyntaxException(malformedJsonException);
            }
            catch (IOException iOException) {
                throw new JsonIOException(iOException);
            }
        }
    }

    private static TypeAdapter<AtomicLong> atomicLongAdapter(final TypeAdapter<Number> typeAdapter) {
        return new TypeAdapter<AtomicLong>(){

            @Override
            public AtomicLong read(JsonReader jsonReader) throws IOException {
                return new AtomicLong(((Number)typeAdapter.read(jsonReader)).longValue());
            }

            @Override
            public void write(JsonWriter jsonWriter, AtomicLong atomicLong) throws IOException {
                typeAdapter.write(jsonWriter, atomicLong.get());
            }
        }.nullSafe();
    }

    private static TypeAdapter<AtomicLongArray> atomicLongArrayAdapter(final TypeAdapter<Number> typeAdapter) {
        return new TypeAdapter<AtomicLongArray>(){

            @Override
            public AtomicLongArray read(JsonReader object) throws IOException {
                ArrayList<Long> arrayList = new ArrayList<Long>();
                ((JsonReader)object).beginArray();
                while (((JsonReader)object).hasNext()) {
                    arrayList.add(((Number)typeAdapter.read((JsonReader)object)).longValue());
                }
                ((JsonReader)object).endArray();
                int n = arrayList.size();
                object = new AtomicLongArray(n);
                for (int i = 0; i < n; ++i) {
                    ((AtomicLongArray)object).set(i, (Long)arrayList.get(i));
                }
                return object;
            }

            @Override
            public void write(JsonWriter jsonWriter, AtomicLongArray atomicLongArray) throws IOException {
                jsonWriter.beginArray();
                int n = atomicLongArray.length();
                for (int i = 0; i < n; ++i) {
                    typeAdapter.write(jsonWriter, atomicLongArray.get(i));
                }
                jsonWriter.endArray();
            }
        }.nullSafe();
    }

    static void checkValidFloatingPoint(double d) {
        if (Double.isNaN(d) || Double.isInfinite(d)) {
            throw new IllegalArgumentException(d + " is not a valid double value as per JSON specification. To override this" + " behavior, use GsonBuilder.serializeSpecialFloatingPointValues() method.");
        }
    }

    private TypeAdapter<Number> doubleAdapter(boolean bl) {
        if (bl) {
            return TypeAdapters.DOUBLE;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Double read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextDouble();
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                Gson.checkValidFloatingPoint(number.doubleValue());
                jsonWriter.value(number);
            }
        };
    }

    private TypeAdapter<Number> floatAdapter(boolean bl) {
        if (bl) {
            return TypeAdapters.FLOAT;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Float read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Float.valueOf((float)jsonReader.nextDouble());
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                Gson.checkValidFloatingPoint(number.floatValue());
                jsonWriter.value(number);
            }
        };
    }

    private static TypeAdapter<Number> longAdapter(LongSerializationPolicy longSerializationPolicy) {
        if (longSerializationPolicy == LongSerializationPolicy.DEFAULT) {
            return TypeAdapters.LONG;
        }
        return new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextLong();
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                if (number == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.value(number.toString());
            }
        };
    }

    public <T> T fromJson(JsonElement jsonElement, Class<T> class_) throws JsonSyntaxException {
        jsonElement = this.fromJson(jsonElement, (Type)class_);
        return Primitives.wrap(class_).cast(jsonElement);
    }

    public <T> T fromJson(JsonElement jsonElement, Type type) throws JsonSyntaxException {
        if (jsonElement == null) {
            return null;
        }
        return this.fromJson(new JsonTreeReader(jsonElement), type);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public <T> T fromJson(JsonReader jsonReader, Type type) throws JsonIOException, JsonSyntaxException {
        boolean bl = true;
        boolean bl2 = jsonReader.isLenient();
        jsonReader.setLenient(true);
        try {
            jsonReader.peek();
            bl = false;
            type = this.getAdapter(TypeToken.get(type)).read(jsonReader);
            return (T)type;
        }
        catch (EOFException eOFException) {
            if (!bl) throw new JsonSyntaxException(eOFException);
            return null;
        }
        catch (IllegalStateException illegalStateException) {
            throw new JsonSyntaxException(illegalStateException);
        }
        catch (IOException iOException) {
            throw new JsonSyntaxException(iOException);
        }
        finally {
            jsonReader.setLenient(bl2);
        }
    }

    public <T> T fromJson(Reader closeable, Type type) throws JsonIOException, JsonSyntaxException {
        closeable = this.newJsonReader((Reader)closeable);
        type = this.fromJson((JsonReader)closeable, type);
        Gson.assertFullConsumption(type, (JsonReader)closeable);
        return (T)type;
    }

    public <T> T fromJson(String string2, Class<T> class_) throws JsonSyntaxException {
        string2 = this.fromJson(string2, (Type)class_);
        return Primitives.wrap(class_).cast(string2);
    }

    public <T> T fromJson(String string2, Type type) throws JsonSyntaxException {
        if (string2 == null) {
            return null;
        }
        return this.fromJson(new StringReader(string2), type);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public <T> TypeAdapter<T> getAdapter(TypeToken<T> typeToken) {
        Object object = this.typeTokenCache;
        Object object2 = typeToken == null ? NULL_KEY_SURROGATE : typeToken;
        object2 = object.get(object2);
        if (object2 != null) {
            return object2;
        }
        object = this.calls.get();
        boolean bl = false;
        object2 = object;
        if (object == null) {
            object2 = new HashMap();
            this.calls.set((Map<TypeToken<?>, FutureTypeAdapter<?>>)object2);
            bl = true;
        }
        if ((object = (FutureTypeAdapter)object2.get(typeToken)) != null) {
            return object;
        }
        try {
            TypeAdapter<T> typeAdapter;
            object = new FutureTypeAdapter<T>();
            object2.put(typeToken, object);
            Iterator<TypeAdapterFactory> iterator = this.factories.iterator();
            do {
                if (!iterator.hasNext()) throw new IllegalArgumentException("GSON cannot handle " + typeToken);
            } while ((typeAdapter = iterator.next().create(this, typeToken)) == null);
            ((FutureTypeAdapter)object).setDelegate(typeAdapter);
            this.typeTokenCache.put(typeToken, typeAdapter);
            return typeAdapter;
        }
        finally {
            object2.remove(typeToken);
            if (bl) {
                this.calls.remove();
            }
        }
    }

    public <T> TypeAdapter<T> getAdapter(Class<T> class_) {
        return this.getAdapter(TypeToken.get(class_));
    }

    public <T> TypeAdapter<T> getDelegateAdapter(TypeAdapterFactory object, TypeToken<T> typeToken) {
        Iterator<TypeAdapterFactory> typeAdapterFactory = object;
        if (!this.factories.contains(object)) {
            typeAdapterFactory = this.jsonAdapterFactory;
        }
        boolean bl = false;
        for (TypeAdapterFactory typeAdapterFactory2 : this.factories) {
            if (!bl) {
                if (typeAdapterFactory2 != typeAdapterFactory) continue;
                bl = true;
                continue;
            }
            TypeAdapter<T> typeAdapter = typeAdapterFactory2.create(this, typeToken);
            if (typeAdapter == null) continue;
            return typeAdapter;
        }
        throw new IllegalArgumentException("GSON cannot serialize " + typeToken);
    }

    public JsonReader newJsonReader(Reader closeable) {
        closeable = new JsonReader((Reader)closeable);
        ((JsonReader)closeable).setLenient(this.lenient);
        return closeable;
    }

    public JsonWriter newJsonWriter(Writer closeable) throws IOException {
        if (this.generateNonExecutableJson) {
            ((Writer)closeable).write(")]}'\n");
        }
        closeable = new JsonWriter((Writer)closeable);
        if (this.prettyPrinting) {
            ((JsonWriter)closeable).setIndent("  ");
        }
        ((JsonWriter)closeable).setSerializeNulls(this.serializeNulls);
        return closeable;
    }

    public String toJson(JsonElement jsonElement) {
        StringWriter stringWriter = new StringWriter();
        this.toJson(jsonElement, stringWriter);
        return stringWriter.toString();
    }

    public String toJson(Object object) {
        if (object == null) {
            return this.toJson(JsonNull.INSTANCE);
        }
        return this.toJson(object, object.getClass());
    }

    public String toJson(Object object, Type type) {
        StringWriter stringWriter = new StringWriter();
        this.toJson(object, type, stringWriter);
        return stringWriter.toString();
    }

    public void toJson(JsonElement jsonElement, JsonWriter jsonWriter) throws JsonIOException {
        boolean bl = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        boolean bl2 = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        boolean bl3 = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        try {
            Streams.write(jsonElement, jsonWriter);
            return;
        }
        catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
        finally {
            jsonWriter.setLenient(bl);
            jsonWriter.setHtmlSafe(bl2);
            jsonWriter.setSerializeNulls(bl3);
        }
    }

    public void toJson(JsonElement jsonElement, Appendable appendable) throws JsonIOException {
        try {
            this.toJson(jsonElement, this.newJsonWriter(Streams.writerForAppendable(appendable)));
            return;
        }
        catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
    }

    public void toJson(Object object, Type object2, JsonWriter jsonWriter) throws JsonIOException {
        object2 = this.getAdapter(TypeToken.get((Type)object2));
        boolean bl = jsonWriter.isLenient();
        jsonWriter.setLenient(true);
        boolean bl2 = jsonWriter.isHtmlSafe();
        jsonWriter.setHtmlSafe(this.htmlSafe);
        boolean bl3 = jsonWriter.getSerializeNulls();
        jsonWriter.setSerializeNulls(this.serializeNulls);
        try {
            ((TypeAdapter)object2).write(jsonWriter, object);
            return;
        }
        catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
        finally {
            jsonWriter.setLenient(bl);
            jsonWriter.setHtmlSafe(bl2);
            jsonWriter.setSerializeNulls(bl3);
        }
    }

    public void toJson(Object object, Type type, Appendable appendable) throws JsonIOException {
        try {
            this.toJson(object, type, this.newJsonWriter(Streams.writerForAppendable(appendable)));
            return;
        }
        catch (IOException iOException) {
            throw new JsonIOException(iOException);
        }
    }

    public String toString() {
        return "{serializeNulls:" + this.serializeNulls + "factories:" + this.factories + ",instanceCreators:" + this.constructorConstructor + "}";
    }

    static class FutureTypeAdapter<T>
    extends TypeAdapter<T> {
        private TypeAdapter<T> delegate;

        FutureTypeAdapter() {
        }

        @Override
        public T read(JsonReader jsonReader) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            return this.delegate.read(jsonReader);
        }

        public void setDelegate(TypeAdapter<T> typeAdapter) {
            if (this.delegate != null) {
                throw new AssertionError();
            }
            this.delegate = typeAdapter;
        }

        @Override
        public void write(JsonWriter jsonWriter, T t) throws IOException {
            if (this.delegate == null) {
                throw new IllegalStateException();
            }
            this.delegate.write(jsonWriter, t);
        }
    }

}

