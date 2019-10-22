/*
 * Decompiled with CFR 0.147.
 */
package android.support.multidex;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.util.zip.CRC32;
import java.util.zip.ZipException;

final class ZipUtil {
    static long computeCrcOfCentralDir(RandomAccessFile randomAccessFile, CentralDirectory arrby) throws IOException {
        CRC32 cRC32 = new CRC32();
        long l = arrby.size;
        randomAccessFile.seek(arrby.offset);
        int n = (int)Math.min(16384L, l);
        arrby = new byte[16384];
        n = randomAccessFile.read(arrby, 0, n);
        do {
            block4: {
                block3: {
                    if (n == -1) break block3;
                    cRC32.update(arrby, 0, n);
                    if ((l -= (long)n) != 0L) break block4;
                }
                return cRC32.getValue();
            }
            n = randomAccessFile.read(arrby, 0, (int)Math.min(16384L, l));
        } while (true);
    }

    static CentralDirectory findCentralDirectory(RandomAccessFile randomAccessFile) throws IOException, ZipException {
        long l;
        long l2 = randomAccessFile.length() - 22L;
        if (l2 < 0L) {
            throw new ZipException("File too short to be a zip file: " + randomAccessFile.length());
        }
        long l3 = l = l2 - 65536L;
        if (l < 0L) {
            l3 = 0L;
        }
        int n = Integer.reverseBytes(101010256);
        do {
            randomAccessFile.seek(l2);
            if (randomAccessFile.readInt() == n) {
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                randomAccessFile.skipBytes(2);
                CentralDirectory centralDirectory = new CentralDirectory();
                centralDirectory.size = (long)Integer.reverseBytes(randomAccessFile.readInt()) & 0xFFFFFFFFL;
                centralDirectory.offset = (long)Integer.reverseBytes(randomAccessFile.readInt()) & 0xFFFFFFFFL;
                return centralDirectory;
            }
            l2 = l = l2 - 1L;
        } while (l >= l3);
        throw new ZipException("End Of Central Directory signature not found");
    }

    static long getZipCrc(File object) throws IOException {
        object = new RandomAccessFile((File)object, "r");
        try {
            long l = ZipUtil.computeCrcOfCentralDir((RandomAccessFile)object, ZipUtil.findCentralDirectory((RandomAccessFile)object));
            return l;
        }
        finally {
            ((RandomAccessFile)object).close();
        }
    }

    static class CentralDirectory {
        long offset;
        long size;

        CentralDirectory() {
        }
    }

}

