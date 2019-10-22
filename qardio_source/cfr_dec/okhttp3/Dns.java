/*
 * Decompiled with CFR 0.147.
 */
package okhttp3;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Arrays;
import java.util.List;

public interface Dns {
    public static final Dns SYSTEM = new Dns(){

        @Override
        public List<InetAddress> lookup(String string2) throws UnknownHostException {
            if (string2 == null) {
                throw new UnknownHostException("hostname == null");
            }
            return Arrays.asList(InetAddress.getAllByName(string2));
        }
    };

    public List<InetAddress> lookup(String var1) throws UnknownHostException;

}

