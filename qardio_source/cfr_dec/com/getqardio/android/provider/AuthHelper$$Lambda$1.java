/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.provider;

import com.getqardio.android.provider.AuthHelper;
import io.realm.Realm;
import java.lang.invoke.LambdaForm;

final class AuthHelper$$Lambda$1
implements Realm.Transaction {
    private final String arg$1;
    private final String arg$2;
    private final long arg$3;
    private final Long arg$4;
    private final String arg$5;
    private final String arg$6;

    private AuthHelper$$Lambda$1(String string2, String string3, long l, Long l2, String string4, String string5) {
        this.arg$1 = string2;
        this.arg$2 = string3;
        this.arg$3 = l;
        this.arg$4 = l2;
        this.arg$5 = string4;
        this.arg$6 = string5;
    }

    public static Realm.Transaction lambdaFactory$(String string2, String string3, long l, Long l2, String string4, String string5) {
        return new AuthHelper$$Lambda$1(string2, string3, l, l2, string4, string5);
    }

    @LambdaForm.Hidden
    @Override
    public void execute(Realm realm) {
        AuthHelper.lambda$saveOrUpdateUserInRealm$0(this.arg$1, this.arg$2, this.arg$3, this.arg$4, this.arg$5, this.arg$6, realm);
    }
}

