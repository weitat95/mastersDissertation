/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal.bind;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LazilyParsedNumber;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.BitSet;
import java.util.Calendar;
import java.util.Currency;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicIntegerArray;

public final class TypeAdapters {
    public static final TypeAdapter<AtomicBoolean> ATOMIC_BOOLEAN;
    public static final TypeAdapterFactory ATOMIC_BOOLEAN_FACTORY;
    public static final TypeAdapter<AtomicInteger> ATOMIC_INTEGER;
    public static final TypeAdapter<AtomicIntegerArray> ATOMIC_INTEGER_ARRAY;
    public static final TypeAdapterFactory ATOMIC_INTEGER_ARRAY_FACTORY;
    public static final TypeAdapterFactory ATOMIC_INTEGER_FACTORY;
    public static final TypeAdapter<BigDecimal> BIG_DECIMAL;
    public static final TypeAdapter<BigInteger> BIG_INTEGER;
    public static final TypeAdapter<BitSet> BIT_SET;
    public static final TypeAdapterFactory BIT_SET_FACTORY;
    public static final TypeAdapter<Boolean> BOOLEAN;
    public static final TypeAdapter<Boolean> BOOLEAN_AS_STRING;
    public static final TypeAdapterFactory BOOLEAN_FACTORY;
    public static final TypeAdapter<Number> BYTE;
    public static final TypeAdapterFactory BYTE_FACTORY;
    public static final TypeAdapter<Calendar> CALENDAR;
    public static final TypeAdapterFactory CALENDAR_FACTORY;
    public static final TypeAdapter<Character> CHARACTER;
    public static final TypeAdapterFactory CHARACTER_FACTORY;
    public static final TypeAdapter<Class> CLASS;
    public static final TypeAdapterFactory CLASS_FACTORY;
    public static final TypeAdapter<Currency> CURRENCY;
    public static final TypeAdapterFactory CURRENCY_FACTORY;
    public static final TypeAdapter<Number> DOUBLE;
    public static final TypeAdapterFactory ENUM_FACTORY;
    public static final TypeAdapter<Number> FLOAT;
    public static final TypeAdapter<InetAddress> INET_ADDRESS;
    public static final TypeAdapterFactory INET_ADDRESS_FACTORY;
    public static final TypeAdapter<Number> INTEGER;
    public static final TypeAdapterFactory INTEGER_FACTORY;
    public static final TypeAdapter<JsonElement> JSON_ELEMENT;
    public static final TypeAdapterFactory JSON_ELEMENT_FACTORY;
    public static final TypeAdapter<Locale> LOCALE;
    public static final TypeAdapterFactory LOCALE_FACTORY;
    public static final TypeAdapter<Number> LONG;
    public static final TypeAdapter<Number> NUMBER;
    public static final TypeAdapterFactory NUMBER_FACTORY;
    public static final TypeAdapter<Number> SHORT;
    public static final TypeAdapterFactory SHORT_FACTORY;
    public static final TypeAdapter<String> STRING;
    public static final TypeAdapter<StringBuffer> STRING_BUFFER;
    public static final TypeAdapterFactory STRING_BUFFER_FACTORY;
    public static final TypeAdapter<StringBuilder> STRING_BUILDER;
    public static final TypeAdapterFactory STRING_BUILDER_FACTORY;
    public static final TypeAdapterFactory STRING_FACTORY;
    public static final TypeAdapterFactory TIMESTAMP_FACTORY;
    public static final TypeAdapter<URI> URI;
    public static final TypeAdapterFactory URI_FACTORY;
    public static final TypeAdapter<URL> URL;
    public static final TypeAdapterFactory URL_FACTORY;
    public static final TypeAdapter<UUID> UUID;
    public static final TypeAdapterFactory UUID_FACTORY;

