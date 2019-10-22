/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.cache;

import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.Flushable;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import okhttp3.internal.Util;
import okhttp3.internal.cache.FaultHidingSink;
import okhttp3.internal.io.FileSystem;
import okhttp3.internal.platform.Platform;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.Okio;
import okio.Sink;
import okio.Source;

public final class DiskLruCache
implements Closeable,
Flushable {
    static final /* synthetic */ boolean $assertionsDisabled;
    static final Pattern LEGAL_KEY_PATTERN;
    private final int appVersion;
    private final Runnable cleanupRunnable;
    boolean closed;
    final File directory;
    private final Executor executor;
    final FileSystem fileSystem;
    boolean hasJournalErrors;
    boolean initialized;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    BufferedSink journalWriter;
    final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, 0.75f, true);
    private long maxSize;
    boolean mostRecentRebuildFailed;
    boolean mostRecentTrimFailed;
    private long nextSequenceNumber = 0L;
    int redundantOpCount;
    private long size = 0L;
    final int valueCount;

    /*
     * Enabled aggressive block sorting
     */
    static {
        boolean bl = !DiskLruCache.class.desiredAssertionStatus();
        $assertionsDisabled = bl;
        LEGAL_KEY_PATTERN = Pattern.compile("[a-z0-9_-]{1,120}");
    }

    DiskLruCache(FileSystem fileSystem, File file, int n, int n2, long l, Executor executor) {
        this.cleanupRunnable = new Runnable(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public void run() {
                boolean bl = true;
                DiskLruCache diskLruCache = DiskLruCache.this;
                synchronized (diskLruCache) {
                    if (DiskLruCache.this.initialized) {
                        bl = false;
                    }
                    if (bl | DiskLruCache.this.closed) {
                        return;
                    }
                    try {
                        DiskLruCache.this.trimToSize();
                    }
                    catch (IOException iOException) {
                        DiskLruCache.this.mostRecentTrimFailed = true;
                    }
                    try {
                        if (DiskLruCache.this.journalRebuildRequired()) {
                            DiskLruCache.this.rebuildJournal();
                            DiskLruCache.this.redundantOpCount = 0;
                        }
                    }
                    catch (IOException iOException) {
                        DiskLruCache.this.mostRecentRebuildFailed = true;
                        DiskLruCache.this.journalWriter = Okio.buffer(Okio.blackhole());
                    }
                    return;
                }
            }
        };
        this.fileSystem = fileSystem;
        this.directory = file;
        this.appVersion = n;
        this.journalFile = new File(file, "journal");
        this.journalFileTmp = new File(file, "journal.tmp");
        this.journalFileBackup = new File(file, "journal.bkp");
        this.valueCount = n2;
        this.maxSize = l;
        this.executor = executor;
    }

    private void checkNotClosed() {
        synchronized (this) {
            if (this.isClosed()) {
                throw new IllegalStateException("cache is closed");
            }
            return;
        }
    }

    public static DiskLruCache create(FileSystem fileSystem, File file, int n, int n2, long l) {
        if (l <= 0L) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        if (n2 <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        }
        return new DiskLruCache(fileSystem, file, n, n2, l, new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>(), Util.threadFactory("OkHttp DiskLruCache", true)));
    }

    private BufferedSink newJournalWriter() throws FileNotFoundException {
        return Okio.buffer(new FaultHidingSink(this.fileSystem.appendingSink(this.journalFile)){
            static final /* synthetic */ boolean $assertionsDisabled;

            /*
             * Enabled aggressive block sorting
             */
            static {
                boolean bl = !DiskLruCache.class.desiredAssertionStatus();
                $assertionsDisabled = bl;
            }

            @Override
            protected void onException(IOException iOException) {
                if (!$assertionsDisabled && !Thread.holdsLock(DiskLruCache.this)) {
                    throw new AssertionError();
                }
                DiskLruCache.this.hasJournalErrors = true;
            }
        });
    }

    private void processJournal() throws IOException {
        this.fileSystem.delete(this.journalFileTmp);
        Iterator<Entry> iterator = this.lruEntries.values().iterator();
        while (iterator.hasNext()) {
            int n;
            Entry entry = iterator.next();
            if (entry.currentEditor == null) {
                for (n = 0; n < this.valueCount; ++n) {
                    this.size += entry.lengths[n];
                }
                continue;
            }
            entry.currentEditor = null;
            for (n = 0; n < this.valueCount; ++n) {
                this.fileSystem.delete(entry.cleanFiles[n]);
                this.fileSystem.delete(entry.dirtyFiles[n]);
            }
            iterator.remove();
        }
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private void readJournal() throws IOException {
        block9: {
            var2_1 = Okio.buffer(this.fileSystem.source(this.journalFile));
            var3_2 = var2_1.readUtf8LineStrict();
            var4_5 = var2_1.readUtf8LineStrict();
            var5_6 = var2_1.readUtf8LineStrict();
            var6_7 = var2_1.readUtf8LineStrict();
            var7_8 = var2_1.readUtf8LineStrict();
            if ("libcore.io.DiskLruCache".equals(var3_2) == false) throw new IOException("unexpected journal header: [" + var3_2 + ", " + var4_5 + ", " + var6_7 + ", " + var7_8 + "]");
            if ("1".equals(var4_5) == false) throw new IOException("unexpected journal header: [" + var3_2 + ", " + var4_5 + ", " + var6_7 + ", " + var7_8 + "]");
            if (Integer.toString(this.appVersion).equals(var5_6) == false) throw new IOException("unexpected journal header: [" + var3_2 + ", " + var4_5 + ", " + var6_7 + ", " + var7_8 + "]");
            if (Integer.toString(this.valueCount).equals(var6_7) == false) throw new IOException("unexpected journal header: [" + var3_2 + ", " + var4_5 + ", " + var6_7 + ", " + var7_8 + "]");
            if (!"".equals(var7_8)) {
                throw new IOException("unexpected journal header: [" + var3_2 + ", " + var4_5 + ", " + var6_7 + ", " + var7_8 + "]");
            }
            break block9;
            finally {
                Util.closeQuietly(var2_1);
            }
        }
        var1_9 = 0;
        try {
            do {
                this.readJournalLine(var2_1.readUtf8LineStrict());
                ++var1_9;
            } while (true);
        }
        catch (EOFException var3_4) {
            this.redundantOpCount = var1_9 - this.lruEntries.size();
            if (var2_1.exhausted()) ** GOTO lbl-1000
            this.rebuildJournal();
            return;
lbl-1000:
            // 1 sources
            {
                this.journalWriter = this.newJournalWriter();
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readJournalLine(String arrstring) throws IOException {
        String string2;
        Object object;
        int n = arrstring.indexOf(32);
        if (n == -1) {
            throw new IOException("unexpected journal line: " + (String)arrstring);
        }
        int n2 = n + 1;
        int n3 = arrstring.indexOf(32, n2);
        if (n3 == -1) {
            object = arrstring.substring(n2);
            string2 = object;
            if (n == "REMOVE".length()) {
                string2 = object;
                if (arrstring.startsWith("REMOVE")) {
                    this.lruEntries.remove(object);
                    return;
                }
            }
        } else {
            string2 = arrstring.substring(n2, n3);
        }
        Entry entry = this.lruEntries.get(string2);
        object = entry;
        if (entry == null) {
            object = new Entry(string2);
            this.lruEntries.put(string2, (Entry)object);
        }
        if (n3 != -1 && n == "CLEAN".length() && arrstring.startsWith("CLEAN")) {
            arrstring = arrstring.substring(n3 + 1).split(" ");
            ((Entry)object).readable = true;
            ((Entry)object).currentEditor = null;
            ((Entry)object).setLengths(arrstring);
            return;
        }
        if (n3 == -1 && n == "DIRTY".length() && arrstring.startsWith("DIRTY")) {
            ((Entry)object).currentEditor = new Editor((Entry)object);
            return;
        }
        if (n3 == -1 && n == "READ".length() && arrstring.startsWith("READ")) return;
        {
            throw new IOException("unexpected journal line: " + (String)arrstring);
        }
    }

    private void validateKey(String string2) {
        if (!LEGAL_KEY_PATTERN.matcher(string2).matches()) {
            throw new IllegalArgumentException("keys must match regex [a-z0-9_-]{1,120}: \"" + string2 + "\"");
        }
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    @Override
    public void close() throws IOException {
        // MONITORENTER : this
        if (!this.initialized || this.closed) {
            this.closed = true;
            // MONITOREXIT : this
            return;
        }
        Entry[] arrentry = this.lruEntries.values().toArray(new Entry[this.lruEntries.size()]);
        int n = arrentry.length;
        int n2 = 0;
        do {
            if (n2 >= n) {
                this.trimToSize();
                this.journalWriter.close();
                this.journalWriter = null;
                this.closed = true;
                return;
            }
            Entry entry = arrentry[n2];
            if (entry.currentEditor != null) {
                entry.currentEditor.abort();
            }
            ++n2;
        } while (true);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    void completeEdit(Editor var1_1, boolean var2_2) throws IOException {
        block17: {
            // MONITORENTER : this
            var8_3 = var1_1.entry;
            if (var8_3.currentEditor != var1_1) {
                throw new IllegalStateException();
            }
            if (var2_2 && !var8_3.readable) {
                for (var3_4 = 0; var3_4 < this.valueCount; ++var3_4) {
                    if (!var1_1.written[var3_4]) {
                        var1_1.abort();
                        throw new IllegalStateException("Newly created entry didn't create value for index " + var3_4);
                    }
                    if (this.fileSystem.exists(var8_3.dirtyFiles[var3_4])) continue;
                    var1_1.abort();
                    return;
                }
            }
            var3_4 = 0;
            break block17;
            do {
                // MONITOREXIT : this
                return;
                break;
            } while (true);
        }
        do {
            if (var3_4 < this.valueCount) {
                var1_1 = var8_3.dirtyFiles[var3_4];
                if (var2_2) {
                    if (this.fileSystem.exists((File)var1_1)) {
                        var9_7 = var8_3.cleanFiles[var3_4];
                        this.fileSystem.rename((File)var1_1, var9_7);
                        var4_5 = var8_3.lengths[var3_4];
                        var8_3.lengths[var3_4] = var6_6 = this.fileSystem.size(var9_7);
                        this.size = this.size - var4_5 + var6_6;
                    }
                } else {
                    this.fileSystem.delete((File)var1_1);
                }
            } else {
                ++this.redundantOpCount;
                var8_3.currentEditor = null;
                if (var8_3.readable | var2_2) {
                    var8_3.readable = true;
                    this.journalWriter.writeUtf8("CLEAN").writeByte(32);
                    this.journalWriter.writeUtf8(var8_3.key);
                    var8_3.writeLengths(this.journalWriter);
                    this.journalWriter.writeByte(10);
                    if (var2_2) {
                        var4_5 = this.nextSequenceNumber;
                        this.nextSequenceNumber = 1L + var4_5;
                        var8_3.sequenceNumber = var4_5;
                    }
                } else {
                    this.lruEntries.remove(var8_3.key);
                    this.journalWriter.writeUtf8("REMOVE").writeByte(32);
                    this.journalWriter.writeUtf8(var8_3.key);
                    this.journalWriter.writeByte(10);
                }
                this.journalWriter.flush();
                if (this.size <= this.maxSize && !this.journalRebuildRequired()) ** continue;
                this.executor.execute(this.cleanupRunnable);
                return;
            }
            ++var3_4;
        } while (true);
    }

    public void delete() throws IOException {
        this.close();
        this.fileSystem.deleteContents(this.directory);
    }

    public Editor edit(String string2) throws IOException {
        return this.edit(string2, -1L);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    Editor edit(String object, long l) throws IOException {
        Entry entry = null;
        synchronized (this) {
            Object object2;
            this.initialize();
            this.checkNotClosed();
            this.validateKey((String)object);
            Entry entry2 = this.lruEntries.get(object);
            if (l != -1L) {
                object2 = entry;
                if (entry2 == null) return object2;
                long l2 = entry2.sequenceNumber;
                if (l2 != l) {
                    return entry;
                }
            }
            if (entry2 != null) {
                object2 = entry;
                if (entry2.currentEditor != null) return object2;
            }
            if (this.mostRecentTrimFailed || this.mostRecentRebuildFailed) {
                this.executor.execute(this.cleanupRunnable);
                return entry;
            }
            this.journalWriter.writeUtf8("DIRTY").writeByte(32).writeUtf8((String)object).writeByte(10);
            this.journalWriter.flush();
            object2 = entry;
            if (this.hasJournalErrors) return object2;
            object2 = entry2;
            if (entry2 == null) {
                object2 = new Entry((String)object);
                this.lruEntries.put((String)object, (Entry)object2);
            }
            ((Entry)object2).currentEditor = object = new Editor((Entry)object2);
            return object;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public void flush() throws IOException {
        synchronized (this) {
            block6: {
                boolean bl = this.initialized;
                if (bl) break block6;
                do {
                    return;
                    break;
                } while (true);
            }
            this.checkNotClosed();
            this.trimToSize();
            this.journalWriter.flush();
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Snapshot get(String object) throws IOException {
        synchronized (this) {
            Object object2;
            block8: {
                block7: {
                    this.initialize();
                    this.checkNotClosed();
                    this.validateKey((String)object);
                    object2 = this.lruEntries.get(object);
                    if (object2 == null) return null;
                    boolean bl = ((Entry)object2).readable;
                    if (bl) break block7;
                    return null;
                }
                object2 = ((Entry)object2).snapshot();
                if (object2 != null) break block8;
                return null;
            }
            ++this.redundantOpCount;
            this.journalWriter.writeUtf8("READ").writeByte(32).writeUtf8((String)object).writeByte(10);
            object = object2;
            if (!this.journalRebuildRequired()) return object;
            this.executor.execute(this.cleanupRunnable);
            return object2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void initialize() throws IOException {
        synchronized (this) {
            if (!$assertionsDisabled && !Thread.holdsLock(this)) {
                throw new AssertionError();
            }
            boolean bl = this.initialized;
            if (!bl) {
                if (this.fileSystem.exists(this.journalFileBackup)) {
                    if (this.fileSystem.exists(this.journalFile)) {
                        this.fileSystem.delete(this.journalFileBackup);
                    } else {
                        this.fileSystem.rename(this.journalFileBackup, this.journalFile);
                    }
                }
                if (bl = this.fileSystem.exists(this.journalFile)) {
                    try {
                        this.readJournal();
                        this.processJournal();
                        this.initialized = true;
                    }
                    catch (IOException iOException) {
                        Platform.get().log(5, "DiskLruCache " + this.directory + " is corrupt: " + iOException.getMessage() + ", removing", iOException);
                        try {
                            this.delete();
                        }
                        finally {
                            this.closed = false;
                        }
                    }
                } else {
                    this.rebuildJournal();
                    this.initialized = true;
                }
            }
            return;
        }
    }

    public boolean isClosed() {
        synchronized (this) {
            boolean bl = this.closed;
            return bl;
        }
    }

    boolean journalRebuildRequired() {
        return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void rebuildJournal() throws IOException {
        synchronized (this) {
            if (this.journalWriter != null) {
                this.journalWriter.close();
            }
            BufferedSink bufferedSink = Okio.buffer(this.fileSystem.sink(this.journalFileTmp));
            try {
                bufferedSink.writeUtf8("libcore.io.DiskLruCache").writeByte(10);
                bufferedSink.writeUtf8("1").writeByte(10);
                bufferedSink.writeDecimalLong(this.appVersion).writeByte(10);
                bufferedSink.writeDecimalLong(this.valueCount).writeByte(10);
                bufferedSink.writeByte(10);
                for (Entry entry : this.lruEntries.values()) {
                    if (entry.currentEditor != null) {
                        bufferedSink.writeUtf8("DIRTY").writeByte(32);
                        bufferedSink.writeUtf8(entry.key);
                        bufferedSink.writeByte(10);
                        continue;
                    }
                    bufferedSink.writeUtf8("CLEAN").writeByte(32);
                    bufferedSink.writeUtf8(entry.key);
                    entry.writeLengths(bufferedSink);
                    bufferedSink.writeByte(10);
                }
            }
            finally {
                bufferedSink.close();
            }
            if (this.fileSystem.exists(this.journalFile)) {
                this.fileSystem.rename(this.journalFile, this.journalFileBackup);
            }
            this.fileSystem.rename(this.journalFileTmp, this.journalFile);
            this.fileSystem.delete(this.journalFileBackup);
            this.journalWriter = this.newJournalWriter();
            this.hasJournalErrors = false;
            this.mostRecentRebuildFailed = false;
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean remove(String object) throws IOException {
        boolean bl = false;
        synchronized (this) {
            boolean bl2;
            block5: {
                this.initialize();
                this.checkNotClosed();
                this.validateKey((String)object);
                object = this.lruEntries.get(object);
                if (object != null) break block5;
                return bl;
            }
            bl = bl2 = this.removeEntry((Entry)object);
            if (!bl2) return bl;
            bl = bl2;
            if (this.size > this.maxSize) return bl;
            this.mostRecentTrimFailed = false;
            return bl2;
        }
    }

    boolean removeEntry(Entry entry) throws IOException {
        if (entry.currentEditor != null) {
            entry.currentEditor.detach();
        }
        for (int i = 0; i < this.valueCount; ++i) {
            this.fileSystem.delete(entry.cleanFiles[i]);
            this.size -= entry.lengths[i];
            entry.lengths[i] = 0L;
        }
        ++this.redundantOpCount;
        this.journalWriter.writeUtf8("REMOVE").writeByte(32).writeUtf8(entry.key).writeByte(10);
        this.lruEntries.remove(entry.key);
        if (this.journalRebuildRequired()) {
            this.executor.execute(this.cleanupRunnable);
        }
        return true;
    }

    void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            this.removeEntry(this.lruEntries.values().iterator().next());
        }
        this.mostRecentTrimFailed = false;
    }

    public final class Editor {
        private boolean done;
        final Entry entry;
        final boolean[] written;

        /*
         * Enabled aggressive block sorting
         */
        Editor(Entry entry) {
            this.entry = entry;
            this$0 = entry.readable ? null : new boolean[this$0.valueCount];
            this.written = this$0;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void abort() throws IOException {
            DiskLruCache diskLruCache = this$0;
            synchronized (diskLruCache) {
                if (this.done) {
                    throw new IllegalStateException();
                }
                if (this.entry.currentEditor == this) {
                    this$0.completeEdit(this, false);
                }
                this.done = true;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public void commit() throws IOException {
            DiskLruCache diskLruCache = this$0;
            synchronized (diskLruCache) {
                if (this.done) {
                    throw new IllegalStateException();
                }
                if (this.entry.currentEditor == this) {
                    this$0.completeEdit(this, true);
                }
                this.done = true;
                return;
            }
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         * Lifted jumps to return sites
         */
        void detach() {
            if (this.entry.currentEditor != this) return;
            int n = 0;
            do {
                if (n >= this$0.valueCount) {
                    this.entry.currentEditor = null;
                    return;
                }
                try {
                    this$0.fileSystem.delete(this.entry.dirtyFiles[n]);
                }
                catch (IOException iOException) {}
                ++n;
            } while (true);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public Sink newSink(int n) {
            DiskLruCache diskLruCache = this$0;
            synchronized (diskLruCache) {
                if (this.done) {
                    throw new IllegalStateException();
                }
                if (this.entry.currentEditor != this) {
                    return Okio.blackhole();
                }
                if (!this.entry.readable) {
                    this.written[n] = true;
                }
                Object object = this.entry.dirtyFiles[n];
                try {
                    object = this$0.fileSystem.sink((File)object);
                    return new FaultHidingSink((Sink)object){

                        /*
                         * Enabled aggressive block sorting
                         * Enabled unnecessary exception pruning
                         * Enabled aggressive exception aggregation
                         */
                        @Override
                        protected void onException(IOException object) {
                            object = this$0;
                            synchronized (object) {
                                Editor.this.detach();
                                return;
                            }
                        }
                    };
                }
                catch (FileNotFoundException fileNotFoundException) {
                    return Okio.blackhole();
                }
            }
        }

    }

    private final class Entry {
        final File[] cleanFiles;
        Editor currentEditor;
        final File[] dirtyFiles;
        final String key;
        final long[] lengths;
        boolean readable;
        long sequenceNumber;

        Entry(String charSequence) {
            this.key = charSequence;
            this.lengths = new long[DiskLruCache.this.valueCount];
            this.cleanFiles = new File[DiskLruCache.this.valueCount];
            this.dirtyFiles = new File[DiskLruCache.this.valueCount];
            charSequence = new StringBuilder((String)charSequence).append('.');
            int n = ((StringBuilder)charSequence).length();
            for (int i = 0; i < DiskLruCache.this.valueCount; ++i) {
                ((StringBuilder)charSequence).append(i);
                this.cleanFiles[i] = new File(DiskLruCache.this.directory, ((StringBuilder)charSequence).toString());
                ((StringBuilder)charSequence).append(".tmp");
                this.dirtyFiles[i] = new File(DiskLruCache.this.directory, ((StringBuilder)charSequence).toString());
                ((StringBuilder)charSequence).setLength(n);
            }
        }

        private IOException invalidLengths(String[] arrstring) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(arrstring));
        }

        void setLengths(String[] arrstring) throws IOException {
            if (arrstring.length != DiskLruCache.this.valueCount) {
                throw this.invalidLengths(arrstring);
            }
            int n = 0;
            do {
                try {
                    if (n >= arrstring.length) break;
                    this.lengths[n] = Long.parseLong(arrstring[n]);
                    ++n;
                }
                catch (NumberFormatException numberFormatException) {
                    throw this.invalidLengths(arrstring);
                }
            } while (true);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        Snapshot snapshot() {
            if (!Thread.holdsLock(DiskLruCache.this)) {
                throw new AssertionError();
            }
            Source[] arrsource = new Source[DiskLruCache.this.valueCount];
            long[] arrl = (long[])this.lengths.clone();
            int n = 0;
            do {
                if (n >= DiskLruCache.this.valueCount) break;
                arrsource[n] = DiskLruCache.this.fileSystem.source(this.cleanFiles[n]);
                ++n;
                continue;
                break;
            } while (true);
            try {
                return new Snapshot(this.key, this.sequenceNumber, arrsource, arrl);
            }
            catch (FileNotFoundException fileNotFoundException) {
                for (n = 0; n < DiskLruCache.this.valueCount && arrsource[n] != null; ++n) {
                    Util.closeQuietly(arrsource[n]);
                }
                try {
                    DiskLruCache.this.removeEntry(this);
                    do {
                        return null;
                        break;
                    } while (true);
                }
                catch (IOException iOException) {
                    return null;
                }
            }
        }

        void writeLengths(BufferedSink bufferedSink) throws IOException {
            for (long l : this.lengths) {
                bufferedSink.writeByte(32).writeDecimalLong(l);
            }
        }
    }

    public final class Snapshot
    implements Closeable {
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;
        private final Source[] sources;

        Snapshot(String string2, long l, Source[] arrsource, long[] arrl) {
            this.key = string2;
            this.sequenceNumber = l;
            this.sources = arrsource;
            this.lengths = arrl;
        }

        @Override
        public void close() {
            Source[] arrsource = this.sources;
            int n = arrsource.length;
            for (int i = 0; i < n; ++i) {
                Util.closeQuietly(arrsource[i]);
            }
        }

        public Source getSource(int n) {
            return this.sources[n];
        }
    }

}

