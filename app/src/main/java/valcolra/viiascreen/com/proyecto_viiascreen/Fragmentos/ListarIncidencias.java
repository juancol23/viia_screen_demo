package valcolra.viiascreen.com.proyecto_viiascreen.Fragmentos;


import android.app.LauncherActivity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import valcolra.viiascreen.com.proyecto_viiascreen.DetalleIncidente;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.PictureAdapterRecyclerView;
import valcolra.viiascreen.com.proyecto_viiascreen.modelo.Picture;
import valcolra.viiascreen.com.proyecto_viiascreen.modelo.Post;
import valcolra.viiascreen.com.proyecto_viiascreen.view.CrearIncidencia;

/**
 * A simple {@link Fragment} subclass.
 */
public class ListarIncidencias extends Fragment {
    ListView listView;
    ArrayAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
/*
    private static final String URL_BASE = "https://api.everlive.com/v1/vqfkdc51v767oq7x/";
    private static final String URL_JSON = "viiascreen_incidentes";
*/
    private List<LauncherActivity.ListItem> listItems;

    public ListarIncidencias() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        final View view = inflater.inflate(R.layout.fragment_listar_incidencia, container, false);


        swipeRefreshLayout = (SwipeRefreshLayout)view.findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setSize(8);


        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                listView= (ListView) view.findViewById(R.id.listView2);
                adapter = new valcolra.viiascreen.com.proyecto_viiascreen.Posteando.PostAdapter(getContext());
                listView.setAdapter(adapter);
            }
        });

        listView= (ListView) view.findViewById(R.id.listView2);
        adapter = new valcolra.viiascreen.com.proyecto_viiascreen.Posteando.PostAdapter(getContext());
        listView.setAdapter(adapter);



        FloatingActionButton fabCrearIncidente = (FloatingActionButton) view.findViewById(R.id.fabCrearIncidente);
        fabCrearIncidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Creando Incidencia", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(getActivity(),CrearIncidencia.class);
                startActivity(i);

            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override

                public void onItemClick(AdapterView<?> arg0, View v, int arg2, long arg3) {
                    /*Prametros de incidencia*/
                    //here v is your ListItem's layout.
                    TextView m_textoPuntos = view.findViewById(R.id.textoPuntoIncidente);
                    TextView m_textofecha=  view.findViewById(R.id.textoFechaIncidente);
                    TextView m_textoUsuario=  view.findViewById(R.id.textoUsuarioIncidente);
                    //ImageView bitmap =  view.findViewById(R.id.imagenPostIncidente);


                    String punto = m_textoPuntos.getText().toString();
                    String fecha = m_textofecha.getText().toString();
                    String usuario = m_textoUsuario.getText().toString();

                    Toast.makeText(getContext(), "Click"+usuario, Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(getActivity(), DetalleIncidente.class);
                    intent.putExtra("incidentePunto", punto);
                    intent.putExtra("fechaIncidenteP", fecha);
                    intent.putExtra("localizacionIncidente", "localization");
                    intent.putExtra("direccionIncidente", "direccion");
                    intent.putExtra("statusIncidente", "estatus");
                    intent.putExtra("creadoPorIncidente", usuario);
                    intent.putExtra("observacionIncidente", "Observación");
                    startActivity(intent);



                }
            }
        );




        return view;
    }


}
