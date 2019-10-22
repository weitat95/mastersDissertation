/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.PendingIntent
 *  android.app.SearchableInfo
 *  android.content.ActivityNotFoundException
 *  android.content.ComponentName
 *  android.content.Context
 *  android.content.Intent
 *  android.content.pm.PackageManager
 *  android.content.pm.ResolveInfo
 *  android.content.res.Configuration
 *  android.content.res.Resources
 *  android.database.Cursor
 *  android.graphics.Rect
 *  android.graphics.drawable.Drawable
 *  android.graphics.drawable.Drawable$ConstantState
 *  android.net.Uri
 *  android.os.Bundle
 *  android.os.IBinder
 *  android.os.Parcel
 *  android.os.Parcelable
 *  android.os.Parcelable$ClassLoaderCreator
 *  android.os.Parcelable$Creator
 *  android.text.Editable
 *  android.text.SpannableStringBuilder
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.text.style.ImageSpan
 *  android.util.AttributeSet
 *  android.util.DisplayMetrics
 *  android.util.Log
 *  android.util.TypedValue
 *  android.view.KeyEvent
 *  android.view.KeyEvent$DispatcherState
 *  android.view.LayoutInflater
 *  android.view.MotionEvent
 *  android.view.TouchDelegate
 *  android.view.View
 *  android.view.View$MeasureSpec
 *  android.view.View$OnClickListener
 *  android.view.View$OnFocusChangeListener
 *  android.view.View$OnKeyListener
 *  android.view.View$OnLayoutChangeListener
 *  android.view.ViewConfiguration
 *  android.view.ViewGroup
 *  android.view.inputmethod.EditorInfo
 *  android.view.inputmethod.InputConnection
 *  android.view.inputmethod.InputMethodManager
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemClickListener
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.AutoCompleteTextView
 *  android.widget.ImageView
 *  android.widget.ListAdapter
 *  android.widget.TextView
 *  android.widget.TextView$OnEditorActionListener
 */
package android.support.v7.widget;

import android.app.PendingIntent;
import android.app.SearchableInfo;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.IBinder;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v4.view.AbsSavedState;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.CursorAdapter;
import android.support.v7.appcompat.R;
import android.support.v7.view.CollapsibleActionView;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.SuggestionsAdapter;
import android.support.v7.widget.TintTypedArray;
import android.support.v7.widget.TooltipCompat;
import android.support.v7.widget.ViewUtils;
import android.text.Editable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.style.ImageSpan;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.TouchDelegate;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.TextView;
import java.lang.reflect.Method;
import java.util.WeakHashMap;