    static {
        CLASS = new TypeAdapter<Class>(){

            @Override
            public Class read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                throw new UnsupportedOperationException("Attempted to deserialize a java.lang.Class. Forgot to register a type adapter?");
            }

            @Override
            public void write(JsonWriter jsonWriter, Class class_) throws IOException {
                if (class_ == null) {
                    jsonWriter.nullValue();
                    return;
                }
                throw new UnsupportedOperationException("Attempted to serialize java.lang.Class: " + class_.getName() + ". Forgot to register a type adapter?");
            }
        };
        CLASS_FACTORY = TypeAdapters.newFactory(Class.class, CLASS);
        BIT_SET = new TypeAdapter<BitSet>(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public BitSet read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                BitSet bitSet = new BitSet();
                jsonReader.beginArray();
                int n = 0;
                Object object = jsonReader.peek();
                do {
                    boolean bl;
                    if (object == JsonToken.END_ARRAY) {
                        jsonReader.endArray();
                        return bitSet;
                    }
                    switch (object) {
                        default: {
                            throw new JsonSyntaxException("Invalid bitset value type: " + (Object)object);
                        }
                        case NUMBER: {
                            if (jsonReader.nextInt() != 0) {
                                bl = true;
                                break;
                            }
                            bl = false;
                            break;
                        }
                        case BOOLEAN: {
                            bl = jsonReader.nextBoolean();
                            break;
                        }
                        case STRING: {
                            object = jsonReader.nextString();
                            try {
                                int n2 = Integer.parseInt((String)object);
                                if (n2 != 0) {
                                    bl = true;
                                    break;
                                }
                                bl = false;
                                break;
                            }
                            catch (NumberFormatException numberFormatException) {
                                throw new JsonSyntaxException("Error: Expecting: bitset number value (1, 0), Found: " + (String)object);
                            }
                        }
                    }
                    if (bl) {
                        bitSet.set(n);
                    }
                    ++n;
                    object = jsonReader.peek();
                } while (true);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, BitSet bitSet) throws IOException {
                if (bitSet == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.beginArray();
                int n = 0;
                do {
                    if (n >= bitSet.length()) {
                        jsonWriter.endArray();
                        return;
                    }
                    int n2 = bitSet.get(n) ? 1 : 0;
                    jsonWriter.value(n2);
                    ++n;
                } while (true);
            }
        };
        BIT_SET_FACTORY = TypeAdapters.newFactory(BitSet.class, BIT_SET);
        BOOLEAN = new TypeAdapter<Boolean>(){

            @Override
            public Boolean read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                if (jsonReader.peek() == JsonToken.STRING) {
                    return Boolean.parseBoolean(jsonReader.nextString());
                }
                return jsonReader.nextBoolean();
            }

