/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils;

import android.content.Context;
import com.getqardio.android.mvp.common.util.RxUtil;
import com.getqardio.android.provider.DataHelper;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.PregnancyUtils$$Lambda$1;
import com.getqardio.android.utils.PregnancyUtils$$Lambda$2;
import com.getqardio.android.utils.PregnancyUtils$$Lambda$3;
import io.reactivex.Observable;
import io.reactivex.ObservableTransformer;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import timber.log.Timber;

public class PregnancyUtils {
    public static int calculateDayOfPregnancy(Date date, Date date2) {
        date = DateUtils.getStartOfTheDay(date);
        return (int)TimeUnit.DAYS.convert(date2.getTime() - date.getTime(), TimeUnit.MILLISECONDS);
    }

    public static Date calculateDueDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(6, 280);
        return calendar.getTime();
    }

    public static int calculateMonthOfPregnancy(Date date, Date date2) {
        date = DateUtils.getStartOfTheDay(date);
        return (int)Math.ceil((date2.getTime() - date.getTime()) / 2688000000L);
    }

    public static Date calculateStartDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(6, -280);
        return calendar.getTime();
    }

    public static int calculateWeekOfPregnancy(Date date, Date date2) {
        date = DateUtils.getStartOfTheDay(date);
        return (int)((double)(date2.getTime() - date.getTime()) * 1.0 / (double)TimeUnit.MILLISECONDS.convert(7L, TimeUnit.DAYS)) + 1;
    }

    static /* synthetic */ Object lambda$stopPregnancyModeAsync$0(Context context, long l, Object object) throws Exception {
        DataHelper.PregnancyDataHelper.stopCurrentPregnancy(context, l);
        DataHelper.PregnancyDataHelper.setPregnancyModeActive(context, l, false);
        return null;
    }

    static /* synthetic */ void lambda$stopPregnancyModeAsync$1(Object object) throws Exception {
        Timber.d("stoped", new Object[0]);
    }

    public static void stopPregnancyModeAsync(Context context, long l) {
        Observable.just(new Object()).map(PregnancyUtils$$Lambda$1.lambdaFactory$(context, l)).compose(RxUtil.applySchedulers()).subscribe(PregnancyUtils$$Lambda$2.lambdaFactory$(), PregnancyUtils$$Lambda$3.lambdaFactory$());
    }
}

