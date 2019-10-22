/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.graphics.Color
 *  android.graphics.DashPathEffect
 *  android.graphics.Typeface
 */
package com.github.mikephil.charting.data;

import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Typeface;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.formatter.IValueFormatter;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.utils.Utils;
import java.util.ArrayList;
import java.util.List;

public abstract class BaseDataSet<T extends Entry>
implements IDataSet<T> {
    protected YAxis.AxisDependency mAxisDependency = YAxis.AxisDependency.LEFT;
    protected List<Integer> mColors = null;
    protected boolean mDrawValues = true;
    private Legend.LegendForm mForm = Legend.LegendForm.DEFAULT;
    private DashPathEffect mFormLineDashEffect = null;
    private float mFormLineWidth = Float.NaN;
    private float mFormSize = Float.NaN;
    protected boolean mHighlightEnabled = true;
    private String mLabel = "DataSet";
    protected List<Integer> mValueColors = null;
    protected transient IValueFormatter mValueFormatter;
    protected float mValueTextSize = 17.0f;
    protected Typeface mValueTypeface;
    protected boolean mVisible = true;

    public BaseDataSet() {
        this.mColors = new ArrayList<Integer>();
        this.mValueColors = new ArrayList<Integer>();
        this.mColors.add(Color.rgb((int)140, (int)234, (int)255));
        this.mValueColors.add(-16777216);
    }

    public BaseDataSet(String string2) {
        this();
        this.mLabel = string2;
    }

    @Override
    public YAxis.AxisDependency getAxisDependency() {
        return this.mAxisDependency;
    }

    @Override
    public int getColor() {
        return this.mColors.get(0);
    }

    @Override
    public int getColor(int n) {
        return this.mColors.get(n % this.mColors.size());
    }

    @Override
    public List<Integer> getColors() {
        return this.mColors;
    }

    @Override
    public Legend.LegendForm getForm() {
        return this.mForm;
    }

    @Override
    public DashPathEffect getFormLineDashEffect() {
        return this.mFormLineDashEffect;
    }

    @Override
    public float getFormLineWidth() {
        return this.mFormLineWidth;
    }

    @Override
    public float getFormSize() {
        return this.mFormSize;
    }

    @Override
    public String getLabel() {
        return this.mLabel;
    }

    @Override
    public IValueFormatter getValueFormatter() {
        if (this.needsFormatter()) {
            return Utils.getDefaultValueFormatter();
        }
        return this.mValueFormatter;
    }

    @Override
    public int getValueTextColor(int n) {
        return this.mValueColors.get(n % this.mValueColors.size());
    }

    @Override
    public float getValueTextSize() {
        return this.mValueTextSize;
    }

    @Override
    public Typeface getValueTypeface() {
        return this.mValueTypeface;
    }

    @Override
    public boolean isDrawValuesEnabled() {
        return this.mDrawValues;
    }

    @Override
    public boolean isHighlightEnabled() {
        return this.mHighlightEnabled;
    }

    @Override
    public boolean isVisible() {
        return this.mVisible;
    }

    @Override
    public boolean needsFormatter() {
        return this.mValueFormatter == null;
    }

    public void resetColors() {
        if (this.mColors == null) {
            this.mColors = new ArrayList<Integer>();
        }
        this.mColors.clear();
    }

    public void setColor(int n) {
        this.resetColors();
        this.mColors.add(n);
    }

    public void setDrawValues(boolean bl) {
        this.mDrawValues = bl;
    }

    @Override
    public void setHighlightEnabled(boolean bl) {
        this.mHighlightEnabled = bl;
    }

    @Override
    public void setValueFormatter(IValueFormatter iValueFormatter) {
        if (iValueFormatter == null) {
            return;
        }
        this.mValueFormatter = iValueFormatter;
    }
}

