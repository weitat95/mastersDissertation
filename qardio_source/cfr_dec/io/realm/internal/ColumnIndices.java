/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmModel;
import io.realm.internal.ColumnInfo;
import io.realm.internal.util.Pair;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

public final class ColumnIndices {
    private final Map<Class<? extends RealmModel>, ColumnInfo> classes;
    private final Map<String, ColumnInfo> classesByName;
    private final Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo> classesToColumnInfo;
    private final boolean mutable;
    private long schemaVersion;

    public ColumnIndices(long l, Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo> object) {
        this(l, new HashMap<Pair<Class<? extends RealmModel>, String>, ColumnInfo>((Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo>)((Object)object)), true);
        for (Map.Entry entry : object.entrySet()) {
            ColumnInfo columnInfo = (ColumnInfo)entry.getValue();
            if (this.mutable != columnInfo.isMutable()) {
                throw new IllegalArgumentException("ColumnInfo mutability does not match ColumnIndices");
            }
            Pair object2 = (Pair)entry.getKey();
            this.classes.put((Class<? extends RealmModel>)object2.first, columnInfo);
            this.classesByName.put((String)object2.second, columnInfo);
        }
    }

    private ColumnIndices(long l, Map<Pair<Class<? extends RealmModel>, String>, ColumnInfo> map, boolean bl) {
        this.schemaVersion = l;
        this.classesToColumnInfo = map;
        this.mutable = bl;
        this.classes = new HashMap<Class<? extends RealmModel>, ColumnInfo>(map.size());
        this.classesByName = new HashMap<String, ColumnInfo>(map.size());
    }

    public ColumnIndices(ColumnIndices object, boolean bl) {
        this(((ColumnIndices)object).schemaVersion, new HashMap<Pair<Class<? extends RealmModel>, String>, ColumnInfo>(((ColumnIndices)object).classesToColumnInfo.size()), bl);
        for (Map.Entry<Pair<Class<? extends RealmModel>, String>, ColumnInfo> entry : ((ColumnIndices)object).classesToColumnInfo.entrySet()) {
            ColumnInfo columnInfo = entry.getValue().copy(bl);
            Pair<Class<? extends RealmModel>, String> object2 = entry.getKey();
            this.classes.put((Class<? extends RealmModel>)object2.first, columnInfo);
            this.classesByName.put((String)object2.second, columnInfo);
            this.classesToColumnInfo.put(object2, columnInfo);
        }
    }

    public void copyFrom(ColumnIndices columnIndices) {
        if (!this.mutable) {
            throw new UnsupportedOperationException("Attempt to modify immutable cache");
        }
        for (Map.Entry<String, ColumnInfo> entry : this.classesByName.entrySet()) {
            ColumnInfo columnInfo = columnIndices.classesByName.get(entry.getKey());
            if (columnInfo == null) {
                throw new IllegalStateException("Failed to copy ColumnIndices cache for class: " + entry.getKey());
            }
            entry.getValue().copyFrom(columnInfo);
        }
        this.schemaVersion = columnIndices.schemaVersion;
    }

    public ColumnInfo getColumnInfo(Class<? extends RealmModel> class_) {
        return this.classes.get(class_);
    }

    public ColumnInfo getColumnInfo(String string2) {
        return this.classesByName.get(string2);
    }

    public long getSchemaVersion() {
        return this.schemaVersion;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ColumnIndices[");
        stringBuilder.append(this.schemaVersion).append(",");
        stringBuilder.append(this.mutable).append(",");
        if (this.classes != null) {
            boolean bl = false;
            for (Map.Entry<String, ColumnInfo> entry : this.classesByName.entrySet()) {
                if (bl) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(entry.getKey()).append("->").append(entry.getValue());
                bl = true;
            }
        }
        return stringBuilder.append("]").toString();
    }
}

