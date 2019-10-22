/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.io.network.invite;

import com.getqardio.android.io.network.invite.models.InviteRequest;
import com.getqardio.android.io.network.invite.models.InviteResponse;
import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface InviteApi {
    @POST(value="/users/messages")
    public Single<InviteResponse> invite(@Header(value="Authorization") String var1, @Body InviteRequest var2);
}

