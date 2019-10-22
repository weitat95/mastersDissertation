/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.BaseRealm;
import io.realm.ProxyState;
import io.realm.RealmFieldType;
import io.realm.RealmObject;
import io.realm.internal.LinkView;
import io.realm.internal.RealmObjectProxy;
import io.realm.internal.Row;
import io.realm.internal.Table;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

public class DynamicRealmObject
extends RealmObject
implements RealmObjectProxy {
    private final ProxyState<DynamicRealmObject> proxyState = new ProxyState<DynamicRealmObject>(this);

    DynamicRealmObject(BaseRealm baseRealm, Row row) {
        this.proxyState.setRealm$realm(baseRealm);
        this.proxyState.setRow$realm(row);
        this.proxyState.setConstructionFinished();
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        boolean bl = true;
        boolean bl2 = false;
        this.proxyState.getRealm$realm().checkIfValid();
        if (this == object) {
            return true;
        }
        boolean bl3 = bl2;
        if (object == null) return bl3;
        bl3 = bl2;
        if (this.getClass() != object.getClass()) return bl3;
        object = (DynamicRealmObject)object;
        String string2 = this.proxyState.getRealm$realm().getPath();
        String string3 = ((DynamicRealmObject)object).proxyState.getRealm$realm().getPath();
        if (string2 != null) {
            bl3 = bl2;
            if (!string2.equals(string3)) return bl3;
        } else if (string3 != null) {
            return false;
        }
        string2 = this.proxyState.getRow$realm().getTable().getName();
        string3 = ((DynamicRealmObject)object).proxyState.getRow$realm().getTable().getName();
        if (string2 != null) {
            bl3 = bl2;
            if (!string2.equals(string3)) return bl3;
        } else if (string3 != null) {
            return false;
        }
        if (this.proxyState.getRow$realm().getIndex() != ((DynamicRealmObject)object).proxyState.getRow$realm().getIndex()) return false;
        return bl;
    }

    public String[] getFieldNames() {
        this.proxyState.getRealm$realm().checkIfValid();
        String[] arrstring = new String[(int)this.proxyState.getRow$realm().getColumnCount()];
        for (int i = 0; i < arrstring.length; ++i) {
            arrstring[i] = this.proxyState.getRow$realm().getColumnName(i);
        }
        return arrstring;
    }

    public String getType() {
        this.proxyState.getRealm$realm().checkIfValid();
        return this.proxyState.getRow$realm().getTable().getClassName();
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        this.proxyState.getRealm$realm().checkIfValid();
        String string2 = this.proxyState.getRealm$realm().getPath();
        String string3 = this.proxyState.getRow$realm().getTable().getName();
        long l = this.proxyState.getRow$realm().getIndex();
        int n2 = string2 != null ? string2.hashCode() : 0;
        if (string3 != null) {
            n = string3.hashCode();
        }
        return ((n2 + 527) * 31 + n) * 31 + (int)(l >>> 32 ^ l);
    }

    @Override
    public void realm$injectObjectContext() {
    }

    @Override
    public ProxyState realmGet$proxyState() {
        return this.proxyState;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public String toString() {
        this.proxyState.getRealm$realm().checkIfValid();
        if (!this.proxyState.getRow$realm().isAttached()) {
            return "Invalid object";
        }
        String string2 = this.proxyState.getRow$realm().getTable().getClassName();
        StringBuilder stringBuilder = new StringBuilder(string2 + " = dynamic[");
        String[] arrstring = this.getFieldNames();
        int n = arrstring.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                stringBuilder.replace(stringBuilder.length() - 1, stringBuilder.length(), "");
                stringBuilder.append("]");
                return stringBuilder.toString();
            }
            String string3 = arrstring[n2];
            long l = this.proxyState.getRow$realm().getColumnIndex(string3);
            RealmFieldType realmFieldType = this.proxyState.getRow$realm().getColumnType(l);
            stringBuilder.append("{");
            stringBuilder.append(string3).append(":");
            switch (realmFieldType) {
                default: {
                    stringBuilder.append("?");
                    break;
                }
                case BOOLEAN: {
                    void var5_6;
                    if (this.proxyState.getRow$realm().isNull(l)) {
                        String string4 = "null";
                    } else {
                        Boolean bl = this.proxyState.getRow$realm().getBoolean(l);
                    }
                    stringBuilder.append(var5_6);
                    break;
                }
                case INTEGER: {
                    void var5_9;
                    if (this.proxyState.getRow$realm().isNull(l)) {
                        String string5 = "null";
                    } else {
                        Long l2 = this.proxyState.getRow$realm().getLong(l);
                    }
                    stringBuilder.append(var5_9);
                    break;
                }
                case FLOAT: {
                    void var5_12;
                    if (this.proxyState.getRow$realm().isNull(l)) {
                        String string6 = "null";
                    } else {
                        Float f = Float.valueOf(this.proxyState.getRow$realm().getFloat(l));
                    }
                    stringBuilder.append(var5_12);
                    break;
                }
                case DOUBLE: {
                    void var5_15;
                    if (this.proxyState.getRow$realm().isNull(l)) {
                        String string7 = "null";
                    } else {
                        Double d = this.proxyState.getRow$realm().getDouble(l);
                    }
                    stringBuilder.append(var5_15);
                    break;
                }
                case STRING: {
                    stringBuilder.append(this.proxyState.getRow$realm().getString(l));
                    break;
                }
                case BINARY: {
                    stringBuilder.append(Arrays.toString(this.proxyState.getRow$realm().getBinaryByteArray(l)));
                    break;
                }
                case DATE: {
                    void var5_18;
                    if (this.proxyState.getRow$realm().isNull(l)) {
                        String string8 = "null";
                    } else {
                        Date date = this.proxyState.getRow$realm().getDate(l);
                    }
                    stringBuilder.append(var5_18);
                    break;
                }
                case OBJECT: {
                    void var5_21;
                    if (this.proxyState.getRow$realm().isNullLink(l)) {
                        String string9 = "null";
                    } else {
                        String string10 = this.proxyState.getRow$realm().getTable().getLinkTarget(l).getClassName();
                    }
                    stringBuilder.append((String)var5_21);
                    break;
                }
                case LIST: {
                    String string11 = this.proxyState.getRow$realm().getTable().getLinkTarget(l).getClassName();
                    stringBuilder.append(String.format(Locale.US, "RealmList<%s>[%s]", string11, this.proxyState.getRow$realm().getLinkList(l).size()));
                }
            }
            stringBuilder.append("},");
            ++n2;
        } while (true);
    }

}

