/*
 * Decompiled with CFR 0.147.
 */
package com.getqardio.android.utils.engagement;

import com.getqardio.android.utils.engagement.EngagementScreenItem;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class EngagementScreenItemsHelper {
    public static List<EngagementScreenItem> items = new ArrayList<EngagementScreenItem>(){
        {
            this.add(new EngagementScreenItem(2131362498, 2130837699));
            this.add(new EngagementScreenItem(2131362500, 2130837701));
            this.add(new EngagementScreenItem(2131362501, 2130837702));
            this.add(new EngagementScreenItem(2131362502, 2130837703));
            this.add(new EngagementScreenItem(2131362503, 2130837704));
            this.add(new EngagementScreenItem(2131362504, 2130837705));
            this.add(new EngagementScreenItem(2131362505, 2130837706));
            this.add(new EngagementScreenItem(2131362506, 2130837707));
            this.add(new EngagementScreenItem(2131362507, 2130837708));
            this.add(new EngagementScreenItem(2131362499, 2130837700));
        }
    };

    public static EngagementScreenItem getRandomItem() {
        int n = new Random().nextInt(items.size());
        return items.get(n);
    }

}

