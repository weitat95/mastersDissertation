/*
 * Decompiled with CFR 0.147.
 */
package com.shopify.buy3;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.shopify.buy3.Storefront$CheckoutQuery$$Lambda$1;
import com.shopify.buy3.Storefront$CollectionQuery$$Lambda$1;
import com.shopify.buy3.Storefront$CollectionQuery$$Lambda$2;
import com.shopify.buy3.Storefront$CollectionQuery$$Lambda$3;
import com.shopify.buy3.Storefront$ProductQuery$$Lambda$3;
import com.shopify.buy3.Storefront$ProductQuery$$Lambda$4;
import com.shopify.buy3.Storefront$ProductQuery$$Lambda$5;
import com.shopify.buy3.Utils;
import com.shopify.graphql.support.AbstractResponse;
import com.shopify.graphql.support.Arguments;
import com.shopify.graphql.support.ID;
import com.shopify.graphql.support.Query;
import com.shopify.graphql.support.SchemaViolationError;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.joda.time.DateTime;

public class Storefront {
    public static MutationQuery mutation(MutationQueryDefinition mutationQueryDefinition) {
        StringBuilder stringBuilder = new StringBuilder("mutation{");
        MutationQuery mutationQuery = new MutationQuery(stringBuilder);
        mutationQueryDefinition.define(mutationQuery);
        stringBuilder.append('}');
        return mutationQuery;
    }

    public static QueryRootQuery query(QueryRootQueryDefinition queryRootQueryDefinition) {
        StringBuilder stringBuilder = new StringBuilder("{");
        QueryRootQuery queryRootQuery = new QueryRootQuery(stringBuilder);
        queryRootQueryDefinition.define(queryRootQuery);
        stringBuilder.append('}');
        return queryRootQuery;
    }

