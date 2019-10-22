/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.highlight;

import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarLineScatterCandleBubbleData;
import com.github.mikephil.charting.data.CombinedData;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.highlight.BarHighlighter;
import com.github.mikephil.charting.highlight.ChartHighlighter;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.highlight.IHighlighter;
import com.github.mikephil.charting.interfaces.dataprovider.BarDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.dataprovider.CombinedDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import java.util.List;

public class CombinedHighlighter
extends ChartHighlighter<CombinedDataProvider>
implements IHighlighter {
    protected BarHighlighter barHighlighter;

    /*
     * Enabled aggressive block sorting
     */
    public CombinedHighlighter(CombinedDataProvider object, BarDataProvider barDataProvider) {
        super(object);
        object = barDataProvider.getBarData() == null ? null : new BarHighlighter(barDataProvider);
        this.barHighlighter = object;
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     * Lifted jumps to return sites
     */
    @Override
    protected List<Highlight> getHighlightsAtXValue(float var1_1, float var2_2, float var3_3) {
        this.mHighlightBuffer.clear();
        var7_4 = ((CombinedDataProvider)this.mChart).getCombinedData().getAllData();
        var4_5 = 0;
        block0: do {
            block9: {
                if (var4_5 >= var7_4.size()) return this.mHighlightBuffer;
                var8_8 = var7_4.get(var4_5);
                if (this.barHighlighter == null || !(var8_8 instanceof BarData)) break block9;
                var8_8 = this.barHighlighter.getHighlight(var2_2, var3_3);
                if (var8_8 != null) {
                    var8_8.setDataIndex(var4_5);
                    this.mHighlightBuffer.add(var8_8);
                }
                ** GOTO lbl19
            }
            var5_6 = 0;
            var6_7 = var8_8.getDataSetCount();
            do {
                block10: {
                    if (var5_6 < var6_7) break block10;
lbl19:
                    // 2 sources
                    ++var4_5;
                    continue block0;
                }
                var8_8 = var7_4.get(var4_5).getDataSetByIndex(var5_6);
                if (var8_8.isHighlightEnabled()) {
                    for (Highlight var9_9 : this.buildHighlights((IDataSet)var8_8, var5_6, var1_1, DataSet.Rounding.CLOSEST)) {
                        var9_9.setDataIndex(var4_5);
                        this.mHighlightBuffer.add(var9_9);
                    }
                }
                ++var5_6;
            } while (true);
            break;
        } while (true);
    }
}

