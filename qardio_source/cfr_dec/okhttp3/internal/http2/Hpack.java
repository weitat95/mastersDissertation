/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import okhttp3.internal.Util;
import okhttp3.internal.http2.Header;
import okhttp3.internal.http2.Huffman;
import okio.Buffer;
import okio.BufferedSink;
import okio.BufferedSource;
import okio.ByteString;
import okio.Okio;
import okio.Source;

final class Hpack {
    static final Map<ByteString, Integer> NAME_TO_FIRST_INDEX;
    static final Header[] STATIC_HEADER_TABLE;

    static {
        STATIC_HEADER_TABLE = new Header[]{new Header(Header.TARGET_AUTHORITY, ""), new Header(Header.TARGET_METHOD, "GET"), new Header(Header.TARGET_METHOD, "POST"), new Header(Header.TARGET_PATH, "/"), new Header(Header.TARGET_PATH, "/index.html"), new Header(Header.TARGET_SCHEME, "http"), new Header(Header.TARGET_SCHEME, "https"), new Header(Header.RESPONSE_STATUS, "200"), new Header(Header.RESPONSE_STATUS, "204"), new Header(Header.RESPONSE_STATUS, "206"), new Header(Header.RESPONSE_STATUS, "304"), new Header(Header.RESPONSE_STATUS, "400"), new Header(Header.RESPONSE_STATUS, "404"), new Header(Header.RESPONSE_STATUS, "500"), new Header("accept-charset", ""), new Header("accept-encoding", "gzip, deflate"), new Header("accept-language", ""), new Header("accept-ranges", ""), new Header("accept", ""), new Header("access-control-allow-origin", ""), new Header("age", ""), new Header("allow", ""), new Header("authorization", ""), new Header("cache-control", ""), new Header("content-disposition", ""), new Header("content-encoding", ""), new Header("content-language", ""), new Header("content-length", ""), new Header("content-location", ""), new Header("content-range", ""), new Header("content-type", ""), new Header("cookie", ""), new Header("date", ""), new Header("etag", ""), new Header("expect", ""), new Header("expires", ""), new Header("from", ""), new Header("host", ""), new Header("if-match", ""), new Header("if-modified-since", ""), new Header("if-none-match", ""), new Header("if-range", ""), new Header("if-unmodified-since", ""), new Header("last-modified", ""), new Header("link", ""), new Header("location", ""), new Header("max-forwards", ""), new Header("proxy-authenticate", ""), new Header("proxy-authorization", ""), new Header("range", ""), new Header("referer", ""), new Header("refresh", ""), new Header("retry-after", ""), new Header("server", ""), new Header("set-cookie", ""), new Header("strict-transport-security", ""), new Header("transfer-encoding", ""), new Header("user-agent", ""), new Header("vary", ""), new Header("via", ""), new Header("www-authenticate", "")};
        NAME_TO_FIRST_INDEX = Hpack.nameToFirstIndex();
    }

    static ByteString checkLowercase(ByteString byteString) throws IOException {
        int n = byteString.size();
        for (int i = 0; i < n; ++i) {
            byte by = byteString.getByte(i);
            if (by < 65 || by > 90) continue;
            throw new IOException("PROTOCOL_ERROR response malformed: mixed case name: " + byteString.utf8());
        }
        return byteString;
    }

    private static Map<ByteString, Integer> nameToFirstIndex() {
        LinkedHashMap<ByteString, Integer> linkedHashMap = new LinkedHashMap<ByteString, Integer>(STATIC_HEADER_TABLE.length);
        for (int i = 0; i < STATIC_HEADER_TABLE.length; ++i) {
            if (linkedHashMap.containsKey(Hpack.STATIC_HEADER_TABLE[i].name)) continue;
            linkedHashMap.put(Hpack.STATIC_HEADER_TABLE[i].name, i);
        }
        return Collections.unmodifiableMap(linkedHashMap);
    }

