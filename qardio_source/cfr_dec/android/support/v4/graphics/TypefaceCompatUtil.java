/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.net.Uri
 *  android.os.CancellationSignal
 *  android.os.Process
 *  android.util.Log
 */
package android.support.v4.graphics;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.CancellationSignal;
import android.os.Process;
import android.util.Log;
import java.io.Closeable;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;

public class TypefaceCompatUtil {
    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public static void closeQuietly(Closeable closeable) {
        if (closeable == null) return;
        try {
            closeable.close();
            return;
        }
        catch (IOException iOException) {
            return;
        }
    }

    public static ByteBuffer copyToDirectBuffer(Context object, Resources object2, int n) {
        block5: {
            if ((object = TypefaceCompatUtil.getTempFile((Context)object)) == null) {
                return null;
            }
            boolean bl = TypefaceCompatUtil.copyToFile((File)object, object2, n);
            if (bl) break block5;
            ((File)object).delete();
            return null;
        }
        object2 = TypefaceCompatUtil.mmap((File)object);
        return object2;
        finally {
            ((File)object).delete();
        }
    }

    public static boolean copyToFile(File file, Resources object, int n) {
        Object object2 = null;
        try {
            object2 = object = object.openRawResource(n);
        }
        catch (Throwable throwable) {
            TypefaceCompatUtil.closeQuietly(object2);
            throw throwable;
        }
        boolean bl = TypefaceCompatUtil.copyToFile(file, (InputStream)object);
        TypefaceCompatUtil.closeQuietly((Closeable)object);
        return bl;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public static boolean copyToFile(File var0, InputStream var1_2) {
        block9: {
            var3_7 /* !! */  = null;
            var4_8 = null;
            var0 = new FileOutputStream((File)var0, false);
            try {
                var3_7 /* !! */  = new byte[1024];
                while ((var2_9 = var1_2.read(var3_7 /* !! */ )) != -1) {
                    var0.write(var3_7 /* !! */ , 0, var2_9);
                }
                break block9;
            }
            catch (IOException var1_3) {}
            ** GOTO lbl-1000
        }
        TypefaceCompatUtil.closeQuietly((Closeable)var0);
        return true;
        catch (Throwable var1_5) {
            var3_7 /* !! */  = var0;
            var0 = var1_5;
            ** GOTO lbl-1000
        }
lbl-1000:
        // 2 sources
        {
            do {
                var3_7 /* !! */  = var0;
                try {
                    Log.e((String)"TypefaceCompatUtil", (String)("Error copying resource contents to temp file: " + var1_4.getMessage()));
                }
                catch (Throwable var0_1) lbl-1000:
                // 2 sources
                {
                    TypefaceCompatUtil.closeQuietly(var3_7 /* !! */ );
                    throw var0;
                }
                TypefaceCompatUtil.closeQuietly((Closeable)var0);
                return false;
                break;
            } while (true);
        }
        catch (IOException var1_6) {
            var0 = var4_8;
            ** continue;
        }
    }

    public static File getTempFile(Context context) {
        String string2 = ".font" + Process.myPid() + "-" + Process.myTid() + "-";
        for (int i = 0; i < 100; ++i) {
            File file = new File(context.getCacheDir(), string2 + i);
            try {
                boolean bl = file.createNewFile();
                if (!bl) continue;
                return file;
            }
            catch (IOException iOException) {
                // empty catch block
            }
        }
        return null;
    }

    /*
     * Exception decompiling
     */
    public static ByteBuffer mmap(Context var0, CancellationSignal var1_6, Uri var2_12) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 8 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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
     * Exception decompiling
     */
    private static ByteBuffer mmap(File var0) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Started 2 blocks at once
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.getStartingBlocks(Op04StructuredStatement.java:404)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:482)
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
}

