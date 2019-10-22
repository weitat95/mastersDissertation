/*
 * Decompiled with CFR 0.147.
 */
package io.realm.rx;

import io.realm.RealmList;
import io.realm.RealmModel;
import io.realm.RealmResults;
import io.realm.rx.RxObservableFactory;
import java.util.IdentityHashMap;
import java.util.Map;

public class RealmObservableFactory
implements RxObservableFactory {
    ThreadLocal<StrongReferenceCounter<RealmList>> listRefs;
    ThreadLocal<StrongReferenceCounter<RealmModel>> objectRefs;
    ThreadLocal<StrongReferenceCounter<RealmResults>> resultsRefs = new ThreadLocal<StrongReferenceCounter<RealmResults>>(){

        @Override
        protected StrongReferenceCounter<RealmResults> initialValue() {
            return new StrongReferenceCounter<RealmResults>();
        }
    };

    public RealmObservableFactory() {
        this.listRefs = new ThreadLocal<StrongReferenceCounter<RealmList>>(){

            @Override
            protected StrongReferenceCounter<RealmList> initialValue() {
                return new StrongReferenceCounter<RealmList>();
            }
        };
        this.objectRefs = new ThreadLocal<StrongReferenceCounter<RealmModel>>(){

            @Override
            protected StrongReferenceCounter<RealmModel> initialValue() {
                return new StrongReferenceCounter<RealmModel>();
            }
        };
    }

    public boolean equals(Object object) {
        return object instanceof RealmObservableFactory;
    }

    public int hashCode() {
        return 37;
    }

    private static class StrongReferenceCounter<K> {
        private final Map<K, Integer> references = new IdentityHashMap<K, Integer>();

        private StrongReferenceCounter() {
        }
    }

}

