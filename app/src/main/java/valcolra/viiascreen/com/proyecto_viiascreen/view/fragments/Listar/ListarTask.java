package valcolra.viiascreen.com.proyecto_viiascreen.view.fragments.Listar;


import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import valcolra.viiascreen.com.proyecto_viiascreen.CrearTask;
import valcolra.viiascreen.com.proyecto_viiascreen.DetalleIncidente;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.CheckingAdapter;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.IncidenteAdapter;
import valcolra.viiascreen.com.proyecto_viiascreen.adapter.TaskAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ListarTask extends Fragment {

    ListView listView;
    ArrayAdapter adapter;
    SwipeRefreshLayout swipeRefreshLayout;
    public ListarTask() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        final View v = inflater.inflate(R.layout.fragment_listar_task2, container, false);
        swipeRefreshLayout =  v.findViewById(R.id.swiperefreshTask);
        swipeRefreshLayout.setSize(8);

        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(false);
                listView =  v.findViewById(R.id.listViewTash);
                adapter = new TaskAdapter(getContext());
                listView.setAdapter(adapter);

            }
        });

        listView =  v.findViewById(R.id.listViewTash);
        adapter = new TaskAdapter(getContext());
        listView.setAdapter(adapter);

        FloatingActionButton fabCrearIncidente = v.findViewById(R.id.fabCrearTask);
        fabCrearIncidente.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Crear Task", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
                Intent i = new Intent(getActivity(), CrearTask.class);
                startActivity(i);
            }
        });

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView < ? > parent, View view, int position, long id) {
                long itemid = listView.getItemIdAtPosition(position);
                Toast.makeText(getActivity(), "You selected : " + itemid, Toast.LENGTH_SHORT).show();
                TextView m_textoPuntos = view.findViewById(R.id.textoPuntoIncidente);
                TextView m_textofecha = view.findViewById(R.id.textoFechaIncidente);
                TextView m_textoUsuario = view.findViewById(R.id.textoUsuarioIncidente);
                TextView m_textoEstado = view.findViewById(R.id.textoStatusIncidente2);
                TextView m_textoServicio = view.findViewById(R.id.textoUsuarioIncidente);
                TextView juanObser = view.findViewById(R.id.textoUsuarioIncidente);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.fondo_vi);
                String punto = m_textoPuntos.getText().toString();
                String fecha = m_textofecha.getText().toString();
                String usuario = m_textoUsuario.getText().toString();
                String estado = m_textoEstado.getText().toString();
                String obser = juanObser.getText().toString();
                Intent intent = new Intent(getActivity(), DetalleIncidente.class);

                // intent.putExtra("bitmap", bitmap);
                intent.putExtra("incidentePunto", punto);
                intent.putExtra("fechaIncidenteP", fecha);
                intent.putExtra("localizacionIncidente", "Javier Prado Este 488");
                intent.putExtra("direccionIncidente", "Javier Prado Este 488 - Lado B");
                intent.putExtra("statusIncidente", estado);
                intent.putExtra("creadoPorIncidente", usuario);
                intent.putExtra("observacionIncidente", obser);
                // intent.putExtra("observacionIncidente", "Lorem ipsum dolor sit amet, consectetur adipisicing elit. Atque mollitia amet aspernatur minima qui blanditiis deserunt rerum recusandae, corporis nesciunt. Consequuntur eligendi, corporis fugit ab iusto ipsam accusantium magnam? In?");
                startActivity(intent);
            }
        });

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new TaskAdapter(getContext());
        listView.setAdapter(adapter);
    }

}
