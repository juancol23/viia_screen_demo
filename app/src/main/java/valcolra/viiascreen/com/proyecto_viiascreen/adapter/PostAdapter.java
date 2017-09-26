package valcolra.viiascreen.com.proyecto_viiascreen.adapter;

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
import valcolra.viiascreen.com.proyecto_viiascreen.modelo.Post;

/**
 * Created by Vic on 25/09/2017.
 */

public class PostAdapter extends ArrayAdapter {

    // Atributos
    private RequestQueue requestQueue;
    JsonObjectRequest jsArrayRequest;

    private static final String URL_BASE = " http://legalmovil.com/datos/";
    private static final String URL_JSON = "social_media.json/";
    private static final String TAG = "PostAdapter";

    private TextView m_numIncidente;
    private TextView m_fechaIncidente;
    private TextView m_statuIncidente;
    private TextView m_userIncidente;
    private TextView m_puntoIncidente;
    private ImageView m_imageIncidencia;

    List<Post> items;


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
                        Log.d("ERROR TAG", "Error Respuesta en JSON: " + error.getMessage());

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
                R.layout.list_item_incidencias,
                parent,
                false) : convertView;


        // Obtener el item actual
        Post item = items.get(position);

        // Obtener Views

        m_numIncidente = listItemView.findViewById(R.id.numIncidente);
        m_fechaIncidente = listItemView.findViewById(R.id.fechaIncidente);
        m_statuIncidente = listItemView.findViewById(R.id.statuIncidente);
        m_userIncidente = listItemView.findViewById(R.id.userIncidente);
        m_puntoIncidente = listItemView.findViewById(R.id.puntoIncidente);
        m_imageIncidencia = listItemView.findViewById(R.id.puntoIncidente);

        // Actualizar los Views

        m_numIncidente.setText(item.getCodpunto());
        m_fechaIncidente.setText(item.getFecha());
        m_statuIncidente.setText(item.getStatus());
        m_userIncidente.setText(item.getUsuario());
        m_puntoIncidente.setText(item.getPuntos());


        // Petición para obtener la imagen
        ImageRequest request = new ImageRequest(
                item.getUrlImagen(),
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        m_imageIncidencia.setImageBitmap(bitmap);
                    }
                }, 0, 0, null,null,
                new Response.ErrorListener() {
                    public void onErrorResponse(VolleyError error) {
                      //  imagenPost.setImageResource(R.drawable.error);
                        Log.d("TAG ERROR", "Error en respuesta Bitmap: "+ error.getMessage());
                    }
                });

        // Añadir petición a la cola
        requestQueue.add(request);


        return listItemView;
    }

    public List<Post> parseJson(JSONObject jsonObject){
        // Variables locales
        List<Post> posts = new ArrayList<>();
        JSONArray jsonArray= null;

        try {
            // Obtener el array del objeto
            jsonArray = jsonObject.getJSONArray("items");

            for(int i=0; i<jsonArray.length(); i++){

                try {
                    JSONObject objeto= jsonArray.getJSONObject(i);

                    Post post = new Post(
                            objeto.getString("punto"),
                            objeto.getString("punto"),
                            objeto.getString("fecha"),
                            objeto.getString("usuario"),
                            objeto.getString("urlImagen1"),
                            objeto.getString("estado"));


                    posts.add(post);

                } catch (JSONException e) {
                    Log.e("TAG ERROR PASEO", "Error de parsing: "+ e.getMessage());
                }
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }


        return posts;
    }
}
