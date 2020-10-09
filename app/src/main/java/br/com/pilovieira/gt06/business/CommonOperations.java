package br.com.pilovieira.gt06.business;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import androidx.core.app.ActivityCompat;
import android.view.View;
import android.widget.Toast;

import br.com.pilovieira.gt06.R;
import br.com.pilovieira.gt06.comm.SMSEmitter;
import br.com.pilovieira.gt06.persist.Prefs;

public class CommonOperations {

    private Context context;
    private GT06Commands commands;

    public CommonOperations(Context context){
        this.context = context;
        this.commands = new GT06Commands(context);
    }

    public void locationAction(View view) {
        Prefs prefs = new Prefs(context);
        String number = prefs.getTrackerNumber();
        if (number.isEmpty()) {
            Toast.makeText(context, R.string.msg_configure_tracker_number, Toast.LENGTH_SHORT).show();
            return;
        }

        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.setData(Uri.parse(commands.getLocation()));
//        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//            Toast.makeText(context, R.string.please_add_call_permission, Toast.LENGTH_SHORT).show();
//            return;
//        }

        context.startActivity(intent);
    }

    public void lockAction() {
        new SMSEmitter(context).emit(context.getString(R.string.lock_vehicle), commands.lockVehicle());
    }

    public void unlockAction() {
        new SMSEmitter(context).emit(context.getString(R.string.unlock_vehicle), commands.unlockVehicle());
    }

}
