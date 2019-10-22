/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Application
 *  android.content.Context
 *  android.os.Handler
 *  android.os.HandlerThread
 *  android.os.Looper
 *  android.os.Message
 *  android.util.JsonWriter
 */
package com.segment.analytics;

import android.app.Application;
import android.content.Context;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.JsonWriter;
import com.segment.analytics.Analytics;
import com.segment.analytics.Cartographer;
import com.segment.analytics.Client;
import com.segment.analytics.Crypto;
import com.segment.analytics.PayloadQueue;
import com.segment.analytics.QueueFile;
import com.segment.analytics.Stats;
import com.segment.analytics.ValueMap;
import com.segment.analytics.integrations.AliasPayload;
import com.segment.analytics.integrations.BasePayload;
import com.segment.analytics.integrations.GroupPayload;
import com.segment.analytics.integrations.IdentifyPayload;
import com.segment.analytics.integrations.Integration;
import com.segment.analytics.integrations.Logger;
import com.segment.analytics.integrations.ScreenPayload;
import com.segment.analytics.integrations.TrackPayload;
import com.segment.analytics.internal.Utils;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

class SegmentIntegration
extends Integration<Void> {
    static final Integration.Factory FACTORY = new Integration.Factory(){

        @Override
        public Integration<?> create(ValueMap valueMap, Analytics analytics) {
            return SegmentIntegration.create((Context)analytics.getApplication(), analytics.client, analytics.cartographer, analytics.networkExecutor, analytics.stats, Collections.unmodifiableMap(analytics.bundledIntegrations), analytics.tag, analytics.flushIntervalInMillis, analytics.flushQueueSize, analytics.getLogger(), analytics.crypto);
        }

        @Override
        public String key() {
            return "Segment.io";
        }
    };
    static final Charset UTF_8 = Charset.forName("UTF-8");
    private final Map<String, Boolean> bundledIntegrations;
    private final Cartographer cartographer;
    private final Client client;
    private final Context context;
    private final Crypto crypto;
    final Object flushLock = new Object();
    private final int flushQueueSize;
    private final ScheduledExecutorService flushScheduler;
    private final Handler handler;
    private final Logger logger;
    private final ExecutorService networkExecutor;
    private final PayloadQueue payloadQueue;
    private final HandlerThread segmentThread;
    private final Stats stats;

    /*
     * Enabled aggressive block sorting
     */
    SegmentIntegration(Context context, Client client, Cartographer cartographer, ExecutorService executorService, PayloadQueue payloadQueue, Stats stats, Map<String, Boolean> map, long l, int n, Logger logger, Crypto crypto) {
        this.context = context;
        this.client = client;
        this.networkExecutor = executorService;
        this.payloadQueue = payloadQueue;
        this.stats = stats;
        this.logger = logger;
        this.bundledIntegrations = map;
        this.cartographer = cartographer;
        this.flushQueueSize = n;
        this.flushScheduler = Executors.newScheduledThreadPool(1, new Utils.AnalyticsThreadFactory());
        this.crypto = crypto;
        this.segmentThread = new HandlerThread("Segment-SegmentDispatcher", 10);
        this.segmentThread.start();
        this.handler = new SegmentDispatcherHandler(this.segmentThread.getLooper(), this);
        long l2 = payloadQueue.size() >= n ? 0L : l;
        this.flushScheduler.scheduleAtFixedRate(new Runnable(){

            @Override
            public void run() {
                SegmentIntegration.this.flush();
            }
        }, l2, l, TimeUnit.MILLISECONDS);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static SegmentIntegration create(Context object, Client client, Cartographer cartographer, ExecutorService executorService, Stats stats, Map<String, Boolean> map, String object2, long l, int n, Logger logger, Crypto crypto) {
        synchronized (SegmentIntegration.class) {
            try {
                object2 = new PayloadQueue.PersistentQueue(SegmentIntegration.createQueueFile(object.getDir("segment-disk-queue", 0), (String)object2));
                return new SegmentIntegration((Context)object, client, cartographer, executorService, (PayloadQueue)object2, stats, map, l, n, logger, crypto);
            }
            catch (IOException iOException) {
                logger.error(iOException, "Could not create disk queue. Falling back to memory queue.", new Object[0]);
                object2 = new PayloadQueue.MemoryQueue();
            }
            return new SegmentIntegration((Context)object, client, cartographer, executorService, (PayloadQueue)object2, stats, map, l, n, logger, crypto);
        }
    }

    static QueueFile createQueueFile(File file, String string2) throws IOException {
        Utils.createDirectory(file);
        File file2 = new File(file, string2);
        try {
            QueueFile queueFile = new QueueFile(file2);
            return queueFile;
        }
        catch (IOException iOException) {
            if (file2.delete()) {
                return new QueueFile(file2);
            }
            throw new IOException("Could not create queue file (" + string2 + ") in " + file + ".");
        }
    }

    private void dispatchEnqueue(BasePayload basePayload) {
        this.handler.sendMessage(this.handler.obtainMessage(0, (Object)basePayload));
    }

    private boolean shouldFlush() {
        return this.payloadQueue.size() > 0 && Utils.isConnected(this.context);
    }

    @Override
    public void alias(AliasPayload aliasPayload) {
        this.dispatchEnqueue(aliasPayload);
    }

    @Override
    public void flush() {
        this.handler.sendMessage(this.handler.obtainMessage(1));
    }

    @Override
    public void group(GroupPayload groupPayload) {
        this.dispatchEnqueue(groupPayload);
    }

    @Override
    public void identify(IdentifyPayload identifyPayload) {
        this.dispatchEnqueue(identifyPayload);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void performEnqueue(BasePayload basePayload) {
        byte[] arrby;
        block11: {
            ValueMap valueMap = basePayload.integrations();
            arrby = new LinkedHashMap<String, Object>(valueMap.size() + this.bundledIntegrations.size());
            arrby.putAll(valueMap);
            arrby.putAll(this.bundledIntegrations);
            arrby.remove("Segment.io");
            valueMap = new ValueMap();
            valueMap.putAll(basePayload);
            valueMap.put("integrations", arrby);
            if (this.payloadQueue.size() >= 1000) {
                arrby = this.flushLock;
                // MONITORENTER : arrby
                if (this.payloadQueue.size() >= 1000) {
                    this.logger.info("Queue is at max capacity (%s), removing oldest payload.", this.payloadQueue.size());
                    this.payloadQueue.remove(1);
                    // MONITOREXIT : arrby
                }
            }
            try {
                arrby = new ByteArrayOutputStream();
                OutputStream outputStream = this.crypto.encrypt((OutputStream)arrby);
                this.cartographer.toJson(valueMap, new OutputStreamWriter(outputStream));
                arrby = arrby.toByteArray();
                if (arrby == null) throw new IOException("Could not serialize payload " + valueMap);
                if (arrby.length == 0) throw new IOException("Could not serialize payload " + valueMap);
                if (arrby.length > 15000) {
                    throw new IOException("Could not serialize payload " + valueMap);
                }
                break block11;
            }
            catch (IOException iOException) {
                this.logger.error(iOException, "Could not add payload %s to queue: %s.", valueMap, this.payloadQueue);
                return;
            }
            catch (IOException iOException) {
                this.logger.error(iOException, "Unable to remove oldest payload from queue.", new Object[0]);
                // MONITOREXIT : arrby
                return;
            }
        }
        this.payloadQueue.add(arrby);
        this.logger.verbose("Enqueued %s payload. %s elements in the queue.", basePayload, this.payloadQueue.size());
        if (this.payloadQueue.size() < this.flushQueueSize) return;
        this.submitFlush();
    }

    /*
     * Exception decompiling
     */
    void performFlush() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 8[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    @Override
    public void screen(ScreenPayload screenPayload) {
        this.dispatchEnqueue(screenPayload);
    }

    void submitFlush() {
        if (!this.shouldFlush()) {
            return;
        }
        this.networkExecutor.submit(new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                Object object = SegmentIntegration.this.flushLock;
                synchronized (object) {
                    SegmentIntegration.this.performFlush();
                    return;
                }
            }
        });
    }

    @Override
    public void track(TrackPayload trackPayload) {
        this.dispatchEnqueue(trackPayload);
    }

    static class BatchPayloadWriter
    implements Closeable {
        private final BufferedWriter bufferedWriter;
        private final JsonWriter jsonWriter;
        private boolean needsComma = false;

        BatchPayloadWriter(OutputStream outputStream) {
            this.bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
            this.jsonWriter = new JsonWriter((Writer)this.bufferedWriter);
        }

        BatchPayloadWriter beginBatchArray() throws IOException {
            this.jsonWriter.name("batch").beginArray();
            this.needsComma = false;
            return this;
        }

        BatchPayloadWriter beginObject() throws IOException {
            this.jsonWriter.beginObject();
            return this;
        }

        @Override
        public void close() throws IOException {
            this.jsonWriter.close();
        }

        /*
         * Enabled aggressive block sorting
         */
        BatchPayloadWriter emitPayloadObject(String string2) throws IOException {
            if (this.needsComma) {
                this.bufferedWriter.write(44);
            } else {
                this.needsComma = true;
            }
            this.bufferedWriter.write(string2);
            return this;
        }

        BatchPayloadWriter endBatchArray() throws IOException {
            if (!this.needsComma) {
                throw new IOException("At least one payload must be provided.");
            }
            this.jsonWriter.endArray();
            return this;
        }

        BatchPayloadWriter endObject() throws IOException {
            this.jsonWriter.name("sentAt").value(Utils.toISO8601Date(new Date())).endObject();
            return this;
        }
    }

    static class PayloadWriter
    implements PayloadQueue.ElementVisitor {
        final Crypto crypto;
        int payloadCount;
        int size;
        final BatchPayloadWriter writer;

        PayloadWriter(BatchPayloadWriter batchPayloadWriter, Crypto crypto) {
            this.writer = batchPayloadWriter;
            this.crypto = crypto;
        }

        @Override
        public boolean read(InputStream inputStream, int n) throws IOException {
            inputStream = this.crypto.decrypt(inputStream);
            int n2 = this.size + n;
            if (n2 > 475000) {
                return false;
            }
            this.size = n2;
            byte[] arrby = new byte[n];
            inputStream.read(arrby, 0, n);
            this.writer.emitPayloadObject(new String(arrby, UTF_8));
            ++this.payloadCount;
            return true;
        }
    }

    static class SegmentDispatcherHandler
    extends Handler {
        private final SegmentIntegration segmentIntegration;

        SegmentDispatcherHandler(Looper looper, SegmentIntegration segmentIntegration) {
            super(looper);
            this.segmentIntegration = segmentIntegration;
        }

        public void handleMessage(Message object) {
            switch (object.what) {
                default: {
                    throw new AssertionError((Object)("Unknown dispatcher message: " + object.what));
                }
                case 0: {
                    object = (BasePayload)object.obj;
                    this.segmentIntegration.performEnqueue((BasePayload)object);
                    return;
                }
                case 1: 
            }
            this.segmentIntegration.submitFlush();
        }
    }

}

