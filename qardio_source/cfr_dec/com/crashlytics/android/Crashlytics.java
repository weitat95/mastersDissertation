/*
 * Decompiled with CFR 0.147.
 */
package com.crashlytics.android;

import com.crashlytics.android.answers.Answers;
import com.crashlytics.android.beta.Beta;
import com.crashlytics.android.core.CrashlyticsCore;
import io.fabric.sdk.android.Fabric;
import io.fabric.sdk.android.Kit;
import io.fabric.sdk.android.KitGroup;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;

public class Crashlytics
extends Kit<Void>
implements KitGroup {
    public final Answers answers;
    public final Beta beta;
    public final CrashlyticsCore core;
    public final Collection<? extends Kit> kits;

    public Crashlytics() {
        this(new Answers(), new Beta(), new CrashlyticsCore());
    }

    Crashlytics(Answers answers, Beta beta, CrashlyticsCore crashlyticsCore) {
        this.answers = answers;
        this.beta = beta;
        this.core = crashlyticsCore;
        this.kits = Collections.unmodifiableCollection(Arrays.asList(answers, beta, crashlyticsCore));
    }

    private static void checkInitialized() {
        if (Crashlytics.getInstance() == null) {
            throw new IllegalStateException("Crashlytics must be initialized by calling Fabric.with(Context) prior to calling Crashlytics.getInstance()");
        }
    }

    public static Crashlytics getInstance() {
        return Fabric.getKit(Crashlytics.class);
    }

    public static void log(int n, String string2, String string3) {
        Crashlytics.checkInitialized();
        Crashlytics.getInstance().core.log(n, string2, string3);
    }

    public static void logException(Throwable throwable) {
        Crashlytics.checkInitialized();
        Crashlytics.getInstance().core.logException(throwable);
    }

    public static void setBool(String string2, boolean bl) {
        Crashlytics.checkInitialized();
        Crashlytics.getInstance().core.setBool(string2, bl);
    }

    public static void setInt(String string2, int n) {
        Crashlytics.checkInitialized();
        Crashlytics.getInstance().core.setInt(string2, n);
    }

    public static void setString(String string2, String string3) {
        Crashlytics.checkInitialized();
        Crashlytics.getInstance().core.setString(string2, string3);
    }

    @Override
    protected Void doInBackground() {
        return null;
    }

    @Override
    public String getIdentifier() {
        return "com.crashlytics.sdk.android:crashlytics";
    }

    @Override
    public Collection<? extends Kit> getKits() {
        return this.kits;
    }

    @Override
    public String getVersion() {
        return "2.6.6.167";
    }

    public static class Builder {
        private Answers answers;
        private Beta beta;
        private CrashlyticsCore core;
        private CrashlyticsCore.Builder coreBuilder;

        public Crashlytics build() {
            if (this.coreBuilder != null) {
                if (this.core != null) {
                    throw new IllegalStateException("Must not use Deprecated methods delay(), disabled(), listener(), pinningInfoProvider() with core()");
                }
                this.core = this.coreBuilder.build();
            }
            if (this.answers == null) {
                this.answers = new Answers();
            }
            if (this.beta == null) {
                this.beta = new Beta();
            }
            if (this.core == null) {
                this.core = new CrashlyticsCore();
            }
            return new Crashlytics(this.answers, this.beta, this.core);
        }

        public Builder core(CrashlyticsCore crashlyticsCore) {
            if (crashlyticsCore == null) {
                throw new NullPointerException("CrashlyticsCore Kit must not be null.");
            }
            if (this.core != null) {
                throw new IllegalStateException("CrashlyticsCore Kit already set.");
            }
            this.core = crashlyticsCore;
            return this;
        }
    }

}

