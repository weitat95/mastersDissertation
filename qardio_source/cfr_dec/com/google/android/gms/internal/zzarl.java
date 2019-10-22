/*
 * Decompiled with CFR 0.147.
 */
package com.google.android.gms.internal;

import com.google.android.gms.internal.zzaqt;
import com.google.android.gms.internal.zzaqz;
import com.google.android.gms.internal.zzarm;

public final class zzarl {
    private static zzarm<Boolean> zzdvw = zzarm.zza("analytics.service_enabled", false, false);
    public static zzarm<Boolean> zzdvx = zzarm.zza("analytics.service_client_enabled", true, true);
    public static zzarm<String> zzdvy = zzarm.zzc("analytics.log_tag", "GAv4", "GAv4-SVC");
    private static zzarm<Long> zzdvz = zzarm.zza("analytics.max_tokens", 60L, 60L);
    private static zzarm<Float> zzdwa = zzarm.zza("analytics.tokens_per_sec", 0.5f, 0.5f);
    public static zzarm<Integer> zzdwb = zzarm.zza("analytics.max_stored_hits", 2000, 20000);
    private static zzarm<Integer> zzdwc = zzarm.zza("analytics.max_stored_hits_per_app", 2000, 2000);
    public static zzarm<Integer> zzdwd = zzarm.zza("analytics.max_stored_properties_per_app", 100, 100);
    public static zzarm<Long> zzdwe = zzarm.zza("analytics.local_dispatch_millis", 1800000L, 120000L);
    public static zzarm<Long> zzdwf = zzarm.zza("analytics.initial_local_dispatch_millis", 5000L, 5000L);
    private static zzarm<Long> zzdwg = zzarm.zza("analytics.min_local_dispatch_millis", 120000L, 120000L);
    private static zzarm<Long> zzdwh = zzarm.zza("analytics.max_local_dispatch_millis", 7200000L, 7200000L);
    public static zzarm<Long> zzdwi = zzarm.zza("analytics.dispatch_alarm_millis", 7200000L, 7200000L);
    public static zzarm<Long> zzdwj = zzarm.zza("analytics.max_dispatch_alarm_millis", 32400000L, 32400000L);
    public static zzarm<Integer> zzdwk = zzarm.zza("analytics.max_hits_per_dispatch", 20, 20);
    public static zzarm<Integer> zzdwl = zzarm.zza("analytics.max_hits_per_batch", 20, 20);
    public static zzarm<String> zzdwm = zzarm.zzc("analytics.insecure_host", "http://www.google-analytics.com", "http://www.google-analytics.com");
    public static zzarm<String> zzdwn = zzarm.zzc("analytics.secure_host", "https://ssl.google-analytics.com", "https://ssl.google-analytics.com");
    public static zzarm<String> zzdwo = zzarm.zzc("analytics.simple_endpoint", "/collect", "/collect");
    public static zzarm<String> zzdwp = zzarm.zzc("analytics.batching_endpoint", "/batch", "/batch");
    public static zzarm<Integer> zzdwq = zzarm.zza("analytics.max_get_length", 2036, 2036);
    public static zzarm<String> zzdwr = zzarm.zzc("analytics.batching_strategy.k", zzaqt.zzdvb.name(), zzaqt.zzdvb.name());
    public static zzarm<String> zzdws;
    private static zzarm<Integer> zzdwt;
    public static zzarm<Integer> zzdwu;
    public static zzarm<Integer> zzdwv;
    public static zzarm<Integer> zzdww;
    public static zzarm<String> zzdwx;
    public static zzarm<Integer> zzdwy;
    private static zzarm<Long> zzdwz;
    public static zzarm<Integer> zzdxa;
    public static zzarm<Integer> zzdxb;
    public static zzarm<Long> zzdxc;
    private static zzarm<String> zzdxd;
    private static zzarm<Integer> zzdxe;
    public static zzarm<Boolean> zzdxf;
    public static zzarm<Long> zzdxg;
    public static zzarm<Long> zzdxh;
    private static zzarm<Long> zzdxi;
    private static zzarm<Long> zzdxj;
    public static zzarm<Long> zzdxk;
    public static zzarm<Long> zzdxl;
    public static zzarm<Long> zzdxm;

    static {
        String string2 = zzaqz.zzdvi.name();
        zzdws = zzarm.zzc("analytics.compression_strategy.k", string2, string2);
        zzdwt = zzarm.zza("analytics.max_hits_per_request.k", 20, 20);
        zzdwu = zzarm.zza("analytics.max_hit_length.k", 8192, 8192);
        zzdwv = zzarm.zza("analytics.max_post_length.k", 8192, 8192);
        zzdww = zzarm.zza("analytics.max_batch_post_length", 8192, 8192);
        zzdwx = zzarm.zzc("analytics.fallback_responses.k", "404,502", "404,502");
        zzdwy = zzarm.zza("analytics.batch_retry_interval.seconds.k", 3600, 3600);
        zzdwz = zzarm.zza("analytics.service_monitor_interval", 86400000L, 86400000L);
        zzdxa = zzarm.zza("analytics.http_connection.connect_timeout_millis", 60000, 60000);
        zzdxb = zzarm.zza("analytics.http_connection.read_timeout_millis", 61000, 61000);
        zzdxc = zzarm.zza("analytics.campaigns.time_limit", 86400000L, 86400000L);
        zzdxd = zzarm.zzc("analytics.first_party_experiment_id", "", "");
        zzdxe = zzarm.zza("analytics.first_party_experiment_variant", 0, 0);
        zzdxf = zzarm.zza("analytics.test.disable_receiver", false, false);
        zzdxg = zzarm.zza("analytics.service_client.idle_disconnect_millis", 10000L, 10000L);
        zzdxh = zzarm.zza("analytics.service_client.connect_timeout_millis", 5000L, 5000L);
        zzdxi = zzarm.zza("analytics.service_client.second_connect_delay_millis", 5000L, 5000L);
        zzdxj = zzarm.zza("analytics.service_client.unexpected_reconnect_millis", 60000L, 60000L);
        zzdxk = zzarm.zza("analytics.service_client.reconnect_throttle_millis", 1800000L, 1800000L);
        zzdxl = zzarm.zza("analytics.monitoring.sample_period_millis", 86400000L, 86400000L);
        zzdxm = zzarm.zza("analytics.initialization_warning_threshold", 5000L, 5000L);
    }
}

