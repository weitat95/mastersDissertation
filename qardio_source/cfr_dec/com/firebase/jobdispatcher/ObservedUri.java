/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.net.Uri
 */
package com.firebase.jobdispatcher;

import android.net.Uri;

public final class ObservedUri {
    private final int flags;
    private final Uri uri;

    public ObservedUri(Uri uri, int n) {
        if (uri == null) {
            throw new IllegalArgumentException("URI must not be null.");
        }
        this.uri = uri;
        this.flags = n;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (this == object) break block4;
                if (!(object instanceof ObservedUri)) {
                    return false;
                }
                object = (ObservedUri)object;
                if (this.flags != ((ObservedUri)object).flags || !this.uri.equals((Object)((ObservedUri)object).uri)) break block5;
            }
            return true;
        }
        return false;
    }

    public int getFlags() {
        return this.flags;
    }

    public Uri getUri() {
        return this.uri;
    }

    public int hashCode() {
        return this.uri.hashCode() ^ this.flags;
    }
}

