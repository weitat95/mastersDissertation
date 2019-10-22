/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.PropertyValuesHolder
 *  android.graphics.Path
 *  android.graphics.PointF
 *  android.util.Property
 */
package android.support.transition;

import android.animation.PropertyValuesHolder;
import android.graphics.Path;
import android.graphics.PointF;
import android.support.transition.PropertyValuesHolderUtilsImpl;
import android.util.Property;

class PropertyValuesHolderUtilsApi21
implements PropertyValuesHolderUtilsImpl {
    PropertyValuesHolderUtilsApi21() {
    }

    @Override
    public PropertyValuesHolder ofPointF(Property<?, PointF> property, Path path) {
        return PropertyValuesHolder.ofObject(property, null, (Path)path);
    }
}