            @Override
            public void write(JsonWriter jsonWriter, Boolean bl) throws IOException {
                jsonWriter.value(bl);
            }
        };
        BOOLEAN_AS_STRING = new TypeAdapter<Boolean>(){

            @Override
            public Boolean read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Boolean.valueOf(jsonReader.nextString());
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, Boolean object) throws IOException {
                object = object == null ? "null" : ((Boolean)object).toString();
                jsonWriter.value((String)object);
            }
        };
        BOOLEAN_FACTORY = TypeAdapters.newFactory(Boolean.TYPE, Boolean.class, BOOLEAN);
        BYTE = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                byte by;
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    by = (byte)jsonReader.nextInt();
                }
                catch (NumberFormatException numberFormatException) {
                    throw new JsonSyntaxException(numberFormatException);
                }
                return by;
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        BYTE_FACTORY = TypeAdapters.newFactory(Byte.TYPE, Byte.class, BYTE);
        SHORT = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                short s;
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    s = (short)jsonReader.nextInt();
                }
                catch (NumberFormatException numberFormatException) {
                    throw new JsonSyntaxException(numberFormatException);
                }
                return s;
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        SHORT_FACTORY = TypeAdapters.newFactory(Short.TYPE, Short.class, SHORT);
        INTEGER = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                int n;
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    n = jsonReader.nextInt();
                }
                catch (NumberFormatException numberFormatException) {
                    throw new JsonSyntaxException(numberFormatException);
                }
                return n;
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        INTEGER_FACTORY = TypeAdapters.newFactory(Integer.TYPE, Integer.class, INTEGER);
        ATOMIC_INTEGER = new TypeAdapter<AtomicInteger>(){

            @Override
            public AtomicInteger read(JsonReader object) throws IOException {
                try {
                    object = new AtomicInteger(((JsonReader)object).nextInt());
                    return object;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new JsonSyntaxException(numberFormatException);
                }
            }

            @Override
            public void write(JsonWriter jsonWriter, AtomicInteger atomicInteger) throws IOException {
                jsonWriter.value(atomicInteger.get());
            }
        }.nullSafe();
        ATOMIC_INTEGER_FACTORY = TypeAdapters.newFactory(AtomicInteger.class, ATOMIC_INTEGER);
        ATOMIC_BOOLEAN = new TypeAdapter<AtomicBoolean>(){

            @Override
            public AtomicBoolean read(JsonReader jsonReader) throws IOException {
                return new AtomicBoolean(jsonReader.nextBoolean());
            }

            @Override
            public void write(JsonWriter jsonWriter, AtomicBoolean atomicBoolean) throws IOException {
                jsonWriter.value(atomicBoolean.get());
            }
        }.nullSafe();
        ATOMIC_BOOLEAN_FACTORY = TypeAdapters.newFactory(AtomicBoolean.class, ATOMIC_BOOLEAN);
        ATOMIC_INTEGER_ARRAY = new TypeAdapter<AtomicIntegerArray>(){

            @Override
            public AtomicIntegerArray read(JsonReader object) throws IOException {
                ArrayList<Integer> arrayList = new ArrayList<Integer>();
                ((JsonReader)object).beginArray();
                while (((JsonReader)object).hasNext()) {
                    try {
                        arrayList.add(((JsonReader)object).nextInt());
                    }
                    catch (NumberFormatException numberFormatException) {
                        throw new JsonSyntaxException(numberFormatException);
                    }
                }
                ((JsonReader)object).endArray();
                int n = arrayList.size();
                object = new AtomicIntegerArray(n);
                for (int i = 0; i < n; ++i) {
                    ((AtomicIntegerArray)object).set(i, (Integer)arrayList.get(i));
                }
                return object;
            }

            @Override
            public void write(JsonWriter jsonWriter, AtomicIntegerArray atomicIntegerArray) throws IOException {
                jsonWriter.beginArray();
                int n = atomicIntegerArray.length();
                for (int i = 0; i < n; ++i) {
                    jsonWriter.value(atomicIntegerArray.get(i));
                }
                jsonWriter.endArray();
            }
        }.nullSafe();
        ATOMIC_INTEGER_ARRAY_FACTORY = TypeAdapters.newFactory(AtomicIntegerArray.class, ATOMIC_INTEGER_ARRAY);
        LONG = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                long l;
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                try {
                    l = jsonReader.nextLong();
                }
                catch (NumberFormatException numberFormatException) {
                    throw new JsonSyntaxException(numberFormatException);
                }
                return l;
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        FLOAT = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return Float.valueOf((float)jsonReader.nextDouble());
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        DOUBLE = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return jsonReader.nextDouble();
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        NUMBER = new TypeAdapter<Number>(){

            @Override
            public Number read(JsonReader jsonReader) throws IOException {
                JsonToken jsonToken = jsonReader.peek();
                switch (jsonToken) {
                    default: {
                        throw new JsonSyntaxException("Expecting number, got: " + (Object)((Object)jsonToken));
                    }
                    case NULL: {
                        jsonReader.nextNull();
                        return null;
                    }
                    case NUMBER: 
                }
                return new LazilyParsedNumber(jsonReader.nextString());
            }

            @Override
            public void write(JsonWriter jsonWriter, Number number) throws IOException {
                jsonWriter.value(number);
            }
        };
        NUMBER_FACTORY = TypeAdapters.newFactory(Number.class, NUMBER);
        CHARACTER = new TypeAdapter<Character>(){

            @Override
            public Character read(JsonReader object) throws IOException {
                if (((JsonReader)object).peek() == JsonToken.NULL) {
                    ((JsonReader)object).nextNull();
                    return null;
                }
                if (((String)(object = ((JsonReader)object).nextString())).length() != 1) {
                    throw new JsonSyntaxException("Expecting character, got: " + (String)object);
                }
                return Character.valueOf(((String)object).charAt(0));
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, Character object) throws IOException {
                object = object == null ? null : String.valueOf(object);
                jsonWriter.value((String)object);
            }
        };
        CHARACTER_FACTORY = TypeAdapters.newFactory(Character.TYPE, Character.class, CHARACTER);
        STRING = new TypeAdapter<String>(){

            @Override
            public String read(JsonReader jsonReader) throws IOException {
                JsonToken jsonToken = jsonReader.peek();
                if (jsonToken == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                if (jsonToken == JsonToken.BOOLEAN) {
                    return Boolean.toString(jsonReader.nextBoolean());
                }
                return jsonReader.nextString();
            }

            @Override
            public void write(JsonWriter jsonWriter, String string2) throws IOException {
                jsonWriter.value(string2);
            }
        };
        BIG_DECIMAL = new TypeAdapter<BigDecimal>(){

            @Override
            public BigDecimal read(JsonReader object) throws IOException {
                if (((JsonReader)object).peek() == JsonToken.NULL) {
                    ((JsonReader)object).nextNull();
                    return null;
                }
                try {
                    object = new BigDecimal(((JsonReader)object).nextString());
                    return object;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new JsonSyntaxException(numberFormatException);
                }
            }

            @Override
            public void write(JsonWriter jsonWriter, BigDecimal bigDecimal) throws IOException {
                jsonWriter.value(bigDecimal);
            }
        };
        BIG_INTEGER = new TypeAdapter<BigInteger>(){

            @Override
            public BigInteger read(JsonReader object) throws IOException {
                if (((JsonReader)object).peek() == JsonToken.NULL) {
                    ((JsonReader)object).nextNull();
                    return null;
                }
                try {
                    object = new BigInteger(((JsonReader)object).nextString());
                    return object;
                }
                catch (NumberFormatException numberFormatException) {
                    throw new JsonSyntaxException(numberFormatException);
                }
            }

            @Override
            public void write(JsonWriter jsonWriter, BigInteger bigInteger) throws IOException {
                jsonWriter.value(bigInteger);
            }
        };
        STRING_FACTORY = TypeAdapters.newFactory(String.class, STRING);
        STRING_BUILDER = new TypeAdapter<StringBuilder>(){

            @Override
            public StringBuilder read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return new StringBuilder(jsonReader.nextString());
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, StringBuilder charSequence) throws IOException {
                void var2_4;
                if (charSequence == null) {
                    Object var2_3 = null;
                } else {
                    String string2 = ((StringBuilder)charSequence).toString();
                }
                jsonWriter.value((String)var2_4);
            }
        };
        STRING_BUILDER_FACTORY = TypeAdapters.newFactory(StringBuilder.class, STRING_BUILDER);
        STRING_BUFFER = new TypeAdapter<StringBuffer>(){

            @Override
            public StringBuffer read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return new StringBuffer(jsonReader.nextString());
            }

            /*
             * WARNING - void declaration
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, StringBuffer charSequence) throws IOException {
                void var2_4;
                if (charSequence == null) {
                    Object var2_3 = null;
                } else {
                    String string2 = ((StringBuffer)charSequence).toString();
                }
                jsonWriter.value((String)var2_4);
            }
        };
        STRING_BUFFER_FACTORY = TypeAdapters.newFactory(StringBuffer.class, STRING_BUFFER);
        URL = new TypeAdapter<URL>(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public URL read(JsonReader object) throws IOException {
                if (((JsonReader)object).peek() == JsonToken.NULL) {
                    ((JsonReader)object).nextNull();
                    return null;
                } else {
                    if ("null".equals(object = ((JsonReader)object).nextString())) return null;
                    return new URL((String)object);
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, URL object) throws IOException {
                object = object == null ? null : ((URL)object).toExternalForm();
                jsonWriter.value((String)object);
            }
        };
        URL_FACTORY = TypeAdapters.newFactory(URL.class, URL);
        URI = new TypeAdapter<URI>(){

            /*
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public URI read(JsonReader object) throws IOException {
                if (((JsonReader)object).peek() == JsonToken.NULL) {
                    ((JsonReader)object).nextNull();
                    return null;
                }
                try {
                    if ("null".equals(object = ((JsonReader)object).nextString())) return null;
                    return new URI((String)object);
                }
                catch (URISyntaxException uRISyntaxException) {
                    throw new JsonIOException(uRISyntaxException);
                }
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, URI object) throws IOException {
                object = object == null ? null : ((URI)object).toASCIIString();
                jsonWriter.value((String)object);
            }
        };
        URI_FACTORY = TypeAdapters.newFactory(URI.class, URI);
        INET_ADDRESS = new TypeAdapter<InetAddress>(){

            @Override
            public InetAddress read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return InetAddress.getByName(jsonReader.nextString());
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, InetAddress object) throws IOException {
                object = object == null ? null : ((InetAddress)object).getHostAddress();
                jsonWriter.value((String)object);
            }
        };
        INET_ADDRESS_FACTORY = TypeAdapters.newTypeHierarchyFactory(InetAddress.class, INET_ADDRESS);
        UUID = new TypeAdapter<UUID>(){

            @Override
            public UUID read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                return UUID.fromString(jsonReader.nextString());
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, UUID object) throws IOException {
                object = object == null ? null : ((UUID)object).toString();
                jsonWriter.value((String)object);
            }
        };
        UUID_FACTORY = TypeAdapters.newFactory(UUID.class, UUID);
        CURRENCY = new TypeAdapter<Currency>(){

            @Override
            public Currency read(JsonReader jsonReader) throws IOException {
                return Currency.getInstance(jsonReader.nextString());
            }

            @Override
            public void write(JsonWriter jsonWriter, Currency currency) throws IOException {
                jsonWriter.value(currency.getCurrencyCode());
            }
        }.nullSafe();
        CURRENCY_FACTORY = TypeAdapters.newFactory(Currency.class, CURRENCY);
        TIMESTAMP_FACTORY = new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                if (typeToken.getRawType() != Timestamp.class) {
                    return null;
                }
                return new TypeAdapter<Timestamp>(gson.getAdapter(Date.class)){
                    final /* synthetic */ TypeAdapter val$dateTypeAdapter;
                    {
                        this.val$dateTypeAdapter = typeAdapter;
                    }

                    @Override
                    public Timestamp read(JsonReader object) throws IOException {
                        if ((object = (Date)this.val$dateTypeAdapter.read((JsonReader)object)) != null) {
                            return new Timestamp(((Date)object).getTime());
                        }
                        return null;
                    }

                    @Override
                    public void write(JsonWriter jsonWriter, Timestamp timestamp) throws IOException {
                        this.val$dateTypeAdapter.write(jsonWriter, timestamp);
                    }
                };
            }

        };
        CALENDAR = new TypeAdapter<Calendar>(){

            @Override
            public Calendar read(JsonReader jsonReader) throws IOException {
                if (jsonReader.peek() == JsonToken.NULL) {
                    jsonReader.nextNull();
                    return null;
                }
                jsonReader.beginObject();
                int n = 0;
                int n2 = 0;
                int n3 = 0;
                int n4 = 0;
                int n5 = 0;
                int n6 = 0;
                while (jsonReader.peek() != JsonToken.END_OBJECT) {
                    String string2 = jsonReader.nextName();
                    int n7 = jsonReader.nextInt();
                    if ("year".equals(string2)) {
                        n = n7;
                        continue;
                    }
                    if ("month".equals(string2)) {
                        n2 = n7;
                        continue;
                    }
                    if ("dayOfMonth".equals(string2)) {
                        n3 = n7;
                        continue;
                    }
                    if ("hourOfDay".equals(string2)) {
                        n4 = n7;
                        continue;
                    }
                    if ("minute".equals(string2)) {
                        n5 = n7;
                        continue;
                    }
                    if (!"second".equals(string2)) continue;
                    n6 = n7;
                }
                jsonReader.endObject();
                return new GregorianCalendar(n, n2, n3, n4, n5, n6);
            }

            @Override
            public void write(JsonWriter jsonWriter, Calendar calendar) throws IOException {
                if (calendar == null) {
                    jsonWriter.nullValue();
                    return;
                }
                jsonWriter.beginObject();
                jsonWriter.name("year");
                jsonWriter.value(calendar.get(1));
                jsonWriter.name("month");
                jsonWriter.value(calendar.get(2));
                jsonWriter.name("dayOfMonth");
                jsonWriter.value(calendar.get(5));
                jsonWriter.name("hourOfDay");
                jsonWriter.value(calendar.get(11));
                jsonWriter.name("minute");
                jsonWriter.value(calendar.get(12));
                jsonWriter.name("second");
                jsonWriter.value(calendar.get(13));
                jsonWriter.endObject();
            }
        };
        CALENDAR_FACTORY = TypeAdapters.newFactoryForMultipleTypes(Calendar.class, GregorianCalendar.class, CALENDAR);
        LOCALE = new TypeAdapter<Locale>(){

            @Override
            public Locale read(JsonReader object) throws IOException {
                if (((JsonReader)object).peek() == JsonToken.NULL) {
                    ((JsonReader)object).nextNull();
                    return null;
                }
                StringTokenizer stringTokenizer = new StringTokenizer(((JsonReader)object).nextString(), "_");
                object = null;
                String string2 = null;
                String string3 = null;
                if (stringTokenizer.hasMoreElements()) {
                    object = stringTokenizer.nextToken();
                }
                if (stringTokenizer.hasMoreElements()) {
                    string2 = stringTokenizer.nextToken();
                }
                if (stringTokenizer.hasMoreElements()) {
                    string3 = stringTokenizer.nextToken();
                }
                if (string2 == null && string3 == null) {
                    return new Locale((String)object);
                }
                if (string3 == null) {
                    return new Locale((String)object, string2);
                }
                return new Locale((String)object, string2, string3);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void write(JsonWriter jsonWriter, Locale object) throws IOException {
                object = object == null ? null : ((Locale)object).toString();
                jsonWriter.value((String)object);
            }
        };
        LOCALE_FACTORY = TypeAdapters.newFactory(Locale.class, LOCALE);
        JSON_ELEMENT = new TypeAdapter<JsonElement>(){

            @Override
            public JsonElement read(JsonReader jsonReader) throws IOException {
                switch (jsonReader.peek()) {
                    default: {
                        throw new IllegalArgumentException();
                    }
                    case STRING: {
                        return new JsonPrimitive(jsonReader.nextString());
                    }
                    case NUMBER: {
                        return new JsonPrimitive(new LazilyParsedNumber(jsonReader.nextString()));
                    }
                    case BOOLEAN: {
                        return new JsonPrimitive(jsonReader.nextBoolean());
                    }
                    case NULL: {
                        jsonReader.nextNull();
                        return JsonNull.INSTANCE;
                    }
                    case BEGIN_ARRAY: {
                        JsonArray jsonArray = new JsonArray();
                        jsonReader.beginArray();
                        while (jsonReader.hasNext()) {
                            jsonArray.add(this.read(jsonReader));
                        }
                        jsonReader.endArray();
                        return jsonArray;
                    }
                    case BEGIN_OBJECT: 
                }
                JsonObject jsonObject = new JsonObject();
                jsonReader.beginObject();
                while (jsonReader.hasNext()) {
                    jsonObject.add(jsonReader.nextName(), this.read(jsonReader));
                }
                jsonReader.endObject();
                return jsonObject;
            }

            @Override
            public void write(JsonWriter jsonWriter, JsonElement iterator) throws IOException {
                if (iterator == null || ((JsonElement)((Object)iterator)).isJsonNull()) {
                    jsonWriter.nullValue();
                    return;
                }
                if (((JsonElement)((Object)iterator)).isJsonPrimitive()) {
                    if (((JsonPrimitive)((Object)(iterator = ((JsonElement)((Object)iterator)).getAsJsonPrimitive()))).isNumber()) {
                        jsonWriter.value(((JsonPrimitive)((Object)iterator)).getAsNumber());
                        return;
                    }
                    if (((JsonPrimitive)((Object)iterator)).isBoolean()) {
                        jsonWriter.value(((JsonPrimitive)((Object)iterator)).getAsBoolean());
                        return;
                    }
                    jsonWriter.value(((JsonPrimitive)((Object)iterator)).getAsString());
                    return;
                }
                if (((JsonElement)((Object)iterator)).isJsonArray()) {
                    jsonWriter.beginArray();
                    iterator = ((JsonElement)((Object)iterator)).getAsJsonArray().iterator();
                    while (iterator.hasNext()) {
                        this.write(jsonWriter, (JsonElement)((Object)iterator.next()));
                    }
                    jsonWriter.endArray();
                    return;
                }
                if (((JsonElement)((Object)iterator)).isJsonObject()) {
                    jsonWriter.beginObject();
                    for (Map.Entry<String, JsonElement> entry : ((JsonElement)((Object)iterator)).getAsJsonObject().entrySet()) {
                        jsonWriter.name(entry.getKey());
                        this.write(jsonWriter, entry.getValue());
                    }
                    jsonWriter.endObject();
                    return;
                }
                throw new IllegalArgumentException("Couldn't write " + iterator.getClass());
            }
        };
        JSON_ELEMENT_FACTORY = TypeAdapters.newTypeHierarchyFactory(JsonElement.class, JSON_ELEMENT);
        ENUM_FACTORY = new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson class_, TypeToken<T> object) {
                if (!Enum.class.isAssignableFrom((Class<?>)(object = ((TypeToken)object).getRawType())) || object == Enum.class) {
                    return null;
                }
                class_ = object;
                if (!((Class)object).isEnum()) {
                    class_ = ((Class)object).getSuperclass();
                }
                return new EnumTypeAdapter(class_);
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(final Class<TT> class_, final TypeAdapter<TT> typeAdapter) {
        return new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
                if (typeToken.getRawType() == class_) {
                    return typeAdapter;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + class_.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactory(final Class<TT> class_, final Class<TT> class_2, final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson class_3, TypeToken<T> typeToken) {
                class_3 = typeToken.getRawType();
                if (class_3 == class_ || class_3 == class_2) {
                    return typeAdapter;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + class_2.getName() + "+" + class_.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static <TT> TypeAdapterFactory newFactoryForMultipleTypes(final Class<TT> class_, final Class<? extends TT> class_2, final TypeAdapter<? super TT> typeAdapter) {
        return new TypeAdapterFactory(){

            @Override
            public <T> TypeAdapter<T> create(Gson class_3, TypeToken<T> typeToken) {
                class_3 = typeToken.getRawType();
                if (class_3 == class_ || class_3 == class_2) {
                    return typeAdapter;
                }
                return null;
            }

            public String toString() {
                return "Factory[type=" + class_.getName() + "+" + class_2.getName() + ",adapter=" + typeAdapter + "]";
            }
        };
    }

    public static <T1> TypeAdapterFactory newTypeHierarchyFactory(final Class<T1> class_, final TypeAdapter<T1> typeAdapter) {
        return new TypeAdapterFactory(){

            public <T2> TypeAdapter<T2> create(final Gson class_2, TypeToken<T2> typeToken) {
                class_2 = typeToken.getRawType();
                if (!class_.isAssignableFrom(class_2)) {
                    return null;
                }
                return new TypeAdapter<T1>(){

                    @Override
                    public T1 read(JsonReader jsonReader) throws IOException {
                        if ((jsonReader = typeAdapter.read(jsonReader)) != null && !class_2.isInstance(jsonReader)) {
                            throw new JsonSyntaxException("Expected a " + class_2.getName() + " but was " + jsonReader.getClass().getName());
                        }
                        return (T1)jsonReader;
                    }

                    @Override
                    public void write(JsonWriter jsonWriter, T1 T1) throws IOException {
                        typeAdapter.write(jsonWriter, T1);
                    }
                };
            }

            public String toString() {
                return "Factory[typeHierarchy=" + class_.getName() + ",adapter=" + typeAdapter + "]";
            }

        };
    }

    private static final class EnumTypeAdapter<T extends Enum<T>>
    extends TypeAdapter<T> {
        private final Map<T, String> constantToName;
        private final Map<String, T> nameToConstant = new HashMap<String, T>();

        /*
         * WARNING - combined exceptions agressively - possible behaviour change.
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public EnumTypeAdapter(Class<T> class_) {
            this.constantToName = new HashMap<T, String>();
            try {
                for (Enum enum_ : (Enum[])class_.getEnumConstants()) {
                    String string2 = enum_.name();
                    String[] arrstring = class_.getField(string2).getAnnotation(SerializedName.class);
                    if (arrstring != null) {
                        String string3 = arrstring.value();
                        arrstring = arrstring.alternate();
                        int n = arrstring.length;
                        int n2 = 0;
                        do {
                            string2 = string3;
                            if (n2 >= n) break;
                            string2 = arrstring[n2];
                            this.nameToConstant.put(string2, enum_);
                            ++n2;
                        } while (true);
                    }
                    this.nameToConstant.put(string2, enum_);
                    this.constantToName.put(enum_, string2);
                }
                return;
            }
            catch (NoSuchFieldException noSuchFieldException) {
                throw new AssertionError(noSuchFieldException);
            }
        }

        @Override
        public T read(JsonReader jsonReader) throws IOException {
            if (jsonReader.peek() == JsonToken.NULL) {
                jsonReader.nextNull();
                return null;
            }
            return (T)((Enum)this.nameToConstant.get(jsonReader.nextString()));
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        @Override
        public void write(JsonWriter jsonWriter, T object) throws IOException {
            void var2_4;
            if (object == null) {
                Object var2_3 = null;
            } else {
                String string2 = this.constantToName.get(object);
            }
            jsonWriter.value((String)var2_4);
        }
    }

}

