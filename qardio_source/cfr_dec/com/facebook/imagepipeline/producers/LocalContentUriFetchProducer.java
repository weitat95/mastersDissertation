/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.database.Cursor
 *  android.net.Uri
 *  android.provider.ContactsContract
 *  android.provider.ContactsContract$Contacts
 *  javax.annotation.Nullable
 */
package com.facebook.imagepipeline.producers;

import android.content.ContentResolver;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import com.facebook.common.util.UriUtil;
import com.facebook.imagepipeline.image.EncodedImage;
import com.facebook.imagepipeline.memory.PooledByteBufferFactory;
import com.facebook.imagepipeline.producers.LocalFetchProducer;
import com.facebook.imagepipeline.request.ImageRequest;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.Executor;
import javax.annotation.Nullable;

public class LocalContentUriFetchProducer
extends LocalFetchProducer {
    private static final String[] PROJECTION = new String[]{"_id", "_data"};
    private final ContentResolver mContentResolver;

    public LocalContentUriFetchProducer(Executor executor, PooledByteBufferFactory pooledByteBufferFactory, ContentResolver contentResolver, boolean bl) {
        super(executor, pooledByteBufferFactory, bl);
        this.mContentResolver = contentResolver;
    }

    @Nullable
    private EncodedImage getCameraImage(Uri uri) throws IOException {
        block7: {
            block6: {
                if ((uri = this.mContentResolver.query(uri, PROJECTION, null, null, null)) == null) {
                    return null;
                }
                int n = uri.getCount();
                if (n != 0) break block6;
                uri.close();
                return null;
            }
            uri.moveToFirst();
            Object object = uri.getString(uri.getColumnIndex("_data"));
            if (object == null) break block7;
            object = this.getEncodedImage(new FileInputStream((String)object), LocalContentUriFetchProducer.getLength((String)object));
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
    @Override
    protected EncodedImage getEncodedImage(ImageRequest object) throws IOException {
        Uri uri = ((ImageRequest)object).getSourceUri();
        if (UriUtil.isLocalContactUri(uri)) {
            if (uri.toString().endsWith("/photo")) {
                object = this.mContentResolver.openInputStream(uri);
                return this.getEncodedImage((InputStream)object, -1);
            }
            InputStream inputStream = ContactsContract.Contacts.openContactPhotoInputStream((ContentResolver)this.mContentResolver, (Uri)uri);
            object = inputStream;
            if (inputStream != null) return this.getEncodedImage((InputStream)object, -1);
            throw new IOException("Contact photo does not exist: " + (Object)uri);
        }
        if (!UriUtil.isLocalCameraUri(uri)) return this.getEncodedImage(this.mContentResolver.openInputStream(uri), -1);
        EncodedImage encodedImage = this.getCameraImage(uri);
        object = encodedImage;
        if (encodedImage != null) return object;
        return this.getEncodedImage(this.mContentResolver.openInputStream(uri), -1);
    }

    @Override
    protected String getProducerName() {
        return "LocalContentUriFetchProducer";
    }
}

