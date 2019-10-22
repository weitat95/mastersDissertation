/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.log.RealmLog;
import java.io.File;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicBoolean;

public class Util {
    /*
     * Enabled aggressive block sorting
     */
    public static boolean deleteRealm(String string2, File file, String string3) {
        boolean bl = true;
        boolean bl2 = true;
        File file2 = new File(file, string3 + ".management");
        File[] arrfile = file2.listFiles();
        if (arrfile != null) {
            int n = arrfile.length;
            int n2 = 0;
            do {
                bl = bl2;
                if (n2 >= n) break;
                File file3 = arrfile[n2];
                bl2 = bl2 && file3.delete();
                ++n2;
            } while (true);
        }
        if (!bl) return false;
        if (!file2.delete()) return false;
        bl2 = true;
        if (!bl2) return false;
        if (!Util.deletes(string2, file, string3)) return false;
        return true;
    }

    private static boolean deletes(String object, File file2, String string2) {
        AtomicBoolean atomicBoolean = new AtomicBoolean(true);
        for (File file2 : Arrays.asList(new File(file2, string2), new File(file2, string2 + ".lock"), new File(file2, string2 + ".log_a"), new File(file2, string2 + ".log_b"), new File(file2, string2 + ".log"), new File((String)object))) {
            if (!file2.exists() || file2.delete()) continue;
            atomicBoolean.set(false);
            RealmLog.warn("Could not delete the file %s", file2);
        }
        return atomicBoolean.get();
    }

    public static Class<? extends RealmModel> getOriginalModelClass(Class<? extends RealmModel> class_) {
        Class<? extends RealmModel> class_2 = class_.getSuperclass();
        Class<? extends RealmModel> class_3 = class_;
        if (!class_2.equals(Object.class)) {
            class_3 = class_;
            if (!class_2.equals(RealmObject.class)) {
                class_3 = class_2;
            }
        }
        return class_3;
    }

    public static String getTablePrefix() {
        return Util.nativeGetTablePrefix();
    }

    public static boolean isEmptyString(String string2) {
        return string2 == null || string2.length() == 0;
    }

    static native String nativeGetTablePrefix();
}

