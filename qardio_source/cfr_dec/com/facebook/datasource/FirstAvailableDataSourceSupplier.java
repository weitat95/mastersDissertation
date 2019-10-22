/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
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
import java.util.List;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;
import javax.annotation.concurrent.ThreadSafe;

@ThreadSafe
public class FirstAvailableDataSourceSupplier<T>
implements Supplier<DataSource<T>> {
    private final List<Supplier<DataSource<T>>> mDataSourceSuppliers;

    /*
     * Enabled aggressive block sorting
     */
    private FirstAvailableDataSourceSupplier(List<Supplier<DataSource<T>>> list) {
        boolean bl = !list.isEmpty();
        Preconditions.checkArgument(bl, "List of suppliers is empty!");
        this.mDataSourceSuppliers = list;
    }

    public static <T> FirstAvailableDataSourceSupplier<T> create(List<Supplier<DataSource<T>>> list) {
        return new FirstAvailableDataSourceSupplier<T>(list);
    }

    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (!(object instanceof FirstAvailableDataSourceSupplier)) {
            return false;
        }
        object = (FirstAvailableDataSourceSupplier)object;
        return Objects.equal(this.mDataSourceSuppliers, ((FirstAvailableDataSourceSupplier)object).mDataSourceSuppliers);
    }

    @Override
    public DataSource<T> get() {
        return new FirstAvailableDataSource();
    }

    public int hashCode() {
        return this.mDataSourceSuppliers.hashCode();
    }

    public String toString() {
        return Objects.toStringHelper(this).add("list", this.mDataSourceSuppliers).toString();
    }

    @ThreadSafe
    private class FirstAvailableDataSource
    extends AbstractDataSource<T> {
        private DataSource<T> mCurrentDataSource = null;
        private DataSource<T> mDataSourceWithResult = null;
        private int mIndex = 0;

        public FirstAvailableDataSource() {
            if (!this.startNextDataSource()) {
                this.setFailure(new RuntimeException("No data source supplier or supplier returned null."));
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean clearCurrentDataSource(DataSource<T> dataSource) {
            synchronized (this) {
                block4: {
                    if (this.isClosed()) return false;
                    DataSource<T> dataSource2 = this.mCurrentDataSource;
                    if (dataSource == dataSource2) break block4;
                    return false;
                }
                this.mCurrentDataSource = null;
                return true;
            }
        }

        private void closeSafely(DataSource<T> dataSource) {
            if (dataSource != null) {
                dataSource.close();
            }
        }

        @Nullable
        private DataSource<T> getDataSourceWithResult() {
            synchronized (this) {
                DataSource<T> dataSource = this.mDataSourceWithResult;
                return dataSource;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @Nullable
        private Supplier<DataSource<T>> getNextSupplier() {
            synchronized (this) {
                if (this.isClosed()) return null;
                if (this.mIndex >= FirstAvailableDataSourceSupplier.this.mDataSourceSuppliers.size()) return null;
                List list = FirstAvailableDataSourceSupplier.this.mDataSourceSuppliers;
                int n = this.mIndex;
                this.mIndex = n + 1;
                return (Supplier)list.get(n);
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        private void maybeSetDataSourceWithResult(DataSource<T> dataSource, boolean bl) {
            DataSource<T> dataSource2 = null;
            synchronized (this) {
                if (dataSource != this.mCurrentDataSource || dataSource == this.mDataSourceWithResult) {
                    return;
                }
                if (this.mDataSourceWithResult == null || bl) {
                    dataSource2 = this.mDataSourceWithResult;
                    this.mDataSourceWithResult = dataSource;
                }
            }
            this.closeSafely(dataSource2);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void onDataSourceFailed(DataSource<T> dataSource) {
            block5: {
                block4: {
                    if (!this.clearCurrentDataSource(dataSource)) break block4;
                    if (dataSource != this.getDataSourceWithResult()) {
                        this.closeSafely(dataSource);
                    }
                    if (!this.startNextDataSource()) break block5;
                }
                return;
            }
            this.setFailure(dataSource.getFailureCause());
        }

        private void onDataSourceNewResult(DataSource<T> dataSource) {
            this.maybeSetDataSourceWithResult(dataSource, dataSource.isFinished());
            if (dataSource == this.getDataSourceWithResult()) {
                this.setResult(null, dataSource.isFinished());
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        private boolean setCurrentDataSource(DataSource<T> dataSource) {
            synchronized (this) {
                block4: {
                    boolean bl = this.isClosed();
                    if (!bl) break block4;
                    return false;
                }
                this.mCurrentDataSource = dataSource;
                return true;
            }
        }

        /*
         * Enabled aggressive block sorting
         */
        private boolean startNextDataSource() {
            Object object = this.getNextSupplier();
            object = object != null ? object.get() : null;
            if (this.setCurrentDataSource((DataSource<T>)object) && object != null) {
                object.subscribe(new InternalDataSubscriber(), CallerThreadExecutor.getInstance());
                return true;
            }
            this.closeSafely((DataSource<T>)object);
            return false;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @Override
        public boolean close() {
            DataSource<T> dataSource;
            DataSource<T> dataSource2;
            synchronized (this) {
                if (!super.close()) {
                    return false;
                }
                dataSource2 = this.mCurrentDataSource;
                this.mCurrentDataSource = null;
                dataSource = this.mDataSourceWithResult;
                this.mDataSourceWithResult = null;
            }
            this.closeSafely(dataSource);
            this.closeSafely(dataSource2);
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
            private InternalDataSubscriber() {
            }

            @Override
            public void onCancellation(DataSource<T> dataSource) {
            }

            @Override
            public void onFailure(DataSource<T> dataSource) {
                FirstAvailableDataSource.this.onDataSourceFailed(dataSource);
            }

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void onNewResult(DataSource<T> dataSource) {
                if (dataSource.hasResult()) {
                    FirstAvailableDataSource.this.onDataSourceNewResult(dataSource);
                    return;
                } else {
                    if (!dataSource.isFinished()) return;
                    {
                        FirstAvailableDataSource.this.onDataSourceFailed(dataSource);
                        return;
                    }
                }
            }

            @Override
            public void onProgressUpdate(DataSource<T> dataSource) {
                float f = FirstAvailableDataSource.this.getProgress();
                FirstAvailableDataSource.this.setProgress(Math.max(f, dataSource.getProgress()));
            }
        }

    }

}

