/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.res.Resources
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.preference.PreferenceManager
 */
package com.getqardio.android.utils.permission;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Build;
import android.preference.PreferenceManager;
import android.support.v13.app.FragmentCompat;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import com.getqardio.android.ui.dialog.CustomAlertDialog;
import com.getqardio.android.utils.permission.PermissionUtil$BlePermissions$$Lambda$1;
import com.getqardio.android.utils.permission.PermissionUtil$BlePermissions$$Lambda$2;
import com.getqardio.android.utils.permission.PermissionUtil$Contact$$Lambda$1;

public class PermissionUtil {

    public static class BlePermissions {
        public static void checkCoarseLocationPermission(Activity activity) {
            block5: {
                block4: {
                    if (ContextCompat.checkSelfPermission((Context)activity, "android.permission.ACCESS_COARSE_LOCATION") == 0) break block4;
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.ACCESS_COARSE_LOCATION")) break block5;
                    CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance((Context)activity, activity.getResources().getString(2131362038), activity.getResources().getString(2131361961));
                    customAlertDialog.setOnClickListener(PermissionUtil$BlePermissions$$Lambda$1.lambdaFactory$(activity));
                    customAlertDialog.show();
                }
                return;
            }
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences((Context)activity.getApplicationContext());
            if (!sharedPreferences.contains("location_permission_checked")) {
                sharedPreferences.edit().putBoolean("location_permission_checked", true).apply();
            }
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, 1);
        }

        public static void checkFineLocationPermission(Activity activity) {
            block3: {
                block4: {
                    block2: {
                        if (ContextCompat.checkSelfPermission((Context)activity, "android.permission.ACCESS_FINE_LOCATION") == 0) break block2;
                        if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.ACCESS_FINE_LOCATION")) break block3;
                        if (Build.VERSION.SDK_INT < 26 || ContextCompat.checkSelfPermission((Context)activity, "android.permission.ACCESS_COARSE_LOCATION") != 0) break block4;
                        ActivityCompat.requestPermissions(activity, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 2);
                    }
                    return;
                }
                CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance((Context)activity, activity.getResources().getString(2131362038), activity.getResources().getString(2131361961));
                customAlertDialog.setOnClickListener(PermissionUtil$BlePermissions$$Lambda$2.lambdaFactory$(activity));
                customAlertDialog.show();
                return;
            }
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 2);
        }

        public static boolean hasCourseLocationPermission(Activity activity) {
            return ContextCompat.checkSelfPermission((Context)activity, "android.permission.ACCESS_COARSE_LOCATION") == 0;
        }

        public static boolean hasFineLocationPermission(Activity activity) {
            return ContextCompat.checkSelfPermission((Context)activity, "android.permission.ACCESS_FINE_LOCATION") == 0;
        }

        static /* synthetic */ void lambda$checkCoarseLocationPermission$0(Activity activity, DialogInterface dialogInterface, int n) {
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.ACCESS_COARSE_LOCATION"}, 1);
        }

        static /* synthetic */ void lambda$checkFineLocationPermission$1(Activity activity, DialogInterface dialogInterface, int n) {
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.ACCESS_FINE_LOCATION"}, 2);
        }
    }

    public static class Contact {
        public static final String[] PERMISSIONS = new String[]{"android.permission.READ_CONTACTS"};

        public static void checkReadContactsPermission(Activity activity) {
            block3: {
                block2: {
                    if (Contact.hasReadContactsPermission((Context)activity)) break block2;
                    if (!ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.READ_CONTACTS")) break block3;
                    CustomAlertDialog customAlertDialog = CustomAlertDialog.newInstance((Context)activity, activity.getResources().getString(2131362030), activity.getResources().getString(2131362029));
                    customAlertDialog.setOnClickListener(PermissionUtil$Contact$$Lambda$1.lambdaFactory$(activity));
                    customAlertDialog.show();
                }
                return;
            }
            Contact.performRequestPermissions(activity);
        }

        public static boolean hasReadContactsPermission(Context context) {
            return ContextCompat.checkSelfPermission(context, "android.permission.READ_CONTACTS") == 0;
        }

        static /* synthetic */ void lambda$checkReadContactsPermission$0(Activity activity, DialogInterface dialogInterface, int n) {
            Contact.performRequestPermissions(activity);
        }

        private static void performRequestPermissions(Activity activity) {
            ActivityCompat.requestPermissions(activity, PERMISSIONS, 3);
        }
    }

    public static class ExternalStorage {
        public static void checkExternalStoragePermission(Activity activity) {
            if (ContextCompat.checkSelfPermission((Context)activity, "android.permission.READ_EXTERNAL_STORAGE") == 0 || ActivityCompat.shouldShowRequestPermissionRationale(activity, "android.permission.READ_EXTERNAL_STORAGE")) {
                return;
            }
            ActivityCompat.requestPermissions(activity, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 0);
        }

        public static void checkExternalStoragePermission(Fragment fragment) {
            FragmentCompat.requestPermissions(fragment, new String[]{"android.permission.READ_EXTERNAL_STORAGE"}, 0);
        }

        public static boolean hasExternalStoragePermission(Activity activity) {
            return ContextCompat.checkSelfPermission((Context)activity, "android.permission.READ_EXTERNAL_STORAGE") == 0;
        }
    }

}

