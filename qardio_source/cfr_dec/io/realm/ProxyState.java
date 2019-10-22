/*
 * Decompiled with CFR 0.147.
 */
package io.realm;

import io.realm.BaseRealm;
import io.realm.ObjectChangeSet;
import io.realm.RealmModel;
import io.realm.internal.ObserverPairList;
import io.realm.internal.OsObject;
import io.realm.internal.PendingRow;
import io.realm.internal.Row;
import io.realm.internal.SharedRealm;
import io.realm.internal.UncheckedRow;
import java.util.List;

public final class ProxyState<E extends RealmModel>
implements PendingRow.FrontEnd {
    private static QueryCallback queryCallback = new QueryCallback();
    private boolean acceptDefaultValue;
    private List<String> excludeFields;
    private E model;
    private ObserverPairList<OsObject.ObjectObserverPair> observerPairs = new ObserverPairList();
    private OsObject osObject;
    private BaseRealm realm;
    private Row row;
    private boolean underConstruction = true;

    public ProxyState() {
    }

    public ProxyState(E e) {
        this.model = e;
    }

    private void notifyQueryFinished() {
        this.observerPairs.foreach(queryCallback);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void registerToObjectNotifier() {
        if (this.realm.sharedRealm == null || this.realm.sharedRealm.isClosed() || !this.row.isAttached() || this.osObject != null) {
            return;
        }
        this.osObject = new OsObject(this.realm.sharedRealm, (UncheckedRow)this.row);
        this.osObject.setObserverPairs(this.observerPairs);
        this.observerPairs = null;
    }

    public boolean getAcceptDefaultValue$realm() {
        return this.acceptDefaultValue;
    }

    public List<String> getExcludeFields$realm() {
        return this.excludeFields;
    }

    public BaseRealm getRealm$realm() {
        return this.realm;
    }

    public Row getRow$realm() {
        return this.row;
    }

    public boolean isUnderConstruction() {
        return this.underConstruction;
    }

    @Override
    public void onQueryFinished(Row row) {
        this.row = row;
        this.notifyQueryFinished();
        if (row.isAttached()) {
            this.registerToObjectNotifier();
        }
    }

    public void setAcceptDefaultValue$realm(boolean bl) {
        this.acceptDefaultValue = bl;
    }

    public void setConstructionFinished() {
        this.underConstruction = false;
        this.excludeFields = null;
    }

    public void setExcludeFields$realm(List<String> list) {
        this.excludeFields = list;
    }

    public void setRealm$realm(BaseRealm baseRealm) {
        this.realm = baseRealm;
    }

    public void setRow$realm(Row row) {
        this.row = row;
    }

    private static class QueryCallback
    implements ObserverPairList.Callback<OsObject.ObjectObserverPair> {
        private QueryCallback() {
        }

        @Override
        public void onCalled(OsObject.ObjectObserverPair objectObserverPair, Object object) {
            objectObserverPair.onChange((RealmModel)object, null);
        }
    }

}

