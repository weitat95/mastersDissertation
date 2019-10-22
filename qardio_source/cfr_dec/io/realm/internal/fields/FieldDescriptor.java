/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal.fields;

import io.realm.RealmFieldType;
import io.realm.internal.ColumnInfo;
import io.realm.internal.Table;
import io.realm.internal.fields.CachedFieldDescriptor;
import io.realm.internal.fields.DynamicFieldDescriptor;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public abstract class FieldDescriptor {
    public static final Set<RealmFieldType> ALL_LINK_FIELD_TYPES;
    public static final Set<RealmFieldType> LIST_LINK_FIELD_TYPE;
    public static final Set<RealmFieldType> NO_LINK_FIELD_TYPE;
    public static final Set<RealmFieldType> OBJECT_LINK_FIELD_TYPE;
    public static final Set<RealmFieldType> SIMPLE_LINK_FIELD_TYPES;
    private long[] columnIndices;
    private final List<String> fields;
    private String finalColumnName;
    private RealmFieldType finalColumnType;
    private long[] nativeTablePointers;
    private final Set<RealmFieldType> validFinalColumnTypes;
    private final Set<RealmFieldType> validInternalColumnTypes;

    static {
        HashSet<RealmFieldType> hashSet = new HashSet<RealmFieldType>(3);
        hashSet.add(RealmFieldType.OBJECT);
        hashSet.add(RealmFieldType.LIST);
        hashSet.add(RealmFieldType.LINKING_OBJECTS);
        ALL_LINK_FIELD_TYPES = Collections.unmodifiableSet(hashSet);
        hashSet = new HashSet(2);
        hashSet.add(RealmFieldType.OBJECT);
        hashSet.add(RealmFieldType.LIST);
        SIMPLE_LINK_FIELD_TYPES = Collections.unmodifiableSet(hashSet);
        hashSet = new HashSet(1);
        hashSet.add(RealmFieldType.LIST);
        LIST_LINK_FIELD_TYPE = Collections.unmodifiableSet(hashSet);
        hashSet = new HashSet(1);
        hashSet.add(RealmFieldType.OBJECT);
        OBJECT_LINK_FIELD_TYPE = Collections.unmodifiableSet(hashSet);
        NO_LINK_FIELD_TYPE = Collections.emptySet();
    }

    protected FieldDescriptor(String string2, Set<RealmFieldType> set, Set<RealmFieldType> set2) {
        this.fields = this.parseFieldDescription(string2);
        if (this.fields.size() <= 0) {
            throw new IllegalArgumentException("Invalid query: Empty field descriptor");
        }
        this.validInternalColumnTypes = set;
        this.validFinalColumnTypes = set2;
    }

    private void compileIfNecessary() {
        if (this.finalColumnType == null) {
            this.compileFieldDescription(this.fields);
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static FieldDescriptor createFieldDescriptor(SchemaProxy schemaProxy, Table object, String string2, Set<RealmFieldType> set, Set<RealmFieldType> set2) {
        if (schemaProxy == null || !schemaProxy.hasCache()) {
            if (set != null) {
                do {
                    return new DynamicFieldDescriptor((Table)object, string2, set, set2);
                    break;
                } while (true);
            }
            set = SIMPLE_LINK_FIELD_TYPES;
            return new DynamicFieldDescriptor((Table)object, string2, set, set2);
        }
        object = ((Table)object).getClassName();
        if (set != null) {
            do {
                return new CachedFieldDescriptor(schemaProxy, (String)object, string2, set, set2);
                break;
            } while (true);
        }
        set = ALL_LINK_FIELD_TYPES;
        return new CachedFieldDescriptor(schemaProxy, (String)object, string2, set, set2);
    }

    public static FieldDescriptor createStandardFieldDescriptor(SchemaProxy schemaProxy, Table table, String string2, RealmFieldType ... arrrealmFieldType) {
        return FieldDescriptor.createFieldDescriptor(schemaProxy, table, string2, null, new HashSet<RealmFieldType>(Arrays.asList(arrrealmFieldType)));
    }

    private List<String> parseFieldDescription(String string2) {
        if (string2 == null || string2.equals("")) {
            throw new IllegalArgumentException("Invalid query: field name is empty");
        }
        if (string2.endsWith(".")) {
            throw new IllegalArgumentException("Invalid query: field name must not end with a period ('.')");
        }
        return Arrays.asList(string2.split("\\."));
    }

    private void verifyColumnType(String string2, String string3, RealmFieldType realmFieldType, Set<RealmFieldType> set) {
        if (!set.contains((Object)realmFieldType)) {
            throw new IllegalArgumentException(String.format(Locale.US, "Invalid query: field '%s' in table '%s' is of invalid type '%s'.", string3, string2, realmFieldType.toString()));
        }
    }

    protected abstract void compileFieldDescription(List<String> var1);

    public final long[] getColumnIndices() {
        this.compileIfNecessary();
        return Arrays.copyOf(this.columnIndices, this.columnIndices.length);
    }

    public final long[] getNativeTablePointers() {
        this.compileIfNecessary();
        return Arrays.copyOf(this.nativeTablePointers, this.nativeTablePointers.length);
    }

    protected final void setCompilationResults(String string2, String string3, RealmFieldType realmFieldType, long[] arrl, long[] arrl2) {
        if (this.validFinalColumnTypes != null && this.validFinalColumnTypes.size() > 0) {
            this.verifyColumnType(string2, string3, realmFieldType, this.validFinalColumnTypes);
        }
        this.finalColumnName = string3;
        this.finalColumnType = realmFieldType;
        this.columnIndices = arrl;
        this.nativeTablePointers = arrl2;
    }

    protected final void verifyInternalColumnType(String string2, String string3, RealmFieldType realmFieldType) {
        this.verifyColumnType(string2, string3, realmFieldType, this.validInternalColumnTypes);
    }

    public static interface SchemaProxy {
        public ColumnInfo getColumnInfo(String var1);

        public long getNativeTablePtr(String var1);

        public boolean hasCache();
    }

}

