/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  javax.annotation.concurrent.GuardedBy
 *  javax.annotation.concurrent.ThreadSafe
 */
package com.facebook.datasource;

import com.facebook.common.executors.CallerThreadExecutor;
import com.facebook.common.internal.Objects;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Supplier;
import com.facebook.datasource.AbstractDataSource;
import com.facebook.datasource.DataSource;
import com.facebook.datasource.DataSubscriber;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.GuardedBy;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class IncreasingQualityDataSourceSupplier<T>
implements Supplier<DataSource<T>> {
    private final List<Supplier<DataSource<T>>> mDataSourceSuppliers;

    /*
     * Enabled aggressive block sorting
     */
    private IncreasingQualityDataSourceSupplier(List<Supplier<DataSource<T>>> list) {
        boolean bl = !list.isEmpty();
        Preconditions.checkArgument(bl, "List of suppliers is empty!");
        this.mDataSourceSuppliers = list;
    }

    public static <T> IncreasingQualityDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> list) {
        return new IncreasingQualityDataSourceSupplier<T>(list);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof IncreasingQualityDataSourceSupplier)) {
            return false;
        }
        object = (IncreasingQualityDataSourceSupplier)object;
        return Objects.equal(this.mDataSourceSuppliers, ((IncreasingQualityDataSourceSupplier)object).mDataSourceSuppliers);
    }

    @Override
    public DataSource<T> get() {
        return new IncreasingQualityDataSource();
    }

    public int hashCode() {
        return this.mDataSourceSuppliers.hashCode();
    }

    public String toString() {
        return Objects.toStringHelper(this).add("list", this.mDataSourceSuppliers).toString();
    }

    @ThreadSafe
    private class IncreasingQualityDataSource
    extends AbstractDataSource<T> {
        @Nullable
        @GuardedBy(value="IncreasingQualityDataSource.this")
        private ArrayList<DataSource<T>> mDataSources;
        @GuardedBy(value="IncreasingQualityDataSource.this")
        private int mIndexOfDataSourceWithResult;

        public IncreasingQualityDataSource() {
            int n;
            this.mIndexOfDataSourceWithResult = n = IncreasingQualityDataSourceSupplier.this.mDataSourceSuppliers.size();
            this.mDataSources = new ArrayList(n);
            int n2 = 0;
            do {
                block4: {
                    block3: {
                        if (n2 >= n) break block3;
                        DataSource dataSource = (DataSource)((Supplier)IncreasingQualityDataSourceSupplier.this.mDataSourceSuppliers.get(n2)).get();
                        this.mDataSources.add(dataSource);
                        dataSource.subscribe(new InternalDataSubscriber(n2), CallerThreadExecutor.getInstance());
                        if (!dataSource.hasResult()) break block4;
                    }
                    return;
                }
                ++n2;
            } while (true);
        }

        private void closeSafely(DataSource<T> dataSource) {
            if (dataSource != null) {
                dataSource.close();
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Nullable
        private DataSource<T> getAndClearDataSource(int n) {
            DataSource dataSource = null;
            synchronized (this) {
                DataSource dataSource2 = dataSource;
                if (this.mDataSources == null) return dataSource2;
                dataSource2 = dataSource;
                if (n >= this.mDataSources.size()) return dataSource2;
                return this.mDataSources.set(n, null);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Nullable
        private DataSource<T> getDataSource(int n) {
            synchronized (this) {
                if (this.mDataSources == null) return null;
                if (n >= this.mDataSources.size()) return null;
                return this.mDataSources.get(n);
            }
        }

        @Nullable
        private DataSource<T> getDataSourceWithResult() {
            synchronized (this) {
                DataSource<T> dataSource = this.getDataSource(this.mIndexOfDataSourceWithResult);
                return dataSource;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void maybeSetIndexOfDataSourceWithResult(int n, DataSource<T> dataSource, boolean bl) {
            int n2;
            int n3;
            block10: {
                synchronized (this) {
                    block9: {
                        n3 = this.mIndexOfDataSourceWithResult;
                        int n4 = this.mIndexOfDataSourceWithResult;
                        if (dataSource != this.getDataSource(n) || n == this.mIndexOfDataSourceWithResult) {
                            return;
                        }
                        if (this.getDataSourceWithResult() == null) break block9;
                        n2 = n4;
                        if (!bl) break block10;
                        n2 = n4;
                        if (n >= this.mIndexOfDataSourceWithResult) break block10;
                    }
                    n2 = n;
                    this.mIndexOfDataSourceWithResult = n;
                }
            }
            n = n3;
            while (n > n2) {
                this.closeSafely(this.getAndClearDataSource(n));
                --n;
            }
            return;
        }

        private void onDataSourceFailed(int n, DataSource<T> dataSource) {
            this.closeSafely(this.tryGetAndClearDataSource(n, dataSource));
            if (n == 0) {
                this.setFailure(dataSource.getFailureCause());
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private void onDataSourceNewResult(int n, DataSource<T> dataSource) {
            this.maybeSetIndexOfDataSourceWithResult(n, dataSource, dataSource.isFinished());
            if (dataSource == this.getDataSourceWithResult()) {
                boolean bl = n == 0 && dataSource.isFinished();
                this.setResult(null, bl);
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Nullable
        private DataSource<T> tryGetAndClearDataSource(int n, DataSource<T> dataSource) {
            synchronized (this) {
                DataSource<T> dataSource2;
                block6: {
                    dataSource2 = this.getDataSourceWithResult();
                    if (dataSource != dataSource2) break block6;
                    return null;
                }
                dataSource2 = dataSource;
                if (dataSource != this.getDataSource(n)) return dataSource2;
                dataSource2 = this.getAndClearDataSource(n);
                return dataSource2;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Converted monitor instructions to comments
         * Lifted jumps to return sites
         */
        @Override
        public boolean close() {
            // MONITORENTER : this
            if (!super.close()) {
                // MONITOREXIT : this
                return false;
            }
            ArrayList<DataSource<T>> arrayList = this.mDataSources;
            this.mDataSources = null;
            // MONITOREXIT : this
            if (arrayList == null) return true;
            int n = 0;
            while (n < arrayList.size()) {
                this.closeSafely(arrayList.get(n));
                ++n;
            }
            return true;
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Nullable
        @Override
        public T getResult() {
            synchronized (this) {
                void var1_3;
                DataSource<T> dataSource = this.getDataSourceWithResult();
                if (dataSource != null) {
                    T t = dataSource.getResult();
                } else {
                    Object var1_4 = null;
                }
                return var1_3;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Override
        public boolean hasResult() {
            synchronized (this) {
                DataSource<T> dataSource = this.getDataSourceWithResult();
                if (dataSource == null) return false;
                boolean bl = dataSource.hasResult();
                if (!bl) return false;
                return true;
            }
        }

        private class InternalDataSubscriber
        implements DataSubscriber<T> {
            private int mIndex;

            public InternalDataSubscriber(int n) {
                this.mIndex = n;
            }

            @Override
            public void onCancellation(DataSource<T> dataSource) {
            }

            @Override
            public void onFailure(DataSource<T> dataSource) {
                IncreasingQualityDataSource.this.onDataSourceFailed(this.mIndex, dataSource);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onNewResult(DataSource<T> dataSource) {
                if (dataSource.hasResult()) {
                    IncreasingQualityDataSource.this.onDataSourceNewResult(this.mIndex, dataSource);
                    return;
                } else {
                    if (!dataSource.isFinished()) return;
                    {
                        IncreasingQualityDataSource.this.onDataSourceFailed(this.mIndex, dataSource);
                        return;
                    }
                }
            }

            @Override
            public void onProgressUpdate(DataSource<T> dataSource) {
                if (this.mIndex == 0) {
                    IncreasingQualityDataSource.this.setProgress(dataSource.getProgress());
                }
            }
        }

    }

}

