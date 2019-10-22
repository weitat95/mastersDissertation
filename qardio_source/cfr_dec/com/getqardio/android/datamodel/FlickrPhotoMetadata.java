/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.datamodel;

import com.getqardio.android.datamodel.BaseEntity;

public class FlickrPhotoMetadata
extends BaseEntity {
    public String id = "";
    public long loadDate = System.currentTimeMillis();
    public String urlZ = "";

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null) return false;
        if (this.getClass() != object.getClass()) {
            return false;
        }
        object = (FlickrPhotoMetadata)object;
        if (this.id != null) {
            if (this.id.equals(((FlickrPhotoMetadata)object).id)) return true;
            return false;
        }
        if (((FlickrPhotoMetadata)object).id == null) return true;
        return false;
    }

    public int hashCode() {
        if (this.id != null) {
            return this.id.hashCode();
        }
        return 0;
    }
}

