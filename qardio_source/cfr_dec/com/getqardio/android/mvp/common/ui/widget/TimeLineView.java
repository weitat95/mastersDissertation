/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Canvas
 *  android.graphics.Paint
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.view.View
 *  android.view.View$MeasureSpec
 */
package com.getqardio.android.mvp.common.ui.widget;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.support.v4.content.ContextCompat;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import com.getqardio.android.googlefit.ActivityDataBucket;
import com.getqardio.android.googlefit.GoogleFitApiImpl;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class TimeLineView
extends View {
    private Paint activityLinePaint;
    private Paint activityMinutesText;
    private Paint activityTimePaintText;
    private float baseTimelineHeight;
    private Context context;
    private float heightForOneMinuteDP;
    private Paint linePaint;
    private float roundedEdgeForLineRadiusDP;
    private float startEndPointForLineDP;
    private float startPointForMinutexTextDP;
    private float startPointForTimeTextDP;
    private float startXPointForLineDP;
    private float timelineWidthDP;
    private List<ActivityDataBucket> todayActivities = new ArrayList<ActivityDataBucket>();

    public TimeLineView(Context context) {
        super(context);
        this.context = context;
        this.timelineWidthDP = TimeLineView.pXFromDp(context, 15.0f);
        this.startXPointForLineDP = TimeLineView.pXFromDp(context, 100.0f);
        this.startEndPointForLineDP = TimeLineView.pXFromDp(context, 0.0f);
        this.startPointForTimeTextDP = TimeLineView.pXFromDp(context, 25.0f);
        this.startPointForMinutexTextDP = TimeLineView.pXFromDp(context, 120.0f);
        this.roundedEdgeForLineRadiusDP = TimeLineView.pXFromDp(context, 7.5f);
        this.heightForOneMinuteDP = TimeLineView.pXFromDp(context, 3.0f);
        this.init();
    }

    public TimeLineView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.context = context;
        this.timelineWidthDP = TimeLineView.pXFromDp(context, 15.0f);
        this.startXPointForLineDP = TimeLineView.pXFromDp(context, 100.0f);
        this.startEndPointForLineDP = TimeLineView.pXFromDp(context, 0.0f);
        this.startPointForTimeTextDP = TimeLineView.pXFromDp(context, 25.0f);
        this.startPointForMinutexTextDP = TimeLineView.pXFromDp(context, 120.0f);
        this.roundedEdgeForLineRadiusDP = TimeLineView.pXFromDp(context, 7.5f);
        this.heightForOneMinuteDP = TimeLineView.pXFromDp(context, 3.0f);
        this.init();
    }

    private void drawActivityLine(Canvas canvas, float f, float f2) {
        canvas.drawLine(this.startXPointForLineDP, f + this.roundedEdgeForLineRadiusDP, this.startXPointForLineDP, f2 - this.roundedEdgeForLineRadiusDP, this.activityLinePaint);
        canvas.drawCircle(this.startXPointForLineDP, f2 - this.roundedEdgeForLineRadiusDP, this.roundedEdgeForLineRadiusDP, this.activityLinePaint);
        canvas.drawCircle(this.startXPointForLineDP, this.roundedEdgeForLineRadiusDP + f, this.roundedEdgeForLineRadiusDP, this.activityLinePaint);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawActivityLines(Canvas canvas) {
        float f = 0.0f;
        float f2 = 0.0f;
        Iterator<ActivityDataBucket> iterator = this.todayActivities.iterator();
        do {
            if (!iterator.hasNext()) {
                Timber.d("Sanjeev: endpoint = " + f2, new Object[0]);
                return;
            }
            ActivityDataBucket activityDataBucket = iterator.next();
            if (!GoogleFitApiImpl.isSupportedActivity(activityDataBucket.getActivityType())) {
                f2 = (float)TimeUnit.MILLISECONDS.toMinutes(activityDataBucket.getDuration()) * (this.heightForOneMinuteDP / 3.0f) < TimeLineView.pXFromDp(this.getContext(), 20.0f) ? (f2 += TimeLineView.pXFromDp(this.getContext(), 20.0f)) : ((float)TimeUnit.MILLISECONDS.toMinutes(activityDataBucket.getDuration()) * (this.heightForOneMinuteDP / 3.0f) > TimeLineView.pXFromDp(this.getContext(), 30.0f) ? (f2 += TimeLineView.pXFromDp(this.getContext(), 30.0f)) : (f2 += (float)TimeUnit.MILLISECONDS.toMinutes(activityDataBucket.getDuration()) * (this.heightForOneMinuteDP / 3.0f)));
            } else {
                f2 = (float)TimeUnit.MILLISECONDS.toMinutes(activityDataBucket.getDuration()) * this.heightForOneMinuteDP < TimeLineView.pXFromDp(this.getContext(), 8.0f) ? (f2 += TimeLineView.pXFromDp(this.getContext(), 8.0f)) : (f2 += (float)TimeUnit.MILLISECONDS.toMinutes(activityDataBucket.getDuration()) * this.heightForOneMinuteDP + TimeLineView.pXFromDp(this.getContext(), 8.0f));
                this.drawActivityLine(canvas, f, f2);
                this.drawActivityTimeText(canvas, f, f2, this.getStringTimeFromTimestamp(activityDataBucket.getEndTimeStamp()), this.getStringTimeFromTimestamp(activityDataBucket.getStartTimeStamp()));
                this.drawActivityMinutesText(canvas, f, f2, (int)TimeUnit.MILLISECONDS.toMinutes(activityDataBucket.getDuration()), activityDataBucket.getActivityType());
            }
            f = f2;
        } while (true);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void drawActivityMinutesText(Canvas canvas, float f, float f2, int n, String string2) {
        int n2 = -1;
        switch (string2.hashCode()) {
            case 1118815609: {
                if (!string2.equals("walking")) break;
                n2 = 0;
                break;
            }
            case 840983793: {
                if (!string2.equals("walking.treadmill")) break;
                n2 = 1;
                break;
            }
            case 1553049696: {
                if (!string2.equals("walking.stroller")) break;
                n2 = 2;
                break;
            }
            case -825486814: {
                if (!string2.equals("walking.nordic")) break;
                n2 = 3;
                break;
            }
            case 1550783935: {
                if (!string2.equals("running")) break;
                n2 = 4;
                break;
            }
            case 245975982: {
                if (!string2.equals("running.jogging")) break;
                n2 = 5;
                break;
            }
            case -1752918601: {
                if (!string2.equals("running.treadmill")) break;
                n2 = 6;
                break;
            }
            case -1389048738: {
                if (!string2.equals("biking")) break;
                n2 = 7;
                break;
            }
            case 856373247: {
                if (!string2.equals("biking.hand")) break;
                n2 = 8;
                break;
            }
            case -1017951715: {
                if (!string2.equals("biking.mountain")) break;
                n2 = 9;
                break;
            }
            case 856684208: {
                if (!string2.equals("biking.road")) break;
                n2 = 10;
                break;
            }
            case 1392758662: {
                if (!string2.equals("biking.spinning")) break;
                n2 = 11;
                break;
            }
            case -393738172: {
                if (!string2.equals("biking.stationary")) break;
                n2 = 12;
                break;
            }
            case -698011684: {
                if (!string2.equals("biking.utility")) break;
                n2 = 13;
                break;
            }
        }
        switch (n2) {
            default: {
                string2 = this.context.getString(2131362169);
                break;
            }
            case 0: 
            case 1: 
            case 2: 
            case 3: {
                string2 = this.context.getString(2131362169);
                break;
            }
            case 4: 
            case 5: 
            case 6: {
                string2 = this.context.getString(2131362168);
                break;
            }
            case 7: 
            case 8: 
            case 9: 
            case 10: 
            case 11: 
            case 12: 
            case 13: {
                string2 = this.context.getString(2131362161);
            }
        }
        String string3 = n + " " + this.context.getString(2131362098) + " " + string2;
        if (n <= 1) {
            string3 = " < 1 " + this.context.getString(2131362097) + " " + string2;
        }
        canvas.drawText(string3, this.startPointForMinutexTextDP, (f + f2) / 2.0f + TimeLineView.pXFromDp(this.context, 7.0f), this.activityMinutesText);
    }

    private void drawActivityTimeText(Canvas canvas, float f, float f2, String string2, String string3) {
        canvas.drawText(string2, this.startPointForTimeTextDP, TimeLineView.pXFromDp(this.context, 5.0f) + f, this.activityTimePaintText);
        canvas.drawText(string3, this.startPointForTimeTextDP, TimeLineView.pXFromDp(this.context, 10.0f) + f2, this.activityTimePaintText);
    }

    private void drawBaseLine(Canvas canvas, float f) {
        Timber.d("Sanjeev: base endpoint = " + f, new Object[0]);
        canvas.drawLine(this.startXPointForLineDP, f, this.startXPointForLineDP, this.startEndPointForLineDP + this.roundedEdgeForLineRadiusDP, this.linePaint);
        canvas.drawCircle(this.startXPointForLineDP, this.startEndPointForLineDP + this.roundedEdgeForLineRadiusDP, this.roundedEdgeForLineRadiusDP, this.linePaint);
        canvas.drawCircle(this.startXPointForLineDP, f, this.roundedEdgeForLineRadiusDP, this.linePaint);
    }

    private String getStringTimeFromTimestamp(long l) {
        Object object = new Date(l);
        Object object2 = Calendar.getInstance();
        ((Calendar)object2).setTime((Date)object);
        int n = ((Calendar)object2).get(11);
        int n2 = ((Calendar)object2).get(12);
        object2 = String.valueOf(n);
        object = String.valueOf(n2);
        if (n2 < 10) {
            object = "0" + n2;
        }
        if (n < 10) {
            object2 = "0" + n;
        }
        return (String)object2 + ":" + (String)object;
    }

    private void init() {
        this.initBaseLine();
        this.initActivityLine();
        this.initTimeText();
        this.initMinutesText();
        this.initGapBetweenActivities();
    }

    private void initActivityLine() {
        this.activityLinePaint = new Paint();
        int n = ContextCompat.getColor(this.context, 2131689548);
        this.activityLinePaint.setColor(n);
        this.activityLinePaint.setStrokeWidth(this.timelineWidthDP);
        this.activityLinePaint.setFlags(1);
    }

    private void initBaseLine() {
        this.linePaint = new Paint();
        int n = ContextCompat.getColor(this.context, 2131689554);
        this.linePaint.setColor(n);
        this.linePaint.setStrokeWidth(this.timelineWidthDP);
        this.linePaint.setFlags(1);
    }

    private void initGapBetweenActivities() {
        Paint paint = new Paint();
        paint.setColor(ContextCompat.getColor(this.context, 2131689621));
        paint.setStrokeWidth(this.timelineWidthDP);
        paint.setFlags(1);
    }

    private void initMinutesText() {
        this.activityMinutesText = new Paint();
        int n = ContextCompat.getColor(this.context, 2131689556);
        this.activityMinutesText.setColor(n);
        this.activityMinutesText.setTextSize(TimeLineView.pXFromDp(this.context, 14.0f));
    }

    private void initTimeText() {
        this.activityTimePaintText = new Paint();
        int n = ContextCompat.getColor(this.context, 2131689554);
        this.activityTimePaintText.setColor(n);
        this.activityTimePaintText.setTextSize(TimeLineView.pXFromDp(this.context, 11.0f));
    }

    /*
     * Enabled aggressive block sorting
     */
    private List<ActivityDataBucket> joinConsecutiveMinutes(List<ActivityDataBucket> list) {
        if (list.isEmpty()) return list;
        if (list.size() < 2) {
            return list;
        }
        LinkedList<ActivityDataBucket> linkedList = new LinkedList<ActivityDataBucket>();
        linkedList.add(list.get(0));
        int n = 1;
        do {
            List<ActivityDataBucket> list2 = linkedList;
            if (n >= list.size()) return list2;
            long l = list.get(n).getEndTimeStamp() - linkedList.getLast().getStartTimeStamp();
            Timber.d("diff = " + l, new Object[0]);
            if (l < 60000L && list.get(n).getActivityType().equals(linkedList.getLast().getActivityType())) {
                linkedList.getLast().setDuration(linkedList.getLast().getDuration() + list.get(n).getDuration());
                linkedList.getLast().setStartTimeStamp(list.get(n).getStartTimeStamp());
            } else {
                linkedList.add(list.get(n));
            }
            ++n;
        } while (true);
    }

    private static float pXFromDp(Context context, float f) {
        return (float)context.getResources().getDisplayMetrics().densityDpi / 160.0f * f;
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        this.drawBaseLine(canvas, this.baseTimelineHeight);
        this.drawActivityLines(canvas);
    }

    protected void onMeasure(int n, int n2) {
        this.setMeasuredDimension(View.MeasureSpec.getSize((int)n), View.MeasureSpec.makeMeasureSpec((int)((int)this.baseTimelineHeight + 200), (int)1073741824));
    }

    public void setActivitiesForTimeline(List<ActivityDataBucket> object) {
        Collections.reverse(object);
        Object object2 = new SimpleDateFormat("HH:mm:ss");
        Iterator<ActivityDataBucket> iterator = object.iterator();
        while (iterator.hasNext()) {
            ActivityDataBucket activityDataBucket = iterator.next();
            Timber.d("Sanjeev: start = " + ((Format)object2).format(activityDataBucket.getStartTimeStamp()) + " end = " + ((Format)object2).format(activityDataBucket.getEndTimeStamp()) + " activity = " + activityDataBucket.getActivityType() + " duration = " + TimeUnit.MILLISECONDS.toMinutes(activityDataBucket.getDuration()), new Object[0]);
        }
        Timber.d("After join.......", new Object[0]);
        this.todayActivities = this.joinConsecutiveMinutes((List<ActivityDataBucket>)object);
        for (ActivityDataBucket activityDataBucket : this.todayActivities) {
            Timber.d("Sanjeev: start = " + ((Format)object2).format(activityDataBucket.getStartTimeStamp()) + " end = " + ((Format)object2).format(activityDataBucket.getEndTimeStamp()) + " activity = " + activityDataBucket.getActivityType() + " duration = " + TimeUnit.MILLISECONDS.toMinutes(activityDataBucket.getDuration()), new Object[0]);
        }
        float f = 0.0f;
        object = object.iterator();
        while (object.hasNext()) {
            object2 = (ActivityDataBucket)object.next();
            if (!GoogleFitApiImpl.isSupportedActivity(((ActivityDataBucket)object2).getActivityType())) {
                if ((float)TimeUnit.MILLISECONDS.toMinutes(((ActivityDataBucket)object2).getDuration()) * (this.heightForOneMinuteDP / 3.0f) < TimeLineView.pXFromDp(this.getContext(), 20.0f)) {
                    f += TimeLineView.pXFromDp(this.getContext(), 20.0f);
                    continue;
                }
                if ((float)TimeUnit.MILLISECONDS.toMinutes(((ActivityDataBucket)object2).getDuration()) * (this.heightForOneMinuteDP / 3.0f) > TimeLineView.pXFromDp(this.getContext(), 30.0f)) {
                    f += TimeLineView.pXFromDp(this.getContext(), 30.0f);
                    continue;
                }
                f += (float)TimeUnit.MILLISECONDS.toMinutes(((ActivityDataBucket)object2).getDuration()) * (this.heightForOneMinuteDP / 3.0f);
                continue;
            }
            if ((float)TimeUnit.MILLISECONDS.toMinutes(((ActivityDataBucket)object2).getDuration()) * this.heightForOneMinuteDP < TimeLineView.pXFromDp(this.getContext(), 8.0f)) {
                f += TimeLineView.pXFromDp(this.getContext(), 8.0f);
                continue;
            }
            f += (float)TimeUnit.MILLISECONDS.toMinutes(((ActivityDataBucket)object2).getDuration()) * this.heightForOneMinuteDP + TimeLineView.pXFromDp(this.getContext(), 8.0f);
        }
        this.baseTimelineHeight = f;
    }

    public void setTotalMinutesForBaseTimeLine(long l) {
        this.requestLayout();
    }
}

