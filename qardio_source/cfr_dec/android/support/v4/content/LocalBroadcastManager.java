/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.BroadcastReceiver
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.content.Intent
 *  android.content.IntentFilter
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 *  android.os.Message
 *  android.util.Log
 */
package android.support.v4.content;

import android.content.BroadcastReceiver;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public final class LocalBroadcastManager {
    private static LocalBroadcastManager mInstance;
    private static final Object mLock;
    private final HashMap<String, ArrayList<ReceiverRecord>> mActions;
    private final Context mAppContext;
    private final Handler mHandler;
    private final ArrayList<BroadcastRecord> mPendingBroadcasts;
    private final HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> mReceivers = new HashMap();

    static {
        mLock = new Object();
    }

    private LocalBroadcastManager(Context context) {
        this.mActions = new HashMap();
        this.mPendingBroadcasts = new ArrayList();
        this.mAppContext = context;
        this.mHandler = new Handler(context.getMainLooper()){

            public void handleMessage(Message message) {
                switch (message.what) {
                    default: {
                        super.handleMessage(message);
                        return;
                    }
                    case 1: 
                }
                LocalBroadcastManager.this.executePendingBroadcasts();
            }
        };
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void executePendingBroadcasts() {
        block3: do {
            BroadcastRecord[] arrbroadcastRecord;
            int n;
            Object object = this.mReceivers;
            synchronized (object) {
                n = this.mPendingBroadcasts.size();
                if (n <= 0) {
                    return;
                }
                arrbroadcastRecord = new BroadcastRecord[n];
                this.mPendingBroadcasts.toArray(arrbroadcastRecord);
                this.mPendingBroadcasts.clear();
            }
            n = 0;
            do {
                if (n >= arrbroadcastRecord.length) continue block3;
                object = arrbroadcastRecord[n];
                int n2 = ((BroadcastRecord)object).receivers.size();
                for (int i = 0; i < n2; ++i) {
                    ReceiverRecord receiverRecord = ((BroadcastRecord)object).receivers.get(i);
                    if (receiverRecord.dead) continue;
                    receiverRecord.receiver.onReceive(this.mAppContext, ((BroadcastRecord)object).intent);
                }
                ++n;
            } while (true);
            break;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static LocalBroadcastManager getInstance(Context object) {
        Object object2 = mLock;
        synchronized (object2) {
            if (mInstance != null) return mInstance;
            mInstance = new LocalBroadcastManager(object.getApplicationContext());
            return mInstance;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void registerReceiver(BroadcastReceiver object, IntentFilter intentFilter) {
        HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ReceiverRecord receiverRecord = new ReceiverRecord(intentFilter, (BroadcastReceiver)object);
            Object object2 = this.mReceivers.get(object);
            ArrayList<ReceiverRecord> arrayList = object2;
            if (object2 == null) {
                arrayList = new ArrayList(1);
                this.mReceivers.put((BroadcastReceiver)object, arrayList);
            }
            arrayList.add(receiverRecord);
            int n = 0;
            while (n < intentFilter.countActions()) {
                object2 = intentFilter.getAction(n);
                arrayList = this.mActions.get(object2);
                object = arrayList;
                if (arrayList == null) {
                    object = new ArrayList(1);
                    this.mActions.put((String)object2, (ArrayList<ReceiverRecord>)object);
                }
                ((ArrayList)object).add(receiverRecord);
                ++n;
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean sendBroadcast(Intent intent) {
        HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ArrayList arrayList;
            int n;
            block28: {
                block27: {
                    ArrayList<ReceiverRecord> arrayList2;
                    String string2 = intent.getAction();
                    String string3 = intent.resolveTypeIfNeeded(this.mAppContext.getContentResolver());
                    Uri uri = intent.getData();
                    String string4 = intent.getScheme();
                    Set set = intent.getCategories();
                    n = (intent.getFlags() & 8) != 0 ? 1 : 0;
                    if (n != 0) {
                        Log.v((String)"LocalBroadcastManager", (String)("Resolving type " + string3 + " scheme " + string4 + " of intent " + (Object)intent));
                    }
                    if ((arrayList2 = this.mActions.get(intent.getAction())) == null) break block27;
                    if (n != 0) {
                        Log.v((String)"LocalBroadcastManager", (String)("Action list: " + arrayList2));
                    }
                    arrayList = null;
                    for (int i = 0; i < arrayList2.size(); ++i) {
                        ArrayList arrayList3;
                        ReceiverRecord receiverRecord = arrayList2.get(i);
                        if (n != 0) {
                            Log.v((String)"LocalBroadcastManager", (String)("Matching against filter " + (Object)receiverRecord.filter));
                        }
                        if (receiverRecord.broadcasting) {
                            arrayList3 = arrayList;
                            if (n != 0) {
                                Log.v((String)"LocalBroadcastManager", (String)"  Filter's target already added");
                                arrayList3 = arrayList;
                            }
                        } else {
                            int n2 = receiverRecord.filter.match(string2, string3, string4, uri, set, "LocalBroadcastManager");
                            if (n2 >= 0) {
                                if (n != 0) {
                                    Log.v((String)"LocalBroadcastManager", (String)("  Filter matched!  match=0x" + Integer.toHexString(n2)));
                                }
                                arrayList3 = arrayList;
                                if (arrayList == null) {
                                    arrayList3 = new ArrayList();
                                }
                                arrayList3.add(receiverRecord);
                                receiverRecord.broadcasting = true;
                            } else {
                                arrayList3 = arrayList;
                                if (n != 0) {
                                    switch (n2) {
                                        default: {
                                            arrayList3 = "unknown reason";
                                            break;
                                        }
                                        case -3: {
                                            arrayList3 = "action";
                                            break;
                                        }
                                        case -4: {
                                            arrayList3 = "category";
                                            break;
                                        }
                                        case -2: {
                                            arrayList3 = "data";
                                            break;
                                        }
                                        case -1: {
                                            arrayList3 = "type";
                                        }
                                    }
                                    Log.v((String)"LocalBroadcastManager", (String)("  Filter did not match: " + arrayList3));
                                    arrayList3 = arrayList;
                                }
                            }
                        }
                        arrayList = arrayList3;
                    }
                    if (arrayList != null) break block28;
                }
                return false;
            }
            for (n = 0; n < arrayList.size(); ++n) {
                ((ReceiverRecord)arrayList.get((int)n)).broadcasting = false;
            }
            this.mPendingBroadcasts.add(new BroadcastRecord(intent, arrayList));
            if (!this.mHandler.hasMessages(1)) {
                this.mHandler.sendEmptyMessage(1);
            }
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void unregisterReceiver(BroadcastReceiver broadcastReceiver) {
        HashMap<BroadcastReceiver, ArrayList<ReceiverRecord>> hashMap = this.mReceivers;
        synchronized (hashMap) {
            ArrayList<ReceiverRecord> arrayList = this.mReceivers.remove((Object)broadcastReceiver);
            if (arrayList == null) {
                return;
            }
            int n = arrayList.size() - 1;
            block2 : while (n >= 0) {
                ReceiverRecord receiverRecord = arrayList.get(n);
                receiverRecord.dead = true;
                int n2 = 0;
                do {
                    block11: {
                        String string2;
                        ArrayList<ReceiverRecord> arrayList2;
                        block12: {
                            block10: {
                                if (n2 >= receiverRecord.filter.countActions()) break block10;
                                string2 = receiverRecord.filter.getAction(n2);
                                arrayList2 = this.mActions.get(string2);
                                if (arrayList2 == null) break block11;
                                break block12;
                            }
                            --n;
                            continue block2;
                        }
                        int n3 = arrayList2.size() - 1;
                        do {
                            if (n3 >= 0) {
                                ReceiverRecord receiverRecord2 = arrayList2.get(n3);
                                if (receiverRecord2.receiver == broadcastReceiver) {
                                    receiverRecord2.dead = true;
                                    arrayList2.remove(n3);
                                }
                            } else {
                                if (arrayList2.size() > 0) break;
                                this.mActions.remove(string2);
                                break;
                            }
                            --n3;
                        } while (true);
                    }
                    ++n2;
                } while (true);
                break;
            }
            return;
        }
    }

    private static final class BroadcastRecord {
        final Intent intent;
        final ArrayList<ReceiverRecord> receivers;

        BroadcastRecord(Intent intent, ArrayList<ReceiverRecord> arrayList) {
            this.intent = intent;
            this.receivers = arrayList;
        }
    }

    private static final class ReceiverRecord {
        boolean broadcasting;
        boolean dead;
        final IntentFilter filter;
        final BroadcastReceiver receiver;

        ReceiverRecord(IntentFilter intentFilter, BroadcastReceiver broadcastReceiver) {
            this.filter = intentFilter;
            this.receiver = broadcastReceiver;
        }

        public String toString() {
            StringBuilder stringBuilder = new StringBuilder(128);
            stringBuilder.append("Receiver{");
            stringBuilder.append((Object)this.receiver);
            stringBuilder.append(" filter=");
            stringBuilder.append((Object)this.filter);
            if (this.dead) {
                stringBuilder.append(" DEAD");
            }
            stringBuilder.append("}");
            return stringBuilder.toString();
        }
    }

}

