/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.ApplicationInfo
 *  android.content.res.ColorStateList
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.os.Process
 *  android.util.Log
 *  android.util.TypedValue
 */
package android.support.v4.content;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Process;
import android.util.Log;
import android.util.TypedValue;
import java.io.File;

public class ContextCompat {
    private static final String TAG = "ContextCompat";
    private static final Object sLock = new Object();
    private static TypedValue sTempValue;

    protected ContextCompat() {
    }

    /*
     * Enabled aggressive block sorting
     */
    private static File buildPath(File file, String ... arrstring) {
        int n = arrstring.length;
        int n2 = 0;
        while (n2 < n) {
            String string2 = arrstring[n2];
            if (file == null) {
                file = new File(string2);
            } else if (string2 != null) {
                file = new File(file, string2);
            }
            ++n2;
        }
        return file;
    }

    public static int checkSelfPermission(Context context, String string2) {
        if (string2 == null) {
            throw new IllegalArgumentException("permission is null");
        }
        return context.checkPermission(string2, Process.myPid(), Process.myUid());
    }

    public static Context createDeviceProtectedStorageContext(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.createDeviceProtectedStorageContext();
        }
        return null;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static File createFilesDir(File file) {
        synchronized (ContextCompat.class) {
            block5: {
                File file2 = file;
                if (file.exists()) return file2;
                file2 = file;
                if (file.mkdirs()) return file2;
                boolean bl = file.exists();
                if (!bl) break block5;
                return file;
            }
            Log.w((String)TAG, (String)("Unable to create files subdir " + file.getPath()));
            return null;
        }
    }

    public static File getCodeCacheDir(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getCodeCacheDir();
        }
        return ContextCompat.createFilesDir(new File(context.getApplicationInfo().dataDir, "code_cache"));
    }

    public static final int getColor(Context context, int n) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColor(n);
        }
        return context.getResources().getColor(n);
    }

    public static final ColorStateList getColorStateList(Context context, int n) {
        if (Build.VERSION.SDK_INT >= 23) {
            return context.getColorStateList(n);
        }
        return context.getResources().getColorStateList(n);
    }

    public static File getDataDir(Context object) {
        if (Build.VERSION.SDK_INT >= 24) {
            return object.getDataDir();
        }
        object = object.getApplicationInfo().dataDir;
        if (object != null) {
            return new File((String)object);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static final Drawable getDrawable(Context context, int n) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getDrawable(n);
        }
        if (Build.VERSION.SDK_INT >= 16) {
            return context.getResources().getDrawable(n);
        }
        Object object = sLock;
        synchronized (object) {
            if (sTempValue == null) {
                sTempValue = new TypedValue();
            }
            context.getResources().getValue(n, sTempValue, true);
            n = ContextCompat.sTempValue.resourceId;
            return context.getResources().getDrawable(n);
        }
    }

    public static File[] getExternalCacheDirs(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return context.getExternalCacheDirs();
        }
        return new File[]{context.getExternalCacheDir()};
    }

    public static File[] getExternalFilesDirs(Context context, String string2) {
        if (Build.VERSION.SDK_INT >= 19) {
            return context.getExternalFilesDirs(string2);
        }
        return new File[]{context.getExternalFilesDir(string2)};
    }

    public static final File getNoBackupFilesDir(Context context) {
        if (Build.VERSION.SDK_INT >= 21) {
            return context.getNoBackupFilesDir();
        }
        return ContextCompat.createFilesDir(new File(context.getApplicationInfo().dataDir, "no_backup"));
    }

    public static File[] getObbDirs(Context context) {
        if (Build.VERSION.SDK_INT >= 19) {
            return context.getObbDirs();
        }
        return new File[]{context.getObbDir()};
    }

    public static boolean isDeviceProtectedStorage(Context context) {
        if (Build.VERSION.SDK_INT >= 24) {
            return context.isDeviceProtectedStorage();
        }
        return false;
    }

    public static boolean startActivities(Context context, Intent[] arrintent) {
        return ContextCompat.startActivities(context, arrintent, null);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean startActivities(Context context, Intent[] arrintent, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            context.startActivities(arrintent, bundle);
            do {
                return true;
                break;
            } while (true);
        }
        context.startActivities(arrintent);
        return true;
    }

    public static void startActivity(Context context, Intent intent, Bundle bundle) {
        if (Build.VERSION.SDK_INT >= 16) {
            context.startActivity(intent, bundle);
            return;
        }
        context.startActivity(intent);
    }

    public static void startForegroundService(Context context, Intent intent) {
        if (Build.VERSION.SDK_INT >= 26) {
            context.startForegroundService(intent);
            return;
        }
        context.startService(intent);
    }
}

