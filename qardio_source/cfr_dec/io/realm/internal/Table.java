/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.exceptions.RealmPrimaryKeyConstraintException;
import io.realm.internal.CheckedRow;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.SharedRealm;
import io.realm.internal.TableQuery;
import io.realm.internal.UncheckedRow;
import io.realm.internal.Util;

public class Table
implements NativeObject {
    private static final String TABLE_PREFIX = Util.getTablePrefix();
    private static final long nativeFinalizerPtr = Table.nativeGetFinalizerPtr();
    private long cachedPrimaryKeyColumnIndex = -1L;
    private final NativeContext context;
    private final long nativePtr;
    private final SharedRealm sharedRealm;

    Table(SharedRealm sharedRealm, long l) {
        this.context = sharedRealm.context;
        this.sharedRealm = sharedRealm;
        this.nativePtr = l;
        this.context.addReference(this);
    }

    Table(Table table, long l) {
        this(table.sharedRealm, l);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getClassNameForTable(String string2) {
        if (string2 == null) {
            return null;
        }
        String string3 = string2;
        if (!string2.startsWith(TABLE_PREFIX)) return string3;
        return string2.substring(TABLE_PREFIX.length());
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private Table getPrimaryKeyTable() {
        Table table;
        if (this.sharedRealm == null) {
            return null;
        }
        if (!this.sharedRealm.hasTable("pk")) {
            this.sharedRealm.createTable("pk");
        }
        Table table2 = table = this.sharedRealm.getTable("pk");
        if (table.getColumnCount() != 0L) return table2;
        this.checkImmutable();
        table.addSearchIndex(table.addColumn(RealmFieldType.STRING, "pk_table"));
        table.addColumn(RealmFieldType.STRING, "pk_property");
        return table;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static String getTableNameForClass(String string2) {
        if (string2 == null) {
            return null;
        }
        String string3 = string2;
        if (string2.startsWith(TABLE_PREFIX)) return string3;
        return TABLE_PREFIX + string2;
    }

    private boolean isPrimaryKey(long l) {
        return l >= 0L && l == this.getPrimaryKey();
    }

    private boolean isPrimaryKeyColumn(long l) {
        return l == this.getPrimaryKey();
    }

    public static boolean migratePrimaryKeyTableIfNeeded(SharedRealm sharedRealm) {
        if (sharedRealm == null || !sharedRealm.isInTransaction()) {
            Table.throwImmutable();
        }
        if (!sharedRealm.hasTable("pk")) {
            return false;
        }
        Table table = sharedRealm.getTable("pk");
        return Table.nativeMigratePrimaryKeyTableIfNeeded(sharedRealm.getGroupNative(), table.nativePtr);
    }

    private native long nativeAddColumn(long var1, int var3, String var4, boolean var5);

    private native long nativeAddColumnLink(long var1, int var3, String var4, long var5);

    private native void nativeAddSearchIndex(long var1, long var3);

    private native void nativeClear(long var1);

    private native void nativeConvertColumnToNotNullable(long var1, long var3, boolean var5);

    private native void nativeConvertColumnToNullable(long var1, long var3, boolean var5);

    private native long nativeCountDouble(long var1, long var3, double var5);

    private native long nativeCountFloat(long var1, long var3, float var5);

    private native long nativeCountLong(long var1, long var3, long var5);

    private native long nativeCountString(long var1, long var3, String var5);

    private native long nativeFindFirstBool(long var1, long var3, boolean var5);

    private native long nativeFindFirstDouble(long var1, long var3, double var5);

    private native long nativeFindFirstFloat(long var1, long var3, float var5);

    public static native long nativeFindFirstInt(long var0, long var2, long var4);

    public static native long nativeFindFirstNull(long var0, long var2);

    public static native long nativeFindFirstString(long var0, long var2, String var4);

    private native long nativeFindFirstTimestamp(long var1, long var3, long var5);

    private native boolean nativeGetBoolean(long var1, long var3, long var5);

    private native byte[] nativeGetByteArray(long var1, long var3, long var5);

    private native long nativeGetColumnCount(long var1);

    private native long nativeGetColumnIndex(long var1, String var3);

    private native String nativeGetColumnName(long var1, long var3);

    private native int nativeGetColumnType(long var1, long var3);

    private native double nativeGetDouble(long var1, long var3, long var5);

    private static native long nativeGetFinalizerPtr();

    private native float nativeGetFloat(long var1, long var3, long var5);

    private native long nativeGetLink(long var1, long var3, long var5);

    private native long nativeGetLinkTarget(long var1, long var3);

    public static native long nativeGetLinkView(long var0, long var2, long var4);

    private native long nativeGetLong(long var1, long var3, long var5);

    private native String nativeGetName(long var1);

    private native long nativeGetSortedViewMulti(long var1, long[] var3, boolean[] var4);

    private native String nativeGetString(long var1, long var3, long var5);

    private native long nativeGetTimestamp(long var1, long var3, long var5);

    private native boolean nativeHasSameSchema(long var1, long var3);

    private native boolean nativeHasSearchIndex(long var1, long var3);

    private native boolean nativeIsColumnNullable(long var1, long var3);

    private native boolean nativeIsNull(long var1, long var3, long var5);

    private native boolean nativeIsNullLink(long var1, long var3, long var5);

    private native boolean nativeIsValid(long var1);

    private native long nativeLowerBoundInt(long var1, long var3, long var5);

    private static native boolean nativeMigratePrimaryKeyTableIfNeeded(long var0, long var2);

    private native void nativeMoveLastOver(long var1, long var3);

    public static native void nativeNullifyLink(long var0, long var2, long var4);

    private static native boolean nativePrimaryKeyTableNeedsMigration(long var0);

    private native void nativeRemoveColumn(long var1, long var3);

    private native void nativeRemoveSearchIndex(long var1, long var3);

    private native void nativeRenameColumn(long var1, long var3, String var5);

    public static native void nativeSetBoolean(long var0, long var2, long var4, boolean var6, boolean var7);

    public static native void nativeSetByteArray(long var0, long var2, long var4, byte[] var6, boolean var7);

    public static native void nativeSetDouble(long var0, long var2, long var4, double var6, boolean var8);

    public static native void nativeSetFloat(long var0, long var2, long var4, float var6, boolean var7);

    public static native void nativeSetLink(long var0, long var2, long var4, long var6, boolean var8);

    public static native void nativeSetLong(long var0, long var2, long var4, long var6, boolean var8);

    public static native void nativeSetLongUnique(long var0, long var2, long var4, long var6);

    public static native void nativeSetNull(long var0, long var2, long var4, boolean var6);

    public static native void nativeSetNullUnique(long var0, long var2, long var4);

    private native long nativeSetPrimaryKey(long var1, long var3, String var5);

    public static native void nativeSetString(long var0, long var2, long var4, String var6, boolean var7);

    public static native void nativeSetStringUnique(long var0, long var2, long var4, String var6);

    public static native void nativeSetTimestamp(long var0, long var2, long var4, long var6, boolean var8);

    private native long nativeSize(long var1);

    private native String nativeToJson(long var1);

    private native long nativeUpperBoundInt(long var1, long var3, long var5);

    private native long nativeVersion(long var1);

    private native long nativeWhere(long var1);

    public static boolean primaryKeyTableNeedsMigration(SharedRealm sharedRealm) {
        if (!sharedRealm.hasTable("pk")) {
            return false;
        }
        return Table.nativePrimaryKeyTableNeedsMigration(sharedRealm.getTable((String)"pk").nativePtr);
    }

    public static void throwDuplicatePrimaryKeyException(Object object) {
        throw new RealmPrimaryKeyConstraintException("Value already exists: " + object);
    }

    private static void throwImmutable() {
        throw new IllegalStateException("Cannot modify managed objects outside of a write transaction.");
    }

    private void verifyColumnName(String string2) {
        if (string2.length() > 63) {
            throw new IllegalArgumentException("Column names are currently limited to max 63 characters.");
        }
    }

    public long addColumn(RealmFieldType realmFieldType, String string2) {
        return this.addColumn(realmFieldType, string2, false);
    }

    public long addColumn(RealmFieldType realmFieldType, String string2, boolean bl) {
        this.verifyColumnName(string2);
        return this.nativeAddColumn(this.nativePtr, realmFieldType.getNativeValue(), string2, bl);
    }

    public void addSearchIndex(long l) {
        this.checkImmutable();
        this.nativeAddSearchIndex(this.nativePtr, l);
    }

    /*
     * Enabled aggressive block sorting
     */
    void checkDuplicatedNullForPrimaryKeyValue(long l, long l2) {
        if (!this.isPrimaryKeyColumn(l)) return;
        {
            RealmFieldType realmFieldType = this.getColumnType(l);
            switch (1.$SwitchMap$io$realm$RealmFieldType[realmFieldType.ordinal()]) {
                default: {
                    return;
                }
                case 1: 
                case 2: {
                    if ((l = this.findFirstNull(l)) == l2 || l == -1L) return;
                    Table.throwDuplicatePrimaryKeyException("null");
                    return;
                }
            }
        }
    }

    void checkImmutable() {
        if (this.isImmutable()) {
            Table.throwImmutable();
        }
    }

    void checkIntValueIsLegal(long l, long l2, long l3) {
        if (this.isPrimaryKeyColumn(l) && (l = this.findFirstLong(l, l3)) != l2 && l != -1L) {
            Table.throwDuplicatePrimaryKeyException(l3);
        }
    }

    void checkStringValueIsLegal(long l, long l2, String string2) {
        if (this.isPrimaryKey(l) && (l = this.findFirstString(l, string2)) != l2 && l != -1L) {
            Table.throwDuplicatePrimaryKeyException(string2);
        }
    }

    public long findFirstLong(long l, long l2) {
        return Table.nativeFindFirstInt(this.nativePtr, l, l2);
    }

    public long findFirstNull(long l) {
        return Table.nativeFindFirstNull(this.nativePtr, l);
    }

    public long findFirstString(long l, String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("null is not supported");
        }
        return Table.nativeFindFirstString(this.nativePtr, l, string2);
    }

    public CheckedRow getCheckedRow(long l) {
        return CheckedRow.get(this.context, this, l);
    }

    public String getClassName() {
        return Table.getClassNameForTable(this.getName());
    }

    public long getColumnCount() {
        return this.nativeGetColumnCount(this.nativePtr);
    }

    public long getColumnIndex(String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("Column name can not be null.");
        }
        return this.nativeGetColumnIndex(this.nativePtr, string2);
    }

    public String getColumnName(long l) {
        return this.nativeGetColumnName(this.nativePtr, l);
    }

    public RealmFieldType getColumnType(long l) {
        return RealmFieldType.fromNativeValue(this.nativeGetColumnType(this.nativePtr, l));
    }

    public Table getLinkTarget(long l) {
        l = this.nativeGetLinkTarget(this.nativePtr, l);
        return new Table(this.sharedRealm, l);
    }

    public String getName() {
        return this.nativeGetName(this.nativePtr);
    }

    @Override
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override
    public long getNativePtr() {
        return this.nativePtr;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public long getPrimaryKey() {
        long l = -2L;
        if (this.cachedPrimaryKeyColumnIndex >= 0L) return this.cachedPrimaryKeyColumnIndex;
        if (this.cachedPrimaryKeyColumnIndex == -2L) {
            return this.cachedPrimaryKeyColumnIndex;
        }
        Table table = this.getPrimaryKeyTable();
        if (table == null) return l;
        l = table.findFirstString(0L, this.getClassName());
        if (l != -1L) {
            this.cachedPrimaryKeyColumnIndex = this.getColumnIndex(table.getUncheckedRow(l).getString(1L));
            do {
                return this.cachedPrimaryKeyColumnIndex;
                break;
            } while (true);
        }
        this.cachedPrimaryKeyColumnIndex = -2L;
        return this.cachedPrimaryKeyColumnIndex;
    }

    SharedRealm getSharedRealm() {
        return this.sharedRealm;
    }

    public UncheckedRow getUncheckedRow(long l) {
        return UncheckedRow.getByRowIndex(this.context, this, l);
    }

    public UncheckedRow getUncheckedRowByPointer(long l) {
        return UncheckedRow.getByRowPointer(this.context, this, l);
    }

    public boolean hasPrimaryKey() {
        return this.getPrimaryKey() >= 0L;
    }

    public boolean hasSameSchema(Table table) {
        if (table == null) {
            throw new IllegalArgumentException("The argument cannot be null");
        }
        return this.nativeHasSameSchema(this.nativePtr, table.nativePtr);
    }

    public boolean hasSearchIndex(long l) {
        return this.nativeHasSearchIndex(this.nativePtr, l);
    }

    public boolean isColumnNullable(long l) {
        return this.nativeIsColumnNullable(this.nativePtr, l);
    }

    boolean isImmutable() {
        return this.sharedRealm != null && !this.sharedRealm.isInTransaction();
    }

    public void moveLastOver(long l) {
        this.checkImmutable();
        this.nativeMoveLastOver(this.nativePtr, l);
    }

    native long nativeGetRowPtr(long var1, long var3);

    public void setBoolean(long l, long l2, boolean bl, boolean bl2) {
        this.checkImmutable();
        Table.nativeSetBoolean(this.nativePtr, l, l2, bl, bl2);
    }

    public void setFloat(long l, long l2, float f, boolean bl) {
        this.checkImmutable();
        Table.nativeSetFloat(this.nativePtr, l, l2, f, bl);
    }

    public void setLink(long l, long l2, long l3, boolean bl) {
        this.checkImmutable();
        Table.nativeSetLink(this.nativePtr, l, l2, l3, bl);
    }

    public void setLong(long l, long l2, long l3, boolean bl) {
        this.checkImmutable();
        this.checkIntValueIsLegal(l, l2, l3);
        Table.nativeSetLong(this.nativePtr, l, l2, l3, bl);
    }

    public void setNull(long l, long l2, boolean bl) {
        this.checkImmutable();
        this.checkDuplicatedNullForPrimaryKeyValue(l, l2);
        Table.nativeSetNull(this.nativePtr, l, l2, bl);
    }

    public void setString(long l, long l2, String string2, boolean bl) {
        this.checkImmutable();
        if (string2 == null) {
            this.checkDuplicatedNullForPrimaryKeyValue(l, l2);
            Table.nativeSetNull(this.nativePtr, l, l2, bl);
            return;
        }
        this.checkStringValueIsLegal(l, l2, string2);
        Table.nativeSetString(this.nativePtr, l, l2, string2, bl);
    }

    public long size() {
        return this.nativeSize(this.nativePtr);
    }

    public String toString() {
        long l = this.getColumnCount();
        String string2 = this.getName();
        StringBuilder stringBuilder = new StringBuilder("The Table ");
        if (string2 != null && !string2.isEmpty()) {
            stringBuilder.append(this.getName());
            stringBuilder.append(" ");
        }
        if (this.hasPrimaryKey()) {
            string2 = this.getColumnName(this.getPrimaryKey());
            stringBuilder.append("has '").append(string2).append("' field as a PrimaryKey, and ");
        }
        stringBuilder.append("contains ");
        stringBuilder.append(l);
        stringBuilder.append(" columns: ");
        int n = 0;
        while ((long)n < l) {
            if (n != 0) {
                stringBuilder.append(", ");
            }
            stringBuilder.append(this.getColumnName(n));
            ++n;
        }
        stringBuilder.append(".");
        stringBuilder.append(" And ");
        stringBuilder.append(this.size());
        stringBuilder.append(" rows.");
        return stringBuilder.toString();
    }

    public TableQuery where() {
        long l = this.nativeWhere(this.nativePtr);
        return new TableQuery(this.context, this, l);
    }

}

