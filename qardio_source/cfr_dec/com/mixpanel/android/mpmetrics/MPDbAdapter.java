/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentValues
 *  android.content.Context
 *  android.database.Cursor
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 *  org.json.JSONArray
 *  org.json.JSONException
 *  org.json.JSONObject
 */
package com.mixpanel.android.mpmetrics;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import com.mixpanel.android.mpmetrics.MPConfig;
import com.mixpanel.android.util.MPLog;
import java.io.File;
import java.util.HashMap;
import java.util.Map;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

class MPDbAdapter {
    private static final String CREATE_EVENTS_TABLE;
    private static final String CREATE_PEOPLE_TABLE;
    private static final String EVENTS_TIME_INDEX;
    private static final String PEOPLE_TIME_INDEX;
    private static final Map<Context, MPDbAdapter> sInstances;
    private final MPDatabaseHelper mDb;

    static {
        sInstances = new HashMap<Context, MPDbAdapter>();
        CREATE_EVENTS_TABLE = "CREATE TABLE " + Table.EVENTS.getName() + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "data" + " STRING NOT NULL, " + "created_at" + " INTEGER NOT NULL, " + "automatic_data" + " INTEGER DEFAULT 0, " + "token" + " STRING NOT NULL DEFAULT '')";
        CREATE_PEOPLE_TABLE = "CREATE TABLE " + Table.PEOPLE.getName() + " (_id INTEGER PRIMARY KEY AUTOINCREMENT, " + "data" + " STRING NOT NULL, " + "created_at" + " INTEGER NOT NULL, " + "automatic_data" + " INTEGER DEFAULT 0, " + "token" + " STRING NOT NULL DEFAULT '')";
        EVENTS_TIME_INDEX = "CREATE INDEX IF NOT EXISTS time_idx ON " + Table.EVENTS.getName() + " (" + "created_at" + ");";
        PEOPLE_TIME_INDEX = "CREATE INDEX IF NOT EXISTS time_idx ON " + Table.PEOPLE.getName() + " (" + "created_at" + ");";
    }

    public MPDbAdapter(Context context) {
        this(context, "mixpanel");
    }

    public MPDbAdapter(Context context, String string2) {
        this.mDb = new MPDatabaseHelper(context, string2);
    }

