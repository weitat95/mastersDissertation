/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  android.os.Build
 *  android.os.Build$VERSION
 */
package com.google.android.gms.internal;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Build;
import com.google.android.gms.internal.zzcgo;
import com.google.android.gms.internal.zzchm;
import com.google.android.gms.internal.zzcho;
import java.io.File;

final class zzcgr
extends SQLiteOpenHelper {
    private /* synthetic */ zzcgo zzizc;

    zzcgr(zzcgo zzcgo2, Context context, String string2) {
        this.zzizc = zzcgo2;
        super(context, string2, null, 1);
    }

    public final SQLiteDatabase getWritableDatabase() {
        if (!zzcgo.zza(this.zzizc).zzu(3600000L)) {
            throw new SQLiteException("Database open failed");
        }
        try {
            SQLiteDatabase sQLiteDatabase = super.getWritableDatabase();
            return sQLiteDatabase;
        }
        catch (SQLiteException sQLiteException) {
            zzcgo.zza(this.zzizc).start();
            this.zzizc.zzawy().zzazd().log("Opening the database failed, dropping and recreating it");
            if (!this.zzizc.getContext().getDatabasePath("google_app_measurement.db").delete()) {
                this.zzizc.zzawy().zzazd().zzj("Failed to delete corrupted db file", "google_app_measurement.db");
            }
            try {
                SQLiteDatabase sQLiteDatabase = super.getWritableDatabase();
                zzcgo.zza(this.zzizc).clear();
                return sQLiteDatabase;
            }
            catch (SQLiteException sQLiteException2) {
                this.zzizc.zzawy().zzazd().zzj("Failed to open freshly created database", (Object)sQLiteException2);
                throw sQLiteException2;
            }
        }
    }

    public final void onCreate(SQLiteDatabase sQLiteDatabase) {
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase);
    }

    public final void onDowngrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }

    public final void onOpen(SQLiteDatabase sQLiteDatabase) {
        Cursor cursor;
        if (Build.VERSION.SDK_INT < 15) {
            cursor = sQLiteDatabase.rawQuery("PRAGMA journal_mode=memory", null);
            cursor.moveToFirst();
        }
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "events", "CREATE TABLE IF NOT EXISTS events ( app_id TEXT NOT NULL, name TEXT NOT NULL, lifetime_count INTEGER NOT NULL, current_bundle_count INTEGER NOT NULL, last_fire_timestamp INTEGER NOT NULL, PRIMARY KEY (app_id, name)) ;", "app_id,name,lifetime_count,current_bundle_count,last_fire_timestamp", zzcgo.zzayo());
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "conditional_properties", "CREATE TABLE IF NOT EXISTS conditional_properties ( app_id TEXT NOT NULL, origin TEXT NOT NULL, name TEXT NOT NULL, value BLOB NOT NULL, creation_timestamp INTEGER NOT NULL, active INTEGER NOT NULL, trigger_event_name TEXT, trigger_timeout INTEGER NOT NULL, timed_out_event BLOB,triggered_event BLOB, triggered_timestamp INTEGER NOT NULL, time_to_live INTEGER NOT NULL, expired_event BLOB, PRIMARY KEY (app_id, name)) ;", "app_id,origin,name,value,active,trigger_event_name,trigger_timeout,creation_timestamp,timed_out_event,triggered_event,triggered_timestamp,time_to_live,expired_event", null);
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "user_attributes", "CREATE TABLE IF NOT EXISTS user_attributes ( app_id TEXT NOT NULL, name TEXT NOT NULL, set_timestamp INTEGER NOT NULL, value BLOB NOT NULL, PRIMARY KEY (app_id, name)) ;", "app_id,name,set_timestamp,value", zzcgo.zzayp());
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "apps", "CREATE TABLE IF NOT EXISTS apps ( app_id TEXT NOT NULL, app_instance_id TEXT, gmp_app_id TEXT, resettable_device_id_hash TEXT, last_bundle_index INTEGER NOT NULL, last_bundle_end_timestamp INTEGER NOT NULL, PRIMARY KEY (app_id)) ;", "app_id,app_instance_id,gmp_app_id,resettable_device_id_hash,last_bundle_index,last_bundle_end_timestamp", zzcgo.zzayq());
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "queue", "CREATE TABLE IF NOT EXISTS queue ( app_id TEXT NOT NULL, bundle_end_timestamp INTEGER NOT NULL, data BLOB NOT NULL);", "app_id,bundle_end_timestamp,data", zzcgo.zzayr());
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "raw_events_metadata", "CREATE TABLE IF NOT EXISTS raw_events_metadata ( app_id TEXT NOT NULL, metadata_fingerprint INTEGER NOT NULL, metadata BLOB NOT NULL, PRIMARY KEY (app_id, metadata_fingerprint));", "app_id,metadata_fingerprint,metadata", null);
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "raw_events", "CREATE TABLE IF NOT EXISTS raw_events ( app_id TEXT NOT NULL, name TEXT NOT NULL, timestamp INTEGER NOT NULL, metadata_fingerprint INTEGER NOT NULL, data BLOB NOT NULL);", "app_id,name,timestamp,metadata_fingerprint,data", zzcgo.zzays());
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "event_filters", "CREATE TABLE IF NOT EXISTS event_filters ( app_id TEXT NOT NULL, audience_id INTEGER NOT NULL, filter_id INTEGER NOT NULL, event_name TEXT NOT NULL, data BLOB NOT NULL, PRIMARY KEY (app_id, event_name, audience_id, filter_id));", "app_id,audience_id,filter_id,event_name,data", null);
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "property_filters", "CREATE TABLE IF NOT EXISTS property_filters ( app_id TEXT NOT NULL, audience_id INTEGER NOT NULL, filter_id INTEGER NOT NULL, property_name TEXT NOT NULL, data BLOB NOT NULL, PRIMARY KEY (app_id, property_name, audience_id, filter_id));", "app_id,audience_id,filter_id,property_name,data", null);
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "audience_filter_values", "CREATE TABLE IF NOT EXISTS audience_filter_values ( app_id TEXT NOT NULL, audience_id INTEGER NOT NULL, current_results BLOB, PRIMARY KEY (app_id, audience_id));", "app_id,audience_id,current_results", null);
        zzcgo.zza(this.zzizc.zzawy(), sQLiteDatabase, "app2", "CREATE TABLE IF NOT EXISTS app2 ( app_id TEXT NOT NULL, first_open_count INTEGER NOT NULL, PRIMARY KEY (app_id));", "app_id,first_open_count", zzcgo.zzayt());
        return;
        finally {
            cursor.close();
        }
    }

    public final void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
    }
}

