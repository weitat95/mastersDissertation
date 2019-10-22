/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.internal.Table;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public abstract class ColumnInfo {
    private final Map<String, ColumnDetails> indicesMap;
    private final boolean mutable;

    protected ColumnInfo(int n) {
        this(n, true);
    }

    private ColumnInfo(int n, boolean bl) {
        this.indicesMap = new HashMap<String, ColumnDetails>(n);
        this.mutable = bl;
    }

    /*
     * Enabled aggressive block sorting
     */
    protected ColumnInfo(ColumnInfo columnInfo, boolean bl) {
        int n = columnInfo == null ? 0 : columnInfo.indicesMap.size();
        this(n, bl);
        if (columnInfo != null) {
            this.indicesMap.putAll(columnInfo.indicesMap);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    protected final long addColumnDetails(Table object, String string2, RealmFieldType realmFieldType) {
        long l = ((Table)object).getColumnIndex(string2);
        if (l >= 0L) {
            object = realmFieldType != RealmFieldType.OBJECT && realmFieldType != RealmFieldType.LIST ? null : ((Table)object).getLinkTarget(l).getClassName();
            this.indicesMap.put(string2, new ColumnDetails(l, realmFieldType, (String)object));
        }
        return l;
    }

    protected abstract ColumnInfo copy(boolean var1);

    protected abstract void copy(ColumnInfo var1, ColumnInfo var2);

    public void copyFrom(ColumnInfo columnInfo) {
        if (!this.mutable) {
            throw new UnsupportedOperationException("Attempt to modify an immutable ColumnInfo");
        }
        if (columnInfo == null) {
            throw new NullPointerException("Attempt to copy null ColumnInfo");
        }
        this.indicesMap.clear();
        this.indicesMap.putAll(columnInfo.indicesMap);
        this.copy(columnInfo, this);
    }

    public long getColumnIndex(String object) {
        if ((object = this.indicesMap.get(object)) == null) {
            return -1L;
        }
        return ((ColumnDetails)object).columnIndex;
    }

    public RealmFieldType getColumnType(String object) {
        if ((object = this.indicesMap.get(object)) == null) {
            return RealmFieldType.UNSUPPORTED_TABLE;
        }
        return ((ColumnDetails)object).columnType;
    }

    public String getLinkedTable(String object) {
        if ((object = this.indicesMap.get(object)) == null) {
            return null;
        }
        return ((ColumnDetails)object).linkTable;
    }

    public final boolean isMutable() {
        return this.mutable;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder("ColumnInfo[");
        stringBuilder.append(this.mutable).append(",");
        if (this.indicesMap != null) {
            boolean bl = false;
            for (Map.Entry<String, ColumnDetails> entry : this.indicesMap.entrySet()) {
                if (bl) {
                    stringBuilder.append(",");
                }
                stringBuilder.append(entry.getKey()).append("->").append(entry.getValue());
                bl = true;
            }
        }
        return stringBuilder.append("]").toString();
    }

    private static final class ColumnDetails {
        public final long columnIndex;
        public final RealmFieldType columnType;
        public final String linkTable;

        ColumnDetails(long l, RealmFieldType realmFieldType, String string2) {
            this.columnIndex = l;
            this.columnType = realmFieldType;
            this.linkTable = string2;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder("ColumnDetails[");
            stringBuilder.append(this.columnIndex);
            stringBuilder.append(", ").append((Object)this.columnType);
            stringBuilder.append(", ").append(this.linkTable);
            return stringBuilder.append("]").toString();
        }
    }

}

