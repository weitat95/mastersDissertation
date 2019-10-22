/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.CheckReturnValue
 *  javax.annotation.Nullable
 */
package com.facebook.common.internal;

import com.facebook.common.internal.Preconditions;
import java.util.Arrays;
import javax.annotation.CheckReturnValue;
import javax.annotation.Nullable;

public final class Objects {
    @CheckReturnValue
    public static boolean equal(@Nullable Object object, @Nullable Object object2) {
        return object == object2 || object != null && object.equals(object2);
    }

    public static int hashCode(@Nullable Object ... arrobject) {
        return Arrays.hashCode(arrobject);
    }

    private static String simpleName(Class<?> object) {
        int n;
        object = ((Class)object).getName().replaceAll("\\$[0-9]+", "\\$");
        int n2 = n = ((String)object).lastIndexOf(36);
        if (n == -1) {
            n2 = ((String)object).lastIndexOf(46);
        }
        return ((String)object).substring(n2 + 1);
    }

    public static ToStringHelper toStringHelper(Object object) {
        return new ToStringHelper(Objects.simpleName(object.getClass()));
    }

    public static final class ToStringHelper {
        private final String className;
        private ValueHolder holderHead;
        private ValueHolder holderTail;
        private boolean omitNullValues;

        private ToStringHelper(String string2) {
            this.holderTail = this.holderHead = new ValueHolder();
            this.omitNullValues = false;
            this.className = Preconditions.checkNotNull(string2);
        }

        private ValueHolder addHolder() {
            ValueHolder valueHolder;
            this.holderTail.next = valueHolder = new ValueHolder();
            this.holderTail = valueHolder;
            return valueHolder;
        }

        private ToStringHelper addHolder(String string2, @Nullable Object object) {
            ValueHolder valueHolder = this.addHolder();
            valueHolder.value = object;
            valueHolder.name = Preconditions.checkNotNull(string2);
            return this;
        }

        public ToStringHelper add(String string2, int n) {
            return this.addHolder(string2, String.valueOf(n));
        }

        public ToStringHelper add(String string2, @Nullable Object object) {
            return this.addHolder(string2, object);
        }

        public ToStringHelper add(String string2, boolean bl) {
            return this.addHolder(string2, String.valueOf(bl));
        }

        public String toString() {
            boolean bl = this.omitNullValues;
            String string2 = "";
            StringBuilder stringBuilder = new StringBuilder(32).append(this.className).append('{');
            ValueHolder valueHolder = this.holderHead.next;
            while (valueHolder != null) {
                String string3;
                block6: {
                    block5: {
                        if (!bl) break block5;
                        string3 = string2;
                        if (valueHolder.value == null) break block6;
                    }
                    stringBuilder.append(string2);
                    string3 = ", ";
                    if (valueHolder.name != null) {
                        stringBuilder.append(valueHolder.name).append('=');
                    }
                    stringBuilder.append(valueHolder.value);
                }
                valueHolder = valueHolder.next;
                string2 = string3;
            }
            return stringBuilder.append('}').toString();
        }

        private static final class ValueHolder {
            String name;
            ValueHolder next;
            Object value;

            private ValueHolder() {
            }
        }

    }

}

