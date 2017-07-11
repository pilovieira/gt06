package br.com.pilovieira.gt06.location;

import java.io.BufferedReader;
import java.io.StringReader;

import br.com.pilovieira.gt06.log.ServerLog;

public class LocationLogDigester {

    private ServerLog log;
    private String lat;
    private String lng;
    private String speed;
    private String time;

    public LocationLogDigester(ServerLog log) {
        this.log = log;
        digest();
    }

    private void digest() {
        try {
            StringReader sr = new StringReader(log.getMessage());
            BufferedReader br = new BufferedReader(sr);

            String line = br.readLine();
            while (line != null) {
                try {
                    processLine(line);
                } catch (Exception ex) {}
                line = br.readLine();
            }
            br.close();
            sr.close();
        } catch (Exception ex) {}
    }

    private void processLine(String line) {
        if (line == null)
            return;
        if (line.contains("q=")) {
            lat = line.trim().split("q=")[1].split(",")[0];
            lng = line.trim().split(",")[1].split("Speed")[0];
        }
        if (line.contains("Speed:"))
            speed = line.trim().split("Speed:")[1].split("km/h")[0];
        if (line.contains("Time:"))
            time = "Time: " + line.trim().split("Time:")[1].split("IMEI")[0];
    }

    public String getLat() {
        return lat;
    }

    public String getLng() {
        return lng;
    }

    public String getSpeed() {
        return speed;
    }

    public String getTime() {
        return time;
    }
}
