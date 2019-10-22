/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.mvp.friends_family.i_follow.model.remote;

import com.getqardio.android.mvp.common.local.AccountContextHelper;
import com.getqardio.android.mvp.common.local.SyncStatus;
import com.getqardio.android.mvp.common.remote.BaseResponse;
import com.getqardio.android.mvp.common.remote.ServerInterface;
import com.getqardio.android.mvp.friends_family.common.FFTypes;
import com.getqardio.android.mvp.friends_family.common.InviteDeleteAcceptToFollowMeRequest;
import com.getqardio.android.mvp.friends_family.i_follow.model.IFollowUserDataSource;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.BpLastMeasurement;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.LastBpMeasurementData;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightExtraInfo;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightLastMeasurement;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.Average;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.EnablePushNotificationsRequest;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.GetIFollowUserResponse;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource$$Lambda$1;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource$$Lambda$2;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource$$Lambda$3;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource$$Lambda$4;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource$$Lambda$5;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.IFollowUserRemoteDataSource$$Lambda$6;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.LastMeasurement;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.Metadata;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.User;
import com.getqardio.android.mvp.friends_family.i_follow.model.remote.Weight;
import com.google.gson.Gson;
import io.reactivex.Maybe;
import io.reactivex.Observable;
import io.reactivex.Single;
import io.reactivex.functions.Function;
import io.reactivex.functions.Predicate;
import java.util.Iterator;
import java.util.List;
import timber.log.Timber;

