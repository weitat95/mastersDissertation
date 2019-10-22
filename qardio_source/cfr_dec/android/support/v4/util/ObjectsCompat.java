/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package android.support.v4.util;

import android.os.Build;
import java.util.Objects;

public class ObjectsCompat {
    private static final ImplBase IMPL = Build.VERSION.SDK_INT >= 19 ? new ImplApi19() : new ImplBase();

    public static boolean equals(Object object, Object object2) {
        return IMPL.equals(object, object2);
    }

    private static class ImplApi19
    extends ImplBase {
        private ImplApi19() {
        }

        @Override
        public boolean equals(Object object, Object object2) {
            return Objects.equals(object, object2);
        }
    }

    private static class ImplBase {
        private ImplBase() {
        }

        public boolean equals(Object object, Object object2) {
            return object == object2 || object != null && object.equals(object2);
        }
    }

}

