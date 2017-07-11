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
        return command.replace("#", getPassword());
    }

    public String getLocation() {
        return String.format("tel:%s", prefs.getTrackerNumber());
    }

    public String lockVehicle() {
        return go("stop#");
    }

    public String unlockVehicle() {
        return go("resume#");
    }

    public String begin() {
        return go("begin#");
    }

    public String monitor() {
        return go("monitor#");
    }

    public String tracker() {
        return go("tracker#");
    }

    public String check() {
        return go("check#");
    }

    public String reset() {
        return go("reset#");
    }

    public String changePassword(String oldPass, String newPass) {
        return go(String.format("password%s %s", oldPass, newPass));
    }

    public String authorizeNumber(String number) {
        return go(String.format("admin# %s", number));
    }

    public String deleteNumber(String number) {
        return go(String.format("noadmin# %s", number));
    }

    public String timeZone(String timezone) {
        return go(String.format("time zone# %s", timezone));
    }

}