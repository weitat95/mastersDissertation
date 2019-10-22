/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.FrameLayout
 *  android.widget.TextView
 */
package com.getqardio.android.shopify.view.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;
import butterknife.BindView;
import butterknife.ButterKnife;
import com.getqardio.android.shopify.domain.model.Cart;
import com.getqardio.android.shopify.domain.repository.CartRepository;
import com.getqardio.android.shopify.domain.repository.RealCartRepository;
import com.getqardio.android.shopify.util.WeakObserver;
import com.getqardio.android.shopify.view.widget.CartBadgeView$$Lambda$1;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableObserver;

public final class CartBadgeView
extends FrameLayout {
    private CartRepository cartRepository = new RealCartRepository();
    private Disposable cartSubscription;
    @BindView
    TextView countView;

    public CartBadgeView(Context context) {
        super(context);
    }

    public CartBadgeView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
    }

    public CartBadgeView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
    }

    static /* synthetic */ void access$lambda$0(CartBadgeView cartBadgeView, Cart cart) {
        cartBadgeView.onCartUpdate(cart);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void onCartUpdate(Cart cart) {
        int n = cart.totalQuantity();
        cart = this.countView;
        int n2 = n == 0 ? 4 : 0;
        cart.setVisibility(n2);
        this.countView.setText((CharSequence)String.valueOf(n));
    }

    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        this.cartSubscription = this.cartRepository.watch().subscribeOn(AndroidSchedulers.mainThread()).subscribeWith(WeakObserver.forTarget(this).delegateOnNext(CartBadgeView$$Lambda$1.lambdaFactory$()).create());
    }

    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (this.cartSubscription != null) {
            this.cartSubscription.dispose();
            this.cartSubscription = null;
        }
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind((View)this);
    }
}

