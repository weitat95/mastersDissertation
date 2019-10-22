/*
 * Decompiled with CFR 0.147.
 */
package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import com.getkeepsafe.relinker.elf.ElfParser;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Dynamic64Structure
extends Elf.DynamicStructure {
    /*
     * Enabled aggressive block sorting
     */
    public Dynamic64Structure(ElfParser elfParser, Elf.Header object, long l, int n) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        object = ((Elf.Header)object).bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        byteBuffer.order((ByteOrder)object);
        this.tag = elfParser.readLong(byteBuffer, l += (long)(n * 16));
        this.val = elfParser.readLong(byteBuffer, 8L + l);
    }
}

