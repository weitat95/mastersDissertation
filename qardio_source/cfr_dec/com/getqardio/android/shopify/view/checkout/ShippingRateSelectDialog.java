/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.DialogInterface
 *  android.content.DialogInterface$OnCancelListener
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.checkout;

import android.content.Context;
import android.content.DialogInterface;
import android.support.design.widget.BottomSheetDialog;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getqardio.android.shopify.ShopifyKeyManager;
import com.getqardio.android.shopify.domain.model.Checkout;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewAdapter;
import com.getqardio.android.shopify.view.checkout.ShippingRateSelectDialog$$Lambda$1;
import java.math.BigDecimal;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;

public final class ShippingRateSelectDialog
extends BottomSheetDialog
implements RecyclerViewAdapter.OnItemClickListener {
    @BindView
    RecyclerView listView;
    private final RecyclerViewAdapter listViewAdapter = new RecyclerViewAdapter(this);
    private OnShippingRateSelectListener onShippingRateSelectListener;
    @BindView
    Toolbar toolbarView;

    public ShippingRateSelectDialog(Context context) {
        super(context);
        this.init();
    }

    public ShippingRateSelectDialog(Context context, int n) {
        super(context, n);
        this.init();
    }

    public ShippingRateSelectDialog(Context context, boolean bl, DialogInterface.OnCancelListener onCancelListener) {
        super(context, bl, onCancelListener);
        this.init();
    }

    private void init() {
        View view = LayoutInflater.from((Context)this.getContext()).inflate(2130968826, null);
        this.setContentView(view);
        ButterKnife.bind(this, view);
        this.toolbarView.setTitle(2131362476);
        this.toolbarView.setNavigationIcon(2130837750);
        this.toolbarView.setNavigationOnClickListener(ShippingRateSelectDialog$$Lambda$1.lambdaFactory$(this));
        this.listView.setAdapter(this.listViewAdapter);
    }

    /* synthetic */ void lambda$init$0(View view) {
        this.dismiss();
    }

    @Override
    public void onItemClick(ListItemViewModel listItemViewModel) {
        if (this.onShippingRateSelectListener != null) {
            this.dismiss();
            this.onShippingRateSelectListener.onShippingRateSelected((Checkout.ShippingRate)listItemViewModel.payload());
        }
    }

    public void show(Checkout.ShippingRates object, OnShippingRateSelectListener object2) {
        Util.checkNotNull(object, "shippingRates == null");
        this.onShippingRateSelectListener = object2;
        object2 = new ArrayList();
        object = ((Checkout.ShippingRates)object).shippingRates.iterator();
        while (object.hasNext()) {
            object2.add(new ShippingRateListItemModel((Checkout.ShippingRate)object.next()));
        }
        this.listViewAdapter.swapItemsAndNotify((List<ListItemViewModel>)object2, new RecyclerViewAdapter.ItemComparator(){

            @Override
            public boolean equalsByContent(ListItemViewModel listItemViewModel, ListItemViewModel listItemViewModel2) {
                return listItemViewModel.payload().equals(listItemViewModel2);
            }

            @Override
            public boolean equalsById(ListItemViewModel listItemViewModel, ListItemViewModel listItemViewModel2) {
                return ((Checkout.ShippingRate)listItemViewModel.payload()).handle.equals(((Checkout.ShippingRate)listItemViewModel2.payload()).handle);
            }
        });
        this.show();
    }

    static final class ItemViewHolder
    extends ListItemViewHolder<Checkout.ShippingRate, ListItemViewModel<Checkout.ShippingRate>> {
        private static final NumberFormat CURRENCY_FORMAT = NumberFormat.getCurrencyInstance(ShopifyKeyManager.getInstance().getLocale());
        @BindView
        TextView priceView;
        @BindView
        TextView titleView;

        ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            super(onClickListener);
        }

        @Override
        public void bindModel(ListItemViewModel<Checkout.ShippingRate> listItemViewModel, int n) {
            super.bindModel(listItemViewModel, n);
            this.titleView.setText((CharSequence)listItemViewModel.payload().title);
            this.priceView.setText((CharSequence)CURRENCY_FORMAT.format(listItemViewModel.payload().price));
        }

        @OnClick
        void onRootViewClick() {
            this.notifyOnClickListener();
        }
    }

    public static interface OnShippingRateSelectListener {
        public void onShippingRateSelected(Checkout.ShippingRate var1);
    }

    private static final class ShippingRateListItemModel
    extends ListItemViewModel<Checkout.ShippingRate> {
        ShippingRateListItemModel(Checkout.ShippingRate shippingRate) {
            super(shippingRate, 2130968827);
        }

        @Override
        public ListItemViewHolder<Checkout.ShippingRate, ListItemViewModel<Checkout.ShippingRate>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            return new ItemViewHolder(onClickListener);
        }
    }

}

