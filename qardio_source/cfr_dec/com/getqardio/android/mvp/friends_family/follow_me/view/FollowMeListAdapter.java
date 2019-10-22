/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.animation.TimeInterpolator
 *  android.content.Context
 *  android.graphics.drawable.Drawable
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.ViewGroup
 *  android.view.ViewPropertyAnimator
 *  android.view.animation.AccelerateInterpolator
 *  android.widget.ImageView
 *  android.widget.LinearLayout
 *  android.widget.TextView
 */
package com.getqardio.android.mvp.friends_family.follow_me.view;

import android.animation.TimeInterpolator;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewPropertyAnimator;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.OnLongClick;
import com.amulyakhare.textdrawable.TextDrawable;
import com.getqardio.android.mvp.common.ui.Binder;
import com.getqardio.android.mvp.common.ui.ColorsUtil;
import com.getqardio.android.mvp.common.ui.ItemCheckedChecker;
import com.getqardio.android.mvp.common.ui.ThemeUtil;
import com.getqardio.android.mvp.friends_family.common.FFTypes;
import com.getqardio.android.mvp.friends_family.follow_me.model.local.FollowMeUser;
import com.getqardio.android.mvp.friends_family.follow_me.presentation.FollowMeUiModelItem;
import com.getqardio.android.utils.ui.Convert;
import io.reactivex.functions.Consumer;
import java.util.ArrayList;
import java.util.List;
import timber.log.Timber;

