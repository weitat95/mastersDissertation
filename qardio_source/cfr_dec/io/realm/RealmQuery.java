/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.BaseRealm;
import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmObjectSchema;
import io.realm.RealmResults;
import io.realm.RealmSchema;
import io.realm.internal.Collection;
import io.realm.internal.LinkView;
import io.realm.internal.SharedRealm;
import io.realm.internal.SortDescriptor;
import io.realm.internal.Table;
import io.realm.internal.TableQuery;
import io.realm.internal.fields.FieldDescriptor;

public class RealmQuery<E extends RealmModel> {
    private String className;
    private Class<E> clazz;
    private LinkView linkView;
    private final TableQuery query;
    private final BaseRealm realm;
    private final RealmObjectSchema schema;
    private final Table table;

    private RealmQuery(Realm realm, Class<E> class_) {
        this.realm = realm;
        this.clazz = class_;
        this.schema = realm.getSchema().getSchemaForClass(class_);
        this.table = this.schema.getTable();
        this.linkView = null;
        this.query = this.table.where();
    }

    public static <E extends RealmModel> RealmQuery<E> createQuery(Realm realm, Class<E> class_) {
        return new RealmQuery<E>(realm, class_);
    }

    /*
     * Enabled aggressive block sorting
     */
    private RealmResults<E> createRealmResults(TableQuery realmResults, SortDescriptor sortDescriptor, SortDescriptor sortDescriptor2, boolean bl) {
        realmResults = new Collection(this.realm.sharedRealm, (TableQuery)((Object)realmResults), sortDescriptor, sortDescriptor2);
        realmResults = this.isDynamicQuery() ? new RealmResults(this.realm, (Collection)((Object)realmResults), this.className) : new RealmResults<E>(this.realm, (Collection)((Object)realmResults), this.clazz);
        if (bl) {
            realmResults.load();
        }
        return realmResults;
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String object, Long l) {
        object = this.schema.getColumnIndices((String)object, RealmFieldType.INTEGER);
        if (l == null) {
            this.query.isNull(((FieldDescriptor)object).getColumnIndices(), ((FieldDescriptor)object).getNativeTablePointers());
            return this;
        }
        this.query.equalTo(((FieldDescriptor)object).getColumnIndices(), ((FieldDescriptor)object).getNativeTablePointers(), l);
        return this;
    }

    private RealmQuery<E> equalToWithoutThreadValidation(String object, String string2, Case case_) {
        object = this.schema.getColumnIndices((String)object, RealmFieldType.STRING);
        this.query.equalTo(((FieldDescriptor)object).getColumnIndices(), ((FieldDescriptor)object).getNativeTablePointers(), string2, case_);
        return this;
    }

    private long getSourceRowIndexForFirstObject() {
        return this.query.find();
    }

    private boolean isDynamicQuery() {
        return this.className != null;
    }

    public RealmQuery<E> equalTo(String string2, Long l) {
        this.realm.checkIfValid();
        return this.equalToWithoutThreadValidation(string2, l);
    }

    public RealmQuery<E> equalTo(String string2, String string3) {
        return this.equalTo(string2, string3, Case.SENSITIVE);
    }

    public RealmQuery<E> equalTo(String string2, String string3, Case case_) {
        this.realm.checkIfValid();
        return this.equalToWithoutThreadValidation(string2, string3, case_);
    }

    public RealmResults<E> findAll() {
        this.realm.checkIfValid();
        return this.createRealmResults(this.query, null, null, true);
    }

    public E findFirst() {
        this.realm.checkIfValid();
        long l = this.getSourceRowIndexForFirstObject();
        if (l < 0L) {
            return null;
        }
        return this.realm.get(this.clazz, this.className, l);
    }

    public RealmQuery<E> notEqualTo(String object, Integer n) {
        this.realm.checkIfValid();
        object = this.schema.getColumnIndices((String)object, RealmFieldType.INTEGER);
        if (n == null) {
            this.query.isNotNull(((FieldDescriptor)object).getColumnIndices(), ((FieldDescriptor)object).getNativeTablePointers());
            return this;
        }
        this.query.notEqualTo(((FieldDescriptor)object).getColumnIndices(), ((FieldDescriptor)object).getNativeTablePointers(), n.intValue());
        return this;
    }
}

