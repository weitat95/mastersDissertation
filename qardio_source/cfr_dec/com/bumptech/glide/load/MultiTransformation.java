/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load;

import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.Resource;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;

public class MultiTransformation<T>
implements Transformation<T> {
    private String id;
    private final Collection<? extends Transformation<T>> transformations;

    @SafeVarargs
    public MultiTransformation(Transformation<T> ... arrtransformation) {
        if (arrtransformation.length < 1) {
            throw new IllegalArgumentException("MultiTransformation must contain at least one Transformation");
        }
        this.transformations = Arrays.asList(arrtransformation);
    }

    @Override
    public String getId() {
        if (this.id == null) {
            StringBuilder stringBuilder = new StringBuilder();
            Iterator<Transformation<T>> iterator = this.transformations.iterator();
            while (iterator.hasNext()) {
                stringBuilder.append(iterator.next().getId());
            }
            this.id = stringBuilder.toString();
        }
        return this.id;
    }

    @Override
    public Resource<T> transform(Resource<T> resource, int n, int n2) {
        Resource<T> resource2 = resource;
        Iterator<Transformation<T>> iterator = this.transformations.iterator();
        while (iterator.hasNext()) {
            Resource<T> resource3 = iterator.next().transform(resource2, n, n2);
            if (resource2 != null && !resource2.equals(resource) && !resource2.equals(resource3)) {
                resource2.recycle();
            }
            resource2 = resource3;
        }
        return resource2;
    }
}

