package br.com.pilovieira.gt06.view;


import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.com.pilovieira.gt06.R;
import br.com.pilovieira.gt06.business.CommonOperations;
import br.com.pilovieira.gt06.business.GT06Commands;
import br.com.pilovieira.gt06.comm.SMSEmitter;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class OperationsFragment extends Fragment {

    private GT06Commands commands;
    private CommonOperations common;
    private SMSEmitter emitter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new GT06Commands(getContext());
        common = new CommonOperations(getContext());
        emitter = new SMSEmitter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_operations, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnGetLocation)
    public void locationAction(View view) {
        common.locationAction(view);
    }

    @OnClick(R.id.btnGetLocationSms)
    public void checkStatusAction() {
        emitter.emit(getString(R.string.get_location_sms), commands.getLocationSms());
    }

    @OnClick(R.id.btnLockVehicle)
    public void lockAction() {
        common.lockAction();
    }

    @OnClick(R.id.btnUnlockVehicle)
    public void unlockAction() {
        common.unlockAction();
    }

    @OnClick(R.id.btnMonitor)
    public void monitorAction() {
        emitter.emit(getString(R.string.monitor), commands.monitor());
    }

    @OnClick(R.id.btnTracker)
    public void trackerAction() {
        emitter.emit(getString(R.string.tracker), commands.tracker());
    }

}