    static final class Reader {
        Header[] dynamicTable;
        int dynamicTableByteCount = 0;
        int headerCount = 0;
        private final List<Header> headerList = new ArrayList<Header>();
        private final int headerTableSizeSetting;
        private int maxDynamicTableByteCount;
        int nextHeaderIndex;
        private final BufferedSource source;

        Reader(int n, int n2, Source source) {
            this.dynamicTable = new Header[8];
            this.nextHeaderIndex = this.dynamicTable.length - 1;
            this.headerTableSizeSetting = n;
            this.maxDynamicTableByteCount = n2;
            this.source = Okio.buffer(source);
        }

        Reader(int n, Source source) {
            this(n, n, source);
        }

        private void adjustDynamicTableByteCount() {
            block3: {
                block2: {
                    if (this.maxDynamicTableByteCount >= this.dynamicTableByteCount) break block2;
                    if (this.maxDynamicTableByteCount != 0) break block3;
                    this.clearDynamicTable();
                }
                return;
            }
            this.evictToRecoverBytes(this.dynamicTableByteCount - this.maxDynamicTableByteCount);
        }

        private void clearDynamicTable() {
            Arrays.fill(this.dynamicTable, null);
            this.nextHeaderIndex = this.dynamicTable.length - 1;
            this.headerCount = 0;
            this.dynamicTableByteCount = 0;
        }

        private int dynamicTableIndex(int n) {
            return this.nextHeaderIndex + 1 + n;
        }

        private int evictToRecoverBytes(int n) {
            int n2 = 0;
            int n3 = 0;
            if (n > 0) {
                int n4 = n;
                n = n3;
                for (n2 = this.dynamicTable.length - 1; n2 >= this.nextHeaderIndex && n4 > 0; --n2) {
                    n4 -= this.dynamicTable[n2].hpackSize;
                    this.dynamicTableByteCount -= this.dynamicTable[n2].hpackSize;
                    --this.headerCount;
                    ++n;
                }
                System.arraycopy(this.dynamicTable, this.nextHeaderIndex + 1, this.dynamicTable, this.nextHeaderIndex + 1 + n, this.headerCount);
                this.nextHeaderIndex += n;
                n2 = n;
            }
            return n2;
        }

