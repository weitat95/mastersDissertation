/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Patterns
 */
package com.getqardio.android.utils;

import android.util.Patterns;
import com.getqardio.android.CustomApplication;
import com.getqardio.android.io.network.invite.InviteApi;
import com.getqardio.android.io.network.invite.models.InviteRequest;
import com.getqardio.android.io.network.invite.models.InviteResponse;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.utils.InvitationManager$$Lambda$1;
import com.getqardio.android.utils.InvitationManager$$Lambda$2;
import io.reactivex.Single;
import io.reactivex.SingleTransformer;
import io.reactivex.functions.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class InvitationManager {
    public static String INVITATION_TYPE_QARDIO_ARM;
    public static String INVITATION_TYPE_QARDIO_BASE;
    private final String invitationType;
    private final InviteApi inviteApi;

    static {
        INVITATION_TYPE_QARDIO_BASE = "QB";
        INVITATION_TYPE_QARDIO_ARM = "QA";
    }

    public InvitationManager(String string2, InviteApi inviteApi) {
        this.invitationType = string2;
        this.inviteApi = inviteApi;
    }

    public static boolean isValidEmail(CharSequence charSequence) {
        if (charSequence == null) {
            return false;
        }
        return Patterns.EMAIL_ADDRESS.matcher(charSequence).matches();
    }

    public static boolean isValidName(CharSequence charSequence) {
        return charSequence != null && !charSequence.toString().isEmpty();
    }

    static /* synthetic */ Integer lambda$invite$0(InviteResponse inviteResponse) throws Exception {
        if (inviteResponse != null && inviteResponse.getStatus().equalsIgnoreCase("success")) {
            return 1000;
        }
        return 1003;
    }

    static /* synthetic */ Integer lambda$invite$1(Throwable throwable) throws Exception {
        return 1003;
    }

    public Single<Integer> invite(String string2, String string3, String string4) {
        if (InvitationManager.isValidEmail(string3) && InvitationManager.isValidName(string2)) {
            InviteRequest inviteRequest = new InviteRequest();
            inviteRequest.setRecipientEmail(string3);
            inviteRequest.setRecipientName(string2);
            inviteRequest.setTemplate("invite-new-qb-user");
            inviteRequest.setType("email");
            inviteRequest.setRecipientLocale(string4);
            string2 = "Bearer " + CustomApplication.getApplication().getCurrentUserToken();
            return this.inviteApi.invite(string2, inviteRequest).compose(RxUtil.applySingleSchedulers()).map(InvitationManager$$Lambda$1.lambdaFactory$()).onErrorReturn(InvitationManager$$Lambda$2.lambdaFactory$());
        }
        if (!InvitationManager.isValidName(string2)) {
            return Single.just(1002);
        }
        return Single.just(1001);
    }
}

