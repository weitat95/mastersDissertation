/*
 * Decompiled with CFR 0.147.
 */
package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Dynamic32Structure;
import com.getkeepsafe.relinker.elf.Elf;
import com.getkeepsafe.relinker.elf.ElfParser;
import com.getkeepsafe.relinker.elf.Program32Header;
import com.getkeepsafe.relinker.elf.Section32Header;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;

public class Elf32Header
extends Elf.Header {
    private final ElfParser parser;

    /*
     * Enabled aggressive block sorting
     */
    public Elf32Header(boolean bl, ElfParser elfParser) throws IOException {
        this.bigEndian = bl;
        this.parser = elfParser;
        ByteBuffer byteBuffer = ByteBuffer.allocate(4);
        ByteOrder byteOrder = bl ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        byteBuffer.order(byteOrder);
        this.type = elfParser.readHalf(byteBuffer, 16L);
        this.phoff = elfParser.readWord(byteBuffer, 28L);
        this.shoff = elfParser.readWord(byteBuffer, 32L);
        this.phentsize = elfParser.readHalf(byteBuffer, 42L);
        this.phnum = elfParser.readHalf(byteBuffer, 44L);
        this.shentsize = elfParser.readHalf(byteBuffer, 46L);
        this.shnum = elfParser.readHalf(byteBuffer, 48L);
        this.shstrndx = elfParser.readHalf(byteBuffer, 50L);
    }

    @Override
    public Elf.DynamicStructure getDynamicStructure(long l, int n) throws IOException {
        return new Dynamic32Structure(this.parser, this, l, n);
    }

    @Override
    public Elf.ProgramHeader getProgramHeader(long l) throws IOException {
        return new Program32Header(this.parser, this, l);
    }

    @Override
    public Elf.SectionHeader getSectionHeader(int n) throws IOException {
        return new Section32Header(this.parser, this, n);
    }
}

