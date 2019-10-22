/*
 * Decompiled with CFR 0.147.
 */
package com.getkeepsafe.relinker.elf;

import java.io.IOException;

public interface Elf {

    public static abstract class DynamicStructure {
        public long tag;
        public long val;
    }

    public static abstract class Header {
        public boolean bigEndian;
        public int phentsize;
        public int phnum;
        public long phoff;
        public int shentsize;
        public int shnum;
        public long shoff;
        public int shstrndx;
        public int type;

        public abstract DynamicStructure getDynamicStructure(long var1, int var3) throws IOException;

        public abstract ProgramHeader getProgramHeader(long var1) throws IOException;

        public abstract SectionHeader getSectionHeader(int var1) throws IOException;
    }

    public static abstract class ProgramHeader {
        public long memsz;
        public long offset;
        public long type;
        public long vaddr;
    }

    public static abstract class SectionHeader {
        public long info;
    }

}

