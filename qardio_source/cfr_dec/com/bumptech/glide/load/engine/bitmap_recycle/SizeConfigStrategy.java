/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 */
package com.bumptech.glide.load.engine.bitmap_recycle;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import com.bumptech.glide.load.engine.bitmap_recycle.BaseKeyPool;
import com.bumptech.glide.load.engine.bitmap_recycle.GroupedLinkedMap;
import com.bumptech.glide.load.engine.bitmap_recycle.LruPoolStrategy;
import com.bumptech.glide.load.engine.bitmap_recycle.Poolable;
import com.bumptech.glide.util.Util;
import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.Set;
import java.util.TreeMap;

@TargetApi(value=19)
public class SizeConfigStrategy
implements LruPoolStrategy {
    private static final Bitmap.Config[] ALPHA_8_IN_CONFIGS;
    private static final Bitmap.Config[] ARGB_4444_IN_CONFIGS;
    private static final Bitmap.Config[] ARGB_8888_IN_CONFIGS;
    private static final Bitmap.Config[] RGB_565_IN_CONFIGS;
    private final GroupedLinkedMap<Key, Bitmap> groupedMap;
    private final KeyPool keyPool = new KeyPool();
    private final Map<Bitmap.Config, NavigableMap<Integer, Integer>> sortedSizes;

    static {
        ARGB_8888_IN_CONFIGS = new Bitmap.Config[]{Bitmap.Config.ARGB_8888, null};
        RGB_565_IN_CONFIGS = new Bitmap.Config[]{Bitmap.Config.RGB_565};
        ARGB_4444_IN_CONFIGS = new Bitmap.Config[]{Bitmap.Config.ARGB_4444};
        ALPHA_8_IN_CONFIGS = new Bitmap.Config[]{Bitmap.Config.ALPHA_8};
    }

    public SizeConfigStrategy() {
        this.groupedMap = new GroupedLinkedMap();
        this.sortedSizes = new HashMap<Bitmap.Config, NavigableMap<Integer, Integer>>();
    }

    private void decrementBitmapOfSize(Integer n, Bitmap.Config object) {
        Integer n2 = (Integer)(object = this.getSizesForConfig((Bitmap.Config)object)).get(n);
        if (n2 == 1) {
            object.remove(n);
            return;
        }
        object.put(n, n2 - 1);
    }

    /*
     * Enabled aggressive block sorting
     */
    private Key findBestKey(Key key, int n, Bitmap.Config config) {
        Key key2 = key;
        Bitmap.Config[] arrconfig = SizeConfigStrategy.getInConfigs(config);
        int n2 = arrconfig.length;
        int n3 = 0;
        do {
            Key key3 = key2;
            if (n3 >= n2) return key3;
            Bitmap.Config config2 = arrconfig[n3];
            Integer n4 = this.getSizesForConfig(config2).ceilingKey(n);
            if (n4 != null && n4 <= n * 8) {
                if (n4 == n) {
                    if (config2 == null) {
                        key3 = key2;
                        if (config == null) return key3;
                    } else {
                        key3 = key2;
                        if (config2.equals((Object)config)) return key3;
                    }
                }
                this.keyPool.offer(key);
                return this.keyPool.get(n4, config2);
            }
            ++n3;
        } while (true);
    }

    private static String getBitmapString(int n, Bitmap.Config config) {
        return "[" + n + "](" + (Object)config + ")";
    }

    private static Bitmap.Config[] getInConfigs(Bitmap.Config config) {
        switch (1.$SwitchMap$android$graphics$Bitmap$Config[config.ordinal()]) {
            default: {
                return new Bitmap.Config[]{config};
            }
            case 1: {
                return ARGB_8888_IN_CONFIGS;
            }
            case 2: {
                return RGB_565_IN_CONFIGS;
            }
            case 3: {
                return ARGB_4444_IN_CONFIGS;
            }
            case 4: 
        }
        return ALPHA_8_IN_CONFIGS;
    }

    private NavigableMap<Integer, Integer> getSizesForConfig(Bitmap.Config config) {
        NavigableMap<Integer, Integer> navigableMap;
        NavigableMap<Integer, Integer> navigableMap2 = navigableMap = this.sortedSizes.get((Object)config);
        if (navigableMap == null) {
            navigableMap2 = new TreeMap<Integer, Integer>();
            this.sortedSizes.put(config, navigableMap2);
        }
        return navigableMap2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    @Override
    public Bitmap get(int n, int n2, Bitmap.Config object) {
        int n3 = Util.getBitmapByteSize(n, n2, object);
        Key key = this.findBestKey(this.keyPool.get(n3, (Bitmap.Config)object), n3, (Bitmap.Config)object);
        Bitmap bitmap = this.groupedMap.get(key);
        if (bitmap != null) {
            void var3_6;
            this.decrementBitmapOfSize(Util.getBitmapByteSize(bitmap), bitmap.getConfig());
            if (bitmap.getConfig() != null) {
                Bitmap.Config config = bitmap.getConfig();
            } else {
                Bitmap.Config config = Bitmap.Config.ARGB_8888;
            }
            bitmap.reconfigure(n, n2, (Bitmap.Config)var3_6);
        }
        return bitmap;
    }

    @Override
    public int getSize(Bitmap bitmap) {
        return Util.getBitmapByteSize(bitmap);
    }

    @Override
    public String logBitmap(int n, int n2, Bitmap.Config config) {
        return SizeConfigStrategy.getBitmapString(Util.getBitmapByteSize(n, n2, config), config);
    }

    @Override
    public String logBitmap(Bitmap bitmap) {
        return SizeConfigStrategy.getBitmapString(Util.getBitmapByteSize(bitmap), bitmap.getConfig());
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void put(Bitmap object) {
        int n = Util.getBitmapByteSize((Bitmap)object);
        Key key = this.keyPool.get(n, object.getConfig());
        this.groupedMap.put(key, (Bitmap)object);
        object = this.getSizesForConfig(object.getConfig());
        Integer n2 = (Integer)object.get(key.size);
        int n3 = key.size;
        n = n2 == null ? 1 : n2 + 1;
        object.put(n3, n);
    }

    @Override
    public Bitmap removeLast() {
        Bitmap bitmap = this.groupedMap.removeLast();
        if (bitmap != null) {
            this.decrementBitmapOfSize(Util.getBitmapByteSize(bitmap), bitmap.getConfig());
        }
        return bitmap;
    }

    public String toString() {
        StringBuilder stringBuilder = new StringBuilder().append("SizeConfigStrategy{groupedMap=").append(this.groupedMap).append(", sortedSizes=(");
        for (Map.Entry<Bitmap.Config, NavigableMap<Integer, Integer>> entry : this.sortedSizes.entrySet()) {
            stringBuilder.append((Object)entry.getKey()).append('[').append(entry.getValue()).append("], ");
        }
        if (!this.sortedSizes.isEmpty()) {
            stringBuilder.replace(stringBuilder.length() - 2, stringBuilder.length(), "");
        }
        return stringBuilder.append(")}").toString();
    }

    static final class Key
    implements Poolable {
        private Bitmap.Config config;
        private final KeyPool pool;
        private int size;

        public Key(KeyPool keyPool) {
            this.pool = keyPool;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public boolean equals(Object object) {
            boolean bl;
            boolean bl2 = bl = false;
            if (!(object instanceof Key)) return bl2;
            object = (Key)object;
            bl2 = bl;
            if (this.size != ((Key)object).size) return bl2;
            if (this.config == null) {
                bl2 = bl;
                if (((Key)object).config != null) return bl2;
                do {
                    return true;
                    break;
                } while (true);
            }
            bl2 = bl;
            if (!this.config.equals((Object)((Key)object).config)) return bl2;
            return true;
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public int hashCode() {
            int n;
            int n2 = this.size;
            if (this.config != null) {
                n = this.config.hashCode();
                do {
                    return n2 * 31 + n;
                    break;
                } while (true);
            }
            n = 0;
            return n2 * 31 + n;
        }

        public void init(int n, Bitmap.Config config) {
            this.size = n;
            this.config = config;
        }

        @Override
        public void offer() {
            this.pool.offer(this);
        }

        public String toString() {
            return SizeConfigStrategy.getBitmapString(this.size, this.config);
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

        public Key get(int n, Bitmap.Config config) {
            Key key = (Key)this.get();
            key.init(n, config);
            return key;
        }
    }

}

