/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.os.Bundle
 *  android.text.TextUtils
 */
package io.branch.referral;

import android.content.Context;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.TextUtils;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

class DeferredAppLinkDataHandler {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Boolean fetchDeferredAppLinkData(Context context, AppLinkFetchEvents object) {
        boolean bl;
        Method method;
        Object object2;
        block4: {
            bl = true;
            try {
                Class.forName("com.facebook.FacebookSdk").getMethod("sdkInitialize", Context.class).invoke(null, new Object[]{context});
                final Class<?> class_ = Class.forName("com.facebook.applinks.AppLinkData");
                object2 = Class.forName("com.facebook.applinks.AppLinkData$CompletionHandler");
                method = class_.getMethod("fetchDeferredAppLinkData", new Class[]{Context.class, String.class, object2});
                object = new InvocationHandler((AppLinkFetchEvents)object){
                    final /* synthetic */ AppLinkFetchEvents val$callback;
                    {
                        this.val$callback = appLinkFetchEvents;
                    }

                    /*
                     * WARNING - void declaration
                     * Enabled aggressive block sorting
                     */
                    @Override
                    public Object invoke(Object object, Method method, Object[] arrobject) throws Throwable {
                        void var3_5;
                        if (method.getName().equals("onDeferredAppLinkDataFetched") && var3_5[0] != null) {
                            object = null;
                            Object t = class_.cast(var3_5[0]);
                            Bundle bundle = (Bundle)Bundle.class.cast(class_.getMethod("getArgumentBundle", new Class[0]).invoke(t, new Object[0]));
                            if (bundle != null) {
                                object = bundle.getString("com.facebook.platform.APPLINK_NATIVE_URL");
                            }
                            if (this.val$callback == null) return null;
                            {
                                this.val$callback.onAppLinkFetchFinished((String)object);
                                return null;
                            }
                        } else {
                            if (this.val$callback == null) return null;
                            {
                                this.val$callback.onAppLinkFetchFinished(null);
                                return null;
                            }
                        }
                    }
                };
                object = Proxy.newProxyInstance(((Class)object2).getClassLoader(), new Class[]{object2}, (InvocationHandler)object);
                object2 = context.getString(context.getResources().getIdentifier("facebook_app_id", "string", context.getPackageName()));
                boolean bl2 = TextUtils.isEmpty((CharSequence)object2);
                if (!bl2) break block4;
                bl = false;
            }
            catch (Throwable throwable) {
                bl = false;
                return bl;
            }
            do {
                return bl;
                break;
            } while (true);
        }
        method.invoke(null, new Object[]{context, object2, object});
        return bl;
    }

    public static interface AppLinkFetchEvents {
        public void onAppLinkFetchFinished(String var1);
    }

}

