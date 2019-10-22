/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.sqlite.SQLiteDatabase
 *  android.database.sqlite.SQLiteDatabase$CursorFactory
 *  android.database.sqlite.SQLiteException
 *  android.database.sqlite.SQLiteOpenHelper
 */
package com.getqardio.android.provider;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import timber.log.Timber;

public class AppSQLStorage
extends SQLiteOpenHelper {
    public AppSQLStorage(Context context) {
        super(context, "qardio.db", null, 154);
    }

    private void dropTables(SQLiteDatabase sQLiteDatabase) {
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS faqs;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS users;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS profiles;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS reminders;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS followings;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS followers;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS settings;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS images;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS measurements;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS devices;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS tooltips;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS visitor_measurements;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS support_tickets;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS measurements_history;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS flickr_photo_metadata;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS notes;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS statistic;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS weight_measurements;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS claim_measurements;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS current_goals;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS device_associations;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS pregnancy_mode_data;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS notifications_device_tokens;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS following_user_info;");
        sQLiteDatabase.execSQL("DROP TABLE IF EXISTS following_metadata;");
    }

    public void onCreate(SQLiteDatabase sQLiteDatabase) {
        Timber.d("onCreate()", new Object[0]);
        sQLiteDatabase.execSQL("CREATE TABLE faqs (_id INTEGER PRIMARY KEY AUTOINCREMENT,faq_id INTEGER,create_date INTEGER,parent INTEGER,category TEXT,question TEXT,answer TEXT,frequency INTEGER,device_type TEXT DEFAULT '',UNIQUE (faq_id) ON CONFLICT REPLACE);");
        sQLiteDatabase.execSQL("CREATE TABLE users (_id INTEGER PRIMARY KEY AUTOINCREMENT,email TEXT,email_hash TEXT,password TEXT,token TEXT,token_expired INTEGER, tracking_id TEXT, UNIQUE (email) ON CONFLICT IGNORE);");
        sQLiteDatabase.execSQL("CREATE TABLE profiles (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,sync_status INTEGER DEFAULT 0,ref_id INTEGER DEFAULT NULL,email TEXT,first_name TEXT,last_name TEXT,dob INTEGER DEFAULT NULL,height FLOAT DEFAULT NULL,height_unit INTEGER,weight FLOAT DEFAULT NULL,weight_unit INTEGER,address TEXT,phone TEXT,gender INTEGER DEFAULT NULL,latitude INTEGER,longitude INTEGER,zip TEXT,state TEXT,country TEXT,locale TEXT,doctor_name TEXT DEFAULT '', doctor_email TEXT DEFAULT '', qb_visible_name TEXT DEFAULT '', UNIQUE (user_id) ON CONFLICT REPLACE);");
        sQLiteDatabase.execSQL("CREATE TABLE reminders (_id INTEGER PRIMARY KEY AUTOINCREMENT,reminder_id INTEGER,user_id INTEGER,sync_status INTEGER DEFAULT 0,days INTEGER DEFAULT 0,remind_time INTEGER,repeat INTEGER DEFAULT 0,type TEXT); \nCREATE INDEX index_reminders_user_id ON reminders (user_id);\nCREATE INDEX index_reminders_reminder_id ON reminders (user_id);");
        sQLiteDatabase.execSQL("CREATE TABLE followings (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,friend_id INTEGER,sync_status INTEGER DEFAULT 0,access_status INTEGER,notification INTEGER,friend_email TEXT);\nCREATE INDEX index_followings_user_id ON followings (user_id);\nCREATE INDEX index_followings_user_id ON followings (friend_id);\nUNIQUE (user_id, friend_id) ON CONFLICT IGNORE);");
        sQLiteDatabase.execSQL("CREATE TABLE followers (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,friend_id INTEGER,sync_status INTEGER DEFAULT 0,access_status INTEGER,friend_email TEXT);\nCREATE INDEX index_followers_user_id ON followers (user_id);\nCREATE INDEX index_followers_user_id ON followers (friend_id);\nUNIQUE (user_id, friend_id) ON CONFLICT IGNORE);");
        sQLiteDatabase.execSQL("CREATE TABLE settings (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,sync_status INTEGER DEFAULT 0,measurements_number INTEGER,pause_size INTEGER,visitor_mode INTEGER DEFAULT 0,allow_location INTEGER DEFAULT 1,albums INTEGER DEFAULT 0,allow_import_shealth INTEGER DEFAULT 0,allow_export_shealth INTEGER DEFAULT 0,allow_weight_export_shealth INTEGER DEFAULT 0,allow_integration_google_fit INTEGER DEFAULT 0,allow_weight_import_shealth INTEGER DEFAULT 0,allow_notifications INTEGER DEFAULT 1,allow_mixpanel_notifications INTEGER DEFAULT 0,show_photo INTEGER DEFAULT 0);\nCREATE INDEX index_settings_user_id ON settings (user_id);");
        sQLiteDatabase.execSQL("CREATE TABLE images (_id INTEGER PRIMARY KEY AUTOINCREMENT,setting_id INTEGER,image_type INTEGER,image_path TEXT);\nCREATE INDEX index_images_setting_id ON images (setting_id);");
        sQLiteDatabase.execSQL("CREATE TABLE measurements (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,sync_status INTEGER DEFAULT 0,revision INTEGER DEFAULT 0,puls INTEGER,dia INTEGER,sys INTEGER,irregular_heart_beat INTEGER,measure_date INTEGER,note TEXT,device_id TEXT,timezone TEXT DEFAULT '',latitude REAL, longitude REAL, tag INTEGER DEFAULT NULL, source INTEGER DEFAULT 0,UNIQUE (user_id, measure_date) ON CONFLICT REPLACE);\nCREATE INDEX index_measurements_user_id ON measurements (user_id);");
        sQLiteDatabase.execSQL("CREATE TABLE devices (_id INTEGER PRIMARY KEY AUTOINCREMENT,device_id TEXT,sync_status INTEGER DEFAULT 0,battery_level INTEGER,is_active INTEGER,measurement_count INTEGER,measurement_errors INTEGER,replaced_batteries INTEGER,UNIQUE (device_id) ON CONFLICT REPLACE);");
        sQLiteDatabase.execSQL("CREATE TABLE tooltips (_id INTEGER PRIMARY KEY AUTOINCREMENT,tooltip_id INTEGER,title TEXT DEFAULT '',text TEXT DEFAULT '',image_url TEXT '',text_position DEFAULT '',UNIQUE (tooltip_id) ON CONFLICT REPLACE);");
        sQLiteDatabase.execSQL("CREATE TABLE visitor_measurements (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,puls INTEGER,dia INTEGER,sys INTEGER,irregular_heart_beat INTEGER,measure_date INTEGER,device_id TEXT,timezone TEXT DEFAULT '',member_name TEXT DEFAULT '');");
        sQLiteDatabase.execSQL("CREATE TABLE support_tickets (_id INTEGER PRIMARY KEY AUTOINCREMENT,subject TEXT DEFAULT '',message_body TEXT DEFAULT '');");
        sQLiteDatabase.execSQL("CREATE TABLE measurements_history (_id INTEGER PRIMARY KEY AUTOINCREMENT,target_email TEXT,note TEXT,target_name TEXT,user_id LONG);");
        sQLiteDatabase.execSQL("CREATE TABLE flickr_photo_metadata (_id INTEGER PRIMARY KEY AUTOINCREMENT,flickr_id TEXT,urlz TEXT,load_date INTEGER,UNIQUE (flickr_id) ON CONFLICT REPLACE);");
        sQLiteDatabase.execSQL("CREATE TABLE notes (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,note_type INTEGER NOT NULL,icon_res INTEGER NOT NULL,text_res INTEGER,note_text INTEGER,measurement_type TEXT DEFAULT 'bp',last_used INTEGER NOT NULL);");
        sQLiteDatabase.execSQL("CREATE TABLE statistic (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,device_id TEXT,measurements_count INTEGER,badMeasurementsCount INTEGER,changedBatteriesCount INTEGER,battery_status INTEGER,is_active INTEGER,firmware TEXT,UNIQUE (user_id,device_id)ON CONFLICT REPLACE);");
        sQLiteDatabase.execSQL("CREATE TABLE weight_measurements (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,sync_status INTEGER DEFAULT 0,weight REAL,impedance INTEGER,full_name TEXT,device_id TEXT,height INTEGER,sex TEXT,battery INTEGER,age INTEGER,fat INTEGER,bmi REAL,z INTEGER,mt INTEGER,bmc INTEGER,tbw INTEGER,measurement_id TEXT,user TEXT,qb_user_id TEXT,source TEXT,measure_date INTEGER,note TEXT,member_name TEXT,device_serial_number TEXT,timezone TEXT DEFAULT '',measurements_source INTEGER DEFAULT 0,visitor TEXT,fw TEXT,UNIQUE (user_id, measure_date) ON CONFLICT REPLACE);\nCREATE INDEX index_weight_measurements_user_id ON weight_measurements (user_id);");
        sQLiteDatabase.execSQL("CREATE TABLE claim_measurements (_id INTEGER,measurement_id TEXT,userId INTEGER,fullName TEXT DEFAULT '',deviceId TEXT DEFAULT '',data1 TEXT DEFAULT '',data2 TEXT DEFAULT '',data3 TEXT DEFAULT '',note TEXT DEFAULT '',memberId INTEGER,irregularHb INTEGER,longitude DOUBLE,latitude DOUBLE,source INTEGER,timezone TEXT,tag INTEGER,time INTEGER,sync_status INTEGER,UNIQUE (time)ON CONFLICT REPLACE);");
        sQLiteDatabase.execSQL("CREATE TABLE current_goals (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, sync_status INTEGER DEFAULT 0, start_date INTEGER NOT NULL, target REAL, target_per_week REAL, type TEXT, excellent_boundary INTEGER,neutral_boundary INTEGER,positive_boundary INTEGER,UNIQUE (user_id) ON CONFLICT REPLACE);\nCREATE INDEX index_current_goals_user_id ON current_goals (user_id);");
        sQLiteDatabase.execSQL("CREATE TABLE device_associations (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, sync_status INTEGER DEFAULT 0, serial_number TEXT, device_id TEXT, mac_address TEXT, startTimestamp TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE pregnancy_mode_data (_id INTEGER PRIMARY KEY,cloud_id INTEGER,user_id INTEGER,start_date INTEGER,end_date INTEGER,due_date INTEGER,start_weight INTEGER, is_show_weight INTEGER, sync_status INTEGER DEFAULT 0);");
        sQLiteDatabase.execSQL("CREATE TABLE notifications_device_tokens (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER,sync_status INTEGER,device_token TEXT);");
        sQLiteDatabase.execSQL("CREATE TABLE following_user_info (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER,access_status TEXT,watcher_email TEXT,notifications INTEGER,owner_email TEXT,first_name TEXT,last_name TEXT)");
        sQLiteDatabase.execSQL("CREATE TABLE following_metadata (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER,metadata_type INTEGER NOT NULL,measurement_type TEXT,date INTEGER,data1 TEXT, data2 TEXT, data3 TEXT, UNIQUE (user_id,metadata_type,measurement_type)ON CONFLICT REPLACE);");
        sQLiteDatabase.execSQL("CREATE TRIGGER IF NOT EXISTS inc_measurement_revision_trigger AFTER UPDATE ON measurements BEGIN UPDATE measurements SET revision = OLD.revision + 1 WHERE measure_date = NEW.measure_date; END;");
        sQLiteDatabase.execSQL("CREATE TRIGGER IF NOT EXISTS delete_following_user_info_trigger AFTER DELETE ON following_user_info BEGIN DELETE FROM following_metadata WHERE user_id=OLD._id; END;");
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     * Lifted jumps to return sites
     */
    public void onUpgrade(SQLiteDatabase var1_1, int var2_2, int var3_3) {
        Timber.d("onUpgrade() from %d to %d", new Object[]{var2_2, var3_3});
        if (var2_2 >= 110) ** GOTO lbl7
        try {
            var1_1.execSQL("ALTER TABLE profiles ADD COLUMN doctor_name TEXT DEFAULT '';");
            var1_1.execSQL("ALTER TABLE profiles ADD COLUMN doctor_email TEXT DEFAULT '';");
            var1_1.execSQL("ALTER TABLE measurements ADD COLUMN timezone TEXT DEFAULT '';");
lbl7:
            // 2 sources
            if (var2_2 < 111) {
                var1_1.execSQL("CREATE TABLE tooltips (_id INTEGER PRIMARY KEY AUTOINCREMENT,tooltip_id INTEGER,title TEXT DEFAULT '',text TEXT DEFAULT '',image_url TEXT '',text_position DEFAULT '',UNIQUE (tooltip_id) ON CONFLICT REPLACE);");
            }
            if (var2_2 < 112) {
                var1_1.execSQL("ALTER TABLE measurements ADD COLUMN latitude REAL;");
                var1_1.execSQL("ALTER TABLE measurements ADD COLUMN longitude REAL;");
                var1_1.execSQL("ALTER TABLE measurements ADD COLUMN tag INTEGER DEFAULT NULL;");
                var1_1.execSQL("ALTER TABLE settings ADD COLUMN allow_location INTEGER DEFAULT 1;");
            }
            if (var2_2 < 113) {
                var1_1.execSQL("CREATE TABLE visitor_measurements (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,puls INTEGER,dia INTEGER,sys INTEGER,irregular_heart_beat INTEGER,measure_date INTEGER,device_id TEXT,timezone TEXT DEFAULT '',member_name TEXT DEFAULT '');");
                var1_1.execSQL("CREATE TABLE support_tickets (_id INTEGER PRIMARY KEY AUTOINCREMENT,subject TEXT DEFAULT '',message_body TEXT DEFAULT '');");
                var1_1.execSQL("CREATE TABLE measurements_history (_id INTEGER PRIMARY KEY AUTOINCREMENT,target_email TEXT,note TEXT,target_name TEXT,user_id LONG);");
                var1_1.execSQL("ALTER TABLE measurements ADD COLUMN revision INTEGER DEFAULT 0;");
                var1_1.execSQL("CREATE TRIGGER IF NOT EXISTS inc_measurement_revision_trigger AFTER UPDATE ON measurements BEGIN UPDATE measurements SET revision = OLD.revision + 1 WHERE measure_date = NEW.measure_date; END;");
            }
            if (var2_2 < 114) {
                var1_1.execSQL("ALTER TABLE measurements ADD COLUMN source INTEGER DEFAULT 0;");
            }
            if (var2_2 < 115) {
                var1_1.execSQL("ALTER TABLE settings ADD COLUMN albums INTEGER DEFAULT 0;");
                var1_1.execSQL("CREATE TABLE flickr_photo_metadata (_id INTEGER PRIMARY KEY AUTOINCREMENT,flickr_id TEXT,urlz TEXT,load_date INTEGER,UNIQUE (flickr_id) ON CONFLICT REPLACE);");
                var1_1.execSQL("CREATE TABLE notes (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,note_type INTEGER NOT NULL,icon_res INTEGER NOT NULL,text_res INTEGER,note_text INTEGER,measurement_type TEXT DEFAULT 'bp',last_used INTEGER NOT NULL);");
                var1_1.execSQL("CREATE TABLE statistic (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,device_id TEXT,measurements_count INTEGER,badMeasurementsCount INTEGER,changedBatteriesCount INTEGER,battery_status INTEGER,is_active INTEGER,firmware TEXT,UNIQUE (user_id,device_id)ON CONFLICT REPLACE);");
            }
            if (var2_2 < 116 && var2_2 > 113) {
                var1_1.execSQL("ALTER TABLE measurements_history ADD COLUMN note TEXT DEFAULT ''");
            }
            if (var2_2 < 117) {
                var1_1.execSQL("ALTER TABLE settings ADD COLUMN allow_import_shealth INTEGER DEFAULT 0;");
                var1_1.execSQL("ALTER TABLE settings ADD COLUMN allow_export_shealth INTEGER DEFAULT 0;");
            }
            if (var2_2 < 140) {
                var1_1.execSQL("CREATE TABLE weight_measurements (_id INTEGER PRIMARY KEY AUTOINCREMENT,user_id INTEGER,sync_status INTEGER DEFAULT 0,weight REAL,impedance INTEGER,full_name TEXT,device_id TEXT,height INTEGER,sex TEXT,battery INTEGER,age INTEGER,fat INTEGER,bmi REAL,z INTEGER,mt INTEGER,bmc INTEGER,tbw INTEGER,measurement_id TEXT,user TEXT,qb_user_id TEXT,source TEXT,measure_date INTEGER,note TEXT,member_name TEXT,device_serial_number TEXT,timezone TEXT DEFAULT '',measurements_source INTEGER DEFAULT 0,visitor TEXT,fw TEXT,UNIQUE (user_id, measure_date) ON CONFLICT REPLACE);\nCREATE INDEX index_weight_measurements_user_id ON weight_measurements (user_id);");
                if (var2_2 > 115) {
                    var1_1.execSQL("ALTER TABLE notes ADD COLUMN measurement_type TEXT DEFAULT 'bp';");
                }
            }
            if (var2_2 < 141) {
                var1_1.execSQL("CREATE TABLE claim_measurements (_id INTEGER,measurement_id TEXT,userId INTEGER,fullName TEXT DEFAULT '',deviceId TEXT DEFAULT '',data1 TEXT DEFAULT '',data2 TEXT DEFAULT '',data3 TEXT DEFAULT '',note TEXT DEFAULT '',memberId INTEGER,irregularHb INTEGER,longitude DOUBLE,latitude DOUBLE,source INTEGER,timezone TEXT,tag INTEGER,time INTEGER,sync_status INTEGER,UNIQUE (time)ON CONFLICT REPLACE);");
                var1_1.execSQL("ALTER TABLE settings ADD COLUMN allow_weight_import_shealth INTEGER DEFAULT 0;");
                var1_1.execSQL("ALTER TABLE settings ADD COLUMN allow_weight_export_shealth INTEGER DEFAULT 0;");
                var1_1.execSQL("ALTER TABLE profiles ADD COLUMN ref_id INTEGER DEFAULT NULL;");
            }
            if (var2_2 < 142) {
                var1_1.execSQL("ALTER TABLE faqs ADD COLUMN device_type TEXT DEFAULT QardioArm;");
                var1_1.execSQL("CREATE TABLE current_goals (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, sync_status INTEGER DEFAULT 0, start_date INTEGER NOT NULL, target REAL, target_per_week REAL, type TEXT, excellent_boundary INTEGER,neutral_boundary INTEGER,positive_boundary INTEGER,UNIQUE (user_id) ON CONFLICT REPLACE);\nCREATE INDEX index_current_goals_user_id ON current_goals (user_id);");
                if (var2_2 > 140) {
                    var1_1.execSQL("ALTER TABLE weight_measurements ADD COLUMN measurements_source INTEGER DEFAULT 0;");
                }
                var1_1.execSQL("CREATE TABLE device_associations (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER, sync_status INTEGER DEFAULT 0, serial_number TEXT, device_id TEXT, mac_address TEXT, startTimestamp TEXT)");
            }
            if (var2_2 < 143 && var2_2 > 141) {
                var1_1.execSQL("ALTER TABLE claim_measurements ADD COLUMN sync_status INTEGER DEFAULT '0';");
            }
            if (var2_2 < 144) {
                if (var2_2 > 142) {
                    var1_1.execSQL("ALTER TABLE device_associations ADD COLUMN mac_address TEXT;");
                }
                var1_1.execSQL("ALTER TABLE followings ADD COLUMN notification DEFAULT 0;");
                var1_1.execSQL("CREATE TABLE notifications_device_tokens (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER,sync_status INTEGER,device_token TEXT);");
                var1_1.execSQL("ALTER TABLE settings ADD COLUMN allow_notifications INTEGER DEFAULT 0;");
                var1_1.execSQL("CREATE TABLE pregnancy_mode_data (_id INTEGER PRIMARY KEY,cloud_id INTEGER,user_id INTEGER,start_date INTEGER,end_date INTEGER,due_date INTEGER,start_weight INTEGER, is_show_weight INTEGER, sync_status INTEGER DEFAULT 0);");
            }
            if (var2_2 < 145) {
                var1_1.execSQL("ALTER TABLE settings ADD COLUMN allow_integration_google_fit INTEGER DEFAULT 0;");
                var1_1.execSQL("CREATE TABLE following_user_info (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER,access_status TEXT,watcher_email TEXT,notifications INTEGER,owner_email TEXT,first_name TEXT,last_name TEXT)");
                var1_1.execSQL("CREATE TABLE following_metadata (_id INTEGER PRIMARY KEY AUTOINCREMENT, user_id INTEGER,metadata_type INTEGER NOT NULL,measurement_type TEXT,date INTEGER,data1 TEXT, data2 TEXT, data3 TEXT, UNIQUE (user_id,metadata_type,measurement_type)ON CONFLICT REPLACE);");
                var1_1.execSQL("CREATE TRIGGER IF NOT EXISTS delete_following_user_info_trigger AFTER DELETE ON following_user_info BEGIN DELETE FROM following_metadata WHERE user_id=OLD._id; END;");
            }
            if (var2_2 < 146 && var2_2 > 140) {
                var1_1.execSQL("ALTER TABLE weight_measurements ADD COLUMN device_serial_number TEXT ;");
            }
            if (var2_2 < 147) {
                var1_1.execSQL("ALTER TABLE users ADD COLUMN tracking_id TEXT ;");
            }
            if (var2_2 < 148) {
                var1_1.execSQL("ALTER TABLE settings ADD COLUMN allow_mixpanel_notifications INTEGER DEFAULT 0;");
            }
            if (var2_2 < 149) {
                var1_1.execSQL("ALTER TABLE weight_measurements ADD COLUMN mt INTEGER DEFAULT 0;");
                var1_1.execSQL("ALTER TABLE weight_measurements ADD COLUMN tbw INTEGER DEFAULT 0;");
                var1_1.execSQL("ALTER TABLE weight_measurements ADD COLUMN bmc INTEGER DEFAULT 0;");
                var1_1.execSQL("ALTER TABLE weight_measurements ADD COLUMN member_name TEXT ;");
                var1_1.execSQL("ALTER TABLE weight_measurements ADD COLUMN visitor TEXT ;");
            }
            if (var2_2 < 150) {
                var1_1.execSQL("ALTER TABLE claim_measurements ADD COLUMN measurement_id TEXT DEFAULT '';");
            }
            if (var2_2 < 151) {
                var1_1.execSQL("ALTER TABLE weight_measurements ADD COLUMN fw TEXT ;");
                var1_1.execSQL("ALTER TABLE device_associations ADD COLUMN startTimestamp");
            }
            if (var2_2 < 152) {
                var1_1.execSQL("ALTER TABLE statistic ADD COLUMN firmware TEXT ;");
            }
            if (var2_2 < 153) {
                var1_1.execSQL("ALTER TABLE profiles ADD COLUMN qb_visible_name TEXT DEFAULT '';");
            }
            if (var2_2 >= 154) return;
            return;
        }
        catch (SQLiteException var4_4) {
            Timber.e(var4_4);
            this.dropTables(var1_1);
            this.onCreate(var1_1);
            return;
        }
    }
}

