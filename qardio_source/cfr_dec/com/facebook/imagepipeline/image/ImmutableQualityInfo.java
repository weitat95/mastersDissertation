/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.image;

import com.facebook.imagepipeline.image.QualityInfo;

public class ImmutableQualityInfo
implements QualityInfo {
    public static final QualityInfo FULL_QUALITY = ImmutableQualityInfo.of(Integer.MAX_VALUE, true, true);
    boolean mIsOfFullQuality;
    boolean mIsOfGoodEnoughQuality;
    int mQuality;

    private ImmutableQualityInfo(int n, boolean bl, boolean bl2) {
        this.mQuality = n;
        this.mIsOfGoodEnoughQuality = bl;
        this.mIsOfFullQuality = bl2;
    }

    public static QualityInfo of(int n, boolean bl, boolean bl2) {
        return new ImmutableQualityInfo(n, bl, bl2);
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block5: {
            block4: {
                if (object == this) break block4;
                if (!(object instanceof ImmutableQualityInfo)) {
                    return false;
                }
                object = (ImmutableQualityInfo)object;
                if (this.mQuality != ((ImmutableQualityInfo)object).mQuality || this.mIsOfGoodEnoughQuality != ((ImmutableQualityInfo)object).mIsOfGoodEnoughQuality || this.mIsOfFullQuality != ((ImmutableQualityInfo)object).mIsOfFullQuality) break block5;
            }
            return true;
        }
        return false;
    }

    @Override
    public int getQuality() {
        return this.mQuality;
    }

    /*
     * Enabled aggressive block sorting
     */
    public int hashCode() {
        int n = 0;
        int n2 = this.mQuality;
        int n3 = this.mIsOfGoodEnoughQuality ? 4194304 : 0;
        if (this.mIsOfFullQuality) {
            n = 8388608;
        }
        return n3 ^ n2 ^ n;
    }

    @Override
    public boolean isOfFullQuality() {
        return this.mIsOfFullQuality;
    }

    @Override
    public boolean isOfGoodEnoughQuality() {
        return this.mIsOfGoodEnoughQuality;
    }
}

