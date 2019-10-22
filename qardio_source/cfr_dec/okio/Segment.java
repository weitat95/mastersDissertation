/*
 * Decompiled with CFR 0.147.
 */
package okio;

import okio.SegmentPool;

final class Segment {
    final byte[] data;
    int limit;
    Segment next;
    boolean owner;
    int pos;
    Segment prev;
    boolean shared;

    Segment() {
        this.data = new byte[8192];
        this.owner = true;
        this.shared = false;
    }

    Segment(Segment segment) {
        this(segment.data, segment.pos, segment.limit);
        segment.shared = true;
    }

    Segment(byte[] arrby, int n, int n2) {
        this.data = arrby;
        this.pos = n;
        this.limit = n2;
        this.owner = false;
        this.shared = true;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void compact() {
        int n;
        int n2;
        int n3;
        if (this.prev == this) {
            throw new IllegalStateException();
        }
        if (!this.prev.owner || (n3 = this.limit - this.pos) > 8192 - (n = this.prev.limit) + (n2 = this.prev.shared ? 0 : this.prev.pos)) {
            return;
        }
        this.writeTo(this.prev, n3);
        this.pop();
        SegmentPool.recycle(this);
    }

    /*
     * Enabled aggressive block sorting
     */
    public Segment pop() {
        Segment segment = this.next != this ? this.next : null;
        this.prev.next = this.next;
        this.next.prev = this.prev;
        this.next = null;
        this.prev = null;
        return segment;
    }

    public Segment push(Segment segment) {
        segment.prev = this;
        segment.next = this.next;
        this.next.prev = segment;
        this.next = segment;
        return segment;
    }

    /*
     * Enabled aggressive block sorting
     */
    public Segment split(int n) {
        Segment segment;
        if (n <= 0 || n > this.limit - this.pos) {
            throw new IllegalArgumentException();
        }
        if (n >= 1024) {
            segment = new Segment(this);
        } else {
            segment = SegmentPool.take();
            System.arraycopy(this.data, this.pos, segment.data, 0, n);
        }
        segment.limit = segment.pos + n;
        this.pos += n;
        this.prev.push(segment);
        return segment;
    }

    public void writeTo(Segment segment, int n) {
        if (!segment.owner) {
            throw new IllegalArgumentException();
        }
        if (segment.limit + n > 8192) {
            if (segment.shared) {
                throw new IllegalArgumentException();
            }
            if (segment.limit + n - segment.pos > 8192) {
                throw new IllegalArgumentException();
            }
            System.arraycopy(segment.data, segment.pos, segment.data, 0, segment.limit - segment.pos);
            segment.limit -= segment.pos;
            segment.pos = 0;
        }
        System.arraycopy(this.data, this.pos, segment.data, segment.limit, n);
        segment.limit += n;
        this.pos += n;
    }
}

