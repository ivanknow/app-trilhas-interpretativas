package br.com.ufrpe.isantos.trilhainterpretativa.entity;

/**
 * Created by ivan on 22/06/2017.
 */

public class Local {
    private double latitude;
    private double longitude;
    private double alt;

    public Local(double latitude, double longitude, double alt) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.alt = alt;
    }

    public Local() {

    }


    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAlt() {
        return alt;
    }


    public void setAlt(double alt) {
        this.alt = alt;
    }
}
