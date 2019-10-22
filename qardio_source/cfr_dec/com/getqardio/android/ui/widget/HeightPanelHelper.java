/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 *  android.text.Editable
 *  android.text.InputFilter
 *  android.text.TextUtils
 *  android.text.TextWatcher
 *  android.view.View
 *  android.widget.AdapterView
 *  android.widget.AdapterView$OnItemSelectedListener
 *  android.widget.ArrayAdapter
 *  android.widget.EditText
 *  android.widget.Spinner
 *  android.widget.SpinnerAdapter
 */
package com.getqardio.android.ui.widget;

import android.content.Context;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import com.getqardio.android.utils.MetricUtils;
import com.getqardio.android.utils.Utils;
import com.getqardio.android.utils.Validator;
import com.getqardio.android.utils.ui.DecimalInputFilter;

public class HeightPanelHelper {
    private EditText cmValue;
    private View feetContainer;
    private EditText feetValue;
    private AdapterView.OnItemSelectedListener heightItemSelectedListener = new AdapterView.OnItemSelectedListener(){

        /*
         * Enabled aggressive block sorting
         */
        public void onItemSelected(AdapterView<?> adapterView, View view, int n, long l) {
            int n2 = HeightPanelHelper.this.shownHeightUnit;
            switch (n) {
                case 0: {
                    HeightPanelHelper.this.shownHeightUnit = 1;
                }
                default: {
                    break;
                }
                case 1: {
                    HeightPanelHelper.this.shownHeightUnit = 0;
                }
            }
            HeightPanelHelper.this.recalculateHeight(n2, HeightPanelHelper.this.shownHeightUnit);
            HeightPanelHelper.this.updateView();
            HeightPanelHelper.this.onHeightChanged();
        }

        public void onNothingSelected(AdapterView<?> adapterView) {
        }
    };
    private HeightPanelCallback heightPanelCallback;
    private EditText inchValue;
    private int shownHeightUnit = 0;
    private Float shownHeightValue;
    private Spinner unitSpinner;
    private boolean watcherEnable = true;

    public HeightPanelHelper(View view, HeightPanelCallback heightPanelCallback) {
        this.init(view, heightPanelCallback, -1);
    }

    public HeightPanelHelper(View view, HeightPanelCallback heightPanelCallback, int n) {
        this.init(view, heightPanelCallback, n);
    }

    private void clearError() {
        this.cmValue.setError(null);
        this.inchValue.setError(null);
    }

    private Float getShownHeightCm() {
        String string2 = this.cmValue.getText().toString();
        if (!TextUtils.isEmpty((CharSequence)string2)) {
            return Utils.parseNumber(string2);
        }
        return null;
    }

