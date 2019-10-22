/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics;

import com.segment.analytics.QueueFile;
import java.io.ByteArrayInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.util.LinkedList;

abstract class PayloadQueue
implements Closeable {
    PayloadQueue() {
    }

    abstract void add(byte[] var1) throws IOException;

    abstract void forEach(ElementVisitor var1) throws IOException;

    abstract void remove(int var1) throws IOException;

    abstract int size();

    static interface ElementVisitor {
        public boolean read(InputStream var1, int var2) throws IOException;
    }

    static class MemoryQueue
    extends PayloadQueue {
        final LinkedList<byte[]> queue = new LinkedList();

        MemoryQueue() {
        }

        @Override
        void add(byte[] arrby) throws IOException {
            this.queue.add(arrby);
        }

        @Override
        public void close() throws IOException {
        }

        @Override
        void forEach(ElementVisitor elementVisitor) throws IOException {
            int n = 0;
            byte[] arrby;
            while (n < this.queue.size() && elementVisitor.read(new ByteArrayInputStream(arrby = this.queue.get(n)), arrby.length)) {
                ++n;
            }
            return;
        }

        @Override
        void remove(int n) throws IOException {
            for (int i = 0; i < n; ++i) {
                this.queue.remove();
            }
        }

        @Override
        int size() {
            return this.queue.size();
        }
    }

    static class PersistentQueue
    extends PayloadQueue {
        final QueueFile queueFile;

        PersistentQueue(QueueFile queueFile) {
            this.queueFile = queueFile;
        }

        @Override
        void add(byte[] arrby) throws IOException {
            this.queueFile.add(arrby);
        }

        @Override
        public void close() throws IOException {
            this.queueFile.close();
        }

        @Override
        void forEach(ElementVisitor elementVisitor) throws IOException {
            this.queueFile.forEach(elementVisitor);
        }

        @Override
        void remove(int n) throws IOException {
            try {
                this.queueFile.remove(n);
                return;
            }
            catch (ArrayIndexOutOfBoundsException arrayIndexOutOfBoundsException) {
                throw new IOException(arrayIndexOutOfBoundsException);
            }
        }

        @Override
        int size() {
            return this.queueFile.size();
        }
    }

}

