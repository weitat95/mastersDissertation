/*
 * Decompiled with CFR 0.147.
 */
package com.google.gson.internal;

import com.google.gson.internal.$Gson$Preconditions;
import java.io.Serializable;
import java.lang.reflect.Array;
import java.lang.reflect.GenericArrayType;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.lang.reflect.TypeVariable;
import java.lang.reflect.WildcardType;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Properties;

public final class $Gson$Types {
    static final Type[] EMPTY_TYPE_ARRAY = new Type[0];

    public static GenericArrayType arrayOf(Type type) {
        return new GenericArrayTypeImpl(type);
    }

    public static Type canonicalize(Type type) {
        if (type instanceof Class) {
            if (((Class)(type = (Class)type)).isArray()) {
                type = new GenericArrayTypeImpl($Gson$Types.canonicalize(((Class)type).getComponentType()));
            }
            return type;
        }
        if (type instanceof ParameterizedType) {
            type = (ParameterizedType)type;
            return new ParameterizedTypeImpl(type.getOwnerType(), type.getRawType(), type.getActualTypeArguments());
        }
        if (type instanceof GenericArrayType) {
            return new GenericArrayTypeImpl(((GenericArrayType)type).getGenericComponentType());
        }
        if (type instanceof WildcardType) {
            type = (WildcardType)type;
            return new WildcardTypeImpl(type.getUpperBounds(), type.getLowerBounds());
        }
        return type;
    }

    /*
     * Enabled aggressive block sorting
     */
    static void checkNotPrimitive(Type type) {
        boolean bl = !(type instanceof Class) || !((Class)type).isPrimitive();
        $Gson$Preconditions.checkArgument(bl);
    }

    private static Class<?> declaringClassOf(TypeVariable<?> typeVariable) {
        if ((typeVariable = typeVariable.getGenericDeclaration()) instanceof Class) {
            return (Class)((Object)typeVariable);
        }
        return null;
    }

    static boolean equal(Object object, Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static boolean equals(Type type, Type type2) {
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
            if (!$Gson$Types.equal(type.getOwnerType(), type2.getOwnerType())) return false;
            if (!type.getRawType().equals(type2.getRawType())) return false;
            if (!Arrays.equals(type.getActualTypeArguments(), type2.getActualTypeArguments())) return false;
            return bl4;
        }
        if (type instanceof GenericArrayType) {
            bl = bl5;
            if (!(type2 instanceof GenericArrayType)) return bl;
            type = (GenericArrayType)type;
            type2 = (GenericArrayType)type2;
            return $Gson$Types.equals(type.getGenericComponentType(), type2.getGenericComponentType());
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

    public static Type getArrayComponentType(Type type) {
        if (type instanceof GenericArrayType) {
            return ((GenericArrayType)type).getGenericComponentType();
        }
        return ((Class)type).getComponentType();
    }

    public static Type getCollectionElementType(Type type, Class<?> type2) {
        type = type2 = $Gson$Types.getSupertype(type, type2, Collection.class);
        if (type2 instanceof WildcardType) {
            type = ((WildcardType)type2).getUpperBounds()[0];
        }
        if (type instanceof ParameterizedType) {
            return ((ParameterizedType)type).getActualTypeArguments()[0];
        }
        return Object.class;
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
                return $Gson$Types.getGenericSupertype(((Class)object2).getGenericInterfaces()[i], object[i], class_);
            }
        }
        if (!((Class)object2).isInterface()) {
            while (object2 != Object.class) {
                object = ((Class)object2).getSuperclass();
                if (object == class_) {
                    return ((Class)object2).getGenericSuperclass();
                }
                if (class_.isAssignableFrom((Class<?>)object)) {
                    return $Gson$Types.getGenericSupertype(((Class)object2).getGenericSuperclass(), object, class_);
                }
                object2 = object;
            }
        }
        return class_;
    }

    public static Type[] getMapKeyAndValueTypes(Type type, Class<?> class_) {
        if (type == Properties.class) {
            return new Type[]{String.class, String.class};
        }
        if ((type = $Gson$Types.getSupertype(type, class_, Map.class)) instanceof ParameterizedType) {
            return ((ParameterizedType)type).getActualTypeArguments();
        }
        return new Type[]{Object.class, Object.class};
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static Class<?> getRawType(Type type) {
        String string2;
        if (type instanceof Class) {
            return (Class)type;
        }
        if (type instanceof ParameterizedType) {
            type = ((ParameterizedType)type).getRawType();
            $Gson$Preconditions.checkArgument(type instanceof Class);
            return (Class)type;
        }
        if (type instanceof GenericArrayType) {
            return Array.newInstance($Gson$Types.getRawType(((GenericArrayType)type).getGenericComponentType()), 0).getClass();
        }
        if (type instanceof TypeVariable) {
            return Object.class;
        }
        if (type instanceof WildcardType) {
            return $Gson$Types.getRawType(((WildcardType)type).getUpperBounds()[0]);
        }
        if (type == null) {
            string2 = "null";
            do {
                throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + string2);
                break;
            } while (true);
        }
        string2 = type.getClass().getName();
        throw new IllegalArgumentException("Expected a Class, ParameterizedType, or GenericArrayType, but <" + type + "> is of type " + string2);
    }

