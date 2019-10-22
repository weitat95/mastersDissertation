/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.util.Log
 */
package com.bumptech.glide.gifdecoder;

import android.util.Log;
import com.bumptech.glide.gifdecoder.GifFrame;
import com.bumptech.glide.gifdecoder.GifHeader;
import java.nio.Buffer;
import java.nio.BufferUnderflowException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.List;

public class GifHeaderParser {
    private final byte[] block = new byte[256];
    private int blockSize = 0;
    private GifHeader header;
    private ByteBuffer rawData;

    private boolean err() {
        return this.header.status != 0;
    }

    private int read() {
        try {
            byte by = this.rawData.get();
            return by & 0xFF;
        }
        catch (Exception exception) {
            this.header.status = 1;
            return 0;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readBitmap() {
        boolean bl = true;
        this.header.currentFrame.ix = this.readShort();
        this.header.currentFrame.iy = this.readShort();
        this.header.currentFrame.iw = this.readShort();
        this.header.currentFrame.ih = this.readShort();
        int n = this.read();
        boolean bl2 = (n & 0x80) != 0;
        int n2 = (int)Math.pow(2.0, (n & 7) + 1);
        Object object = this.header.currentFrame;
        if ((n & 0x40) == 0) {
            bl = false;
        }
        ((GifFrame)object).interlace = bl;
        this.header.currentFrame.lct = bl2 ? this.readColorTable(n2) : null;
        this.header.currentFrame.bufferFrameStart = this.rawData.position();
        this.skipImageData();
        if (this.err()) {
            return;
        }
        object = this.header;
        ++((GifHeader)object).frameCount;
        this.header.frames.add(this.header.currentFrame);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private int readBlock() {
        this.blockSize = this.read();
        int n = 0;
        int n2 = 0;
        if (this.blockSize <= 0) return n;
        int n4 = 0;
        do {
            int n3 = n4;
            n = n2;
            try {
                if (n2 >= this.blockSize) return n;
                n3 = n4;
                n3 = n4 = this.blockSize - n2;
                this.rawData.get(this.block, n2, n4);
                n2 += n4;
            }
            catch (Exception exception) {
                if (Log.isLoggable((String)"GifHeaderParser", (int)3)) {
                    Log.d((String)"GifHeaderParser", (String)("Error Reading Block n: " + n2 + " count: " + n3 + " blockSize: " + this.blockSize), (Throwable)exception);
                }
                this.header.status = 1;
                return n2;
            }
        } while (true);
    }

    private int[] readColorTable(int n) {
        int n2;
        int n3;
        int[] arrn;
        int[] arrn2 = null;
        byte[] arrby = new byte[n * 3];
        try {
            this.rawData.get(arrby);
            arrn = new int[256];
            n3 = 0;
            n2 = 0;
        }
        catch (BufferUnderflowException bufferUnderflowException) {
            if (Log.isLoggable((String)"GifHeaderParser", (int)3)) {
                Log.d((String)"GifHeaderParser", (String)"Format Error Reading Color Table", (Throwable)bufferUnderflowException);
            }
            this.header.status = 1;
        }
        do {
            arrn2 = arrn;
            if (n2 < n) {
                int n4 = n3 + 1;
                n3 = arrby[n3];
                int n5 = n4 + 1;
                arrn[n2] = 0xFF000000 | (n3 & 0xFF) << 16 | (arrby[n4] & 0xFF) << 8 | arrby[n5] & 0xFF;
                n3 = n5 + 1;
                ++n2;
                continue;
            }
            break;
        } while (true);
        return arrn2;
    }

    private void readContents() {
        boolean bl = false;
        block11 : while (!bl && !this.err()) {
            switch (this.read()) {
                default: {
                    this.header.status = 1;
                    continue block11;
                }
                case 44: {
                    if (this.header.currentFrame == null) {
                        this.header.currentFrame = new GifFrame();
                    }
                    this.readBitmap();
                    continue block11;
                }
                case 33: {
                    switch (this.read()) {
                        default: {
                            this.skip();
                            continue block11;
                        }
                        case 249: {
                            this.header.currentFrame = new GifFrame();
                            this.readGraphicControlExt();
                            continue block11;
                        }
                        case 255: {
                            this.readBlock();
                            String string2 = "";
                            for (int i = 0; i < 11; ++i) {
                                string2 = string2 + (char)this.block[i];
                            }
                            if (string2.equals("NETSCAPE2.0")) {
                                this.readNetscapeExt();
                                continue block11;
                            }
                            this.skip();
                            continue block11;
                        }
                        case 254: {
                            this.skip();
                            continue block11;
                        }
                        case 1: 
                    }
                    this.skip();
                    continue block11;
                }
                case 59: 
            }
            bl = true;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readGraphicControlExt() {
        int n;
        boolean bl = true;
        this.read();
        int n2 = this.read();
        this.header.currentFrame.dispose = (n2 & 0x1C) >> 2;
        if (this.header.currentFrame.dispose == 0) {
            this.header.currentFrame.dispose = 1;
        }
        GifFrame gifFrame = this.header.currentFrame;
        if ((n2 & 1) == 0) {
            bl = false;
        }
        gifFrame.transparency = bl;
        n2 = n = this.readShort();
        if (n < 3) {
            n2 = 10;
        }
        this.header.currentFrame.delay = n2 * 10;
        this.header.currentFrame.transIndex = this.read();
        this.read();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readHeader() {
        String string2 = "";
        for (int i = 0; i < 6; ++i) {
            string2 = string2 + (char)this.read();
        }
        if (!string2.startsWith("GIF")) {
            this.header.status = 1;
            return;
        } else {
            this.readLSD();
            if (!this.header.gctFlag || this.err()) return;
            {
                this.header.gct = this.readColorTable(this.header.gctSize);
                this.header.bgColor = this.header.gct[this.header.bgIndex];
                return;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void readLSD() {
        this.header.width = this.readShort();
        this.header.height = this.readShort();
        int n = this.read();
        GifHeader gifHeader = this.header;
        boolean bl = (n & 0x80) != 0;
        gifHeader.gctFlag = bl;
        this.header.gctSize = 2 << (n & 7);
        this.header.bgIndex = this.read();
        this.header.pixelAspect = this.read();
    }

    private void readNetscapeExt() {
        do {
            this.readBlock();
            if (this.block[0] != 1) continue;
            byte by = this.block[1];
            byte by2 = this.block[2];
            this.header.loopCount = (by2 & 0xFF) << 8 | by & 0xFF;
        } while (this.blockSize > 0 && !this.err());
    }

    private int readShort() {
        return this.rawData.getShort();
    }

    private void reset() {
        this.rawData = null;
        Arrays.fill(this.block, (byte)0);
        this.header = new GifHeader();
        this.blockSize = 0;
    }

    private void skip() {
        int n;
        do {
            n = this.read();
            this.rawData.position(this.rawData.position() + n);
        } while (n > 0);
    }

    private void skipImageData() {
        this.read();
        this.skip();
    }

    public void clear() {
        this.rawData = null;
        this.header = null;
    }

    public GifHeader parseHeader() {
        if (this.rawData == null) {
            throw new IllegalStateException("You must call setData() before parseHeader()");
        }
        if (this.err()) {
            return this.header;
        }
        this.readHeader();
        if (!this.err()) {
            this.readContents();
            if (this.header.frameCount < 0) {
                this.header.status = 1;
            }
        }
        return this.header;
    }

    public GifHeaderParser setData(byte[] arrby) {
        this.reset();
        if (arrby != null) {
            this.rawData = ByteBuffer.wrap(arrby);
            this.rawData.rewind();
            this.rawData.order(ByteOrder.LITTLE_ENDIAN);
            return this;
        }
        this.rawData = null;
        this.header.status = 2;
        return this;
    }
}

