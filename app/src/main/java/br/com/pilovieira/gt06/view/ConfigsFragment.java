package br.com.pilovieira.gt06.view;


import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;

import br.com.pilovieira.gt06.R;
import br.com.pilovieira.gt06.business.GT06Commands;
import br.com.pilovieira.gt06.business.ListenerProvider;
import br.com.pilovieira.gt06.comm.SMSEmitter;
import br.com.pilovieira.gt06.databinding.FragmentConfigsBinding;

public class ConfigsFragment extends Fragment {

    private GT06Commands commands;
    private SMSEmitter emitter;

    private FragmentConfigsBinding binding;
    private Button btnChangePassword;
    private Button btnAuthorize;
    private Button btnRemoveAuth;
    private Button btnTimeZone;
    private Button btnSetApn;
    private Button btnSetIpAndPort;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        commands = new GT06Commands(getContext());
        emitter = new SMSEmitter(getContext());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentConfigsBinding.inflate(inflater, container, false);
        btnChangePassword = binding.btnChangePassword;
        btnAuthorize = binding.btnAuthorize;
        btnRemoveAuth = binding.btnRemoveAuth;
        btnTimeZone = binding.btnTimeZone;
        btnSetApn = binding.btnSetApn;
        btnSetIpAndPort = binding.btnSetIpAndPort;

        binding.btnChangePassword.setOnClickListener(view -> btnChangePasswordClicked());
        binding.btnAuthorize.setOnClickListener(view -> btnAuthorizeClicked());
        binding.btnRemoveAuth.setOnClickListener(view -> mountBtnDeleteNumber());
        binding.btnSetApn.setOnClickListener(view -> btnSetApnClicked());
        binding.btnSetIpAndPort.setOnClickListener(view -> btnSetIpAndPortClicked());
        binding.btnTimeZone.setOnClickListener(view -> btnTimeZoneClicked());
        binding.btnRestart.setOnClickListener(view -> restartAction());
        binding.btnBegin.setOnClickListener(view -> beginAction());

        return binding.getRoot();
    }

    public void btnChangePasswordClicked() {
        ListenerProvider.openDialogTwoParam(this, btnChangePassword, R.string.old_password, R.string.new_password, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String oldPass, String newPass) {
                emitter.emit(btnChangePassword.getText().toString(), commands.changePassword(oldPass, newPass));
            }
        });
    }

    public void btnAuthorizeClicked() {
        ListenerProvider.openDialogOneParam(this, btnAuthorize, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String number) {
                emitter.emit(btnAuthorize.getText().toString(), commands.authorizeNumber(number));
            }
        });
    }

    public void mountBtnDeleteNumber() {
        ListenerProvider.openDialogOneParam(this, btnRemoveAuth, R.string.number, new ListenerProvider.CommandOneParam() {
            @Override
            public void apply(String number) {
                emitter.emit(btnRemoveAuth.getText().toString(), commands.deleteNumber(number));
            }
        });
    }

    public void btnSetApnClicked() {
        ListenerProvider.openDialogThreeParam(this, btnSetApn, R.string.apn_name, R.string.user, R.string.pass, new ListenerProvider.CommandThreeParam() {
            @Override
            public void apply(String name, String user, String pass) {
                emitter.emit(btnSetApn.getText().toString(), commands.setAPN(name, user, pass));
            }
        });
    }

    public void btnSetIpAndPortClicked() {
        ListenerProvider.openDialogTwoParam(this, btnSetIpAndPort, R.string.ip, R.string.port, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String ip, String port) {
                emitter.emit(btnSetIpAndPort.getText().toString(), commands.setIpAndPort(ip, port));
            }
        });
    }

    public void btnTimeZoneClicked() {
        ListenerProvider.openDialogTwoParam(this, btnTimeZone, R.string.direction, R.string.hours, new ListenerProvider.CommandTwoParam() {
            @Override
            public void apply(String direction, String hours) {
                emitter.emit(btnTimeZone.getText().toString(), commands.timeZone(direction, hours));
            }
        });
    }

    public void restartAction() {
        emitter.emit(getString(R.string.restart_tracker), commands.reset());
    }

    public void beginAction() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle(R.string.are_you_sure);
        builder.setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                emitter.emit(getString(R.string.factory_reset_begin), commands.begin());
            }
        });
        builder.setNegativeButton(R.string.no, null);
        builder.show();
    }

}
