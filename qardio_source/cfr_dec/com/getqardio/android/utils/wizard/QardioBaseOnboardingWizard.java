/*
 * Decompiled with CFR 0.147.
 * 
 * Could not load the following classes:
 *  android.bluetooth.BluetoothAdapter
 *  android.os.Bundle
 */
package com.getqardio.android.utils.wizard;

import android.bluetooth.BluetoothAdapter;
import android.os.Bundle;
import com.getqardio.android.ui.fragment.AbstractQBOnboardingFragment;
import com.getqardio.android.ui.fragment.GetWIFIPwdFragment;
import com.getqardio.android.ui.fragment.QBOnboardingChooseModeStateFragment;
import com.getqardio.android.ui.fragment.QBOnboardingConfirmProfileFragment;
import com.getqardio.android.ui.fragment.QBOnboardingGetReadyStateFragment;
import com.getqardio.android.ui.fragment.QBOnboardingHowToChangeNameFragment;
import com.getqardio.android.ui.fragment.QBOnboardingReadyFragment;
import com.getqardio.android.ui.fragment.QBOnboardingStepOnStateFragment;
import com.getqardio.android.ui.fragment.QBPlaceOnFloorFragment;
import com.getqardio.android.ui.fragment.QBProgressOnboardingFragment;
import com.getqardio.android.ui.fragment.QBSelectWiFiFromBaseOnboardingFragment;
import com.getqardio.android.ui.fragment.QBSelectWifiStateOnboardingFragment;
import com.getqardio.android.ui.fragment.QBShowSwitchScreenFragment;
import com.getqardio.android.ui.fragment.QBStepOffFragment;
import com.getqardio.android.ui.fragment.TurnOnBluetoothOnboardingFragment;
import com.getqardio.android.utils.wizard.OnOnboardingFinishedListener;
import com.getqardio.android.utils.wizard.QardioBaseState;

public class QardioBaseOnboardingWizard {
    private boolean bluetoothOn;
    private QardioBaseState heldState;
    private OnStateChangedListener listener;
    private OnOnboardingFinishedListener onboardingFinishedListener;
    private int qbVersion;
    private QardioBaseState state;

    /*
     * Enabled aggressive block sorting
     */
    public QardioBaseOnboardingWizard(OnStateChangedListener onStateChangedListener) {
        this.listener = onStateChangedListener;
        onStateChangedListener = BluetoothAdapter.getDefaultAdapter();
        boolean bl = onStateChangedListener != null && onStateChangedListener.getState() == 12;
        this.bluetoothOn = bl;
        this.changeState();
    }

    /*
     * Enabled force condition propagation
     * Lifted jumps to return sites
     */
    private void callOnStateChangedListener(QardioBaseState qardioBaseState) {
        if (this.listener == null || qardioBaseState == null) return;
        switch (1.$SwitchMap$com$getqardio$android$utils$wizard$QardioBaseState[qardioBaseState.ordinal()]) {
            default: {
                return;
            }
            case 13: {
                this.listener.onBoardingStateChanged(TurnOnBluetoothOnboardingFragment.newInstance());
                return;
            }
            case 1: {
                this.listener.onBoardingStateChanged(QBOnboardingGetReadyStateFragment.newInstance());
                return;
            }
            case 5: {
                this.listener.onBoardingStateChanged(QBOnboardingStepOnStateFragment.newInstance(2131362373, 2131362372, true));
                return;
            }
            case 3: {
                this.listener.onBoardingStateChanged(QBOnboardingConfirmProfileFragment.newInstance());
                return;
            }
            case 2: {
                qardioBaseState = new Bundle();
                qardioBaseState.putBoolean("EXTRA_WIFI_CHECK_NEEDED", true);
                this.listener.onBoardingStateChanged(QBSelectWifiStateOnboardingFragment.newInstance((Bundle)qardioBaseState));
                return;
            }
            case 4: {
                this.listener.onBoardingStateChanged(QBOnboardingChooseModeStateFragment.newInstance());
                return;
            }
            case 7: {
                this.listener.onBoardingStateChanged(QBProgressOnboardingFragment.newInstance());
                return;
            }
            case 14: {
                this.listener.onBoardingStateChanged(QBSelectWiFiFromBaseOnboardingFragment.newInstance(null));
                return;
            }
            case 9: {
                this.listener.onBoardingStateChanged(QBOnboardingReadyFragment.newInstance(true));
                return;
            }
            case 8: {
                this.listener.onBoardingStateChanged(QBOnboardingHowToChangeNameFragment.newInstance());
                return;
            }
            case 10: {
                this.listener.onBoardingStateChanged(GetWIFIPwdFragment.newInstance(null));
                return;
            }
            case 11: {
                this.listener.onBoardingStateChanged(QBShowSwitchScreenFragment.newInstance(null));
                return;
            }
            case 12: {
                this.listener.onBoardingStateChanged(QBPlaceOnFloorFragment.newInstance(null));
                return;
            }
            case 6: 
        }
        this.listener.onBoardingStateChanged(QBStepOffFragment.newInstance(null));
    }

