/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnClickListener
 *  android.text.InputFilter
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.widget.EditText
 *  android.widget.NumberPicker
 *  android.widget.NumberPicker$Formatter
 *  android.widget.NumberPicker$OnValueChangeListener
 *  android.widget.TextView
 */
package com.getqardio.android.ui.dialog;

import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;
import android.text.InputFilter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.TextView;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.Utils;
import java.lang.reflect.Field;

public class WeightPickerDialog
extends AlertDialog
implements DialogInterface.OnClickListener {
    private static int[] UNITS = new int[]{0, 1, 2};
    private boolean canChooseWeightUnit = true;
    private float currentWeightInKg = -1.0f;
    private int currentWeightUnit = -1;
    private NumberPicker fractionalPartPicker;
    private NumberPicker integerPartPicker;
    private NumberPicker unitsPicker;
    private OnWeightSelectedListener weightSelectedListener;

    public WeightPickerDialog(Context context, int n, OnWeightSelectedListener onWeightSelectedListener) {
        super(context);
        this.weightSelectedListener = onWeightSelectedListener;
        this.canChooseWeightUnit = false;
        this.currentWeightUnit = n;
        this.init();
    }

    public WeightPickerDialog(Context context, OnWeightSelectedListener onWeightSelectedListener) {
        super(context);
        this.weightSelectedListener = onWeightSelectedListener;
        this.canChooseWeightUnit = true;
        this.init();
    }

    private void adjustWeightBoundsForUnits(int n) {
        float f = MetricUtils.convertWeight(0, n, 10.0f);
        float f2 = MetricUtils.convertWeight(0, n, 200.0f);
        this.integerPartPicker.setMinValue((int)f);
        this.integerPartPicker.setMaxValue((int)f2);
    }

    private void doFixForNumberPickers() {
        try {
            Field field = NumberPicker.class.getDeclaredField("mInputText");
            field.setAccessible(true);
            ((EditText)field.get((Object)this.unitsPicker)).setFilters(new InputFilter[0]);
            field = (EditText)field.get((Object)this.fractionalPartPicker);
            field.setTextDirection(5);
            field.setFilters(new InputFilter[0]);
            return;
        }
        catch (IllegalAccessException illegalAccessException) {
            return;
        }
        catch (NoSuchFieldException noSuchFieldException) {
            return;
        }
    }

    private int getWeightUnitPosition(int n) {
        int n2 = 0;
        int[] arrn = UNITS;
        int n3 = arrn.length;
        for (int i = 0; i < n3; ++i) {
            if (arrn[i] == n) {
                return n2;
            }
            ++n2;
        }
        return -1;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void init() {
        View view = LayoutInflater.from((Context)this.getContext()).inflate(2130968857, null);
        this.setView(view);
        this.setTitle(2131362413);
        this.setButton(-1, this.getContext().getString(17039370), this);
        this.setButton(-2, this.getContext().getString(17039360), this);
        this.integerPartPicker = (NumberPicker)view.findViewById(2131821443);
        this.integerPartPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

            public void onValueChange(NumberPicker numberPicker, int n, int n2) {
                WeightPickerDialog.this.onWeightValueChanged();
            }
        });
        this.fractionalPartPicker = (NumberPicker)view.findViewById(2131821444);
        this.fractionalPartPicker.setMinValue(0);
        this.fractionalPartPicker.setMaxValue(9);
        this.fractionalPartPicker.setWrapSelectorWheel(true);
        this.fractionalPartPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onValueChange(NumberPicker numberPicker, int n, int n2) {
                if (n == WeightPickerDialog.this.fractionalPartPicker.getMaxValue() && n2 == WeightPickerDialog.this.fractionalPartPicker.getMinValue()) {
                    WeightPickerDialog.this.integerPartPicker.setValue(Math.min(WeightPickerDialog.this.integerPartPicker.getMaxValue(), WeightPickerDialog.this.integerPartPicker.getValue() + 1));
                } else if (n == WeightPickerDialog.this.fractionalPartPicker.getMinValue() && n2 == WeightPickerDialog.this.fractionalPartPicker.getMaxValue()) {
                    WeightPickerDialog.this.integerPartPicker.setValue(Math.max(WeightPickerDialog.this.integerPartPicker.getMinValue(), WeightPickerDialog.this.integerPartPicker.getValue() - 1));
                }
                WeightPickerDialog.this.onWeightValueChanged();
            }
        });
        this.fractionalPartPicker.setFormatter(new NumberPicker.Formatter(){

            public String format(int n) {
                return String.format(Utils.getLocale(), ".%d", n);
            }
        });
        TextView textView = (TextView)view.findViewById(2131821446);
        this.unitsPicker = (NumberPicker)view.findViewById(2131821445);
        if (this.canChooseWeightUnit) {
            this.unitsPicker.setVisibility(0);
            textView.setVisibility(8);
            this.unitsPicker.setMinValue(0);
            this.unitsPicker.setMaxValue(UNITS.length - 1);
            this.unitsPicker.setFormatter(new NumberPicker.Formatter(){

                public String format(int n) {
                    return WeightPickerDialog.this.getContext().getString(MetricUtils.getWeightUnitNameRes(UNITS[n]));
                }
            });
            this.unitsPicker.setOnValueChangedListener(new NumberPicker.OnValueChangeListener(){

                public void onValueChange(NumberPicker numberPicker, int n, int n2) {
                    WeightPickerDialog.this.onWeightUnitChanged(UNITS[n2]);
                }
            });
        } else {
            this.unitsPicker.setVisibility(8);
            this.adjustWeightBoundsForUnits(this.currentWeightUnit);
            textView.setVisibility(0);
            textView.setText((CharSequence)this.getContext().getString(MetricUtils.getWeightUnitNameRes(this.currentWeightUnit)));
        }
        this.doFixForNumberPickers();
        this.setWeight(0, 70.0f);
    }

    private void onWeightUnitChanged(int n) {
        float f = MetricUtils.convertWeight(0, n, this.currentWeightInKg);
        float f2 = MetricUtils.convertWeight(0, n, 10.0f);
        float f3 = MetricUtils.convertWeight(0, n, 200.0f);
        this.setWeight(n, Math.min(Math.max(f, f2), f3));
    }

    private void onWeightValueChanged() {
        float f = this.integerPartPicker.getValue();
        float f2 = (float)this.fractionalPartPicker.getValue() * 1.0f / 10.0f;
        this.currentWeightInKg = MetricUtils.convertWeight(this.currentWeightUnit, 0, f + f2);
    }

    private void showWeight() {
        float f = Utils.round(Float.valueOf(MetricUtils.convertWeight(0, this.currentWeightUnit, this.currentWeightInKg)), 1);
        int n = (int)f;
        float f2 = n;
        this.integerPartPicker.setValue(n);
        this.fractionalPartPicker.setValue((int)(10.0f * (f - f2)));
    }

    public void onClick(DialogInterface dialogInterface, int n) {
        if (n == -1) {
            if (this.weightSelectedListener != null) {
                float f = MetricUtils.convertWeight(0, this.currentWeightUnit, this.currentWeightInKg);
                this.weightSelectedListener.onWeightSelected(f, this.currentWeightUnit);
            }
            return;
        }
        this.dismiss();
    }

    public void setWeight(int n, float f) {
        if (this.canChooseWeightUnit) {
            int n2 = this.getWeightUnitPosition(n);
            int n3 = this.getWeightUnitPosition(this.currentWeightUnit);
            if (this.currentWeightUnit < 0 || n2 != n3) {
                this.unitsPicker.setValue(n2);
                this.adjustWeightBoundsForUnits(n);
            }
            this.currentWeightUnit = n;
        }
        this.currentWeightInKg = MetricUtils.convertWeight(n, 0, f);
        this.showWeight();
    }

    public static interface OnWeightSelectedListener {
        public void onWeightSelected(float var1, int var2);
    }

}

