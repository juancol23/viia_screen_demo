package valcolra.viiascreen.com.proyecto_viiascreen.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

import valcolra.viiascreen.com.proyecto_viiascreen.DetalleIncidente;
import valcolra.viiascreen.com.proyecto_viiascreen.Fragmentos.ListarIncidencias;
import valcolra.viiascreen.com.proyecto_viiascreen.R;
import valcolra.viiascreen.com.proyecto_viiascreen.modelo.Picture;

/**
 * Created by Vic on 25/09/2017.
 */

public class PictureAdapterRecyclerView extends RecyclerView.Adapter<PictureAdapterRecyclerView.PictureViewHolder> {

    private static final String URL_BASE = "https://api.everlive.com/v1/e82dy3vmux1jchlu/";
    private static final String URL_JSON = "VT_incidencia";
    private ArrayList<Picture> pictures;
    private int resource;
    private Activity activity;
    private Context c;



    public PictureAdapterRecyclerView(ArrayList<Picture> pictures, int resource, Activity activity) {
        this.pictures = pictures;
        this.resource = resource;
        this.activity = activity;
    }

    @Override
    public PictureViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(resource, parent, false);
        return new PictureViewHolder(view);
    }

    @Override
    public void onBindViewHolder(PictureViewHolder holder, int position) {
        final Picture picture = pictures.get(position);
        holder.m_numIncidente.setText(picture.getNumIncidente());
        holder.m_fechaIncidente.setText(picture.getFechaIncidente());
        holder.m_statuIncidente.setText(picture.getStatuIncidente());
        holder.m_userIncidente.setText(picture.getUserIncidente());
        holder.m_puntoIncidente.setText(picture.getPuntoIncidente());

        holder.m_linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(view.getContext(),"Click "+picture.getNumIncidente(), Toast.LENGTH_SHORT).show();
               // Toast.makeText(activity,"click"+picture.getNumIncidente(),Toast.LENGTH_SHORT);
               /* Intent i = new Intent(activity, DetalleIncidente.class);
                activity.startActivity(i);*/
                Intent explicit_intent;//Declaro el Intent
                //Instanciamos el Intent dandole:
                // el contexto y la clase a la cual nos deseamos dirigir
                explicit_intent = new Intent(activity,DetalleIncidente.class);

                //ImageView bitmap = picture.ge;

                String numIncidenteP=picture.getNumIncidente();
                String fechaIncidenteP=picture.getFechaIncidente();
                String statuIncidente=picture.getStatuIncidente();
                String userIncidente=picture.getUserIncidente();
                String puntoIncidente=picture.getPuntoIncidente();

             // Bitmap bitmaps = bitmap.getDrawable();
                explicit_intent.putExtra("numIncidenteP",numIncidenteP);//Guardamos una cadena
                explicit_intent.putExtra("fechaIncidenteP",fechaIncidenteP);//Guardamos un entero
                explicit_intent.putExtra("statuIncidente",statuIncidente);//Guardamos un entero
                explicit_intent.putExtra("userIncidente",userIncidente);//Guardamos un entero
                explicit_intent.putExtra("puntoIncidente",puntoIncidente);//Guardamos un entero

                //lo iniciamos pasandole la intencion, con todos sus parametros guardados
                activity.startActivity(explicit_intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return pictures.size();
    }

    public class PictureViewHolder extends RecyclerView.ViewHolder{
        private TextView m_numIncidente;
        private TextView m_fechaIncidente;
        private TextView m_statuIncidente;
        private TextView m_userIncidente;
        private TextView m_puntoIncidente;
        public LinearLayout m_linearLayout;
        public ImageView imageView;


        public PictureViewHolder(View itemView){
            super(itemView);

            m_numIncidente = itemView.findViewById(R.id.numIncidente);
            m_fechaIncidente = itemView.findViewById(R.id.fechaIncidente);
            m_statuIncidente = itemView.findViewById(R.id.statuIncidente);
            m_userIncidente = itemView.findViewById(R.id.userIncidente);
            m_puntoIncidente = itemView.findViewById(R.id.puntoIncidente);
            m_linearLayout = itemView.findViewById(R.id.listaItemIncidencia);



        }


    }


}
