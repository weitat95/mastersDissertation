/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.disklrucache;

import com.bumptech.glide.disklrucache.StrictLineReader;
import com.bumptech.glide.disklrucache.Util;
import java.io.BufferedWriter;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public final class DiskLruCache
implements Closeable {
    private final int appVersion;
    private final Callable<Void> cleanupCallable;
    private final File directory;
    final ThreadPoolExecutor executorService;
    private final File journalFile;
    private final File journalFileBackup;
    private final File journalFileTmp;
    private Writer journalWriter;
    private final LinkedHashMap<String, Entry> lruEntries = new LinkedHashMap(0, 0.75f, true);
    private long maxSize;
    private long nextSequenceNumber = 0L;
    private int redundantOpCount;
    private long size = 0L;
    private final int valueCount;

    private DiskLruCache(File file, int n, int n2, long l) {
        this.executorService = new ThreadPoolExecutor(0, 1, 60L, TimeUnit.SECONDS, new LinkedBlockingQueue<Runnable>());
        this.cleanupCallable = new Callable<Void>(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            @Override
            public Void call() throws Exception {
                DiskLruCache diskLruCache = DiskLruCache.this;
                synchronized (diskLruCache) {
                    if (DiskLruCache.this.journalWriter == null) {
                        return null;
                    }
                    DiskLruCache.this.trimToSize();
                    if (DiskLruCache.this.journalRebuildRequired()) {
                        DiskLruCache.this.rebuildJournal();
                        DiskLruCache.this.redundantOpCount = 0;
                    }
                    return null;
                }
            }
        };
        this.directory = file;
        this.appVersion = n;
        this.journalFile = new File(file, "journal");
        this.journalFileTmp = new File(file, "journal.tmp");
        this.journalFileBackup = new File(file, "journal.bkp");
        this.valueCount = n2;
        this.maxSize = l;
    }

    private void checkNotClosed() {
        if (this.journalWriter == null) {
            throw new IllegalStateException("cache is closed");
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Converted monitor instructions to comments
     * Lifted jumps to return sites
     */
    private void completeEdit(Editor var1_1, boolean var2_2) throws IOException {
        block17: {
            // MONITORENTER : this
            var8_3 = Editor.access$1400((Editor)var1_1);
            if (Entry.access$700(var8_3) != var1_1) {
                throw new IllegalStateException();
            }
            if (var2_2 && !Entry.access$600(var8_3)) {
                for (var3_4 = 0; var3_4 < this.valueCount; ++var3_4) {
                    if (!Editor.access$1500((Editor)var1_1)[var3_4]) {
                        var1_1.abort();
                        throw new IllegalStateException("Newly created entry didn't create value for index " + var3_4);
                    }
                    if (var8_3.getDirtyFile(var3_4).exists()) continue;
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
                var1_1 = var8_3.getDirtyFile(var3_4);
                if (var2_2) {
                    if (var1_1.exists()) {
                        var9_7 = var8_3.getCleanFile(var3_4);
                        var1_1.renameTo(var9_7);
                        var4_5 = Entry.access$1000(var8_3)[var3_4];
                        Entry.access$1000((Entry)var8_3)[var3_4] = var6_6 = var9_7.length();
                        this.size = this.size - var4_5 + var6_6;
                    }
                } else {
                    DiskLruCache.deleteIfExists((File)var1_1);
                }
            } else {
                ++this.redundantOpCount;
                Entry.access$702(var8_3, null);
                if (Entry.access$600(var8_3) | var2_2) {
                    Entry.access$602(var8_3, true);
                    this.journalWriter.append("CLEAN");
                    this.journalWriter.append(' ');
                    this.journalWriter.append(Entry.access$1100(var8_3));
                    this.journalWriter.append(var8_3.getLengths());
                    this.journalWriter.append('\n');
                    if (var2_2) {
                        var4_5 = this.nextSequenceNumber;
                        this.nextSequenceNumber = 1L + var4_5;
                        Entry.access$1202(var8_3, var4_5);
                    }
                } else {
                    this.lruEntries.remove(Entry.access$1100(var8_3));
                    this.journalWriter.append("REMOVE");
                    this.journalWriter.append(' ');
                    this.journalWriter.append(Entry.access$1100(var8_3));
                    this.journalWriter.append('\n');
                }
                this.journalWriter.flush();
                if (this.size <= this.maxSize && !this.journalRebuildRequired()) ** continue;
                this.executorService.submit(this.cleanupCallable);
                return;
            }
            ++var3_4;
        } while (true);
    }

    private static void deleteIfExists(File file) throws IOException {
        if (file.exists() && !file.delete()) {
            throw new IOException();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Editor edit(String string2, long l) throws IOException {
        Object object = null;
        synchronized (this) {
            Object object2;
            this.checkNotClosed();
            Entry entry = this.lruEntries.get(string2);
            if (l != -1L) {
                object2 = object;
                if (entry == null) return object2;
                long l2 = entry.sequenceNumber;
                if (l2 != l) {
                    return object;
                }
            }
            if (entry == null) {
                object2 = new Entry(string2);
                this.lruEntries.put(string2, (Entry)object2);
            } else {
                Editor editor = entry.currentEditor;
                object2 = entry;
                if (editor != null) {
                    return object;
                }
            }
            object = new Editor((Entry)object2);
            ((Entry)object2).currentEditor = (Editor)object;
            this.journalWriter.append("DIRTY");
            this.journalWriter.append(' ');
            this.journalWriter.append(string2);
            this.journalWriter.append('\n');
            this.journalWriter.flush();
            return object;
        }
    }

    private boolean journalRebuildRequired() {
        return this.redundantOpCount >= 2000 && this.redundantOpCount >= this.lruEntries.size();
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static DiskLruCache open(File object, int n, int n2, long l) throws IOException {
        if (l <= 0L) {
            throw new IllegalArgumentException("maxSize <= 0");
        }
        if (n2 <= 0) {
            throw new IllegalArgumentException("valueCount <= 0");
        }
        Object object2 = new File((File)object, "journal.bkp");
        if (((File)object2).exists()) {
            File file = new File((File)object, "journal");
            if (file.exists()) {
                ((File)object2).delete();
            } else {
                DiskLruCache.renameTo((File)object2, file, false);
            }
        }
        object2 = new DiskLruCache((File)object, n, n2, l);
        if (((DiskLruCache)object2).journalFile.exists()) {
            try {
                super.readJournal();
                super.processJournal();
                return object2;
            }
            catch (IOException iOException) {
                System.out.println("DiskLruCache " + object + " is corrupt: " + iOException.getMessage() + ", removing");
                ((DiskLruCache)object2).delete();
            }
        }
        ((File)object).mkdirs();
        object = new DiskLruCache((File)object, n, n2, l);
        super.rebuildJournal();
        return object;
    }

    private void processJournal() throws IOException {
        DiskLruCache.deleteIfExists(this.journalFileTmp);
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
                DiskLruCache.deleteIfExists(entry.getCleanFile(n));
                DiskLruCache.deleteIfExists(entry.getDirtyFile(n));
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
            var2_1 = new StrictLineReader(new FileInputStream(this.journalFile), Util.US_ASCII);
            var3_2 = var2_1.readLine();
            var4_5 = var2_1.readLine();
            var5_6 = var2_1.readLine();
            var6_7 = var2_1.readLine();
            var7_8 = var2_1.readLine();
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
                this.readJournalLine(var2_1.readLine());
                ++var1_9;
            } while (true);
        }
        catch (EOFException var3_4) {
            this.redundantOpCount = var1_9 - this.lruEntries.size();
            if (!var2_1.hasUnterminatedLine()) ** GOTO lbl-1000
            this.rebuildJournal();
            return;
lbl-1000:
            // 1 sources
            {
                this.journalWriter = new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(this.journalFile, true), Util.US_ASCII));
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

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void rebuildJournal() throws IOException {
        synchronized (this) {
            if (this.journalWriter != null) {
                this.journalWriter.close();
            }
            BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(this.journalFileTmp), Util.US_ASCII));
            try {
                bufferedWriter.write("libcore.io.DiskLruCache");
                bufferedWriter.write("\n");
                bufferedWriter.write("1");
                bufferedWriter.write("\n");
                bufferedWriter.write(Integer.toString(this.appVersion));
                bufferedWriter.write("\n");
                bufferedWriter.write(Integer.toString(this.valueCount));
                bufferedWriter.write("\n");
                bufferedWriter.write("\n");
                for (Entry entry : this.lruEntries.values()) {
                    if (entry.currentEditor != null) {
                        bufferedWriter.write("DIRTY " + entry.key + '\n');
                        continue;
                    }
                    bufferedWriter.write("CLEAN " + entry.key + entry.getLengths() + '\n');
                }
            }
            finally {
                ((Writer)bufferedWriter).close();
            }
            if (this.journalFile.exists()) {
                DiskLruCache.renameTo(this.journalFile, this.journalFileBackup, true);
            }
            DiskLruCache.renameTo(this.journalFileTmp, this.journalFile, false);
            this.journalFileBackup.delete();
            this.journalWriter = new BufferedWriter(new OutputStreamWriter((OutputStream)new FileOutputStream(this.journalFile, true), Util.US_ASCII));
            return;
        }
    }

    private static void renameTo(File file, File file2, boolean bl) throws IOException {
        if (bl) {
            DiskLruCache.deleteIfExists(file2);
        }
        if (!file.renameTo(file2)) {
            throw new IOException();
        }
    }

    private void trimToSize() throws IOException {
        while (this.size > this.maxSize) {
            this.remove(this.lruEntries.entrySet().iterator().next().getKey());
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void close() throws IOException {
        synchronized (this) {
            Writer writer = this.journalWriter;
            if (writer != null) {
                for (Entry entry : new ArrayList<Entry>(this.lruEntries.values())) {
                    if (entry.currentEditor == null) continue;
                    entry.currentEditor.abort();
                }
                this.trimToSize();
                this.journalWriter.close();
                this.journalWriter = null;
            }
            return;
        }
    }

    public void delete() throws IOException {
        this.close();
        Util.deleteContents(this.directory);
    }

    public Editor edit(String string2) throws IOException {
        return this.edit(string2, -1L);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public Value get(String string2) throws IOException {
        Value value = null;
        synchronized (this) {
            Entry entry;
            block10: {
                this.checkNotClosed();
                entry = this.lruEntries.get(string2);
                if (entry != null) break block10;
                return value;
            }
            Value value2 = value;
            if (!entry.readable) return value2;
            File[] arrfile = entry.cleanFiles;
            int n = arrfile.length;
            for (int i = 0; i < n; ++i) {
                value2 = value;
                if (!arrfile[i].exists()) return value2;
                continue;
            }
            try {
                ++this.redundantOpCount;
                this.journalWriter.append("READ");
                this.journalWriter.append(' ');
                this.journalWriter.append(string2);
                this.journalWriter.append('\n');
                if (this.journalRebuildRequired()) {
                    this.executorService.submit(this.cleanupCallable);
                }
                value2 = new Value(string2, entry.sequenceNumber, entry.cleanFiles, entry.lengths);
                return value2;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean remove(String string2) throws IOException {
        synchronized (this) {
            this.checkNotClosed();
            Entry entry = this.lruEntries.get(string2);
            if (entry == null) return false;
            Object object = entry.currentEditor;
            if (object != null) {
                return false;
            }
            for (int i = 0; i < this.valueCount; this.size -= entry.lengths[i], ++i) {
                object = entry.getCleanFile(i);
                if (((File)object).exists() && !((File)object).delete()) {
                    throw new IOException("failed to delete " + object);
                }
                Entry.access$1000((Entry)entry)[i] = 0L;
            }
            ++this.redundantOpCount;
            this.journalWriter.append("REMOVE");
            this.journalWriter.append(' ');
            this.journalWriter.append(string2);
            this.journalWriter.append('\n');
            this.lruEntries.remove(string2);
            if (!this.journalRebuildRequired()) return true;
            this.executorService.submit(this.cleanupCallable);
            return true;
        }
    }

    public final class Editor {
        private boolean committed;
        private final Entry entry;
        private final boolean[] written;

        /*
         * Enabled aggressive block sorting
         */
        private Editor(Entry entry) {
            this.entry = entry;
            this$0 = entry.readable ? null : new boolean[((DiskLruCache)this$0).valueCount];
            this.written = this$0;
        }

        static /* synthetic */ Entry access$1400(Editor editor) {
            return editor.entry;
        }

        static /* synthetic */ boolean[] access$1500(Editor editor) {
            return editor.written;
        }

        public void abort() throws IOException {
            this$0.completeEdit(this, false);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        public void abortUnlessCommitted() {
            if (this.committed) return;
            try {
                this.abort();
                return;
            }
            catch (IOException iOException) {
                return;
            }
        }

        public void commit() throws IOException {
            this$0.completeEdit(this, true);
            this.committed = true;
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        public File getFile(int n) throws IOException {
            DiskLruCache diskLruCache = this$0;
            synchronized (diskLruCache) {
                if (this.entry.currentEditor != this) {
                    throw new IllegalStateException();
                }
                if (!this.entry.readable) {
                    this.written[n] = true;
                }
                File file = this.entry.getDirtyFile(n);
                if (!this$0.directory.exists()) {
                    this$0.directory.mkdirs();
                }
                return file;
            }
        }
    }

    private final class Entry {
        File[] cleanFiles;
        private Editor currentEditor;
        File[] dirtyFiles;
        private final String key;
        private final long[] lengths;
        private boolean readable;
        private long sequenceNumber;

        private Entry(String charSequence) {
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

        static /* synthetic */ long access$1202(Entry entry, long l) {
            entry.sequenceNumber = l;
            return l;
        }

        private IOException invalidLengths(String[] arrstring) throws IOException {
            throw new IOException("unexpected journal line: " + Arrays.toString(arrstring));
        }

        private void setLengths(String[] arrstring) throws IOException {
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

        public File getCleanFile(int n) {
            return this.cleanFiles[n];
        }

        public File getDirtyFile(int n) {
            return this.dirtyFiles[n];
        }

        public String getLengths() throws IOException {
            StringBuilder stringBuilder = new StringBuilder();
            for (long l : this.lengths) {
                stringBuilder.append(' ').append(l);
            }
            return stringBuilder.toString();
        }
    }

    public final class Value {
        private final File[] files;
        private final String key;
        private final long[] lengths;
        private final long sequenceNumber;

        private Value(String string2, long l, File[] arrfile, long[] arrl) {
            this.key = string2;
            this.sequenceNumber = l;
            this.files = arrfile;
            this.lengths = arrl;
        }

        public File getFile(int n) {
            return this.files[n];
        }
    }

}