    private void cleanupAutomaticEvents(Table object, String string2) {
        object = object.getName();
        try {
            this.mDb.getWritableDatabase().delete((String)object, "automatic_data = 1 AND token = '" + string2 + "'", null);
            return;
        }
        catch (SQLiteException sQLiteException) {
            MPLog.e("MixpanelAPI.Database", "Could not clean automatic Mixpanel records from " + (String)object + ". Re-initializing database.", sQLiteException);
            this.mDb.deleteDatabase();
            return;
        }
        finally {
            this.mDb.close();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static MPDbAdapter getInstance(Context object) {
        Map<Context, MPDbAdapter> map = sInstances;
        synchronized (map) {
            Context context = object.getApplicationContext();
            if (sInstances.containsKey((Object)context)) return sInstances.get((Object)context);
            object = new MPDbAdapter(context);
            sInstances.put(context, (MPDbAdapter)object);
            return object;
        }
    }

    public int addJSON(JSONObject jSONObject, String string2, Table table, boolean bl) {
        int n;
        block17: {
            JSONObject jSONObject2;
            if (!this.belowMemThreshold()) {
                MPLog.e("MixpanelAPI.Database", "There is not enough space left on the device to store Mixpanel data, so data was discarded");
                return -2;
            }
            String string3 = table.getName();
            Object var8_8 = null;
            JSONObject jSONObject3 = jSONObject2 = null;
            table = var8_8;
            SQLiteDatabase sQLiteDatabase = this.mDb.getWritableDatabase();
            jSONObject3 = jSONObject2;
            table = var8_8;
            ContentValues contentValues = new ContentValues();
            jSONObject3 = jSONObject2;
            table = var8_8;
            contentValues.put("data", jSONObject.toString());
            jSONObject3 = jSONObject2;
            table = var8_8;
            contentValues.put("created_at", Long.valueOf(System.currentTimeMillis()));
            jSONObject3 = jSONObject2;
            table = var8_8;
            contentValues.put("automatic_data", Boolean.valueOf(bl));
            jSONObject3 = jSONObject2;
            table = var8_8;
            contentValues.put("token", string2);
            jSONObject3 = jSONObject2;
            table = var8_8;
            sQLiteDatabase.insert(string3, null, contentValues);
            jSONObject3 = jSONObject2;
            table = var8_8;
            jSONObject3 = jSONObject = sQLiteDatabase.rawQuery("SELECT COUNT(*) FROM " + string3 + " WHERE token='" + string2 + "'", null);
            table = jSONObject;
            jSONObject.moveToFirst();
            jSONObject3 = jSONObject;
            table = jSONObject;
            try {
                n = jSONObject.getInt(0);
                if (jSONObject == null) break block17;
            }
            catch (SQLiteException sQLiteException) {
                block19: {
                    block18: {
                        table = jSONObject3;
                        try {
                            MPLog.e("MixpanelAPI.Database", "Could not add Mixpanel data to table " + string3 + ". Re-initializing database.", sQLiteException);
                            jSONObject = jSONObject3;
                            if (jSONObject3 == null) break block18;
                            table = jSONObject3;
                        }
                        catch (Throwable throwable) {
                            if (table != null) {
                                table.close();
                            }
                            this.mDb.close();
                            throw throwable;
                        }
                        jSONObject3.close();
                        jSONObject = null;
                    }
                    table = jSONObject;
                    this.mDb.deleteDatabase();
                    if (jSONObject == null) break block19;
                    jSONObject.close();
                }
                this.mDb.close();
                return -1;
            }
            jSONObject.close();
        }
        this.mDb.close();
        return n;
    }

    protected boolean belowMemThreshold() {
        return this.mDb.belowMemThreshold();
    }

    public void cleanupAutomaticEvents(String string2) {
        synchronized (this) {
            this.cleanupAutomaticEvents(Table.EVENTS, string2);
            this.cleanupAutomaticEvents(Table.PEOPLE, string2);
            return;
        }
    }

    public void cleanupEvents(long l, Table object) {
        object = object.getName();
        try {
            this.mDb.getWritableDatabase().delete((String)object, "created_at <= " + l, null);
            return;
        }
        catch (SQLiteException sQLiteException) {
            MPLog.e("MixpanelAPI.Database", "Could not clean timed-out Mixpanel records from " + (String)object + ". Re-initializing database.", sQLiteException);
            this.mDb.deleteDatabase();
            return;
        }
        finally {
            this.mDb.close();
        }
    }

    public void cleanupEvents(String charSequence, Table object, String string2, boolean bl) {
        object = object.getName();
        try {
            SQLiteDatabase sQLiteDatabase = this.mDb.getWritableDatabase();
            charSequence = new StringBuffer("_id <= " + (String)charSequence + " AND " + "token" + " = '" + string2 + "'");
            if (!bl) {
                ((StringBuffer)charSequence).append(" AND automatic_data=0");
            }
            sQLiteDatabase.delete((String)object, ((StringBuffer)charSequence).toString(), null);
            return;
        }
        catch (SQLiteException sQLiteException) {
            MPLog.e("MixpanelAPI.Database", "Could not clean sent Mixpanel records from " + (String)object + ". Re-initializing database.", sQLiteException);
            this.mDb.deleteDatabase();
            return;
        }
        finally {
            this.mDb.close();
        }
    }

    public void deleteDB() {
        this.mDb.deleteDatabase();
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public String[] generateDataString(Table var1_1, String var2_4, boolean var3_7) {
        block14: {
            var14_27 = null;
            var9_28 = null;
            var13_29 = null;
            var10_30 = null;
            var8_31 = null;
            var12_35 = null;
            var11_36 = null;
            var15_37 = var1_1.getName();
            var16_38 = this.mDb.getReadableDatabase();
            var5_39 = var9_28;
            var1_2 = var11_36;
            var4_40 = var10_30;
            var6_41 = var14_27;
            var7_68 = var13_29;
            try {
                var17_95 = new StringBuffer("SELECT * FROM " + var15_37 + " WHERE " + "token" + " = '" + (String)var2_23 /* !! */  + "' ");
                var5_39 = var9_28;
                var1_3 = var11_36;
                var4_40 = var10_30;
                var6_42 = var14_27;
                var7_69 = var13_29;
                var2_23 /* !! */  = new StringBuffer("SELECT COUNT(*) FROM " + var15_37 + " WHERE " + "token" + " = '" + (String)var2_23 /* !! */  + "' ");
                if (var3_26 == false) {
                    var5_39 = var9_28;
                    var1_4 = var11_36;
                    var4_40 = var10_30;
                    var6_43 = var14_27;
                    var7_70 = var13_29;
                    var17_95.append("AND automatic_data = 0 ");
                    var5_39 = var9_28;
                    var1_5 = var11_36;
                    var4_40 = var10_30;
                    var6_44 = var14_27;
                    var7_71 = var13_29;
                    var2_23 /* !! */ .append(" AND automatic_data = 0");
                }
                var5_39 = var9_28;
                var1_7 = var11_36;
                var4_40 = var10_30;
                var6_46 = var14_27;
                var7_73 = var13_29;
                var17_95.append("ORDER BY created_at ASC LIMIT 50");
                var5_39 = var9_28;
                var1_8 = var11_36;
                var4_40 = var10_30;
                var6_47 = var14_27;
                var7_74 = var13_29;
                var5_39 = var9_28 = var16_38.rawQuery(var17_95.toString(), null);
                var1_9 = var11_36;
                var4_40 = var10_30;
                var6_48 = var9_28;
                var7_75 = var13_29;
                var10_30 = var16_38.rawQuery(var2_23 /* !! */ .toString(), null);
                var5_39 = var9_28;
                var1_10 = var11_36;
                var4_40 = var10_30;
                var6_49 = var9_28;
                var7_76 = var10_30;
                var10_30.moveToFirst();
                var5_39 = var9_28;
                var1_11 = var11_36;
                var4_40 = var10_30;
                var6_50 = var9_28;
                var7_77 = var10_30;
                var11_36 = String.valueOf(var10_30.getInt(0));
                var5_39 = var9_28;
                var1_12 = var11_36;
                var4_40 = var10_30;
                var6_51 = var9_28;
                var7_78 = var10_30;
                var13_29 = new JSONArray();
                var2_23 /* !! */  = var12_35;
                do {
                    var5_39 = var9_28;
                    var1_14 = var11_36;
                    var4_40 = var10_30;
                    var6_53 = var9_28;
                    var7_80 = var10_30;
                    if (!var9_28.moveToNext()) break;
                    var5_39 = var9_28;
                    var1_15 = var11_36;
                    var4_40 = var10_30;
                    var6_54 = var9_28;
                    var7_81 = var10_30;
                    if (var9_28.isLast()) {
                        var5_39 = var9_28;
                        var1_16 = var11_36;
                        var4_40 = var10_30;
                        var6_55 = var9_28;
                        var7_82 = var10_30;
                        var2_23 /* !! */  = var9_28.getString(var9_28.getColumnIndex("_id"));
                    }
                    var5_39 = var9_28;
                    var1_18 = var11_36;
                    var4_40 = var10_30;
                    var6_57 = var9_28;
                    var7_84 = var10_30;
                    try {
                        var13_29.put((Object)new JSONObject(var9_28.getString(var9_28.getColumnIndex("data"))));
                    }
                    catch (JSONException var1_19) {}
                } while (true);
                var5_39 = var9_28;
                var1_20 = var11_36;
                var4_40 = var10_30;
                var6_58 = var9_28;
                var7_85 = var10_30;
                if (var13_29.length() <= 0) break block14;
                var5_39 = var9_28;
                var1_21 = var11_36;
                var4_40 = var10_30;
                var6_59 = var9_28;
                var7_86 = var10_30;
                var8_32 = var13_29.toString();
            }
            catch (SQLiteException var2_24) {
                var6_64 = var5_39;
                var7_91 = var4_40;
                try {
                    MPLog.e("MixpanelAPI.Database", "Could not pull records for Mixpanel out of database " + var15_37 + ". Waiting to send.", var2_24);
                    var2_25 = null;
                    var8_34 = null;
                }
                catch (Throwable var1_22) {
                    this.mDb.close();
                    if (var6_67 != null) {
                        var6_67.close();
                    }
                    if (var7_94 == null) throw var1_22;
                    var7_94.close();
                    throw var1_22;
                }
                this.mDb.close();
                if (var5_39 != null) {
                    var5_39.close();
                }
                var5_39 = var8_34;
                var6_65 = var2_25;
                var7_92 = var1_2;
                if (var4_40 != null) {
                    var4_40.close();
                    var5_39 = var8_34;
                    var6_66 = var2_25;
                    var7_93 = var1_2;
                }
lbl156:
                // 4 sources
                if (var6_63 == null) return null;
                if (var5_39 == null) return null;
                return new String[]{var6_63, var5_39, var7_90};
            }
        }
        this.mDb.close();
        if (var9_28 != null) {
            var9_28.close();
        }
        var5_39 = var8_33;
        var6_61 = var2_23 /* !! */ ;
        var7_88 = var11_36;
        if (var10_30 != null) {
            var10_30.close();
            var7_89 = var11_36;
            var6_62 /* !! */  = var2_23 /* !! */ ;
            var5_39 = var8_33;
        }
        ** GOTO lbl156
    }

    public File getDatabaseFile() {
        return this.mDb.mDatabaseFile;
    }

    private static class MPDatabaseHelper
    extends SQLiteOpenHelper {
        private final MPConfig mConfig;
        private final File mDatabaseFile;

        MPDatabaseHelper(Context context, String string2) {
            super(context, string2, null, 5);
            this.mDatabaseFile = context.getDatabasePath(string2);
            this.mConfig = MPConfig.getInstance(context);
        }

        private void migrateTableFrom4To5(SQLiteDatabase sQLiteDatabase) {
            int n;
            int n2;
            String string2;
            sQLiteDatabase.execSQL("ALTER TABLE " + Table.EVENTS.getName() + " ADD COLUMN " + "automatic_data" + " INTEGER DEFAULT 0");
            sQLiteDatabase.execSQL("ALTER TABLE " + Table.PEOPLE.getName() + " ADD COLUMN " + "automatic_data" + " INTEGER DEFAULT 0");
            sQLiteDatabase.execSQL("ALTER TABLE " + Table.EVENTS.getName() + " ADD COLUMN " + "token" + " STRING NOT NULL DEFAULT ''");
            sQLiteDatabase.execSQL("ALTER TABLE " + Table.PEOPLE.getName() + " ADD COLUMN " + "token" + " STRING NOT NULL DEFAULT ''");
            Cursor cursor = sQLiteDatabase.rawQuery("SELECT * FROM " + Table.EVENTS.getName(), null);
            while (cursor.moveToNext()) {
                n2 = n = 0;
                string2 = new JSONObject(cursor.getString(cursor.getColumnIndex("data"))).getJSONObject("properties").getString("token");
                n2 = n;
                n2 = n = cursor.getInt(cursor.getColumnIndex("_id"));
                try {
                    sQLiteDatabase.execSQL("UPDATE " + Table.EVENTS.getName() + " SET " + "token" + " = '" + string2 + "' WHERE _id = " + n);
                }
                catch (JSONException jSONException) {
                    sQLiteDatabase.delete(Table.EVENTS.getName(), "_id = " + n2, null);
                }
            }
            cursor = sQLiteDatabase.rawQuery("SELECT * FROM " + Table.PEOPLE.getName(), null);
            while (cursor.moveToNext()) {
                n2 = n = 0;
                string2 = new JSONObject(cursor.getString(cursor.getColumnIndex("data"))).getString("$token");
                n2 = n;
                n2 = n = cursor.getInt(cursor.getColumnIndex("_id"));
                try {
                    sQLiteDatabase.execSQL("UPDATE " + Table.PEOPLE.getName() + " SET " + "token" + " = '" + string2 + "' WHERE _id = " + n);
                }
                catch (JSONException jSONException) {
                    sQLiteDatabase.delete(Table.PEOPLE.getName(), "_id = " + n2, null);
                }
            }
        }

        public boolean belowMemThreshold() {
            return !this.mDatabaseFile.exists() || Math.max(this.mDatabaseFile.getUsableSpace(), (long)this.mConfig.getMinimumDatabaseLimit()) >= this.mDatabaseFile.length();
        }

        public void deleteDatabase() {
            this.close();
            this.mDatabaseFile.delete();
        }

        public void onCreate(SQLiteDatabase sQLiteDatabase) {
            MPLog.v("MixpanelAPI.Database", "Creating a new Mixpanel events DB");
            sQLiteDatabase.execSQL(CREATE_EVENTS_TABLE);
            sQLiteDatabase.execSQL(CREATE_PEOPLE_TABLE);
            sQLiteDatabase.execSQL(EVENTS_TIME_INDEX);
            sQLiteDatabase.execSQL(PEOPLE_TIME_INDEX);
        }

        public void onUpgrade(SQLiteDatabase sQLiteDatabase, int n, int n2) {
            MPLog.v("MixpanelAPI.Database", "Upgrading app, replacing Mixpanel events DB");
            if (n2 == 5) {
                this.migrateTableFrom4To5(sQLiteDatabase);
                return;
            }
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.EVENTS.getName());
            sQLiteDatabase.execSQL("DROP TABLE IF EXISTS " + Table.PEOPLE.getName());
            sQLiteDatabase.execSQL(CREATE_EVENTS_TABLE);
            sQLiteDatabase.execSQL(CREATE_PEOPLE_TABLE);
            sQLiteDatabase.execSQL(EVENTS_TIME_INDEX);
            sQLiteDatabase.execSQL(PEOPLE_TIME_INDEX);
        }
    }

    public static enum Table {
        EVENTS("events"),
        PEOPLE("people");

        private final String mTableName;

        private Table(String string3) {
            this.mTableName = string3;
        }

        public String getName() {
            return this.mTableName;
        }
    }

}