        private ByteString getName(int n) {
            if (this.isStaticHeader(n)) {
                return Hpack.STATIC_HEADER_TABLE[n].name;
            }
            return this.dynamicTable[this.dynamicTableIndex((int)(n - Hpack.STATIC_HEADER_TABLE.length))].name;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void insertIntoDynamicTable(int n, Header header) {
            int n2;
            this.headerList.add(header);
            int n3 = n2 = header.hpackSize;
            if (n != -1) {
                n3 = n2 - this.dynamicTable[this.dynamicTableIndex((int)n)].hpackSize;
            }
            if (n3 > this.maxDynamicTableByteCount) {
                this.clearDynamicTable();
                return;
            }
            n2 = this.evictToRecoverBytes(this.dynamicTableByteCount + n3 - this.maxDynamicTableByteCount);
            if (n == -1) {
                if (this.headerCount + 1 > this.dynamicTable.length) {
                    Header[] arrheader = new Header[this.dynamicTable.length * 2];
                    System.arraycopy(this.dynamicTable, 0, arrheader, this.dynamicTable.length, this.dynamicTable.length);
                    this.nextHeaderIndex = this.dynamicTable.length - 1;
                    this.dynamicTable = arrheader;
                }
                n = this.nextHeaderIndex;
                this.nextHeaderIndex = n - 1;
                this.dynamicTable[n] = header;
                ++this.headerCount;
            } else {
                int n4 = this.dynamicTableIndex(n);
                this.dynamicTable[n + (n4 + n2)] = header;
            }
            this.dynamicTableByteCount += n3;
        }

        private boolean isStaticHeader(int n) {
            return n >= 0 && n <= STATIC_HEADER_TABLE.length - 1;
        }

        private int readByte() throws IOException {
            return this.source.readByte() & 0xFF;
        }

        private void readIndexedHeader(int n) throws IOException {
            if (this.isStaticHeader(n)) {
                Header header = STATIC_HEADER_TABLE[n];
                this.headerList.add(header);
                return;
            }
            int n2 = this.dynamicTableIndex(n - STATIC_HEADER_TABLE.length);
            if (n2 < 0 || n2 > this.dynamicTable.length - 1) {
                throw new IOException("Header index too large " + (n + 1));
            }
            this.headerList.add(this.dynamicTable[n2]);
        }

        private void readLiteralHeaderWithIncrementalIndexingIndexedName(int n) throws IOException {
            this.insertIntoDynamicTable(-1, new Header(this.getName(n), this.readByteString()));
        }

        private void readLiteralHeaderWithIncrementalIndexingNewName() throws IOException {
            this.insertIntoDynamicTable(-1, new Header(Hpack.checkLowercase(this.readByteString()), this.readByteString()));
        }

        private void readLiteralHeaderWithoutIndexingIndexedName(int n) throws IOException {
            ByteString byteString = this.getName(n);
            ByteString byteString2 = this.readByteString();
            this.headerList.add(new Header(byteString, byteString2));
        }

        private void readLiteralHeaderWithoutIndexingNewName() throws IOException {
            ByteString byteString = Hpack.checkLowercase(this.readByteString());
            ByteString byteString2 = this.readByteString();
            this.headerList.add(new Header(byteString, byteString2));
        }

        public List<Header> getAndResetHeaderList() {
            ArrayList<Header> arrayList = new ArrayList<Header>(this.headerList);
            this.headerList.clear();
            return arrayList;
        }

        /*
         * Enabled aggressive block sorting
         */
        ByteString readByteString() throws IOException {
            int n = this.readByte();
            boolean bl = (n & 0x80) == 128;
            n = this.readInt(n, 127);
            if (bl) {
                return ByteString.of(Huffman.get().decode(this.source.readByteArray(n)));
            }
            return this.source.readByteString(n);
        }

        void readHeaders() throws IOException {
            while (!this.source.exhausted()) {
                int n = this.source.readByte() & 0xFF;
                if (n == 128) {
                    throw new IOException("index == 0");
                }
                if ((n & 0x80) == 128) {
                    this.readIndexedHeader(this.readInt(n, 127) - 1);
                    continue;
                }
                if (n == 64) {
                    this.readLiteralHeaderWithIncrementalIndexingNewName();
                    continue;
                }
                if ((n & 0x40) == 64) {
                    this.readLiteralHeaderWithIncrementalIndexingIndexedName(this.readInt(n, 63) - 1);
                    continue;
                }
                if ((n & 0x20) == 32) {
                    this.maxDynamicTableByteCount = this.readInt(n, 31);
                    if (this.maxDynamicTableByteCount < 0 || this.maxDynamicTableByteCount > this.headerTableSizeSetting) {
                        throw new IOException("Invalid dynamic table size update " + this.maxDynamicTableByteCount);
                    }
                    this.adjustDynamicTableByteCount();
                    continue;
                }
                if (n == 16 || n == 0) {
                    this.readLiteralHeaderWithoutIndexingNewName();
                    continue;
                }
                this.readLiteralHeaderWithoutIndexingIndexedName(this.readInt(n, 15) - 1);
            }
        }

        int readInt(int n, int n2) throws IOException {
            int n3;
            if ((n &= n2) < n2) {
                return n;
            }
            n = 0;
            while (((n3 = this.readByte()) & 0x80) != 0) {
                n2 += (n3 & 0x7F) << n;
                n += 7;
            }
            return n2 + (n3 << n);
        }
    }

    static final class Writer {
        Header[] dynamicTable = new Header[8];
        int dynamicTableByteCount = 0;
        private boolean emitDynamicTableSizeUpdate;
        int headerCount = 0;
        int headerTableSizeSetting;
        int maxDynamicTableByteCount;
        int nextHeaderIndex = this.dynamicTable.length - 1;
        private final Buffer out;
        private int smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
        private final boolean useCompression;

