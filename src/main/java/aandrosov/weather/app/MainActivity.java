package aandrosov.weather.app;

import aandrosov.weather.api.OpenWeatherMapClient;
import aandrosov.weather.api.OpenWeatherMapResult;
import aandrosov.weather.api.OpenWeatherMapUnits;
import aandrosov.weather.api.entities.CurrentWeatherEntity;
import aandrosov.weather.api.parameters.CurrentWeatherParameters;
import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.core.location.LocationListenerCompat;
import androidx.core.location.LocationManagerCompat;
import androidx.core.location.LocationRequestCompat;

import java.io.IOException;
import java.util.Calendar;
import java.util.Locale;

public class MainActivity extends Activity implements LocationListenerCompat {

    public static final int PERMISSION_LOCATION_REQUEST = 200;

    private OpenWeatherMapClient weatherClient;

    private TextView cityLabel;
    private TextView weatherLabel;
    private TextView pressureLabel;
    private TextView humidityLabel;
    private TextView visibilityLabel;
    private TextView temperatureLabel;

    private ViewGroup contentView;

    public void onLocationChanged(Location location) {
        if (location == null) {
            return;
        }

        CurrentWeatherParameters parameters = new CurrentWeatherParameters
                .Builder(location.getLongitude(), location.getLatitude())
                .setLanguage(Locale.getDefault().getLanguage())
                .setUnits(OpenWeatherMapUnits.METRIC)
                .build();
        try {
            weatherClient.currentWeather(parameters, this::onWeatherFetchCompleted);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            showMessageAndTerminate(R.string.failed_connect_to_the_server_alert);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestPermissions(new String[]{
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS,
                Manifest.permission.CONTROL_LOCATION_UPDATES,
                Manifest.permission.LOCATION_HARDWARE}, PERMISSION_LOCATION_REQUEST);
        setContentView(R.layout.activity_main);
        defineBackground();

        contentView = findViewById(android.R.id.content);
        contentView.setAlpha(0);

        cityLabel = findViewById(R.id.city_label);
        weatherLabel = findViewById(R.id.weather_label);
        pressureLabel = findViewById(R.id.pressure_label);
        humidityLabel = findViewById(R.id.humidity_label);
        visibilityLabel = findViewById(R.id.visibility_label);
        temperatureLabel = findViewById(R.id.temperature_label);

        weatherClient = ((Application) getApplication()).getWeatherClient();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_LOCATION_REQUEST
                && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            fetchLocation();
        } else {
            showMessageAndTerminate(R.string.permission_alert);
        }
    }

    protected void onWeatherFetchCompleted(OpenWeatherMapResult result) {
        if (result instanceof OpenWeatherMapResult.Error error) {
            error.extract().printStackTrace(System.err);
            showMessageAndTerminate(R.string.error_check_your_internet_connection);
        } else {
            OpenWeatherMapResult.Success success = (OpenWeatherMapResult.Success) result;
            CurrentWeatherEntity weather = (CurrentWeatherEntity) success.extract();
            updateUIComponents(weather);
        }
    }

    private void fetchLocation() {
        LocationManager locationManager = getSystemService(LocationManager.class);
        if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_DENIED) {
            throw new RuntimeException("Cannot get access to gps");
        }

        LocationManagerCompat.getCurrentLocation(
                locationManager, LocationManager.NETWORK_PROVIDER, null, getMainExecutor(), this::onLocationChanged);

        LocationRequestCompat request = new LocationRequestCompat.Builder(0)
                .setQuality(LocationRequestCompat.QUALITY_HIGH_ACCURACY)
                .build();
        LocationManagerCompat.requestLocationUpdates(locationManager, LocationManager.GPS_PROVIDER, request, getMainExecutor(), this);
    }

    private void updateUIComponents(CurrentWeatherEntity weather) {
        String visibility = weather.getVisibility() + "m";
        String humidity = weather.getMain().getHumidity() + "%";
        String pressure = weather.getMain().getPressure() + "hPa";
        String temperature = (int) weather.getMain().getTemperature() + "°";

        if(((String)cityLabel.getText()).isEmpty()) {
            contentView.setAlpha(1);
            Animation fadeOutAnimation = AnimationUtils.loadAnimation(this, R.anim.fade_out);
            contentView.setAnimation(fadeOutAnimation);
        }

        cityLabel.setText(weather.getName());
        weatherLabel.setText(weather.getWeather().getMain());
        humidityLabel.setText(humidity);
        visibilityLabel.setText(visibility);
        pressureLabel.setText(pressure);
        temperatureLabel.setText(temperature);
    }

    private void defineBackground() {
        byte time = (byte) Calendar.getInstance().get(Calendar.HOUR_OF_DAY);

        if (time < 6 || time > 20) {
            Window window = getWindow();
            window.setBackgroundDrawable(getDrawable(R.drawable.night_background));
            window.setStatusBarColor(getColor(R.color.night_background_top));
            window.setNavigationBarColor(getColor(R.color.night_background_bottom));
        }
    }

    private void showMessageAndTerminate(int messageId) {
        new AlertDialog.Builder(this)
                .setMessage(messageId)
                .setCancelable(true)
                .setOnCancelListener(dialog -> System.exit(0))
                .create()
                .show();
    }
}
