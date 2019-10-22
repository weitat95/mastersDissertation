/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.text.TextUtils
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewPropertyAnimator
 *  android.view.animation.AccelerateInterpolator
 *  android.widget.ImageButton
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.friends_family.i_follow.view;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import com.amulyakhare.textdrawable.TextDrawable;
import com.getqardio.android.mvp.common.ui.ColorsUtil;
import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.common.ui.ThemeUtil;
import com.getqardio.android.mvp.common.ui.widget.MeasurementFlatIndicator;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.BpLastMeasurement;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.IFollowUser;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightExtraInfo;
import com.getqardio.android.mvp.friends_family.i_follow.model.local.WeightLastMeasurement;
import com.getqardio.android.utils.DateUtils;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.QardioBaseUtils;
import com.getqardio.android.utils.ui.Convert;
import io.reactivex.functions.BiConsumer;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import timber.log.Timber;

public class IFollowListAdapter
extends RecyclerView.Adapter<UserViewHolder> {
    private ItemCheckedChecker itemCheckedChecker;
    private BiConsumer<Integer, Boolean> itemNotificationOnOffClickListener;
    private Consumer<Integer> itemOnBpClickListener;
    private Consumer<Integer> itemOnClickListener;
    private Consumer<Integer> itemOnLongClickListener;
    private Consumer<Integer> itemOnWeightClickListener;
    private List<IFollowUser> users = new ArrayList<IFollowUser>();
    private int weightUnit;

    IFollowListAdapter(ItemCheckedChecker itemCheckedChecker) {
        this.itemCheckedChecker = itemCheckedChecker;
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    @Override
    public void onBindViewHolder(UserViewHolder userViewHolder, int n) {
        userViewHolder.bind(n);
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
        return new UserViewHolder(LayoutInflater.from((Context)viewGroup.getContext()).inflate(2130968720, viewGroup, false));
    }

    public void setData(List<IFollowUser> list, int n) {
        this.users = list;
        this.weightUnit = n;
        this.notifyDataSetChanged();
    }

    void setItemNotificationOnOffClickListener(BiConsumer<Integer, Boolean> biConsumer) {
        this.itemNotificationOnOffClickListener = biConsumer;
    }

    void setItemOnBpClickListener(Consumer<Integer> consumer) {
        this.itemOnBpClickListener = consumer;
    }

    void setItemOnClickListener(Consumer<Integer> consumer) {
        this.itemOnClickListener = consumer;
    }

    void setItemOnLongClickListener(Consumer<Integer> consumer) {
        this.itemOnLongClickListener = consumer;
    }

    void setItemOnWeightClickListener(Consumer<Integer> consumer) {
        this.itemOnWeightClickListener = consumer;
    }

    class UserViewHolder
    extends RecyclerView.ViewHolder {
        @BindView
        MeasurementFlatIndicator bmiIndicatorView;
        @BindView
        View bpBlockView;
        @BindView
        TextView bpDateView;
        @BindView
        MeasurementFlatIndicator bpIndicatorView;
        @BindView
        TextView bpView;
        @BindView
        View checkIconView;
        private int currentPosition;
        @BindView
        TextView emailView;
        @BindView
        ImageView imageView;
        @BindView
        View nameEmailAreaView;
        @BindView
        TextView nameView;
        @BindView
        ImageButton notificationButton;
        @BindView
        ImageView pulseImg;
        @BindView
        View pulseLabel;
        @BindView
        TextView pulseView;
        @BindView
        View rootView;
        @BindView
        View separatorView;
        private TextDrawable.IBuilder textDrawableBuilder;
        private IFollowUser user;
        @BindView
        View weightBlockView;
        @BindView
        TextView weightDateView;
        @BindView
        TextView weightView;

        UserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.textDrawableBuilder = TextDrawable.builder().beginConfig().toUpperCase().fontSize((int)Convert.convertDpToPixel(24.0f, view.getContext())).endConfig().round();
        }

        private void checkItem(boolean bl, String string2) {
            if (bl) {
                this.displayCheckedState();
                return;
            }
            this.displayNormalState(string2);
        }

        private void displayCheckedState() {
            this.imageView.setImageDrawable((Drawable)this.textDrawableBuilder.build(" ", ContextCompat.getColor(this.nameEmailAreaView.getContext(), 2131689556)));
            this.showCheckIconWithAnimation();
            this.rootView.setBackgroundColor(ContextCompat.getColor(this.nameEmailAreaView.getContext(), 2131689553));
        }

        private void displayNormalState(String string2) {
            int n = ColorsUtil.generateColor(string2);
            this.imageView.setImageDrawable((Drawable)this.textDrawableBuilder.build(string2.substring(0, 1), n));
            this.rootView.setBackground(ContextCompat.getDrawable(this.nameEmailAreaView.getContext(), ThemeUtil.getSelectableItemBackground(this.nameEmailAreaView.getContext())));
            this.checkIconView.setVisibility(8);
        }

        private boolean isBpVisible(BpLastMeasurement bpLastMeasurement) {
            return bpLastMeasurement != null && bpLastMeasurement.getDia() != null && bpLastMeasurement.getSys() != null;
        }

        private boolean isWeightVisible(WeightLastMeasurement weightLastMeasurement) {
            return weightLastMeasurement != null && weightLastMeasurement.getWeight() != null && (weightLastMeasurement.getExtraInfo() != null && weightLastMeasurement.getExtraInfo().getHeight() != null && weightLastMeasurement.getExtraInfo().getHeight() != -1 || weightLastMeasurement.getExtraInfo() != null && weightLastMeasurement.getExtraInfo().getBmi() != null);
        }

        /*
         * Enabled aggressive block sorting
         */
        private void setNotificationEnabledIcon(Context context) {
            ImageButton imageButton = this.notificationButton;
            context = this.user.isNotificationsEnabled() ? ContextCompat.getDrawable(context, 2130837814) : ContextCompat.getDrawable(context, 2130837815);
            imageButton.setImageDrawable((Drawable)context);
        }

        private void setupBpBlock(BpLastMeasurement bpLastMeasurement) {
            Context context = this.bpDateView.getContext();
            this.bpBlockView.setVisibility(0);
            this.bpView.setText((CharSequence)String.format("%d/%d", bpLastMeasurement.getSys(), bpLastMeasurement.getDia()));
            this.bpIndicatorView.highlightSector(this.bpIndicatorView.calculateSectorForBp(bpLastMeasurement.getDia(), bpLastMeasurement.getSys()));
            this.bpDateView.setText((CharSequence)DateUtils.formatDateInLocaleAndWithTodayAndYesterday(context, new Date(bpLastMeasurement.getTime())));
            if (bpLastMeasurement.getPulse() == null || bpLastMeasurement.getPulse() == 0) {
                this.pulseView.setVisibility(8);
                this.pulseLabel.setVisibility(8);
                this.pulseImg.setVisibility(8);
                return;
            }
            this.pulseView.setText((CharSequence)String.valueOf(bpLastMeasurement.getPulse()));
            this.pulseView.setVisibility(0);
            this.pulseImg.setVisibility(0);
            this.pulseLabel.setVisibility(0);
        }

        /*
         * WARNING - void declaration
         * Enabled aggressive block sorting
         */
        private void setupWeightBlock(Context object, WeightLastMeasurement weightLastMeasurement) {
            void var2_3;
            this.weightBlockView.setVisibility(0);
            float f = MetricUtils.convertWeight(0, IFollowListAdapter.this.weightUnit, var2_3.getWeight().floatValue());
            String string2 = object.getString(MetricUtils.getWeightUnitNameRes(IFollowListAdapter.this.weightUnit));
            String string3 = String.format("%.0f", Float.valueOf(f));
            this.weightView.setText((CharSequence)String.format("%s %s", string3, string2));
            Timber.d("w.getExtraInfo() - %s", var2_3.getExtraInfo());
            if (var2_3.getExtraInfo().getBmi() != null) {
                this.bmiIndicatorView.highlightSector(this.bpIndicatorView.calculateSectorForBmi(var2_3.getExtraInfo().getBmi().floatValue()));
            } else {
                this.bmiIndicatorView.highlightSector(this.bpIndicatorView.calculateSectorForBmi(QardioBaseUtils.bmi(var2_3.getWeight().floatValue(), var2_3.getExtraInfo().getHeight())));
            }
            this.weightDateView.setText((CharSequence)DateUtils.formatDateInLocaleAndWithTodayAndYesterday(this.bpDateView.getContext(), new Date(var2_3.getTime())));
        }

        private void showCheckIconWithAnimation() {
            this.checkIconView.setAlpha(0.0f);
            this.checkIconView.setScaleX(0.0f);
            this.checkIconView.setScaleY(0.0f);
            this.checkIconView.setVisibility(0);
            this.checkIconView.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(300L).setInterpolator((TimeInterpolator)new AccelerateInterpolator()).start();
        }

        /*
         * Enabled aggressive block sorting
         */
        void bind(int n) {
            WeightLastMeasurement weightLastMeasurement;
            this.currentPosition = n;
            this.user = (IFollowUser)IFollowListAdapter.this.users.get(n);
            Context context = this.itemView.getContext();
            String string2 = this.user.getWatchingEmail();
            Object object = this.user.buildFullName();
            int n2 = ColorsUtil.generateColor(string2);
            if (!TextUtils.isEmpty((CharSequence)object)) {
                this.imageView.setImageDrawable((Drawable)this.textDrawableBuilder.build(((String)object).substring(0, 1), n2));
            } else {
                this.imageView.setImageDrawable((Drawable)this.textDrawableBuilder.build(string2.substring(0, 1), n2));
            }
            this.nameView.setText((CharSequence)object);
            this.emailView.setText((CharSequence)string2);
            object = this.user.getBpLastMeasurement();
            if (this.isBpVisible((BpLastMeasurement)object)) {
                this.setupBpBlock((BpLastMeasurement)object);
            } else {
                this.bpBlockView.setVisibility(8);
            }
            if (this.isWeightVisible(weightLastMeasurement = this.user.getWeightLastMeasurement())) {
                this.setupWeightBlock(context, weightLastMeasurement);
            } else {
                this.weightBlockView.setVisibility(8);
            }
            View view = this.separatorView;
            n2 = this.isWeightVisible(weightLastMeasurement) && this.isBpVisible((BpLastMeasurement)object) ? 0 : 8;
            view.setVisibility(n2);
            this.setNotificationEnabledIcon(context);
            this.checkItem(IFollowListAdapter.this.itemCheckedChecker.isItemChecked(n), string2);
        }

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @OnClick
        void notificationSettingsClicked() {
            BiConsumer biConsumer;
            boolean bl;
            boolean bl2 = true;
            try {
                biConsumer = IFollowListAdapter.this.itemNotificationOnOffClickListener;
                int n = this.currentPosition;
                bl = !this.user.isNotificationsEnabled();
                biConsumer.accept(n, bl);
            }
            catch (Exception exception) {
                Timber.e(exception);
            }
            biConsumer = this.notificationButton.getContext();
            IFollowUser iFollowUser = this.user;
            bl = !this.user.isNotificationsEnabled() ? bl2 : false;
            iFollowUser.setNotificationsEnabled(bl);
            this.setNotificationEnabledIcon((Context)biConsumer);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @OnClick
        void onBloodPressureCLick() {
            if (IFollowListAdapter.this.itemOnBpClickListener == null) return;
            try {
                IFollowListAdapter.this.itemOnBpClickListener.accept(this.currentPosition);
                return;
            }
            catch (Exception exception) {
                Timber.e(exception);
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @OnClick
        void onNameEmailAreaClick() {
            if (IFollowListAdapter.this.itemOnClickListener == null) return;
            try {
                IFollowListAdapter.this.itemOnClickListener.accept(this.currentPosition);
                return;
            }
            catch (Exception exception) {
                Timber.e(exception);
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @OnLongClick
        boolean onNameEmailAreaLongClick() {
            if (IFollowListAdapter.this.itemOnLongClickListener == null) return false;
            try {
                IFollowListAdapter.this.itemOnLongClickListener.accept(this.currentPosition);
                do {
                    return true;
                    break;
                } while (true);
            }
            catch (Exception exception) {
                Timber.e(exception);
                return true;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @OnClick
        void onWeightClick() {
            if (IFollowListAdapter.this.itemOnWeightClickListener == null) return;
            try {
                IFollowListAdapter.this.itemOnWeightClickListener.accept(this.currentPosition);
                return;
            }
            catch (Exception exception) {
                Timber.e(exception);
                return;
            }
        }
    }

}

