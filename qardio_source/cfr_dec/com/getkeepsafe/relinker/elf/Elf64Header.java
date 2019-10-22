/*
 * Decompiled with CFR 0.147.
 */
package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Dynamic64Structure;
import com.getkeepsafe.relinker.elf.Elf;
import com.getkeepsafe.relinker.elf.ElfParser;
import com.getkeepsafe.relinker.elf.Program64Header;
import com.getkeepsafe.relinker.elf.Section64Header;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Elf64Header
extends Elf.Header {
    private final ElfParser parser;

    /*
     * Enabled aggressive block sorting
     */
    public Elf64Header(boolean bl, ElfParser elfParser) throws IOException {
        this.bigEndian = bl;
        this.parser = elfParser;
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        ByteOrder byteOrder = bl ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        byteBuffer.order(byteOrder);
        this.type = elfParser.readHalf(byteBuffer, 16L);
        this.phoff = elfParser.readLong(byteBuffer, 32L);
        this.shoff = elfParser.readLong(byteBuffer, 40L);
        this.phentsize = elfParser.readHalf(byteBuffer, 54L);
        this.phnum = elfParser.readHalf(byteBuffer, 56L);
        this.shentsize = elfParser.readHalf(byteBuffer, 58L);
        this.shnum = elfParser.readHalf(byteBuffer, 60L);
        this.shstrndx = elfParser.readHalf(byteBuffer, 62L);
    }

    @Override
    public Elf.DynamicStructure getDynamicStructure(long l, int n) throws IOException {
        return new Dynamic64Structure(this.parser, this, l, n);
    }

    @Override
    public Elf.ProgramHeader getProgramHeader(long l) throws IOException {
        return new Program64Header(this.parser, this, l);
    }

    @Override
    public Elf.SectionHeader getSectionHeader(int n) throws IOException {
        return new Section64Header(this.parser, this, n);
    }
}

