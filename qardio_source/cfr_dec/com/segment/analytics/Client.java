/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.text.TextUtils
 */
package com.segment.analytics;

import android.text.TextUtils;
import com.segment.analytics.ConnectionFactory;
import com.segment.analytics.internal.Utils;
import java.io.Closeable;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.util.zip.GZIPOutputStream;

class Client {
    final ConnectionFactory connectionFactory;
    final String writeKey;

    Client(String string2, ConnectionFactory connectionFactory) {
        this.writeKey = string2;
        this.connectionFactory = connectionFactory;
    }

    private static Connection createGetConnection(HttpURLConnection httpURLConnection) throws IOException {
        return new Connection(httpURLConnection, Utils.getInputStream(httpURLConnection), null){

            @Override
            public void close() throws IOException {
                super.close();
                this.is.close();
            }
        };
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private static Connection createPostConnection(HttpURLConnection httpURLConnection) throws IOException {
        OutputStream outputStream;
        if (TextUtils.equals((CharSequence)"gzip", (CharSequence)httpURLConnection.getRequestProperty("Content-Encoding"))) {
            outputStream = new GZIPOutputStream(httpURLConnection.getOutputStream());
            do {
                return new Connection(httpURLConnection, null, outputStream){

                    /*
                     * Exception decompiling
                     */
                    @Override
                    public void close() throws IOException {
                        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
                        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [0[TRYBLOCK]], but top level block is 4[CATCHBLOCK]
                        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
                        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
                        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
                        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
                        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
                        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
                        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
                        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
                        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
                        // org.benf.cfr.reader.entities.ClassFile.analyseInnerClassesPass1(ClassFile.java:794)
                        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:902)
                        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
                        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
                        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
                        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
                        // org.benf.cfr.reader.Main.main(Main.java:48)
                        throw new IllegalStateException("Decompilation failed");
                    }
                };
                break;
            } while (true);
        }
        outputStream = httpURLConnection.getOutputStream();
        return new /* invalid duplicate definition of identical inner class */;
    }

    Connection attribution() throws IOException {
        return Client.createPostConnection(this.connectionFactory.attribution(this.writeKey));
    }

    Connection fetchSettings() throws IOException {
        HttpURLConnection httpURLConnection = this.connectionFactory.projectSettings(this.writeKey);
        int n = httpURLConnection.getResponseCode();
        if (n != 200) {
            httpURLConnection.disconnect();
            throw new IOException("HTTP " + n + ": " + httpURLConnection.getResponseMessage());
        }
        return Client.createGetConnection(httpURLConnection);
    }

    Connection upload() throws IOException {
        return Client.createPostConnection(this.connectionFactory.upload(this.writeKey));
    }

    static abstract class Connection
    implements Closeable {
        final HttpURLConnection connection;
        final InputStream is;
        final OutputStream os;

        Connection(HttpURLConnection httpURLConnection, InputStream inputStream, OutputStream outputStream) {
            if (httpURLConnection == null) {
                throw new IllegalArgumentException("connection == null");
            }
            this.connection = httpURLConnection;
            this.is = inputStream;
            this.os = outputStream;
        }

        @Override
        public void close() throws IOException {
            this.connection.disconnect();
        }
    }

    static class HTTPException
    extends IOException {
        final String responseBody;
        final int responseCode;
        final String responseMessage;

        HTTPException(int n, String string2, String string3) {
            super("HTTP " + n + ": " + string2 + ". Response: " + string3);
            this.responseCode = n;
            this.responseMessage = string2;
            this.responseBody = string3;
        }
    }

}