        Writer(int n, boolean bl, Buffer buffer) {
            this.headerTableSizeSetting = n;
            this.maxDynamicTableByteCount = n;
            this.useCompression = bl;
            this.out = buffer;
        }

        Writer(Buffer buffer) {
            this(4096, true, buffer);
        }

        private void adjustDynamicTableByteCount() {
            block3: {
                block2: {
                    if (this.maxDynamicTableByteCount >= this.dynamicTableByteCount) break block2;
                    if (this.maxDynamicTableByteCount != 0) break block3;
                    this.clearDynamicTable();
                }
                return;
            }
            this.evictToRecoverBytes(this.dynamicTableByteCount - this.maxDynamicTableByteCount);
        }

        private void clearDynamicTable() {
            Arrays.fill(this.dynamicTable, null);
            this.nextHeaderIndex = this.dynamicTable.length - 1;
            this.headerCount = 0;
            this.dynamicTableByteCount = 0;
        }

        private int evictToRecoverBytes(int n) {
            int n2 = 0;
            int n3 = 0;
            if (n > 0) {
                int n4 = n;
                n = n3;
                for (n2 = this.dynamicTable.length - 1; n2 >= this.nextHeaderIndex && n4 > 0; --n2) {
                    n4 -= this.dynamicTable[n2].hpackSize;
                    this.dynamicTableByteCount -= this.dynamicTable[n2].hpackSize;
                    --this.headerCount;
                    ++n;
                }
                System.arraycopy(this.dynamicTable, this.nextHeaderIndex + 1, this.dynamicTable, this.nextHeaderIndex + 1 + n, this.headerCount);
                Arrays.fill(this.dynamicTable, this.nextHeaderIndex + 1, this.nextHeaderIndex + 1 + n, null);
                this.nextHeaderIndex += n;
                n2 = n;
            }
            return n2;
        }

        private void insertIntoDynamicTable(Header header) {
            int n = header.hpackSize;
            if (n > this.maxDynamicTableByteCount) {
                this.clearDynamicTable();
                return;
            }
            this.evictToRecoverBytes(this.dynamicTableByteCount + n - this.maxDynamicTableByteCount);
            if (this.headerCount + 1 > this.dynamicTable.length) {
                Header[] arrheader = new Header[this.dynamicTable.length * 2];
                System.arraycopy(this.dynamicTable, 0, arrheader, this.dynamicTable.length, this.dynamicTable.length);
                this.nextHeaderIndex = this.dynamicTable.length - 1;
                this.dynamicTable = arrheader;
            }
            int n2 = this.nextHeaderIndex;
            this.nextHeaderIndex = n2 - 1;
            this.dynamicTable[n2] = header;
            ++this.headerCount;
            this.dynamicTableByteCount += n;
        }

        void setHeaderTableSizeSetting(int n) {
            this.headerTableSizeSetting = n;
            if (this.maxDynamicTableByteCount == (n = Math.min(n, 16384))) {
                return;
            }
            if (n < this.maxDynamicTableByteCount) {
                this.smallestHeaderTableSizeSetting = Math.min(this.smallestHeaderTableSizeSetting, n);
            }
            this.emitDynamicTableSizeUpdate = true;
            this.maxDynamicTableByteCount = n;
            this.adjustDynamicTableByteCount();
        }

        void writeByteString(ByteString byteString) throws IOException {
            if (this.useCompression && Huffman.get().encodedLength(byteString) < byteString.size()) {
                Buffer buffer = new Buffer();
                Huffman.get().encode(byteString, buffer);
                byteString = buffer.readByteString();
                this.writeInt(byteString.size(), 127, 128);
                this.out.write(byteString);
                return;
            }
            this.writeInt(byteString.size(), 127, 0);
            this.out.write(byteString);
        }

