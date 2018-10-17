package android.bigiot.org.androidexampleconsumer;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class DashboardFragment extends Fragment {
    public static final String TAG = DashboardFragment.class.getSimpleName();
    private View rootView;

    public static DashboardFragment newInstance() {
        return new DashboardFragment();
    }

    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_dashboard, container, false);

        setUpElements();
        setUpListeners();

        return rootView;
    }

    private void setUpElements() {

    }

    private void setUpListeners() {

    }
}
