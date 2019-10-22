/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Environment
 */
package com.getqardio.android.utils.cache;

import android.os.Environment;
import com.getqardio.android.utils.Utils;
import java.io.File;
import java.util.Arrays;
import java.util.Comparator;

public class PregnancyGalleryCache {
    private final File cacheDir;
    private FilesDateComparator comparator;

    public PregnancyGalleryCache(File file) {
        this.cacheDir = file;
        this.comparator = new FilesDateComparator();
        this.ensureCacheDirExists();
    }

    public void addPhoto(String object) {
        object = new File((String)object);
        Utils.copyFile((File)object, new File(this.cacheDir, System.currentTimeMillis() + "." + Utils.getFileExtension((File)object)));
    }

    public File createNewPhotoFile() {
        return new File(Environment.getExternalStorageDirectory() + "/" + Environment.DIRECTORY_PICTURES + "/" + System.currentTimeMillis() + ".jpg");
    }

    public void ensureCacheDirExists() {
        if (!this.cacheDir.exists()) {
            this.cacheDir.mkdirs();
        }
    }

    public File getCacheDir() {
        return this.cacheDir;
    }

    public File[] getPhotoFiles() {
        File[] arrfile = this.cacheDir.listFiles();
        if (arrfile == null) {
            return new File[0];
        }
        Arrays.sort(arrfile, this.comparator);
        return arrfile;
    }

    private static class FilesDateComparator
    implements Comparator<File> {
        private FilesDateComparator() {
        }

        @Override
        public int compare(File file, File file2) {
            return (int)(file.lastModified() - file2.lastModified());
        }
    }

}

