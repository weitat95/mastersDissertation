/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.decoder;

import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.references.ResourceReleaser;
import com.facebook.common.util.StreamUtil;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.ByteArrayPool;
import com.facebook.imagepipeline.memory.PooledByteArrayBufferedInputStream;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;

public class ProgressiveJpegParser {
    private int mBestScanEndOffset;
    private int mBestScanNumber;
    private final ByteArrayPool mByteArrayPool;
    private int mBytesParsed;
    private int mLastByteRead;
    private int mNextFullScanNumber;
    private int mParserState;

    public ProgressiveJpegParser(ByteArrayPool byteArrayPool) {
        this.mByteArrayPool = Preconditions.checkNotNull(byteArrayPool);
        this.mBytesParsed = 0;
        this.mLastByteRead = 0;
        this.mNextFullScanNumber = 0;
        this.mBestScanEndOffset = 0;
        this.mBestScanNumber = 0;
        this.mParserState = 0;
    }

    /*
     * Exception decompiling
     */
    private boolean doParseMoreData(InputStream var1_1) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: First case is not immediately after switch.
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.examineSwitchContiguity(SwitchReplacer.java:358)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.op3rewriters.SwitchReplacer.replaceRawSwitches(SwitchReplacer.java:61)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:430)
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
    private static boolean doesMarkerStartSegment(int n) {
        boolean bl = true;
        if (n == 1) {
            return false;
        }
        if (n >= 208) {
            if (n <= 215) return false;
        }
        if (n == 217) return false;
        if (n == 216) return false;
        return bl;
    }

    private void newScanOrImageEndFound(int n) {
        if (this.mNextFullScanNumber > 0) {
            this.mBestScanEndOffset = n;
        }
        n = this.mNextFullScanNumber;
        this.mNextFullScanNumber = n + 1;
        this.mBestScanNumber = n;
    }

    public int getBestScanEndOffset() {
        return this.mBestScanEndOffset;
    }

    public int getBestScanNumber() {
        return this.mBestScanNumber;
    }

    public boolean parseMoreData(EncodedImage closeable) {
        if (this.mParserState == 6) {
            return false;
        }
        if (closeable.getSize() <= this.mBytesParsed) {
            return false;
        }
        closeable = new PooledByteArrayBufferedInputStream(closeable.getInputStream(), (byte[])this.mByteArrayPool.get(16384), this.mByteArrayPool);
        try {
            StreamUtil.skip((InputStream)closeable, this.mBytesParsed);
            boolean bl = this.doParseMoreData((InputStream)closeable);
            return bl;
        }
        catch (IOException iOException) {
            Throwables.propagate(iOException);
            return false;
        }
        finally {
            Closeables.closeQuietly((InputStream)closeable);
        }
    }
}

