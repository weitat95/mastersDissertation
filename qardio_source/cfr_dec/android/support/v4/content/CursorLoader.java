/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.support.v4.content.Loader.android.support.v4.content.Loader
 *  android.support.v4.content.Loader.android.support.v4.content.Loader$ForceLoadContentObserver
 */
package android.support.v4.content;

import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.support.v4.content.AsyncTaskLoader;
import android.support.v4.content.Loader;
import android.support.v4.content.Loader.android.support.v4.content.Loader;
import android.support.v4.os.CancellationSignal;
import java.io.FileDescriptor;
import java.io.PrintWriter;
import java.util.Arrays;

public class CursorLoader
extends AsyncTaskLoader<Cursor> {
    CancellationSignal mCancellationSignal;
    Cursor mCursor;
    final Loader.ForceLoadContentObserver mObserver = new Loader.ForceLoadContentObserver();
    String[] mProjection;
    String mSelection;
    String[] mSelectionArgs;
    String mSortOrder;
    Uri mUri;

    public CursorLoader(Context context) {
        super(context);
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    @Override
    public void cancelLoadInBackground() {
        super.cancelLoadInBackground();
        synchronized (this) {
            if (this.mCancellationSignal != null) {
                this.mCancellationSignal.cancel();
            }
            return;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    public void deliverResult(Cursor cursor) {
        if (this.isReset()) {
            if (cursor == null) return;
            {
                cursor.close();
                return;
            }
        } else {
            Cursor cursor2 = this.mCursor;
            this.mCursor = cursor;
            if (this.isStarted()) {
                super.deliverResult(cursor);
            }
            if (cursor2 == null || cursor2 == cursor || cursor2.isClosed()) return;
            {
                cursor2.close();
                return;
            }
        }
    }

    @Override
    public void dump(String string2, FileDescriptor fileDescriptor, PrintWriter printWriter, String[] arrstring) {
        super.dump(string2, fileDescriptor, printWriter, arrstring);
        printWriter.print(string2);
        printWriter.print("mUri=");
        printWriter.println((Object)this.mUri);
        printWriter.print(string2);
        printWriter.print("mProjection=");
        printWriter.println(Arrays.toString(this.mProjection));
        printWriter.print(string2);
        printWriter.print("mSelection=");
        printWriter.println(this.mSelection);
        printWriter.print(string2);
        printWriter.print("mSelectionArgs=");
        printWriter.println(Arrays.toString(this.mSelectionArgs));
        printWriter.print(string2);
        printWriter.print("mSortOrder=");
        printWriter.println(this.mSortOrder);
        printWriter.print(string2);
        printWriter.print("mCursor=");
        printWriter.println((Object)this.mCursor);
        printWriter.print(string2);
        printWriter.print("mContentChanged=");
        printWriter.println(this.mContentChanged);
    }

    /*
     * Exception decompiling
     */
    @Override
    public Cursor loadInBackground() {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [2[TRYBLOCK]], but top level block is 12[CATCHBLOCK]
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

    @Override
    public void onCanceled(Cursor cursor) {
        if (cursor != null && !cursor.isClosed()) {
            cursor.close();
        }
    }

    @Override
    protected void onReset() {
        super.onReset();
        this.onStopLoading();
        if (this.mCursor != null && !this.mCursor.isClosed()) {
            this.mCursor.close();
        }
        this.mCursor = null;
    }

    @Override
    protected void onStartLoading() {
        if (this.mCursor != null) {
            this.deliverResult(this.mCursor);
        }
        if (this.takeContentChanged() || this.mCursor == null) {
            this.forceLoad();
        }
    }

    @Override
    protected void onStopLoading() {
        this.cancelLoad();
    }
}

