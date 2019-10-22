/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.io;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import okio.Okio;
import okio.Sink;
import okio.Source;

public interface FileSystem {
    public static final FileSystem SYSTEM = new FileSystem(){

        @Override
        public Sink appendingSink(File file) throws FileNotFoundException {
            try {
                Sink sink = Okio.appendingSink(file);
                return sink;
            }
            catch (FileNotFoundException fileNotFoundException) {
                file.getParentFile().mkdirs();
                return Okio.appendingSink(file);
            }
        }

        @Override
        public void delete(File file) throws IOException {
            if (!file.delete() && file.exists()) {
                throw new IOException("failed to delete " + file);
            }
        }

        @Override
        public void deleteContents(File file2) throws IOException {
            File[] arrfile = file2.listFiles();
            if (arrfile == null) {
                throw new IOException("not a readable directory: " + file2);
            }
            for (File file2 : arrfile) {
                if (file2.isDirectory()) {
                    this.deleteContents(file2);
                }
                if (file2.delete()) continue;
                throw new IOException("failed to delete " + file2);
            }
        }

        @Override
        public boolean exists(File file) {
            return file.exists();
        }

        @Override
        public void rename(File file, File file2) throws IOException {
            this.delete(file2);
            if (!file.renameTo(file2)) {
                throw new IOException("failed to rename " + file + " to " + file2);
            }
        }

        @Override
        public Sink sink(File file) throws FileNotFoundException {
            try {
                Sink sink = Okio.sink(file);
                return sink;
            }
            catch (FileNotFoundException fileNotFoundException) {
                file.getParentFile().mkdirs();
                return Okio.sink(file);
            }
        }

        @Override
        public long size(File file) {
            return file.length();
        }

        @Override
        public Source source(File file) throws FileNotFoundException {
            return Okio.source(file);
        }
    };

    public Sink appendingSink(File var1) throws FileNotFoundException;

    public void delete(File var1) throws IOException;

    public void deleteContents(File var1) throws IOException;

    public boolean exists(File var1);

    public void rename(File var1, File var2) throws IOException;

    public Sink sink(File var1) throws FileNotFoundException;

    public long size(File var1);

    public Source source(File var1) throws FileNotFoundException;

}

