/*
 * Decompiled with CFR 0.147.
 */
package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import com.getkeepsafe.relinker.elf.ElfParser;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Program32Header
extends Elf.ProgramHeader {
    /*
     * Enabled aggressive block sorting
     */
    public Program32Header(ElfParser elfParser, Elf.Header header, long l) throws IOException {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        ByteOrder byteOrder = header.bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        byteBuffer.order(byteOrder);
        l = header.phoff + (long)header.phentsize * l;
        this.type = elfParser.readWord(byteBuffer, l);
        this.offset = elfParser.readWord(byteBuffer, 4L + l);
        this.vaddr = elfParser.readWord(byteBuffer, 8L + l);
        this.memsz = elfParser.readWord(byteBuffer, 20L + l);
    }
}

