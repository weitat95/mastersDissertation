/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.provider.MediaStore
 *  android.provider.MediaStore$Images
 *  android.provider.MediaStore$Images$Media
 */
package com.getqardio.android.utils;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.MediaStore;
import com.getqardio.android.datamodel.FlickrPhotoMetadata;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.permission.PermissionUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

public class RandomImageGenerator {
    private List<String> imageList;
    private Random random = new Random(System.currentTimeMillis());

    /*
     * Enabled aggressive block sorting
     */
    public RandomImageGenerator(Activity activity, boolean bl, boolean bl2) {
        boolean bl3 = PermissionUtil.ExternalStorage.hasExternalStoragePermission(activity);
        if (bl) {
            this.imageList = this.getFlickrImages((Context)activity);
            if (!bl3 || !bl2) return;
            {
                this.imageList.addAll(this.getAllImagesFromDevice((Context)activity));
                return;
            }
        } else {
            if (bl3 && bl2) {
                this.imageList = this.getAllImagesFromDevice((Context)activity);
                return;
            }
            this.imageList = this.getFlickrImages((Context)activity);
            if (!bl3) return;
            {
                this.imageList.addAll(this.getAllImagesFromDevice((Context)activity));
                return;
            }
        }
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    private List<String> getAllImagesFromDevice(Context var1_1) {
        block5: {
            var3_5 = var1_1.getContentResolver().query(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, new String[]{"_id", "_data"}, null, null, "bucket_display_name");
            var1_1 = new ArrayList<E>(var3_5.getCount());
            try {
                var2_6 = var3_5.getColumnIndex("_data");
                var3_5.moveToFirst();
                while (!var3_5.isAfterLast()) {
                    var1_1.add(var3_5.getString(var2_6));
                    var3_5.moveToNext();
                }
                break block5;
            }
            catch (Throwable var1_2) {}
            ** GOTO lbl-1000
        }
        var3_5.close();
        return var1_1;
        catch (Throwable var1_4) {}
lbl-1000:
        // 2 sources
        {
            var3_5.close();
            throw var1_3;
        }
    }

    private List<String> getFlickrImages(Context object) {
        Object object2 = DataHelper.FlickrHelper.getAllFlickrPhotosMetadata((Context)object);
        object = new ArrayList(object2.size());
        object2 = object2.iterator();
        while (object2.hasNext()) {
            object.add(((FlickrPhotoMetadata)object2.next()).urlZ);
        }
        return object;
    }

    public String getRandomImagePath() {
        if (!this.imageList.isEmpty()) {
            return this.imageList.get(this.random.nextInt(this.imageList.size()));
        }
        return "";
    }
}

