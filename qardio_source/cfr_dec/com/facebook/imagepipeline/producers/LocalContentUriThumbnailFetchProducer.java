/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.database.Cursor
 *  android.graphics.Rect
 *  android.media.ExifInterface
 *  android.net.Uri
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Thumbnails
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.graphics.Rect;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import com.facebook.common.logging.FLog;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.common.ResizeOptions;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.producers.LocalFetchProducer;
import com.facebook.imagepipeline.producers.ThumbnailProducer;
import com.facebook.imagepipeline.producers.ThumbnailSizeChecker;
import com.facebook.imagepipeline.request.ImageRequest;
import com.facebook.imageutils.JfifUtil;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class LocalContentUriThumbnailFetchProducer
extends LocalFetchProducer
implements ThumbnailProducer<EncodedImage> {
    private static final Rect MICRO_THUMBNAIL_DIMENSIONS;
    private static final Rect MINI_THUMBNAIL_DIMENSIONS;
    private static final String[] PROJECTION;
    private static final Class<?> TAG;
    private static final String[] THUMBNAIL_PROJECTION;
    private final ContentResolver mContentResolver;

    static {
        TAG = LocalContentUriThumbnailFetchProducer.class;
        PROJECTION = new String[]{"_id", "_data"};
        THUMBNAIL_PROJECTION = new String[]{"_data"};
        MINI_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 512, 384);
        MICRO_THUMBNAIL_DIMENSIONS = new Rect(0, 0, 96, 96);
    }

    public LocalContentUriThumbnailFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, ContentResolver contentResolver, boolean bl) {
        super(executor, pooledByteBufferFactory, bl);
        this.mContentResolver = contentResolver;
    }

    @Nullable
    private EncodedImage getCameraImage(Uri uri, ResizeOptions object) throws IOException {
        block8: {
            block7: {
                if ((uri = this.mContentResolver.query(uri, PROJECTION, null, null, null)) == null) {
                    return null;
                }
                int n = uri.getCount();
                if (n != 0) break block7;
                uri.close();
                return null;
            }
            uri.moveToFirst();
            String string2 = uri.getString(uri.getColumnIndex("_data"));
            if (object == null) break block8;
            object = this.getThumbnail((ResizeOptions)object, uri.getInt(uri.getColumnIndex("_id")));
            if (object == null) break block8;
            ((EncodedImage)object).setRotationAngle(LocalContentUriThumbnailFetchProducer.getRotationAngle(string2));
            return object;
        }
        uri.close();
        return null;
        finally {
            uri.close();
        }
    }

    private static int getLength(String string2) {
        if (string2 == null) {
            return -1;
        }
        return (int)new File(string2).length();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static int getRotationAngle(String string2) {
        int n = 0;
        if (string2 == null) return n;
        try {
            return JfifUtil.getAutoRotateAngleFromOrientation(new ExifInterface(string2).getAttributeInt("Orientation", 1));
        }
        catch (IOException iOException) {
            FLog.e(TAG, iOException, "Unable to retrieve thumbnail rotation for %s", string2);
            return 0;
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private EncodedImage getThumbnail(ResizeOptions resizeOptions, int n) throws IOException {
        Object object;
        Object object2;
        block9: {
            block8: {
                object2 = null;
                int n2 = LocalContentUriThumbnailFetchProducer.getThumbnailKind(resizeOptions);
                if (n2 == 0) {
                    return object2;
                }
                object = null;
                try {
                    resizeOptions = MediaStore.Images.Thumbnails.queryMiniThumbnail((ContentResolver)this.mContentResolver, (long)n, (int)n2, (String[])THUMBNAIL_PROJECTION);
                    if (resizeOptions != null) break block8;
                    object = object2;
                    if (resizeOptions == null) return object;
                }
                catch (Throwable throwable) {
                    if (object == null) throw throwable;
                    object.close();
                    throw throwable;
                }
                resizeOptions.close();
                return null;
            }
            object = resizeOptions;
            resizeOptions.moveToFirst();
            object = resizeOptions;
            if (resizeOptions.getCount() <= 0) break block9;
            object = resizeOptions;
            String string2 = resizeOptions.getString(resizeOptions.getColumnIndex("_data"));
            object = resizeOptions;
            if (!new File(string2).exists()) break block9;
            object = resizeOptions;
            object = object2 = this.getEncodedImage(new FileInputStream(string2), LocalContentUriThumbnailFetchProducer.getLength(string2));
            if (resizeOptions == null) return object;
            resizeOptions.close();
            return object2;
        }
        object = object2;
        if (resizeOptions == null) return object;
        resizeOptions.close();
        return null;
    }

    private static int getThumbnailKind(ResizeOptions resizeOptions) {
        if (ThumbnailSizeChecker.isImageBigEnough(MICRO_THUMBNAIL_DIMENSIONS.width(), MICRO_THUMBNAIL_DIMENSIONS.height(), resizeOptions)) {
            return 3;
        }
        if (ThumbnailSizeChecker.isImageBigEnough(MINI_THUMBNAIL_DIMENSIONS.width(), MINI_THUMBNAIL_DIMENSIONS.height(), resizeOptions)) {
            return 1;
        }
        return 0;
    }

    @Override
    public boolean canProvideImageForSize(ResizeOptions resizeOptions) {
        return ThumbnailSizeChecker.isImageBigEnough(MINI_THUMBNAIL_DIMENSIONS.width(), MINI_THUMBNAIL_DIMENSIONS.height(), resizeOptions);
    }

    @Override
    protected EncodedImage getEncodedImage(ImageRequest object) throws IOException {
        Uri uri = ((ImageRequest)object).getSourceUri();
        if (UriUtil.isLocalCameraUri(uri) && (object = this.getCameraImage(uri, ((ImageRequest)object).getResizeOptions())) != null) {
            return object;
        }
        return null;
    }

    @Override
    protected String getProducerName() {
        return "LocalContentUriThumbnailFetchProducer";
    }
}

