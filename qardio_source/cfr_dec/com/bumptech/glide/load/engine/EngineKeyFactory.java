/*
 * Decompiled with CFR 0.147.
 */
package com.bumptech.glide.load.engine;

import com.bumptech.glide.load.Encoder;
import com.bumptech.glide.load.Key;
import com.bumptech.glide.load.ResourceDecoder;
import com.bumptech.glide.load.ResourceEncoder;
import com.bumptech.glide.load.Transformation;
import com.bumptech.glide.load.engine.EngineKey;
import com.bumptech.glide.load.resource.transcode.ResourceTranscoder;

class EngineKeyFactory {
    EngineKeyFactory() {
    }

    public EngineKey buildKey(String string2, Key key, int n, int n2, ResourceDecoder resourceDecoder, ResourceDecoder resourceDecoder2, Transformation transformation, ResourceEncoder resourceEncoder, ResourceTranscoder resourceTranscoder, Encoder encoder) {
        return new EngineKey(string2, key, n, n2, resourceDecoder, resourceDecoder2, transformation, resourceEncoder, resourceTranscoder, encoder);
    }
}

