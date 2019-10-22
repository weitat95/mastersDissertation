/*
 * Decompiled with CFR 0.147.
 */
package retrofit2;

import java.io.IOException;
import retrofit2.Response;

public interface Call<T>
extends Cloneable {
    public void cancel();

    public Call<T> clone();

    public Response<T> execute() throws IOException;

    public boolean isCanceled();
}