    public static class AppliedGiftCard
    extends AbstractResponse<AppliedGiftCard>
    implements Node {
        public AppliedGiftCard() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public AppliedGiftCard(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block14 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1424833483: {
                        if (!string3.equals("amountUsed")) break;
                        n = 0;
                        break;
                    }
                    case -339185956: {
                        if (!string3.equals("balance")) break;
                        n = 1;
                        break;
                    }
                    case 3355: {
                        if (!string3.equals("id")) break;
                        n = 2;
                        break;
                    }
                    case 1454527904: {
                        if (!string3.equals("lastCharacters")) break;
                        n = 3;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 4;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 1: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 2: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 3: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block14;
                    }
                    case 4: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class Article
    extends AbstractResponse<Article>
    implements Node {
        public Article() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Article(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block32 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -1406328437: {
                        if (!((String)object).equals("author")) break;
                        n = 0;
                        break;
                    }
                    case 3026850: {
                        if (!((String)object).equals("blog")) break;
                        n = 1;
                        break;
                    }
                    case -602415628: {
                        if (!((String)object).equals("comments")) break;
                        n = 2;
                        break;
                    }
                    case 951530617: {
                        if (!((String)object).equals("content")) break;
                        n = 3;
                        break;
                    }
                    case -389493820: {
                        if (!((String)object).equals("contentHtml")) break;
                        n = 4;
                        break;
                    }
                    case -1321359999: {
                        if (!((String)object).equals("excerpt")) break;
                        n = 5;
                        break;
                    }
                    case -417367860: {
                        if (!((String)object).equals("excerptHtml")) break;
                        n = 6;
                        break;
                    }
                    case 3355: {
                        if (!((String)object).equals("id")) break;
                        n = 7;
                        break;
                    }
                    case 100313435: {
                        if (!((String)object).equals("image")) break;
                        n = 8;
                        break;
                    }
                    case -614144319: {
                        if (!((String)object).equals("publishedAt")) break;
                        n = 9;
                        break;
                    }
                    case 3552281: {
                        if (!((String)object).equals("tags")) break;
                        n = 10;
                        break;
                    }
                    case 110371416: {
                        if (!((String)object).equals("title")) break;
                        n = 11;
                        break;
                    }
                    case 116079: {
                        if (!((String)object).equals("url")) break;
                        n = 12;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 13;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new ArticleAuthor(this.jsonAsObject(entry.getValue(), string2)));
                        continue block32;
                    }
                    case 1: {
                        this.responseData.put(string2, new Blog(this.jsonAsObject(entry.getValue(), string2)));
                        continue block32;
                    }
                    case 2: {
                        this.responseData.put(string2, new CommentConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block32;
                    }
                    case 3: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block32;
                    }
                    case 4: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block32;
                    }
                    case 5: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block32;
                    }
                    case 6: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block32;
                    }
                    case 7: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block32;
                    }
                    case 8: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new Image(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block32;
                    }
                    case 9: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block32;
                    }
                    case 10: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(this.jsonAsString(iterator2.next(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block32;
                    }
                    case 11: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block32;
                    }
                    case 12: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block32;
                    }
                    case 13: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class ArticleAuthor
    extends AbstractResponse<ArticleAuthor> {
        public ArticleAuthor() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ArticleAuthor(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block16 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 97544: {
                        if (!((String)object).equals("bio")) break;
                        n = 0;
                        break;
                    }
                    case 96619420: {
                        if (!((String)object).equals("email")) break;
                        n = 1;
                        break;
                    }
                    case 132835675: {
                        if (!((String)object).equals("firstName")) break;
                        n = 2;
                        break;
                    }
                    case -1459599807: {
                        if (!((String)object).equals("lastName")) break;
                        n = 3;
                        break;
                    }
                    case 3373707: {
                        if (!((String)object).equals("name")) break;
                        n = 4;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 5;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block16;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block16;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block16;
                    }
                    case 3: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block16;
                    }
                    case 4: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block16;
                    }
                    case 5: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class ArticleConnection
    extends AbstractResponse<ArticleConnection> {
        public ArticleConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ArticleConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new ArticleEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class ArticleEdge
    extends AbstractResponse<ArticleEdge> {
        public ArticleEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ArticleEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new Article(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static enum ArticleSortKeys {
        AUTHOR,
        BLOG_TITLE,
        ID,
        RELEVANCE,
        TITLE,
        UPDATED_AT,
        UNKNOWN_VALUE;


        public String toString() {
            switch (1.$SwitchMap$com$shopify$buy3$Storefront$ArticleSortKeys[this.ordinal()]) {
                default: {
                    return "";
                }
                case 1: {
                    return "AUTHOR";
                }
                case 2: {
                    return "BLOG_TITLE";
                }
                case 3: {
                    return "ID";
                }
                case 4: {
                    return "RELEVANCE";
                }
                case 5: {
                    return "TITLE";
                }
                case 6: 
            }
            return "UPDATED_AT";
        }
    }

    public static class Attribute
    extends AbstractResponse<Attribute> {
        public Attribute() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Attribute(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 106079: {
                        if (!((String)object).equals("key")) break;
                        n = 0;
                        break;
                    }
                    case 111972721: {
                        if (!((String)object).equals("value")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class AttributeInput
    implements Serializable {
        private String key;
        private String value;

        public void appendTo(StringBuilder stringBuilder) {
            stringBuilder.append('{');
            stringBuilder.append("");
            stringBuilder.append("key:");
            Query.appendQuotedString(stringBuilder, this.key.toString());
            stringBuilder.append(",");
            stringBuilder.append("value:");
            Query.appendQuotedString(stringBuilder, this.value.toString());
            stringBuilder.append('}');
        }
    }

    public static class AvailableShippingRates
    extends AbstractResponse<AvailableShippingRates> {
        public AvailableShippingRates() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public AvailableShippingRates(JsonObject arrayList) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)arrayList)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                arrayList = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)arrayList)).hashCode()) {
                    case 108386723: {
                        if (!((String)((Object)arrayList)).equals("ready")) break;
                        n = 0;
                        break;
                    }
                    case 530833029: {
                        if (!((String)((Object)arrayList)).equals("shippingRates")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)arrayList)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        arrayList = null;
                        if (!entry.getValue().isJsonNull()) {
                            arrayList = new ArrayList<ShippingRate>();
                            Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                            while (iterator2.hasNext()) {
                                arrayList.add(new ShippingRate(this.jsonAsObject(iterator2.next(), string2)));
                            }
                        }
                        this.responseData.put(string2, arrayList);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public Boolean getReady() {
            return (Boolean)this.get("ready");
        }

        public List<ShippingRate> getShippingRates() {
            return (List)this.get("shippingRates");
        }
    }

    public static class AvailableShippingRatesQuery
    extends Query<AvailableShippingRatesQuery> {
        AvailableShippingRatesQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public AvailableShippingRatesQuery ready() {
            this.startField("ready");
            return this;
        }

        public AvailableShippingRatesQuery shippingRates(ShippingRateQueryDefinition shippingRateQueryDefinition) {
            this.startField("shippingRates");
            this._queryBuilder.append('{');
            shippingRateQueryDefinition.define(new ShippingRateQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface AvailableShippingRatesQueryDefinition {
        public void define(AvailableShippingRatesQuery var1);
    }

    public static class Blog
    extends AbstractResponse<Blog>
    implements Node {
        public Blog() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Blog(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block14 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1228877251: {
                        if (!string3.equals("articles")) break;
                        n = 0;
                        break;
                    }
                    case 3355: {
                        if (!string3.equals("id")) break;
                        n = 1;
                        break;
                    }
                    case 110371416: {
                        if (!string3.equals("title")) break;
                        n = 2;
                        break;
                    }
                    case 116079: {
                        if (!string3.equals("url")) break;
                        n = 3;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 4;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new ArticleConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 1: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block14;
                    }
                    case 3: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block14;
                    }
                    case 4: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class BlogConnection
    extends AbstractResponse<BlogConnection> {
        public BlogConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public BlogConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new BlogEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class BlogEdge
    extends AbstractResponse<BlogEdge> {
        public BlogEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public BlogEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new Blog(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static enum BlogSortKeys {
        HANDLE,
        ID,
        RELEVANCE,
        TITLE,
        UNKNOWN_VALUE;


        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case HANDLE: {
                    return "HANDLE";
                }
                case ID: {
                    return "ID";
                }
                case RELEVANCE: {
                    return "RELEVANCE";
                }
                case TITLE: 
            }
            return "TITLE";
        }
    }

    public static enum CardBrand {
        AMERICAN_EXPRESS,
        DINERS_CLUB,
        DISCOVER,
        JCB,
        MASTERCARD,
        VISA,
        UNKNOWN_VALUE;


        /*
         * Enabled aggressive block sorting
         */
        public static CardBrand fromGraphQl(String string2) {
            if (string2 == null) {
                return null;
            }
            int n = -1;
            switch (string2.hashCode()) {
                case 1512044081: {
                    if (!string2.equals("AMERICAN_EXPRESS")) break;
                    n = 0;
                    break;
                }
                case -420007048: {
                    if (!string2.equals("DINERS_CLUB")) break;
                    n = 1;
                    break;
                }
                case 1055811561: {
                    if (!string2.equals("DISCOVER")) break;
                    n = 2;
                    break;
                }
                case 73257: {
                    if (!string2.equals("JCB")) break;
                    n = 3;
                    break;
                }
                case -1553624974: {
                    if (!string2.equals("MASTERCARD")) break;
                    n = 4;
                    break;
                }
                case 2634817: {
                    if (!string2.equals("VISA")) break;
                    n = 5;
                    break;
                }
            }
            switch (n) {
                default: {
                    return UNKNOWN_VALUE;
                }
                case 0: {
                    return AMERICAN_EXPRESS;
                }
                case 1: {
                    return DINERS_CLUB;
                }
                case 2: {
                    return DISCOVER;
                }
                case 3: {
                    return JCB;
                }
                case 4: {
                    return MASTERCARD;
                }
                case 5: 
            }
            return VISA;
        }

        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case AMERICAN_EXPRESS: {
                    return "AMERICAN_EXPRESS";
                }
                case DINERS_CLUB: {
                    return "DINERS_CLUB";
                }
                case DISCOVER: {
                    return "DISCOVER";
                }
                case JCB: {
                    return "JCB";
                }
                case MASTERCARD: {
                    return "MASTERCARD";
                }
                case VISA: 
            }
            return "VISA";
        }
    }

    public static class Checkout
    extends AbstractResponse<Checkout>
    implements Node {
        public Checkout() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Checkout(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block56 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 591132694: {
                        if (!((String)object).equals("appliedGiftCards")) break;
                        n = 0;
                        break;
                    }
                    case 309662268: {
                        if (!((String)object).equals("availableShippingRates")) break;
                        n = 1;
                        break;
                    }
                    case 402429918: {
                        if (!((String)object).equals("completedAt")) break;
                        n = 2;
                        break;
                    }
                    case 598371643: {
                        if (!((String)object).equals("createdAt")) break;
                        n = 3;
                        break;
                    }
                    case 1004773790: {
                        if (!((String)object).equals("currencyCode")) break;
                        n = 4;
                        break;
                    }
                    case 555169704: {
                        if (!((String)object).equals("customAttributes")) break;
                        n = 5;
                        break;
                    }
                    case 606175198: {
                        if (!((String)object).equals("customer")) break;
                        n = 6;
                        break;
                    }
                    case 96619420: {
                        if (!((String)object).equals("email")) break;
                        n = 7;
                        break;
                    }
                    case 3355: {
                        if (!((String)object).equals("id")) break;
                        n = 8;
                        break;
                    }
                    case -1816387540: {
                        if (!((String)object).equals("lineItems")) break;
                        n = 9;
                        break;
                    }
                    case 3387378: {
                        if (!((String)object).equals("note")) break;
                        n = 10;
                        break;
                    }
                    case 106006350: {
                        if (!((String)object).equals("order")) break;
                        n = 11;
                        break;
                    }
                    case -1444671377: {
                        if (!((String)object).equals("orderStatusUrl")) break;
                        n = 12;
                        break;
                    }
                    case 1612862990: {
                        if (!((String)object).equals("paymentDue")) break;
                        n = 13;
                        break;
                    }
                    case 108386723: {
                        if (!((String)object).equals("ready")) break;
                        n = 14;
                        break;
                    }
                    case -238100612: {
                        if (!((String)object).equals("requiresShipping")) break;
                        n = 15;
                        break;
                    }
                    case 1193227878: {
                        if (!((String)object).equals("shippingAddress")) break;
                        n = 16;
                        break;
                    }
                    case -398689598: {
                        if (!((String)object).equals("shippingLine")) break;
                        n = 17;
                        break;
                    }
                    case 2109292197: {
                        if (!((String)object).equals("subtotalPrice")) break;
                        n = 18;
                        break;
                    }
                    case -741799062: {
                        if (!((String)object).equals("taxExempt")) break;
                        n = 19;
                        break;
                    }
                    case -841643659: {
                        if (!((String)object).equals("taxesIncluded")) break;
                        n = 20;
                        break;
                    }
                    case -719302555: {
                        if (!((String)object).equals("totalPrice")) break;
                        n = 21;
                        break;
                    }
                    case -849906233: {
                        if (!((String)object).equals("totalTax")) break;
                        n = 22;
                        break;
                    }
                    case -1949194674: {
                        if (!((String)object).equals("updatedAt")) break;
                        n = 23;
                        break;
                    }
                    case -791817861: {
                        if (!((String)object).equals("webUrl")) break;
                        n = 24;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 25;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new AppliedGiftCard(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 1: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new AvailableShippingRates(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 2: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 3: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block56;
                    }
                    case 4: {
                        this.responseData.put(string2, CurrencyCode.fromGraphQl(this.jsonAsString(entry.getValue(), string2)));
                        continue block56;
                    }
                    case 5: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator3 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator3.hasNext()) {
                            object.add(new Attribute(this.jsonAsObject(iterator3.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 6: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new Customer(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 7: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 8: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block56;
                    }
                    case 9: {
                        this.responseData.put(string2, new CheckoutLineItemConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block56;
                    }
                    case 10: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 11: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new Order(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 12: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 13: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block56;
                    }
                    case 14: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block56;
                    }
                    case 15: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block56;
                    }
                    case 16: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new MailingAddress(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 17: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new ShippingRate(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block56;
                    }
                    case 18: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block56;
                    }
                    case 19: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block56;
                    }
                    case 20: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block56;
                    }
                    case 21: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block56;
                    }
                    case 22: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block56;
                    }
                    case 23: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block56;
                    }
                    case 24: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block56;
                    }
                    case 25: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public AvailableShippingRates getAvailableShippingRates() {
            return (AvailableShippingRates)this.get("availableShippingRates");
        }

        public CurrencyCode getCurrencyCode() {
            return (CurrencyCode)((Object)this.get("currencyCode"));
        }

        public ID getId() {
            return (ID)this.get("id");
        }

        public CheckoutLineItemConnection getLineItems() {
            return (CheckoutLineItemConnection)this.get("lineItems");
        }

        public Boolean getRequiresShipping() {
            return (Boolean)this.get("requiresShipping");
        }

        public ShippingRate getShippingLine() {
            return (ShippingRate)this.get("shippingLine");
        }

        public BigDecimal getSubtotalPrice() {
            return (BigDecimal)this.get("subtotalPrice");
        }

        public BigDecimal getTotalPrice() {
            return (BigDecimal)this.get("totalPrice");
        }

        public BigDecimal getTotalTax() {
            return (BigDecimal)this.get("totalTax");
        }

        public String getWebUrl() {
            return (String)this.get("webUrl");
        }
    }

    public static class CheckoutAttributesUpdatePayload
    extends AbstractResponse<CheckoutAttributesUpdatePayload> {
        public CheckoutAttributesUpdatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutAttributesUpdatePayload(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1536904518: {
                        if (!((String)object).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)object).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new Checkout(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 1: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CheckoutCompleteFreePayload
    extends AbstractResponse<CheckoutCompleteFreePayload> {
        public CheckoutCompleteFreePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutCompleteFreePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 1536904518: {
                        if (!((String)((Object)serializable)).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Checkout(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CheckoutCompleteWithCreditCardPayload
    extends AbstractResponse<CheckoutCompleteWithCreditCardPayload> {
        public CheckoutCompleteWithCreditCardPayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutCompleteWithCreditCardPayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block12 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 1536904518: {
                        if (!((String)((Object)serializable)).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case -786681338: {
                        if (!((String)((Object)serializable)).equals("payment")) break;
                        n = 1;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 2;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 3;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new Checkout(this.jsonAsObject(entry.getValue(), string2)));
                        continue block12;
                    }
                    case 1: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Payment(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block12;
                    }
                    case 2: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block12;
                    }
                    case 3: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CheckoutCompleteWithTokenizedPaymentPayload
    extends AbstractResponse<CheckoutCompleteWithTokenizedPaymentPayload> {
        public CheckoutCompleteWithTokenizedPaymentPayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutCompleteWithTokenizedPaymentPayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block12 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 1536904518: {
                        if (!((String)((Object)serializable)).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case -786681338: {
                        if (!((String)((Object)serializable)).equals("payment")) break;
                        n = 1;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 2;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 3;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new Checkout(this.jsonAsObject(entry.getValue(), string2)));
                        continue block12;
                    }
                    case 1: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Payment(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block12;
                    }
                    case 2: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block12;
                    }
                    case 3: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public Payment getPayment() {
            return (Payment)this.get("payment");
        }

        public List<UserError> getUserErrors() {
            return (List)this.get("userErrors");
        }
    }

    public static class CheckoutCompleteWithTokenizedPaymentPayloadQuery
    extends Query<CheckoutCompleteWithTokenizedPaymentPayloadQuery> {
        CheckoutCompleteWithTokenizedPaymentPayloadQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public CheckoutCompleteWithTokenizedPaymentPayloadQuery payment(PaymentQueryDefinition paymentQueryDefinition) {
            this.startField("payment");
            this._queryBuilder.append('{');
            paymentQueryDefinition.define(new PaymentQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public CheckoutCompleteWithTokenizedPaymentPayloadQuery userErrors(UserErrorQueryDefinition userErrorQueryDefinition) {
            this.startField("userErrors");
            this._queryBuilder.append('{');
            userErrorQueryDefinition.define(new UserErrorQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition {
        public void define(CheckoutCompleteWithTokenizedPaymentPayloadQuery var1);
    }

    public static class CheckoutCreateInput
    implements Serializable {
        private Boolean allowPartialAddresses;
        private List<AttributeInput> customAttributes;
        private String email;
        private List<CheckoutLineItemInput> lineItems;
        private String note;
        private MailingAddressInput shippingAddress;

        public void appendTo(StringBuilder stringBuilder) {
            String string2 = "";
            stringBuilder.append('{');
            if (this.email != null) {
                stringBuilder.append("");
                string2 = ",";
                stringBuilder.append("email:");
                Query.appendQuotedString(stringBuilder, this.email.toString());
            }
            String string3 = string2;
            if (this.lineItems != null) {
                stringBuilder.append(string2);
                string2 = ",";
                stringBuilder.append("lineItems:");
                stringBuilder.append('[');
                string3 = "";
                for (CheckoutLineItemInput serializable : this.lineItems) {
                    stringBuilder.append(string3);
                    string3 = ",";
                    serializable.appendTo(stringBuilder);
                }
                stringBuilder.append(']');
                string3 = string2;
            }
            string2 = string3;
            if (this.shippingAddress != null) {
                stringBuilder.append(string3);
                string2 = ",";
                stringBuilder.append("shippingAddress:");
                this.shippingAddress.appendTo(stringBuilder);
            }
            string3 = string2;
            if (this.note != null) {
                stringBuilder.append(string2);
                string3 = ",";
                stringBuilder.append("note:");
                Query.appendQuotedString(stringBuilder, this.note.toString());
            }
            string2 = string3;
            if (this.customAttributes != null) {
                stringBuilder.append(string3);
                string2 = ",";
                stringBuilder.append("customAttributes:");
                stringBuilder.append('[');
                string3 = "";
                for (AttributeInput attributeInput : this.customAttributes) {
                    stringBuilder.append(string3);
                    string3 = ",";
                    attributeInput.appendTo(stringBuilder);
                }
                stringBuilder.append(']');
            }
            if (this.allowPartialAddresses != null) {
                stringBuilder.append(string2);
                stringBuilder.append("allowPartialAddresses:");
                stringBuilder.append(this.allowPartialAddresses);
            }
            stringBuilder.append('}');
        }

        public CheckoutCreateInput setLineItems(List<CheckoutLineItemInput> list) {
            this.lineItems = list;
            return this;
        }
    }

    public static class CheckoutCreatePayload
    extends AbstractResponse<CheckoutCreatePayload> {
        public CheckoutCreatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutCreatePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 1536904518: {
                        if (!((String)((Object)serializable)).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Checkout(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public Checkout getCheckout() {
            return (Checkout)this.get("checkout");
        }
    }

    public static class CheckoutCreatePayloadQuery
    extends Query<CheckoutCreatePayloadQuery> {
        CheckoutCreatePayloadQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public CheckoutCreatePayloadQuery checkout(CheckoutQueryDefinition checkoutQueryDefinition) {
            this.startField("checkout");
            this._queryBuilder.append('{');
            checkoutQueryDefinition.define(new CheckoutQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface CheckoutCreatePayloadQueryDefinition {
        public void define(CheckoutCreatePayloadQuery var1);
    }

    public static class CheckoutCustomerAssociatePayload
    extends AbstractResponse<CheckoutCustomerAssociatePayload> {
        public CheckoutCustomerAssociatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutCustomerAssociatePayload(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1536904518: {
                        if (!((String)object).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)object).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new Checkout(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 1: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CheckoutCustomerDisassociatePayload
    extends AbstractResponse<CheckoutCustomerDisassociatePayload> {
        public CheckoutCustomerDisassociatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutCustomerDisassociatePayload(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1536904518: {
                        if (!((String)object).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)object).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new Checkout(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 1: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CheckoutEmailUpdatePayload
    extends AbstractResponse<CheckoutEmailUpdatePayload> {
        public CheckoutEmailUpdatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutEmailUpdatePayload(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1536904518: {
                        if (!((String)object).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)object).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new Checkout(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 1: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public Checkout getCheckout() {
            return (Checkout)this.get("checkout");
        }

        public List<UserError> getUserErrors() {
            return (List)this.get("userErrors");
        }
    }

    public static class CheckoutEmailUpdatePayloadQuery
    extends Query<CheckoutEmailUpdatePayloadQuery> {
        CheckoutEmailUpdatePayloadQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public CheckoutEmailUpdatePayloadQuery checkout(CheckoutQueryDefinition checkoutQueryDefinition) {
            this.startField("checkout");
            this._queryBuilder.append('{');
            checkoutQueryDefinition.define(new CheckoutQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public CheckoutEmailUpdatePayloadQuery userErrors(UserErrorQueryDefinition userErrorQueryDefinition) {
            this.startField("userErrors");
            this._queryBuilder.append('{');
            userErrorQueryDefinition.define(new UserErrorQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface CheckoutEmailUpdatePayloadQueryDefinition {
        public void define(CheckoutEmailUpdatePayloadQuery var1);
    }

    public static class CheckoutGiftCardApplyPayload
    extends AbstractResponse<CheckoutGiftCardApplyPayload> {
        public CheckoutGiftCardApplyPayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutGiftCardApplyPayload(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1536904518: {
                        if (!((String)object).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)object).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new Checkout(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 1: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CheckoutGiftCardRemovePayload
    extends AbstractResponse<CheckoutGiftCardRemovePayload> {
        public CheckoutGiftCardRemovePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutGiftCardRemovePayload(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1536904518: {
                        if (!((String)object).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)object).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new Checkout(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 1: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CheckoutLineItem
    extends AbstractResponse<CheckoutLineItem>
    implements Node {
        public CheckoutLineItem() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutLineItem(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block16 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 555169704: {
                        if (!((String)((Object)serializable)).equals("customAttributes")) break;
                        n = 0;
                        break;
                    }
                    case 3355: {
                        if (!((String)((Object)serializable)).equals("id")) break;
                        n = 1;
                        break;
                    }
                    case -1285004149: {
                        if (!((String)((Object)serializable)).equals("quantity")) break;
                        n = 2;
                        break;
                    }
                    case 110371416: {
                        if (!((String)((Object)serializable)).equals("title")) break;
                        n = 3;
                        break;
                    }
                    case 236785797: {
                        if (!((String)((Object)serializable)).equals("variant")) break;
                        n = 4;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 5;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = new ArrayList<Attribute>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new Attribute(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block16;
                    }
                    case 1: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block16;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsInteger(entry.getValue(), string2));
                        continue block16;
                    }
                    case 3: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block16;
                    }
                    case 4: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new ProductVariant(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block16;
                    }
                    case 5: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public Integer getQuantity() {
            return (Integer)this.get("quantity");
        }

        public String getTitle() {
            return (String)this.get("title");
        }

        public ProductVariant getVariant() {
            return (ProductVariant)this.get("variant");
        }
    }

    public static class CheckoutLineItemConnection
    extends AbstractResponse<CheckoutLineItemConnection> {
        public CheckoutLineItemConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutLineItemConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new CheckoutLineItemEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public List<CheckoutLineItemEdge> getEdges() {
            return (List)this.get("edges");
        }
    }

    public static class CheckoutLineItemConnectionQuery
    extends Query<CheckoutLineItemConnectionQuery> {
        CheckoutLineItemConnectionQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public CheckoutLineItemConnectionQuery edges(CheckoutLineItemEdgeQueryDefinition checkoutLineItemEdgeQueryDefinition) {
            this.startField("edges");
            this._queryBuilder.append('{');
            checkoutLineItemEdgeQueryDefinition.define(new CheckoutLineItemEdgeQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface CheckoutLineItemConnectionQueryDefinition {
        public void define(CheckoutLineItemConnectionQuery var1);
    }

    public static class CheckoutLineItemEdge
    extends AbstractResponse<CheckoutLineItemEdge> {
        public CheckoutLineItemEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutLineItemEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new CheckoutLineItem(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public CheckoutLineItem getNode() {
            return (CheckoutLineItem)this.get("node");
        }
    }

    public static class CheckoutLineItemEdgeQuery
    extends Query<CheckoutLineItemEdgeQuery> {
        CheckoutLineItemEdgeQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public CheckoutLineItemEdgeQuery node(CheckoutLineItemQueryDefinition checkoutLineItemQueryDefinition) {
            this.startField("node");
            this._queryBuilder.append('{');
            checkoutLineItemQueryDefinition.define(new CheckoutLineItemQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface CheckoutLineItemEdgeQueryDefinition {
        public void define(CheckoutLineItemEdgeQuery var1);
    }

    public static class CheckoutLineItemInput
    implements Serializable {
        private List<AttributeInput> customAttributes;
        private int quantity;
        private ID variantId;

        public CheckoutLineItemInput(int n, ID iD) {
            this.quantity = n;
            this.variantId = iD;
        }

        public void appendTo(StringBuilder stringBuilder) {
            stringBuilder.append('{');
            stringBuilder.append("");
            stringBuilder.append("quantity:");
            stringBuilder.append(this.quantity);
            stringBuilder.append(",");
            stringBuilder.append("variantId:");
            Query.appendQuotedString(stringBuilder, this.variantId.toString());
            if (this.customAttributes != null) {
                stringBuilder.append(",");
                stringBuilder.append("customAttributes:");
                stringBuilder.append('[');
                String string2 = "";
                for (AttributeInput attributeInput : this.customAttributes) {
                    stringBuilder.append(string2);
                    string2 = ",";
                    attributeInput.appendTo(stringBuilder);
                }
                stringBuilder.append(']');
            }
            stringBuilder.append('}');
        }
    }

    public static class CheckoutLineItemQuery
    extends Query<CheckoutLineItemQuery> {
        CheckoutLineItemQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
            this.startField("id");
        }

        public CheckoutLineItemQuery quantity() {
            this.startField("quantity");
            return this;
        }

        public CheckoutLineItemQuery title() {
            this.startField("title");
            return this;
        }

        public CheckoutLineItemQuery variant(ProductVariantQueryDefinition productVariantQueryDefinition) {
            this.startField("variant");
            this._queryBuilder.append('{');
            productVariantQueryDefinition.define(new ProductVariantQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface CheckoutLineItemQueryDefinition {
        public void define(CheckoutLineItemQuery var1);
    }

    public static class CheckoutLineItemsAddPayload
    extends AbstractResponse<CheckoutLineItemsAddPayload> {
        public CheckoutLineItemsAddPayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutLineItemsAddPayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 1536904518: {
                        if (!((String)((Object)serializable)).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Checkout(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CheckoutLineItemsRemovePayload
    extends AbstractResponse<CheckoutLineItemsRemovePayload> {
        public CheckoutLineItemsRemovePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutLineItemsRemovePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 1536904518: {
                        if (!((String)((Object)serializable)).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Checkout(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CheckoutLineItemsUpdatePayload
    extends AbstractResponse<CheckoutLineItemsUpdatePayload> {
        public CheckoutLineItemsUpdatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutLineItemsUpdatePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 1536904518: {
                        if (!((String)((Object)serializable)).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Checkout(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CheckoutQuery
    extends Query<CheckoutQuery> {
        CheckoutQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
            this.startField("id");
        }

        static /* synthetic */ void lambda$lineItems$0(LineItemsArguments lineItemsArguments) {
        }

        public CheckoutQuery availableShippingRates(AvailableShippingRatesQueryDefinition availableShippingRatesQueryDefinition) {
            this.startField("availableShippingRates");
            this._queryBuilder.append('{');
            availableShippingRatesQueryDefinition.define(new AvailableShippingRatesQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public CheckoutQuery currencyCode() {
            this.startField("currencyCode");
            return this;
        }

        public CheckoutQuery lineItems(int n, CheckoutLineItemConnectionQueryDefinition checkoutLineItemConnectionQueryDefinition) {
            return this.lineItems(n, Storefront$CheckoutQuery$$Lambda$1.lambdaFactory$(), checkoutLineItemConnectionQueryDefinition);
        }

        public CheckoutQuery lineItems(int n, LineItemsArgumentsDefinition lineItemsArgumentsDefinition, CheckoutLineItemConnectionQueryDefinition checkoutLineItemConnectionQueryDefinition) {
            this.startField("lineItems");
            this._queryBuilder.append("(first:");
            this._queryBuilder.append(n);
            lineItemsArgumentsDefinition.define(new LineItemsArguments(this._queryBuilder));
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            checkoutLineItemConnectionQueryDefinition.define(new CheckoutLineItemConnectionQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public CheckoutQuery requiresShipping() {
            this.startField("requiresShipping");
            return this;
        }

        public CheckoutQuery shippingLine(ShippingRateQueryDefinition shippingRateQueryDefinition) {
            this.startField("shippingLine");
            this._queryBuilder.append('{');
            shippingRateQueryDefinition.define(new ShippingRateQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public CheckoutQuery subtotalPrice() {
            this.startField("subtotalPrice");
            return this;
        }

        public CheckoutQuery totalPrice() {
            this.startField("totalPrice");
            return this;
        }

        public CheckoutQuery totalTax() {
            this.startField("totalTax");
            return this;
        }

        public CheckoutQuery webUrl() {
            this.startField("webUrl");
            return this;
        }

        public class LineItemsArguments
        extends Arguments {
            LineItemsArguments(StringBuilder stringBuilder) {
                super(stringBuilder, false);
            }
        }

        public static interface LineItemsArgumentsDefinition {
            public void define(LineItemsArguments var1);
        }

    }

    public static interface CheckoutQueryDefinition {
        public void define(CheckoutQuery var1);
    }

    public static class CheckoutShippingAddressUpdatePayload
    extends AbstractResponse<CheckoutShippingAddressUpdatePayload> {
        public CheckoutShippingAddressUpdatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutShippingAddressUpdatePayload(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1536904518: {
                        if (!((String)object).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)object).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new Checkout(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 1: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public Checkout getCheckout() {
            return (Checkout)this.get("checkout");
        }

        public List<UserError> getUserErrors() {
            return (List)this.get("userErrors");
        }
    }

    public static class CheckoutShippingAddressUpdatePayloadQuery
    extends Query<CheckoutShippingAddressUpdatePayloadQuery> {
        CheckoutShippingAddressUpdatePayloadQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public CheckoutShippingAddressUpdatePayloadQuery checkout(CheckoutQueryDefinition checkoutQueryDefinition) {
            this.startField("checkout");
            this._queryBuilder.append('{');
            checkoutQueryDefinition.define(new CheckoutQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public CheckoutShippingAddressUpdatePayloadQuery userErrors(UserErrorQueryDefinition userErrorQueryDefinition) {
            this.startField("userErrors");
            this._queryBuilder.append('{');
            userErrorQueryDefinition.define(new UserErrorQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface CheckoutShippingAddressUpdatePayloadQueryDefinition {
        public void define(CheckoutShippingAddressUpdatePayloadQuery var1);
    }

    public static class CheckoutShippingLineUpdatePayload
    extends AbstractResponse<CheckoutShippingLineUpdatePayload> {
        public CheckoutShippingLineUpdatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CheckoutShippingLineUpdatePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 1536904518: {
                        if (!((String)((Object)serializable)).equals("checkout")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Checkout(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public Checkout getCheckout() {
            return (Checkout)this.get("checkout");
        }

        public List<UserError> getUserErrors() {
            return (List)this.get("userErrors");
        }
    }

    public static class CheckoutShippingLineUpdatePayloadQuery
    extends Query<CheckoutShippingLineUpdatePayloadQuery> {
        CheckoutShippingLineUpdatePayloadQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public CheckoutShippingLineUpdatePayloadQuery checkout(CheckoutQueryDefinition checkoutQueryDefinition) {
            this.startField("checkout");
            this._queryBuilder.append('{');
            checkoutQueryDefinition.define(new CheckoutQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public CheckoutShippingLineUpdatePayloadQuery userErrors(UserErrorQueryDefinition userErrorQueryDefinition) {
            this.startField("userErrors");
            this._queryBuilder.append('{');
            userErrorQueryDefinition.define(new UserErrorQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface CheckoutShippingLineUpdatePayloadQueryDefinition {
        public void define(CheckoutShippingLineUpdatePayloadQuery var1);
    }

    public static class Collection
    extends AbstractResponse<Collection>
    implements Node {
        public Collection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Collection(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block22 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -1724546052: {
                        if (!((String)object).equals("description")) break;
                        n = 0;
                        break;
                    }
                    case 985506247: {
                        if (!((String)object).equals("descriptionHtml")) break;
                        n = 1;
                        break;
                    }
                    case -1224577496: {
                        if (!((String)object).equals("handle")) break;
                        n = 2;
                        break;
                    }
                    case 3355: {
                        if (!((String)object).equals("id")) break;
                        n = 3;
                        break;
                    }
                    case 100313435: {
                        if (!((String)object).equals("image")) break;
                        n = 4;
                        break;
                    }
                    case -1003761308: {
                        if (!((String)object).equals("products")) break;
                        n = 5;
                        break;
                    }
                    case 110371416: {
                        if (!((String)object).equals("title")) break;
                        n = 6;
                        break;
                    }
                    case -1949194674: {
                        if (!((String)object).equals("updatedAt")) break;
                        n = 7;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 8;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block22;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block22;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block22;
                    }
                    case 3: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block22;
                    }
                    case 4: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new Image(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block22;
                    }
                    case 5: {
                        this.responseData.put(string2, new ProductConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block22;
                    }
                    case 6: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block22;
                    }
                    case 7: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block22;
                    }
                    case 8: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public String getDescription() {
            return (String)this.get("description");
        }

        public ID getId() {
            return (ID)this.get("id");
        }

        public Image getImage() {
            return (Image)this.get("image");
        }

        public ProductConnection getProducts() {
            return (ProductConnection)this.get("products");
        }

        public String getTitle() {
            return (String)this.get("title");
        }
    }

    public static class CollectionConnection
    extends AbstractResponse<CollectionConnection> {
        public CollectionConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CollectionConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new CollectionEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public List<CollectionEdge> getEdges() {
            return (List)this.get("edges");
        }
    }

    public static class CollectionConnectionQuery
    extends Query<CollectionConnectionQuery> {
        CollectionConnectionQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public CollectionConnectionQuery edges(CollectionEdgeQueryDefinition collectionEdgeQueryDefinition) {
            this.startField("edges");
            this._queryBuilder.append('{');
            collectionEdgeQueryDefinition.define(new CollectionEdgeQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface CollectionConnectionQueryDefinition {
        public void define(CollectionConnectionQuery var1);
    }

    public static class CollectionEdge
    extends AbstractResponse<CollectionEdge> {
        public CollectionEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CollectionEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new Collection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public String getCursor() {
            return (String)this.get("cursor");
        }

        public Collection getNode() {
            return (Collection)this.get("node");
        }
    }

    public static class CollectionEdgeQuery
    extends Query<CollectionEdgeQuery> {
        CollectionEdgeQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public CollectionEdgeQuery cursor() {
            this.startField("cursor");
            return this;
        }

        public CollectionEdgeQuery node(CollectionQueryDefinition collectionQueryDefinition) {
            this.startField("node");
            this._queryBuilder.append('{');
            collectionQueryDefinition.define(new CollectionQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface CollectionEdgeQueryDefinition {
        public void define(CollectionEdgeQuery var1);
    }

    public static class CollectionQuery
    extends Query<CollectionQuery> {
        CollectionQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
            this.startField("id");
        }

        static /* synthetic */ void lambda$description$0(DescriptionArguments descriptionArguments) {
        }

        static /* synthetic */ void lambda$image$1(ImageArguments imageArguments) {
        }

        static /* synthetic */ void lambda$products$2(ProductsArguments productsArguments) {
        }

        public CollectionQuery description() {
            return this.description(Storefront$CollectionQuery$$Lambda$1.lambdaFactory$());
        }

        public CollectionQuery description(DescriptionArgumentsDefinition descriptionArgumentsDefinition) {
            this.startField("description");
            DescriptionArguments descriptionArguments = new DescriptionArguments(this._queryBuilder);
            descriptionArgumentsDefinition.define(descriptionArguments);
            DescriptionArguments.end(descriptionArguments);
            return this;
        }

        public CollectionQuery image(ImageArgumentsDefinition imageArgumentsDefinition, ImageQueryDefinition imageQueryDefinition) {
            this.startField("image");
            ImageArguments imageArguments = new ImageArguments(this._queryBuilder);
            imageArgumentsDefinition.define(imageArguments);
            ImageArguments.end(imageArguments);
            this._queryBuilder.append('{');
            imageQueryDefinition.define(new ImageQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public CollectionQuery image(ImageQueryDefinition imageQueryDefinition) {
            return this.image(Storefront$CollectionQuery$$Lambda$2.lambdaFactory$(), imageQueryDefinition);
        }

        public CollectionQuery products(int n, ProductsArgumentsDefinition productsArgumentsDefinition, ProductConnectionQueryDefinition productConnectionQueryDefinition) {
            this.startField("products");
            this._queryBuilder.append("(first:");
            this._queryBuilder.append(n);
            productsArgumentsDefinition.define(new ProductsArguments(this._queryBuilder));
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            productConnectionQueryDefinition.define(new ProductConnectionQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public CollectionQuery products(int n, ProductConnectionQueryDefinition productConnectionQueryDefinition) {
            return this.products(n, Storefront$CollectionQuery$$Lambda$3.lambdaFactory$(), productConnectionQueryDefinition);
        }

        public CollectionQuery title() {
            this.startField("title");
            return this;
        }

        public class DescriptionArguments
        extends Arguments {
            DescriptionArguments(StringBuilder stringBuilder) {
                super(stringBuilder, true);
            }
        }

        public static interface DescriptionArgumentsDefinition {
            public void define(DescriptionArguments var1);
        }

        public class ImageArguments
        extends Arguments {
            ImageArguments(StringBuilder stringBuilder) {
                super(stringBuilder, true);
            }
        }

        public static interface ImageArgumentsDefinition {
            public void define(ImageArguments var1);
        }

        public class ProductsArguments
        extends Arguments {
            ProductsArguments(StringBuilder stringBuilder) {
                super(stringBuilder, false);
            }

            public ProductsArguments after(String string2) {
                if (string2 != null) {
                    this.startArgument("after");
                    Query.appendQuotedString(CollectionQuery.this._queryBuilder, string2.toString());
                }
                return this;
            }
        }

        public static interface ProductsArgumentsDefinition {
            public void define(ProductsArguments var1);
        }

    }

    public static interface CollectionQueryDefinition {
        public void define(CollectionQuery var1);
    }

    public static enum CollectionSortKeys {
        ID,
        RELEVANCE,
        TITLE,
        UPDATED_AT,
        UNKNOWN_VALUE;


        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case ID: {
                    return "ID";
                }
                case RELEVANCE: {
                    return "RELEVANCE";
                }
                case TITLE: {
                    return "TITLE";
                }
                case UPDATED_AT: 
            }
            return "UPDATED_AT";
        }
    }

    public static class Comment
    extends AbstractResponse<Comment>
    implements Node {
        public Comment() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Comment(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block14 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1406328437: {
                        if (!string3.equals("author")) break;
                        n = 0;
                        break;
                    }
                    case 951530617: {
                        if (!string3.equals("content")) break;
                        n = 1;
                        break;
                    }
                    case -389493820: {
                        if (!string3.equals("contentHtml")) break;
                        n = 2;
                        break;
                    }
                    case 3355: {
                        if (!string3.equals("id")) break;
                        n = 3;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 4;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new CommentAuthor(this.jsonAsObject(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block14;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block14;
                    }
                    case 3: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 4: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CommentAuthor
    extends AbstractResponse<CommentAuthor> {
        public CommentAuthor() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CommentAuthor(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case 96619420: {
                        if (!string3.equals("email")) break;
                        n = 0;
                        break;
                    }
                    case 3373707: {
                        if (!string3.equals("name")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CommentConnection
    extends AbstractResponse<CommentConnection> {
        public CommentConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CommentConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new CommentEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CommentEdge
    extends AbstractResponse<CommentEdge> {
        public CommentEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CommentEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new Comment(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static enum CountryCode {
        AD,
        AE,
        AF,
        AG,
        AI,
        AL,
        AM,
        AN,
        AO,
        AR,
        AT,
        AU,
        AW,
        AX,
        AZ,
        BA,
        BB,
        BD,
        BE,
        BF,
        BG,
        BH,
        BI,
        BJ,
        BL,
        BM,
        BN,
        BO,
        BR,
        BS,
        BT,
        BV,
        BW,
        BY,
        BZ,
        CA,
        CC,
        CD,
        CF,
        CG,
        CH,
        CI,
        CK,
        CL,
        CM,
        CN,
        CO,
        CR,
        CU,
        CV,
        CW,
        CX,
        CY,
        CZ,
        DE,
        DJ,
        DK,
        DM,
        DO,
        DZ,
        EC,
        EE,
        EG,
        EH,
        ER,
        ES,
        ET,
        FI,
        FJ,
        FK,
        FO,
        FR,
        GA,
        GB,
        GD,
        GE,
        GF,
        GG,
        GH,
        GI,
        GL,
        GM,
        GN,
        GP,
        GQ,
        GR,
        GS,
        GT,
        GW,
        GY,
        HK,
        HM,
        HN,
        HR,
        HT,
        HU,
        ID,
        IE,
        IL,
        IM,
        IN,
        IO,
        IQ,
        IR,
        IS,
        IT,
        JE,
        JM,
        JO,
        JP,
        KE,
        KG,
        KH,
        KI,
        KM,
        KN,
        KP,
        KR,
        KW,
        KY,
        KZ,
        LA,
        LB,
        LC,
        LI,
        LK,
        LR,
        LS,
        LT,
        LU,
        LV,
        LY,
        MA,
        MC,
        MD,
        ME,
        MF,
        MG,
        MK,
        ML,
        MM,
        MN,
        MO,
        MQ,
        MR,
        MS,
        MT,
        MU,
        MV,
        MW,
        MX,
        MY,
        MZ,
        NA,
        NC,
        NE,
        NF,
        NG,
        NI,
        NL,
        NO,
        NP,
        NR,
        NU,
        NZ,
        OM,
        PA,
        PE,
        PF,
        PG,
        PH,
        PK,
        PL,
        PM,
        PN,
        PS,
        PT,
        PY,
        QA,
        RE,
        RO,
        RS,
        RU,
        RW,
        SA,
        SB,
        SC,
        SD,
        SE,
        SG,
        SH,
        SI,
        SJ,
        SK,
        SL,
        SM,
        SN,
        SO,
        SR,
        SS,
        ST,
        SV,
        SX,
        SY,
        SZ,
        TC,
        TD,
        TF,
        TG,
        TH,
        TJ,
        TK,
        TL,
        TM,
        TN,
        TO,
        TR,
        TT,
        TV,
        TW,
        TZ,
        UA,
        UG,
        UM,
        US,
        UY,
        UZ,
        VA,
        VC,
        VE,
        VG,
        VN,
        VU,
        WF,
        WS,
        XK,
        YE,
        YT,
        ZA,
        ZM,
        ZW,
        UNKNOWN_VALUE;


        /*
         * Enabled aggressive block sorting
         */
        public static CountryCode fromGraphQl(String string2) {
            if (string2 == null) {
                return null;
            }
            int n = -1;
            switch (string2.hashCode()) {
                case 2083: {
                    if (!string2.equals("AD")) break;
                    n = 0;
                    break;
                }
                case 2084: {
                    if (!string2.equals("AE")) break;
                    n = 1;
                    break;
                }
                case 2085: {
                    if (!string2.equals("AF")) break;
                    n = 2;
                    break;
                }
                case 2086: {
                    if (!string2.equals("AG")) break;
                    n = 3;
                    break;
                }
                case 2088: {
                    if (!string2.equals("AI")) break;
                    n = 4;
                    break;
                }
                case 2091: {
                    if (!string2.equals("AL")) break;
                    n = 5;
                    break;
                }
                case 2092: {
                    if (!string2.equals("AM")) break;
                    n = 6;
                    break;
                }
                case 2093: {
                    if (!string2.equals("AN")) break;
                    n = 7;
                    break;
                }
                case 2094: {
                    if (!string2.equals("AO")) break;
                    n = 8;
                    break;
                }
                case 2097: {
                    if (!string2.equals("AR")) break;
                    n = 9;
                    break;
                }
                case 2099: {
                    if (!string2.equals("AT")) break;
                    n = 10;
                    break;
                }
                case 2100: {
                    if (!string2.equals("AU")) break;
                    n = 11;
                    break;
                }
                case 2102: {
                    if (!string2.equals("AW")) break;
                    n = 12;
                    break;
                }
                case 2103: {
                    if (!string2.equals("AX")) break;
                    n = 13;
                    break;
                }
                case 2105: {
                    if (!string2.equals("AZ")) break;
                    n = 14;
                    break;
                }
                case 2111: {
                    if (!string2.equals("BA")) break;
                    n = 15;
                    break;
                }
                case 2112: {
                    if (!string2.equals("BB")) break;
                    n = 16;
                    break;
                }
                case 2114: {
                    if (!string2.equals("BD")) break;
                    n = 17;
                    break;
                }
                case 2115: {
                    if (!string2.equals("BE")) break;
                    n = 18;
                    break;
                }
                case 2116: {
                    if (!string2.equals("BF")) break;
                    n = 19;
                    break;
                }
                case 2117: {
                    if (!string2.equals("BG")) break;
                    n = 20;
                    break;
                }
                case 2118: {
                    if (!string2.equals("BH")) break;
                    n = 21;
                    break;
                }
                case 2119: {
                    if (!string2.equals("BI")) break;
                    n = 22;
                    break;
                }
                case 2120: {
                    if (!string2.equals("BJ")) break;
                    n = 23;
                    break;
                }
                case 2122: {
                    if (!string2.equals("BL")) break;
                    n = 24;
                    break;
                }
                case 2123: {
                    if (!string2.equals("BM")) break;
                    n = 25;
                    break;
                }
                case 2124: {
                    if (!string2.equals("BN")) break;
                    n = 26;
                    break;
                }
                case 2125: {
                    if (!string2.equals("BO")) break;
                    n = 27;
                    break;
                }
                case 2128: {
                    if (!string2.equals("BR")) break;
                    n = 28;
                    break;
                }
                case 2129: {
                    if (!string2.equals("BS")) break;
                    n = 29;
                    break;
                }
                case 2130: {
                    if (!string2.equals("BT")) break;
                    n = 30;
                    break;
                }
                case 2132: {
                    if (!string2.equals("BV")) break;
                    n = 31;
                    break;
                }
                case 2133: {
                    if (!string2.equals("BW")) break;
                    n = 32;
                    break;
                }
                case 2135: {
                    if (!string2.equals("BY")) break;
                    n = 33;
                    break;
                }
                case 2136: {
                    if (!string2.equals("BZ")) break;
                    n = 34;
                    break;
                }
                case 2142: {
                    if (!string2.equals("CA")) break;
                    n = 35;
                    break;
                }
                case 2144: {
                    if (!string2.equals("CC")) break;
                    n = 36;
                    break;
                }
                case 2145: {
                    if (!string2.equals("CD")) break;
                    n = 37;
                    break;
                }
                case 2147: {
                    if (!string2.equals("CF")) break;
                    n = 38;
                    break;
                }
                case 2148: {
                    if (!string2.equals("CG")) break;
                    n = 39;
                    break;
                }
                case 2149: {
                    if (!string2.equals("CH")) break;
                    n = 40;
                    break;
                }
                case 2150: {
                    if (!string2.equals("CI")) break;
                    n = 41;
                    break;
                }
                case 2152: {
                    if (!string2.equals("CK")) break;
                    n = 42;
                    break;
                }
                case 2153: {
                    if (!string2.equals("CL")) break;
                    n = 43;
                    break;
                }
                case 2154: {
                    if (!string2.equals("CM")) break;
                    n = 44;
                    break;
                }
                case 2155: {
                    if (!string2.equals("CN")) break;
                    n = 45;
                    break;
                }
                case 2156: {
                    if (!string2.equals("CO")) break;
                    n = 46;
                    break;
                }
                case 2159: {
                    if (!string2.equals("CR")) break;
                    n = 47;
                    break;
                }
                case 2162: {
                    if (!string2.equals("CU")) break;
                    n = 48;
                    break;
                }
                case 2163: {
                    if (!string2.equals("CV")) break;
                    n = 49;
                    break;
                }
                case 2164: {
                    if (!string2.equals("CW")) break;
                    n = 50;
                    break;
                }
                case 2165: {
                    if (!string2.equals("CX")) break;
                    n = 51;
                    break;
                }
                case 2166: {
                    if (!string2.equals("CY")) break;
                    n = 52;
                    break;
                }
                case 2167: {
                    if (!string2.equals("CZ")) break;
                    n = 53;
                    break;
                }
                case 2177: {
                    if (!string2.equals("DE")) break;
                    n = 54;
                    break;
                }
                case 2182: {
                    if (!string2.equals("DJ")) break;
                    n = 55;
                    break;
                }
                case 2183: {
                    if (!string2.equals("DK")) break;
                    n = 56;
                    break;
                }
                case 2185: {
                    if (!string2.equals("DM")) break;
                    n = 57;
                    break;
                }
                case 2187: {
                    if (!string2.equals("DO")) break;
                    n = 58;
                    break;
                }
                case 2198: {
                    if (!string2.equals("DZ")) break;
                    n = 59;
                    break;
                }
                case 2206: {
                    if (!string2.equals("EC")) break;
                    n = 60;
                    break;
                }
                case 2208: {
                    if (!string2.equals("EE")) break;
                    n = 61;
                    break;
                }
                case 2210: {
                    if (!string2.equals("EG")) break;
                    n = 62;
                    break;
                }
                case 2211: {
                    if (!string2.equals("EH")) break;
                    n = 63;
                    break;
                }
                case 2221: {
                    if (!string2.equals("ER")) break;
                    n = 64;
                    break;
                }
                case 2222: {
                    if (!string2.equals("ES")) break;
                    n = 65;
                    break;
                }
                case 2223: {
                    if (!string2.equals("ET")) break;
                    n = 66;
                    break;
                }
                case 2243: {
                    if (!string2.equals("FI")) break;
                    n = 67;
                    break;
                }
                case 2244: {
                    if (!string2.equals("FJ")) break;
                    n = 68;
                    break;
                }
                case 2245: {
                    if (!string2.equals("FK")) break;
                    n = 69;
                    break;
                }
                case 2249: {
                    if (!string2.equals("FO")) break;
                    n = 70;
                    break;
                }
                case 2252: {
                    if (!string2.equals("FR")) break;
                    n = 71;
                    break;
                }
                case 2266: {
                    if (!string2.equals("GA")) break;
                    n = 72;
                    break;
                }
                case 2267: {
                    if (!string2.equals("GB")) break;
                    n = 73;
                    break;
                }
                case 2269: {
                    if (!string2.equals("GD")) break;
                    n = 74;
                    break;
                }
                case 2270: {
                    if (!string2.equals("GE")) break;
                    n = 75;
                    break;
                }
                case 2271: {
                    if (!string2.equals("GF")) break;
                    n = 76;
                    break;
                }
                case 2272: {
                    if (!string2.equals("GG")) break;
                    n = 77;
                    break;
                }
                case 2273: {
                    if (!string2.equals("GH")) break;
                    n = 78;
                    break;
                }
                case 2274: {
                    if (!string2.equals("GI")) break;
                    n = 79;
                    break;
                }
                case 2277: {
                    if (!string2.equals("GL")) break;
                    n = 80;
                    break;
                }
                case 2278: {
                    if (!string2.equals("GM")) break;
                    n = 81;
                    break;
                }
                case 2279: {
                    if (!string2.equals("GN")) break;
                    n = 82;
                    break;
                }
                case 2281: {
                    if (!string2.equals("GP")) break;
                    n = 83;
                    break;
                }
                case 2282: {
                    if (!string2.equals("GQ")) break;
                    n = 84;
                    break;
                }
                case 2283: {
                    if (!string2.equals("GR")) break;
                    n = 85;
                    break;
                }
                case 2284: {
                    if (!string2.equals("GS")) break;
                    n = 86;
                    break;
                }
                case 2285: {
                    if (!string2.equals("GT")) break;
                    n = 87;
                    break;
                }
                case 2288: {
                    if (!string2.equals("GW")) break;
                    n = 88;
                    break;
                }
                case 2290: {
                    if (!string2.equals("GY")) break;
                    n = 89;
                    break;
                }
                case 2307: {
                    if (!string2.equals("HK")) break;
                    n = 90;
                    break;
                }
                case 2309: {
                    if (!string2.equals("HM")) break;
                    n = 91;
                    break;
                }
                case 2310: {
                    if (!string2.equals("HN")) break;
                    n = 92;
                    break;
                }
                case 2314: {
                    if (!string2.equals("HR")) break;
                    n = 93;
                    break;
                }
                case 2316: {
                    if (!string2.equals("HT")) break;
                    n = 94;
                    break;
                }
                case 2317: {
                    if (!string2.equals("HU")) break;
                    n = 95;
                    break;
                }
                case 2331: {
                    if (!string2.equals("ID")) break;
                    n = 96;
                    break;
                }
                case 2332: {
                    if (!string2.equals("IE")) break;
                    n = 97;
                    break;
                }
                case 2339: {
                    if (!string2.equals("IL")) break;
                    n = 98;
                    break;
                }
                case 2340: {
                    if (!string2.equals("IM")) break;
                    n = 99;
                    break;
                }
                case 2341: {
                    if (!string2.equals("IN")) break;
                    n = 100;
                    break;
                }
                case 2342: {
                    if (!string2.equals("IO")) break;
                    n = 101;
                    break;
                }
                case 2344: {
                    if (!string2.equals("IQ")) break;
                    n = 102;
                    break;
                }
                case 2345: {
                    if (!string2.equals("IR")) break;
                    n = 103;
                    break;
                }
                case 2346: {
                    if (!string2.equals("IS")) break;
                    n = 104;
                    break;
                }
                case 2347: {
                    if (!string2.equals("IT")) break;
                    n = 105;
                    break;
                }
                case 2363: {
                    if (!string2.equals("JE")) break;
                    n = 106;
                    break;
                }
                case 2371: {
                    if (!string2.equals("JM")) break;
                    n = 107;
                    break;
                }
                case 2373: {
                    if (!string2.equals("JO")) break;
                    n = 108;
                    break;
                }
                case 2374: {
                    if (!string2.equals("JP")) break;
                    n = 109;
                    break;
                }
                case 2394: {
                    if (!string2.equals("KE")) break;
                    n = 110;
                    break;
                }
                case 2396: {
                    if (!string2.equals("KG")) break;
                    n = 111;
                    break;
                }
                case 2397: {
                    if (!string2.equals("KH")) break;
                    n = 112;
                    break;
                }
                case 2398: {
                    if (!string2.equals("KI")) break;
                    n = 113;
                    break;
                }
                case 2402: {
                    if (!string2.equals("KM")) break;
                    n = 114;
                    break;
                }
                case 2403: {
                    if (!string2.equals("KN")) break;
                    n = 115;
                    break;
                }
                case 2405: {
                    if (!string2.equals("KP")) break;
                    n = 116;
                    break;
                }
                case 2407: {
                    if (!string2.equals("KR")) break;
                    n = 117;
                    break;
                }
                case 2412: {
                    if (!string2.equals("KW")) break;
                    n = 118;
                    break;
                }
                case 2414: {
                    if (!string2.equals("KY")) break;
                    n = 119;
                    break;
                }
                case 2415: {
                    if (!string2.equals("KZ")) break;
                    n = 120;
                    break;
                }
                case 2421: {
                    if (!string2.equals("LA")) break;
                    n = 121;
                    break;
                }
                case 2422: {
                    if (!string2.equals("LB")) break;
                    n = 122;
                    break;
                }
                case 2423: {
                    if (!string2.equals("LC")) break;
                    n = 123;
                    break;
                }
                case 2429: {
                    if (!string2.equals("LI")) break;
                    n = 124;
                    break;
                }
                case 2431: {
                    if (!string2.equals("LK")) break;
                    n = 125;
                    break;
                }
                case 2438: {
                    if (!string2.equals("LR")) break;
                    n = 126;
                    break;
                }
                case 2439: {
                    if (!string2.equals("LS")) break;
                    n = 127;
                    break;
                }
                case 2440: {
                    if (!string2.equals("LT")) break;
                    n = 128;
                    break;
                }
                case 2441: {
                    if (!string2.equals("LU")) break;
                    n = 129;
                    break;
                }
                case 2442: {
                    if (!string2.equals("LV")) break;
                    n = 130;
                    break;
                }
                case 2445: {
                    if (!string2.equals("LY")) break;
                    n = 131;
                    break;
                }
                case 2452: {
                    if (!string2.equals("MA")) break;
                    n = 132;
                    break;
                }
                case 2454: {
                    if (!string2.equals("MC")) break;
                    n = 133;
                    break;
                }
                case 2455: {
                    if (!string2.equals("MD")) break;
                    n = 134;
                    break;
                }
                case 2456: {
                    if (!string2.equals("ME")) break;
                    n = 135;
                    break;
                }
                case 2457: {
                    if (!string2.equals("MF")) break;
                    n = 136;
                    break;
                }
                case 2458: {
                    if (!string2.equals("MG")) break;
                    n = 137;
                    break;
                }
                case 2462: {
                    if (!string2.equals("MK")) break;
                    n = 138;
                    break;
                }
                case 2463: {
                    if (!string2.equals("ML")) break;
                    n = 139;
                    break;
                }
                case 2464: {
                    if (!string2.equals("MM")) break;
                    n = 140;
                    break;
                }
                case 2465: {
                    if (!string2.equals("MN")) break;
                    n = 141;
                    break;
                }
                case 2466: {
                    if (!string2.equals("MO")) break;
                    n = 142;
                    break;
                }
                case 2468: {
                    if (!string2.equals("MQ")) break;
                    n = 143;
                    break;
                }
                case 2469: {
                    if (!string2.equals("MR")) break;
                    n = 144;
                    break;
                }
                case 2470: {
                    if (!string2.equals("MS")) break;
                    n = 145;
                    break;
                }
                case 2471: {
                    if (!string2.equals("MT")) break;
                    n = 146;
                    break;
                }
                case 2472: {
                    if (!string2.equals("MU")) break;
                    n = 147;
                    break;
                }
                case 2473: {
                    if (!string2.equals("MV")) break;
                    n = 148;
                    break;
                }
                case 2474: {
                    if (!string2.equals("MW")) break;
                    n = 149;
                    break;
                }
                case 2475: {
                    if (!string2.equals("MX")) break;
                    n = 150;
                    break;
                }
                case 2476: {
                    if (!string2.equals("MY")) break;
                    n = 151;
                    break;
                }
                case 2477: {
                    if (!string2.equals("MZ")) break;
                    n = 152;
                    break;
                }
                case 2483: {
                    if (!string2.equals("NA")) break;
                    n = 153;
                    break;
                }
                case 2485: {
                    if (!string2.equals("NC")) break;
                    n = 154;
                    break;
                }
                case 2487: {
                    if (!string2.equals("NE")) break;
                    n = 155;
                    break;
                }
                case 2488: {
                    if (!string2.equals("NF")) break;
                    n = 156;
                    break;
                }
                case 2489: {
                    if (!string2.equals("NG")) break;
                    n = 157;
                    break;
                }
                case 2491: {
                    if (!string2.equals("NI")) break;
                    n = 158;
                    break;
                }
                case 2494: {
                    if (!string2.equals("NL")) break;
                    n = 159;
                    break;
                }
                case 2497: {
                    if (!string2.equals("NO")) break;
                    n = 160;
                    break;
                }
                case 2498: {
                    if (!string2.equals("NP")) break;
                    n = 161;
                    break;
                }
                case 2500: {
                    if (!string2.equals("NR")) break;
                    n = 162;
                    break;
                }
                case 2503: {
                    if (!string2.equals("NU")) break;
                    n = 163;
                    break;
                }
                case 2508: {
                    if (!string2.equals("NZ")) break;
                    n = 164;
                    break;
                }
                case 2526: {
                    if (!string2.equals("OM")) break;
                    n = 165;
                    break;
                }
                case 2545: {
                    if (!string2.equals("PA")) break;
                    n = 166;
                    break;
                }
                case 2549: {
                    if (!string2.equals("PE")) break;
                    n = 167;
                    break;
                }
                case 2550: {
                    if (!string2.equals("PF")) break;
                    n = 168;
                    break;
                }
                case 2551: {
                    if (!string2.equals("PG")) break;
                    n = 169;
                    break;
                }
                case 2552: {
                    if (!string2.equals("PH")) break;
                    n = 170;
                    break;
                }
                case 2555: {
                    if (!string2.equals("PK")) break;
                    n = 171;
                    break;
                }
                case 2556: {
                    if (!string2.equals("PL")) break;
                    n = 172;
                    break;
                }
                case 2557: {
                    if (!string2.equals("PM")) break;
                    n = 173;
                    break;
                }
                case 2558: {
                    if (!string2.equals("PN")) break;
                    n = 174;
                    break;
                }
                case 2563: {
                    if (!string2.equals("PS")) break;
                    n = 175;
                    break;
                }
                case 2564: {
                    if (!string2.equals("PT")) break;
                    n = 176;
                    break;
                }
                case 2569: {
                    if (!string2.equals("PY")) break;
                    n = 177;
                    break;
                }
                case 2576: {
                    if (!string2.equals("QA")) break;
                    n = 178;
                    break;
                }
                case 2611: {
                    if (!string2.equals("RE")) break;
                    n = 179;
                    break;
                }
                case 2621: {
                    if (!string2.equals("RO")) break;
                    n = 180;
                    break;
                }
                case 2625: {
                    if (!string2.equals("RS")) break;
                    n = 181;
                    break;
                }
                case 2627: {
                    if (!string2.equals("RU")) break;
                    n = 182;
                    break;
                }
                case 2629: {
                    if (!string2.equals("RW")) break;
                    n = 183;
                    break;
                }
                case 2638: {
                    if (!string2.equals("SA")) break;
                    n = 184;
                    break;
                }
                case 2639: {
                    if (!string2.equals("SB")) break;
                    n = 185;
                    break;
                }
                case 2640: {
                    if (!string2.equals("SC")) break;
                    n = 186;
                    break;
                }
                case 2641: {
                    if (!string2.equals("SD")) break;
                    n = 187;
                    break;
                }
                case 2642: {
                    if (!string2.equals("SE")) break;
                    n = 188;
                    break;
                }
                case 2644: {
                    if (!string2.equals("SG")) break;
                    n = 189;
                    break;
                }
                case 2645: {
                    if (!string2.equals("SH")) break;
                    n = 190;
                    break;
                }
                case 2646: {
                    if (!string2.equals("SI")) break;
                    n = 191;
                    break;
                }
                case 2647: {
                    if (!string2.equals("SJ")) break;
                    n = 192;
                    break;
                }
                case 2648: {
                    if (!string2.equals("SK")) break;
                    n = 193;
                    break;
                }
                case 2649: {
                    if (!string2.equals("SL")) break;
                    n = 194;
                    break;
                }
                case 2650: {
                    if (!string2.equals("SM")) break;
                    n = 195;
                    break;
                }
                case 2651: {
                    if (!string2.equals("SN")) break;
                    n = 196;
                    break;
                }
                case 2652: {
                    if (!string2.equals("SO")) break;
                    n = 197;
                    break;
                }
                case 2655: {
                    if (!string2.equals("SR")) break;
                    n = 198;
                    break;
                }
                case 2656: {
                    if (!string2.equals("SS")) break;
                    n = 199;
                    break;
                }
                case 2657: {
                    if (!string2.equals("ST")) break;
                    n = 200;
                    break;
                }
                case 2659: {
                    if (!string2.equals("SV")) break;
                    n = 201;
                    break;
                }
                case 2661: {
                    if (!string2.equals("SX")) break;
                    n = 202;
                    break;
                }
                case 2662: {
                    if (!string2.equals("SY")) break;
                    n = 203;
                    break;
                }
                case 2663: {
                    if (!string2.equals("SZ")) break;
                    n = 204;
                    break;
                }
                case 2671: {
                    if (!string2.equals("TC")) break;
                    n = 205;
                    break;
                }
                case 2672: {
                    if (!string2.equals("TD")) break;
                    n = 206;
                    break;
                }
                case 2674: {
                    if (!string2.equals("TF")) break;
                    n = 207;
                    break;
                }
                case 2675: {
                    if (!string2.equals("TG")) break;
                    n = 208;
                    break;
                }
                case 2676: {
                    if (!string2.equals("TH")) break;
                    n = 209;
                    break;
                }
                case 2678: {
                    if (!string2.equals("TJ")) break;
                    n = 210;
                    break;
                }
                case 2679: {
                    if (!string2.equals("TK")) break;
                    n = 211;
                    break;
                }
                case 2680: {
                    if (!string2.equals("TL")) break;
                    n = 212;
                    break;
                }
                case 2681: {
                    if (!string2.equals("TM")) break;
                    n = 213;
                    break;
                }
                case 2682: {
                    if (!string2.equals("TN")) break;
                    n = 214;
                    break;
                }
                case 2683: {
                    if (!string2.equals("TO")) break;
                    n = 215;
                    break;
                }
                case 2686: {
                    if (!string2.equals("TR")) break;
                    n = 216;
                    break;
                }
                case 2688: {
                    if (!string2.equals("TT")) break;
                    n = 217;
                    break;
                }
                case 2690: {
                    if (!string2.equals("TV")) break;
                    n = 218;
                    break;
                }
                case 2691: {
                    if (!string2.equals("TW")) break;
                    n = 219;
                    break;
                }
                case 2694: {
                    if (!string2.equals("TZ")) break;
                    n = 220;
                    break;
                }
                case 2700: {
                    if (!string2.equals("UA")) break;
                    n = 221;
                    break;
                }
                case 2706: {
                    if (!string2.equals("UG")) break;
                    n = 222;
                    break;
                }
                case 2712: {
                    if (!string2.equals("UM")) break;
                    n = 223;
                    break;
                }
                case 2718: {
                    if (!string2.equals("US")) break;
                    n = 224;
                    break;
                }
                case 2724: {
                    if (!string2.equals("UY")) break;
                    n = 225;
                    break;
                }
                case 2725: {
                    if (!string2.equals("UZ")) break;
                    n = 226;
                    break;
                }
                case 2731: {
                    if (!string2.equals("VA")) break;
                    n = 227;
                    break;
                }
                case 2733: {
                    if (!string2.equals("VC")) break;
                    n = 228;
                    break;
                }
                case 2735: {
                    if (!string2.equals("VE")) break;
                    n = 229;
                    break;
                }
                case 2737: {
                    if (!string2.equals("VG")) break;
                    n = 230;
                    break;
                }
                case 2744: {
                    if (!string2.equals("VN")) break;
                    n = 231;
                    break;
                }
                case 2751: {
                    if (!string2.equals("VU")) break;
                    n = 232;
                    break;
                }
                case 2767: {
                    if (!string2.equals("WF")) break;
                    n = 233;
                    break;
                }
                case 2780: {
                    if (!string2.equals("WS")) break;
                    n = 234;
                    break;
                }
                case 2803: {
                    if (!string2.equals("XK")) break;
                    n = 235;
                    break;
                }
                case 2828: {
                    if (!string2.equals("YE")) break;
                    n = 236;
                    break;
                }
                case 2843: {
                    if (!string2.equals("YT")) break;
                    n = 237;
                    break;
                }
                case 2855: {
                    if (!string2.equals("ZA")) break;
                    n = 238;
                    break;
                }
                case 2867: {
                    if (!string2.equals("ZM")) break;
                    n = 239;
                    break;
                }
                case 2877: {
                    if (!string2.equals("ZW")) break;
                    n = 240;
                    break;
                }
            }
            switch (n) {
                default: {
                    return UNKNOWN_VALUE;
                }
                case 0: {
                    return AD;
                }
                case 1: {
                    return AE;
                }
                case 2: {
                    return AF;
                }
                case 3: {
                    return AG;
                }
                case 4: {
                    return AI;
                }
                case 5: {
                    return AL;
                }
                case 6: {
                    return AM;
                }
                case 7: {
                    return AN;
                }
                case 8: {
                    return AO;
                }
                case 9: {
                    return AR;
                }
                case 10: {
                    return AT;
                }
                case 11: {
                    return AU;
                }
                case 12: {
                    return AW;
                }
                case 13: {
                    return AX;
                }
                case 14: {
                    return AZ;
                }
                case 15: {
                    return BA;
                }
                case 16: {
                    return BB;
                }
                case 17: {
                    return BD;
                }
                case 18: {
                    return BE;
                }
                case 19: {
                    return BF;
                }
                case 20: {
                    return BG;
                }
                case 21: {
                    return BH;
                }
                case 22: {
                    return BI;
                }
                case 23: {
                    return BJ;
                }
                case 24: {
                    return BL;
                }
                case 25: {
                    return BM;
                }
                case 26: {
                    return BN;
                }
                case 27: {
                    return BO;
                }
                case 28: {
                    return BR;
                }
                case 29: {
                    return BS;
                }
                case 30: {
                    return BT;
                }
                case 31: {
                    return BV;
                }
                case 32: {
                    return BW;
                }
                case 33: {
                    return BY;
                }
                case 34: {
                    return BZ;
                }
                case 35: {
                    return CA;
                }
                case 36: {
                    return CC;
                }
                case 37: {
                    return CD;
                }
                case 38: {
                    return CF;
                }
                case 39: {
                    return CG;
                }
                case 40: {
                    return CH;
                }
                case 41: {
                    return CI;
                }
                case 42: {
                    return CK;
                }
                case 43: {
                    return CL;
                }
                case 44: {
                    return CM;
                }
                case 45: {
                    return CN;
                }
                case 46: {
                    return CO;
                }
                case 47: {
                    return CR;
                }
                case 48: {
                    return CU;
                }
                case 49: {
                    return CV;
                }
                case 50: {
                    return CW;
                }
                case 51: {
                    return CX;
                }
                case 52: {
                    return CY;
                }
                case 53: {
                    return CZ;
                }
                case 54: {
                    return DE;
                }
                case 55: {
                    return DJ;
                }
                case 56: {
                    return DK;
                }
                case 57: {
                    return DM;
                }
                case 58: {
                    return DO;
                }
                case 59: {
                    return DZ;
                }
                case 60: {
                    return EC;
                }
                case 61: {
                    return EE;
                }
                case 62: {
                    return EG;
                }
                case 63: {
                    return EH;
                }
                case 64: {
                    return ER;
                }
                case 65: {
                    return ES;
                }
                case 66: {
                    return ET;
                }
                case 67: {
                    return FI;
                }
                case 68: {
                    return FJ;
                }
                case 69: {
                    return FK;
                }
                case 70: {
                    return FO;
                }
                case 71: {
                    return FR;
                }
                case 72: {
                    return GA;
                }
                case 73: {
                    return GB;
                }
                case 74: {
                    return GD;
                }
                case 75: {
                    return GE;
                }
                case 76: {
                    return GF;
                }
                case 77: {
                    return GG;
                }
                case 78: {
                    return GH;
                }
                case 79: {
                    return GI;
                }
                case 80: {
                    return GL;
                }
                case 81: {
                    return GM;
                }
                case 82: {
                    return GN;
                }
                case 83: {
                    return GP;
                }
                case 84: {
                    return GQ;
                }
                case 85: {
                    return GR;
                }
                case 86: {
                    return GS;
                }
                case 87: {
                    return GT;
                }
                case 88: {
                    return GW;
                }
                case 89: {
                    return GY;
                }
                case 90: {
                    return HK;
                }
                case 91: {
                    return HM;
                }
                case 92: {
                    return HN;
                }
                case 93: {
                    return HR;
                }
                case 94: {
                    return HT;
                }
                case 95: {
                    return HU;
                }
                case 96: {
                    return ID;
                }
                case 97: {
                    return IE;
                }
                case 98: {
                    return IL;
                }
                case 99: {
                    return IM;
                }
                case 100: {
                    return IN;
                }
                case 101: {
                    return IO;
                }
                case 102: {
                    return IQ;
                }
                case 103: {
                    return IR;
                }
                case 104: {
                    return IS;
                }
                case 105: {
                    return IT;
                }
                case 106: {
                    return JE;
                }
                case 107: {
                    return JM;
                }
                case 108: {
                    return JO;
                }
                case 109: {
                    return JP;
                }
                case 110: {
                    return KE;
                }
                case 111: {
                    return KG;
                }
                case 112: {
                    return KH;
                }
                case 113: {
                    return KI;
                }
                case 114: {
                    return KM;
                }
                case 115: {
                    return KN;
                }
                case 116: {
                    return KP;
                }
                case 117: {
                    return KR;
                }
                case 118: {
                    return KW;
                }
                case 119: {
                    return KY;
                }
                case 120: {
                    return KZ;
                }
                case 121: {
                    return LA;
                }
                case 122: {
                    return LB;
                }
                case 123: {
                    return LC;
                }
                case 124: {
                    return LI;
                }
                case 125: {
                    return LK;
                }
                case 126: {
                    return LR;
                }
                case 127: {
                    return LS;
                }
                case 128: {
                    return LT;
                }
                case 129: {
                    return LU;
                }
                case 130: {
                    return LV;
                }
                case 131: {
                    return LY;
                }
                case 132: {
                    return MA;
                }
                case 133: {
                    return MC;
                }
                case 134: {
                    return MD;
                }
                case 135: {
                    return ME;
                }
                case 136: {
                    return MF;
                }
                case 137: {
                    return MG;
                }
                case 138: {
                    return MK;
                }
                case 139: {
                    return ML;
                }
                case 140: {
                    return MM;
                }
                case 141: {
                    return MN;
                }
                case 142: {
                    return MO;
                }
                case 143: {
                    return MQ;
                }
                case 144: {
                    return MR;
                }
                case 145: {
                    return MS;
                }
                case 146: {
                    return MT;
                }
                case 147: {
                    return MU;
                }
                case 148: {
                    return MV;
                }
                case 149: {
                    return MW;
                }
                case 150: {
                    return MX;
                }
                case 151: {
                    return MY;
                }
                case 152: {
                    return MZ;
                }
                case 153: {
                    return NA;
                }
                case 154: {
                    return NC;
                }
                case 155: {
                    return NE;
                }
                case 156: {
                    return NF;
                }
                case 157: {
                    return NG;
                }
                case 158: {
                    return NI;
                }
                case 159: {
                    return NL;
                }
                case 160: {
                    return NO;
                }
                case 161: {
                    return NP;
                }
                case 162: {
                    return NR;
                }
                case 163: {
                    return NU;
                }
                case 164: {
                    return NZ;
                }
                case 165: {
                    return OM;
                }
                case 166: {
                    return PA;
                }
                case 167: {
                    return PE;
                }
                case 168: {
                    return PF;
                }
                case 169: {
                    return PG;
                }
                case 170: {
                    return PH;
                }
                case 171: {
                    return PK;
                }
                case 172: {
                    return PL;
                }
                case 173: {
                    return PM;
                }
                case 174: {
                    return PN;
                }
                case 175: {
                    return PS;
                }
                case 176: {
                    return PT;
                }
                case 177: {
                    return PY;
                }
                case 178: {
                    return QA;
                }
                case 179: {
                    return RE;
                }
                case 180: {
                    return RO;
                }
                case 181: {
                    return RS;
                }
                case 182: {
                    return RU;
                }
                case 183: {
                    return RW;
                }
                case 184: {
                    return SA;
                }
                case 185: {
                    return SB;
                }
                case 186: {
                    return SC;
                }
                case 187: {
                    return SD;
                }
                case 188: {
                    return SE;
                }
                case 189: {
                    return SG;
                }
                case 190: {
                    return SH;
                }
                case 191: {
                    return SI;
                }
                case 192: {
                    return SJ;
                }
                case 193: {
                    return SK;
                }
                case 194: {
                    return SL;
                }
                case 195: {
                    return SM;
                }
                case 196: {
                    return SN;
                }
                case 197: {
                    return SO;
                }
                case 198: {
                    return SR;
                }
                case 199: {
                    return SS;
                }
                case 200: {
                    return ST;
                }
                case 201: {
                    return SV;
                }
                case 202: {
                    return SX;
                }
                case 203: {
                    return SY;
                }
                case 204: {
                    return SZ;
                }
                case 205: {
                    return TC;
                }
                case 206: {
                    return TD;
                }
                case 207: {
                    return TF;
                }
                case 208: {
                    return TG;
                }
                case 209: {
                    return TH;
                }
                case 210: {
                    return TJ;
                }
                case 211: {
                    return TK;
                }
                case 212: {
                    return TL;
                }
                case 213: {
                    return TM;
                }
                case 214: {
                    return TN;
                }
                case 215: {
                    return TO;
                }
                case 216: {
                    return TR;
                }
                case 217: {
                    return TT;
                }
                case 218: {
                    return TV;
                }
                case 219: {
                    return TW;
                }
                case 220: {
                    return TZ;
                }
                case 221: {
                    return UA;
                }
                case 222: {
                    return UG;
                }
                case 223: {
                    return UM;
                }
                case 224: {
                    return US;
                }
                case 225: {
                    return UY;
                }
                case 226: {
                    return UZ;
                }
                case 227: {
                    return VA;
                }
                case 228: {
                    return VC;
                }
                case 229: {
                    return VE;
                }
                case 230: {
                    return VG;
                }
                case 231: {
                    return VN;
                }
                case 232: {
                    return VU;
                }
                case 233: {
                    return WF;
                }
                case 234: {
                    return WS;
                }
                case 235: {
                    return XK;
                }
                case 236: {
                    return YE;
                }
                case 237: {
                    return YT;
                }
                case 238: {
                    return ZA;
                }
                case 239: {
                    return ZM;
                }
                case 240: 
            }
            return ZW;
        }

        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case AD: {
                    return "AD";
                }
                case AE: {
                    return "AE";
                }
                case AF: {
                    return "AF";
                }
                case AG: {
                    return "AG";
                }
                case AI: {
                    return "AI";
                }
                case AL: {
                    return "AL";
                }
                case AM: {
                    return "AM";
                }
                case AN: {
                    return "AN";
                }
                case AO: {
                    return "AO";
                }
                case AR: {
                    return "AR";
                }
                case AT: {
                    return "AT";
                }
                case AU: {
                    return "AU";
                }
                case AW: {
                    return "AW";
                }
                case AX: {
                    return "AX";
                }
                case AZ: {
                    return "AZ";
                }
                case BA: {
                    return "BA";
                }
                case BB: {
                    return "BB";
                }
                case BD: {
                    return "BD";
                }
                case BE: {
                    return "BE";
                }
                case BF: {
                    return "BF";
                }
                case BG: {
                    return "BG";
                }
                case BH: {
                    return "BH";
                }
                case BI: {
                    return "BI";
                }
                case BJ: {
                    return "BJ";
                }
                case BL: {
                    return "BL";
                }
                case BM: {
                    return "BM";
                }
                case BN: {
                    return "BN";
                }
                case BO: {
                    return "BO";
                }
                case BR: {
                    return "BR";
                }
                case BS: {
                    return "BS";
                }
                case BT: {
                    return "BT";
                }
                case BV: {
                    return "BV";
                }
                case BW: {
                    return "BW";
                }
                case BY: {
                    return "BY";
                }
                case BZ: {
                    return "BZ";
                }
                case CA: {
                    return "CA";
                }
                case CC: {
                    return "CC";
                }
                case CD: {
                    return "CD";
                }
                case CF: {
                    return "CF";
                }
                case CG: {
                    return "CG";
                }
                case CH: {
                    return "CH";
                }
                case CI: {
                    return "CI";
                }
                case CK: {
                    return "CK";
                }
                case CL: {
                    return "CL";
                }
                case CM: {
                    return "CM";
                }
                case CN: {
                    return "CN";
                }
                case CO: {
                    return "CO";
                }
                case CR: {
                    return "CR";
                }
                case CU: {
                    return "CU";
                }
                case CV: {
                    return "CV";
                }
                case CW: {
                    return "CW";
                }
                case CX: {
                    return "CX";
                }
                case CY: {
                    return "CY";
                }
                case CZ: {
                    return "CZ";
                }
                case DE: {
                    return "DE";
                }
                case DJ: {
                    return "DJ";
                }
                case DK: {
                    return "DK";
                }
                case DM: {
                    return "DM";
                }
                case DO: {
                    return "DO";
                }
                case DZ: {
                    return "DZ";
                }
                case EC: {
                    return "EC";
                }
                case EE: {
                    return "EE";
                }
                case EG: {
                    return "EG";
                }
                case EH: {
                    return "EH";
                }
                case ER: {
                    return "ER";
                }
                case ES: {
                    return "ES";
                }
                case ET: {
                    return "ET";
                }
                case FI: {
                    return "FI";
                }
                case FJ: {
                    return "FJ";
                }
                case FK: {
                    return "FK";
                }
                case FO: {
                    return "FO";
                }
                case FR: {
                    return "FR";
                }
                case GA: {
                    return "GA";
                }
                case GB: {
                    return "GB";
                }
                case GD: {
                    return "GD";
                }
                case GE: {
                    return "GE";
                }
                case GF: {
                    return "GF";
                }
                case GG: {
                    return "GG";
                }
                case GH: {
                    return "GH";
                }
                case GI: {
                    return "GI";
                }
                case GL: {
                    return "GL";
                }
                case GM: {
                    return "GM";
                }
                case GN: {
                    return "GN";
                }
                case GP: {
                    return "GP";
                }
                case GQ: {
                    return "GQ";
                }
                case GR: {
                    return "GR";
                }
                case GS: {
                    return "GS";
                }
                case GT: {
                    return "GT";
                }
                case GW: {
                    return "GW";
                }
                case GY: {
                    return "GY";
                }
                case HK: {
                    return "HK";
                }
                case HM: {
                    return "HM";
                }
                case HN: {
                    return "HN";
                }
                case HR: {
                    return "HR";
                }
                case HT: {
                    return "HT";
                }
                case HU: {
                    return "HU";
                }
                case ID: {
                    return "ID";
                }
                case IE: {
                    return "IE";
                }
                case IL: {
                    return "IL";
                }
                case IM: {
                    return "IM";
                }
                case IN: {
                    return "IN";
                }
                case IO: {
                    return "IO";
                }
                case IQ: {
                    return "IQ";
                }
                case IR: {
                    return "IR";
                }
                case IS: {
                    return "IS";
                }
                case IT: {
                    return "IT";
                }
                case JE: {
                    return "JE";
                }
                case JM: {
                    return "JM";
                }
                case JO: {
                    return "JO";
                }
                case JP: {
                    return "JP";
                }
                case KE: {
                    return "KE";
                }
                case KG: {
                    return "KG";
                }
                case KH: {
                    return "KH";
                }
                case KI: {
                    return "KI";
                }
                case KM: {
                    return "KM";
                }
                case KN: {
                    return "KN";
                }
                case KP: {
                    return "KP";
                }
                case KR: {
                    return "KR";
                }
                case KW: {
                    return "KW";
                }
                case KY: {
                    return "KY";
                }
                case KZ: {
                    return "KZ";
                }
                case LA: {
                    return "LA";
                }
                case LB: {
                    return "LB";
                }
                case LC: {
                    return "LC";
                }
                case LI: {
                    return "LI";
                }
                case LK: {
                    return "LK";
                }
                case LR: {
                    return "LR";
                }
                case LS: {
                    return "LS";
                }
                case LT: {
                    return "LT";
                }
                case LU: {
                    return "LU";
                }
                case LV: {
                    return "LV";
                }
                case LY: {
                    return "LY";
                }
                case MA: {
                    return "MA";
                }
                case MC: {
                    return "MC";
                }
                case MD: {
                    return "MD";
                }
                case ME: {
                    return "ME";
                }
                case MF: {
                    return "MF";
                }
                case MG: {
                    return "MG";
                }
                case MK: {
                    return "MK";
                }
                case ML: {
                    return "ML";
                }
                case MM: {
                    return "MM";
                }
                case MN: {
                    return "MN";
                }
                case MO: {
                    return "MO";
                }
                case MQ: {
                    return "MQ";
                }
                case MR: {
                    return "MR";
                }
                case MS: {
                    return "MS";
                }
                case MT: {
                    return "MT";
                }
                case MU: {
                    return "MU";
                }
                case MV: {
                    return "MV";
                }
                case MW: {
                    return "MW";
                }
                case MX: {
                    return "MX";
                }
                case MY: {
                    return "MY";
                }
                case MZ: {
                    return "MZ";
                }
                case NA: {
                    return "NA";
                }
                case NC: {
                    return "NC";
                }
                case NE: {
                    return "NE";
                }
                case NF: {
                    return "NF";
                }
                case NG: {
                    return "NG";
                }
                case NI: {
                    return "NI";
                }
                case NL: {
                    return "NL";
                }
                case NO: {
                    return "NO";
                }
                case NP: {
                    return "NP";
                }
                case NR: {
                    return "NR";
                }
                case NU: {
                    return "NU";
                }
                case NZ: {
                    return "NZ";
                }
                case OM: {
                    return "OM";
                }
                case PA: {
                    return "PA";
                }
                case PE: {
                    return "PE";
                }
                case PF: {
                    return "PF";
                }
                case PG: {
                    return "PG";
                }
                case PH: {
                    return "PH";
                }
                case PK: {
                    return "PK";
                }
                case PL: {
                    return "PL";
                }
                case PM: {
                    return "PM";
                }
                case PN: {
                    return "PN";
                }
                case PS: {
                    return "PS";
                }
                case PT: {
                    return "PT";
                }
                case PY: {
                    return "PY";
                }
                case QA: {
                    return "QA";
                }
                case RE: {
                    return "RE";
                }
                case RO: {
                    return "RO";
                }
                case RS: {
                    return "RS";
                }
                case RU: {
                    return "RU";
                }
                case RW: {
                    return "RW";
                }
                case SA: {
                    return "SA";
                }
                case SB: {
                    return "SB";
                }
                case SC: {
                    return "SC";
                }
                case SD: {
                    return "SD";
                }
                case SE: {
                    return "SE";
                }
                case SG: {
                    return "SG";
                }
                case SH: {
                    return "SH";
                }
                case SI: {
                    return "SI";
                }
                case SJ: {
                    return "SJ";
                }
                case SK: {
                    return "SK";
                }
                case SL: {
                    return "SL";
                }
                case SM: {
                    return "SM";
                }
                case SN: {
                    return "SN";
                }
                case SO: {
                    return "SO";
                }
                case SR: {
                    return "SR";
                }
                case SS: {
                    return "SS";
                }
                case ST: {
                    return "ST";
                }
                case SV: {
                    return "SV";
                }
                case SX: {
                    return "SX";
                }
                case SY: {
                    return "SY";
                }
                case SZ: {
                    return "SZ";
                }
                case TC: {
                    return "TC";
                }
                case TD: {
                    return "TD";
                }
                case TF: {
                    return "TF";
                }
                case TG: {
                    return "TG";
                }
                case TH: {
                    return "TH";
                }
                case TJ: {
                    return "TJ";
                }
                case TK: {
                    return "TK";
                }
                case TL: {
                    return "TL";
                }
                case TM: {
                    return "TM";
                }
                case TN: {
                    return "TN";
                }
                case TO: {
                    return "TO";
                }
                case TR: {
                    return "TR";
                }
                case TT: {
                    return "TT";
                }
                case TV: {
                    return "TV";
                }
                case TW: {
                    return "TW";
                }
                case TZ: {
                    return "TZ";
                }
                case UA: {
                    return "UA";
                }
                case UG: {
                    return "UG";
                }
                case UM: {
                    return "UM";
                }
                case US: {
                    return "US";
                }
                case UY: {
                    return "UY";
                }
                case UZ: {
                    return "UZ";
                }
                case VA: {
                    return "VA";
                }
                case VC: {
                    return "VC";
                }
                case VE: {
                    return "VE";
                }
                case VG: {
                    return "VG";
                }
                case VN: {
                    return "VN";
                }
                case VU: {
                    return "VU";
                }
                case WF: {
                    return "WF";
                }
                case WS: {
                    return "WS";
                }
                case XK: {
                    return "XK";
                }
                case YE: {
                    return "YE";
                }
                case YT: {
                    return "YT";
                }
                case ZA: {
                    return "ZA";
                }
                case ZM: {
                    return "ZM";
                }
                case ZW: 
            }
            return "ZW";
        }
    }

    public static class CreditCard
    extends AbstractResponse<CreditCard> {
        public CreditCard() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CreditCard(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block22 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 93997959: {
                        if (!((String)object).equals("brand")) break;
                        n = 0;
                        break;
                    }
                    case 459635981: {
                        if (!((String)object).equals("expiryMonth")) break;
                        n = 1;
                        break;
                    }
                    case -816109552: {
                        if (!((String)object).equals("expiryYear")) break;
                        n = 2;
                        break;
                    }
                    case -1473009738: {
                        if (!((String)object).equals("firstDigits")) break;
                        n = 3;
                        break;
                    }
                    case 132835675: {
                        if (!((String)object).equals("firstName")) break;
                        n = 4;
                        break;
                    }
                    case 1499816732: {
                        if (!((String)object).equals("lastDigits")) break;
                        n = 5;
                        break;
                    }
                    case -1459599807: {
                        if (!((String)object).equals("lastName")) break;
                        n = 6;
                        break;
                    }
                    case -1738081356: {
                        if (!((String)object).equals("maskedNumber")) break;
                        n = 7;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 8;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block22;
                    }
                    case 1: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsInteger(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block22;
                    }
                    case 2: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsInteger(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block22;
                    }
                    case 3: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block22;
                    }
                    case 4: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block22;
                    }
                    case 5: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block22;
                    }
                    case 6: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block22;
                    }
                    case 7: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block22;
                    }
                    case 8: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static enum CropRegion {
        BOTTOM,
        CENTER,
        LEFT,
        RIGHT,
        TOP,
        UNKNOWN_VALUE;


        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case BOTTOM: {
                    return "BOTTOM";
                }
                case CENTER: {
                    return "CENTER";
                }
                case LEFT: {
                    return "LEFT";
                }
                case RIGHT: {
                    return "RIGHT";
                }
                case TOP: 
            }
            return "TOP";
        }
    }

    public static enum CurrencyCode {
        AED,
        AFN,
        ALL,
        AMD,
        ANG,
        AOA,
        ARS,
        AUD,
        AWG,
        AZN,
        BAM,
        BBD,
        BDT,
        BGN,
        BHD,
        BND,
        BOB,
        BRL,
        BSD,
        BTN,
        BWP,
        BYR,
        BZD,
        CAD,
        CDF,
        CHF,
        CLP,
        CNY,
        COP,
        CRC,
        CVE,
        CZK,
        DKK,
        DOP,
        DZD,
        EGP,
        ETB,
        EUR,
        FJD,
        GBP,
        GEL,
        GHS,
        GMD,
        GTQ,
        GYD,
        HKD,
        HNL,
        HRK,
        HTG,
        HUF,
        IDR,
        ILS,
        INR,
        ISK,
        JEP,
        JMD,
        JOD,
        JPY,
        KES,
        KGS,
        KHR,
        KMF,
        KRW,
        KWD,
        KYD,
        KZT,
        LAK,
        LBP,
        LKR,
        LRD,
        LSL,
        LTL,
        LVL,
        MAD,
        MDL,
        MGA,
        MKD,
        MMK,
        MNT,
        MOP,
        MUR,
        MVR,
        MWK,
        MXN,
        MYR,
        MZN,
        NAD,
        NGN,
        NIO,
        NOK,
        NPR,
        NZD,
        OMR,
        PEN,
        PGK,
        PHP,
        PKR,
        PLN,
        PYG,
        QAR,
        RON,
        RSD,
        RUB,
        RWF,
        SAR,
        SBD,
        SCR,
        SDG,
        SEK,
        SGD,
        SRD,
        SSP,
        STD,
        SYP,
        THB,
        TMT,
        TND,
        TRY,
        TTD,
        TWD,
        TZS,
        UAH,
        UGX,
        USD,
        UYU,
        UZS,
        VEF,
        VND,
        VUV,
        WST,
        XAF,
        XCD,
        XOF,
        XPF,
        YER,
        ZAR,
        ZMW,
        UNKNOWN_VALUE;


        /*
         * Enabled aggressive block sorting
         */
        public static CurrencyCode fromGraphQl(String string2) {
            if (string2 == null) {
                return null;
            }
            int n = -1;
            switch (string2.hashCode()) {
                case 64672: {
                    if (!string2.equals("AED")) break;
                    n = 0;
                    break;
                }
                case 64713: {
                    if (!string2.equals("AFN")) break;
                    n = 1;
                    break;
                }
                case 64897: {
                    if (!string2.equals("ALL")) break;
                    n = 2;
                    break;
                }
                case 64920: {
                    if (!string2.equals("AMD")) break;
                    n = 3;
                    break;
                }
                case 64954: {
                    if (!string2.equals("ANG")) break;
                    n = 4;
                    break;
                }
                case 64979: {
                    if (!string2.equals("AOA")) break;
                    n = 5;
                    break;
                }
                case 65090: {
                    if (!string2.equals("ARS")) break;
                    n = 6;
                    break;
                }
                case 65168: {
                    if (!string2.equals("AUD")) break;
                    n = 7;
                    break;
                }
                case 65233: {
                    if (!string2.equals("AWG")) break;
                    n = 8;
                    break;
                }
                case 65333: {
                    if (!string2.equals("AZN")) break;
                    n = 9;
                    break;
                }
                case 65518: {
                    if (!string2.equals("BAM")) break;
                    n = 10;
                    break;
                }
                case 65540: {
                    if (!string2.equals("BBD")) break;
                    n = 11;
                    break;
                }
                case 65618: {
                    if (!string2.equals("BDT")) break;
                    n = 12;
                    break;
                }
                case 65705: {
                    if (!string2.equals("BGN")) break;
                    n = 13;
                    break;
                }
                case 65726: {
                    if (!string2.equals("BHD")) break;
                    n = 14;
                    break;
                }
                case 65912: {
                    if (!string2.equals("BND")) break;
                    n = 15;
                    break;
                }
                case 65941: {
                    if (!string2.equals("BOB")) break;
                    n = 16;
                    break;
                }
                case 66044: {
                    if (!string2.equals("BRL")) break;
                    n = 17;
                    break;
                }
                case 66067: {
                    if (!string2.equals("BSD")) break;
                    n = 18;
                    break;
                }
                case 66108: {
                    if (!string2.equals("BTN")) break;
                    n = 19;
                    break;
                }
                case 66203: {
                    if (!string2.equals("BWP")) break;
                    n = 20;
                    break;
                }
                case 66267: {
                    if (!string2.equals("BYR")) break;
                    n = 21;
                    break;
                }
                case 66284: {
                    if (!string2.equals("BZD")) break;
                    n = 22;
                    break;
                }
                case 66470: {
                    if (!string2.equals("CAD")) break;
                    n = 23;
                    break;
                }
                case 66565: {
                    if (!string2.equals("CDF")) break;
                    n = 24;
                    break;
                }
                case 66689: {
                    if (!string2.equals("CHF")) break;
                    n = 25;
                    break;
                }
                case 66823: {
                    if (!string2.equals("CLP")) break;
                    n = 26;
                    break;
                }
                case 66894: {
                    if (!string2.equals("CNY")) break;
                    n = 27;
                    break;
                }
                case 66916: {
                    if (!string2.equals("COP")) break;
                    n = 28;
                    break;
                }
                case 66996: {
                    if (!string2.equals("CRC")) break;
                    n = 29;
                    break;
                }
                case 67122: {
                    if (!string2.equals("CVE")) break;
                    n = 30;
                    break;
                }
                case 67252: {
                    if (!string2.equals("CZK")) break;
                    n = 31;
                    break;
                }
                case 67748: {
                    if (!string2.equals("DKK")) break;
                    n = 32;
                    break;
                }
                case 67877: {
                    if (!string2.equals("DOP")) break;
                    n = 33;
                    break;
                }
                case 68206: {
                    if (!string2.equals("DZD")) break;
                    n = 34;
                    break;
                }
                case 68590: {
                    if (!string2.equals("EGP")) break;
                    n = 35;
                    break;
                }
                case 68979: {
                    if (!string2.equals("ETB")) break;
                    n = 36;
                    break;
                }
                case 69026: {
                    if (!string2.equals("EUR")) break;
                    n = 37;
                    break;
                }
                case 69632: {
                    if (!string2.equals("FJD")) break;
                    n = 38;
                    break;
                }
                case 70357: {
                    if (!string2.equals("GBP")) break;
                    n = 39;
                    break;
                }
                case 70446: {
                    if (!string2.equals("GEL")) break;
                    n = 40;
                    break;
                }
                case 70546: {
                    if (!string2.equals("GHS")) break;
                    n = 41;
                    break;
                }
                case 70686: {
                    if (!string2.equals("GMD")) break;
                    n = 42;
                    break;
                }
                case 70916: {
                    if (!string2.equals("GTQ")) break;
                    n = 43;
                    break;
                }
                case 71058: {
                    if (!string2.equals("GYD")) break;
                    n = 44;
                    break;
                }
                case 71585: {
                    if (!string2.equals("HKD")) break;
                    n = 45;
                    break;
                }
                case 71686: {
                    if (!string2.equals("HNL")) break;
                    n = 46;
                    break;
                }
                case 71809: {
                    if (!string2.equals("HRK")) break;
                    n = 47;
                    break;
                }
                case 71867: {
                    if (!string2.equals("HTG")) break;
                    n = 48;
                    break;
                }
                case 71897: {
                    if (!string2.equals("HUF")) break;
                    n = 49;
                    break;
                }
                case 72343: {
                    if (!string2.equals("IDR")) break;
                    n = 50;
                    break;
                }
                case 72592: {
                    if (!string2.equals("ILS")) break;
                    n = 51;
                    break;
                }
                case 72653: {
                    if (!string2.equals("INR")) break;
                    n = 52;
                    break;
                }
                case 72801: {
                    if (!string2.equals("ISK")) break;
                    n = 53;
                    break;
                }
                case 73333: {
                    if (!string2.equals("JEP")) break;
                    n = 54;
                    break;
                }
                case 73569: {
                    if (!string2.equals("JMD")) break;
                    n = 55;
                    break;
                }
                case 73631: {
                    if (!string2.equals("JOD")) break;
                    n = 56;
                    break;
                }
                case 73683: {
                    if (!string2.equals("JPY")) break;
                    n = 57;
                    break;
                }
                case 74297: {
                    if (!string2.equals("KES")) break;
                    n = 58;
                    break;
                }
                case 74359: {
                    if (!string2.equals("KGS")) break;
                    n = 59;
                    break;
                }
                case 74389: {
                    if (!string2.equals("KHR")) break;
                    n = 60;
                    break;
                }
                case 74532: {
                    if (!string2.equals("KMF")) break;
                    n = 61;
                    break;
                }
                case 74704: {
                    if (!string2.equals("KRW")) break;
                    n = 62;
                    break;
                }
                case 74840: {
                    if (!string2.equals("KWD")) break;
                    n = 63;
                    break;
                }
                case 74902: {
                    if (!string2.equals("KYD")) break;
                    n = 64;
                    break;
                }
                case 74949: {
                    if (!string2.equals("KZT")) break;
                    n = 65;
                    break;
                }
                case 75126: {
                    if (!string2.equals("LAK")) break;
                    n = 66;
                    break;
                }
                case 75162: {
                    if (!string2.equals("LBP")) break;
                    n = 67;
                    break;
                }
                case 75443: {
                    if (!string2.equals("LKR")) break;
                    n = 68;
                    break;
                }
                case 75646: {
                    if (!string2.equals("LRD")) break;
                    n = 69;
                    break;
                }
                case 75685: {
                    if (!string2.equals("LSL")) break;
                    n = 70;
                    break;
                }
                case 75716: {
                    if (!string2.equals("LTL")) break;
                    n = 71;
                    break;
                }
                case 75778: {
                    if (!string2.equals("LVL")) break;
                    n = 72;
                    break;
                }
                case 76080: {
                    if (!string2.equals("MAD")) break;
                    n = 73;
                    break;
                }
                case 76181: {
                    if (!string2.equals("MDL")) break;
                    n = 74;
                    break;
                }
                case 76263: {
                    if (!string2.equals("MGA")) break;
                    n = 75;
                    break;
                }
                case 76390: {
                    if (!string2.equals("MKD")) break;
                    n = 76;
                    break;
                }
                case 76459: {
                    if (!string2.equals("MMK")) break;
                    n = 77;
                    break;
                }
                case 76499: {
                    if (!string2.equals("MNT")) break;
                    n = 78;
                    break;
                }
                case 76526: {
                    if (!string2.equals("MOP")) break;
                    n = 79;
                    break;
                }
                case 76714: {
                    if (!string2.equals("MUR")) break;
                    n = 80;
                    break;
                }
                case 76745: {
                    if (!string2.equals("MVR")) break;
                    n = 81;
                    break;
                }
                case 76769: {
                    if (!string2.equals("MWK")) break;
                    n = 82;
                    break;
                }
                case 76803: {
                    if (!string2.equals("MXN")) break;
                    n = 83;
                    break;
                }
                case 76838: {
                    if (!string2.equals("MYR")) break;
                    n = 84;
                    break;
                }
                case 76865: {
                    if (!string2.equals("MZN")) break;
                    n = 85;
                    break;
                }
                case 77041: {
                    if (!string2.equals("NAD")) break;
                    n = 86;
                    break;
                }
                case 77237: {
                    if (!string2.equals("NGN")) break;
                    n = 87;
                    break;
                }
                case 77300: {
                    if (!string2.equals("NIO")) break;
                    n = 88;
                    break;
                }
                case 77482: {
                    if (!string2.equals("NOK")) break;
                    n = 89;
                    break;
                }
                case 77520: {
                    if (!string2.equals("NPR")) break;
                    n = 90;
                    break;
                }
                case 77816: {
                    if (!string2.equals("NZD")) break;
                    n = 91;
                    break;
                }
                case 78388: {
                    if (!string2.equals("OMR")) break;
                    n = 92;
                    break;
                }
                case 79097: {
                    if (!string2.equals("PEN")) break;
                    n = 93;
                    break;
                }
                case 79156: {
                    if (!string2.equals("PGK")) break;
                    n = 94;
                    break;
                }
                case 79192: {
                    if (!string2.equals("PHP")) break;
                    n = 95;
                    break;
                }
                case 79287: {
                    if (!string2.equals("PKR")) break;
                    n = 96;
                    break;
                }
                case 79314: {
                    if (!string2.equals("PLN")) break;
                    n = 97;
                    break;
                }
                case 79710: {
                    if (!string2.equals("PYG")) break;
                    n = 98;
                    break;
                }
                case 79938: {
                    if (!string2.equals("QAR")) break;
                    n = 99;
                    break;
                }
                case 81329: {
                    if (!string2.equals("RON")) break;
                    n = 100;
                    break;
                }
                case 81443: {
                    if (!string2.equals("RSD")) break;
                    n = 101;
                    break;
                }
                case 81503: {
                    if (!string2.equals("RUB")) break;
                    n = 102;
                    break;
                }
                case 81569: {
                    if (!string2.equals("RWF")) break;
                    n = 103;
                    break;
                }
                case 81860: {
                    if (!string2.equals("SAR")) break;
                    n = 104;
                    break;
                }
                case 81877: {
                    if (!string2.equals("SBD")) break;
                    n = 105;
                    break;
                }
                case 81922: {
                    if (!string2.equals("SCR")) break;
                    n = 106;
                    break;
                }
                case 81942: {
                    if (!string2.equals("SDG")) break;
                    n = 107;
                    break;
                }
                case 81977: {
                    if (!string2.equals("SEK")) break;
                    n = 108;
                    break;
                }
                case 82032: {
                    if (!string2.equals("SGD")) break;
                    n = 109;
                    break;
                }
                case 82373: {
                    if (!string2.equals("SRD")) break;
                    n = 110;
                    break;
                }
                case 82416: {
                    if (!string2.equals("SSP")) break;
                    n = 111;
                    break;
                }
                case 82435: {
                    if (!string2.equals("STD")) break;
                    n = 112;
                    break;
                }
                case 82602: {
                    if (!string2.equals("SYP")) break;
                    n = 113;
                    break;
                }
                case 83022: {
                    if (!string2.equals("THB")) break;
                    n = 114;
                    break;
                }
                case 83195: {
                    if (!string2.equals("TMT")) break;
                    n = 115;
                    break;
                }
                case 83210: {
                    if (!string2.equals("TND")) break;
                    n = 116;
                    break;
                }
                case 83355: {
                    if (!string2.equals("TRY")) break;
                    n = 117;
                    break;
                }
                case 83396: {
                    if (!string2.equals("TTD")) break;
                    n = 118;
                    break;
                }
                case 83489: {
                    if (!string2.equals("TWD")) break;
                    n = 119;
                    break;
                }
                case 83597: {
                    if (!string2.equals("TZS")) break;
                    n = 120;
                    break;
                }
                case 83772: {
                    if (!string2.equals("UAH")) break;
                    n = 121;
                    break;
                }
                case 83974: {
                    if (!string2.equals("UGX")) break;
                    n = 122;
                    break;
                }
                case 84326: {
                    if (!string2.equals("USD")) break;
                    n = 123;
                    break;
                }
                case 84529: {
                    if (!string2.equals("UYU")) break;
                    n = 124;
                    break;
                }
                case 84558: {
                    if (!string2.equals("UZS")) break;
                    n = 125;
                    break;
                }
                case 84855: {
                    if (!string2.equals("VEF")) break;
                    n = 126;
                    break;
                }
                case 85132: {
                    if (!string2.equals("VND")) break;
                    n = 127;
                    break;
                }
                case 85367: {
                    if (!string2.equals("VUV")) break;
                    n = 128;
                    break;
                }
                case 86264: {
                    if (!string2.equals("WST")) break;
                    n = 129;
                    break;
                }
                case 86653: {
                    if (!string2.equals("XAF")) break;
                    n = 130;
                    break;
                }
                case 86713: {
                    if (!string2.equals("XCD")) break;
                    n = 131;
                    break;
                }
                case 87087: {
                    if (!string2.equals("XOF")) break;
                    n = 132;
                    break;
                }
                case 87118: {
                    if (!string2.equals("XPF")) break;
                    n = 133;
                    break;
                }
                case 87750: {
                    if (!string2.equals("YER")) break;
                    n = 134;
                    break;
                }
                case 88587: {
                    if (!string2.equals("ZAR")) break;
                    n = 135;
                    break;
                }
                case 88964: {
                    if (!string2.equals("ZMW")) break;
                    n = 136;
                    break;
                }
            }
            switch (n) {
                default: {
                    return UNKNOWN_VALUE;
                }
                case 0: {
                    return AED;
                }
                case 1: {
                    return AFN;
                }
                case 2: {
                    return ALL;
                }
                case 3: {
                    return AMD;
                }
                case 4: {
                    return ANG;
                }
                case 5: {
                    return AOA;
                }
                case 6: {
                    return ARS;
                }
                case 7: {
                    return AUD;
                }
                case 8: {
                    return AWG;
                }
                case 9: {
                    return AZN;
                }
                case 10: {
                    return BAM;
                }
                case 11: {
                    return BBD;
                }
                case 12: {
                    return BDT;
                }
                case 13: {
                    return BGN;
                }
                case 14: {
                    return BHD;
                }
                case 15: {
                    return BND;
                }
                case 16: {
                    return BOB;
                }
                case 17: {
                    return BRL;
                }
                case 18: {
                    return BSD;
                }
                case 19: {
                    return BTN;
                }
                case 20: {
                    return BWP;
                }
                case 21: {
                    return BYR;
                }
                case 22: {
                    return BZD;
                }
                case 23: {
                    return CAD;
                }
                case 24: {
                    return CDF;
                }
                case 25: {
                    return CHF;
                }
                case 26: {
                    return CLP;
                }
                case 27: {
                    return CNY;
                }
                case 28: {
                    return COP;
                }
                case 29: {
                    return CRC;
                }
                case 30: {
                    return CVE;
                }
                case 31: {
                    return CZK;
                }
                case 32: {
                    return DKK;
                }
                case 33: {
                    return DOP;
                }
                case 34: {
                    return DZD;
                }
                case 35: {
                    return EGP;
                }
                case 36: {
                    return ETB;
                }
                case 37: {
                    return EUR;
                }
                case 38: {
                    return FJD;
                }
                case 39: {
                    return GBP;
                }
                case 40: {
                    return GEL;
                }
                case 41: {
                    return GHS;
                }
                case 42: {
                    return GMD;
                }
                case 43: {
                    return GTQ;
                }
                case 44: {
                    return GYD;
                }
                case 45: {
                    return HKD;
                }
                case 46: {
                    return HNL;
                }
                case 47: {
                    return HRK;
                }
                case 48: {
                    return HTG;
                }
                case 49: {
                    return HUF;
                }
                case 50: {
                    return IDR;
                }
                case 51: {
                    return ILS;
                }
                case 52: {
                    return INR;
                }
                case 53: {
                    return ISK;
                }
                case 54: {
                    return JEP;
                }
                case 55: {
                    return JMD;
                }
                case 56: {
                    return JOD;
                }
                case 57: {
                    return JPY;
                }
                case 58: {
                    return KES;
                }
                case 59: {
                    return KGS;
                }
                case 60: {
                    return KHR;
                }
                case 61: {
                    return KMF;
                }
                case 62: {
                    return KRW;
                }
                case 63: {
                    return KWD;
                }
                case 64: {
                    return KYD;
                }
                case 65: {
                    return KZT;
                }
                case 66: {
                    return LAK;
                }
                case 67: {
                    return LBP;
                }
                case 68: {
                    return LKR;
                }
                case 69: {
                    return LRD;
                }
                case 70: {
                    return LSL;
                }
                case 71: {
                    return LTL;
                }
                case 72: {
                    return LVL;
                }
                case 73: {
                    return MAD;
                }
                case 74: {
                    return MDL;
                }
                case 75: {
                    return MGA;
                }
                case 76: {
                    return MKD;
                }
                case 77: {
                    return MMK;
                }
                case 78: {
                    return MNT;
                }
                case 79: {
                    return MOP;
                }
                case 80: {
                    return MUR;
                }
                case 81: {
                    return MVR;
                }
                case 82: {
                    return MWK;
                }
                case 83: {
                    return MXN;
                }
                case 84: {
                    return MYR;
                }
                case 85: {
                    return MZN;
                }
                case 86: {
                    return NAD;
                }
                case 87: {
                    return NGN;
                }
                case 88: {
                    return NIO;
                }
                case 89: {
                    return NOK;
                }
                case 90: {
                    return NPR;
                }
                case 91: {
                    return NZD;
                }
                case 92: {
                    return OMR;
                }
                case 93: {
                    return PEN;
                }
                case 94: {
                    return PGK;
                }
                case 95: {
                    return PHP;
                }
                case 96: {
                    return PKR;
                }
                case 97: {
                    return PLN;
                }
                case 98: {
                    return PYG;
                }
                case 99: {
                    return QAR;
                }
                case 100: {
                    return RON;
                }
                case 101: {
                    return RSD;
                }
                case 102: {
                    return RUB;
                }
                case 103: {
                    return RWF;
                }
                case 104: {
                    return SAR;
                }
                case 105: {
                    return SBD;
                }
                case 106: {
                    return SCR;
                }
                case 107: {
                    return SDG;
                }
                case 108: {
                    return SEK;
                }
                case 109: {
                    return SGD;
                }
                case 110: {
                    return SRD;
                }
                case 111: {
                    return SSP;
                }
                case 112: {
                    return STD;
                }
                case 113: {
                    return SYP;
                }
                case 114: {
                    return THB;
                }
                case 115: {
                    return TMT;
                }
                case 116: {
                    return TND;
                }
                case 117: {
                    return TRY;
                }
                case 118: {
                    return TTD;
                }
                case 119: {
                    return TWD;
                }
                case 120: {
                    return TZS;
                }
                case 121: {
                    return UAH;
                }
                case 122: {
                    return UGX;
                }
                case 123: {
                    return USD;
                }
                case 124: {
                    return UYU;
                }
                case 125: {
                    return UZS;
                }
                case 126: {
                    return VEF;
                }
                case 127: {
                    return VND;
                }
                case 128: {
                    return VUV;
                }
                case 129: {
                    return WST;
                }
                case 130: {
                    return XAF;
                }
                case 131: {
                    return XCD;
                }
                case 132: {
                    return XOF;
                }
                case 133: {
                    return XPF;
                }
                case 134: {
                    return YER;
                }
                case 135: {
                    return ZAR;
                }
                case 136: 
            }
            return ZMW;
        }

        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case AED: {
                    return "AED";
                }
                case AFN: {
                    return "AFN";
                }
                case ALL: {
                    return "ALL";
                }
                case AMD: {
                    return "AMD";
                }
                case ANG: {
                    return "ANG";
                }
                case AOA: {
                    return "AOA";
                }
                case ARS: {
                    return "ARS";
                }
                case AUD: {
                    return "AUD";
                }
                case AWG: {
                    return "AWG";
                }
                case AZN: {
                    return "AZN";
                }
                case BAM: {
                    return "BAM";
                }
                case BBD: {
                    return "BBD";
                }
                case BDT: {
                    return "BDT";
                }
                case BGN: {
                    return "BGN";
                }
                case BHD: {
                    return "BHD";
                }
                case BND: {
                    return "BND";
                }
                case BOB: {
                    return "BOB";
                }
                case BRL: {
                    return "BRL";
                }
                case BSD: {
                    return "BSD";
                }
                case BTN: {
                    return "BTN";
                }
                case BWP: {
                    return "BWP";
                }
                case BYR: {
                    return "BYR";
                }
                case BZD: {
                    return "BZD";
                }
                case CAD: {
                    return "CAD";
                }
                case CDF: {
                    return "CDF";
                }
                case CHF: {
                    return "CHF";
                }
                case CLP: {
                    return "CLP";
                }
                case CNY: {
                    return "CNY";
                }
                case COP: {
                    return "COP";
                }
                case CRC: {
                    return "CRC";
                }
                case CVE: {
                    return "CVE";
                }
                case CZK: {
                    return "CZK";
                }
                case DKK: {
                    return "DKK";
                }
                case DOP: {
                    return "DOP";
                }
                case DZD: {
                    return "DZD";
                }
                case EGP: {
                    return "EGP";
                }
                case ETB: {
                    return "ETB";
                }
                case EUR: {
                    return "EUR";
                }
                case FJD: {
                    return "FJD";
                }
                case GBP: {
                    return "GBP";
                }
                case GEL: {
                    return "GEL";
                }
                case GHS: {
                    return "GHS";
                }
                case GMD: {
                    return "GMD";
                }
                case GTQ: {
                    return "GTQ";
                }
                case GYD: {
                    return "GYD";
                }
                case HKD: {
                    return "HKD";
                }
                case HNL: {
                    return "HNL";
                }
                case HRK: {
                    return "HRK";
                }
                case HTG: {
                    return "HTG";
                }
                case HUF: {
                    return "HUF";
                }
                case IDR: {
                    return "IDR";
                }
                case ILS: {
                    return "ILS";
                }
                case INR: {
                    return "INR";
                }
                case ISK: {
                    return "ISK";
                }
                case JEP: {
                    return "JEP";
                }
                case JMD: {
                    return "JMD";
                }
                case JOD: {
                    return "JOD";
                }
                case JPY: {
                    return "JPY";
                }
                case KES: {
                    return "KES";
                }
                case KGS: {
                    return "KGS";
                }
                case KHR: {
                    return "KHR";
                }
                case KMF: {
                    return "KMF";
                }
                case KRW: {
                    return "KRW";
                }
                case KWD: {
                    return "KWD";
                }
                case KYD: {
                    return "KYD";
                }
                case KZT: {
                    return "KZT";
                }
                case LAK: {
                    return "LAK";
                }
                case LBP: {
                    return "LBP";
                }
                case LKR: {
                    return "LKR";
                }
                case LRD: {
                    return "LRD";
                }
                case LSL: {
                    return "LSL";
                }
                case LTL: {
                    return "LTL";
                }
                case LVL: {
                    return "LVL";
                }
                case MAD: {
                    return "MAD";
                }
                case MDL: {
                    return "MDL";
                }
                case MGA: {
                    return "MGA";
                }
                case MKD: {
                    return "MKD";
                }
                case MMK: {
                    return "MMK";
                }
                case MNT: {
                    return "MNT";
                }
                case MOP: {
                    return "MOP";
                }
                case MUR: {
                    return "MUR";
                }
                case MVR: {
                    return "MVR";
                }
                case MWK: {
                    return "MWK";
                }
                case MXN: {
                    return "MXN";
                }
                case MYR: {
                    return "MYR";
                }
                case MZN: {
                    return "MZN";
                }
                case NAD: {
                    return "NAD";
                }
                case NGN: {
                    return "NGN";
                }
                case NIO: {
                    return "NIO";
                }
                case NOK: {
                    return "NOK";
                }
                case NPR: {
                    return "NPR";
                }
                case NZD: {
                    return "NZD";
                }
                case OMR: {
                    return "OMR";
                }
                case PEN: {
                    return "PEN";
                }
                case PGK: {
                    return "PGK";
                }
                case PHP: {
                    return "PHP";
                }
                case PKR: {
                    return "PKR";
                }
                case PLN: {
                    return "PLN";
                }
                case PYG: {
                    return "PYG";
                }
                case QAR: {
                    return "QAR";
                }
                case RON: {
                    return "RON";
                }
                case RSD: {
                    return "RSD";
                }
                case RUB: {
                    return "RUB";
                }
                case RWF: {
                    return "RWF";
                }
                case SAR: {
                    return "SAR";
                }
                case SBD: {
                    return "SBD";
                }
                case SCR: {
                    return "SCR";
                }
                case SDG: {
                    return "SDG";
                }
                case SEK: {
                    return "SEK";
                }
                case SGD: {
                    return "SGD";
                }
                case SRD: {
                    return "SRD";
                }
                case SSP: {
                    return "SSP";
                }
                case STD: {
                    return "STD";
                }
                case SYP: {
                    return "SYP";
                }
                case THB: {
                    return "THB";
                }
                case TMT: {
                    return "TMT";
                }
                case TND: {
                    return "TND";
                }
                case TRY: {
                    return "TRY";
                }
                case TTD: {
                    return "TTD";
                }
                case TWD: {
                    return "TWD";
                }
                case TZS: {
                    return "TZS";
                }
                case UAH: {
                    return "UAH";
                }
                case UGX: {
                    return "UGX";
                }
                case USD: {
                    return "USD";
                }
                case UYU: {
                    return "UYU";
                }
                case UZS: {
                    return "UZS";
                }
                case VEF: {
                    return "VEF";
                }
                case VND: {
                    return "VND";
                }
                case VUV: {
                    return "VUV";
                }
                case WST: {
                    return "WST";
                }
                case XAF: {
                    return "XAF";
                }
                case XCD: {
                    return "XCD";
                }
                case XOF: {
                    return "XOF";
                }
                case XPF: {
                    return "XPF";
                }
                case YER: {
                    return "YER";
                }
                case ZAR: {
                    return "ZAR";
                }
                case ZMW: 
            }
            return "ZMW";
        }
    }

    public static class Customer
    extends AbstractResponse<Customer> {
        public Customer() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Customer(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block30 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 2092353019: {
                        if (!((String)object).equals("acceptsMarketing")) break;
                        n = 0;
                        break;
                    }
                    case 874544034: {
                        if (!((String)object).equals("addresses")) break;
                        n = 1;
                        break;
                    }
                    case 598371643: {
                        if (!((String)object).equals("createdAt")) break;
                        n = 2;
                        break;
                    }
                    case 856425075: {
                        if (!((String)object).equals("defaultAddress")) break;
                        n = 3;
                        break;
                    }
                    case 1714148973: {
                        if (!((String)object).equals("displayName")) break;
                        n = 4;
                        break;
                    }
                    case 96619420: {
                        if (!((String)object).equals("email")) break;
                        n = 5;
                        break;
                    }
                    case 132835675: {
                        if (!((String)object).equals("firstName")) break;
                        n = 6;
                        break;
                    }
                    case 3355: {
                        if (!((String)object).equals("id")) break;
                        n = 7;
                        break;
                    }
                    case -1459599807: {
                        if (!((String)object).equals("lastName")) break;
                        n = 8;
                        break;
                    }
                    case -1008770331: {
                        if (!((String)object).equals("orders")) break;
                        n = 9;
                        break;
                    }
                    case 106642798: {
                        if (!((String)object).equals("phone")) break;
                        n = 10;
                        break;
                    }
                    case -1949194674: {
                        if (!((String)object).equals("updatedAt")) break;
                        n = 11;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 12;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block30;
                    }
                    case 1: {
                        this.responseData.put(string2, new MailingAddressConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block30;
                    }
                    case 2: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block30;
                    }
                    case 3: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new MailingAddress(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 4: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block30;
                    }
                    case 5: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 6: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 7: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block30;
                    }
                    case 8: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 9: {
                        this.responseData.put(string2, new OrderConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block30;
                    }
                    case 10: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 11: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block30;
                    }
                    case 12: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerAccessToken
    extends AbstractResponse<CustomerAccessToken> {
        public CustomerAccessToken() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerAccessToken(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1042689291: {
                        if (!string3.equals("accessToken")) break;
                        n = 0;
                        break;
                    }
                    case 250196615: {
                        if (!string3.equals("expiresAt")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerAccessTokenCreatePayload
    extends AbstractResponse<CustomerAccessTokenCreatePayload> {
        public CustomerAccessTokenCreatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerAccessTokenCreatePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case -1617296329: {
                        if (!((String)((Object)serializable)).equals("customerAccessToken")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new CustomerAccessToken(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerAccessTokenDeletePayload
    extends AbstractResponse<CustomerAccessTokenDeletePayload> {
        public CustomerAccessTokenDeletePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerAccessTokenDeletePayload(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block12 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -772509412: {
                        if (!((String)object).equals("deletedAccessToken")) break;
                        n = 0;
                        break;
                    }
                    case 770585881: {
                        if (!((String)object).equals("deletedCustomerAccessTokenId")) break;
                        n = 1;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)object).equals("userErrors")) break;
                        n = 2;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 3;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block12;
                    }
                    case 1: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block12;
                    }
                    case 2: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block12;
                    }
                    case 3: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerAccessTokenRenewPayload
    extends AbstractResponse<CustomerAccessTokenRenewPayload> {
        public CustomerAccessTokenRenewPayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerAccessTokenRenewPayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case -1617296329: {
                        if (!((String)((Object)serializable)).equals("customerAccessToken")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new CustomerAccessToken(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerActivatePayload
    extends AbstractResponse<CustomerActivatePayload> {
        public CustomerActivatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerActivatePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 606175198: {
                        if (!((String)((Object)serializable)).equals("customer")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Customer(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerAddressCreatePayload
    extends AbstractResponse<CustomerAddressCreatePayload> {
        public CustomerAddressCreatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerAddressCreatePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 236371510: {
                        if (!((String)((Object)serializable)).equals("customerAddress")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new MailingAddress(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerAddressDeletePayload
    extends AbstractResponse<CustomerAddressDeletePayload> {
        public CustomerAddressDeletePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerAddressDeletePayload(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 530527960: {
                        if (!((String)object).equals("deletedCustomerAddressId")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)object).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerAddressUpdatePayload
    extends AbstractResponse<CustomerAddressUpdatePayload> {
        public CustomerAddressUpdatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerAddressUpdatePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 236371510: {
                        if (!((String)((Object)serializable)).equals("customerAddress")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new MailingAddress(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerCreatePayload
    extends AbstractResponse<CustomerCreatePayload> {
        public CustomerCreatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerCreatePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 606175198: {
                        if (!((String)((Object)serializable)).equals("customer")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Customer(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerRecoverPayload
    extends AbstractResponse<CustomerRecoverPayload> {
        public CustomerRecoverPayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerRecoverPayload(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block8 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1189479190: {
                        if (((String)object).equals("userErrors")) {
                            n = 0;
                        }
                    }
                    default: {
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 1;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block8;
                    }
                    case 1: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerResetPayload
    extends AbstractResponse<CustomerResetPayload> {
        public CustomerResetPayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerResetPayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 606175198: {
                        if (!((String)((Object)serializable)).equals("customer")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Customer(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class CustomerUpdatePayload
    extends AbstractResponse<CustomerUpdatePayload> {
        public CustomerUpdatePayload() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public CustomerUpdatePayload(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 606175198: {
                        if (!((String)((Object)serializable)).equals("customer")) break;
                        n = 0;
                        break;
                    }
                    case 1189479190: {
                        if (!((String)((Object)serializable)).equals("userErrors")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new Customer(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 1: {
                        serializable = new ArrayList<UserError>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new UserError(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static enum DigitalWallet {
        ANDROID_PAY,
        APPLE_PAY,
        SHOPIFY_PAY,
        UNKNOWN_VALUE;


        /*
         * Enabled aggressive block sorting
         */
        public static DigitalWallet fromGraphQl(String string2) {
            if (string2 == null) {
                return null;
            }
            int n = -1;
            switch (string2.hashCode()) {
                case -1203214696: {
                    if (!string2.equals("ANDROID_PAY")) break;
                    n = 0;
                    break;
                }
                case 693748227: {
                    if (!string2.equals("APPLE_PAY")) break;
                    n = 1;
                    break;
                }
                case -998851345: {
                    if (!string2.equals("SHOPIFY_PAY")) break;
                    n = 2;
                    break;
                }
            }
            switch (n) {
                default: {
                    return UNKNOWN_VALUE;
                }
                case 0: {
                    return ANDROID_PAY;
                }
                case 1: {
                    return APPLE_PAY;
                }
                case 2: 
            }
            return SHOPIFY_PAY;
        }

        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case ANDROID_PAY: {
                    return "ANDROID_PAY";
                }
                case APPLE_PAY: {
                    return "APPLE_PAY";
                }
                case SHOPIFY_PAY: 
            }
            return "SHOPIFY_PAY";
        }
    }

    public static class Domain
    extends AbstractResponse<Domain> {
        public Domain() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Domain(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block12 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case 3208616: {
                        if (!string3.equals("host")) break;
                        n = 0;
                        break;
                    }
                    case 771932053: {
                        if (!string3.equals("sslEnabled")) break;
                        n = 1;
                        break;
                    }
                    case 116079: {
                        if (!string3.equals("url")) break;
                        n = 2;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 3;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block12;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block12;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block12;
                    }
                    case 3: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class Image
    extends AbstractResponse<Image> {
        public Image() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Image(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block12 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -904776074: {
                        if (!((String)object).equals("altText")) break;
                        n = 0;
                        break;
                    }
                    case 3355: {
                        if (!((String)object).equals("id")) break;
                        n = 1;
                        break;
                    }
                    case 114148: {
                        if (!((String)object).equals("src")) break;
                        n = 2;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 3;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block12;
                    }
                    case 1: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new ID(this.jsonAsString(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block12;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block12;
                    }
                    case 3: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public String getSrc() {
            return (String)this.get("src");
        }
    }

    public static class ImageConnection
    extends AbstractResponse<ImageConnection> {
        public ImageConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ImageConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new ImageEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public List<ImageEdge> getEdges() {
            return (List)this.get("edges");
        }
    }

    public static class ImageConnectionQuery
    extends Query<ImageConnectionQuery> {
        ImageConnectionQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public ImageConnectionQuery edges(ImageEdgeQueryDefinition imageEdgeQueryDefinition) {
            this.startField("edges");
            this._queryBuilder.append('{');
            imageEdgeQueryDefinition.define(new ImageEdgeQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface ImageConnectionQueryDefinition {
        public void define(ImageConnectionQuery var1);
    }

    public static class ImageEdge
    extends AbstractResponse<ImageEdge> {
        public ImageEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ImageEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new Image(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public Image getNode() {
            return (Image)this.get("node");
        }
    }

    public static class ImageEdgeQuery
    extends Query<ImageEdgeQuery> {
        ImageEdgeQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public ImageEdgeQuery node(ImageQueryDefinition imageQueryDefinition) {
            this.startField("node");
            this._queryBuilder.append('{');
            imageQueryDefinition.define(new ImageQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface ImageEdgeQueryDefinition {
        public void define(ImageEdgeQuery var1);
    }

    public static class ImageQuery
    extends Query<ImageQuery> {
        ImageQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public ImageQuery src() {
            this.startField("src");
            return this;
        }
    }

    public static interface ImageQueryDefinition {
        public void define(ImageQuery var1);
    }

    public static class MailingAddress
    extends AbstractResponse<MailingAddress>
    implements Node {
        public MailingAddress() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public MailingAddress(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block42 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -1218714947: {
                        if (!((String)object).equals("address1")) break;
                        n = 0;
                        break;
                    }
                    case -1218714946: {
                        if (!((String)object).equals("address2")) break;
                        n = 1;
                        break;
                    }
                    case 3053931: {
                        if (!((String)object).equals("city")) break;
                        n = 2;
                        break;
                    }
                    case 950484093: {
                        if (!((String)object).equals("company")) break;
                        n = 3;
                        break;
                    }
                    case 957831062: {
                        if (!((String)object).equals("country")) break;
                        n = 4;
                        break;
                    }
                    case -1477067101: {
                        if (!((String)object).equals("countryCode")) break;
                        n = 5;
                        break;
                    }
                    case 132835675: {
                        if (!((String)object).equals("firstName")) break;
                        n = 6;
                        break;
                    }
                    case 1811591356: {
                        if (!((String)object).equals("formatted")) break;
                        n = 7;
                        break;
                    }
                    case -1717880983: {
                        if (!((String)object).equals("formattedArea")) break;
                        n = 8;
                        break;
                    }
                    case 3355: {
                        if (!((String)object).equals("id")) break;
                        n = 9;
                        break;
                    }
                    case -1459599807: {
                        if (!((String)object).equals("lastName")) break;
                        n = 10;
                        break;
                    }
                    case -1439978388: {
                        if (!((String)object).equals("latitude")) break;
                        n = 11;
                        break;
                    }
                    case 137365935: {
                        if (!((String)object).equals("longitude")) break;
                        n = 12;
                        break;
                    }
                    case 3373707: {
                        if (!((String)object).equals("name")) break;
                        n = 13;
                        break;
                    }
                    case 106642798: {
                        if (!((String)object).equals("phone")) break;
                        n = 14;
                        break;
                    }
                    case -987485392: {
                        if (!((String)object).equals("province")) break;
                        n = 15;
                        break;
                    }
                    case -203737795: {
                        if (!((String)object).equals("provinceCode")) break;
                        n = 16;
                        break;
                    }
                    case 120609: {
                        if (!((String)object).equals("zip")) break;
                        n = 17;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 18;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 1: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 2: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 3: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 4: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 5: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 6: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 7: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(this.jsonAsString(iterator2.next(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 8: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 9: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block42;
                    }
                    case 10: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 11: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsDouble(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 12: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsDouble(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 13: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 14: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 15: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 16: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 17: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 18: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class MailingAddressConnection
    extends AbstractResponse<MailingAddressConnection> {
        public MailingAddressConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public MailingAddressConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new MailingAddressEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class MailingAddressEdge
    extends AbstractResponse<MailingAddressEdge> {
        public MailingAddressEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public MailingAddressEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new MailingAddress(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class MailingAddressInput
    implements Serializable {
        private String address1;
        private String address2;
        private String city;
        private String company;
        private String country;
        private String firstName;
        private String lastName;
        private String phone;
        private String province;
        private String zip;

        public void appendTo(StringBuilder stringBuilder) {
            String string2 = "";
            stringBuilder.append('{');
            if (this.address1 != null) {
                stringBuilder.append("");
                string2 = ",";
                stringBuilder.append("address1:");
                Query.appendQuotedString(stringBuilder, this.address1.toString());
            }
            String string3 = string2;
            if (this.address2 != null) {
                stringBuilder.append(string2);
                string3 = ",";
                stringBuilder.append("address2:");
                Query.appendQuotedString(stringBuilder, this.address2.toString());
            }
            string2 = string3;
            if (this.city != null) {
                stringBuilder.append(string3);
                string2 = ",";
                stringBuilder.append("city:");
                Query.appendQuotedString(stringBuilder, this.city.toString());
            }
            string3 = string2;
            if (this.company != null) {
                stringBuilder.append(string2);
                string3 = ",";
                stringBuilder.append("company:");
                Query.appendQuotedString(stringBuilder, this.company.toString());
            }
            string2 = string3;
            if (this.country != null) {
                stringBuilder.append(string3);
                string2 = ",";
                stringBuilder.append("country:");
                Query.appendQuotedString(stringBuilder, this.country.toString());
            }
            string3 = string2;
            if (this.firstName != null) {
                stringBuilder.append(string2);
                string3 = ",";
                stringBuilder.append("firstName:");
                Query.appendQuotedString(stringBuilder, this.firstName.toString());
            }
            string2 = string3;
            if (this.lastName != null) {
                stringBuilder.append(string3);
                string2 = ",";
                stringBuilder.append("lastName:");
                Query.appendQuotedString(stringBuilder, this.lastName.toString());
            }
            string3 = string2;
            if (this.phone != null) {
                stringBuilder.append(string2);
                string3 = ",";
                stringBuilder.append("phone:");
                Query.appendQuotedString(stringBuilder, this.phone.toString());
            }
            string2 = string3;
            if (this.province != null) {
                stringBuilder.append(string3);
                string2 = ",";
                stringBuilder.append("province:");
                Query.appendQuotedString(stringBuilder, this.province.toString());
            }
            if (this.zip != null) {
                stringBuilder.append(string2);
                stringBuilder.append("zip:");
                Query.appendQuotedString(stringBuilder, this.zip.toString());
            }
            stringBuilder.append('}');
        }

        public MailingAddressInput setAddress1(String string2) {
            this.address1 = string2;
            return this;
        }

        public MailingAddressInput setAddress2(String string2) {
            this.address2 = string2;
            return this;
        }

        public MailingAddressInput setCity(String string2) {
            this.city = string2;
            return this;
        }

        public MailingAddressInput setCountry(String string2) {
            this.country = string2;
            return this;
        }

        public MailingAddressInput setFirstName(String string2) {
            this.firstName = string2;
            return this;
        }

        public MailingAddressInput setLastName(String string2) {
            this.lastName = string2;
            return this;
        }

        public MailingAddressInput setPhone(String string2) {
            this.phone = string2;
            return this;
        }

        public MailingAddressInput setProvince(String string2) {
            this.province = string2;
            return this;
        }

        public MailingAddressInput setZip(String string2) {
            this.zip = string2;
            return this;
        }
    }

    public static class Mutation
    extends AbstractResponse<Mutation> {
        public Mutation() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Mutation(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block58 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -892963226: {
                        if (!((String)object).equals("checkoutAttributesUpdate")) break;
                        n = 0;
                        break;
                    }
                    case 1563975595: {
                        if (!((String)object).equals("checkoutCompleteFree")) break;
                        n = 1;
                        break;
                    }
                    case -1364309618: {
                        if (!((String)object).equals("checkoutCompleteWithCreditCard")) break;
                        n = 2;
                        break;
                    }
                    case 53373666: {
                        if (!((String)object).equals("checkoutCompleteWithTokenizedPayment")) break;
                        n = 3;
                        break;
                    }
                    case 720129506: {
                        if (!((String)object).equals("checkoutCreate")) break;
                        n = 4;
                        break;
                    }
                    case -2025844678: {
                        if (!((String)object).equals("checkoutCustomerAssociate")) break;
                        n = 5;
                        break;
                    }
                    case 143713204: {
                        if (!((String)object).equals("checkoutCustomerDisassociate")) break;
                        n = 6;
                        break;
                    }
                    case 1166982751: {
                        if (!((String)object).equals("checkoutEmailUpdate")) break;
                        n = 7;
                        break;
                    }
                    case -900222136: {
                        if (!((String)object).equals("checkoutGiftCardApply")) break;
                        n = 8;
                        break;
                    }
                    case -1660632086: {
                        if (!((String)object).equals("checkoutGiftCardRemove")) break;
                        n = 9;
                        break;
                    }
                    case -2028746437: {
                        if (!((String)object).equals("checkoutLineItemsAdd")) break;
                        n = 10;
                        break;
                    }
                    case 882682282: {
                        if (!((String)object).equals("checkoutLineItemsRemove")) break;
                        n = 11;
                        break;
                    }
                    case 978446831: {
                        if (!((String)object).equals("checkoutLineItemsUpdate")) break;
                        n = 12;
                        break;
                    }
                    case 1633198377: {
                        if (!((String)object).equals("checkoutShippingAddressUpdate")) break;
                        n = 13;
                        break;
                    }
                    case -437024239: {
                        if (!((String)object).equals("checkoutShippingLineUpdate")) break;
                        n = 14;
                        break;
                    }
                    case 1934117907: {
                        if (!((String)object).equals("customerAccessTokenCreate")) break;
                        n = 15;
                        break;
                    }
                    case 1950953666: {
                        if (!((String)object).equals("customerAccessTokenDelete")) break;
                        n = 16;
                        break;
                    }
                    case 1184243862: {
                        if (!((String)object).equals("customerAccessTokenRenew")) break;
                        n = 17;
                        break;
                    }
                    case -1699677455: {
                        if (!((String)object).equals("customerActivate")) break;
                        n = 18;
                        break;
                    }
                    case 652954322: {
                        if (!((String)object).equals("customerAddressCreate")) break;
                        n = 19;
                        break;
                    }
                    case 669790081: {
                        if (!((String)object).equals("customerAddressDelete")) break;
                        n = 20;
                        break;
                    }
                    case 1166402207: {
                        if (!((String)object).equals("customerAddressUpdate")) break;
                        n = 21;
                        break;
                    }
                    case 1463299706: {
                        if (!((String)object).equals("customerCreate")) break;
                        n = 22;
                        break;
                    }
                    case -1828302938: {
                        if (!((String)object).equals("customerRecover")) break;
                        n = 23;
                        break;
                    }
                    case 2138892305: {
                        if (!((String)object).equals("customerReset")) break;
                        n = 24;
                        break;
                    }
                    case 1976747591: {
                        if (!((String)object).equals("customerUpdate")) break;
                        n = 25;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 26;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutAttributesUpdatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 1: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutCompleteFreePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 2: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutCompleteWithCreditCardPayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 3: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutCompleteWithTokenizedPaymentPayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 4: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutCreatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 5: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutCustomerAssociatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 6: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutCustomerDisassociatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 7: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutEmailUpdatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 8: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutGiftCardApplyPayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 9: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutGiftCardRemovePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 10: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutLineItemsAddPayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 11: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutLineItemsRemovePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 12: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutLineItemsUpdatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 13: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutShippingAddressUpdatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 14: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CheckoutShippingLineUpdatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 15: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerAccessTokenCreatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 16: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerAccessTokenDeletePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 17: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerAccessTokenRenewPayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 18: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerActivatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 19: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerAddressCreatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 20: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerAddressDeletePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 21: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerAddressUpdatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 22: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerCreatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 23: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerRecoverPayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 24: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerResetPayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 25: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CustomerUpdatePayload(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block58;
                    }
                    case 26: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public CheckoutCompleteWithTokenizedPaymentPayload getCheckoutCompleteWithTokenizedPayment() {
            return (CheckoutCompleteWithTokenizedPaymentPayload)this.get("checkoutCompleteWithTokenizedPayment");
        }

        public CheckoutCreatePayload getCheckoutCreate() {
            return (CheckoutCreatePayload)this.get("checkoutCreate");
        }

        public CheckoutEmailUpdatePayload getCheckoutEmailUpdate() {
            return (CheckoutEmailUpdatePayload)this.get("checkoutEmailUpdate");
        }

        public CheckoutShippingAddressUpdatePayload getCheckoutShippingAddressUpdate() {
            return (CheckoutShippingAddressUpdatePayload)this.get("checkoutShippingAddressUpdate");
        }

        public CheckoutShippingLineUpdatePayload getCheckoutShippingLineUpdate() {
            return (CheckoutShippingLineUpdatePayload)this.get("checkoutShippingLineUpdate");
        }
    }

    public static class MutationQuery
    extends Query<MutationQuery> {
        MutationQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public MutationQuery checkoutCompleteWithTokenizedPayment(ID iD, TokenizedPaymentInput tokenizedPaymentInput, CheckoutCompleteWithTokenizedPaymentPayloadQueryDefinition checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition) {
            this.startField("checkoutCompleteWithTokenizedPayment");
            this._queryBuilder.append("(checkoutId:");
            Query.appendQuotedString(this._queryBuilder, iD.toString());
            this._queryBuilder.append(",payment:");
            tokenizedPaymentInput.appendTo(this._queryBuilder);
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            checkoutCompleteWithTokenizedPaymentPayloadQueryDefinition.define(new CheckoutCompleteWithTokenizedPaymentPayloadQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public MutationQuery checkoutCreate(CheckoutCreateInput checkoutCreateInput, CheckoutCreatePayloadQueryDefinition checkoutCreatePayloadQueryDefinition) {
            this.startField("checkoutCreate");
            this._queryBuilder.append("(input:");
            checkoutCreateInput.appendTo(this._queryBuilder);
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            checkoutCreatePayloadQueryDefinition.define(new CheckoutCreatePayloadQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public MutationQuery checkoutEmailUpdate(ID iD, String string2, CheckoutEmailUpdatePayloadQueryDefinition checkoutEmailUpdatePayloadQueryDefinition) {
            this.startField("checkoutEmailUpdate");
            this._queryBuilder.append("(checkoutId:");
            Query.appendQuotedString(this._queryBuilder, iD.toString());
            this._queryBuilder.append(",email:");
            Query.appendQuotedString(this._queryBuilder, string2.toString());
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            checkoutEmailUpdatePayloadQueryDefinition.define(new CheckoutEmailUpdatePayloadQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public MutationQuery checkoutShippingAddressUpdate(MailingAddressInput mailingAddressInput, ID iD, CheckoutShippingAddressUpdatePayloadQueryDefinition checkoutShippingAddressUpdatePayloadQueryDefinition) {
            this.startField("checkoutShippingAddressUpdate");
            this._queryBuilder.append("(shippingAddress:");
            mailingAddressInput.appendTo(this._queryBuilder);
            this._queryBuilder.append(",checkoutId:");
            Query.appendQuotedString(this._queryBuilder, iD.toString());
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            checkoutShippingAddressUpdatePayloadQueryDefinition.define(new CheckoutShippingAddressUpdatePayloadQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public MutationQuery checkoutShippingLineUpdate(ID iD, String string2, CheckoutShippingLineUpdatePayloadQueryDefinition checkoutShippingLineUpdatePayloadQueryDefinition) {
            this.startField("checkoutShippingLineUpdate");
            this._queryBuilder.append("(checkoutId:");
            Query.appendQuotedString(this._queryBuilder, iD.toString());
            this._queryBuilder.append(",shippingRateHandle:");
            Query.appendQuotedString(this._queryBuilder, string2.toString());
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            checkoutShippingLineUpdatePayloadQueryDefinition.define(new CheckoutShippingLineUpdatePayloadQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public String toString() {
            return this._queryBuilder.toString();
        }
    }

    public static interface MutationQueryDefinition {
        public void define(MutationQuery var1);
    }

    public static interface Node {
    }

    public static class NodeQuery
    extends Query<NodeQuery> {
        NodeQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
            this.startField("__typename");
        }

        public NodeQuery onCheckout(CheckoutQueryDefinition checkoutQueryDefinition) {
            this.startInlineFragment("Checkout");
            checkoutQueryDefinition.define(new CheckoutQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public NodeQuery onCollection(CollectionQueryDefinition collectionQueryDefinition) {
            this.startInlineFragment("Collection");
            collectionQueryDefinition.define(new CollectionQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public NodeQuery onPayment(PaymentQueryDefinition paymentQueryDefinition) {
            this.startInlineFragment("Payment");
            paymentQueryDefinition.define(new PaymentQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public NodeQuery onProduct(ProductQueryDefinition productQueryDefinition) {
            this.startInlineFragment("Product");
            productQueryDefinition.define(new ProductQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface NodeQueryDefinition {
        public void define(NodeQuery var1);
    }

    public static class Order
    extends AbstractResponse<Order>
    implements Node {
        public Order() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Order(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block36 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 1004773790: {
                        if (!((String)object).equals("currencyCode")) break;
                        n = 0;
                        break;
                    }
                    case 1718131672: {
                        if (!((String)object).equals("customerLocale")) break;
                        n = 1;
                        break;
                    }
                    case -1772070735: {
                        if (!((String)object).equals("customerUrl")) break;
                        n = 2;
                        break;
                    }
                    case 96619420: {
                        if (!((String)object).equals("email")) break;
                        n = 3;
                        break;
                    }
                    case 3355: {
                        if (!((String)object).equals("id")) break;
                        n = 4;
                        break;
                    }
                    case -1816387540: {
                        if (!((String)object).equals("lineItems")) break;
                        n = 5;
                        break;
                    }
                    case 1488198711: {
                        if (!((String)object).equals("orderNumber")) break;
                        n = 6;
                        break;
                    }
                    case 106642798: {
                        if (!((String)object).equals("phone")) break;
                        n = 7;
                        break;
                    }
                    case 203012129: {
                        if (!((String)object).equals("processedAt")) break;
                        n = 8;
                        break;
                    }
                    case 1193227878: {
                        if (!((String)object).equals("shippingAddress")) break;
                        n = 9;
                        break;
                    }
                    case 2109292197: {
                        if (!((String)object).equals("subtotalPrice")) break;
                        n = 10;
                        break;
                    }
                    case -719302555: {
                        if (!((String)object).equals("totalPrice")) break;
                        n = 11;
                        break;
                    }
                    case -681465477: {
                        if (!((String)object).equals("totalRefunded")) break;
                        n = 12;
                        break;
                    }
                    case -423820457: {
                        if (!((String)object).equals("totalShippingPrice")) break;
                        n = 13;
                        break;
                    }
                    case -849906233: {
                        if (!((String)object).equals("totalTax")) break;
                        n = 14;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 15;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, CurrencyCode.fromGraphQl(this.jsonAsString(entry.getValue(), string2)));
                        continue block36;
                    }
                    case 1: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block36;
                    }
                    case 2: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block36;
                    }
                    case 3: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block36;
                    }
                    case 4: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block36;
                    }
                    case 5: {
                        this.responseData.put(string2, new OrderLineItemConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block36;
                    }
                    case 6: {
                        this.responseData.put(string2, this.jsonAsInteger(entry.getValue(), string2));
                        continue block36;
                    }
                    case 7: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block36;
                    }
                    case 8: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block36;
                    }
                    case 9: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new MailingAddress(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block36;
                    }
                    case 10: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new BigDecimal(this.jsonAsString(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block36;
                    }
                    case 11: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block36;
                    }
                    case 12: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block36;
                    }
                    case 13: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block36;
                    }
                    case 14: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new BigDecimal(this.jsonAsString(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block36;
                    }
                    case 15: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class OrderConnection
    extends AbstractResponse<OrderConnection> {
        public OrderConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public OrderConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new OrderEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class OrderEdge
    extends AbstractResponse<OrderEdge> {
        public OrderEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public OrderEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new Order(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class OrderLineItem
    extends AbstractResponse<OrderLineItem> {
        public OrderLineItem() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public OrderLineItem(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block14 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 555169704: {
                        if (!((String)((Object)serializable)).equals("customAttributes")) break;
                        n = 0;
                        break;
                    }
                    case -1285004149: {
                        if (!((String)((Object)serializable)).equals("quantity")) break;
                        n = 1;
                        break;
                    }
                    case 110371416: {
                        if (!((String)((Object)serializable)).equals("title")) break;
                        n = 2;
                        break;
                    }
                    case 236785797: {
                        if (!((String)((Object)serializable)).equals("variant")) break;
                        n = 3;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 4;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        serializable = new ArrayList<Attribute>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new Attribute(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block14;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsInteger(entry.getValue(), string2));
                        continue block14;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block14;
                    }
                    case 3: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new ProductVariant(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block14;
                    }
                    case 4: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class OrderLineItemConnection
    extends AbstractResponse<OrderLineItemConnection> {
        public OrderLineItemConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public OrderLineItemConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new OrderLineItemEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class OrderLineItemEdge
    extends AbstractResponse<OrderLineItemEdge> {
        public OrderLineItemEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public OrderLineItemEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new OrderLineItem(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static enum OrderSortKeys {
        ID,
        PROCESSED_AT,
        RELEVANCE,
        TOTAL_PRICE,
        UNKNOWN_VALUE;


        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case ID: {
                    return "ID";
                }
                case PROCESSED_AT: {
                    return "PROCESSED_AT";
                }
                case RELEVANCE: {
                    return "RELEVANCE";
                }
                case TOTAL_PRICE: 
            }
            return "TOTAL_PRICE";
        }
    }

    public static class PageInfo
    extends AbstractResponse<PageInfo> {
        public PageInfo() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public PageInfo(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case 3763420: {
                        if (!string3.equals("hasNextPage")) break;
                        n = 0;
                        break;
                    }
                    case 37293536: {
                        if (!string3.equals("hasPreviousPage")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class Payment
    extends AbstractResponse<Payment>
    implements Node {
        public Payment() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Payment(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block26 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -1413853096: {
                        if (!((String)object).equals("amount")) break;
                        n = 0;
                        break;
                    }
                    case 738353401: {
                        if (!((String)object).equals("billingAddress")) break;
                        n = 1;
                        break;
                    }
                    case 1536904518: {
                        if (!((String)object).equals("checkout")) break;
                        n = 2;
                        break;
                    }
                    case -564824663: {
                        if (!((String)object).equals("creditCard")) break;
                        n = 3;
                        break;
                    }
                    case 1203236063: {
                        if (!((String)object).equals("errorMessage")) break;
                        n = 4;
                        break;
                    }
                    case 3355: {
                        if (!((String)object).equals("id")) break;
                        n = 5;
                        break;
                    }
                    case 430456398: {
                        if (!((String)object).equals("idempotencyKey")) break;
                        n = 6;
                        break;
                    }
                    case 108386723: {
                        if (!((String)object).equals("ready")) break;
                        n = 7;
                        break;
                    }
                    case 3556498: {
                        if (!((String)object).equals("test")) break;
                        n = 8;
                        break;
                    }
                    case 2141246174: {
                        if (!((String)object).equals("transaction")) break;
                        n = 9;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 10;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block26;
                    }
                    case 1: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new MailingAddress(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block26;
                    }
                    case 2: {
                        this.responseData.put(string2, new Checkout(this.jsonAsObject(entry.getValue(), string2)));
                        continue block26;
                    }
                    case 3: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new CreditCard(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block26;
                    }
                    case 4: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block26;
                    }
                    case 5: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block26;
                    }
                    case 6: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block26;
                    }
                    case 7: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block26;
                    }
                    case 8: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block26;
                    }
                    case 9: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new Transaction(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block26;
                    }
                    case 10: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public String getErrorMessage() {
            return (String)this.get("errorMessage");
        }

        public ID getId() {
            return (ID)this.get("id");
        }

        public Boolean getReady() {
            return (Boolean)this.get("ready");
        }
    }

    public static class PaymentQuery
    extends Query<PaymentQuery> {
        PaymentQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
            this.startField("id");
        }

        public PaymentQuery errorMessage() {
            this.startField("errorMessage");
            return this;
        }

        public PaymentQuery ready() {
            this.startField("ready");
            return this;
        }
    }

    public static interface PaymentQueryDefinition {
        public void define(PaymentQuery var1);
    }

    public static class PaymentSettings
    extends AbstractResponse<PaymentSettings> {
        public PaymentSettings() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public PaymentSettings(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block18 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -214673405: {
                        if (!((String)object).equals("acceptedCardBrands")) break;
                        n = 0;
                        break;
                    }
                    case -696589043: {
                        if (!((String)object).equals("cardVaultUrl")) break;
                        n = 1;
                        break;
                    }
                    case -1477067101: {
                        if (!((String)object).equals("countryCode")) break;
                        n = 2;
                        break;
                    }
                    case 1004773790: {
                        if (!((String)object).equals("currencyCode")) break;
                        n = 3;
                        break;
                    }
                    case 78140373: {
                        if (!((String)object).equals("shopifyPaymentsAccountId")) break;
                        n = 4;
                        break;
                    }
                    case 1497113264: {
                        if (!((String)object).equals("supportedDigitalWallets")) break;
                        n = 5;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 6;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(CardBrand.fromGraphQl(this.jsonAsString(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block18;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block18;
                    }
                    case 2: {
                        this.responseData.put(string2, CountryCode.fromGraphQl(this.jsonAsString(entry.getValue(), string2)));
                        continue block18;
                    }
                    case 3: {
                        this.responseData.put(string2, CurrencyCode.fromGraphQl(this.jsonAsString(entry.getValue(), string2)));
                        continue block18;
                    }
                    case 4: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block18;
                    }
                    case 5: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator3 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator3.hasNext()) {
                            object.add(DigitalWallet.fromGraphQl(this.jsonAsString(iterator3.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block18;
                    }
                    case 6: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public List<CardBrand> getAcceptedCardBrands() {
            return (List)this.get("acceptedCardBrands");
        }

        public CountryCode getCountryCode() {
            return (CountryCode)((Object)this.get("countryCode"));
        }
    }

    public static class PaymentSettingsQuery
    extends Query<PaymentSettingsQuery> {
        PaymentSettingsQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public PaymentSettingsQuery acceptedCardBrands() {
            this.startField("acceptedCardBrands");
            return this;
        }

        public PaymentSettingsQuery countryCode() {
            this.startField("countryCode");
            return this;
        }
    }

    public static interface PaymentSettingsQueryDefinition {
        public void define(PaymentSettingsQuery var1);
    }

    public static class Product
    extends AbstractResponse<Product>
    implements Node {
        public Product() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Product(JsonObject serializable) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)serializable)).entrySet().iterator();
            block38 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                serializable = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)serializable)).hashCode()) {
                    case 1853891989: {
                        if (!((String)((Object)serializable)).equals("collections")) break;
                        n = 0;
                        break;
                    }
                    case 598371643: {
                        if (!((String)((Object)serializable)).equals("createdAt")) break;
                        n = 1;
                        break;
                    }
                    case -1724546052: {
                        if (!((String)((Object)serializable)).equals("description")) break;
                        n = 2;
                        break;
                    }
                    case 985506247: {
                        if (!((String)((Object)serializable)).equals("descriptionHtml")) break;
                        n = 3;
                        break;
                    }
                    case -1224577496: {
                        if (!((String)((Object)serializable)).equals("handle")) break;
                        n = 4;
                        break;
                    }
                    case 3355: {
                        if (!((String)((Object)serializable)).equals("id")) break;
                        n = 5;
                        break;
                    }
                    case -1185250696: {
                        if (!((String)((Object)serializable)).equals("images")) break;
                        n = 6;
                        break;
                    }
                    case -1249474914: {
                        if (!((String)((Object)serializable)).equals("options")) break;
                        n = 7;
                        break;
                    }
                    case -1491615543: {
                        if (!((String)((Object)serializable)).equals("productType")) break;
                        n = 8;
                        break;
                    }
                    case -614144319: {
                        if (!((String)((Object)serializable)).equals("publishedAt")) break;
                        n = 9;
                        break;
                    }
                    case 3552281: {
                        if (!((String)((Object)serializable)).equals("tags")) break;
                        n = 10;
                        break;
                    }
                    case 110371416: {
                        if (!((String)((Object)serializable)).equals("title")) break;
                        n = 11;
                        break;
                    }
                    case -1949194674: {
                        if (!((String)((Object)serializable)).equals("updatedAt")) break;
                        n = 12;
                        break;
                    }
                    case 1335514151: {
                        if (!((String)((Object)serializable)).equals("variantBySelectedOptions")) break;
                        n = 13;
                        break;
                    }
                    case -1249574770: {
                        if (!((String)((Object)serializable)).equals("variants")) break;
                        n = 14;
                        break;
                    }
                    case -820075192: {
                        if (!((String)((Object)serializable)).equals("vendor")) break;
                        n = 15;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)serializable)).equals("__typename")) break;
                        n = 16;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new CollectionConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block38;
                    }
                    case 1: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block38;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block38;
                    }
                    case 3: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block38;
                    }
                    case 4: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block38;
                    }
                    case 5: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block38;
                    }
                    case 6: {
                        this.responseData.put(string2, new ImageConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block38;
                    }
                    case 7: {
                        serializable = new ArrayList<Object>();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            serializable.add(new ProductOption(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, serializable);
                        continue block38;
                    }
                    case 8: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block38;
                    }
                    case 9: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block38;
                    }
                    case 10: {
                        serializable = new ArrayList();
                        Iterator<JsonElement> iterator3 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator3.hasNext()) {
                            serializable.add(this.jsonAsString(iterator3.next(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block38;
                    }
                    case 11: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block38;
                    }
                    case 12: {
                        this.responseData.put(string2, Utils.parseDateTime(this.jsonAsString(entry.getValue(), string2)));
                        continue block38;
                    }
                    case 13: {
                        serializable = null;
                        if (!entry.getValue().isJsonNull()) {
                            serializable = new ProductVariant(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, serializable);
                        continue block38;
                    }
                    case 14: {
                        this.responseData.put(string2, new ProductVariantConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block38;
                    }
                    case 15: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block38;
                    }
                    case 16: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public String getDescriptionHtml() {
            return (String)this.get("descriptionHtml");
        }

        public ID getId() {
            return (ID)this.get("id");
        }

        public ImageConnection getImages() {
            return (ImageConnection)this.get("images");
        }

        public List<ProductOption> getOptions() {
            return (List)this.get("options");
        }

        public List<String> getTags() {
            return (List)this.get("tags");
        }

        public String getTitle() {
            return (String)this.get("title");
        }

        public ProductVariantConnection getVariants() {
            return (ProductVariantConnection)this.get("variants");
        }
    }

    public static enum ProductCollectionSortKeys {
        BEST_SELLING,
        COLLECTION_DEFAULT,
        CREATED,
        ID,
        MANUAL,
        PRICE,
        RELEVANCE,
        TITLE,
        UNKNOWN_VALUE;


        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case BEST_SELLING: {
                    return "BEST_SELLING";
                }
                case COLLECTION_DEFAULT: {
                    return "COLLECTION_DEFAULT";
                }
                case CREATED: {
                    return "CREATED";
                }
                case ID: {
                    return "ID";
                }
                case MANUAL: {
                    return "MANUAL";
                }
                case PRICE: {
                    return "PRICE";
                }
                case RELEVANCE: {
                    return "RELEVANCE";
                }
                case TITLE: 
            }
            return "TITLE";
        }
    }

    public static class ProductConnection
    extends AbstractResponse<ProductConnection> {
        public ProductConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ProductConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new ProductEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public List<ProductEdge> getEdges() {
            return (List)this.get("edges");
        }
    }

    public static class ProductConnectionQuery
    extends Query<ProductConnectionQuery> {
        ProductConnectionQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public ProductConnectionQuery edges(ProductEdgeQueryDefinition productEdgeQueryDefinition) {
            this.startField("edges");
            this._queryBuilder.append('{');
            productEdgeQueryDefinition.define(new ProductEdgeQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface ProductConnectionQueryDefinition {
        public void define(ProductConnectionQuery var1);
    }

    public static class ProductEdge
    extends AbstractResponse<ProductEdge> {
        public ProductEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ProductEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new Product(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public String getCursor() {
            return (String)this.get("cursor");
        }

        public Product getNode() {
            return (Product)this.get("node");
        }
    }

    public static class ProductEdgeQuery
    extends Query<ProductEdgeQuery> {
        ProductEdgeQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public ProductEdgeQuery cursor() {
            this.startField("cursor");
            return this;
        }

        public ProductEdgeQuery node(ProductQueryDefinition productQueryDefinition) {
            this.startField("node");
            this._queryBuilder.append('{');
            productQueryDefinition.define(new ProductQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface ProductEdgeQueryDefinition {
        public void define(ProductEdgeQuery var1);
    }

    public static enum ProductImageSortKeys {
        CREATED_AT,
        ID,
        POSITION,
        RELEVANCE,
        UNKNOWN_VALUE;


        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case CREATED_AT: {
                    return "CREATED_AT";
                }
                case ID: {
                    return "ID";
                }
                case POSITION: {
                    return "POSITION";
                }
                case RELEVANCE: 
            }
            return "RELEVANCE";
        }
    }

    public static class ProductOption
    extends AbstractResponse<ProductOption>
    implements Node {
        public ProductOption() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ProductOption(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block12 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 3355: {
                        if (!((String)object).equals("id")) break;
                        n = 0;
                        break;
                    }
                    case 3373707: {
                        if (!((String)object).equals("name")) break;
                        n = 1;
                        break;
                    }
                    case -823812830: {
                        if (!((String)object).equals("values")) break;
                        n = 2;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 3;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block12;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block12;
                    }
                    case 2: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(this.jsonAsString(iterator2.next(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block12;
                    }
                    case 3: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public ID getId() {
            return (ID)this.get("id");
        }

        public String getName() {
            return (String)this.get("name");
        }

        public List<String> getValues() {
            return (List)this.get("values");
        }
    }

    public static class ProductOptionQuery
    extends Query<ProductOptionQuery> {
        ProductOptionQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
            this.startField("id");
        }

        public ProductOptionQuery name() {
            this.startField("name");
            return this;
        }

        public ProductOptionQuery values() {
            this.startField("values");
            return this;
        }
    }

    public static interface ProductOptionQueryDefinition {
        public void define(ProductOptionQuery var1);
    }

    public static class ProductQuery
    extends Query<ProductQuery> {
        ProductQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
            this.startField("id");
        }

        static /* synthetic */ void lambda$images$2(ImagesArguments imagesArguments) {
        }

        static /* synthetic */ void lambda$options$3(OptionsArguments optionsArguments) {
        }

        static /* synthetic */ void lambda$variants$4(VariantsArguments variantsArguments) {
        }

        public ProductQuery descriptionHtml() {
            this.startField("descriptionHtml");
            return this;
        }

        public ProductQuery images(int n, ImageConnectionQueryDefinition imageConnectionQueryDefinition) {
            return this.images(n, Storefront$ProductQuery$$Lambda$3.lambdaFactory$(), imageConnectionQueryDefinition);
        }

        public ProductQuery images(int n, ImagesArgumentsDefinition imagesArgumentsDefinition, ImageConnectionQueryDefinition imageConnectionQueryDefinition) {
            this.startField("images");
            this._queryBuilder.append("(first:");
            this._queryBuilder.append(n);
            imagesArgumentsDefinition.define(new ImagesArguments(this._queryBuilder));
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            imageConnectionQueryDefinition.define(new ImageConnectionQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public ProductQuery options(ProductOptionQueryDefinition productOptionQueryDefinition) {
            return this.options(Storefront$ProductQuery$$Lambda$4.lambdaFactory$(), productOptionQueryDefinition);
        }

        public ProductQuery options(OptionsArgumentsDefinition optionsArgumentsDefinition, ProductOptionQueryDefinition productOptionQueryDefinition) {
            this.startField("options");
            OptionsArguments optionsArguments = new OptionsArguments(this._queryBuilder);
            optionsArgumentsDefinition.define(optionsArguments);
            OptionsArguments.end(optionsArguments);
            this._queryBuilder.append('{');
            productOptionQueryDefinition.define(new ProductOptionQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public ProductQuery tags() {
            this.startField("tags");
            return this;
        }

        public ProductQuery title() {
            this.startField("title");
            return this;
        }

        public ProductQuery variants(int n, VariantsArgumentsDefinition variantsArgumentsDefinition, ProductVariantConnectionQueryDefinition productVariantConnectionQueryDefinition) {
            this.startField("variants");
            this._queryBuilder.append("(first:");
            this._queryBuilder.append(n);
            variantsArgumentsDefinition.define(new VariantsArguments(this._queryBuilder));
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            productVariantConnectionQueryDefinition.define(new ProductVariantConnectionQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public ProductQuery variants(int n, ProductVariantConnectionQueryDefinition productVariantConnectionQueryDefinition) {
            return this.variants(n, Storefront$ProductQuery$$Lambda$5.lambdaFactory$(), productVariantConnectionQueryDefinition);
        }

        public class ImagesArguments
        extends Arguments {
            ImagesArguments(StringBuilder stringBuilder) {
                super(stringBuilder, false);
            }
        }

        public static interface ImagesArgumentsDefinition {
            public void define(ImagesArguments var1);
        }

        public class OptionsArguments
        extends Arguments {
            OptionsArguments(StringBuilder stringBuilder) {
                super(stringBuilder, true);
            }
        }

        public static interface OptionsArgumentsDefinition {
            public void define(OptionsArguments var1);
        }

        public class VariantsArguments
        extends Arguments {
            VariantsArguments(StringBuilder stringBuilder) {
                super(stringBuilder, false);
            }
        }

        public static interface VariantsArgumentsDefinition {
            public void define(VariantsArguments var1);
        }

    }

    public static interface ProductQueryDefinition {
        public void define(ProductQuery var1);
    }

    public static enum ProductSortKeys {
        CREATED_AT,
        ID,
        PRODUCT_TYPE,
        RELEVANCE,
        TITLE,
        UPDATED_AT,
        VENDOR,
        UNKNOWN_VALUE;


        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case CREATED_AT: {
                    return "CREATED_AT";
                }
                case ID: {
                    return "ID";
                }
                case PRODUCT_TYPE: {
                    return "PRODUCT_TYPE";
                }
                case RELEVANCE: {
                    return "RELEVANCE";
                }
                case TITLE: {
                    return "TITLE";
                }
                case UPDATED_AT: {
                    return "UPDATED_AT";
                }
                case VENDOR: 
            }
            return "VENDOR";
        }
    }

    public static class ProductVariant
    extends AbstractResponse<ProductVariant>
    implements Node {
        public ProductVariant() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ProductVariant(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block30 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -733902135: {
                        if (!((String)object).equals("available")) break;
                        n = 0;
                        break;
                    }
                    case 1030176167: {
                        if (!((String)object).equals("availableForSale")) break;
                        n = 1;
                        break;
                    }
                    case -110207951: {
                        if (!((String)object).equals("compareAtPrice")) break;
                        n = 2;
                        break;
                    }
                    case 3355: {
                        if (!((String)object).equals("id")) break;
                        n = 3;
                        break;
                    }
                    case 100313435: {
                        if (!((String)object).equals("image")) break;
                        n = 4;
                        break;
                    }
                    case 106934601: {
                        if (!((String)object).equals("price")) break;
                        n = 5;
                        break;
                    }
                    case -309474065: {
                        if (!((String)object).equals("product")) break;
                        n = 6;
                        break;
                    }
                    case -1814358141: {
                        if (!((String)object).equals("selectedOptions")) break;
                        n = 7;
                        break;
                    }
                    case 113949: {
                        if (!((String)object).equals("sku")) break;
                        n = 8;
                        break;
                    }
                    case 110371416: {
                        if (!((String)object).equals("title")) break;
                        n = 9;
                        break;
                    }
                    case -791592328: {
                        if (!((String)object).equals("weight")) break;
                        n = 10;
                        break;
                    }
                    case -1457286116: {
                        if (!((String)object).equals("weightUnit")) break;
                        n = 11;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 12;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsBoolean(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block30;
                    }
                    case 2: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new BigDecimal(this.jsonAsString(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 3: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block30;
                    }
                    case 4: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new Image(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 5: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block30;
                    }
                    case 6: {
                        this.responseData.put(string2, new Product(this.jsonAsObject(entry.getValue(), string2)));
                        continue block30;
                    }
                    case 7: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new SelectedOption(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 8: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 9: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block30;
                    }
                    case 10: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsDouble(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block30;
                    }
                    case 11: {
                        this.responseData.put(string2, WeightUnit.fromGraphQl(this.jsonAsString(entry.getValue(), string2)));
                        continue block30;
                    }
                    case 12: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public Boolean getAvailableForSale() {
            return (Boolean)this.get("availableForSale");
        }

        public BigDecimal getCompareAtPrice() {
            return (BigDecimal)this.get("compareAtPrice");
        }

        public ID getId() {
            return (ID)this.get("id");
        }

        public BigDecimal getPrice() {
            return (BigDecimal)this.get("price");
        }

        public List<SelectedOption> getSelectedOptions() {
            return (List)this.get("selectedOptions");
        }

        public String getSku() {
            return (String)this.get("sku");
        }

        public String getTitle() {
            return (String)this.get("title");
        }
    }

    public static class ProductVariantConnection
    extends AbstractResponse<ProductVariantConnection> {
        public ProductVariantConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ProductVariantConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new ProductVariantEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public List<ProductVariantEdge> getEdges() {
            return (List)this.get("edges");
        }
    }

    public static class ProductVariantConnectionQuery
    extends Query<ProductVariantConnectionQuery> {
        ProductVariantConnectionQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public ProductVariantConnectionQuery edges(ProductVariantEdgeQueryDefinition productVariantEdgeQueryDefinition) {
            this.startField("edges");
            this._queryBuilder.append('{');
            productVariantEdgeQueryDefinition.define(new ProductVariantEdgeQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface ProductVariantConnectionQueryDefinition {
        public void define(ProductVariantConnectionQuery var1);
    }

    public static class ProductVariantEdge
    extends AbstractResponse<ProductVariantEdge> {
        public ProductVariantEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ProductVariantEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new ProductVariant(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public ProductVariant getNode() {
            return (ProductVariant)this.get("node");
        }
    }

    public static class ProductVariantEdgeQuery
    extends Query<ProductVariantEdgeQuery> {
        ProductVariantEdgeQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public ProductVariantEdgeQuery node(ProductVariantQueryDefinition productVariantQueryDefinition) {
            this.startField("node");
            this._queryBuilder.append('{');
            productVariantQueryDefinition.define(new ProductVariantQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }
    }

    public static interface ProductVariantEdgeQueryDefinition {
        public void define(ProductVariantEdgeQuery var1);
    }

    public static class ProductVariantQuery
    extends Query<ProductVariantQuery> {
        ProductVariantQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
            this.startField("id");
        }

        public ProductVariantQuery availableForSale() {
            this.startField("availableForSale");
            return this;
        }

        public ProductVariantQuery compareAtPrice() {
            this.startField("compareAtPrice");
            return this;
        }

        public ProductVariantQuery price() {
            this.startField("price");
            return this;
        }

        public ProductVariantQuery selectedOptions(SelectedOptionQueryDefinition selectedOptionQueryDefinition) {
            this.startField("selectedOptions");
            this._queryBuilder.append('{');
            selectedOptionQueryDefinition.define(new SelectedOptionQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public ProductVariantQuery sku() {
            this.startField("sku");
            return this;
        }

        public ProductVariantQuery title() {
            this.startField("title");
            return this;
        }
    }

    public static interface ProductVariantQueryDefinition {
        public void define(ProductVariantQuery var1);
    }

    public static class QueryRoot
    extends AbstractResponse<QueryRoot> {
        public QueryRoot() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public QueryRoot(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block14 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 606175198: {
                        if (!((String)object).equals("customer")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!((String)object).equals("node")) break;
                        n = 1;
                        break;
                    }
                    case 104993457: {
                        if (!((String)object).equals("nodes")) break;
                        n = 2;
                        break;
                    }
                    case 3529462: {
                        if (!((String)object).equals("shop")) break;
                        n = 3;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 4;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new Customer(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block14;
                    }
                    case 1: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = UnknownNode.create(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block14;
                    }
                    case 2: {
                        ArrayList<Object> arrayList = new ArrayList<Object>();
                        for (JsonElement jsonElement : this.jsonAsArray(entry.getValue(), string2)) {
                            object = null;
                            if (!jsonElement.isJsonNull()) {
                                object = UnknownNode.create(this.jsonAsObject(jsonElement, string2));
                            }
                            arrayList.add(object);
                        }
                        this.responseData.put(string2, arrayList);
                        continue block14;
                    }
                    case 3: {
                        this.responseData.put(string2, new Shop(this.jsonAsObject(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 4: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public Node getNode() {
            return (Node)this.get("node");
        }

        public Shop getShop() {
            return (Shop)this.get("shop");
        }
    }

    public static class QueryRootQuery
    extends Query<QueryRootQuery> {
        QueryRootQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public QueryRootQuery node(ID iD, NodeQueryDefinition nodeQueryDefinition) {
            this.startField("node");
            this._queryBuilder.append("(id:");
            Query.appendQuotedString(this._queryBuilder, iD.toString());
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            nodeQueryDefinition.define(new NodeQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public QueryRootQuery shop(ShopQueryDefinition shopQueryDefinition) {
            this.startField("shop");
            this._queryBuilder.append('{');
            shopQueryDefinition.define(new ShopQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public String toString() {
            return this._queryBuilder.toString();
        }
    }

    public static interface QueryRootQueryDefinition {
        public void define(QueryRootQuery var1);
    }

    public static class SelectedOption
    extends AbstractResponse<SelectedOption> {
        public SelectedOption() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public SelectedOption(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case 3373707: {
                        if (!string3.equals("name")) break;
                        n = 0;
                        break;
                    }
                    case 111972721: {
                        if (!string3.equals("value")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public String getName() {
            return (String)this.get("name");
        }

        public String getValue() {
            return (String)this.get("value");
        }
    }

    public static class SelectedOptionQuery
    extends Query<SelectedOptionQuery> {
        SelectedOptionQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public SelectedOptionQuery name() {
            this.startField("name");
            return this;
        }

        public SelectedOptionQuery value() {
            this.startField("value");
            return this;
        }
    }

    public static interface SelectedOptionQueryDefinition {
        public void define(SelectedOptionQuery var1);
    }

    public static class ShippingRate
    extends AbstractResponse<ShippingRate> {
        public ShippingRate() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ShippingRate(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block12 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1224577496: {
                        if (!string3.equals("handle")) break;
                        n = 0;
                        break;
                    }
                    case 106934601: {
                        if (!string3.equals("price")) break;
                        n = 1;
                        break;
                    }
                    case 110371416: {
                        if (!string3.equals("title")) break;
                        n = 2;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 3;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block12;
                    }
                    case 1: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block12;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block12;
                    }
                    case 3: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public String getHandle() {
            return (String)this.get("handle");
        }

        public BigDecimal getPrice() {
            return (BigDecimal)this.get("price");
        }

        public String getTitle() {
            return (String)this.get("title");
        }
    }

    public static class ShippingRateQuery
    extends Query<ShippingRateQuery> {
        ShippingRateQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public ShippingRateQuery handle() {
            this.startField("handle");
            return this;
        }

        public ShippingRateQuery price() {
            this.startField("price");
            return this;
        }

        public ShippingRateQuery title() {
            this.startField("title");
            return this;
        }
    }

    public static interface ShippingRateQueryDefinition {
        public void define(ShippingRateQuery var1);
    }

    public static class Shop
    extends AbstractResponse<Shop> {
        public Shop() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Shop(JsonObject object) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)object).entrySet().iterator();
            block42 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case -1228877251: {
                        if (!((String)object).equals("articles")) break;
                        n = 0;
                        break;
                    }
                    case 93832465: {
                        if (!((String)object).equals("blogs")) break;
                        n = 1;
                        break;
                    }
                    case -696589043: {
                        if (!((String)object).equals("cardVaultUrl")) break;
                        n = 2;
                        break;
                    }
                    case -500490723: {
                        if (!((String)object).equals("collectionByHandle")) break;
                        n = 3;
                        break;
                    }
                    case 1853891989: {
                        if (!((String)object).equals("collections")) break;
                        n = 4;
                        break;
                    }
                    case 1004773790: {
                        if (!((String)object).equals("currencyCode")) break;
                        n = 5;
                        break;
                    }
                    case -1724546052: {
                        if (!((String)object).equals("description")) break;
                        n = 6;
                        break;
                    }
                    case -2129824553: {
                        if (!((String)object).equals("moneyFormat")) break;
                        n = 7;
                        break;
                    }
                    case 3373707: {
                        if (!((String)object).equals("name")) break;
                        n = 8;
                        break;
                    }
                    case 633465929: {
                        if (!((String)object).equals("paymentSettings")) break;
                        n = 9;
                        break;
                    }
                    case -958413466: {
                        if (!((String)object).equals("primaryDomain")) break;
                        n = 10;
                        break;
                    }
                    case 1539108570: {
                        if (!((String)object).equals("privacyPolicy")) break;
                        n = 11;
                        break;
                    }
                    case 1311529838: {
                        if (!((String)object).equals("productByHandle")) break;
                        n = 12;
                        break;
                    }
                    case 1004558538: {
                        if (!((String)object).equals("productTypes")) break;
                        n = 13;
                        break;
                    }
                    case -1003761308: {
                        if (!((String)object).equals("products")) break;
                        n = 14;
                        break;
                    }
                    case 526579402: {
                        if (!((String)object).equals("refundPolicy")) break;
                        n = 15;
                        break;
                    }
                    case 78140373: {
                        if (!((String)object).equals("shopifyPaymentsAccountId")) break;
                        n = 16;
                        break;
                    }
                    case -196841: {
                        if (!((String)object).equals("termsOfService")) break;
                        n = 17;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 18;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new ArticleConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block42;
                    }
                    case 1: {
                        this.responseData.put(string2, new BlogConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block42;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block42;
                    }
                    case 3: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new Collection(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 4: {
                        this.responseData.put(string2, new CollectionConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block42;
                    }
                    case 5: {
                        this.responseData.put(string2, CurrencyCode.fromGraphQl(this.jsonAsString(entry.getValue(), string2)));
                        continue block42;
                    }
                    case 6: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 7: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block42;
                    }
                    case 8: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block42;
                    }
                    case 9: {
                        this.responseData.put(string2, new PaymentSettings(this.jsonAsObject(entry.getValue(), string2)));
                        continue block42;
                    }
                    case 10: {
                        this.responseData.put(string2, new Domain(this.jsonAsObject(entry.getValue(), string2)));
                        continue block42;
                    }
                    case 11: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new ShopPolicy(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 12: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new Product(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 13: {
                        this.responseData.put(string2, new StringConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block42;
                    }
                    case 14: {
                        this.responseData.put(string2, new ProductConnection(this.jsonAsObject(entry.getValue(), string2)));
                        continue block42;
                    }
                    case 15: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new ShopPolicy(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 16: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = this.jsonAsString(entry.getValue(), string2);
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 17: {
                        object = null;
                        if (!entry.getValue().isJsonNull()) {
                            object = new ShopPolicy(this.jsonAsObject(entry.getValue(), string2));
                        }
                        this.responseData.put(string2, object);
                        continue block42;
                    }
                    case 18: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public CollectionConnection getCollections() {
            return (CollectionConnection)this.get("collections");
        }

        public String getName() {
            return (String)this.get("name");
        }

        public PaymentSettings getPaymentSettings() {
            return (PaymentSettings)this.get("paymentSettings");
        }
    }

    public static class ShopPolicy
    extends AbstractResponse<ShopPolicy>
    implements Node {
        public ShopPolicy() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public ShopPolicy(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block14 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case 3029410: {
                        if (!string3.equals("body")) break;
                        n = 0;
                        break;
                    }
                    case 3355: {
                        if (!string3.equals("id")) break;
                        n = 1;
                        break;
                    }
                    case 110371416: {
                        if (!string3.equals("title")) break;
                        n = 2;
                        break;
                    }
                    case 116079: {
                        if (!string3.equals("url")) break;
                        n = 3;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 4;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block14;
                    }
                    case 1: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 2: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block14;
                    }
                    case 3: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block14;
                    }
                    case 4: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class ShopQuery
    extends Query<ShopQuery> {
        ShopQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public ShopQuery collections(int n, CollectionsArgumentsDefinition collectionsArgumentsDefinition, CollectionConnectionQueryDefinition collectionConnectionQueryDefinition) {
            this.startField("collections");
            this._queryBuilder.append("(first:");
            this._queryBuilder.append(n);
            collectionsArgumentsDefinition.define(new CollectionsArguments(this._queryBuilder));
            this._queryBuilder.append(')');
            this._queryBuilder.append('{');
            collectionConnectionQueryDefinition.define(new CollectionConnectionQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public ShopQuery name() {
            this.startField("name");
            return this;
        }

        public ShopQuery paymentSettings(PaymentSettingsQueryDefinition paymentSettingsQueryDefinition) {
            this.startField("paymentSettings");
            this._queryBuilder.append('{');
            paymentSettingsQueryDefinition.define(new PaymentSettingsQuery(this._queryBuilder));
            this._queryBuilder.append('}');
            return this;
        }

        public class CollectionsArguments
        extends Arguments {
            CollectionsArguments(StringBuilder stringBuilder) {
                super(stringBuilder, false);
            }

            public CollectionsArguments after(String string2) {
                if (string2 != null) {
                    this.startArgument("after");
                    Query.appendQuotedString(ShopQuery.this._queryBuilder, string2.toString());
                }
                return this;
            }

            public CollectionsArguments sortKey(CollectionSortKeys collectionSortKeys) {
                if (collectionSortKeys != null) {
                    this.startArgument("sortKey");
                    ShopQuery.this._queryBuilder.append(collectionSortKeys.toString());
                }
                return this;
            }
        }

        public static interface CollectionsArgumentsDefinition {
            public void define(CollectionsArguments var1);
        }

    }

    public static interface ShopQueryDefinition {
        public void define(ShopQuery var1);
    }

    public static class StringConnection
    extends AbstractResponse<StringConnection> {
        public StringConnection() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public StringConnection(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                Object object = this.getFieldName(string2);
                int n = -1;
                switch (((String)object).hashCode()) {
                    case 96356950: {
                        if (!((String)object).equals("edges")) break;
                        n = 0;
                        break;
                    }
                    case 859134941: {
                        if (!((String)object).equals("pageInfo")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)object).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        object = new ArrayList();
                        Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                        while (iterator2.hasNext()) {
                            object.add(new StringEdge(this.jsonAsObject(iterator2.next(), string2)));
                        }
                        this.responseData.put(string2, object);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, new PageInfo(this.jsonAsObject(entry.getValue(), string2)));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class StringEdge
    extends AbstractResponse<StringEdge> {
        public StringEdge() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public StringEdge(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1349119146: {
                        if (!string3.equals("cursor")) break;
                        n = 0;
                        break;
                    }
                    case 3386882: {
                        if (!string3.equals("node")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static class TokenizedPaymentInput
    implements Serializable {
        private BigDecimal amount;
        private MailingAddressInput billingAddress;
        private String idempotencyKey;
        private String identifier;
        private String paymentData;
        private Boolean test;
        private String type;

        public TokenizedPaymentInput(BigDecimal bigDecimal, String string2, MailingAddressInput mailingAddressInput, String string3, String string4) {
            this.amount = bigDecimal;
            this.idempotencyKey = string2;
            this.billingAddress = mailingAddressInput;
            this.type = string3;
            this.paymentData = string4;
        }

        public void appendTo(StringBuilder stringBuilder) {
            stringBuilder.append('{');
            stringBuilder.append("");
            stringBuilder.append("amount:");
            Query.appendQuotedString(stringBuilder, this.amount.toString());
            stringBuilder.append(",");
            stringBuilder.append("idempotencyKey:");
            Query.appendQuotedString(stringBuilder, this.idempotencyKey.toString());
            stringBuilder.append(",");
            stringBuilder.append("billingAddress:");
            this.billingAddress.appendTo(stringBuilder);
            stringBuilder.append(",");
            stringBuilder.append("type:");
            Query.appendQuotedString(stringBuilder, this.type.toString());
            stringBuilder.append(",");
            stringBuilder.append("paymentData:");
            Query.appendQuotedString(stringBuilder, this.paymentData.toString());
            if (this.test != null) {
                stringBuilder.append(",");
                stringBuilder.append("test:");
                stringBuilder.append(this.test);
            }
            if (this.identifier != null) {
                stringBuilder.append(",");
                stringBuilder.append("identifier:");
                Query.appendQuotedString(stringBuilder, this.identifier.toString());
            }
            stringBuilder.append('}');
        }

        public TokenizedPaymentInput setIdentifier(String string2) {
            this.identifier = string2;
            return this;
        }
    }

    public static class Transaction
    extends AbstractResponse<Transaction> {
        public Transaction() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public Transaction(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block14 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case -1413853096: {
                        if (!string3.equals("amount")) break;
                        n = 0;
                        break;
                    }
                    case 3292052: {
                        if (!string3.equals("kind")) break;
                        n = 1;
                        break;
                    }
                    case -892481550: {
                        if (!string3.equals("status")) break;
                        n = 2;
                        break;
                    }
                    case 3556498: {
                        if (!string3.equals("test")) break;
                        n = 3;
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 4;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new BigDecimal(this.jsonAsString(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 1: {
                        this.responseData.put(string2, TransactionKind.fromGraphQl(this.jsonAsString(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 2: {
                        this.responseData.put(string2, TransactionStatus.fromGraphQl(this.jsonAsString(entry.getValue(), string2)));
                        continue block14;
                    }
                    case 3: {
                        this.responseData.put(string2, this.jsonAsBoolean(entry.getValue(), string2));
                        continue block14;
                    }
                    case 4: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }
    }

    public static enum TransactionKind {
        AUTHORIZATION,
        CAPTURE,
        CHANGE,
        EMV_AUTHORIZATION,
        SALE,
        UNKNOWN_VALUE;


        /*
         * Enabled aggressive block sorting
         */
        public static TransactionKind fromGraphQl(String string2) {
            if (string2 == null) {
                return null;
            }
            int n = -1;
            switch (string2.hashCode()) {
                case 1730672729: {
                    if (!string2.equals("AUTHORIZATION")) break;
                    n = 0;
                    break;
                }
                case 1270567718: {
                    if (!string2.equals("CAPTURE")) break;
                    n = 1;
                    break;
                }
                case 1986660272: {
                    if (!string2.equals("CHANGE")) break;
                    n = 2;
                    break;
                }
                case -19657080: {
                    if (!string2.equals("EMV_AUTHORIZATION")) break;
                    n = 3;
                    break;
                }
                case 2537543: {
                    if (!string2.equals("SALE")) break;
                    n = 4;
                    break;
                }
            }
            switch (n) {
                default: {
                    return UNKNOWN_VALUE;
                }
                case 0: {
                    return AUTHORIZATION;
                }
                case 1: {
                    return CAPTURE;
                }
                case 2: {
                    return CHANGE;
                }
                case 3: {
                    return EMV_AUTHORIZATION;
                }
                case 4: 
            }
            return SALE;
        }

        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case AUTHORIZATION: {
                    return "AUTHORIZATION";
                }
                case CAPTURE: {
                    return "CAPTURE";
                }
                case CHANGE: {
                    return "CHANGE";
                }
                case EMV_AUTHORIZATION: {
                    return "EMV_AUTHORIZATION";
                }
                case SALE: 
            }
            return "SALE";
        }
    }

    public static enum TransactionStatus {
        ERROR,
        FAILURE,
        PENDING,
        SUCCESS,
        UNKNOWN_VALUE;


        /*
         * Enabled aggressive block sorting
         */
        public static TransactionStatus fromGraphQl(String string2) {
            if (string2 == null) {
                return null;
            }
            int n = -1;
            switch (string2.hashCode()) {
                case 66247144: {
                    if (!string2.equals("ERROR")) break;
                    n = 0;
                    break;
                }
                case -368591510: {
                    if (!string2.equals("FAILURE")) break;
                    n = 1;
                    break;
                }
                case 35394935: {
                    if (!string2.equals("PENDING")) break;
                    n = 2;
                    break;
                }
                case -1149187101: {
                    if (!string2.equals("SUCCESS")) break;
                    n = 3;
                    break;
                }
            }
            switch (n) {
                default: {
                    return UNKNOWN_VALUE;
                }
                case 0: {
                    return ERROR;
                }
                case 1: {
                    return FAILURE;
                }
                case 2: {
                    return PENDING;
                }
                case 3: 
            }
            return SUCCESS;
        }

        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case ERROR: {
                    return "ERROR";
                }
                case FAILURE: {
                    return "FAILURE";
                }
                case PENDING: {
                    return "PENDING";
                }
                case SUCCESS: 
            }
            return "SUCCESS";
        }
    }

    public static class UnknownNode
    extends AbstractResponse<UnknownNode>
    implements Node {
        public UnknownNode() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public UnknownNode(JsonObject iterator) throws SchemaViolationError {
            iterator = ((JsonObject)((Object)iterator)).entrySet().iterator();
            block8 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                String string3 = this.getFieldName(string2);
                int n = -1;
                switch (string3.hashCode()) {
                    case 3355: {
                        if (string3.equals("id")) {
                            n = 0;
                        }
                    }
                    default: {
                        break;
                    }
                    case -2073950043: {
                        if (!string3.equals("__typename")) break;
                        n = 1;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        this.responseData.put(string2, new ID(this.jsonAsString(entry.getValue(), string2)));
                        continue block8;
                    }
                    case 1: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        /*
         * Enabled aggressive block sorting
         */
        public static Node create(JsonObject jsonObject) throws SchemaViolationError {
            String string2 = jsonObject.getAsJsonPrimitive("__typename").getAsString();
            int n = -1;
            switch (string2.hashCode()) {
                case -839401507: {
                    if (!string2.equals("AppliedGiftCard")) break;
                    n = 0;
                    break;
                }
                case 932275414: {
                    if (!string2.equals("Article")) break;
                    n = 1;
                    break;
                }
                case 2073538: {
                    if (!string2.equals("Blog")) break;
                    n = 2;
                    break;
                }
                case 1601548646: {
                    if (!string2.equals("Checkout")) break;
                    n = 3;
                    break;
                }
                case -920136531: {
                    if (!string2.equals("CheckoutLineItem")) break;
                    n = 4;
                    break;
                }
                case 252152510: {
                    if (!string2.equals("Collection")) break;
                    n = 5;
                    break;
                }
                case -1679915457: {
                    if (!string2.equals("Comment")) break;
                    n = 6;
                    break;
                }
                case 430158537: {
                    if (!string2.equals("MailingAddress")) break;
                    n = 7;
                    break;
                }
                case 76453678: {
                    if (!string2.equals("Order")) break;
                    n = 8;
                    break;
                }
                case 877971942: {
                    if (!string2.equals("Payment")) break;
                    n = 9;
                    break;
                }
                case 1355179215: {
                    if (!string2.equals("Product")) break;
                    n = 10;
                    break;
                }
                case 1724170788: {
                    if (!string2.equals("ProductOption")) break;
                    n = 11;
                    break;
                }
                case -899019594: {
                    if (!string2.equals("ProductVariant")) break;
                    n = 12;
                    break;
                }
                case 768325608: {
                    if (!string2.equals("ShopPolicy")) break;
                    n = 13;
                    break;
                }
            }
            switch (n) {
                default: {
                    return new UnknownNode(jsonObject);
                }
                case 0: {
                    return new AppliedGiftCard(jsonObject);
                }
                case 1: {
                    return new Article(jsonObject);
                }
                case 2: {
                    return new Blog(jsonObject);
                }
                case 3: {
                    return new Checkout(jsonObject);
                }
                case 4: {
                    return new CheckoutLineItem(jsonObject);
                }
                case 5: {
                    return new Collection(jsonObject);
                }
                case 6: {
                    return new Comment(jsonObject);
                }
                case 7: {
                    return new MailingAddress(jsonObject);
                }
                case 8: {
                    return new Order(jsonObject);
                }
                case 9: {
                    return new Payment(jsonObject);
                }
                case 10: {
                    return new Product(jsonObject);
                }
                case 11: {
                    return new ProductOption(jsonObject);
                }
                case 12: {
                    return new ProductVariant(jsonObject);
                }
                case 13: 
            }
            return new ShopPolicy(jsonObject);
        }
    }

    public static class UserError
    extends AbstractResponse<UserError> {
        public UserError() {
        }

        /*
         * Enabled aggressive block sorting
         */
        public UserError(JsonObject arrayList) throws SchemaViolationError {
            Iterator<Map.Entry<String, JsonElement>> iterator = ((JsonObject)((Object)arrayList)).entrySet().iterator();
            block10 : while (iterator.hasNext()) {
                Map.Entry<String, JsonElement> entry = iterator.next();
                String string2 = entry.getKey();
                arrayList = this.getFieldName(string2);
                int n = -1;
                switch (((String)((Object)arrayList)).hashCode()) {
                    case 97427706: {
                        if (!((String)((Object)arrayList)).equals("field")) break;
                        n = 0;
                        break;
                    }
                    case 954925063: {
                        if (!((String)((Object)arrayList)).equals("message")) break;
                        n = 1;
                        break;
                    }
                    case -2073950043: {
                        if (!((String)((Object)arrayList)).equals("__typename")) break;
                        n = 2;
                        break;
                    }
                }
                switch (n) {
                    default: {
                        throw new SchemaViolationError(this, string2, entry.getValue());
                    }
                    case 0: {
                        arrayList = null;
                        if (!entry.getValue().isJsonNull()) {
                            arrayList = new ArrayList<String>();
                            Iterator<JsonElement> iterator2 = this.jsonAsArray(entry.getValue(), string2).iterator();
                            while (iterator2.hasNext()) {
                                arrayList.add(this.jsonAsString(iterator2.next(), string2));
                            }
                        }
                        this.responseData.put(string2, arrayList);
                        continue block10;
                    }
                    case 1: {
                        this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
                        continue block10;
                    }
                    case 2: 
                }
                this.responseData.put(string2, this.jsonAsString(entry.getValue(), string2));
            }
            return;
        }

        public String getMessage() {
            return (String)this.get("message");
        }
    }

    public static class UserErrorQuery
    extends Query<UserErrorQuery> {
        UserErrorQuery(StringBuilder stringBuilder) {
            super(stringBuilder);
        }

        public UserErrorQuery field() {
            this.startField("field");
            return this;
        }

        public UserErrorQuery message() {
            this.startField("message");
            return this;
        }
    }

    public static interface UserErrorQueryDefinition {
        public void define(UserErrorQuery var1);
    }

    public static enum WeightUnit {
        GRAMS,
        KILOGRAMS,
        OUNCES,
        POUNDS,
        UNKNOWN_VALUE;


        /*
         * Enabled aggressive block sorting
         */
        public static WeightUnit fromGraphQl(String string2) {
            if (string2 == null) {
                return null;
            }
            int n = -1;
            switch (string2.hashCode()) {
                case 68077788: {
                    if (!string2.equals("GRAMS")) break;
                    n = 0;
                    break;
                }
                case 1316588059: {
                    if (!string2.equals("KILOGRAMS")) break;
                    n = 1;
                    break;
                }
                case -1952374775: {
                    if (!string2.equals("OUNCES")) break;
                    n = 2;
                    break;
                }
                case -1929067673: {
                    if (!string2.equals("POUNDS")) break;
                    n = 3;
                    break;
                }
            }
            switch (n) {
                default: {
                    return UNKNOWN_VALUE;
                }
                case 0: {
                    return GRAMS;
                }
                case 1: {
                    return KILOGRAMS;
                }
                case 2: {
                    return OUNCES;
                }
                case 3: 
            }
            return POUNDS;
        }

        public String toString() {
            switch (this) {
                default: {
                    return "";
                }
                case GRAMS: {
                    return "GRAMS";
                }
                case KILOGRAMS: {
                    return "KILOGRAMS";
                }
                case OUNCES: {
                    return "OUNCES";
                }
                case POUNDS: 
            }
            return "POUNDS";
        }
    }

}

