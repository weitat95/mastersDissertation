/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.fitness;

import com.google.android.gms.common.api.Scope;
import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public final class zzh {
    /*
     * Enabled aggressive block sorting
     */
    public static Set<Scope> zzi(Collection<Scope> collection) {
        HashSet<Scope> hashSet = new HashSet<Scope>(collection.size());
        Iterator<Scope> iterator = collection.iterator();
        while (iterator.hasNext()) {
            Scope scope = iterator.next();
            Scope scope2 = scope.equals(new Scope("https://www.googleapis.com/auth/fitness.activity.read")) ? new Scope("https://www.googleapis.com/auth/fitness.activity.write") : (scope.equals(new Scope("https://www.googleapis.com/auth/fitness.location.read")) ? new Scope("https://www.googleapis.com/auth/fitness.location.write") : (scope.equals(new Scope("https://www.googleapis.com/auth/fitness.body.read")) ? new Scope("https://www.googleapis.com/auth/fitness.body.write") : (scope.equals(new Scope("https://www.googleapis.com/auth/fitness.nutrition.read")) ? new Scope("https://www.googleapis.com/auth/fitness.nutrition.write") : (scope.equals(new Scope("https://www.googleapis.com/auth/fitness.blood_pressure.read")) ? new Scope("https://www.googleapis.com/auth/fitness.blood_pressure.write") : (scope.equals(new Scope("https://www.googleapis.com/auth/fitness.blood_glucose.read")) ? new Scope("https://www.googleapis.com/auth/fitness.blood_glucose.write") : (scope.equals(new Scope("https://www.googleapis.com/auth/fitness.oxygen_saturation.read")) ? new Scope("https://www.googleapis.com/auth/fitness.oxygen_saturation.write") : (scope.equals(new Scope("https://www.googleapis.com/auth/fitness.body_temperature.read")) ? new Scope("https://www.googleapis.com/auth/fitness.body_temperature.write") : (scope.equals(new Scope("https://www.googleapis.com/auth/fitness.reproductive_health.read")) ? new Scope("https://www.googleapis.com/auth/fitness.reproductive_health.write") : scope))))))));
            if (!scope2.equals(scope) && collection.contains(scope2)) continue;
            hashSet.add(scope);
        }
        return hashSet;
    }
}

