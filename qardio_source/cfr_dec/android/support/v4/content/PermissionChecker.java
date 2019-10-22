/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.pm.PackageManager
 *  android.os.Process
 */
package android.support.v4.content;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Process;
import android.support.v4.app.AppOpsManagerCompat;

public final class PermissionChecker {
    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static int checkPermission(Context context, String object, int n, int n2, String string2) {
        String string3;
        void var1_5;
        block6: {
            String[] arrstring;
            block7: {
                block5: {
                    void var4_8;
                    void var2_6;
                    void var3_7;
                    if (context.checkPermission((String)object, (int)var2_6, (int)var3_7) == -1) break block5;
                    string3 = AppOpsManagerCompat.permissionToOp((String)object);
                    if (string3 == null) {
                        return 0;
                    }
                    void var1_2 = var4_8;
                    if (var4_8 != null) break block6;
                    arrstring = context.getPackageManager().getPackagesForUid((int)var3_7);
                    if (arrstring != null && arrstring.length > 0) break block7;
                }
                return -1;
            }
            String string4 = arrstring[0];
        }
        if (AppOpsManagerCompat.noteProxyOp(context, string3, (String)var1_5) != 0) {
            return -2;
        }
        return 0;
    }

    public static int checkSelfPermission(Context context, String string2) {
        return PermissionChecker.checkPermission(context, string2, Process.myPid(), Process.myUid(), context.getPackageName());
    }
}

