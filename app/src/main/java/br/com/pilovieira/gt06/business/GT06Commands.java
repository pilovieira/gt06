package br.com.pilovieira.gt06.business;

import android.content.Context;

import br.com.pilovieira.gt06.persist.Prefs;

public class GT06Commands {

    private Context context;
    private Prefs prefs;

    public GT06Commands(Context context) {
        this.context = context;
        this.prefs = new Prefs(context);
    }

    private String getPassword() {
        return prefs.getPassword();
    }

    private String go(String command) {
        return command.replace("*", getPassword());
    }

    public String getLocation() {
        return String.format("tel:%s", prefs.getTrackerNumber());
    }

    public String getLocationSms() {
        return go("#smslink#*#");
    }

    public String lockVehicle() {
        return go("#stopoil#*#");
    }

    public String unlockVehicle() {
        return go("#supplyoil#*#");
    }

    public String begin() {
        return go("#begin#*#");
    }

    public String monitor() {
        return go("#monitor#*#");
    }

    public String tracker() {
        return go("#tracker#*#");
    }

    public String reset() {
        return go("#reboot#*#");
    }

    public String changePassword(String oldPass, String newPass) {
        return go(String.format("#password#%s#%s#", oldPass, newPass));
    }

    public String authorizeNumber(String number) {
        return go(String.format("#admin#*#%s#", number));
    }

    public String deleteNumber(String number) {
        return go(String.format("#noadmin#*#%s#", number));
    }

    public String timeZone(String direction, String hours) {
        return go(String.format("#timezone#*#%s#%s#00#", direction, hours));
    }

    public String activateGeoFence(String semidiameter) {
        return go(String.format("#stockade#*#%s#", semidiameter));
    }

    public String cancelGeoFence() {
        return go("#nostockade#*#");
    }

    public String activateSpeedAlarm(String speed) {
        return go(String.format("#speed#*#%s#", speed));
    }

    public String cancelSpeedAlarm() {
        return go("#nospeed#*#");
    }

    public String activateAcc() {
        return go("#ACC#ON#*#");
    }

    public String cancelAcc() {
        return go("#ACC#OFF#*#");
    }

}
