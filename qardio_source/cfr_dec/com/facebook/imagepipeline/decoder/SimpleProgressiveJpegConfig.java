/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.imagepipeline.decoder;

import com.facebook.common.internal.Preconditions;
import com.facebook.imagepipeline.decoder.ProgressiveJpegConfig;
import com.facebook.imagepipeline.image.ImmutableQualityInfo;
import com.facebook.imagepipeline.image.QualityInfo;
import java.util.Collections;
import java.util.List;

public class SimpleProgressiveJpegConfig
implements ProgressiveJpegConfig {
    private final DynamicValueConfig mDynamicValueConfig;

    public SimpleProgressiveJpegConfig() {
        this(new DefaultDynamicValueConfig());
    }

    public SimpleProgressiveJpegConfig(DynamicValueConfig dynamicValueConfig) {
        this.mDynamicValueConfig = Preconditions.checkNotNull(dynamicValueConfig);
    }

    @Override
    public int getNextScanNumberToDecode(int n) {
        List<Integer> list = this.mDynamicValueConfig.getScansToDecode();
        if (list == null || list.isEmpty()) {
            return n + 1;
        }
        for (int i = 0; i < list.size(); ++i) {
            if (list.get(i) <= n) continue;
            return list.get(i);
        }
        return Integer.MAX_VALUE;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public QualityInfo getQualityInfo(int n) {
        boolean bl;
        if (n >= this.mDynamicValueConfig.getGoodEnoughScanNumber()) {
            bl = true;
            do {
                return ImmutableQualityInfo.of(n, bl, false);
                break;
            } while (true);
        }
        bl = false;
        return ImmutableQualityInfo.of(n, bl, false);
    }

    private static class DefaultDynamicValueConfig
    implements DynamicValueConfig {
        private DefaultDynamicValueConfig() {
        }

        @Override
        public int getGoodEnoughScanNumber() {
            return 0;
        }

        @Override
        public List<Integer> getScansToDecode() {
            return Collections.EMPTY_LIST;
        }
    }

    public static interface DynamicValueConfig {
        public int getGoodEnoughScanNumber();

        public List<Integer> getScansToDecode();
    }

}

