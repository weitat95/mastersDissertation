/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils.logger;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.UnsupportedEncodingException;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

public class ReverseLineReader {
    private ByteBuffer buf;
    private int bufPos;
    private ByteArrayOutputStream byteArrayOutputStream;
    private final FileChannel channel;
    private final String encoding;
    private long filePos;
    private byte lastLineBreak = (byte)10;

    public ReverseLineReader(RandomAccessFile randomAccessFile, String string2) throws IOException {
        this.byteArrayOutputStream = new ByteArrayOutputStream();
        this.channel = randomAccessFile.getChannel();
        this.filePos = randomAccessFile.length();
        this.encoding = string2;
    }

    private String bufToString() throws UnsupportedEncodingException {
        if (this.byteArrayOutputStream.size() == 0) {
            return "";
        }
        byte[] arrby = this.byteArrayOutputStream.toByteArray();
        for (int i = 0; i < arrby.length / 2; ++i) {
            byte by = arrby[i];
            arrby[i] = arrby[arrby.length - i - 1];
            arrby[arrby.length - i - 1] = by;
        }
        this.byteArrayOutputStream.reset();
        return new String(arrby, this.encoding);
    }

    public String readLine() throws IOException {
        block0: do {
            if (this.bufPos < 0) {
                if (this.filePos == 0L) {
                    if (this.byteArrayOutputStream == null) {
                        return null;
                    }
                    String string2 = this.bufToString();
                    this.byteArrayOutputStream = null;
                    return string2;
                }
                long l = Math.max(this.filePos - 8192L, 0L);
                long l2 = this.filePos - l;
                this.buf = this.channel.map(FileChannel.MapMode.READ_ONLY, l, l2);
                this.bufPos = (int)l2;
                this.filePos = l;
            }
            do {
                int n = this.bufPos;
                this.bufPos = n - 1;
                if (n <= 0) continue block0;
                byte by = this.buf.get(this.bufPos);
                if (by == 13 || by == 10) {
                    if (by != this.lastLineBreak) {
                        this.lastLineBreak = by;
                        continue;
                    }
                    this.lastLineBreak = by;
                    return this.bufToString();
                }
                this.byteArrayOutputStream.write(by);
            } while (true);
            break;
        } while (true);
    }
}