        /*
         * Unable to fully structure code
         * Enabled aggressive block sorting
         * Lifted jumps to return sites
         */
        void writeHeaders(List<Header> var1_1) throws IOException {
            if (this.emitDynamicTableSizeUpdate) {
                if (this.smallestHeaderTableSizeSetting < this.maxDynamicTableByteCount) {
                    this.writeInt(this.smallestHeaderTableSizeSetting, 31, 32);
                }
                this.emitDynamicTableSizeUpdate = false;
                this.smallestHeaderTableSizeSetting = Integer.MAX_VALUE;
                this.writeInt(this.maxDynamicTableByteCount, 31, 32);
            }
            var4_2 = 0;
            var8_3 = var1_1.size();
            while (var4_2 < var8_3) {
                block16: {
                    var10_10 = var1_1.get(var4_2);
                    var11_11 = var10_10.name.toAsciiLowercase();
                    var12_12 = var10_10.value;
                    var6_7 = -1;
                    var3_5 = -1;
                    var13_13 = Hpack.NAME_TO_FIRST_INDEX.get(var11_11);
                    var2_4 = var6_7;
                    if (var13_13 != null) {
                        var5_6 = var13_13 + 1;
                        var2_4 = var6_7;
                        var3_5 = var5_6;
                        if (var5_6 > 1) {
                            var2_4 = var6_7;
                            var3_5 = var5_6;
                            if (var5_6 < 8) {
                                if (Util.equal(Hpack.STATIC_HEADER_TABLE[var5_6 - 1].value, var12_12)) {
                                    var2_4 = var5_6;
                                    var3_5 = var5_6;
                                } else {
                                    var2_4 = var6_7;
                                    var3_5 = var5_6;
                                    if (Util.equal(Hpack.STATIC_HEADER_TABLE[var5_6].value, var12_12)) {
                                        var2_4 = var5_6 + 1;
                                        var3_5 = var5_6;
                                    }
                                }
                            }
                        }
                    }
                    var7_8 = var2_4;
                    var6_7 = var3_5;
                    if (var2_4 != -1) ** GOTO lbl48
                    var5_6 = this.nextHeaderIndex + 1;
                    var9_9 = this.dynamicTable.length;
                    do {
                        block18: {
                            block19: {
                                block17: {
                                    var7_8 = var2_4;
                                    var6_7 = var3_5;
                                    if (var5_6 >= var9_9) break block17;
                                    var6_7 = var3_5;
                                    if (!Util.equal(this.dynamicTable[var5_6].name, var11_11)) break block18;
                                    if (!Util.equal(this.dynamicTable[var5_6].value, var12_12)) break block19;
                                    var7_8 = var5_6 - this.nextHeaderIndex + Hpack.STATIC_HEADER_TABLE.length;
                                    var6_7 = var3_5;
                                }
                                if (var7_8 == -1) break;
                                this.writeInt(var7_8, 127, 128);
                                break block16;
                            }
                            var6_7 = var3_5;
                            if (var3_5 == -1) {
                                var6_7 = var5_6 - this.nextHeaderIndex + Hpack.STATIC_HEADER_TABLE.length;
                            }
                        }
                        ++var5_6;
                        var3_5 = var6_7;
                    } while (true);
                    if (var6_7 == -1) {
                        this.out.writeByte(64);
                        this.writeByteString(var11_11);
                        this.writeByteString(var12_12);
                        this.insertIntoDynamicTable(var10_10);
                    } else if (var11_11.startsWith(Header.PSEUDO_PREFIX) && !Header.TARGET_AUTHORITY.equals(var11_11)) {
                        this.writeInt(var6_7, 15, 0);
                        this.writeByteString(var12_12);
                    } else {
                        this.writeInt(var6_7, 63, 64);
                        this.writeByteString(var12_12);
                        this.insertIntoDynamicTable(var10_10);
                    }
                }
                ++var4_2;
            }
        }

        void writeInt(int n, int n2, int n3) {
            if (n < n2) {
                this.out.writeByte(n3 | n);
                return;
            }
            this.out.writeByte(n3 | n2);
            n -= n2;
            while (n >= 128) {
                this.out.writeByte(n & 0x7F | 0x80);
                n >>>= 7;
            }
            this.out.writeByte(n);
        }
    }

}

