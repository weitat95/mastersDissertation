/*
 * Decompiled with CFR 0.147.
 */
package okhttp3.internal.http2;

import java.io.IOException;
import java.util.List;
import okhttp3.internal.http2.ErrorCode;
import okhttp3.internal.http2.Header;
import okio.BufferedSource;

public interface PushObserver {
    public static final PushObserver CANCEL = new PushObserver(){

        @Override
        public boolean onData(int n, BufferedSource bufferedSource, int n2, boolean bl) throws IOException {
            bufferedSource.skip(n2);
            return true;
        }

        @Override
        public boolean onHeaders(int n, List<Header> list, boolean bl) {
            return true;
        }

        @Override
        public boolean onRequest(int n, List<Header> list) {
            return true;
        }

        @Override
        public void onReset(int n, ErrorCode errorCode) {
        }
    };

    public boolean onData(int var1, BufferedSource var2, int var3, boolean var4) throws IOException;

    public boolean onHeaders(int var1, List<Header> var2, boolean var3);

    public boolean onRequest(int var1, List<Header> var2);

    public void onReset(int var1, ErrorCode var2);

}

