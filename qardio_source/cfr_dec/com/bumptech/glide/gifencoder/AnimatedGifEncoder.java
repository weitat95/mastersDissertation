/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.Canvas
 *  android.graphics.Color
 *  android.graphics.Paint
 *  android.util.Log
 */
package com.bumptech.glide.gifencoder;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import com.bumptech.glide.gifencoder.LZWEncoder;
import com.bumptech.glide.gifencoder.NeuQuant;
import java.io.IOException;
import java.io.OutputStream;

public class AnimatedGifEncoder {
    private boolean closeStream = false;
    private int colorDepth;
    private byte[] colorTab;
    private int delay = 0;
    private int dispose = -1;
    private boolean firstFrame = true;
    private boolean hasTransparentPixels;
    private int height;
    private Bitmap image;
    private byte[] indexedPixels;
    private OutputStream out;
    private int palSize = 7;
    private byte[] pixels;
    private int repeat = -1;
    private int sample = 10;
    private boolean sizeSet = false;
    private boolean started = false;
    private int transIndex;
    private Integer transparent = null;
    private boolean[] usedEntry = new boolean[256];
    private int width;

    /*
     * Enabled aggressive block sorting
     */
    private void analyzePixels() {
        int n = this.pixels.length;
        int n2 = n / 3;
        this.indexedPixels = new byte[n2];
        NeuQuant neuQuant = new NeuQuant(this.pixels, n, this.sample);
        this.colorTab = neuQuant.process();
        for (n = 0; n < this.colorTab.length; n += 3) {
            byte by = this.colorTab[n];
            this.colorTab[n] = this.colorTab[n + 2];
            this.colorTab[n + 2] = by;
            this.usedEntry[n / 3] = false;
        }
        int n3 = 0;
        for (n = 0; n < n2; ++n, ++n3) {
            byte[] arrby = this.pixels;
            int n4 = n3 + 1;
            byte by = arrby[n3];
            arrby = this.pixels;
            n3 = n4 + 1;
            n4 = neuQuant.map(by & 0xFF, arrby[n4] & 0xFF, this.pixels[n3] & 0xFF);
            this.usedEntry[n4] = true;
            this.indexedPixels[n] = (byte)n4;
        }
        this.pixels = null;
        this.colorDepth = 8;
        this.palSize = 7;
        if (this.transparent != null) {
            this.transIndex = this.findClosest(this.transparent);
            return;
        } else {
            if (!this.hasTransparentPixels) return;
            {
                this.transIndex = this.findClosest(0);
                return;
            }
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private int findClosest(int n) {
        if (this.colorTab == null) {
            return -1;
        }
        int n2 = Color.red((int)n);
        int n3 = Color.green((int)n);
        int n4 = Color.blue((int)n);
        n = 0;
        int n5 = 16777216;
        int n6 = this.colorTab.length;
        int n7 = 0;
        do {
            int n8 = n;
            if (n7 >= n6) return n8;
            byte[] arrby = this.colorTab;
            int n9 = n7 + 1;
            n8 = n2 - (arrby[n7] & 0xFF);
            arrby = this.colorTab;
            int n10 = n9 + 1;
            n7 = n3 - (arrby[n9] & 0xFF);
            n9 = n4 - (this.colorTab[n10] & 0xFF);
            int n11 = n8 * n8 + n7 * n7 + n9 * n9;
            n9 = n10 / 3;
            n7 = n5;
            n8 = n;
            if (this.usedEntry[n9]) {
                n7 = n5;
                n8 = n;
                if (n11 < n5) {
                    n7 = n11;
                    n8 = n9;
                }
            }
            n = n10 + 1;
            n5 = n7;
            n7 = n;
            n = n8;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void getImagePixels() {
        int[] arrn;
        int n = this.image.getWidth();
        int n2 = this.image.getHeight();
        if (n != this.width || n2 != this.height) {
            arrn = Bitmap.createBitmap((int)this.width, (int)this.height, (Bitmap.Config)Bitmap.Config.ARGB_8888);
            new Canvas((Bitmap)arrn).drawBitmap((Bitmap)arrn, 0.0f, 0.0f, null);
            this.image = arrn;
        }
        arrn = new int[n * n2];
        this.image.getPixels(arrn, 0, n, 0, 0, n, n2);
        this.pixels = new byte[arrn.length * 3];
        this.hasTransparentPixels = false;
        int n3 = 0;
        int n4 = arrn.length;
        int n5 = 0;
        for (n = 0; n < n4; ++n, ++n5) {
            int n6 = arrn[n];
            n2 = n3;
            if (n6 == 0) {
                n2 = n3 + 1;
            }
            byte[] arrby = this.pixels;
            n3 = n5 + 1;
            arrby[n5] = (byte)(n6 & 0xFF);
            arrby = this.pixels;
            n5 = n3 + 1;
            arrby[n3] = (byte)(n6 >> 8 & 0xFF);
            this.pixels[n5] = (byte)(n6 >> 16 & 0xFF);
            n3 = n2;
        }
        double d = (double)(n3 * 100) / (double)arrn.length;
        boolean bl = d > 4.0;
        this.hasTransparentPixels = bl;
        if (Log.isLoggable((String)"AnimatedGifEncoder", (int)3)) {
            Log.d((String)"AnimatedGifEncoder", (String)("got pixels for frame with " + d + "% transparent pixels"));
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void writeGraphicCtrlExt() throws IOException {
        int n;
        int n2;
        this.out.write(33);
        this.out.write(249);
        this.out.write(4);
        if (this.transparent == null && !this.hasTransparentPixels) {
            n = 0;
            n2 = 0;
        } else {
            n = 1;
            n2 = 2;
        }
        if (this.dispose >= 0) {
            n2 = this.dispose & 7;
        }
        this.out.write(n2 << 2 | 0 | 0 | n);
        this.writeShort(this.delay);
        this.out.write(this.transIndex);
        this.out.write(0);
    }

    private void writeImageDesc() throws IOException {
        this.out.write(44);
        this.writeShort(0);
        this.writeShort(0);
        this.writeShort(this.width);
        this.writeShort(this.height);
        if (this.firstFrame) {
            this.out.write(0);
            return;
        }
        this.out.write(this.palSize | 0x80);
    }

    private void writeLSD() throws IOException {
        this.writeShort(this.width);
        this.writeShort(this.height);
        this.out.write(this.palSize | 0xF0);
        this.out.write(0);
        this.out.write(0);
    }

    private void writeNetscapeExt() throws IOException {
        this.out.write(33);
        this.out.write(255);
        this.out.write(11);
        this.writeString("NETSCAPE2.0");
        this.out.write(3);
        this.out.write(1);
        this.writeShort(this.repeat);
        this.out.write(0);
    }

    private void writePalette() throws IOException {
        this.out.write(this.colorTab, 0, this.colorTab.length);
        int n = this.colorTab.length;
        for (int i = 0; i < 768 - n; ++i) {
            this.out.write(0);
        }
    }

    private void writePixels() throws IOException {
        new LZWEncoder(this.width, this.height, this.indexedPixels, this.colorDepth).encode(this.out);
    }

    private void writeShort(int n) throws IOException {
        this.out.write(n & 0xFF);
        this.out.write(n >> 8 & 0xFF);
    }

    private void writeString(String string2) throws IOException {
        for (int i = 0; i < string2.length(); ++i) {
            this.out.write((byte)string2.charAt(i));
        }
    }

    public boolean addFrame(Bitmap bitmap) {
        if (bitmap == null || !this.started) {
            return false;
        }
        try {
            if (!this.sizeSet) {
                this.setSize(bitmap.getWidth(), bitmap.getHeight());
            }
            this.image = bitmap;
            this.getImagePixels();
            this.analyzePixels();
            if (this.firstFrame) {
                this.writeLSD();
                this.writePalette();
                if (this.repeat >= 0) {
                    this.writeNetscapeExt();
                }
            }
            this.writeGraphicCtrlExt();
            this.writeImageDesc();
            if (!this.firstFrame) {
                this.writePalette();
            }
            this.writePixels();
            this.firstFrame = false;
            return true;
        }
        catch (IOException iOException) {
            return false;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean finish() {
        boolean bl;
        if (!this.started) {
            return false;
        }
        boolean bl2 = true;
        this.started = false;
        try {
            this.out.write(59);
            this.out.flush();
            bl = bl2;
            if (this.closeStream) {
                this.out.close();
                bl = bl2;
            }
        }
        catch (IOException iOException) {
            bl = false;
        }
        this.transIndex = 0;
        this.out = null;
        this.image = null;
        this.pixels = null;
        this.indexedPixels = null;
        this.colorTab = null;
        this.closeStream = false;
        this.firstFrame = true;
        return bl;
    }

    public void setDelay(int n) {
        this.delay = Math.round((float)n / 10.0f);
    }

    public void setSize(int n, int n2) {
        if (this.started && !this.firstFrame) {
            return;
        }
        this.width = n;
        this.height = n2;
        if (this.width < 1) {
            this.width = 320;
        }
        if (this.height < 1) {
            this.height = 240;
        }
        this.sizeSet = true;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public boolean start(OutputStream outputStream) {
        if (outputStream == null) {
            return false;
        }
        boolean bl = true;
        this.closeStream = false;
        this.out = outputStream;
        try {
            this.writeString("GIF89a");
        }
        catch (IOException iOException) {
            bl = false;
        }
        this.started = bl;
        return bl;
    }
}

