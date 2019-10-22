/*
 * Decompiled with CFR 0.147.
 */
package android.arch.lifecycle;

import android.arch.lifecycle.GenericLifecycleObserver;
import android.arch.lifecycle.Lifecycle;
import android.arch.lifecycle.LifecycleOwner;
import android.arch.lifecycle.OnLifecycleEvent;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

class ReflectiveGenericLifecycleObserver
implements GenericLifecycleObserver {
    private static final Set<Lifecycle.Event> ALL_EVENTS;
    static final Map<Class, CallbackInfo> sInfoCache;
    private final CallbackInfo mInfo;
    private final Object mWrapped;

    static {
        sInfoCache = new HashMap<Class, CallbackInfo>();
        ALL_EVENTS = new HashSet<Lifecycle.Event>(){
            {
                this.add(Lifecycle.Event.ON_CREATE);
                this.add(Lifecycle.Event.ON_START);
                this.add(Lifecycle.Event.ON_RESUME);
                this.add(Lifecycle.Event.ON_PAUSE);
                this.add(Lifecycle.Event.ON_STOP);
                this.add(Lifecycle.Event.ON_DESTROY);
            }
        };
    }

    ReflectiveGenericLifecycleObserver(Object object) {
        this.mWrapped = object;
        this.mInfo = ReflectiveGenericLifecycleObserver.getInfo(this.mWrapped.getClass());
    }

    /*
     * Enabled aggressive block sorting
     */
    private static CallbackInfo createInfo(Class arrclass) {
        int n;
        Object object;
        Method[] arrmethod = arrclass.getDeclaredMethods();
        Object object2 = null;
        HashSet<Lifecycle.Event> hashSet = new HashSet<Lifecycle.Event>();
        for (Method method : arrmethod) {
            OnLifecycleEvent onLifecycleEvent = method.getAnnotation(OnLifecycleEvent.class);
            if (onLifecycleEvent == null) continue;
            object = method.getParameterTypes();
            n = 0;
            if (((Class<?>[])object).length > 0) {
                n = 1;
                if (!object[0].isAssignableFrom(LifecycleOwner.class)) {
                    throw new IllegalArgumentException("invalid parameter type. Must be one and instanceof LifecycleOwner");
                }
            }
            if (((Class<?>[])object).length > 1) {
                n = 2;
                if (!object[1].isAssignableFrom(Lifecycle.Event.class)) {
                    throw new IllegalArgumentException("invalid parameter type. second arg must be an event");
                }
            }
            if (((Class<?>[])object).length > 2) {
                throw new IllegalArgumentException("cannot have more than 2 params");
            }
            object = object2;
            if (object2 == null) {
                object = new ArrayList();
            }
            object2 = ReflectiveGenericLifecycleObserver.expandOnAnyEvents(onLifecycleEvent.value());
            object.add(new MethodReference((Set<Lifecycle.Event>)object2, n, method));
            hashSet.addAll((Collection<Lifecycle.Event>)object2);
            object2 = object;
        }
        object2 = new CallbackInfo((Set<Lifecycle.Event>)hashSet, (List<MethodReference>)object2);
        sInfoCache.put((Class)arrclass, (CallbackInfo)object2);
        object = arrclass.getSuperclass();
        if (object != null) {
            ((CallbackInfo)object2).mSuper = ReflectiveGenericLifecycleObserver.getInfo(object);
        }
        arrclass = arrclass.getInterfaces();
        int n2 = arrclass.length;
        n = 0;
        while (n < n2) {
            object = ReflectiveGenericLifecycleObserver.getInfo(arrclass[n]);
            if (!((CallbackInfo)object).mEvents.isEmpty()) {
                if (((CallbackInfo)object2).mInterfaces == null) {
                    ((CallbackInfo)object2).mInterfaces = new ArrayList<CallbackInfo>();
                }
                ((CallbackInfo)object2).mInterfaces.add((CallbackInfo)object);
            }
            ++n;
        }
        return object2;
    }

    private static Set<Lifecycle.Event> expandOnAnyEvents(Lifecycle.Event[] arrevent) {
        boolean bl = false;
        int n = arrevent.length;
        int n2 = 0;
        do {
            block4: {
                boolean bl2;
                block3: {
                    bl2 = bl;
                    if (n2 >= n) break block3;
                    if (arrevent[n2] != Lifecycle.Event.ON_ANY) break block4;
                    bl2 = true;
                }
                if (bl2) break;
                HashSet<Lifecycle.Event> hashSet = new HashSet<Lifecycle.Event>();
                Collections.addAll(hashSet, arrevent);
                return hashSet;
            }
            ++n2;
        } while (true);
        return ALL_EVENTS;
    }

    private static CallbackInfo getInfo(Class class_) {
        CallbackInfo callbackInfo = sInfoCache.get(class_);
        if (callbackInfo != null) {
            return callbackInfo;
        }
        return ReflectiveGenericLifecycleObserver.createInfo(class_);
    }

    /*
     * Exception decompiling
     */
    private void invokeCallback(MethodReference var1_1, LifecycleOwner var2_4, Lifecycle.Event var3_5) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 3[SWITCH]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private void invokeCallbacks(CallbackInfo callbackInfo, LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        int n;
        if (callbackInfo.mEvents.contains((Object)event)) {
            for (n = callbackInfo.mMethodReferences.size() - 1; n >= 0; --n) {
                this.invokeCallback(callbackInfo.mMethodReferences.get(n), lifecycleOwner, event);
            }
        }
        if (callbackInfo.mSuper != null) {
            this.invokeCallbacks(callbackInfo.mSuper, lifecycleOwner, event);
        }
        if (callbackInfo.mInterfaces != null) {
            int n2 = callbackInfo.mInterfaces.size();
            for (n = 0; n < n2; ++n) {
                this.invokeCallbacks(callbackInfo.mInterfaces.get(n), lifecycleOwner, event);
            }
        }
    }

    @Override
    public void onStateChanged(LifecycleOwner lifecycleOwner, Lifecycle.Event event) {
        this.invokeCallbacks(this.mInfo, lifecycleOwner, event);
    }

    static class CallbackInfo {
        final Set<Lifecycle.Event> mEvents;
        List<CallbackInfo> mInterfaces;
        List<MethodReference> mMethodReferences;
        CallbackInfo mSuper;

        CallbackInfo(Set<Lifecycle.Event> set, List<MethodReference> list) {
            this.mEvents = set;
            this.mMethodReferences = list;
        }
    }

    static class MethodReference {
        final int mCallType;
        final Set<Lifecycle.Event> mEvents;
        final Method mMethod;

        MethodReference(Set<Lifecycle.Event> set, int n, Method method) {
            this.mEvents = set;
            this.mCallType = n;
            this.mMethod = method;
            this.mMethod.setAccessible(true);
        }
    }

}

