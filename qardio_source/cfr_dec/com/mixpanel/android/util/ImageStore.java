/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.graphics.Bitmap
 *  android.graphics.BitmapFactory
 *  android.graphics.BitmapFactory$Options
 *  android.util.Base64
 */
package com.mixpanel.android.util;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.util.Base64;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.util.HttpService;
import com.mixpanel.android.util.MPLog;
import com.mixpanel.android.util.RemoteService;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Map;
import javax.net.ssl.SSLSocketFactory;

public class ImageStore {
    private static LruCache<String, Bitmap> sMemoryCache;
    private final MPConfig mConfig;
    private final MessageDigest mDigest;
    private final File mDirectory;
    private final RemoteService mPoster;

    public ImageStore(Context context, String string2) {
        this(context, "MixpanelAPI.Images." + string2, new HttpService());
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public ImageStore(Context object, String string2, RemoteService remoteService) {
        this.mDirectory = object.getDir(string2, 0);
        this.mPoster = remoteService;
        this.mConfig = MPConfig.getInstance(object);
        try {
            object = MessageDigest.getInstance("SHA1");
        }
        catch (NoSuchAlgorithmException noSuchAlgorithmException) {
            MPLog.w("MixpanelAPI.ImageStore", "Images won't be stored because this platform doesn't supply a SHA1 hash function");
            object = null;
        }
        this.mDigest = object;
        if (sMemoryCache == null) {
            synchronized (ImageStore.class) {
                if (sMemoryCache == null) {
                    sMemoryCache = new LruCache<String, Bitmap>((int)(Runtime.getRuntime().maxMemory() / 1024L) / this.mConfig.getImageCacheMaxMemoryFactor()){

                        @Override
                        protected int sizeOf(String string2, Bitmap bitmap) {
                            return bitmap.getRowBytes() * bitmap.getHeight() / 1024;
                        }
                    };
                }
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void addBitmapToMemoryCache(String string2, Bitmap bitmap) {
        if (ImageStore.getBitmapFromMemCache(string2) == null) {
            LruCache<String, Bitmap> lruCache = sMemoryCache;
            synchronized (lruCache) {
                sMemoryCache.put(string2, bitmap);
                return;
            }
        }
    }

    private static Bitmap decodeImage(File file) throws CantGetImageException {
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile((String)file.getAbsolutePath(), (BitmapFactory.Options)options);
        if ((float)options.outHeight * (float)options.outWidth > ImageStore.getAvailableMemory()) {
            throw new CantGetImageException("Do not have enough memory for the image");
        }
        options = BitmapFactory.decodeFile((String)file.getAbsolutePath());
        if (options == null) {
            file.delete();
            throw new CantGetImageException("Bitmap on disk can't be opened or was corrupt");
        }
        return options;
    }

    private static float getAvailableMemory() {
        Runtime runtime = Runtime.getRuntime();
        float f = runtime.totalMemory() - runtime.freeMemory();
        return (float)runtime.maxMemory() - f;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static Bitmap getBitmapFromMemCache(String string2) {
        LruCache<String, Bitmap> lruCache = sMemoryCache;
        synchronized (lruCache) {
            return sMemoryCache.get(string2);
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static void removeBitmapFromMemCache(String string2) {
        LruCache<String, Bitmap> lruCache = sMemoryCache;
        synchronized (lruCache) {
            sMemoryCache.remove(string2);
            return;
        }
    }

    private File storedFile(String object) {
        if (this.mDigest == null) {
            return null;
        }
        object = this.mDigest.digest(object.getBytes());
        object = "MP_IMG_" + Base64.encodeToString((byte[])object, (int)10);
        return new File(this.mDirectory, (String)object);
    }

    public void deleteStorage(String string2) {
        File file = this.storedFile(string2);
        if (file != null) {
            file.delete();
            ImageStore.removeBitmapFromMemCache(string2);
        }
    }

    public Bitmap getImage(String string2) throws CantGetImageException {
        Bitmap bitmap;
        Bitmap bitmap2 = bitmap = ImageStore.getBitmapFromMemCache(string2);
        if (bitmap == null) {
            bitmap2 = ImageStore.decodeImage(this.getImageFile(string2));
            ImageStore.addBitmapToMemoryCache(string2, bitmap2);
        }
        return bitmap2;
    }

    /*
     * WARNING - Removed back jump from a try to a catch block - possible behaviour change.
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public File getImageFile(String var1_1) throws CantGetImageException {
        var5_6 = this.storedFile((String)var1_1);
        if (var5_6 != null) {
            if (var5_6.exists() != false) return var5_6;
        }
        var2_7 = this.mConfig.getSSLSocketFactory();
        var6_11 = this.mPoster.performRequest((String)var1_1, null, (SSLSocketFactory)var2_7);
        if (var6_11 == null) return var5_6;
        if (var5_6 == null) return var5_6;
        if (var6_11.length >= 10000000) return var5_6;
        var1_1 = null;
        var4_12 = null;
        var3_13 = null;
        var2_7 = new FileOutputStream(var5_6);
        var2_7.write(var6_11);
        if (var2_7 == null) return var5_6;
        try {
            var2_7.close();
            return var5_6;
        }
        catch (IOException var1_4) {
            MPLog.w("MixpanelAPI.ImageStore", "Problem closing output file", var1_4);
            return var5_6;
        }
        catch (IOException var1_2) {
            throw new CantGetImageException("Can't download bitmap", var1_2);
        }
        catch (RemoteService.ServiceUnavailableException var1_3) {
            throw new CantGetImageException("Couldn't download image due to service availability", var1_3);
        }
        catch (FileNotFoundException var2_8) {
            block18: {
                var1_1 = var3_13;
                break block18;
                catch (Throwable var3_14) {
                    var1_1 = var2_7;
                    var2_7 = var3_14;
                    ** GOTO lbl-1000
                }
                catch (IOException var3_15) {
                    var1_1 = var2_7;
                    var2_7 = var3_15;
                    throw new CantGetImageException("Can't store bitmap", (Throwable)var2_7);
                }
                catch (FileNotFoundException var3_16) {
                    var1_1 = var2_7;
                    var2_7 = var3_16;
                }
            }
            throw new CantGetImageException("It appears that ImageStore is misconfigured, or disk storage is unavailable- can't write to bitmap directory", (Throwable)var2_7);
            {
                catch (Throwable var2_9) lbl-1000:
                // 2 sources
                {
                    if (var1_1 == null) throw var2_7;
                    try {
                        var1_1.close();
                    }
                    catch (IOException var1_5) {
                        MPLog.w("MixpanelAPI.ImageStore", "Problem closing output file", var1_5);
                        throw var2_7;
                    }
                    throw var2_7;
                    catch (IOException var2_10) {}
                    var1_1 = var4_12;
                    {
                        throw new CantGetImageException("Can't store bitmap", (Throwable)var2_7);
                    }
                }
            }
        }
    }

    public static class CantGetImageException
    extends Exception {
        public CantGetImageException(String string2) {
            super(string2);
        }

        public CantGetImageException(String string2, Throwable throwable) {
            super(string2, throwable);
        }
    }

}

