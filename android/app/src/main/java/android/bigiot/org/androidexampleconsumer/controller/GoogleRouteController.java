package android.bigiot.org.androidexampleconsumer.controller;

import android.bigiot.org.androidexampleconsumer.R;
import android.content.Context;
import android.location.Location;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import android.bigiot.org.androidexampleconsumer.model.RouteInfo;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class GoogleRouteController {
    private static final String TAG = GoogleRouteController.class.getSimpleName();

    public static void routeRequest(Context context, Location originLocation, Location destinationLocation, final GoogleRouteController.RouteResolvedCallback routeResolvedCallback) {
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=%s,%s&destinations=%s,%s&key=%s";

        url = String.format(url, originLocation.getLatitude(), originLocation.getLongitude(),
                destinationLocation.getLatitude(), destinationLocation.getLongitude(),
                context.getResources().getString(R.string.google_maps_key));

        Log.e("TAG", url.toString());

        // Request a string response from the provided URL.
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        RouteInfo distanceProperties = parseRouteJSONArray(response);
                        routeResolvedCallback.onRouteResolved(distanceProperties);
                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e(TAG, "That didn't work!");
            }
        });

        // Add the request to the RequestQueue.
        VolleyController.getInstance(context).addToQueue(jsonObjectRequest);
    }

    private static RouteInfo parseRouteJSONArray(JSONObject microCityJSONArray) {
        try {
            RouteInfo routeProperties = new RouteInfo();
            JSONArray rowsArray = microCityJSONArray.getJSONArray("rows");
            if (rowsArray.length() > 0) {
                JSONArray elementsArray = rowsArray.getJSONObject(0).getJSONArray("elements");
                if (elementsArray.length() > 0) {
                    double dist = elementsArray.getJSONObject(0).getJSONObject("distance").getDouble("value") / 1000.0 ;
                    double time = elementsArray.getJSONObject(0).getJSONObject("duration").getDouble("value") / 60.0;
                    routeProperties.setDistance(dist);
                    routeProperties.setTime(time);

                    return routeProperties;
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    public interface RouteResolvedCallback {
        void onRouteResolved(RouteInfo distanceProperties);
    }
}
