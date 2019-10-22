/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.ContentResolver
 *  android.content.Context
 *  android.database.Cursor
 *  android.net.Uri
 *  android.text.TextUtils
 */
package com.getqardio.android.provider;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.text.TextUtils;
import com.getqardio.android.datamodel.Profile;
import com.getqardio.android.datamodel.WeightMeasurement;
import com.getqardio.android.mvp.friends_family.common.FFTypes;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserRepository;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.BpLastMeasurement;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.LastBpMeasurementData;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.provider.MeasurementHelper;
import com.getqardio.android.provider.WearableDataHelper$$Lambda$1;
import com.getqardio.android.provider.WearableDataHelper$$Lambda$2;
import com.getqardio.android.provider.WearableDataHelper$$Lambda$3;
import com.getqardio.android.provider.WearableDataHelper$$Lambda$4;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.ui.Convert;
import com.getqardio.shared.wearable.datamodel.BPMeasurementsDescription;
import com.getqardio.shared.wearable.datamodel.FollowingData;
import com.getqardio.shared.wearable.datamodel.WeightMeasurementsDescription;
import io.reactivex.Flowable;
import io.reactivex.Observable;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class WearableDataHelper {
    private static final SimpleDateFormat LAST_MEASUREMENT_DATE_FORMATTER;
    private static final long MONTH_IN_MILLIS;
    private static final String[] SYNC_USER_BP_MEASUREMENTS_PROJECTION;
    private static final long WEEK_IN_MILLIS;
    private IFollowUserRepository iFollowUserRepository;

    static {
        SYNC_USER_BP_MEASUREMENTS_PROJECTION = new String[]{"dia", "sys", "puls", "measure_date"};
        LAST_MEASUREMENT_DATE_FORMATTER = new SimpleDateFormat("LLLL dd, yyyy");
        WEEK_IN_MILLIS = TimeUnit.DAYS.toMillis(7L);
        MONTH_IN_MILLIS = TimeUnit.DAYS.toMillis(30L);
    }

    public WearableDataHelper(IFollowUserRepository iFollowUserRepository) {
        this.iFollowUserRepository = iFollowUserRepository;
    }

    /*
     * WARNING - combined exceptions agressively - possible behaviour change.
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    public static BPMeasurementsDescription[] getBPMeasurementDescriptions(Context context, long l) {
        long l2 = System.currentTimeMillis();
        Uri uri = MeasurementHelper.BloodPressure.buildMeasurementsUri(l);
        BPMeasurementsDescription[] arrbPMeasurementsDescription = "measure_date>=? AND (sync_status | " + 3 + " = " + 3 + ")";
        String string2 = Long.toString(l2 - MONTH_IN_MILLIS);
        string2 = context.getContentResolver().query(uri, SYNC_USER_BP_MEASUREMENTS_PROJECTION, (String)arrbPMeasurementsDescription, new String[]{string2}, "measure_date DESC");
        arrbPMeasurementsDescription = new BPMeasurementsDescription[3];
        int n = 0;
        int n2 = 0;
        int n3 = 0;
        int n4 = 0;
        int n5 = 0;
        int n6 = 0;
        int n7 = 0;
        int n8 = 0;
        try {
            int n9 = string2.getColumnIndex("dia");
            int n10 = string2.getColumnIndex("sys");
            int n11 = string2.getColumnIndex("puls");
            int n12 = string2.getColumnIndex("measure_date");
            while (string2.moveToNext()) {
                l = string2.getLong(n12);
                int n13 = string2.getInt(n9);
                int n14 = string2.getInt(n10);
                int n15 = string2.getInt(n11);
                if (arrbPMeasurementsDescription[0] == null) {
                    arrbPMeasurementsDescription[0] = new BPMeasurementsDescription();
                    arrbPMeasurementsDescription[0].date = LAST_MEASUREMENT_DATE_FORMATTER.format(l);
                    arrbPMeasurementsDescription[0].sys = String.valueOf(n14);
                    arrbPMeasurementsDescription[0].dia = String.valueOf(n13);
                    arrbPMeasurementsDescription[0].pulse = String.valueOf(n15);
                }
                long l3 = WEEK_IN_MILLIS;
                int n16 = n4;
                int n17 = n;
                int n18 = n3;
                int n19 = n2;
                if (l2 - l <= l3) {
                    n17 = n + n13;
                    n19 = n2 + n14;
                    n18 = n3 + n15;
                    n16 = n4 + 1;
                }
                n5 += n13;
                n6 += n14;
                n7 += n15;
                ++n8;
                n4 = n16;
                n = n17;
                n3 = n18;
                n2 = n19;
            }
        }
        finally {
            string2.close();
        }
        if (n4 > 0) {
            arrbPMeasurementsDescription[1] = new BPMeasurementsDescription();
            arrbPMeasurementsDescription[1].date = context.getString(2131362404);
            arrbPMeasurementsDescription[1].sys = String.valueOf(Math.round((float)n2 / (float)n4));
            arrbPMeasurementsDescription[1].dia = String.valueOf(Math.round((float)n / (float)n4));
            arrbPMeasurementsDescription[1].pulse = String.valueOf(Math.round((float)n3 / (float)n4));
        }
        if (n8 > 0) {
            arrbPMeasurementsDescription[2] = new BPMeasurementsDescription();
            arrbPMeasurementsDescription[2].date = context.getString(2131362286);
            arrbPMeasurementsDescription[2].sys = String.valueOf(Math.round((float)n6 / (float)n8));
            arrbPMeasurementsDescription[2].dia = String.valueOf(Math.round((float)n5 / (float)n8));
            arrbPMeasurementsDescription[2].pulse = String.valueOf(Math.round((float)n7 / (float)n8));
        }
        if (arrbPMeasurementsDescription[0] != null) return arrbPMeasurementsDescription;
        string2 = "(sync_status | " + 3 + " = " + 3 + ")";
        context = context.getContentResolver().query(uri, SYNC_USER_BP_MEASUREMENTS_PROJECTION, string2, null, "measure_date DESC" + " LIMIT 1");
        try {
            if (!context.moveToFirst()) return arrbPMeasurementsDescription;
            n6 = context.getColumnIndex("dia");
            n7 = context.getColumnIndex("sys");
            n5 = context.getColumnIndex("measure_date");
            n3 = context.getColumnIndex("puls");
            arrbPMeasurementsDescription[0] = new BPMeasurementsDescription();
            arrbPMeasurementsDescription[0].date = LAST_MEASUREMENT_DATE_FORMATTER.format(context.getLong(n5));
            arrbPMeasurementsDescription[0].sys = String.valueOf(context.getInt(n7));
            arrbPMeasurementsDescription[0].dia = String.valueOf(context.getInt(n6));
            arrbPMeasurementsDescription[0].pulse = String.valueOf(context.getInt(n3));
            return arrbPMeasurementsDescription;
        }
        finally {
            context.close();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public static WeightMeasurementsDescription getLastWeightMeasurementDescription(Context context, long l) {
        Object object;
        Object object2;
        block7: {
            block6: {
                int n;
                object2 = null;
                object = MeasurementHelper.Weight.getLastMeasurement(context, l, null);
                if (object == null) break block6;
                object2 = DataHelper.ProfileHelper.getProfileForUser(context, l);
                int n2 = n = 0;
                if (object2 != null) {
                    n2 = n;
                    if (((Profile)object2).weightUnit != null) {
                        n2 = ((Profile)object2).weightUnit;
                    }
                }
                object2 = new WeightMeasurementsDescription();
                ((WeightMeasurementsDescription)object2).date = LAST_MEASUREMENT_DATE_FORMATTER.format(((WeightMeasurement)object).measureDate);
                ((WeightMeasurementsDescription)object2).weight = context.getString(2131362549, new Object[]{Float.valueOf(MetricUtils.convertWeight(0, n2, ((WeightMeasurement)object).weight.floatValue())), context.getString(MetricUtils.getWeightUnitNameRes(n2))});
                ((WeightMeasurementsDescription)object2).fat = ((WeightMeasurement)object).fat != null ? context.getString(2131362238, new Object[]{Utils.formatInteger(((WeightMeasurement)object).fat)}) : context.getString(2131362495);
                if (((WeightMeasurement)object).muscle == null) break block7;
                ((WeightMeasurementsDescription)object2).muscle = context.getString(2131362290, new Object[]{Convert.floatToString(Float.valueOf(((WeightMeasurement)object).muscle.intValue()), 0)});
            }
            return object2;
        }
        if ((object = QardioBaseUtils.musclePercentage((WeightMeasurement)object)) != null) {
            ((WeightMeasurementsDescription)object2).muscle = context.getString(2131362290, new Object[]{Convert.floatToString((Float)object, 0)});
            return object2;
        }
        ((WeightMeasurementsDescription)object2).muscle = context.getString(2131362495);
        return object2;
    }

    static /* synthetic */ Iterable lambda$getFollowingData$0(List list) throws Exception {
        return list;
    }

    static /* synthetic */ boolean lambda$getFollowingData$1(IFollowUser iFollowUser) throws Exception {
        return iFollowUser.getAccessStatus() == FFTypes.Status.APPROVED;
    }

    private void loadFollowingMetadata(Context context, FollowingData followingData, IFollowUser iFollowUser) {
        BPMeasurementsDescription bPMeasurementsDescription = new BPMeasurementsDescription();
        bPMeasurementsDescription.userName = followingData.getFullName();
        bPMeasurementsDescription.sys = String.valueOf(iFollowUser.getBpLastMeasurement().getSys());
        bPMeasurementsDescription.dia = String.valueOf(iFollowUser.getBpLastMeasurement().getDia());
        bPMeasurementsDescription.pulse = String.valueOf(iFollowUser.getBpLastMeasurement().getPulse());
        bPMeasurementsDescription.date = LAST_MEASUREMENT_DATE_FORMATTER.format(iFollowUser.getBpLastMeasurement().getTime());
        followingData.lastBPMeasurement = bPMeasurementsDescription;
        bPMeasurementsDescription = new BPMeasurementsDescription();
        bPMeasurementsDescription.userName = followingData.getFullName();
        bPMeasurementsDescription.sys = WearableDataHelper.round(iFollowUser.getBpLastMeasurement().getAverageLastWeek().getData1());
        bPMeasurementsDescription.dia = WearableDataHelper.round(iFollowUser.getBpLastMeasurement().getAverageLastWeek().getData2());
        bPMeasurementsDescription.pulse = WearableDataHelper.round(iFollowUser.getBpLastMeasurement().getAverageLastWeek().getData3());
        bPMeasurementsDescription.date = context.getString(2131362404);
        followingData.weeklyBPMeasurement = bPMeasurementsDescription;
        bPMeasurementsDescription = new BPMeasurementsDescription();
        bPMeasurementsDescription.userName = followingData.getFullName();
        bPMeasurementsDescription.sys = WearableDataHelper.round(iFollowUser.getBpLastMeasurement().getAverageLastMonth().getData1());
        bPMeasurementsDescription.dia = WearableDataHelper.round(iFollowUser.getBpLastMeasurement().getAverageLastMonth().getData2());
        bPMeasurementsDescription.pulse = WearableDataHelper.round(iFollowUser.getBpLastMeasurement().getAverageLastMonth().getData3());
        bPMeasurementsDescription.date = context.getString(2131362286);
        followingData.monthlyBPMeasurement = bPMeasurementsDescription;
    }

    private static String round(String string2) {
        long l;
        if (TextUtils.isEmpty((CharSequence)string2)) {
            return "--";
        }
        try {
            l = Math.round(Double.parseDouble(string2));
        }
        catch (NumberFormatException numberFormatException) {
            return "--";
        }
        return String.valueOf(l);
    }

    public Observable<FollowingData> getFollowingData(Context context, long l) {
        return this.iFollowUserRepository.getIFollowUsers(l).toObservable().flatMapIterable(WearableDataHelper$$Lambda$1.lambdaFactory$()).distinct(WearableDataHelper$$Lambda$2.lambdaFactory$()).filter(WearableDataHelper$$Lambda$3.lambdaFactory$()).map(WearableDataHelper$$Lambda$4.lambdaFactory$(this, context));
    }

    /* synthetic */ FollowingData lambda$getFollowingData$2(Context context, IFollowUser iFollowUser) throws Exception {
        FollowingData followingData = new FollowingData();
        followingData.firstName = iFollowUser.getFirstName();
        followingData.lastName = iFollowUser.getLastName();
        this.loadFollowingMetadata(context, followingData, iFollowUser);
        return followingData;
    }
}

