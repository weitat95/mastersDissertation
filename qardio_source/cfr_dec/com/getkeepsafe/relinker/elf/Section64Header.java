/*
 * Decompiled with CFR 0.147.
 */
package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import com.getkeepsafe.relinker.elf.ElfParser;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Section64Header
extends Elf.SectionHeader {
    /*
     * Enabled aggressive block sorting
     */
    public Section64Header(ElfParser elfParser, Elf.Header header, int n) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        ByteOrder byteOrder = header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        byteBuffer.order(byteOrder);
        this.info = elfParser.readWord(byteBuffer, header.shoff + (long)(header.shentsize * n) + 44L);
    }
}

