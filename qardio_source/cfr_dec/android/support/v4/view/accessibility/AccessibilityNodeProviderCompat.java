/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Bundle
 *  android.view.accessibility.AccessibilityNodeInfo
 */
package android.support.v4.view.accessibility;

import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.accessibility.AccessibilityNodeInfoCompat;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompatJellyBean;
import android.support.v4.view.accessibility.AccessibilityNodeProviderCompatKitKat;
import android.view.accessibility.AccessibilityNodeInfo;
import java.util.ArrayList;
import java.util.List;

public class AccessibilityNodeProviderCompat {
    private static final AccessibilityNodeProviderImpl IMPL = Build.VERSION.SDK_INT >= 19 ? new AccessibilityNodeProviderKitKatImpl() : (Build.VERSION.SDK_INT >= 16 ? new AccessibilityNodeProviderJellyBeanImpl() : new AccessibilityNodeProviderStubImpl());
    private final Object mProvider;

    public AccessibilityNodeProviderCompat() {
        this.mProvider = IMPL.newAccessibilityNodeProviderBridge(this);
    }

    public AccessibilityNodeProviderCompat(Object object) {
        this.mProvider = object;
    }

    public AccessibilityNodeInfoCompat createAccessibilityNodeInfo(int n) {
        return null;
    }

    public List<AccessibilityNodeInfoCompat> findAccessibilityNodeInfosByText(String string2, int n) {
        return null;
    }

    public AccessibilityNodeInfoCompat findFocus(int n) {
        return null;
    }

    public Object getProvider() {
        return this.mProvider;
    }

    public boolean performAction(int n, int n2, Bundle bundle) {
        return false;
    }

    static interface AccessibilityNodeProviderImpl {
        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat var1);
    }

    private static class AccessibilityNodeProviderJellyBeanImpl
    extends AccessibilityNodeProviderStubImpl {
        AccessibilityNodeProviderJellyBeanImpl() {
        }

        @Override
        public Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            return AccessibilityNodeProviderCompatJellyBean.newAccessibilityNodeProviderBridge(new AccessibilityNodeProviderCompatJellyBean.AccessibilityNodeInfoBridge(){

                @Override
                public Object createAccessibilityNodeInfo(int n) {
                    AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = accessibilityNodeProviderCompat.createAccessibilityNodeInfo(n);
                    if (accessibilityNodeInfoCompat == null) {
                        return null;
                    }
                    return accessibilityNodeInfoCompat.unwrap();
                }

                /*
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                @Override
                public List<Object> findAccessibilityNodeInfosByText(String arrayList, int n) {
                    List<AccessibilityNodeInfoCompat> list = accessibilityNodeProviderCompat.findAccessibilityNodeInfosByText((String)((Object)arrayList), n);
                    if (list == null) {
                        return null;
                    }
                    ArrayList<AccessibilityNodeInfo> arrayList2 = new ArrayList<AccessibilityNodeInfo>();
                    int n2 = list.size();
                    n = 0;
                    do {
                        arrayList = arrayList2;
                        if (n >= n2) return arrayList;
                        arrayList2.add(list.get(n).unwrap());
                        ++n;
                    } while (true);
                }

                @Override
                public boolean performAction(int n, int n2, Bundle bundle) {
                    return accessibilityNodeProviderCompat.performAction(n, n2, bundle);
                }
            });
        }

    }

    private static class AccessibilityNodeProviderKitKatImpl
    extends AccessibilityNodeProviderStubImpl {
        AccessibilityNodeProviderKitKatImpl() {
        }

        @Override
        public Object newAccessibilityNodeProviderBridge(final AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            return AccessibilityNodeProviderCompatKitKat.newAccessibilityNodeProviderBridge(new AccessibilityNodeProviderCompatKitKat.AccessibilityNodeInfoBridge(){

                @Override
                public Object createAccessibilityNodeInfo(int n) {
                    AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = accessibilityNodeProviderCompat.createAccessibilityNodeInfo(n);
                    if (accessibilityNodeInfoCompat == null) {
                        return null;
                    }
                    return accessibilityNodeInfoCompat.unwrap();
                }

                /*
                 * Enabled force condition propagation
                 * Lifted jumps to return sites
                 */
                @Override
                public List<Object> findAccessibilityNodeInfosByText(String arrayList, int n) {
                    List<AccessibilityNodeInfoCompat> list = accessibilityNodeProviderCompat.findAccessibilityNodeInfosByText((String)((Object)arrayList), n);
                    if (list == null) {
                        return null;
                    }
                    ArrayList<AccessibilityNodeInfo> arrayList2 = new ArrayList<AccessibilityNodeInfo>();
                    int n2 = list.size();
                    n = 0;
                    do {
                        arrayList = arrayList2;
                        if (n >= n2) return arrayList;
                        arrayList2.add(list.get(n).unwrap());
                        ++n;
                    } while (true);
                }

                @Override
                public Object findFocus(int n) {
                    AccessibilityNodeInfoCompat accessibilityNodeInfoCompat = accessibilityNodeProviderCompat.findFocus(n);
                    if (accessibilityNodeInfoCompat == null) {
                        return null;
                    }
                    return accessibilityNodeInfoCompat.unwrap();
                }

                @Override
                public boolean performAction(int n, int n2, Bundle bundle) {
                    return accessibilityNodeProviderCompat.performAction(n, n2, bundle);
                }
            });
        }

    }

    static class AccessibilityNodeProviderStubImpl
    implements AccessibilityNodeProviderImpl {
        AccessibilityNodeProviderStubImpl() {
        }

        @Override
        public Object newAccessibilityNodeProviderBridge(AccessibilityNodeProviderCompat accessibilityNodeProviderCompat) {
            return null;
        }
    }

}

