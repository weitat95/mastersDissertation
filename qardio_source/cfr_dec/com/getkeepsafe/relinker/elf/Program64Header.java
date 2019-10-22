/*
 * Decompiled with CFR 0.147.
 */
package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import com.getkeepsafe.relinker.elf.ElfParser;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Program64Header
extends Elf.ProgramHeader {
    /*
     * Enabled aggressive block sorting
     */
    public Program64Header(ElfParser elfParser, Elf.Header header, long l) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        ByteOrder byteOrder = header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        byteBuffer.order(byteOrder);
        l = header.phoff + (long)header.phentsize * l;
        this.type = elfParser.readWord(byteBuffer, l);
        this.offset = elfParser.readLong(byteBuffer, 8L + l);
        this.vaddr = elfParser.readLong(byteBuffer, 16L + l);
        this.memsz = elfParser.readLong(byteBuffer, 40L + l);
    }
}

