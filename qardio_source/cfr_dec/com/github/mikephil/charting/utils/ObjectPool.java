/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.utils;

public class ObjectPool<T extends Poolable> {
    private static int ids = 0;
    private int desiredCapacity;
    private T modelObject;
    private Object[] objects;
    private int objectsPointer;
    private int poolId;
    private float replenishPercentage;

    private ObjectPool(int n, T t) {
        if (n <= 0) {
            throw new IllegalArgumentException("Object Pool must be instantiated with a capacity greater than 0!");
        }
        this.desiredCapacity = n;
        this.objects = new Object[this.desiredCapacity];
        this.objectsPointer = 0;
        this.modelObject = t;
        this.replenishPercentage = 1.0f;
        this.refillPool();
    }

    public static ObjectPool create(int n, Poolable object) {
        synchronized (ObjectPool.class) {
            object = new ObjectPool<Poolable>(n, (Poolable)object);
            ((ObjectPool)object).poolId = ids++;
            return object;
        }
    }

    private void refillPool() {
        this.refillPool(this.replenishPercentage);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void refillPool(float f) {
        int n;
        int n2 = (int)((float)this.desiredCapacity * f);
        if (n2 < 1) {
            n = 1;
        } else {
            n = n2;
            if (n2 > this.desiredCapacity) {
                n = this.desiredCapacity;
            }
        }
        n2 = 0;
        do {
            if (n2 >= n) {
                this.objectsPointer = n - 1;
                return;
            }
            this.objects[n2] = ((Poolable)this.modelObject).instantiate();
            ++n2;
        } while (true);
    }

    private void resizePool() {
        int n = this.desiredCapacity;
        this.desiredCapacity *= 2;
        Object[] arrobject = new Object[this.desiredCapacity];
        for (int i = 0; i < n; ++i) {
            arrobject[i] = this.objects[i];
        }
        this.objects = arrobject;
    }

    public T get() {
        synchronized (this) {
            if (this.objectsPointer == -1 && this.replenishPercentage > 0.0f) {
                this.refillPool();
            }
            Poolable poolable = (Poolable)this.objects[this.objectsPointer];
            poolable.currentOwnerId = Poolable.NO_OWNER;
            --this.objectsPointer;
            return (T)poolable;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void recycle(T t) {
        synchronized (this) {
            if (((Poolable)t).currentOwnerId != Poolable.NO_OWNER) {
                if (((Poolable)t).currentOwnerId == this.poolId) {
                    throw new IllegalArgumentException("The object passed is already stored in this pool!");
                }
                throw new IllegalArgumentException("The object to recycle already belongs to poolId " + ((Poolable)t).currentOwnerId + ".  Object cannot belong to two different pool instances simultaneously!");
            }
            ++this.objectsPointer;
            if (this.objectsPointer >= this.objects.length) {
                this.resizePool();
            }
            ((Poolable)t).currentOwnerId = this.poolId;
            this.objects[this.objectsPointer] = t;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setReplenishPercentage(float f) {
        float f2 = f;
        if (f2 > 1.0f) {
            f = 1.0f;
        } else {
            f = f2;
            if (f2 < 0.0f) {
                f = 0.0f;
            }
        }
        this.replenishPercentage = f;
    }

    public static abstract class Poolable {
        public static int NO_OWNER = -1;
        int currentOwnerId = NO_OWNER;

        protected abstract Poolable instantiate();
    }

}

