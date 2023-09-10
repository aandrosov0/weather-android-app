package aandrosov.weather.api.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class MainEntity implements Entity {

    private float temperature;
    private float feelsLike;
    private float temperatureMinimum;
    private float temperatureMaximum;
    private int pressure;
    private int humidity;
    private Integer seaLevel;
    private Integer groundLevel;

    public MainEntity() {
    }

    public MainEntity(float temperature, float feelsLike, float temperatureMinimum, float temperatureMaximum, int pressure, int humidity, int seaLevel, int groundLevel) {
        this.temperature = temperature;
        this.feelsLike = feelsLike;
        this.temperatureMinimum = temperatureMinimum;
        this.temperatureMaximum = temperatureMaximum;
        this.pressure = pressure;
        this.humidity = humidity;
        this.seaLevel = seaLevel;
        this.groundLevel = groundLevel;
    }

    @Override
    public void fill(JSONObject object) throws JSONException {
        this.temperature = (float) object.getDouble("temp");
        this.feelsLike = (float) object.getDouble("feels_like");
        this.temperatureMinimum = (float) object.getDouble("temp_min");
        this.temperatureMaximum = (float) object.getDouble("temp_max");
        this.pressure = object.getInt("pressure");
        this.humidity = object.getInt("humidity");

        if (object.has("sea_level")) {
            this.seaLevel = object.getInt("sea_level");
        }

        if (object.has("grnd_level")) {
            this.groundLevel = object.getInt("grnd_level");
        }
    }

    public float getTemperature() {
        return temperature;
    }

    public void setTemperature(float temperature) {
        this.temperature = temperature;
    }

    public float getFeelsLike() {
        return feelsLike;
    }

    public void setFeelsLike(float feelsLike) {
        this.feelsLike = feelsLike;
    }

    public float getTemperatureMinimum() {
        return temperatureMinimum;
    }

    public void setTemperatureMinimum(float temperatureMinimum) {
        this.temperatureMinimum = temperatureMinimum;
    }

    public float getTemperatureMaximum() {
        return temperatureMaximum;
    }

    public void setTemperatureMaximum(float temperatureMaximum) {
        this.temperatureMaximum = temperatureMaximum;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    public int getSeaLevel() {
        return seaLevel;
    }

    public void setSeaLevel(int seaLevel) {
        this.seaLevel = seaLevel;
    }

    public int getGroundLevel() {
        return groundLevel;
    }

    public void setGroundLevel(int groundLevel) {
        this.groundLevel = groundLevel;
    }
}
