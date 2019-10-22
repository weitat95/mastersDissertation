/*
 * Decompiled with CFR 0.147.
 */
package com.segment.analytics;

import com.segment.analytics.PayloadQueue;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.WritableByteChannel;
import java.util.NoSuchElementException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class QueueFile
implements Closeable {
    private static final Logger LOGGER = Logger.getLogger(QueueFile.class.getName());
    private static final byte[] ZEROES = new byte[4096];
    private final byte[] buffer = new byte[16];
    private int elementCount;
    int fileLength;
    private Element first;
    private Element last;
    final RandomAccessFile raf;

    public QueueFile(File file) throws IOException {
        if (!file.exists()) {
            QueueFile.initialize(file);
        }
        this.raf = QueueFile.open(file);
        this.readHeader();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void expandIfNecessary(int n) throws IOException {
        int n2;
        int n3;
        int n4 = n + 4;
        n = this.remainingBytes();
        if (n >= n4) {
            return;
        }
        int n5 = this.fileLength;
        do {
            n3 = n + n5;
            n2 = n5 << 1;
            if (n2 < n5) {
                throw new EOFException("Cannot grow file beyond " + n5 + " bytes");
            }
            n5 = n2;
            n = n3;
        } while (n3 < n4);
        this.setLength(n2);
        n = this.wrapPosition(this.last.position + 4 + this.last.length);
        if (n <= this.first.position) {
            FileChannel fileChannel = this.raf.getChannel();
            fileChannel.position(this.fileLength);
            if (fileChannel.transferTo(16L, n -= 16, fileChannel) != (long)n) {
                throw new AssertionError((Object)"Copied insufficient number of bytes!");
            }
            this.ringErase(16, n);
        }
        if (this.last.position < this.first.position) {
            n = this.fileLength + this.last.position - 16;
            this.writeHeader(n2, this.elementCount, this.first.position, n);
            this.last = new Element(n, this.last.length);
        } else {
            this.writeHeader(n2, this.elementCount, this.first.position, this.last.position);
        }
        this.fileLength = n2;
    }

    private static void initialize(File file) throws IOException {
        File file2 = new File(file.getPath() + ".tmp");
        RandomAccessFile randomAccessFile = QueueFile.open(file2);
        try {
            randomAccessFile.setLength(4096L);
            randomAccessFile.seek(0L);
            byte[] arrby = new byte[16];
            QueueFile.writeInt(arrby, 0, 4096);
            randomAccessFile.write(arrby);
            if (!file2.renameTo(file)) {
                throw new IOException("Rename failed!");
            }
        }
        finally {
            randomAccessFile.close();
        }
    }

    private static RandomAccessFile open(File file) throws FileNotFoundException {
        return new RandomAccessFile(file, "rwd");
    }

    private Element readElement(int n) throws IOException {
        if (n == 0) {
            return Element.NULL;
        }
        this.ringRead(n, this.buffer, 0, 4);
        return new Element(n, QueueFile.readInt(this.buffer, 0));
    }

    private void readHeader() throws IOException {
        this.raf.seek(0L);
        this.raf.readFully(this.buffer);
        this.fileLength = QueueFile.readInt(this.buffer, 0);
        if ((long)this.fileLength > this.raf.length()) {
            throw new IOException("File is truncated. Expected length: " + this.fileLength + ", Actual length: " + this.raf.length());
        }
        if (this.fileLength <= 0) {
            throw new IOException("File is corrupt; length stored in header (" + this.fileLength + ") is invalid.");
        }
        this.elementCount = QueueFile.readInt(this.buffer, 4);
        int n = QueueFile.readInt(this.buffer, 8);
        int n2 = QueueFile.readInt(this.buffer, 12);
        this.first = this.readElement(n);
        this.last = this.readElement(n2);
    }

    private static int readInt(byte[] arrby, int n) {
        return ((arrby[n] & 0xFF) << 24) + ((arrby[n + 1] & 0xFF) << 16) + ((arrby[n + 2] & 0xFF) << 8) + (arrby[n + 3] & 0xFF);
    }

    private int remainingBytes() {
        return this.fileLength - this.usedBytes();
    }

    private void ringErase(int n, int n2) throws IOException {
        while (n2 > 0) {
            int n3 = Math.min(n2, ZEROES.length);
            this.ringWrite(n, ZEROES, 0, n3);
            n2 -= n3;
            n += n3;
        }
    }

    private void ringWrite(int n, byte[] arrby, int n2, int n3) throws IOException {
        if ((n = this.wrapPosition(n)) + n3 <= this.fileLength) {
            this.raf.seek(n);
            this.raf.write(arrby, n2, n3);
            return;
        }
        int n4 = this.fileLength - n;
        this.raf.seek(n);
        this.raf.write(arrby, n2, n4);
        this.raf.seek(16L);
        this.raf.write(arrby, n2 + n4, n3 - n4);
    }

    private void setLength(int n) throws IOException {
        this.raf.setLength(n);
        this.raf.getChannel().force(true);
    }

    private int usedBytes() {
        if (this.elementCount == 0) {
            return 16;
        }
        if (this.last.position >= this.first.position) {
            return this.last.position - this.first.position + 4 + this.last.length + 16;
        }
        return this.last.position + 4 + this.last.length + this.fileLength - this.first.position;
    }

    private void writeHeader(int n, int n2, int n3, int n4) throws IOException {
        QueueFile.writeInt(this.buffer, 0, n);
        QueueFile.writeInt(this.buffer, 4, n2);
        QueueFile.writeInt(this.buffer, 8, n3);
        QueueFile.writeInt(this.buffer, 12, n4);
        this.raf.seek(0L);
        this.raf.write(this.buffer);
    }

    private static void writeInt(byte[] arrby, int n, int n2) {
        arrby[n] = (byte)(n2 >> 24);
        arrby[n + 1] = (byte)(n2 >> 16);
        arrby[n + 2] = (byte)(n2 >> 8);
        arrby[n + 3] = (byte)n2;
    }

    public void add(byte[] arrby) throws IOException {
        this.add(arrby, 0, arrby.length);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void add(byte[] arrby, int n, int n2) throws IOException {
        synchronized (this) {
            if (arrby == null) {
                throw new NullPointerException("data == null");
            }
            if ((n | n2) < 0 || n2 > arrby.length - n) {
                throw new IndexOutOfBoundsException();
            }
            this.expandIfNecessary(n2);
            boolean bl = this.isEmpty();
            int n3 = bl ? 16 : this.wrapPosition(this.last.position + 4 + this.last.length);
            Element element = new Element(n3, n2);
            QueueFile.writeInt(this.buffer, 0, n2);
            this.ringWrite(element.position, this.buffer, 0, 4);
            this.ringWrite(element.position + 4, arrby, n, n2);
            n = bl ? element.position : this.first.position;
            this.writeHeader(this.fileLength, this.elementCount + 1, n, element.position);
            this.last = element;
            ++this.elementCount;
            if (bl) {
                this.first = this.last;
            }
            return;
        }
    }

    public void clear() throws IOException {
        synchronized (this) {
            this.writeHeader(4096, 0, 0, 0);
            this.raf.seek(16L);
            this.raf.write(ZEROES, 0, 4080);
            this.elementCount = 0;
            this.first = Element.NULL;
            this.last = Element.NULL;
            if (this.fileLength > 4096) {
                this.setLength(4096);
            }
            this.fileLength = 4096;
            return;
        }
    }

    @Override
    public void close() throws IOException {
        synchronized (this) {
            this.raf.close();
            return;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int forEach(PayloadQueue.ElementVisitor elementVisitor) throws IOException {
        synchronized (this) {
            int n = this.first.position;
            int n2 = 0;
            do {
                Element element;
                block9: {
                    if (n2 >= this.elementCount) break;
                    element = this.readElement(n);
                    boolean bl = elementVisitor.read(new ElementInputStream(element), element.length);
                    if (bl) break block9;
                    ++n2;
                    do {
                        return n2;
                        break;
                    } while (true);
                }
                n = this.wrapPosition(element.position + 4 + element.length);
                ++n2;
                continue;
                break;
            } while (true);
            try {
                n2 = this.elementCount;
                return n2;
            }
            catch (Throwable throwable) {
                throw throwable;
            }
            finally {
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean isEmpty() {
        synchronized (this) {
            int n = this.elementCount;
            if (n != 0) return false;
            return true;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public void remove(int n) throws IOException {
        synchronized (this) {
            if (this.isEmpty()) {
                throw new NoSuchElementException();
            }
            if (n < 0) {
                throw new IllegalArgumentException("Cannot remove negative (" + n + ") number of elements.");
            }
            if (n != 0) {
                if (n == this.elementCount) {
                    this.clear();
                } else {
                    if (n > this.elementCount) {
                        throw new IllegalArgumentException("Cannot remove more elements (" + n + ") than present in queue (" + this.elementCount + ").");
                    }
                    int n2 = this.first.position;
                    int n3 = 0;
                    int n4 = this.first.position;
                    int n5 = this.first.length;
                    for (int i = 0; i < n; n3 += n5 + 4, ++i) {
                        n4 = this.wrapPosition(n4 + 4 + n5);
                        this.ringRead(n4, this.buffer, 0, 4);
                        n5 = QueueFile.readInt(this.buffer, 0);
                    }
                    this.writeHeader(this.fileLength, this.elementCount - n, n4, this.last.position);
                    this.elementCount -= n;
                    this.first = new Element(n4, n5);
                    this.ringErase(n2, n3);
                }
            }
            return;
        }
    }

    void ringRead(int n, byte[] arrby, int n2, int n3) throws IOException {
        if ((n = this.wrapPosition(n)) + n3 <= this.fileLength) {
            this.raf.seek(n);
            this.raf.readFully(arrby, n2, n3);
            return;
        }
        int n4 = this.fileLength - n;
        this.raf.seek(n);
        this.raf.readFully(arrby, n2, n4);
        this.raf.seek(16L);
        this.raf.readFully(arrby, n2 + n4, n3 - n4);
    }

    public int size() {
        synchronized (this) {
            int n = this.elementCount;
            return n;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public String toString() {
        final StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(this.getClass().getSimpleName()).append('[');
        stringBuilder.append("fileLength=").append(this.fileLength);
        stringBuilder.append(", size=").append(this.elementCount);
        stringBuilder.append(", first=").append(this.first);
        stringBuilder.append(", last=").append(this.last);
        stringBuilder.append(", element lengths=[");
        try {
            this.forEach(new PayloadQueue.ElementVisitor(){
                boolean first = true;

                /*
                 * Enabled aggressive block sorting
                 */
                @Override
                public boolean read(InputStream inputStream, int n) throws IOException {
                    if (this.first) {
                        this.first = false;
                    } else {
                        stringBuilder.append(", ");
                    }
                    stringBuilder.append(n);
                    return true;
                }
            });
        }
        catch (IOException iOException) {
            LOGGER.log(Level.WARNING, "read error", iOException);
        }
        stringBuilder.append("]]");
        return stringBuilder.toString();
    }

    int wrapPosition(int n) {
        if (n < this.fileLength) {
            return n;
        }
        return n + 16 - this.fileLength;
    }

    static class Element {
        static final Element NULL = new Element(0, 0);
        final int length;
        final int position;

        Element(int n, int n2) {
            this.position = n;
            this.length = n2;
        }

        public String toString() {
            return this.getClass().getSimpleName() + "[position = " + this.position + ", length = " + this.length + "]";
        }
    }

    final class ElementInputStream
    extends InputStream {
        private int position;
        private int remaining;

        ElementInputStream(Element element) {
            this.position = QueueFile.this.wrapPosition(element.position + 4);
            this.remaining = element.length;
        }

        @Override
        public int read() throws IOException {
            if (this.remaining == 0) {
                return -1;
            }
            QueueFile.this.raf.seek(this.position);
            int n = QueueFile.this.raf.read();
            this.position = QueueFile.this.wrapPosition(this.position + 1);
            --this.remaining;
            return n;
        }

        @Override
        public int read(byte[] arrby, int n, int n2) throws IOException {
            if ((n | n2) < 0 || n2 > arrby.length - n) {
                throw new ArrayIndexOutOfBoundsException();
            }
            if (this.remaining == 0) {
                return -1;
            }
            int n3 = n2;
            if (n2 > this.remaining) {
                n3 = this.remaining;
            }
            QueueFile.this.ringRead(this.position, arrby, n, n3);
            this.position = QueueFile.this.wrapPosition(this.position + n3);
            this.remaining -= n3;
            return n3;
        }
    }

}

