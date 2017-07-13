package br.com.pilovieira.gt06.view;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import br.com.pilovieira.gt06.R;
import br.com.pilovieira.gt06.business.GT06Commands;
import br.com.pilovieira.gt06.business.ListenerProvider;
import br.com.pilovieira.gt06.comm.SMSEmitter;
import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class AdvancedOperationsFragment extends Fragment {

    @Bind(R.id.btnActivateGeoFence) Button btnActivateGeoFence;
    @Bind(R.id.btnActivateOverSpeed) Button btnActivateOverSpeed;

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
        View view = inflater.inflate(R.layout.fragment_advanced_operations, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @OnClick(R.id.btnActivateGeoFence)
    public void activateGeoFenceAction() {
        ListenerProvider.openDialogOneParam(this, btnActivateGeoFence, R.string.diameter, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String semidiameter) {
                emitter.emit(btnActivateGeoFence.getText().toString(), commands.activateGeoFence(semidiameter));
            }
        });
    }

    @OnClick(R.id.btnCancelGeoFence)
    public void cancelGeoFenceAction() {
        emitter.emit(getString(R.string.cancel_geo_fence), commands.cancelGeoFence());
    }

    @OnClick(R.id.btnActivateOverSpeed)
    public void activateOverSpeedAction() {
        ListenerProvider.openDialogOneParam(this, btnActivateOverSpeed, R.string.speed3Digits, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String speed) {
                emitter.emit(btnActivateOverSpeed.getText().toString(), commands.activateSpeedAlarm(speed));
            }
        });
    }

    @OnClick(R.id.btnCancelOverSpeed)
    public void cancelOverSpeedAction() {
        emitter.emit(getString(R.string.cancel_geo_fence), commands.cancelSpeedAlarm());
    }

    @OnClick(R.id.btnActivateAcc)
    public void activateAccAction() {
        emitter.emit(getString(R.string.cancel_geo_fence), commands.activateAcc());
    }

    @OnClick(R.id.btnCancelAcc)
    public void cancelAccAction() {
        emitter.emit(getString(R.string.cancel_geo_fence), commands.cancelAcc());
    }

}
