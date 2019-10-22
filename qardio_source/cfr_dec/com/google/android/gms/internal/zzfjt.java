/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzfjs;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public final class zzfjt {
    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static void zza(String object, Object object2, StringBuffer stringBuffer, StringBuffer stringBuffer2) throws IllegalAccessException, InvocationTargetException {
        if (object2 == null) return;
        if (object2 instanceof zzfjs) {
            n2 = stringBuffer.length();
            if (object != null) {
                stringBuffer2.append(stringBuffer).append(zzfjt.zzty((String)object)).append(" <\n");
                stringBuffer.append("  ");
            }
            class_ = object2.getClass();
            object5 = class_.getFields();
            n4 = ((Field[])object5).length;
        } else {
            object = zzfjt.zzty((String)object);
            stringBuffer2.append(stringBuffer).append((String)object).append(": ");
            if (object2 instanceof String) {
                object = object2 = (String)object2;
                if (!object2.startsWith("http")) {
                    object = object2;
                    if (object2.length() > 200) {
                        object = String.valueOf(object2.substring(0, 200)).concat("[...]");
                    }
                }
                object = zzfjt.zzgr((String)object);
                stringBuffer2.append("\"").append((String)object).append("\"");
            } else if (object2 instanceof byte[]) {
                zzfjt.zza((byte[])object2, stringBuffer2);
            } else {
                stringBuffer2.append(object2);
            }
            stringBuffer2.append("\n");
            return;
        }
        for (n = 0; n < n4; ++n) {
            object6 = object5[n];
            n3 = object6.getModifiers();
            object4 = object6.getName();
            if ("cachedSize".equals(object4) || (n3 & 1) != 1 || (n3 & 8) == 8 || object4.startsWith("_") || object4.endsWith("_")) continue;
            object3 = object6.getType();
            object6 = object6.get(object2);
            if (object3.isArray() && object3.getComponentType() != Byte.TYPE) {
                n3 = object6 == null ? 0 : Array.getLength(object6);
                for (i = 0; i < n3; ++i) {
                    zzfjt.zza((String)object4, Array.get(object6, i), stringBuffer, stringBuffer2);
                }
                continue;
            }
            zzfjt.zza((String)object4, object6, stringBuffer, stringBuffer2);
        }
        object4 = class_.getMethods();
        n3 = ((Method[])object4).length;
        n = 0;
        do {
            block20: {
                if (n >= n3) {
                    if (object == null) return;
                    stringBuffer.setLength(n2);
                    stringBuffer2.append(stringBuffer).append(">\n");
                    return;
                }
                object5 = object4[n].getName();
                if (!object5.startsWith("set")) break block20;
                object3 = object5.substring(3);
                object5 = String.valueOf(object3);
                object5 = object5.length() != 0 ? "has".concat((String)object5) : new String("has");
                if (!((Boolean)(object5 = class_.getMethod((String)object5, new Class[0])).invoke(object2, new Object[0])).booleanValue()) ** GOTO lbl81
                {
                    catch (NoSuchMethodException noSuchMethodException) {}
                }
                try {
                    block22: {
                        block21: {
                            object5 = String.valueOf(object3);
                            if (object5.length() == 0) break block21;
                            object5 = "get".concat((String)object5);
                            break block22;
                            break block20;
                        }
                        object5 = new String("get");
                    }
                    object5 = class_.getMethod((String)object5, new Class[0]);
                }
                catch (NoSuchMethodException noSuchMethodException) {}
                zzfjt.zza((String)object3, object5.invoke(object2, new Object[0]), stringBuffer, stringBuffer2);
            }
            ++n;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void zza(byte[] arrby, StringBuffer stringBuffer) {
        if (arrby == null) {
            stringBuffer.append("\"\"");
            return;
        }
        stringBuffer.append('\"');
        int n = 0;
        do {
            if (n >= arrby.length) {
                stringBuffer.append('\"');
                return;
            }
            int n2 = arrby[n] & 0xFF;
            if (n2 == 92 || n2 == 34) {
                stringBuffer.append('\\').append((char)n2);
            } else if (n2 >= 32 && n2 < 127) {
                stringBuffer.append((char)n2);
            } else {
                stringBuffer.append(String.format("\\%03o", n2));
            }
            ++n;
        } while (true);
    }

    public static <T extends zzfjs> String zzd(T t) {
        if (t == null) {
            return "";
        }
        StringBuffer stringBuffer = new StringBuffer();
        try {
            zzfjt.zza(null, t, new StringBuffer(), stringBuffer);
            return stringBuffer.toString();
        }
        catch (IllegalAccessException illegalAccessException) {
            String string2 = String.valueOf(illegalAccessException.getMessage());
            if (string2.length() != 0) {
                return "Error printing proto: ".concat(string2);
            }
            return new String("Error printing proto: ");
        }
        catch (InvocationTargetException invocationTargetException) {
            String string3 = String.valueOf(invocationTargetException.getMessage());
            if (string3.length() != 0) {
                return "Error printing proto: ".concat(string3);
            }
            return new String("Error printing proto: ");
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String zzgr(String string2) {
        int n = string2.length();
        StringBuilder stringBuilder = new StringBuilder(n);
        int n2 = 0;
        while (n2 < n) {
            char c = string2.charAt(n2);
            if (c >= ' ' && c <= '~' && c != '\"' && c != '\'') {
                stringBuilder.append(c);
            } else {
                stringBuilder.append(String.format("\\u%04x", c));
            }
            ++n2;
        }
        return stringBuilder.toString();
    }

    /*
     * Enabled aggressive block sorting
     */
    private static String zzty(String string2) {
        StringBuffer stringBuffer = new StringBuffer();
        int n = 0;
        while (n < string2.length()) {
            char c = string2.charAt(n);
            if (n == 0) {
                stringBuffer.append(Character.toLowerCase(c));
            } else if (Character.isUpperCase(c)) {
                stringBuffer.append('_').append(Character.toLowerCase(c));
            } else {
                stringBuffer.append(c);
            }
            ++n;
        }
        return stringBuffer.toString();
    }
}

