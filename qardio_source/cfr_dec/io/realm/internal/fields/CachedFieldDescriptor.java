/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal.fields;

import io.realm.RealmFieldType;
import io.realm.internal.ColumnInfo;
import io.realm.internal.fields.FieldDescriptor;
import java.util.List;
import java.util.Locale;
import java.util.Set;

class CachedFieldDescriptor
extends FieldDescriptor {
    private final String className;
    private final FieldDescriptor.SchemaProxy schema;

    CachedFieldDescriptor(FieldDescriptor.SchemaProxy schemaProxy, String string2, String string3, Set<RealmFieldType> set, Set<RealmFieldType> set2) {
        super(string3, set, set2);
        this.className = string2;
        this.schema = schemaProxy;
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void compileFieldDescription(List<String> list) {
        int n = list.size();
        long[] arrl = new long[n];
        long[] arrl2 = new long[n];
        String string2 = this.className;
        String string3 = null;
        RealmFieldType realmFieldType = null;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.setCompilationResults(this.className, string3, realmFieldType, arrl, arrl2);
                return;
            }
            string3 = list.get(n2);
            if (string3 == null || string3.length() <= 0) {
                throw new IllegalArgumentException("Invalid query: Field descriptor contains an empty field.  A field description may not begin with or contain adjacent periods ('.').");
            }
            ColumnInfo columnInfo = this.schema.getColumnInfo(string2);
            if (columnInfo == null) {
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid query: table '%s' not found in this schema.", string2));
            }
            long l = columnInfo.getColumnIndex(string3);
            if (l < 0L) {
                throw new IllegalArgumentException(String.format(Locale.US, "Invalid query: field '%s' not found in table '%s'.", string3, string2));
            }
            realmFieldType = columnInfo.getColumnType(string3);
            if (n2 < n - 1) {
                this.verifyInternalColumnType(string2, string3, realmFieldType);
            }
            string2 = columnInfo.getLinkedTable(string3);
            arrl[n2] = l;
            l = realmFieldType != RealmFieldType.LINKING_OBJECTS ? 0L : this.schema.getNativeTablePtr(string2);
            arrl2[n2] = l;
            ++n2;
        } while (true);
    }
}

