package valcolra.viiascreen.com.proyecto_viiascreen.Fragmentos;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import valcolra.viiascreen.com.proyecto_viiascreen.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class CameraIncidencia extends Fragment {


    public CameraIncidencia() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_camera_incidencia, container, false);
    }

}
