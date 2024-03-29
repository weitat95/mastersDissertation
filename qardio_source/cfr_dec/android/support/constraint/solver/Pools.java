/*
 * Decompiled with CFR 0.147.
 */
package android.support.constraint.solver;

final class Pools {

    static interface Pool<T> {
        public T acquire();

        public boolean release(T var1);

        public void releaseAll(T[] var1, int var2);
    }

    static class SimplePool<T>
    implements Pool<T> {
        private final Object[] mPool;
        private int mPoolSize;

        SimplePool(int n) {
            if (n <= 0) {
                throw new IllegalArgumentException("The max pool size must be > 0");
            }
            this.mPool = new Object[n];
        }

        @Override
        public T acquire() {
            if (this.mPoolSize > 0) {
                int n = this.mPoolSize - 1;
                Object object = this.mPool[n];
                this.mPool[n] = null;
                --this.mPoolSize;
                return (T)object;
            }
            return null;
        }

        @Override
        public boolean release(T t) {
            if (this.mPoolSize < this.mPool.length) {
                this.mPool[this.mPoolSize] = t;
                ++this.mPoolSize;
                return true;
            }
            return false;
        }

        @Override
        public void releaseAll(T[] arrT, int n) {
            int n2 = n;
            if (n > arrT.length) {
                n2 = arrT.length;
            }
            for (n = 0; n < n2; ++n) {
                T t = arrT[n];
                if (this.mPoolSize >= this.mPool.length) continue;
                this.mPool[this.mPoolSize] = t;
                ++this.mPoolSize;
            }
        }
    }

}