    static Type getSupertype(Type type, Class<?> class_, Class<?> class_2) {
        $Gson$Preconditions.checkArgument(class_2.isAssignableFrom(class_));
        return $Gson$Types.resolve(type, class_, $Gson$Types.getGenericSupertype(type, class_, class_2));
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

    public static ParameterizedType newParameterizedTypeWithOwner(Type type, Type type2, Type ... arrtype) {
        return new ParameterizedTypeImpl(type, type2, arrtype);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public static Type resolve(Type type, Class<?> class_, Type object) {
        void var2_13;
        void var2_10;
        void var1_8;
        Type type2;
        Type type3;
        while (var2_10 instanceof TypeVariable) {
            type3 = (TypeVariable)var2_10;
            type2 = $Gson$Types.resolveTypeVariable(type, var1_8, type3);
            Class<?> class_2 = type2;
            if (type2 != type3) continue;
            Class<?> class_3 = type2;
            return var2_13;
        }
        if (var2_10 instanceof Class && ((Class)var2_10).isArray()) {
            Type type4;
            void var0_3;
            Class class_4 = (Class)var2_10;
            type2 = class_4.getComponentType();
            if (type2 == (type4 = $Gson$Types.resolve(type, var1_8, type2))) {
                Class class_5 = class_4;
                return var0_3;
            }
            GenericArrayType genericArrayType = $Gson$Types.arrayOf(type4);
            return var0_3;
        }
        if (var2_10 instanceof GenericArrayType) {
            Type type5;
            GenericArrayType genericArrayType = (GenericArrayType)var2_10;
            type2 = genericArrayType.getGenericComponentType();
            if (type2 == (type5 = $Gson$Types.resolve(type, var1_8, type2))) return var2_13;
            return $Gson$Types.arrayOf(type5);
        }
        if (var2_10 instanceof ParameterizedType) {
            type3 = (ParameterizedType)var2_10;
            Type type6 = type3.getOwnerType();
            Type type7 = $Gson$Types.resolve(type, var1_8, type6);
            boolean bl = type7 != type6;
            type2 = type3.getActualTypeArguments();
            int n = 0;
            int n2 = ((Type)type2).length;
            do {
                void var2_22;
                if (n >= n2) {
                    Type type8 = type3;
                    if (!bl) return var2_13;
                    return $Gson$Types.newParameterizedTypeWithOwner(type7, type3.getRawType(), (Type[])type2);
                }
                Type type9 = $Gson$Types.resolve(type, var1_8, type2[n]);
                Type type10 = type2;
                boolean bl2 = bl;
                if (type9 != type2[n]) {
                    Type type11 = type2;
                    bl2 = bl;
                    if (!bl) {
                        Type[] arrtype = (Type[])type2.clone();
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
        Type[] arrtype = type2.getUpperBounds();
        if (((Type)type3).length == 1) {
            Type type12 = $Gson$Types.resolve(type, var1_8, type3[0]);
            Type type13 = type2;
            if (type12 == type3[0]) return var2_13;
            return $Gson$Types.supertypeOf(type12);
        }
        Type type14 = type2;
        if (arrtype.length != 1) return var2_13;
        Type type15 = $Gson$Types.resolve(type, var1_8, arrtype[0]);
        Type type16 = type2;
        if (type15 == arrtype[0]) return var2_13;
        return $Gson$Types.subtypeOf(type15);
    }

    /*
     * Enabled aggressive block sorting
     */
    static Type resolveTypeVariable(Type type, Class<?> class_, TypeVariable<?> typeVariable) {
        Class<?> class_2 = $Gson$Types.declaringClassOf(typeVariable);
        if (class_2 == null || !((type = $Gson$Types.getGenericSupertype(type, class_, class_2)) instanceof ParameterizedType)) {
            return typeVariable;
        }
        int n = $Gson$Types.indexOf(class_2.getTypeParameters(), typeVariable);
        return ((ParameterizedType)type).getActualTypeArguments()[n];
    }

    public static WildcardType subtypeOf(Type type) {
        Type[] arrtype = EMPTY_TYPE_ARRAY;
        return new WildcardTypeImpl(new Type[]{type}, arrtype);
    }

    public static WildcardType supertypeOf(Type type) {
        return new WildcardTypeImpl(new Type[]{Object.class}, new Type[]{type});
    }

    public static String typeToString(Type type) {
        if (type instanceof Class) {
            return ((Class)type).getName();
        }
        return type.toString();
    }

    private static final class GenericArrayTypeImpl
    implements Serializable,
    GenericArrayType {
        private final Type componentType;

        public GenericArrayTypeImpl(Type type) {
            this.componentType = $Gson$Types.canonicalize(type);
        }

        public boolean equals(Object object) {
            return object instanceof GenericArrayType && $Gson$Types.equals(this, (GenericArrayType)object);
        }

        @Override
        public Type getGenericComponentType() {
            return this.componentType;
        }

        public int hashCode() {
            return this.componentType.hashCode();
        }

        public String toString() {
            return $Gson$Types.typeToString(this.componentType) + "[]";
        }
    }

    private static final class ParameterizedTypeImpl
    implements Serializable,
    ParameterizedType {
        private final Type ownerType;
        private final Type rawType;
        private final Type[] typeArguments;

        /*
         * Enabled aggressive block sorting
         */
        public ParameterizedTypeImpl(Type type, Type type2, Type ... arrtype) {
            int n;
            boolean bl = false;
            if (type2 instanceof Class) {
                Class class_ = (Class)type2;
                n = Modifier.isStatic(class_.getModifiers()) || class_.getEnclosingClass() == null ? 1 : 0;
                if (type != null || n != 0) {
                    bl = true;
                }
                $Gson$Preconditions.checkArgument(bl);
            }
            type = type == null ? null : $Gson$Types.canonicalize(type);
            this.ownerType = type;
            this.rawType = $Gson$Types.canonicalize(type2);
            this.typeArguments = (Type[])arrtype.clone();
            n = 0;
            while (n < this.typeArguments.length) {
                $Gson$Preconditions.checkNotNull(this.typeArguments[n]);
                $Gson$Types.checkNotPrimitive(this.typeArguments[n]);
                this.typeArguments[n] = $Gson$Types.canonicalize(this.typeArguments[n]);
                ++n;
            }
            return;
        }

        public boolean equals(Object object) {
            return object instanceof ParameterizedType && $Gson$Types.equals(this, (ParameterizedType)object);
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
            return Arrays.hashCode(this.typeArguments) ^ this.rawType.hashCode() ^ $Gson$Types.hashCodeOrZero(this.ownerType);
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder((this.typeArguments.length + 1) * 30);
            stringBuilder.append($Gson$Types.typeToString(this.rawType));
            if (this.typeArguments.length == 0) {
                return stringBuilder.toString();
            }
            stringBuilder.append("<").append($Gson$Types.typeToString(this.typeArguments[0]));
            for (int i = 1; i < this.typeArguments.length; ++i) {
                stringBuilder.append(", ").append($Gson$Types.typeToString(this.typeArguments[i]));
            }
            return stringBuilder.append(">").toString();
        }
    }

    private static final class WildcardTypeImpl
    implements Serializable,
    WildcardType {
        private final Type lowerBound;
        private final Type upperBound;

        /*
         * Enabled aggressive block sorting
         */
        public WildcardTypeImpl(Type[] arrtype, Type[] arrtype2) {
            boolean bl = true;
            boolean bl2 = arrtype2.length <= 1;
            $Gson$Preconditions.checkArgument(bl2);
            bl2 = arrtype.length == 1;
            $Gson$Preconditions.checkArgument(bl2);
            if (arrtype2.length != 1) {
                $Gson$Preconditions.checkNotNull(arrtype[0]);
                $Gson$Types.checkNotPrimitive(arrtype[0]);
                this.lowerBound = null;
                this.upperBound = $Gson$Types.canonicalize(arrtype[0]);
                return;
            }
            $Gson$Preconditions.checkNotNull(arrtype2[0]);
            $Gson$Types.checkNotPrimitive(arrtype2[0]);
            bl2 = arrtype[0] == Object.class ? bl : false;
            $Gson$Preconditions.checkArgument(bl2);
            this.lowerBound = $Gson$Types.canonicalize(arrtype2[0]);
            this.upperBound = Object.class;
        }

        public boolean equals(Object object) {
            return object instanceof WildcardType && $Gson$Types.equals(this, (WildcardType)object);
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
                return "? super " + $Gson$Types.typeToString(this.lowerBound);
            }
            if (this.upperBound == Object.class) {
                return "?";
            }
            return "? extends " + $Gson$Types.typeToString(this.upperBound);
        }
    }

}

