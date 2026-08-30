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
import br.com.pilovieira.gt06.databinding.FragmentOperationsBinding;

public class OperationsFragment extends Fragment {

    private GT06Commands commands;
    private CommonOperations common;
    private SMSEmitter emitter;
    private FragmentOperationsBinding binding;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new GT06Commands(getContext());
        common = new CommonOperations(getContext());
        emitter = new SMSEmitter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOperationsBinding.inflate(inflater, container, false);

        binding.btnGetLocation.setOnClickListener(this::locationAction);
        binding.btnGetLocationSms.setOnClickListener(view -> checkStatusAction());
        binding.btnLockVehicle.setOnClickListener(view -> lockAction());
        binding.btnUnlockVehicle.setOnClickListener(view -> unlockAction());
        binding.btnMonitor.setOnClickListener(view -> monitorAction());
        binding.btnTracker.setOnClickListener(view -> trackerAction());

        return binding.getRoot();
    }

    public void locationAction(View view) {
        common.locationAction(view);
    }

    public void checkStatusAction() {
        emitter.emit(getString(R.string.get_location_sms), commands.getLocationSms());
    }

    public void lockAction() {
        common.lockAction();
    }

    public void unlockAction() {
        common.unlockAction();
    }

    public void monitorAction() {
        emitter.emit(getString(R.string.monitor), commands.monitor());
    }

    public void trackerAction() {
        emitter.emit(getString(R.string.tracker), commands.tracker());
    }

}