public class IFollowUserRemoteDataSource
implements IFollowUserDataSource {
    private AccountContextHelper accountContextHelper;
    private final ServerInterface serverInterface;

    public IFollowUserRemoteDataSource(ServerInterface serverInterface, AccountContextHelper accountContextHelper) {
        this.serverInterface = serverInterface;
        this.accountContextHelper = accountContextHelper;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private BpLastMeasurement getBpLastMeasurement(long l, User object) {
        Object object2 = ((User)object).getMetadata().iterator();
        do {
            if (object2.hasNext()) continue;
            return null;
        } while (!((Metadata)(object = object2.next())).getType().equals("bp"));
        object2 = new BpLastMeasurement();
        ((BpLastMeasurement)object2).setUserId(l);
        try {
            ((BpLastMeasurement)object2).setSys(Integer.parseInt(((Metadata)object).getLastMeasurement().getData1()));
        }
        catch (NumberFormatException numberFormatException) {
            Timber.e(numberFormatException);
        }
        try {
            ((BpLastMeasurement)object2).setDia(Integer.parseInt(((Metadata)object).getLastMeasurement().getData2()));
        }
        catch (NumberFormatException numberFormatException) {
            Timber.e(numberFormatException);
        }
        try {
            ((BpLastMeasurement)object2).setPulse(Integer.parseInt(((Metadata)object).getLastMeasurement().getData3()));
        }
        catch (NumberFormatException numberFormatException) {
            Timber.e(numberFormatException);
        }
        ((BpLastMeasurement)object2).setTime(((Metadata)object).getLastMeasurement().getDate());
        ((BpLastMeasurement)object2).setAverageLastDay(new LastBpMeasurementData(((Metadata)object).getAverageLastDay()));
        ((BpLastMeasurement)object2).setAverageLastWeek(new LastBpMeasurementData(((Metadata)object).getAverageLastWeek()));
        ((BpLastMeasurement)object2).setAverageLastMonth(new LastBpMeasurementData(((Metadata)object).getAverageLastMonth()));
        return object2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private WeightLastMeasurement getWeightLastMeasurement(long l, User object) {
        Object object2 = ((User)object).getMetadata().iterator();
        do {
            if (object2.hasNext()) continue;
            return null;
        } while (!((Metadata)(object = object2.next())).getType().equals("weight"));
        Object object3 = new Gson();
        object2 = new WeightLastMeasurement();
        ((WeightLastMeasurement)object2).setUserId(l);
        try {
            ((WeightLastMeasurement)object2).setWeight(Float.valueOf(Float.parseFloat(((Metadata)object).getLastMeasurement().getData1())));
        }
        catch (NumberFormatException numberFormatException) {
            Timber.e(numberFormatException);
        }
        try {
            ((WeightLastMeasurement)object2).setImpedance(Integer.parseInt(((Metadata)object).getLastMeasurement().getData2()));
        }
        catch (NumberFormatException numberFormatException) {
            Timber.e(numberFormatException);
        }
        WeightExtraInfo weightExtraInfo = new WeightExtraInfo();
        object3 = ((Gson)object3).fromJson(((Metadata)object).getLastMeasurement().getData3(), Weight.ExtraInfo.class);
        weightExtraInfo.setImpedance(((Weight.ExtraInfo)object3).getImpedance());
        weightExtraInfo.setAge(((Weight.ExtraInfo)object3).getAge());
        int n = ((Weight.ExtraInfo)object3).getBattery() == null ? 0 : (int)(((Weight.ExtraInfo)object3).getBattery() % 101L);
        weightExtraInfo.setBattery(n);
        weightExtraInfo.setBone(((Weight.ExtraInfo)object3).getBone());
        weightExtraInfo.setFat(((Weight.ExtraInfo)object3).getFat());
        weightExtraInfo.setHeight(((Weight.ExtraInfo)object3).getHeight());
        weightExtraInfo.setMeasurementId(((Weight.ExtraInfo)object3).getId());
        weightExtraInfo.setMuscle(((Weight.ExtraInfo)object3).getMuscle());
        weightExtraInfo.setSex(((Weight.ExtraInfo)object3).getSex());
        weightExtraInfo.setSource(((Weight.ExtraInfo)object3).getSource());
        weightExtraInfo.setUser(((Weight.ExtraInfo)object3).getUser());
        weightExtraInfo.setWater(((Weight.ExtraInfo)object3).getWater());
        weightExtraInfo.setUserId(((Weight.ExtraInfo)object3).getUserId());
        weightExtraInfo.setBmi(((Weight.ExtraInfo)object3).getBmi());
        ((WeightLastMeasurement)object2).setExtraInfo(weightExtraInfo);
        ((WeightLastMeasurement)object2).setTime(((Metadata)object).getLastMeasurement().getDate());
        return object2;
    }

    static /* synthetic */ IFollowUser lambda$deleteIFollowUser$3(IFollowUser iFollowUser, BaseResponse baseResponse) throws Exception {
        return iFollowUser;
    }

    static /* synthetic */ IFollowUser lambda$enablePushNotifications$4(IFollowUser iFollowUser, BaseResponse baseResponse) throws Exception {
        return iFollowUser;
    }

    static /* synthetic */ Iterable lambda$getIFollowUsersMaybe$0(List list) throws Exception {
        return list;
    }

    static /* synthetic */ boolean lambda$getIFollowUsersMaybe$2(List list) throws Exception {
        return !list.isEmpty();
    }

    private IFollowUser mapRespToIFollowUser(long l, User user) {
        IFollowUser iFollowUser = new IFollowUser();
        iFollowUser.setUserId(l);
        iFollowUser.setUserEmail(user.getWatcherEmail());
        iFollowUser.setAccessStatus(FFTypes.Status.valueOf(user.getAccessStatus()));
        iFollowUser.setNotificationsEnabled(user.getNotifications());
        iFollowUser.setWatchingEmail(user.getOwnerEmail());
        iFollowUser.setFirstName(user.getFirstname());
        iFollowUser.setLastName(user.getLastname());
        iFollowUser.setBpLastMeasurement(this.getBpLastMeasurement(l, user));
        iFollowUser.setWeightLastMeasurement(this.getWeightLastMeasurement(l, user));
        return iFollowUser;
    }

    @Override
    public Single<IFollowUser> deleteIFollowUser(long l, IFollowUser iFollowUser) {
        return this.serverInterface.deleteIFollowUser(this.accountContextHelper.getAuthToken(l), new InviteDeleteAcceptToFollowMeRequest(iFollowUser.getWatchingEmail())).map(IFollowUserRemoteDataSource$$Lambda$5.lambdaFactory$(iFollowUser));
    }

    @Override
    public Single<IFollowUser> enablePushNotifications(long l, IFollowUser iFollowUser, boolean bl) {
        return this.serverInterface.enablePushNotifications(this.accountContextHelper.getAuthToken(l), new EnablePushNotificationsRequest(iFollowUser.getWatchingEmail(), bl)).map(IFollowUserRemoteDataSource$$Lambda$6.lambdaFactory$(iFollowUser));
    }

    @Override
    public Maybe<List<IFollowUser>> getIFollowUsersMaybe(long l) {
        return this.serverInterface.getIFollowUsers(this.accountContextHelper.getAuthToken(l)).map(IFollowUserRemoteDataSource$$Lambda$1.lambdaFactory$()).flattenAsObservable(IFollowUserRemoteDataSource$$Lambda$2.lambdaFactory$()).map(IFollowUserRemoteDataSource$$Lambda$3.lambdaFactory$(this, l)).toList().toMaybe().filter(IFollowUserRemoteDataSource$$Lambda$4.lambdaFactory$());
    }

    /* synthetic */ IFollowUser lambda$getIFollowUsersMaybe$1(long l, User user) throws Exception {
        return this.mapRespToIFollowUser(l, user);
    }

    @Override
    public Maybe<List<IFollowUser>> rewriteIFollowUsers(long l, List<IFollowUser> list) {
        throw new UnsupportedOperationException();
    }

    @Override
    public Single<IFollowUser> saveIFollowUser(long l, IFollowUser iFollowUser, SyncStatus syncStatus) {
        throw new UnsupportedOperationException();
    }
}

