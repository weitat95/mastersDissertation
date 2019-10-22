/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.SharedPreferences
 *  android.content.SharedPreferences$Editor
 *  android.content.pm.ApplicationInfo
 *  android.os.Build
 *  android.os.Build$VERSION
 *  android.util.Log
 */
package android.support.multidex;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.os.Build;
import android.support.multidex.ZipUtil;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileFilter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

final class MultiDexExtractor {
    private static Method sApplyMethod;

    static {
        try {
            sApplyMethod = SharedPreferences.Editor.class.getMethod("apply", new Class[0]);
        }
        catch (NoSuchMethodException noSuchMethodException) {
            sApplyMethod = null;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private static void apply(SharedPreferences.Editor editor) {
        if (sApplyMethod != null) {
            try {
                sApplyMethod.invoke((Object)editor, new Object[0]);
                return;
            }
            catch (IllegalAccessException illegalAccessException) {
                // empty catch block
            }
            catch (InvocationTargetException invocationTargetException) {}
        }
        editor.commit();
    }

    private static void closeQuietly(Closeable closeable) {
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            Log.w((String)"MultiDex", (String)"Failed to close resource", (Throwable)iOException);
            return;
        }
    }

    /*
     * Exception decompiling
     */
    private static void extract(ZipFile var0, ZipEntry var1_4, File var2_6, String var3_7) throws IOException, FileNotFoundException {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [1[TRYBLOCK]], but top level block is 2[TRYBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static SharedPreferences getMultiDexPreferences(Context context) {
        int n;
        if (Build.VERSION.SDK_INT < 11) {
            n = 0;
            do {
                return context.getSharedPreferences("multidex.version", n);
                break;
            } while (true);
        }
        n = 4;
        return context.getSharedPreferences("multidex.version", n);
    }

    private static long getTimeStamp(File file) {
        long l;
        long l2 = l = file.lastModified();
        if (l == -1L) {
            l2 = l - 1L;
        }
        return l2;
    }

    private static long getZipCrc(File file) throws IOException {
        long l;
        long l2 = l = ZipUtil.getZipCrc(file);
        if (l == -1L) {
            l2 = l - 1L;
        }
        return l2;
    }

    private static boolean isModified(Context context, File file, long l) {
        return (context = MultiDexExtractor.getMultiDexPreferences(context)).getLong("timestamp", -1L) != MultiDexExtractor.getTimeStamp(file) || context.getLong("crc", -1L) != l;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static List<File> load(Context object, ApplicationInfo object2, File file, boolean bl) throws IOException {
        Log.i((String)"MultiDex", (String)("MultiDexExtractor.load(" + ((ApplicationInfo)object2).sourceDir + ", " + bl + ")"));
        File file2 = new File(((ApplicationInfo)object2).sourceDir);
        long l = MultiDexExtractor.getZipCrc(file2);
        if (!bl && !MultiDexExtractor.isModified((Context)object, file2, l)) {
            try {
                object = object2 = MultiDexExtractor.loadExistingExtractions((Context)object, file2, file);
            }
            catch (IOException iOException) {
                Log.w((String)"MultiDex", (String)"Failed to reload existing extracted secondary dex files, falling back to fresh extraction", (Throwable)iOException);
                List<File> list = MultiDexExtractor.performExtractions(file2, file);
                MultiDexExtractor.putStoredApkInfo((Context)object, MultiDexExtractor.getTimeStamp(file2), l, list.size() + 1);
                object = list;
            }
        } else {
            Log.i((String)"MultiDex", (String)"Detected that extraction must be performed.");
            object2 = MultiDexExtractor.performExtractions(file2, file);
            MultiDexExtractor.putStoredApkInfo((Context)object, MultiDexExtractor.getTimeStamp(file2), l, object2.size() + 1);
            object = object2;
        }
        Log.i((String)"MultiDex", (String)("load found " + object.size() + " secondary dex files"));
        return object;
    }

    private static List<File> loadExistingExtractions(Context object, File object2, File file) throws IOException {
        Log.i((String)"MultiDex", (String)"loading existing secondary dex files");
        object2 = ((File)object2).getName() + ".classes";
        int n = MultiDexExtractor.getMultiDexPreferences((Context)object).getInt("dex.number", 1);
        object = new ArrayList(n);
        for (int i = 2; i <= n; ++i) {
            File file2 = new File(file, (String)object2 + i + ".zip");
            if (file2.isFile()) {
                object.add(file2);
                if (MultiDexExtractor.verifyZipFile(file2)) continue;
                Log.i((String)"MultiDex", (String)("Invalid zip file: " + file2));
                throw new IOException("Invalid ZIP file.");
            }
            throw new IOException("Missing extracted secondary dex file '" + file2.getPath() + "'");
        }
        return object;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static void mkdirChecked(File file) throws IOException {
        file.mkdir();
        if (file.isDirectory()) return;
        File file2 = file.getParentFile();
        if (file2 == null) {
            Log.e((String)"MultiDex", (String)("Failed to create dir " + file.getPath() + ". Parent file is null."));
            do {
                throw new IOException("Failed to create cache directory " + file.getPath());
                break;
            } while (true);
        }
        Log.e((String)"MultiDex", (String)("Failed to create dir " + file.getPath() + ". parent file is a dir " + file2.isDirectory() + ", a file " + file2.isFile() + ", exists " + file2.exists() + ", readable " + file2.canRead() + ", writable " + file2.canWrite()));
        throw new IOException("Failed to create cache directory " + file.getPath());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static List<File> performExtractions(File var0, File var1_3) throws IOException {
        var9_5 = var0.getName() + ".classes";
        MultiDexExtractor.prepareDexDir(var1_3, var9_5);
        var8_6 = new ArrayList<File>();
        var10_7 = new ZipFile((File)var0);
        var2_8 = 2;
        try {
            var0 = var10_7.getEntry("classes" + 2 + ".dex");
lbl8:
            // 2 sources
            while (var0 != null) {
                var11_14 = new File(var1_3, var9_5 + var2_8 + ".zip");
                var8_6.add(var11_14);
                Log.i((String)"MultiDex", (String)("Extraction is needed for file " + var11_14));
                var3_9 = 0;
                var5_11 = false;
                while (var3_9 < 3 && !var5_11) {
                    var4_10 = var3_9 + 1;
                    MultiDexExtractor.extract(var10_7, (ZipEntry)var0, var11_14, var9_5);
                    var6_12 = MultiDexExtractor.verifyZipFile(var11_14);
                    var12_15 = new StringBuilder().append("Extraction ");
                    var7_13 = var6_12 != false ? "success" : "failed";
                    Log.i((String)"MultiDex", (String)var12_15.append(var7_13).append(" - length ").append(var11_14.getAbsolutePath()).append(": ").append(var11_14.length()).toString());
                    var5_11 = var6_12;
                    var3_9 = var4_10;
                    if (var6_12) continue;
                    var11_14.delete();
                    var5_11 = var6_12;
                    var3_9 = var4_10;
                    if (!var11_14.exists()) continue;
                    Log.w((String)"MultiDex", (String)("Failed to delete corrupted secondary dex '" + var11_14.getPath() + "'"));
                    var5_11 = var6_12;
                    var3_9 = var4_10;
                }
                if (!var5_11) {
                    throw new IOException("Could not create zip file " + var11_14.getAbsolutePath() + " for secondary dex (" + var2_8 + ")");
                }
                ** GOTO lbl49
            }
            ** GOTO lbl51
        }
        catch (Throwable var0_1) {
            try {
                var10_7.close();
            }
            catch (IOException var1_4) {
                Log.w((String)"MultiDex", (String)"Failed to close resource", (Throwable)var1_4);
                throw var0_1;
            }
            throw var0_1;
lbl49:
            // 1 sources
            var0 = var10_7.getEntry("classes" + ++var2_8 + ".dex");
            ** GOTO lbl8
lbl51:
            // 1 sources
            try {
                var10_7.close();
                return var8_6;
            }
            catch (IOException var0_2) {
                Log.w((String)"MultiDex", (String)"Failed to close resource", (Throwable)var0_2);
                return var8_6;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private static void prepareDexDir(File file, String arrfile) throws IOException {
        MultiDexExtractor.mkdirChecked(file.getParentFile());
        MultiDexExtractor.mkdirChecked(file);
        arrfile = file.listFiles(new FileFilter((String)arrfile){
            final /* synthetic */ String val$extractedFilePrefix;
            {
                this.val$extractedFilePrefix = string2;
            }

            @Override
            public boolean accept(File file) {
                return !file.getName().startsWith(this.val$extractedFilePrefix);
            }
        });
        if (arrfile == null) {
            Log.w((String)"MultiDex", (String)("Failed to list secondary dex dir content (" + file.getPath() + ")."));
            return;
        }
        int n = arrfile.length;
        int n2 = 0;
        while (n2 < n) {
            file = arrfile[n2];
            Log.i((String)"MultiDex", (String)("Trying to delete old file " + file.getPath() + " of size " + file.length()));
            if (!file.delete()) {
                Log.w((String)"MultiDex", (String)("Failed to delete old file " + file.getPath()));
            } else {
                Log.i((String)"MultiDex", (String)("Deleted old file " + file.getPath()));
            }
            ++n2;
        }
        return;
    }

    private static void putStoredApkInfo(Context context, long l, long l2, int n) {
        context = MultiDexExtractor.getMultiDexPreferences(context).edit();
        context.putLong("timestamp", l);
        context.putLong("crc", l2);
        context.putInt("dex.number", n);
        MultiDexExtractor.apply((SharedPreferences.Editor)context);
    }

    /*
     * Loose catch block
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    static boolean verifyZipFile(File file) {
        try {
            ZipFile zipFile = new ZipFile(file);
            {
                catch (IOException iOException) {
                    Log.w((String)"MultiDex", (String)("Got an IOException trying to open zip file: " + file.getAbsolutePath()), (Throwable)iOException);
                    return false;
                }
            }
            try {
                zipFile.close();
                return true;
            }
            catch (IOException iOException) {
                Log.w((String)"MultiDex", (String)("Failed to close zip file: " + file.getAbsolutePath()));
                return false;
            }
        }
        catch (ZipException zipException) {
            Log.w((String)"MultiDex", (String)("File " + file.getAbsolutePath() + " is not a valid zip file."), (Throwable)zipException);
            return false;
        }
    }

}

