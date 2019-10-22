/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import okhttp3.HttpUrl;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import okhttp3.internal.Util;
import okio.Buffer;
import okio.BufferedSink;

public final class FormBody
extends RequestBody {
    private static final MediaType CONTENT_TYPE = MediaType.parse("application/x-www-form-urlencoded");
    private final List<String> encodedNames;
    private final List<String> encodedValues;

    FormBody(List<String> list, List<String> list2) {
        this.encodedNames = Util.immutableList(list);
        this.encodedValues = Util.immutableList(list2);
    }

    /*
     * Enabled aggressive block sorting
     */
    private long writeOrCountBytes(BufferedSink bufferedSink, boolean bl) {
        long l = 0L;
        bufferedSink = bl ? new Buffer() : bufferedSink.buffer();
        int n = this.encodedNames.size();
        for (int i = 0; i < n; ++i) {
            if (i > 0) {
                ((Buffer)bufferedSink).writeByte(38);
            }
            ((Buffer)bufferedSink).writeUtf8(this.encodedNames.get(i));
            ((Buffer)bufferedSink).writeByte(61);
            ((Buffer)bufferedSink).writeUtf8(this.encodedValues.get(i));
        }
        if (bl) {
            l = ((Buffer)bufferedSink).size();
            ((Buffer)bufferedSink).clear();
        }
        return l;
    }

    @Override
    public long contentLength() {
        return this.writeOrCountBytes(null, true);
    }

    @Override
    public MediaType contentType() {
        return CONTENT_TYPE;
    }

    @Override
    public void writeTo(BufferedSink bufferedSink) throws IOException {
        this.writeOrCountBytes(bufferedSink, false);
    }

    public static final class Builder {
        private final List<String> names = new ArrayList<String>();
        private final List<String> values = new ArrayList<String>();

        public Builder add(String string2, String string3) {
            this.names.add(HttpUrl.canonicalize(string2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true));
            this.values.add(HttpUrl.canonicalize(string3, " \"':;<=>@[]^`{}|/\\?#&!$(),~", false, false, true, true));
            return this;
        }

        public Builder addEncoded(String string2, String string3) {
            this.names.add(HttpUrl.canonicalize(string2, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true));
            this.values.add(HttpUrl.canonicalize(string3, " \"':;<=>@[]^`{}|/\\?#&!$(),~", true, false, true, true));
            return this;
        }

        public FormBody build() {
            return new FormBody(this.names, this.values);
        }
    }

}

