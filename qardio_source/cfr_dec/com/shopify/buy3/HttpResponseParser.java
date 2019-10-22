/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.shopify.buy3.GraphError;
import com.shopify.buy3.GraphHttpError;
import com.shopify.buy3.GraphParseError;
import com.shopify.buy3.GraphResponse;
import com.shopify.buy3.RealGraphCall;
import com.shopify.buy3.Utils;
import com.shopify.graphql.support.AbstractResponse;
import com.shopify.graphql.support.Error;
import com.shopify.graphql.support.TopLevelResponse;
import java.io.Reader;
import java.util.List;
import okhttp3.Response;
import okhttp3.ResponseBody;
import timber.log.Timber;

final class HttpResponseParser<T extends AbstractResponse<T>> {
    private final RealGraphCall.ResponseDataConverter<T> responseDataConverter;

    HttpResponseParser(RealGraphCall.ResponseDataConverter<T> responseDataConverter) {
        this.responseDataConverter = Utils.checkNotNull(responseDataConverter, "responseDataConverter == null");
    }

    private Response checkResponse(Response response) throws GraphError {
        if (!response.isSuccessful()) {
            try {
                throw new GraphHttpError(response);
            }
            catch (Throwable throwable) {
                response.close();
                throw throwable;
            }
        }
        return response;
    }

    private TopLevelResponse parseTopLevelResponse(Response response) throws GraphError {
        try {
            Object object = new JsonReader(response.body().charStream());
            object = new TopLevelResponse((JsonObject)new JsonParser().parse((JsonReader)object));
            return object;
        }
        catch (Exception exception) {
            Timber.w(exception, "failed to parse GraphQL response", new Object[0]);
            throw new GraphParseError("Failed to parse GraphQL http response", exception);
        }
        finally {
            response.close();
        }
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    GraphResponse<T> parse(Response graphResponse) throws GraphError {
        TopLevelResponse topLevelResponse = this.parseTopLevelResponse(this.checkResponse((Response)((Object)graphResponse)));
        try {
            if (topLevelResponse.getData() != null) {
                graphResponse = this.responseDataConverter.convert(topLevelResponse);
                do {
                    return new GraphResponse<GraphResponse<Object>>(graphResponse, topLevelResponse.getErrors());
                    break;
                } while (true);
            }
            graphResponse = null;
            return new GraphResponse<GraphResponse<Object>>(graphResponse, topLevelResponse.getErrors());
        }
        catch (Exception exception) {
            Timber.w(exception, "failed to process GraphQL response", new Object[0]);
            throw new GraphError("Failed to process GraphQL response ", exception);
        }
    }
}

