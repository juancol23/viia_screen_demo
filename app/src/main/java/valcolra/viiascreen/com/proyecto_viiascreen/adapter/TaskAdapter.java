package valcolra.viiascreen.com.proyecto_viiascreen.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import valcolra.viiascreen.com.proyecto_viiascreen.DetalleIncidente;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.model.ModelIncidente;

/**
 * Created by Vic on 29/09/2017.
 */

public class TaskAdapter  extends ArrayAdapter {

    // Atributos
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;


    // private static final String URL_BASE = "https://api.everlive.com/v1/e82dy3vmux1jchlu/";
    private static final String URL_BASE = "http://api.everlive.com/v1/vqfkdc51v767oq7x/";
    private static final String URL_JSON = "viiascreen_incidentes/?filter={\"estado\":\"tarea\"}";
    private static final String filterTask = "/viiascreen_incidentes";
    private static final String TAG = "TaskAdapter";
    List< ModelIncidente > items;

    public TaskAdapter(Context context) {
        super(context, 0);

        // Crear nueva cola de peticiones
        requestQueue = Volley.newRequestQueue(context);

        // Nueva petición JSONObject
        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE + URL_JSON,
                null,
                new Response.Listener <JSONObject> () {
                    @Override
                    public void onResponse(JSONObject response) {
                        items = parseJson(response);
                        notifyDataSetChanged();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error Respuesta en JSON: " + error.getMessage());

                    }
                }
        );

        // Añadir petición a la cola
        requestQueue.add(jsArrayRequest);
    }

    @Override
    public int getCount() {
        return items != null ? items.size() : 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());

        // Referencia del view procesado
        final View listItemView;

        //Comprobando si el View no existe
        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.lista_item_design_incidente,
                parent,
                false) : convertView;

        // Obtener el item actual
        final ModelIncidente item = items.get(position);

        // Obtener Views
        final ImageView m_imagen_Incidente = (ImageView) listItemView.
                findViewById(R.id.imagenPostIncidente2);
        TextView m_texto_puntos_Incidente = (TextView) listItemView.
                findViewById(R.id.textoPuntoIncidente);
        TextView m_texto_fecha_Incidente = (TextView) listItemView.
                findViewById(R.id.textoFechaIncidente);
        TextView m_texto_usuario_Incidente = (TextView) listItemView.
                findViewById(R.id.textoUsuarioIncidente);
        TextView m_texto_status_Incidentes = (TextView) listItemView.
                findViewById(R.id.textoStatusIncidente2);

        // Actualizar los Views
        m_texto_puntos_Incidente.setText(item.getPunto());
        m_texto_fecha_Incidente.setText(item.getFecha());
        m_texto_usuario_Incidente.setText(item.getUsuario());
        m_texto_status_Incidentes.setText(item.getEstado());


        final String estado = m_texto_status_Incidentes.getText().toString();

        ImageView mImageEstado = listItemView.findViewById(R.id.estadoImagen);
        if(estado.equals("cerrado")){
            mImageEstado.setImageResource(R.drawable.locked);


        }else if(estado.equals("tarea")){
            mImageEstado.setImageResource(R.drawable.unlocked);

        }



        LinearLayout m_incidenteListClick = listItemView.findViewById(R.id.incidenteListClick);
        m_incidenteListClick.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ImageView mImageEstado = listItemView.findViewById(R.id.estadoImagen);

                String visto = item.getId();



                Intent intent = new Intent(getContext(), DetalleIncidente.class);

                String ID = item.getId();
                ImageView m_viia_list_checking_desgin_checking_urlImagen2 = view.findViewById(R.id.imagenPostIncidente2);
                m_viia_list_checking_desgin_checking_urlImagen2.buildDrawingCache();
                Bitmap bitmap = m_viia_list_checking_desgin_checking_urlImagen2.getDrawingCache();

                intent.putExtra("image", bitmap);
                intent.putExtra("ID", ID);


                getContext().startActivity(intent);
            }
        });
        // Petición para obtener la imagen
        ImageRequest request = new ImageRequest(
                item.getImagen(),
                new Response.Listener <Bitmap> () {
                    @Override
                    public void onResponse(Bitmap bitmap) {

                        //Glide.with(getContext()).load(imageToString(bitmap)).into(m_imagen_Incidente);
                        m_imagen_Incidente.setImageBitmap(bitmap);
                        // Picasso.with(getContext()).load(String.valueOf(imageToString(bitmap))).into(m_imagen_Incidente);

                    }
                }, 0, 0, null, null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        Log.d(TAG, "Error en respuesta Bitmap: " + error.getMessage());
                        Log.d(TAG, "Error en respuesta Bitmap: " + error.getMessage());
                        m_imagen_Incidente.setImageResource(R.drawable.imagn);
                        m_imagen_Incidente.setVisibility(View.VISIBLE);
                    }
                });
        // Añadir petición a la cola
        requestQueue.add(request);



        return listItemView;
    }

    public List < ModelIncidente > parseJson(JSONObject jsonObject) {
        // Variables locales
        List < ModelIncidente > posts = new ArrayList< >();
        JSONArray jsonArray = null;
        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("Result");
            for (int i = 0; i < jsonArray.length(); i++) {
                try {
                    JSONObject objeto = jsonArray.getJSONObject(i);
                    ModelIncidente post = new ModelIncidente(
                            objeto.getString("urlImagen1"),
                            objeto.getString("punto"),
                            objeto.getString("createdTask"),
                            objeto.getString("usuario"),
                            objeto.getString("estado"),
                            objeto.getString("Id")
                    );
                    posts.add(post);

                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: " + e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return posts;
    }
}
