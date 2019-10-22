/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.internal.KeepMember;
import io.realm.internal.Table;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

@KeepMember
public class SortDescriptor {
    static final Set<RealmFieldType> DISTINCT_VALID_FIELD_TYPES;
    static final Set<RealmFieldType> SORT_VALID_FIELD_TYPES;
    private final boolean[] ascendings;
    private final long[][] columnIndices;
    private final Table table;

    static {
        SORT_VALID_FIELD_TYPES = Collections.unmodifiableSet(new HashSet<RealmFieldType>(Arrays.asList(new RealmFieldType[]{RealmFieldType.BOOLEAN, RealmFieldType.INTEGER, RealmFieldType.FLOAT, RealmFieldType.DOUBLE, RealmFieldType.STRING, RealmFieldType.DATE})));
        DISTINCT_VALID_FIELD_TYPES = Collections.unmodifiableSet(new HashSet<RealmFieldType>(Arrays.asList(new RealmFieldType[]{RealmFieldType.BOOLEAN, RealmFieldType.INTEGER, RealmFieldType.STRING, RealmFieldType.DATE})));
    }

    @KeepMember
    private long getTablePtr() {
        return this.table.getNativePtr();
    }

    @KeepMember
    boolean[] getAscendings() {
        return this.ascendings;
    }

    @KeepMember
    long[][] getColumnIndices() {
        return this.columnIndices;
    }
}

