package aandrosov.weather.app;

import aandrosov.weather.api.OpenWeatherMapClient;
import android.os.Handler;
import android.os.Looper;
import androidx.core.os.HandlerCompat;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class Application extends android.app.Application {

    public static final int MAX_THREADS = 4;
    public static final String OPEN_WEATHER_MAP_APP_ID = "cd97bdf9b7f5bef563720670d7f7face";
    private final Executor executor = Executors.newFixedThreadPool(MAX_THREADS);

    private final Handler mainThreadHandler = HandlerCompat.createAsync(Looper.getMainLooper());

    private final OpenWeatherMapClient weatherClient = new OpenWeatherMapClient(OPEN_WEATHER_MAP_APP_ID, mainThreadHandler, executor);

    public OpenWeatherMapClient getWeatherClient() {
        return weatherClient;
    }
}
