/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Bitmap
 *  android.text.TextUtils
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.CursorAdapter
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.adapter;

import android.content.Context;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CursorAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.basealgoritms.BodyComposition;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.ui.adapter.DateTimeFormatHelper;
import com.getqardio.android.ui.adapter.WeightMeasurementsAdapter$$Lambda$1;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.BackPanelListViewHelper;
import com.getqardio.android.utils.ui.Convert;
import java.text.SimpleDateFormat;
import java.util.Date;

public class WeightMeasurementsAdapter
extends CursorAdapter
implements DateTimeFormatHelper.Callback {
    private static final int[] COLORS = new int[]{2131689494, 2131689491, 2131689493, 2131689492};
    private final Context context;
    private View.OnClickListener deleteButtonClickListener;
    private OnDeleteListener onDeleteListener;
    private SimpleDateFormat timeFormat;
    private int weightUnit;

    public WeightMeasurementsAdapter(Context context, int n) {
        super(context, null, 0);
        this.context = context;
        this.weightUnit = n;
        this.deleteButtonClickListener = WeightMeasurementsAdapter$$Lambda$1.lambdaFactory$(this);
    }

    private String createNoDataString(Resources resources, int n) {
        return String.format(resources.getString(n), resources.getString(2131362495)).replaceAll("%", " ");
    }

    public static long extractDate(View object) {
        if ((object = (ViewHolder)object.getTag()) != null && object.measurementDate != null) {
            return object.measurementDate.getTime();
        }
        return -1L;
    }

    private int getBmiValueColor(double d) {
        return COLORS[this.getValueIndex(d)];
    }

    private int getValueIndex(double d) {
        if (d < 18.5) {
            return 0;
        }
        if (d < 25.0) {
            return 1;
        }
        if (d < 30.0) {
            return 2;
        }
        return 3;
    }

    private void setDate(ViewHolder viewHolder, WeightMeasurement weightMeasurement) {
        if (weightMeasurement.measureDate != null) {
            viewHolder.date.setText((CharSequence)DateUtils.formatDateInLocaleAndWithTodayAndYesterday(this.context, weightMeasurement.measureDate));
            viewHolder.time.setText((CharSequence)this.timeFormat.format(weightMeasurement.measureDate));
            viewHolder.measurementDate = weightMeasurement.measureDate;
        }
    }

    private void setFatPercentage(ViewHolder viewHolder, Resources resources, Float f) {
        if (f != null && MetricUtils.isPercentageValid(f.floatValue())) {
            viewHolder.fat.setText((CharSequence)resources.getString(2131362238, new Object[]{Convert.floatToString(f, 0)}));
            return;
        }
        viewHolder.fat.setText((CharSequence)this.createNoDataString(resources, 2131362238));
    }

    private void setMusclePercentage(ViewHolder viewHolder, Resources resources, Float f) {
        if (f != null && MetricUtils.isPercentageValid(f.floatValue())) {
            viewHolder.muscle.setText((CharSequence)resources.getString(2131362290, new Object[]{Convert.floatToString(f, 0)}));
            return;
        }
        viewHolder.muscle.setText((CharSequence)this.createNoDataString(resources, 2131362290));
    }

    private void setSourceIcon(ImageView imageView, Integer n) {
        if (n != null) {
            imageView.setImageResource(n.intValue());
            imageView.setVisibility(0);
            return;
        }
        imageView.setVisibility(4);
        imageView.setImageBitmap(null);
    }

    private void setSourceIcon(ViewHolder viewHolder, Integer n) {
        if (n != null) {
            switch (n) {
                default: {
                    return;
                }
                case 2: {
                    this.setSourceIcon(viewHolder.source, (Integer)2130837825);
                    return;
                }
                case 1: {
                    this.setSourceIcon(viewHolder.source, (Integer)2130837776);
                    return;
                }
                case 0: {
                    this.setSourceIcon(viewHolder.source, null);
                    return;
                }
                case 3: {
                    this.setSourceIcon(viewHolder.source, (Integer)2130837775);
                    return;
                }
                case 4: 
            }
            this.setSourceIcon(viewHolder.source, (Integer)2130837802);
            return;
        }
        this.setSourceIcon(viewHolder.source, null);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    public void bindView(View view, Context object, Cursor object2) {
        float f;
        void var3_12;
        ViewHolder viewHolder = (ViewHolder)view.getTag();
        WeightMeasurement weightMeasurement = MeasurementHelper.Weight.parseMeasurement((Cursor)var3_12);
        Resources resources = object.getResources();
        if (weightMeasurement.height != null && weightMeasurement.weight != null && weightMeasurement.weight.floatValue() / (float)weightMeasurement.height.intValue() != Float.POSITIVE_INFINITY) {
            f = BodyComposition.bmi(weightMeasurement.weight.floatValue(), weightMeasurement.height);
            viewHolder.bmiLevelIndicator.setVisibility(0);
            viewHolder.bmiLevelIndicator.setColorFilter(resources.getColor(this.getBmiValueColor(f)));
            viewHolder.bmi.setText((CharSequence)(object.getString(2131362180) + " " + Convert.floatToString(Float.valueOf(f), 1)));
        } else {
            String string2 = resources.getString(2131362495);
            viewHolder.bmi.setText((CharSequence)String.format(string2, new Object[0]).replaceAll("%", " "));
            viewHolder.bmiLevelIndicator.setVisibility(4);
        }
        if (weightMeasurement.measureDate != null) {
            this.setDate(viewHolder, weightMeasurement);
        }
        f = MetricUtils.convertWeight(0, this.weightUnit, weightMeasurement.weight.floatValue());
        String string3 = resources.getString(MetricUtils.getWeightUnitNameRes(this.weightUnit));
        viewHolder.weight.setText((CharSequence)resources.getString(2131362549, new Object[]{Float.valueOf(f), string3}));
        if (weightMeasurement.measurementSource == 4) {
            void var2_8;
            void var2_6;
            if (weightMeasurement.muscle != null) {
                Float f2 = Float.valueOf(weightMeasurement.muscle.intValue());
            } else {
                Object var2_10 = null;
            }
            this.setMusclePercentage(viewHolder, resources, (Float)var2_6);
            if (weightMeasurement.fat != null) {
                Float f3 = Float.valueOf(weightMeasurement.fat.intValue());
            } else {
                Object var2_11 = null;
            }
            this.setFatPercentage(viewHolder, resources, (Float)var2_8);
        } else if (weightMeasurement.fat != null && MetricUtils.isPercentageValid(weightMeasurement.fat.intValue())) {
            viewHolder.fat.setText((CharSequence)resources.getString(2131362238, new Object[]{Utils.formatInteger(weightMeasurement.fat)}));
            if (weightMeasurement.muscle != null) {
                this.setMusclePercentage(viewHolder, resources, Float.valueOf(weightMeasurement.muscle.intValue()));
            } else {
                this.setMusclePercentage(viewHolder, resources, QardioBaseUtils.musclePercentage(weightMeasurement));
            }
        } else {
            viewHolder.fat.setText((CharSequence)this.createNoDataString(resources, 2131362238));
            this.setMusclePercentage(viewHolder, resources, null);
        }
        this.setSourceIcon(viewHolder, weightMeasurement.measurementSource);
        int n = TextUtils.isEmpty((CharSequence)weightMeasurement.note) ? 4 : 0;
        viewHolder.note.setVisibility(n);
        viewHolder.deleteButton.setTag((Object)weightMeasurement.measureDate);
        viewHolder.deleteButton.setOnClickListener(this.deleteButtonClickListener);
        BackPanelListViewHelper.hideBackPanel(view);
    }

    /* synthetic */ void lambda$new$0(View object) {
        if ((object = (Date)object.getTag()) != null && this.onDeleteListener != null) {
            this.onDeleteListener.onDeleteMeasurement(((Date)object).getTime());
        }
    }

    public View newView(Context context, Cursor object, ViewGroup viewGroup) {
        context = BackPanelListViewHelper.wrapListViewItem(context, 2130968855, 2130968679, viewGroup);
        object = new ViewHolder();
        object.date = (TextView)context.findViewById(2131821414);
        object.time = (TextView)context.findViewById(2131821415);
        object.weight = (TextView)context.findViewById(2131821417);
        object.bmi = (TextView)context.findViewById(2131821418);
        object.fat = (TextView)context.findViewById(2131821422);
        object.muscle = (TextView)context.findViewById(2131821421);
        object.note = (ImageView)context.findViewById(2131821424);
        object.deleteButton = (ImageView)context.findViewById(2131821017);
        object.source = (ImageView)context.findViewById(2131821423);
        object.bmiLevelIndicator = (ImageView)context.findViewById(2131821416);
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
        public void onDeleteMeasurement(long var1);
    }

    private static class ViewHolder {
        TextView bmi;
        ImageView bmiLevelIndicator;
        TextView date;
        ImageView deleteButton;
        TextView fat;
        Date measurementDate;
        TextView muscle;
        ImageView note;
        ImageView source;
        TextView time;
        TextView weight;

        private ViewHolder() {
        }
    }

}

