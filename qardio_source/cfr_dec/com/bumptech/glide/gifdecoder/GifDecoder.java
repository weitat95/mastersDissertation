/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.annotation.TargetApi
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package com.bumptech.glide.gifdecoder;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.util.Log;
import com.bumptech.glide.gifdecoder.GifFrame;
import com.bumptech.glide.gifdecoder.GifHeader;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class GifDecoder {
    private static final Bitmap.Config BITMAP_CONFIG;
    private static final String TAG;
    private int[] act;
    private BitmapProvider bitmapProvider;
    private final byte[] block = new byte[256];
    private byte[] data;
    private int framePointer;
    private GifHeader header;
    private byte[] mainPixels;
    private int[] mainScratch;
    private byte[] pixelStack;
    private short[] prefix;
    private Bitmap previousImage;
    private ByteBuffer rawData;
    private boolean savePrevious;
    private int status;
    private byte[] suffix;

    static {
        TAG = GifDecoder.class.getSimpleName();
        BITMAP_CONFIG = Bitmap.Config.ARGB_8888;
    }

    public GifDecoder(BitmapProvider bitmapProvider) {
        this.bitmapProvider = bitmapProvider;
        this.header = new GifHeader();
    }

    /*
     * Enabled aggressive block sorting
     */
    private void decodeBitmapData(GifFrame arrby) {
        int n;
        if (arrby != null) {
            this.rawData.position(arrby.bufferFrameStart);
        }
        int n2 = arrby == null ? this.header.width * this.header.height : arrby.iw * arrby.ih;
        if (this.mainPixels == null || this.mainPixels.length < n2) {
            this.mainPixels = new byte[n2];
        }
        if (this.prefix == null) {
            this.prefix = new short[4096];
        }
        if (this.suffix == null) {
            this.suffix = new byte[4096];
        }
        if (this.pixelStack == null) {
            this.pixelStack = new byte[4097];
        }
        int n3 = this.read();
        int n4 = 1 << n3;
        int n5 = n4 + 2;
        int n6 = -1;
        int n7 = n3 + 1;
        int n8 = (1 << n7) - 1;
        for (n = 0; n < n4; ++n) {
            this.prefix[n] = 0;
            this.suffix[n] = (byte)n;
        }
        int n9 = 0;
        int n10 = 0;
        n = 0;
        int n11 = 0;
        int n12 = 0;
        int n13 = 0;
        int n14 = 0;
        int n15 = 0;
        block1: do {
            int n16;
            block24: {
                block25: {
                    block23: {
                        if (n15 >= n2) break block23;
                        n16 = n9;
                        n9 = n12;
                        if (n12 != 0) break block24;
                        n9 = this.readBlock();
                        if (n9 > 0) break block25;
                        this.status = 3;
                    }
                    do {
                        if (n10 >= n2) {
                            return;
                        }
                        this.mainPixels[n10] = 0;
                        ++n10;
                    } while (true);
                }
                n16 = 0;
            }
            n14 += (this.block[n16] & 0xFF) << n13;
            n12 = n13 + 8;
            int n17 = n16 + 1;
            int n18 = n9 - 1;
            n9 = n5;
            while (n12 >= n7) {
                n13 = n14 & n8;
                n16 = n14 >> n7;
                int n19 = n12 - n7;
                if (n13 == n4) {
                    n7 = n3 + 1;
                    n8 = (1 << n7) - 1;
                    n9 = n4 + 2;
                    n6 = -1;
                    n12 = n19;
                    n14 = n16;
                    continue;
                }
                if (n13 > n9) {
                    this.status = 3;
                    n5 = n9;
                    n9 = n17;
                    n13 = n19;
                    n12 = n18;
                    n14 = n16;
                    continue block1;
                }
                if (n13 == n4 + 1) {
                    n5 = n9;
                    n9 = n17;
                    n13 = n19;
                    n12 = n18;
                    n14 = n16;
                    continue block1;
                }
                if (n6 == -1) {
                    this.pixelStack[n] = this.suffix[n13];
                    n6 = n13;
                    ++n;
                    n12 = n19;
                    n14 = n16;
                    n11 = n13;
                    continue;
                }
                n12 = n13;
                n14 = n;
                if (n13 >= n9) {
                    this.pixelStack[n] = (byte)n11;
                    n12 = n6;
                    n14 = n + 1;
                }
                while (n12 >= n4) {
                    this.pixelStack[n14] = this.suffix[n12];
                    n12 = this.prefix[n12];
                    ++n14;
                }
                int n20 = this.suffix[n12] & 0xFF;
                arrby = this.pixelStack;
                n5 = n14 + 1;
                arrby[n14] = (byte)n20;
                n12 = n9;
                n14 = n8;
                n11 = n7;
                if (n9 < 4096) {
                    this.prefix[n9] = (short)n6;
                    this.suffix[n9] = (byte)n20;
                    n12 = n = n9 + 1;
                    n14 = n8;
                    n11 = n7;
                    if ((n & n8) == 0) {
                        n12 = n;
                        n14 = n8;
                        n11 = n7;
                        if (n < 4096) {
                            n11 = n7 + 1;
                            n14 = n8 + n;
                            n12 = n;
                        }
                    }
                }
                n = n10;
                n6 = n5;
                while (n6 > 0) {
                    this.mainPixels[n] = this.pixelStack[--n6];
                    ++n15;
                    ++n;
                }
                n5 = n6;
                n9 = n12;
                n12 = n19;
                n8 = n14;
                n7 = n11;
                n14 = n16;
                n11 = n20;
                n6 = n13;
                n10 = n;
                n = n5;
            }
            n5 = n9;
            n9 = n17;
            n13 = n12;
            n12 = n18;
        } while (true);
    }

    private Bitmap getNextBitmap() {
        Bitmap bitmap;
        Bitmap bitmap2 = bitmap = this.bitmapProvider.obtain(this.header.width, this.header.height, BITMAP_CONFIG);
        if (bitmap == null) {
            bitmap2 = Bitmap.createBitmap((int)this.header.width, (int)this.header.height, (Bitmap.Config)BITMAP_CONFIG);
        }
        GifDecoder.setAlpha(bitmap2);
        return bitmap2;
    }

    private int read() {
        try {
            byte by = this.rawData.get();
            return by & 0xFF;
        }
        catch (Exception exception) {
            this.status = 1;
            return 0;
        }
    }

    private int readBlock() {
        int n = this.read();
        int n2 = 0;
        int n3 = 0;
        if (n > 0) {
            do {
                n2 = n3;
                if (n3 >= n) break;
                n2 = n - n3;
                try {
                    this.rawData.get(this.block, n3, n2);
                    n3 += n2;
                }
                catch (Exception exception) {
                    Log.w((String)TAG, (String)"Error Reading Block", (Throwable)exception);
                    this.status = 1;
                    n2 = n3;
                    break;
                }
            } while (true);
        }
        return n2;
    }

    @TargetApi(value=12)
    private static void setAlpha(Bitmap bitmap) {
        if (Build.VERSION.SDK_INT >= 12) {
            bitmap.setHasAlpha(true);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private Bitmap setPixels(GifFrame gifFrame, GifFrame gifFrame2) {
        int n;
        int n2 = this.header.width;
        int n3 = this.header.height;
        int[] arrn = this.mainScratch;
        if (gifFrame2 != null && gifFrame2.dispose > 0) {
            if (gifFrame2.dispose == 2) {
                n = 0;
                if (!gifFrame.transparency) {
                    n = this.header.bgColor;
                }
                Arrays.fill(arrn, n);
            } else if (gifFrame2.dispose == 3 && this.previousImage != null) {
                this.previousImage.getPixels(arrn, 0, n2, 0, 0, n2, n3);
            }
        }
        this.decodeBitmapData(gifFrame);
        int n4 = 1;
        int n5 = 8;
        int n6 = 0;
        for (int i = 0; i < gifFrame.ih; ++i) {
            int n7 = i;
            n = n6;
            int n8 = n5;
            int n9 = n4;
            if (gifFrame.interlace) {
                n = n6;
                n8 = n5;
                n9 = n4;
                if (n6 >= gifFrame.ih) {
                    n9 = n4 + 1;
                    switch (n9) {
                        default: {
                            n8 = n5;
                            n = n6;
                            break;
                        }
                        case 2: {
                            n = 4;
                            n8 = n5;
                            break;
                        }
                        case 3: {
                            n = 2;
                            n8 = 4;
                            break;
                        }
                        case 4: {
                            n = 1;
                            n8 = 2;
                        }
                    }
                }
                n7 = n;
                n += n8;
            }
            if ((n5 = n7 + gifFrame.iy) < this.header.height) {
                n7 = n5 * this.header.width;
                n6 = n7 + gifFrame.ix;
                n5 = n4 = n6 + gifFrame.iw;
                if (this.header.width + n7 < n4) {
                    n5 = n7 + this.header.width;
                }
                n4 = i * gifFrame.iw;
                while (n6 < n5) {
                    n7 = this.mainPixels[n4];
                    if ((n7 = this.act[n7 & 0xFF]) != 0) {
                        arrn[n6] = n7;
                    }
                    ++n6;
                    ++n4;
                }
            }
            n6 = n;
            n5 = n8;
            n4 = n9;
        }
        if (this.savePrevious && (gifFrame.dispose == 0 || gifFrame.dispose == 1)) {
            if (this.previousImage == null) {
                this.previousImage = this.getNextBitmap();
            }
            this.previousImage.setPixels(arrn, 0, n2, 0, 0, n2, n3);
        }
        gifFrame = this.getNextBitmap();
        gifFrame.setPixels(arrn, 0, n2, 0, 0, n2, n3);
        return gifFrame;
    }

    public void advance() {
        this.framePointer = (this.framePointer + 1) % this.header.frameCount;
    }

    public void clear() {
        this.header = null;
        this.data = null;
        this.mainPixels = null;
        this.mainScratch = null;
        if (this.previousImage != null) {
            this.bitmapProvider.release(this.previousImage);
        }
        this.previousImage = null;
        this.rawData = null;
    }

    public int getCurrentFrameIndex() {
        return this.framePointer;
    }

    public int getDelay(int n) {
        int n2;
        int n3 = n2 = -1;
        if (n >= 0) {
            n3 = n2;
            if (n < this.header.frameCount) {
                n3 = this.header.frames.get((int)n).delay;
            }
        }
        return n3;
    }

    public int getFrameCount() {
        return this.header.frameCount;
    }

    public int getLoopCount() {
        return this.header.loopCount;
    }

    public int getNextDelay() {
        if (this.header.frameCount <= 0 || this.framePointer < 0) {
            return -1;
        }
        return this.getDelay(this.framePointer);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public Bitmap getNextFrame() {
        GifFrame gifFrame = null;
        synchronized (this) {
            GifFrame gifFrame2;
            if (this.header.frameCount <= 0 || this.framePointer < 0) {
                if (Log.isLoggable((String)TAG, (int)3)) {
                    Log.d((String)TAG, (String)("unable to decode frame, frameCount=" + this.header.frameCount + " framePointer=" + this.framePointer));
                }
                this.status = 1;
            }
            if (this.status == 1 || this.status == 2) {
                gifFrame2 = gifFrame;
                if (!Log.isLoggable((String)TAG, (int)3)) return gifFrame2;
                Log.d((String)TAG, (String)("Unable to decode frame, status=" + this.status));
                return gifFrame;
            }
            this.status = 0;
            GifFrame gifFrame3 = this.header.frames.get(this.framePointer);
            gifFrame2 = null;
            int n = this.framePointer - 1;
            if (n >= 0) {
                gifFrame2 = this.header.frames.get(n);
            }
            if (gifFrame3.lct == null) {
                this.act = this.header.gct;
            } else {
                this.act = gifFrame3.lct;
                if (this.header.bgIndex == gifFrame3.transIndex) {
                    this.header.bgColor = 0;
                }
            }
            n = 0;
            if (gifFrame3.transparency) {
                n = this.act[gifFrame3.transIndex];
                this.act[gifFrame3.transIndex] = 0;
            }
            if (this.act == null) {
                if (Log.isLoggable((String)TAG, (int)3)) {
                    Log.d((String)TAG, (String)"No Valid Color Table");
                }
                this.status = 1;
                return gifFrame;
            }
            gifFrame2 = gifFrame = this.setPixels(gifFrame3, gifFrame2);
            if (!gifFrame3.transparency) return gifFrame2;
            this.act[gifFrame3.transIndex] = n;
            return gifFrame;
        }
    }

    public void setData(GifHeader gifHeader, byte[] object) {
        this.header = gifHeader;
        this.data = object;
        this.status = 0;
        this.framePointer = -1;
        this.rawData = ByteBuffer.wrap((byte[])object);
        this.rawData.rewind();
        this.rawData.order(ByteOrder.LITTLE_ENDIAN);
        this.savePrevious = false;
        object = gifHeader.frames.iterator();
        while (object.hasNext()) {
            if (((GifFrame)object.next()).dispose != 3) continue;
            this.savePrevious = true;
            break;
        }
        this.mainPixels = new byte[gifHeader.width * gifHeader.height];
        this.mainScratch = new int[gifHeader.width * gifHeader.height];
    }

    public static interface BitmapProvider {
        public Bitmap obtain(int var1, int var2, Bitmap.Config var3);

        public void release(Bitmap var1);
    }

}

