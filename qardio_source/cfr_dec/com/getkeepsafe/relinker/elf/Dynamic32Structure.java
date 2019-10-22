/*
 * Decompiled with CFR 0.147.
 */
package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import com.getkeepsafe.relinker.elf.ElfParser;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Dynamic32Structure
extends Elf.DynamicStructure {
    /*
     * Enabled aggressive block sorting
     */
    public Dynamic32Structure(ElfParser elfParser, Elf.Header object, long l, int n) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        object = ((Elf.Header)object).bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        byteBuffer.order((ByteOrder)object);
        this.tag = elfParser.readWord(byteBuffer, l += (long)(n * 8));
        this.val = elfParser.readWord(byteBuffer, 4L + l);
    }
}

