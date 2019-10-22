/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.io.IOException;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

public interface Call
extends Cloneable {
    public void cancel();

    public Call clone();

    public void enqueue(Callback var1);

    public Response execute() throws IOException;

    public Request request();

    public static interface Factory {
        public Call newCall(Request var1);
    }

}

