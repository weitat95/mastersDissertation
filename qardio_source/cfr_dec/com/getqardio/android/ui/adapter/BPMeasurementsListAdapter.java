/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.database.Cursor
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.view.ViewGroup$LayoutParams
 *  android.widget.CursorAdapter
 *  android.widget.ImageView
 *  android.widget.RelativeLayout
 *  android.widget.RelativeLayout$LayoutParams
 *  android.widget.TextView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.database.Cursor;
import android.graphics.PorterDuff;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.getqardio.android.datamodel.BPMeasurement;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.adapter.DateTimeFormatHelper;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;
import com.getqardio.shared.utils.BpLevelConstants;
import java.text.SimpleDateFormat;
import java.util.Date;

public class BPMeasurementsListAdapter
extends CursorAdapter
implements DateTimeFormatHelper.Callback {
    private final Context context;
    private View.OnClickListener deleteButtonListener;
    private OnDeleteListener onDeleteListener;
    private boolean showLocationTags;
    private SimpleDateFormat timeFormat;

    public BPMeasurementsListAdapter(Context context, boolean bl) {
        super(context, null, 0);
        this.context = context;
        this.showLocationTags = bl;
        this.deleteButtonListener = new View.OnClickListener(){

            public void onClick(View object) {
                if ((object = (Date)object.getTag()) != null && BPMeasurementsListAdapter.this.onDeleteListener != null) {
                    BPMeasurementsListAdapter.this.onDeleteListener.onDelete(((Date)object).getTime());
                }
            }
        };
    }

    private int amountIfIndicatorsVisible(BPMeasurement bPMeasurement) {
        int n = 0;
        if (this.isNoteVisible(bPMeasurement)) {
            n = 0 + 1;
        }
        int n2 = n;
        if (this.isLocationTagVisible(bPMeasurement)) {
            n2 = n + 1;
        }
        n = n2;
        if (this.isSourceVisible(bPMeasurement)) {
            n = n2 + 1;
        }
        return n;
    }

    public static long extractDate(View object) {
        if ((object = (ViewHolder)object.getTag()) != null && object.measurementDate != null) {
            return object.measurementDate.getTime();
        }
        return -1L;
    }

    /*
     * Enabled aggressive block sorting
     */
    private RelativeLayout.LayoutParams getCenteredInBoxPosition(RelativeLayout.LayoutParams layoutParams, BPMeasurement bPMeasurement) {
        int n = 12;
        boolean bl = true;
        int n2 = this.amountIfIndicatorsVisible(bPMeasurement);
        if (n2 == 1) {
            return this.getRlLayoutParamsWithIndicatorPosition(13, 13);
        }
        RelativeLayout.LayoutParams layoutParams2 = layoutParams;
        if (n2 != 2) return layoutParams2;
        if (this.isSourceVisible(bPMeasurement) && this.isLocationTagVisible(bPMeasurement)) {
            bl = layoutParams.getRules()[20] == -1;
            if (bl) {
                return this.getRlLayoutParamsWithIndicatorPosition(n, 14);
            }
            n = 10;
            return this.getRlLayoutParamsWithIndicatorPosition(n, 14);
        }
        if (layoutParams.getRules()[10] != -1) {
            bl = false;
        }
        if (!bl) return this.getRlLayoutParamsWithIndicatorPosition(n, 14);
        n = 10;
        return this.getRlLayoutParamsWithIndicatorPosition(n, 14);
    }

    private RelativeLayout.LayoutParams getRlLayoutParamsWithIndicatorPosition(int n, int n2) {
        RelativeLayout.LayoutParams layoutParams = new RelativeLayout.LayoutParams(-2, -2);
        layoutParams.addRule(n);
        layoutParams.addRule(n2);
        return layoutParams;
    }

    private boolean isLocationTagVisible(BPMeasurement bPMeasurement) {
        return this.showLocationTags && bPMeasurement.tag != null && bPMeasurement.tag != 0;
    }

    private boolean isNoteVisible(BPMeasurement bPMeasurement) {
        return !TextUtils.isEmpty((CharSequence)bPMeasurement.note);
    }

    private boolean isSourceVisible(BPMeasurement bPMeasurement) {
        return bPMeasurement.source != null && bPMeasurement.source != 0;
    }

    private void setAqi(ViewHolder viewHolder, BPMeasurement bPMeasurement) {
        viewHolder.aqiLabel.setVisibility(4);
        viewHolder.aqiIndicator.setVisibility(4);
    }

    private void setBp(Context context, ViewHolder viewHolder, BPMeasurement bPMeasurement) {
        if (bPMeasurement.dia != null && bPMeasurement.sys != null) {
            String string2 = String.format("%s / %s", Utils.formatInteger(bPMeasurement.sys), Utils.formatInteger(bPMeasurement.dia));
            viewHolder.bloodPressure.setText((CharSequence)string2);
            viewHolder.bpLevel.setColorFilter(BpLevelConstants.getColorForLevel(context, bPMeasurement.dia, bPMeasurement.sys).intValue(), PorterDuff.Mode.SRC_ATOP);
        }
    }

    private void setDate(ViewHolder viewHolder, BPMeasurement bPMeasurement) {
        if (bPMeasurement.measureDate != null) {
            viewHolder.date.setText((CharSequence)DateUtils.formatDateInLocaleAndWithTodayAndYesterday(this.context, bPMeasurement.measureDate));
            viewHolder.time.setText((CharSequence)this.timeFormat.format(bPMeasurement.measureDate));
            viewHolder.measurementDate = bPMeasurement.measureDate;
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setLocationTag(ViewHolder viewHolder, BPMeasurement bPMeasurement) {
        RelativeLayout.LayoutParams layoutParams = this.getRlLayoutParamsWithIndicatorPosition(10, 21);
        if (this.isLocationTagVisible(bPMeasurement)) {
            viewHolder.locationTag.setVisibility(0);
            layoutParams = this.getCenteredInBoxPosition(layoutParams, bPMeasurement);
            switch (bPMeasurement.tag) {
                default: {
                    throw new RuntimeException(String.format("Unknown location tag - %s", bPMeasurement.tag));
                }
                case 1: {
                    viewHolder.locationTag.setImageResource(2130837851);
                    bPMeasurement = layoutParams;
                    break;
                }
                case 2: {
                    viewHolder.locationTag.setImageResource(2130837853);
                    bPMeasurement = layoutParams;
                    break;
                }
                case 3: {
                    viewHolder.locationTag.setImageResource(2130837857);
                    bPMeasurement = layoutParams;
                    break;
                }
                case 4: {
                    viewHolder.locationTag.setImageResource(2130837849);
                    bPMeasurement = layoutParams;
                    break;
                }
                case 5: {
                    viewHolder.locationTag.setImageResource(2130837847);
                    bPMeasurement = layoutParams;
                    break;
                }
                case 6: {
                    viewHolder.locationTag.setImageResource(2130837855);
                    bPMeasurement = layoutParams;
                    break;
                }
            }
        } else {
            viewHolder.locationTag.setVisibility(4);
            bPMeasurement = layoutParams;
        }
        viewHolder.locationTag.setLayoutParams((ViewGroup.LayoutParams)bPMeasurement);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setNote(ViewHolder viewHolder, BPMeasurement bPMeasurement) {
        RelativeLayout.LayoutParams layoutParams = this.getRlLayoutParamsWithIndicatorPosition(12, 20);
        if (this.isNoteVisible(bPMeasurement)) {
            viewHolder.note.setVisibility(0);
            bPMeasurement = this.getCenteredInBoxPosition(layoutParams, bPMeasurement);
        } else {
            viewHolder.note.setVisibility(4);
            bPMeasurement = layoutParams;
        }
        viewHolder.note.setLayoutParams((ViewGroup.LayoutParams)bPMeasurement);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setPulse(ViewHolder viewHolder, BPMeasurement bPMeasurement) {
        Object object;
        if (bPMeasurement.irregularHeartBeat != null && bPMeasurement.irregularHeartBeat.booleanValue()) {
            viewHolder.pulse.setCompoundDrawablesWithIntrinsicBounds(2130837796, 0, 0, 0);
        } else {
            viewHolder.pulse.setCompoundDrawablesWithIntrinsicBounds(2130837778, 0, 0, 0);
        }
        if ((object = bPMeasurement.pulse) != null) {
            object = (Integer)object != 0 ? Utils.formatInteger((Integer)object) : "--";
            viewHolder.pulse.setText((CharSequence)object);
        }
        viewHolder.deleteButton.setTag((Object)bPMeasurement.measureDate);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setSource(ViewHolder viewHolder, BPMeasurement bPMeasurement) {
        RelativeLayout.LayoutParams layoutParams = this.getRlLayoutParamsWithIndicatorPosition(10, 20);
        if (bPMeasurement.source != null) {
            viewHolder.source.setVisibility(0);
            layoutParams = this.getCenteredInBoxPosition(layoutParams, bPMeasurement);
            switch (bPMeasurement.source) {
                default: {
                    throw new RuntimeException(String.format("Unknown source- %s", bPMeasurement.source));
                }
                case 0: {
                    viewHolder.source.setVisibility(4);
                    bPMeasurement = layoutParams;
                    break;
                }
                case 1: {
                    viewHolder.source.setImageResource(2130837776);
                    bPMeasurement = layoutParams;
                    break;
                }
                case 2: {
                    viewHolder.source.setImageResource(2130837825);
                    bPMeasurement = layoutParams;
                    break;
                }
                case 3: {
                    viewHolder.source.setImageResource(2130837775);
                    bPMeasurement = layoutParams;
                    break;
                }
                case 4: {
                    viewHolder.source.setImageResource(2130837802);
                    bPMeasurement = layoutParams;
                    break;
                }
            }
        } else {
            viewHolder.source.setVisibility(4);
            bPMeasurement = layoutParams;
        }
        viewHolder.source.setLayoutParams((ViewGroup.LayoutParams)bPMeasurement);
    }

    private void setWeather(ViewHolder viewHolder, BPMeasurement bPMeasurement) {
        viewHolder.weatherIndicator.setVisibility(4);
        viewHolder.temperature.setVisibility(4);
    }

    public void bindView(View view, Context context, Cursor object) {
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        object = MeasurementHelper.BloodPressure.parseMeasurement(object);
        this.setDate(viewHolder, (BPMeasurement)object);
        this.setBp(context, viewHolder, (BPMeasurement)object);
        this.setNote(viewHolder, (BPMeasurement)object);
        this.setLocationTag(viewHolder, (BPMeasurement)object);
        this.setSource(viewHolder, (BPMeasurement)object);
        this.setAqi(viewHolder, (BPMeasurement)object);
        this.setWeather(viewHolder, (BPMeasurement)object);
        this.setPulse(viewHolder, (BPMeasurement)object);
        BackPanelListViewHelper.hideBackPanel(view);
    }

    public View newView(Context context, Cursor object, ViewGroup viewGroup) {
        context = BackPanelListViewHelper.wrapListViewItem(context, 2130968647, 2130968679, viewGroup);
        object = new ViewHolder();
        object.date = (TextView)context.findViewById(2131820920);
        object.time = (TextView)context.findViewById(2131820921);
        object.note = (ImageView)context.findViewById(2131820923);
        object.locationTag = (ImageView)context.findViewById(2131820924);
        object.bloodPressure = (TextView)context.findViewById(2131820929);
        object.pulse = (TextView)context.findViewById(2131820930);
        object.bpLevel = (ImageView)context.findViewById(2131820919);
        object.deleteButton = (ImageView)context.findViewById(2131821017);
        object.deleteButton.setOnClickListener(this.deleteButtonListener);
        object.source = (ImageView)context.findViewById(2131820922);
        object.weatherIndicator = (ImageView)context.findViewById(2131820925);
        object.temperature = (TextView)context.findViewById(2131820926);
        object.aqiLabel = (TextView)context.findViewById(2131820927);
        object.aqiIndicator = (TextView)context.findViewById(2131820928);
        context.setTag(object);
        return context;
    }

    @Override
    public void setDateFormat(SimpleDateFormat simpleDateFormat) {
    }

    public void setOnDeleteListener(OnDeleteListener onDeleteListener) {
        this.onDeleteListener = onDeleteListener;
    }

    @Override
    public void setTimeFormat(SimpleDateFormat simpleDateFormat) {
        this.timeFormat = simpleDateFormat;
    }

    @Override
    public void updateData() {
        this.notifyDataSetChanged();
    }

    public static interface OnDeleteListener {
        public void onDelete(long var1);
    }

    private static class ViewHolder {
        public TextView aqiIndicator;
        public TextView aqiLabel;
        public TextView bloodPressure;
        public ImageView bpLevel;
        public TextView date;
        public ImageView deleteButton;
        public ImageView locationTag;
        public Date measurementDate;
        public ImageView note;
        public TextView pulse;
        public ImageView source;
        public TextView temperature;
        public TextView time;
        public ImageView weatherIndicator;

        private ViewHolder() {
        }
    }

}

