/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.model;

import com.bumptech.glide.util.LruCache;
import com.bumptech.glide.util.Util;
import java.util.Queue;

public class ModelCache<A, B> {
    private final LruCache<ModelKey<A>, B> cache;

    public ModelCache() {
        this(250);
    }

    public ModelCache(int n) {
        this.cache = new LruCache<ModelKey<A>, B>(n){

            @Override
            protected void onItemEvicted(ModelKey<A> modelKey, B b2) {
                modelKey.release();
            }
        };
    }

    public B get(A object, int n, int n2) {
        object = ModelKey.get(object, n, n2);
        B b2 = this.cache.get((ModelKey<A>)object);
        ((ModelKey)object).release();
        return b2;
    }

    public void put(A object, int n, int n2, B b2) {
        object = ModelKey.get(object, n, n2);
        this.cache.put((ModelKey<A>)object, b2);
    }

    static final class ModelKey<A> {
        private static final Queue<ModelKey<?>> KEY_QUEUE = Util.createQueue(0);
        private int height;
        private A model;
        private int width;

        private ModelKey() {
        }

        static <A> ModelKey<A> get(A a2, int n, int n2) {
            ModelKey<?> modelKey;
            ModelKey<Object> modelKey2 = modelKey = KEY_QUEUE.poll();
            if (modelKey == null) {
                modelKey2 = new ModelKey<A>();
            }
            super.init(a2, n, n2);
            return modelKey2;
        }

        private void init(A a2, int n, int n2) {
            this.model = a2;
            this.width = n;
            this.height = n2;
        }

        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = bl = false;
            if (object instanceof ModelKey) {
                object = (ModelKey)object;
                bl2 = bl;
                if (this.width == ((ModelKey)object).width) {
                    bl2 = bl;
                    if (this.height == ((ModelKey)object).height) {
                        bl2 = bl;
                        if (this.model.equals(((ModelKey)object).model)) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }

        public int hashCode() {
            return (this.height * 31 + this.width) * 31 + this.model.hashCode();
        }

        public void release() {
            KEY_QUEUE.offer(this);
        }
    }

}

