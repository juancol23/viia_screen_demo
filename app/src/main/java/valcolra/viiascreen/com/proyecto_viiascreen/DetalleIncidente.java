package valcolra.viiascreen.com.proyecto_viiascreen;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.design.widget.CollapsingToolbarLayout;

import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.design.widget.TextInputEditText;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.transition.Fade;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import valcolra.viiascreen.com.proyecto_viiascreen.view.CrearIncidencia;

public class DetalleIncidente extends AppCompatActivity {
    TextInputEditText m_detalle_punto,m_detalle_fecha,m_detalle_localizacion,m_detalle_direccion,m_detalle_status,m_detalle_creado_por,m_detalle_observacion;
    Bitmap m_detalle_imagen;
    Bitmap bitmap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle_incidente);

        showToolbar("", true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            getWindow().setEnterTransition(new Fade());
        }
        FloatingActionButton fabEditar = (FloatingActionButton) findViewById(R.id.fabEditar);
        fabEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Disponibles para Editar", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();


            }
        });
        FloatingActionButton fabEnviar = (FloatingActionButton) findViewById(R.id.fabEnviar);
        fabEnviar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Ahora es una tarea", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();

            }
        });


        m_detalle_punto = (TextInputEditText) findViewById(R.id.detalle_punto);
        m_detalle_fecha = (TextInputEditText) findViewById(R.id.detalle_fecha);
        m_detalle_localizacion = (TextInputEditText) findViewById(R.id.detalle_localizacion);
        m_detalle_direccion = (TextInputEditText) findViewById(R.id.detalle_direccion);
        m_detalle_status = (TextInputEditText) findViewById(R.id.detalle_status);
        m_detalle_creado_por = (TextInputEditText) findViewById(R.id.detalle_creado_por);
        m_detalle_observacion = (TextInputEditText) findViewById(R.id.detalle_creado_por);

        Intent intent=getIntent();
        Bundle extras =intent.getExtras();
        if (extras != null) {//ver si contiene datos

            String detalle_punto=(String)extras.get("incidentePunto");//Obtengo el nombre
            String detalle_fecha=(String)extras.get("fechaIncidenteP");//Obtengo el nombre
            String detalle_localizacion=(String)extras.get("localizacionIncidente");//Obtengo el nombre
            String detalle_direccion=(String)extras.get("direccionIncidente");//Obtengo el nombre
            String detalle_status=(String)extras.get("statusIncidente");//Obtengo el nombre
            String detalle_creado_por=(String)extras.get("creadoPorIncidente");//Obtengo el nombre
            String detalle_observacion=(String)extras.get("observacionIncidente");//Obtengo el nombre


            m_detalle_punto.setText(detalle_punto);
            m_detalle_fecha.setText(detalle_fecha);
            m_detalle_localizacion.setText(detalle_localizacion);
            m_detalle_direccion.setText(detalle_direccion);
            m_detalle_status.setText(detalle_status);
            m_detalle_creado_por.setText(detalle_creado_por);
            m_detalle_observacion.setText(detalle_observacion);

        }
    }

    public void showToolbar(String tittle, boolean upButton){
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle(tittle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(upButton);
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsingToolbar);

    }
}
