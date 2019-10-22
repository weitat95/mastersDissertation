/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson;

import com.google.gson.DefaultDateTypeAdapter;
import com.google.gson.FieldNamingPolicy;
import com.google.gson.FieldNamingStrategy;
import com.google.gson.Gson;
import com.google.gson.InstanceCreator;
import com.google.gson.LongSerializationPolicy;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.Excluder;
import com.google.gson.internal.bind.TreeTypeAdapter;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class GsonBuilder {
    private boolean complexMapKeySerialization = false;
    private String datePattern;
    private int dateStyle = 2;
    private boolean escapeHtmlChars = true;
    private Excluder excluder = Excluder.DEFAULT;
    private final List<TypeAdapterFactory> factories;
    private FieldNamingStrategy fieldNamingPolicy;
    private boolean generateNonExecutableJson = false;
    private final List<TypeAdapterFactory> hierarchyFactories;
    private final Map<Type, InstanceCreator<?>> instanceCreators;
    private boolean lenient = false;
    private LongSerializationPolicy longSerializationPolicy = LongSerializationPolicy.DEFAULT;
    private boolean prettyPrinting = false;
    private boolean serializeNulls = false;
    private boolean serializeSpecialFloatingPointValues = false;
    private int timeStyle = 2;

    public GsonBuilder() {
        this.fieldNamingPolicy = FieldNamingPolicy.IDENTITY;
        this.instanceCreators = new HashMap();
        this.factories = new ArrayList<TypeAdapterFactory>();
        this.hierarchyFactories = new ArrayList<TypeAdapterFactory>();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void addTypeAdaptersForDate(String object, int n, int n2, List<TypeAdapterFactory> list) {
        block4: {
            block3: {
                block2: {
                    if (object == null || "".equals(((String)object).trim())) break block2;
                    object = new DefaultDateTypeAdapter((String)object);
                    break block3;
                }
                if (n == 2 || n2 == 2) break block4;
                object = new DefaultDateTypeAdapter(n, n2);
            }
            list.add(TreeTypeAdapter.newFactory(TypeToken.get(Date.class), object));
            list.add(TreeTypeAdapter.newFactory(TypeToken.get(Timestamp.class), object));
            list.add(TreeTypeAdapter.newFactory(TypeToken.get(java.sql.Date.class), object));
        }
    }

    public Gson create() {
        ArrayList<TypeAdapterFactory> arrayList = new ArrayList<TypeAdapterFactory>();
        arrayList.addAll(this.factories);
        Collections.reverse(arrayList);
        arrayList.addAll(this.hierarchyFactories);
        this.addTypeAdaptersForDate(this.datePattern, this.dateStyle, this.timeStyle, arrayList);
        return new Gson(this.excluder, this.fieldNamingPolicy, this.instanceCreators, this.serializeNulls, this.complexMapKeySerialization, this.generateNonExecutableJson, this.escapeHtmlChars, this.prettyPrinting, this.lenient, this.serializeSpecialFloatingPointValues, this.longSerializationPolicy, arrayList);
    }
}

