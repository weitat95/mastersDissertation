/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.BaseRealm;
import io.realm.RealmFieldType;
import io.realm.RealmList;
import io.realm.RealmObject;
import io.realm.RealmSchema;
import io.realm.SchemaConnector;
import io.realm.internal.ColumnInfo;
import io.realm.internal.Table;
import io.realm.internal.fields.FieldDescriptor;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class RealmObjectSchema {
    private static final Map<Class<?>, FieldMetaData> SUPPORTED_LINKED_FIELDS;
    private static final Map<Class<?>, FieldMetaData> SUPPORTED_SIMPLE_FIELDS;
    private final ColumnInfo columnInfo;
    private final BaseRealm realm;
    private final RealmSchema schema;
    private final Table table;

    static {
        HashMap<Class<RealmList>, FieldMetaData> hashMap = new HashMap<Class<RealmList>, FieldMetaData>();
        hashMap.put(String.class, new FieldMetaData(RealmFieldType.STRING, true));
        hashMap.put(Short.TYPE, new FieldMetaData(RealmFieldType.INTEGER, false));
        hashMap.put(Short.class, new FieldMetaData(RealmFieldType.INTEGER, true));
        hashMap.put(Integer.TYPE, new FieldMetaData(RealmFieldType.INTEGER, false));
        hashMap.put(Integer.class, new FieldMetaData(RealmFieldType.INTEGER, true));
        hashMap.put(Long.TYPE, new FieldMetaData(RealmFieldType.INTEGER, false));
        hashMap.put(Long.class, new FieldMetaData(RealmFieldType.INTEGER, true));
        hashMap.put(Float.TYPE, new FieldMetaData(RealmFieldType.FLOAT, false));
        hashMap.put(Float.class, new FieldMetaData(RealmFieldType.FLOAT, true));
        hashMap.put(Double.TYPE, new FieldMetaData(RealmFieldType.DOUBLE, false));
        hashMap.put(Double.class, new FieldMetaData(RealmFieldType.DOUBLE, true));
        hashMap.put(Boolean.TYPE, new FieldMetaData(RealmFieldType.BOOLEAN, false));
        hashMap.put(Boolean.class, new FieldMetaData(RealmFieldType.BOOLEAN, true));
        hashMap.put(Byte.TYPE, new FieldMetaData(RealmFieldType.INTEGER, false));
        hashMap.put(Byte.class, new FieldMetaData(RealmFieldType.INTEGER, true));
        hashMap.put(byte[].class, new FieldMetaData(RealmFieldType.BINARY, true));
        hashMap.put(Date.class, new FieldMetaData(RealmFieldType.DATE, true));
        SUPPORTED_SIMPLE_FIELDS = Collections.unmodifiableMap(hashMap);
        hashMap = new HashMap();
        hashMap.put(RealmObject.class, new FieldMetaData(RealmFieldType.OBJECT, false));
        hashMap.put(RealmList.class, new FieldMetaData(RealmFieldType.LIST, false));
        SUPPORTED_LINKED_FIELDS = Collections.unmodifiableMap(hashMap);
    }

    RealmObjectSchema(BaseRealm baseRealm, RealmSchema realmSchema, Table table, ColumnInfo columnInfo) {
        this.schema = realmSchema;
        this.realm = baseRealm;
        this.table = table;
        this.columnInfo = columnInfo;
    }

    private SchemaConnector getSchemaConnector() {
        return new SchemaConnector(this.schema);
    }

    protected final FieldDescriptor getColumnIndices(String string2, RealmFieldType ... arrrealmFieldType) {
        return FieldDescriptor.createStandardFieldDescriptor(this.getSchemaConnector(), this.getTable(), string2, arrrealmFieldType);
    }

    Table getTable() {
        return this.table;
    }

    private static final class FieldMetaData {
        final boolean defaultNullable;
        final RealmFieldType realmType;

        FieldMetaData(RealmFieldType realmFieldType, boolean bl) {
            this.realmType = realmFieldType;
            this.defaultNullable = bl;
        }
    }

}

