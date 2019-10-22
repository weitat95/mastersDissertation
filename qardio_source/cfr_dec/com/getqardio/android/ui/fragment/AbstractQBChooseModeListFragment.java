/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.app.Activity
 *  android.app.Fragment
 *  android.content.Context
 *  android.content.res.Resources
 *  android.graphics.drawable.Drawable
 *  android.os.Bundle
 *  android.view.LayoutInflater
 *  android.view.View
 *  android.view.View$OnClickListener
 *  android.view.ViewGroup
 *  android.widget.Button
 *  android.widget.ImageView
 *  android.widget.TextView
 */
package com.getqardio.android.ui.fragment;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import com.getqardio.android.baseble.QardioBaseDevice;
import com.getqardio.android.ui.fragment.AbstractQBChooseModeListFragment$$Lambda$1;
import com.getqardio.android.ui.fragment.AbstractQBChooseModeListFragment$SelectModeAdapter$ModeHolder$$Lambda$1;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.QBOnboardingDataProvider;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public abstract class AbstractQBChooseModeListFragment
extends AbstractQBOnboardingFragment {
    protected QBOnboardingDataProvider dataProvider;
    protected Button doneButton;
    protected SelectModeAdapter modesListAdapter;
    protected QardioBaseDevice.BaseMode selectedMode;

    private List<OperationModel> prepareSupportedOperationModes() {
        ArrayList<OperationModel> arrayList = new ArrayList<OperationModel>(4);
        arrayList.add(new OperationModel(this.getString(2131361989), this.getString(2131361988), false, QardioBaseDevice.BaseMode.MODE_NORMAL));
        arrayList.add(new OperationModel(this.getString(2131361996), this.getString(2131361995), false, QardioBaseDevice.BaseMode.MODE_WEIGHT_ONLY));
        arrayList.add(new OperationModel(this.getString(2131361994), this.getString(2131361993), false, QardioBaseDevice.BaseMode.MODE_SMART));
        arrayList.add(new OperationModel(this.getString(2131361991), this.getString(2131361990), false, QardioBaseDevice.BaseMode.MODE_PREGNANCY));
        return arrayList;
    }

    @Override
    protected int getLayoutBackground() {
        return 2130837939;
    }

    @Override
    protected int getLayoutResource() {
        return 2130968663;
    }

    /* synthetic */ void lambda$onViewCreated$0(View view) {
        this.onDone();
    }

    public void onActivityCreated(Bundle bundle) {
        super.onActivityCreated(bundle);
    }

    protected void onDone() {
    }

    @Override
    public void onResume() {
        super.onResume();
        this.dataProvider = (QBOnboardingDataProvider)this.getParentFragment();
    }

    @Override
    public void onViewCreated(View object, Bundle bundle) {
        super.onViewCreated((View)object, bundle);
        object = (RecyclerView)this.rootView.findViewById(2131820995);
        ((RecyclerView)object).setLayoutManager(new LinearLayoutManager((Context)this.getActivity(), 1, false));
        ((RecyclerView)object).setHasFixedSize(true);
        this.modesListAdapter = new SelectModeAdapter(this.prepareSupportedOperationModes());
        ((RecyclerView)object).setAdapter(this.modesListAdapter);
        this.doneButton = (Button)this.rootView.findViewById(2131820996);
        this.doneButton.setOnClickListener(AbstractQBChooseModeListFragment$$Lambda$1.lambdaFactory$(this));
    }

    public static interface Callback {
        public void onModeItemClicked(QardioBaseDevice.BaseMode var1);

        public void onModeSaved(QardioBaseDevice.BaseMode var1, HashMap<String, Object> var2);
    }

    class OperationModel {
        String description;
        boolean isSelected;
        QardioBaseDevice.BaseMode mode;
        String title;

        public OperationModel(String string2, String string3, boolean bl, QardioBaseDevice.BaseMode baseMode) {
            this.title = string2;
            this.description = string3;
            this.mode = baseMode;
            this.isSelected = bl;
        }
    }

    public static class SelectModeAdapter
    extends RecyclerView.Adapter<ModeHolder> {
        private int currentSelection = -1;
        private ModeSelectionListener modeSelectionListener;
        private List<OperationModel> operationModelList;

        SelectModeAdapter(List<OperationModel> list) {
            this.operationModelList = list;
        }

        @Override
        public int getItemCount() {
            return this.operationModelList.size();
        }

        @Override
        public void onBindViewHolder(ModeHolder modeHolder, int n) {
            modeHolder.bindModelWithUI(n, this.operationModelList.get(n));
        }

        @Override
        public ModeHolder onCreateViewHolder(ViewGroup viewGroup, int n) {
            return new ModeHolder(LayoutInflater.from((Context)viewGroup.getContext()).inflate(2130968792, viewGroup, false));
        }

        void setItemSelected(int n) {
            if (n != this.currentSelection) {
                this.operationModelList.get((int)n).isSelected = true;
                if (this.currentSelection > -1) {
                    this.operationModelList.get((int)this.currentSelection).isSelected = false;
                    this.notifyItemChanged(this.currentSelection);
                }
                this.notifyItemChanged(n);
                this.currentSelection = n;
            }
        }

        public void setModeSelectionListener(ModeSelectionListener modeSelectionListener) {
            this.modeSelectionListener = modeSelectionListener;
        }

        class ModeHolder
        extends RecyclerView.ViewHolder {
            private TextView modeDescription;
            private ImageView modeSelected;
            private TextView modeTitle;

            public ModeHolder(View view) {
                super(view);
                this.modeTitle = (TextView)view.findViewById(2131821240);
                this.modeDescription = (TextView)view.findViewById(2131821241);
                this.modeSelected = (ImageView)view.findViewById(2131821239);
            }

            void bindModelWithUI(int n, OperationModel operationModel) {
                if (!operationModel.title.equals(this.modeTitle.getText())) {
                    this.modeTitle.setText((CharSequence)operationModel.title);
                    this.modeDescription.setText((CharSequence)operationModel.description);
                }
                this.itemView.setOnClickListener(AbstractQBChooseModeListFragment$SelectModeAdapter$ModeHolder$$Lambda$1.lambdaFactory$(this, operationModel, n));
                if (QardioBaseDevice.BaseMode.MODE_PREGNANCY.equals((Object)operationModel.mode)) {
                    this.modeSelected.setImageDrawable(this.modeSelected.getResources().getDrawable(2130837782));
                    return;
                }
                if (operationModel.isSelected) {
                    this.modeSelected.setImageDrawable(this.modeSelected.getResources().getDrawable(2130837811));
                    return;
                }
                this.modeSelected.setImageDrawable(this.modeSelected.getResources().getDrawable(2130837785));
            }

            /* synthetic */ void lambda$bindModelWithUI$0(OperationModel operationModel, int n, View view) {
                SelectModeAdapter.this.modeSelectionListener.onModeSelected(operationModel.mode);
                SelectModeAdapter.this.setItemSelected(n);
            }
        }

        static interface ModeSelectionListener {
            public void onModeSelected(QardioBaseDevice.BaseMode var1);
        }

    }

}

