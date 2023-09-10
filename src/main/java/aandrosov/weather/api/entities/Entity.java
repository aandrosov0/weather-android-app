package aandrosov.weather.api.entities;

import org.json.JSONException;
import org.json.JSONObject;

public interface Entity {

    void fill(JSONObject object) throws JSONException;
}
