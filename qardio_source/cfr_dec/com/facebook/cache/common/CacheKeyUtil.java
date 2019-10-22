/*
 * Decompiled with CFR 0.147.
 */
package com.facebook.cache.common;

import com.facebook.cache.common.CacheKey;
import com.facebook.cache.common.MultiCacheKey;
import com.facebook.common.util.SecureHashUtil;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

public final class CacheKeyUtil {
    public static String getFirstResourceId(CacheKey object) {
        try {
            if (object instanceof MultiCacheKey) {
                return CacheKeyUtil.secureHashKey(((MultiCacheKey)object).getCacheKeys().get(0));
            }
            object = CacheKeyUtil.secureHashKey((CacheKey)object);
            return object;
        }
        catch (UnsupportedEncodingException unsupportedEncodingException) {
            throw new RuntimeException(unsupportedEncodingException);
        }
    }

    public static List<String> getResourceIds(CacheKey arrayList) {
        block7: {
            block6: {
                if (!(arrayList instanceof MultiCacheKey)) break block6;
                List<CacheKey> list = ((MultiCacheKey)((Object)arrayList)).getCacheKeys();
                ArrayList<String> arrayList2 = new ArrayList<String>(list.size());
                int n = 0;
                do {
                    arrayList = arrayList2;
                    if (n < list.size()) {
                        arrayList2.add(CacheKeyUtil.secureHashKey(list.get(n)));
                        ++n;
                        continue;
                    }
                    break block7;
                    break;
                } while (true);
            }
            try {
                ArrayList<String> arrayList3 = new ArrayList<String>(1);
                arrayList3.add(CacheKeyUtil.secureHashKey((CacheKey)((Object)arrayList)));
                arrayList = arrayList3;
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                throw new RuntimeException(unsupportedEncodingException);
            }
        }
        return arrayList;
    }

    private static String secureHashKey(CacheKey cacheKey) throws UnsupportedEncodingException {
        return SecureHashUtil.makeSHA1HashBase64(cacheKey.toString().getBytes("UTF-8"));
    }
}

