/*
 * Decompiled with CFR 0.147.
 */
package okio;

import okio.Segment;

final class SegmentPool {
    static long byteCount;
    static Segment next;

    private SegmentPool() {
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static void recycle(Segment segment) {
        if (segment.next != null || segment.prev != null) {
            throw new IllegalArgumentException();
        }
        if (segment.shared) {
            return;
        }
        synchronized (SegmentPool.class) {
            if (byteCount + 8192L > 65536L) {
                return;
            }
            byteCount += 8192L;
            segment.next = next;
            segment.limit = 0;
            segment.pos = 0;
            next = segment;
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static Segment take() {
        synchronized (SegmentPool.class) {
            if (next != null) {
                Segment segment = next;
                next = segment.next;
                segment.next = null;
                byteCount -= 8192L;
                return segment;
            }
            return new Segment();
        }
    }
}

