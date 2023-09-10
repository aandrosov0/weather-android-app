package aandrosov.weather.api;

import aandrosov.weather.api.entities.CurrentWeatherEntity;
import aandrosov.weather.api.parameters.CurrentWeatherParameters;
import android.os.Handler;
import androidx.annotation.NonNull;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Executor;

public class OpenWeatherMapClient {

    public static final String BASE_URL = "https://api.openweathermap.org/";

    private final String appId;

    private final Handler handler;

    private final Executor executor;

    public OpenWeatherMapClient(String appId, Handler handler, Executor executor) {
        this.appId = appId;
        this.handler = handler;
        this.executor = executor;
    }

    public void currentWeather(@NonNull CurrentWeatherParameters parameters, @NonNull OpenWeatherMapCallback callback) throws IOException {
        URL url = new URL(BASE_URL + "data/2.5/weather?" + parameters + "&appid=" + appId);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        executor.execute(() -> {
            try {
                JSONObject object = readStream(connection.getInputStream());
                CurrentWeatherEntity weather = new CurrentWeatherEntity();
                weather.fill(object);
                handler.post(() -> callback.onComplete(new OpenWeatherMapResult.Success(weather)));
            } catch (JSONException | IOException exception) {
                handler.post(() -> callback.onComplete(new OpenWeatherMapResult.Error(exception)));
            } finally {
                connection.disconnect();
            }
        });
    }

    private JSONObject readStream(InputStream in) throws JSONException, IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));

        StringBuilder result = new StringBuilder();
        for (String line; (line = reader.readLine()) != null;) {
            result.append(line);
        }

        return (JSONObject) new JSONTokener(result.toString()).nextValue();
    }
}
