/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.Intent
 *  android.net.Uri
 *  android.os.Handler
 *  android.os.Looper
 */
package com.getqardio.android.io.network.request;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Handler;
import android.os.Looper;
import com.bumptech.glide.DrawableTypeRequest;
import com.bumptech.glide.GenericRequestBuilder;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.Target;
import com.getqardio.android.datamodel.FlickrPhotoMetadata;
import com.getqardio.android.io.network.AsyncReceiverHandler;
import com.getqardio.android.io.network.JSONParser;
import com.getqardio.android.io.network.NetworkContract;
import com.getqardio.android.io.network.NetworkRequestHelper;
import com.getqardio.android.io.network.request.FlickrRequestHandler$$Lambda$1;
import com.getqardio.android.io.network.request.RequestHandler;
import com.getqardio.android.io.network.response.GetFlickrPublicPhotosResponse;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.ui.BasicNameValuePair;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;
import java.util.Random;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class FlickrRequestHandler
extends RequestHandler {
    private static final long UPDATE_PERIOD = TimeUnit.DAYS.toMillis(7L);
    private Handler uiThreadHandler = new Handler(Looper.getMainLooper());

    public static Intent createSyncFlickrIntent(Context context, long l) {
        return AsyncReceiverHandler.createIntent(context, 15, l);
    }

    private void deleteFlickrPhoto(Context context, FlickrPhotoMetadata flickrPhotoMetadata) {
        DataHelper.FlickrHelper.removeFlickrPhotosMetadata(context, flickrPhotoMetadata);
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    private List<Integer> generateRandomIndexes(int var1_1, int var2_2) {
        var5_3 = new ArrayList<Integer>(var1_1);
        if (var2_2 == 0) {
            return var5_3;
        }
        var3_4 = var1_1;
        if (var2_2 < var1_1) {
            var3_4 = var2_2;
        }
        if (var2_2 == var3_4) {
            var1_1 = 0;
            while (var1_1 < var3_4) {
                var5_3.add(var1_1, var1_1);
                ++var1_1;
            }
            return var5_3;
        }
        var6_5 = new Random(System.currentTimeMillis());
        var1_1 = 0;
        do lbl-1000:
        // 3 sources
        {
            if (!var5_3.contains(var4_6 = var6_5.nextInt(var2_2))) {
                var5_3.add(var4_6);
            }
            var1_1 = var4_6 = var1_1 + 1;
            if (var5_3.size() < var3_4) ** GOTO lbl-1000
            var1_1 = var4_6;
        } while (var4_6 >= 100);
        return var5_3;
    }

    private GetFlickrPublicPhotosResponse getFlickrPublicPhotos() {
        Object object = new ArrayList<BasicNameValuePair>(7);
        object.add(new BasicNameValuePair("method", "flickr.people.getPublicPhotos"));
        object.add(new BasicNameValuePair("api_key", "01fd241d1831bfa55348641360e96964"));
        object.add(new BasicNameValuePair("user_id", "125918859@N07"));
        object.add(new BasicNameValuePair("extras", "url_z"));
        object.add(new BasicNameValuePair("per_page", Integer.toString(500)));
        object.add(new BasicNameValuePair("format", "json"));
        object.add(new BasicNameValuePair("nojsoncallback", Integer.toString(1)));
        object = NetworkRequestHelper.request(NetworkRequestHelper.Method.GET, NetworkContract.Flickr.URI, object);
        if (((NetworkRequestHelper.HttpResponse)object).isSuccessful()) {
            return JSONParser.parseFlickrPublicPhotos(((NetworkRequestHelper.HttpResponse)object).getResponseBody());
        }
        object = new GetFlickrPublicPhotosResponse();
        ((GetFlickrPublicPhotosResponse)object).setError("Network error");
        return object;
    }

    static /* synthetic */ void lambda$loadFlickrPhoto$0(Context context, FlickrPhotoMetadata flickrPhotoMetadata) {
        Glide.with(context).load(flickrPhotoMetadata.urlZ).skipMemoryCache(true).preload();
    }

    private void loadFlickrPhoto(Context context, FlickrPhotoMetadata flickrPhotoMetadata) {
        Timber.d("loadFlickrPhoto", new Object[0]);
        this.uiThreadHandler.post(FlickrRequestHandler$$Lambda$1.lambdaFactory$(context, flickrPhotoMetadata));
        DataHelper.FlickrHelper.saveFlickrPhotosMetadata(context, flickrPhotoMetadata);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private boolean needUpdate(List<FlickrPhotoMetadata> object) {
        if (object.isEmpty()) {
            return true;
        }
        long l = ((FlickrPhotoMetadata)object.get((int)0)).loadDate;
        object = Calendar.getInstance();
        ((Calendar)object).set(1, 2017);
        ((Calendar)object).set(2, 6);
        ((Calendar)object).set(5, 27);
        ((Calendar)object).set(10, 0);
        ((Calendar)object).set(12, 0);
        ((Calendar)object).setTimeZone(TimeZone.getTimeZone("GMT"));
        if (l < ((Calendar)object).getTimeInMillis()) return true;
        if (System.currentTimeMillis() - l < UPDATE_PERIOD) return false;
        return true;
    }

    private RequestHandler.ProcessResult syncFlickr(Context context) {
        block7: {
            List<FlickrPhotoMetadata> list = DataHelper.FlickrHelper.getAllFlickrPhotosMetadata(context);
            if (!this.needUpdate(list)) break block7;
            list = list.iterator();
            while (list.hasNext()) {
                this.deleteFlickrPhoto(context, (FlickrPhotoMetadata)list.next());
            }
            list = this.getFlickrPublicPhotos();
            if (((GetFlickrPublicPhotosResponse)((Object)list)).isSuccessful()) {
                list = ((GetFlickrPublicPhotosResponse)list).photos;
                if (list.isEmpty()) {
                    return RequestHandler.ProcessResult.SUCCESS;
                }
                if (list.size() > 0 && list.size() <= 10) {
                    list = list.iterator();
                    while (list.hasNext()) {
                        this.loadFlickrPhoto(context, (FlickrPhotoMetadata)list.next());
                    }
                } else {
                    Iterator<Integer> iterator = this.generateRandomIndexes(10, list.size()).iterator();
                    while (iterator.hasNext()) {
                        this.loadFlickrPhoto(context, (FlickrPhotoMetadata)list.get(iterator.next()));
                    }
                }
            }
        }
        return RequestHandler.ProcessResult.SUCCESS;
    }

    @Override
    public RequestHandler.ProcessResult processIntent(Context context, Intent intent, long l, String string2) {
        return this.syncFlickr(context);
    }
}

