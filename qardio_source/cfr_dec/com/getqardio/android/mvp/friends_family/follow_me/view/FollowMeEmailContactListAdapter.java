/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.graphics.drawable.Drawable
 *  android.net.Uri
 *  android.provider.ContactsContract
 *  android.provider.ContactsContract$CommonDataKinds
 *  android.provider.ContactsContract$CommonDataKinds$Email
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.FilterQueryProvider
 *  android.widget.Filterable
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.widget.CursorAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FilterQueryProvider;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.amulyakhare.textdrawable.TextDrawable;
import com.getqardio.android.mvp.common.ui.ColorsUtil;
import com.getqardio.android.utils.ui.Convert;

public class FollowMeEmailContactListAdapter
extends CursorAdapter
implements Filterable {
    static final String[] PROJECTION = new String[]{"_id", "display_name", "data1"};
    private ContentResolver contentResolver;
    @BindView
    TextView email;
    @BindView
    ImageView image;
    private final LayoutInflater inflater;
    @BindView
    TextView name;
    private final TextDrawable.IBuilder textDrawableBuilder;

    public FollowMeEmailContactListAdapter(Context context, Cursor cursor) {
        super(context, cursor, true);
        this.contentResolver = context.getContentResolver();
        this.inflater = LayoutInflater.from((Context)context);
        this.textDrawableBuilder = TextDrawable.builder().beginConfig().toUpperCase().fontSize((int)Convert.convertDpToPixel(16.0f, context)).endConfig().round();
    }

    @Override
    public void bindView(View object, Context object2, Cursor cursor) {
        ButterKnife.bind(this, (View)object);
        object = cursor.getString(cursor.getColumnIndex("data1"));
        this.email.setText((CharSequence)object);
        object2 = cursor.getString(cursor.getColumnIndex("display_name"));
        this.name.setText((CharSequence)object2);
        if (object2 != null) {
            this.image.setImageDrawable((Drawable)this.textDrawableBuilder.build(((String)object2).substring(0, 1), ColorsUtil.generateColor(object2)));
            return;
        }
        this.image.setImageDrawable((Drawable)this.textDrawableBuilder.build(((String)object).substring(0, 1), ColorsUtil.generateColor(object)));
    }

    @Override
    public String convertToString(Cursor cursor) {
        return cursor.getString(cursor.getColumnIndex("data1"));
    }

    @Override
    public View newView(Context context, Cursor cursor, ViewGroup viewGroup) {
        return this.inflater.inflate(2130968714, viewGroup, false);
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    @Override
    public Cursor runQueryOnBackgroundThread(CharSequence charSequence) {
        if (this.getFilterQueryProvider() != null) {
            return this.getFilterQueryProvider().runQuery(charSequence);
        }
        StringBuilder stringBuilder = null;
        String[] arrstring = null;
        if (charSequence != null) {
            stringBuilder = new StringBuilder();
            stringBuilder.append("UPPER(");
            stringBuilder.append("data1");
            stringBuilder.append(") GLOB ? or UPPER(");
            stringBuilder.append("display_name");
            stringBuilder.append(") GLOB ?");
            arrstring = new String[]{"*" + charSequence.toString().toUpperCase() + "*", "*" + charSequence.toString().toUpperCase() + "*"};
        }
        ContentResolver contentResolver = this.contentResolver;
        Uri uri = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String[] arrstring2 = PROJECTION;
        if (stringBuilder == null) {
            charSequence = null;
            do {
                return contentResolver.query(uri, arrstring2, (String)charSequence, arrstring, null);
                break;
            } while (true);
        }
        charSequence = stringBuilder.toString();
        return contentResolver.query(uri, arrstring2, (String)charSequence, arrstring, null);
    }
}