    private Float getShownHeightFeet() {
        String string2 = this.feetValue.getText().toString();
        String string3 = this.inchValue.getText().toString();
        if (!TextUtils.isEmpty((CharSequence)string2) && !TextUtils.isEmpty((CharSequence)string3)) {
            float f;
            float f2;
            try {
                f = Integer.parseInt(string2);
                f2 = (float)Integer.parseInt(string3) / 12.0f;
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
            return Float.valueOf(f + f2);
        }
        return null;
    }

    /*
     * Enabled aggressive block sorting
     */
    private void init(View view, HeightPanelCallback heightPanelCallback, int n) {
        this.heightPanelCallback = heightPanelCallback;
        heightPanelCallback = view.getContext();
        this.unitSpinner = (Spinner)view.findViewById(2131821090);
        heightPanelCallback = n != -1 ? ArrayAdapter.createFromResource((Context)heightPanelCallback, (int)2131755012, (int)2130968774) : ArrayAdapter.createFromResource((Context)heightPanelCallback, (int)2131755012, (int)2130968782);
        heightPanelCallback.setDropDownViewResource(2130968781);
        this.unitSpinner.setAdapter((SpinnerAdapter)heightPanelCallback);
        this.unitSpinner.setSelection(1);
        this.unitSpinner.setOnItemSelectedListener(this.heightItemSelectedListener);
        this.feetContainer = view.findViewById(2131821091);
        this.cmValue = (EditText)view.findViewById(2131821094);
        this.feetValue = (EditText)view.findViewById(2131821092);
        this.inchValue = (EditText)view.findViewById(2131821093);
        this.cmValue.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                block3: {
                    block2: {
                        if (!HeightPanelHelper.this.watcherEnable) break block2;
                        HeightPanelHelper.this.shownHeightValue = HeightPanelHelper.this.recalculateShownHeight();
                        if (!HeightPanelHelper.this.isHeightValid(true)) break block3;
                        HeightPanelHelper.this.onHeightChanged();
                    }
                    return;
                }
                HeightPanelHelper.this.onHeightError();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.cmValue.setFilters(new InputFilter[]{new DecimalInputFilter()});
        this.feetValue.addTextChangedListener(new TextWatcher(){

            public void afterTextChanged(Editable editable) {
                block3: {
                    block2: {
                        if (!HeightPanelHelper.this.watcherEnable) break block2;
                        HeightPanelHelper.this.shownHeightValue = HeightPanelHelper.this.recalculateShownHeight();
                        if (!HeightPanelHelper.this.isHeightValid(true)) break block3;
                        HeightPanelHelper.this.onHeightChanged();
                    }
                    return;
                }
                HeightPanelHelper.this.onHeightError();
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
        this.inchValue.addTextChangedListener(new TextWatcher(){

            /*
             * Enabled aggressive block sorting
             * Enabled unnecessary exception pruning
             * Enabled aggressive exception aggregation
             */
            public void afterTextChanged(Editable editable) {
                if (HeightPanelHelper.this.watcherEnable) {
                    try {
                        if (Integer.parseInt(editable.toString()) > 11) {
                            editable.clear();
                            editable.append((CharSequence)Integer.toString(11));
                        }
                    }
                    catch (NumberFormatException numberFormatException) {}
                    HeightPanelHelper.this.shownHeightValue = HeightPanelHelper.this.recalculateShownHeight();
                    if (!HeightPanelHelper.this.isHeightValid(true)) {
                        HeightPanelHelper.this.onHeightError();
                        return;
                    }
                    HeightPanelHelper.this.onHeightChanged();
                }
            }

            public void beforeTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }

            public void onTextChanged(CharSequence charSequence, int n, int n2, int n3) {
            }
        });
    }

    private void onHeightChanged() {
        if (this.heightPanelCallback != null) {
            this.heightPanelCallback.onHeightChanged();
        }
    }

    private void onHeightError() {
        if (this.heightPanelCallback != null) {
            this.heightPanelCallback.onHeightError();
        }
    }

    private boolean profileHasHeight() {
        if (this.heightPanelCallback != null) {
            return this.heightPanelCallback.profileHasHeight();
        }
        return true;
    }

    private void recalculateHeight(int n, int n2) {
        if (n != this.shownHeightUnit && this.shownHeightValue != null) {
            this.shownHeightValue = Float.valueOf(MetricUtils.convertHeight(n, n2, this.shownHeightValue.floatValue()));
            this.shownHeightUnit = n2;
        }
    }

    private Float recalculateShownHeight() {
        if (this.shownHeightUnit == 1) {
            return this.getShownHeightFeet();
        }
        return this.getShownHeightCm();
    }

    private void showError(int n) {
        this.cmValue.setError((CharSequence)this.cmValue.getContext().getString(n));
        this.inchValue.setError((CharSequence)this.inchValue.getContext().getString(n));
    }

    /*
     * Enabled aggressive block sorting
     * Enabled unnecessary exception pruning
     * Enabled aggressive exception aggregation
     */
    private void updateView() {
        switch (this.shownHeightUnit) {
            case 1: {
                this.feetContainer.setVisibility(0);
                this.cmValue.setVisibility(4);
                break;
            }
            case 0: {
                this.cmValue.setVisibility(0);
                this.feetContainer.setVisibility(4);
            }
        }
        this.watcherEnable = false;
        try {
            if (this.shownHeightValue == null) return;
            if (this.shownHeightUnit == 1) {
                int n = this.shownHeightValue.intValue();
                String string2 = Integer.toString(n);
                this.feetValue.setText((CharSequence)string2);
                this.feetValue.setSelection(this.feetValue.length());
                n = Math.round((this.shownHeightValue.floatValue() - (float)n) * 12.0f);
                this.inchValue.setText((CharSequence)Integer.toString(n));
                return;
            }
            String string3 = String.format(Utils.getLocale(), "%.1f", this.shownHeightValue);
            this.cmValue.setText((CharSequence)string3);
            this.cmValue.setSelection(this.cmValue.length());
            return;
        }
        finally {
            this.watcherEnable = true;
        }
    }

    public Float getShownHeight() {
        return this.shownHeightValue;
    }

    public int getShownHeightUnit() {
        return this.shownHeightUnit;
    }

    /*
     * Enabled aggressive block sorting
     */
    public boolean isHeightValid(boolean bl) {
        Float f = this.getShownHeight();
        if (f == null && !this.profileHasHeight()) {
            if (!bl) return true;
            {
                this.clearError();
                return true;
            }
        } else {
            if (f == null) {
                if (!bl) return false;
                {
                    this.showError(2131362254);
                }
                return false;
            }
            if (!Validator.isHeightMaxValid(this.shownHeightUnit, f.floatValue())) {
                if (!bl) return false;
                {
                    this.showError(2131362256);
                }
                return false;
            }
            if (!Validator.isHeightMinValid(this.shownHeightUnit, f.floatValue())) {
                if (!bl) return false;
                {
                    this.showError(2131362254);
                }
                return false;
            }
            if (!bl) return true;
            {
                this.clearError();
                return true;
            }
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setHeight(float f, int n) {
        this.shownHeightUnit = n;
        this.shownHeightValue = Float.valueOf(f);
        switch (n) {
            default: {
                n = 1;
                break;
            }
            case 1: {
                n = 0;
                break;
            }
            case 0: {
                n = 1;
            }
        }
        this.unitSpinner.setOnItemSelectedListener(null);
        this.unitSpinner.setSelection(n);
        this.unitSpinner.setOnItemSelectedListener(this.heightItemSelectedListener);
        this.updateView();
    }

    public static interface HeightPanelCallback {
        public void onHeightChanged();

        public void onHeightError();

        public boolean profileHasHeight();
    }

}

