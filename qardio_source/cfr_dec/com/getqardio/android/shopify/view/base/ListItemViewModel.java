/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.shopify.view.base;

import com.getqardio.android.shopify.view.base.ListItemViewHolder;

public abstract class ListItemViewModel<T> {
    private final T payload;
    private int position;
    private final int viewType;

    public ListItemViewModel(T t, int n) {
        this.payload = t;
        this.viewType = n;
    }

    public void bindView(ListItemViewHolder<T, ListItemViewModel<T>> listItemViewHolder, int n) {
        this.position = n;
        listItemViewHolder.bindModel(this, n);
    }

    public abstract ListItemViewHolder<T, ListItemViewModel<T>> createViewHolder(ListItemViewHolder.OnClickListener var1);

    /*
     * Enabled aggressive block sorting
     */
    public boolean equals(Object object) {
        block7: {
            block6: {
                if (this == object) break block6;
                if (!(object instanceof ListItemViewModel)) {
                    return false;
                }
                object = (ListItemViewModel)object;
                if (this.viewType != ((ListItemViewModel)object).viewType) {
                    return false;
                }
                if (this.payload != null) {
                    return this.payload.equals(((ListItemViewModel)object).payload);
                }
                if (((ListItemViewModel)object).payload != null) break block7;
            }
            return true;
        }
        return false;
    }

    public boolean equalsByContent(ListItemViewModel listItemViewModel) {
        return false;
    }

    public boolean equalsById(ListItemViewModel listItemViewModel) {
        return false;
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public int hashCode() {
        int n;
        if (this.payload != null) {
            n = this.payload.hashCode();
            do {
                return n * 31 + this.viewType;
                break;
            } while (true);
        }
        n = 0;
        return n * 31 + this.viewType;
    }

    public long itemId() {
        if (this.payload == null) {
            return 0L;
        }
        return this.payload().hashCode();
    }

    public T payload() {
        return this.payload;
    }

    public int position() {
        return this.position;
    }

    public int viewType() {
        return this.viewType;
    }
}

