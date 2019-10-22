/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal.fields;

import io.realm.RealmFieldType;
import io.realm.internal.Table;
import io.realm.internal.fields.FieldDescriptor;
import java.util.List;
import java.util.Locale;
import java.util.Set;

class DynamicFieldDescriptor
extends FieldDescriptor {
    private final Table table;

    DynamicFieldDescriptor(Table table, String string2, Set<RealmFieldType> set, Set<RealmFieldType> set2) {
        super(string2, set, set2);
        this.table = table;
    }

    @Override
    protected void compileFieldDescription(List<String> list) {
        int n = list.size();
        long[] arrl = new long[n];
        Table table = this.table;
        String string2 = null;
        String string3 = null;
        RealmFieldType realmFieldType = null;
        for (int i = 0; i < n; ++i) {
            string3 = list.get(i);
            if (string3 == null || string3.length() <= 0) {
                throw new IllegalArgumentException("Invalid query: Field descriptor contains an empty field.  A field description may not begin with or contain adjacent periods ('.').");
            }
            string2 = table.getClassName();
            long l = table.getColumnIndex(string3);
            if (l < 0L) {
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid query: field '%s' not found in table '%s'.", string3, string2));
            }
            realmFieldType = table.getColumnType(l);
            Table table2 = table;
            if (i < n - 1) {
                this.verifyInternalColumnType(string2, string3, realmFieldType);
                table2 = table.getLinkTarget(l);
            }
            arrl[i] = l;
            table = table2;
        }
        this.setCompilationResults(string2, string3, realmFieldType, arrl, new long[n]);
    }
}

