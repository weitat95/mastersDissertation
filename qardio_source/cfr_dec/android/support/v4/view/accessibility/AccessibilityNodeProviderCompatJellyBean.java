/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.view.accessibility.AccessibilityNodeInfo
 *  android.view.accessibility.AccessibilityNodeProvider
 */
package android.support.v4.view.accessibility;

import android.os.Bundle;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityNodeProvider;
import java.util.List;

class AccessibilityNodeProviderCompatJellyBean {
    public static Object newAccessibilityNodeProviderBridge(final AccessibilityNodeInfoBridge accessibilityNodeInfoBridge) {
        return new AccessibilityNodeProvider(){

            public AccessibilityNodeInfo createAccessibilityNodeInfo(int n) {
                return (AccessibilityNodeInfo)accessibilityNodeInfoBridge.createAccessibilityNodeInfo(n);
            }

            public List<AccessibilityNodeInfo> findAccessibilityNodeInfosByText(String string2, int n) {
                return accessibilityNodeInfoBridge.findAccessibilityNodeInfosByText(string2, n);
            }

            public boolean performAction(int n, int n2, Bundle bundle) {
                return accessibilityNodeInfoBridge.performAction(n, n2, bundle);
            }
        };
    }

    static interface AccessibilityNodeInfoBridge {
        public Object createAccessibilityNodeInfo(int var1);

        public List<Object> findAccessibilityNodeInfosByText(String var1, int var2);

        public boolean performAction(int var1, int var2, Bundle var3);
    }

}

