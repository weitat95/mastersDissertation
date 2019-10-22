/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmFieldType;
import io.realm.internal.NativeContext;
import io.realm.internal.NativeObject;
import io.realm.internal.Property;
import java.util.ArrayList;
import java.util.List;

public class OsObjectSchemaInfo
implements NativeObject {
    private static final long nativeFinalizerPtr = OsObjectSchemaInfo.nativeGetFinalizerPtr();
    private long nativePtr;

    private OsObjectSchemaInfo(long l) {
        this.nativePtr = l;
        NativeContext.dummyContext.addReference(this);
    }

    private OsObjectSchemaInfo(String string2) {
        this(OsObjectSchemaInfo.nativeCreateRealmObjectSchema(string2));
    }

    private static native void nativeAddProperty(long var0, long var2);

    private static native long nativeCreateRealmObjectSchema(String var0);

    private static native String nativeGetClassName(long var0);

    private static native long nativeGetFinalizerPtr();

    @Override
    public long getNativeFinalizerPtr() {
        return nativeFinalizerPtr;
    }

    @Override
    public long getNativePtr() {
        return this.nativePtr;
    }

    public static class Builder {
        private String className;
        private List<Property> propertyList = new ArrayList<Property>();

        public Builder(String string2) {
            this.className = string2;
        }

        public Builder addLinkedProperty(String object, RealmFieldType realmFieldType, String string2) {
            object = new Property((String)object, realmFieldType, string2);
            this.propertyList.add((Property)object);
            return this;
        }

        public Builder addProperty(String object, RealmFieldType realmFieldType, boolean bl, boolean bl2, boolean bl3) {
            object = new Property((String)object, realmFieldType, bl, bl2, bl3);
            this.propertyList.add((Property)object);
            return this;
        }

        public OsObjectSchemaInfo build() {
            OsObjectSchemaInfo osObjectSchemaInfo = new OsObjectSchemaInfo(this.className);
            for (Property property : this.propertyList) {
                OsObjectSchemaInfo.nativeAddProperty(osObjectSchemaInfo.nativePtr, property.getNativePtr());
            }
            return osObjectSchemaInfo;
        }
    }

}

