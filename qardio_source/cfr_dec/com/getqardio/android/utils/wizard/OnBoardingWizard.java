/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.content.Context
 */
package com.getqardio.android.utils.wizard;

import android.content.Context;
import com.qardio.ble.bpcollector.mobiledevice.BLEStatus;

public class OnBoardingWizard {
    private BLEStatus bleStatus;
    private Context context;
    private GlobalState globalState;
    private InitialState initialState;
    private OnStateChangedListener onStateChangedListener;
    private OnWizardFinishedListener onWizardFinishedListener;
    private State state;

    public OnBoardingWizard(Context context, OnStateChangedListener onStateChangedListener) {
        this.context = context;
        this.bleStatus = BLEStatus.getInstance(context);
        this.onStateChangedListener = onStateChangedListener;
    }

    private void callStateChangedListener(State state) {
        if (this.onStateChangedListener != null && state != null) {
            this.onStateChangedListener.onStateChanged(state.titleResource, state.imageResource, state.messageResource, state.buttonTextResource);
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    private void changeState(State state) {
        Object var3_2 = null;
        boolean bl = false;
        switch (1.$SwitchMap$com$getqardio$android$utils$wizard$OnBoardingWizard$State[state.ordinal()]) {
            default: {
                state = var3_2;
                break;
            }
            case 1: {
                if (BLEStatus.getInstance(this.context).hasAdvertising()) {
                    state = State.WEAR;
                    break;
                }
                state = State.NOT_CONNECTED;
                break;
            }
            case 2: {
                state = State.TIGHT_THE_CUFF;
                break;
            }
            case 3: {
                state = State.PAIRING;
                break;
            }
            case 4: {
                state = State.WRAP_THE_DEVICE;
                break;
            }
            case 5: {
                if (this.initialState == InitialState.OUTRO) {
                    state = State.SWITCHING_OFF;
                    break;
                }
                state = State.SWITCH_ON;
                break;
            }
            case 6: {
                if (this.globalState == GlobalState.PAIRED) {
                    state = State.PAIRED;
                    break;
                }
                state = State.NOT_PAIRED;
                break;
            }
            case 7: {
                bl = true;
                this.onCloseWizard();
                state = var3_2;
                break;
            }
            case 8: {
                bl = true;
                this.onCloseWizard();
                state = var3_2;
                break;
            }
            case 9: {
                state = State.SWITCH_ON;
                break;
            }
            case 10: {
                state = State.LOW_BATTERY;
                break;
            }
            case 11: {
                bl = this.initialState == InitialState.LOW_BATTERY;
                this.onCloseWizard();
                state = var3_2;
            }
        }
        if (!bl) {
            this.setState(state);
        }
    }

    private void onCloseWizard() {
        if (this.onWizardFinishedListener != null) {
            this.onWizardFinishedListener.onWizardFinished();
        }
    }

    private void onLowBattery() {
        if (this.initialState == InitialState.WIZARD) {
            this.callStateChangedListener(State.LOW_BATTERY);
        }
    }

    private void setGlobalState(GlobalState globalState) {
        this.globalState = globalState;
    }

    private void setState(State state) {
        this.state = state;
        this.callStateChangedListener(state);
    }

    public void changeState() {
        block5: {
            block4: {
                if (this.globalState == GlobalState.BLUETOOTH_OFF) break block4;
                if (!this.bleStatus.isBatteryLow() || this.state == State.LOW_BATTERY || this.state == State.REPLACE_BATTERIES || this.initialState != InitialState.WIZARD) break block5;
                this.onLowBattery();
            }
            return;
        }
        if (this.state == null) {
            this.state = State.SWITCH_ON;
        }
        this.changeState(this.state);
    }

    public InitialState getInitialState() {
        return this.initialState;
    }

    /*
     * Enabled aggressive block sorting
     */
    public void init(InitialState initialState, boolean bl) {
        this.initialState = initialState;
        if (initialState == InitialState.LOW_BATTERY) {
            this.setStateReplaceBatteries();
            return;
        } else {
            if (initialState == InitialState.OUTRO) {
                this.setState(State.WRAP_THE_DEVICE);
                return;
            }
            if (!bl) return;
            {
                this.setState(State.SWITCH_ON);
                return;
            }
        }
    }

    public void setOnWizardFinishedListener(OnWizardFinishedListener onWizardFinishedListener) {
        this.onWizardFinishedListener = onWizardFinishedListener;
    }

    public void setStateBluetoothOff() {
        this.setGlobalState(GlobalState.BLUETOOTH_OFF);
        this.callStateChangedListener(State.BLUETOOTH_OFF);
    }

    public void setStateBluetoothOn() {
        this.setGlobalState(GlobalState.BLUETOOTH_ON);
        if (this.state == null) {
            this.callStateChangedListener(State.SWITCH_ON);
            return;
        }
        this.callStateChangedListener(this.state);
    }

    public void setStateNeedReset() {
        this.callStateChangedListener(State.NEED_RESET);
    }

    /*
     * Enabled aggressive block sorting
     */
    public void setStatePaired(boolean bl) {
        GlobalState globalState = bl ? GlobalState.PAIRED : GlobalState.NOT_PAIRED;
        this.setGlobalState(globalState);
    }

    public void setStateReady() {
        this.onCloseWizard();
    }

    public void setStateReplaceBatteries() {
        this.state = State.REPLACE_BATTERIES;
        this.callStateChangedListener(this.state);
    }

    private static enum GlobalState {
        BLUETOOTH_ON,
        BLUETOOTH_OFF,
        PAIRED,
        NOT_PAIRED;

    }

    public static enum InitialState {
        WIZARD,
        OUTRO,
        LOW_BATTERY;

    }

    public static interface OnStateChangedListener {
        public void onStateChanged(int var1, int var2, int var3, int var4);
    }

    public static interface OnWizardFinishedListener {
        public void onWizardFinished();
    }

    private static enum State {
        SWITCH_ON(2131361874, 2130838029, 2131361873, 2131361872),
        WEAR(2131361876, 2130837986, 2131361875, 2131361872),
        TIGHT_THE_CUFF(2131361870, 2130837625, 2131361869, 2131361872),
        NOT_CONNECTED(2131361856, 2130837696, 2131361855, 2131361871),
        WRAP_THE_DEVICE(2131361878, 2130838045, 2131361877, 2131361872),
        PAIRING(2131361862, 2130837915, 2131361861, 2131361872),
        PAIRED(2131361864, 2130837595, 2131361863, 2131361872),
        NOT_PAIRED(2131361868, 2130837952, 2131361867, 2131361872),
        SWITCHING_OFF(2131361858, 2130837696, 2131361857, 2131361872),
        REPLACE_BATTERIES(2131361854, 2130837949, 2131361853, 2131361872),
        BLUETOOTH_OFF(2131361852, 2130837596, 2131362436, 2131361872),
        LOW_BATTERY(2131361860, 2130837950, 2131361859, 2131361872),
        NEED_RESET(2131361866, 2130837951, 2131361865, 2131361872);

        int buttonTextResource;
        int imageResource;
        int messageResource;
        int titleResource;

        private State(int n2, int n3, int n4, int n5) {
            this.titleResource = n2;
            this.imageResource = n3;
            this.messageResource = n4;
            this.buttonTextResource = n5;
        }
    }

}

