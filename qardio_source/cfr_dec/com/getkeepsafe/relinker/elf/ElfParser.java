/*
 * Decompiled with CFR 0.147.
 */
package com.getkeepsafe.relinker.elf;

import com.getkeepsafe.relinker.elf.Elf;
import com.getkeepsafe.relinker.elf.Elf32Header;
import com.getkeepsafe.relinker.elf.Elf64Header;
import java.io.Closeable;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.nio.channels.FileChannel;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class ElfParser
implements Elf,
Closeable {
    private final int MAGIC;
    private final FileChannel channel;

    public ElfParser(File file) throws FileNotFoundException {
        this.MAGIC = 1179403647;
        if (file == null || !file.exists()) {
            throw new IllegalArgumentException("File is null or does not exist");
        }
        this.channel = new FileInputStream(file).getChannel();
    }

    private long offsetFromVma(Elf.Header header, long l, long l2) throws IOException {
        for (long i = 0L; i < l; ++i) {
            Elf.ProgramHeader programHeader = header.getProgramHeader(i);
            if (programHeader.type != 1L || programHeader.vaddr > l2 || l2 > programHeader.vaddr + programHeader.memsz) continue;
            return l2 - programHeader.vaddr + programHeader.offset;
        }
        throw new IllegalStateException("Could not map vma to file offset!");
    }

    @Override
    public void close() throws IOException {
        this.channel.close();
    }

    /*
     * Enabled aggressive block sorting
     */
    public Elf.Header parseHeader() throws IOException {
        this.channel.position(0L);
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        byteBuffer.order(ByteOrder.LITTLE_ENDIAN);
        if (this.readWord(byteBuffer, 0L) != 1179403647L) {
            throw new IllegalArgumentException("Invalid ELF Magic!");
        }
        short s = this.readByte(byteBuffer, 4L);
        boolean bl = this.readByte(byteBuffer, 5L) == 2;
        if (s == 1) {
            return new Elf32Header(bl, this);
        }
        if (s == 2) {
            return new Elf64Header(bl, this);
        }
        throw new IllegalStateException("Invalid class type!");
    }

    /*
     * Enabled aggressive block sorting
     */
    public List<String> parseNeededDependencies() throws IOException {
        long l;
        Elf.DynamicStructure dynamicStructure;
        this.channel.position(0L);
        ArrayList<String> arrayList = new ArrayList<String>();
        Object object = this.parseHeader();
        ByteBuffer byteBuffer = ByteBuffer.allocate(8);
        Object object2 = ((Elf.Header)object).bigEndian ? ByteOrder.BIG_ENDIAN : ByteOrder.LITTLE_ENDIAN;
        byteBuffer.order((ByteOrder)object2);
        long l2 = l = (long)((Elf.Header)object).phnum;
        if (l == 65535L) {
            l2 = object.getSectionHeader((int)0).info;
        }
        long l3 = 0L;
        long l4 = 0L;
        do {
            block12: {
                block11: {
                    l = l3;
                    if (l4 >= l2) break block11;
                    object2 = ((Elf.Header)object).getProgramHeader(l4);
                    if (((Elf.ProgramHeader)object2).type != 2L) break block12;
                    l = ((Elf.ProgramHeader)object2).offset;
                }
                if (l != 0L) break;
                return Collections.unmodifiableList(arrayList);
            }
            ++l4;
        } while (true);
        int n = 0;
        object2 = new ArrayList<String>();
        l4 = 0L;
        do {
            dynamicStructure = ((Elf.Header)object).getDynamicStructure(l, n);
            if (dynamicStructure.tag == 1L) {
                object2.add((String)((Object)Long.valueOf(dynamicStructure.val)));
                l3 = l4;
            } else {
                l3 = l4;
                if (dynamicStructure.tag == 5L) {
                    l3 = dynamicStructure.val;
                }
            }
            ++n;
            l4 = l3;
        } while (dynamicStructure.tag != 0L);
        if (l3 == 0L) {
            throw new IllegalStateException("String table offset not found!");
        }
        l2 = this.offsetFromVma((Elf.Header)object, l2, l3);
        object = object2.iterator();
        do {
            object2 = arrayList;
            if (!object.hasNext()) return object2;
            arrayList.add(this.readString(byteBuffer, (Long)object.next() + l2));
        } while (true);
    }

    protected void read(ByteBuffer byteBuffer, long l, int n) throws IOException {
        int n2;
        byteBuffer.position(0);
        byteBuffer.limit(n);
        for (long i = 0L; i < (long)n; i += (long)n2) {
            n2 = this.channel.read(byteBuffer, l + i);
            if (n2 != -1) continue;
            throw new EOFException();
        }
        byteBuffer.position(0);
    }

    protected short readByte(ByteBuffer byteBuffer, long l) throws IOException {
        this.read(byteBuffer, l, 1);
        return (short)(byteBuffer.get() & 0xFF);
    }

    protected int readHalf(ByteBuffer byteBuffer, long l) throws IOException {
        this.read(byteBuffer, l, 2);
        return byteBuffer.getShort() & 0xFFFF;
    }

    protected long readLong(ByteBuffer byteBuffer, long l) throws IOException {
        this.read(byteBuffer, l, 8);
        return byteBuffer.getLong();
    }

    protected String readString(ByteBuffer byteBuffer, long l) throws IOException {
        short s;
        StringBuilder stringBuilder = new StringBuilder();
        while ((s = this.readByte(byteBuffer, l)) != 0) {
            stringBuilder.append((char)s);
            ++l;
        }
        return stringBuilder.toString();
    }

    protected long readWord(ByteBuffer byteBuffer, long l) throws IOException {
        this.read(byteBuffer, l, 4);
        return (long)byteBuffer.getInt() & 0xFFFFFFFFL;
    }
}

