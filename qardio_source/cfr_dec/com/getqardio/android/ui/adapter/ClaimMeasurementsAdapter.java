/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.CursorAdapter
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.datamodel.ClaimMeasurement;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ClaimMeasurementsAdapter
extends CursorAdapter {
    private View.OnClickListener claimButtonListener;
    private SimpleDateFormat dateFormat;
    private View.OnClickListener deleteButtonListener;
    private Date now;
    private OnClaimOperationListener onClaimOperationListener;
    private String userName;
    private int weightUnit;

    public ClaimMeasurementsAdapter(Context context, int n, String string2) {
        super(context, null, 0);
        this.weightUnit = n;
        this.userName = string2;
        this.claimButtonListener = new View.OnClickListener(){

            public void onClick(View object) {
                object = (ClaimTagHolder)object.getTag();
                Integer n = object.measurementId;
                if (n != null && ClaimMeasurementsAdapter.this.onClaimOperationListener != null) {
                    ClaimMeasurementsAdapter.this.onClaimOperationListener.onClaim(n);
                }
                BackPanelListViewHelper.hideBackPanelWithAnimation(object.listViewItem);
            }
        };
        this.deleteButtonListener = new View.OnClickListener(){

            public void onClick(View object) {
                object = (DeleteTagHolder)object.getTag();
                Long l = object.measurementTime;
                if (l != null && ClaimMeasurementsAdapter.this.onClaimOperationListener != null) {
                    ClaimMeasurementsAdapter.this.onClaimOperationListener.onDeleteClaim(l);
                }
                BackPanelListViewHelper.hideBackPanelWithAnimation(object.listViewItem);
            }
        };
        this.now = new Date();
        this.dateFormat = DateUtils.getCurrentDateFormat();
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void bindView(View object, Context object2, Cursor object3) {
        void var3_6;
        void var2_3;
        ViewHolder viewHolder = (ViewHolder)object.getTag();
        Resources resources = var2_3.getResources();
        ClaimMeasurement claimMeasurement = MeasurementHelper.Claim.parseClaimMeasurement((Cursor)var3_6);
        viewHolder.name.setText((CharSequence)claimMeasurement.fullname);
        float f = MetricUtils.convertWeight(0, this.weightUnit, Float.valueOf(claimMeasurement.data1).floatValue());
        String string2 = resources.getString(MetricUtils.getWeightUnitNameRes(this.weightUnit));
        viewHolder.value.setText((CharSequence)resources.getString(2131362549, new Object[]{Float.valueOf(f), string2}));
        String string3 = DateUtils.formatDateInLocaleAndWithTodayAndYesterday((Context)var2_3, claimMeasurement.measureDate);
        String string4 = string3 + " " + DateUtils.getCurrentTimeFormat().format(claimMeasurement.measureDate);
        viewHolder.when.setText((CharSequence)string4);
        if (TextUtils.equals((CharSequence)claimMeasurement.fullname, (CharSequence)this.userName)) {
            viewHolder.claimButton.setVisibility(8);
        } else {
            viewHolder.claimButton.setVisibility(0);
        }
        ((ClaimTagHolder)viewHolder.claimButton.getTag()).measurementId = claimMeasurement.id;
        ((DeleteTagHolder)viewHolder.deleteButton.getTag()).measurementTime = claimMeasurement.measureDate.getTime();
    }

    public View newView(Context context, Cursor object, ViewGroup object2) {
        context = BackPanelListViewHelper.wrapListViewItem(context, 2130968850, 2130968851, object2);
        object = new ViewHolder();
        object.name = (TextView)context.findViewById(2131821401);
        object.when = (TextView)context.findViewById(2131821402);
        object.value = (TextView)context.findViewById(2131821403);
        object2 = new ClaimTagHolder();
        object2.listViewItem = context;
        DeleteTagHolder deleteTagHolder = new DeleteTagHolder();
        deleteTagHolder.listViewItem = context;
        object.claimButton = (Button)context.findViewById(2131821404);
        object.claimButton.setOnClickListener(this.claimButtonListener);
        object.claimButton.setTag(object2);
        object.deleteButton = (ImageView)context.findViewById(2131821405);
        object.deleteButton.setOnClickListener(this.deleteButtonListener);
        object.deleteButton.setTag((Object)deleteTagHolder);
        context.setTag(object);
        return context;
    }

    public void setOnClaimOperationListener(OnClaimOperationListener onClaimOperationListener) {
        this.onClaimOperationListener = onClaimOperationListener;
    }

    private static class ClaimTagHolder {
        View listViewItem;
        Integer measurementId;

        private ClaimTagHolder() {
        }
    }

    private static class DeleteTagHolder {
        View listViewItem;
        Long measurementTime;

        private DeleteTagHolder() {
        }
    }

    public static interface OnClaimOperationListener {
        public void onClaim(int var1);

        public void onDeleteClaim(long var1);
    }

    private static class ViewHolder {
        Button claimButton;
        ImageView deleteButton;
        TextView name;
        TextView value;
        TextView when;

        private ViewHolder() {
        }
    }

}

