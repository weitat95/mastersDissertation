/*
 * Decompiled with CFR 0.147.
 */
package io.realm.internal;

import io.realm.RealmChangeListener;
import io.realm.RealmFieldType;
import io.realm.internal.CheckedRow;
import io.realm.internal.Collection;
import io.realm.internal.InvalidRow;
import io.realm.internal.LinkView;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.Table;
import io.realm.internal.UncheckedRow;
import java.lang.ref.WeakReference;
import java.util.Date;

public class PendingRow
implements Row {
    private WeakReference<FrontEnd> frontEndRef;
    private RealmChangeListener<PendingRow> listener;
    private Collection pendingCollection;
    private boolean returnCheckedRow;
    private SharedRealm sharedRealm;

    private void clearPendingCollection() {
        this.pendingCollection.removeListener(this, this.listener);
        this.pendingCollection = null;
        this.listener = null;
        this.sharedRealm.removePendingRow(this);
    }

    private void notifyFrontEnd() {
        if (this.frontEndRef == null) {
            throw new IllegalStateException("The 'frontEnd' has not been set.");
        }
        FrontEnd frontEnd = (FrontEnd)this.frontEndRef.get();
        if (frontEnd == null) {
            this.clearPendingCollection();
            return;
        }
        if (this.pendingCollection.isValid()) {
            UncheckedRow uncheckedRow = this.pendingCollection.firstUncheckedRow();
            this.clearPendingCollection();
            if (uncheckedRow != null) {
                if (this.returnCheckedRow) {
                    uncheckedRow = CheckedRow.getFromRow(uncheckedRow);
                }
                frontEnd.onQueryFinished(uncheckedRow);
                return;
            }
            frontEnd.onQueryFinished(InvalidRow.INSTANCE);
            return;
        }
        this.clearPendingCollection();
    }

    public void executeQuery() {
        if (this.pendingCollection == null) {
            throw new IllegalStateException("The query has been executed. This 'PendingRow' is not valid anymore.");
        }
        this.notifyFrontEnd();
    }

    @Override
    public byte[] getBinaryByteArray(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public boolean getBoolean(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public long getColumnCount() {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public long getColumnIndex(String string2) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public String getColumnName(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public RealmFieldType getColumnType(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public Date getDate(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public double getDouble(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public float getFloat(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public long getIndex() {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public long getLink(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public LinkView getLinkList(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public long getLong(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public String getString(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public Table getTable() {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public boolean isAttached() {
        return false;
    }

    @Override
    public boolean isNull(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public boolean isNullLink(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public void nullifyLink(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public void setBoolean(long l, boolean bl) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public void setFloat(long l, float f) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public void setLink(long l, long l2) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public void setLong(long l, long l2) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public void setNull(long l) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    @Override
    public void setString(long l, String string2) {
        throw new IllegalStateException("The pending query has not been executed.");
    }

    public static interface FrontEnd {
        public void onQueryFinished(Row var1);
    }

}

