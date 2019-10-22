/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 */
package com.bumptech.glide.load.engine.bitmap_recycle;

import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BaseKeyPool;
import com.bumptech.glide.load.engine.bitmap_recycle.GroupedLinkedMap;
import com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.Poolable;
import com.bumptech.glide.util.Util;

class AttributeStrategy
implements LruPoolStrategy {
    private final GroupedLinkedMap<Key, Bitmap> groupedMap;
    private final KeyPool keyPool = new KeyPool();

    AttributeStrategy() {
        this.groupedMap = new GroupedLinkedMap();
    }

    private static String getBitmapString(int n, int n2, Bitmap.Config config) {
        return "[" + n + "x" + n2 + "], " + (Object)config;
    }

    private static String getBitmapString(Bitmap bitmap) {
        return AttributeStrategy.getBitmapString(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
    }

    @Override
    public Bitmap get(int n, int n2, Bitmap.Config object) {
        object = this.keyPool.get(n, n2, (Bitmap.Config)object);
        return this.groupedMap.get((Key)object);
    }

    @Override
    public int getSize(Bitmap bitmap) {
        return Util.getBitmapByteSize(bitmap);
    }

    @Override
    public String logBitmap(int n, int n2, Bitmap.Config config) {
        return AttributeStrategy.getBitmapString(n, n2, config);
    }

    @Override
    public String logBitmap(Bitmap bitmap) {
        return AttributeStrategy.getBitmapString(bitmap);
    }

    @Override
    public void put(Bitmap bitmap) {
        Key key = this.keyPool.get(bitmap.getWidth(), bitmap.getHeight(), bitmap.getConfig());
        this.groupedMap.put(key, bitmap);
    }

    @Override
    public Bitmap removeLast() {
        return this.groupedMap.removeLast();
    }

    public String toString() {
        return "AttributeStrategy:\n  " + this.groupedMap;
    }

    static class Key
    implements Poolable {
        private Bitmap.Config config;
        private int height;
        private final KeyPool pool;
        private int width;

        public Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = bl = false;
            if (object instanceof Key) {
                object = (Key)object;
                bl2 = bl;
                if (this.width == ((Key)object).width) {
                    bl2 = bl;
                    if (this.height == ((Key)object).height) {
                        bl2 = bl;
                        if (this.config == ((Key)object).config) {
                            bl2 = true;
                        }
                    }
                }
            }
            return bl2;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n;
            int n2 = this.width;
            int n3 = this.height;
            if (this.config != null) {
                n = this.config.hashCode();
                do {
                    return (n2 * 31 + n3) * 31 + n;
                    break;
                } while (true);
            }
            n = 0;
            return (n2 * 31 + n3) * 31 + n;
        }

        public void init(int n, int n2, Bitmap.Config config) {
            this.width = n;
            this.height = n2;
            this.config = config;
        }

        @Override
        public void offer() {
            this.pool.offer(this);
        }

        public String toString() {
            return AttributeStrategy.getBitmapString(this.width, this.height, this.config);
        }
    }

    static class KeyPool
    extends BaseKeyPool<Key> {
        KeyPool() {
        }

        @Override
        protected Key create() {
            return new Key(this);
        }

        public Key get(int n, int n2, Bitmap.Config config) {
            Key key = (Key)this.get();
            key.init(n, n2, config);
            return key;
        }
    }

}

