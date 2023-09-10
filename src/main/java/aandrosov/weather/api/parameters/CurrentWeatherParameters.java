package aandrosov.weather.api.parameters;

import aandrosov.weather.api.OpenWeatherMapUnits;

import java.util.Locale;

public class CurrentWeatherParameters {

    public static class Builder {

        private final CurrentWeatherParameters parameters;

        public Builder(double longitude, double latitude) {
            parameters = new CurrentWeatherParameters();
            parameters.longitude = longitude;
            parameters.latitude = latitude;
        }

        public Builder setLanguage(String language) {
            parameters.language = language;
            return this;
        }

        public Builder setUnits(OpenWeatherMapUnits units) {
            parameters.units = units;
            return this;
        }

        public CurrentWeatherParameters build() {
            return parameters;
        }
    }

    private double longitude;
    private double latitude;

    private String language;
    private OpenWeatherMapUnits units;

    protected CurrentWeatherParameters() {}

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();

        if (language != null) {
            builder.append("&lang=").append(language);
        }

        if (units != null) {
            builder.append("&units=").append(units.name().toLowerCase(Locale.ROOT));
        }

        return "lon=" + longitude + "&lat=" + latitude + builder;
    }
}
