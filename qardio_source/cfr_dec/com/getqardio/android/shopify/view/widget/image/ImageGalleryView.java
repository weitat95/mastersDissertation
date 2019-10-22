/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.Rect
 *  android.util.AttributeSet
 *  android.view.View
 *  android.widget.FrameLayout
 */
package com.getqardio.android.shopify.view.widget.image;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Rect;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.PagerSnapHelper;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import com.getqardio.android.shopify.util.Util;
import com.getqardio.android.shopify.view.base.ListItemViewHolder;
import com.getqardio.android.shopify.view.base.ListItemViewModel;
import com.getqardio.android.shopify.view.base.RecyclerViewAdapter;
import com.getqardio.android.shopify.view.widget.image.ShopifyDraweeView;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class ImageGalleryView
extends FrameLayout
implements RecyclerViewAdapter.OnItemClickListener {
    private final RecyclerViewAdapter.ItemComparator itemComparator;
    private final RecyclerViewAdapter pagerAdapter = new RecyclerViewAdapter();
    private final RecyclerViewAdapter pagerIndicatorAdapter = new RecyclerViewAdapter(this);
    @BindView
    View pagerIndicatorFrameView;
    @BindView
    RecyclerView pagerIndicatorView;
    @BindView
    RecyclerView pagerView;

    public ImageGalleryView(Context context) {
        super(context);
        this.itemComparator = new RecyclerViewAdapter.ItemComparator(){

            @Override
            public boolean equalsByContent(ListItemViewModel listItemViewModel, ListItemViewModel listItemViewModel2) {
                return listItemViewModel.equals(listItemViewModel2);
            }

            @Override
            public boolean equalsById(ListItemViewModel listItemViewModel, ListItemViewModel listItemViewModel2) {
                return listItemViewModel.equals(listItemViewModel2);
            }
        };
    }

    public ImageGalleryView(Context context, AttributeSet attributeSet) {
        super(context, attributeSet);
        this.itemComparator = new /* invalid duplicate definition of identical inner class */;
    }

    public ImageGalleryView(Context context, AttributeSet attributeSet, int n) {
        super(context, attributeSet, n);
        this.itemComparator = new /* invalid duplicate definition of identical inner class */;
    }

    private void initPagerIndicator() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this.getContext(), 0, false);
        this.pagerIndicatorView.setLayoutManager(linearLayoutManager);
        this.pagerIndicatorView.setHasFixedSize(true);
        this.pagerIndicatorView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrolled(RecyclerView recyclerView, int n, int n2) {
                ImageGalleryView.this.pagerIndicatorFrameView.setTranslationX(ImageGalleryView.this.pagerIndicatorFrameView.getTranslationX() - (float)n);
            }
        });
        final int n = this.getResources().getDimensionPixelOffset(2131427466) / 2;
        this.pagerIndicatorView.addItemDecoration(new RecyclerView.ItemDecoration(){

            /*
             * Enabled aggressive block sorting
             */
            @Override
            public void getItemOffsets(Rect rect, View view, RecyclerView recyclerView, RecyclerView.State state) {
                int n4 = 0;
                int n2 = recyclerView.getChildAdapterPosition(view);
                if (n2 == -1) {
                    return;
                }
                int n3 = n2 == 0 ? n : 0;
                rect.left = n3;
                n3 = n4;
                if (recyclerView.getAdapter().getItemCount() > 1) {
                    n3 = n4;
                    if (n2 == recyclerView.getAdapter().getItemCount() - 1) {
                        n3 = n;
                    }
                }
                rect.right = n3;
            }
        });
        this.pagerIndicatorFrameView.setTranslationX((float)n);
        this.pagerIndicatorView.setAdapter(this.pagerIndicatorAdapter);
    }

    private void initPagerView() {
        this.pagerView.setLayoutManager(new LinearLayoutManager(this.getContext(), 0, false));
        this.pagerView.setHasFixedSize(true);
        this.pagerView.setAdapter(this.pagerAdapter);
        new PagerSnapHelper().attachToRecyclerView(this.pagerView);
        this.pagerView.addOnScrollListener(new RecyclerView.OnScrollListener(){

            @Override
            public void onScrolled(RecyclerView recyclerView, int n, int n2) {
                n2 = ((LinearLayoutManager)ImageGalleryView.this.pagerView.getLayoutManager()).findFirstVisibleItemPosition();
                recyclerView = ImageGalleryView.this.pagerView.getLayoutManager().findViewByPosition(n2);
                n2 = ((LinearLayoutManager)ImageGalleryView.this.pagerIndicatorView.getLayoutManager()).findFirstVisibleItemPosition();
                View view = ImageGalleryView.this.pagerIndicatorView.getLayoutManager().findViewByPosition(n2);
                float f = 1.0f * (float)n / (float)(ImageGalleryView.this.pagerView.getAdapter().getItemCount() * recyclerView.getWidth());
                ImageGalleryView.this.pagerIndicatorFrameView.setTranslationX(ImageGalleryView.this.pagerIndicatorFrameView.getTranslationX() + (float)view.getWidth() * f * (float)ImageGalleryView.this.pagerIndicatorView.getAdapter().getItemCount());
            }
        });
    }

    protected void onFinishInflate() {
        super.onFinishInflate();
        ButterKnife.bind((View)this);
        this.initPagerView();
        this.initPagerIndicator();
    }

    @Override
    public void onItemClick(ListItemViewModel listItemViewModel) {
        this.pagerView.smoothScrollToPosition(listItemViewModel.position());
    }

    public void renderImages(List<String> object) {
        Util.checkNotNull(object, "images == null");
        ArrayList<ListItemViewModel<Object>> arrayList = new ArrayList<ListItemViewModel>();
        Iterator<String> iterator = object.iterator();
        while (iterator.hasNext()) {
            arrayList.add(new PagerListItemModel(iterator.next()));
        }
        this.pagerAdapter.swapItemsAndNotify(arrayList, this.itemComparator);
        arrayList = new ArrayList();
        object = object.iterator();
        while (object.hasNext()) {
            arrayList.add(new PagerIndicatorListItemModel((String)object.next()));
        }
        this.pagerIndicatorAdapter.swapItemsAndNotify(arrayList, this.itemComparator);
    }

    static final class PagerIndicatorListItemModel
    extends ListItemViewModel<String> {
        PagerIndicatorListItemModel(String string2) {
            super(string2, 2130968725);
            Util.checkNotNull(string2, "image == null");
        }

        @Override
        public ListItemViewHolder<String, ListItemViewModel<String>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            return new PagerListItemModel.ItemViewHolder(onClickListener);
        }

        static final class ItemViewHolder
        extends ListItemViewHolder<String, ListItemViewModel<String>> {
            @BindView
            ShopifyDraweeView imageView;

            ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
                super(onClickListener);
            }

            @Override
            public void bindModel(ListItemViewModel<String> listItemViewModel, int n) {
                super.bindModel(listItemViewModel, n);
                this.imageView.loadShopifyImage(listItemViewModel.payload());
            }

            @OnClick
            void onImageClick() {
                this.onClickListener().onClick(this.itemModel());
            }
        }

    }

    static final class PagerListItemModel
    extends ListItemViewModel<String> {
        PagerListItemModel(String string2) {
            super(string2, 2130968726);
            Util.checkNotNull(string2, "image == null");
        }

        @Override
        public ListItemViewHolder<String, ListItemViewModel<String>> createViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
            return new ItemViewHolder(onClickListener);
        }

        static final class ItemViewHolder
        extends ListItemViewHolder<String, ListItemViewModel<String>> {
            @BindView
            ShopifyDraweeView imageView;

            ItemViewHolder(ListItemViewHolder.OnClickListener onClickListener) {
                super(onClickListener);
            }

            @Override
            public void bindModel(ListItemViewModel<String> listItemViewModel, int n) {
                super.bindModel(listItemViewModel, n);
                this.imageView.loadShopifyImage(listItemViewModel.payload());
            }

            @OnClick
            void onImageClick() {
                this.onClickListener().onClick(this.itemModel());
            }
        }

    }

}

