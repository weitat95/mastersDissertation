/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.platform;

import java.lang.reflect.GenericDeclaration;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

class OptionalMethod<T> {
    private final String methodName;
    private final Class[] methodParams;
    private final Class<?> returnType;

    public OptionalMethod(Class<?> class_, String string2, Class ... arrclass) {
        this.returnType = class_;
        this.methodName = string2;
        this.methodParams = arrclass;
    }

    private Method getMethod(Class<?> genericDeclaration) {
        GenericDeclaration genericDeclaration2 = null;
        if (this.methodName != null) {
            genericDeclaration2 = genericDeclaration = OptionalMethod.getPublicMethod(genericDeclaration, this.methodName, this.methodParams);
            if (genericDeclaration != null) {
                genericDeclaration2 = genericDeclaration;
                if (this.returnType != null) {
                    genericDeclaration2 = genericDeclaration;
                    if (!this.returnType.isAssignableFrom(((Method)genericDeclaration).getReturnType())) {
                        genericDeclaration2 = null;
                    }
                }
            }
        }
        return genericDeclaration2;
    }

    private static Method getPublicMethod(Class<?> genericDeclaration, String string2, Class[] arrclass) {
        GenericDeclaration genericDeclaration2 = null;
        try {
            genericDeclaration2 = genericDeclaration = ((Class)genericDeclaration).getMethod(string2, arrclass);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            return genericDeclaration2;
        }
        int n = ((Method)genericDeclaration).getModifiers();
        if ((n & 1) == 0) {
            genericDeclaration = null;
        }
        return genericDeclaration;
    }

    public Object invoke(T object, Object ... object2) throws InvocationTargetException {
        Method method = this.getMethod(object.getClass());
        if (method == null) {
            throw new AssertionError((Object)("Method " + this.methodName + " not supported for object " + object));
        }
        try {
            object = method.invoke(object, (Object[])object2);
            return object;
        }
        catch (IllegalAccessException illegalAccessException) {
            object2 = new AssertionError((Object)("Unexpectedly could not call: " + method));
            ((Throwable)object2).initCause(illegalAccessException);
            throw object2;
        }
    }

    public Object invokeOptional(T object, Object ... arrobject) throws InvocationTargetException {
        Method method = this.getMethod(object.getClass());
        if (method == null) {
            return null;
        }
        try {
            object = method.invoke(object, arrobject);
            return object;
        }
        catch (IllegalAccessException illegalAccessException) {
            return null;
        }
    }

    public Object invokeOptionalWithoutCheckedException(T object, Object ... object2) {
        try {
            object = this.invokeOptional(object, (Object[])object2);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getTargetException();
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException)throwable;
            }
            object2 = new AssertionError((Object)"Unexpected exception");
            ((Throwable)object2).initCause(throwable);
            throw object2;
        }
    }

    public Object invokeWithoutCheckedException(T object, Object ... object2) {
        try {
            object = this.invoke(object, (Object[])object2);
            return object;
        }
        catch (InvocationTargetException invocationTargetException) {
            Throwable throwable = invocationTargetException.getTargetException();
            if (throwable instanceof RuntimeException) {
                throw (RuntimeException)throwable;
            }
            object2 = new AssertionError((Object)"Unexpected exception");
            ((Throwable)object2).initCause(throwable);
            throw object2;
        }
    }

    public boolean isSupported(T t) {
        return this.getMethod(t.getClass()) != null;
    }
}

