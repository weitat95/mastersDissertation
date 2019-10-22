/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.internal.Keep;
import java.nio.ByteBuffer;
import java.util.Date;

@Keep
public enum RealmFieldType {
    INTEGER(0),
    BOOLEAN(1),
    STRING(2),
    BINARY(4),
    UNSUPPORTED_TABLE(5),
    UNSUPPORTED_MIXED(6),
    UNSUPPORTED_DATE(7),
    DATE(8),
    FLOAT(9),
    DOUBLE(10),
    OBJECT(12),
    LIST(13),
    LINKING_OBJECTS(14);

    private static final RealmFieldType[] typeList;
    private final int nativeValue;

    static {
        typeList = new RealmFieldType[15];
        RealmFieldType[] arrrealmFieldType = RealmFieldType.values();
        for (int i = 0; i < arrrealmFieldType.length; ++i) {
            RealmFieldType.typeList[arrrealmFieldType[i].nativeValue] = arrrealmFieldType[i];
        }
    }

    private RealmFieldType(int n2) {
        this.nativeValue = n2;
    }

    public static RealmFieldType fromNativeValue(int n) {
        RealmFieldType realmFieldType;
        if (n >= 0 && n < typeList.length && (realmFieldType = typeList[n]) != null) {
            return realmFieldType;
        }
        throw new IllegalArgumentException("Invalid native Realm type: " + n);
    }

    public int getNativeValue() {
        return this.nativeValue;
    }

    /*
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    public boolean isValid(Object object) {
        boolean bl;
        boolean bl2 = bl = false;
        switch (this.nativeValue) {
            default: {
                throw new RuntimeException("Unsupported Realm type:  " + (Object)((Object)this));
            }
            case 0: {
                if (!(object instanceof Long || object instanceof Integer || object instanceof Short)) {
                    bl2 = bl;
                    if (!(object instanceof Byte)) return bl2;
                }
                bl2 = true;
            }
            case 12: 
            case 13: 
            case 14: {
                return bl2;
            }
            case 1: {
                return object instanceof Boolean;
            }
            case 2: {
                return object instanceof String;
            }
            case 4: {
                if (object instanceof byte[]) return true;
                bl2 = bl;
                if (!(object instanceof ByteBuffer)) return bl2;
                return true;
            }
            case 5: {
                if (object == null) return true;
                bl2 = bl;
                if (!(object instanceof Object[][])) return bl2;
                return true;
            }
            case 7: {
                return object instanceof Date;
            }
            case 8: {
                return object instanceof Date;
            }
            case 9: {
                return object instanceof Float;
            }
            case 10: 
        }
        return object instanceof Double;
    }
}

