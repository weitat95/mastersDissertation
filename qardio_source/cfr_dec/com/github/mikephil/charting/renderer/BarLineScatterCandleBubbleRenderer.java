/*
 * Decompiled with CFR 0.147.
 */
package com.github.mikephil.charting.renderer;

import com.github.mikephil.charting.animation.ChartAnimator;
import com.github.mikephil.charting.data.DataSet;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.interfaces.dataprovider.BarLineScatterCandleBubbleDataProvider;
import com.github.mikephil.charting.interfaces.datasets.IBarLineScatterCandleBubbleDataSet;
import com.github.mikephil.charting.interfaces.datasets.IDataSet;
import com.github.mikephil.charting.renderer.DataRenderer;
import com.github.mikephil.charting.utils.ViewPortHandler;

public abstract class BarLineScatterCandleBubbleRenderer
extends DataRenderer {
    protected XBounds mXBounds = new XBounds();

    public BarLineScatterCandleBubbleRenderer(ChartAnimator chartAnimator, ViewPortHandler viewPortHandler) {
        super(chartAnimator, viewPortHandler);
    }

    /*
     * Enabled aggressive block sorting
     */
    protected boolean isInBoundsX(Entry entry, IBarLineScatterCandleBubbleDataSet iBarLineScatterCandleBubbleDataSet) {
        block3: {
            block2: {
                if (entry == null) break block2;
                float f = iBarLineScatterCandleBubbleDataSet.getEntryIndex(entry);
                if (entry != null && !(f >= (float)iBarLineScatterCandleBubbleDataSet.getEntryCount() * this.mAnimator.getPhaseX())) break block3;
            }
            return false;
        }
        return true;
    }

    protected boolean shouldDrawValues(IDataSet iDataSet) {
        return iDataSet.isVisible() && iDataSet.isDrawValuesEnabled();
    }

    protected class XBounds {
        public int max;
        public int min;
        public int range;

        protected XBounds() {
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        public void set(BarLineScatterCandleBubbleDataProvider barLineScatterCandleBubbleDataProvider, IBarLineScatterCandleBubbleDataSet iBarLineScatterCandleBubbleDataSet) {
            void var2_3;
            int n = 0;
            float f = Math.max(0.0f, Math.min(1.0f, BarLineScatterCandleBubbleRenderer.this.mAnimator.getPhaseX()));
            float f2 = barLineScatterCandleBubbleDataProvider.getLowestVisibleX();
            float f3 = barLineScatterCandleBubbleDataProvider.getHighestVisibleX();
            Object t = var2_3.getEntryForXValue(f2, Float.NaN, DataSet.Rounding.DOWN);
            Object t2 = var2_3.getEntryForXValue(f3, Float.NaN, DataSet.Rounding.UP);
            int n2 = t == null ? 0 : var2_3.getEntryIndex(t);
            this.min = n2;
            n2 = t2 == null ? n : var2_3.getEntryIndex(t2);
            this.max = n2;
            this.range = (int)((float)(this.max - this.min) * f);
        }
    }

}

