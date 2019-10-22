/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

import java.util.Arrays;

public final class Settings {
    private int set;
    private final int[] values = new int[10];

    void clear() {
        this.set = 0;
        Arrays.fill(this.values, 0);
    }

    int get(int n) {
        return this.values[n];
    }

    int getHeaderTableSize() {
        if ((this.set & 2) != 0) {
            return this.values[1];
        }
        return -1;
    }

    int getInitialWindowSize() {
        if ((this.set & 0x80) != 0) {
            return this.values[7];
        }
        return 65535;
    }

    int getMaxConcurrentStreams(int n) {
        if ((this.set & 0x10) != 0) {
            n = this.values[4];
        }
        return n;
    }

    int getMaxFrameSize(int n) {
        if ((this.set & 0x20) != 0) {
            n = this.values[5];
        }
        return n;
    }

    boolean isSet(int n) {
        return (this.set & 1 << n) != 0;
    }

    /*
     * Enabled aggressive block sorting
     */
    void merge(Settings settings) {
        int n = 0;
        while (n < 10) {
            if (settings.isSet(n)) {
                this.set(n, settings.get(n));
            }
            ++n;
        }
        return;
    }

    Settings set(int n, int n2) {
        if (n >= this.values.length) {
            return this;
        }
        this.set |= 1 << n;
        this.values[n] = n2;
        return this;
    }

    int size() {
        return Integer.bitCount(this.set);
    }
}

