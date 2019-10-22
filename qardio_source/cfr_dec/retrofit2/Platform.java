/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.os.Handler
 *  android.os.Looper
 *  org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement
 */
package retrofit2;

import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import java.lang.invoke.MethodHandle;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.Executor;
import org.codehaus.mojo.animal_sniffer.IgnoreJRERequirement;
import retrofit2.CallAdapter;
import retrofit2.DefaultCallAdapterFactory;
import retrofit2.ExecutorCallAdapterFactory;

class Platform {
    private static final Platform PLATFORM = Platform.findPlatform();

    Platform() {
    }

    private static Platform findPlatform() {
        try {
            Class.forName("android.os.Build");
            if (Build.VERSION.SDK_INT != 0) {
                Android android = new Android();
                return android;
            }
        }
        catch (ClassNotFoundException classNotFoundException) {
            // empty catch block
        }
        try {
            Class.forName("java.util.Optional");
            Java8 java8 = new Java8();
            return java8;
        }
        catch (ClassNotFoundException classNotFoundException) {
            try {
                Class.forName("org.robovm.apple.foundation.NSObject");
                IOS iOS = new IOS();
                return iOS;
            }
            catch (ClassNotFoundException classNotFoundException2) {
                return new Platform();
            }
        }
    }

    static Platform get() {
        return PLATFORM;
    }

    CallAdapter.Factory defaultCallAdapterFactory(Executor executor) {
        if (executor != null) {
            return new ExecutorCallAdapterFactory(executor);
        }
        return DefaultCallAdapterFactory.INSTANCE;
    }

    Executor defaultCallbackExecutor() {
        return null;
    }

    Object invokeDefaultMethod(Method method, Class<?> class_, Object object, Object ... arrobject) throws Throwable {
        throw new UnsupportedOperationException();
    }

    boolean isDefaultMethod(Method method) {
        return false;
    }

    static class Android
    extends Platform {
        Android() {
        }

        @Override
        CallAdapter.Factory defaultCallAdapterFactory(Executor executor) {
            return new ExecutorCallAdapterFactory(executor);
        }

        @Override
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        static class MainThreadExecutor
        implements Executor {
            private final Handler handler = new Handler(Looper.getMainLooper());

            MainThreadExecutor() {
            }

            @Override
            public void execute(Runnable runnable) {
                this.handler.post(runnable);
            }
        }

    }

    static class IOS
    extends Platform {
        IOS() {
        }

        @Override
        CallAdapter.Factory defaultCallAdapterFactory(Executor executor) {
            return new ExecutorCallAdapterFactory(executor);
        }

        @Override
        public Executor defaultCallbackExecutor() {
            return new MainThreadExecutor();
        }

        static class MainThreadExecutor
        implements Executor {
            private static Method addOperation;
            private static Object queue;

            static {
                try {
                    Class<?> class_ = Class.forName("org.robovm.apple.foundation.NSOperationQueue");
                    queue = class_.getDeclaredMethod("getMainQueue", new Class[0]).invoke(null, new Object[0]);
                    addOperation = class_.getDeclaredMethod("addOperation", Runnable.class);
                    return;
                }
                catch (Exception exception) {
                    throw new AssertionError(exception);
                }
            }

            MainThreadExecutor() {
            }

            /*
             * WARNING - void declaration
             * Enabled force condition propagation
             * Lifted jumps to return sites
             */
            @Override
            public void execute(Runnable runnable) {
                void var1_3;
                try {
                    addOperation.invoke(queue, runnable);
                    return;
                }
                catch (IllegalAccessException illegalAccessException) {
                    do {
                        throw new AssertionError(var1_3);
                        break;
                    } while (true);
                }
                catch (InvocationTargetException invocationTargetException) {
                    Throwable throwable = invocationTargetException.getCause();
                    if (throwable instanceof RuntimeException) {
                        throw (RuntimeException)throwable;
                    }
                    if (!(throwable instanceof Error)) throw new RuntimeException(throwable);
                    throw (Error)throwable;
                }
                catch (IllegalArgumentException illegalArgumentException) {
                    throw new AssertionError(var1_3);
                }
            }
        }

    }

    @IgnoreJRERequirement
    static class Java8
    extends Platform {
        Java8() {
        }

        @Override
        Object invokeDefaultMethod(Method method, Class<?> class_, Object object, Object ... arrobject) throws Throwable {
            Constructor constructor = MethodHandles.Lookup.class.getDeclaredConstructor(Class.class, Integer.TYPE);
            constructor.setAccessible(true);
            return ((MethodHandles.Lookup)constructor.newInstance(class_, -1)).unreflectSpecial(method, class_).bindTo(object).invokeWithArguments(arrobject);
        }

        @Override
        boolean isDefaultMethod(Method method) {
            return method.isDefault();
        }
    }

}