public class FollowMeListAdapter
extends RecyclerView.Adapter<Binder<FollowMeUiModelItem>> {
    private ItemCheckedChecker itemCheckedChecker;
    private Consumer<Integer> itemOnClickListener;
    private Consumer<Integer> itemOnLongClickListener;
    private List<FollowMeUiModelItem> users = new ArrayList<FollowMeUiModelItem>();

    FollowMeListAdapter(ItemCheckedChecker itemCheckedChecker) {
        this.itemCheckedChecker = itemCheckedChecker;
    }

    @Override
    public int getItemCount() {
        return this.users.size();
    }

    @Override
    public int getItemViewType(int n) {
        switch (1.$SwitchMap$com$getqardio$android$mvp$friends_family$follow_me$presentation$FollowMeUiModelItem$Type[this.users.get(n).getType().ordinal()]) {
            default: {
                throw new RuntimeException(String.format("Wrong type -%s on pos - %d", new Object[]{this.users.get(n).getType(), n}));
            }
            case 1: {
                return 0;
            }
            case 2: {
                return 1;
            }
            case 3: 
        }
        return 2;
    }

    @Override
    public void onBindViewHolder(Binder<FollowMeUiModelItem> binder, int n) {
        if (binder instanceof UserViewHolder) {
            ((UserViewHolder)binder).bind(this.users.get(n), n);
            return;
        }
        binder.bind(this.users.get(n));
    }

    @Override
    public Binder<FollowMeUiModelItem> onCreateViewHolder(ViewGroup viewGroup, int n) {
        switch (n) {
            default: {
                throw new RuntimeException(String.format("Wrong view type - %d", n));
            }
            case 0: {
                return new HeaderApprovedHolder(LayoutInflater.from((Context)viewGroup.getContext()).inflate(2130968677, viewGroup, false));
            }
            case 1: {
                return new HeaderPendingHolder(LayoutInflater.from((Context)viewGroup.getContext()).inflate(2130968677, viewGroup, false));
            }
            case 2: 
        }
        return new UserViewHolder(LayoutInflater.from((Context)viewGroup.getContext()).inflate(2130968717, viewGroup, false));
    }

    public void setData(List<FollowMeUiModelItem> list) {
        this.users = list;
        this.notifyDataSetChanged();
    }

    public void setOnItemClickListener(Consumer<Integer> consumer) {
        this.itemOnClickListener = consumer;
    }

    public void setOnItemLongClickListener(Consumer<Integer> consumer) {
        this.itemOnLongClickListener = consumer;
    }

    private class HeaderApprovedHolder
    extends Binder<FollowMeUiModelItem> {
        public HeaderApprovedHolder(View view) {
            super(view);
        }

        @Override
        public void bind(FollowMeUiModelItem followMeUiModelItem) {
            ((TextView)this.itemView).setText(2131362205);
        }
    }

    private class HeaderPendingHolder
    extends Binder<FollowMeUiModelItem> {
        public HeaderPendingHolder(View view) {
            super(view);
        }

        @Override
        public void bind(FollowMeUiModelItem followMeUiModelItem) {
            ((TextView)this.itemView).setText(2131362305);
        }
    }

    class UserViewHolder
    extends Binder<FollowMeUiModelItem> {
        @BindView
        View checkIcon;
        private int currentPosition;
        @BindView
        TextView email;
        @BindView
        ImageView image;
        @BindView
        TextView name;
        @BindView
        LinearLayout root;
        private TextDrawable.IBuilder textDrawableBuilder;

        UserViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            this.textDrawableBuilder = TextDrawable.builder().beginConfig().toUpperCase().fontSize((int)Convert.convertDpToPixel(24.0f, view.getContext())).endConfig().round();
        }

        private void displayCheckedState() {
            this.image.setImageDrawable((Drawable)this.textDrawableBuilder.build(" ", ContextCompat.getColor(this.root.getContext(), 2131689556)));
            this.showCheckIconWithAnimation();
            this.root.setBackgroundColor(ContextCompat.getColor(this.root.getContext(), 2131689554));
        }

        private void displayNormalState(String string2) {
            int n = ColorsUtil.generateColor(string2);
            this.image.setImageDrawable((Drawable)this.textDrawableBuilder.build(string2.substring(0, 1), n));
            this.root.setBackground(ContextCompat.getDrawable(this.root.getContext(), ThemeUtil.getSelectableItemBackground(this.root.getContext())));
            this.checkIcon.setVisibility(8);
        }

        private void displayPendingState(String string2) {
            Context context = this.root.getContext();
            this.image.setImageDrawable((Drawable)this.textDrawableBuilder.build(string2.substring(0, 1), ContextCompat.getColor(context, 2131689553)));
            this.root.setBackground(ContextCompat.getDrawable(context, ThemeUtil.getSelectableItemBackground(context)));
            this.checkIcon.setVisibility(8);
        }

        private void showCheckIconWithAnimation() {
            this.checkIcon.setAlpha(0.0f);
            this.checkIcon.setScaleX(0.0f);
            this.checkIcon.setScaleY(0.0f);
            this.checkIcon.setVisibility(0);
            this.checkIcon.animate().alpha(1.0f).scaleX(1.0f).scaleY(1.0f).setDuration(300L).setInterpolator((TimeInterpolator)new AccelerateInterpolator()).start();
        }

        @Override
        public void bind(FollowMeUiModelItem followMeUiModelItem) {
        }

        public void bind(FollowMeUiModelItem object, int n) {
            this.currentPosition = n;
            Object object2 = ((FollowMeUiModelItem)object).getFollowMeUser();
            if (((FollowMeUser)object2).getAccessStatus() == FFTypes.Status.APPROVED) {
                object = ((FollowMeUser)object2).getWatcherEmail();
                object2 = String.format("%s %s", ((FollowMeUser)object2).getFirstName(), ((FollowMeUser)object2).getLastName()).trim();
                this.email.setText((CharSequence)object);
                this.name.setText((CharSequence)object2);
                if (FollowMeListAdapter.this.itemCheckedChecker.isItemChecked(n)) {
                    this.displayCheckedState();
                    return;
                }
                this.displayNormalState((String)object2);
                return;
            }
            object = ((FollowMeUser)object2).getWatcherEmail();
            this.email.setText((CharSequence)object);
            object2 = ((String)object).substring(0, ((String)object).indexOf(64) + 1);
            object2 = String.format("%s%s", ((String)object2).substring(0, 1).toUpperCase(), ((String)object2).substring(1, ((String)object2).length() - 1).toLowerCase());
            this.name.setText((CharSequence)object2);
            if (FollowMeListAdapter.this.itemCheckedChecker.isItemChecked(n)) {
                this.displayCheckedState();
                return;
            }
            this.displayPendingState((String)object);
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        @OnLongClick
        boolean clickLongOnClickableArea() {
            if (FollowMeListAdapter.this.itemOnLongClickListener == null) return false;
            try {
                FollowMeListAdapter.this.itemOnLongClickListener.accept(this.currentPosition);
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
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        @OnClick
        void clickOnClickableArea() {
            if (FollowMeListAdapter.this.itemCheckedChecker.isAnyItemChecked()) {
                if (FollowMeListAdapter.this.itemOnClickListener == null) return;
                {
                    try {
                        FollowMeListAdapter.this.itemOnClickListener.accept(this.currentPosition);
                        return;
                    }
                    catch (Exception exception) {
                        Timber.e(exception);
                        return;
                    }
                }
            } else {
                if (FollowMeListAdapter.this.itemOnClickListener == null) return;
                {
                    try {
                        FollowMeListAdapter.this.itemOnClickListener.accept(this.currentPosition);
                        return;
                    }
                    catch (Exception exception) {
                        Timber.e(exception);
                        return;
                    }
                }
            }
        }
    }

}

