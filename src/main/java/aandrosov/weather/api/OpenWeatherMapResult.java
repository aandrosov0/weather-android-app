package aandrosov.weather.api;

import aandrosov.weather.api.entities.Entity;

public class OpenWeatherMapResult {

    public static class Success extends OpenWeatherMapResult {

        private final Entity object;

        public Success(Entity object) {
            this.object = object;
        }

        public Entity extract() {
            return object;
        }
    }
    public static class Error extends OpenWeatherMapResult {

        private final Exception exception;

        public Error(Exception exception) {
            this.exception = exception;
        }

        public Exception extract() {
            return exception;
        }
    }
}
