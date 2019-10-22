/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.NoSuchElementException;
import okhttp3.MediaType;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.Sink;

final class Utils {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    static ResponseBody buffer(ResponseBody responseBody) throws IOException {
        Buffer buffer = new Buffer();
        responseBody.source().readAll(buffer);
        return ResponseBody.create(responseBody.contentType(), responseBody.contentLength(), buffer);
    }

    static <T> T checkNotNull(T t, String string2) {
        if (t == null) {
            throw new NullPointerException(string2);
        }
        return t;
    }

    static void checkNotPrimitive(Type type) {
        if (type instanceof Class && ((Class)type).isPrimitive()) {
            throw new IllegalArgumentException();
        }
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        if ((typeVariable = typeVariable.getGenericDeclaration()) instanceof Class) {
            return (Class)((Object)typeVariable);
        }
        return null;
    }

    private static boolean equal(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    static boolean equals(Type type, Type type2) {
        boolean bl;
        boolean bl2 = true;
        boolean bl3 = true;
        boolean bl4 = true;
        boolean bl5 = false;
        if (type == type2) {
            return true;
        }
        if (type instanceof Class) {
            return type.equals(type2);
        }
        if (type instanceof ParameterizedType) {
            bl = bl5;
            if (!(type2 instanceof ParameterizedType)) return bl;
            type = (ParameterizedType)type;
            type2 = (ParameterizedType)type2;
            if (!Utils.equal(type.getOwnerType(), type2.getOwnerType())) return false;
            if (!type.getRawType().equals(type2.getRawType())) return false;
            if (!Arrays.equals(type.getActualTypeArguments(), type2.getActualTypeArguments())) return false;
            return bl4;
        }
        if (type instanceof GenericArrayType) {
            bl = bl5;
            if (!(type2 instanceof GenericArrayType)) return bl;
            type = (GenericArrayType)type;
            type2 = (GenericArrayType)type2;
            return Utils.equals(type.getGenericComponentType(), type2.getGenericComponentType());
        }
        if (type instanceof WildcardType) {
            bl = bl5;
            if (!(type2 instanceof WildcardType)) return bl;
            type = (WildcardType)type;
            type2 = (WildcardType)type2;
            if (!Arrays.equals(type.getUpperBounds(), type2.getUpperBounds())) return false;
            if (!Arrays.equals(type.getLowerBounds(), type2.getLowerBounds())) return false;
            return bl2;
        }
        bl = bl5;
        if (!(type instanceof TypeVariable)) return bl;
        bl = bl5;
        if (!(type2 instanceof TypeVariable)) return bl;
        type = (TypeVariable)type;
        type2 = (TypeVariable)type2;
        if (type.getGenericDeclaration() != type2.getGenericDeclaration()) return false;
        if (!type.getName().equals(type2.getName())) return false;
        return bl3;
    }

    static Type getCallResponseType(Type type) {
        if (!(type instanceof ParameterizedType)) {
            throw new IllegalArgumentException("Call return type must be parameterized as Call<Foo> or Call<? extends Foo>");
        }
        return Utils.getParameterUpperBound(0, (ParameterizedType)type);
    }

    static Type getGenericSupertype(Type object, Class<?> object2, Class<?> class_) {
        if (class_ == object2) {
            return object;
        }
        if (class_.isInterface()) {
            object = ((Class)object2).getInterfaces();
            int n = ((Class<?>[])object).length;
            for (int i = 0; i < n; ++i) {
                if (object[i] == class_) {
                    return ((Class)object2).getGenericInterfaces()[i];
                }
                if (!class_.isAssignableFrom(object[i])) continue;
                return Utils.getGenericSupertype(((Class)object2).getGenericInterfaces()[i], object[i], class_);
            }
        }
        if (!((Class)object2).isInterface()) {
            while (object2 != Object.class) {
                object = ((Class)object2).getSuperclass();
                if (object == class_) {
                    return ((Class)object2).getGenericSuperclass();
                }
                if (class_.isAssignableFrom((Class<?>)object)) {
                    return Utils.getGenericSupertype(((Class)object2).getGenericSuperclass(), object, class_);
                }
                object2 = object;
            }
        }
        return class_;
    }

    static Type getParameterUpperBound(int n, ParameterizedType object) {
        Object object2 = object.getActualTypeArguments();
        if (n < 0 || n >= ((Type[])object2).length) {
            throw new IllegalArgumentException("Index " + n + " not in range [0," + ((Type[])object2).length + ") for " + object);
        }
        object = object2 = object2[n];
        if (object2 instanceof WildcardType) {
            object = ((WildcardType)object2).getUpperBounds()[0];
        }
        return object;
    }

    static Class<?> getRawType(Type type) {
        if (type == null) {
            throw new NullPointerException("type == null");
        }
        if (type instanceof Class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType) {
            if (!((type = ((ParameterizedType)type).getRawType()) instanceof Class)) {
                throw new IllegalArgumentException();
            }
            return (Class)type;
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance(Utils.getRawType(((GenericArrayType)type).getGenericComponentType()), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return Utils.getRawType(((WildcardType)type).getUpperBounds()[0]);
        }
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + type.getClass().getName());
    }

    static Type getSupertype(Type type, Class<?> class_, Class<?> class_2) {
        if (!class_2.isAssignableFrom(class_)) {
            throw new IllegalArgumentException();
        }
        return Utils.resolve(type, class_, Utils.getGenericSupertype(type, class_, class_2));
    }

    /*
     * Enabled aggressive block sorting
     */
    static boolean hasUnresolvableType(Type arrtype) {
        if (arrtype instanceof Class) {
            return false;
        }
        if (!(arrtype instanceof ParameterizedType)) {
            String string2;
            if (arrtype instanceof GenericArrayType) {
                return Utils.hasUnresolvableType(((GenericArrayType)arrtype).getGenericComponentType());
            }
            if (arrtype instanceof TypeVariable) {
                return true;
            }
            if (arrtype instanceof WildcardType) {
                return true;
            }
            if (arrtype == null) {
                string2 = "null";
                throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + arrtype + "> is of type " + string2);
            }
            string2 = arrtype.getClass().getName();
            throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + arrtype + "> is of type " + string2);
        }
        arrtype = ((ParameterizedType)arrtype).getActualTypeArguments();
        int n = arrtype.length;
        int n2 = 0;
        while (n2 < n) {
            if (Utils.hasUnresolvableType(arrtype[n2])) {
                return true;
            }
            ++n2;
        }
        return false;
    }

    static int hashCodeOrZero(Object object) {
        if (object != null) {
            return object.hashCode();
        }
        return 0;
    }

    private static int indexOf(Object[] arrobject, Object object) {
        for (int i = 0; i < arrobject.length; ++i) {
            if (!object.equals(arrobject[i])) continue;
            return i;
        }
        throw new NoSuchElementException();
    }

    static boolean isAnnotationPresent(Annotation[] arrannotation, Class<? extends Annotation> class_) {
        boolean bl = false;
        int n = arrannotation.length;
        int n2 = 0;
        do {
            block4: {
                boolean bl2;
                block3: {
                    bl2 = bl;
                    if (n2 >= n) break block3;
                    if (!class_.isInstance(arrannotation[n2])) break block4;
                    bl2 = true;
                }
                return bl2;
            }
            ++n2;
        } while (true);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    static Type resolve(Type type, Class<?> arrtype, Type object) {
        void var2_13;
        void var2_10;
        Type type2;
        Type[] arrtype2;
        Type type3;
        while (var2_10 instanceof TypeVariable) {
            type3 = (TypeVariable)var2_10;
            type2 = Utils.resolveTypeVariable(type, arrtype2, type3);
            Class<?> class_ = type2;
            if (type2 != type3) continue;
            Class<?> class_2 = type2;
            return var2_13;
        }
        if (var2_10 instanceof Class && ((Class)var2_10).isArray()) {
            Type type4;
            void var0_3;
            Class class_ = (Class)var2_10;
            type2 = class_.getComponentType();
            if (type2 == (type4 = Utils.resolve(type, arrtype2, type2))) {
                Class class_3 = class_;
                return var0_3;
            }
            GenericArrayTypeImpl genericArrayTypeImpl = new GenericArrayTypeImpl(type4);
            return var0_3;
        }
        if (var2_10 instanceof GenericArrayType) {
            Type type5;
            GenericArrayType genericArrayType = (GenericArrayType)var2_10;
            type2 = genericArrayType.getGenericComponentType();
            if (type2 == (type5 = Utils.resolve(type, arrtype2, type2))) return var2_13;
            return new GenericArrayTypeImpl(type5);
        }
        if (var2_10 instanceof ParameterizedType) {
            type3 = (ParameterizedType)var2_10;
            Type type6 = type3.getOwnerType();
            Type type7 = Utils.resolve(type, arrtype2, type6);
            boolean bl = type7 != type6;
            type2 = type3.getActualTypeArguments();
            int n = 0;
            int n2 = ((Type)type2).length;
            do {
                void var2_22;
                if (n >= n2) {
                    Type type8 = type3;
                    if (!bl) return var2_13;
                    return new ParameterizedTypeImpl(type7, type3.getRawType(), (Type[])type2);
                }
                Type type9 = Utils.resolve(type, arrtype2, type2[n]);
                Type type10 = type2;
                boolean bl2 = bl;
                if (type9 != type2[n]) {
                    Type type11 = type2;
                    bl2 = bl;
                    if (!bl) {
                        Type[] arrtype3 = (Type[])type2.clone();
                        bl2 = true;
                    }
                    var2_21[n] = type9;
                }
                ++n;
                type2 = var2_22;
                bl = bl2;
            } while (true);
        }
        if (!(var2_10 instanceof WildcardType)) return var2_10;
        type2 = (WildcardType)var2_10;
        type3 = type2.getLowerBounds();
        Type[] arrtype4 = type2.getUpperBounds();
        if (((Type)type3).length == 1) {
            Type type12 = Utils.resolve(type, arrtype2, type3[0]);
            Type type13 = type2;
            if (type12 == type3[0]) return var2_13;
            return new WildcardTypeImpl(new Type[]{Object.class}, new Type[]{type12});
        }
        Type type14 = type2;
        if (arrtype4.length != 1) return var2_13;
        Type type15 = Utils.resolve(type, arrtype2, arrtype4[0]);
        Type type16 = type2;
        if (type15 == arrtype4[0]) return var2_13;
        arrtype2 = EMPTY_TYPE_ARRAY;
        return new WildcardTypeImpl(new Type[]{type15}, arrtype2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private static Type resolveTypeVariable(Type type, Class<?> class_, TypeVariable<?> typeVariable) {
        Class<?> class_2 = Utils.declaringClassOf(typeVariable);
        if (class_2 == null || !((type = Utils.getGenericSupertype(type, class_, class_2)) instanceof ParameterizedType)) {
            return typeVariable;
        }
        int n = Utils.indexOf(class_2.getTypeParameters(), typeVariable);
        return ((ParameterizedType)type).getActualTypeArguments()[n];
    }

    static String typeToString(Type type) {
        if (type instanceof Class) {
            return ((Class)type).getName();
        }
        return type.toString();
    }

    static <T> void validateServiceInterface(Class<T> class_) {
        if (!class_.isInterface()) {
            throw new IllegalArgumentException("API declarations must be interfaces.");
        }
        if (class_.getInterfaces().length > 0) {
            throw new IllegalArgumentException("API interfaces must not extend other interfaces.");
        }
    }

    private static final class GenericArrayTypeImpl
    implements GenericArrayType {
        private final Type componentType;

        public GenericArrayTypeImpl(Type type) {
            this.componentType = type;
        }

        public boolean equals(Object object) {
            return object instanceof GenericArrayType && Utils.equals(this, (GenericArrayType)object);
        }

        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            return Utils.typeToString(this.componentType) + "[]";
        }
    }

    private static final class ParameterizedTypeImpl
    implements ParameterizedType {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        /*
         * Enabled aggressive block sorting
         */
        public ParameterizedTypeImpl(Type arrtype, Type type, Type ... arrtype2) {
            int n;
            int n2 = 1;
            int n3 = 0;
            if (type instanceof Class) {
                n = arrtype == null ? 1 : 0;
                if (((Class)type).getEnclosingClass() != null) {
                    n2 = 0;
                }
                if (n != n2) {
                    throw new IllegalArgumentException();
                }
            }
            this.ownerType = arrtype;
            this.rawType = type;
            arrtype = this.typeArguments = (Type[])arrtype2.clone();
            n2 = arrtype.length;
            n = n3;
            while (n < n2) {
                type = arrtype[n];
                if (type == null) {
                    throw new NullPointerException();
                }
                Utils.checkNotPrimitive(type);
                ++n;
            }
            return;
        }

        public boolean equals(Object object) {
            return object instanceof ParameterizedType && Utils.equals(this, (ParameterizedType)object);
        }

        @Override
        public Type[] getActualTypeArguments() {
            return (Type[])this.typeArguments.clone();
        }

        @Override
        public Type getOwnerType() {
            return this.ownerType;
        }

        @Override
        public Type getRawType() {
            return this.rawType;
        }

        public int hashCode() {
            return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ Utils.hashCodeOrZero(this.ownerType);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder((this.typeArguments.length + 1) * 30);
            stringBuilder.append(Utils.typeToString(this.rawType));
            if (this.typeArguments.length == 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append("<").append(Utils.typeToString(this.typeArguments[0]));
            for (int i = 1; i < this.typeArguments.length; ++i) {
                stringBuilder.append(", ").append(Utils.typeToString(this.typeArguments[i]));
            }
            return stringBuilder.append(">").toString();
        }
    }

    private static final class WildcardTypeImpl
    implements WildcardType {
        private final Type lowerBound;
        private final Type upperBound;

        public WildcardTypeImpl(Type[] arrtype, Type[] arrtype2) {
            if (arrtype2.length > 1) {
                throw new IllegalArgumentException();
            }
            if (arrtype.length != 1) {
                throw new IllegalArgumentException();
            }
            if (arrtype2.length == 1) {
                if (arrtype2[0] == null) {
                    throw new NullPointerException();
                }
                Utils.checkNotPrimitive(arrtype2[0]);
                if (arrtype[0] != Object.class) {
                    throw new IllegalArgumentException();
                }
                this.lowerBound = arrtype2[0];
                this.upperBound = Object.class;
                return;
            }
            if (arrtype[0] == null) {
                throw new NullPointerException();
            }
            Utils.checkNotPrimitive(arrtype[0]);
            this.lowerBound = null;
            this.upperBound = arrtype[0];
        }

        public boolean equals(Object object) {
            return object instanceof WildcardType && Utils.equals(this, (WildcardType)object);
        }

        @Override
        public Type[] getLowerBounds() {
            if (this.lowerBound != null) {
                return new Type[]{this.lowerBound};
            }
            return EMPTY_TYPE_ARRAY;
        }

        @Override
        public Type[] getUpperBounds() {
            return new Type[]{this.upperBound};
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n;
            if (this.lowerBound != null) {
                n = this.lowerBound.hashCode() + 31;
                do {
                    return n ^ this.upperBound.hashCode() + 31;
                    break;
                } while (true);
            }
            n = 1;
            return n ^ this.upperBound.hashCode() + 31;
        }

        public String toString() {
            if (this.lowerBound != null) {
                return "? super " + Utils.typeToString(this.lowerBound);
            }
            if (this.upperBound == Object.class) {
                return "?";
            }
            return "? extends " + Utils.typeToString(this.upperBound);
        }
    }

}

