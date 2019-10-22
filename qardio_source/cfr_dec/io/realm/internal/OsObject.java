/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.ObjectChangeSet;
import io.realm.RealmFieldType;
import io.realm.RealmModel;
import io.realm.RealmObjectChangeListener;
import io.realm.exceptions.RealmException;
import io.realm.internal.KeepMember;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.ObserverPairList;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;

@KeepMember
public class OsObject
implements NativeObject {
    private static final long nativeFinalizerPtr = OsObject.nativeGetFinalizerPtr();
    private final long nativePtr;
    private ObserverPairList<ObjectObserverPair> observerPairs = new ObserverPairList();

    public OsObject(SharedRealm sharedRealm, UncheckedRow uncheckedRow) {
        this.nativePtr = OsObject.nativeCreate(sharedRealm.getNativePtr(), uncheckedRow.getNativePtr());
        sharedRealm.context.addReference(this);
    }

    public static UncheckedRow create(Table table) {
        SharedRealm sharedRealm = table.getSharedRealm();
        return new UncheckedRow(sharedRealm.context, table, OsObject.nativeCreateNewObject(sharedRealm.getNativePtr(), table.getNativePtr()));
    }

    /*
     * Enabled aggressive block sorting
     */
    public static UncheckedRow createWithPrimaryKey(Table table, Object object) {
        boolean bl;
        long l = OsObject.getAndVerifyPrimaryKeyColumnIndex(table);
        RealmFieldType realmFieldType = table.getColumnType(l);
        SharedRealm sharedRealm = table.getSharedRealm();
        if (realmFieldType == RealmFieldType.STRING) {
            if (object == null) return new UncheckedRow(sharedRealm.context, table, OsObject.nativeCreateNewObjectWithStringPrimaryKey(sharedRealm.getNativePtr(), table.getNativePtr(), l, (String)object));
            if (object instanceof String) return new UncheckedRow(sharedRealm.context, table, OsObject.nativeCreateNewObjectWithStringPrimaryKey(sharedRealm.getNativePtr(), table.getNativePtr(), l, (String)object));
            throw new IllegalArgumentException("Primary key value is not a String: " + object);
        }
        if (realmFieldType != RealmFieldType.INTEGER) throw new RealmException("Cannot check for duplicate rows for unsupported primary key type: " + (Object)((Object)realmFieldType));
        long l2 = object == null ? 0L : Long.parseLong(object.toString());
        NativeContext nativeContext = sharedRealm.context;
        long l3 = sharedRealm.getNativePtr();
        long l4 = table.getNativePtr();
        if (object == null) {
            bl = true;
            return new UncheckedRow(nativeContext, table, OsObject.nativeCreateNewObjectWithLongPrimaryKey(l3, l4, l, l2, bl));
        }
        bl = false;
        return new UncheckedRow(nativeContext, table, OsObject.nativeCreateNewObjectWithLongPrimaryKey(l3, l4, l, l2, bl));
    }

    private static long getAndVerifyPrimaryKeyColumnIndex(Table table) {
        long l = table.getPrimaryKey();
        if (l == -2L) {
            throw new IllegalStateException(table.getName() + " has no primary key defined.");
        }
        return l;
    }

    private static native long nativeCreate(long var0, long var2);

    private static native long nativeCreateNewObject(long var0, long var2);

    private static native long nativeCreateNewObjectWithLongPrimaryKey(long var0, long var2, long var4, long var6, boolean var8);

    private static native long nativeCreateNewObjectWithStringPrimaryKey(long var0, long var2, long var4, String var6);

    private static native long nativeCreateRow(long var0, long var2);

    private static native long nativeCreateRowWithLongPrimaryKey(long var0, long var2, long var4, long var6, boolean var8);

    private static native long nativeCreateRowWithStringPrimaryKey(long var0, long var2, long var4, String var6);

    private static native long nativeGetFinalizerPtr();

    private native void nativeStartListening(long var1);

    private native void nativeStopListening(long var1);

    @KeepMember
    private void notifyChangeListeners(String[] arrstring) {
        this.observerPairs.foreach(new Callback(arrstring));
    }

    @Override
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override
    public long getNativePtr() {
        return this.nativePtr;
    }

    public void setObserverPairs(ObserverPairList<ObjectObserverPair> observerPairList) {
        if (!this.observerPairs.isEmpty()) {
            throw new IllegalStateException("'observerPairs' is not empty. Listeners have been added before.");
        }
        this.observerPairs = observerPairList;
        if (!observerPairList.isEmpty()) {
            this.nativeStartListening(this.nativePtr);
        }
    }

    private static class Callback
    implements ObserverPairList.Callback<ObjectObserverPair> {
        private final String[] changedFields;

        Callback(String[] arrstring) {
            this.changedFields = arrstring;
        }

        /*
         * Enabled aggressive block sorting
         */
        private ObjectChangeSet createChangeSet() {
            String[] arrstring;
            boolean bl = this.changedFields == null;
            if (bl) {
                arrstring = new String[]{};
                return new OsObjectChangeSet(arrstring, bl);
            }
            arrstring = this.changedFields;
            return new OsObjectChangeSet(arrstring, bl);
        }

        @Override
        public void onCalled(ObjectObserverPair objectObserverPair, Object object) {
            objectObserverPair.onChange((RealmModel)object, this.createChangeSet());
        }
    }

    public static class ObjectObserverPair<T extends RealmModel>
    extends ObserverPairList.ObserverPair<T, RealmObjectChangeListener<T>> {
        public void onChange(T t, ObjectChangeSet objectChangeSet) {
            ((RealmObjectChangeListener)this.listener).onChange(t, objectChangeSet);
        }
    }

    private static class OsObjectChangeSet
    implements ObjectChangeSet {
        final String[] changedFields;
        final boolean deleted;

        OsObjectChangeSet(String[] arrstring, boolean bl) {
            this.changedFields = arrstring;
            this.deleted = bl;
        }
    }

}

