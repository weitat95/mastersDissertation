/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.content.Context
 *  android.content.ContextWrapper
 *  android.content.res.ColorStateList
 *  android.content.res.TypedArray
 *  android.graphics.PorterDuff
 *  android.graphics.PorterDuff$Mode
 *  android.util.AttributeSet
 *  android.util.Log
 *  android.view.InflateException
 *  android.view.Menu
 *  android.view.MenuInflater
 *  android.view.MenuItem
 *  android.view.MenuItem$OnMenuItemClickListener
 *  android.view.SubMenu
 *  android.view.View
 *  org.xmlpull.v1.XmlPullParser
 *  org.xmlpull.v1.XmlPullParserException
 */
package android.support.v7.view;

import android.app.Activity;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.support.v4.view.ActionProvider;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.appcompat.R;
import android.support.v7.view.menu.MenuItemImpl;
import android.support.v7.view.menu.MenuItemWrapperICS;
import android.support.v7.widget.DrawableUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.InflateException;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.SubMenu;
import android.view.View;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

public class SupportMenuInflater
extends MenuInflater {
    static final Class<?>[] ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE;
    static final Class<?>[] ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    final Object[] mActionProviderConstructorArguments;
    final Object[] mActionViewConstructorArguments;
    Context mContext;
    private Object mRealOwner;

    static {
        ACTION_VIEW_CONSTRUCTOR_SIGNATURE = new Class[]{Context.class};
        ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE = ACTION_VIEW_CONSTRUCTOR_SIGNATURE;
    }

    public SupportMenuInflater(Context context) {
        super(context);
        this.mContext = context;
        this.mActionViewConstructorArguments = new Object[]{context};
        this.mActionProviderConstructorArguments = this.mActionViewConstructorArguments;
    }

    /*
     * Enabled aggressive block sorting
     */
    private Object findRealOwner(Object object) {
        if (object instanceof Activity || !(object instanceof ContextWrapper)) {
            return object;
        }
        return this.findRealOwner((Object)((ContextWrapper)object).getBaseContext());
    }

    /*
     * Unable to fully structure code
     * Enabled aggressive block sorting
     */
    private void parseMenu(XmlPullParser var1_1, AttributeSet var2_2, Menu var3_3) throws XmlPullParserException, IOException {
        block26: {
            block27: {
                var10_4 = new MenuState((Menu)var3_3);
                var4_5 = var1_1.getEventType();
                var6_6 = 0;
                var9_7 = null;
                do {
                    if (var4_5 == 2) {
                        var3_3 = var1_1.getName();
                        if (!var3_3.equals("menu")) break block26;
                        var4_5 = var1_1.next();
                        break block27;
                    }
                    var4_5 = var5_8 = var1_1.next();
                } while (var5_8 != 1);
                var4_5 = var5_8;
            }
            var5_8 = 0;
            var8_9 = var4_5;
            block6: do {
                if (var5_8 != 0) {
                    return;
                }
                switch (var8_9) {
                    default: {
                        var3_3 = var9_7;
                        var7_10 = var5_8;
                        var4_5 = var6_6;
                        ** GOTO lbl90
                    }
                    case 2: {
                        var4_5 = var6_6;
                        var7_10 = var5_8;
                        var3_3 = var9_7;
                        if (var6_6 == 0) {
                            var3_3 = var1_1.getName();
                            if (var3_3.equals("group")) {
                                var10_4.readGroup(var2_2);
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                            } else if (var3_3.equals("item")) {
                                var10_4.readItem(var2_2);
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                            } else if (var3_3.equals("menu")) {
                                this.parseMenu(var1_1, var2_2, (Menu)var10_4.addSubMenuItem());
                                var4_5 = var6_6;
                                var7_10 = var5_8;
                                var3_3 = var9_7;
                            } else {
                                var4_5 = 1;
                                var7_10 = var5_8;
                            }
                        }
                        ** GOTO lbl90
                    }
                    case 3: {
                        var11_11 = var1_1.getName();
                        if (var6_6 != 0 && var11_11.equals(var9_7)) {
                            var4_5 = 0;
                            var3_3 = null;
                            var7_10 = var5_8;
                        } else if (var11_11.equals("group")) {
                            var10_4.resetGroup();
                            var4_5 = var6_6;
                            var7_10 = var5_8;
                            var3_3 = var9_7;
                        } else if (var11_11.equals("item")) {
                            var4_5 = var6_6;
                            var7_10 = var5_8;
                            var3_3 = var9_7;
                            if (!var10_4.hasAddedItem()) {
                                if (var10_4.itemActionProvider != null && var10_4.itemActionProvider.hasSubMenu()) {
                                    var10_4.addSubMenuItem();
                                    var4_5 = var6_6;
                                    var7_10 = var5_8;
                                    var3_3 = var9_7;
                                } else {
                                    var10_4.addItem();
                                    var4_5 = var6_6;
                                    var7_10 = var5_8;
                                    var3_3 = var9_7;
                                }
                            }
                        } else {
                            var4_5 = var6_6;
                            var7_10 = var5_8;
                            var3_3 = var9_7;
                            if (var11_11.equals("menu")) {
                                var7_10 = 1;
                                var4_5 = var6_6;
                                var3_3 = var9_7;
                            }
                        }
lbl90:
                        // 12 sources
                        var8_9 = var1_1.next();
                        var6_6 = var4_5;
                        var5_8 = var7_10;
                        var9_7 = var3_3;
                        continue block6;
                    }
                    case 1: 
                }
                break;
            } while (true);
            throw new RuntimeException("Unexpected end of document");
        }
        throw new RuntimeException("Expecting menu, got " + (String)var3_3);
    }

    Object getRealOwner() {
        if (this.mRealOwner == null) {
            this.mRealOwner = this.findRealOwner((Object)this.mContext);
        }
        return this.mRealOwner;
    }

    /*
     * Exception decompiling
     */
    public void inflate(int var1_1, Menu var2_2) {
        // This method has failed to decompile.  When submitting a bug report, please provide this stack trace, and (if you hold appropriate legal rights) the relevant class file.
        // org.benf.cfr.reader.util.ConfusedCFRException: Tried to end blocks [3[CATCHBLOCK]], but top level block is 5[CATCHBLOCK]
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.processEndingBlocks(Op04StructuredStatement.java:427)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op04StructuredStatement.buildNestedBlocks(Op04StructuredStatement.java:479)
        // org.benf.cfr.reader.bytecode.analysis.opgraph.Op03SimpleStatement.createInitialStructuredBlock(Op03SimpleStatement.java:619)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisInner(CodeAnalyser.java:750)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysisOrWrapFail(CodeAnalyser.java:238)
        // org.benf.cfr.reader.bytecode.CodeAnalyser.getAnalysis(CodeAnalyser.java:183)
        // org.benf.cfr.reader.entities.attributes.AttributeCode.analyse(AttributeCode.java:96)
        // org.benf.cfr.reader.entities.Method.analyse(Method.java:397)
        // org.benf.cfr.reader.entities.ClassFile.analyseMid(ClassFile.java:922)
        // org.benf.cfr.reader.entities.ClassFile.analyseTop(ClassFile.java:813)
        // org.benf.cfr.reader.Driver.doJarVersionTypes(Driver.java:239)
        // org.benf.cfr.reader.Driver.doJar(Driver.java:120)
        // org.benf.cfr.reader.CfrDriverImpl.analyse(CfrDriverImpl.java:65)
        // org.benf.cfr.reader.Main.main(Main.java:48)
        throw new IllegalStateException("Decompilation failed");
    }

    private static class InflatedOnMenuItemClickListener
    implements MenuItem.OnMenuItemClickListener {
        private static final Class<?>[] PARAM_TYPES = new Class[]{MenuItem.class};
        private Method mMethod;
        private Object mRealOwner;

        public InflatedOnMenuItemClickListener(Object object, String string2) {
            this.mRealOwner = object;
            Class<?> class_ = object.getClass();
            try {
                this.mMethod = class_.getMethod(string2, PARAM_TYPES);
                return;
            }
            catch (Exception exception) {
                string2 = new InflateException("Couldn't resolve menu item onClick handler " + string2 + " in class " + class_.getName());
                string2.initCause((Throwable)exception);
                throw string2;
            }
        }

        public boolean onMenuItemClick(MenuItem menuItem) {
            try {
                if (this.mMethod.getReturnType() == Boolean.TYPE) {
                    return (Boolean)this.mMethod.invoke(this.mRealOwner, new Object[]{menuItem});
                }
                this.mMethod.invoke(this.mRealOwner, new Object[]{menuItem});
                return true;
            }
            catch (Exception exception) {
                throw new RuntimeException(exception);
            }
        }
    }

    private class MenuState {
        private int groupCategory;
        private int groupCheckable;
        private boolean groupEnabled;
        private int groupId;
        private int groupOrder;
        private boolean groupVisible;
        ActionProvider itemActionProvider;
        private String itemActionProviderClassName;
        private String itemActionViewClassName;
        private int itemActionViewLayout;
        private boolean itemAdded;
        private int itemAlphabeticModifiers;
        private char itemAlphabeticShortcut;
        private int itemCategoryOrder;
        private int itemCheckable;
        private boolean itemChecked;
        private CharSequence itemContentDescription;
        private boolean itemEnabled;
        private int itemIconResId;
        private ColorStateList itemIconTintList = null;
        private PorterDuff.Mode itemIconTintMode = null;
        private int itemId;
        private String itemListenerMethodName;
        private int itemNumericModifiers;
        private char itemNumericShortcut;
        private int itemShowAsAction;
        private CharSequence itemTitle;
        private CharSequence itemTitleCondensed;
        private CharSequence itemTooltipText;
        private boolean itemVisible;
        private Menu menu;

        public MenuState(Menu menu2) {
            this.menu = menu2;
            this.resetGroup();
        }

        private char getShortcut(String string2) {
            if (string2 == null) {
                return '\u0000';
            }
            return string2.charAt(0);
        }

        private <T> T newInstance(String string2, Class<?>[] object, Object[] arrobject) {
            try {
                object = SupportMenuInflater.this.mContext.getClassLoader().loadClass(string2).getConstructor((Class<?>)object);
                object.setAccessible(true);
                object = object.newInstance(arrobject);
            }
            catch (Exception exception) {
                Log.w((String)"SupportMenuInflater", (String)("Cannot instantiate class: " + string2), (Throwable)exception);
                return null;
            }
            return (T)object;
        }

        /*
         * Enabled aggressive block sorting
         */
        private void setItem(MenuItem menuItem) {
            MenuItem menuItem2 = menuItem.setChecked(this.itemChecked).setVisible(this.itemVisible).setEnabled(this.itemEnabled);
            boolean bl = this.itemCheckable >= 1;
            menuItem2.setCheckable(bl).setTitleCondensed(this.itemTitleCondensed).setIcon(this.itemIconResId);
            if (this.itemShowAsAction >= 0) {
                menuItem.setShowAsAction(this.itemShowAsAction);
            }
            if (this.itemListenerMethodName != null) {
                if (SupportMenuInflater.this.mContext.isRestricted()) {
                    throw new IllegalStateException("The android:onClick attribute cannot be used within a restricted context");
                }
                menuItem.setOnMenuItemClickListener((MenuItem.OnMenuItemClickListener)new InflatedOnMenuItemClickListener(SupportMenuInflater.this.getRealOwner(), this.itemListenerMethodName));
            }
            if (menuItem instanceof MenuItemImpl) {
                menuItem2 = (MenuItemImpl)menuItem;
            }
            if (this.itemCheckable >= 2) {
                if (menuItem instanceof MenuItemImpl) {
                    ((MenuItemImpl)menuItem).setExclusiveCheckable(true);
                } else if (menuItem instanceof MenuItemWrapperICS) {
                    ((MenuItemWrapperICS)menuItem).setExclusiveCheckable(true);
                }
            }
            boolean bl2 = false;
            if (this.itemActionViewClassName != null) {
                menuItem.setActionView((View)this.newInstance(this.itemActionViewClassName, ACTION_VIEW_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionViewConstructorArguments));
                bl2 = true;
            }
            if (this.itemActionViewLayout > 0) {
                if (!bl2) {
                    menuItem.setActionView(this.itemActionViewLayout);
                } else {
                    Log.w((String)"SupportMenuInflater", (String)"Ignoring attribute 'itemActionViewLayout'. Action view already specified.");
                }
            }
            if (this.itemActionProvider != null) {
                MenuItemCompat.setActionProvider(menuItem, this.itemActionProvider);
            }
            MenuItemCompat.setContentDescription(menuItem, this.itemContentDescription);
            MenuItemCompat.setTooltipText(menuItem, this.itemTooltipText);
            MenuItemCompat.setAlphabeticShortcut(menuItem, this.itemAlphabeticShortcut, this.itemAlphabeticModifiers);
            MenuItemCompat.setNumericShortcut(menuItem, this.itemNumericShortcut, this.itemNumericModifiers);
            if (this.itemIconTintMode != null) {
                MenuItemCompat.setIconTintMode(menuItem, this.itemIconTintMode);
            }
            if (this.itemIconTintList != null) {
                MenuItemCompat.setIconTintList(menuItem, this.itemIconTintList);
            }
        }

        public void addItem() {
            this.itemAdded = true;
            this.setItem(this.menu.add(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle));
        }

        public SubMenu addSubMenuItem() {
            this.itemAdded = true;
            SubMenu subMenu = this.menu.addSubMenu(this.groupId, this.itemId, this.itemCategoryOrder, this.itemTitle);
            this.setItem(subMenu.getItem());
            return subMenu;
        }

        public boolean hasAddedItem() {
            return this.itemAdded;
        }

        public void readGroup(AttributeSet attributeSet) {
            attributeSet = SupportMenuInflater.this.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuGroup);
            this.groupId = attributeSet.getResourceId(R.styleable.MenuGroup_android_id, 0);
            this.groupCategory = attributeSet.getInt(R.styleable.MenuGroup_android_menuCategory, 0);
            this.groupOrder = attributeSet.getInt(R.styleable.MenuGroup_android_orderInCategory, 0);
            this.groupCheckable = attributeSet.getInt(R.styleable.MenuGroup_android_checkableBehavior, 0);
            this.groupVisible = attributeSet.getBoolean(R.styleable.MenuGroup_android_visible, true);
            this.groupEnabled = attributeSet.getBoolean(R.styleable.MenuGroup_android_enabled, true);
            attributeSet.recycle();
        }

        /*
         * Enabled aggressive block sorting
         */
        public void readItem(AttributeSet attributeSet) {
            int n;
            attributeSet = SupportMenuInflater.this.mContext.obtainStyledAttributes(attributeSet, R.styleable.MenuItem);
            this.itemId = attributeSet.getResourceId(R.styleable.MenuItem_android_id, 0);
            this.itemCategoryOrder = 0xFFFF0000 & attributeSet.getInt(R.styleable.MenuItem_android_menuCategory, this.groupCategory) | 0xFFFF & attributeSet.getInt(R.styleable.MenuItem_android_orderInCategory, this.groupOrder);
            this.itemTitle = attributeSet.getText(R.styleable.MenuItem_android_title);
            this.itemTitleCondensed = attributeSet.getText(R.styleable.MenuItem_android_titleCondensed);
            this.itemIconResId = attributeSet.getResourceId(R.styleable.MenuItem_android_icon, 0);
            this.itemAlphabeticShortcut = this.getShortcut(attributeSet.getString(R.styleable.MenuItem_android_alphabeticShortcut));
            this.itemAlphabeticModifiers = attributeSet.getInt(R.styleable.MenuItem_alphabeticModifiers, 4096);
            this.itemNumericShortcut = this.getShortcut(attributeSet.getString(R.styleable.MenuItem_android_numericShortcut));
            this.itemNumericModifiers = attributeSet.getInt(R.styleable.MenuItem_numericModifiers, 4096);
            if (attributeSet.hasValue(R.styleable.MenuItem_android_checkable)) {
                n = attributeSet.getBoolean(R.styleable.MenuItem_android_checkable, false) ? 1 : 0;
                this.itemCheckable = n;
            } else {
                this.itemCheckable = this.groupCheckable;
            }
            this.itemChecked = attributeSet.getBoolean(R.styleable.MenuItem_android_checked, false);
            this.itemVisible = attributeSet.getBoolean(R.styleable.MenuItem_android_visible, this.groupVisible);
            this.itemEnabled = attributeSet.getBoolean(R.styleable.MenuItem_android_enabled, this.groupEnabled);
            this.itemShowAsAction = attributeSet.getInt(R.styleable.MenuItem_showAsAction, -1);
            this.itemListenerMethodName = attributeSet.getString(R.styleable.MenuItem_android_onClick);
            this.itemActionViewLayout = attributeSet.getResourceId(R.styleable.MenuItem_actionLayout, 0);
            this.itemActionViewClassName = attributeSet.getString(R.styleable.MenuItem_actionViewClass);
            this.itemActionProviderClassName = attributeSet.getString(R.styleable.MenuItem_actionProviderClass);
            n = this.itemActionProviderClassName != null ? 1 : 0;
            if (n != 0 && this.itemActionViewLayout == 0 && this.itemActionViewClassName == null) {
                this.itemActionProvider = (ActionProvider)this.newInstance(this.itemActionProviderClassName, ACTION_PROVIDER_CONSTRUCTOR_SIGNATURE, SupportMenuInflater.this.mActionProviderConstructorArguments);
            } else {
                if (n != 0) {
                    Log.w((String)"SupportMenuInflater", (String)"Ignoring attribute 'actionProviderClass'. Action view already specified.");
                }
                this.itemActionProvider = null;
            }
            this.itemContentDescription = attributeSet.getText(R.styleable.MenuItem_contentDescription);
            this.itemTooltipText = attributeSet.getText(R.styleable.MenuItem_tooltipText);
            this.itemIconTintMode = attributeSet.hasValue(R.styleable.MenuItem_iconTintMode) ? DrawableUtils.parseTintMode(attributeSet.getInt(R.styleable.MenuItem_iconTintMode, -1), this.itemIconTintMode) : null;
            this.itemIconTintList = attributeSet.hasValue(R.styleable.MenuItem_iconTint) ? attributeSet.getColorStateList(R.styleable.MenuItem_iconTint) : null;
            attributeSet.recycle();
            this.itemAdded = false;
        }

        public void resetGroup() {
            this.groupId = 0;
            this.groupCategory = 0;
            this.groupOrder = 0;
            this.groupCheckable = 0;
            this.groupVisible = true;
            this.groupEnabled = true;
        }
    }

}

