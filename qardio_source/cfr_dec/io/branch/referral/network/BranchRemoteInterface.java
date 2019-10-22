/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.referral.network;

import android.content.Context;
import io.branch.referral.Branch;
import io.branch.referral.Defines;
import io.branch.referral.PrefHelper;
import io.branch.referral.ServerResponse;
import io.branch.referral.network.BranchRemoteInterfaceUrlConnection;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public abstract class BranchRemoteInterface {
    private boolean addCommonParams(JSONObject jSONObject, String string2) {
        try {
            jSONObject.put("sdk", (Object)"android2.12.1");
            if (!string2.equals("bnc_no_value")) {
                jSONObject.put(Defines.Jsonkey.BranchKey.getKey(), (Object)string2);
                return true;
            }
        }
        catch (JSONException jSONException) {
            // empty catch block
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private String convertJSONtoString(JSONObject jSONObject) {
        JSONArray jSONArray;
        StringBuilder stringBuilder = new StringBuilder();
        if (jSONObject != null && (jSONArray = jSONObject.names()) != null) {
            boolean bl = true;
            int n = jSONArray.length();
            for (int i = 0; i < n; ++i) {
                try {
                    String string2 = jSONArray.getString(i);
                    if (bl) {
                        stringBuilder.append("?");
                        bl = false;
                    } else {
                        stringBuilder.append("&");
                    }
                    String string3 = jSONObject.getString(string2);
                    stringBuilder.append(string2).append("=").append(string3);
                    continue;
                }
                catch (JSONException jSONException) {
                    jSONException.printStackTrace();
                    return null;
                }
            }
        }
        return stringBuilder.toString();
    }

    public static final BranchRemoteInterface getDefaultBranchRemoteInterface(Context context) {
        if (false) {
            return null;
        }
        return new BranchRemoteInterfaceUrlConnection(context);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private ServerResponse processEntityForJSON(String string2, int n, String object) {
        object = new ServerResponse((String)object, n);
        PrefHelper.Debug("BranchSDK", "returned " + string2);
        if (string2 == null) return object;
        try {
            ((ServerResponse)object).setPost((Object)new JSONObject(string2));
        }
        catch (JSONException jSONException) {
            try {
                ((ServerResponse)object).setPost((Object)new JSONArray(string2));
                return object;
            }
            catch (JSONException jSONException2) {
                PrefHelper.Debug(this.getClass().getSimpleName(), "JSON exception: " + jSONException2.getMessage());
                return object;
            }
        }
        return object;
    }

    public abstract BranchResponse doRestfulGet(String var1) throws BranchRemoteException;

    public abstract BranchResponse doRestfulPost(String var1, JSONObject var2) throws BranchRemoteException;

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public final ServerResponse make_restful_get(String object, JSONObject object2, String string2, String string3) {
        ServerResponse serverResponse;
        void var4_11;
        void var2_5;
        void var3_10;
        if (object2 == null) {
            JSONObject jSONObject = new JSONObject();
        }
        if (!this.addCommonParams((JSONObject)var2_5, (String)var4_11)) {
            return new ServerResponse((String)var3_10, -114);
        }
        object = (String)object + this.convertJSONtoString((JSONObject)var2_5);
        long l = System.currentTimeMillis();
        PrefHelper.Debug("BranchSDK", "getting " + (String)object);
        try {
            object = this.doRestfulGet((String)object);
            serverResponse = this.processEntityForJSON(((BranchResponse)object).responseData, ((BranchResponse)object).responseCode, (String)var3_10);
            object = serverResponse;
        }
        catch (BranchRemoteException branchRemoteException) {
            block7: {
                ServerResponse serverResponse2;
                try {
                    if (branchRemoteException.branchErrorCode != -111) break block7;
                    serverResponse2 = new ServerResponse((String)var3_10, -111);
                    object = serverResponse2;
                }
                catch (Throwable throwable) {
                    if (Branch.getInstance() == null) throw throwable;
                    int n = (int)(System.currentTimeMillis() - l);
                    Branch.getInstance().addExtraInstrumentationData((String)var3_10 + "-" + Defines.Jsonkey.Branch_Round_Trip_Time.getKey(), String.valueOf(n));
                    throw throwable;
                }
                if (Branch.getInstance() == null) return object;
                int n = (int)(System.currentTimeMillis() - l);
                Branch.getInstance().addExtraInstrumentationData((String)var3_10 + "-" + Defines.Jsonkey.Branch_Round_Trip_Time.getKey(), String.valueOf(n));
                return serverResponse2;
            }
            ServerResponse serverResponse3 = new ServerResponse((String)var3_10, -113);
            object = serverResponse3;
            if (Branch.getInstance() == null) return object;
            int n = (int)(System.currentTimeMillis() - l);
            Branch.getInstance().addExtraInstrumentationData((String)var3_10 + "-" + Defines.Jsonkey.Branch_Round_Trip_Time.getKey(), String.valueOf(n));
            return serverResponse3;
        }
        if (Branch.getInstance() == null) return object;
        int n = (int)(System.currentTimeMillis() - l);
        Branch.getInstance().addExtraInstrumentationData((String)var3_10 + "-" + Defines.Jsonkey.Branch_Round_Trip_Time.getKey(), String.valueOf(n));
        return serverResponse;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public final ServerResponse make_restful_post(JSONObject object, String object2, String string2, String string3) {
        void var3_14;
        void var1_2;
        ServerResponse serverResponse;
        void var1_4;
        void var4_15;
        long l = System.currentTimeMillis();
        if (object == null) {
            JSONObject jSONObject = new JSONObject();
        }
        if (!this.addCommonParams((JSONObject)var1_2, (String)var4_15)) {
            ServerResponse serverResponse2 = new ServerResponse((String)var3_14, -114);
            return var1_4;
        }
        PrefHelper.Debug("BranchSDK", "posting to " + (String)((Object)serverResponse));
        PrefHelper.Debug("BranchSDK", "Post value = " + var1_2.toString());
        try {
            BranchResponse branchResponse = this.doRestfulPost((String)((Object)serverResponse), (JSONObject)var1_2);
            ServerResponse serverResponse3 = serverResponse = this.processEntityForJSON(branchResponse.responseData, branchResponse.responseCode, (String)var3_14);
        }
        catch (BranchRemoteException branchRemoteException) {
            block7: {
                try {
                    if (branchRemoteException.branchErrorCode != -111) break block7;
                    ServerResponse serverResponse4 = serverResponse = new ServerResponse((String)var3_14, -111);
                }
                catch (Throwable throwable) {
                    if (Branch.getInstance() == null) throw throwable;
                    int n = (int)(System.currentTimeMillis() - l);
                    Branch.getInstance().addExtraInstrumentationData((String)var3_14 + "-" + Defines.Jsonkey.Branch_Round_Trip_Time.getKey(), String.valueOf(n));
                    throw throwable;
                }
                if (Branch.getInstance() == null) return var1_4;
                int n = (int)(System.currentTimeMillis() - l);
                Branch.getInstance().addExtraInstrumentationData((String)var3_14 + "-" + Defines.Jsonkey.Branch_Round_Trip_Time.getKey(), String.valueOf(n));
                return serverResponse;
            }
            ServerResponse serverResponse5 = serverResponse = new ServerResponse((String)var3_14, -113);
            if (Branch.getInstance() == null) return var1_4;
            int n = (int)(System.currentTimeMillis() - l);
            Branch.getInstance().addExtraInstrumentationData((String)var3_14 + "-" + Defines.Jsonkey.Branch_Round_Trip_Time.getKey(), String.valueOf(n));
            return serverResponse;
        }
        if (Branch.getInstance() == null) return var1_4;
        int n = (int)(System.currentTimeMillis() - l);
        Branch.getInstance().addExtraInstrumentationData((String)var3_14 + "-" + Defines.Jsonkey.Branch_Round_Trip_Time.getKey(), String.valueOf(n));
        return serverResponse;
    }

    public static class BranchRemoteException
    extends Exception {
        private int branchErrorCode = -113;

        public BranchRemoteException(int n) {
            this.branchErrorCode = n;
        }
    }

    public static class BranchResponse {
        private final int responseCode;
        private final String responseData;

        public BranchResponse(String string2, int n) {
            this.responseData = string2;
            this.responseCode = n;
        }
    }

}

