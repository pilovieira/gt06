package br.com.pilovieira.gt06.view;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import br.com.pilovieira.gt06.R;
import br.com.pilovieira.gt06.business.GT06Commands;
import br.com.pilovieira.gt06.business.ListenerProvider;
import br.com.pilovieira.gt06.comm.SMSEmitter;
import br.com.pilovieira.gt06.databinding.FragmentAdvancedOperationsBinding;

public class AdvancedOperationsFragment extends Fragment {

    private FragmentAdvancedOperationsBinding binding;
    private Button btnActivateGeoFence;
    private Button btnActivateOverSpeed;

    private GT06Commands commands;
    private SMSEmitter emitter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new GT06Commands(getContext());
        emitter = new SMSEmitter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentAdvancedOperationsBinding.inflate(inflater, container, false);
        btnActivateGeoFence = binding.btnActivateGeoFence;
        btnActivateOverSpeed = binding.btnActivateOverSpeed;

        binding.btnActivateGeoFence.setOnClickListener(view -> activateGeoFenceAction());
        binding.btnCancelGeoFence.setOnClickListener(view -> cancelGeoFenceAction());
        binding.btnActivateOverSpeed.setOnClickListener(view -> activateOverSpeedAction());
        binding.btnCancelOverSpeed.setOnClickListener(view -> cancelOverSpeedAction());
        binding.btnActivateAcc.setOnClickListener(view -> activateAccAction());
        binding.btnCancelAcc.setOnClickListener(view -> cancelAccAction());

        return binding.getRoot();
    }

    public void activateGeoFenceAction() {
        ListenerProvider.openDialogOneParam(this, btnActivateGeoFence, R.string.diameter, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String semidiameter) {
                emitter.emit(btnActivateGeoFence.getText().toString(), commands.activateGeoFence(semidiameter));
            }
        });
    }

    public void cancelGeoFenceAction() {
        emitter.emit(getString(R.string.cancel_geo_fence), commands.cancelGeoFence());
    }

    public void activateOverSpeedAction() {
        ListenerProvider.openDialogOneParam(this, btnActivateOverSpeed, R.string.speed3Digits, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String speed) {
                emitter.emit(btnActivateOverSpeed.getText().toString(), commands.activateSpeedAlarm(speed));
            }
        });
    }

    public void cancelOverSpeedAction() {
        emitter.emit(getString(R.string.cancel_overspeed_alarm), commands.cancelSpeedAlarm());
    }

    public void activateAccAction() {
        emitter.emit(getString(R.string.activate_acc_alarm), commands.activateAcc());
    }

    public void cancelAccAction() {
        emitter.emit(getString(R.string.cancel_acc_alarm), commands.cancelAcc());
    }

}
