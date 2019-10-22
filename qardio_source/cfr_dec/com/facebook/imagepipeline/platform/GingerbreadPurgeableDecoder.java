/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Bitmap
 *  android.graphics.Bitmap$Config
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.graphics.Rect
 *  android.os.MemoryFile
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.platform;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.MemoryFile;
import com.facebook.common.internal.ByteStreams;
import com.facebook.common.internal.Closeables;
import com.facebook.common.internal.Preconditions;
import com.facebook.common.internal.Throwables;
import com.facebook.common.references.CloseableReference;
import com.facebook.common.streams.LimitedInputStream;
import com.facebook.common.webp.WebpBitmapFactory;
import com.facebook.common.webp.WebpSupportStatus;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBuffer;
import com.facebook.imagepipeline.memory.PooledByteBufferInputStream;
import com.facebook.imagepipeline.platform.DalvikPurgeableDecoder;
import java.io.Closeable;
import java.io.FileDescriptor;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Method;
import javax.annotation.Nullable;

public class GingerbreadPurgeableDecoder
extends DalvikPurgeableDecoder {
    private static Method sGetFileDescriptorMethod;
    private final boolean mWebpSupportEnabled;

    public GingerbreadPurgeableDecoder(boolean bl, WebpBitmapFactory.WebpErrorLogger webpErrorLogger) {
        if (WebpSupportStatus.sWebpBitmapFactory != null) {
            WebpSupportStatus.sWebpBitmapFactory.setWebpErrorLogger(webpErrorLogger);
        }
        this.mWebpSupportEnabled = bl;
    }

    /*
     * Loose catch block
     * WARNING - void declaration
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private static MemoryFile copyToMemoryFile(CloseableReference<PooledByteBuffer> closeableReference, int n, @Nullable byte[] object) throws IOException {
        PooledByteBufferInputStream pooledByteBufferInputStream;
        MemoryFile memoryFile;
        OutputStream outputStream;
        Object object2;
        Object var7_10;
        LimitedInputStream limitedInputStream;
        block6: {
            int n2 = object == null ? 0 : ((Object)object).length;
            memoryFile = new MemoryFile(null, n + n2);
            memoryFile.allowPurging(false);
            limitedInputStream = null;
            var7_10 = null;
            object2 = null;
            Object var6_15 = null;
            pooledByteBufferInputStream = new PooledByteBufferInputStream(closeableReference.get());
            limitedInputStream = new LimitedInputStream(pooledByteBufferInputStream, n);
            {
                catch (Throwable throwable) {
                    PooledByteBufferInputStream pooledByteBufferInputStream2 = pooledByteBufferInputStream;
                    Object var5_23 = var7_10;
                    break block7;
                }
            }
            outputStream = memoryFile.getOutputStream();
            object2 = outputStream;
            ByteStreams.copy(limitedInputStream, outputStream);
            if (object == null) break block6;
            object2 = outputStream;
            memoryFile.writeBytes((byte[])object, 0, n, ((Object)object).length);
        }
        CloseableReference.closeSafely(closeableReference);
        Closeables.closeQuietly(pooledByteBufferInputStream);
        Closeables.closeQuietly(limitedInputStream);
        Closeables.close(outputStream, true);
        return memoryFile;
        catch (Throwable throwable) {
            void var5_22;
            void var6_17;
            void var2_4;
            block7: {
                LimitedInputStream limitedInputStream2 = limitedInputStream;
                Object var5_21 = var7_10;
                break block7;
                catch (Throwable throwable2) {
                    LimitedInputStream limitedInputStream3 = limitedInputStream;
                    PooledByteBufferInputStream pooledByteBufferInputStream3 = pooledByteBufferInputStream;
                    LimitedInputStream limitedInputStream4 = limitedInputStream3;
                    OutputStream outputStream2 = object2;
                    object2 = throwable2;
                }
            }
            CloseableReference.closeSafely(closeableReference);
            Closeables.closeQuietly((InputStream)var2_4);
            Closeables.closeQuietly((InputStream)var5_22);
            Closeables.close((Closeable)var6_17, true);
            throw object2;
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Method getFileDescriptorMethod() {
        synchronized (this) {
            Method method = sGetFileDescriptorMethod;
            if (method != null) return sGetFileDescriptorMethod;
            try {
                sGetFileDescriptorMethod = MemoryFile.class.getDeclaredMethod("getFileDescriptor", new Class[0]);
                return sGetFileDescriptorMethod;
            }
            catch (Exception exception) {
                throw Throwables.propagate(exception);
            }
        }
    }

    private FileDescriptor getMemoryFileDescriptor(MemoryFile object) {
        try {
            object = (FileDescriptor)this.getFileDescriptorMethod().invoke(object, new Object[0]);
            return object;
        }
        catch (Exception exception) {
            throw Throwables.propagate(exception);
        }
    }

    @Override
    protected Bitmap decodeByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> closeableReference, BitmapFactory.Options options) {
        return this.decodeFileDescriptorAsPurgeable(closeableReference, closeableReference.get().size(), null, options);
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    protected Bitmap decodeFileDescriptorAsPurgeable(CloseableReference<PooledByteBuffer> var1_1, int var2_4, byte[] var3_5, BitmapFactory.Options var4_6) {
        block8: {
            var6_7 = null;
            var5_8 = null;
            try {
                var5_8 = var1_1 = GingerbreadPurgeableDecoder.copyToMemoryFile((CloseableReference<PooledByteBuffer>)var1_1, var2_4, (byte[])var3_5 /* !! */ );
                var6_7 = var1_1;
                var3_5 /* !! */  = this.getMemoryFileDescriptor(var1_1);
                var5_8 = var1_1;
                var6_7 = var1_1;
                if (!this.mWebpSupportEnabled) break block8;
                var5_8 = var1_1;
                var6_7 = var1_1;
                var3_5 /* !! */  = WebpSupportStatus.sWebpBitmapFactory.decodeFileDescriptor((FileDescriptor)var3_5 /* !! */ , null, var4_6);
            }
            catch (IOException var1_2) {
                var6_7 = var5_8;
                throw Throwables.propagate(var1_2);
            }
            catch (Throwable var1_3) {
                if (var6_7 != null) {
                    var6_7.close();
                }
                throw var1_3;
            }
lbl21:
            // 2 sources
            do {
                var5_8 = var1_1;
                var6_7 = var1_1;
                var3_5 /* !! */  = Preconditions.checkNotNull(var3_5 /* !! */ , "BitmapFactory returned null");
                if (var1_1 != null) {
                    var1_1.close();
                }
                return var3_5 /* !! */ ;
                break;
            } while (true);
        }
        var5_8 = var1_1;
        var6_7 = var1_1;
        {
            var3_5 /* !! */  = BitmapFactory.decodeFileDescriptor((FileDescriptor)var3_5 /* !! */ , null, (BitmapFactory.Options)var4_6);
            ** continue;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    protected Bitmap decodeJPEGByteArrayAsPurgeable(CloseableReference<PooledByteBuffer> closeableReference, int n, BitmapFactory.Options options) {
        byte[] arrby;
        if (GingerbreadPurgeableDecoder.endsWithEOI(closeableReference, n)) {
            arrby = null;
            do {
                return this.decodeFileDescriptorAsPurgeable(closeableReference, n, arrby, options);
                break;
            } while (true);
        }
        arrby = EOI;
        return this.decodeFileDescriptorAsPurgeable(closeableReference, n, arrby, options);
    }
}

