package android.bigiot.org.androidexampleconsumer;

import android.bigiot.org.androidexampleconsumer.Utils.Utils;
import android.bigiot.org.androidexampleconsumer.controller.BigIotController;
import android.bigiot.org.androidexampleconsumer.model.ChargeStation;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Icon;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeFragment extends Fragment implements BigIotController.OnAccessResponseCallback {
    public static final String TAG = HomeFragment.class.getSimpleName();
    private View rootView;
    private MapView mapView;
    private MapboxMap mMapboxMap;

    private ArrayList<ChargeStation> chargeStations;
    private Icon iconVehicle;
    private Icon iconMotorbike;

    public static HomeFragment newInstance() {
        return new HomeFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        setUpElements();
        setUpListeners();

        chargeStations = new ArrayList<>();

        iconVehicle = Utils.drawableToIcon(
                getContext(),
                R.drawable.mapbox_marker_icon_default,
                getResources().getColor(R.color.colorPrimary));
        iconMotorbike = Utils.drawableToIcon(
                getContext(),
                R.drawable.mapbox_marker_icon_default,
                getResources().getColor(R.color.colorAccent));

        Mapbox.getInstance(getActivity(), getString(R.string.access_token));
        mapView = rootView.findViewById(R.id.mapView);

        try {
            BigIotController.getInstance(getActivity()).accessOffering(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mapView.onCreate(savedInstanceState);

        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(MapboxMap mapboxMap) {
                mMapboxMap = mapboxMap;

                CameraPosition position = new CameraPosition.Builder()
                        .target(new LatLng(41.40, 2.16))
                        .zoom(10.75)
                        .build();

                mapboxMap.animateCamera(CameraUpdateFactory
                        .newCameraPosition(position), 100);
            }
        });
    }

    private void setUpElements() {

    }

    private void setUpListeners() {

    }

    @Override
    public void onStart() {
        super.onStart();
        mapView.onStart();
    }

    @Override
    public void onResume() {
        super.onResume();
        mapView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mapView.onPause();
    }

    @Override
    public void onStop() {
        super.onStop();
        mapView.onStop();
    }

    @Override
    public void onLowMemory() {
        super.onLowMemory();
        mapView.onLowMemory();
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mapView.onDestroy();
    }

    private void saveChargingStations(JSONArray stations) {
        for (int i = 0; i < stations.length(); ++i) {
            JSONObject jsonObject;
            try {
                jsonObject = stations.getJSONObject(i);

                double longitude = jsonObject.getDouble("longitude");
                String spotType = jsonObject.getString("spotType");
                if (spotType.equals("moto")) {
                    // Avoiding overlapping
                    longitude += 0.0025;
                }
                chargeStations.add(new ChargeStation(
                        jsonObject.getDouble("latitude"),
                        longitude,
                        jsonObject.getString("address"),
                        jsonObject.getInt("availableSpots"),
                        jsonObject.getInt("availableMennekeSpots"),
                        jsonObject.getInt("availableSchukoSpots"),
                        spotType
                ));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        showStationsInMap();
    }

    private void showStationsInMap() {
        try {
            for (ChargeStation chargeStation : chargeStations) {
                Icon icon;
                if (chargeStation.getSpotType().equals("Motorbike")) {
                    icon = iconMotorbike;
                } else {
                    icon = iconVehicle;
                }

                mMapboxMap.addMarker(new MarkerOptions()
                        .position(new LatLng(chargeStation.getLatitude(), chargeStation.getLongitude()))
                        .title(chargeStation.getSpotType() + " - " + chargeStation.getAddress())
                        .snippet(
                                "Spots: " + chargeStation.getAvailableSpots() + "\n" +
                                        "Menneke Spots: " + chargeStation.getAvailableMennekeSpots() + "\n" +
                                        "Schuko Spots: " + chargeStation.getAvailableSchukoSpots())
                        .icon(icon)
                );
            }
        } catch (Exception ignored) {
        }
    }

    @Override
    public void onAccessResponse(String response) {
        Log.e(TAG, "Access response " + response);
        try {
            JSONArray jsonArray = new JSONArray(response);
            saveChargingStations(jsonArray);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
