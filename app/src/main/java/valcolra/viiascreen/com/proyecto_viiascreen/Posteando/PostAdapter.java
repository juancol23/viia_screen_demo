package valcolra.viiascreen.com.proyecto_viiascreen.Posteando;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

import valcolra.viiascreen.com.proyecto_viiascreen.R;

/**
 * Created by Vic on 25/09/2017.
 */

public class PostAdapter extends ArrayAdapter {

    // Atributos
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;


    private static final String URL_BASE = "https://api.everlive.com/v1/e82dy3vmux1jchlu/";
    private static final String URL_JSON = "viiascreen_incidentes";
    private static final String TAG = "PostAdapter";
    List<Post2> items;

    public PostAdapter(Context context) {
        super(context,0);

        // Crear nueva cola de peticiones
        requestQueue= Volley.newRequestQueue(context);

        // Nueva petición JSONObject
        jsArrayRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL_BASE + URL_JSON,
                null,
                new Response.Listener<JSONObject>() {
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
        View listItemView;

        //Comprobando si el View no existe
        listItemView = null == convertView ? layoutInflater.inflate(
                R.layout.post,
                parent,
                false) : convertView;


        // Obtener el item actual
        Post2 item = items.get(position);

        // Obtener Views
        final ImageView m_imagen_Incidente = (ImageView) listItemView.
                findViewById(R.id.imagenPostIncidente);
        TextView m_texto_puntos_Incidente = (TextView) listItemView.
                findViewById(R.id.textoPuntoIncidente);
        TextView m_texto_fecha_Incidente = (TextView) listItemView.
                findViewById(R.id.textoFechaIncidente);
        TextView m_texto_usuario_Incidente = (TextView) listItemView.
                findViewById(R.id.textoUsuarioIncidente);

        TextView m_texto_location_Incidente = (TextView) listItemView.
                findViewById(R.id.textoLocationIncidente);

        TextView m_texto_address_Incidente = (TextView) listItemView.
                findViewById(R.id.textoAddressIncidente);

        TextView m_texto_status_Incidentes = (TextView) listItemView.
                findViewById(R.id.textoStatusIncidente);

        TextView m_texto_creadoPor_Incidentes = (TextView) listItemView.
                findViewById(R.id.detalle_creado_por);

        TextView m_texto_observaciones_Incidentes = (TextView) listItemView.
                findViewById(R.id.textoObservacionIncidente);


        // Actualizar los Views
        m_texto_puntos_Incidente.setText(item.getPunto());
        m_texto_fecha_Incidente.setText(item.getFecha());
        m_texto_usuario_Incidente.setText(item.getUsuario());
/*
        m_texto_location_Incidente.setText(item.getLocation());
        m_texto_address_Incidente.setText(item.getAddresso());
        m_texto_status_Incidentes.setText(item.getStatus());
        m_texto_creadoPor_Incidentes.setText(item.getCreadoPor());
        m_texto_observaciones_Incidentes.setText(item.getObservacion());
*/

        // Petición para obtener la imagen
        ImageRequest request = new ImageRequest(
                item.getImagen(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        m_imagen_Incidente.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                        //imagenPost.setImageResource(R.drawable.error);
                        Log.d(TAG, "Error en respuesta Bitmap: "+ error.getMessage());
                    }
                });
        // Añadir petición a la cola
        requestQueue.add(request);


        return listItemView;
    }

    public List<Post2> parseJson(JSONObject jsonObject){
        // Variables locales
        List<Post2> posts = new ArrayList<>();
        JSONArray jsonArray= null;

        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("Result");

            for(int i=0; i<jsonArray.length(); i++){

                try {
                    JSONObject objeto= jsonArray.getJSONObject(i);

                    Post2 post = new Post2(
                            objeto.getString("punto"),
                            objeto.getString("fecha"),
                            objeto.getString("usuario"),
                            objeto.getString("urlImagen1")
                            );


                    posts.add(post);

                } catch (JSONException e) {
                    Log.e(TAG, "Error de parsing: "+ e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return posts;
    }
}
