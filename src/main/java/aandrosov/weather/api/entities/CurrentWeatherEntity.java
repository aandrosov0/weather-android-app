package aandrosov.weather.api.entities;

import org.json.JSONException;
import org.json.JSONObject;

public class CurrentWeatherEntity implements Entity {

    private int id;

    private String name;

    private int timezone;

    private int visibility;

    private WeatherEntity weather;

    private CoordinatesEntity coordinates;

    private MainEntity main;

    public CurrentWeatherEntity() {
    }

    public CurrentWeatherEntity(int id, String name, int timezone, WeatherEntity weather, CoordinatesEntity coordinates, MainEntity main) {
        this.id = id;
        this.name = name;
        this.timezone = timezone;
        this.weather = weather;
        this.coordinates = coordinates;
        this.main = main;
    }

    @Override
    public void fill(JSONObject object) throws JSONException {
        this.id = object.getInt("id");
        this.name = object.getString("name");
        this.timezone = object.getInt("timezone");
        this.visibility = object.getInt("visibility");

        this.main = new MainEntity();
        this.main.fill(object.getJSONObject("main"));

        this.weather = new WeatherEntity();
        this.weather.fill(object.getJSONArray("weather").getJSONObject(0));

        this.coordinates = new CoordinatesEntity();
        this.coordinates.fill(object.getJSONObject("coord"));
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public WeatherEntity getWeather() {
        return weather;
    }

    public void setWeather(WeatherEntity weather) {
        this.weather = weather;
    }

    public CoordinatesEntity getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(CoordinatesEntity coordinates) {
        this.coordinates = coordinates;
    }

    public MainEntity getMain() {
        return main;
    }

    public void setMain(MainEntity main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }
}