    private void closeOnBoarding() {
        if (this.onboardingFinishedListener != null) {
            this.onboardingFinishedListener.onOnBoardingFinished();
        }
    }

    /*
     * Enabled aggressive block sorting
     */
    public void changeState() {
        QardioBaseState qardioBaseState = null;
        if (!this.bluetoothOn) {
            this.callOnStateChangedListener(QardioBaseState.BLUETOOTH_OFF);
            return;
        } else {
            if (this.state == null) {
                qardioBaseState = QardioBaseState.GET_READY;
            } else if (this.heldState != null) {
                qardioBaseState = this.heldState;
                this.heldState = null;
            } else {
                switch (this.state) {
                    default: {
                        break;
                    }
                    case GET_READY: {
                        qardioBaseState = QardioBaseState.CONFIRM_PROFILE;
                        break;
                    }
                    case SELECT_WIFI: {
                        qardioBaseState = QardioBaseState.CONFIGURING;
                        break;
                    }
                    case CONFIRM_PROFILE: {
                        qardioBaseState = QardioBaseState.CHOOSE_MODE;
                        break;
                    }
                    case CHOOSE_MODE: {
                        qardioBaseState = QardioBaseState.GET_WIFI_PWD_READY;
                        break;
                    }
                    case PREPARE: {
                        qardioBaseState = QardioBaseState.STEP_OFF;
                        break;
                    }
                    case STEP_OFF: {
                        qardioBaseState = QardioBaseState.SELECT_WIFI;
                        break;
                    }
                    case CONFIGURING: {
                        qardioBaseState = QardioBaseState.HOW_TO_CHANGE_NAME;
                        break;
                    }
                    case HOW_TO_CHANGE_NAME: {
                        qardioBaseState = QardioBaseState.READY;
                        break;
                    }
                    case READY: {
                        this.closeOnBoarding();
                        break;
                    }
                    case GET_WIFI_PWD_READY: {
                        if (this.qbVersion == 2) {
                            qardioBaseState = QardioBaseState.QB2ONLY_REVEAL_SWITCH_CONTROL;
                            break;
                        }
                        qardioBaseState = QardioBaseState.PREPARE;
                        break;
                    }
                    case QB2ONLY_REVEAL_SWITCH_CONTROL: {
                        qardioBaseState = QardioBaseState.QB2_PLACE_IT_ON_FLOOR;
                        break;
                    }
                    case QB2_PLACE_IT_ON_FLOOR: {
                        qardioBaseState = QardioBaseState.PREPARE;
                    }
                }
            }
            if (qardioBaseState == null) return;
            {
                this.setState(qardioBaseState);
                return;
            }
        }
    }

    public void setBluetoothOff() {
        this.bluetoothOn = false;
        this.heldState = this.state;
        this.changeState();
    }

    public void setBluetoothOn() {
        this.bluetoothOn = true;
        this.changeState();
    }

    public void setOnboardingFinishedListener(OnOnboardingFinishedListener onOnboardingFinishedListener) {
        this.onboardingFinishedListener = onOnboardingFinishedListener;
    }

    public void setQbVersion(int n) {
        this.qbVersion = n;
    }

    public void setState(QardioBaseState qardioBaseState) {
        this.state = qardioBaseState;
        this.callOnStateChangedListener(qardioBaseState);
    }

    public static interface OnStateChangedListener {
        public void onBoardingStateChanged(AbstractQBOnboardingFragment var1);
    }

}

