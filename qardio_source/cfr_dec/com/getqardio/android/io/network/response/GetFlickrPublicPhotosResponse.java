/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.io.network.response;

import com.getqardio.android.datamodel.FlickrPhotoMetadata;
import java.util.ArrayList;
import java.util.List;

public class GetFlickrPublicPhotosResponse {
    public int code;
    public String message;
    public List<FlickrPhotoMetadata> photos = new ArrayList<FlickrPhotoMetadata>();
    public String stat;

    public boolean isSuccessful() {
        return "ok".equals(this.stat);
    }

    public void setError(String string2) {
        this.stat = "fail";
        this.message = string2;
    }
}

