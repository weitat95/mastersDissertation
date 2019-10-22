/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.SuppressLint
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package io.branch.referral;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import io.branch.referral.Branch;
import io.branch.referral.Defines;
import io.branch.referral.PrefHelper;
import io.branch.referral.ServerRequest;
import io.branch.referral.ServerRequestInitSession;
import io.branch.referral.ServerRequestLogout;
import io.branch.referral.ServerRequestRegisterClose;
import io.branch.referral.ServerRequestRegisterInstall;
import io.branch.referral.ServerRequestRegisterOpen;
import java.util.Collections;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class ServerRequestQueue {
    private static ServerRequestQueue SharedInstance;
    private SharedPreferences.Editor editor;
    private final List<ServerRequest> queue;
    private SharedPreferences sharedPref;

    @SuppressLint(value={"CommitPrefEdits"})
    private ServerRequestQueue(Context context) {
        this.sharedPref = context.getSharedPreferences("BNC_Server_Request_Queue", 0);
        this.editor = this.sharedPref.edit();
        this.queue = this.retrieve(context);
    }

    static /* synthetic */ List access$000(ServerRequestQueue serverRequestQueue) {
        return serverRequestQueue.queue;
    }

    static /* synthetic */ SharedPreferences.Editor access$100(ServerRequestQueue serverRequestQueue) {
        return serverRequestQueue.editor;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static ServerRequestQueue getInstance(Context context) {
        if (SharedInstance == null) {
            synchronized (ServerRequestQueue.class) {
                if (SharedInstance == null) {
                    SharedInstance = new ServerRequestQueue(context);
                }
            }
        }
        return SharedInstance;
    }

    private void persist() {
        new Thread(new Runnable(){

            /*
             * Unable to fully structure code
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                list = ServerRequestQueue.access$000(ServerRequestQueue.this);
                synchronized (list) {
                    jSONArray = new JSONArray();
                    iterator = ServerRequestQueue.access$000(ServerRequestQueue.this).iterator();
                    while (iterator.hasNext()) {
                        jSONObject = ((ServerRequest)iterator.next()).toJSON();
                        if (jSONObject == null) continue;
                        jSONArray.put((Object)jSONObject);
                    }
                    ServerRequestQueue.access$100(ServerRequestQueue.this).putString("BNCServerRequestQueue", jSONArray.toString()).commit();
                    if (true != false) return;
                    try {
                        block15: {
                            ServerRequestQueue.access$100(ServerRequestQueue.this).putString("BNCServerRequestQueue", jSONArray.toString()).commit();
                            break block15;
                            catch (ConcurrentModificationException concurrentModificationException) {
                                try {
                                    PrefHelper.Debug("Persisting Queue: ", "Failed to persit queue " + concurrentModificationException.getMessage());
                                    if (false != false) return;
                                }
                                catch (Throwable throwable) {
                                    if (false != false) throw throwable;
                                    try {
                                        ServerRequestQueue.access$100(ServerRequestQueue.this).putString("BNCServerRequestQueue", jSONArray.toString()).commit();
                                    }
                                    catch (ConcurrentModificationException concurrentModificationException2) {
                                        throw throwable;
                                    }
                                    throw throwable;
                                }
                                try {
                                    ServerRequestQueue.access$100(ServerRequestQueue.this).putString("BNCServerRequestQueue", jSONArray.toString()).commit();
                                }
                                catch (ConcurrentModificationException concurrentModificationException3) {}
                            }
                        }
lbl38:
                        // 2 sources
                        do {
                            return;
                            break;
                        } while (true);
                    }
                    catch (ConcurrentModificationException concurrentModificationException) {
                        ** continue;
                    }
                }
            }
        }).start();
    }

    private List<ServerRequest> retrieve(Context context) {
        List<ServerRequest> list;
        block6: {
            list = Collections.synchronizedList(new LinkedList());
            String string2 = this.sharedPref.getString("BNCServerRequestQueue", null);
            if (string2 != null) {
                int n;
                try {
                    string2 = new JSONArray(string2);
                    n = 0;
                }
                catch (JSONException jSONException) {
                    // empty catch block
                }
                do {
                    block7: {
                        if (n >= Math.min(string2.length(), 25)) break block6;
                        ServerRequest serverRequest = ServerRequest.fromJSON(string2.getJSONObject(n), context);
                        if (serverRequest == null) break block7;
                        if (serverRequest instanceof ServerRequestRegisterClose || serverRequest instanceof ServerRequestLogout) break block7;
                        list.add(serverRequest);
                    }
                    ++n;
                } while (true);
            }
        }
        return list;
    }

    public void clear() {
        try {
            this.queue.clear();
            this.persist();
            return;
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean containsClose() {
        List<ServerRequest> list = this.queue;
        synchronized (list) {
            ServerRequest serverRequest;
            Iterator<ServerRequest> iterator = this.queue.iterator();
            do {
                if (iterator.hasNext()) continue;
                return false;
            } while ((serverRequest = iterator.next()) == null || !serverRequest.getRequestPath().equals(Defines.RequestPath.RegisterClose.getPath()));
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean containsInstallOrOpen() {
        List<ServerRequest> list = this.queue;
        synchronized (list) {
            ServerRequest serverRequest;
            Iterator<ServerRequest> iterator = this.queue.iterator();
            do {
                if (iterator.hasNext()) continue;
                return false;
            } while ((serverRequest = iterator.next()) == null || !(serverRequest instanceof ServerRequestRegisterInstall) && !(serverRequest instanceof ServerRequestRegisterOpen));
            return true;
        }
    }

    public ServerRequest dequeue() {
        ServerRequest serverRequest;
        ServerRequest serverRequest2 = null;
        ServerRequest serverRequest3 = null;
        serverRequest3 = serverRequest = this.queue.remove(0);
        serverRequest2 = serverRequest;
        try {
            this.persist();
            return serverRequest;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return serverRequest3;
        }
        catch (NoSuchElementException noSuchElementException) {
            return serverRequest2;
        }
    }

    public void enqueue(ServerRequest serverRequest) {
        if (serverRequest != null) {
            this.queue.add(serverRequest);
            if (this.getSize() >= 25) {
                this.queue.remove(1);
            }
            this.persist();
        }
    }

    public int getSize() {
        return this.queue.size();
    }

    public void insert(ServerRequest serverRequest, int n) {
        int n2 = n;
        try {
            if (this.queue.size() < n) {
                n2 = this.queue.size();
            }
            this.queue.add(n2, serverRequest);
            this.persist();
            return;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    public void moveInstallOrOpenToFront(ServerRequest serverRequest, int n, Branch.BranchReferralInitListener list) {
        list = this.queue;
        // MONITORENTER : list
        Iterator<ServerRequest> iterator = this.queue.iterator();
        while (iterator.hasNext()) {
            ServerRequest serverRequest2 = iterator.next();
            if (serverRequest2 == null || !(serverRequest2 instanceof ServerRequestRegisterInstall) && !(serverRequest2 instanceof ServerRequestRegisterOpen)) continue;
            iterator.remove();
            break;
        }
        // MONITOREXIT : list
        if (n == 0) {
            this.insert(serverRequest, 0);
            return;
        }
        this.insert(serverRequest, 1);
    }

    public ServerRequest peek() {
        try {
            ServerRequest serverRequest = this.queue.get(0);
            return serverRequest;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return null;
        }
        catch (NoSuchElementException noSuchElementException) {
            return null;
        }
    }

    public ServerRequest peekAt(int n) {
        try {
            ServerRequest serverRequest = this.queue.get(n);
            return serverRequest;
        }
        catch (IndexOutOfBoundsException indexOutOfBoundsException) {
            return null;
        }
        catch (NoSuchElementException noSuchElementException) {
            return null;
        }
    }

    public boolean remove(ServerRequest serverRequest) {
        boolean bl;
        boolean bl2 = false;
        try {
            bl2 = bl = this.queue.remove(serverRequest);
        }
        catch (UnsupportedOperationException unsupportedOperationException) {
            return bl2;
        }
        this.persist();
        return bl;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setInstallOrOpenCallback(Branch.BranchReferralInitListener branchReferralInitListener) {
        List<ServerRequest> list = this.queue;
        synchronized (list) {
            Iterator<ServerRequest> iterator = this.queue.iterator();
            while (iterator.hasNext()) {
                ServerRequest serverRequest = iterator.next();
                if (serverRequest == null) continue;
                if (serverRequest instanceof ServerRequestRegisterInstall) {
                    ((ServerRequestRegisterInstall)serverRequest).setInitFinishedCallback(branchReferralInitListener);
                    continue;
                }
                if (!(serverRequest instanceof ServerRequestRegisterOpen)) continue;
                ((ServerRequestRegisterOpen)serverRequest).setInitFinishedCallback(branchReferralInitListener);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void setStrongMatchWaitLock() {
        List<ServerRequest> list = this.queue;
        synchronized (list) {
            Iterator<ServerRequest> iterator = this.queue.iterator();
            while (iterator.hasNext()) {
                ServerRequest serverRequest = iterator.next();
                if (serverRequest == null || !(serverRequest instanceof ServerRequestInitSession)) continue;
                serverRequest.addProcessWaitLock(ServerRequest.PROCESS_WAIT_LOCK.STRONG_MATCH_PENDING_WAIT_LOCK);
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unlockProcessWait(ServerRequest.PROCESS_WAIT_LOCK pROCESS_WAIT_LOCK) {
        List<ServerRequest> list = this.queue;
        synchronized (list) {
            Iterator<ServerRequest> iterator = this.queue.iterator();
            while (iterator.hasNext()) {
                ServerRequest serverRequest = iterator.next();
                if (serverRequest == null) continue;
                serverRequest.removeProcessWaitLock(pROCESS_WAIT_LOCK);
            }
            return;
        }
    }

}

