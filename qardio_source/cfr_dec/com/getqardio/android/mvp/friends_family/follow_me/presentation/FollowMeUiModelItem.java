/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.follow_me.presentation;

import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;

public class FollowMeUiModelItem {
    private FollowMeUser followMeUser;
    private Type type;

    FollowMeUiModelItem(Type type, FollowMeUser followMeUser) {
        this.type = type;
        this.followMeUser = followMeUser;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block7: {
            block6: {
                if (this == object) break block6;
                if (object == null || this.getClass() != object.getClass()) {
                    return false;
                }
                object = (FollowMeUiModelItem)object;
                if (this.type != ((FollowMeUiModelItem)object).type) {
                    return false;
                }
                if (this.followMeUser != null) {
                    return this.followMeUser.equals(((FollowMeUiModelItem)object).followMeUser);
                }
                if (((FollowMeUiModelItem)object).followMeUser != null) break block7;
            }
            return true;
        }
        return false;
    }

    public FollowMeUser getFollowMeUser() {
        return this.followMeUser;
    }

    public Type getType() {
        return this.type;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        int n2 = this.type != null ? this.type.hashCode() : 0;
        if (this.followMeUser != null) {
            n = this.followMeUser.hashCode();
        }
        return n2 * 31 + n;
    }

    public static enum Type {
        HEADER_PENDING,
        HEADER_APPROVED,
        ITEM;

    }

}

