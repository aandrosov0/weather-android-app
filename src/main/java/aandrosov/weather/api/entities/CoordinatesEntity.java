package aandrosov.weather.api.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class CoordinatesEntity implements Entity {

    private double longitude;
    private double latitude;

    public CoordinatesEntity() {
    }

    public CoordinatesEntity(double longitude, double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
    }

    @Override
    public void fill(JSONObject object) throws JSONException {
        this.latitude = object.getDouble("lat");
        this.longitude = object.getDouble("lon");
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }
}