public class SearchView
extends LinearLayoutCompat
implements CollapsibleActionView {
    static final AutoCompleteTextViewReflector HIDDEN_METHOD_INVOKER = new AutoCompleteTextViewReflector();
    private Bundle mAppSearchData;
    private boolean mClearingFocus;
    final ImageView mCloseButton;
    private final ImageView mCollapsedIcon;
    private int mCollapsedImeOptions;
    private final CharSequence mDefaultQueryHint;
    private final View mDropDownAnchor;
    private boolean mExpandedInActionView;
    final ImageView mGoButton;
    private boolean mIconified;
    private boolean mIconifiedByDefault;
    private int mMaxWidth;
    private CharSequence mOldQueryText;
    private final View.OnClickListener mOnClickListener;
    private OnCloseListener mOnCloseListener;
    private final TextView.OnEditorActionListener mOnEditorActionListener;
    private final AdapterView.OnItemClickListener mOnItemClickListener;
    private final AdapterView.OnItemSelectedListener mOnItemSelectedListener;
    private OnQueryTextListener mOnQueryChangeListener;
    View.OnFocusChangeListener mOnQueryTextFocusChangeListener;
    private View.OnClickListener mOnSearchClickListener;
    private OnSuggestionListener mOnSuggestionListener;
    private final WeakHashMap<String, Drawable.ConstantState> mOutsideDrawablesCache;
    private CharSequence mQueryHint;
    private boolean mQueryRefinement;
    private Runnable mReleaseCursorRunnable;
    final ImageView mSearchButton;
    private final View mSearchEditFrame;
    private final Drawable mSearchHintIcon;
    private final View mSearchPlate;
    final SearchAutoComplete mSearchSrcTextView;
    private Rect mSearchSrcTextViewBounds = new Rect();
    private Rect mSearchSrtTextViewBoundsExpanded = new Rect();
    SearchableInfo mSearchable;
    private final View mSubmitArea;
    private boolean mSubmitButtonEnabled;
    private final int mSuggestionCommitIconResId;
    private final int mSuggestionRowLayout;
    CursorAdapter mSuggestionsAdapter;
    private int[] mTemp = new int[2];
    private int[] mTemp2 = new int[2];
    View.OnKeyListener mTextKeyListener;
    private TextWatcher mTextWatcher;
    private UpdatableTouchDelegate mTouchDelegate;
    private final Runnable mUpdateDrawableStateRunnable = new Runnable(){

        @Override
        public void run() {
            SearchView.this.updateFocusedState();
        }
    };
    private CharSequence mUserQuery;
    private final Intent mVoiceAppSearchIntent;
    final ImageView mVoiceButton;
    private boolean mVoiceButtonEnabled;
    private final Intent mVoiceWebSearchIntent;

    public SearchView(Context context) {
        this(context, null);
    }

    public SearchView(Context context, AttributeSet attributeSet) {
        this(context, attributeSet, R.attr.searchViewStyle);
    }

    public SearchView(Context context, AttributeSet object, int n) {
        super(context, (AttributeSet)object, n);
        this.mReleaseCursorRunnable = new Runnable(){

            @Override
            public void run() {
                if (SearchView.this.mSuggestionsAdapter != null && SearchView.this.mSuggestionsAdapter instanceof SuggestionsAdapter) {
                    SearchView.this.mSuggestionsAdapter.changeCursor(null);
                }
            }
        };
        this.mOutsideDrawablesCache = new WeakHashMap();
        this.mOnClickListener = new View.OnClickListener(){

            /*
             * Enabled aggressive block sorting
             */
            public void onClick(View view) {
                if (view == SearchView.this.mSearchButton) {
                    SearchView.this.onSearchClicked();
                    return;
                } else {
                    if (view == SearchView.this.mCloseButton) {
                        SearchView.this.onCloseClicked();
                        return;
                    }
                    if (view == SearchView.this.mGoButton) {
                        SearchView.this.onSubmitQuery();
                        return;
                    }
                    if (view == SearchView.this.mVoiceButton) {
                        SearchView.this.onVoiceClicked();
                        return;
                    }
                    if (view != SearchView.this.mSearchSrcTextView) return;
                    {
                        SearchView.this.forceSuggestionQuery();
                        return;
                    }
                }
            }
        };
        this.mTextKeyListener = new View.OnKeyListener(){

            /*
             * Enabled aggressive block sorting
             */
            public boolean onKey(View view, int n, KeyEvent keyEvent) {
                block5: {
                    block4: {
                        if (SearchView.this.mSearchable == null) break block4;
                        if (SearchView.this.mSearchSrcTextView.isPopupShowing() && SearchView.this.mSearchSrcTextView.getListSelection() != -1) {
                            return SearchView.this.onSuggestionsKey(view, n, keyEvent);
                        }
                        if (!SearchView.this.mSearchSrcTextView.isEmpty() && keyEvent.hasNoModifiers() && keyEvent.getAction() == 1 && n == 66) break block5;
                    }
                    return false;
                }
                view.cancelLongPress();
                SearchView.this.launchQuerySearch(0, null, SearchView.this.mSearchSrcTextView.getText().toString());
                return true;
            }
        };
        this.mOnEditorActionListener = new TextView.OnEditorActionListener(){

            public boolean onEditorAction(TextView textView, int n, KeyEvent keyEvent) {
                SearchView.this.onSubmitQuery();
                return true;
            }
        };
        this.mOnItemClickListener = new AdapterView.OnItemClickListener(){

            public void onItemClick(AdapterView<?> adapterView, View view, int n, long l) {
                SearchView.this.onItemClicked(n, 0, null);
            }
        };
        this.mOnItemSelectedListener = new AdapterView.OnItemSelectedListener(){

            public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
                SearchView.this.onItemSelected(n);
            }

            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        };
        this.mTextWatcher = new TextWatcher(){

            public void afterTextChanged(Editable editable) {
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
                SearchView.this.onTextChanged(charSequence);
            }
        };
        object = TintTypedArray.obtainStyledAttributes(context, (AttributeSet)object, R.styleable.SearchView, n, 0);
        LayoutInflater.from((Context)context).inflate(((TintTypedArray)object).getResourceId(R.styleable.SearchView_layout, R.layout.abc_search_view), (ViewGroup)this, true);
        this.mSearchSrcTextView = (SearchAutoComplete)this.findViewById(R.id.search_src_text);
        this.mSearchSrcTextView.setSearchView(this);
        this.mSearchEditFrame = this.findViewById(R.id.search_edit_frame);
        this.mSearchPlate = this.findViewById(R.id.search_plate);
        this.mSubmitArea = this.findViewById(R.id.submit_area);
        this.mSearchButton = (ImageView)this.findViewById(R.id.search_button);
        this.mGoButton = (ImageView)this.findViewById(R.id.search_go_btn);
        this.mCloseButton = (ImageView)this.findViewById(R.id.search_close_btn);
        this.mVoiceButton = (ImageView)this.findViewById(R.id.search_voice_btn);
        this.mCollapsedIcon = (ImageView)this.findViewById(R.id.search_mag_icon);
        ViewCompat.setBackground(this.mSearchPlate, ((TintTypedArray)object).getDrawable(R.styleable.SearchView_queryBackground));
        ViewCompat.setBackground(this.mSubmitArea, ((TintTypedArray)object).getDrawable(R.styleable.SearchView_submitBackground));
        this.mSearchButton.setImageDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_searchIcon));
        this.mGoButton.setImageDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_goIcon));
        this.mCloseButton.setImageDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_closeIcon));
        this.mVoiceButton.setImageDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_voiceIcon));
        this.mCollapsedIcon.setImageDrawable(((TintTypedArray)object).getDrawable(R.styleable.SearchView_searchIcon));
        this.mSearchHintIcon = ((TintTypedArray)object).getDrawable(R.styleable.SearchView_searchHintIcon);
        TooltipCompat.setTooltipText((View)this.mSearchButton, this.getResources().getString(R.string.abc_searchview_description_search));
        this.mSuggestionRowLayout = ((TintTypedArray)object).getResourceId(R.styleable.SearchView_suggestionRowLayout, R.layout.abc_search_dropdown_item_icons_2line);
        this.mSuggestionCommitIconResId = ((TintTypedArray)object).getResourceId(R.styleable.SearchView_commitIcon, 0);
        this.mSearchButton.setOnClickListener(this.mOnClickListener);
        this.mCloseButton.setOnClickListener(this.mOnClickListener);
        this.mGoButton.setOnClickListener(this.mOnClickListener);
        this.mVoiceButton.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.setOnClickListener(this.mOnClickListener);
        this.mSearchSrcTextView.addTextChangedListener(this.mTextWatcher);
        this.mSearchSrcTextView.setOnEditorActionListener(this.mOnEditorActionListener);
        this.mSearchSrcTextView.setOnItemClickListener(this.mOnItemClickListener);
        this.mSearchSrcTextView.setOnItemSelectedListener(this.mOnItemSelectedListener);
        this.mSearchSrcTextView.setOnKeyListener(this.mTextKeyListener);
        this.mSearchSrcTextView.setOnFocusChangeListener(new View.OnFocusChangeListener(){

            public void onFocusChange(View view, boolean bl) {
                if (SearchView.this.mOnQueryTextFocusChangeListener != null) {
                    SearchView.this.mOnQueryTextFocusChangeListener.onFocusChange((View)SearchView.this, bl);
                }
            }
        });
        this.setIconifiedByDefault(((TintTypedArray)object).getBoolean(R.styleable.SearchView_iconifiedByDefault, true));
        n = ((TintTypedArray)object).getDimensionPixelSize(R.styleable.SearchView_android_maxWidth, -1);
        if (n != -1) {
            this.setMaxWidth(n);
        }
        this.mDefaultQueryHint = ((TintTypedArray)object).getText(R.styleable.SearchView_defaultQueryHint);
        this.mQueryHint = ((TintTypedArray)object).getText(R.styleable.SearchView_queryHint);
        n = ((TintTypedArray)object).getInt(R.styleable.SearchView_android_imeOptions, -1);
        if (n != -1) {
            this.setImeOptions(n);
        }
        if ((n = ((TintTypedArray)object).getInt(R.styleable.SearchView_android_inputType, -1)) != -1) {
            this.setInputType(n);
        }
        this.setFocusable(((TintTypedArray)object).getBoolean(R.styleable.SearchView_android_focusable, true));
        ((TintTypedArray)object).recycle();
        this.mVoiceWebSearchIntent = new Intent("android.speech.action.WEB_SEARCH");
        this.mVoiceWebSearchIntent.addFlags(268435456);
        this.mVoiceWebSearchIntent.putExtra("android.speech.extra.LANGUAGE_MODEL", "web_search");
        this.mVoiceAppSearchIntent = new Intent("android.speech.action.RECOGNIZE_SPEECH");
        this.mVoiceAppSearchIntent.addFlags(268435456);
        this.mDropDownAnchor = this.findViewById(this.mSearchSrcTextView.getDropDownAnchor());
        if (this.mDropDownAnchor != null) {
            this.mDropDownAnchor.addOnLayoutChangeListener(new View.OnLayoutChangeListener(){

                public void onLayoutChange(View view, int n, int n2, int n3, int n4, int n5, int n6, int n7, int n8) {
                    SearchView.this.adjustDropDownSizeAndPosition();
                }
            });
        }
        this.updateViewsVisibility(this.mIconifiedByDefault);
        this.updateQueryHint();
    }

    private Intent createIntent(String string2, Uri uri, String string3, String string4, int n, String string5) {
        string2 = new Intent(string2);
        string2.addFlags(268435456);
        if (uri != null) {
            string2.setData(uri);
        }
        string2.putExtra("user_query", this.mUserQuery);
        if (string4 != null) {
            string2.putExtra("query", string4);
        }
        if (string3 != null) {
            string2.putExtra("intent_extra_data_key", string3);
        }
        if (this.mAppSearchData != null) {
            string2.putExtra("app_data", this.mAppSearchData);
        }
        if (n != 0) {
            string2.putExtra("action_key", n);
            string2.putExtra("action_msg", string5);
        }
        string2.setComponent(this.mSearchable.getSearchActivity());
        return string2;
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private Intent createIntentFromSuggestion(Cursor cursor, int n, String string2) {
        String string3;
        String string4;
        String string5;
        try {
            string5 = string4 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_action");
            if (string4 == null) {
                string5 = this.mSearchable.getSuggestIntentAction();
            }
        }
        catch (RuntimeException runtimeException) {
            try {
                n = cursor.getPosition();
            }
            catch (RuntimeException runtimeException2) {
                n = -1;
            }
            Log.w((String)"SearchView", (String)("Search suggestions cursor at row " + n + " returned exception."), (Throwable)runtimeException);
            return null;
        }
        string4 = string5;
        if (string5 == null) {
            string4 = "android.intent.action.SEARCH";
        }
        string5 = string3 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_data");
        if (string3 == null) {
            string5 = this.mSearchable.getSuggestIntentData();
        }
        string3 = string5;
        if (string5 != null) {
            String string6 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_data_id");
            string3 = string5;
            if (string6 != null) {
                string3 = string5 + "/" + Uri.encode((String)string6);
            }
        }
        string5 = string3 == null ? null : Uri.parse((String)string3);
        string3 = SuggestionsAdapter.getColumnString(cursor, "suggest_intent_query");
        return this.createIntent(string4, (Uri)string5, SuggestionsAdapter.getColumnString(cursor, "suggest_intent_extra_data"), string3, n, string2);
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private Intent createVoiceAppSearchIntent(Intent object, SearchableInfo searchableInfo) {
        void var1_6;
        void var1_4;
        void var4_13;
        void var2_8;
        ComponentName componentName = var2_8.getSearchActivity();
        Intent intent = new Intent("android.intent.action.SEARCH");
        intent.setComponent(componentName);
        PendingIntent pendingIntent = PendingIntent.getActivity((Context)this.getContext(), (int)0, (Intent)intent, (int)1073741824);
        Bundle bundle = new Bundle();
        if (this.mAppSearchData != null) {
            bundle.putParcelable("app_data", (Parcelable)this.mAppSearchData);
        }
        Intent intent2 = new Intent(object);
        String string2 = "free_form";
        Object var4_11 = null;
        String string3 = null;
        int n = 1;
        Resources resources = this.getResources();
        if (var2_8.getVoiceLanguageModeId() != 0) {
            String string4 = resources.getString(var2_8.getVoiceLanguageModeId());
        }
        if (var2_8.getVoicePromptTextId() != 0) {
            String string5 = resources.getString(var2_8.getVoicePromptTextId());
        }
        if (var2_8.getVoiceLanguageId() != 0) {
            string3 = resources.getString(var2_8.getVoiceLanguageId());
        }
        if (var2_8.getVoiceMaxResults() != 0) {
            n = var2_8.getVoiceMaxResults();
        }
        intent2.putExtra("android.speech.extra.LANGUAGE_MODEL", (String)var1_4);
        intent2.putExtra("android.speech.extra.PROMPT", (String)var4_13);
        intent2.putExtra("android.speech.extra.LANGUAGE", string3);
        intent2.putExtra("android.speech.extra.MAX_RESULTS", n);
        if (componentName == null) {
            Object var1_5 = null;
        } else {
            String string6 = componentName.flattenToShortString();
        }
        intent2.putExtra("calling_package", (String)var1_6);
        intent2.putExtra("android.speech.extra.RESULTS_PENDINGINTENT", (Parcelable)pendingIntent);
        intent2.putExtra("android.speech.extra.RESULTS_PENDINGINTENT_BUNDLE", bundle);
        return intent2;
    }

    /*
     * WARNING - void declaration
     * Enabled aggressive block sorting
     */
    private Intent createVoiceWebSearchIntent(Intent object, SearchableInfo searchableInfo) {
        void var2_6;
        void var1_4;
        Intent intent = new Intent(object);
        ComponentName componentName = var2_6.getSearchActivity();
        if (componentName == null) {
            Object var1_3 = null;
        } else {
            String string2 = componentName.flattenToShortString();
        }
        intent.putExtra("calling_package", (String)var1_4);
        return intent;
    }

    private void dismissSuggestions() {
        this.mSearchSrcTextView.dismissDropDown();
    }

    private void getChildBoundsWithinSearchView(View view, Rect rect) {
        view.getLocationInWindow(this.mTemp);
        this.getLocationInWindow(this.mTemp2);
        int n = this.mTemp[1] - this.mTemp2[1];
        int n2 = this.mTemp[0] - this.mTemp2[0];
        rect.set(n2, n, view.getWidth() + n2, view.getHeight() + n);
    }

    private CharSequence getDecoratedHint(CharSequence charSequence) {
        if (!this.mIconifiedByDefault || this.mSearchHintIcon == null) {
            return charSequence;
        }
        int n = (int)((double)this.mSearchSrcTextView.getTextSize() * 1.25);
        this.mSearchHintIcon.setBounds(0, 0, n, n);
        SpannableStringBuilder spannableStringBuilder = new SpannableStringBuilder((CharSequence)"   ");
        spannableStringBuilder.setSpan((Object)new ImageSpan(this.mSearchHintIcon), 1, 2, 33);
        spannableStringBuilder.append(charSequence);
        return spannableStringBuilder;
    }

    private int getPreferredHeight() {
        return this.getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_height);
    }

    private int getPreferredWidth() {
        return this.getContext().getResources().getDimensionPixelSize(R.dimen.abc_search_view_preferred_width);
    }

    /*
     * Enabled aggressive block sorting
     */
    private boolean hasVoiceSearch() {
        boolean bl;
        boolean bl2 = bl = false;
        if (this.mSearchable == null) return bl2;
        bl2 = bl;
        if (!this.mSearchable.getVoiceSearchEnabled()) return bl2;
        Intent intent = null;
        if (this.mSearchable.getVoiceSearchLaunchWebSearch()) {
            intent = this.mVoiceWebSearchIntent;
        } else if (this.mSearchable.getVoiceSearchLaunchRecognizer()) {
            intent = this.mVoiceAppSearchIntent;
        }
        bl2 = bl;
        if (intent == null) return bl2;
        bl2 = bl;
        if (this.getContext().getPackageManager().resolveActivity(intent, 65536) == null) return bl2;
        return true;
    }

    static boolean isLandscapeMode(Context context) {
        return context.getResources().getConfiguration().orientation == 2;
    }

    private boolean isSubmitAreaEnabled() {
        return (this.mSubmitButtonEnabled || this.mVoiceButtonEnabled) && !this.isIconified();
    }

    private void launchIntent(Intent intent) {
        if (intent == null) {
            return;
        }
        try {
            this.getContext().startActivity(intent);
            return;
        }
        catch (RuntimeException runtimeException) {
            Log.e((String)"SearchView", (String)("Failed launch activity: " + (Object)intent), (Throwable)runtimeException);
            return;
        }
    }

    private boolean launchSuggestion(int n, int n2, String string2) {
        Cursor cursor = this.mSuggestionsAdapter.getCursor();
        if (cursor != null && cursor.moveToPosition(n)) {
            this.launchIntent(this.createIntentFromSuggestion(cursor, n2, string2));
            return true;
        }
        return false;
    }

    private void postUpdateFocusedState() {
        this.post(this.mUpdateDrawableStateRunnable);
    }

    private void rewriteQueryFromSuggestion(int n) {
        Editable editable = this.mSearchSrcTextView.getText();
        Object object = this.mSuggestionsAdapter.getCursor();
        if (object == null) {
            return;
        }
        if (object.moveToPosition(n)) {
            if ((object = this.mSuggestionsAdapter.convertToString((Cursor)object)) != null) {
                this.setQuery((CharSequence)object);
                return;
            }
            this.setQuery((CharSequence)editable);
            return;
        }
        this.setQuery((CharSequence)editable);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void setQuery(CharSequence charSequence) {
        this.mSearchSrcTextView.setText(charSequence);
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        int n = TextUtils.isEmpty((CharSequence)charSequence) ? 0 : charSequence.length();
        searchAutoComplete.setSelection(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateCloseButton() {
        int n = 1;
        int n2 = 0;
        boolean bl = !TextUtils.isEmpty((CharSequence)this.mSearchSrcTextView.getText());
        int n3 = n;
        if (!bl) {
            n3 = this.mIconifiedByDefault && !this.mExpandedInActionView ? n : 0;
        }
        int[] arrn = this.mCloseButton;
        n3 = n3 != 0 ? n2 : 8;
        arrn.setVisibility(n3);
        Drawable drawable2 = this.mCloseButton.getDrawable();
        if (drawable2 != null) {
            arrn = bl ? ENABLED_STATE_SET : EMPTY_STATE_SET;
            drawable2.setState(arrn);
        }
    }

    private void updateQueryHint() {
        CharSequence charSequence = this.getQueryHint();
        SearchAutoComplete searchAutoComplete = this.mSearchSrcTextView;
        CharSequence charSequence2 = charSequence;
        if (charSequence == null) {
            charSequence2 = "";
        }
        searchAutoComplete.setHint(this.getDecoratedHint(charSequence2));
    }

    private void updateSearchAutoComplete() {
        int n;
        int n2 = 1;
        this.mSearchSrcTextView.setThreshold(this.mSearchable.getSuggestThreshold());
        this.mSearchSrcTextView.setImeOptions(this.mSearchable.getImeOptions());
        int n3 = n = this.mSearchable.getInputType();
        if ((n & 0xF) == 1) {
            n3 = n &= 0xFFFEFFFF;
            if (this.mSearchable.getSuggestAuthority() != null) {
                n3 = n | 0x10000 | 0x80000;
            }
        }
        this.mSearchSrcTextView.setInputType(n3);
        if (this.mSuggestionsAdapter != null) {
            this.mSuggestionsAdapter.changeCursor(null);
        }
        if (this.mSearchable.getSuggestAuthority() != null) {
            this.mSuggestionsAdapter = new SuggestionsAdapter(this.getContext(), this, this.mSearchable, this.mOutsideDrawablesCache);
            this.mSearchSrcTextView.setAdapter((ListAdapter)this.mSuggestionsAdapter);
            SuggestionsAdapter suggestionsAdapter = (SuggestionsAdapter)this.mSuggestionsAdapter;
            n3 = n2;
            if (this.mQueryRefinement) {
                n3 = 2;
            }
            suggestionsAdapter.setQueryRefinement(n3);
        }
    }

    private void updateSubmitArea() {
        int n;
        block2: {
            block3: {
                int n2;
                n = n2 = 8;
                if (!this.isSubmitAreaEnabled()) break block2;
                if (this.mGoButton.getVisibility() == 0) break block3;
                n = n2;
                if (this.mVoiceButton.getVisibility() != 0) break block2;
            }
            n = 0;
        }
        this.mSubmitArea.setVisibility(n);
    }

    private void updateSubmitButton(boolean bl) {
        int n;
        block2: {
            block3: {
                int n2;
                n = n2 = 8;
                if (!this.mSubmitButtonEnabled) break block2;
                n = n2;
                if (!this.isSubmitAreaEnabled()) break block2;
                n = n2;
                if (!this.hasFocus()) break block2;
                if (bl) break block3;
                n = n2;
                if (this.mVoiceButtonEnabled) break block2;
            }
            n = 0;
        }
        this.mGoButton.setVisibility(n);
    }

    /*
     * Enabled aggressive block sorting
     */
    private void updateViewsVisibility(boolean bl) {
        int n = 8;
        boolean bl2 = true;
        this.mIconified = bl;
        int n2 = bl ? 0 : 8;
        boolean bl3 = !TextUtils.isEmpty((CharSequence)this.mSearchSrcTextView.getText());
        this.mSearchButton.setVisibility(n2);
        this.updateSubmitButton(bl3);
        View view = this.mSearchEditFrame;
        n2 = bl ? n : 0;
        view.setVisibility(n2);
        n2 = this.mCollapsedIcon.getDrawable() == null || this.mIconifiedByDefault ? 8 : 0;
        this.mCollapsedIcon.setVisibility(n2);
        this.updateCloseButton();
        bl = !bl3 ? bl2 : false;
        this.updateVoiceButton(bl);
        this.updateSubmitArea();
    }

    private void updateVoiceButton(boolean bl) {
        int n;
        int n2 = n = 8;
        if (this.mVoiceButtonEnabled) {
            n2 = n;
            if (!this.isIconified()) {
                n2 = n;
                if (bl) {
                    n2 = 0;
                    this.mGoButton.setVisibility(8);
                }
            }
        }
        this.mVoiceButton.setVisibility(n2);
    }

    /*
     * Enabled aggressive block sorting
     */
    void adjustDropDownSizeAndPosition() {
        if (this.mDropDownAnchor.getWidth() > 1) {
            Resources resources = this.getContext().getResources();
            int n = this.mSearchPlate.getPaddingLeft();
            Rect rect = new Rect();
            boolean bl = ViewUtils.isLayoutRtl((View)this);
            int n2 = this.mIconifiedByDefault ? resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_icon_width) + resources.getDimensionPixelSize(R.dimen.abc_dropdownitem_text_padding_left) : 0;
            this.mSearchSrcTextView.getDropDownBackground().getPadding(rect);
            int n3 = bl ? -rect.left : n - (rect.left + n2);
            this.mSearchSrcTextView.setDropDownHorizontalOffset(n3);
            n3 = this.mDropDownAnchor.getWidth();
            int n4 = rect.left;
            int n5 = rect.right;
            this.mSearchSrcTextView.setDropDownWidth(n3 + n4 + n5 + n2 - n);
        }
    }

    public void clearFocus() {
        this.mClearingFocus = true;
        super.clearFocus();
        this.mSearchSrcTextView.clearFocus();
        this.mSearchSrcTextView.setImeVisibility(false);
        this.mClearingFocus = false;
    }

    void forceSuggestionQuery() {
        HIDDEN_METHOD_INVOKER.doBeforeTextChanged(this.mSearchSrcTextView);
        HIDDEN_METHOD_INVOKER.doAfterTextChanged(this.mSearchSrcTextView);
    }

    public int getImeOptions() {
        return this.mSearchSrcTextView.getImeOptions();
    }

    public int getInputType() {
        return this.mSearchSrcTextView.getInputType();
    }

    public int getMaxWidth() {
        return this.mMaxWidth;
    }

    public CharSequence getQuery() {
        return this.mSearchSrcTextView.getText();
    }

    public CharSequence getQueryHint() {
        if (this.mQueryHint != null) {
            return this.mQueryHint;
        }
        if (this.mSearchable != null && this.mSearchable.getHintId() != 0) {
            return this.getContext().getText(this.mSearchable.getHintId());
        }
        return this.mDefaultQueryHint;
    }

    int getSuggestionCommitIconResId() {
        return this.mSuggestionCommitIconResId;
    }

    int getSuggestionRowLayout() {
        return this.mSuggestionRowLayout;
    }

    public CursorAdapter getSuggestionsAdapter() {
        return this.mSuggestionsAdapter;
    }

    public boolean isIconified() {
        return this.mIconified;
    }

    void launchQuerySearch(int n, String string2, String string3) {
        string2 = this.createIntent("android.intent.action.SEARCH", null, null, string3, n, string2);
        this.getContext().startActivity((Intent)string2);
    }

    @Override
    public void onActionViewCollapsed() {
        this.setQuery("", false);
        this.clearFocus();
        this.updateViewsVisibility(true);
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions);
        this.mExpandedInActionView = false;
    }

    @Override
    public void onActionViewExpanded() {
        if (this.mExpandedInActionView) {
            return;
        }
        this.mExpandedInActionView = true;
        this.mCollapsedImeOptions = this.mSearchSrcTextView.getImeOptions();
        this.mSearchSrcTextView.setImeOptions(this.mCollapsedImeOptions | 0x2000000);
        this.mSearchSrcTextView.setText((CharSequence)"");
        this.setIconified(false);
    }

    void onCloseClicked() {
        if (TextUtils.isEmpty((CharSequence)this.mSearchSrcTextView.getText())) {
            if (this.mIconifiedByDefault && (this.mOnCloseListener == null || !this.mOnCloseListener.onClose())) {
                this.clearFocus();
                this.updateViewsVisibility(true);
            }
            return;
        }
        this.mSearchSrcTextView.setText((CharSequence)"");
        this.mSearchSrcTextView.requestFocus();
        this.mSearchSrcTextView.setImeVisibility(true);
    }

    protected void onDetachedFromWindow() {
        this.removeCallbacks(this.mUpdateDrawableStateRunnable);
        this.post(this.mReleaseCursorRunnable);
        super.onDetachedFromWindow();
    }

    boolean onItemClicked(int n, int n2, String string2) {
        boolean bl = false;
        if (this.mOnSuggestionListener == null || !this.mOnSuggestionListener.onSuggestionClick(n)) {
            this.launchSuggestion(n, 0, null);
            this.mSearchSrcTextView.setImeVisibility(false);
            this.dismissSuggestions();
            bl = true;
        }
        return bl;
    }

    boolean onItemSelected(int n) {
        if (this.mOnSuggestionListener == null || !this.mOnSuggestionListener.onSuggestionSelect(n)) {
            this.rewriteQueryFromSuggestion(n);
            return true;
        }
        return false;
    }

    @Override
    protected void onLayout(boolean bl, int n, int n2, int n3, int n4) {
        block3: {
            block2: {
                super.onLayout(bl, n, n2, n3, n4);
                if (!bl) break block2;
                this.getChildBoundsWithinSearchView((View)this.mSearchSrcTextView, this.mSearchSrcTextViewBounds);
                this.mSearchSrtTextViewBoundsExpanded.set(this.mSearchSrcTextViewBounds.left, 0, this.mSearchSrcTextViewBounds.right, n4 - n2);
                if (this.mTouchDelegate != null) break block3;
                this.mTouchDelegate = new UpdatableTouchDelegate(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds, (View)this.mSearchSrcTextView);
                this.setTouchDelegate((TouchDelegate)this.mTouchDelegate);
            }
            return;
        }
        this.mTouchDelegate.setBounds(this.mSearchSrtTextViewBoundsExpanded, this.mSearchSrcTextViewBounds);
    }

    /*
     * Enabled aggressive block sorting
     */
    @Override
    protected void onMeasure(int n, int n2) {
        if (this.isIconified()) {
            super.onMeasure(n, n2);
            return;
        }
        int n3 = View.MeasureSpec.getMode((int)n);
        int n4 = View.MeasureSpec.getSize((int)n);
        switch (n3) {
            default: {
                n = n4;
                break;
            }
            case Integer.MIN_VALUE: {
                if (this.mMaxWidth > 0) {
                    n = Math.min(this.mMaxWidth, n4);
                    break;
                }
                n = Math.min(this.getPreferredWidth(), n4);
                break;
            }
            case 1073741824: {
                n = n4;
                if (this.mMaxWidth <= 0) break;
                n = Math.min(this.mMaxWidth, n4);
                break;
            }
            case 0: {
                n = this.mMaxWidth > 0 ? this.mMaxWidth : this.getPreferredWidth();
            }
        }
        n4 = View.MeasureSpec.getMode((int)n2);
        n2 = View.MeasureSpec.getSize((int)n2);
        switch (n4) {
            case Integer.MIN_VALUE: {
                n2 = Math.min(this.getPreferredHeight(), n2);
            }
            default: {
                break;
            }
            case 0: {
                n2 = this.getPreferredHeight();
            }
        }
        super.onMeasure(View.MeasureSpec.makeMeasureSpec((int)n, (int)1073741824), View.MeasureSpec.makeMeasureSpec((int)n2, (int)1073741824));
    }

    void onQueryRefine(CharSequence charSequence) {
        this.setQuery(charSequence);
    }

    protected void onRestoreInstanceState(Parcelable parcelable) {
        if (!(parcelable instanceof SavedState)) {
            super.onRestoreInstanceState(parcelable);
            return;
        }
        parcelable = (SavedState)parcelable;
        super.onRestoreInstanceState(parcelable.getSuperState());
        this.updateViewsVisibility(parcelable.isIconified);
        this.requestLayout();
    }

    protected Parcelable onSaveInstanceState() {
        SavedState savedState = new SavedState(super.onSaveInstanceState());
        savedState.isIconified = this.isIconified();
        return savedState;
    }

    void onSearchClicked() {
        this.updateViewsVisibility(false);
        this.mSearchSrcTextView.requestFocus();
        this.mSearchSrcTextView.setImeVisibility(true);
        if (this.mOnSearchClickListener != null) {
            this.mOnSearchClickListener.onClick((View)this);
        }
    }

    void onSubmitQuery() {
        Editable editable = this.mSearchSrcTextView.getText();
        if (!(editable == null || TextUtils.getTrimmedLength((CharSequence)editable) <= 0 || this.mOnQueryChangeListener != null && this.mOnQueryChangeListener.onQueryTextSubmit(editable.toString()))) {
            if (this.mSearchable != null) {
                this.launchQuerySearch(0, null, editable.toString());
            }
            this.mSearchSrcTextView.setImeVisibility(false);
            this.dismissSuggestions();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    boolean onSuggestionsKey(View view, int n, KeyEvent keyEvent) {
        block6: {
            block5: {
                if (this.mSearchable == null || this.mSuggestionsAdapter == null || keyEvent.getAction() != 0 || !keyEvent.hasNoModifiers()) break block5;
                if (n == 66 || n == 84 || n == 61) {
                    return this.onItemClicked(this.mSearchSrcTextView.getListSelection(), 0, null);
                }
                if (n == 21 || n == 22) {
                    n = n == 21 ? 0 : this.mSearchSrcTextView.length();
                    this.mSearchSrcTextView.setSelection(n);
                    this.mSearchSrcTextView.setListSelection(0);
                    this.mSearchSrcTextView.clearListSelection();
                    HIDDEN_METHOD_INVOKER.ensureImeVisible(this.mSearchSrcTextView, true);
                    return true;
                }
                if (n == 19 && this.mSearchSrcTextView.getListSelection() == 0) break block6;
            }
            return false;
        }
        return false;
    }

    /*
     * Enabled aggressive block sorting
     */
    void onTextChanged(CharSequence charSequence) {
        boolean bl = true;
        Editable editable = this.mSearchSrcTextView.getText();
        this.mUserQuery = editable;
        boolean bl2 = !TextUtils.isEmpty((CharSequence)editable);
        this.updateSubmitButton(bl2);
        bl2 = !bl2 ? bl : false;
        this.updateVoiceButton(bl2);
        this.updateCloseButton();
        this.updateSubmitArea();
        if (this.mOnQueryChangeListener != null && !TextUtils.equals((CharSequence)charSequence, (CharSequence)this.mOldQueryText)) {
            this.mOnQueryChangeListener.onQueryTextChange(charSequence.toString());
        }
        this.mOldQueryText = charSequence.toString();
    }

    void onTextFocusChanged() {
        this.updateViewsVisibility(this.isIconified());
        this.postUpdateFocusedState();
        if (this.mSearchSrcTextView.hasFocus()) {
            this.forceSuggestionQuery();
        }
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    void onVoiceClicked() {
        SearchableInfo searchableInfo;
        block6: {
            block5: {
                if (this.mSearchable != null) {
                    searchableInfo = this.mSearchable;
                    try {
                        if (searchableInfo.getVoiceSearchLaunchWebSearch()) {
                            searchableInfo = this.createVoiceWebSearchIntent(this.mVoiceWebSearchIntent, searchableInfo);
                            this.getContext().startActivity((Intent)searchableInfo);
                            return;
                        }
                        if (!searchableInfo.getVoiceSearchLaunchRecognizer()) break block5;
                        break block6;
                    }
                    catch (ActivityNotFoundException activityNotFoundException) {
                        Log.w((String)"SearchView", (String)"Could not find voice search activity");
                        return;
                    }
                }
            }
            return;
        }
        searchableInfo = this.createVoiceAppSearchIntent(this.mVoiceAppSearchIntent, searchableInfo);
        this.getContext().startActivity((Intent)searchableInfo);
    }

    public void onWindowFocusChanged(boolean bl) {
        super.onWindowFocusChanged(bl);
        this.postUpdateFocusedState();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    public boolean requestFocus(int n, Rect rect) {
        boolean bl;
        if (this.mClearingFocus) {
            return false;
        }
        if (!this.isFocusable()) {
            return false;
        }
        if (this.isIconified()) return super.requestFocus(n, rect);
        boolean bl2 = bl = this.mSearchSrcTextView.requestFocus(n, rect);
        if (!bl) return bl2;
        this.updateViewsVisibility(false);
        return bl;
    }

    public void setAppSearchData(Bundle bundle) {
        this.mAppSearchData = bundle;
    }

    public void setIconified(boolean bl) {
        if (bl) {
            this.onCloseClicked();
            return;
        }
        this.onSearchClicked();
    }

    public void setIconifiedByDefault(boolean bl) {
        if (this.mIconifiedByDefault == bl) {
            return;
        }
        this.mIconifiedByDefault = bl;
        this.updateViewsVisibility(bl);
        this.updateQueryHint();
    }

    public void setImeOptions(int n) {
        this.mSearchSrcTextView.setImeOptions(n);
    }

    public void setInputType(int n) {
        this.mSearchSrcTextView.setInputType(n);
    }

    public void setMaxWidth(int n) {
        this.mMaxWidth = n;
        this.requestLayout();
    }

    public void setOnCloseListener(OnCloseListener onCloseListener) {
        this.mOnCloseListener = onCloseListener;
    }

    public void setOnQueryTextFocusChangeListener(View.OnFocusChangeListener onFocusChangeListener) {
        this.mOnQueryTextFocusChangeListener = onFocusChangeListener;
    }

    public void setOnQueryTextListener(OnQueryTextListener onQueryTextListener) {
        this.mOnQueryChangeListener = onQueryTextListener;
    }

    public void setOnSearchClickListener(View.OnClickListener onClickListener) {
        this.mOnSearchClickListener = onClickListener;
    }

    public void setOnSuggestionListener(OnSuggestionListener onSuggestionListener) {
        this.mOnSuggestionListener = onSuggestionListener;
    }

    public void setQuery(CharSequence charSequence, boolean bl) {
        this.mSearchSrcTextView.setText(charSequence);
        if (charSequence != null) {
            this.mSearchSrcTextView.setSelection(this.mSearchSrcTextView.length());
            this.mUserQuery = charSequence;
        }
        if (bl && !TextUtils.isEmpty((CharSequence)charSequence)) {
            this.onSubmitQuery();
        }
    }

    public void setQueryHint(CharSequence charSequence) {
        this.mQueryHint = charSequence;
        this.updateQueryHint();
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setQueryRefinementEnabled(boolean bl) {
        this.mQueryRefinement = bl;
        if (this.mSuggestionsAdapter instanceof SuggestionsAdapter) {
            SuggestionsAdapter suggestionsAdapter = (SuggestionsAdapter)this.mSuggestionsAdapter;
            int n = bl ? 2 : 1;
            suggestionsAdapter.setQueryRefinement(n);
        }
    }

    public void setSearchableInfo(SearchableInfo searchableInfo) {
        this.mSearchable = searchableInfo;
        if (this.mSearchable != null) {
            this.updateSearchAutoComplete();
            this.updateQueryHint();
        }
        this.mVoiceButtonEnabled = this.hasVoiceSearch();
        if (this.mVoiceButtonEnabled) {
            this.mSearchSrcTextView.setPrivateImeOptions("nm");
        }
        this.updateViewsVisibility(this.isIconified());
    }

    public void setSubmitButtonEnabled(boolean bl) {
        this.mSubmitButtonEnabled = bl;
        this.updateViewsVisibility(this.isIconified());
    }

    public void setSuggestionsAdapter(CursorAdapter cursorAdapter) {
        this.mSuggestionsAdapter = cursorAdapter;
        this.mSearchSrcTextView.setAdapter((ListAdapter)this.mSuggestionsAdapter);
    }

    /*
     * Enabled aggressive block sorting
     */
    void updateFocusedState() {
        int[] arrn = this.mSearchSrcTextView.hasFocus() ? FOCUSED_STATE_SET : EMPTY_STATE_SET;
        Drawable drawable2 = this.mSearchPlate.getBackground();
        if (drawable2 != null) {
            drawable2.setState(arrn);
        }
        if ((drawable2 = this.mSubmitArea.getBackground()) != null) {
            drawable2.setState(arrn);
        }
        this.invalidate();
    }

    private static class AutoCompleteTextViewReflector {
        private Method doAfterTextChanged;
        private Method doBeforeTextChanged;
        private Method ensureImeVisible;

        /*
         * Enabled aggressive block sorting
         * Enabled unnecessary exception pruning
         * Enabled aggressive exception aggregation
         */
        AutoCompleteTextViewReflector() {
            try {
                this.doBeforeTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doBeforeTextChanged", new Class[0]);
                this.doBeforeTextChanged.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            try {
                this.doAfterTextChanged = AutoCompleteTextView.class.getDeclaredMethod("doAfterTextChanged", new Class[0]);
                this.doAfterTextChanged.setAccessible(true);
            }
            catch (NoSuchMethodException noSuchMethodException) {}
            try {
                this.ensureImeVisible = AutoCompleteTextView.class.getMethod("ensureImeVisible", Boolean.TYPE);
                this.ensureImeVisible.setAccessible(true);
                return;
            }
            catch (NoSuchMethodException noSuchMethodException) {
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void doAfterTextChanged(AutoCompleteTextView autoCompleteTextView) {
            if (this.doAfterTextChanged == null) return;
            try {
                this.doAfterTextChanged.invoke((Object)autoCompleteTextView, new Object[0]);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void doBeforeTextChanged(AutoCompleteTextView autoCompleteTextView) {
            if (this.doBeforeTextChanged == null) return;
            try {
                this.doBeforeTextChanged.invoke((Object)autoCompleteTextView, new Object[0]);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }

        /*
         * Enabled force condition propagation
         * Lifted jumps to return sites
         */
        void ensureImeVisible(AutoCompleteTextView autoCompleteTextView, boolean bl) {
            if (this.ensureImeVisible == null) return;
            try {
                this.ensureImeVisible.invoke((Object)autoCompleteTextView, bl);
                return;
            }
            catch (Exception exception) {
                return;
            }
        }
    }

    public static interface OnCloseListener {
        public boolean onClose();
    }

    public static interface OnQueryTextListener {
        public boolean onQueryTextChange(String var1);

        public boolean onQueryTextSubmit(String var1);
    }

    public static interface OnSuggestionListener {
        public boolean onSuggestionClick(int var1);

        public boolean onSuggestionSelect(int var1);
    }

    static class SavedState
    extends AbsSavedState {
        public static final Parcelable.Creator<SavedState> CREATOR = new Parcelable.ClassLoaderCreator<SavedState>(){

            public SavedState createFromParcel(Parcel parcel) {
                return new SavedState(parcel, null);
            }

            public SavedState createFromParcel(Parcel parcel, ClassLoader classLoader) {
                return new SavedState(parcel, classLoader);
            }

            public SavedState[] newArray(int n) {
                return new SavedState[n];
            }
        };
        boolean isIconified;

        public SavedState(Parcel parcel, ClassLoader classLoader) {
            super(parcel, classLoader);
            this.isIconified = (Boolean)parcel.readValue(null);
        }

        SavedState(Parcelable parcelable) {
            super(parcelable);
        }

        public String toString() {
            return "SearchView.SavedState{" + Integer.toHexString(System.identityHashCode(this)) + " isIconified=" + this.isIconified + "}";
        }

        @Override
        public void writeToParcel(Parcel parcel, int n) {
            super.writeToParcel(parcel, n);
            parcel.writeValue((Object)this.isIconified);
        }

    }

    public static class SearchAutoComplete
    extends AppCompatAutoCompleteTextView {
        private boolean mHasPendingShowSoftInputRequest;
        final Runnable mRunShowSoftInputIfNecessary = new Runnable(){

            @Override
            public void run() {
                SearchAutoComplete.this.showSoftInputIfNecessary();
            }
        };
        private SearchView mSearchView;
        private int mThreshold = this.getThreshold();

        public SearchAutoComplete(Context context) {
            this(context, null);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet) {
            this(context, attributeSet, R.attr.autoCompleteTextViewStyle);
        }

        public SearchAutoComplete(Context context, AttributeSet attributeSet, int n) {
            super(context, attributeSet, n);
        }

        private int getSearchViewTextMinWidthDp() {
            Configuration configuration = this.getResources().getConfiguration();
            int n = configuration.screenWidthDp;
            int n2 = configuration.screenHeightDp;
            if (n >= 960 && n2 >= 720 && configuration.orientation == 2) {
                return 256;
            }
            if (n >= 600 || n >= 640 && n2 >= 480) {
                return 192;
            }
            return 160;
        }

        private boolean isEmpty() {
            return TextUtils.getTrimmedLength((CharSequence)this.getText()) == 0;
        }

        private void setImeVisibility(boolean bl) {
            InputMethodManager inputMethodManager = (InputMethodManager)this.getContext().getSystemService("input_method");
            if (!bl) {
                this.mHasPendingShowSoftInputRequest = false;
                this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
                inputMethodManager.hideSoftInputFromWindow(this.getWindowToken(), 0);
                return;
            }
            if (inputMethodManager.isActive((View)this)) {
                this.mHasPendingShowSoftInputRequest = false;
                this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
                inputMethodManager.showSoftInput((View)this, 0);
                return;
            }
            this.mHasPendingShowSoftInputRequest = true;
        }

        private void showSoftInputIfNecessary() {
            if (this.mHasPendingShowSoftInputRequest) {
                ((InputMethodManager)this.getContext().getSystemService("input_method")).showSoftInput((View)this, 0);
                this.mHasPendingShowSoftInputRequest = false;
            }
        }

        public boolean enoughToFilter() {
            return this.mThreshold <= 0 || super.enoughToFilter();
        }

        public InputConnection onCreateInputConnection(EditorInfo editorInfo) {
            editorInfo = super.onCreateInputConnection(editorInfo);
            if (this.mHasPendingShowSoftInputRequest) {
                this.removeCallbacks(this.mRunShowSoftInputIfNecessary);
                this.post(this.mRunShowSoftInputIfNecessary);
            }
            return editorInfo;
        }

        protected void onFinishInflate() {
            super.onFinishInflate();
            DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
            this.setMinWidth((int)TypedValue.applyDimension((int)1, (float)this.getSearchViewTextMinWidthDp(), (DisplayMetrics)displayMetrics));
        }

        protected void onFocusChanged(boolean bl, int n, Rect rect) {
            super.onFocusChanged(bl, n, rect);
            this.mSearchView.onTextFocusChanged();
        }

        public boolean onKeyPreIme(int n, KeyEvent keyEvent) {
            if (n == 4) {
                if (keyEvent.getAction() == 0 && keyEvent.getRepeatCount() == 0) {
                    KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                    if (dispatcherState != null) {
                        dispatcherState.startTracking(keyEvent, (Object)this);
                    }
                    return true;
                }
                if (keyEvent.getAction() == 1) {
                    KeyEvent.DispatcherState dispatcherState = this.getKeyDispatcherState();
                    if (dispatcherState != null) {
                        dispatcherState.handleUpEvent(keyEvent);
                    }
                    if (keyEvent.isTracking() && !keyEvent.isCanceled()) {
                        this.mSearchView.clearFocus();
                        this.setImeVisibility(false);
                        return true;
                    }
                }
            }
            return super.onKeyPreIme(n, keyEvent);
        }

        public void onWindowFocusChanged(boolean bl) {
            super.onWindowFocusChanged(bl);
            if (bl && this.mSearchView.hasFocus() && this.getVisibility() == 0) {
                this.mHasPendingShowSoftInputRequest = true;
                if (SearchView.isLandscapeMode(this.getContext())) {
                    HIDDEN_METHOD_INVOKER.ensureImeVisible(this, true);
                }
            }
        }

        public void performCompletion() {
        }

        protected void replaceText(CharSequence charSequence) {
        }

        void setSearchView(SearchView searchView) {
            this.mSearchView = searchView;
        }

        public void setThreshold(int n) {
            super.setThreshold(n);
            this.mThreshold = n;
        }

    }

    private static class UpdatableTouchDelegate
    extends TouchDelegate {
        private final Rect mActualBounds;
        private boolean mDelegateTargeted;
        private final View mDelegateView;
        private final int mSlop;
        private final Rect mSlopBounds;
        private final Rect mTargetBounds;

        public UpdatableTouchDelegate(Rect rect, Rect rect2, View view) {
            super(rect, view);
            this.mSlop = ViewConfiguration.get((Context)view.getContext()).getScaledTouchSlop();
            this.mTargetBounds = new Rect();
            this.mSlopBounds = new Rect();
            this.mActualBounds = new Rect();
            this.setBounds(rect, rect2);
            this.mDelegateView = view;
        }

        /*
         * Enabled aggressive block sorting
         */
        public boolean onTouchEvent(MotionEvent motionEvent) {
            boolean bl;
            int n = (int)motionEvent.getX();
            int n2 = (int)motionEvent.getY();
            boolean bl2 = false;
            boolean bl3 = true;
            boolean bl4 = false;
            switch (motionEvent.getAction()) {
                default: {
                    bl = bl3;
                    break;
                }
                case 0: {
                    bl = bl3;
                    if (!this.mTargetBounds.contains(n, n2)) break;
                    this.mDelegateTargeted = true;
                    bl2 = true;
                    bl = bl3;
                    break;
                }
                case 1: 
                case 2: {
                    boolean bl5 = this.mDelegateTargeted;
                    bl = bl3;
                    bl2 = bl5;
                    if (!bl5) break;
                    bl = bl3;
                    bl2 = bl5;
                    if (this.mSlopBounds.contains(n, n2)) break;
                    bl = false;
                    bl2 = bl5;
                    break;
                }
                case 3: {
                    bl2 = this.mDelegateTargeted;
                    this.mDelegateTargeted = false;
                    bl = bl3;
                }
            }
            if (!bl2) return bl4;
            if (bl && !this.mActualBounds.contains(n, n2)) {
                motionEvent.setLocation((float)(this.mDelegateView.getWidth() / 2), (float)(this.mDelegateView.getHeight() / 2));
                return this.mDelegateView.dispatchTouchEvent(motionEvent);
            } else {
                motionEvent.setLocation((float)(n - this.mActualBounds.left), (float)(n2 - this.mActualBounds.top));
            }
            return this.mDelegateView.dispatchTouchEvent(motionEvent);
        }

        public void setBounds(Rect rect, Rect rect2) {
            this.mTargetBounds.set(rect);
            this.mSlopBounds.set(rect);
            this.mSlopBounds.inset(-this.mSlop, -this.mSlop);
            this.mActualBounds.set(rect2);
        }
    }

}

