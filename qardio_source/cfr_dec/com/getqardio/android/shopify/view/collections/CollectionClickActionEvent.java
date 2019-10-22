/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.os.Bundle
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$Creator
 */
package com.getqardio.android.shopify.view.collections;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;
import com.getqardio.android.shopify.domain.model.Collection;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.ScreenActionEvent;

public final class CollectionClickActionEvent
extends ScreenActionEvent
implements Parcelable {
    public static final String ACTION;
    public static final Parcelable.Creator<CollectionClickActionEvent> CREATOR;
    private static final String EXTRAS_ID = "collection_id";
    private static final String EXTRAS_IMAGE_URL = "collection_image_url";
    private static final String EXTRAS_TITLE = "collection_title";

    static {
        CREATOR = new Parcelable.Creator<CollectionClickActionEvent>(){

            public CollectionClickActionEvent createFromParcel(Parcel parcel) {
                return new CollectionClickActionEvent(parcel);
            }

            public CollectionClickActionEvent[] newArray(int n) {
                return new CollectionClickActionEvent[n];
            }
        };
        ACTION = CollectionClickActionEvent.class.getSimpleName();
    }

    CollectionClickActionEvent(Parcel parcel) {
        super(parcel);
    }

    CollectionClickActionEvent(Collection collection) {
        super(ACTION);
        Util.checkNotNull(collection, "collection == null");
        this.payload.putString(EXTRAS_ID, collection.id);
        this.payload.putString(EXTRAS_IMAGE_URL, collection.image);
        this.payload.putString(EXTRAS_TITLE, collection.title);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public String id() {
        return this.payload().getString(EXTRAS_ID);
    }

    public String imageUrl() {
        return this.payload().getString(EXTRAS_IMAGE_URL);
    }

    public String title() {
        return this.payload().getString(EXTRAS_TITLE);
    }

    @Override
    public void writeToParcel(Parcel parcel, int n) {
        super.writeToParcel(parcel, n);
    }

}

